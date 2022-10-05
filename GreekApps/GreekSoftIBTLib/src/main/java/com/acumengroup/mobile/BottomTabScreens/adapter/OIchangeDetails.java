package com.acumengroup.mobile.BottomTabScreens.adapter;


import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.mobile.BottomTabScreens.adapter.childAdaper.OptionChainAdapter;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;

import java.util.ArrayList;

public class OIchangeDetails extends GreekBaseFragment {

    public OIchangeDetails() {
    }
RecyclerView recyclerView;
    OptionChainAdapter optionAdapter;
    ArrayList<String>symbolname,optionsList,instuName;
    Spinner spnrexchange,spnrSymbol,spnrexpiry;
    ArrayList<OptionChainhashData> optionChainhashData;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View derivativeView = inflater.inflate(R.layout.fragment_oichange_detail, container, false);

        setupView(derivativeView);
        return derivativeView;

    }

    private void setupView(View derivativeView) {
        recyclerView= derivativeView.findViewById(R.id.oi_detail_list);
        spnrSymbol= derivativeView.findViewById(R.id.spnr_symbol);
        spnrexchange= derivativeView.findViewById(R.id.spnr_exchange);
        spnrexpiry= derivativeView.findViewById(R.id.spnr_expiry);

        symbolname = new ArrayList<>();
        symbolname.add("Nifty");
        symbolname.add("Rel");

        instuName = new ArrayList<>();
        instuName.add("NSE");

        optionsList = new ArrayList<>();
        optionsList.add("26 SEPTEMBER 2019");
        optionsList.add("26 APRIL 2019");
        optionsList.add("26 MAY 2019");
        optionsList.add("26 AUGUST 2019");
        optionsList.add("26 DECEMBER 2019");



        ArrayAdapter<String> symbolAdapter = new ArrayAdapter<>(getActivity(), AccountDetails.getRowSpinnerSimple(), symbolname);
        symbolAdapter.setDropDownViewResource(R.layout.custom_spinner);
        spnrSymbol.setAdapter(symbolAdapter);
        spnrSymbol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//                sendRequest(optionsList.get(position).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> instumentAdapter = new ArrayAdapter<>(getActivity(), AccountDetails.getRowSpinnerSimple(), instuName);
        instumentAdapter.setDropDownViewResource(R.layout.custom_spinner);
        spnrexchange.setAdapter(instumentAdapter);
        spnrexchange.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//                sendRequest(optionsList.get(position).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> expiryAdater = new ArrayAdapter<>(getActivity(), AccountDetails.getRowSpinnerSimple(), optionsList);
        expiryAdater.setDropDownViewResource(R.layout.custom_spinner);
        spnrexpiry.setAdapter(expiryAdater);
        spnrexpiry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//                sendRequest(optionsList.get(position).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


      //  optionChainhashData=new ArrayList<>();

        optionChainhashData = new ArrayList<>();
        for(int i=0;i<10;i++) {
            OptionChainhashData optionChainhashDataList = new OptionChainhashData();
            optionChainhashDataList.setStrike("11450");
            optionChainhashDataList.setdLtp_call("11450");
            optionChainhashDataList.setdLtp_put("11450");
            optionChainhashDataList.setdNetChange_call("11450");
            optionChainhashDataList.setdNetChange_put("11450");
            optionChainhashDataList.setlOpenInterest_call("11450");
            optionChainhashDataList.setlOpenInterest_put("11450");
            optionChainhashDataList.setAskPrice_call("11450");
            optionChainhashDataList.setAskPrice_put("11450");
            optionChainhashDataList.setlBidPrice_call("11450");
            optionChainhashDataList.setlBidPrice_put("11450");
            optionChainhashDataList.setlVolume_call("11450");
            optionChainhashDataList.setlVolume_put("11450");
            optionChainhashData.add(optionChainhashDataList);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        optionAdapter = new OptionChainAdapter((AppCompatActivity) getActivity(), R.layout.fragment_option_chain_row, optionChainhashData);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.setAdapter(optionAdapter);

    }


    public OIchangeDetails newInstance() {

      return new OIchangeDetails();
    }
}
