package in.co.vyapari.ui.activity.user;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.developers.imagezipper.ImageZipper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;


import in.co.vyapari.R;
import in.co.vyapari.VyapariApp;
import in.co.vyapari.constant.Constants;
import in.co.vyapari.middleware.listener.ServiceCall;
import in.co.vyapari.middleware.listener.ServiceCallV;
import in.co.vyapari.middleware.service.CommonService;
import in.co.vyapari.middleware.service.UserService;
import in.co.vyapari.model.Login;
import in.co.vyapari.model.Register;
import in.co.vyapari.model.response.BaseModel;
import in.co.vyapari.model.response.BaseModelV;
import in.co.vyapari.model.response.ErrorModel;
import in.co.vyapari.model.response.dto.GstinForVyapariDTO;
import in.co.vyapari.model.response.dto.SectorDTO;
import in.co.vyapari.model.response.dto.promocodeDTO;
import in.co.vyapari.ui.adapter.OneLineSpinnerAdapter;
import in.co.vyapari.ui.base.SohoActivity;
import in.co.vyapari.ui.generic.MySpinner;
import in.co.vyapari.ui.generic.MyTextWatcher;
import in.co.vyapari.ui.generic.dialog.LicensesDialog;
import in.co.vyapari.util.Utils;
import in.co.vyapari.util.ValidateUtil;


public class RegisterActivity extends SohoActivity {

    @BindView(R.id.account_type_options)
    RadioGroup accountTypeRG;
    @BindView(R.id.company_rb)
    RadioButton companyRB;
    @BindView(R.id.chartered_accountant_rb)
    RadioButton charteredAccountantRB;
    @BindView(R.id.registerpage_input_name)
    EditText nameEditText;
    @BindView(R.id.registerpage_input_surname)
    EditText surnameEditText;
    @BindView(R.id.registerpage_input_email)
    EditText emailEditText;
    @BindView(R.id.registerpage_input_phone)
    EditText phoneEditText;
    @BindView(R.id.registerpage_input_password)
    EditText passwordEditText;
    @BindView(R.id.registerpage_input_password2)
    EditText password2EditText;

    @BindView(R.id.registerpage_mid_area)
    LinearLayout midAreaLL;

    @BindView(R.id.registerpage_sector_sp)
    MySpinner sectorSP;
    @BindView(R.id.registerpage_input_gstin)
    EditText gstinEditText;

    @BindView(R.id.registerpage_bottom_area)
    LinearLayout bottomAreaLL;

    @BindView(R.id.gstin_valid_data)
    LinearLayout gstin_valid_data;

    @BindView(R.id.Company_Name)
    EditText Company_Name;
    @BindView(R.id.province)
    EditText province;
    @BindView(R.id.GST_reg_type)
    EditText GST_reg_type;
    @BindView(R.id.orgnisation_type)
    EditText orgnisation_type;
    @BindView(R.id.registerpage_address)
    EditText addressET;
    @BindView(R.id.registerpage_postal_code)
    EditText postalCodeET;
    @BindView(R.id.registerpage_check_bt)
    Button checkButton;
    @BindView(R.id.registerpage_usage_contract)
    CheckBox usageContractCheckBox;
    @BindView(R.id.registerpage_register)
    Button registerButton;
    @BindView(R.id.company_logo_img)
    ImageView companyImageViews;
    @BindView(R.id.signature_logo)
    ImageView company_singView;

    @BindView(R.id.companysector)
    LinearLayout companysector;

    @BindView(R.id.SectorOther)
    EditText SectorOther;
    @BindView(R.id.promocode)
    EditText promocode;
    @BindView(R.id.promocodeapply)
    Button promocodeapply;

    @BindView(R.id.promodistributor)
    LinearLayout promodistributor;

    @BindView(R.id.Promo_Distributor)
    EditText Promo_Distributor;
    @BindView(R.id.gstintext)
    TextView gstintext;


    private Context mContext;
    private List<SectorDTO> sectorList;
    private boolean gstinIsValid;
    private Login login;
    private GstinForVyapariDTO gstinForVyapari;
    private promocodeDTO promocodedto;

    private Uri fileUri;
    private String imageType;
    SectorDTO other;
    Register reg = new Register();

    private static final int PICK_IMAGE_REQUEST = 1;
    private File actualImage;
    private File compressedImage;
    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_IMAGES = 11;
    public static final String IMAGE_DIRECTORY_NAME = "imageuploadtest";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mContext = this;

        config();
        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(),
                    "device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }

    private void config() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}, MEDIA_TYPE_IMAGES);
        }

        emailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    checkEmail();
                }
            }
        });

        gstinEditText.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void afterTextChanged() {
                if (gstinEditText.getText().toString().length() > 0) {
                    checkButton.setEnabled(true);
                } else {
                    checkButton.setEnabled(true);
                }
            }
        });
        //default its false
        reg.setIsCharteredAccountant(false);
        accountTypeRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int position) {
                if (position == R.id.company_rb) {
                    midAreaLL.setVisibility(View.VISIBLE);
                    gstin_valid_data.setVisibility(View.GONE);
                    gstintext.setTextColor(getResources().getColor(R.color.white));
                    gstinEditText.setText("");
                    gstinEditText.setEnabled(true);
                    gstinEditText.setTextColor(getResources().getColor(R.color.white));
                    checkButton.setVisibility(View.VISIBLE);
                    companyImageViews.setVisibility(View.INVISIBLE);
                    company_singView.setVisibility(View.INVISIBLE);
                    reg.setIsCharteredAccountant(false);
                } else {
                    reg.setIsCharteredAccountant(true);
                    midAreaLL.setVisibility(View.GONE);
                    gstin_valid_data.setVisibility(View.GONE);
                }
            }
        });

        getSector();
        sectorSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                other = sectorList.get(i);
                if (other.getTitle().equalsIgnoreCase("OTHERS")) {
                    companysector.setVisibility(View.VISIBLE);
                } else {
                    companysector.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getSector() {
        CommonService.getSectorList(new ServiceCall<BaseModelV<List<SectorDTO>>>() {
            @Override
            public void start() {
                lockSpinner(sectorSP);
            }

            @Override
            public void onResponse(boolean isOnline, BaseModelV<List<SectorDTO>> response) {
                unlockSpinner(sectorSP);
                List<SectorDTO> data = response.getData();
                if (data == null) {
                    data = new ArrayList<>();
                }
                sectorList = data;
                sectorSP.setAdapter(new OneLineSpinnerAdapter(mContext, sectorList));
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {

            }
        });
    }

    @OnClick(R.id.promocodeapply)
    void clikPromoCode() {
        final String promo = promocode.getText().toString().trim();
        CommonService.validPromoCode(promo, new ServiceCall<BaseModelV<promocodeDTO>>() {
            @Override
            public void start() {
                Utils.showLoading(mContext);
            }

            @Override
            public void onResponse(boolean isOnline, BaseModelV<promocodeDTO> response) {
                Utils.hideLoading();
                gstinIsValid = !response.isError();
                if (!response.isError()) {
                    promocodedto = response.getData();
                    reg.setPromoCode(promocodedto.getPromoCode());
                    promodistributor.setVisibility(View.VISIBLE);
                    promocodeapply.setVisibility(View.GONE);
                    Promo_Distributor.setText(promocodedto.getCode() + "-" + promocodedto.getName());
                    Promo_Distributor.setTextColor(getResources().getColor(R.color.light_grey));
                    promocode.setTextColor(getResources().getColor(R.color.light_grey));
                    Promo_Distributor.setEnabled(false);
                    promocode.setEnabled(false);

                    Toasty.success(mContext, response.getMessage()).show();
                } else {
                    Toasty.success(mContext, response.getMessage()).show();
                }
            }


            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                Utils.hideLoading();

                Toasty.error(mContext, getString(R.string.invalid_promo_code)).show();
            }
        });
    }

    @OnClick(R.id.registerpage_check_bt)
    void gstinValidateClick() {
        final String gstin = gstinEditText.getText().toString().trim();
        CommonService.validGSTINForVyapari(gstin, new ServiceCall<BaseModel<GstinForVyapariDTO>>() {
            @Override
            public void start() {
                Utils.showLoading(mContext);
            }

            @Override
            public void onResponse(boolean isOnline, BaseModel<GstinForVyapariDTO> response) {
                Utils.hideLoading();
                gstinIsValid = !response.isError();
                if (!response.isError()) {
                    gstinForVyapari = response.getData();


                    Toasty.success(mContext, response.getMessage()).show();

                    bottomAreaLL.setVisibility(View.VISIBLE);
                    gstin_valid_data.setVisibility(View.VISIBLE);
                    checkButton.setVisibility(View.VISIBLE);
                    if (gstinForVyapari != null) {

                        reg.setTaxId(gstin);
                        Company_Name.setText(gstinForVyapari.getTradeName());

                        province.setText(gstinForVyapari.getPradrobjDTO().getStcd());

                        GST_reg_type.setText(gstinForVyapari.getType());
                        if (gstinForVyapari.getConstitution().equalsIgnoreCase("Partnership")) {
                            orgnisation_type.setText("FIRM");
                        } else if (gstinForVyapari.getConstitution().equalsIgnoreCase("Public Limited Company")) {
                            orgnisation_type.setText("COMPANY");
                        } else {
                            orgnisation_type.setText("PERSON");
                        }

                        gstinEditText.setTextColor(getResources().getColor(R.color.light_grey));
                        gstinEditText.setEnabled(false);

                        checkButton.setVisibility(View.GONE);

                        addressET.setText(gstinForVyapari.getPradrobjDTO().getFlno() + " " + gstinForVyapari.getPradrobjDTO().getBno()
                                + " " + gstinForVyapari.getPradrobjDTO().getBnm() + " " + gstinForVyapari.getPradrobjDTO().getSt() + " " +
                                gstinForVyapari.getPradrobjDTO().getLoc() + " " + gstinForVyapari.getPradrobjDTO().getStcd());
                        postalCodeET.setText(gstinForVyapari.getPradrobjDTO().getPncd());
                        gstintext.setTextColor(getResources().getColor(R.color.light_grey));
                        Company_Name.setTextColor(getResources().getColor(R.color.light_grey));
                        Company_Name.setEnabled(false);
                        province.setTextColor(getResources().getColor(R.color.light_grey));
                        province.setEnabled(false);
                        GST_reg_type.setTextColor(getResources().getColor(R.color.light_grey));
                        GST_reg_type.setEnabled(false);
                        orgnisation_type.setTextColor(getResources().getColor(R.color.light_grey));
                        orgnisation_type.setEnabled(false);

                    }
                } else {
                    bottomAreaLL.setVisibility(View.GONE);
                    gstin_valid_data.setVisibility(View.GONE);
                    Toasty.error(mContext, response.getMessage()).show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                Utils.hideLoading();
                bottomAreaLL.setVisibility(View.GONE);
                gstin_valid_data.setVisibility(View.GONE);
                Toasty.error(mContext, getString(R.string.gstin_error_msg)).show();
            }
        });
    }

    @OnClick(R.id.registerpage_register)
    void doRegisterClick() {
        Utils.hideKeyboard(this);
        if (!VyapariApp.getServiceQueue().isSuccess()) {
            return;
        }

        String name = nameEditText.getText().toString().trim();
        String surname = surnameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String sector = sectorList.get(sectorSP.getSelectedItemPosition()).getId();
        String password = passwordEditText.getText().toString().trim();
        String password2 = password2EditText.getText().toString().trim();
        String taxId = gstinEditText.getText().toString().trim();
        String othersector = SectorOther.getText().toString().trim();
        String address = addressET.getText().toString().trim();
        String postalCode = postalCodeET.getText().toString().trim();

        if (name.length() < Constants.NAME_MIN_SIZE || surname.length() < Constants.SURNAME_MIN_SIZE) {
            Toasty.warning(mContext, getString(R.string.warn_empty_name_or_surname)).show();
            return;
        }

        if (!checkEmail()) {
            Toasty.warning(mContext, getString(R.string.warn_empty_email)).show();
            return;
        }

        if (!checkPassword(passwordEditText) || !checkPassword(password2EditText) || !password.equals(password2)) {
            Toasty.warning(mContext, getString(R.string.warn_password_msg)).show();
            return;
        }

        if (other.getTitle().equalsIgnoreCase("OTHERS")) {

            if (othersector.equalsIgnoreCase("")) {
                Toasty.warning(mContext, getString(R.string.companysector)).show();
                return;
            }
        } else {
            companysector.setVisibility(View.GONE);
        }
        if (!usageContractCheckBox.isChecked()) {
            usageContractClick();
            return;
        }


        if (sector.equals(Constants.SECTOR_OTHER)) {
            sector = Constants.SECTOR_OTHER + "|other";
        }


        reg.setName(name);
        reg.setSurname(surname);
        reg.setPhone(phone);
        reg.setEmail(email);
        reg.setPassword(password);
        reg.setTerms(true);
        if (reg.getIsCharteredAccountant() == true) {
            reg.setCity("");
            reg.setDistrict("");
            reg.setSector("");
            reg.setTaxId("");
            reg.setAddress("");
            reg.setPostalCode("");
            reg.setIcon("");
            reg.setIconOther("");
        } else {
            reg.setCity(gstinForVyapari.getPradrobjDTO().getLoc());
            reg.setDistrict(gstinForVyapari.getPradrobjDTO().getStcd());
            if (companyRB.isChecked()) {
                reg.setSector(sector);
                if (taxId.length() > 0 && gstinIsValid) {
                    reg.setTaxId(taxId);
                }
            }


            reg.setAddress(address);
            reg.setPostalCode(postalCode);

        }

        login = new Login(email, password);

        doRegisterServiceCall(reg);
    }

    private void doRegisterServiceCall(Register register) {
        UserService.doRegister(register, new ServiceCallV<BaseModelV<String>>() {
            @Override
            public void start() {
                Utils.showLoading(mContext);
            }

            @Override
            public void onResponse(boolean isOnline, BaseModelV<String> response) {
                Utils.hideLoading();
                if (!response.isError()) {
                    String message = response.getMessage();
                    if (message != null) {
                        Utils.setObjectSharedPreferencesValue(mContext, login, Constants.LOGIN_INFO);
                        Toasty.success(mContext, message, Toast.LENGTH_LONG).show();
                        startActivity(new Intent(mContext, LoginActivity.class));
                        finish();
                    }
                } else {
                    Toasty.error(RegisterActivity.this, response.getMessage()).show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, ErrorModel error) {
                Utils.hideLoading();
                Toasty.error(getApplicationContext(), error.getMessage()).show();
            }
        });
    }

    private boolean checkStringArea(String s, int minSize, String errorMessage) {
        return ValidateUtil.validateStrings(mContext, s, minSize, errorMessage);
    }

    private boolean checkEmail() {
        String email = emailEditText.getText().toString();
        return ValidateUtil.validateEmail(RegisterActivity.this, email);
    }

    private boolean checkPassword(EditText editText) {
        String password = editText.getText().toString();
        return ValidateUtil.validatePassword(RegisterActivity.this, password);
    }

    @OnClick(R.id.register_agreement_tv)
    void usageContractClick() {
        Utils.showLoading(mContext);
        UserService.getUsageContract(new ServiceCall<BaseModelV<String>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModelV<String> response) {
                Utils.hideLoading();
                if (!response.isError()) {
                    new LicensesDialog(mContext, response.getData())
                            .setTitle(R.string.term_of_use)
                            .setPositiveButton(R.string.agree, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    usageContractCheckBox.setChecked(true);
                                }
                            })
                            .setNegativeButton(R.string.cancel, null)
                            .setCancelable(false)
                            .show();
                }
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                Utils.hideLoading();
                Toasty.error(getApplicationContext(), getString(R.string.error)).show();
            }
        });
    }

    @OnClick(R.id.compnaylogo)
    void getImageForCompanyLogo() {
        reg.setIcon("");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            imageType = "Logo";
            requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA"}, Constants.PERMISSION_READ);
        } else {
            imageType = "Logo";
            getImage("Logo");
        }
    }

    @OnClick(R.id.signature)
    void getImageForSignature() {
        reg.setIconOther("");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            imageType = "Signature";
            requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA"}, Constants.PERMISSION_READ);
        } else {
            imageType = "Signature";
            getImage("Signature");
        }
    }

    private void getImage(String type) {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Faild to open", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                actualImage = FileUtil.from(this, data.getData());
                File actualFile = new File(String.valueOf(actualImage));
                setCompressedImage(actualFile);
            } catch (IOException e) {
                Toast.makeText(this, "Faild to read", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }


    private void setCompressedImage(File actualFile) {
        try {
            File imageZipperFile = new ImageZipper(mContext).setQuality(10).setMaxWidth(200).setMaxHeight(200).compressToFile(actualFile);
            //           int result_file_size = Integer.parseInt(String.valueOf(imageZipperFile.length() / 1024));
            //           Toast.makeText(mContext, "" + result_file_size + " Kb", Toast.LENGTH_LONG).show();
            Bitmap b = BitmapFactory.decodeFile(imageZipperFile.getAbsolutePath());


            if (imageType.equalsIgnoreCase("Logo")) {
                companyImageViews.setImageBitmap(b);
                companyImageViews.setVisibility(View.VISIBLE);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                reg.setIcon(encoded);

            } else {
                company_singView.setImageBitmap(b);
                company_singView.setVisibility(View.VISIBLE);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                reg.setIconOther(encoded);

            }

        } catch (Exception e) {

        }
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(mContext, LoginActivity.class));
        super.onBackPressed();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.PERMISSION_READ: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        if (imageType.equalsIgnoreCase("Logo")) {
                            getImage("Logo");
                        } else {
                            getImage("Signature");
                        }
                    } catch (Exception e) {

                    }
                } else {
                    Toasty.error(mContext, getString(R.string.permission_warn)).show();
                }
            }
            case MEDIA_TYPE_IMAGES: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toasty.error(mContext, getString(R.string.permission_warn)).show();
                }
            }
        }
    }
}