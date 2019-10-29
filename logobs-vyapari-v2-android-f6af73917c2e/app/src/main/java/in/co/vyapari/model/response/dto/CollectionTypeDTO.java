package in.co.vyapari.model.response.dto;

public class CollectionTypeDTO {


    private Integer logicalRefernce;

    private String code;

    private String description;

    private String LineType;

    private Integer status;

    public Integer getLogicalRefernce() {
        return logicalRefernce;
    }

    public void setLogicalRefernce(Integer logicalRefernce) {
        this.logicalRefernce = logicalRefernce;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLineType() {
        return LineType;
    }

    public void setLineType(String lineType) {
        this.LineType = lineType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
