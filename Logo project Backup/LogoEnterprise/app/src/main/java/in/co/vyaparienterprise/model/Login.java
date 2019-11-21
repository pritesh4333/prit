package in.co.vyaparienterprise.model;

/**
 * Created by Bekir.Dursun on 3.10.2017.
 */

public class Login {

    private String Email;
    private String Password;

    public Login() {
    }

    public Login(String email, String password) {
        this.Email = email;
        this.Password = password;
    }

    public void setUserName(String email) {
        this.Email = email;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public String getUserName() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }
}
