package warehousedelivery.taaxgenie.in.resagitpvtltd;

public class GetSet {

    private String name;
    private String number;
    private String type;
    private String date;
    private String dureation;

    public GetSet(String name, String number, String type, String date, String duration) {
        this.name = name;
        this.number = number;
        this.type = type;
        this.date = date;
        this.dureation = duration;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDureation() {
        return dureation;
    }

    public void setDureation(String dureation) {
        this.dureation = dureation;
    }
}
