///*****************************************************************
// * Gridnine AB http://www.gridnine.com
// * Project: GN-Platform
// *
// * $Id: MiscUtil.java 181404 2016-01-22 14:00:49Z kozlov $
// *****************************************************************/
//package ru.galuzin.drools_test.bof;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//
//import java.io.BufferedOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.FilenameFilter;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.math.BigDecimal;
//import java.math.BigInteger;
//import java.net.URL;
//import java.security.MessageDigest;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.GregorianCalendar;
//import java.util.List;
//import java.util.Locale;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.TimeZone;
//import java.util.concurrent.TimeUnit;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipInputStream;
//
//public final class MiscUtil {
//    public static final Log TIMING_LOG = LogFactory
//        .getLog("com.gridnine.bof.TIMING"); //$NON-NLS-1$
//
//    private static long lastTimestamp;
//
//    public final static int CURRENT_DECADE = 1;
//
//    public final static int LAST_DECADE = 2;
//
//    public static <T> boolean equals(final T o1, final T o2) {
//        if (o1 == null) {
//            return o2 == null;
//        }
//        if (o2 == null) {
//            return false;
//        }
//        if (boolean[].class.isInstance(o1)) {
//            return Arrays.equals((boolean[]) o1, (boolean[]) o2);
//        }
//        if (byte[].class.isInstance(o1)) {
//            return Arrays.equals((byte[]) o1, (byte[]) o2);
//        }
//        if (char[].class.isInstance(o1)) {
//            return Arrays.equals((char[]) o1, (char[]) o2);
//        }
//        if (short[].class.isInstance(o1)) {
//            return Arrays.equals((short[]) o1, (short[]) o2);
//        }
//        if (int[].class.isInstance(o1)) {
//            return Arrays.equals((int[]) o1, (int[]) o2);
//        }
//        if (long[].class.isInstance(o1)) {
//            return Arrays.equals((long[]) o1, (long[]) o2);
//        }
//        if (float[].class.isInstance(o1)) {
//            return Arrays.equals((float[]) o1, (float[]) o2);
//        }
//        if (double[].class.isInstance(o1)) {
//            return Arrays.equals((double[]) o1, (double[]) o2);
//        }
//        if (Object[].class.isInstance(o1)) {
//            return Arrays.equals((Object[]) o1, (Object[]) o2);
//        }
//        if (BigDecimal.class.isInstance(o1) && BigDecimal.class.isInstance(o2)) {
//            return ((BigDecimal) o1).compareTo((BigDecimal) o2) == 0;
//        }
//        if (Date.class.isInstance(o1) && Date.class.isInstance(o2)) {
//            return ((Date) o1).getTime() == ((Date) o2).getTime();
//        }
//        return o1.equals(o2);
//    }
//
//    public static int compare(final Date date1, final Date date2) {
//        long time1 = date1.getTime();
//        long time2 = date2.getTime();
//        return (time1 < time2) ? -1 : ((time1 == time2) ? 0 : 1);
//    }
//
//    public static int compare(final Date date1, final Date date2,
//                              final boolean nullIsLess) {
//        if (date1 == null) {
//            return date2 == null ? 0 : (nullIsLess ? -1 : 1);
//        }
//        if (date2 == null) {
//            return nullIsLess ? 1 : -1;
//        }
//
//        long time1 = date1.getTime();
//        long time2 = date2.getTime();
//        return (time1 < time2) ? -1 : ((time1 == time2) ? 0 : 1);
//    }
//
//    public static int compare(final String string1, final String string2,
//                              final boolean nullIsLess, final boolean ignoreCase) {
//        if (((string1 == null) || TextUtil.isBlank(string1))) {
//            return ((string2 == null) || TextUtil.isBlank(string1)) ? 0
//                : (nullIsLess ? -1 : 1);
//        }
//        if (((string2 == null) || TextUtil.isBlank(string1))) {
//            return nullIsLess ? 1 : -1;
//        }
//
//        if (ignoreCase) {
//            return string1.compareToIgnoreCase(string2);
//        }
//
//        return string1.compareTo(string2);
//    }
//
//    public static Date getGmtDate() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.MILLISECOND,
//            calendar.getTimeZone().getOffset(calendar.getTimeInMillis()));
//        return calendar.getTime();
//    }
//
//    /**
//     * Useful to convert SQL types {@link java.sql.Timestamp} and
//     * {@link java.sql.Date} to standard {@link Date}.
//     *
//     * @param date
//     *            date to be cloned, may be <code>null</code>
//     * @return date clone
//     */
//    public static Date cloneDate(final Date date) {
//        if (date == null) {
//            return null;
//        }
//        return new Date(date.getTime());
//    }
//
//    public static Date clearTime(final Date value) {
//        if (value == null) {
//            return value;
//        }
//        Calendar cldr = Calendar.getInstance();
//        cldr.setTime(value);
//        clearTime(cldr);
//        return cldr.getTime();
//    }
//
//    public static void clearTime(final Calendar cldr) {
//        cldr.set(Calendar.HOUR_OF_DAY, 0);
//        cldr.set(Calendar.MINUTE, 0);
//        cldr.set(Calendar.SECOND, 0);
//        cldr.set(Calendar.MILLISECOND, 0);
//    }
//
//    public static Date setDayEndTime(final Date value) {
//        if (value == null) {
//            return value;
//        }
//        Calendar cldr = Calendar.getInstance();
//        cldr.setTime(value);
//        cldr.set(Calendar.HOUR_OF_DAY, 23);
//        cldr.set(Calendar.MINUTE, 59);
//        cldr.set(Calendar.SECOND, 59);
//        cldr.set(Calendar.MILLISECOND, 999);
//        return cldr.getTime();
//    }
//
//    public static boolean isBetween(final Date date1, final Date date2,
//                                    final Date date) {
//        if (date == null) {
//            return false;
//        }
//        if ((date1 != null) && date1.after(date)) {
//            return false;
//        }
//        if ((date2 != null) && date2.before(date)) {
//            return false;
//        }
//        return true;
//    }
//
//    public static boolean isSameDate(final Date date1, final Date date2) {
//        if (date1 == null) {
//            return date2 == null;
//        }
//        if (date2 == null) {
//            return false;
//        }
//        Calendar cldr = Calendar.getInstance();
//        cldr.setTime(date1);
//        clearTime(cldr);
//        long time1 = cldr.getTimeInMillis();
//        cldr.setTime(date2);
//        clearTime(cldr);
//        long time2 = cldr.getTimeInMillis();
//        return time1 == time2;
//    }
//
//    public static void logTiming(final long start, final String msg) {
//        if (!TIMING_LOG.isDebugEnabled()) {
//            return;
//        }
//        long timing = System.currentTimeMillis() - start;
//        if (timing < 1000) {
//            TIMING_LOG.debug(msg + ", timing=" + timing + " msec"); //$NON-NLS-1$ //$NON-NLS-2$
//        } else {
//            TIMING_LOG.debug(msg + ", timing=" + (timing / 1000) + " sec"); //$NON-NLS-1$ //$NON-NLS-2$
//        }
//    }
//
//    public static synchronized long getTimestamp() {
//        long result = System.currentTimeMillis();
//        if (result <= lastTimestamp) {
//            result = ++lastTimestamp;
//        } else {
//            lastTimestamp = result;
//        }
//        return result;
//    }
//
//    public static int find(final byte[] arr, final byte[] subArr,
//            final int start) {
//        if ((arr.length == 0) || (subArr.length == 0) || (start > arr.length)
//            || (arr.length < subArr.length)) {
//            return -1;
//        }
//        for (int i = start; i < (arr.length - subArr.length); i++) {
//            for (int j = 0; j < subArr.length; j++) {
//                if (arr[i + j] != subArr[j]) {
//                    break;
//                }
//                if (j == (subArr.length - 1)) {
//                    return i;
//                }
//            }
//        }
//        return -1;
//    }
//
//    public static String toString(final Object obj) {
//        return (obj == null) ? "" : obj.toString(); //$NON-NLS-1$
//    }
//
//    public static void unpack(final URL zipFile, final File folder)
//            throws IOException {
//        unpack(zipFile, folder, null);
//    }
//
//    public static void unpack(final URL zipFile, final File folder,
//                              final FilenameFilter filter) throws IOException {
//        ZipInputStream zipStrm =
//            new ZipInputStream(IoUtil.getResourceInputStream(zipFile));
//        try {
//            ZipEntry entry;
//            while ((entry = zipStrm.getNextEntry()) != null) {
//                if ((filter != null) && !filter.accept(folder, entry.getName())) {
//                    continue;
//                }
//                unpackEntry(zipStrm, entry, folder);
//            }
//        } finally {
//            zipStrm.close();
//        }
//    }
//
//    private static void unpackEntry(final ZipInputStream zipStrm,
//                                    final ZipEntry entry, final File baseFolder) throws IOException {
//        String name = entry.getName();
//        if (name.endsWith("/")) { //$NON-NLS-1$
//            File folder = new File(baseFolder.getCanonicalPath() + '/' + name);
//            if (!folder.exists() && !folder.mkdirs()) {
//                throw new IOException("can't create folder " + folder); //$NON-NLS-1$
//            }
//            folder.setLastModified(entry.getTime());
//            return;
//        }
//        File file = new File(baseFolder.getCanonicalPath() + '/' + name);
//        File folder = file.getParentFile();
//        if (!folder.exists() && !folder.mkdirs()) {
//            throw new IOException("can't create folder " + folder); //$NON-NLS-1$
//        }
//        OutputStream strm =
//            new BufferedOutputStream(new FileOutputStream(file, false));
////        try {
////            IoUtil.copyStream(zipStrm, strm, 1024);
////        } finally {
////            strm.close();
////        }
//        file.setLastModified(entry.getTime());
//    }
//
//    public static String getCheckSum(final InputStream strm) throws Exception {
//        if (strm == null) {
//            return null;
//        }
//        MessageDigest md = MessageDigest.getInstance("MD5"); //$NON-NLS-1$
//        updateCheckSum(md, strm);
////        return Base64.encode(md.digest());
//    }
//
//    public static String getCheckSum(final byte content[]) throws Exception {
//        if (content == null) {
//            return null;
//        }
//        MessageDigest md = MessageDigest.getInstance("MD5"); //$NON-NLS-1$
//        md.update(content);
////        return Base64.encode(md.digest());
//    }
//
//    public static String getCheckSum(final URL url, final boolean unpackZip)
//            throws Exception {
//        if (url == null) {
//            return null;
//        }
//        if (!unpackZip || !url.getFile().toLowerCase().endsWith(".zip")) { //$NON-NLS-1$
////            InputStream is = IoUtil.getResourceInputStream(url);
////            try {
////                return getCheckSum(is);
////            } finally {
////                is.close();
////            }
//        }
//        MessageDigest md = MessageDigest.getInstance("MD5"); //$NON-NLS-1$
////        ZipInputStream zipStrm =
////            new ZipInputStream(IoUtil.getResourceInputStream(url));
//        try {
//            calculateCheckSum(md, zipStrm);
//        } finally {
//            zipStrm.close();
//        }
//        return Base64.encode(md.digest());
//    }
//
//    @SuppressWarnings("nls")
//    private static void calculateCheckSum(final MessageDigest md,
//            final ZipInputStream strm) throws IOException {
//        ZipEntry entry;
//        while ((entry = strm.getNextEntry()) != null) {
//            String entryName = entry.getName();
//            md.update(entryName.getBytes("utf-8"));
//            if (entryName.endsWith("/")) {
//                continue;
//            }
//            entryName = entryName.toLowerCase();
//            if (entryName.endsWith(".zip") || entryName.endsWith(".jar")) {
//                calculateCheckSum(md, new ZipInputStream(strm));
//            } else {
//                updateCheckSum(md, strm);
//            }
//        }
//    }
//
//    private static void updateCheckSum(final MessageDigest md,
//            final InputStream strm) throws IOException {
//        byte buf[] = new byte[256];
//        int len;
//        while ((len = strm.read(buf)) != -1) {
//            md.update(buf, 0, len);
//        }
//    }
//
//    public static Date getStartDecade(final int decadeType) {
//        GregorianCalendar cal = new GregorianCalendar();
//        int curDay = cal.get(Calendar.DATE);
//        if (decadeType == CURRENT_DECADE) {
//            if ((curDay >= 1) && (curDay <= 10)) {
//                cal.set(Calendar.DATE, 1);
//                return MiscUtil.clearTime(cal.getTime());
//            } else if ((curDay >= 11) && (curDay <= 20)) {
//                cal.set(Calendar.DATE, 11);
//                return MiscUtil.clearTime(cal.getTime());
//            } else {
//                cal.set(Calendar.DATE, 21);
//                return MiscUtil.clearTime(cal.getTime());
//            }
//        } else if (decadeType == LAST_DECADE) {
//            if ((curDay >= 1) && (curDay <= 10)) {
//                cal.set(Calendar.DATE, 1);
//                cal.add(Calendar.DATE, -1);
//                cal.set(Calendar.DATE, 21);
//                return MiscUtil.clearTime(cal.getTime());
//            } else if ((curDay >= 11) && (curDay <= 20)) {
//                cal.set(Calendar.DATE, 1);
//                return MiscUtil.clearTime(cal.getTime());
//            } else {
//                cal.set(Calendar.DATE, 11);
//                return MiscUtil.clearTime(cal.getTime());
//            }
//        }
//        return null;
//    }
//
//    public static Date getEndDecade(final int decadeType) {
//        GregorianCalendar cal = new GregorianCalendar();
//        int curDay = cal.get(Calendar.DATE);
//        if (decadeType == CURRENT_DECADE) {
//            if ((curDay >= 1) && (curDay <= 10)) {
//                cal.set(Calendar.DATE, 10);
//                return MiscUtil.setDayEndTime(cal.getTime());
//            } else if ((curDay >= 11) && (curDay <= 20)) {
//                cal.set(Calendar.DATE, 20);
//                return MiscUtil.setDayEndTime(cal.getTime());
//            } else {
//                cal.set(Calendar.DATE,
//                    cal.getActualMaximum(Calendar.DAY_OF_MONTH));
//                return MiscUtil.setDayEndTime(cal.getTime());
//            }
//        } else if (decadeType == LAST_DECADE) {
//            if ((curDay >= 1) && (curDay <= 10)) {
//                cal.set(Calendar.DATE, 1);
//                cal.add(Calendar.DATE, -1);
//                return MiscUtil.setDayEndTime(cal.getTime());
//            } else if ((curDay >= 11) && (curDay <= 20)) {
//                cal.set(Calendar.DATE, 10);
//                return MiscUtil.setDayEndTime(cal.getTime());
//            } else {
//                cal.set(Calendar.DATE, 20);
//                return MiscUtil.setDayEndTime(cal.getTime());
//            }
//        }
//        return null;
//    }
//
//    public static <T> T findByLocale(final Map<Locale, T> map,
//            final Locale locale) {
//        if (locale == null) {
//            return null;
//        }
//        // try exact match first
//        T result = map.get(locale);
//        if (result != null) {
//            return result;
//        }
//        // now language part matching only is also OK
//        for (Entry<Locale, T> entry : map.entrySet()) {
//            if (TextUtil.isSame(locale.getLanguage(), entry.getKey()
//                .getLanguage())) {
//                return entry.getValue();
//            }
//        }
//        return null;
//    }
//
//    public static class Pair<T1, T2> {
//        private T1 first;
//
//        private T2 second;
//
//        public Pair(final T1 f, final T2 s) {
//            first = f;
//            second = s;
//        }
//
//        public T1 getFirst() {
//            return first;
//        }
//
//        public void setFirst(final T1 value) {
//            first = value;
//        }
//
//        public T2 getSecond() {
//            return second;
//        }
//
//        public void setSecond(final T2 value) {
//            second = value;
//        }
//
//        public static <T1, T2> Pair<T1, T2> of(final T1 o1, final T2 o2) {
//            return new Pair<T1, T2>(o1, o2);
//        }
//
//        @Override
//        public boolean equals(final Object obj) {
//            if (this == obj) {
//                return true;
//            }
//            if (!(obj instanceof Pair)) {
//                return false;
//            }
//            Pair<?, ?> that = (Pair<?, ?>) obj;
//            return MiscUtil.equals(getFirst(), that.getFirst())
//                && MiscUtil.equals(getSecond(), that.getSecond());
//        }
//
//        @Override
//        public int hashCode() {
//            return MiscUtil.hash(getFirst(), getSecond());
//        }
//    }
//
//    public static <T> T guarded(final T obj, final T... deflts) {
//        if (obj != null) {
//            return obj;
//        }
//        for (T deft : deflts) {
//            if (deft != null) {
//                return deft;
//            }
//        }
//        return null;
//    }
//
//    @SuppressWarnings("unchecked")
//    public static <TEl> List<TEl> guardedList(final List<TEl> list) {
//        return guarded(list, new ArrayList<TEl>(0));
//    }
//
//    public static BigDecimal guardedBDec(final BigDecimal value) {
//        return value == null ? BigDecimal.ZERO : value;
//    }
//
//    public static BigDecimal guardedBDec(final Double value) {
//        return value == null ? BigDecimal.ZERO : BigDecimal.valueOf(value
//            .doubleValue());
//    }
//
//    public static BigInteger guardedBInt(final BigInteger value) {
//        return value == null ? BigInteger.ZERO : value;
//    }
//
//    public static Integer guardedInt(final Integer i) {
//        return i == null ? Integer.valueOf(0) : i;
//    }
//
//    public static String guardedStr(final String str) {
//        return guarded(str, "");
//    }
//
//    public static int hashCode(final Object o) {
//        return o == null ? 0 : o.hashCode();
//    }
//
//    public static int hash(final Object... objects) {
//        int result = 37;
//        for (Object o : objects) {
//            result = (result * 13) + hashCode(o);
//        }
//        return result;
//    }
//
//    private MiscUtil() {
//        // no-op
//    }
//
//    public static int getAgeInYears(final Date checkDate, final Date birthDate) {
//        Calendar checkCldr = Calendar.getInstance();
//        checkCldr.setTime(checkDate);
//        Calendar bdCldr = Calendar.getInstance();
//        bdCldr.setTime(birthDate);
//        int result = checkCldr.get(Calendar.YEAR) - bdCldr.get(Calendar.YEAR);
//        if (result <= 0) {
//            return result;
//        }
//        if ((bdCldr.get(Calendar.MONTH) > checkCldr.get(Calendar.MONTH))
//            || ((bdCldr.get(Calendar.MONTH) == checkCldr.get(Calendar.MONTH)) && (bdCldr
//                .get(Calendar.DAY_OF_MONTH) > checkCldr
//                .get(Calendar.DAY_OF_MONTH)))) {
//            return result - 1;
//        }
//        return result;
//    }
//
//    public static int getAgeInMonths(final Date checkDate, final Date birthDate) {
//        Calendar checkCldr = Calendar.getInstance();
//        checkCldr.setTime(checkDate);
//        Calendar bdCldr = Calendar.getInstance();
//        bdCldr.setTime(birthDate);
//        int result =
//            (((checkCldr.get(Calendar.YEAR) - bdCldr.get(Calendar.YEAR)) * 12) + checkCldr
//                .get(Calendar.MONTH)) - bdCldr.get(Calendar.MONTH);
//        if ((bdCldr.get(Calendar.MONTH) > checkCldr.get(Calendar.MONTH))
//            || ((bdCldr.get(Calendar.MONTH) == checkCldr.get(Calendar.MONTH)) && (bdCldr
//                .get(Calendar.DAY_OF_MONTH) > checkCldr
//                .get(Calendar.DAY_OF_MONTH)))) {
//            return result - 1;
//        }
//        return result;
//    }
//
//    public static int deltaDays(final Date to, final Date from) {
//        if (to == null) {
//            return 0;
//        }
//        Calendar c = Calendar.getInstance();
//        c.setTime(to);
//
//        Calendar current = Calendar.getInstance();
//        if (from != null) {
//            current.setTime(from);
//        }
//
//        int deltaYears = c.get(Calendar.YEAR) - current.get(Calendar.YEAR);
//        int deltaDays;
//        if (deltaYears == 0) {
//            deltaDays =
//                c.get(Calendar.DAY_OF_YEAR) - current.get(Calendar.DAY_OF_YEAR);
//        } else {
//            int daysCount = current.getActualMaximum(Calendar.DAY_OF_YEAR);
//            deltaDays =
//                (daysCount + c.get(Calendar.DAY_OF_YEAR))
//                    - current.get(Calendar.DAY_OF_YEAR);
//        }
//        return deltaDays;
//    }
//
//    public static double deltaHours(final Date to, final Date from) {
//        if (to == null) {
//            return 0;
//        }
//        Date current = from;
//        if (from == null) {
//            current = new Date();
//        }
//        long diff = to.getTime() - current.getTime();
//        return (diff / 3600000d);
//    }
//
//    public static Date toTimeZone(final Date date, final String timeZone) {
//        return toTimeZone(date, TimeZone.getTimeZone(timeZone));
//    }
//
//    public static Date toTimeZone(final Date date, final TimeZone timeZone) {
//        if ((date == null) || (timeZone == null)) {
//            return null;
//        }
//        Calendar cldr = Calendar.getInstance();
//        cldr.setTime(date);
//        int offset = timeZone.getOffset(date.getTime());
//        int offset2 = cldr.getTimeZone().getOffset(cldr.getTime().getTime());
//        cldr.add(Calendar.MILLISECOND, offset - offset2);
//        return cldr.getTime();
//    }
//
//    public static Date addToDate(final Date date, final TimeUnit unit,
//                                 final int duration) {
//        if ((date == null) || (unit == null) || (duration == 0)) {
//            return date;
//        }
//        return new Date(date.getTime() + unit.toMillis(duration));
//    }
//}
