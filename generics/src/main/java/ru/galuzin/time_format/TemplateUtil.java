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

//    public static String formatFullTime(final int value) {
//        try {
//            int hours = (int) Math.floor(value / 60.0);
//            int minutes = value - (hours * 60);
//            Locale locale =
//                    Environment.getPublished(LocaleManager.class)
//                            .getCurrentLocale();
//            if (locale == null) {
//                locale = new Locale("ru");
//            }
//            StringBuffer sb = new StringBuffer();
//            sb.append(hours).append(" ");
//            sb.append(hourForms.get(locale)
//                    .get(TextUtil.getFormByNumber(hours)));
//            sb.append(" ").append(minutes);
//            sb.append(" ").append(
//                    minForms.get(locale).get(TextUtil.getFormByNumber(minutes)));
//            return sb.toString();
//        } catch (Exception e) {
//            return formatTime(value);
//        }
//    }
//
//    public static String format(final DictionaryReference<?> ref) {
//        if (ref == null) {
//            return "";
//        }
//        BaseDictionary res =
//                Environment.getPublished(DictionaryCache.class).resolveReference(
//                        ref);
//        if (res != null) {
//            return format(res);
//        }
//        return TextUtil.isBlank(ref.getCaption()) ? ref.getCode() : ref
//                .getCaption();
//    }

//    public static String format(final BaseDictionary res) {
//        if (res == null) {
//            return "";
//        }
//        String displayName = getDisplayName(res);
//        if (displayName == null) {
//            return getDisplayCode(res);
//        }
//        return displayName + " (" + getDisplayCode(res) + ')';
//    }

//    public static <D extends BaseExtendDictionary> String formatLocationFrom(
//            final DictionaryReference<D> ref) {
//        return formatLocationDirection(ref, DeclensionDirection.FROM);
//    }
//
//    public static <D extends BaseExtendDictionary> String formatLocationTo(
//            final DictionaryReference<D> ref) {
//        return formatLocationDirection(ref, DeclensionDirection.TO);
//    }
//
//    public static <D extends BaseExtendDictionary> String formatLocationWhere(
//            final DictionaryReference<D> ref) {
//        return formatLocationDirection(ref, DeclensionDirection.WHERE);
//    }
//
//    public static <D extends BaseExtendDictionary> String formatLocationBetween(
//            final DictionaryReference<D> ref) {
//        return formatLocationDirection(ref, DeclensionDirection.BETWEEN);
//    }
//
//    private static <D extends BaseExtendDictionary> String formatLocationDirection(
//            final DictionaryReference<D> ref,
//            final DeclensionDirection direction) {
//        if (ref == null) {
//            return ""; //$NON-NLS-1$
//        }
//        D res =
//                Environment.getPublished(DictionaryCache.class).resolveReference(
//                        ref);
//        if (res == null) {
//            return "";
//        }
//        return formatLocationDirection(res, direction);
//    }
//
//    public static String formatLocationFrom(final BaseExtendDictionary res) {
//        return formatLocationDirection(res, DeclensionDirection.FROM);
//    }
//
//    public static String formatLocationTo(final BaseExtendDictionary res) {
//        return formatLocationDirection(res, DeclensionDirection.TO);
//    }
//
//    public static String formatLocationWhere(final BaseExtendDictionary res) {
//        return formatLocationDirection(res, DeclensionDirection.WHERE);
//    }
//
//    public static String formatLocationBetween(final BaseExtendDictionary res) {
//        return formatLocationDirection(res, DeclensionDirection.BETWEEN);
//    }
//
//    public static <D extends BaseDictionary> String formatResource(
//            final DictionaryReference<D> ref) {
//        if (ref == null) {
//            return ""; //$NON-NLS-1$
//        }
//        D res =
//                Environment.getPublished(DictionaryCache.class).resolveReference(
//                        ref);
//        if (res == null) {
//            return !TextUtil.isBlank(ref.getCaption()) ? ref.getCaption() : ref
//                    .getCode();
//        }
//        return formatResource(res);
//    }
//
//    public static String formatResource(final BaseDictionary res) {
//        if (res == null) {
//            return ""; //$NON-NLS-1$
//        }
//        Locale locale =
//                Environment.getPublished(LocaleManager.class).getCurrentLocale();
//        String result = MiscUtil.findByLocale(res.getTranslations(), locale);
//        if (TextUtil.isBlank(result)
//                && !MiscUtil.equals(locale, Locale.ENGLISH)) {
//            result =
//                    MiscUtil.findByLocale(res.getTranslations(), Locale.ENGLISH);
//        }
//        return TextUtil.isBlank(result) ? res.getCode() : result;
//    }
//
//    private static String formatLocationDirection(
//            final BaseExtendDictionary dict, final DeclensionDirection direction) {
//        Locale locale =
//                Environment.getPublished(LocaleManager.class).getCurrentLocale();
//        if (dict == null) {
//            return "";
//        }
//        if (!(dict instanceof GeoRegion) && !(dict instanceof Country)
//                && !(dict instanceof GeoLocation)) {
//            return "";
//        }
//        Locale ruLocale = new Locale("RU");
//        if ((locale.getLanguage() == null)
//                || ((locale.getLanguage() != null) && !locale.getLanguage().equals(
//                ruLocale.getLanguage()))) {
//            return formatResource(dict);
//        }
//        String result = dict.getDeclensions().get(direction);
//        if (TextUtil.isBlank(result)) {
//            result = formatResource(dict);
//            switch (direction) {
//                case FROM:
//                    result = TextUtil.toCase(result, "р", true);
//                    break;
//                case TO:
//                    result = TextUtil.toCase(result, "в", true);
//                    break;
//                case WHERE:
//                    result = TextUtil.toCase(result, "п", false);
//                    break;
//                case BETWEEN:
//                    result = TextUtil.toCase(result, "т", false);
//                    break;
//                default:
//                    break;
//            }
//        }
//        return result;
//    }
//
//    public static String formatCity(final DictionaryReference<GeoLocation> ref,
//                                    final boolean withCode) {
//        Locale locale =
//                Environment.getPublished(LocaleManager.class).getCurrentLocale();
//        if (ref == null) {
//            return "";
//        }
//        LocationComponents comps = LocationComponents.create(ref);
//        if (comps == null) {
//            return ref.getCode();
//        }
//        StringBuilder result = new StringBuilder();
//        if (comps.getCity() != null) {
//            result.append(getDisplayName(comps.getCity()));
//        } else {
//            result.append(getDisplayName(comps.getResource()));
//        }
//        if (withCode) {
//            result.append(" (").append(getDisplayCode(comps.getResource()))
//                    .append(')');
//        }
//        return result.toString();
//    }
//
//    public static String formatAirport(
//            final DictionaryReference<GeoLocation> ref) {
//        if (ref == null) {
//            return "";
//        }
//        LocationComponents comps = LocationComponents.create(ref);
//        if (comps == null) {
//            return ref.getCode();
//        }
//        StringBuilder result = new StringBuilder();
//        if (comps.getCity() != null) {
//            result.append(getDisplayName(comps.getCity()));
//        } else {
//            result.append(getDisplayName(comps.getResource()));
//        }
//        if (comps.getCountry() != null) {
//            result.append(", ").append(getDisplayName(comps.getCountry()));
//        }
//        if (comps.getAirport() != null) {
//            result.append(" - ");
//            result.append(getDisplayName(comps.getAirport()));
//        }
//        result.append(" (").append(getDisplayCode(comps.getResource()))
//                .append(')');
//        return result.toString();
//    }
//
//    public static <D extends BaseDictionary> String getDisplayName(
//            final DictionaryReference<D> ref) {
//        return getDisplayName(ref, null);
//    }
//
//    public static <D extends BaseDictionary> String getDisplayName(
//            final DictionaryReference<D> ref, final Locale locale) {
//        if (ref == null) {
//            return "";
//        }
//        BaseDictionary res =
//                Environment.getPublished(DictionaryCache.class).resolveReference(
//                        ref);
//        if (res == null) {
//            return TextUtil.isBlank(ref.getCaption()) ? ref.getCode() : ref
//                    .getCaption();
//        }
//        return getDisplayName(res, locale);
//    }
//
//    public static <D extends BaseDictionary> String getDisplayCode(
//            final DictionaryReference<D> ref) {
//        if (ref == null) {
//            return "";
//        }
//        BaseDictionary res =
//                Environment.getPublished(DictionaryCache.class).resolveReference(
//                        ref);
//        if (res == null) {
//            return ref.getCode();
//        }
//        return getDisplayCode(res);
//    }
//
//    private static <D extends BaseDictionary> String getDisplayCode(final D res) {
//        String result = res.getCodeVariants().get(CodeSystem.IATA.name());
//        if (result != null) {
//            return result;
//        }
//        result = res.getCodeVariants().get(CodeSystem.ISO.name());
//        if (result != null) {
//            return result;
//        }
//        result = res.getCodeVariants().get(CodeSystem.CRT.name());
//        if (result != null) {
//            return result;
//        }
//        return res.getCode();
//    }
//
//    private static <D extends BaseDictionary> String getDisplayName(final D res) {
//        return getDisplayName(res, null);
//    }
//
//    private static <D extends BaseDictionary> String getDisplayName(
//            final D res, final Locale locale) {
//        if (res == null) {
//            return null;
//        }
//        Locale loc = locale;
//        if (loc == null) {
//            loc =
//                    Environment.getPublished(LocaleManager.class)
//                            .getCurrentLocale();
//        }
//        String result = MiscUtil.findByLocale(res.getTranslations(), loc);
//        if (TextUtil.isBlank(result) && !MiscUtil.equals(loc, Locale.ENGLISH)) {
//            result =
//                    MiscUtil.findByLocale(res.getTranslations(), Locale.ENGLISH);
//        }
//        return TextUtil.isBlank(result) ? null : result;
//    }
//
//    public static boolean isSameCity(
//            final DictionaryReference<GeoLocation> ref1,
//            final DictionaryReference<GeoLocation> ref2) {
//        if ((ref1 == null) || (ref2 == null)) {
//            return false;
//        }
//        if (ref1.equals(ref2)) {
//            return true;
//        }
//        DictionaryCache dictionaryCache =
//                Environment.getPublished(DictionaryCache.class);
//        GeoLocation loc1 = dictionaryCache.resolveReference(ref1);
//        GeoLocation loc2 = dictionaryCache.resolveReference(ref2);
//        if ((loc1 == null) || (loc2 == null)) {
//            return false;
//        }
//        return getCity(loc1).equals(getCity(loc2));
//    }
//
//    private static GeoLocation getCity(final GeoLocation loc) {
//        GeoLocation result = loc;
//        Set<GeoLocation> seen = new HashSet<GeoLocation>();
//        while (result != null) {
//            if (seen.contains(result)) {
//                LogFactory.getLog(TemplateUtil.class).warn(
//                        "cyclic parents detected for GEO location " + loc);
//                return result;
//            }
//            seen.add(result);
//            if (result.getType() == LocationType.CITY) {
//                return result;
//            }
//            result =
//                    Environment.getPublished(DictionaryCache.class)
//                            .resolveReference(result.getParent());
//        }
//        return loc;
//    }
//
//    private TemplateUtil() {
//        // no-op
//    }
//
//    private final static class LocationComponents {
//        static LocationComponents create(
//                final DictionaryReference<GeoLocation> ref) {
//            GeoLocation loc =
//                    Environment.getPublished(DictionaryCache.class)
//                            .resolveReference(ref);
//            if (loc == null) {
//                return null;
//            }
//            return new LocationComponents(loc);
//        }
//
//        private final GeoLocation resource;
//
//        private Country country;
//
//        private GeoLocation city;
//
//        private GeoLocation airport;
//
//        LocationComponents(final GeoLocation loc) {
//            resource = loc;
//            DictionaryCache dictionaryCache =
//                    Environment.getPublished(DictionaryCache.class);
//            country = dictionaryCache.resolveReference(resource.getCountry());
//            if (resource.getType() == LocationType.CITY) {
//                city = resource;
//            } else if (resource.getType() == LocationType.AIRPORT) {
//                airport = resource;
//            }
//            GeoLocation parent = resource;
//            Set<GeoLocation> seen = new HashSet<GeoLocation>();
//            while (parent != null) {
//                parent = dictionaryCache.resolveReference(parent.getParent());
//                if (parent == null) {
//                    break;
//                }
//                if (seen.contains(parent)) {
//                    LogFactory.getLog(TemplateUtil.class).warn(
//                            "cyclic parents detected for GEO location " + resource);
//                    break;
//                }
//                seen.add(parent);
//                if (country == null) {
//                    country =
//                            dictionaryCache.resolveReference(parent.getCountry());
//                }
//                if (parent.getType() == LocationType.CITY) {
//                    city = parent;
//                } else if (parent.getType() == LocationType.AIRPORT) {
//                    airport = parent;
//                }
//            }
//        }
//
//        GeoLocation getResource() {
//            return resource;
//        }
//
//        Country getCountry() {
//            return country;
//        }
//
//        GeoLocation getCity() {
//            return city;
//        }
//
//        GeoLocation getAirport() {
//            return airport;
//        }
//    }
}
