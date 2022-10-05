package com.acumengroup.ui.quickaction;

/**
 * Created by Arcadia
 */

public class QuickActionItemDetails {
    private int icon;
    private int background;
    private String title;
    private boolean enabled;
    private boolean titleEnabled;
    private boolean iconEnabled;

    public QuickActionItemDetails(int icon, String title, Boolean enable, int background) {
        this.icon = icon;
        this.title = title;
        this.enabled = enable;
        this.background = background;
        iconEnabled = true;
        titleEnabled = true;
    }

    public QuickActionItemDetails(int icon, Boolean enable, int background) {
        this.icon = icon;
        this.enabled = enable;
        this.background = background;
        iconEnabled = true;
    }

    public QuickActionItemDetails(String title, Boolean enable, int background) {
        this.title = title;
        this.enabled = enable;
        this.background = background;
        titleEnabled = true;
    }

    public boolean isTitleEnabled() {
        return titleEnabled;
    }

    public void setTitleEnabled(boolean titileEnabled) {
        this.titleEnabled = titileEnabled;
    }

    public boolean isIconEnabled() {
        return iconEnabled;
    }

    public void setIconEnabled(boolean iconEnabled) {
        this.iconEnabled = iconEnabled;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

}
