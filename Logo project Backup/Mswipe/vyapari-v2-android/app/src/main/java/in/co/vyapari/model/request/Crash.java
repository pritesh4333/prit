package in.co.vyapari.model.request;

/**
 * Created by bekirdursun on 29.03.2018.
 */

public class Crash {

    private String Page;
    private String Log;

    public Crash(String page, String log) {
        this.Page = page;
        this.Log = log;
    }

    public void setPage(String page) {
        Page = page;
    }

    public void setLog(String log) {
        Log = log;
    }
}
