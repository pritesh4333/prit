package in.co.vyaparienterprise.model;

public class POS {



    private String Code;

    private String Description;

    private String TIN;

    private String ParentId;

    private String LogicalReference;

    private String StateCode;

    private String Id;

    private Integer ExternalId;

    private Boolean IsActive;

    private String CreatedOn;

    private String CreatedBy;

    private String LastUpdatedOn;

    private String LastUpdatedBy;

    private String DeletedOn;

    public POS(String description) {
        this.Description =description;

    }


    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getTIN() {
        return TIN;
    }

    public void setTIN(String TIN) {
        this.TIN = TIN;
    }

    public String getParentId() {
        return ParentId;
    }

    public void setParentId(String parentId) {
        ParentId = parentId;
    }

    public String getLogicalReference() {
        return LogicalReference;
    }

    public void setLogicalReference(String logicalReference) {
        LogicalReference = logicalReference;
    }

    public String getStateCode() {
        return StateCode;
    }

    public void setStateCode(String stateCode) {
        StateCode = stateCode;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public Integer getExternalId() {
        return ExternalId;
    }

    public void setExternalId(Integer externalId) {
        ExternalId = externalId;
    }

    public Boolean getActive() {
        return IsActive;
    }

    public void setActive(Boolean active) {
        IsActive = active;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getLastUpdatedOn() {
        return LastUpdatedOn;
    }

    public void setLastUpdatedOn(String lastUpdatedOn) {
        LastUpdatedOn = lastUpdatedOn;
    }

    public String getLastUpdatedBy() {
        return LastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        LastUpdatedBy = lastUpdatedBy;
    }

    public String getDeletedOn() {
        return DeletedOn;
    }

    public void setDeletedOn(String deletedOn) {
        DeletedOn = deletedOn;
    }
}
