package com.reels.video.download.instagram.video.downloader.saver.story.video;



import android.os.Parcel;
import android.os.Parcelable;

public class Download  implements Parcelable{

    public Download(){

    }

    private int progress;
    private String currentFileSize;
    private String totalFileSize;

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getCurrentFileSize() {
        return currentFileSize;
    }

    public void setCurrentFileSize(String currentFileSize) {
        this.currentFileSize = currentFileSize;
    }

    public String getTotalFileSize() {
        return totalFileSize;
    }

    public void setTotalFileSize(String totalFileSize) {
        this.totalFileSize = totalFileSize;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(progress);
        dest.writeString(currentFileSize);
        dest.writeString(totalFileSize);
    }

    private Download(Parcel in) {

        progress = in.readInt();
        currentFileSize = in.readString();
        totalFileSize = in.readString();
    }

    public static final Parcelable.Creator<Download> CREATOR = new Parcelable.Creator<Download>() {
        public Download createFromParcel(Parcel in) {
            return new Download(in);
        }

        public Download[] newArray(int size) {
            return new Download[size];
        }
    };
}
