/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: GN-Platform
 *
 * $Id: TextUtil.java 196417 2016-04-05 15:50:27Z tischenko $
 *****************************************************************/
package ru.galuzin.drools_test.bof;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.Format;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TextUtil {
    public static boolean isBlank(final String str) {
        return (str == null) || (str.trim().length() == 0);
    }

    public static boolean isSame(final String o1, final String o2) {
        return isSame(o1, o2, false);
    }

    public static boolean isSame(final String o1, final String o2,
                                 final boolean ignoreCase) {
        if (isBlank(o1) && isBlank(o2)) {
            return true;
        }
        if (isBlank(o1) || isBlank(o2)) {
            return false;
        }
        return ignoreCase ? o1.trim().equalsIgnoreCase(o2.trim()) : o1.trim()
            .equals(o2.trim());
    }

    public static String nonNullStr(final String s) {
        return (s == null) ? "" : s; //$NON-NLS-1$
    }

    public static String replaceAll(final String str, final String from,
                                    final String to) {
        if (str == null) {
            return null;
        }
        if ((from == null) || (from.length() == 0)) {
            return str;
        }
        String toStr = to;
        if (toStr == null) {
            toStr = ""; //$NON-NLS-1$
        }
        String result = str;
        int p = 0;
        while (true) {
            p = result.indexOf(from, p);
            if (p == -1) {
                break;
            }
            result =
                result.substring(0, p) + toStr
                    + result.substring(p + from.length());
            p += toStr.length();
        }
        return result;
    }

    public static Locale str2locale(final String str) {
        if ((str == null) || (str.trim().length() == 0)) {
            return null;
        }
        if (str.indexOf('_') == -1) {
            return new Locale(str.trim().toLowerCase(Locale.ENGLISH));
        }
        String[] parts = str.trim().split("_"); //$NON-NLS-1$
        if (parts.length == 1) {
            return new Locale(parts[0].toLowerCase(Locale.ENGLISH));
        }
        if (parts.length == 2) {
            return new Locale(parts[0].toLowerCase(Locale.ENGLISH),
                parts[1].toUpperCase(Locale.ENGLISH));
        }
        if (parts.length == 3) {
            return new Locale(parts[0].toLowerCase(Locale.ENGLISH),
                parts[1].toUpperCase(Locale.ENGLISH), parts[2]);
        }
        throw new IllegalArgumentException("invalid locale string - " + str); //$NON-NLS-1$
    }

    public static String first(final String str, final int count) {
        if ((str == null) || (count <= 0)) {
            return ""; //$NON-NLS-1$
        }
        if (str.length() <= (count + 3)) {
            return str;
        }
        return str.substring(0, count) + "..."; //$NON-NLS-1$
    }

    public static String capitalize(final String str) {
        if (isBlank(str)) {
            return ""; //$NON-NLS-1$
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    public static String format(final Format format, final Object obj) {
        return format(format, obj, ""); //$NON-NLS-1$
    }

    public static String format(final Format format, final Object obj,
                                final String stub) {
        return (obj == null) ? stub : format.format(obj);
    }

    public static String fillLeft(final String str, final int len) {
        return fillLeft(str, ' ', len);
    }

    public static String fillLeft(final String str, final char ch, final int len) {
        if (len <= 0) {
            throw new IllegalArgumentException(
                "invalid fill left length parameter " + len); //$NON-NLS-1$
        }
        String s = (str == null) ? "" : str; //$NON-NLS-1$
        if (s.length() >= len) {
            return s;
        }
        StringBuilder result = new StringBuilder(len);
        int count = len - s.length();
        for (int i = 0; i < count; i++) {
            result.append(ch);
        }
        return result.append(s).toString();
    }

    public static String fillRight(final String str, final int len) {
        return fillRight(str, ' ', len);
    }

    public static String fillRight(final String str, final char ch,
                                   final int len) {
        if (len <= 0) {
            throw new IllegalArgumentException(
                "invalid fill right length parameter " + len); //$NON-NLS-1$
        }
        String s = (str == null) ? "" : str; //$NON-NLS-1$
        if (s.length() >= len) {
            return s;
        }
        StringBuilder result = new StringBuilder(len);
        result.append(s);
        int count = len - s.length();
        for (int i = 0; i < count; i++) {
            result.append(ch);
        }
        return result.toString();
    }


    private static void name2set(final Set<String> set, final String name) {
        if (isBlank(name)) {
            return;
        }
        for (String str : name.split("\\s+")) { //$NON-NLS-1$
            if (!isBlank(str)) {
                set.add(str.trim().toLowerCase());
            }
        }
    }

    public static int compare(final String o1, final String o2,
                              final boolean nullIsFirst, final boolean ignoreCase) {
        if (isBlank(o1) && isBlank(o2)) {
            return 0;
        }
        if (isBlank(o1) && !isBlank(o2)) {
            return nullIsFirst ? -1 : 1;
        }
        if (!isBlank(o1) && isBlank(o2)) {
            return nullIsFirst ? 1 : -1;
        }
        return ignoreCase ? o1.compareToIgnoreCase(o2) : o1.compareTo(o2);
    }

    public static String combine(final char separator, final String... parts) {
        if ((parts == null) || (parts.length == 0)) {
            return null;
        }
        if (parts.length == 1) {
            return isBlank(parts[0]) ? "" : parts[0].trim(); //$NON-NLS-1$
        }
        StringBuilder result = new StringBuilder();
        for (String part : parts) {
            if (isBlank(part)) {
                continue;
            }
            if (result.length() > 0) {
                result.append(separator);
            }
            result.append(part.trim());
        }
        return result.toString();
    }

    public static String getExceptionStackTrace(final Throwable e) {
        if (e == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        printError(e, e.toString(), sb);
        return sb.toString();
    }

    private static void printError(final Throwable t, final String header,
                                   final StringBuilder sb) {
        if (t == null) {
            return;
        }
        String nl = System.getProperty("line.separator"); //$NON-NLS-1$
        if (!TextUtil.isBlank(header)) {
            sb.append(nl).append(header).append(nl).append(nl);
        }
        StackTraceElement[] stackTrace = t.getStackTrace();
        for (StackTraceElement element : stackTrace) {
            sb.append(element.toString()).append(nl);
        }
        Throwable next = t.getCause();
        printError(next, "Caused by " + next, sb); //$NON-NLS-1$
        if (t instanceof SQLException) {
            // Handle SQLException specifically.
            next = ((SQLException) t).getNextException();
            printError(next, "Next exception: " + next, sb); //$NON-NLS-1$
        } else if (t instanceof InvocationTargetException) {
            // Handle InvocationTargetException specifically.
            next = ((InvocationTargetException) t).getTargetException();
            printError(next, "Target exception: " + next, sb); //$NON-NLS-1$
        }
    }

    public static String join(final String sep, final Iterable<?> objects) {
        StringBuilder sb = new StringBuilder();
        for (Iterator<?> it = objects.iterator(); it.hasNext();) {
            Object o = it.next();
//            sb.append(MiscUtil.toString(o));
            if (it.hasNext()) {
                sb.append(sep);
            }
        }
        return sb.toString();
    }

    public static String convertToUnicode(final String source) {
        if (TextUtil.isBlank(source)) {
            return source;
        }
        StringBuilder result = new StringBuilder(source);
        boolean inQuote = false;
        for (int n = 0; n < result.length(); n++) {
            char ch = result.charAt(n);
            if (ch == '\"') {
                inQuote = !inQuote;
                continue;
            }
            if (!inQuote) {
                continue;
            }
            if ((ch >= 0x0020) && (ch <= 0x007e)) {
                continue;
            }
            result.delete(n, n + 1);
            result.insert(n++, "\\u"); //$NON-NLS-1$
            String hex = Integer.toHexString(ch & 0xFFFF); // Get hex value of the char.
            for (int j = 0; j < (4 - hex.length()); j++) { // Prepend zeros because unicode requires 4 digits
                result.insert(++n, "0"); //$NON-NLS-1$
            }
            result.insert(++n, hex.toLowerCase());
        }
        return result.toString();
    }

    public static String join(final String separator, final String... values) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String value : values) {
            if (!isBlank(value)) {
                if ((separator != null) && (stringBuilder.length() > 0)) {
                    stringBuilder.append(separator);
                }
                stringBuilder.append(value);
            }
        }
        return stringBuilder.toString();
    }

    private TextUtil() {
        // no-op
    }

    public static String toCase(final String str, final String choice,
                                final boolean useAdditionRules) {

        if (useAdditionRules && "в".equalsIgnoreCase(choice)) {
            char last = str.charAt(str.length() - 1);
            if ((last != 'а') && (last != 'ы')) {
                return str;
            }
        }

        if (useAdditionRules && "п".equalsIgnoreCase(choice)) {
            char last = str.charAt(str.length() - 1);
            if ((last == 'и')) {
                return str;
            }
        }

        Map<String, String[]> strPub = new HashMap<String, String[]>();
        strPub.put("а", new String[] { "ы", "е", "у", "ой", "е" });
        strPub
            .put("(ш/ж/к/ч)а", new String[] { "%и", "%е", "%у", "%ой", "%е" });
        strPub.put("б/в/м/г/д/л/ж/з/к/н/п/т/ф/ч/ц/щ/р/х", new String[] { "%а",
            "%у", "%а", "%ом", "%е" });
        strPub.put("и", new String[] { "ей", "ям", "%", "ями", "ях" });
        strPub.put("ый", new String[] { "ого", "ому", "%", "ым", "ом" });
        strPub.put("й", new String[] { "я", "ю", "я", "ем", "е" });
        strPub.put("о", new String[] { "а", "у", "%", "ом", "е" });
        strPub.put("с/ш", new String[] { "%а", "%у", "%", "%ом", "%е" });
        strPub.put("ы", new String[] { "ов", "ам", "%", "ами", "ах" });
        strPub.put("ь", new String[] { "я", "ю", "я", "ем", "е" });
        strPub.put("уль", new String[] { "ули", "уле", "улю", "улей", "уле" });
        strPub
            .put("(ч/ш/д/т)ь", new String[] { "%и", "%и", "%ь", "%ью", "%и" });
        strPub.put("я", new String[] { "и", "е", "ю", "ей", "е" });

        Map<String, Integer> cases = new HashMap<String, Integer>(5);
        cases.put("р", Integer.valueOf(0));
        cases.put("д", Integer.valueOf(1));
        cases.put("в", Integer.valueOf(2));
        cases.put("т", Integer.valueOf(3));
        cases.put("п", Integer.valueOf(4));

        Integer choiseInt = cases.get(choice);
        if (choiseInt == null) {
            return str;
        }

        Map<String, Integer> exs = new HashMap<String, Integer>(2);
        exs.put("ц", Integer.valueOf(2));
        exs.put("ок", Integer.valueOf(2));

        String lastKey = null;
        String reformedStr = null;
        String grouped;
        String forPseudo = null;
        String forLong = null;
        Pattern pattern = Pattern.compile(".*\\({1}(.*)\\){1}(.*)");
        for (String key : new HashSet<String>(strPub.keySet())) {
            Matcher matcher = pattern.matcher(key);
            if ((key.length() > 1)
                && str.substring(
                    (str.length() - key.length()) >= 0 ? str.length()
                        - key.length() : 0, str.length()).equals(key)) {
                lastKey = key;
                reformedStr = str.substring(0, str.length() - key.length());
                break;
            } else if (matcher.matches()) {
                String b = matcher.group(1);
                String c = matcher.group(2);
                String[] splitted = b.split("/");

                for (String element : splitted) {
                    grouped = element + c;
                    strPub.put(grouped, strPub.get(key));
                    if ((str.length() >= grouped.length())
                        && str.substring(str.length() - grouped.length(),
                            str.length()).equals(grouped)) {
                        String[] eachSplited = strPub.get(grouped);
                        for (int x = 0; x < eachSplited.length; x++) {
                            eachSplited[x] =
                                eachSplited[x].replace("%", element);
                        }
                        reformedStr =
                            str.substring(0, str.length() - grouped.length());
                        forPseudo = grouped;
                    }
                }
            } else {
                lastKey = str.substring(str.length() - 1, str.length());
                int length = 0;
                if (forPseudo != null) {
                    length = forPseudo.length();
                }
                if ((length == 0) && (lastKey != null)) {
                    length = lastKey.length();
                }
                reformedStr = str.substring(0, str.length() - length);
            }

            if (key.matches(".*\\/+.*") && !key.matches(".*\\(+.*\\).*")
                && (lastKey != null) && key.contains(lastKey)) {
                forLong = key;
            }

            for (String e : exs.keySet()) {
                if ((str.length() >= e.length())
                    && str.substring(str.length() - e.length(), str.length())
                        .equals(e)) {
                    reformedStr =
                        str.substring(0, str.length() - exs.get(e).intValue());
                }
            }
        }
        String key = forPseudo;
        if (key == null) {
            key = forLong;
        }
        if (key == null) {
            key = lastKey;
        }
        String[] ending = strPub.get(key);
        int ch = cases.get(choice).intValue();
        if (ending != null) {
            return reformedStr + ending[ch].replace("%", lastKey);
        }
        return str;
    }

    public static int getFormByNumber(final int number) {
        if (number < 0) {
            return 0;
        }
        if ((number > 10) && (number < 20)) {
            return 3;
        }
        int last = number % 10;
        if (last == 1) {
            return 1;
        } else if ((last > 1) && (last < 5)) {
            return 2;
        }
        return 3;
    }
}
