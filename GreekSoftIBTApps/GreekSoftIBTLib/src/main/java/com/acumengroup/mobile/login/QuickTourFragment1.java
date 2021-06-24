package com.acumengroup.mobile.login;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.R;

/**
 * Created by user on 16-Oct-17.
 */

public class QuickTourFragment1 extends Fragment {

    private static final String ARG_LAYOUT_RES_ID = "layoutResId";
    private int layoutResId;

    public static QuickTourFragment1 newInstance(int layoutResId) {
        QuickTourFragment1 sampleSlide = new QuickTourFragment1();
        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT_RES_ID, layoutResId);
        sampleSlide.setArguments(args);
        return sampleSlide;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(ARG_LAYOUT_RES_ID)) {
            layoutResId = getArguments().getInt(ARG_LAYOUT_RES_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(layoutResId, container, false);
        RelativeLayout layout=view.findViewById(R.id.quicktour1);
        String packagname = getActivity().getPackageName();
        if(layoutResId == R.layout.quickintro2){

            if (packagname.equalsIgnoreCase("com.vishwas.mobile")){
                layout.setBackground(getActivity().getResources().getDrawable(R.drawable.vishwas_scrip_intro));
            }else if (packagname.equalsIgnoreCase("com.tog.mobile")){
                layout.setBackground(getActivity().getResources().getDrawable(R.drawable.phillips_scrip_intro));
            }else if (packagname.equalsIgnoreCase("com.msfl.mobile")){
                layout.setBackground(getActivity().getResources().getDrawable(R.drawable.scrip_intro));
            }

        }if(layoutResId == R.layout.quickintro3){
            if (packagname.equalsIgnoreCase("com.vishwas.mobile")){
                layout.setBackground(getActivity().getResources().getDrawable(R.drawable.vishwas_watchlist_intro));
            }else if (packagname.equalsIgnoreCase("com.tog.mobile")){
                layout.setBackground(getActivity().getResources().getDrawable(R.drawable.phillips_watchlist_intro));
            }else if (packagname.equalsIgnoreCase("com.msfl.mobile")){
                layout.setBackground(getActivity().getResources().getDrawable(R.drawable.watchlist_intro));
            }

        }



        return view;
    }
}
