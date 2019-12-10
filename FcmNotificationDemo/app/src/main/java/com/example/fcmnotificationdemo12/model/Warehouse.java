package com.example.fcmnotificationdemo12.model;

public class Warehouse {
    private float Code;
    private String Message;
    private boolean IsError;
    Data Data;


    // Getter Methods

    public float getCode() {
        return Code;
    }

    public String getMessage() {
        return Message;
    }

    public boolean getIsError() {
        return IsError;
    }

    public Data getData() {
        return Data;
    }

    // Setter Methods

    public void setCode(float Code) {
        this.Code = Code;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public void setIsError(boolean IsError) {
        this.IsError = IsError;
    }

    public void setData(Data DataObject) {
        this.Data = DataObject;
    }

}
