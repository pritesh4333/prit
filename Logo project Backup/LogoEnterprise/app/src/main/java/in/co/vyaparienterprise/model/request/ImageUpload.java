package in.co.vyaparienterprise.model.request;

/**
 * Created by bekirdursun on 21.03.2018.
 */

public class ImageUpload {

    private String FileName;
    private String Base64StringData;

    public ImageUpload(String fileName, String base64StringData) {
        FileName = fileName;
        Base64StringData = base64StringData;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getBase64StringData() {
        return Base64StringData;
    }

    public void setBase64StringData(String base64StringData) {
        Base64StringData = base64StringData;
    }
}
