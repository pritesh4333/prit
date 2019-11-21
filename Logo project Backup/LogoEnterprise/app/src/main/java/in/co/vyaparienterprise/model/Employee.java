package in.co.vyaparienterprise.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bekir.Dursun on 25.10.2017.
 */

public class Employee implements Parcelable {

    private String Id;
    private String FullName;
    private String Job;
    private String Email;
    private String Phone;
    private String DateOfBirth;
    private boolean IsManager;

    public Employee() {
    }

    protected Employee(Parcel in) {
        Id = in.readString();
        FullName = in.readString();
        Job = in.readString();
        Email = in.readString();
        Phone = in.readString();
        DateOfBirth = in.readString();
        IsManager = in.readByte() != 0;
    }

    public static final Creator<Employee> CREATOR = new Creator<Employee>() {
        @Override
        public Employee createFromParcel(Parcel in) {
            return new Employee(in);
        }

        @Override
        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    public String getJob() {
        return Job;
    }

    public void setJob(String Job) {
        this.Job = Job;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String DateOfBirth) {
        this.DateOfBirth = DateOfBirth;
    }

    public boolean isManager() {
        return IsManager;
    }

    public void setManager(boolean manager) {
        IsManager = manager;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Id);
        parcel.writeString(FullName);
        parcel.writeString(Job);
        parcel.writeString(Email);
        parcel.writeString(Phone);
        parcel.writeString(DateOfBirth);
        parcel.writeByte((byte) (IsManager ? 1 : 0));
    }
}
