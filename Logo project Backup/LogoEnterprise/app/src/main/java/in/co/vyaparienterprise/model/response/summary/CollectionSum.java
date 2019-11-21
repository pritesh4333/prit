package in.co.vyaparienterprise.model.response.summary;

import java.io.Serializable;

public class CollectionSum implements Serializable {


    private String slipDate;
    private String receiptNo;
    private String ARPName;

    private String ARPCode;

    private int slipType;

    private int status;

    private double total;



    private String headerReference;

    public String getHeaderReference() {
        return headerReference;
    }

    public void setHeaderReference(String headerReference) {
        this.headerReference = headerReference;
    }
    public String getSlipDate() {
        return slipDate;
    }

    public void setSlipDate(String slipDate) {
        this.slipDate = slipDate;
    }

    public String getARPName() {
        return ARPName;
    }

    public void setARPName(String aRPName) {
        this.ARPName = aRPName;
    }

    public String getARPCode() {
        return ARPCode;
    }

    public void setARPCode(String aRPCode) {
        this.ARPCode = aRPCode;
    }

    public int getSlipType() {
        return slipType;
    }

    public void setSlipType(int slipType) {
        this.slipType = slipType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }
}
