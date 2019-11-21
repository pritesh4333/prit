package in.co.vyaparienterprise.model.response.dto;

import in.co.vyaparienterprise.model.User;

/**
 * Created by Bekir.Dursun on 17.10.2017.
 */

public class LoginDTO {

    private String AccessToken;
    private boolean IsFirstLogin;
    private String Language;
    private CompanyDTO Company;
    private User User;

    public void setAccessToken(String accessToken) {
        AccessToken = accessToken;
    }

    public void setFirstLogin(boolean firstLogin) {
        IsFirstLogin = firstLogin;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public void setCompany(CompanyDTO company) {
        Company = company;
    }

    public void setUser(in.co.vyaparienterprise.model.User user) {
        User = user;
    }

    public String getAccessToken() {
        return AccessToken;
    }

    public boolean isFirstLogin() {
        return IsFirstLogin;
    }

    public String getLanguage() {
        return Language;
    }

    public CompanyDTO getCompany() {
        return Company;
    }

    public User getUser() {
        return User;
    }
}
