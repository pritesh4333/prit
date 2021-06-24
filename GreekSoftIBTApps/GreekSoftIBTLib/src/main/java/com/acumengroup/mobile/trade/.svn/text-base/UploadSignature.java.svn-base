package com.acumengroup.mobile.trade;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.gesture.Gesture;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.MutualFundSipNewOrder.MFundMediaAlreadyUploadResponce;
import com.acumengroup.greekmain.core.model.MutualFundSipNewOrder.MFundMediaUploadRequest;
import com.acumengroup.greekmain.core.model.MutualFundSipNewOrder.MFundMediaUploadResponce;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.login.LoginActivity;
import com.acumengroup.mobile.markets.PdfActivity;
import com.acumengroup.ui.GreekDialog;
import com.acumengroup.ui.textview.GreekTextView;
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
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import static com.acumengroup.greekmain.core.network.WSHandler.SERVER_URL;
import static com.acumengroup.mobile.GreekBaseActivity.GREEK;

public class UploadSignature extends GreekBaseFragment implements View.OnClickListener, Step
        , BlockingStep, SingleUploadBroadcastReciever.Delegate, GestureOverlayView.OnGesturePerformedListener {

    Button redrawButton;
    private Button saveButton, btn_previous, next_btn;
    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 1;
    private GestureOverlayView gestureOverlayView;
    private MFundMediaAlreadyUploadResponce mFundMediaAlreadyUploadResponce;
    private GreekTextView title, view_photo;
    private MFundMediaUploadResponce mFundMediaUploadResponce;
    private boolean nextScreen_flag = false;
    private StepperLayout.OnCompleteClickedCallback onCompleteClickedCB;
    private static String selectedPath;
    private final SingleUploadBroadcastReciever uploadReceiver =
            new SingleUploadBroadcastReciever();
    private boolean IsUploadReceiverRegister = false;
    private boolean guesturePerformed = false;

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.redraw_button) {

            gestureOverlayView.clear(false);
            guesturePerformed = false;
        }

        if (view.getId() == R.id.save_button) {

            checkPermissionAndSaveSignature();
        }

        if (view.getId() == R.id.btn_previous) {

            navigateTo(NAV_TO_MUTUALFUND_KYC_IPVP_UPLOAD, getArguments(), false);

        }
        if (view.getId() == R.id.view_photo) {

            ShowAlreadyPhoto();
        }

    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        AccountDetails.currentFragment = NAV_TO_MUTUALFUND_SIGNATURE_UPLOAD;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        View mfActionView = super.onCreateView(inflater, container, savedInstanceState);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_sigature_upload).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_sigature_upload).setBackground(getResources().getDrawable(R.drawable.bg_drawable));
        }


        //SetupView(mfActionView);
        redrawButton = mfActionView.findViewById(R.id.redraw_button);
        saveButton = mfActionView.findViewById(R.id.save_button);
        title = mfActionView.findViewById(R.id.signature_title);
        view_photo = mfActionView.findViewById(R.id.view_photo);
        view_photo.setOnClickListener(this);
        next_btn = mfActionView.findViewById(R.id.nextbtn);
        btn_previous = mfActionView.findViewById(R.id.btn_previous);

        gestureOverlayView = mfActionView.findViewById(R.id.sign_pad);
        gestureOverlayView.addOnGesturePerformedListener(this);
        redrawButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        next_btn.setOnClickListener(this);
        btn_previous.setOnClickListener(this);


        if (AccountDetails.getClientCode(getMainActivity()) != null && !AccountDetails.getClientCode(getMainActivity())
                .equalsIgnoreCase("ClientCode")) {

            saveButton.setVisibility(View.VISIBLE);

        } else {

            saveButton.setVisibility(View.GONE);

        }

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            title.setTextColor(getResources().getColor(R.color.black));
        } else if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("black")) {
            title.setTextColor(getResources().getColor(R.color.white));

        }

        MFundMediaUploadRequest.sendRequestUpload(AccountDetails.getUsername(getMainActivity()), "3", AccountDetails.getUserPAN(getMainActivity()), getMainActivity(), serviceResponseHandler);
        return mfActionView;
    }


    private void checkPermissionAndSaveSignature() {
        try {

            // Check whether this app has write external storage permission or not.
            int writeExternalStoragePermission = ContextCompat.checkSelfPermission(getMainActivity().getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

            // If do not grant write external storage permission.
            if (writeExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
                // Request user to grant write external storage permission.
                ActivityCompat.requestPermissions(getMainActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);

            } else {

                saveSignature();
            }

        } catch (Exception e) {
            Log.v("Signature Gestures", e.getMessage());
            e.printStackTrace();
        }
    }

    private void ShowAlreadyPhoto() {


        // "http://192.168.209.192:8080/downloadFile?imageName=1400778238_abcde1234t_SUPP.pdf"

        String showPDFUrl = AccountDetails.getIsSecure() + "://" + AccountDetails.getArachne_IP() + ":" + AccountDetails.getArachne_Port() + "/downloadFile?imageName=" + AccountDetails.getposCode() + "_" + AccountDetails.getUserPAN(getMainActivity()) + "_SIGN.pdf";

        Intent i = new Intent(getContext(), PdfActivity.class);
        i.putExtra("showPDFUrl", showPDFUrl);
        startActivity(i);
    }


    @Override
    public void handleResponse(Object response) {
        // super.handleResponse(response);
        hideProgress();

        JSONResponse jsonResponse = (JSONResponse) response;

        if (UPLOAD_PHOTO_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && UPLOAD_PHOTO_SVC_GROUP.equals(jsonResponse.getServiceName())) {
            try {

                mFundMediaUploadResponce = (MFundMediaUploadResponce) jsonResponse.getResponse();

                String errorCode = mFundMediaUploadResponce.getErrorCode();

                if (errorCode.equalsIgnoreCase("0")) {

                    GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Signature uploaded successfully.",
                            "Ok", false, new GreekDialog.DialogListener() {

                                @Override
                                public void alertDialogAction(GreekDialog.Action action, Object... data) {

                                    if (action == GreekDialog.Action.OK) {

                                        nextScreen_flag = true;

                                        title.setText("Your signature uploaded.");
                                        view_photo.setVisibility(View.VISIBLE);


                                        gestureOverlayView.clear(false);
                                        guesturePerformed = false;

                                        showProgress();
                                        MFundMediaUploadRequest.sendRequestKYC(AccountDetails.getUsername(getMainActivity()), AccountDetails.getUserPAN(getMainActivity()), "01", getMainActivity(), serviceResponseHandler);


                                    }
                                }
                            });

                } else {

                    GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Signature upload failed !",
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

        } else if (KYC_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && KYC_SVC_NAME.equals(jsonResponse.getServiceName())) {

            try {
                mFundMediaUploadResponce = (MFundMediaUploadResponce) jsonResponse.getResponse();
                String errorCode = mFundMediaUploadResponce.getErrorCode();

                if (errorCode.equalsIgnoreCase("0")) {

                    GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Almost done! You will receive a mail once your KYC is processed.",
                            "Ok", false, new GreekDialog.DialogListener() {

                                @Override
                                public void alertDialogAction(GreekDialog.Action action, Object... data) {

                                    if (action == GreekDialog.Action.OK) {


                                        if (onCompleteClickedCB != null) {
                                            onCompleteClicked(onCompleteClickedCB);
                                        }
                                    }
                                }
                            });

                } else {

                    GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "KYC Failed. \nReason :" + mFundMediaUploadResponce.getStatus(),
                            "Ok", false, new GreekDialog.DialogListener() {

                                @Override
                                public void alertDialogAction(GreekDialog.Action action, Object... data) {

                                    if (action == GreekDialog.Action.OK) {

                                        if (onCompleteClickedCB != null) {
                                            onCompleteClicked(onCompleteClickedCB);
                                        }

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
                    gestureOverlayView.setVisibility(View.GONE);
                    title.setText("Your signature already uploaded.");
                    view_photo.setVisibility(View.VISIBLE);

//                    if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
//                        title.setTextColor(getResources().getColor(R.color.black));
//                    } else {
//                        title.setTextColor(getResources().getColor(R.color.white));
//                    }

                    if (AccountDetails.getClientCode(getMainActivity()) != null && !AccountDetails.getClientCode(getMainActivity()).equalsIgnoreCase("ClientCode")) {

                        redrawButton.setEnabled(true);
                        saveButton.setEnabled(true);
                        redrawButton.setClickable(true);
                        saveButton.setClickable(true);

                        gestureOverlayView.setVisibility(View.VISIBLE);
                    } else {

                        redrawButton.setEnabled(false);
                        saveButton.setEnabled(false);
                        redrawButton.setClickable(false);
                        saveButton.setClickable(false);
                        gestureOverlayView.setVisibility(View.GONE);
                    }


                } else {

                    gestureOverlayView.setVisibility(View.VISIBLE);
                    title.setText("Upload your signature");
                    view_photo.setVisibility(View.GONE);

//                    if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
//                        title.setTextColor(getResources().getColor(R.color.black));
//                    } else {
//                        title.setTextColor(getResources().getColor(R.color.white));
//                    }
                    redrawButton.setEnabled(true);
                    saveButton.setEnabled(true);
                    redrawButton.setClickable(true);
                    saveButton.setClickable(true);

                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION) {

            int grantResultsLength = grantResults.length;

            if (grantResultsLength > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                saveSignature();

            } else {
                Toast.makeText(getMainActivity().getApplicationContext(), "You denied write external storage permission.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void saveSignature() {
        try {
            // First destroy cached image.
            gestureOverlayView.destroyDrawingCache();

            // Enable drawing cache function.
            gestureOverlayView.setDrawingCacheEnabled(true);

            // Get drawing cache bitmap.
            Bitmap drawingCacheBitmap = gestureOverlayView.getDrawingCache();

            // Create a new bitmap
            Bitmap bitmap = Bitmap.createBitmap(drawingCacheBitmap);
            // Get image file save path and name.
            String filePath = Environment.getExternalStorageDirectory().toString();

            filePath += File.separator;

            filePath += "sign.TIFF";

            selectedPath = filePath;

            File file = new File(filePath);

            file.createNewFile();

            FileOutputStream fileOutputStream = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);

            fileOutputStream.flush();

            fileOutputStream.close();


            if (bitmap != null) {

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                showProgress();
                uploadMultipart();

            } else {

                Toast.makeText(getMainActivity(), "Please draw your signature.", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Log.v("Signature Gestures", e.getMessage());
            e.printStackTrace();
        }
    }

    public void uploadMultipart() {
        //getting name for the image
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US);
        Date now = new Date();
        String fileName = formatter.format(now);


        Uri filePath = Uri.fromFile(new File(selectedPath));

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
                        .addParameter("imgeId", "3") //Adding text parameter to the request
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
                                GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Signature upload failed !",
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
                                GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Signature uploaded successfully.",
                                        "Ok", false, new GreekDialog.DialogListener() {

                                            @Override
                                            public void alertDialogAction(GreekDialog.Action action, Object... data) {

                                                if (action == GreekDialog.Action.OK) {

                                                    nextScreen_flag = true;

                                                    title.setText("Your signature uploaded.");
                                                    view_photo.setVisibility(View.VISIBLE);


                                                    gestureOverlayView.clear(false);
                                                    guesturePerformed = false;

                                                    showProgress();
                                                    MFundMediaUploadRequest.sendRequestKYC(AccountDetails.getUsername(getMainActivity()), AccountDetails.getUserPAN(getMainActivity()), "01", getMainActivity(), serviceResponseHandler);


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

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {

        btn_previous.setVisibility(View.GONE);
        next_btn.setVisibility(View.GONE);
        saveButton.setVisibility(View.GONE);

    }

    @Override
    public void onError(@NonNull VerificationError verificationError) {

    }


    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback onNextClickedCallback) {


    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback onCompleteClickedCallback) {


        onCompleteClickedCB = onCompleteClickedCallback;

        if (nextScreen_flag) {
            Intent i = new Intent(getMainActivity(), LoginActivity.class);
            startActivity(i);
            getMainActivity().finish();

        } else {

            checkPermissionAndSaveSignature();

        }


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
        GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Signature upload failed !",
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
        GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Signature uploaded successfully.",
                "Ok", false, new GreekDialog.DialogListener() {

                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {

                        if (action == GreekDialog.Action.OK) {

                            nextScreen_flag = true;

                            title.setText("Your signature uploaded.");
                            view_photo.setVisibility(View.VISIBLE);


                            gestureOverlayView.clear(false);
                            guesturePerformed = false;

                            showProgress();
                            MFundMediaUploadRequest.sendRequestKYC(AccountDetails.getUsername(getMainActivity()), AccountDetails.getUserPAN(getMainActivity()), "01", getMainActivity(), serviceResponseHandler);


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

    @Override
    public void onGesturePerformed(GestureOverlayView gestureOverlayView, Gesture gesture) {

    }
}
