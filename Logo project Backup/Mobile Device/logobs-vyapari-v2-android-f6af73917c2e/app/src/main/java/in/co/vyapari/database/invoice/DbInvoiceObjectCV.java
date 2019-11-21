package in.co.vyapari.database.invoice;

import in.co.vyapari.database.DbObjectCV;

/**
 * Created by bekirdursun on 6.02.2018.
 */

public class DbInvoiceObjectCV<T> extends DbObjectCV<T> {

    private String invoiceType;
    private String invoiceDate;

    public DbInvoiceObjectCV(DbInvoiceObject<T> dbInvoiceObject) {
        super(dbInvoiceObject);
        this.invoiceType = String.valueOf(dbInvoiceObject.getInvoiceType());
        this.invoiceDate = dbInvoiceObject.getInvoiceDate() != null ? dbInvoiceObject.getInvoiceDate().toString() : null;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }
}
