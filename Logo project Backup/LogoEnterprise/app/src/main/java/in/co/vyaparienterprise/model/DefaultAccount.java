package in.co.vyaparienterprise.model;

public class DefaultAccount {
    private String cashcode;
    private String cardcode;
    private String bankid;
    private String cashid;
    private String cardid;
    private String bankcode;
    private String othercode;
    private String username="";
    public String getBankid() {
        return bankid;
    }

    public void setBankid(String bankid) {
        this.bankid = bankid;
    }

    public String getCashid() {
        return cashid;
    }

    public void setCashid(String cashid) {
        this.cashid = cashid;
    }

    public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getOthercode() {
        return othercode;
    }

    public void setOthercode(String othercode) {
        this.othercode = othercode;
    }



    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    public String getCashcode() {
        return cashcode;
    }

    public void setCashcode(String cashcode) {
        this.cashcode = cashcode;
    }

    public String getCardcode() {
        return cardcode;
    }

    public void setCardcode(String cardcode) {
        this.cardcode = cardcode;
    }




}
