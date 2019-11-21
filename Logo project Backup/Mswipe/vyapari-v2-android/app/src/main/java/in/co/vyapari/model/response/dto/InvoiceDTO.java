package in.co.vyapari.model.response.dto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import in.co.vyapari.model.Address;
import in.co.vyapari.model.Currency;
import in.co.vyapari.model.Invoice;
import in.co.vyapari.model.InvoiceLine;
import in.co.vyapari.model.KeyValue;

public class InvoiceDTO {

    private String Id;
    private int InvoiceType;
    private Date InvoiceDate = Calendar.getInstance().getTime();
    private String InvoiceNumber;
    private Date PaymentDate;
    private Date DeliveryDate;
    private Date DocumentDate = Calendar.getInstance().getTime();
    private String InvoicePaymentType;
    private String Description;
    private String Note;
    private FirmDTO Firm;
    private boolean DeliveryAddressDifferent;
    private Address DeliveryAddress;
    private KeyValue Warehouse;
    private String DocumentNumber;
    private boolean GSTIncluded;
    private Currency Currency;
    private double ExchangeRate;
    private ArrayList<InvoiceLine> InvoiceLines = new ArrayList<>();
    private double GeneralDiscountRate;
    private double GeneralDiscountAmount;
    private double GeneralDiscountAmountAsCurrency;
    private double BaseAmount;
    private double AmountWithoutGST;
    private double AmountWithoutGSTAsCurrency;
    private int DiscountType;
    private double DiscountAmount;
    private double DiscountAmountAsCurrency;
    private double CESSAmount;
    private double CESSAmountAsCurrency;
    private double CGSTAmount;
    private double CGSTAmountAsCurrency;
    private double SGSTAmount;
    private double SGSTAmountAsCurrency;
    private double IGSTAmount;
    private double IGSTAmountAsCurrency;
    private double TotalGSTAmount;
    private double TotalGSTAmountAsCurrency;
    private double TotalAmount;
    private double TotalAmountAsCurrency;
    private String TotalAmountAsText;



    private int InvoiceStatus;


    public InvoiceDTO(Invoice invoice) {
        if (invoice != null) {
            Id = invoice.getId();
            InvoiceType = invoice.getInvoiceType();
            InvoiceDate = invoice.getInvoiceDate();
            InvoiceNumber = invoice.getInvoiceNumber();
            PaymentDate = invoice.getPaymentDate();
            DeliveryDate = invoice.getDeliveryDate();
            DocumentDate = invoice.getDocumentDate();
            InvoicePaymentType = invoice.getInvoicePaymentType();
            Description = invoice.getDescription();
            Note = invoice.getNote();
            Firm = new FirmDTO(invoice.getFirm());
            DeliveryAddressDifferent = invoice.isDeliveryAddressDifferent();
            DeliveryAddress = invoice.getDeliveryAddress();
            Warehouse = invoice.getWarehouse();
            DocumentNumber = invoice.getDocumentNumber();
            GSTIncluded = invoice.isGSTIncluded();
            Currency = invoice.getCurrency();
            ExchangeRate = invoice.getExchangeRate();
            InvoiceLines = invoice.getInvoiceLines();
            GeneralDiscountRate = invoice.getGeneralDiscountRate();
            GeneralDiscountAmount = invoice.getGeneralDiscountAmount();
            GeneralDiscountAmountAsCurrency = invoice.getGeneralDiscountAmountAsCurrency();
            BaseAmount = invoice.getBaseAmount();
            AmountWithoutGST = invoice.getAmountWithoutGST();
            AmountWithoutGSTAsCurrency = invoice.getAmountWithoutGSTAsCurrency();
            DiscountType = invoice.getDiscountType();
            DiscountAmount = invoice.getDiscountAmount();
            DiscountAmountAsCurrency = invoice.getDiscountAmountAsCurrency();
            CESSAmount = invoice.getCESSAmount();
            CESSAmountAsCurrency = invoice.getCESSAmountAsCurrency();
            CGSTAmount = invoice.getCGSTAmount();
            CGSTAmountAsCurrency = invoice.getCGSTAmountAsCurrency();
            SGSTAmount = invoice.getSGSTAmount();
            SGSTAmountAsCurrency = invoice.getSGSTAmountAsCurrency();
            IGSTAmount = invoice.getIGSTAmount();
            IGSTAmountAsCurrency = invoice.getIGSTAmountAsCurrency();
            TotalGSTAmount = invoice.getTotalGSTAmount();
            TotalGSTAmountAsCurrency = invoice.getTotalGSTAmountAsCurrency();
            TotalAmount = invoice.getTotalAmount();
            TotalAmountAsCurrency = invoice.getTotalAmountAsCurrency();
            TotalAmountAsText = invoice.getTotalAmountAsText();
        }
    }


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public int getInvoiceType() {
        return InvoiceType;
    }

    public void setInvoiceType(int invoiceType) {
        InvoiceType = invoiceType;
    }

    public Date getInvoiceDate() {
        return InvoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        InvoiceDate = invoiceDate;
    }

    public String getInvoiceNumber() {
        return InvoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        InvoiceNumber = invoiceNumber;
    }

    public Date getPaymentDate() {
        return PaymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        PaymentDate = paymentDate;
    }

    public Date getDeliveryDate() {
        return DeliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        DeliveryDate = deliveryDate;
    }

    public Date getDocumentDate() {
        return DocumentDate;
    }

    public void setDocumentDate(Date documentDate) {
        DocumentDate = documentDate;
    }

    public String getInvoicePaymentType() {
        return InvoicePaymentType;
    }

    public void setInvoicePaymentType(String invoicePaymentType) {
        InvoicePaymentType = invoicePaymentType;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public FirmDTO getFirm() {
        return Firm;
    }

    public void setFirm(FirmDTO firm) {
        Firm = firm;
    }

    public boolean isDeliveryAddressDifferent() {
        return DeliveryAddressDifferent;
    }

    public void setDeliveryAddressDifferent(boolean deliveryAddressDifferent) {
        DeliveryAddressDifferent = deliveryAddressDifferent;
    }

    public Address getDeliveryAddress() {
        return DeliveryAddress;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        DeliveryAddress = deliveryAddress;
    }

    public KeyValue getWarehouse() {
        return Warehouse;
    }

    public void setWarehouse(KeyValue warehouse) {
        Warehouse = warehouse;
    }

    public String getDocumentNumber() {
        return DocumentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        DocumentNumber = documentNumber;
    }

    public boolean isGSTIncluded() {
        return GSTIncluded;
    }

    public void setGSTIncluded(boolean GSTIncluded) {
        this.GSTIncluded = GSTIncluded;
    }

    public Currency getCurrency() {
        return Currency;
    }

    public void setCurrency(Currency currency) {
        Currency = currency;
    }

    public double getExchangeRate() {
        return ExchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        ExchangeRate = exchangeRate;
    }

    public ArrayList<InvoiceLine> getInvoiceLines() {
        return InvoiceLines;
    }

    public void setInvoiceLines(ArrayList<InvoiceLine> invoiceLines) {
        InvoiceLines = invoiceLines;
    }

    public double getGeneralDiscountRate() {
        return GeneralDiscountRate;
    }

    public void setGeneralDiscountRate(double generalDiscountRate) {
        GeneralDiscountRate = generalDiscountRate;
    }

    public double getGeneralDiscountAmount() {
        return GeneralDiscountAmount;
    }

    public void setGeneralDiscountAmount(double generalDiscountAmount) {
        GeneralDiscountAmount = generalDiscountAmount;
    }

    public double getGeneralDiscountAmountAsCurrency() {
        return GeneralDiscountAmountAsCurrency;
    }

    public void setGeneralDiscountAmountAsCurrency(double generalDiscountAmountAsCurrency) {
        GeneralDiscountAmountAsCurrency = generalDiscountAmountAsCurrency;
    }

    public double getBaseAmount() {
        return BaseAmount;
    }

    public void setBaseAmount(double baseAmount) {
        BaseAmount = baseAmount;
    }

    public double getAmountWithoutGST() {
        return AmountWithoutGST;
    }

    public void setAmountWithoutGST(double amountWithoutGST) {
        AmountWithoutGST = amountWithoutGST;
    }

    public double getAmountWithoutGSTAsCurrency() {
        return AmountWithoutGSTAsCurrency;
    }

    public void setAmountWithoutGSTAsCurrency(double amountWithoutGSTAsCurrency) {
        AmountWithoutGSTAsCurrency = amountWithoutGSTAsCurrency;
    }

    public int getDiscountType() {
        return DiscountType;
    }

    public void setDiscountType(int discountType) {
        DiscountType = discountType;
    }

    public double getDiscountAmount() {
        return DiscountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        DiscountAmount = discountAmount;
    }

    public double getDiscountAmountAsCurrency() {
        return DiscountAmountAsCurrency;
    }

    public void setDiscountAmountAsCurrency(double discountAmountAsCurrency) {
        DiscountAmountAsCurrency = discountAmountAsCurrency;
    }

    public double getCESSAmount() {
        return CESSAmount;
    }

    public void setCESSAmount(double CESSAmount) {
        this.CESSAmount = CESSAmount;
    }

    public double getCESSAmountAsCurrency() {
        return CESSAmountAsCurrency;
    }

    public void setCESSAmountAsCurrency(double CESSAmountAsCurrency) {
        this.CESSAmountAsCurrency = CESSAmountAsCurrency;
    }

    public double getCGSTAmount() {
        return CGSTAmount;
    }

    public void setCGSTAmount(double CGSTAmount) {
        this.CGSTAmount = CGSTAmount;
    }

    public double getCGSTAmountAsCurrency() {
        return CGSTAmountAsCurrency;
    }

    public void setCGSTAmountAsCurrency(double CGSTAmountAsCurrency) {
        this.CGSTAmountAsCurrency = CGSTAmountAsCurrency;
    }

    public double getSGSTAmount() {
        return SGSTAmount;
    }

    public void setSGSTAmount(double SGSTAmount) {
        this.SGSTAmount = SGSTAmount;
    }

    public double getSGSTAmountAsCurrency() {
        return SGSTAmountAsCurrency;
    }

    public void setSGSTAmountAsCurrency(double SGSTAmountAsCurrency) {
        this.SGSTAmountAsCurrency = SGSTAmountAsCurrency;
    }

    public double getIGSTAmount() {
        return IGSTAmount;
    }

    public void setIGSTAmount(double IGSTAmount) {
        this.IGSTAmount = IGSTAmount;
    }

    public double getIGSTAmountAsCurrency() {
        return IGSTAmountAsCurrency;
    }

    public void setIGSTAmountAsCurrency(double IGSTAmountAsCurrency) {
        this.IGSTAmountAsCurrency = IGSTAmountAsCurrency;
    }

    public double getTotalGSTAmount() {
        return TotalGSTAmount;
    }

    public void setTotalGSTAmount(double totalGSTAmount) {
        TotalGSTAmount = totalGSTAmount;
    }

    public double getTotalGSTAmountAsCurrency() {
        return TotalGSTAmountAsCurrency;
    }

    public void setTotalGSTAmountAsCurrency(double totalGSTAmountAsCurrency) {
        TotalGSTAmountAsCurrency = totalGSTAmountAsCurrency;
    }

    public double getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        TotalAmount = totalAmount;
    }

    public double getTotalAmountAsCurrency() {
        return TotalAmountAsCurrency;
    }

    public void setTotalAmountAsCurrency(double totalAmountAsCurrency) {
        TotalAmountAsCurrency = totalAmountAsCurrency;
    }

    public String getTotalAmountAsText() {
        return TotalAmountAsText;
    }

    public void setTotalAmountAsText(String totalAmountAsText) {
        TotalAmountAsText = totalAmountAsText;
    }
    public int getInvoiceStatus() {
        return InvoiceStatus;
    }

    public void setInvoiceStatus(int invoiceStatus) {
        InvoiceStatus = invoiceStatus;
    }
}