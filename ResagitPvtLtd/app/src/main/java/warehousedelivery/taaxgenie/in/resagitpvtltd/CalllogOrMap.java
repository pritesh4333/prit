package warehousedelivery.taaxgenie.in.resagitpvtltd;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.QuickContactBadge;
import android.widget.Toast;

public class CalllogOrMap extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calllog_or_map);

        Button Maps=(Button)findViewById(R.id.Maps) ;
        Button Call=(Button)findViewById(R.id.Call) ;
        Button Bhavisha=(Button)findViewById(R.id.Bhavisha);


        Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CalllogOrMap.this,CallRecords.class);
                startActivity(i);
            }
            });

        Maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CalllogOrMap.this,MapsActivity.class);
                startActivity(i);
            }
        });
        Bhavisha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CalllogOrMap.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
}
