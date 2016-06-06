/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: GN-Platform
 *
 * $Id: Base64.java 80396 2014-04-17 08:38:04Z kozlov $
 *****************************************************************/
package ru.galuzin.base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/*
 * The code is based on http://www.mscs.mu.edu/~doug/base64/
 */
public final class Base64 {
    /** Maximum line length (76) of Base64 output. */
    private final static int MAX_LINE_LENGTH = 76;

    /** The equals sign (=) as a byte. */
    private final static byte EQUALS_SIGN = (byte) '=';

    /** The new line character (\n) as a byte. */
    public final static byte NEW_LINE = (byte) '\n';

    /** The 64 valid Base64 values. */
    private final static byte[] ALPHABET = { (byte) 'A', (byte) 'B',
            (byte) 'C', (byte) 'D', (byte) 'E', (byte) 'F', (byte) 'G', (byte) 'H',
            (byte) 'I', (byte) 'J', (byte) 'K', (byte) 'L', (byte) 'M', (byte) 'N',
            (byte) 'O', (byte) 'P', (byte) 'Q', (byte) 'R', (byte) 'S', (byte) 'T',
            (byte) 'U', (byte) 'V', (byte) 'W', (byte) 'X', (byte) 'Y', (byte) 'Z',
            (byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd', (byte) 'e', (byte) 'f',
            (byte) 'g', (byte) 'h', (byte) 'i', (byte) 'j', (byte) 'k', (byte) 'l',
            (byte) 'm', (byte) 'n', (byte) 'o', (byte) 'p', (byte) 'q', (byte) 'r',
            (byte) 's', (byte) 't', (byte) 'u', (byte) 'v', (byte) 'w', (byte) 'x',
            (byte) 'y', (byte) 'z', (byte) '0', (byte) '1', (byte) '2', (byte) '3',
            (byte) '4', (byte) '5', (byte) '6', (byte) '7', (byte) '8', (byte) '9',
            (byte) '+', (byte) '/' };

    /**
     * Translates a Base64 value to either its 6-bit reconstruction value or a
     * negative number indicating some other meaning.
     */
    final static byte[] DECODABET = { -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal  0 -  8
            -5, -5, // Whitespace: Tab and Linefeed
            -9, -9, // Decimal 11 - 12
            -5, // Whitespace: Carriage Return
            -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 14 - 26
            -9, -9, -9, -9, -9, // Decimal 27 - 31
            -5, // Whitespace: Space
            -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, // Decimal 33 - 42
            62, // Plus sign at decimal 43
            -9, -9, -9, // Decimal 44 - 46
            63, // Slash at decimal 47
            52, 53, 54, 55, 56, 57, 58, 59, 60, 61, // Numbers zero through nine
            -9, -9, -9, // Decimal 58 - 60
            -1, // Equals sign at decimal 61
            -9, -9, -9, // Decimal 62 - 64
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, // Letters 'A' through 'N'
            14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, // Letters 'O' through 'Z'
            -9, -9, -9, -9, -9, -9, // Decimal 91 - 96
            26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, // Letters 'a' through 'm'
            39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, // Letters 'n' through 'z'
            -9, -9, -9, -9 // Decimal 123 - 126
        /*,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,     // Decimal 127 - 139
        -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,     // Decimal 140 - 152
        -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,     // Decimal 153 - 165
        -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,     // Decimal 166 - 178
        -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,     // Decimal 179 - 191
        -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,     // Decimal 192 - 204
        -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,     // Decimal 205 - 217
        -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,     // Decimal 218 - 230
        -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,     // Decimal 231 - 243
        -9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9,-9         // Decimal 244 - 255 */
    };

    private final static byte WHITE_SPACE_ENC = -5; // Indicates white space in encoding

    private final static byte EQUALS_SIGN_ENC = -1; // Indicates equals sign in encoding

    public static String encode(final byte[] source) {
        if (source == null) {
            return null;
        }
        if (source.length == 0) {
            return ""; //$NON-NLS-1$
        }
        return encode(source, 0, source.length);
    }

    public static String objectToString(final Serializable obj)
            throws IOException {
        if (obj == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        new ObjectOutputStream(baos).writeObject(obj);
        return encode(baos.toByteArray());
    }

    public static byte[] decode(final String str) throws DecodingException {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return new byte[0];
        }
        byte[] bytes = str.getBytes();
        return decode(bytes, 0, bytes.length);
    }

    public static Object stringToObject(final String str) throws IOException,
            ClassNotFoundException {
        if (str == null) {
            return null;
        }
        return new ObjectInputStream(new ByteArrayInputStream(decode(str)))
                .readObject();
    }

    static byte[] encode3to4(final byte[] threeBytes, final int numSigBytes) {
        byte[] dest = new byte[4];
        encode3to4(threeBytes, 0, numSigBytes, dest, 0);
        return dest;
    }

    static byte[] encode3to4(final byte[] source, final int srcOffset,
                             final int numSigBytes, final byte[] destination,
                             final int destOffset) {
        //           1         2         3
        // 01234567890123456789012345678901 Bit position
        // --------000000001111111122222222 Array position from threeBytes
        // --------|    ||    ||    ||    | Six bit groups to index ALPHABET
        //          >>18  >>12  >> 6  >> 0  Right shift necessary
        //                0x3f  0x3f  0x3f  Additional AND

        // Create buffer with zero-padding if there are only one or two
        // significant bytes passed in the array.
        // We have to shift left 24 in order to flush out the 1's that appear
        // when Java treats a value as negative that is cast from a byte to an int.
        int inBuff =
                (numSigBytes > 0 ? ((source[srcOffset] << 24) >>> 8) : 0)
                        | (numSigBytes > 1 ? ((source[srcOffset + 1] << 24) >>> 16) : 0)
                        | (numSigBytes > 2 ? ((source[srcOffset + 2] << 24) >>> 24) : 0);
        switch (numSigBytes) {
            case 3:
                destination[destOffset] = ALPHABET[(inBuff >>> 18)];
                destination[destOffset + 1] = ALPHABET[(inBuff >>> 12) & 0x3f];
                destination[destOffset + 2] = ALPHABET[(inBuff >>> 6) & 0x3f];
                destination[destOffset + 3] = ALPHABET[(inBuff) & 0x3f];
                return destination;
            case 2:
                destination[destOffset] = ALPHABET[(inBuff >>> 18)];
                destination[destOffset + 1] = ALPHABET[(inBuff >>> 12) & 0x3f];
                destination[destOffset + 2] = ALPHABET[(inBuff >>> 6) & 0x3f];
                destination[destOffset + 3] = EQUALS_SIGN;
                return destination;
            case 1:
                destination[destOffset] = ALPHABET[(inBuff >>> 18)];
                destination[destOffset + 1] = ALPHABET[(inBuff >>> 12) & 0x3f];
                destination[destOffset + 2] = EQUALS_SIGN;
                destination[destOffset + 3] = EQUALS_SIGN;
                return destination;
            default:
                return destination;
        } // end switch
    } // end encode3to4

    static String encode(final byte[] source, final int off, final int len) {
        int len43 = (len * 4) / 3;
        byte[] outBuff = new byte[(len43) // Main 4:3
                + ((len % 3) > 0 ? 4 : 0) // Account for padding
                + (len43 / MAX_LINE_LENGTH)]; // New lines
        int d = 0;
        int e = 0;
        int len2 = len - 2;
        int lineLength = 0;
        for (; d < len2; d += 3, e += 4) {
            encode3to4(source, d, 3, outBuff, e);
            lineLength += 4;
            if (lineLength == MAX_LINE_LENGTH) {
                outBuff[e + 4] = NEW_LINE;
                e++;
                lineLength = 0;
            } // end if: end of line
        } // en dfor: each piece of array
        if (d < len) {
            encode3to4(source, d, len - d, outBuff, e);
            e += 4;
        } // end if: some padding needed
        return new String(outBuff, 0, e);
    } // end encodeBytes

    /* ********  D E C O D I N G   M E T H O D S  ******** */

    /**
     * Decodes the first four bytes of array <var>fourBytes</var> and returns an
     * array up to three bytes long with the decoded values.
     *
     * @param fourBytes
     *            the array with Base64 content
     * @return array with decoded values
     * @since 1.3
     */
    static byte[] decode4to3(final byte[] fourBytes) {
        byte[] outBuff1 = new byte[3];
        int count = decode4to3(fourBytes, 0, outBuff1, 0);
        byte[] outBuff2 = new byte[count];
        for (int i = 0; i < count; i++) {
            outBuff2[i] = outBuff1[i];
        }
        return outBuff2;
    }

    /**
     * Decodes four bytes from array <var>source</var> and writes the resulting
     * bytes (up to three of them) to <var>destination</var>. The source and
     * destination arrays can be manipulated anywhere along their length by
     * specifying <var>srcOffset</var> and <var>destOffset</var>. This method
     * does not check to make sure your arrays are large enough to accomodate
     * <var>srcOffset</var> + 4 for the <var>source</var> array or
     * <var>destOffset</var> + 3 for the <var>destination</var> array. This
     * method returns the actual number of bytes that were converted from the
     * Base64 encoding.
     *
     *
     * @param source
     *            the array to convert
     * @param srcOffset
     *            the index where conversion begins
     * @param destination
     *            the array to hold the conversion
     * @param destOffset
     *            the index where output will be put
     * @return the number of decoded bytes converted
     * @since 1.3
     */
    static int decode4to3(final byte[] source, final int srcOffset,
                          final byte[] destination, final int destOffset) {
        // Example: Dk==
        if (source[srcOffset + 2] == EQUALS_SIGN) {
            int outBuff =
                    ((DECODABET[source[srcOffset]] << 24) >>> 6)
                            | ((DECODABET[source[srcOffset + 1]] << 24) >>> 12);

            destination[destOffset] = (byte) (outBuff >>> 16);
            return 1;
        }
        // Example: DkL=
        else if (source[srcOffset + 3] == EQUALS_SIGN) {
            int outBuff =
                    ((DECODABET[source[srcOffset]] << 24) >>> 6)
                            | ((DECODABET[source[srcOffset + 1]] << 24) >>> 12)
                            | ((DECODABET[source[srcOffset + 2]] << 24) >>> 18);

            destination[destOffset] = (byte) (outBuff >>> 16);
            destination[destOffset + 1] = (byte) (outBuff >>> 8);
            return 2;
        }
        // Example: DkLE
        else {
            int outBuff =
                    ((DECODABET[source[srcOffset]] << 24) >>> 6)
                            | ((DECODABET[source[srcOffset + 1]] << 24) >>> 12)
                            | ((DECODABET[source[srcOffset + 2]] << 24) >>> 18)
                            | ((DECODABET[source[srcOffset + 3]] << 24) >>> 24);

            destination[destOffset] = (byte) (outBuff >> 16);
            destination[destOffset + 1] = (byte) (outBuff >> 8);
            destination[destOffset + 2] = (byte) (outBuff);
            return 3;
        }
    } // end decodeToBytes

    static byte[] decode(final byte[] source, final int off, final int len)
            throws DecodingException {
        int len34 = (len * 3) / 4;
        byte[] outBuff = new byte[len34]; // Upper limit on size of output
        int outBuffPosn = 0;
        byte[] b4 = new byte[4];
        int b4Posn = 0;
        int i = 0;
        byte sbiCrop = 0;
        byte sbiDecode = 0;
        for (i = 0; i < len; i++) {
            sbiCrop = (byte) (source[i] & 0x7f); // Only the low seven bits
            sbiDecode = DECODABET[sbiCrop];

            if (sbiDecode >= WHITE_SPACE_ENC) {// White space, Equals sign or better
                if (sbiDecode >= EQUALS_SIGN_ENC) {
                    b4[b4Posn++] = sbiCrop;
                    if (b4Posn > 3) {
                        outBuffPosn += decode4to3(b4, 0, outBuff, outBuffPosn);
                        b4Posn = 0;
                        // If that was the equals sign, break out of 'for' loop
                        if (sbiCrop == EQUALS_SIGN) {
                            break;
                        }
                    } // end if: quartet built
                } // end if: equals sign or better
            } // end if: white space, equals sign or better
            else {
                throw new DecodingException(String.format(
                        "bad Base64 input character at %s: %s (decimal)", //$NON-NLS-1$
                        Integer.valueOf(i), Byte.valueOf(source[i])));
            } // end else:
        } // each input character
        byte[] out = new byte[outBuffPosn];
        System.arraycopy(outBuff, 0, out, 0, outBuffPosn);
        return out;
    } // end decode

    private Base64() {
        // no-op
    }

    public static class DecodingException extends IOException {
        private static final long serialVersionUID = -2512490204932774444L;

        DecodingException(final String msg) {
            super(msg);
        }
    }
}
