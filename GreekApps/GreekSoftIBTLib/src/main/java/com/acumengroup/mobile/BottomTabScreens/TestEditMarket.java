package com.acumengroup.mobile.BottomTabScreens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.acumengroup.greekmain.core.market.StreamingController;
import com.acumengroup.greekmain.core.model.marketsindianindices.IndianIndice;
import com.acumengroup.greekmain.core.model.marketsindianindices.MarketsIndianIndicesResponse;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.BottomTabScreens.adapter.EditMarketAdapter;
import com.acumengroup.mobile.GreekBaseActivity;
import com.acumengroup.mobile.R;
import com.acumengroup.greekmain.core.app.ServiceResponseHandler;
import com.acumengroup.ui.edittext.GreekEditText;

import java.util.ArrayList;
import java.util.Locale;

public class TestEditMarket extends GreekBaseActivity {

    StreamingController streamController;
    private RecyclerView editMarketRecyclerView;
    private MarketsIndianIndicesResponse indianIndicesResponse;
    private int maxSizeList;
    private EditMarketAdapter adapter;
    ArrayList<IndianIndice> temp_list;
    ArrayList<IndianIndice> filterList;
    String imageId;
    GreekEditText searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_market);
        Intent intent = getIntent();
        imageId = intent.getStringExtra("imageid");
        SetupView();
        sendIndianIndicesRequest();
    }

    private void SetupView() {
        serviceResponseHandler = new ServiceResponseHandler(this, this);
        streamController = new StreamingController();

        editMarketRecyclerView = findViewById(R.id.edit_market_recyleview);
        searchText = findViewById(R.id.edit_search);
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

        /*Intent intent=new Intent();
                    intent.putExtra("MESSAGE",message);
                    setResult(2,intent);
                    finish();//finishing activity  */

        editMarketRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, editMarketRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
//                Toast.makeText(getApplicationContext(),"Click",Toast.LENGTH_LONG).show();

                  /*      Intent intent = new Intent();
                        intent.putExtra("symbol", filterList.get(position).getToken());
                        intent.putExtra("ltp", filterList.get(position).getLtp());
                        intent.putExtra("change", filterList.get(position).getChange() + "(" + temp_list.get(position).getP_change() + "%)");
                        intent.putExtra("clickimg", imageId);

                        setResult(2, intent);
//                MarketBottomFragment frag = new MarketBottomFragment();

                        finish();*/

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    private void sendIndianIndicesRequest() {
//        streamController.sendIndianIndicesRequesNew(getMainActivity(), serviceResponseHandler);
        streamController.sendIndianIndicesRequest(this, serviceResponseHandler);
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

                indianIndice = indianIndicesResponse.getIndianIndices().get(i);
                temp_list.add(indianIndice);
            }

            filterList.addAll(temp_list);

            adapter = new EditMarketAdapter(filterList, this, imageId);
            editMarketRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            editMarketRecyclerView.setHasFixedSize(true);
            editMarketRecyclerView.setAdapter(adapter);

        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent = new Intent();
            intent.putExtra("symbol", "");
            intent.putExtra("ltp", "");
            intent.putExtra("change", "");
            intent.putExtra("clickimg", "");
            setResult(2, intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
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

    // Do Search...
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
                    (TestEditMarket.this).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Notify the List that the DataSet has changed...

                            adapter = new EditMarketAdapter(filterList, TestEditMarket.this, imageId);
                            editMarketRecyclerView.setLayoutManager(new LinearLayoutManager(TestEditMarket.this));
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
}
