package in.co.vyaparienterprise.model;

/**
 * Created by Bekir.Dursun on 25.10.2017.
 */

public class CalendarModel {

    private String Minute;
    private String Hour;
    private String Day;
    private String Month;
    private String MonthName;
    private String Year;

    public CalendarModel(int minute, int hour, int day, int month, String monthName, int year) {
        this.Minute = minute < 10 ? ("0" + minute) : String.valueOf(minute);
        this.Hour = hour < 10 ? ("0" + hour) : String.valueOf(hour);
        this.Day = day < 10 ? ("0" + day) : String.valueOf(day);
        this.Month = month < 10 ? ("0" + month) : String.valueOf(month);
        this.MonthName = monthName;
        this.Year = String.valueOf(year);
    }

    public String getMinute() {
        return Minute;
    }

    public void setMinute(String minute) {
        Minute = minute;
    }

    public String getHour() {
        return Hour;
    }

    public void setHour(String hour) {
        Hour = hour;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        this.Day = day;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        this.Month = month;
    }

    public String getMonthName() {
        return MonthName;
    }

    public void setMonthName(String monthName) {
        this.MonthName = monthName;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        this.Year = year;
    }
}
