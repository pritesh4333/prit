package in.co.vyaparienterprise.ui.activity.warehouse;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import in.co.vyaparienterprise.R;
import in.co.vyaparienterprise.ui.base.SohoActivity;

public class WarehouseCreate extends SohoActivity {
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse_create);
        setDetailToolbarConfig(R.string.warehouse, R.drawable.ico_mini_ar_ap, R.string.warehouse);
        ButterKnife.bind(this);
        mContext = this;
    }
}
