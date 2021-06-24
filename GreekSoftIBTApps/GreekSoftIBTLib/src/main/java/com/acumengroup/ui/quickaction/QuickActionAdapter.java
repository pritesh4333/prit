package com.acumengroup.ui.quickaction;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import androidx.core.view.GravityCompat;
import android.text.TextUtils.TruncateAt;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.acumengroup.ui.textview.GreekTextView;

import java.util.Vector;

/**
 * Created by Arcadia
 */

public class QuickActionAdapter extends BaseAdapter {

    private Context mContext;
    private Vector<QuickActionItemDetails> items;
    private int resourceId = 0;
    private int selectedPosition = -1;
    private int enableTextColor = Color.BLACK;
    private int disableTextColor = Color.GRAY;
    private Typeface textStyle = Typeface.DEFAULT_BOLD;
    private int textSize = 14;
    private int gravity = Gravity.CENTER;
    private int L_lPad = 5, L_tPad = 5, L_rPad = 5, L_bPad = 5;
    private int T_lPad = 5, T_tPad = 5, T_rPad = 5, T_bPad = 5;
    private int I_lMargin = 2, I_tMargin = 2, I_rMargin = 2, I_bMargin = 2;
    private int orientation = LinearLayout.VERTICAL;

    private Drawable arrowUp = null;
    private Drawable arrowDown = null;

    private int itemLayoutWidth = -1;
    private int itemLayoutHeight = -1;

    public QuickActionAdapter(Context context, Vector<QuickActionItemDetails> items/* , int imageRes */) {
        this.mContext = context;
        this.items = items;
        // this.enableBgResource = imageRes;
    }

    public int getEnableTextColor() {
        return enableTextColor;
    }

    public void setEnableTextColor(int enableTextColor) {
        this.enableTextColor = enableTextColor;
    }

    public int getDisableTextColor() {
        return disableTextColor;
    }

    public void setDisableTextColor(int disableTextColor) {
        this.disableTextColor = disableTextColor;
    }

    public Typeface getTextStyle() {
        return textStyle;
    }

    public void setTextStyle(Typeface textStyle) {
        this.textStyle = textStyle;
    }

    public int getCount() {
        return items.size();
    }

    public Object getItem(int position) {
        return items.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getGravity() {
        return gravity;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    public void setLayoutPadding(int left, int top, int right, int bottom) {
        this.L_lPad = left;
        this.L_tPad = top;
        this.L_rPad = right;
        this.L_bPad = bottom;
    }

    public void setTextPadding(int left, int top, int right, int bottom) {
        this.T_lPad = left;
        this.T_tPad = top;
        this.T_rPad = right;
        this.T_bPad = bottom;
    }

    public int getL_lPad() {
        return L_lPad;
    }

    public int getL_tPad() {
        return L_tPad;
    }

    public int getL_rPad() {
        return L_rPad;
    }

    public int getL_bPad() {
        return L_bPad;
    }

    public int getT_lPad() {
        return T_lPad;
    }

    public int getT_tPad() {
        return T_tPad;
    }

    public int getT_rPad() {
        return T_rPad;
    }

    public int getT_bPad() {
        return T_bPad;
    }

    public int getOrientation() {
        return orientation;
    }

    /**
     * @param orientation <br>
     *                    0 - Horizontal <br>
     *                    1 - Vertical (Default) <br>
     */
    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public Drawable getArrowUp() {
        return arrowUp;
    }

    public void setArrowUp(Drawable arrowUp) {
        this.arrowUp = arrowUp;
    }

    public Drawable getArrowDown() {
        return arrowDown;
    }

    public void setArrowDown(Drawable arrowDown) {
        this.arrowDown = arrowDown;
    }

    public void setItemMargin(int left, int top, int right, int bottom) {
        this.I_lMargin = left;
        this.I_tMargin = top;
        this.I_rMargin = right;
        this.I_bMargin = bottom;
    }

    public int getI_lMargin() {
        return I_lMargin;
    }

    public int getI_tMargin() {
        return I_tMargin;
    }

    public int getI_rMargin() {
        return I_rMargin;
    }

    public int getI_bMargin() {
        return I_bMargin;
    }

    public void setItemLayoutParams(int itemLayoutWidth, int itemLayoutHeight) {
        this.itemLayoutWidth = itemLayoutWidth;
        this.itemLayoutHeight = itemLayoutHeight;
    }

    public int getItemLayoutWidth() {
        return itemLayoutWidth;
    }

    public int getItemLayoutHeight() {
        return itemLayoutHeight;
    }

    public void setSelection(int position, int resourceId) {
        this.selectedPosition = position;
        this.resourceId = resourceId;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ItemView item;

        if (convertView != null) {
            item = (ItemView) convertView;
        } else {
            item = new ItemView(mContext, position);
        }
        if (items.get(position).isIconEnabled())
            item.setIconImageView(items.get(position).getIcon());
        if (items.get(position).isTitleEnabled())
            item.setTitleTextView(items.get(position).getTitle());

        // item.setBackgroundResource1(resourceId);
        item.setEnabled(items.get(position).isEnabled(), position);

        return item;
    }

    public class ItemView extends LinearLayout {
        private ImageView iconImageView;
        private GreekTextView titleTextView;

        public ItemView(Context context, int position) {
            super(context);

            // if (enableBgResource != 0)
            // setBackgroundResource(enableBgResource);
            if (items.get(position).getBackground() != 0)
                setBackgroundResource(items.get(position).getBackground());

            setPadding(getL_lPad(), getL_tPad(), getL_rPad(), getL_bPad());

            int width = LayoutParams.FILL_PARENT;
            int height = LayoutParams.WRAP_CONTENT;
            if (getItemLayoutWidth() != -1) width = getItemLayoutWidth();
            if (getItemLayoutHeight() != -1) width = getItemLayoutHeight();
            setLayoutParams(new LayoutParams(width, height));

            LayoutParams params = (LayoutParams) getLayoutParams();
            params.leftMargin = getI_lMargin();
            params.topMargin = getI_tMargin();
            params.rightMargin = getI_rMargin();
            params.bottomMargin = getI_bMargin();
            setLayoutParams(params);
            setGravity(Gravity.CENTER | GravityCompat.START);

            setBaselineAligned(false);

            setOrientation(orientation);

            if (items.get(position).isIconEnabled()) {
                iconImageView = new ImageView(context);
                iconImageView.setScaleType(ImageView.ScaleType.CENTER);
                iconImageView.setImageResource(items.get(position).getIcon());
                addView(iconImageView);
            }
            if (items.get(position).isTitleEnabled()) {
                titleTextView = new GreekTextView(context);
                titleTextView.setTextColor(enableTextColor);
                titleTextView.setGravity(gravity);
                titleTextView.setTypeface(getTextStyle());
                titleTextView.setMaxLines(1);
                titleTextView.setEllipsize(TruncateAt.MARQUEE);
                titleTextView.setMarqueeRepeatLimit(-1);
                titleTextView.setSelected(true);
                titleTextView.setTypeface(textStyle);
                titleTextView.setPadding(getT_lPad(), getT_tPad(), getT_rPad(), getT_bPad());
                titleTextView.setText(items.get(position).getTitle());
                addView(titleTextView);
            }
            if (!items.get(position).isEnabled()) {
                if (items.get(position).isIconEnabled()) iconImageView.setAlpha(80);
                if (items.get(position).isTitleEnabled())
                    titleTextView.setTextColor(disableTextColor);
            }
        }

        public void setIconImageView(int iconImageView) {
            this.iconImageView.setImageResource(iconImageView);
        }

        public void setTitleTextView(String titleTextView) {
            if (this.titleTextView != null) this.titleTextView.setText(titleTextView);
        }

        public void setEnabled(boolean isEnabled, int position) {
            if (!isEnabled) {
                if (items.get(position).isIconEnabled()) this.iconImageView.setAlpha(80);
                if (items.get(position).isTitleEnabled())
                    this.titleTextView.setTextColor(disableTextColor);
                // if (disableBgResource != 0)
                // setBackgroundResource(disableBgResource);
                // if(items.get(position).getBackground() !=0 )
                // setBackgroundResource(items.get(position).getBackground());

            } else {
                if (items.get(position).isIconEnabled()) this.iconImageView.setAlpha(255);
                if (items.get(position).isTitleEnabled())
                    this.titleTextView.setTextColor(enableTextColor);
            }

            if (items.get(position).getBackground() != 0)
                setBackgroundResource(items.get(position).getBackground());

            if (position == selectedPosition) setBackgroundResource(resourceId);

        }

    }

}