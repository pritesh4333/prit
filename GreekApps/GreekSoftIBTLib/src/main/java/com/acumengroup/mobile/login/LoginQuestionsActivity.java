package com.acumengroup.mobile.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.provider.Settings;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.GreekUIServiceHandler;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.constants.GreekConstants;
import com.acumengroup.greekmain.core.market.OrderStreamingController;
import com.acumengroup.greekmain.core.market.StreamingController;
import com.acumengroup.greekmain.core.model.GCM.ReceiveNotifyInformationResponse;
import com.acumengroup.greekmain.core.model.GCM.SendNotifyInformationRequest;
import com.acumengroup.greekmain.core.model.userloginvalidation.QuestionListManager;
import com.acumengroup.greekmain.core.model.userloginvalidation.Questions;
import com.acumengroup.greekmain.core.model.userloginvalidation.SendLoginAnswerValidateRequest;
import com.acumengroup.greekmain.core.model.userloginvalidation.SendLoginAnswerValidateResponse;
import com.acumengroup.greekmain.core.model.userloginvalidation.SendLoginQuestionAnswerRequest;
import com.acumengroup.greekmain.core.model.userloginvalidation.SendLoginQuestionAnswerResponse;
import com.acumengroup.greekmain.core.model.userloginvalidation.sendForgotAnswersRequest;
import com.acumengroup.greekmain.core.model.userloginvalidation.sendForgotAnswersResponse;
import com.acumengroup.greekmain.core.network.OrderStreamingAuthResponse;
import com.acumengroup.greekmain.core.network.ServiceRequest;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.service.HeartBeatService;
import com.acumengroup.mobile.service.MyService;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.edittext.GreekEditText;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.Util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;

/**
 * Created by sushant.patil on 3/22/2016.
 */
public class LoginQuestionsActivity extends AppCompatActivity implements GreekUIServiceHandler, GreekConstants {
    ListView row_list;
    CustomAdapter commonAdapter;
    Button submit, cancel;
    private LinearLayout progressLayout;
    private ServiceResponseHandler serviceResponseHandler;
    ArrayList<String> questionIdList = new ArrayList<>();
    StreamingController streamingController;
    OrderStreamingController orderStreamingController;
    int checkCheckbox = 0;
    String userFlag, sessionId, brokerId, userpass, gcmDeviceToken, userCode, SessionId, deviceId, gcid, userType;
    LinkedHashMap<String, String> hm = new LinkedHashMap<>();
    boolean answerlength = true;
    GreekBaseActivity greekBaseActivity;
    private boolean deleteAnswers = false;
    GreekTextView forgotAnswers;
    RelativeLayout loginQuestionBgLabel;
    GreekTextView titleLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_login_question);

        serviceResponseHandler = new ServiceResponseHandler(this, this);
        greekBaseActivity = new GreekBaseActivity();
        streamingController = new StreamingController();
        orderStreamingController = new OrderStreamingController();
        row_list = findViewById(R.id.listView_questions);
        gcmDeviceToken = Util.getPrefs(getApplicationContext()).getString("GCMToken", "");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        row_list.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                return true;
            }
        });

        forgotAnswers = findViewById(R.id.forgot_answers);
        submit = findViewById(R.id.okBtnAns);
        cancel = findViewById(R.id.cancelBtnAns);
        progressLayout = findViewById(R.id.customProgress);
        setupView();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_list.clearFocus();
                ArrayList<QuestionListManager> questionDetail = new ArrayList<>();
                if (userFlag.equals("0")) {
                    if (checkCheckbox == 5) {
                        answerlength = true;
                        for (int i = 0; i < questionIdList.size(); i++) {
                            if (hm.get(questionIdList.get(i)) != null) {
                                QuestionListManager questionListManager = new QuestionListManager();
                                questionListManager.setQuestion_id(questionIdList.get(i));
                                questionListManager.setAnswer(hm.get(questionIdList.get(i)));
                                questionDetail.add(questionListManager);
                            } else {
                                Toast.makeText(getApplicationContext(), "Answer should not be Empty", Toast.LENGTH_LONG).show();
                                break;
                            }
                            if (questionDetail.get(i).getAnswer().length() < 3) {
                                answerlength = false;
                            }
                        }
                        if (questionDetail.size() != 0 && questionDetail.size() == 5) {
                            if (answerlength) {
                                SendLoginQuestionAnswerRequest.sendRequest(userCode, sessionId, "0", questionDetail, LoginQuestionsActivity.this, serviceResponseHandler);   //0=mobile,1= webClient
                                submit.setEnabled(false);
                            } else {
                                Toast.makeText(getApplicationContext(), "Answer should between 3 to 12 characters.", Toast.LENGTH_LONG).show();
                            }
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Exact 5 Questions to be selected.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    if (checkCheckbox == 2) {
                        for (int i = 0; i < questionIdList.size(); i++) {
                            if (hm.get(questionIdList.get(i)) != null) {
                                QuestionListManager questionListManager = new QuestionListManager();
                                questionListManager.setQuestion_id(questionIdList.get(i));
                                questionListManager.setAnswer(hm.get(questionIdList.get(i)));
                                questionDetail.add(questionListManager);
                            } else {
                                Toast.makeText(getApplicationContext(), "Answer should not be Empty", Toast.LENGTH_LONG).show();
                                break;
                            }
                        }
                        if (questionDetail.size() != 0 && questionDetail.size() == 2) {
                            progressLayout.setVisibility(View.VISIBLE);

                            SendLoginAnswerValidateRequest.sendRequest(userCode, sessionId, "0", questionDetail, LoginQuestionsActivity.this, serviceResponseHandler);
                            submit.setEnabled(false);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Exact 2 Questions to be selected.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GreekBaseActivity.USER_TYPE = GreekBaseActivity.USER.OPENUSER;
                AccountDetails.setLogin_user_type("openuser");
                startActivity(new Intent(LoginQuestionsActivity.this, LoginActivity.class));
                finish();

            }
        });

        forgotAnswers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendForgotAnswersRequest.sendRequest(userCode, gcid, LoginQuestionsActivity.this, serviceResponseHandler);
            }
        });
    }

    private void setupView() {
        questionIdList.clear();
        Bundle bundleObject = getIntent().getExtras();
        Intent i = getIntent();
        userFlag = i.getStringExtra("flag");
        sessionId = i.getStringExtra("sessionId");
        brokerId = i.getStringExtra("brokerId");
        userpass = i.getStringExtra(("userpass"));
        userCode = i.getStringExtra(("usercode"));
        gcid = i.getStringExtra("gcid");

        if (i.getStringExtra("userType").equalsIgnoreCase("2")) {
            userType = "CUSTOMER";
        } else if (i.getStringExtra("userType").equalsIgnoreCase("1")) {
            userType = "MFCUSTOMER";
        } else if (i.getStringExtra("userType").equalsIgnoreCase("0")) {
            userType = "IBTCUSTOMER";
        }

        List<Questions> listObject = (List<Questions>) bundleObject.getSerializable("response");
        if (listObject.size() == 7) {
            forgotAnswers.setVisibility(View.GONE);
        }
        if (listObject.size() == 2) {
            forgotAnswers.setVisibility(View.VISIBLE);
        }
        commonAdapter = new CustomAdapter(getApplicationContext(), listObject);
        row_list.setAdapter(commonAdapter);
        loginQuestionBgLabel = findViewById(R.id.loginQuestionBg);
        titleLabel = findViewById(R.id.textView6);
        if (AccountDetails.getThemeFlag(getApplicationContext()).equalsIgnoreCase("white")) {
            AccountDetails.textColorDropdown = R.color.textColorCustomWhite;
            loginQuestionBgLabel.setBackgroundColor(getResources().getColor(R.color.backgroundColorWhite));
            titleLabel.setBackgroundColor(getResources().getColor(R.color.backgroundColorWhite));
            titleLabel.setTextColor(getResources().getColor(R.color.dividerColorWhite));
            forgotAnswers.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        } else if (AccountDetails.getThemeFlag(getApplicationContext()).equalsIgnoreCase("black")) {
            AccountDetails.textColorDropdown = R.color.textColorCustom;
        }

    }


    public class CustomAdapter extends BaseAdapter {
        private final Context mContext;
        List<Questions> questionList = new ArrayList<>();


        public CustomAdapter(Context context, List<Questions> questionList) {
            this.mContext = context;
            this.questionList = questionList;
        }

        public void clear() {
            questionList.clear();
        }

        @Override
        public int getCount() {
            return questionList.size();
        }

        @Override
        public Object getItem(int position) {
            return questionList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final Holder holder;
            if (convertView == null) {
                holder = new Holder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.row_login_question, null);
                holder.row_list_question_layout = convertView.findViewById(R.id.row_list_question);
                holder.tvQuestion = convertView.findViewById(R.id.question_text);
                holder.edAnswer = convertView.findViewById(R.id.answer_text);

                //holder.edAnswer.setImeOptions(EditorInfo.IME_ACTION_DONE);
                holder.chQuestionCheck = convertView.findViewById(R.id.question_check);

                if (AccountDetails.getThemeFlag(getApplicationContext()).equalsIgnoreCase("white")) {
                    holder.chQuestionCheck.setButtonTintList(ContextCompat.getColorStateList(LoginQuestionsActivity.this, R.color.checkbox_tint));
                    holder.tvQuestion.setTextColor(getResources().getColor(R.color.dividerColorWhite));
                    holder.edAnswer.setTextColor(getResources().getColor(R.color.dividerColorWhite));
                    holder.edAnswer.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.login_text_color)));
                }

                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            final Questions model = questionList.get(position);
            if (questionList.size() == 2) {
                holder.chQuestionCheck.setChecked(true);
                holder.chQuestionCheck.setEnabled(false);
                holder.edAnswer.setEnabled(true);
                holder.edAnswer.setFocusable(true);
                if (position == 0)
                    holder.edAnswer.requestFocus();
                checkCheckbox = 2;
                if (!questionIdList.contains(model.getQuestion_id()))
                    questionIdList.add(model.getQuestion_id());

            }
            holder.tvQuestion.setText(model.getQuestion());

            if (deleteAnswers) {
                holder.edAnswer.setText("");
            }
            holder.row_list_question_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (questionList.size() == 2) {
                        holder.edAnswer.setEnabled(true);
                        holder.edAnswer.setFocusable(true);
                        holder.edAnswer.requestFocus();
                    } else {
                        if (holder.chQuestionCheck.isChecked()) {
                            holder.chQuestionCheck.setChecked(false);
                            holder.edAnswer.setEnabled(false);

                        } else {
                            holder.chQuestionCheck.setChecked(true);
                            holder.edAnswer.setEnabled(true);
                            holder.edAnswer.setFocusable(true);
                            holder.edAnswer.requestFocus();
                            holder.edAnswer.setText("");

                        }
                    }
                }
            });
            holder.chQuestionCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        holder.edAnswer.setEnabled(true);
                        holder.edAnswer.setFocusable(true);
                        holder.edAnswer.requestFocus();
                        if (!questionIdList.contains(model.getQuestion_id())) {
                            questionIdList.add(model.getQuestion_id());
                            checkCheckbox++;
                        }

                    } else {
                        holder.edAnswer.setEnabled(false);
                        if (questionIdList.contains(model.getQuestion_id())) {
                            questionIdList.remove(model.getQuestion_id());
                            checkCheckbox--;
                        }
                        holder.edAnswer.setText("");


                    }
                }
            });

            holder.edAnswer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        if (!holder.edAnswer.getText().toString().trim().equals("")) {
                            hm.put(model.getQuestion_id(), holder.edAnswer.getText().toString());

                        } else {
                            hm.remove(model.getQuestion_id());
                        }
                    }
                }
            });

            return convertView;
        }

        public class Holder {
            GreekTextView tvQuestion;
            GreekEditText edAnswer;
            CheckBox chQuestionCheck;
            LinearLayout row_list_question_layout;
        }
    }

    @Override
    public void process(Object response) {

    }

    @Override
    public void handleResponse(Object response) {

        JSONResponse jsonResponse = (JSONResponse) response;
        if ("Login".equals(jsonResponse.getServiceGroup()) && "jsetQuestionBank".equals(jsonResponse.getServiceName())) {
            SendLoginQuestionAnswerResponse sendLoginQuestionAnswerResponse;

            try {
                progressLayout.setVisibility(View.GONE);
                sendLoginQuestionAnswerResponse = (SendLoginQuestionAnswerResponse) jsonResponse.getResponse();
                String errorCode = sendLoginQuestionAnswerResponse.getErrorCode();
                if (sendLoginQuestionAnswerResponse.getExecutionCode() != null && sendLoginQuestionAnswerResponse.getExecutionCode().equals("0")) {
                    if (errorCode.equals("1")) {
                        submit.setEnabled(false);
                        GreekDialog.alertDialog(this, 0, GREEK, getString(R.string.CP_PASSWORD_EXPIRED_MSG), "Ok", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                if (action == GreekDialog.Action.OK) {
                                    Intent intent = new Intent(LoginQuestionsActivity.this, ChangePasswordActivity.class);
                                    intent.putExtra("brokerid", brokerId);
                                    startActivityForResult(intent, PASSWORD_CHANGE_NEEDED);
                                    progressLayout.setVisibility(View.GONE);
                                }
                            }
                        });
                    } else if (errorCode.equals("2")) {
                        submit.setEnabled(true);
                        GreekDialog.alertDialog(this, 0, GREEK, getString(R.string.CP_USER_PASSWORD_INVALID_MSG), "Ok", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                if (action == GreekDialog.Action.OK) {
                                    progressLayout.setVisibility(View.GONE);
                                }
                            }
                        });
                    } else if (errorCode.equals("3")) {
                        submit.setEnabled(true);
                        GreekDialog.alertDialog(this, 0, GREEK, getString(R.string.CP_FAILURE_MSG), "Ok", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                if (action == GreekDialog.Action.OK) {
                                    progressLayout.setVisibility(View.GONE);
                                }
                            }
                        });
                    } else if (errorCode.equals("4")) {
                        submit.setEnabled(true);
                        GreekDialog.alertDialog(this, 0, GREEK, getString(R.string.CP_DUPLICATE_MSG), "Ok", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                if (action == GreekDialog.Action.OK) {
                                    progressLayout.setVisibility(View.GONE);
                                }
                            }
                        });
                    } else if (errorCode.equals("5")) {
                        submit.setEnabled(true);
                        GreekDialog.alertDialog(this, 0, GREEK, getString(R.string.CP_MAX_ATTEMPTS_MSG), "Ok", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                if (action == GreekDialog.Action.OK) {
                                    progressLayout.setVisibility(View.GONE);
                                }
                            }
                        });
                    } else if (errorCode.equals("6")) {
                        submit.setEnabled(true);
                        GreekDialog.alertDialog(this, 0, GREEK, getString(R.string.SESSION_INVALID), "Ok", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                if (action == GreekDialog.Action.OK) {
                                    progressLayout.setVisibility(View.GONE);
                                }
                            }
                        });
                    } else if (errorCode.equals("0")) {
                        submit.setEnabled(false);
                        SessionId = jsonResponse.getSessionId();
                        deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                        AccountDetails.setAccountDetails(LoginQuestionsActivity.this, sendLoginQuestionAnswerResponse.getClientCode(),
                                userCode, userType, deviceId,
                                "", "", sendLoginQuestionAnswerResponse.getExecutionCode(),
                                sendLoginQuestionAnswerResponse.getErrorCode(), SessionId, brokerId,"");

                        AccountDetails.allowedmarket_nse = false;
                        AccountDetails.allowedmarket_nfo = false;
                        AccountDetails.allowedmarket_ncd = false;
                        AccountDetails.allowedmarket_bse = false;
                        AccountDetails.allowedmarket_bfo = false;
                        AccountDetails.allowedmarket_bcd = false;
                        AccountDetails.allowedmarket_ncdex = false;
                        AccountDetails.allowedmarket_mcx = false;

                        for (int i = 0; i < sendLoginQuestionAnswerResponse.getAllowedMarket().size(); i++) {
                            if (sendLoginQuestionAnswerResponse.getAllowedMarket().get(i).getMarket_id().equals("1")) {
                                AccountDetails.allowedmarket_nse = true;
                            } else if (sendLoginQuestionAnswerResponse.getAllowedMarket().get(i).getMarket_id().equals("2")) {
                                AccountDetails.allowedmarket_nfo = true;
                            } else if (sendLoginQuestionAnswerResponse.getAllowedMarket().get(i).getMarket_id().equals("3")) {
                                AccountDetails.allowedmarket_ncd = true;
                            } else if (sendLoginQuestionAnswerResponse.getAllowedMarket().get(i).getMarket_id().equals("4")) {
                                AccountDetails.allowedmarket_bse = true;
                            } else if (sendLoginQuestionAnswerResponse.getAllowedMarket().get(i).getMarket_id().equals("5")) {
                                AccountDetails.allowedmarket_bfo = true;
                            } else if (sendLoginQuestionAnswerResponse.getAllowedMarket().get(i).getMarket_id().equals("6")) {
                                AccountDetails.allowedmarket_bcd = true;
                            } else if (sendLoginQuestionAnswerResponse.getAllowedMarket().get(i).getMarket_id().equals("7")) {
                                AccountDetails.allowedmarket_ncdex = true;
                            } else if (sendLoginQuestionAnswerResponse.getAllowedMarket().get(i).getMarket_id().equals("9")) {
                                AccountDetails.allowedmarket_mcx = true;
                            }
                        }


                        AccountDetails.orderTimeFlag = true;
                        AccountDetails.setOrdTime(sendLoginQuestionAnswerResponse.getOrderTime());
                        SharedPreferences.Editor editor = Util.getPrefs(this).edit();
                        editor.putString("orderTime", sendLoginQuestionAnswerResponse.getOrderTime());
                        editor.putBoolean("orderTimeFlag", true);
                        editor.putString("USER_TYPE", AccountDetails.getUsertype(getApplicationContext()));
                        editor.putString("GREEK_RETAINED_CUST_UNAME", userCode);
                        editor.putString("GREEK_RETAINED_CUST_PASS", userpass);
                        editor.putString("GREEK_RETAINED_BROKER_ID", brokerId);

                        editor.apply();
                        editor.commit();
                        startService();

                        if (AccountDetails.getUsertype(getApplicationContext()).equalsIgnoreCase("CUSTOMER")) {
                            GreekBaseActivity.USER_TYPE = GreekBaseActivity.USER.CUSTOMER;
                            AccountDetails.setLogin_user_type("customer");
                        } else if (AccountDetails.getUsertype(getApplicationContext()).equalsIgnoreCase("MFCUSTOMER")) {
                            AccountDetails.setLogin_user_type("mfcustomer");
                            GreekBaseActivity.USER_TYPE = GreekBaseActivity.USER.MFCUSTOMER;
                        } else if (AccountDetails.getUsertype(getApplicationContext()).equalsIgnoreCase("IBTCUSTOMER")) {
                            AccountDetails.setLogin_user_type("ibtcustomer");
                            GreekBaseActivity.USER_TYPE = GreekBaseActivity.USER.IBTCUSTOMER;
                        }


                        final Intent intent = new Intent(LoginQuestionsActivity.this, GreekBaseActivity.class);
                        if (Util.getPrefs(getApplicationContext()).getBoolean("Notification", false)) {
                            intent.putExtra("isProceed", NAV_TO_NOTIFICATION_SCREEN);
                        } else {
                            if (Util.getPrefs(getApplicationContext()).getBoolean("GREEK_APP_DEFAULT_DASHBOARD_SCREEN", false)) {
                                intent.putExtra("isProceed", NAV_TO_MARKET_HOME_SCREEN);

                            } else if (Util.getPrefs(getApplicationContext()).getBoolean("GREEK_APP_DEFAULT_WATCHLIST_SCREEN", false)) {

                                intent.putExtra("isProceed", NAV_TO_WATCHLIST_SCREEN_SCREEN);

                            } else {
                                intent.putExtra("isProceed", NAV_TO_MARKET_HOME_SCREEN);
                            }
                        }
                        setResult(LOGIN_SUCCESS, intent);
                        startActivity(intent);
                        streamingController.sendStreamingLoginRequest(this, AccountDetails.getUsername(getApplicationContext()), AccountDetails.getClientCode(this), AccountDetails.getSessionId(getApplicationContext()), AccountDetails.getToken(this), null, null);
                        orderStreamingController.sendStreamingLoginRequest(this, AccountDetails.getUsername(this), AccountDetails.getClientCode(this), AccountDetails.getSessionId(getApplicationContext()), AccountDetails.getToken(this));
                        SendNotifyInformationRequest.sendRequest(AccountDetails.getClientCode(getApplicationContext()), AccountDetails.getUsername(getApplicationContext()), AccountDetails.getSessionId(getApplicationContext()), deviceId, getString(R.string.fcm_api_key), gcmDeviceToken, "1", LoginQuestionsActivity.this, serviceResponseHandler);

                        new HeartBeatService(LoginQuestionsActivity.this, AccountDetails.getUsername(getApplicationContext()), AccountDetails.getSessionId(this), AccountDetails.getClientCode(this)).start();
                        finish();
                        finish();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        } else if ("Login".equals(jsonResponse.getServiceGroup()) && "gcm_information".equals(jsonResponse.getServiceName())) {
            ReceiveNotifyInformationResponse receiveGCMInformationResponse;
            try {
                receiveGCMInformationResponse = (ReceiveNotifyInformationResponse) jsonResponse.getResponse();
                String errorCode = receiveGCMInformationResponse.getErrorCode();
                if (errorCode.equals("0")) {

                } else {

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("Login".equals(jsonResponse.getServiceGroup()) && "jvalidate_question".equals(jsonResponse.getServiceName())) {
            SendLoginAnswerValidateResponse sendLoginAnswerValidateResponse;
            try {
                deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                sendLoginAnswerValidateResponse = (SendLoginAnswerValidateResponse) jsonResponse.getResponse();
                SessionId = jsonResponse.getSessionId();
                String errorCode = sendLoginAnswerValidateResponse.getErrorCode();
                if (sendLoginAnswerValidateResponse.getExecutionCode() != null && sendLoginAnswerValidateResponse.getExecutionCode().equals("0")) {
                    if (errorCode.equals("1")) {
                        submit.setEnabled(false);
                        GreekDialog.alertDialog(this, 0, GREEK, getString(R.string.CP_PASSWORD_EXPIRED_MSG), "Ok", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                if (action == GreekDialog.Action.OK) {
                                    Intent intent = new Intent(LoginQuestionsActivity.this, ChangePasswordActivity.class);
                                    startActivityForResult(intent, PASSWORD_CHANGE_NEEDED);
                                    progressLayout.setVisibility(View.GONE);
                                }
                            }
                        });
                    } else if (errorCode.equals("2")) {
                        submit.setEnabled(true);
                        GreekDialog.alertDialog(this, 0, GREEK, getString(R.string.CP_USER_PASSWORD_INVALID_MSG), "Ok", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                if (action == GreekDialog.Action.OK) {
                                    progressLayout.setVisibility(View.GONE);
                                    deleteAnswers = true;
                                    commonAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                    } else if (errorCode.equals("3")) {
                        submit.setEnabled(true);
                        GreekDialog.alertDialog(this, 0, GREEK, getString(R.string.CP_FAILURE_MSG), "Ok", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                if (action == GreekDialog.Action.OK) {
                                    progressLayout.setVisibility(View.GONE);
                                }
                            }
                        });
                    } else if (errorCode.equals("4")) {
                        submit.setEnabled(true);
                        GreekDialog.alertDialog(this, 0, GREEK, getString(R.string.CP_DUPLICATE_MSG), "Ok", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                if (action == GreekDialog.Action.OK) {
                                    progressLayout.setVisibility(View.GONE);
                                }
                            }
                        });
                    } else if (errorCode.equals("5")) {
                        submit.setEnabled(true);
                        GreekDialog.alertDialog(this, 0, GREEK, getString(R.string.CP_MAX_ATTEMPTS_MSG), "Ok", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                if (action == GreekDialog.Action.OK) {
                                    progressLayout.setVisibility(View.GONE);
                                }
                            }
                        });
                    } else if (errorCode.equals("7")) {
                        submit.setEnabled(true);
                        GreekDialog.alertDialog(this, 0, GREEK, getString(R.string.CP_INACTIVE_MSG), "Ok", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                if (action == GreekDialog.Action.OK) {
                                    progressLayout.setVisibility(View.GONE);
                                }
                            }
                        });
                    } else if (errorCode.equals("8")) {
                        submit.setEnabled(true);
                        GreekDialog.alertDialog(this, 0, GREEK, getString(R.string.CP_INVALID_2FA_MSG), "Ok", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                if (action == GreekDialog.Action.OK) {
                                    progressLayout.setVisibility(View.GONE);
                                    deleteAnswers = true;
                                    commonAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                    } else if (errorCode.equals("6")) {
                        submit.setEnabled(true);
                        GreekDialog.alertDialog(this, 0, GREEK, getString(R.string.SESSION_INVALID), "Ok", false, new GreekDialog.DialogListener() {

                            @Override
                            public void alertDialogAction(GreekDialog.Action action, Object... data) {
                                if (action == GreekDialog.Action.OK) {
                                    progressLayout.setVisibility(View.GONE);
                                }
                            }
                        });
                    } else if (errorCode.equals("0")) {
                        submit.setEnabled(false);

                        AccountDetails.setAccountDetails(LoginQuestionsActivity.this,
                                sendLoginAnswerValidateResponse.getClientCode(),
                                userCode, userType, deviceId,
                                "", "", sendLoginAnswerValidateResponse.getExecutionCode(),
                                sendLoginAnswerValidateResponse.getErrorCode(), SessionId, brokerId);

                        AccountDetails.allowedmarket_nse = false;
                        AccountDetails.allowedmarket_nfo = false;
                        AccountDetails.allowedmarket_ncd = false;
                        AccountDetails.allowedmarket_bse = false;
                        AccountDetails.allowedmarket_bfo = false;
                        AccountDetails.allowedmarket_bcd = false;
                        AccountDetails.allowedmarket_ncdex = false;
                        AccountDetails.allowedmarket_mcx = false;


                        for (int i = 0; i < sendLoginAnswerValidateResponse.getAllowedMarket().size(); i++) {
                            if (sendLoginAnswerValidateResponse.getAllowedMarket().get(i).getMarket_id().equals("1")) {
                                AccountDetails.allowedmarket_nse = true;
                            } else if (sendLoginAnswerValidateResponse.getAllowedMarket().get(i).getMarket_id().equals("2")) {
                                AccountDetails.allowedmarket_nfo = true;
                            } else if (sendLoginAnswerValidateResponse.getAllowedMarket().get(i).getMarket_id().equals("3")) {
                                AccountDetails.allowedmarket_ncd = true;
                            } else if (sendLoginAnswerValidateResponse.getAllowedMarket().get(i).getMarket_id().equals("4")) {
                                AccountDetails.allowedmarket_bse = true;
                            } else if (sendLoginAnswerValidateResponse.getAllowedMarket().get(i).getMarket_id().equals("5")) {
                                AccountDetails.allowedmarket_bfo = true;
                            } else if (sendLoginAnswerValidateResponse.getAllowedMarket().get(i).getMarket_id().equals("6")) {
                                AccountDetails.allowedmarket_bcd = true;
                            } else if (sendLoginAnswerValidateResponse.getAllowedMarket().get(i).getMarket_id().equals("7")) {
                                AccountDetails.allowedmarket_ncdex = true;
                            } else if (sendLoginAnswerValidateResponse.getAllowedMarket().get(i).getMarket_id().equals("9")) {
                                AccountDetails.allowedmarket_mcx = true;
                            }

                        }
                        AccountDetails.orderTimeFlag = true;
                        AccountDetails.setOrdTime(sendLoginAnswerValidateResponse.getOrderTime());
                        SharedPreferences.Editor editor = Util.getPrefs(this).edit();
                        editor.putString("orderTime", sendLoginAnswerValidateResponse.getOrderTime());
                        editor.putBoolean("orderTimeFlag", true);
                        editor.putString("USER_TYPE", AccountDetails.getUsertype(getApplicationContext()));
                        editor.putString("GREEK_RETAINED_CUST_UNAME", userCode);
                        editor.putString("GREEK_RETAINED_CUST_PASS", userpass);
                        editor.putString("GREEK_RETAINED_BROKER_ID", brokerId);
                        editor.apply();
                        editor.commit();

                        if (AccountDetails.getUsertype(getApplicationContext()).equalsIgnoreCase("CUSTOMER")) {

                            GreekBaseActivity.USER_TYPE = GreekBaseActivity.USER.CUSTOMER;
                            AccountDetails.setLogin_user_type("customer");
                        } else if (AccountDetails.getUsertype(getApplicationContext()).equalsIgnoreCase("MFCUSTOMER")) {
                            GreekBaseActivity.USER_TYPE = GreekBaseActivity.USER.MFCUSTOMER;
                            AccountDetails.setLogin_user_type("mfcustomer");
                        } else if (AccountDetails.getUsertype(getApplicationContext()).equalsIgnoreCase("IBTCUSTOMER")) {
                            GreekBaseActivity.USER_TYPE = GreekBaseActivity.USER.IBTCUSTOMER;
                            AccountDetails.setLogin_user_type("ibtcustomer");
                        }


                        final Intent intent = new Intent(LoginQuestionsActivity.this, GreekBaseActivity.class);
                        if (Util.getPrefs(getApplicationContext()).getBoolean("Notification", false)) {
                            intent.putExtra("isProceed", NAV_TO_NOTIFICATION_SCREEN);
                        } else {
                            if (Util.getPrefs(getApplicationContext()).getBoolean("GREEK_APP_DEFAULT_DASHBOARD_SCREEN", false)) {
                                intent.putExtra("isProceed", NAV_TO_MARKET_HOME_SCREEN);

                            } else if (Util.getPrefs(getApplicationContext()).getBoolean("GREEK_APP_DEFAULT_WATCHLIST_SCREEN", false)) {

                                intent.putExtra("isProceed", NAV_TO_WATCHLIST_SCREEN_SCREEN);

                            } else {
                                intent.putExtra("isProceed", NAV_TO_MARKET_HOME_SCREEN);
                            }
                        }
                        setResult(LOGIN_SUCCESS, intent);
                        startService();

                        streamingController.sendStreamingLoginRequest(this, AccountDetails.getUsername(getApplicationContext()), AccountDetails.getClientCode(this), AccountDetails.getSessionId(getApplicationContext()), AccountDetails.getToken(this), null, null);
                        orderStreamingController.sendStreamingLoginRequest(this, AccountDetails.getUsername(this), AccountDetails.getClientCode(this), AccountDetails.getSessionId(getApplicationContext()), AccountDetails.getToken(this));
                        SendNotifyInformationRequest.sendRequest(AccountDetails.getClientCode(getApplicationContext()), AccountDetails.getUsername(getApplicationContext()), AccountDetails.getSessionId(getApplicationContext()), deviceId, getString(R.string.fcm_api_key), gcmDeviceToken, "1", LoginQuestionsActivity.this, serviceResponseHandler);

                        new HeartBeatService(LoginQuestionsActivity.this, AccountDetails.getUsername(getApplicationContext()), AccountDetails.getSessionId(this), AccountDetails.getClientCode(this)).start();
                        startActivity(intent);
                        overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_right_out_activity);
                        finish();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        } else if ("Login".equals(jsonResponse.getServiceGroup()) && "forget_answers".equals(jsonResponse.getServiceName())) {
            sendForgotAnswersResponse sendforgotanswersresponse;
            try {
                sendforgotanswersresponse = (sendForgotAnswersResponse) jsonResponse.getResponse();
                String errorCode = sendforgotanswersresponse.getErrorCode();
                if (errorCode.equals("0")) {
                    GreekDialog.alertDialog(this, 0, GREEK, getString(R.string.LP_FORGOT_ANSWER), "Ok", false, new GreekDialog.DialogListener() {

                        @Override
                        public void alertDialogAction(GreekDialog.Action action, Object... data) {
                            if (action == GreekDialog.Action.OK) {
                                progressLayout.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void startService() {

        Intent i = new Intent(this, MyService.class);
        startService(i);
    }

    @Override
    public void handleError(int errorCode, String message, Object error, ServiceRequest serviceRequest) {

    }

    @Override
    public void infoDialog(int action, String msg, JSONResponse jsonResponse) {

    }

    @Override
    public void handleInvalidSession(String msg, int actionCode, JSONResponse jsonResponse) {

    }

    @Override
    public void showMsgOnScreen(int action, String msg, JSONResponse jsonResponse) {

    }

    @Override
    public void infoDialogOK(int action, String message, JSONResponse jsonResponse) {

    }

    //TO IDENTIFY THE EVENT ON PARTICULAR ACTIVITY
    @Override
    public void onBackPressed() {
        GreekBaseActivity.USER_TYPE = GreekBaseActivity.USER.OPENUSER;
        AccountDetails.setLogin_user_type("openuser");
        super.onBackPressed();
    }

    //To IDENTIFY THE TOUCH EVENT ON ANYWEHRE ON CURRENT SCREEN
    public void onEventMainThread(OrderStreamingAuthResponse response) {
        try {
            if (response.getError_code().equals("0")) {
                orderStreamingController.sendStreamingGCMInfoRequest(getApplicationContext(), AccountDetails.getUsername(getApplicationContext()), AccountDetails.getClientCode(getApplicationContext()), AccountDetails.getSessionId(getApplicationContext()), "true");
            } else {

            }

        } catch (Exception e) {
            Log.e("Login Failure :", e.getMessage());
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
}