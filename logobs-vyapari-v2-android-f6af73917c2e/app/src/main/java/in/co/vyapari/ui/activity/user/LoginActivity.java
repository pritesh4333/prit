package in.co.vyapari.ui.activity.user;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import in.co.vyapari.R;
import in.co.vyapari.constant.Constants;
import in.co.vyapari.constant.MobileConstants;
import in.co.vyapari.middleware.listener.ServiceCall;
import in.co.vyapari.middleware.service.UserService;
import in.co.vyapari.model.DefaultAccount;
import in.co.vyapari.model.Login;
import in.co.vyapari.model.response.BaseModel;
import in.co.vyapari.model.response.dto.CompanyDTO;
import in.co.vyapari.model.response.dto.LoginDTO;
import in.co.vyapari.ui.activity.app.MainActivity;
import in.co.vyapari.ui.base.SohoActivity;
import in.co.vyapari.util.DataUtil;
import in.co.vyapari.util.Utils;

public class LoginActivity extends SohoActivity {

    @BindView(R.id.loginpage_input_email)
    EditText emailEditText;
    @BindView(R.id.loginpage_input_password)
    EditText passwordEditText;
    @BindView(R.id.loginpage_progressbar)
    ProgressBar progressBar;
    @BindView(R.id.loginpage_do_login)
    Button loginButton;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mContext = this;



        Login login = (Login) Utils.getObjectSharedPreferencesValue(mContext, Constants.LOGIN_INFO, Login.class);

        if (login != null) {
            emailEditText.setText(login.getUserName());
            passwordEditText.setText(login.getPassword());
        }

        emailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    checkEmail();
                }
            }
        });

        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    checkPassword();
                }
            }
        });
    }

    @OnClick(R.id.loginpage_do_login)
    void doLoginClick() {
        Utils.hideKeyboard(this);
        final String email = emailEditText.getText().toString().trim();
        final String password = passwordEditText.getText().toString();
        if (!checkEmail() || !checkPassword()) {
            return;
        }

        MobileConstants.UserName = email;
        Login login = new Login(email, password);
        doLoginServiceCall(login);
    }

    @OnClick(R.id.loginpage_register)
    void registerClick() {
        startActivity(new Intent(mContext, RegisterActivity.class));
        finish();
        //startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(Constants.BASE_SIGNUP_URL)));
    }

    @OnClick(R.id.loginpage_forget_password)
    void lostPWClick() {
        //startActivity(new Intent(mContext, LostPWActivity.class));
        startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(Constants.BASE_FORGET_PASS_URL)));
    }

    private void doLoginServiceCall(final Login login) {
        MobileConstants.isLoginService = true;
        loginButton.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        UserService.doLogin(login, new ServiceCall<BaseModel<LoginDTO>>() {
            @Override
            public void onResponse(boolean isPreloaded, BaseModel<LoginDTO> response) {
                MobileConstants.isLoginService = false;
                if (!response.isError()) {
                    if (response.getData() != null) {
                        LoginDTO loginDTO = response.getData();
                        if (loginDTO.getCompany() == null) {
                            loginDTO.setCompany(new CompanyDTO());
                        }

                        if (loginDTO != null) {
                            DefaultAccount defaultAccount = (DefaultAccount) Utils.getObjectSharedPreferencesValue(mContext, Constants.DEFAULT_ACCOUNT_INFO, DefaultAccount.class);
                            DefaultAccount defaultAccountnew = new DefaultAccount();
                            if (defaultAccount != null) {
                                defaultAccountnew.setCashcode(defaultAccount.getCashcode());
                                defaultAccountnew.setBankcode(defaultAccount.getBankcode());
                                defaultAccountnew.setCardcode(defaultAccount.getCardcode());
                                defaultAccountnew.setCashid(defaultAccount.getCashid());
                                defaultAccountnew.setBankid(defaultAccount.getBankid());
                                defaultAccountnew.setCardid(defaultAccount.getCardid());
                                defaultAccountnew.setOthercode(defaultAccount.getOthercode());
                                defaultAccountnew.setUsername(login.getUserName());
                            }else{
                                defaultAccountnew.setUsername(login.getUserName());
                            }
                            Utils.setObjectSharedPreferencesValue(mContext, defaultAccountnew, Constants.DEFAULT_ACCOUNT_INFO);
                            Utils.setObjectSharedPreferencesValue(mContext, login, Constants.LOGIN_INFO);
                            Utils.setObjectSharedPreferencesValue(mContext, loginDTO, Constants.LOGIN_RESPONSE_INFO);
                            DataUtil.post(loginDTO);
                            MobileConstants.accessToken = loginDTO.getAccessToken();

                            if (loginDTO.isFirstLogin()) {
                                startActivity(new Intent(LoginActivity.this, FirstLoginActivity.class));
                            } else {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            }
                            finish();
                        } else {
                            Toasty.error(mContext, getString(R.string.error)).show();
                            resetViews();
                        }
                    } else {
                        Toasty.error(mContext, response.getErrorDescription()).show();
                        resetViews();
                    }
                } else {
                    Toasty.error(mContext, response.getErrorDescription()).show();
                    resetViews();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable ignored) {
                MobileConstants.isLoginService = false;
                Toasty.error(mContext, getString(R.string.error)).show();
                resetViews();
            }
        });
    }


    private boolean checkEmail() {
        //String email = emailEditText.getText().toString();
        //return ValidateUtil.validateEmail(mContext, email);
        return true;
    }

    private boolean checkPassword() {
        //String password = passwordEditText.getText().toString();
        //return ValidateUtil.validatePassword(mContext, password);
        return true;
    }

    private void resetViews() {
        progressBar.setVisibility(View.GONE);
        loginButton.setVisibility(View.VISIBLE);
    }

}