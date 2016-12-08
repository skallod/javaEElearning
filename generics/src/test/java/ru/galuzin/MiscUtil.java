package ru.galuzin;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by galuzin on 09.11.2016.
 */
public class MiscUtil {
    public static int deltaDays(final Date to, final Date from) {
        if (to == null) {
            return 0;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(to);

        Calendar current = Calendar.getInstance();
        if (from != null) {
            current.setTime(from);
        }

        int deltaYears = c.get(Calendar.YEAR) - current.get(Calendar.YEAR);
        int deltaDays;
        if (deltaYears == 0) {
            deltaDays =
                    c.get(Calendar.DAY_OF_YEAR) - current.get(Calendar.DAY_OF_YEAR);
        } else {
            int daysCount = current.getActualMaximum(Calendar.DAY_OF_YEAR);
            deltaDays = (daysCount + c.get(Calendar.DAY_OF_YEAR))
                    - current.get(Calendar.DAY_OF_YEAR);
        }
        return deltaDays;
    }
    public static void clearTime(final Calendar cldr) {
        cldr.set(Calendar.HOUR_OF_DAY, 0);
        cldr.set(Calendar.MINUTE, 0);
        cldr.set(Calendar.SECOND, 0);
        cldr.set(Calendar.MILLISECOND, 0);
    }

//    public static boolean isOffDay(final Date date) {
//        return offDays.contains(MiscUtil.clearTime(date));
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
}
