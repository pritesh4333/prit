package com.acumengroup.mobile.chartiqscreen;

import android.content.pm.ActivityInfo;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.mobile.R;

public class DrawActivity extends AppCompatActivity {

    ListView listView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_draw);
//        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        listView = (ListView) findViewById(R.id.listview);
        if(AccountDetails.getThemeFlag(this).equalsIgnoreCase("white")){
            listView.setBackgroundColor(getColor(AccountDetails.backgroundColor));
        }
        if(!AccountDetails.getThemeFlag(this).equalsIgnoreCase("white")){
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.drawing_tools, R.layout.custom_background);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent result = new Intent();
                result.putExtra("drawingTool", String.valueOf(adapter.getItem(position)));
                setResult(RESULT_OK, result);
                finish();
            }
        });
        }else{

            final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.drawing_tools, R.layout.custom_backgroun_white);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent result = new Intent();
                    result.putExtra("drawingTool", String.valueOf(adapter.getItem(position)));
                    setResult(RESULT_OK, result);
                    finish();
                }
            });

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void clearAllDrawings(View view) {
        Intent result = new Intent();
        result.putExtra("clearAllDrawings", true);
        setResult(RESULT_OK, result);
        finish();
    }
}
