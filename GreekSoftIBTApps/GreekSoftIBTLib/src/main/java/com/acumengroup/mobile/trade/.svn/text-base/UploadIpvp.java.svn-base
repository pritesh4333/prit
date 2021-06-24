package com.acumengroup.mobile.trade;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.MutualFundSipNewOrder.MFundMediaAlreadyUploadResponce;
import com.acumengroup.greekmain.core.model.MutualFundSipNewOrder.MFundMediaUploadRequest;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.MutualFund.MarshMallowPermission;
import com.acumengroup.mobile.R;
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

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import static androidx.appcompat.app.AppCompatActivity.RESULT_OK;
import static com.acumengroup.greekmain.core.network.WSHandler.SERVER_URL;
import static com.acumengroup.mobile.GreekBaseActivity.GREEK;

public class UploadIpvp extends GreekBaseFragment implements View.OnClickListener, Step, BlockingStep, SingleUploadBroadcastReciever.Delegate {

    private static String selectedFilePath = null;
    public static final int VIDEO_CAPTURED = 2;
    private ProgressBar progressBar;
    private static VideoView videoview;
    private Button btntakepoto, btnsubmit, btn_next, btn_previous;
    private GreekTextView title, view_video;
    private MarshMallowPermission marshMallowPermission;
    private MFundMediaAlreadyUploadResponce mFundMediaAlreadyUploadResponce;
    private boolean nextScreen_flag = false;
    private StepperLayout.OnNextClickedCallback onNextClickedCB;
    private final SingleUploadBroadcastReciever uploadReceiver =
            new SingleUploadBroadcastReciever();
    private boolean IsUploadReceiverRegister = false;
    private MediaController mediacontroller;
    private Uri uri;
    private boolean isContinuously = false;


    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btn_take_video) {

            boolean result = marshMallowPermission.checkPermissionForCameraAndGallery();
            if (result) {

                CameraRecordIntent();

            } else {

                marshMallowPermission.requestPermissionForVideoAndStorage();

            }

        } else if (view.getId() == R.id.btn_video_submit) {

            SubmitVideoDetails();

        } else if (view.getId() == R.id.btn_next) {

            navigateTo(NAV_TO_MUTUALFUND_SIGNATURE_UPLOAD, getArguments(), false);

        } else if (view.getId() == R.id.btn_previous) {

            navigateTo(NAV_TO_MUTUALFUND_KYC_CHEQUE_UPLOAD, getArguments(), false);
        }
    }

    private void SubmitVideoDetails() {

        if (selectedFilePath != null) {

            showProgress();
            uploadMultipart();

        } else {
            Toast.makeText(getContext(), "Something wrong happened.", Toast.LENGTH_LONG).show();
        }


    }

    private void CameraRecordIntent() {

        Intent intent = new Intent(android.provider.MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(android.provider.MediaStore.EXTRA_DURATION_LIMIT, 10);
        startActivityForResult(intent, VIDEO_CAPTURED);

    }

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        AccountDetails.currentFragment = NAV_TO_MUTUALFUND_KYC_IPVP_UPLOAD;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        View mfActionView = super.onCreateView(inflater, container, savedInstanceState);
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_kyc_ipvp_document).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));
        } else {
            attachLayout(R.layout.fragment_kyc_ipvp_document).setBackground(getResources().getDrawable(R.drawable.bg_drawable));
        }


        marshMallowPermission = new MarshMallowPermission(getMainActivity());

        SetupView(mfActionView);
        btntakepoto.setOnClickListener(this);
        btnsubmit.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        btn_previous.setOnClickListener(this);

        MFundMediaUploadRequest.sendRequestUpload(AccountDetails.getUsername(getMainActivity()), "4", AccountDetails.getUserPAN(getMainActivity()), getMainActivity(), serviceResponseHandler);

        mediacontroller = new MediaController(getContext());
        mediacontroller.setAnchorView(videoview);
        //String uriPath = "https://www.demonuts.com/Demonuts/smallvideo.mp4";
        String uriPath = AccountDetails.getIsSecure() + "://" + AccountDetails.getArachne_IP() + ":" + AccountDetails.getArachne_Port() + "/downloadVideo?videoName=IPV_" + AccountDetails.getUserPAN(getMainActivity()) + ".mp4";
//        String uriPath = "https://www.demonuts.com/Demonuts/smallvideo.mp4";
        uri = Uri.parse(uriPath);

        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                progressBar.setVisibility(View.GONE);
            }
        });


        view_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isContinuously = false;
                progressBar.setVisibility(View.VISIBLE);
                videoview.setMediaController(mediacontroller);
                videoview.setVideoURI(uri);
                videoview.requestFocus();
                videoview.start();
            }
        });


        return mfActionView;
    }


    private void SetupView(View mfActionView) {

        progressBar = mfActionView.findViewById(R.id.progrss);
        btntakepoto = mfActionView.findViewById(R.id.btn_take_video);
        btn_next = mfActionView.findViewById(R.id.btn_next);
        btn_previous = mfActionView.findViewById(R.id.btn_previous);
        btnsubmit = mfActionView.findViewById(R.id.btn_video_submit);
        videoview = mfActionView.findViewById(R.id.vdo_pan);
        title = mfActionView.findViewById(R.id.ipvp_title);
        view_video = mfActionView.findViewById(R.id.view_video);
        view_video.setOnClickListener(this);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            title.setTextColor(getResources().getColor(R.color.black));
        } else if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("black")) {
            title.setTextColor(getResources().getColor(R.color.white));

        }


        if (AccountDetails.getClientCode(getMainActivity()) != null && !AccountDetails.getClientCode(getMainActivity()).equalsIgnoreCase("ClientCode")) {

            btnsubmit.setVisibility(View.VISIBLE);

        } else {

            btnsubmit.setVisibility(View.GONE);

        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && requestCode == VIDEO_CAPTURED) {

            OnVideoCaptureResult(data, getMainActivity());

        }
    }


    public static void OnVideoCaptureResult(Intent data, Context context) {

        MediaController mc = new MediaController(context);
        videoview.setMediaController(mc);

        //Saving image to mobile internal memory for sometime
        Uri selectedImageUri = data.getData();

        selectedFilePath = getPath(selectedImageUri, context);

        Bitmap thumb = ThumbnailUtils.createVideoThumbnail(selectedFilePath, MediaStore.Images.Thumbnails.MINI_KIND);

        BitmapDrawable bitmapDrawable = new BitmapDrawable(thumb);
        videoview.setBackgroundDrawable(bitmapDrawable);


    }

    @Override
    public void handleResponse(Object response) {
        // super.handleResponse(response);
        hideProgress();

        JSONResponse jsonResponse = (JSONResponse) response;
        if (UPLOAD_PHOTO_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && UPLOAD_PHOTO_SVC_GROUP.equals(jsonResponse.getServiceName())) {

            try {
                nextScreen_flag = true;

                if (onNextClickedCB != null) {
                    onNextClicked(onNextClickedCB);
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
                    videoview.setVisibility(View.VISIBLE);
                    title.setText("Your face and pan video already uploaded.");
                    view_video.setVisibility(View.VISIBLE);
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


                    videoview.setVisibility(View.VISIBLE);

                    title.setText("Upload your face and pan video ");
                    view_video.setVisibility(View.GONE);
//                    if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
//                        title.setTextColor(getResources().getColor(R.color.black));
//                    } else {
//                        title.setTextColor(getResources().getColor(R.color.white));
//                    }
                    btntakepoto.setEnabled(true);
                    btnsubmit.setEnabled(true);
                    btnsubmit.setClickable(true);
                    btntakepoto.setClickable(true);

                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


    public void uploadMultipart() {
        //getting name for the image
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US);
        Date now = new Date();
        String fileName = formatter.format(now);


        Uri filePath = Uri.fromFile(new File(selectedFilePath));

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
                        .addParameter("imgeId", "4") //Adding text parameter to the request
                        .addParameter("clientCode", AccountDetails.getUsername(getMainActivity())) //Adding text parameter to the request
                        .addParameter("imgeType", "mp4") //Adding text parameter to the request
                        //.addParameter("panNo",AccountDetails.getUserPAN(getMainActivity())) //Adding text parameter to the request
                        .addParameter("panNo", AccountDetails.getUserPAN(getMainActivity()))
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(2)
                        .setDelegate(new UploadStatusDelegate() {
                            @Override
                            public void onProgress(Context context, UploadInfo uploadInfo) {

                            }

                            @Override
                            public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {

                                hideProgress();
                                GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Video upload failed !",
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
                                GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Video uploaded successfully.",
                                        "Ok", false, new GreekDialog.DialogListener() {

                                            @Override
                                            public void alertDialogAction(GreekDialog.Action action, Object... data) {

                                                if (action == GreekDialog.Action.OK) {

                                                    // imgPan.setVisibility(View.GONE);
                                                    btnsubmit.setEnabled(false);
                                                    btntakepoto.setEnabled(false);
                                                    btnsubmit.setClickable(false);
                                                    btntakepoto.setClickable(false);
                                                    // btn_next.setVisibility(View.VISIBLE);

                                                    SharedPreferences.Editor editor = Util.getPrefs(getMainActivity()).edit();
                                                    editor.putString("uploadChequeSubmit", "yes");
                                                    editor.apply();
                                                    editor.commit();

                                                    title.setText("Uploaded your face and pan video");
                                                    view_video.setVisibility(View.GONE);

                                                    nextScreen_flag = true;
                                                    selectedFilePath = null;

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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MarshMallowPermission.VIDEO_PERMISSION_REQUEST_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                CameraRecordIntent();

            }

        }

    }


    public static String getPath(Uri uri, Context context) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = context.getContentResolver().query(
                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
        cursor.close();

        return path;
    }


    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {

        btn_next.setVisibility(View.GONE);
        btn_previous.setVisibility(View.GONE);
        btnsubmit.setVisibility(View.GONE);

    }

    @Override
    public void onError(@NonNull VerificationError verificationError) {

    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback onNextClickedCallback) {

        onNextClickedCB = onNextClickedCallback;

        if (selectedFilePath == null) {

            onNextClickedCallback.goToNextStep();

        } else {

            SubmitVideoDetails();

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
        GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Video upload failed !",
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
        GreekDialog.alertDialog(getMainActivity(), 0, GREEK, "Video uploaded successfully.",
                "Ok", false, new GreekDialog.DialogListener() {

                    @Override
                    public void alertDialogAction(GreekDialog.Action action, Object... data) {

                        if (action == GreekDialog.Action.OK) {

                            btnsubmit.setEnabled(false);
                            btntakepoto.setEnabled(false);
                            btnsubmit.setClickable(false);
                            btntakepoto.setClickable(false);
                            // btn_next.setVisibility(View.VISIBLE);

                            SharedPreferences.Editor editor = Util.getPrefs(getMainActivity()).edit();
                            editor.putString("uploadChequeSubmit", "yes");
                            editor.apply();
                            editor.commit();

                            title.setText("Uploaded your face and pan video");

                            view_video.setVisibility(View.GONE);
                            nextScreen_flag = true;
                            selectedFilePath = null;
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
