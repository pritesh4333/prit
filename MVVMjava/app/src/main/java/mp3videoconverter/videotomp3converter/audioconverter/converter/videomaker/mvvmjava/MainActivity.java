package mp3videoconverter.videotomp3converter.audioconverter.converter.videomaker.mvvmjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import mp3videoconverter.videotomp3converter.audioconverter.converter.videomaker.mvvmjava.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MainActivityviewmodel viewmodel=new MainActivityviewmodel();
        activityMainBinding.setViewModel(viewmodel);
        activityMainBinding.executePendingBindings();
        activityMainBinding.setLifecycleOwner(this);

        viewmodel.showtext();

    }
}