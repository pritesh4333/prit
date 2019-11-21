package in.co.vyaparienterprise.ui.activity.management;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import in.co.vyaparienterprise.R;
import in.co.vyaparienterprise.middleware.ServiceCreator;
import in.co.vyaparienterprise.middleware.ServiceRequest;
import in.co.vyaparienterprise.model.User;
import in.co.vyaparienterprise.model.response.BaseModel;
import in.co.vyaparienterprise.model.response.ErrorModel;
import in.co.vyaparienterprise.model.response.dto.LoginDTO;
import in.co.vyaparienterprise.ui.base.SohoActivity;
import in.co.vyaparienterprise.util.DataUtil;
import in.co.vyaparienterprise.util.ErrorUtils;
import in.co.vyaparienterprise.util.ValidateUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends SohoActivity {

    @BindView(R.id.management_profile_fullname)
    EditText fullnameET;
    @BindView(R.id.management_profile_phone)
    EditText phoneET;
    @BindView(R.id.management_profile_email)
    EditText emailET;

    private Context mContext;
    private User user;
    private User userRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_profile);
        ButterKnife.bind(this);
        mContext = this;

        setToolbarConfig(R.string.profile_info);

        LoginDTO loginDTO = DataUtil.getBundle(LoginDTO.class);
        user = loginDTO.getUser();
        userRequest = new User();

        config();
    }

    private void config() {
        fullnameET.setText(user.getFullName());
        phoneET.setText(user.getPhone());
        emailET.setText(user.getEmail());

       /* if (user.getUserImage() != null) {
            profileAreaLL.setVisibility(View.GONE);
            CustomPicasso.getImageLoader(mContext)
                    .load(user.getUserImage())
                    .into(profilePhotoIV);
        } else {*/
        // }
    }

    @OnClick(R.id.management_profile_submit_button)
    public void saveProfileClick() {
        boolean isValid = ValidateUtil.validateEditTexts(fullnameET, emailET);
        if (!isValid) {
            Toasty.warning(mContext, getString(R.string.empy_warn)).show();
            return;
        }

        String fullname = fullnameET.getText().toString();
        String phone = phoneET.getText().toString();
        String email = emailET.getText().toString();

        userRequest.setId(user.getId());
        userRequest.setFullName(fullname);
        userRequest.setPhone(phone);
        userRequest.setEmail(email);

        updateProfile();
    }

    private void updateProfile() {
        ServiceRequest apiService = ServiceCreator.getClient().create(ServiceRequest.class);
        Call<BaseModel<User>> call = apiService.updateProfile(userRequest);
        call.enqueue(new Callback<BaseModel<User>>() {
            @Override
            public void onResponse(Call<BaseModel<User>> call, Response<BaseModel<User>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getData() != null) {
                        user = response.body().getData();
                        if (user != null) {
                            config();
                        } else {
                            Toasty.error(mContext, getString(R.string.error)).show();
                        }
                    } else {
                        Toasty.error(mContext, response.body().getErrorDescription()).show();
                    }
                } else {
                    ErrorModel error = ErrorUtils.parseError(response);
                    Toasty.error(mContext, error.getError()).show();
                }
            }

            @Override
            public void onFailure(Call<BaseModel<User>> call, Throwable ignored) {
                Toasty.error(mContext, getString(R.string.error)).show();
            }
        });
    }
}