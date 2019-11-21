package in.co.vyaparienterprise.ui.generic.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.internal.MDButton;

import in.co.vyaparienterprise.R;
import in.co.vyaparienterprise.constant.Constants;
import in.co.vyaparienterprise.model.KeyValue;
import in.co.vyaparienterprise.ui.generic.MyTextWatcher;
import in.co.vyaparienterprise.ui.generic.currencyedittext.CurrencyEditText;
import in.co.vyaparienterprise.ui.listener.ActionListener;
import in.co.vyaparienterprise.util.Utils;

/**
 * Created by bekirdursun on 27.12.2017.
 */

public class DiscountDialog {

    private Context context;
    private CurrencyEditText discountDialogAmountEt;
    private LinearLayout discountDialogPercentLL;
    private AppCompatSeekBar discountDialogPercentSB;
    private ImageView downCountIV;
    private ImageView upCountIV;
    private RadioButton amountRB;
    private RadioButton percentRB;
    private int percentValue = 20;
    private KeyValue keyValue;

    public DiscountDialog(Context context, ActionListener<KeyValue> actionListener) {
        this.context = context;
        discountDialog(context, null, actionListener);
    }

    public DiscountDialog(Context context, KeyValue discount, ActionListener<KeyValue> actionListener) {
        this.context = context;
        discountDialog(context, discount, actionListener);
    }

    public void discountDialog(final Context context, final KeyValue discount, final ActionListener<KeyValue> actionListener) {
        keyValue = new KeyValue();
        keyValue.setKey(Constants.AMOUNT);
        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .title(context.getString(R.string.discount))
                .customView(R.layout.dialog_customview, true)
                .positiveText(R.string.save)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        actionListener.results(keyValue);
                    }
                })
                .negativeText(R.string.cancel)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (discount != null) {
                            actionListener.results(null);
                        }
                    }
                })
                .build();

        MDButton negativeAction = dialog.getActionButton(DialogAction.NEGATIVE);
        if (discount == null) {
            negativeAction.setText(R.string.cancel);
        } else {
            negativeAction.setText(R.string.delete_discount);
        }

        amountRB = dialog.getCustomView().findViewById(R.id.amount);
        percentRB = dialog.getCustomView().findViewById(R.id.percent);
        discountDialogAmountEt = dialog.getCustomView().findViewById(R.id.amount_et);
        discountDialogPercentLL = dialog.getCustomView().findViewById(R.id.percent_ll);
        discountDialogPercentSB = dialog.getCustomView().findViewById(R.id.percent_sb);
        downCountIV = dialog.getCustomView().findViewById(R.id.down_count);
        upCountIV = dialog.getCustomView().findViewById(R.id.up_count);

        if (discount != null) {
            if (Utils.equalsKeyValue(discount, Constants.AMOUNT)) {
                amountRB.setChecked(true);
                percentRB.setChecked(false);
                discountDialogAmountEt.setVisibility(View.VISIBLE);
                discountDialogPercentLL.setVisibility(View.GONE);
                discountDialogAmountEt.setValue(Double.valueOf(discount.getValue()));
            } else if (Utils.equalsKeyValue(discount, Constants.PERCENT)) {
                amountRB.setChecked(false);
                percentRB.setChecked(true);
                double tempPercentValue = Double.valueOf(discount.getValue());
                percentValue = (int) tempPercentValue;
                discountDialogAmountEt.setVisibility(View.GONE);
                discountDialogPercentLL.setVisibility(View.VISIBLE);
                percentRB.setText(String.valueOf(context.getString(R.string.percent) + " (%" + percentValue + ")"));
                discountDialogPercentSB.setProgress(percentValue);
            }
            if (discount.getKey() == null) {
                keyValue.setKey(Constants.AMOUNT);
            } else {
                keyValue.setKey(discount.getKey());
            }
            keyValue.setValue(String.valueOf(discount.getValue()));
        }

        discountDialogAmountEt.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void afterTextChanged() {
                keyValue.setValue(String.valueOf(discountDialogAmountEt.getDouble()));
            }
        });

        discountDialogPercentSB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                percentRB.setText(String.valueOf(context.getString(R.string.percent) + " (%" + progress + ")"));
                percentValue = progress;
                keyValue.setValue(String.valueOf(percentValue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        upCountIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressCalculate(true);
            }
        });

        downCountIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressCalculate(false);
            }
        });

        RadioGroup radioGroup = dialog.getCustomView().findViewById(R.id.options);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int position) {
                if (position == R.id.amount) {
                    keyValue.setKey(Constants.AMOUNT);
                    discountDialogAmountEt.setVisibility(View.VISIBLE);
                    discountDialogPercentLL.setVisibility(View.GONE);
                } else {
                    keyValue.setKey(Constants.PERCENT);
                    discountDialogAmountEt.setVisibility(View.GONE);
                    discountDialogPercentLL.setVisibility(View.VISIBLE);
                    percentRB.setText(String.valueOf(context.getString(R.string.percent) + " (%" + percentValue + ")"));
                }
            }
        });

        dialog.show();
    }

    private void progressCalculate(boolean isUp) {
        int progress = discountDialogPercentSB.getProgress();
        if (isUp) {
            progress++;
        } else {
            progress--;
            progress = progress < 0 ? 0 : progress;
        }
        discountDialogPercentSB.setProgress(progress);
        percentRB.setText(String.valueOf(context.getString(R.string.percent) + " (%" + progress + ")"));
        percentValue = progress;
        keyValue.setValue(String.valueOf(percentValue));
    }
}
