package in.co.vyaparienterprise.ui.generic.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.ImageView;

import in.co.vyaparienterprise.R;

/**
 * Created by bekir on 04/01/2017.
 */

public class LoadingDialog extends Dialog {

    private Context context;
    private Animation shakeAnim;
    private ImageView progress;
    private int counter = 0;
    private CountDownTimer countDownTimer;

    public LoadingDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_loading);

        configComponents();
    }

    private void configComponents() {
        /*shakeAnim = AnimationUtils.loadAnimation(context, R.anim.image_shake);
        progress = findViewById(R.id.imageViewLoading);
        imageShake.startAnimation(shakeAnim);*/
        progress = findViewById(R.id.imageViewLoading);

        countDownTimer = new CountDownTimer(300000, 250) {
            @Override
            public void onTick(long l) {
                if (counter % 2 == 0) {
                    progress.setImageResource(R.drawable.ico_loa1);
                } else {
                    progress.setImageResource(R.drawable.ico_loa2);
                }
                counter++;
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        countDownTimer.cancel();
    }

}