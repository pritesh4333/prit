package in.co.vyapari.util;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import in.co.vyapari.model.CalendarModel;

public class DateUtil {
    private static Locale turkishLocale;
    private static Locale usLocale;

    public static final SimpleDateFormat dayNameFormat = new SimpleDateFormat("EEEE", getIndiaLocale());
    public static final SimpleDateFormat dotSeparatedFormat = new SimpleDateFormat("dd.MM.yyyy", getIndiaLocale());
    public static final SimpleDateFormat simpleFormat = new SimpleDateFormat("dd/MM/yyyy", getIndiaLocale());
    public static final SimpleDateFormat humanReadableFormat = new SimpleDateFormat("d MMMM yyyy", getIndiaLocale());
    public static final SimpleDateFormat humanReadableWithNameFormat = new SimpleDateFormat("d MMMM yyyy EEEE", getIndiaLocale());
    public static final SimpleDateFormat monthDayNameFormat = new SimpleDateFormat("dd MMMM", getIndiaLocale());
    public static final SimpleDateFormat monthYearFormat = new SimpleDateFormat("MMMM yyyy", getIndiaLocale());
    public static final SimpleDateFormat pathParameterDateFormat = new SimpleDateFormat("yyyy-MM-dd", getIndiaLocale());
    public static final SimpleDateFormat shortHumanReadableFormat = new SimpleDateFormat("d MMM", getIndiaLocale());
    public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy", getIndiaLocale());

    public static Locale getTurkishLocale() {
        if (turkishLocale == null) {
            turkishLocale = new Locale("tr", "TR");
        }
        return turkishLocale;
    }

    public static Locale getUsLocale() {
        if (usLocale == null) {
            usLocale = new Locale("en", "US");
        }
        return usLocale;
    }

    public static Locale getIndiaLocale() {
        if (usLocale == null) {
            usLocale = new Locale("en", "IN");
        }
        return usLocale;
    }

    public static CalendarModel dateToCM(Date date) {
        Calendar c = Calendar.getInstance();

        if (date != null) {
            c.setTimeInMillis(date.getTime());
        } else {
            c.setTimeInMillis(0);
        }

        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);
        String monthName = getMonthForInt(mMonth);

        return new CalendarModel(mMinute, mHour, mDay, mMonth, monthName, mYear);
    }

    public static long dateToTimestamp(String date) {
        try {
            Date tempDate = DateUtil.dotSeparatedFormat.parse(date);
            return tempDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Calendar dateToCalendar(String s) {
        String[] parts = s.split("/");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.valueOf(parts[2]), Integer.valueOf(parts[1]) - 1, Integer.valueOf(parts[0]));
        return calendar;
    }

    public static String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11) {
            month = months[num].toUpperCase();//.substring(0, 3);
        }
        return month;
    }

    public static int daysBetween(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }

        Calendar sDate = getDatePart(startDate);
        Calendar eDate = getDatePart(endDate);

        int daysBetween = 0;
        while (sDate.before(eDate)) {
            sDate.add(Calendar.DAY_OF_MONTH, 1);
            daysBetween++;
        }
        return daysBetween;
    }

    private static Calendar getDatePart(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }
}
