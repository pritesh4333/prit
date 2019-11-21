package in.co.vyapari.model;

import java.util.List;

public class ArApCollection {


    private String slipNo;



    private String receiptNo;

    private String slipDate;

    private String description;

    private List<Transaction> transactions = null;

    public String getSlipNo() {
        return slipNo;
    }

    public void setSlipNo(String slipNo) {
        this.slipNo = slipNo;
    }

    public String getSlipDate() {
        return slipDate;
    }

    public void setSlipDate(String slipDate) {
        this.slipDate = slipDate;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }
}
