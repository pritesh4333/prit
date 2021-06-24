package com.acumengroup.mobile.trade;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.MutualFundSipNewOrder.MFundMediaAlreadyUploadResponce;
import com.acumengroup.greekmain.core.model.MutualFundSipNewOrder.MFundMediaUploadRequest;
import com.acumengroup.greekmain.core.model.MutualFundSipNewOrder.MFundMediaUploadResponce;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.MutualFund.MarshMallowPermission;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.markets.PdfActivity;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.button.GreekButton;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.Util;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadStatusDelegate;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import static androidx.appcompat.app.AppCompatActivity.RESULT_OK;
import static com.acumengroup.mobile.GreekBaseActivity.GREEK;
import static com.android.volley.VolleyLog.TAG;
import static com.acumengroup.greekmain.core.network.WSHandler.SERVER_URL;

public class KYCUploadPhoto extends GreekBaseFragment implements View.OnClickListener, Step, BlockingStep, SingleUploadBroadcastReciever.Delegate {

    private static final int PICK_FILE_REQUEST = 1;
    private static final int REQUEST_CODE_CAMERA_PERMISSION = 1;
    private MarshMallowPermission marshMallowPermission;
    private String selectedFilePath;
    public static ImageView imgSelfie;
    private Button btntakepoto, btnsubmit, btn_next, btn_previous;
    private String userChoosenTask, encodedImage = " ";
    private String imagepath = "";
    private String fname;
    private File file;
    private static File destination;
    private File fileOfImage;
    private boolean disabled_button = false;
    public static final int CAMERA_REQUEST = 0;
    public static final int GALLERY_PICTURE = 1;
    private static AppCompatActivity activity;
    private MFundMediaUploadResponce mFundMediaUploadResponce;
    private MFundMediaAlreadyUploadResponce mFundMediaAlreadyUploadResponce;
    public static Bitmap bitmap = null;
    private GreekTextView txt_title, view_photo;
    private String selectedImagePath;
    private boolean nextScreen_flag = false;
    private StepperLayout.OnNextClickedCallback onNextClickedCB;
    private final SingleUploadBroadcastReciever uploadReceiver = new SingleUploadBroadcastReciever();
    private boolean IsUploadReceiverRegister = false;

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        AccountDetails.currentFragment = NAV_TO_MUTUALFUND_KYC_UPLOAD;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        View mfActionView = super.onCreateView(inflater, container, savedInstanceState);
        //attachLayout(R.layout.fragment_kyc_document);


        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_kyc_document).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {

            attachLayout(R.layout.fragment_kyc_document).setBackground(getResources().getDrawable(R.drawable.bg_drawable));
        }

        SetupView(mfActionView);
        marshMallowPermission = new MarshMallowPermission(getMainActivity());

        activity = getMainActivity();

        //  showProgress();
        MFundMediaUploadRequest.sendRequestUpload(AccountDetails.getUsername(getMainActivity()), "1", AccountDetails.getUserPAN(getMainActivity()), getMainActivity(), serviceResponseHandler);


        return mfActionView;
    }

    private void SetupView(View mfActionView) {

        imgSelfie = mfActionView.findViewById(R.id.img_selfie);
        btnsubmit = (GreekButton) mfActionView.findViewById(R.id.btn_submit);
        btn_next = (GreekButton) mfActionView.findViewById(R.id.btn_next);
        btntakepoto = (GreekButton) mfActionView.findViewById(R.id.btn_take_photo);
        btn_previous = (GreekButton) mfActionView.findViewById(R.id.btn_previous);
        txt_title = mfActionView.findViewById(R.id.face_title);
        view_photo = mfActionView.findViewById(R.id.view_photo);


        if (AccountDetails.getClientCode(getMainActivity()) != null && !AccountDetails.getClientCode(getMainActivity()).equalsIgnoreCase("ClientCode")) {

            btnsubmit.setVisibility(View.VISIBLE);

        } else {

            btnsubmit.setVisibility(View.GONE);
        }

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            txt_title.setTextColor(getResources().getColor(R.color.black));
            // title.setTextColor(getResources().getColor(R.color.black));
            //attachLayout(R.layout.fragment_kyc_details).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("black")) {
            txt_title.setTextColor(getResources().getColor(R.color.white));
            //  title.setTextColor(getResources().getColor(R.color.white));
            //attachLayout(R.layout.fragment_kyc_details).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));
        }

        btntakepoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogForSelectImage();

            }
        });
        btnsubmit.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        btn_previous.setOnClickListener(this);
        view_photo.setOnClickListener(this);


        String submit = Util.getPrefs(getMainActivity()).getString("uploadfaceSubmit", "");

        if (!submit.equalsIgnoreCase("yes")) {
            btntakepoto.setClickable(true);
            btntakepoto.setEnabled(true);
            btnsubmit.setClickable(true);
            btnsubmit.setEnabled(true);
        } else {
            btntakepoto.setClickable(false);
            btntakepoto.setEnabled(false);
            btnsubmit.setClickable(false);
            btnsubmit.setEnabled(false);
        }

//        String img = Util.getPrefs(getActivity()).getString("faceImage", "");
//
//        if (!img.equalsIgnoreCase("")) {
//
//            byte[] b = Base64.decode(img, Base64.DEFAULT);
//            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
//            imgSelfie.setImageBitmap(bitmap);
//
//        } else {
//            imgSelfie.setVisibility(View.VISIBLE);
//        }


    }


    @Override
    public void onClick(View view) {


        if (view.getId() == R.id.btn_next) {

            navigateTo(NAV_TO_UPLOAD__PAN_DETAILS, getArguments(), true);

        }
        if (view.getId() == R.id.btn_previous) {

            navigateTo(NAV_TO_ALL_BANKDETAILS_MF, getArguments(), true);

        }

        if (view.getId() == R.id.btn_take_photo) {

            dialogForSelectImage();

        }
        if (view.getId() == R.id.btn_submit) {

            SubmitPhotoDetails();
        }

        if (view.getId() == R.id.view_photo) {
            ShowAlreadyPhoto();
        }

    }

    public void SubmitPhotoDetails() {

        if (bitmap != null) {

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

            encodedImage = getStringImage(bitmap);

            // Log.e("KYCUploadPhoto", "encodedImage=====>" + encodedImage);

            /*if (encodedImage != null) {

                //UpdateImageTask(encodedImage);

                showProgress();
                MFundMediaUploadRequest.sendRequest("1", AccountDetails.getUsername(getMainActivity()), encodedImage, "jpeg",AccountDetails.getUserPAN(getMainActivity()), getMainActivity(), serviceResponseHandler);
            }*/

            showProgress();
            uploadMultipart();

        } else {

            Toast.makeText(getMainActivity(), "Please select Photo", Toast.LENGTH_LONG).show();
        }

    }

    private void ShowAlreadyPhoto() {


        // "http://192.168.209.192:8080/downloadFile?imageName=1400778238_abcde1234t_SUPP.pdf"

        String showPDFUrl = AccountDetails.getIsSecure() + "://" + AccountDetails.getArachne_IP() + ":" + AccountDetails.getArachne_Port() + "/downloadFile?imageName=" + AccountDetails.getposCode() + "_" + AccountDetails.getUserPAN(getMainActivity()) + "_CLIIMG.pdf";

        Log.e("KYCUploadPhoto", "showPDFUrl======>" + showPDFUrl);

        Intent i = new Intent(getContext(), PdfActivity.class);
        i.putExtra("showPDFUrl", showPDFUrl);
        startActivity(i);


    }

    public void uploadMultipart() {
        //getting name for the image
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US);
        Date now = new Date();
        String fileName = formatter.format(now);


        Uri filePath = Uri.fromFile(destination);

        //getting the actual path of the image
        String path = FilePath.getPath(getContext(), filePath);

        Log.e("UploadpanDetails", "PDF path===========>>" + path);
        Log.e("UploadpanDetails", "PDF fileName===========>>" + fileName);

        if (path == null) {

            Toast.makeText(getContext(), "Please move your  file to internal storage and retry", Toast.LENGTH_LONG).show();

        } else {
            //Uploading code
            try {
                String uploadId = UUID.randomUUID().toString();
                uploadReceiver.setUploadID(uploadId);
                uploadReceiver.setDelegate(this);
                //Creating a multi part request
                new MultipartUploadRequest(getContext(), uploadId, SERVER_URL)
                        .addFileToUpload(path, "imagePath") //Adding file
                        .addParameter("imgeId", "1") //Adding text parameter to the request
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
                                //Toast.makeText(getActivity(),"completed",Toast.LENGTH_LONG).show();
                                GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Photo uploaded successfully.",
                                        "Ok", false, new GreekDialog.DialogListener() {

                                            @Override
                                            public void alertDialogAction(GreekDialog.Action action, Object... data) {

                                                if (action == GreekDialog.Action.OK) {

                                                    txt_title.setText("Your face photo uploaded.");
                                                    view_photo.setVisibility(View.VISIBLE);
                                                    nextScreen_flag = true;

                                                    bitmap = null;

                                                    SharedPreferences.Editor editor = Util.getPrefs(getMainActivity()).edit();
                                                    editor.putString("uploadfaceSubmit", "yes");
                                                    editor.putString("faceImage", encodedImage);
                                                    editor.apply();
                                                    editor.commit();

                                                    btntakepoto.setClickable(false);
                                                    btntakepoto.setEnabled(false);
                                                    btnsubmit.setClickable(false);
                                                    btnsubmit.setEnabled(false);

                                                    if (onNextClickedCB != null) {
                                                        onNextClicked(onNextClickedCB);
                                                    }

                                                    btn_next.setVisibility(View.VISIBLE);
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
                Toast.makeText(getContext(), exc.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void dialogForSelectImage() {

        final CharSequence[] items = {"Capture Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getMainActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Capture Photo")) {

                    boolean result = marshMallowPermission.checkPermissionForCameraAndGallery();

                    userChoosenTask = "Capture Image";

                    Log.e(TAG, "checkPermissionForCameraAndGallery===result>>" + result);

                    if (result) {

                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);

                    } else {

                        marshMallowPermission.requestPermissionForCameraAndGallery();

                    }

                } else if (items[item].equals("Choose from Gallery")) {

                    boolean result = marshMallowPermission.checkPermissionForExternalStorage();

                    userChoosenTask = "Choose Image From Gallery";

                    if (result) {

                        Intent pictureActionIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        pictureActionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                        pictureActionIntent.setType("image/jpeg");
                        startActivityForResult(pictureActionIntent, GALLERY_PICTURE);

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {

            onCaptureImageResult(data);

        } else if (resultCode == RESULT_OK && requestCode == GALLERY_PICTURE) {
            if (data != null) {

                onSelectFromGalleryResult(data);

            } else {
                Toast.makeText(getMainActivity(), "Cancelled",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }

    public static void onSelectFromGalleryResult(Intent data) {


        if (data != null) {


            destination = new File(getPath(activity, data.getData()));


            Log.e("KYCUploadPhoto", "Capture Image Destination in Storage===>" + destination);
            bitmap = getBitmapofPhoto(data);
            //bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), data.getData());

        }

        try {
            if (bitmap == null) {
                bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), data.getData());

            }
            // Log.d(TAG, String.valueOf(bitmap));


        } catch (IOException e) {
            e.printStackTrace();
        }

        if (bitmap != null) {

            imgSelfie.setVisibility(View.VISIBLE);
            imgSelfie.setImageBitmap(bitmap);

        }

    }

    public static String getPathofPhoto(Intent data) {
        Uri selectedImage = data.getData();

        String[] filePath = {MediaStore.Images.Media.DATA};

        Cursor c = activity.getContentResolver().query(selectedImage, filePath, null, null, null);

        c.moveToFirst();

        int columnIndex = c.getColumnIndex(filePath[0]);

        String picturePath = c.getString(columnIndex);

        c.close();

        //Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));

        return picturePath;
    }

    public static Bitmap getBitmapofPhoto(Intent data) {

        Uri selectedImage = data.getData();
        BitmapFactory.Options options;
        Bitmap thumbnail_bitmap;

        String[] filePath = {MediaStore.Images.Media.DATA};

        Cursor c = activity.getContentResolver().query(selectedImage, filePath, null, null, null);

        c.moveToFirst();

        int columnIndex = c.getColumnIndex(filePath[0]);

        String picturePath = c.getString(columnIndex);

        c.close();


        options = new BitmapFactory.Options();
        options.inSampleSize = 2;

        thumbnail_bitmap = BitmapFactory.decodeFile(picturePath);

        return thumbnail_bitmap;
    }


    public static void onCaptureImageResult(Intent data) {

        bitmap = (Bitmap) data.getExtras().get("data");

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");

        Log.e("KYCUploadPhoto", "Capture Image Destination in Storage===>" + destination);

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        ExifInterface ei = null;
        try {
            ei = new ExifInterface(destination.toString());
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

            switch (orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:

                    bitmap = RotateBitmap(bitmap, 90);

                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    //uploadFile(selectedImagePath);

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

            /*imgSelfie.setVisibility(View.VISIBLE);
            imgSelfie.setImageBitmap(bitmap);*/

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MarshMallowPermission.CAMERA_PERMISSION_REQUEST_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }

        } else if (requestCode == MarshMallowPermission.EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                Intent pictureActionIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pictureActionIntent, GALLERY_PICTURE);
            }
        }


    }


    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public static Bitmap RotateBitmap(Bitmap source, float angle) {

        Matrix matrix = new Matrix();
        matrix.setRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(),
                source.getHeight(), matrix, true);
    }


    @Override
    public void handleResponse(Object response) {
        // super.handleResponse(response);
        hideProgress();

        JSONResponse jsonResponse = (JSONResponse) response;

        Log.e("KYCUploadPhoto", "jsonResponse===========>" + jsonResponse);

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

                                        txt_title.setText("Your face photo uploaded.");
                                        view_photo.setVisibility(View.VISIBLE);

                                        nextScreen_flag = true;


                                        SharedPreferences.Editor editor = Util.getPrefs(getMainActivity()).edit();
                                        editor.putString("uploadfaceSubmit", "yes");
                                        editor.commit();

                                        btntakepoto.setClickable(false);
                                        btntakepoto.setEnabled(false);
                                        btnsubmit.setClickable(false);
                                        btnsubmit.setEnabled(false);

                                        if (onNextClickedCB != null) {
                                            onNextClicked(onNextClickedCB);

                                        }


                                        btn_next.setVisibility(View.VISIBLE);
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

        } else if (ALREADY_UPLOAD_PHOTO_SVC_GROUP.equals(jsonResponse.getServiceName())) {
            try {

                mFundMediaAlreadyUploadResponce = (MFundMediaAlreadyUploadResponce) jsonResponse.getResponse();

                String errorCode = mFundMediaAlreadyUploadResponce.getErrorCode();

                if (errorCode.equalsIgnoreCase("0")) {
                    String image_name = mFundMediaAlreadyUploadResponce.getImageName();
                    String decoded_imagestring = mFundMediaAlreadyUploadResponce.getImageData();

//                    byte[] decodedString = Base64.decode(decoded_imagestring, Base64.DEFAULT);
//                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    nextScreen_flag = true;
                    disabled_button = true;
                    imgSelfie.setVisibility(View.GONE);
                    txt_title.setText("Your face photo already uploaded.");
                    view_photo.setVisibility(View.VISIBLE);

//                    if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
//                        txt_title.setTextColor(getResources().getColor(R.color.black));
//                    } else {
//                        txt_title.setTextColor(getResources().getColor(R.color.white));
//                    }

                    if (AccountDetails.getClientCode(getMainActivity()) != null && !AccountDetails.getClientCode(getMainActivity()).equalsIgnoreCase("ClientCode")) {

                        btnsubmit.setEnabled(true);
                        btntakepoto.setEnabled(true);
                        btnsubmit.setClickable(true);
                        btntakepoto.setClickable(true);

                    } else {

                        btnsubmit.setEnabled(false);
                        btntakepoto.setEnabled(false);
                        btnsubmit.setClickable(false);
                        btntakepoto.setClickable(false);
                    }

                    //btn_next.setVisibility(View.VISIBLE);


//                    GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Photo Already Uploaded.",
//                            "Ok", false, new GreekDialog.DialogListener() {
//
//                                @Override
//                                public void alertDialogAction(GreekDialog.Action action, Object... data) {
//
//                                    if (action == GreekDialog.Action.OK) {
//
//
//                                    }
//                                }
//                            });

                } else {

                    imgSelfie.setVisibility(View.VISIBLE);
                    txt_title.setText("Upload your face photo.");
                    view_photo.setVisibility(View.GONE);

                    txt_title.setTypeface(null, Typeface.BOLD);

//                    if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
//                        txt_title.setTextColor(getResources().getColor(R.color.black));
//                    } else {
//                        txt_title.setTextColor(getResources().getColor(R.color.white));
//                    }
                    btnsubmit.setEnabled(true);
                    btntakepoto.setEnabled(true);
                    btnsubmit.setClickable(true);
                    btntakepoto.setClickable(true);


//                    GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Please Upload Your Photo.",
//                            "Ok", false, new GreekDialog.DialogListener() {
//
//                                @Override
//                                public void alertDialogAction(GreekDialog.Action action, Object... data) {
//
//                                    if (action == GreekDialog.Action.OK) {
//
//
//
//                                    }
//                                }
//                            });
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {

        MFundMediaUploadRequest.sendRequestUpload(AccountDetails.getUsername(getMainActivity()), "1", AccountDetails.getUserPAN(getMainActivity()), getMainActivity(), serviceResponseHandler);
        if (disabled_button) {
            btnsubmit.setEnabled(false);
            btntakepoto.setEnabled(false);
            btnsubmit.setClickable(false);
            btntakepoto.setClickable(false);
            btn_next.setVisibility(View.GONE);
        }

        btn_next.setVisibility(View.GONE);
        btn_previous.setVisibility(View.GONE);
        btnsubmit.setVisibility(View.GONE);


        String submit = Util.getPrefs(getMainActivity()).getString("uploadfaceSubmit", "");

        if (!submit.equalsIgnoreCase("yes")) {
            btntakepoto.setClickable(true);
            btntakepoto.setEnabled(true);
            btnsubmit.setClickable(true);
            btnsubmit.setEnabled(true);
        } else {
            btntakepoto.setClickable(false);
            btntakepoto.setEnabled(false);
            btnsubmit.setClickable(false);
            btnsubmit.setEnabled(false);
        }

        String img = Util.getPrefs(getMainActivity()).getString("faceImage", "");

        if (!img.equalsIgnoreCase("")) {

            byte[] b = Base64.decode(img, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            imgSelfie.setImageBitmap(bitmap);

        } else {
            imgSelfie.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void onError(@NonNull VerificationError verificationError) {

    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback onNextClickedCallback) {

        onNextClickedCB = onNextClickedCallback;

        if (nextScreen_flag) {

            onNextClickedCallback.goToNextStep();

        } else {

            SubmitPhotoDetails();
//            Toast.makeText(getContext(), "Your face photo is not uploaded.", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback onCompleteClickedCallback) {

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback onBackClickedCallback) {
        onBackClickedCallback.goToPrevStep();
    }

    @Override
    public void onProgress(int progress) {

    }

    @Override
    public void onProgress(long uploadedBytes, long totalBytes) {

    }

    @Override
    public void onError(Exception exception) {
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
    public void onCompleted(int serverResponseCode, byte[] serverResponseBody) {
        hideProgress();
        //Toast.makeText(getActivity(),"completed",Toast.LENGTH_LONG).show();
        GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Photo uploaded successfully.",
                "Ok", false, new GreekDialog.DialogListener() {

                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {

                        if (action == GreekDialog.Action.OK) {

                            txt_title.setText("Your face photo uploaded.");
                            view_photo.setVisibility(View.VISIBLE);

                            nextScreen_flag = true;

                            bitmap = null;

                            SharedPreferences.Editor editor = Util.getPrefs(getMainActivity()).edit();
                            editor.putString("uploadfaceSubmit", "yes");
                            editor.commit();

                            btntakepoto.setClickable(false);
                            btntakepoto.setEnabled(false);
                            btnsubmit.setClickable(false);
                            btnsubmit.setEnabled(false);

                            if (onNextClickedCB != null) {
                                onNextClicked(onNextClickedCB);

                            }


                            btn_next.setVisibility(View.VISIBLE);
                        }
                    }
                });

    }

    @Override
    public void onCancelled() {

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

}
