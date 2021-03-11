/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: BOF
 *
 * $Id: TemplateUtil.java 113212 2014-12-10 09:13:36Z kozlov $
 *****************************************************************/
package ru.galuzin.time_format;

import java.math.BigDecimal;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

//import org.apache.commons.logging.LogFactory;
//
//import com.gridnine.bof.common.Environment;
//import com.gridnine.bof.common.l10n.LocaleManager;
//import com.gridnine.bof.common.model.dict.BaseDictionary;
//import com.gridnine.bof.common.model.dict.BaseExtendDictionary;
//import com.gridnine.bof.common.model.dict.CodeSystem;
//import com.gridnine.bof.common.model.dict.Country;
//import com.gridnine.bof.common.model.dict.CurrencyInfo;
//import com.gridnine.bof.common.model.dict.DeclensionDirection;
//import com.gridnine.bof.common.model.dict.DictionaryCache;
//import com.gridnine.bof.common.model.dict.DictionaryReference;
//import com.gridnine.bof.common.model.dict.GeoLocation;
//import com.gridnine.bof.common.model.dict.GeoRegion;
//import com.gridnine.bof.common.model.dict.LocationType;
//import com.gridnine.bof.common.model.dict.PreferenceKey;
//import com.gridnine.bof.common.model.helpers.DictHelper;
//import com.gridnine.bof.common.model.system.Money;
//import com.gridnine.bof.common.util.MiscUtil;
//import com.gridnine.bof.common.util.TextUtil;

@SuppressWarnings("nls")
public final class TemplateUtil {

    private static final Map<Locale, DateFormatSymbols> dfSymbols;

    private static final Map<Locale, List<String>> minForms;

    private static final Map<Locale, List<String>> hourForms;

    static {
        Map<Locale, DateFormatSymbols> dfSymbolsTemp =
                new HashMap<Locale, DateFormatSymbols>();
        {
            Locale l = new Locale("ru"); //$NON-NLS-1$
            DateFormatSymbols dfs = new DateFormatSymbols(l);
            dfs.setMonths(new String[] { "января", //$NON-NLS-1$
                    "февраля", //$NON-NLS-1$
                    "марта", //$NON-NLS-1$
                    "апреля", //$NON-NLS-1$
                    "мая", //$NON-NLS-1$
                    "июня", //$NON-NLS-1$
                    "июля", //$NON-NLS-1$
                    "августа", //$NON-NLS-1$
                    "сентября", //$NON-NLS-1$
                    "октября", //$NON-NLS-1$
                    "ноября", //$NON-NLS-1$
                    "декабря" }); //$NON-NLS-1$
            dfSymbolsTemp.put(l, dfs);
        }
        dfSymbols = Collections.unmodifiableMap(dfSymbolsTemp);

        minForms = new HashMap<Locale, List<String>>();
        hourForms = new HashMap<Locale, List<String>>();
        {
            Locale l = new Locale("ru");
            List<String> minutes = new ArrayList<String>();
            minutes.add("");
            minutes.add("минута");
            minutes.add("минуты");
            minutes.add("минут");
            minForms.put(l, minutes);
            List<String> hours = new ArrayList<String>();
            hours.add("");
            hours.add("час");
            hours.add("часа");
            hours.add("часов");
            hourForms.put(l, hours);
        }
        {
            Locale l = new Locale("en");
            List<String> minutes = new ArrayList<String>();
            minutes.add("");
            minutes.add("minute");
            minutes.add("minutes");
            minutes.add("minutes");
            minForms.put(l, minutes);
            List<String> hours = new ArrayList<String>();
            hours.add("");
            hours.add("hour");
            hours.add("hours");
            hours.add("hours");
            hourForms.put(l, hours);
        }
    }

    public static String format(final Enum<?> value) {
        return value == null ? "" : value.toString();
    }

//    public static String format(final BigDecimal price) {
//        if (price == null) {
//            return "";
//        }
//        String currCode =
//                DictHelper.getPreferenceValue(PreferenceKey.EQUIVE_CURRENCY, "");
//        CurrencyInfo currInfo = DictHelper.getCurrencyInfoByAnyCode(currCode);
//        String curr = null;
//        if (currInfo != null) {
//            curr = getDisplayName(currInfo);
//        }
//        if (curr == null) {
//            curr = currCode;
//        }
//        return new DecimalFormat("#0").format(price) + " " + curr;
//    }
//
//    public static String format(final Money price) {
//        if (price == null) {
//            return "";
//        }
//        CurrencyInfo currInfo =
//                DictHelper.getCurrencyInfoByAnyCode(price.getCurrency()
//                        .getCurrencyCode());
//        String curr = null;
//        if (currInfo != null) {
//            curr = getDisplayName(currInfo);
//        }
//        if (curr == null) {
//            curr = price.getCurrency().getCurrencyCode();
//        }
//        return new DecimalFormat("#0").format(price.getValue()) + curr;
//    }
//
//    public static String format(final Date date, final String format) {
//        return format(date, format,
//                Environment.getPublished(LocaleManager.class).getCurrentLocale());
//    }

    public static String format(final Date date, final String format,
                                final Locale locale) {
        DateFormatSymbols dfs = dfSymbols.get(locale);
        SimpleDateFormat df =
                dfs != null ? new SimpleDateFormat(format, dfs)
                        : new SimpleDateFormat(format, locale);
        return (date == null) ? "" : df.format(date);
    }

    public static String formatTime(final int value) {
        int h = (int) Math.floor(value / 60.0);
        Calendar cldr = Calendar.getInstance();
        cldr.set(Calendar.HOUR_OF_DAY, h);
        cldr.set(Calendar.MINUTE, value - (h * 60));
        cldr.set(Calendar.SECOND, 0);
        cldr.set(Calendar.MILLISECOND, 0);
        return new SimpleDateFormat("HH:mm").format(cldr.getTime());
    }

}
