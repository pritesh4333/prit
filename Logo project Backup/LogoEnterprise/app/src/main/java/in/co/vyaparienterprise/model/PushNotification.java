package in.co.vyaparienterprise.model;

import org.json.JSONObject;

/**
 * Created by Bekir.Dursun on 5.10.2017.
 */

public class PushNotification {

    private int id;
    private String title;
    private String message;
    private JSONObject data;

    public PushNotification(int id, String title, String message, JSONObject data) {
        this.data = data;
        this.message = message;
        this.title = title;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
