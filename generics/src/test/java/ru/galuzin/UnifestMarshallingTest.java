package ru.galuzin;

import com.gridnine.gds.unifest.ws.proxy.avia.BaseProduct;
import com.gridnine.gds.unifest.ws.proxy.avia.BookingFile;
import com.gridnine.gds.unifest.ws.proxy.avia.Product;
import com.gridnine.gds.unifest.ws.proxy.avia.ProductSegment;
import com.gridnine.gds.unifest.ws.proxy.avia.Reservation;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.File;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by galuzin on 08.11.2016.
 */
public class UnifestMarshallingTest {
    @Test
    public void test1() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(RootElement.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Object obj1 = unmarshaller.unmarshal(new File("c:\\Dev\\Doc\\7800_timelimit\\booking-create2.xml"));
            System.out.println("fsda");
            RootElement rootElement = (RootElement)obj1;
            Date timelimit = getTimeLimit(rootElement.getBookingFile().getReservations().getReservation().get(0));
            System.out.println(timelimit);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка загрузки файла конфигурации"/*, e*/);
        }
    }

    public static Date getTimeLimit(final Reservation res/*,
                                    final EntityReference<Organization> partnerProfile*/) {
        if (res == null) {
            return null;
        }
        Date startDate = getFirstTravelDate(res);
        //Set<DictionaryReference<Airline>> carriers = getCarriers(res);
        return getTimelimit(toDate(res.getDate()), startDate, toDate(res.getTimeLimit())//,
                /*carriers, partnerProfile*/);
    }

    public static Date getFirstTravelDate(final Reservation res) {
        Date result = null;
        for (Product product : res.getProducts().getAirTicket()) {
            Date productDate = getFirstTravelDate(product);
            if (compare(productDate, result, false) < 0) {
                result = productDate;
            }
        }
        return result;
    }

    public static Date getTimelimit(final Date resDate, final Date startDate,
                                    final Date resTimelimit//,
                                    /*final Set<DictionaryReference<Airline>> carriers,
                                    final EntityReference<Organization> partnerProfile*/) {
        if (resDate == null) {
            return null;
        }
        if (startDate == null) {
            return null;
        }
        Date timelimit = null;
        int daysBeforeFlight = MiscUtil.deltaDays(startDate, resDate);

        if (daysBeforeFlight == 0) {
            long hoursBeforeFlight =
                    TimeUnit.MILLISECONDS.toHours(startDate.getTime()
                            - resDate.getTime());
            if (hoursBeforeFlight < 4) {
                timelimit = setMinutesTimeLimit(resDate, 15);
            } else if (hoursBeforeFlight < 8) {
                timelimit = setMinutesTimeLimit(resDate, 30);
            } else {
                timelimit = setMinutesTimeLimit(resDate, 60);
            }
        } else if (daysBeforeFlight == 1) {
            if (isBeforeTime(resDate, 18, 30)) {
                timelimit = setMinutesTimeLimit(resDate, 120);
            } else {
                timelimit = setMinutesTimeLimit(resDate, 60);
            }
        } else if (daysBeforeFlight == 2) {
            if (isBeforeTime(resDate, 18, 0)) {
                timelimit = setEndWorkDayTimeLimit(resDate, 0);
            } else if (isBeforeTime(resDate, 23, 30)) {
                timelimit = setTimeTimeLimit(resDate, 23, 30);
            } else {
                timelimit = setTimeTimeLimit(resDate, 23, 59);
            }
        } else if (daysBeforeFlight == 3) {
            timelimit = setMinutesTimeLimit(resDate, 24 * 60);
        } else {
            int daysCount = 3;

//            if (partnerProfile != null) {
                try {
                    /*EntityContainer<Organization> partnerProfileCtr =
                            Environment.getPublished(EntityCache.class).resolve(
                                    partnerProfile);

                    Integer partnerTimeLimit =
                            partnerProfileCtr.getEntity().getPartnerDetails()
                                    .getTimeLimit();

                    if ((partnerTimeLimit == null)
                            && !ProfileHelper.resolveReference(partnerProfile)
                            .getCode().equalsIgnoreCase("default")) {
                        partnerProfileCtr =
                                Environment.getPublished(EntityCache.class)
                                        .resolve(
                                                new EntityReference<Organization>(
                                                        "default", Organization.class, null));
                        partnerTimeLimit =
                                partnerProfileCtr.getEntity().getPartnerDetails()
                                        .getTimeLimit();
                    }*/

                    //if (partnerTimeLimit != null) {
                        daysCount = 2;//partnerTimeLimit.intValue();
                    //}
                    if (daysCount < 1) {
                        System.out.println("TimeLimit less than 1 day. Will be used 1 day");
                        daysCount = 1;
                    } else if (daysCount > 3) {
                        System.out.println("TimeLimit greater than 3 days. Will be used 3 days");
                        daysCount = 3;
                    }

                } catch (Exception e) {
                    System.out.println("Error loading entity: ");
                }
            //}

            timelimit = setMinutesTimeLimit(resDate, daysCount * 24 * 60);
        }
        Date airlineTimeLimit = null;
        //for (DictionaryReference<Airline> carrier : carriers) {
            Date carrierTimeLimit =
                    getAirlineTimeLimit(resDate, daysBeforeFlight);

            if (compare(carrierTimeLimit, airlineTimeLimit, false) < 0) {
                airlineTimeLimit = carrierTimeLimit;
            }
        //}

        if (airlineTimeLimit != null) {
            if (airlineTimeLimit.before(timelimit)) {
                timelimit = airlineTimeLimit;
            }
        }

        if (timelimit != null) {
            if (resTimelimit == null) {
                return timelimit;
            } else if (resTimelimit.after(timelimit)) {
                return timelimit;
            } else {
                int timelimitDays = MiscUtil.deltaDays(timelimit, resTimelimit);
                int resDays = MiscUtil.deltaDays(resTimelimit, resDate);
                if (timelimitDays > 0) {
                    if (resDays > 0) {
                        timelimit = setEndWorkDayTimeLimit(resTimelimit, 0);
                        if (timelimit.before(resDate)) {
                            timelimit = setDayEndTime(resTimelimit);
                        }
                    }
                }
            }
            if (timelimit.after(startDate)) {
                timelimit = setMinutesTimeLimit(startDate, -60);
            }
            return timelimit;
        }
        return null;
    }

    public static Date setMinutesTimeLimit(final Date reservationDate,
                                           final int minutes) {
        Calendar cldr = Calendar.getInstance();
        cldr.setTime(reservationDate);
        cldr.add(Calendar.MINUTE, minutes);
        return cldr.getTime();
    }

    private static Date setEndWorkDayTimeLimit(final Date reservationDate,
                                               final int days) {
        Calendar cldr = Calendar.getInstance();
        cldr.setTime(reservationDate);
        MiscUtil.clearTime(cldr);
        cldr.add(Calendar.DATE, days);

        //boolean isOff = CalendarHelper.isOffDay(reservationDate);
        boolean isOff = false;
        if (isOff) {
            int dayOfWeek = cldr.get(Calendar.DAY_OF_WEEK);
            if ((dayOfWeek == Calendar.SUNDAY)
                    || (dayOfWeek == Calendar.SATURDAY)) {
                isOff = true;
            }
        }
        if (isOff) {
            cldr.set(Calendar.HOUR_OF_DAY, 19);
        } else {
            cldr.set(Calendar.HOUR_OF_DAY, 21);
        }

        return cldr.getTime();
    }

    public static Date setDayEndTime(final Date value) {
        if (value == null) {
            return value;
        }
        Calendar cldr = Calendar.getInstance();
        cldr.setTime(value);
        cldr.set(Calendar.HOUR_OF_DAY, 23);
        cldr.set(Calendar.MINUTE, 59);
        cldr.set(Calendar.SECOND, 59);
        cldr.set(Calendar.MILLISECOND, 999);
        return cldr.getTime();
    }

    private static Date getAirlineTimeLimit(final Date resDate,
                                            final int daysBeforeFlight) {
//        if (carrier == null) {
//            return null;
//        }
        String code = "AF";//carrier.getCode();

        if (carrierCodeIn(new String[] { "U9" }, code)) {
            return setEndWorkDayTimeLimit(resDate, 0);
        }

        if (carrierCodeIn(new String[] { "UT" }, code)) {
            return setMinutesTimeLimit(resDate, 24 * 60);
        }

        if ((daysBeforeFlight == 0)
                && carrierCodeIn(new String[] { "SU" }, code)) {
            return setMinutesTimeLimit(resDate, 15);
        }

        if (daysBeforeFlight < 2) {
            if (carrierCodeIn(new String[] { "U6", "NN" }, code)) {
                return setMinutesTimeLimit(resDate, 30);
            }
            return null;
        }
        if (daysBeforeFlight > 2) {
            if (carrierCodeIn(new String[] { "ZI", "UN" }, code)) {
                if (isBeforeTime(resDate, 18, 0)) {
                    return setEndWorkDayTimeLimit(resDate, 0);
                }
                return setTimeTimeLimit(setEndWorkDayTimeLimit(resDate, 1), 12,
                        0);
            }
            if (carrierCodeIn(new String[] { "U6", "FV", "S7", "IB", "NN",
                    "9U", "OS", "VY", "OM" }, code)) {
                if (isBeforeTime(resDate, 18, 0)) {
                    return setEndWorkDayTimeLimit(resDate, 0);
                }
                if (isBeforeTime(resDate, 23, 30)) {
                    return setTimeTimeLimit(resDate, 23, 30);
                }
                return setTimeTimeLimit(resDate, 23, 59);
            }
            if (carrierCodeIn(new String[] { "PR", "HY", "U8", "B2", "PS",
                    "TK", "OK", "PG", "SU", "KC", "LX", "V9", "BT", "SK" }, code)) {
                return setMinutesTimeLimit(resDate, 60 * 24);
            }
            if (carrierCodeIn(new String[] { "9W" }, code)) {
                return setMinutesTimeLimit(resDate, 60 * 3);
            }
            if (carrierCodeIn(new String[] { "A3" }, code)) {
                return setMinutesTimeLimit(resDate, 60 * 6);
            }
            if (carrierCodeIn(new String[] { "R3" }, code)) {
                return setMinutesTimeLimit(resDate, 60 * 2);
            }
            if (carrierCodeIn(new String[] { "LH", "GR", "MS", "LY" }, code)) {
                return setEndWorkDayTimeLimit(resDate, 1);
            }
            if (carrierCodeIn(new String[] { "AB", "KL", "VX" }, code)) {
                return setEndWorkDayTimeLimit(resDate, 2);
            }
            if (carrierCodeIn(new String[] { "AF" }, code)) {
                return setTimeTimeLimit(resDate, 23, 59);
            }
        }

        return null;
    }

    private static boolean carrierCodeIn(final String[] codes, final String code) {
        for(String code1 : codes){
            if(code1.equalsIgnoreCase(code)){
                return true;
            }
        }
        return false;/*CollectionUtil.contains(Arrays.asList(codes), code,
                new Equator<String, String>() {
                    @Override
                    public boolean equal(final String element, final String what) {
                        return element.equalsIgnoreCase(what);
                    }
                });*/
    }

    private static Date setTimeTimeLimit(final Date reservationDate,
                                         final int hours, final int minutes) {
        Calendar cldr = Calendar.getInstance();
        cldr.setTime(reservationDate);
        MiscUtil.clearTime(cldr);
        cldr.set(Calendar.HOUR_OF_DAY, hours);
        cldr.set(Calendar.MINUTE, minutes);
        return cldr.getTime();
    }

    private static boolean isBeforeTime(final Date date, final int hours,
                                        final int minutes) {
        Calendar cldr = Calendar.getInstance();
        cldr.setTime(date);
        MiscUtil.clearTime(cldr);
        cldr.set(Calendar.HOUR_OF_DAY, hours);
        cldr.set(Calendar.MINUTE, minutes);
        return date.before(cldr.getTime());
    }

    public static int compare(final Date date1, final Date date2,
                              final boolean nullIsLess) {
        if (date1 == null) {
            return date2 == null ? 0 : (nullIsLess ? -1 : 1);
        }
        if (date2 == null) {
            return nullIsLess ? 1 : -1;
        }

        long time1 = date1.getTime();
        long time2 = date2.getTime();
        return (time1 < time2) ? -1 : ((time1 == time2) ? 0 : 1);
    }

    public static Date getFirstTravelDate(final BaseProduct product) {
        return findFirstTravelDate((Product) product);
    }

    public static Date findFirstTravelDate(final Product product) {
        Date firstTravelDate = null;

        for (ProductSegment segment : product.getSegments().getSegment()) {
                if (compare(toDate(segment.getDateBegin()), firstTravelDate,
                        false) == -1) {
                    firstTravelDate = toDate(segment.getDateBegin());
                }
            }

        return firstTravelDate;
    }

    public static Date toDate(XMLGregorianCalendar calendar){
        if(calendar == null) {
            return null;
        }
        return calendar.toGregorianCalendar().getTime();
    }

}
