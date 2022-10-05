package com.acumengroup.mobile.actionbar;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.button.GreekButton;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.operation.StringStuff;


/**
 * Created by Arcadia
 */
public class GreekActionBar extends RelativeLayout {
    public ImageView arcadia_logo;
    private final GreekTextView niftyVal;
    private final GreekTextView niftyChange;
    private final GreekTextView sensexVal;
    private final GreekTextView sensexChange;
    private final GreekTextView DhaanyaVal;
    private final GreekTextView DhaanyaChange;
    private final GreekTextView goldVal;
    private final GreekTextView goldChange;
    private final ImageButton mHomeBtn;
    private final ImageButton mSearchBtn;
    private final GreekTextView mTitleView, niftyText, sensexText, goldText, dhaanyaText;
    private final GreekButton niftyDetail, sensexDetail, goldDetail, dhaanyaDetail, niftyDetailPercentage, sensexDetailPercent;
    private final LinearLayout signal_layout, nifty_layout, sensex_layout, linear_divider;
    private final RelativeLayout appTicker1Label;
    private final RelativeLayout appTicker, appTicker1;
    private final GreekTextView eq_signal_indicator, fno_signal_indicator, cd_signal_indicator, com_signal_indicator;
    private final GreekTextView eq_signal;
    private final GreekTextView fno_signal;
    private final GreekTextView cd_signal;
    private final GreekTextView com_signal;
    private Context ctx;
    private final LinearLayout equity_layout, fno_layout, cd_layout, com_layout;
    private GreekTextView niftyNameText, sensexNameText, niftyvalue, sensexvalue;
    private int textColorPositive, textColorNegative;


    public GreekActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout mBarView = (RelativeLayout) mInflater.inflate(R.layout.greek_actionbar_new, null);
        addView(mBarView);
        this.ctx = context;

        niftyNameText = mBarView.findViewById(R.id.txt_frst_name_actbar);
        sensexNameText = mBarView.findViewById(R.id.txt_sec_name_actbar);
        niftyvalue = mBarView.findViewById(R.id.txt_actbar_nifty);
        sensexvalue = mBarView.findViewById(R.id.txt_actbar_sensex);
        arcadia_logo = mBarView.findViewById(R.id.arcadia_logo);

        mHomeBtn = mBarView.findViewById(R.id.btnMenu);
        mSearchBtn = mBarView.findViewById(R.id.btnQuotes);

        appTicker = mBarView.findViewById(R.id.appTicker);
        appTicker1 = mBarView.findViewById(R.id.appTicker1);


        niftyDetail = mBarView.findViewById(R.id.niftyDetail);
        niftyDetailPercentage = mBarView.findViewById(R.id.niftyDetailPercent);

        niftyText = mBarView.findViewById(R.id.nifty_text);
        dhaanyaDetail = mBarView.findViewById(R.id.dhanyaDetail);
        goldDetail = mBarView.findViewById(R.id.goldDetail);

        sensexDetailPercent = mBarView.findViewById(R.id.sensexDetailPercent);
        sensexDetail = mBarView.findViewById(R.id.sensexDetail);
        sensexText = mBarView.findViewById(R.id.sensex_text);

        goldText = mBarView.findViewById(R.id.gold_text);
        dhaanyaText = mBarView.findViewById(R.id.dhanya_text);
        mTitleView = mBarView.findViewById(R.id.appTitle);

        niftyVal = mBarView.findViewById(R.id.nifty_value_text);
        niftyChange = mBarView.findViewById(R.id.nifty_per_value);

        goldVal = mBarView.findViewById(R.id.gold_value_text);
        goldChange = mBarView.findViewById(R.id.gold_per_value);

        DhaanyaVal = mBarView.findViewById(R.id.dhanya_value);
        DhaanyaChange = mBarView.findViewById(R.id.dhanya_per_value);

        sensexVal = mBarView.findViewById(R.id.sensex_value);
        sensexChange = mBarView.findViewById(R.id.sensex_per_value);

        signal_layout = mBarView.findViewById(R.id.signal_layout);
        nifty_layout = mBarView.findViewById(R.id.nifty_layout);
        sensex_layout = mBarView.findViewById(R.id.sensex_layout);
        linear_divider = mBarView.findViewById(R.id.linear_divider);

        equity_layout = mBarView.findViewById(R.id.eq_status_layout);
        fno_layout = mBarView.findViewById(R.id.fno_status_layout);
        cd_layout = mBarView.findViewById(R.id.cd_status_layout);
        com_layout = mBarView.findViewById(R.id.com_status_layout);

        appTicker1Label = mBarView.findViewById(R.id.appTicker1);

        eq_signal_indicator = mBarView.findViewById(R.id.eq_signal_indicator);
        fno_signal_indicator = mBarView.findViewById(R.id.fno_signal_indicator);
        cd_signal_indicator = mBarView.findViewById(R.id.cd_signal_indicator);
        com_signal_indicator = mBarView.findViewById(R.id.com_signal_indicator);

        eq_signal = mBarView.findViewById(R.id.eq_indicator);
        fno_signal = mBarView.findViewById(R.id.fno_indicator);
        cd_signal = mBarView.findViewById(R.id.cd_indicator);
        com_signal = mBarView.findViewById(R.id.com_indicator);
        setTheme();


        if (GreekBaseActivity.USER_TYPE == GreekBaseActivity.USER.OPENUSER) {

            equity_layout.setVisibility(View.VISIBLE);
            fno_layout.setVisibility(View.VISIBLE);
            cd_layout.setVisibility(View.VISIBLE);
            com_layout.setVisibility(View.VISIBLE);
        } else {
            if (AccountDetails.allowedmarket_nse || AccountDetails.allowedmarket_bse) {
                equity_layout.setVisibility(View.VISIBLE);
            }
            if (AccountDetails.allowedmarket_nfo || AccountDetails.allowedmarket_bfo) {
                fno_layout.setVisibility(View.VISIBLE);
            }
            if (AccountDetails.allowedmarket_ncd || AccountDetails.allowedmarket_bcd) {
                cd_layout.setVisibility(View.VISIBLE);
            }
            if (AccountDetails.allowedmarket_mcx || AccountDetails.allowedmarket_ncdex) {
                com_layout.setVisibility(View.VISIBLE);
            }
        }
    }

    private void setTheme() {

        String packagname =  ctx.getPackageName();
         if (packagname.equalsIgnoreCase("com.vishwas.mobile")){
             if (AccountDetails.getThemeFlag(getContext()).equalsIgnoreCase("white")) {
                 arcadia_logo.setBackground(getResources().getDrawable(R.drawable.vishwas_action_top_bar_blue));
             }else{
                 arcadia_logo.setBackground(getResources().getDrawable(R.drawable.vishwas_action_top_bar_white));
             }

        }else  if (packagname.equalsIgnoreCase("com.tradeongo.mobile")){
             arcadia_logo.setBackground(getResources().getDrawable(R.drawable.philips_login_icon));
        }else  if (packagname.equalsIgnoreCase("com.mandot.mobile")){
             arcadia_logo.setBackground(getResources().getDrawable(R.mipmap.mandot_login_icon));
        }else  if (packagname.equalsIgnoreCase("com.msfl.mobile")){
             arcadia_logo.setBackground(getResources().getDrawable(R.drawable.marwadi_login_icon));
        }else  if (packagname.equalsIgnoreCase("com.clicktrade.mobile")){
             arcadia_logo.setBackground(getResources().getDrawable(R.drawable.pentagon_actionbar));
             arcadia_logo.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
             LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
             layoutParams.gravity= Gravity.CENTER;
             arcadia_logo.setLayoutParams(layoutParams);
         }

        if (AccountDetails.getThemeFlag(getContext()).equalsIgnoreCase("white")) {
            niftyNameText.setTextColor(getResources().getColor(R.color.textColorCustomWhite));
            sensexNameText.setTextColor(getResources().getColor(R.color.textColorCustomWhite));
            eq_signal_indicator.setTextColor( getResources().getColor(R.color.textColorCustomWhite));
            fno_signal_indicator.setTextColor(  getResources().getColor(R.color.textColorCustomWhite));
            cd_signal_indicator.setTextColor(  getResources().getColor(R.color.textColorCustomWhite));
            com_signal_indicator.setTextColor(  getResources().getColor(R.color.textColorCustomWhite));
            signal_layout.setBackgroundColor(getContext().getResources().getColor(R.color.white));
            appTicker1.setBackgroundColor(getResources().getColor(R.color.white));
            mHomeBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_blck_24dp));
            mSearchBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_search_black_24dp));
        }

    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void settingThemeAssetActionBar() {
        appTicker.setBackgroundColor(getResources().getColor(R.color.white));

        mHomeBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_blck_24dp));
        mSearchBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_search_black_24dp));

        linear_divider.setBackgroundColor(getResources().getColor(R.color.black));
        signal_layout.setBackgroundColor(getResources().getColor(R.color.marketStatusStripWhite));
        appTicker1Label.setBackgroundColor(getResources().getColor(R.color.login_bg));

        niftyText.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        niftyVal.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        niftyChange.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        niftyDetailPercentage.setBackground(getResources().getDrawable(R.drawable.nifty_shape_down_red));

        sensexText.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        sensexVal.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        sensexChange.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        sensexDetailPercent.setBackground(getResources().getDrawable(R.drawable.nifty_shape_down_red));

        goldText.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        goldVal.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        goldChange.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

        dhaanyaText.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        DhaanyaVal.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        DhaanyaChange.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

        eq_signal_indicator.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        fno_signal_indicator.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        cd_signal_indicator.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        com_signal_indicator.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void changeStatus(String market_id, String status) {
        if(GreekBaseActivity.USER_TYPE != GreekBaseActivity.USER.OPENUSER){
            if (market_id.equalsIgnoreCase("eq")) {
                if (status.equalsIgnoreCase("green")) {
                    eq_signal.setBackground(getResources().getDrawable(R.drawable.ic_round_green_circle_24dp));
                } else if (status.equalsIgnoreCase("red")) {
                    eq_signal.setBackground(getResources().getDrawable(R.drawable.ic_round_red_circle_24dp));
                } else if (status.equalsIgnoreCase("blue")) {
                    eq_signal.setBackground(getResources().getDrawable(R.drawable.ic_round_blue_circle_24dp));
                } else if (status.equalsIgnoreCase("pink")) {
                    eq_signal.setBackground(getResources().getDrawable(R.drawable.ic_round_pink_circle_24dp));
                } else if (status.equalsIgnoreCase("yellow")) {
                    eq_signal.setBackground(getResources().getDrawable(R.drawable.ic_round_yellow_circle_24dp));
                }
            }

        if (market_id.equalsIgnoreCase("fno")) {
            if (status.equalsIgnoreCase("green")) {
                fno_signal.setBackground(getResources().getDrawable(R.drawable.ic_round_green_circle_24dp));
            } else if (status.equalsIgnoreCase("red")) {
                fno_signal.setBackground(getResources().getDrawable(R.drawable.ic_round_red_circle_24dp));
            } else if (status.equalsIgnoreCase("blue")) {
                fno_signal.setBackground(getResources().getDrawable(R.drawable.ic_round_blue_circle_24dp));
            } else if (status.equalsIgnoreCase("pink")) {
                fno_signal.setBackground(getResources().getDrawable(R.drawable.ic_round_pink_circle_24dp));
            } else if (status.equalsIgnoreCase("yellow")) {
                fno_signal.setBackground(getResources().getDrawable(R.drawable.ic_round_yellow_circle_24dp));
            }
        }
        if (market_id.equalsIgnoreCase("cd")) {
            if (status.equalsIgnoreCase("green")) {
                cd_signal.setBackground(getResources().getDrawable(R.drawable.ic_round_green_circle_24dp));
            } else if (status.equalsIgnoreCase("red")) {
                cd_signal.setBackground(getResources().getDrawable(R.drawable.ic_round_red_circle_24dp));
            } else if (status.equalsIgnoreCase("blue")) {
                cd_signal.setBackground(getResources().getDrawable(R.drawable.ic_round_blue_circle_24dp));
            } else if (status.equalsIgnoreCase("pink")) {
                cd_signal.setBackground(getResources().getDrawable(R.drawable.ic_round_pink_circle_24dp));
            } else if (status.equalsIgnoreCase("yellow")) {
                cd_signal.setBackground(getResources().getDrawable(R.drawable.ic_round_yellow_circle_24dp));
            }
        }


        if (market_id.equalsIgnoreCase("com")) {
            if (status.equalsIgnoreCase("green")) {
                com_signal.setBackground(getResources().getDrawable(R.drawable.ic_round_green_circle_24dp));
            } else if (status.equalsIgnoreCase("red")) {
                com_signal.setBackground(getResources().getDrawable(R.drawable.ic_round_red_circle_24dp));
            } else if (status.equalsIgnoreCase("blue")) {
                com_signal.setBackground(getResources().getDrawable(R.drawable.ic_round_blue_circle_24dp));
            } else if (status.equalsIgnoreCase("pink")) {
                com_signal.setBackground(getResources().getDrawable(R.drawable.ic_round_pink_circle_24dp));
            } else if (status.equalsIgnoreCase("yellow")) {
                com_signal.setBackground(getResources().getDrawable(R.drawable.ic_round_yellow_circle_24dp));
            }
        }
    }
    }

    public void setNiftyDetail(OnClickListener onClickAction) {
        nifty_layout.setOnClickListener(onClickAction);
    }

    public void setActionbarChangenifty(String name, String vol,String change) {

        if (name.equalsIgnoreCase("nifty 50")) {
            name = "Nifty";
        }
        niftyNameText.setText(name);
        niftyvalue.setText(vol);
        if (change.startsWith("-")) {
            textColorNegative = R.color.dark_red_negative;
            niftyvalue.setTextColor(getResources().getColor(textColorNegative));
        } else {
            if (AccountDetails.getThemeFlag(getContext()).equalsIgnoreCase("white")) {
                textColorPositive = R.color.whitetheambuyColor;
            } else {
                textColorPositive = R.color.dark_green_positive;
            }
            niftyvalue.setTextColor(getResources().getColor(textColorPositive));
        }

    }

    public void setActionbarChangeSensex(String name, String vol,String change) {
        if (name.equalsIgnoreCase("nifty 50")) {
            name = "Nifty";
        }
        sensexNameText.setText(name);
        sensexvalue.setText(vol);
        if (change.startsWith("-")) {
            textColorNegative = R.color.dark_red_negative;
            sensexvalue.setTextColor(getResources().getColor(textColorNegative));
        } else {
            if (AccountDetails.getThemeFlag(getContext()).equalsIgnoreCase("white")) {
                textColorPositive = R.color.whitetheambuyColor;
            } else {
                textColorPositive = R.color.dark_green_positive;
            }
            sensexvalue.setTextColor(getResources().getColor(textColorPositive));
        }
    }


    public void updateActionbarNifty(String vol,String change) {
        niftyvalue.setText(vol);
        if (change.startsWith("-")) {

            textColorNegative = R.color.dark_red_negative;

            niftyvalue.setTextColor(getResources().getColor(textColorNegative));
        } else {

            if (AccountDetails.getThemeFlag(getContext()).equalsIgnoreCase("white")) {
                textColorPositive = R.color.whitetheambuyColor;

            } else {
                textColorPositive = R.color.dark_green_positive;

            }

            niftyvalue.setTextColor(getResources().getColor(textColorPositive));
        }


    }

    public void updateActionbarSensex(String vol,String change) {
        sensexvalue.setText(vol);
        if (change.startsWith("-")) {

            textColorNegative = R.color.dark_red_negative;

            sensexvalue.setTextColor(getResources().getColor(textColorNegative));
        } else {

            if (AccountDetails.getThemeFlag(getContext()).equalsIgnoreCase("white")) {
                textColorPositive = R.color.whitetheambuyColor;

            } else {
                textColorPositive = R.color.dark_green_positive;

            }

            sensexvalue.setTextColor(getResources().getColor(textColorPositive));
        }


    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setNiftyIcon(String color) {

        if (color.equalsIgnoreCase("green")) {
            niftyDetail.setBackground(getResources().getDrawable(R.drawable.nifty_shape_green));

            //sensexDetail.setBackground(getResources().getDrawable(R.drawable.nifty_shape_green));
            dhaanyaDetail.setBackground(getResources().getDrawable(R.drawable.nifty_shape_green));
            goldDetail.setBackground(getResources().getDrawable(R.drawable.nifty_shape_green));

        } else if (color.equalsIgnoreCase("red")) {
            niftyDetail.setBackground(getResources().getDrawable(R.drawable.nifty_shape));

            //sensexDetail.setBackground(getResources().getDrawable(R.drawable.nifty_shape));
            dhaanyaDetail.setBackground(getResources().getDrawable(R.drawable.nifty_shape));
            goldDetail.setBackground(getResources().getDrawable(R.drawable.nifty_shape));
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setSensexIcon(String color) {

        if (color.equalsIgnoreCase("green")) {
            sensexDetail.setBackground(getResources().getDrawable(R.drawable.nifty_shape_green));
        } else if (color.equalsIgnoreCase("red")) {
            sensexDetail.setBackground(getResources().getDrawable(R.drawable.nifty_shape));
        }
    }


    public void setSensexDetail(OnClickListener onClickAction) {
        sensex_layout.setOnClickListener(onClickAction);
    }

    public void setPopUpEq(OnClickListener onClickAction) {
        eq_signal_indicator.setOnClickListener(onClickAction);
    }

    public void setPopUpFno(OnClickListener onClickAction) {
        fno_signal_indicator.setOnClickListener(onClickAction);
    }

    public void setPopUpCd(OnClickListener onClickAction) {
        cd_signal_indicator.setOnClickListener(onClickAction);
    }

    public void setPopUpCom(OnClickListener onClickAction) {
        com_signal_indicator.setOnClickListener(onClickAction);
    }

    public void setHomeAction(OnClickListener onClickAction) {
        mHomeBtn.setOnClickListener(onClickAction);
        mHomeBtn.setVisibility(View.VISIBLE);
    }

    public void setSearchAction(OnClickListener onClickAction) {
        mSearchBtn.setOnClickListener(onClickAction);
        showSearchAction();
    }


    public void hideSearchAction() {
//        mSearchBtn.setVisibility(View.INVISIBLE);
    }

    public void showSearchAction() {
//        mSearchBtn.setVisibility(View.VISIBLE);

    }

    @SuppressLint("NewApi")
    public void setNiftyValues(String value, String change, String changePer, String name) {
        //value = StringStuff.commaDecorator(value);
        niftyVal.setText(value);

        if (!name.equalsIgnoreCase("")) {
            niftyText.setText(name);
        }
        String roundOffChangePer = String.format("%.2f", Double.parseDouble(changePer));
        String roundOffChange = String.format("%.2f", Double.parseDouble(change));
        niftyChange.setText(String.format("%s(%s%%)", change, roundOffChangePer));
        niftyChange.setTextColor(getColorForChange(changePer));
        niftyDetailPercentage.setBackground(getShapeForChange(changePer));

    }

    public void setNiftyClick(Boolean click) {

        nifty_layout.setClickable(click);
    }

    public void setSensexClick(Boolean click) {

        sensex_layout.setClickable(click);
    }


    @SuppressLint("NewApi")
    public void setSensexValues(String value, String change, String changePer, String name) {
        //value = StringStuff.commaDecorator(value);
        sensexVal.setText(value);

        if (!name.equalsIgnoreCase("")) {
            sensexText.setText(name);
        }
        String roundOffChangePer = String.format("%.2f", Double.parseDouble(changePer));
        String roundOffChange = String.format("%.2f", Double.parseDouble(change));
        sensexChange.setText(String.format("%s(%s%%)", change, roundOffChangePer));
        sensexChange.setTextColor(getColorForChange(changePer));
        sensexDetailPercent.setBackground(getShapeForChange(changePer));

    }

    public void setGoldValues(String value, String change, String changePer) {
        value = StringStuff.commaDecorator(value);
        goldVal.setText(value);
        String roundOffChangePer = String.format("%.2f", Double.parseDouble(changePer));
        String roundOffChange = String.format("%.2f", Double.parseDouble(change));
        goldChange.setText(String.format("%s(%s%%)", roundOffChange, roundOffChangePer));
        goldChange.setTextColor(getColorForChange(changePer));
    }


    private int getColorForChange(String change) {
        int color = 0;
        if (change.isEmpty() || change.equals("NA")) {
            color = getResources().getColor(R.color.action_bar_positive);
        } else {

            if (AccountDetails.getThemeFlag(getContext()).equalsIgnoreCase("white")) {
                float diff = Float.parseFloat(change.replace("%", ""));
                if (diff > 0)
                    if (AccountDetails.getThemeFlag(ctx).equalsIgnoreCase("white")) {
                        color = getResources().getColor(R.color.whitetheambuyColor);
                    } else {
                        color = getResources().getColor(R.color.green_textcolor);
                    }
                else if (diff < 0)
                    color = getResources().getColor(R.color.red_textcolor);
                else if (AccountDetails.getThemeFlag(ctx).equalsIgnoreCase("white")) {
                    color = getResources().getColor(R.color.whitetheambuyColor);
                } else {
                    color = getResources().getColor(R.color.green_textcolor);
                }
            } else if (AccountDetails.getThemeFlag(getContext()).equalsIgnoreCase("black")) {
                float diff = Float.parseFloat(change.replace("%", ""));
                if (diff > 0)
                    if (AccountDetails.getThemeFlag(ctx).equalsIgnoreCase("white")) {
                        color = getResources().getColor(R.color.whitetheambuyColor);
                    } else {
                        color = getResources().getColor(R.color.green_textcolor);
                    }
                else if (diff < 0) color = getResources().getColor(R.color.red_textcolor);
                else if (AccountDetails.getThemeFlag(ctx).equalsIgnoreCase("white")) {
                    color = getResources().getColor(R.color.whitetheambuyColor);
                } else {
                    color = getResources().getColor(R.color.green_textcolor);
                }
            }
        }
        return color;
    }

    private Drawable getShapeForChange(String change) {
        Drawable icon = null;
        if (change.isEmpty() || change.equals("NA")) {

            icon = getResources().getDrawable(R.drawable.nifty_shape_up_blue);
        } else {

            if (AccountDetails.getThemeFlag(getContext()).equalsIgnoreCase("white")) {
                float diff = Float.parseFloat(change.replace("%", ""));

                Log.e("GeekActionBar", "diff====>" + diff);

                if (diff > 0)
                    icon = getResources().getDrawable(R.drawable.nifty_shape_up_green);
                else if (diff < 0)
                    icon = getResources().getDrawable(R.drawable.nifty_shape_down_red);

                else icon = getResources().getDrawable(R.drawable.nifty_shape_up_green);

            } else if (AccountDetails.getThemeFlag(getContext()).equalsIgnoreCase("black")) {
                float diff = Float.parseFloat(change.replace("%", ""));
                if (diff > 0)
                    icon = getResources().getDrawable(R.drawable.nifty_shape_up_green);
                else if (diff < 0)
                    icon = getResources().getDrawable(R.drawable.nifty_shape_down_red);
                else
                    icon = getResources().getDrawable(R.drawable.nifty_shape_up_green);
            }
        }
        return icon;
    }

    public void setTitle(CharSequence title) {
        mTitleView.setText(title);
        //setMoreVisibility(title);
    }

    public String getNiftyTitleText() {
        return niftyText.getText().toString();
    }

    public String getSensexTitleText() {
        return sensexText.getText().toString();
    }

    public void setTitleVisibility(int state) {
        mTitleView.setVisibility(state);
    }

}
