package in.co.vyapari.ui.activity.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.developers.imagezipper.ImageZipper;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import in.co.vyapari.R;
import in.co.vyapari.constant.Constants;
import in.co.vyapari.constant.MobileConstants;
import in.co.vyapari.middleware.ServiceCreator;
import in.co.vyapari.middleware.ServiceRequest;
import in.co.vyapari.middleware.listener.ServiceCall;
import in.co.vyapari.middleware.listener.ServiceCallV;
import in.co.vyapari.middleware.service.CommonService;
import in.co.vyapari.middleware.service.ProductService;
import in.co.vyapari.model.DefaultAccount;
import in.co.vyapari.model.KeyValue;
import in.co.vyapari.model.Login;
import in.co.vyapari.model.Mswipe;
import in.co.vyapari.model.UpdateLogo;
import in.co.vyapari.model.UpdatePassword;
import in.co.vyapari.model.request.NotificationConfig;
import in.co.vyapari.model.response.BaseModel;
import in.co.vyapari.model.response.BaseModelV;
import in.co.vyapari.model.response.ErrorModel;
import in.co.vyapari.model.response.dto.ArApCollectionDTO;
import in.co.vyapari.model.response.dto.CollectionTypeDTO;
import in.co.vyapari.model.response.dto.LoginDTO;
import in.co.vyapari.model.response.dto.UserDTO;
import in.co.vyapari.model.response.dto.promocodeDTO;
import in.co.vyapari.ui.activity.invoice.CreateInvoiceActivity;
import in.co.vyapari.ui.activity.management.EditCompanyActivity;
import in.co.vyapari.ui.activity.user.AppSharedPrefrences;
import in.co.vyapari.ui.activity.user.FileUtil;
import in.co.vyapari.ui.activity.user.LoginActivity;
import in.co.vyapari.ui.activity.user.MswipeDeclineActivity;
import in.co.vyapari.ui.activity.user.MswipeSignatureActivity;
import in.co.vyapari.ui.adapter.invoice.InvoicesAdapter;
import in.co.vyapari.ui.base.SohoActivity;
import in.co.vyapari.ui.fragment.CollectionFragment;
import in.co.vyapari.ui.fragment.FirmsFragment;
import in.co.vyapari.ui.fragment.PaymentFragment;
import in.co.vyapari.ui.fragment.ProductsFragment;
import in.co.vyapari.ui.fragment.PurchasesFragment;
import in.co.vyapari.ui.fragment.SalesFragment;
import in.co.vyapari.util.DataUtil;
import in.co.vyapari.util.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static in.co.vyapari.ui.activity.user.MswipePaymentView.MSWIPE_CARDSALE_ACTIVITY_REQUEST_CODE;
import static in.co.vyapari.ui.activity.user.MswipePaymentView.MSWIPE_CARDSALE_DECLINE_REQUEST;
import static in.co.vyapari.ui.activity.user.MswipePaymentView.MSWIPE_CARDSALE_SIGNATURE_REQUEST;

public class MainActivity extends SohoActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_left_icon)
    ImageView leftIcon;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.invoices_sublist_layout)
    LinearLayout invoicesSublistLL;
    @BindView(R.id.payment_sublist_layout)
    LinearLayout payment_sublist_layout;
    @BindView(R.id.invoice_collapse)
    ImageView invoiceCollapse;
    @BindView(R.id.invoice_expand)
    ImageView invoiceExpand;
    @BindView(R.id.payment_collapse)
    ImageView paymentCollapse;
    @BindView(R.id.payment_expand)
    ImageView paymentExpand;

    private Context context;
    private boolean drawInvoiceKey;
    private boolean drawpaymentKey = true;
    private int mainFragmentId = R.id.draw_sale;
    private int mainFragmentDefaultId = R.id.draw_sale;

    private NavigationView navigationView;
    private TextView nameSurname;
    private TextView companyName;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERAPICK_IMAGE_REQUEST = 2;
    public static final int MEDIA_TYPE_IMAGES = 11;
    private File actualImage;
    ImageView update_logo_img;
    public String selectImagType = "";
    LoginDTO loginResponse;
    private promocodeDTO promocodedto;
    EditText update_promo_distributor;
    UpdateLogo updatelogo;
    String companyimage = null;
	  String companyLogoimage=null;
    UserDTO userdro;
    String PromocodeType="";
    String PromocodeUpdate="";
    String update_promos;
      AlertDialog promoalertDialog;
     EditText update_promocode;
    boolean doubleBackToExitPressedOnce = false;
      Spinner bank_spinner;
     Spinner cash_spinner;
    Spinner collection_type_spinner;
    private ArApCollectionDTO ArApCollectionDTO;
    List<CollectionTypeDTO> arapNo;
    List<String> bank;
    List<String> cash;
    List<String> card;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbarConfig(getString(R.string.sales), false);
        ButterKnife.bind(this);
        context = this;

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Utils.hideKeyboard(context);
            }
        };

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        RelativeLayout notProfilePhoto = navigationView.findViewById(R.id.left_not_profile);
        CircleImageView profilePhoto = navigationView.findViewById(R.id.left_profile_photo);
        nameSurname = navigationView.findViewById(R.id.left_name_surname);
        companyName = navigationView.findViewById(R.id.left_company);

        loginResponse = DataUtil.getBundleAndRemove(LoginDTO.class);

        /*if (loginResponse != null && loginResponse.getCompany() != null && loginResponse.getCompany().getImageBitmap() != null) {
            notProfilePhoto.setVisibility(View.GONE);
            profilePhoto.setVisibility(View.VISIBLE);
            profilePhoto.setImageBitmap(loginResponse.getCompany().getImageBitmap());
        } else {
            profilePhoto.setVisibility(View.GONE);
            notProfilePhoto.setVisibility(View.VISIBLE);
            notProfilePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //displayView(R.id.draw_management_firm);
                }
            });
        }*/

        if (loginResponse != null && loginResponse.getUser() != null) {
            nameSurname.setText(loginResponse.getUser().getFullName());
            companyName.setText(loginResponse.getCompany().getName());
        } else {
            Toasty.error(this, getString(R.string.error)).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        if (loginResponse != null) {

            if (loginResponse.getCompany().getImage() != null) {

                loginResponse.getCompany().setImage(null);
            }
            Utils.saveCompanyLogo(context, companyimage);
            DataUtil.post(loginResponse);
        }

        setNotificationConfig(true);
        displayView(R.id.draw_sale);
    }

    @OnClick(R.id.toolbar_left_icon)
    public void leftIconClick() {
        drawer.openDrawer(GravityCompat.START);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.draw_sale, R.id.draw_purchase, R.id.draw_collection, R.id.draw_payment, R.id.draw_product,
            R.id.draw_firm, R.id.draw_management_firm,
            R.id.draw_support, R.id.draw_logout, R.id.Setting_Layout})
    public void drawClick(View v) {
        displayView(v.getId());
    }

    @OnClick(R.id.draw_invoices)
    public void drawManagementClick() {
        if (!drawInvoiceKey) {
            invoiceCollapse.setVisibility(View.GONE);
            invoiceExpand.setVisibility(View.VISIBLE);
            invoicesSublistLL.setVisibility(View.GONE);
            drawInvoiceKey = true;
        } else {
            invoiceCollapse.setVisibility(View.VISIBLE);
            invoiceExpand.setVisibility(View.GONE);
            invoicesSublistLL.setVisibility(View.VISIBLE);
            drawInvoiceKey = false;
        }
    }

    @OnClick(R.id.draw_payment_collection)
    public void drawPaymentClick() {
        if (drawpaymentKey) {

            paymentCollapse.setVisibility(View.GONE);
            paymentExpand.setVisibility(View.VISIBLE);
            payment_sublist_layout.setVisibility(View.VISIBLE);
            drawpaymentKey = false;
        } else {
            paymentCollapse.setVisibility(View.VISIBLE);
            paymentExpand.setVisibility(View.GONE);
            payment_sublist_layout.setVisibility(View.GONE);
            drawpaymentKey = true;
        }
    }


    private void displayView(int id) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (id) {
            case R.id.draw_sale:
                setToolbarConfig(getString(R.string.sales));
                SalesFragment salesFragment = new SalesFragment();
                fragmentTransaction.replace(R.id.frame, salesFragment);
                mainFragmentId = id;
                break;
            case R.id.draw_purchase:
                setToolbarConfig(getString(R.string.purchases));
                PurchasesFragment purchaseFragment = new PurchasesFragment();
                fragmentTransaction.replace(R.id.frame, purchaseFragment);
                mainFragmentId = id;
                break;
            case R.id.draw_collection:
                setToolbarConfig(getString(R.string.Collection));
                CollectionFragment collectionFragment = new CollectionFragment();
                fragmentTransaction.replace(R.id.frame, collectionFragment);
                mainFragmentId = id;
                break;
            case R.id.draw_payment:
                setToolbarConfig(getString(R.string.payments));
                PaymentFragment paymentFragments = new PaymentFragment();
                fragmentTransaction.replace(R.id.frame, paymentFragments);
                mainFragmentId = id;
                break;
            case R.id.draw_product:
                setToolbarConfig(getString(R.string.products));
                ProductsFragment productsFragment = new ProductsFragment();
                fragmentTransaction.replace(R.id.frame, productsFragment);
                mainFragmentId = id;
                break;
            case R.id.draw_firm:
                setToolbarConfig(getString(R.string.firms));
                FirmsFragment firmsFragment = new FirmsFragment();
                fragmentTransaction.replace(R.id.frame, firmsFragment);
                mainFragmentId = id;
                break;
            /*case R.id.draw_management_profile:
                openIntent(EditProfileActivity.class);
                break;*/
            case R.id.draw_management_firm:
                openIntent(EditCompanyActivity.class);
                break;
            case R.id.Setting_Layout:
                openSeetingPopup();
                break;
            case R.id.draw_support:
                openSupport();
                break;
            case R.id.draw_logout:
                logout();
            default:
                break;
        }

        fragmentTransaction.commit();
        drawer.closeDrawer(GravityCompat.START);
    }

    private void logout() {
        Utils.generateDialog(this, getString(R.string.logout), getString(R.string.are_you_sure),
                getString(R.string.logout), new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Utils.showLoading(MainActivity.this);
                        setNotificationConfig(false);
                    }
                },
                getString(R.string.cancel), null);
    }

    private void logoutCall() {
        Utils.setObjectSharedPreferencesValue(this, null, Constants.LOGIN_INFO);
        MobileConstants.accessToken = "";
        ServiceRequest apiService = ServiceCreator.getClient().create(ServiceRequest.class);
        Call<BaseModel<String>> call = apiService.logout();
        call.enqueue(new Callback<BaseModel<String>>() {
            @Override
            public void onResponse(Call<BaseModel<String>> call, Response<BaseModel<String>> response) {
                Utils.hideLoading();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<BaseModel<String>> call, Throwable ignored) {
                Utils.hideLoading();
                finish();
            }
        });
    }


    private void openSupport() {
        drawer.closeDrawer(GravityCompat.START);

        //Intent intent = new Intent(MainActivity.this, JetlinkChatActivity.class);
        //startActivity(intent);

        /*Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                UserAttributes userAttributes = Utils.createUserAttributes(loginDTO);
                Intercom.client().setLauncherVisibility(Intercom.Visibility.GONE);
                Intercom.client().setUserHash(SecureUtil.getHmacSHA(String.valueOf(user.getId())));
                Intercom.client().registerIdentifiedUser(Registration.create().withUserId(String.valueOf(user.getId())));
                Intercom.client().updateUser(userAttributes);
                Intercom.client().displayConversationsList();
            }
        }, 400);*/
    }

    private void openIntent(final Class Intent) {
        drawer.closeDrawer(GravityCompat.START);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivityForResult(new Intent(context, Intent), Constants.REFRESH_CODE);
            }
        }, 400);
    }

    private void openSeetingPopup() {
        drawer.closeDrawer(GravityCompat.START);
        updatelogo = new UpdateLogo();
        final Dialog dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.setting_popup);

        LinearLayout Add_Company_logo = (LinearLayout) dialog.findViewById(R.id.Add_Company_logo);
        LinearLayout Add_autho_signature = (LinearLayout) dialog.findViewById(R.id.Add_autho_signature);
        LinearLayout Add_promo_code = (LinearLayout) dialog.findViewById(R.id.Add_promo_code);
        LinearLayout change_pass = (LinearLayout) dialog.findViewById(R.id.change_pass);
        final LinearLayout DeactiveAccount = (LinearLayout) dialog.findViewById(R.id.DeactiveAccount);
        LinearLayout Default_accpunts = (LinearLayout) dialog.findViewById(R.id.Default_accpunts);
        LinearLayout mswipe = (LinearLayout) dialog.findViewById(R.id.mswipe);


        Add_Company_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GenrateTokenForPortal();
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.setting_update_popup, viewGroup, false);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(dialogView);
                TextView title = (TextView) dialogView.findViewById(R.id.title);
                title.setText("Company logo");
                final AlertDialog alertDialog = builder.create();
                final LinearLayout Updatelogos = (LinearLayout) dialogView.findViewById(R.id.update_logo);
                Updatelogos.setVisibility(View.VISIBLE);
                ImageView closedialog = (ImageView) dialogView.findViewById(R.id.closes);
                update_logo_img = (ImageView) dialogView.findViewById(R.id.update_logo_img);
                Button update_btn = (Button) dialogView.findViewById(R.id.update_btn);
                Button remove_btn = (Button) dialogView.findViewById(R.id.remove_btn);

                selectImagType="companyLogo";

                closedialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                update_logo_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectImagType = "companyLogo";

                        ViewGroup viewGroup = findViewById(android.R.id.content);
                        View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.photo_selection, viewGroup, false);
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setView(dialogView);
                        builder.setTitle("Add Photo");
                        final AlertDialog alertDialog = builder.create();
                        final ImageView camera = (ImageView) dialogView.findViewById(R.id.camera);
                        final ImageView gallery = (ImageView) dialogView.findViewById(R.id.gallery);
                        Button cancelselection=(Button)dialogView.findViewById(R.id.cancelselection);

                        camera.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.dismiss();
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                    requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA"}, Constants.PERMISSION_READ);
                                } else {
                                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                    startActivityForResult(cameraIntent, CAMERAPICK_IMAGE_REQUEST);
                                }
                            }
                        });
                        gallery.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.dismiss();
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.setType("image/*");
                                startActivityForResult(intent, PICK_IMAGE_REQUEST);
                            }
                        });
                        cancelselection.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog.show();

                    }
                });
                update_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {



                        updatelogo.setId(userdro.getUserTenant().getTenant().getId());
                        updatelogo.setName(userdro.getUserTenant().getTenant().getName());
                        updatelogo.setTitle(userdro.getUserTenant().getTenant().getTitle());
                        updatelogo.setCommercialTitle(userdro.getUserTenant().getTenant().getCommercialTitle());
                        updatelogo.setLogicalState(userdro.getUserTenant().getTenant().getLogicalState());
                        updatelogo.setCode(userdro.getUserTenant().getTenant().getCode());
                        updatelogo.setFirmNo(userdro.getUserTenant().getTenant().getFirmNo());
                        updatelogo.setTaxNo(userdro.getUserTenant().getTenant().getTaxNo());
                        updatelogo.setUsers(null);
                        updatelogo.setProvince(userdro.getUserTenant().getTenant().getProvince());
                        updatelogo.setSector(userdro.getUserTenant().getTenant().getSector());
                        updatelogo.setIcon(userdro.getUserTenant().getTenant().getIcon());
                        updatelogo.setIconOther(userdro.getUserTenant().getTenant().getIconOther());
                        updatelogo.setCreationChannel(userdro.getUserTenant().getTenant().getCreationChannel());
                        updatelogo.setExtension(userdro.getUserTenant().getTenant().getExtension());


                        UpdateCompanyLogo(updatelogo, alertDialog);
                    }
                });
                remove_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updatelogo.setId(userdro.getUserTenant().getTenant().getId());
                        updatelogo.setName(userdro.getUserTenant().getTenant().getName());
                        updatelogo.setTitle(userdro.getUserTenant().getTenant().getTitle());
                        updatelogo.setCommercialTitle(userdro.getUserTenant().getTenant().getCommercialTitle());
                        updatelogo.setLogicalState(userdro.getUserTenant().getTenant().getLogicalState());
                        updatelogo.setCode(userdro.getUserTenant().getTenant().getCode());
                        updatelogo.setFirmNo(userdro.getUserTenant().getTenant().getFirmNo());
                        updatelogo.setTaxNo(userdro.getUserTenant().getTenant().getTaxNo());
                        updatelogo.setUsers(null);
                        updatelogo.setProvince(userdro.getUserTenant().getTenant().getProvince());
                        updatelogo.setSector(userdro.getUserTenant().getTenant().getSector());
                        updatelogo.setCreationChannel(userdro.getUserTenant().getTenant().getCreationChannel());
                        updatelogo.setExtension(userdro.getUserTenant().getTenant().getExtension());
                        if (selectImagType.equalsIgnoreCase("companyLogo")){
                            updatelogo.setIcon("");
                        }else{
                            updatelogo.setIconOther("");
                        }
                        RemoveCompanyLogo(updatelogo, alertDialog);
                    }
                });
                alertDialog.show();
            }
        });
        Add_autho_signature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GenrateTokenForPortal();
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.setting_update_popup, viewGroup, false);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(dialogView);
                TextView title = (TextView) dialogView.findViewById(R.id.title);
                title.setText("Authorisae Signature");
                final AlertDialog alertDialog = builder.create();
                LinearLayout Updatelogo = (LinearLayout) dialogView.findViewById(R.id.update_logo);
                Updatelogo.setVisibility(View.VISIBLE);
                ImageView closedialog = (ImageView) dialogView.findViewById(R.id.closes);
                update_logo_img = (ImageView) dialogView.findViewById(R.id.update_logo_img);
                Button update_btn = (Button) dialogView.findViewById(R.id.update_btn);
                Button remove_btn = (Button) dialogView.findViewById(R.id.remove_btn);
                selectImagType="AuthoriseSignature";
                closedialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                update_logo_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectImagType = "AuthoriseSignature";
                        ViewGroup viewGroup = findViewById(android.R.id.content);
                        View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.photo_selection, viewGroup, false);
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setView(dialogView);
                        builder.setTitle("Add Photo");
                        final AlertDialog alertDialog = builder.create();
                        final ImageView camera = (ImageView) dialogView.findViewById(R.id.camera);
                        final ImageView gallery = (ImageView) dialogView.findViewById(R.id.gallery);
                        Button cancelselection=(Button)dialogView.findViewById(R.id.cancelselection);

                        camera.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.dismiss();
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                    requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA"}, Constants.PERMISSION_READ);
                                } else {
                                     Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                    startActivityForResult(cameraIntent, CAMERAPICK_IMAGE_REQUEST);
                                }
                            }
                        });
                        gallery.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.dismiss();
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.setType("image/*");
                                startActivityForResult(intent, PICK_IMAGE_REQUEST);
                            }
                        });
                        cancelselection.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }
                });
                update_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updatelogo.setId(userdro.getUserTenant().getTenant().getId());
                        updatelogo.setName(userdro.getUserTenant().getTenant().getName());
                        updatelogo.setTitle(userdro.getUserTenant().getTenant().getTitle());
                        updatelogo.setCommercialTitle(userdro.getUserTenant().getTenant().getCommercialTitle());
                        updatelogo.setLogicalState(userdro.getUserTenant().getTenant().getLogicalState());
                        updatelogo.setCode(userdro.getUserTenant().getTenant().getCode());
                        updatelogo.setFirmNo(userdro.getUserTenant().getTenant().getFirmNo());
                        updatelogo.setTaxNo(userdro.getUserTenant().getTenant().getTaxNo());
                        updatelogo.setUsers(null);
                        updatelogo.setProvince(userdro.getUserTenant().getTenant().getProvince());
                        updatelogo.setSector(userdro.getUserTenant().getTenant().getSector());
                        updatelogo.setIcon(userdro.getUserTenant().getTenant().getIcon());
                        updatelogo.setIconOther(userdro.getUserTenant().getTenant().getIconOther());
                        updatelogo.setCreationChannel(userdro.getUserTenant().getTenant().getCreationChannel());
                        updatelogo.setExtension(userdro.getUserTenant().getTenant().getExtension());
                        UpdateCompanyLogo(updatelogo,alertDialog);
                    }
                });
                remove_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updatelogo.setId(userdro.getUserTenant().getTenant().getId());
                        updatelogo.setName(userdro.getUserTenant().getTenant().getName());
                        updatelogo.setTitle(userdro.getUserTenant().getTenant().getTitle());
                        updatelogo.setCommercialTitle(userdro.getUserTenant().getTenant().getCommercialTitle());
                        updatelogo.setLogicalState(userdro.getUserTenant().getTenant().getLogicalState());
                        updatelogo.setCode(userdro.getUserTenant().getTenant().getCode());
                        updatelogo.setFirmNo(userdro.getUserTenant().getTenant().getFirmNo());
                        updatelogo.setTaxNo(userdro.getUserTenant().getTenant().getTaxNo());
                        updatelogo.setUsers(null);
                        updatelogo.setProvince(userdro.getUserTenant().getTenant().getProvince());
                        updatelogo.setSector(userdro.getUserTenant().getTenant().getSector());
                        updatelogo.setCreationChannel(userdro.getUserTenant().getTenant().getCreationChannel());
                        updatelogo.setExtension(userdro.getUserTenant().getTenant().getExtension());
                        if (selectImagType.equalsIgnoreCase("companyLogo")){
                            updatelogo.setIcon("");
                        }else{
                            updatelogo.setIconOther("");
                        }
                        RemoveCompanyLogo(updatelogo,alertDialog);
                    }
                });
                alertDialog.show();
            }
        });
        Add_promo_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.setting_update_popup, viewGroup, false);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(dialogView);
                TextView title = (TextView) dialogView.findViewById(R.id.title);
                title.setText("Promo Code");
                promoalertDialog = builder.create();
                GenrateTokenForPortal();
                PromocodeType="PromoCode";
                LinearLayout promocode_layout = (LinearLayout) dialogView.findViewById(R.id.promocode_layout);
                promocode_layout.setVisibility(View.VISIBLE);
                ImageView closedialog = (ImageView) dialogView.findViewById(R.id.closes);
                  update_promocode = (EditText) dialogView.findViewById(R.id.update_promocode);
                update_promo_distributor = (EditText) dialogView.findViewById(R.id.update_promo_distributor);
                Button valid_promo = (Button) dialogView.findViewById(R.id.valid_promo);
                Button update_promo = (Button) dialogView.findViewById(R.id.update_promo);



                closedialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        promoalertDialog.dismiss();
                    }
                });
                valid_promo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        getPromoCodeValidate(update_promocode.getText().toString());
                    }
                });
                update_promo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PromocodeUpdate="PromoCodeUpdate";
                        GenrateTokenForPortal();
                          update_promos = update_promocode.getText().toString().trim();

                    }
                });

                promoalertDialog.show();
            }
        });
        change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.setting_update_popup, viewGroup, false);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(dialogView);
                TextView title = (TextView) dialogView.findViewById(R.id.title);
                title.setText("Change Password");
                final AlertDialog alertDialog = builder.create();
                GenrateTokenForPortal();
                LinearLayout changepass_layout = (LinearLayout) dialogView.findViewById(R.id.changepass_layout);
                changepass_layout.setVisibility(View.VISIBLE);
                ImageView closedialog = (ImageView) dialogView.findViewById(R.id.closes);
                final EditText existing_pass = (EditText) dialogView.findViewById(R.id.existing_pass);
                final EditText new_pass = (EditText) dialogView.findViewById(R.id.new_pass);
                final EditText confirm_new_pass = (EditText) dialogView.findViewById(R.id.confirm_new_pass);
                Button change_pass_btn = (Button) dialogView.findViewById(R.id.change_pass_btn);
                closedialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                change_pass_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String existpass=existing_pass.getText().toString().trim();
                        String new_pas=new_pass.getText().toString().trim();
                        String confirm_new_pas=confirm_new_pass.getText().toString().trim();
                        Boolean status=true;
                        if (existpass.equalsIgnoreCase("")){
                            status=false;
                            Toasty.error(MainActivity.this,"Enter Existing Password").show();
                        }else if(new_pas.equalsIgnoreCase("")){
                            status=false;
                            Toasty.error(MainActivity.this,"Enter New Password").show();
                        }else if(confirm_new_pas.equalsIgnoreCase("")){
                            status=false;
                            Toasty.error(MainActivity.this,"Enter Confirm Password").show();
                        }else if(!new_pas.equalsIgnoreCase(confirm_new_pas)){
                            status=false;
                            Toasty.error(MainActivity.this,"Password and confirm password does not match").show();
                        }
                        if (status) {
                            UpdatePassword updatepass= new UpdatePassword();
                            updatepass.setUsername(loginResponse.getUser().getEmail());
                            updatepass.setOldPassword(existpass);
                            updatepass.setNewPassword(confirm_new_pas);

                            ChangePassword(updatepass,alertDialog);
                        }
                    }
                });
                alertDialog.show();
            }
        });
        DeactiveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GenrateTokenForPortal();
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.setting_update_popup, viewGroup, false);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(dialogView);
                TextView title = (TextView) dialogView.findViewById(R.id.title);
                title.setText("Deactive Account");

                final AlertDialog alertDialog = builder.create();
                LinearLayout deactive_layout = (LinearLayout) dialogView.findViewById(R.id.deactive_layout);
                deactive_layout.setVisibility(View.VISIBLE);
                ImageView closedialog = (ImageView) dialogView.findViewById(R.id.closes);
                ImageView update_logo_img = (ImageView) dialogView.findViewById(R.id.update_logo_img);
                Button deactive_ok_btn = (Button) dialogView.findViewById(R.id.deactive_ok_btn);
                Button deactive_cancel_btn = (Button) dialogView.findViewById(R.id.deactive_cancel_btn);
                closedialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                deactive_ok_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            JSONObject jsn = new JSONObject();

                            jsn.put("", "0");
                            Deactiveaccount(jsn.toString(), alertDialog);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
                deactive_cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });



                        alertDialog.show();
                    }
                });
        Default_accpunts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCollectionType();
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.setting_update_popup, viewGroup, false);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(dialogView);
                TextView title = (TextView) dialogView.findViewById(R.id.title);
                title.setText("Default Accounts");
                final AlertDialog alertDialog = builder.create();
                LinearLayout defaultaccount = (LinearLayout) dialogView.findViewById(R.id.defaultaccount);
                defaultaccount.setVisibility(View.VISIBLE);
                bank_spinner=(Spinner)dialogView.findViewById(R.id.bank_spinner);
                cash_spinner=(Spinner)dialogView.findViewById(R.id.cash_spinner);
                collection_type_spinner=(Spinner)dialogView.findViewById(R.id.collection_type_spinner);
                ImageView closedialog = (ImageView) dialogView.findViewById(R.id.closes);
                Button default_cancel_btn=(Button)dialogView.findViewById(R.id.default_cancel_btn);
                Button default_ok_btn = (Button) dialogView.findViewById(R.id.default_ok_btn);



                final String[] bankid = new String[1];
                final String[]  cashid= new String[1];
                final String[] cardid= new String[1];
                bank_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        for (int ii=0;ii<arapNo.size();ii++){

                            if (arapNo.get(ii).getLineType().equalsIgnoreCase("BANK")){
                                bankid[0] =bank.get(i);
                            }


                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                cash_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        for (int ii=0;ii<arapNo.size();ii++){

                            if (arapNo.get(ii).getLineType().equalsIgnoreCase("CASH")){
                                cashid[0] =cash.get(i);
                            }


                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                collection_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        for (int ii=0;ii<arapNo.size();ii++){

                            if (arapNo.get(ii).getLineType().equalsIgnoreCase("CARD")){
                                cardid[0] =card.get(i);
                            }


                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                closedialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                default_cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                default_ok_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                      Login login= (Login) Utils.getObjectSharedPreferencesValue(MainActivity.this, Constants.LOGIN_INFO, Login.class);
                        DefaultAccount defaultaccount = new DefaultAccount();
                        defaultaccount.setBankid(bankid[0]);
                        defaultaccount.setCashid(cashid[0]);
                        defaultaccount.setCardid(cardid[0]);
                        defaultaccount.setUsername(login.getUserName());
                        for (int i =0;i<arapNo.size();i++){

                            if (arapNo.get(i).getDescription().equalsIgnoreCase(bankid[0])) {
                                defaultaccount.setBankcode(arapNo.get(i).getCode());
                            } else if (arapNo.get(i).getDescription().equalsIgnoreCase(cashid[0])) {
                                defaultaccount.setCashcode(arapNo.get(i).getCode());
                            } else if (arapNo.get(i).getDescription().equalsIgnoreCase(cardid[0])) {
                                defaultaccount.setCardcode(arapNo.get(i).getCode());
                            }

                        }

                        Utils.setObjectSharedPreferencesValue(MainActivity.this, defaultaccount, Constants.DEFAULT_ACCOUNT_INFO);
                        alertDialog.dismiss();
                        Toasty.success(MainActivity.this, getString(R.string.success)).show();

                    }
                });

                alertDialog.show();
            }
        });
                mswipe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        ViewGroup viewGroup = findViewById(android.R.id.content);
                        View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.setting_update_popup, viewGroup, false);
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setView(dialogView);
                        TextView title = (TextView) dialogView.findViewById(R.id.title);
                        title.setText("MSwipe");
                        final AlertDialog alertDialog = builder.create();
                        LinearLayout mswipe_layout = (LinearLayout) dialogView.findViewById(R.id.mswipe_layout);
                        mswipe_layout.setVisibility(View.VISIBLE);
                        ImageView closedialog = (ImageView) dialogView.findViewById(R.id.closes);
                        final EditText User_id = (EditText) dialogView.findViewById(R.id.User_id);
                        final EditText password = (EditText) dialogView.findViewById(R.id.password);
                        final EditText mobileno = (EditText) dialogView.findViewById(R.id.mobileno);
                        Button mswipe_btn = (Button) dialogView.findViewById(R.id.mswipe_btn);

                        Mswipe swipe = (Mswipe) Utils.getObjectSharedPreferencesValue(MainActivity.this, Constants.Mswipe_INFO, Mswipe.class);
                        if (swipe == null) {

                        } else {
                            User_id.setText(swipe.getUserid());
                            password.setText(swipe.getPass());
                            mobileno.setText(swipe.getMobile());
                        }

                        closedialog.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.dismiss();
                            }
                        });
                        mswipe_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String userid = User_id.getText().toString().trim();
                                String pass = password.getText().toString().trim();
                                String mobno = mobileno.getText().toString().trim();
                                boolean valid = true;
                                if (userid.equalsIgnoreCase("")) {
                                    valid = false;
                                    Toasty.error(MainActivity.this, getString(R.string.user_id)).show();
                                } else if (pass.equalsIgnoreCase("")) {
                                    valid = false;
                                    Toasty.error(MainActivity.this, getString(R.string.mpass)).show();

                                } else if (!mobno.equalsIgnoreCase("") && mobno.length() < 10) {
                                    valid = false;
                                    Toasty.error(MainActivity.this, getString(R.string.mmobile)).show();

                                }
                                if (valid) {
                                    Mswipe mswipe = new Mswipe();
                                    mswipe.setUserid(userid);
                                    mswipe.setPass(pass);
                                    mswipe.setMobile(mobno);
                                    Utils.setObjectSharedPreferencesValue(MainActivity.this, mswipe, Constants.Mswipe_INFO);
                                    Toasty.success(MainActivity.this, getString(R.string.success)).show();

                                    alertDialog.dismiss();

                                }

                            }
                        });

                        alertDialog.show();
                    }
                });


                dialog.show();


            }

            private void getUserData(String portalToken) {
                CommonService.getUserData(portalToken, new ServiceCallV<BaseModelV<UserDTO>>() {
                    @Override
                    public void start() {
                        Utils.showLoading(MainActivity.this);
                    }

                    @Override
                    public void onResponse(boolean isOnline, BaseModelV<UserDTO> response) {


                        if (!response.isError()) {
                            userdro = response.getData();
                            Bitmap bitmap = null;
                            if (selectImagType.equalsIgnoreCase("companyLogo")) {
                                try {
                                    byte[] encodeByte = Base64.decode(String.valueOf(userdro.getUserTenant().getTenant().getIcon()), Base64.DEFAULT);
                                    bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                                    update_logo_img.setImageBitmap(bitmap);

                                } catch (Exception e) {
                                    e.getMessage();
                                }
                            } else {
                                try {
                                    byte[] encodeByte = Base64.decode(String.valueOf(userdro.getUserTenant().getTenant().getIconOther()), Base64.DEFAULT);
                                    bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                                    update_logo_img.setImageBitmap(bitmap);

                                } catch (Exception e) {
                                    e.getMessage();
                                }
                            }
                            if (PromocodeType.equalsIgnoreCase("PromoCode")) {

                                if (PromocodeUpdate.equalsIgnoreCase("")) {
                                    Utils.hideLoading();
                                    PromocodeType = "";
                                    update_promocode.setText(userdro.getUserTenant().getTenant().getCreationChannel().substring(3));
                                    getPromoCodeValidate(userdro.getUserTenant().getTenant().getCreationChannel().substring(3));
                                    update_promo_distributor.setEnabled(false);
                                }

                            }
                            if (PromocodeUpdate.equalsIgnoreCase("PromoCodeUpdate")) {
                                Utils.hideLoading();
                                PromocodeUpdate = "";
                                getPromoCodeUpdate(update_promos, userdro.getUserTenant().getTenantId(), promoalertDialog);
                            }
                            Utils.hideLoading();


                            //Toasty.success(MainActivity.this, response.getMessage()).show();
                        } else {
                            Toasty.success(MainActivity.this, response.getMessage()).show();
                        }
                    }


                    @Override
                    public void onFailure(boolean isOnline, ErrorModel throwable) {
                        Utils.hideLoading();

                        Toasty.error(MainActivity.this, getString(R.string.error)).show();
                    }
                });
            }

            private void GenrateTokenForPortal() {

                Utils.showLoading(MainActivity.this);
                CommonService.getTokenForPortal(new ServiceCall<BaseModel<KeyValue>>() {
                    @Override
                    public void onResponse(boolean isOnline, BaseModel<KeyValue> response) {

                        Utils.hideLoading();
                        if (!response.isError()) {
                            if (response.getData() != null) {
                                MobileConstants.portalToken = response.getData().getKey();
                                Log.e("Portal Token", response.getData().getKey());

                                getUserData(MobileConstants.portalToken);

                            }
                        } else {
                            Toasty.error(MainActivity.this, response.getErrorDescription()).show();

                        }
                    }

                    @Override
                    public void onFailure(boolean isOnline, Throwable throwable) {
                        Utils.hideLoading();
                        Toasty.error(MainActivity.this, getString(R.string.error)).show();
                    }
                });


            }


            private void Deactiveaccount(String state, final AlertDialog alertDialog) {

                CommonService.Deactiveaccount(userdro.getUserTenant().getTenantId(), state, new ServiceCallV<BaseModelV<String>>() {
                    @Override
                    public void start() {
                        Utils.showLoading(MainActivity.this);
                    }

                    @Override
                    public void onResponse(boolean isOnline, BaseModelV<String> response) {
                        Utils.hideLoading();

                        if (!response.isError()) {

                            alertDialog.dismiss();
                            Intent i = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(i);

                            Toasty.success(MainActivity.this, response.getMessage()).show();
                        } else {
                            Toasty.success(MainActivity.this, response.getMessage()).show();
                        }
                    }


                    @Override
                    public void onFailure(boolean isOnline, ErrorModel throwable) {
                        Utils.hideLoading();

                        Toasty.error(MainActivity.this, getString(R.string.error)).show();
                    }
                });
            }

            private void ChangePassword(UpdatePassword updatepass, final AlertDialog alertDialog) {

                CommonService.UpdatePassword(updatepass, new ServiceCallV<BaseModelV<String>>() {
                    @Override
                    public void start() {
                        Utils.showLoading(MainActivity.this);
                    }

                    @Override
                    public void onResponse(boolean isOnline, BaseModelV<String> response) {
                        Utils.hideLoading();

                        if (!response.isError()) {
                            alertDialog.dismiss();
                            Toasty.success(MainActivity.this, response.getMessage()).show();
                            MobileConstants.portalToken = "";
                            Intent i = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(i);
                        } else {
                            Toasty.success(MainActivity.this, response.getMessage()).show();
                        }
                    }

                    @Override
                    public void onFailure(boolean isOnline, ErrorModel error) {
                        Utils.hideLoading();

                        Toasty.error(MainActivity.this, getString(R.string.error)).show();
                    }


                });
            }

            private void UpdateCompanyLogo(UpdateLogo updatelogo, final AlertDialog alertDialog) {
                CommonService.UpdateCompanyLogo(updatelogo, new ServiceCallV<BaseModelV<String>>() {
                    @Override
                    public void start() {
                        Utils.showLoading(MainActivity.this);
                    }

                    @Override
                    public void onResponse(boolean isOnline, BaseModelV<String> response) {
                        Utils.hideLoading();

                        if (!response.isError()) {
                            alertDialog.dismiss();
                            Toasty.success(MainActivity.this, response.getMessage()).show();
                        } else {
                            Toasty.success(MainActivity.this, response.getMessage()).show();
                        }
                    }


                    @Override
                    public void onFailure(boolean isOnline, ErrorModel throwable) {
                        Utils.hideLoading();

                        Toasty.error(MainActivity.this, getString(R.string.error)).show();
                    }
                });
            }

            private void RemoveCompanyLogo(UpdateLogo updatelogo, final AlertDialog alertDialog) {
                CommonService.UpdateCompanyLogo(updatelogo, new ServiceCallV<BaseModelV<String>>() {
                    @Override
                    public void start() {
                        Utils.showLoading(MainActivity.this);
                    }

                    @Override
                    public void onResponse(boolean isOnline, BaseModelV<String> response) {
                        Utils.hideLoading();

                        if (!response.isError()) {
                            update_logo_img.setImageBitmap(null);
                            alertDialog.dismiss();
                            Toasty.success(MainActivity.this, response.getMessage()).show();
                        } else {
                            Toasty.success(MainActivity.this, response.getMessage()).show();
                        }
                    }


                    @Override
                    public void onFailure(boolean isOnline, ErrorModel throwable) {
                        Utils.hideLoading();

                        Toasty.error(MainActivity.this, getString(R.string.error)).show();
                    }
                });
            }

            private void setCollectionType() {
                Utils.showLoading(MainActivity.this);
                ProductService.getCollectionType(new ServiceCall<BaseModel<List<CollectionTypeDTO>>>() {
                    @Override
                    public void onResponse(boolean isOnline, BaseModel<List<CollectionTypeDTO>> response) {

                        if (!response.isError()) {
                            List<CollectionTypeDTO> data = response.getData();
                            if (data == null) {
                                data = new ArrayList<>();
                            }
                            arapNo = data;
                            bank = new ArrayList<>();
                            cash = new ArrayList<>();
                            card = new ArrayList<>();
                            for (int i = 0; i < arapNo.size(); i++) {

                                if (arapNo.get(i).getLineType().equalsIgnoreCase("BANK")) {
                                    bank.add(arapNo.get(i).getDescription());
                                } else if (arapNo.get(i).getLineType().equalsIgnoreCase("CASH")) {
                                    cash.add(arapNo.get(i).getDescription());
                                } else if (arapNo.get(i).getLineType().equalsIgnoreCase("CARD")) {
                                    card.add(arapNo.get(i).getDescription());
                                }


                            }
                            DefaultAccount defaultAccount = (DefaultAccount) Utils.getObjectSharedPreferencesValue(MainActivity.this, Constants.DEFAULT_ACCOUNT_INFO, DefaultAccount.class);
                            if (defaultAccount != null&&defaultAccount.getBankid()!=null) {
                                for (int ii = 0; ii < arapNo.size(); ii++) {
                                    if (arapNo.get(ii).getDescription().equalsIgnoreCase(defaultAccount.getBankid())) {
                                        ArrayAdapter<String> bankadapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, bank);
                                        bankadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        bank_spinner.setAdapter(bankadapter);
                                        int spinnerPosition = bankadapter.getPosition(defaultAccount.getBankid());
                                        bank_spinner.setSelection(spinnerPosition);
                                    }
                                    if (arapNo.get(ii).getDescription().equalsIgnoreCase(defaultAccount.getCashid())) {
                                        ArrayAdapter<String> cashadapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, cash);
                                        cashadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        cash_spinner.setAdapter(cashadapter);
                                        int spinnerPosition = cashadapter.getPosition(defaultAccount.getCashid());
                                        cash_spinner.setSelection(spinnerPosition);
                                    }
                                    if (arapNo.get(ii).getDescription().equalsIgnoreCase(defaultAccount.getCardid())) {
                                        ArrayAdapter<String> cardadapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, card);
                                        cardadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        collection_type_spinner.setAdapter(cardadapter);
                                        int spinnerPosition = cardadapter.getPosition(defaultAccount.getCardid());
                                        collection_type_spinner.setSelection(spinnerPosition);
                                    }

                                }
                                Utils.hideLoading();
                            } else {


                                ArrayAdapter<String> bankadapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, bank);
                                bankadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                bank_spinner.setAdapter(bankadapter);
                                bank_spinner.setSelection(0);


                                ArrayAdapter<String> cashadapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, cash);
                                cashadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                cash_spinner.setAdapter(cashadapter);
                                cash_spinner.setSelection(0);


                                ArrayAdapter<String> cardadapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, card);
                                cardadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                collection_type_spinner.setAdapter(cardadapter);
                                collection_type_spinner.setSelection(0);
                                Utils.hideLoading();
                            }

                        } else {
                            Utils.hideLoading();
                            Toasty.error(MainActivity.this, response.getErrorDescription()).show();
                        }
                    }

                    @Override
                    public void onFailure(boolean isOnline, Throwable throwable) {
                        Utils.hideLoading();
                        Toasty.error(MainActivity.this, getString(R.string.error)).show();
                    }
                });
            }

            private void getPromoCodeUpdate(String promocode, String tententid, final AlertDialog alertDialog) {

                CommonService.validPromoUpdate(promocode, tententid, new ServiceCallV<BaseModelV<String>>() {
                    @Override
                    public void start() {
                        Utils.showLoading(MainActivity.this);
                    }

                    @Override
                    public void onResponse(boolean isOnline, BaseModelV<String> response) {
                        Utils.hideLoading();

                        if (!response.isError()) {
                            alertDialog.dismiss();
                            Toasty.success(MainActivity.this, response.getMessage()).show();
                        } else {
                            Toasty.success(MainActivity.this, response.getMessage()).show();
                        }
                    }


                    @Override
                    public void onFailure(boolean isOnline, ErrorModel throwable) {
                        Utils.hideLoading();

                        Toasty.error(MainActivity.this, getString(R.string.invalid_promo_code)).show();
                    }
                });
            }

            private void getPromoCodeValidate(String promocode) {

                CommonService.validPromoCode(promocode, new ServiceCall<BaseModelV<promocodeDTO>>() {
                    @Override
                    public void start() {
                        Utils.showLoading(MainActivity.this);
                    }

                    @Override
                    public void onResponse(boolean isOnline, BaseModelV<promocodeDTO> response) {
                        Utils.hideLoading();

                        if (!response.isError()) {
                            promocodedto = response.getData();
                            update_promo_distributor.setText(promocodedto.getCode() + "-" + promocodedto.getName());

                            Toasty.success(MainActivity.this, response.getMessage()).show();
                        } else {
                            Toasty.success(MainActivity.this, response.getMessage()).show();
                        }
                    }


                    @Override
                    public void onFailure(boolean isOnline, Throwable throwable) {
                        Utils.hideLoading();

                        Toasty.error(MainActivity.this, getString(R.string.invalid_promo_code)).show();
                    }
                });

            }

            private void setNotificationConfig(final boolean status) {
                OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
                    @Override
                    public void idsAvailable(String userId, String registrationId) {
                        NotificationConfig nf = new NotificationConfig(userId, registrationId, status);
                        CommonService.setNotificationConfig(nf, new ServiceCall<BaseModel<Boolean>>() {
                            @Override
                            public void onResponse(boolean isOnline, BaseModel<Boolean> response) {
                                if (!status) {
                                    logoutCall();
                                }
                            }

                            @Override
                            public void onFailure(boolean isOnline, Throwable throwable) {

                            }
                        });
                    }
                });
            }

            @Override
            public void onBackPressed() {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    if (mainFragmentDefaultId != mainFragmentId) {
                        displayView(mainFragmentDefaultId);
                    } else {
                        if (doubleBackToExitPressedOnce) {
                            super.onBackPressed();
                            return;
                        }

                        this.doubleBackToExitPressedOnce = true;
                        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                doubleBackToExitPressedOnce = false;
                            }
                        }, 2000);
                    }
                }
            }

            @Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
                if (requestCode == Constants.REFRESH_CODE) {
                    if (resultCode == Activity.RESULT_OK) {
                        LoginDTO loginResponse = DataUtil.getBundle(LoginDTO.class);
                        if (loginResponse.getUser() != null) {
                            companyName.setText(loginResponse.getCompany().getName());
                        }
                    }
                } else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
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
                } else if (requestCode == MSWIPE_CARDSALE_ACTIVITY_REQUEST_CODE) {

                    boolean status = data.getBooleanExtra("status", false);
                    String statusMessage = data.getStringExtra("statusMessage");
                    String receiptDetail = data.getStringExtra("receiptDetail");

                    if (resultCode == RESULT_OK) {
                        if (AppSharedPrefrences.getAppSharedPrefrencesInstace().isSignatureRequired()) {
                            Intent intent = new Intent(MainActivity.this, MswipeSignatureActivity.class);
                            intent.putExtra("Title", "card sale");
                            intent.putExtra("receiptDetail", receiptDetail);
                            intent.putExtra("cardSaleResponseData", data.getSerializableExtra("cardSaleResponseData"));
                            startActivityForResult(intent, MSWIPE_CARDSALE_SIGNATURE_REQUEST);
                            CreateInvoiceActivity.paymentstatus = "success";
                            InvoicesAdapter.Updateui();


                        } else {
                            showapproveDialog(Boolean.toString(status), data.getExtras().getString("AuthCode"),
                                    data.getExtras().getString("RRNo"), statusMessage);
                        }
                    } else {

                        Intent intent = new Intent(MainActivity.this, MswipeDeclineActivity.class);
                        intent.putExtra("statusMessage", statusMessage);
                        intent.putExtra("cardSaleResponseData", data.getSerializableExtra("cardSaleResponseData"));
                        intent.putExtra("Title", MainActivity.this.getResources().getString(R.string.card_sale));
                        startActivityForResult(intent, MSWIPE_CARDSALE_DECLINE_REQUEST);
                        CreateInvoiceActivity.paymentstatus = "Fail";

                    }
                } else if (requestCode == CAMERAPICK_IMAGE_REQUEST && resultCode == RESULT_OK) {

                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    File filesDir = MainActivity.this.getFilesDir();
                    File imageFile = new File(filesDir,   "camerapic.jpg");

                    OutputStream os;
                    try {
                        os = new FileOutputStream(imageFile);

                        photo.compress(Bitmap.CompressFormat.JPEG, 100, os);
                        os.flush();
                        os.close();
                    } catch (Exception e) {
                        Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
                    }

                    File actualFile = new File(String.valueOf(imageFile));
                    setCompressedImage(actualFile);
                } else {
                    CreateInvoiceActivity.paymentstatus = "";
                }
            }

            public void showapproveDialog(String status, String authcode, String rrno, String reason) {

                final Dialog dialog = new Dialog(MainActivity.this, R.style.styleCustDlg);
                dialog.setContentView(R.layout.cardsale_status_customdlg);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(true);

                TextView txtstatusmsg = (TextView) dialog.findViewById(R.id.customdlg_Txt_status);
                txtstatusmsg.setText(status);

                TextView txtauthcode = (TextView) dialog.findViewById(R.id.customdlg_Txt_authcode);
                txtauthcode.setText(authcode);

                TextView txtrrno = (TextView) dialog.findViewById(R.id.customdlg_Txt_rrno);
                txtrrno.setText(rrno);

                TextView txtreason = (TextView) dialog.findViewById(R.id.customdlg_Txt_reason);
                txtreason.setText(reason);

                Button yes = (Button) dialog.findViewById(R.id.customdlg_BTN_yes);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        finish();
                        Intent intent = new Intent(MainActivity.this, MenuView.class);
                        startActivity(intent);
                    }
                });

                dialog.show();
            }

            private void setCompressedImage(File actualFile) {
                try {
                    File imageZipperFile = new ImageZipper(MainActivity.this).setQuality(10).setMaxWidth(200).setMaxHeight(200).compressToFile(actualFile);
                    //           int result_file_size = Integer.parseInt(String.valueOf(imageZipperFile.length() / 1024));
                    //           Toast.makeText(mContext, "" + result_file_size + " Kb", Toast.LENGTH_LONG).show();
                    Bitmap b = BitmapFactory.decodeFile(imageZipperFile.getAbsolutePath());


                    update_logo_img.setImageBitmap(b);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    b.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    if (selectImagType.equalsIgnoreCase("companyLogo")) {
                        userdro.getUserTenant().getTenant().setIcon(encoded);
                    } else {
                        userdro.getUserTenant().getTenant().setIconOther(encoded);
                    }


                } catch (Exception e) {

                }
            }

            @Override
            public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
                switch (requestCode) {
                    case Constants.PERMISSION_READ: {
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            try {

                                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(cameraIntent, CAMERAPICK_IMAGE_REQUEST);
                            } catch (Exception e) {
Log.e("camera error",e.toString());
                            }
                        } else {
                            Toasty.error(MainActivity.this, getString(R.string.permission_warn)).show();
                        }
                    }
                    case MEDIA_TYPE_IMAGES: {
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                        } else {
                            Toasty.error(MainActivity.this, getString(R.string.permission_warn)).show();
                        }
                    }
                }
            }

        }
