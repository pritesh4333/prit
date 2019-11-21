package in.co.vyaparienterprise.model;

public class Transaction {
    private Double amount;

    private String arpCode;

    private String description;

    private GlCrossAccount glCrossAccount;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getArpCode() {
        return arpCode;
    }

    public void setArpCode(String arpCode) {
        this.arpCode = arpCode;
    }

    public GlCrossAccount getGlCrossAccount() {
        return glCrossAccount;
    }

    public void setGlCrossAccount(GlCrossAccount glCrossAccount) {
        this.glCrossAccount = glCrossAccount;
    }

}
