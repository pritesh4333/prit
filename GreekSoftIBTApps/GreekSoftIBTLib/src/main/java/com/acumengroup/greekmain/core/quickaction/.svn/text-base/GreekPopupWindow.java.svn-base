package com.acumengroup.greekmain.core.quickaction;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;

import com.acumengroup.mobile.R;


/**
 * Created by Arcadia
 */
public class GreekPopupWindow extends CustomPopupWindow implements GreekPopupWindowListener {

    private static GreekPopupWindow popupWindow;
    //	private View root;
    private POPUP_DIRECTION direction;
    private Drawable drawableResourceId1 = null, drawableResourceId2 = null;
    private GreekPopupWindowLayout popupWindowLayout;

    public GreekPopupWindow(Context context, View parent, View rootView, POPUP_DIRECTION direction) {
        super(parent);

        this.direction = direction;

        popupWindowLayout = new GreekPopupWindowLayout(context, this);

		/*if (direction == POPUP_DIRECTION.LEFT_OR_RIGHT) {
            popupWindowLayout.setOrientation(0);
		} else if (direction == POPUP_DIRECTION.TOP_OR_BOTTOM) {
			popupWindowLayout.setOrientation(1);
		}*/

        popupWindowLayout.setContentView(rootView, direction);

//		this.root = popupWindowLayout;
      //  showPopupAnimation();
        setContentView(popupWindowLayout);

    }

    public static GreekPopupWindow getInstance(Context context, View v, View layout, POPUP_DIRECTION direction) {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
        popupWindow = new GreekPopupWindow(context, v, layout, direction);
        return popupWindow;
    }

    /**
     * @param drawableResourceId1 - Left or top
     * @param drawableResourceId2 - Right or Bottom
     */
    public void setArrowImage(Drawable drawableResourceId1, Drawable drawableResourceId2) {
        this.drawableResourceId1 = drawableResourceId1;
        this.drawableResourceId2 = drawableResourceId2;
    }

    /**
     * @return returns true if popup window shows
     */
    public boolean isPopupShowing() {
        return window != null && window.isShowing();
    }

    public void showPopupAnimation() {
        window.setAnimationStyle(R.style.PopupWindowAnimation);
    }



    public void show() {
        preShow();

        calculateXY(popupWindowLayout.getMeasuredWidth(), popupWindowLayout.getMeasuredHeight());

    }

    private void calculateXY(int viewWidth, int viewHeight) {

        int xPos = 0, yPos = 0;

        int[] location = new int[2];
        parentView.getLocationOnScreen(location);

        // Parent view exact location in screen.
        Rect parentRect = new Rect(location[0], location[1], location[0] + parentView.getWidth(), location[1] + parentView.getHeight());

        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();

        if (direction == POPUP_DIRECTION.LEFT_OR_RIGHT) {

            // Y position
            if (parentRect.centerY() > (viewHeight / 2)) {

                if ((screenHeight - parentRect.centerY()) > (viewHeight / 2)) {
                    yPos = (parentRect.centerY() - (viewHeight / 2));
                } else {
                    yPos = (screenHeight - viewHeight);
                }

            } else {
                yPos = 0;
            }

            int value = yPos - parentRect.centerY();
            if (parentRect.centerY() > yPos) {
                value = parentRect.centerY() - yPos;
            }

            // X position
//			if ((screenWidth - parentRect.right) > viewWidth) {
            // Right Side
//				xPos = parentRect.right;
            xPos = (parentRect.centerX() - (viewWidth / 2));
            popupWindowLayout.setLeftArrow(drawableResourceId1, value);
//			} else {
            // Left Side
//				xPos = (parentRect.left - viewWidth);
//				xPos = (screenWidth - viewWidth);
//
//				popupWindowLayout.setRightArrow(drawableResourceId2, value);
//			}

            yPos = yPos == 0 ? 10 : yPos;

        } else if (direction == POPUP_DIRECTION.TOP_OR_BOTTOM) {

            // X position
            if (parentRect.centerX() > (viewWidth / 2)) {

                if ((screenWidth - parentRect.centerX()) > (viewWidth / 2)) {
                    xPos = (parentRect.centerX() - (viewWidth / 2));
                } else {
                    xPos = (screenWidth - viewWidth);
                }

            } else {
                xPos = 0;
            }

            int value = xPos - parentRect.centerX();
            if (parentRect.centerX() > xPos) {
                value = parentRect.centerX() - xPos;
            }

            // Y position
            if ((screenHeight - parentRect.bottom) > viewHeight) {
                // Bottom Side
                yPos = parentRect.bottom;
                popupWindowLayout.setTopArrow(drawableResourceId1, value);
            } else {
                // Top Side
                yPos = (parentRect.top - viewHeight);
                popupWindowLayout.setBottomArrow(drawableResourceId2, value);
            }

            xPos = xPos == 0 ? 10 : xPos;

            if (xPos + viewWidth == screenWidth) {
                xPos -= 10;
            }

        }

        // Show popup
        window.showAtLocation(parentView, Gravity.NO_GRAVITY, xPos, yPos);
        window.update(xPos, yPos, -1, -1);

    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {

        if (window != null) {
            // Window exists.
            if (window.isShowing()) {
                // Update the window X & Y depends upon new width & height.
                calculateXY(w, h);
            }
        }
    }

    public enum POPUP_DIRECTION {
        LEFT_OR_RIGHT, TOP_OR_BOTTOM
    }

}