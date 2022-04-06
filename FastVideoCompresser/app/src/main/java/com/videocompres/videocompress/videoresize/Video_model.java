package com.videocompres.videocompress.videoresize;


public class Video_model {
    String str_path,str_thumb;

    String size;
    String duration;
    boolean selected;

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }



    public String getStr_path() {
        return str_path;
    }

    public void setStr_path(String str_path) {
        this.str_path = str_path;
    }

    public String getStr_thumb() {
        return str_thumb;
    }

    public void setStr_thumb(String str_thumb) {
        this.str_thumb = str_thumb;
    }


}