package in.co.vyapari.model;

/**
 * Created by bekirdursun on 9.04.2018.
 */

public class VersionControl {

    private String LatestVersion;
    private String CurrentVersion;
    private int VersionCount;
    private String Description;
    private boolean IsForceUpdate;

    public String getLatestVersion() {
        return LatestVersion;
    }

    public void setLatestVersion(String latestVersion) {
        LatestVersion = latestVersion;
    }

    public String getCurrentVersion() {
        return CurrentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        CurrentVersion = currentVersion;
    }

    public int getVersionCount() {
        return VersionCount;
    }

    public void setVersionCount(int versionCount) {
        VersionCount = versionCount;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public boolean isForceUpdate() {
        return IsForceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        IsForceUpdate = forceUpdate;
    }
}
