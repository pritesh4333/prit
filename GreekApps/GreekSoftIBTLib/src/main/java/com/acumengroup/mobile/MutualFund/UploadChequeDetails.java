package com.acumengroup.mobile.MutualFund;

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
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.markets.PdfActivity;
import com.acumengroup.mobile.trade.FilePath;
import com.acumengroup.mobile.trade.SingleUploadBroadcastReciever;
import com.acumengroup.ui.GreekDialog;
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
import static com.acumengroup.greekmain.core.network.WSHandler.SERVER_URL;
import static com.acumengroup.mobile.GreekBaseActivity.GREEK;

public class UploadChequeDetails extends GreekBaseFragment implements View.OnClickListener, Step, BlockingStep, SingleUploadBroadcastReciever.Delegate {

    static ImageView imgPan;
    Button btntakepoto, btnsubmit, btn_next, btn_previous;
    protected static final int CAMERA_REQUEST = 0;
    protected static final int GALLERY_PICTURE = 1;
    GreekTextView title, view_photo;

    public static Bitmap bitmap = null;
    private File filePathofPDF;
    private MarshMallowPermission marshMallowPermission;
    static AppCompatActivity activity;
    private MFundMediaUploadResponce mFundMediaUploadResponce;
    private MFundMediaAlreadyUploadResponce mFundMediaAlreadyUploadResponce;
    private boolean nextScreen_flag = false;
    private StepperLayout.OnNextClickedCallback onNextClickedCB;
    private boolean disabled_button = false;
    private static File destination;
    private final SingleUploadBroadcastReciever uploadReceiver =
            new SingleUploadBroadcastReciever();
    private boolean IsUploadReceiverRegister = false;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_next) {
            navigateTo(NAV_TO_MUTUALFUND_KYC_IPVP_UPLOAD, getArguments(), false);
        }
        if (view.getId() == R.id.btn_previous) {
            navigateTo(NAV_TO_MUTUALFUND_KYC_BANK_UPLOAD, getArguments(), false);
        }
        if (view.getId() == R.id.btn_take_pan_photo) {
            dialogForSelectImage();
        }
        if (view.getId() == R.id.btn_pan_submit) {
            SubmitChequeDetails();
        }
        if (view.getId() == R.id.view_photo) {
            ShowAlreadyPhoto();
        }

    }

    private void SubmitChequeDetails() {

        if (bitmap != null) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            showProgress();
            uploadMultipart();
        } else {
            Toast.makeText(getMainActivity(), "Please select Photo", Toast.LENGTH_LONG).show();
        }

    }

    public void uploadMultipart() {
        //getting name for the image
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US);
        Date now = new Date();
        String fileName = formatter.format(now);
        Uri filePath = Uri.fromFile(destination);

        //getting the actual path of the image
        String path = FilePath.getPath(getContext(), filePath);
        if (path == null) {

            Toast.makeText(getContext(), "Please move your video file to internal storage and retry", Toast.LENGTH_LONG).show();

        } else {
            //Uploading code
            try {
                String uploadId = UUID.randomUUID().toString();
                uploadReceiver.setUploadID(uploadId);
                uploadReceiver.setDelegate(this);
                //Creating a multi part request
                new MultipartUploadRequest(getContext(), uploadId, SERVER_URL)
                        .addFileToUpload(path, "imagePath") //Adding file
                        .addParameter("imgeId", "7") //Adding text parameter to the request
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
                                GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Cheque photo upload failed !",
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
                                GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Cheque photo uploaded successfully.",
                                        "Ok", false, new GreekDialog.DialogListener() {

                                            @Override
                                            public void alertDialogAction(GreekDialog.Action action, Object... data) {

                                                if (action == GreekDialog.Action.OK) {

                                                    btnsubmit.setEnabled(false);
                                                    btntakepoto.setEnabled(false);
                                                    btnsubmit.setClickable(false);
                                                    btntakepoto.setClickable(false);

                                                    bitmap = null;

                                                    SharedPreferences.Editor editor = Util.getPrefs(getMainActivity()).edit();
                                                    editor.putString("uploadChequeSubmit", "yes");
                                                    editor.apply();
                                                    editor.commit();

                                                    title.setText("Your Cheque photo uploaded.");
                                                    view_photo.setVisibility(View.VISIBLE);


                                                    nextScreen_flag = true;

                                                    if (onNextClickedCB != null) {
                                                        onNextClicked(onNextClickedCB);
                                                    }

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

    private void ShowAlreadyPhoto() {


        // "http://192.168.209.192:8080/downloadFile?imageName=1400778238_abcde1234t_SUPP.pdf"

        String showPDFUrl = AccountDetails.getIsSecure() + "://" + AccountDetails.getArachne_IP() + ":" + AccountDetails.getArachne_Port() + "/downloadFile?imageName=" + AccountDetails.getposCode() + "_" + AccountDetails.getUserPAN(getMainActivity()) + "_CHEQUE.pdf";

        Intent i = new Intent(getContext(), PdfActivity.class);
        i.putExtra("showPDFUrl", showPDFUrl);
        startActivity(i);


    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        AccountDetails.currentFragment = NAV_TO_MUTUALFUND_KYC_CHEQUE_UPLOAD;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        View mfActionView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_upload_cheque_details).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_upload_cheque_details).setBackground(getResources().getDrawable(R.drawable.bg_drawable));
        }


        marshMallowPermission = new MarshMallowPermission(getMainActivity());
        activity = getMainActivity();

        SetupView(mfActionView);
        btntakepoto.setOnClickListener(this);
        btnsubmit.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        btn_previous.setOnClickListener(this);

        if (AccountDetails.getClientCode(getMainActivity()) != null && !AccountDetails.getClientCode(getMainActivity()).equalsIgnoreCase("ClientCode")) {
            btnsubmit.setVisibility(View.VISIBLE);

        } else {
            btnsubmit.setVisibility(View.GONE);
        }


        MFundMediaUploadRequest.sendRequestUpload(AccountDetails.getUsername(getMainActivity()), "7", AccountDetails.getUserPAN(getMainActivity()), getMainActivity(), serviceResponseHandler);
        return mfActionView;
    }

    private void SetupView(View mfActionView) {
        btntakepoto = mfActionView.findViewById(R.id.btn_take_pan_photo);
        btn_previous = mfActionView.findViewById(R.id.btn_previous);
        btn_next = mfActionView.findViewById(R.id.btn_next);
        btnsubmit = mfActionView.findViewById(R.id.btn_pan_submit);
        imgPan = mfActionView.findViewById(R.id.img_pan);
        title = mfActionView.findViewById(R.id.pan_upload_title);
        view_photo = mfActionView.findViewById(R.id.view_photo);
        view_photo.setOnClickListener(this);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            title.setTextColor(getResources().getColor(R.color.black));
        } else if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("black")) {
            title.setTextColor(getResources().getColor(R.color.white));
        }


        String submit = Util.getPrefs(getMainActivity()).getString("uploadChequeSubmit", "");

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

    }

    private void dialogForSelectImage() {

        final CharSequence[] items = {"Capture Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getMainActivity());
        builder.setTitle("Add Cheque photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Capture Photo")) {

                    boolean result = marshMallowPermission.checkPermissionForCameraAndGallery();

                    if (result) {

                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);

                    } else {

                        marshMallowPermission.requestPermissionForCameraAndGallery();

                    }

                } else if (items[item].equals("Choose from Gallery")) {

                    boolean result = marshMallowPermission.checkPermissionForExternalStorage();

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
            bitmap = getBitmapofPhoto(data);

        }

        try {
            if (bitmap == null) {
                bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), data.getData());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (bitmap != null) {
            imgPan.setVisibility(View.VISIBLE);
            imgPan.setImageBitmap(bitmap);
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


    public static void onCaptureImageResult(Intent data) {
        bitmap = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
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

            imgPan.setVisibility(View.VISIBLE);
            imgPan.setImageBitmap(bitmap);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MarshMallowPermission.CAMERA_PERMISSION_REQUEST_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }

        } else if (requestCode == MarshMallowPermission.EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                Intent pictureActionIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pictureActionIntent, GALLERY_PICTURE);
            }
        }


    }

    public static Bitmap RotateBitmap(Bitmap source, float angle) {

        Matrix matrix = new Matrix();
        matrix.setRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(),
                source.getHeight(), matrix, true);
    }


    @Override
    public void handleResponse(Object response) {
        hideProgress();

        JSONResponse jsonResponse = (JSONResponse) response;
        if (UPLOAD_PHOTO_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && UPLOAD_PHOTO_SVC_GROUP.equals(jsonResponse.getServiceName())) {
            try {

                mFundMediaUploadResponce = (MFundMediaUploadResponce) jsonResponse.getResponse();
                String errorCode = mFundMediaUploadResponce.getErrorCode();
                if (errorCode.equalsIgnoreCase("0")) {

                    GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Cheque photo uploaded successfully.",
                            "Ok", false, new GreekDialog.DialogListener() {

                                @Override
                                public void alertDialogAction(GreekDialog.Action action, Object... data) {

                                    if (action == GreekDialog.Action.OK) {
                                        btnsubmit.setEnabled(false);
                                        btntakepoto.setEnabled(false);
                                        btnsubmit.setClickable(false);
                                        btntakepoto.setClickable(false);
                                        SharedPreferences.Editor editor = Util.getPrefs(getMainActivity()).edit();
                                        editor.putString("uploadChequeSubmit", "yes");
                                        editor.apply();
                                        editor.commit();
                                        title.setText("Your Cheque photo uploaded.");
                                        view_photo.setVisibility(View.VISIBLE);


                                        nextScreen_flag = true;

                                        if (onNextClickedCB != null) {
                                            onNextClicked(onNextClickedCB);
                                        }
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
                    nextScreen_flag = true;
                    disabled_button = true;
                    imgPan.setVisibility(View.GONE);
                    title.setText("Your Cheque photo already uploaded.");
                    view_photo.setVisibility(View.VISIBLE);

//                    if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
//                        title.setTextColor(getResources().getColor(R.color.black));
//                    } else {
//                        title.setTextColor(getResources().getColor(R.color.white));
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

                } else {

                    imgPan.setVisibility(View.VISIBLE);
                    title.setText("Upload your Cheque photo");
                    view_photo.setVisibility(View.GONE);

//                    if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
////                        title.setTextColor(getResources().getColor(R.color.black));
////                    } else {
////                        title.setTextColor(getResources().getColor(R.color.white));
////                    }

                    btnsubmit.setEnabled(true);
                    btntakepoto.setEnabled(true);
                    btnsubmit.setClickable(true);
                    btntakepoto.setClickable(true);
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

        MFundMediaUploadRequest.sendRequestUpload(AccountDetails.getUsername(getMainActivity()), "7", AccountDetails.getUserPAN(getMainActivity()), getMainActivity(), serviceResponseHandler);

        if (disabled_button) {
            btnsubmit.setEnabled(false);
            btntakepoto.setEnabled(false);
            btnsubmit.setClickable(false);
            btntakepoto.setClickable(false);
        }

        btn_next.setVisibility(View.GONE);
        btn_previous.setVisibility(View.GONE);
        btnsubmit.setVisibility(View.GONE);

        String submit = Util.getPrefs(getMainActivity()).getString("uploadChequeSubmit", "");

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

            SubmitChequeDetails();
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
        GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Cheque photo uploaded successfully.",
                "Ok", false, new GreekDialog.DialogListener() {

                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {

                        if (action == GreekDialog.Action.OK) {
                            btnsubmit.setEnabled(false);
                            btntakepoto.setEnabled(false);
                            btnsubmit.setClickable(false);
                            btntakepoto.setClickable(false);

                            bitmap = null;

                            SharedPreferences.Editor editor = Util.getPrefs(getMainActivity()).edit();
                            editor.putString("uploadChequeSubmit", "yes");
                            editor.apply();
                            editor.commit();
                            title.setText("Your Cheque photo uploaded.");
                            view_photo.setVisibility(View.VISIBLE);


                            nextScreen_flag = true;

                            if (onNextClickedCB != null) {
                                onNextClicked(onNextClickedCB);
                            }


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
