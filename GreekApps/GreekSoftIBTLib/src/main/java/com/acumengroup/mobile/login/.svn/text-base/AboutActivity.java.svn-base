package com.acumengroup.mobile.login;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.constants.LabelConfig;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.textview.GreekTextView;

/**
 * Created by user on 28-Nov-16.
 */

public class AboutActivity extends AppCompatActivity implements LabelConfig {
    GreekTextView version_no, version_date_time,about_us,version_no_txt;
    LinearLayout viewBackground, parent_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        about_us = findViewById(R.id.about_us);
        version_no_txt = findViewById(R.id.version_no_txt);

        parent_layout = findViewById(R.id.parent_layout);
        viewBackground = findViewById(R.id.viewBg);
         String abtstring=getString(R.string.about);

        String abt_messae=abtstring.replaceAll("APPNAME", GreekBaseActivity.GREEK);
        version_no_txt.setText(abt_messae);

        setTheme();


        /*version_no = (GreekTextView) findViewById(R.id.version_no_txt);
        version_date_time = (GreekTextView) findViewById(R.id.version_date_time);
        viewBackground = (LinearLayout) findViewById(R.id.viewBg);
        version_no.setText("Version No. : " + VERSION_NO);

        if (AccountDetails.getThemeFlag(getApplicationContext()).equalsIgnoreCase("white")) {
            viewBackground.setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
            version_no.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            version_date_time.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

        } else {
            viewBackground.setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
            version_no.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            version_date_time.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        }

        try {
            String timestamp;
            Date buildDate = new Date(BuildConfig.TIMESTAMP);
            SimpleDateFormat s = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
            timestamp = s.format(buildDate);
            version_date_time.setText("Build Time : " + timestamp);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    private void setTheme() {
        if (AccountDetails.getThemeFlag(this).equalsIgnoreCase("white")) {
            version_no_txt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            about_us.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));

            parent_layout.setBackgroundColor(getResources().getColor(R.color.white));
            viewBackground.setBackgroundColor(getResources().getColor(R.color.white));
        }
    }
}
