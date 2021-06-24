package com.acumengroup.mobile.BottomTabScreens;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.greekmain.core.market.StreamingController;
import com.acumengroup.greekmain.core.model.marketsindianindices.IndianIndice;
import com.acumengroup.greekmain.core.model.marketsindianindices.MarketsIndianIndicesResponse;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.BottomTabScreens.adapter.EditMarketAdapter;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.edittext.GreekEditText;

import java.util.ArrayList;
import java.util.Locale;

public class MarketEditFragment extends GreekBaseFragment {

    View editmarketview;
    RecyclerView editMarketRecyclerView;
    StreamingController streamController;
    MarketsIndianIndicesResponse indianIndicesResponse;
    private int maxSizeList;
    private EditMarketAdapter adapter;
    ArrayList<IndianIndice> temp_list;
    ArrayList<IndianIndice> filterList;
    String imageId;
    String assetType;
    GreekEditText searchText;
    private LinearLayout main_linear;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        editmarketview = inflater.inflate(R.layout.fragment_edit_market, container, false);
        if (getArguments() != null) {
            imageId = getArguments().getString("imageid");
            assetType = getArguments().getString("assetType");
        }
        SetupView(editmarketview);
        sendIndianIndicesRequest();
        AccountDetails.currentFragment = NAV_TO_MARKET_EDIT_FRAGMENT;
        settheme();
        return editmarketview;
    }

    private void settheme() {
        if (AccountDetails.getThemeFlag(getActivity()).equalsIgnoreCase("White")) {
            searchText.setTextColor(getResources().getColor(R.color.gray_date));
            searchText.setHintTextColor(getResources().getColor(R.color.grey_text));
            searchText.setBackgroundDrawable(getMainActivity().getResources().getDrawable(R.drawable.rounded_react_shape_gray));
            searchText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_gray_24dp, 0, 0, 0);
            main_linear.setBackgroundColor(getResources().getColor(R.color.white));

        }

    }


    private void sendIndianIndicesRequest() {
        streamController.sendIndianIndicesRequest(getMainActivity(), serviceResponseHandler);
    }

    private void SetupView(View editmarketview) {
        serviceResponseHandler = new ServiceResponseHandler(getMainActivity(), this);
        streamController = new StreamingController();

        editMarketRecyclerView = editmarketview.findViewById(R.id.edit_market_recyleview);
        main_linear = editmarketview.findViewById(R.id.main_linear);
        searchText = editmarketview.findViewById(R.id.edit_search);
        filterList = new ArrayList<>();


        searchText.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = searchText.getText().toString().toLowerCase(Locale.getDefault());

                filter(text);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editMarketRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), editMarketRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                final int pos = position;
                Bundle bundle = new Bundle();
                bundle.putString("symbol", temp_list.get(position).getToken());
                OverviewTabFragment frag = new OverviewTabFragment();
                frag.setArguments(bundle);

                ImageView iv = view.findViewById(R.id.img_add);
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Bundle bundle = new Bundle();

                        if (filterList.get(pos).getToken().equalsIgnoreCase("nifty 50")) {
                            bundle.putString("symbol", "Nifty");
                        } else {
                            bundle.putString("symbol", filterList.get(pos).getToken());
                        }

                        bundle.putString("token", filterList.get(pos).getIndexCode());
                        bundle.putString("ltp", filterList.get(pos).getLtp());
                        bundle.putString("high", filterList.get(pos).getHigh());
                        bundle.putString("low", filterList.get(pos).getLow());

                            if (getAssetType(filterList.get(pos).getIndexCode()).equalsIgnoreCase("currency")) {
                                bundle.putString("change", String.format("%.4f", Double.parseDouble(filterList.get(pos).getChange())) + "(" + String.format("%.2f", Double.parseDouble(filterList.get(pos).getP_change())) + "%)");
                            } else {
                                bundle.putString("change", String.format("%.2f", Double.parseDouble(filterList.get(pos).getChange())) + "(" + String.format("%.2f", Double.parseDouble(filterList.get(pos).getP_change())) + "%)");
                            }
                        bundle.putString("clickimg", imageId);
                        setArguments(bundle);
                        hideKeyboard(getMainActivity());

                        if (previousFragment != null)
                            previousFragment.onFragmentResult(bundle);
                        goBackOnce();
                    }
                });


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Override
    public void handleResponse(Object response) {
        JSONResponse jsonResponse = (JSONResponse) response;
        try {

            indianIndicesResponse = (MarketsIndianIndicesResponse) jsonResponse.getResponse();
            updateAdapterSize();
            hideProgress();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void updateAdapterSize() {

        temp_list = new ArrayList<>();
        IndianIndice indianIndice = new IndianIndice();

        if (indianIndicesResponse != null) {

            maxSizeList = indianIndicesResponse.getIndianIndices().size();

            for (int i = 0; i < maxSizeList; i++) {
                if(!indianIndicesResponse.getIndianIndices().get(i).getToken().equalsIgnoreCase("")&&Double.parseDouble(indianIndicesResponse.getIndianIndices().get(i).getIndexCode())>0) {
                    indianIndice = indianIndicesResponse.getIndianIndices().get(i);
                    temp_list.add(indianIndice);
                }

            }

            filterList.addAll(temp_list);

            adapter = new EditMarketAdapter(filterList, getMainActivity(), imageId);
            editMarketRecyclerView.setLayoutManager(new LinearLayoutManager(getMainActivity()));
            editMarketRecyclerView.setHasFixedSize(true);
            editMarketRecyclerView.setAdapter(adapter);

        }
    }


    private class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener) {

            this.clicklistener = clicklistener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recycleView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clicklistener != null) {
                        clicklistener.onLongClick(child, recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clicklistener != null && gestureDetector.onTouchEvent(e)) {
                clicklistener.onClick(child, rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }


    public void filter(final String text) {

        // Searching could be complex..so we will dispatch it to a different thread...
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    // Clear the filter list
                    filterList.clear();

                    // If there is no search value, then add all original list items to filter list
                    if (TextUtils.isEmpty(text)) {

                        /*hideicon = true;
                        invalidateOptionsMenu();*/

                        filterList.addAll(temp_list);


                    } else {
                        // Iterate in the original List and add it to filter list...
                        for (IndianIndice item : temp_list) {
                            if (item.getToken().toLowerCase().contains(text.toLowerCase())) {
                                // Adding Matched items
                                filterList.add(item);
                            }

                        }
                    }

                    // Set on UI Thread
                    getMainActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Notify the List that the DataSet has changed...

                            adapter = new EditMarketAdapter(filterList, getMainActivity(), imageId);
                            editMarketRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            editMarketRecyclerView.setHasFixedSize(true);
                            editMarketRecyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();

                        }
                    });
                } catch (Exception e) {
                    System.out.println("Error in filter");
                    e.printStackTrace();
                }


            }
        }).start();

    }


    private String getAssetType(String token) {
        int tokenInt = Integer.parseInt(token);
        if(((tokenInt >= 101000000) && (tokenInt <= 101999999)) || ((tokenInt >= 201000000) && (tokenInt <= 201999999))) {
            return "equity";
        } else if(((tokenInt >= 102000000) && (tokenInt <= 102999999)) || ((tokenInt >= 202000000) && (tokenInt <= 202999999))) {
            return "fno";
        } else if(((tokenInt >= 403000000) && (tokenInt <= 403999999)) || ((tokenInt >= 303000000) && (tokenInt <= 303999999))) {
            return "commodity";
        } else {
            return "currency";
        }
    }

    public void hideKeyboard(AppCompatActivity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
