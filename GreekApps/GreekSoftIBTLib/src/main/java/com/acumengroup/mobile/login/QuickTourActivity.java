package com.acumengroup.mobile.login;

import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.acumengroup.mobile.R;

/**
 * Created by user on 06-Oct-17.
 */

public class QuickTourActivity extends AppIntro {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Typeface font = Typeface.createFromAsset(this.getResources().getAssets(), "DaxOT.ttf");


        // Note here that we DO NOT use setContentView();

        // Add your slide fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.

        //addSlide(QuickTourFragment1.newInstance(R.layout.quickintro1));
         addSlide(QuickTourFragment1.newInstance(R.layout.quickintro3));
        addSlide(QuickTourFragment1.newInstance(R.layout.quickintro2));
      /*  addSlide(QuickTourFragment1.newInstance(R.layout.quicktour4));
        addSlide(QuickTourFragment1.newInstance(R.layout.quicktour5));
        addSlide(QuickTourFragment1.newInstance(R.layout.quicktour6));
        addSlide(QuickTourFragment1.newInstance(R.layout.quicktour7));*/

        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest.
        //addSlide(AppIntroFragment.newInstance(title, description, image, backgroundColor));

        // OPTIONAL METHODS
        // Override bar/separator color.
        /*setBarColor(Color.parseColor("#3F51B5"));
        setSeparatorColor(Color.parseColor("#2196F3"));*/

      /*  TextView title = (TextView)findViewById(R.id.txt_quicktour);
        title.setTypeface(font);*/

        // Hide Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(true);



        setColorSkipButton(getResources().getColor(R.color.light_orange));
        setSkipTextTypeface(getResources().getString(R.string.GREEK_TEXTVIEW_FONT));



//        setColorSkipButton.setTypeface(font,Typeface.NORMAL);

        setColorDoneText(getResources().getColor(R.color.light_orange));
        setDoneTextTypeface(getResources().getString(R.string.GREEK_TEXTVIEW_FONT));

        setIndicatorColor(getResources().getColor(R.color.light_orange), getResources().getColor(R.color.gray_border));

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
        setVibrate(true);
        setVibrateIntensity(30);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
        // Do something when users tap on Skip button.
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
        // Do something when users tap on Done button.
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}

