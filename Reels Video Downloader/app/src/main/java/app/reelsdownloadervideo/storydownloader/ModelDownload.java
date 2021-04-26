package app.reelsdownloadervideo.storydownloader;



import android.os.Parcel;
import android.os.Parcelable;

public class ModelDownload implements Parcelable{

    private String currentFileSizes;
    private String totalFileSizes;
    private int progresss;

    public ModelDownload(){

    }

    public String getCurrentFileSize() {
        return currentFileSizes;
    }
    public void setCurrentFileSize(String currentFileSize) {
        this.currentFileSizes = currentFileSize;
    }
    public String getTotalFileSize() {
        return totalFileSizes;
    }
    public int getProgress() {
        return progresss;
    }
    public void setProgress(int progress) {
        this.progresss = progress;
    }
    public void setTotalFileSize(String totalFileSize) {
        this.totalFileSizes = totalFileSize;
    }

    private ModelDownload(Parcel in) {
        progresss = in.readInt();
        currentFileSizes = in.readString();
        totalFileSizes = in.readString();
    }
    public static final Creator<ModelDownload> CREATOR = new Creator<ModelDownload>() {
        public ModelDownload createFromParcel(Parcel in) {
            return new ModelDownload(in);
        }
        public ModelDownload[] newArray(int size) {
            return new ModelDownload[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(progresss);
        dest.writeString(currentFileSizes);
        dest.writeString(totalFileSizes);
    }
}
