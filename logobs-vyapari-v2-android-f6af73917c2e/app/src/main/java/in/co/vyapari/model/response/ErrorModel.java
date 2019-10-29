package in.co.vyapari.model.response;

/**
 * Created by bekir on 02/08/16.
 */
public class ErrorModel<T> {

    private int code;
    private String message;
    private T data;
    private String Error;

    public ErrorModel() {
    }

    public ErrorModel(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorModel(String error) {
        this.message = error;
        this.Error = error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        Error = error;
    }
}
