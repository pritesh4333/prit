package in.co.vyaparienterprise.model.response.dto;

import java.util.Calendar;
import java.util.Date;

import in.co.vyaparienterprise.model.Currency;
import in.co.vyaparienterprise.model.KeyValue;
import in.co.vyaparienterprise.model.Product;

public class ProductDTO {

    private String Id;
    private String Code;
    private String Barcode;
    private String Name;
    private KeyValue ProductType;
    private KeyValue SACHSNCode;
    private KeyValue GSTCode;
    private KeyValue CESSCode;
    private String ActiveUnit;
    private String ActiveUnitDesc;
    private  int CardType;
    private KeyValue Unit;
    private KeyValue UnitSet;
    private double SalesPrice;
    private Currency SalesCurrency;
    private String mainUnitReference;
    private double PurchasePrice;
    private Currency PurchaseCurrency;
    private double PhysicalInventory;
    private double ActualInventory;
    private String Description;
    private boolean GSTIncluded;
    private boolean IsActive;

    public ProductDTO(Product product) {
        Id = product.getId();
        Code = product.getCode();
        Barcode = product.getBarcode();
        Name = product.getName();
        ProductType = product.getProductType();
        SACHSNCode = product.getSACHSNCode();
        GSTCode = product.getGSTCode();
        CESSCode = product.getCESSCode();
        CardType=product.getCardType();
        Unit = product.getUnit();
        mainUnitReference=product.getMainUnitReference();
        UnitSet = product.getUnitSet();
        SalesPrice = product.getSalesPrice();
        SalesCurrency = product.getSalesCurrency();
        PurchasePrice = product.getPurchasePrice();
        PurchaseCurrency = product.getPurchaseCurrency();
        PhysicalInventory = product.getPhysicalInventory();
        ActualInventory = product.getActualInventory();
        Description = product.getDescription();
        GSTIncluded = product.isGSTIncluded();
        IsActive = product.isActive();
    }


    public String getId() {
        return Id;
    }

    public String getCode() {
        return Code;
    }

    public String getBarcode() {
        return Barcode;
    }

    public String getName() {
        return Name;
    }

    public KeyValue getProductType() {
        return ProductType;
    }

    public KeyValue getSACHSNCode() {
        return SACHSNCode;
    }

    public KeyValue getGSTCode() {
        return GSTCode;
    }

    public KeyValue getCESSCode() {
        return CESSCode;
    }

    public KeyValue getUnit() {
        return Unit;
    }

    public KeyValue getUnitSet() {
        return UnitSet;
    }

    public double getSalesPrice() {
        return SalesPrice;
    }

    public Currency getSalesCurrency() {
        return SalesCurrency;
    }

    public double getPurchasePrice() {
        return PurchasePrice;
    }

    public Currency getPurchaseCurrency() {
        return PurchaseCurrency;
    }

    public double getPhysicalInventory() {
        return PhysicalInventory;
    }

    public double getActualInventory() {
        return ActualInventory;
    }

    public String getDescription() {
        return Description;
    }



    public boolean isGSTIncluded() {
        return GSTIncluded;
    }

    public boolean isActive() {
        return IsActive;
    }
    public int getCardType() {
        return CardType;
    }

    public void setCardType(int cardType) {
        CardType = cardType;
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

    public String getMainUnitReference() {
        return mainUnitReference;
    }

    public void setMainUnitReference(String mainUnitReference) {
        this.mainUnitReference = mainUnitReference;
    }
}
