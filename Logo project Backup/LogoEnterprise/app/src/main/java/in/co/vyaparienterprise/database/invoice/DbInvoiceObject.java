package in.co.vyaparienterprise.database.invoice;

import java.util.Date;

import in.co.vyaparienterprise.database.DbObject;

/**
 * Created by bekirdursun on 5.02.2018.
 */

public class DbInvoiceObject<T> extends DbObject<T> {

    private int invoiceType;
    private Date invoiceDate;

    public DbInvoiceObject(T data) {
        super(data);
    }

    public int getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(int invoiceType) {
        this.invoiceType = invoiceType;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }
}