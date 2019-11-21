package in.co.vyaparienterprise.model.response.dto;

import in.co.vyaparienterprise.model.UserTenant;

public class UserDTO {

    private String accessToken;

    private UserTenant userTenant;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public UserTenant getUserTenant() {
        return userTenant;
    }

    public void setUserTenant(UserTenant userTenant) {
        this.userTenant = userTenant;
    }




}
