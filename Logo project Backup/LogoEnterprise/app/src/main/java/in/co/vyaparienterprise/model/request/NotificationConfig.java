package in.co.vyaparienterprise.model.request;

/**
 * Created by bekirdursun on 25.12.2017.
 */

public class NotificationConfig {

    private String OneSignalUserId;
    private String RegistrationId;
    private boolean isActive;

    public NotificationConfig(String oneSignalUserId, String registrationId, boolean isActive) {
        OneSignalUserId = oneSignalUserId;
        RegistrationId = registrationId;
        this.isActive = isActive;
    }

    public String getOneSignalUserId() {
        return OneSignalUserId;
    }

    public void setOneSignalUserId(String oneSignalUserId) {
        OneSignalUserId = oneSignalUserId;
    }

    public String getRegistrationId() {
        return RegistrationId;
    }

    public void setRegistrationId(String registrationId) {
        RegistrationId = registrationId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
