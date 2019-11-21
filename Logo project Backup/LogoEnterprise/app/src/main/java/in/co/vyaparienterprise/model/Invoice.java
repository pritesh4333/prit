package in.co.vyaparienterprise.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import in.co.vyaparienterprise.model.response.dto.InvoiceDTO;

public class Invoice {

    private String Id;
    private String PointOfSale;
    private int InvoiceType;
    private int OrderType;
    private String orderNumber;
    private int DispatchType;
    private Date InvoiceDate = Calendar.getInstance().getTime();
    private String InvoiceNumber;
    private Date PaymentDate;
    private Date DeliveryDate;
    private Date DocumentDate = Calendar.getInstance().getTime();
    private String InvoicePaymentType;
    private String Description;
    private String Note;
    private Firm Firm;
    private boolean DeliveryAddressDifferent;
    private Address DeliveryAddress;
    private KeyValue Warehouse;
    private KeyValue POS;
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

    public Invoice() {
    }

    public Invoice(int invoiceType) {
        this.InvoiceType = invoiceType;
        this.OrderType=invoiceType;
        this.DispatchType=invoiceType;
    }

    public Invoice(String id) {
        this.Id = id;
    }

    public Invoice(String id, int invoiceType) {
        this.Id = id;
        this.InvoiceType = invoiceType;

    }

    public Invoice(InvoiceDTO invoiceDTO, Firm selectedFirm) {
        Id = invoiceDTO.getId();
        PointOfSale=invoiceDTO.getPointOfSale();
        InvoiceType = invoiceDTO.getInvoiceType();
        OrderType=invoiceDTO.getOrderType();
        orderNumber=invoiceDTO.getOrderNumber();
        DispatchType=invoiceDTO.getDispatchType();
        InvoiceDate = invoiceDTO.getInvoiceDate();
        InvoiceNumber = invoiceDTO.getInvoiceNumber();
        PaymentDate = invoiceDTO.getPaymentDate();
        DeliveryDate = invoiceDTO.getDeliveryDate();
        DocumentDate = invoiceDTO.getDocumentDate();
        InvoicePaymentType = invoiceDTO.getInvoicePaymentType();
        Description = invoiceDTO.getDescription();
        Note = invoiceDTO.getNote();
        Firm = selectedFirm == null ? new Firm(invoiceDTO.getFirm()) : selectedFirm;
        DeliveryAddressDifferent = invoiceDTO.isDeliveryAddressDifferent();
        DeliveryAddress = invoiceDTO.getDeliveryAddress();
        Warehouse = invoiceDTO.getWarehouse();
        POS=invoiceDTO.getPOS();
        DocumentNumber = invoiceDTO.getDocumentNumber();
        GSTIncluded = invoiceDTO.isGSTIncluded();
        Currency = invoiceDTO.getCurrency();
        ExchangeRate = invoiceDTO.getExchangeRate();
        InvoiceLines = invoiceDTO.getInvoiceLines();
        GeneralDiscountRate = invoiceDTO.getGeneralDiscountRate();
        GeneralDiscountAmount = invoiceDTO.getGeneralDiscountAmount();
        GeneralDiscountAmountAsCurrency = invoiceDTO.getGeneralDiscountAmountAsCurrency();
        BaseAmount = invoiceDTO.getBaseAmount();
        AmountWithoutGST = invoiceDTO.getAmountWithoutGST();
        AmountWithoutGSTAsCurrency = invoiceDTO.getAmountWithoutGSTAsCurrency();
        DiscountType = invoiceDTO.getDiscountType();
        DiscountAmount = invoiceDTO.getDiscountAmount();
        DiscountAmountAsCurrency = invoiceDTO.getDiscountAmountAsCurrency();
        CESSAmount = invoiceDTO.getCESSAmount();
        CESSAmountAsCurrency = invoiceDTO.getCESSAmountAsCurrency();
        CGSTAmount = invoiceDTO.getCGSTAmount();
        CGSTAmountAsCurrency = invoiceDTO.getCGSTAmountAsCurrency();
        SGSTAmount = invoiceDTO.getSGSTAmount();
        SGSTAmountAsCurrency = invoiceDTO.getSGSTAmountAsCurrency();
        IGSTAmount = invoiceDTO.getIGSTAmount();
        IGSTAmountAsCurrency = invoiceDTO.getIGSTAmountAsCurrency();
        TotalGSTAmount = invoiceDTO.getTotalGSTAmount();
        TotalGSTAmountAsCurrency = invoiceDTO.getTotalGSTAmountAsCurrency();
        TotalAmount = invoiceDTO.getTotalAmount();
        TotalAmountAsCurrency = invoiceDTO.getTotalAmountAsCurrency();
        TotalAmountAsText = invoiceDTO.getTotalAmountAsText();
        InvoiceStatus=invoiceDTO.getInvoiceStatus();
    }

    public String getId() {
        return Id;
    }
    public String getPointOfSale() {
        return PointOfSale;
    }

    public void setPointOfSale(String pointOfSale) {
        PointOfSale = pointOfSale;
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
    public int getOrderType() {
        return OrderType;
    }

    public void setOrderType(int orderType) {
        OrderType = orderType;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }


    public int getDispatchType() {
        return DispatchType;
    }

    public void setDispatchType(int dispatchType) {
        DispatchType = dispatchType;
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

    public Firm getFirm() {
        return Firm;
    }
    public KeyValue getPOS() {
        return POS;
    }

    public void setPOS(KeyValue POS) {
        this.POS = POS;
    }
    public void setFirm(Firm firm) {
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