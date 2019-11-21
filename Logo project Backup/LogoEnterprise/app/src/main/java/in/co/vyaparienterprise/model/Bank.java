package in.co.vyaparienterprise.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bekir.Dursun on 25.10.2017.
 */

public class Bank implements Parcelable {

    private String Id;
    private String Name;
    private String Branch;
    private String BranchCode;
    private String AccountNumber;
    private Currency Currency;
    private String Iban;

    public Bank() {
    }

    protected Bank(Parcel in) {
        Id = in.readString();
        Name = in.readString();
        Branch = in.readString();
        BranchCode = in.readString();
        AccountNumber = in.readString();
        Currency = in.readParcelable(KeyValue.class.getClassLoader());
        Iban = in.readString();
    }

    public static final Creator<Bank> CREATOR = new Creator<Bank>() {
        @Override
        public Bank createFromParcel(Parcel in) {
            return new Bank(in);
        }

        @Override
        public Bank[] newArray(int size) {
            return new Bank[size];
        }
    };

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getBranch() {
        return Branch;
    }

    public void setBranch(String Branch) {
        this.Branch = Branch;
    }

    public String getBranchCode() {
        return BranchCode;
    }

    public void setBranchCode(String BranchCode) {
        this.BranchCode = BranchCode;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String AccountNumber) {
        this.AccountNumber = AccountNumber;
    }

    public Currency getCurrency() {
        return Currency;
    }

    public void setCurrency(Currency Currency) {
        this.Currency = Currency;
    }

    public String getIban() {
        return Iban;
    }

    public void setIban(String Iban) {
        this.Iban = Iban;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Id);
        dest.writeString(Name);
        dest.writeString(Branch);
        dest.writeString(BranchCode);
        dest.writeString(AccountNumber);
        dest.writeParcelable(Currency, flags);
        dest.writeString(Iban);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
