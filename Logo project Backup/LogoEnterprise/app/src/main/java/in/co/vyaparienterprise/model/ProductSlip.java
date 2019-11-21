package in.co.vyaparienterprise.model;

import java.util.ArrayList;

public class ProductSlip {

    private String slipNo;
    private String slipDate;
    private String orgUnitCode;
    KeyValue Warehouse;
    private String total;
    ArrayList<Lines> lines = new ArrayList<Lines>();


    // Getter Methods
    public String getSlipNo() {
        return slipNo;
    }

    public String getSlipDate() {
        return slipDate;
    }

    public String getOrgUnitCode() {
        return orgUnitCode;
    }

    public KeyValue getWarehouse() {
        return Warehouse;
    }

    public String getTotal() {
        return total;
    }

    // Setter Methods

    public void setSlipNo(String slipNo) {
        this.slipNo = slipNo;
    }

    public void setSlipDate(String slipDate) {
        this.slipDate = slipDate;
    }

    public void setOrgUnitCode(String orgUnitCode) {
        this.orgUnitCode = orgUnitCode;
    }

    public void setWarehouse(KeyValue WarehouseObject) {
        this.Warehouse = WarehouseObject;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public ArrayList<Lines> getLines() {
        return lines;
    }

    public void setLines(ArrayList<Lines> lines) {
        this.lines = lines;
    }
}
