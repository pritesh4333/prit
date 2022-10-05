package com.acumengroup.mobile.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.acumengroup.greekmain.core.constants.GreekConstants;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.MutualFund.MarshMallowPermission;
import com.acumengroup.mobile.MutualFund.UploadAadharDetails;
import com.acumengroup.mobile.MutualFund.UploadBankDetails;
import com.acumengroup.mobile.MutualFund.UploadChequeDetails;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.trade.KYCUploadPhoto;
import com.acumengroup.mobile.trade.UploadIpvp;
import com.acumengroup.mobile.trade.UploadSignature;
import com.acumengroup.mobile.trade.UploadpanDetails;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

import static com.acumengroup.mobile.trade.KYCUploadPhoto.CAMERA_REQUEST;
import static com.acumengroup.mobile.trade.KYCUploadPhoto.GALLERY_PICTURE;
import static com.acumengroup.mobile.trade.KYCUploadPhoto.onCaptureImageResult;
import static com.acumengroup.mobile.trade.KYCUploadPhoto.onSelectFromGalleryResult;
import static com.acumengroup.mobile.trade.UploadIpvp.OnVideoCaptureResult;
import static com.acumengroup.mobile.trade.UploadIpvp.VIDEO_CAPTURED;

public class UserKycValidation extends AppCompatActivity implements GreekConstants, StepperLayout.StepperListener {

    private String from = "";
    public GreekBaseFragment previousFragment;
    public StepperLayout mStepperLayout;
    public MyStepperAdapter myStepperAdapter;
    private RelativeLayout progressLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_kyc_validation);

        from = getIntent().getStringExtra("from");
        Bundle args = new Bundle();
        args.putString("from", from);

        mStepperLayout = findViewById(R.id.stepperLayout);
        progressLayout = findViewById(R.id.customProgress);

        myStepperAdapter = new MyStepperAdapter(getSupportFragmentManager(), this);
        mStepperLayout.setAdapter(myStepperAdapter);
        mStepperLayout.setBackButtonEnabled(true);
        mStepperLayout.setCompleteButtonEnabled(true);
        mStepperLayout.setTabNavigationEnabled(false);

    }

    // Replace current Fragment with the destination Fragment.
    public void replaceFragment(Fragment destFragment) {
        // First get FragmentManager object.
        FragmentManager fragmentManager = this.getSupportFragmentManager();

        // Begin Fragment transaction.
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Replace the layout holder with the required Fragment object.
        //fragmentTransaction.replace(R.id.frame1, destFragment);

        // Commit the Fragment replace action.
        fragmentTransaction.commit();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void onCompleted(View completeButton) {

        Toast.makeText(this, "onCompleted!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(VerificationError verificationError) {
        Toast.makeText(this, "onCompleted!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStepSelected(int i) {
        Toast.makeText(this, "onCompleted!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReturn() {
        finish();
    }

    public static class MyStepperAdapter extends AbstractFragmentStepAdapter {

        public MyStepperAdapter(FragmentManager fm, Context context) {
            super(fm, context);
        }

        @Override
        public Step createStep(int position) {

            if (position == 0) {
                final UserCreationPanDetails step = new UserCreationPanDetails();
                Bundle b = new Bundle();
                b.putInt("Current Position", position);
                step.setArguments(b);
                return step;
            } else if (position == 1) {
                final UserCreationPersonalDetails step1 = new UserCreationPersonalDetails();
                Bundle b = new Bundle();
                b.putInt("Current Position", position);
                step1.setArguments(b);
                return step1;
            } else if (position == 2) {
                final UserCreationBankDetails step = new UserCreationBankDetails();
                Bundle b = new Bundle();
                b.putInt("Current Position", position);
                step.setArguments(b);
                return step;
            } else if (position == 3) {
                final KYCUploadPhoto step = new KYCUploadPhoto();
                Bundle b = new Bundle();
                b.putInt("Current Position", position);
                step.setArguments(b);
                return step;
            } else if (position == 4) {
                final UploadpanDetails step = new UploadpanDetails();
                Bundle b = new Bundle();
                b.putInt("Current Position", position);
                step.setArguments(b);
                return step;
            } else if (position == 5) {
                final UploadAadharDetails step = new UploadAadharDetails();
                Bundle b = new Bundle();
                b.putInt("Current Position", position);
                step.setArguments(b);
                return step;
            } else if (position == 6) {
                final UploadBankDetails step = new UploadBankDetails();
                Bundle b = new Bundle();
                b.putInt("Current Position", position);
                step.setArguments(b);
                return step;
            } else if (position == 7) {
                final UploadChequeDetails step = new UploadChequeDetails();
                Bundle b = new Bundle();
                b.putInt("Current Position", position);
                step.setArguments(b);
                return step;
            } else if (position == 8) {
                final UploadIpvp step = new UploadIpvp();
                Bundle b = new Bundle();
                b.putInt("Current Position", position);
                step.setArguments(b);
                return step;
            } else if (position == 9) {
                final UploadSignature step = new UploadSignature();
                Bundle b = new Bundle();
                b.putInt("Current Position", position);
                step.setArguments(b);
                return step;
            } else {
                return null;

            }

        }

        @Override
        public int getCount() {
            return 10;
        }

        @NonNull
        @Override
        public StepViewModel getViewModel(@IntRange(from = 0) int position) {
            //Override this method to set Step title for the Tabs, not necessary for other stepper types
            StepViewModel.Builder builder = new StepViewModel.Builder(context);

            switch (position) {
                case 0:
                    builder
                            .setEndButtonLabel("Save & Proceed")
                            .setTitle("Pan Details");

                    break;
                case 1:
                    builder
                            .setEndButtonLabel("Save & Proceed")
                            .setTitle("Personal Details");
                    break;

                case 2:
                    builder
//                            .setEndButtonLabel("Go to KYC Upload Document")
                            .setEndButtonLabel("Save & Proceed")
                            .setTitle("Bank Details");
                    break;

                case 3:
                    builder
//                            .setEndButtonLabel("Go to PAN Photo Upload ")
                            .setEndButtonLabel("Save & Proceed")
                            .setTitle("Face Photo Upload");
                    break;

                case 4:
                    builder
//                            .setEndButtonLabel("Go to IPVP upload")
                            .setEndButtonLabel("Save & Proceed")
                            .setTitle("PAN Photo Upload ");
                    break;
                case 5:
                    builder
//                            .setEndButtonLabel("Go to IPVP upload")
                            .setEndButtonLabel("Save & Proceed")
                            .setTitle("Aadhar Photo Upload ");
                    break;
                case 6:
                    builder
//                            .setEndButtonLabel("Go to IPVP upload")
                            .setEndButtonLabel("Save & Proceed")
                            .setTitle("Bank Photo Upload ");
                    break;
                case 7:
                    builder
//                            .setEndButtonLabel("Go to IPVP upload")
                            .setEndButtonLabel("Save & Proceed")
                            .setTitle("Cheque Photo Upload ");
                    break;
                case 8:
                    builder
//                            .setEndButtonLabel("Go to Signature upload")
                            .setEndButtonLabel("Save & Proceed")
                            .setTitle("PAN With Face Video Upload ");
                    break;
                case 9:
                    builder
                            .setEndButtonLabel("Complete")
                            .setTitle("Signature Upload");
                    break;


            }
            return builder.create();
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

        } else if (requestCode == MarshMallowPermission.VIDEO_PERMISSION_REQUEST_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                Intent intent = new Intent(android.provider.MediaStore.ACTION_VIDEO_CAPTURE);
                intent.putExtra(android.provider.MediaStore.EXTRA_DURATION_LIMIT, 10);
                startActivityForResult(intent, VIDEO_CAPTURED);
            }

        } else if (requestCode == MarshMallowPermission.EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                Intent pictureActionIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pictureActionIntent, GALLERY_PICTURE);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {

            onCaptureImageResult(data);

        } else if (requestCode == VIDEO_CAPTURED && resultCode == Activity.RESULT_OK) {

            OnVideoCaptureResult(data, getApplicationContext());

        } else if (resultCode == RESULT_OK && requestCode == GALLERY_PICTURE) {
            if (data != null) {

                onSelectFromGalleryResult(data);

            } else {
                Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void showProgress() {

        if (progressLayout != null)
            progressLayout.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progressLayout.setVisibility(View.GONE);
    }

}
