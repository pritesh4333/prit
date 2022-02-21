package app.reelsdownloadervideo.storydownloader;


public class ReelsModel {

    String sdurations;
    boolean sselecteds;
    String sstr_paths,sstr_thumbs;
    String ssizes;

    public void setSize(String size) {
        this.ssizes = size;
    }
    public String getStr_path() {
        return sstr_paths;
    }
    public void setStr_path(String str_path) {
        this.sstr_paths = str_path;
    }
    public String getStr_thumb() {
        return sstr_thumbs;
    }
    public void setStr_thumb(String str_thumb) {
        this.sstr_thumbs = str_thumb;
    }
    public boolean getSelected() {
        return sselecteds;
    }
    public void setSelected(boolean selected) {
        this.sselecteds = selected;
    }
    public String getDuration() {
        return sdurations;
    }
    public void setDuration(String duration) {
        this.sdurations = duration;
    }
    public String getSize() {
        return ssizes;
    }
}