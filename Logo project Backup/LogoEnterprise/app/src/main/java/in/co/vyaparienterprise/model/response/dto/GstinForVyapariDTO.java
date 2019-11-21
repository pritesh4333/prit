package in.co.vyaparienterprise.model.response.dto;

public class GstinForVyapariDTO {


    private String name;

    private String taxNumber;

    private String panNumber;

    private String tradeName;

    private String nature;

    private pradrobjDTO pradrobj;

    private String gstin;

    private String status;

    private String type;


    private String constitution;


    public void GstinForVyapariDTO(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getGstin() {
        return gstin;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public pradrobjDTO getPradrobjDTO() {
        return pradrobj;
    }

    public void setPradrobjDTO(pradrobjDTO pradrobjDTO) {
        this.pradrobj = pradrobjDTO;
    }
    public String getConstitution() {
        return constitution;
    }

    public void setConstitution(String constitution) {
        this.constitution = constitution;
    }

}

