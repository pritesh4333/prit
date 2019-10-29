package warehousedelivery.taaxgenie.in.resagitpvtltd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AppsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps);
        TextView apssid= (TextView)findViewById(R.id.apssid);
        TextView totalapp=(TextView)findViewById(R.id.totalapp);
        totalapp.setText("Total Apps:- "+getIntent().getIntExtra("TotalApps",0));
        apssid.setText(getIntent().getStringExtra("apps"));
    }
}
