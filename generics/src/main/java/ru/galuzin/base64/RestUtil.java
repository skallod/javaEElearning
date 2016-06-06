/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: BOF Unifest
 *
 * $Id: RestUtil.java 116502 2014-12-24 09:40:52Z kozlov $
 *****************************************************************/
package ru.galuzin.base64;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


public final class RestUtil {
    private static final String[] EMPTY_ARR = {};

    public static final ThreadLocal<DateFormat> DATE_FORMATS =
            new ThreadLocal<DateFormat>() {
                @Override
                protected DateFormat initialValue() {
                    return new SimpleDateFormat("yyyy-MM-dd");
                }
            };

    public static final ThreadLocal<DateFormat> DATE_TIME_LOCAL_FORMATS =
            new ThreadLocal<DateFormat>() {
                @Override
                protected DateFormat initialValue() {
                    return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                }
            };

    public static final ThreadLocal<DateFormat> DATE_TIME_FULL_FORMATS =
            new ThreadLocal<DateFormat>() {
                @Override
                protected DateFormat initialValue() {
                    return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
                }
            };

    public static final ThreadLocal<DateFormat> DATE_TIME_LONG_FORMATS =
            new ThreadLocal<DateFormat>() {
                @Override
                protected DateFormat initialValue() {
                    return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                }
            };

    public static final ThreadLocal<DateFormat> DATE_TIME_FULL_SIMPLE =
            new ThreadLocal<DateFormat>() {
                @Override
                protected DateFormat initialValue() {
                    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                }
            };

    public static final ThreadLocal<DateFormat> DATE_TIME_HOUR_FORMATS =
            new ThreadLocal<DateFormat>() {
                @Override
                protected DateFormat initialValue() {
                    return new SimpleDateFormat("HH");
                }
            };

    public static final ThreadLocal<DateFormat> DATE_TIME_MINUTES_FORMATS =
            new ThreadLocal<DateFormat>() {
                @Override
                protected DateFormat initialValue() {
                    return new SimpleDateFormat("mm");
                }
            };

    public static final ThreadLocal<DateFormat> DATE_TIME_HOUR_MINUTES_FORMATS =
            new ThreadLocal<DateFormat>() {
                @Override
                protected DateFormat initialValue() {
                    return new SimpleDateFormat("HH:mm");
                }
            };

    public static final ThreadLocal<DateFormat> DATE_CARD_EXPIRED_FORMATS =
            new ThreadLocal<DateFormat>() {
                @Override
                protected DateFormat initialValue() {
                    return new SimpleDateFormat("MM/yy");
                }
            };

    public static String[] safeGet(final Map<String, String[]> parameters,
                                   final String name) {
        String[] result = parameters.get(name);
        return result == null ? EMPTY_ARR : result;
    }

    public static String readString(final Map<String, String[]> parameters,
                                    final String name) {
        String[] values = parameters.get(name);
        if ((values == null) || (values.length == 0)) {
            return null;
        }
        return values[0];
    }

//    public static <E extends Enum<E>> E readEnum(
//            final Map<String, String[]> parameters, final Class<E> cls,
//            final String name) {
//        String[] values = parameters.get(name);
//        if ((values == null) || (values.length == 0)) {
//            return null;
//        }
//        return CollectionUtil.findEnumConstant(cls, values[0]);
//    }

    public static boolean readBoolean(final Map<String, String[]> parameters,
                                      final String name) {
        String[] values = parameters.get(name);
        if ((values == null) || (values.length == 0)) {
            return false;
        }
        return Boolean.parseBoolean(values[0]);
    }

    public static int readInt(final Map<String, String[]> parameters,
                              final String name, final int def) throws NumberFormatException {
        String[] values = parameters.get(name);
        if ((values == null) || (values.length == 0)) {
            return def;
        }
        return Integer.parseInt(values[0]);
    }

    public static int readInt(final Map<String, String[]> parameters,
                              final String name) throws NumberFormatException {
        return readInt(parameters, name, 0);
    }

    public static Integer readInteger(final Map<String, String[]> parameters,
                                      final String name) throws NumberFormatException {
        String[] values = parameters.get(name);
        if ((values == null) || (values.length == 0)) {
            return null;
        }
        return new Integer(values[0]);
    }

    public static Date readDate(final Map<String, String[]> parameters,
                                final String name) {
        String dateStr = RestUtil.readString(parameters, name);
        if (dateStr == null) {
            return null;
        }
        try {
            return RestUtil.DATE_FORMATS.get().parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date readDateUnsafe(final Map<String, String[]> parameters,
                                      final String name) throws ParseException {
        String dateStr = RestUtil.readString(parameters, name);
        if (dateStr == null) {
            return null;
        }
        return RestUtil.DATE_FORMATS.get().parse(dateStr);
    }

    public static String encodeBase64(final byte[] source) {
        return Base64.encode(source).replaceAll(
                String.valueOf((char) Base64.NEW_LINE), "");
    }

    public static byte[] decodeBase64(final String str)
            throws Base64.DecodingException {
        return Base64.decode(str);
    }

    private RestUtil() {
        // no-op
    }
}
