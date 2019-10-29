package in.co.vyapari.model.response.dto;

public class pradrobjDTO {


    private String st;

    private String stcd;

    private String flno;

    private String loc;

    private String pncd;

    private String ntr;

    private String lg;

    private String bnm;

    private String bno;

    private String lt;


    public void pradrobj(String st,String stcd,String flno,String loc,String pncd,String ntr,String lg,String bnm,String bno,String lt){
        this.st=st;
        this.stcd=stcd;
        this.flno=flno;
        this.loc=loc;
        this.pncd=pncd;
        this.ntr=ntr;
        this.lg=lg;
        this.bnm=bnm;
        this.bno=bno;
        this.lt=lt;

    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getStcd() {
        return stcd;
    }

    public void setStcd(String stcd) {
        this.stcd = stcd;
    }

    public String getFlno() {
        return flno;
    }

    public void setFlno(String flno) {
        this.flno = flno;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getPncd() {
        return pncd;
    }

    public void setPncd(String pncd) {
        this.pncd = pncd;
    }

    public String getNtr() {
        return ntr;
    }

    public void setNtr(String ntr) {
        this.ntr = ntr;
    }

    public String getLg() {
        return lg;
    }

    public void setLg(String lg) {
        this.lg = lg;
    }

    public String getBnm() {
        return bnm;
    }

    public void setBnm(String bnm) {
        this.bnm = bnm;
    }

    public String getBno() {
        return bno;
    }

    public void setBno(String bno) {
        this.bno = bno;
    }

    public String getLt() {
        return lt;
    }

    public void setLt(String lt) {
        this.lt = lt;
    }
}
