package in.co.vyapari.ui.activity.management;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import in.co.vyapari.R;
import in.co.vyapari.ui.base.SohoActivity;

public class SettingActivity extends SohoActivity {
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        mContext = this;

        setDetailToolbarConfig(R.string.company_info, R.string.company_name);
    }
}
