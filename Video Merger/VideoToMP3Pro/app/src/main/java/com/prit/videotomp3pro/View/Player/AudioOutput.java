package com.prit.videotomp3pro.View.Player;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.prit.videotomp3pro.R;
//import com.prit.videotomp3pro.View.AudioOutputfolder.visualizer.SquareBarVisualizerActivity;

public class AudioOutput extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_output);
    }
    public void line(View view) {
        startActivity(LineVisualizerActivity.class);
    }

    public void bar(View view) {
        startActivity(BarVisualizerActivity.class);
    }

    public void circle(View view) {
        startActivity(CircleVisualizerActivity.class);
    }

    public void circleBar(View view) {
        startActivity(CircleBarVisualizerActivity.class);
    }

    public void lineBar(View view) {
        startActivity(LineBarVisualizerActivity.class);
    }

    public void service(View view) {
        startActivity(ServiceExampleActivity.class);
    }

    //public void square(View view) {
      //  startActivity(SquareBarVisualizerActivity.class);
    //}

    public void startActivity(Class clazz) {
        startActivity(new Intent(this, clazz));
    }
}
