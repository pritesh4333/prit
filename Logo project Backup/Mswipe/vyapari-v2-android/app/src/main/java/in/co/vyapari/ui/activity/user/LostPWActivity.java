package in.co.vyapari.ui.activity.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import in.co.vyapari.R;
import in.co.vyapari.middleware.ServiceCreator;
import in.co.vyapari.middleware.ServiceRequest;
import in.co.vyapari.model.response.BaseModel;
import in.co.vyapari.ui.base.SohoActivity;
import in.co.vyapari.util.Utils;
import in.co.vyapari.util.ValidateUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LostPWActivity extends SohoActivity {

    @BindView(R.id.lost_pw_input_email)
    EditText emailEditText;
    @BindView(R.id.lost_pw_progressbar)
    ProgressBar progressBar;
    @BindView(R.id.lost_pw_submit_button)
    Button sendButton;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        setContentView(R.layout.activity_lost_pw);
        ButterKnife.bind(this);
        mContext = this;

        emailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    checkEmail();
                }
            }
        });
    }

    @OnClick(R.id.lost_pw_submit_button)
    void lostPWClick() {
        Utils.hideKeyboard(this);
        String email = emailEditText.getText().toString().trim();
        if (!checkEmail()) {
            return;
        }
        sendButton.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        lostPWServiceCall(email);
    }

    private void lostPWServiceCall(String email) {
        ServiceRequest apiService = ServiceCreator.getClientNonToken().create(ServiceRequest.class);
        Call<BaseModel<String>> call = apiService.lostPW(email);
        call.enqueue(new Callback<BaseModel<String>>() {
            @Override
            public void onResponse(Call<BaseModel<String>> call, Response<BaseModel<String>> response) {
                if (response.isSuccessful()) {
                    String message = response.body().getMessage();
                    if (!response.body().isError()) {
                        Toasty.info(mContext, message).show();
                    } else {
                        Toasty.error(mContext, message).show();
                    }
                } else {
                    Toasty.error(mContext, getString(R.string.error)).show();
                }

                finish();
            }

            @Override
            public void onFailure(Call<BaseModel<String>> call, Throwable ignored) {
                Toasty.error(mContext, getString(R.string.error)).show();
            }
        });
    }


    private boolean checkEmail() {
        String email = emailEditText.getText().toString();
        return ValidateUtil.validateEmail(mContext, email);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(mContext, LoginActivity.class));
        super.onBackPressed();
    }
}