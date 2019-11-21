package in.co.vyapari.model.response;

/**
 * Created by bekir on 15/08/16.
 */
public class BaseModelV<T> {

    private int code;
    private String message;
    private boolean isError;
    private T data;

    public BaseModelV() {
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

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
