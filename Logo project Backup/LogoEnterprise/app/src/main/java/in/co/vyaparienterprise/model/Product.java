package in.co.vyaparienterprise.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;

import in.co.vyaparienterprise.model.response.dto.ProductDTO;

public class Product implements Parcelable {

    private String Id;
    private String Code;
    private String Barcode;
    private String Name;
    private KeyValue ProductType;
    private KeyValue SACHSNCode;
    private KeyValue GSTCode;
    private KeyValue CESSCode;
    private KeyValue Unit;
    private KeyValue UnitSet;
    private double SalesPrice;
    private String ActiveUnit;
    private  String ActiveUnitDesc;
    private int CardType;
    private Currency SalesCurrency;
    private String mainUnitReference;
    private double PurchasePrice;
    private Currency PurchaseCurrency;
    private double PhysicalInventory;
    private double ActualInventory;
    private String Description;
    private boolean GSTIncluded;
    private boolean IsActive;

    public Product() {
    }

    public Product(String id) {
        this.Id = id;
    }

    public Product(ProductDTO productDTO) {
        Id = productDTO.getId();
        Code = productDTO.getCode();
        Barcode = productDTO.getBarcode();
        Name = productDTO.getName();
        ProductType = productDTO.getProductType();
        SACHSNCode = productDTO.getSACHSNCode();
        GSTCode = productDTO.getGSTCode();
        CESSCode = productDTO.getCESSCode();
        CardType=productDTO.getCardType();
        Unit = productDTO.getUnit();
        ActiveUnit=productDTO.getActiveUnit();
        UnitSet = productDTO.getUnitSet();
        mainUnitReference=productDTO.getMainUnitReference();
        SalesPrice = productDTO.getSalesPrice();
        SalesCurrency = productDTO.getSalesCurrency();
        PurchasePrice = productDTO.getPurchasePrice();
        PurchaseCurrency = productDTO.getPurchaseCurrency();
        PhysicalInventory = productDTO.getPhysicalInventory();
        ActualInventory = productDTO.getActualInventory();
        Description = productDTO.getDescription();
        GSTIncluded = productDTO.isGSTIncluded();
        IsActive = productDTO.isActive();
    }

    protected Product(Parcel in) {
        Id = in.readString();
        Code = in.readString();
        Barcode = in.readString();
        Name = in.readString();
        ProductType = in.readParcelable(KeyValue.class.getClassLoader());
        SACHSNCode = in.readParcelable(KeyValue.class.getClassLoader());
        GSTCode = in.readParcelable(KeyValue.class.getClassLoader());
        CESSCode = in.readParcelable(KeyValue.class.getClassLoader());
        Unit = in.readParcelable(KeyValue.class.getClassLoader());
        mainUnitReference= in.readString();
        UnitSet = in.readParcelable(KeyValue.class.getClassLoader());
        SalesPrice = in.readDouble();
        SalesCurrency = in.readParcelable(Currency.class.getClassLoader());
        PurchasePrice = in.readDouble();
        PurchaseCurrency = in.readParcelable(Currency.class.getClassLoader());
        PhysicalInventory = in.readDouble();
        ActualInventory = in.readDouble();
        Description = in.readString();
        GSTIncluded = in.readByte() != 0;
        IsActive = in.readByte() != 0;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public KeyValue getProductType() {
        return ProductType;
    }

    public void setProductType(KeyValue productType) {
        ProductType = productType;
    }

    public KeyValue getSACHSNCode() {
        return SACHSNCode;
    }

    public void setSACHSNCode(KeyValue SACHSNCode) {
        this.SACHSNCode = SACHSNCode;
    }

    public KeyValue getGSTCode() {
        return GSTCode;
    }

    public void setGSTCode(KeyValue GSTCode) {
        this.GSTCode = GSTCode;
    }

    public KeyValue getCESSCode() {
        return CESSCode;
    }

    public void setCESSCode(KeyValue CESSCode) {
        this.CESSCode = CESSCode;
    }

    public KeyValue getUnit() {
        return Unit;
    }

    public void setUnit(KeyValue unit) {
        Unit = unit;
    }

    public KeyValue getUnitSet() {
        return UnitSet;
    }

    public void setUnitSet(KeyValue unit) {
        UnitSet = unit;
    }

    public double getSalesPrice() {
        return SalesPrice;
    }

    public void setSalesPrice(double salePrice) {
        SalesPrice = salePrice;
    }

    public Currency getSalesCurrency() {
        return SalesCurrency;
    }

    public void setSaleCurrency(Currency saleCurrency) {
        SalesCurrency = saleCurrency;
    }

    public double getPurchasePrice() {
        return PurchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        PurchasePrice = purchasePrice;
    }

    public Currency getPurchaseCurrency() {
        return PurchaseCurrency;
    }

    public void setPurchaseCurrency(Currency purchaseCurrency) {
        PurchaseCurrency = purchaseCurrency;
    }
    public String getMainUnitReference() {
        return mainUnitReference;
    }

    public void setMainUnitReference(String mainUnitReference) {
        this.mainUnitReference = mainUnitReference;
    }
    public double getPhysicalInventory() {
        return PhysicalInventory;
    }

    public void setPhysicalInventory(double physicalInventory) {
        PhysicalInventory = physicalInventory;
    }
    public int getCardType() {
        return CardType;
    }

    public void setCardType(int cardType) {
        CardType = cardType;
    }
    public double getActualInventory() {
        return ActualInventory;
    }

    public void setActualInventory(double actualInventory) {
        ActualInventory = actualInventory;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public boolean isGSTIncluded() {
        return GSTIncluded;
    }

    public void setGSTIncluded(boolean GSTIncluded) {
        this.GSTIncluded = GSTIncluded;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean active) {
        IsActive = active;
    }

    public String getActiveUnit() {
        return ActiveUnit;
    }

    public void setActiveUnit(String activeUnit) {
        ActiveUnit = activeUnit;
    }
    public String getActiveUnitDesc() {
        return ActiveUnitDesc;
    }

    public void setActiveUnitDesc(String activeUnitDesc) {
        ActiveUnitDesc = activeUnitDesc;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Id);
        dest.writeString(Code);
        dest.writeString(Barcode);
        dest.writeString(Name);
        dest.writeParcelable(ProductType, flags);
        dest.writeParcelable(SACHSNCode, flags);
        dest.writeParcelable(GSTCode, flags);
        dest.writeParcelable(CESSCode, flags);
        dest.writeParcelable(Unit, flags);
        dest.writeString(mainUnitReference);
        dest.writeParcelable(UnitSet, flags);
        dest.writeDouble(SalesPrice);
        dest.writeParcelable(SalesCurrency, flags);
        dest.writeDouble(PurchasePrice);
        dest.writeParcelable(PurchaseCurrency, flags);
        dest.writeDouble(PhysicalInventory);
        dest.writeDouble(ActualInventory);
        dest.writeString(Description);
        dest.writeByte((byte) (GSTIncluded ? 1 : 0));
        dest.writeByte((byte) (IsActive ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
