package com.acumengroup.ui.quickaction;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by Arcadia
 */

public class QuickActionView extends CustomPopupWindow implements OnClickListener, DialogInterface {

    private final View root;
    private final ImageView mArrowUp;
    private final ImageView mArrowDown;
    private final Context context;
    protected LinearLayout mTrack;
    protected ScrollView scroller;
    protected int mNumColumns = -1;
    private OnClickListener mClickListener;
    private int layoutBGColor = -1;
    private int layoutBGResource = -1;
    private boolean showVerticalDivider = false;
    private int verticalDividerColor = Color.GRAY;
    private int verticalDividerHieght = 2;

    private QuickActionAdapter mAdapter;

    public QuickActionView(View anchor, QuickActionAdapter adapter) {
        super(anchor);
        this.mAdapter = adapter;
        context = anchor.getContext();

        root = new QuickActionPopupWindow(context);
        mArrowDown = root.findViewById(QuickActionPopupWindow.ARROWDOWM_ID);
        mArrowUp = root.findViewById(QuickActionPopupWindow.ARROWUP_ID);
        mArrowUp.setImageDrawable(adapter.getArrowUp());
        mArrowDown.setImageDrawable(adapter.getArrowDown());

        setContentView(root);
        mTrack = root.findViewById(QuickActionPopupWindow.LAYOUT_ID);
        mTrack.setOrientation(LinearLayout.VERTICAL);
        mTrack.setPadding(0, 0, 0, 0);

        scroller = root.findViewById(QuickActionPopupWindow.SCROLLER_ID);
        scroller.setPadding(5, 0, 5, 0);
    }

    /**
     * @return returns true if popup window shows
     */
    public boolean isPopupShowing() {
        return window != null && window.isShowing();
    }

    public int getNumColumns() {
        return mNumColumns;
    }

    /**
     * Set the number of columns per row -1 for auto columns
     *
     * @param value
     */
    public void setNumColumns(int value) {
        mNumColumns = value;
    }

    public boolean isShowVerticalDivider() {
        return showVerticalDivider;
    }

    public void setShowVerticalDivider(boolean showVerticalDivider) {
        this.showVerticalDivider = showVerticalDivider;
    }

    public int getVerticalDividerHieght() {
        return verticalDividerHieght;
    }

    public void setVerticalDividerHieght(int verticalDividerHieght) {
        this.verticalDividerHieght = verticalDividerHieght;
    }

    public int getVerticalDividerColor() {
        return verticalDividerColor;
    }

    public void setVerticalDividerColor(int verticalDividerColor) {
        this.verticalDividerColor = verticalDividerColor;
    }

    public int getLayoutBGColor() {
        return layoutBGColor;
    }

    public void setLayoutBGColor(int layoutBGColor) {
        this.layoutBGColor = layoutBGColor;
    }

    public int getLayoutBGResource() {
        return layoutBGResource;
    }

    public void setLayoutBGResource(int layoutBGResource) {
        this.layoutBGResource = layoutBGResource;
    }

    public QuickActionView setOnClickListener(OnClickListener listener) {
        mClickListener = listener;
        return this;
    }

    public void show() {
        preShow();

        if (getLayoutBGColor() != -1) mTrack.setBackgroundColor(getLayoutBGColor());
        if (getLayoutBGResource() != -1) mTrack.setBackgroundResource(getLayoutBGResource());

        int xPos, yPos;

        int[] location = new int[2];
        parentView.getLocationOnScreen(location);
        Rect anchorRect = new Rect(location[0], location[1], location[0] + parentView.getWidth(), location[1] + parentView.getHeight());

        // Create List
        createActionList();

        root.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        root.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        int rootHeight = root.getMeasuredHeight();
        int rootWidth = root.getMeasuredWidth();

        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();

        // check x position
        final int anchorCenterX = anchorRect.centerX();

        xPos = anchorRect.left;

        if (anchorCenterX - (rootWidth / 2) >= 0) {
            xPos = anchorCenterX - (rootWidth / 2);
            if (xPos + rootWidth > screenWidth) {
                xPos -= (xPos + rootWidth) - screenWidth;
            }
        } else {
            xPos = 0;
        }

        int dyTop = anchorRect.top;
        int dyBottom = screenHeight - anchorRect.bottom;

        boolean onTop = (dyTop > dyBottom);

        if (onTop) {
            if (rootHeight > dyTop) {
                yPos = 15;
                LayoutParams l = scroller.getLayoutParams();
                l.height = dyTop - parentView.getHeight();
            } else {
                yPos = anchorRect.top - rootHeight;
            }
        } else {
            yPos = anchorRect.bottom;

            if (rootHeight > dyBottom) {
                LayoutParams l = scroller.getLayoutParams();
                l.height = dyBottom;
            }
        }

        showArrow(((onTop) ? QuickActionPopupWindow.ARROWDOWM_ID : QuickActionPopupWindow.ARROWUP_ID), anchorRect.centerX() - xPos);

        setAnimationStyle(screenWidth, anchorRect.centerX(), onTop);
        window.showAtLocation(parentView, Gravity.NO_GRAVITY, xPos, yPos);
        root.getLocationOnScreen(location);
    }

    @SuppressWarnings("unused")
    private void setAnimationStyle(int screenWidth, int requestedX, boolean onTop) {
        int arrowPos = requestedX - mArrowUp.getMeasuredWidth() / 2;
        // window.setAnimationStyle( R.style.Animations_PopDownMenu_Fade );
    }

    protected void createActionList() {

        final int screenWidth = windowManager.getDefaultDisplay().getWidth();
        final int numItems = mAdapter.getCount();

        View view;
        ViewGroup parent = null;
        boolean needNewRow = true;
        int totalChildWidth = 0;

        for (int i = 0; i < numItems; i++) {
            if (i % mNumColumns == 0 && mNumColumns > 0) {
                needNewRow = true;
            }
            int childWidth = 0;

            if (mAdapter.getItemLayoutWidth() != -1) {
                childWidth = mAdapter.getItemLayoutWidth();
                totalChildWidth += childWidth;
                // 20 for Padding
                if (totalChildWidth > (screenWidth - 20)) {
                    totalChildWidth = childWidth;
                    // mNumColumns = -1;
                    needNewRow = true;
                }
            }

            if (needNewRow) {

                if (i != 0 && showVerticalDivider) {
                    LinearLayout divider = new LinearLayout(context);
                    int width = LayoutParams.WRAP_CONTENT;
                    if (getNumColumns() == 1) width = LayoutParams.MATCH_PARENT;
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, verticalDividerHieght);
                    params.weight = 1;
                    divider.setLayoutParams(params);
                    divider.setBackgroundColor(verticalDividerColor);
                    mTrack.addView(divider);
                }

                parent = new LinearLayout(context);
                int width = LayoutParams.WRAP_CONTENT;
                if (getNumColumns() == 1) width = LayoutParams.MATCH_PARENT;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, LayoutParams.WRAP_CONTENT);
                params.weight = 1;
                /*
				 * params.bottomMargin = 10; params.leftMargin= 10;
				 * params.rightMargin= 10; params.topMargin= 10;
				 * parent.setBackgroundColor(Color.RED);
				 */
                parent.setLayoutParams(params);

                mTrack.addView(parent);

				/*
				 * TextView view2 = new TextView(context);
				 * view2.setLayoutParams(new LinearLayout.LayoutParams(
				 * width,5)); view2.setBackgroundColor(Color.RED);
				 * mTrack.addView(view2);
				 */
            }

            view = mAdapter.getView(i, null, parent);
            view.setFocusable(true);
            view.setClickable(true);
            view.setTag(i);
            view.setOnClickListener(this);
            addActionView(view, parent);

            parent.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            int padding = mTrack.getPaddingLeft() + mTrack.getPaddingRight();
            int parentWidth = parent.getMeasuredWidth() + padding;

            childWidth = parentWidth / parent.getChildCount();

            needNewRow = false;

            // For Automatic filling items
            if (mAdapter.getItemLayoutWidth() == -1) {
                if (parentWidth + childWidth > screenWidth) {
                    mNumColumns = -1;
                    needNewRow = true;
                }
            }
        }
    }

    protected void addActionView(View view, ViewGroup parent) {
        parent.addView(view);
    }

    private void showArrow(int whichArrow, int requestedX) {
        final View showArrow = (whichArrow == QuickActionPopupWindow.ARROWUP_ID) ? mArrowUp : mArrowDown;
        final View hideArrow = (whichArrow == QuickActionPopupWindow.ARROWUP_ID) ? mArrowDown : mArrowUp;

        final int arrowWidth = mArrowUp.getMeasuredWidth();

        showArrow.setVisibility(View.VISIBLE);
        ViewGroup.MarginLayoutParams param = (ViewGroup.MarginLayoutParams) showArrow.getLayoutParams();
        param.leftMargin = requestedX - (arrowWidth / 2);

        hideArrow.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {

        int index = (Integer) v.getTag();

        if (index > -1) {
            if (mClickListener != null && ((QuickActionItemDetails) mAdapter.getItem(index)).isEnabled()) {
                dismiss();
                mClickListener.onClick(this, index);
            }
        }
    }

	/*
	 * private int getChildIndex(View v) {
	 * 
	 * ViewGroup parent = (ViewGroup) v.getParent(); int index =
	 * getChildIndex(v, parent); int index2 = getRowIndex(parent, mTrack);
	 * 
	 * return index + index2; }
	 * 
	 * private int getChildIndex(View v, ViewGroup parent) {
	 * 
	 * for (int i = 0; i < parent.getChildCount(); i++) { if
	 * (parent.getChildAt(i).equals(v)) return i; } return -1; }
	 * 
	 * private int getRowIndex(ViewGroup v, ViewGroup parent) {
	 * 
	 * int index = 0; for (int i = 0; i < parent.getChildCount(); i++) { if
	 * (parent.getChildAt(i).equals(v)) return index; index += ((LinearLayout)
	 * parent.getChildAt(0)).getChildCount(); } return 0; }
	 */

    @Override
    public void cancel() {
        this.dismiss();
    }

}