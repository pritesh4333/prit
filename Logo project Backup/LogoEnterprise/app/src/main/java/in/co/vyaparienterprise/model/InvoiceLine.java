package in.co.vyaparienterprise.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class InvoiceLine implements Parcelable {
    public static final Creator<InvoiceLine> CREATOR = new Creator<InvoiceLine>() {
        @Override
        public InvoiceLine createFromParcel(Parcel in) {
            return new InvoiceLine(in);
        }

        @Override
        public InvoiceLine[] newArray(int size) {
            return new InvoiceLine[size];
        }
    };
    private String Id;
    private int InvoiceLineNumber;
    private Product Product;
    private double UnitPrice;
    //private SACHSNCode SACHSNCode;
    private Currency Currency;
    private KeyValue Unit;
    private String Description2;
    private boolean GSTIncluded;
    private double UnitPriceAsCurrency;
    private double Quantity;
    private double ExchangeRate;
    private double CGSTRate;
    private double CGSTAmount;
    private double CGSTAmountAsCurrency;
    private double SGSTRate;
    private double SGSTAmount;
    private double SGSTAmountAsCurrency;
    private double IGSTRate;
    private double IGSTAmount;
    private double IGSTAmountAsCurrency;
    private double CESSRate;
    private double CESSAmount;
    private double CESSAmountAsCurrency;

    private int DiscountType;
    private double DiscountRate;
    private double DiscountAmount;
    private double DiscountAmountAsCurrency;
    private double GeneralDiscountAmount;
    private double Total;
    private double TotalAsCurrency;
    private String DeliveryDate;

    public InvoiceLine() {
    }

    protected InvoiceLine(Parcel in) {
        Id = in.readString();
        InvoiceLineNumber = in.readInt();
        Product = in.readParcelable(Product.class.getClassLoader());
        UnitPrice = in.readDouble();
        //SACHSNCode=in.readParcelable(SACHSNCode.class.getClassLoader());
        Currency = in.readParcelable(Currency.class.getClassLoader());
        Unit = in.readParcelable(KeyValue.class.getClassLoader());
        Description2 = in.readString();
        GSTIncluded = in.readByte() != 0;
        UnitPriceAsCurrency = in.readDouble();
        Quantity = in.readDouble();
        ExchangeRate = in.readDouble();
        CGSTRate = in.readDouble();
        CGSTAmount = in.readDouble();

        CGSTAmountAsCurrency = in.readDouble();
        SGSTRate = in.readDouble();
        SGSTAmount = in.readDouble();
        SGSTAmountAsCurrency = in.readDouble();
        IGSTRate = in.readDouble();
        IGSTAmount = in.readDouble();
        IGSTAmountAsCurrency = in.readDouble();
        CESSRate = in.readDouble();
        CESSAmount = in.readDouble();
        CESSAmountAsCurrency = in.readDouble();
        DiscountType = in.readInt();
        DiscountRate = in.readDouble();
        DiscountAmount = in.readDouble();
        DiscountAmountAsCurrency = in.readDouble();
        GeneralDiscountAmount = in.readDouble();
        Total = in.readDouble();
        TotalAsCurrency = in.readDouble();
         DeliveryDate=in.readString();
    }



    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public int getInvoiceLineNumber() {
        return InvoiceLineNumber;
    }

    public void setInvoiceLineNumber(int invoiceLineNumber) {
        InvoiceLineNumber = invoiceLineNumber;
    }

    public Product getProduct() {
        return Product;
    }

    public void setProduct(Product product) {
        Product = product;
    }

    public double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        UnitPrice = unitPrice;
    }
//    public SACHSNCode getSACHSNCode() {
//        return SACHSNCode;
//    }
//
//    public void setSACHSNCode(SACHSNCode SACHSNCode) {
//        this.SACHSNCode = SACHSNCode;
//    }
    public Currency getCurrency() {
        return Currency;
    }

    public void setCurrency(Currency currency) {
        Currency = currency;
    }

    public KeyValue getUnit() {
        return Unit;
    }

    public void setUnit(KeyValue unit) {
        Unit = unit;
    }

    public String getDescription2() {
        return Description2;
    }

    public void setDescription2(String description2) {
        this.Description2 = description2;
    }

    public boolean isGSTIncluded() {
        return GSTIncluded;
    }

    public void setGSTIncluded(boolean GSTIncluded) {
        this.GSTIncluded = GSTIncluded;
    }

    public double getUnitPriceAsCurrency() {
        return UnitPriceAsCurrency;
    }

    public void setUnitPriceAsCurrency(double unitPriceAsCurrency) {
        UnitPriceAsCurrency = unitPriceAsCurrency;
    }

    public double getQuantity() {
        return Quantity;
    }

    public void setQuantity(double quantity) {
        Quantity = quantity;
    }

    public double getExchangeRate() {
        return ExchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        ExchangeRate = exchangeRate;
    }

    public double getCGSTRate() {
        return CGSTRate;
    }

    public void setCGSTRate(double CGSTRate) {
        this.CGSTRate = CGSTRate;
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

    public double getSGSTRate() {
        return SGSTRate;
    }

    public void setSGSTRate(double SGSTRate) {
        this.SGSTRate = SGSTRate;
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

    public double getIGSTRate() {
        return IGSTRate;
    }

    public void setIGSTRate(double IGSTRate) {
        this.IGSTRate = IGSTRate;
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

    public double getCESSRate() {
        return CESSRate;
    }

    public void setCESSRate(double CESSRate) {
        this.CESSRate = CESSRate;
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

    public int getDiscountType() {
        return DiscountType;
    }

    public void setDiscountType(int discountType) {
        this.DiscountType = discountType;
    }

    public double getDiscountRate() {
        return DiscountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.DiscountRate = discountRate;
    }

    public double getDiscountAmount() {
        return DiscountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.DiscountAmount = discountAmount;
    }

    public double getDiscountAmountAsCurrency() {
        return DiscountAmountAsCurrency;
    }

    public void setDiscountAmountAsCurrency(double discountAmountAsCurrency) {
        this.DiscountAmountAsCurrency = discountAmountAsCurrency;
    }

    public double getGeneralDiscountAmount() {
        return GeneralDiscountAmount;
    }

    public void setGeneralDiscountAmount(double generalDiscountAmount) {
        this.GeneralDiscountAmount = generalDiscountAmount;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }

    public double getTotalAsCurrency() {
        return TotalAsCurrency;
    }

    public void setTotalAsCurrency(double totalAsCurrency) {
        TotalAsCurrency = totalAsCurrency;
    }
        public String getDeliveryDate() {
        return DeliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.DeliveryDate = deliveryDate;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Id);
        dest.writeInt(InvoiceLineNumber);
        dest.writeParcelable(Product, flags);
        dest.writeDouble(UnitPrice);
        //dest.writeParcelable(SACHSNCode, flags);
        dest.writeParcelable(Currency, flags);
        dest.writeParcelable(Unit, flags);
        dest.writeString(Description2);
        dest.writeByte((byte) (GSTIncluded ? 1 : 0));
        dest.writeDouble(UnitPriceAsCurrency);
        dest.writeDouble(Quantity);
        dest.writeDouble(ExchangeRate);
        dest.writeDouble(CGSTRate);
        dest.writeDouble(CGSTAmount);
        dest.writeDouble(CGSTAmountAsCurrency);
        dest.writeDouble(SGSTRate);

        dest.writeDouble(SGSTAmount);
        dest.writeDouble(SGSTAmountAsCurrency);
        dest.writeDouble(IGSTRate);
        dest.writeDouble(IGSTAmount);
        dest.writeDouble(IGSTAmountAsCurrency);
        dest.writeDouble(CESSRate);
        dest.writeDouble(CESSAmount);
        dest.writeDouble(CESSAmountAsCurrency);
        dest.writeInt(DiscountType);
        dest.writeDouble(DiscountRate);
        dest.writeDouble(DiscountAmount);
        dest.writeDouble(DiscountAmountAsCurrency);
        dest.writeDouble(GeneralDiscountAmount);
        dest.writeDouble(Total);
        dest.writeDouble(TotalAsCurrency);
        dest.writeString(DeliveryDate);
    }
}