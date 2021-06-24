package com.acumengroup.mobile.login;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.acumengroup.mobile.R;

public class PasswordChangeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);

        String PassExpiryType = getIntent().getStringExtra("PassExpiryType");
        String Comingfrom = getIntent().getStringExtra("Comingfrom");



        Bundle bundle = new Bundle();
        bundle.putString("Comingfrom", Comingfrom);


        if (PassExpiryType.equalsIgnoreCase("LoginPass")) {

            bundle.putString("PassExpiryType", "LoginPass");


        } else if (PassExpiryType.equalsIgnoreCase("TransPass")) {

            bundle.putString("PassExpiryType", "TransPass");

        } else if (PassExpiryType.equalsIgnoreCase("BothPass")) {

            bundle.putString("PassExpiryType", "BothPass");
        }

        Fragment someFragment = new PasswordChangeFragment();
        someFragment.setArguments(bundle);
        FragmentTransaction trans_nome = getSupportFragmentManager().beginTransaction();
        trans_nome.add(R.id.transaction_layout, someFragment);
        trans_nome.commit();
    }
}
