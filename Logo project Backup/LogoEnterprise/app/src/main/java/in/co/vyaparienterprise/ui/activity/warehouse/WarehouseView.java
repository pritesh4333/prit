package in.co.vyaparienterprise.ui.activity.warehouse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.ButterKnife;
import in.co.vyaparienterprise.R;
import in.co.vyaparienterprise.ui.base.SohoActivity;

public class WarehouseView extends SohoActivity {
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse_view);
        setDetailToolbarConfig(R.string.warehouse, R.drawable.ico_mini_ar_ap, R.string.warehouse);
        ButterKnife.bind(this);
        mContext = this;
        TextView editwarehouse=(TextView)findViewById(R.id.editwarehouse);
        EditText toolbar_name_et=(EditText)findViewById(R.id.toolbar_name_et);
        toolbar_name_et.setFocusable(false);
        editwarehouse.setVisibility(View.VISIBLE);
        editwarehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(WarehouseView.this,WarehouseCreate.class);
                startActivity(i);
            }
        });

    }
}
