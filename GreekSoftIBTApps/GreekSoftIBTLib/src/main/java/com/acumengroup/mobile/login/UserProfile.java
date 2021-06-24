package com.acumengroup.mobile.login;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.model.MutualFundSipNewOrder.MFundMediaAlreadyUploadResponce;
import com.acumengroup.greekmain.core.model.MutualFundSipNewOrder.MFundMediaUploadResponce;
import com.acumengroup.greekmain.core.model.UserDetail.UserProfileRequest;
import com.acumengroup.greekmain.core.model.portfoliotrending.AllowedProduct;
import com.acumengroup.greekmain.core.network.WSHandler;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.MutualFund.MarshMallowPermission;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.trade.SingleUploadBroadcastReciever;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.textview.GreekTextView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.acumengroup.mobile.GreekBaseActivity.GREEK;
import static com.acumengroup.mobile.trade.KYCUploadPhoto.CAMERA_REQUEST;
import static com.acumengroup.mobile.trade.KYCUploadPhoto.GALLERY_PICTURE;

public class UserProfile extends GreekBaseFragment implements SingleUploadBroadcastReciever.Delegate {

    private View userprofilrview;
    private GreekTextView usernametxt, userIdTxt, bankNameTxt, acNumberTxt, panTxt, dematTxt, segmentTxt, productTxt, emailIdTXT, mobileTXT, personalDetails, registerMobile, registerEmail, panDetails, bankDetails, bankName, account_no, account_details, demat, product, segment;
    private static CircleImageView uploadImg;
    public static Bitmap bitmap = null;
    private static File destination;
    private MarshMallowPermission marshMallowPermission;
    private static AppCompatActivity activity;
    private LinearLayout userbg, personal_details_bg, account_detail_bg, bank_detail_bg;

    private View vLine1, vLine2, vLine3, vLine4, vLine5, vLine6;

    private boolean IsUploadReceiverRegister = false;

    private final SingleUploadBroadcastReciever uploadReceiver = new SingleUploadBroadcastReciever();
    private MFundMediaAlreadyUploadResponce mFundMediaAlreadyUploadResponce;
    private MFundMediaUploadResponce mFundMediaUploadResponce;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        userprofilrview = super.onCreateView(inflater, container, savedInstanceState);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.user_profile_activity).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.user_profile_activity).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }
        userprofilrview = inflater.inflate(R.layout.user_profile_activity, container, false);
        AccountDetails.currentFragment = NAV_TO_USER_SCREEN;
        setupViews(userprofilrview);
        setTheme();
        getProducttype();
        senduserprofileRequest();
//        ProfilePicUploadRequest.sendRequestUpload(AccountDetails.getUsername(getMainActivity()), "5", AccountDetails.getUserPAN(getMainActivity()), getMainActivity(), serviceResponseHandler);

        String showPDFUrl = AccountDetails.getIsSecure() + "://" + AccountDetails.getArachne_IP() + ":" + AccountDetails.getArachne_Port() + "/downloadFileV2?imageName=ClientImage_" + AccountDetails.getUsername(getActivity());
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            Picasso.with(getMainActivity()).load(showPDFUrl + ".jpeg").networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).error(R.drawable.ic_person_black_24dp).into(uploadImg);
        }else {
            Picasso.with(getMainActivity()).load(showPDFUrl + ".jpeg").networkPolicy(NetworkPolicy.NO_CACHE).
                    memoryPolicy(MemoryPolicy.NO_CACHE).error(R.drawable.ic_person_white_24dp).into(uploadImg);
        }
        return userprofilrview;
    }

    private void setTheme() {
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {

            userbg.setBackgroundColor(getResources().getColor(R.color.white));
//            account_detail_bg.setBackgroundColor(getResources().getColor(R.color.buttonColor));
//            personal_details_bg.setBackgroundColor(getResources().getColor(R.color.buttonColor));
//            bank_detail_bg.setBackgroundColor(getResources().getColor(R.color.buttonColor));

            vLine1.setBackgroundColor(getResources().getColor(R.color.black));
            vLine2.setBackgroundColor(getResources().getColor(R.color.black));
            vLine3.setBackgroundColor(getResources().getColor(R.color.black));
            vLine4.setBackgroundColor(getResources().getColor(R.color.black));
            vLine5.setBackgroundColor(getResources().getColor(R.color.black));
            vLine6.setBackgroundColor(getResources().getColor(R.color.black));

            usernametxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            userIdTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            bankNameTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            acNumberTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            panTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            dematTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            segmentTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            productTxt.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            emailIdTXT.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            mobileTXT.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
//            personalDetails.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            registerMobile.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            registerEmail.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            panDetails.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
//            bankDetails.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            bankName.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            account_no.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
//            account_details.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            demat.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            product.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
            segment.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (uploadReceiver != null)
            uploadReceiver.register(getMainActivity());

        IsUploadReceiverRegister = true;
    }


    @Override
    public void onPause() {
        super.onPause();
        if (uploadReceiver != null && IsUploadReceiverRegister)
            uploadReceiver.unregister(getMainActivity());
        IsUploadReceiverRegister = false;

    }

    @Override
    public void onFragmentPause() {
        super.onFragmentPause();
        if (uploadReceiver != null && IsUploadReceiverRegister)
            uploadReceiver.unregister(getMainActivity());
        IsUploadReceiverRegister = false;
    }

    private void setupViews(View userprofilrview) {
        serviceResponseHandler = new ServiceResponseHandler(getMainActivity(), this);

        marshMallowPermission = new MarshMallowPermission(getMainActivity());
        activity = getMainActivity();
        userbg = userprofilrview.findViewById(R.id.userbg);
        personal_details_bg = userprofilrview.findViewById(R.id.personal_detail_bg);
        account_detail_bg = userprofilrview.findViewById(R.id.account_details_bg);
        bank_detail_bg = userprofilrview.findViewById(R.id.bank_details_bg);
        usernametxt = userprofilrview.findViewById(R.id.user_name);
        userIdTxt = userprofilrview.findViewById(R.id.user_id);
        bankNameTxt = userprofilrview.findViewById(R.id.txt_bank_name);
        acNumberTxt = userprofilrview.findViewById(R.id.txt_ac_number);
        panTxt = userprofilrview.findViewById(R.id.txt_pan);
        dematTxt = userprofilrview.findViewById(R.id.txt_demat);
        productTxt = userprofilrview.findViewById(R.id.txt_product);
        segmentTxt = userprofilrview.findViewById(R.id.txt_segment);
        emailIdTXT = userprofilrview.findViewById(R.id.txt_email);
        mobileTXT = userprofilrview.findViewById(R.id.txt_mobile);
        uploadImg = userprofilrview.findViewById(R.id.upload_img);
        bankName = userprofilrview.findViewById(R.id.bank_name1);
        personalDetails = userprofilrview.findViewById(R.id.personal_detail);
        registerMobile = userprofilrview.findViewById(R.id.register_mobile);
        registerEmail = userprofilrview.findViewById(R.id.register_email);
        panDetails = userprofilrview.findViewById(R.id.pan_details);
        bankDetails = userprofilrview.findViewById(R.id.bank_details);
        account_details = userprofilrview.findViewById(R.id.account_details);
        account_no = userprofilrview.findViewById(R.id.account_number);
        demat = userprofilrview.findViewById(R.id.demat);
        product = userprofilrview.findViewById(R.id.products);
        segment = userprofilrview.findViewById(R.id.segment);

        vLine1 = userprofilrview.findViewById(R.id.vLine1);
        vLine2 = userprofilrview.findViewById(R.id.vLine2);
        vLine3 = userprofilrview.findViewById(R.id.vLine3);
        vLine4 = userprofilrview.findViewById(R.id.vLine4);
        vLine5 = userprofilrview.findViewById(R.id.vLine5);
        vLine6 = userprofilrview.findViewById(R.id.vLine6);

        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogForSelectImage();
            }
        });


    }


   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK && data != null) {

            onCapturePhotoResult(data);

        } else if (resultCode == RESULT_OK && requestCode == GALLERY_PICTURE && data != null) {
            if (data != null) {

                onSelectPhotoFromGalleryResult(data);

            } else {
                Toast.makeText(getMainActivity(), "Some thing wrong happened ", Toast.LENGTH_SHORT).show();
            }
        }

    }*/

    public static void onSelectPhotoFromGalleryResult(Intent data, Context activity) {
        Uri filePath = data.getData();
        try {
            bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }


        // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(activity.getContentResolver(), bitmap, "Title", null);

        Uri tempUri = data.getData();

        // CALL THIS METHOD TO GET THE ACTUAL PATH
        destination = new File(getRealPathFromURI(tempUri,activity));


        if (bitmap != null) {

         /*   uploadImg.setVisibility(View.VISIBLE);
            uploadImg.setImageBitmap(bitmap);*/

//            new UserProfile().SubmitAAdharDetails();
        }

    }


    public static Bitmap getBitmapofPhoto(Intent data) {
        Uri selectedImage = data.getData();
        String[] filePath = {MediaStore.Images.Media.DATA};
        Cursor c = activity.getContentResolver().query(selectedImage, filePath, null, null, null);
        c.moveToFirst();
        int columnIndex = c.getColumnIndex(filePath[0]);
        String picturePath = c.getString(columnIndex);
        c.close();
        Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
        return thumbnail;
    }

    public static String getRealPathFromURI(Uri uri, Context activity) {
        Cursor cursor = activity.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
    public static String getRealPathFromDocumentUri(Context context, Uri uri){
        String filePath = "";

        Pattern p = Pattern.compile("(\\d+)$");
        Matcher m = p.matcher(uri.toString());
        if (!m.find()) {
            return filePath;
        }
        String imgId = m.group();

        String[] column = { MediaStore.Images.Media.DATA };
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{ imgId }, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();

        return filePath;
    }
    public static void onCapturePhotoResult(Intent data, Activity activity) {
        bitmap = (Bitmap) data.getExtras().get("data");


        // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(activity.getContentResolver(), bitmap, "Title", null);

        Uri tempUri = Uri.parse(path);

        // CALL THIS METHOD TO GET THE ACTUAL PATH
        destination = new File(getRealPathFromURI(tempUri,activity));


        ExifInterface ei = null;
        try {
            ei = new ExifInterface(destination.toString());
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

            switch (orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:

                    bitmap = RotateBitmap(bitmap, 90);

                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    bitmap = RotateBitmap(bitmap, 180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    bitmap = RotateBitmap(bitmap, 270);
                    break;
                default:
                    break;

            }
        } catch (IOException e) {

            e.printStackTrace();
        }
        if (bitmap != null) {


          /*  uploadImg.setVisibility(View.VISIBLE);
            uploadImg.setImageBitmap(bitmap);*/

//            new UserProfile().SubmitAAdharDetails();

        }

    }

    private Uri getImageUri(AppCompatActivity userProfileClass, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getMainActivity().getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);

    }

    private void senduserprofileRequest() {
        UserProfileRequest.sendRequest(AccountDetails.getClientCode(getMainActivity()), AccountDetails.getUsername(getMainActivity()), getMainActivity(), serviceResponseHandler);
        sendForClientDp();
    }

    private void sendForClientDp() {
        showProgress();

        WSHandler.getRequest(getMainActivity(), "getClientDpId?gscid=" + AccountDetails.getUsername(getMainActivity()), new WSHandler.GreekResponseCallback() {
            @Override
            public void onSuccess(JSONObject response) {
                hideProgress();
                Log.e("LASFragment", "response=======>" + response);
                //{"success":"true","message":"","ErrorCode":0,"data":{"DPID":"1208860000000310"}}

                try {

                    String DPID = String.valueOf(response.getJSONArray("data").getJSONObject(0).getString("DPID"));
                    dematTxt.setText(DPID);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String message) {
                hideProgress();

            }
        });


    }


    @Override
    public void handleResponse(Object response) {

        JSONResponse jsonResponse = (JSONResponse) response;


        try {

//                UserProfileResponse userProfileResponse = (UserProfileResponse)jsonResponse.getResponse();

//                JSONObject jsonObject2_data = jsonResponse.getJSONObject("data");
            if ("userprofile_bajaj".equals(jsonResponse.getServiceGroup()) && "getProfileDetails_Bajaj".equals(jsonResponse.getServiceName())) {
                JSONObject jsonObject = jsonResponse.getResObject();
                JSONObject jsonObject_response = jsonObject.getJSONObject("response");

                JSONArray data = jsonObject_response.getJSONArray("data");
                String product = "";
                for (int i = 0; i < data.length(); i++) {

                    JSONObject object = data.getJSONObject(i);
                    if (!object.getString("bankname").equalsIgnoreCase("null"))
                        bankNameTxt.setText(object.getString("bankname"));
                    if (!object.getString("bankacno").equalsIgnoreCase("null"))
                        acNumberTxt.setText(object.getString("bankacno"));
                    if (!object.getString("cpanno").equalsIgnoreCase("null"))
                        panTxt.setText(object.getString("cpanno"));
                    emailIdTXT.setText(object.getString("cemailid"));

                    /*if (object.getString("bisMF").equalsIgnoreCase("1")) {
                        product = "MF,";
                    }
                    if (object.getString("bSSEQ").equalsIgnoreCase("1")) {
                        product = product + "SSEQ,";
                    }
                    if (object.getString("bMTF").equalsIgnoreCase("1")) {
                        product = product + "MTF,";
                    }
                    if (object.getString("IntradayProductSetting").equalsIgnoreCase("1")) {
                        product = product + "Intraday,";
                    }
                    if (object.getString("DeliveryProductSetting").equalsIgnoreCase("1")) {
                        product = product + "Delivery";
                    }*/
                   /* for(int g=0;g<AccountDetails.getAllowedProduct().size();g++){
                        product = product +getProductType(AccountDetails.getAllowedProduct().get(g).getiProductToken())+ ",";
                    }*/
                   product=object.getString("AllowedProduct");

                    productTxt.setText(product);
                    segmentTxt.setText(object.getString("cmktallowed"));
                    usernametxt.setText(AccountDetails.getCLIENTNAME(getMainActivity()));
                    userIdTxt.setText(AccountDetails.getUsername(getMainActivity()));
                    if (!object.getString("lmobileno").equalsIgnoreCase("null"))
                        mobileTXT.setText(object.getString("lmobileno"));



               /* dematTxt.setText(userProfileResponse.get());
                bankNameTxt.setText(userProfileResponse.getBankname());
                bankNameTxt.setText(userProfileResponse.getBankname());
                bankNameTxt.setText(userProfileResponse.getBankname());
                bankNameTxt.setText(userProfileResponse.getBankname());
                bankNameTxt.setText(userProfileResponse.getBankname());*/
                }


            }


            if (UPLOAD_PHOTO_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && UPLOAD_PHOTO_SVC_GROUP.equals(jsonResponse.getServiceName())) {
                try {

                    mFundMediaUploadResponce = (MFundMediaUploadResponce) jsonResponse.getResponse();

                    String errorCode = mFundMediaUploadResponce.getErrorCode();

                    if (errorCode.equalsIgnoreCase("0")) {

                        GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Photo uploaded successfully.",
                                "Ok", false, new GreekDialog.DialogListener() {

                                    @Override
                                    public void alertDialogAction(GreekDialog.Action action, Object... data) {

                                        if (action == GreekDialog.Action.OK) {


                                        }
                                    }
                                });

                    } else {

                        GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Photo upload failed !",
                                "Ok", false, new GreekDialog.DialogListener() {

                                    @Override
                                    public void alertDialogAction(GreekDialog.Action action, Object... data) {

                                        if (action == GreekDialog.Action.OK) {

                                        }
                                    }
                                });
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if ("AlreadyPhotoUploadV2".equals(jsonResponse.getServiceName())) {
                try {

                    mFundMediaAlreadyUploadResponce = (MFundMediaAlreadyUploadResponce) jsonResponse.getResponse();

                    String errorCode = mFundMediaAlreadyUploadResponce.getErrorCode();

                    if (errorCode.equalsIgnoreCase("0")) {

                        String showPDFUrl = AccountDetails.getIsSecure() + "://" + AccountDetails.getArachne_IP() + ":" + AccountDetails.getArachne_Port() + "/downloadFileV2?imageName=ClientImage_" + AccountDetails.getUsername(getMainActivity());

                        Picasso.with(getMainActivity()).load(showPDFUrl + ".jpeg").into(uploadImg);

                    } else {

                        uploadImg.setImageDrawable(getResources().getDrawable(R.drawable.upload_photo));

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        } catch (Exception e) {

            e.printStackTrace();
        }


    }


    private void dialogForSelectImage() {

        final CharSequence[] items = {"Capture Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getMainActivity());
//        TextView t2 = new TextView(getMainActivity());
        builder.setTitle("Add Photo!");
//        t2.setText("Add Aadhar photo!");


       /* Typeface font = Typeface.createFromAsset(getResources().getAssets(), "DaxOT.ttf");
        t2.setTypeface(font,Typeface.NORMAL);*/


        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Capture Photo")) {

                    boolean result = marshMallowPermission.checkPermissionForCameraAndGallery();
                    if (result) {

//                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                        getMainActivity().startActivityForResult(cameraIntent, CAMERA_REQUEST);
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        getMainActivity().startActivityForResult(intent, CAMERA_REQUEST);

                    } else {
                        marshMallowPermission.requestPermissionForCameraAndGallery();
                    }

                } else if (items[item].equals("Choose from Gallery")) {

                    boolean result = marshMallowPermission.checkPermissionForExternalStorage();
                    if (result) {

                        Intent pictureActionIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        pictureActionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                        pictureActionIntent.setType("image/jpeg");
                        getMainActivity().startActivityForResult(pictureActionIntent, GALLERY_PICTURE);

                    } else {
                        marshMallowPermission.requestPermissionForExternalStorage();

                    }

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public static void setProfilePic() {
        if (uploadImg != null) {
            uploadImg.setVisibility(View.VISIBLE);
            uploadImg.setImageBitmap(bitmap);
        }
    }


    public static Bitmap RotateBitmap(Bitmap source, float angle) {

        Matrix matrix = new Matrix();
        matrix.setRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(),
                source.getHeight(), matrix, true);
    }

    HashMap<String, String> allowedproduct = new HashMap<>();
    private void getProducttype() {
        List<AllowedProduct> statusResponse = AccountDetails.getAllowedProduct();

        for (int i = 0; i < statusResponse.size(); i++) {
            allowedproduct.put(statusResponse.get(i).getiProductToken(), statusResponse.get(i).getcProductName());
        }

    }
    private String getProduct(String token) {

        return allowedproduct.get(token);

    }

    public String getProductType(String type) {
        if (type.equalsIgnoreCase(Intraday_product))
            return getProduct(Intraday_product);
        else if (type.equalsIgnoreCase(Delivery_product))
            return getProduct(Delivery_product);
        else if (type.equalsIgnoreCase(MTF_product))
            return getProduct(MTF_product);
        else if (type.equalsIgnoreCase(SSEQ_product))
            return getProduct(SSEQ_product);
        else if (type.equalsIgnoreCase(TNC_product))
            return getProduct(TNC_product);
        else if (type.equalsIgnoreCase(Catalyst_product))
            return getProduct(Catalyst_product);
        return "";
    }
    private void SubmitAAdharDetails() {

        if (bitmap != null) {

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            showProgress();
//            uploadMultipart();

        } else {

            Toast.makeText(getMainActivity(), "Please select Photo", Toast.LENGTH_LONG).show();
        }
    }

   /* public void uploadMultipart() {
        //getting name for the image
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US);
        Date now = new Date();
        String fileName = formatter.format(now);

        Uri filePath = Uri.fromFile(destination);

        //getting the actual path of the image
        String path = FilePath.getPath(getMainActivity(), filePath);

        if (path == null) {

            Toast.makeText(getMainActivity(), "Please move your video file to internal storage and retry", Toast.LENGTH_LONG).show();

        } else {
            //Uploading code
            try {
                String uploadId = UUID.randomUUID().toString();
                uploadReceiver.setUploadID(uploadId);
                uploadReceiver.setDelegate(this);
                //Creating a multi part request
                new MultipartUploadRequest(getMainActivity(), uploadId, SERVER_URL)
                        .addFileToUpload(path, "imagePath") //Adding file
                        .addParameter("imgeId", "5") //Adding text parameter to the request
                        .addParameter("clientCode", AccountDetails.getUsername(getMainActivity())) //Adding text parameter to the request
                        .addParameter("imgeType", "jpeg") //Adding text parameter to the request
                        //.addParameter("panNo",AccountDetails.getUserPAN(getMainActivity())) //Adding text parameter to the request
                        .addParameter("panNo", AccountDetails.getUserPAN(getMainActivity())) //Adding text parameter to the request
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(2)
                        .setDelegate(new UploadStatusDelegate() {
                            @Override
                            public void onProgress(Context context, UploadInfo uploadInfo) {

                            }

                            @Override
                            public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {

                                hideProgress();
                                GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Photo upload failed !",
                                        "Ok", false, new GreekDialog.DialogListener() {

                                            @Override
                                            public void alertDialogAction(GreekDialog.Action action, Object... data) {

                                                if (action == GreekDialog.Action.OK) {


                                                }
                                            }
                                        });
                            }

                            @Override
                            public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {

                                hideProgress();
                                GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Photo uploaded successfully.",
                                        "Ok", false, new GreekDialog.DialogListener() {

                                            @Override
                                            public void alertDialogAction(GreekDialog.Action action, Object... data) {

                                                if (action == GreekDialog.Action.OK) {


                                                    bitmap = null;

                                                    SharedPreferences.Editor editor = Util.getPrefs(getMainActivity()).edit();
                                                    editor.putString("uploadAadharSubmit", "yes");
                                                    editor.apply();
                                                    editor.commit();

                                                    uploadImg.setVisibility(View.VISIBLE);
                                                    uploadImg.setImageBitmap(bitmap);


                                                }
                                            }
                                        });
                            }

                            @Override
                            public void onCancelled(Context context, UploadInfo uploadInfo) {
                                hideProgress();
                            }
                        })
//                        .setUtf8Charset("application/x-www-form-urlencoded; charset=UTF-8")
                        .startUpload();

                //Starting the upload

            } catch (Exception exc) {
                Toast.makeText(getMainActivity(), exc.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }*/


    @Override
    public void onProgress(int progress) {

    }

    @Override
    public void onProgress(long uploadedBytes, long totalBytes) {

    }

    @Override
    public void onError(Exception exception) {

    }

    @Override
    public void onCompleted(int serverResponseCode, byte[] serverResponseBody) {

    }

    @Override
    public void onCancelled() {

    }
}