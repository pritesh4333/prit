package in.co.vyaparienterprise.model;

public class UpdateLogo {



    private String id;

    private String name;

    private String title;

    private String commercialTitle;

    private Integer logicalState;

    private String code;

    private String firmNo;

    private String taxNo;

    private User users = null;

    private String province;

    private String sector;

    private Object icon;

    private Object iconOther;

    private String creationChannel;

    private String extension;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCommercialTitle() {
        return commercialTitle;
    }

    public void setCommercialTitle(String commercialTitle) {
        this.commercialTitle = commercialTitle;
    }

    public Integer getLogicalState() {
        return logicalState;
    }

    public void setLogicalState(Integer logicalState) {
        this.logicalState = logicalState;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFirmNo() {
        return firmNo;
    }

    public void setFirmNo(String firmNo) {
        this.firmNo = firmNo;
    }

    public String getTaxNo() {
        return taxNo;
    }

    public void setTaxNo(String taxNo) {
        this.taxNo = taxNo;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public Object getIcon() {
        return icon;
    }

    public void setIcon(Object icon) {
        this.icon = icon;
    }

    public Object getIconOther() {
        return iconOther;
    }

    public void setIconOther(Object iconOther) {
        this.iconOther = iconOther;
    }

    public String getCreationChannel() {
        return creationChannel;
    }

    public void setCreationChannel(String creationChannel) {
        this.creationChannel = creationChannel;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }


}
