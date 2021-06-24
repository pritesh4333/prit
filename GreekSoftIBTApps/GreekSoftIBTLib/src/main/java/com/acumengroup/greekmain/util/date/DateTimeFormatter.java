package com.acumengroup.greekmain.util.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeFormatter {

    public static final int TIME_TYPE_SHORT = 1;
    public static final int TIME_TYPE_MEDIUM = 2;
    private static SimpleDateFormat sf = new SimpleDateFormat("dd MMM yy");
    private static SimpleDateFormat sf2 = new SimpleDateFormat("dd MMM yyyy");
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    private static SimpleDateFormat sf3 = new SimpleDateFormat("dd/MM/yyyy");
    private static SimpleDateFormat sf1 = new SimpleDateFormat("dd MMM HH:mm:ss");
    private static SimpleDateFormat sf4 = new SimpleDateFormat("dd MMM HH:mm");
    private static SimpleDateFormat sf5 = new SimpleDateFormat("dd.MM.yyyy");
    private static SimpleDateFormat sf6 = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");

    public static String normalCurrentHourTime(int type) {

        if (type == TIME_TYPE_SHORT)
            return DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date());
        else if (type == TIME_TYPE_MEDIUM)
            return DateFormat.getTimeInstance(DateFormat.MEDIUM).format(new Date());

        return "";
    }

	/* Returns the current date.For Example=>2011/05/26 */

    public static String normalDate() {
        return dateFormat.format(new Date());
    }

	/*
     * Returns the previous month and date from current Month and date. For
	 * Example=>Give the interval is 4 the output is 2011/09/26
	 */

    public static String normalDate(final int interval) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, interval);
        Date date = calendar.getTime();

        return dateFormat.format(date);
    }

    public static Date addYearToDate(Date currentDate, int yearToAdd) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.YEAR, yearToAdd);
        return calendar.getTime();
    }

    public static String normalReverseOrder() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(date);
        return formattedDate;

    }

    public static String normalDateMonthNameYear() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("dd MMM yyyy");
        String formattedDate = df.format(date);
        return formattedDate;

    }

    public static String normalUSDateOrder() {

        Date date = new Date();
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String formattedDate = df.format(date);
        return formattedDate;

    }

    /*
     * Returns the date with time for the particular timestamp Give the date
     * object,returns the date and time For exmaple, Give new Date(),output is
     * 26 May 15:54:38
     */
    public static String toFullDateTime(Date timestamp) {
        return format(sf1, timestamp);
    }

    public static String toFullYearDateTime(Date timestamp) {
        return format(sf6, timestamp);
    }

    public static String toFullDateTimeYear(Date timestamp) {
        return format(sf4, timestamp);
    }

    public static String toFullDateTimeYearChart(Date timestamp) {
        // return format(sf6, timestamp);
        return (String) android.text.format.DateFormat.format("MMM dd,yyyy", timestamp);
    }

    public static String toFullDateYearOnly(Date timestamp) {
        return format(sf5, timestamp);
    }

	/*
	 * Returns the date with time for the particular timestamp Give the date
	 * object,returns the date and time For exmaple, Give new Date(),output is
	 * 26 May 15:54:38
	 */

    public static String toFullDateTime(long timestamp) {
        return toFullDateTime(new Date(timestamp));
    }

    public static String toFullYearDateTime(long timestamp) {
        return toFullYearDateTime(new Date(timestamp));
    }

    public static String toFullDateTimeYear(long timestamp) {
        return toFullDateTimeYear(new Date(timestamp));
    }

    public static String toFullDateYearOnly(long timestamp) {
        return toFullDateYearOnly(new Date(timestamp));
    }

    public static String toFullDateTime(String time) {
        return toFullDateTime(Long.parseLong(time) * 1000);
    }

    public static String toFullYearDateTime(String time) {
        return toFullYearDateTime(Long.parseLong(time) * 1000);
    }

    public static String toFullDateTimeYear(String time) {
        return toFullDateTimeYear(Long.parseLong(time));
    }

    public static String format(SimpleDateFormat sf, Date timestamp) {

        return sf.format(timestamp);

    }

    public static String toDD_MMM_YY(Date timestamp) {
        return format(sf, timestamp);
    }

    public static String toDD_MMM_YY(long timestamp) {
        return toDD_MMM_YY(new Date(timestamp));
    }

    public static String toDD_MM_YYYY(String time) {
        if (time != null)
            return DateTimeFormatter.format(sf2, new Date(Long.parseLong(time) * 1000));
        return "";
    }

    public static String toDD_MM_YYYY(String time, String customFormat) {
        SimpleDateFormat sf = new SimpleDateFormat(customFormat);
        if (time != null)
            return DateTimeFormatter.format(sf, new Date(Long.parseLong(time) * 1000));
        return "";
    }

    public static String getDateFromTimeStamp(String timeStamp, String customFormat) {//old
        try {
            return getDateFromTimeStamp(Long.parseLong(timeStamp), customFormat);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }



    public static String getGMTDateFromTimeStamp(String timeStamp, String customFormat) {
        try {
            return getGMTDateFromTimeStamp(Long.parseLong(timeStamp), customFormat);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getDateFromCalendar(Calendar calendar, String customFormat) {
        try {
            return getDateFromTimeStamp(calendar.getTimeInMillis(), customFormat);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    public static String getDateCurrentTimeZone( String timeOrder) {
        try {
            Calendar calendar = Calendar.getInstance();
            TimeZone timeZone = TimeZone.getTimeZone("Asia/Calcutta");
            calendar.setTimeInMillis((19800 * 1000) - 1800000);
            calendar.add(Calendar.MILLISECOND, timeZone.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date currentTimeZone = (Date) calendar.getTime();
            currentTimeZone.setSeconds(Integer.parseInt(timeOrder));
            return simpleDateFormat.format(currentTimeZone);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public static String getDateCurrentNewTimeZone( String timeOrder) {
        try {
            Calendar calendar = Calendar.getInstance();
            TimeZone timeZone = TimeZone.getTimeZone("Asia/Calcutta");
            calendar.setTimeInMillis((315513000));
            calendar.add(Calendar.MILLISECOND, timeZone.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currentTimeZone = (Date) calendar.getTime();
            currentTimeZone.setSeconds(Integer.parseInt(timeOrder));
            return simpleDateFormat.format(currentTimeZone);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDateFromTimeStamp(long timeStamp, String customFormat) {
        try {
            SimpleDateFormat sf = new SimpleDateFormat(customFormat);
            return DateTimeFormatter.format(sf, new Date(timeStamp));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * NSE: timestamp is from 1980
     * BSE timestamp is from 1970
     * timeStampStr is in SECONDS
     * @param timeStampStr
     * @param customFormat
     * @param exchange
     * @return
     */
    public static String getDateFromTimeStamp(String timeStampStr, String customFormat,String exchange) {
        try {
            long timeStamp = Long.parseLong(timeStampStr);
            Date newDate = null;



            if(exchange.equalsIgnoreCase("nse")){
                timeStamp = (timeStamp+315513000+19800);
                timeStamp=timeStamp*1000;//convert into milliseconds
               // newDate = addYearToDate(date,10);
                Date date = new Date(timeStamp);
                newDate = date;
            }
            else
            {
//              timeStamp = (timeStamp+315513000+19800);
                timeStamp=timeStamp*1000;//convert into milliseconds
                // newDate = addYearToDate(date,10);
                Date date = new Date(timeStamp);
                newDate = date;
            }
            SimpleDateFormat sf = new SimpleDateFormat(customFormat);
            sf.setTimeZone(TimeZone.getTimeZone("GMT"));
            return DateTimeFormatter.format(sf, newDate);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getDateFromTimeStampForNews(String timeStampStr, String customFormat,String exchange) {
        try {
            long timeStamp = Long.parseLong(timeStampStr);
//            long timeStamp = 1516720202;
            Date newDate = null;

            if(exchange.equalsIgnoreCase("aaa")){
                timeStamp = (timeStamp+315513000+19800);
                timeStamp=timeStamp*1000;//convert into milliseconds
                // newDate = addYearToDate(date,10);
                Date date = new Date(timeStamp);
                newDate = date;
            }
            else
            {
                timeStamp=timeStamp*1000;//convert into milliseconds
                // newDate = addYearToDate(date,10);
                Date date = new Date(timeStamp);
                newDate = date;
            }
            SimpleDateFormat sf = new SimpleDateFormat(customFormat);
           // sf.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
            return DateTimeFormatter.format(sf, newDate);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    //For Chat Msg Time (Time showing wrong)


    public static String getDateFromTimeStampForchatMSG(String timeStampStr, String customFormat,String exchange) {
        try {
            long timeStamp = Long.parseLong(timeStampStr);
//            long timeStamp = 1516720202;
            Date newDate = null;

            if(exchange.equalsIgnoreCase("aaa")){
                timeStamp = (timeStamp+315513000+19800);
                timeStamp=timeStamp*1000;//convert into milliseconds
                // newDate = addYearToDate(date,10);
                Date date = new Date(timeStamp);
                newDate = date;
            }
            else
            {
                timeStamp=timeStamp*1000;//convert into milliseconds
                // newDate = addYearToDate(date,10);
                Date date = new Date(timeStamp);
                newDate = date;
            }
            SimpleDateFormat sf = new SimpleDateFormat(customFormat);
            sf.setTimeZone(TimeZone.getTimeZone("GMT+05:30"));
            return DateTimeFormatter.format(sf, newDate);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }



    public static String getNewsDateTimeFromTimeStamp(String timeStampStr, String customFormat,String exchange) {
        try {
            long timeStamp = Long.parseLong(timeStampStr);
            Date newDate = null;
            timeStamp=timeStamp*1000;//convert into milliseconds
            // newDate = addYearToDate(date,10);
            Date date = new Date(timeStamp);
            newDate = date;
            SimpleDateFormat sf = new SimpleDateFormat(customFormat);
            return DateTimeFormatter.format(sf, newDate);
        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    public static String getGMTDateFromTimeStamp(long timeStamp, String customFormat) {
        try {
            SimpleDateFormat sf = new SimpleDateFormat(customFormat);
            sf.setTimeZone(TimeZone.getTimeZone("GMT"));

            return DateTimeFormatter.format(sf, new Date(timeStamp));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String toDDMMYYYY(String time) {
        if (time != null) return DateTimeFormatter.format(sf3, new Date(Long.parseLong(time)));
        return "";
    }

    public static String toDDMMYYYY(long time) {
        return DateTimeFormatter.format(sf3, new Date(time));
    }

    public static String customFormat(long time, String customFormat) {
        SimpleDateFormat sf = new SimpleDateFormat(customFormat);
        return DateTimeFormatter.format(sf, new Date(time));
    }


    public static int compareBetweenTwoDates(long fromTime, long toTime) {
        int diffInDays = (int) ((toTime - fromTime) / (1000 * 60 * 60 * 24));
        return diffInDays;
    }

    public static int compareBetweenTwoDates(String fromTime, String toTime) {
        try {
            int diffInDays = (int) ((Long.parseLong(toTime) - Long.parseLong(fromTime)) / (1000 * 60 * 60 * 24));
            return diffInDays;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /*
     * Compares 2 calendar dates and returns true if calendarDate1=
     * calendarDate2
     */
    public static boolean isDate1EqualsDate2(Calendar calendarDate1, Calendar calendarDate2) {

        boolean isEqual = false;
        int day1 = calendarDate1.get(Calendar.DAY_OF_MONTH);
        int month1 = calendarDate1.get(Calendar.MONTH);
        int year1 = calendarDate1.get(Calendar.YEAR);

        int day2 = calendarDate2.get(Calendar.DAY_OF_MONTH);
        int month2 = calendarDate2.get(Calendar.MONTH);
        int year2 = calendarDate2.get(Calendar.YEAR);

        isEqual = day1 == day2 && month1 == month2 && year1 == year2;

        return isEqual;
    }

    /*
     * Compares 2 calendar dates and returns true if calendarDate1 >
     * calendarDate2
     */
    public static boolean isDate1GreaterThanDate2(Calendar calendarDate1, Calendar calendarDate2) {

        boolean isGreater = false;
        int day1 = calendarDate1.get(Calendar.DAY_OF_MONTH);
        int month1 = calendarDate1.get(Calendar.MONTH);
        int year1 = calendarDate1.get(Calendar.YEAR);

        int day2 = calendarDate2.get(Calendar.DAY_OF_MONTH);
        int month2 = calendarDate2.get(Calendar.MONTH);
        int year2 = calendarDate2.get(Calendar.YEAR);

        if (year1 > year2) {
            isGreater = true;

        } else if (year1 < year2) {
            isGreater = false;

        } else {

            // year1 == year2
            if (month1 > month2) {
                isGreater = true;

            } else if (month1 < month2) {
                isGreater = false;

            } else {

                // month1 == month2
                isGreater = day1 > day2;
            }
        }

        return isGreater;
    }

    /*
     * Compares 2 calendar dates and returns true if calendarDate1 <
     * calendarDate2
     */
    public static boolean isDate1LesserThanDate2(Calendar calendarDate1, Calendar calendarDate2) {

        boolean isLesser = false;
        int day1 = calendarDate1.get(Calendar.DAY_OF_MONTH);
        int month1 = calendarDate1.get(Calendar.MONTH);
        int year1 = calendarDate1.get(Calendar.YEAR);

        int day2 = calendarDate2.get(Calendar.DAY_OF_MONTH);
        int month2 = calendarDate2.get(Calendar.MONTH);
        int year2 = calendarDate2.get(Calendar.YEAR);

        if (year1 < year2) {
            isLesser = true;

        } else if (year1 > year2) {
            isLesser = false;

        } else {

            // year1 == year2
            if (month1 < month2) {
                isLesser = true;

            } else if (month1 > month2) {
                isLesser = false;

            } else {

                // month1 == month2
                isLesser = day1 < day2;
            }
        }

        return isLesser;
    }

}
