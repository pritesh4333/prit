package in.co.vyapari.model.response;

/**
 * Created by bekir on 15/08/16.
 */
public class BaseModel<T> {

    private int Code;
    private String Message;
    private boolean IsError;
    private T Data;

    public BaseModel() {
    }

    public BaseModel(T data) {
        this.Data = data;
    }

    public int getErrorCode() {
        return Code;
    }

    public String getErrorDescription() {
        return Message;
    }

    public String getMessage() {
        return Message;
    }

    public boolean isError() {
        return IsError;
    }

    public void setData(T data) {
        Data = data;
    }

    public T getData() {
        return Data;
    }

}
