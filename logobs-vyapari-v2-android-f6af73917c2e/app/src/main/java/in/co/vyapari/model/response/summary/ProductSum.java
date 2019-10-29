package in.co.vyapari.model.response.summary;

import in.co.vyapari.model.Currency;
import in.co.vyapari.model.Product;

public class ProductSum {

    private String Id;
    private String Name;
    private String Code;



    private int CardType;
    private double SalesPrice;
    private double PurchasePrice;
    private boolean GSTIncluded;
    private Currency PurchaseCurrency;
    private Currency SaleCurrency;
    private double PhysicalInventory;
    private double ActualInventory;
    private boolean IsActive;

    public ProductSum(Product product) {
        this.Id = product.getId();
        this.Name = product.getName();
        this.Code = product.getCode();
        this.SalesPrice = product.getSalesPrice();
        this.CardType=product.getCardType();
        this.PurchasePrice = product.getPurchasePrice();
        this.GSTIncluded = product.isGSTIncluded();
        this.PurchaseCurrency = product.getPurchaseCurrency();
        this.SaleCurrency = product.getSalesCurrency();
        this.PhysicalInventory = product.getPhysicalInventory();
        this.ActualInventory = product.getActualInventory();
        this.SaleCurrency = product.getSalesCurrency();
        this.IsActive = product.isActive();
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getCode() {
        return Code;
    }

    public double getSalesPrice() {
        return SalesPrice;
    }

    public double getPurchasePrice() {
        return PurchasePrice;
    }

    public boolean isGSTIncluded() {
        return GSTIncluded;
    }

    public Currency getPurchaseCurrency() {
        return PurchaseCurrency;
    }

    public Currency getSaleCurrency() {
        return SaleCurrency;
    }

    public double getPhysicalInventory() {
        return PhysicalInventory;
    }

    public double getActualInventory() {
        return ActualInventory;
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
}
