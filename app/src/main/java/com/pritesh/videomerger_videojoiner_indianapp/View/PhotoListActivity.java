package com.prit.videocompressorpro.View;

import android.os.Bundle;

import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.prit.videocompressorpro.R;
import com.prit.videocompressorpro.ViewModel.AdapterSelectedPhoto;

/**
 * Created by deepshikha on 20/3/17.
 */

public class PhotoListActivity extends AppCompatActivity {
    int int_position;
    private GridView gridView;
    AdapterSelectedPhoto adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = (GridView)findViewById(R.id.gv_folder);
        int_position = getIntent().getIntExtra("value", 0);
        adapter = new AdapterSelectedPhoto(this,MainActivity.al_images,int_position);
        gridView.setAdapter(adapter);
    }
}