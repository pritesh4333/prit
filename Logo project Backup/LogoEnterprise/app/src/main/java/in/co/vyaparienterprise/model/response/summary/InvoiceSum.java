package in.co.vyaparienterprise.model.response.summary;

import java.util.Date;

import in.co.vyaparienterprise.model.Currency;
import in.co.vyaparienterprise.model.Invoice;

/**
 * Created by Bekir.Dursun on 24.10.2017.
 */

public class InvoiceSum {

    private String Id;
    private Date InvoiceDate;
    private String InvoiceNumber;
    private Date PaymentDate;
    private String Description;
    private String FirmName;
    private double Total;
    private Currency Currency;
    private boolean IsEditable;
    private boolean IsActive;
    private int InvoiceType;
    private int InvoiceStatus;


    public InvoiceSum(Invoice invoice) {
        this.Id = invoice.getId();
        this.InvoiceDate = invoice.getInvoiceDate();
        this.InvoiceNumber = invoice.getInvoiceNumber();
        this.PaymentDate = invoice.getPaymentDate();
        this.Description = invoice.getDescription();
        this.FirmName = invoice.getFirm().getName();
        this.Total = invoice.getTotalAmount();
        this.Currency = invoice.getCurrency();
        this.IsEditable = true;
        this.IsActive = true;
        this.InvoiceType=invoice.getInvoiceType();
        this.InvoiceStatus=invoice.getInvoiceStatus();
    }

    public String getId() {
        return Id;
    }

    public Date getInvoiceDate() {
        return InvoiceDate;
    }

    public String getInvoiceNumber() {
        return InvoiceNumber;
    }

    public Date getPaymentDate() {
        return PaymentDate;
    }

    public String getDescription() {
        return Description;
    }

    public String getFirmName() {
        return FirmName;
    }

    public double getTotal() {
        return Total;
    }

    public Currency getCurrency() {
        return Currency;
    }

    public boolean isEditable() {
        return IsEditable;
    }

    public boolean isActive() {
        return IsActive;
    }
    public int getInvoiceType() {
        return InvoiceType;
    }

    public void setInvoiceType(int invoiceType) {
        InvoiceType = invoiceType;
    }

    public int getInvoiceStatus() {
        return InvoiceStatus;
    }

    public void setInvoiceStatus(int invoiceStatus) {
        InvoiceStatus = invoiceStatus;
    }
}
