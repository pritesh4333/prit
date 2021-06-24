package com.acumengroup.mobile.messaging;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.Notifications.NotificationRequest;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.ChatMessage.ChatAppMsgDTO;
import com.acumengroup.mobile.ChatMessage.MessageListAdapter;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.edittext.GreekEditText;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.greekmain.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class NotificationsFragment extends GreekBaseFragment {

    private View mChangePwdView;
    private GreekTextView text_news_label;
    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private RecyclerView msgRecyclerView;
    private GreekEditText msgInputText;
    private ImageButton msgSendButton;
    private List<ChatAppMsgDTO> msgDtoList;
    private NotificationListAdapter chatAppMsgAdapter;
    private RelativeLayout errorLayout;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
            EventBus.getDefault().post("NotificationIcon");
        }

    }

    @Override
    public void onFragmentPause() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onFragmentPause();
    }

    @Override
    public void onPause() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
            EventBus.getDefault().post("NotificationIcon");
        }
        sendNotification();
    }

    private final SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
// after pull down we are request notification request to aracane server
            sendNotification();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mChangePwdView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_chat_message).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));

        } else {
            attachLayout(R.layout.fragment_chat_message).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));

        }

        AccountDetails.currentFragment = NAV_TO_NOTIFICATION_SCREEN;

        text_news_label = mChangePwdView.findViewById(R.id.text_news_recomm);
        swipeRefreshLayout = mChangePwdView.findViewById(R.id.refreshList);
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        //text_news_label.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        setAppTitle(getClass().toString(), "Notifications");

        SharedPreferences.Editor editor = Util.getPrefs(getMainActivity()).edit();
        editor.putInt("fcmcount", 0);
        //editor.putInt("NotificationVal", 0);
        editor.putBoolean("Notification", false).apply();
        editor.commit();


        sendNotification();

        errorLayout = mChangePwdView.findViewById(R.id.showmsgLayout);
        ((GreekTextView) errorLayout.findViewById(R.id.errorHeader)).setText("No data available.");
        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            ((GreekTextView) errorLayout.findViewById(R.id.errorHeader)).setTextColor(getResources().getColor(R.color.black));
        }else{
            ((GreekTextView) errorLayout.findViewById(R.id.errorHeader)).setTextColor(getResources().getColor(R.color.white));

        }

        msgRecyclerView = mChangePwdView.findViewById(R.id.chat_recycler_view);
        msgInputText = mChangePwdView.findViewById(R.id.chat_input_msg);
        msgSendButton = mChangePwdView.findViewById(R.id.chat_send_msg);

        // Set RecyclerView layout manager.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getMainActivity());
        msgRecyclerView.setLayoutManager(linearLayoutManager);

        // Create the initial data list.
        msgDtoList = new ArrayList<ChatAppMsgDTO>();


        // Create the data adapter with above data list.
        chatAppMsgAdapter = new NotificationListAdapter(msgDtoList);

        // Set data adapter to RecyclerView.
        msgRecyclerView.setAdapter(chatAppMsgAdapter);


        msgSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String msgContent = msgInputText.getText().toString();

                if (!TextUtils.isEmpty(msgContent)) {

                    // Add a new sent message to the list.
                    ChatAppMsgDTO msgDto = new ChatAppMsgDTO(ChatAppMsgDTO.MSG_TYPE_SENT, msgContent);
                    msgDtoList.add(msgDto);

                    int newMsgPosition = msgDtoList.size() - 1;

                    // Notify recycler view insert one new data.
                    chatAppMsgAdapter.notifyItemInserted(newMsgPosition);

                    // Scroll RecyclerView to the last message.
                    msgRecyclerView.scrollToPosition(newMsgPosition);

                    // Empty the input edit text box.
                    msgInputText.setText("");

                }
            }
        });

        return mChangePwdView;
    }

    private void toggleErrorLayout(boolean show) {
        errorLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void sendNotification() {

        NotificationRequest.sendRequest(AccountDetails.getUsername(getMainActivity()), getMainActivity(), serviceResponseHandler);
    }
    private void refreshComplete() {
        hideProgress();
        if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void handleResponse(Object response) {

        JSONResponse jsonResponse = (JSONResponse) response;
        msgDtoList.clear();
       refreshComplete();

        if ("getNotifications".equals(jsonResponse.getServiceName())) {
            try {

                toggleErrorLayout(false);
                //ChatMessageResponse chatMessageResponse = (ChatMessageResponse) jsonResponse.getResponse();
                JSONObject jsonOBData = jsonResponse.getResponseData();

                if(jsonOBData.optJSONArray("Data") != null) {
                    JSONArray jsonArrayData = jsonOBData.getJSONArray("Data");
// for (int i = jsonArrayData.length()-1; i >= 0; i--) for reverse order

                    for (int i = 0; i < jsonArrayData.length(); i++) {

                        JSONObject jsonObject = jsonArrayData.getJSONObject(i);

                        String lLogTime = jsonObject.getString("lLogTime");
                        String cSenderId = jsonObject.getString("cSenderId");
                        String cRecieverId = jsonObject.getString("cRecieverId");
                        String cMessage = jsonObject.getString("cMessage");
                        String iMsgType = jsonObject.getString("iMsgType");
                        String bSendStatus = jsonObject.getString("bSendStatus");
                        String iSendingChannel = jsonObject.getString("iSendingChannel");


                        ChatAppMsgDTO msgDto = new ChatAppMsgDTO();


                        if (!cSenderId.equalsIgnoreCase(AccountDetails.getUserCode(getMainActivity()))) {
                            //This messages will come on left side of screen.

                            msgDto.setMsgType(ChatAppMsgDTO.MSG_TYPE_RECEIVED);
                            msgDto.setMsgContent(cMessage);

                            msgDto.setlLogTime(lLogTime);
                            msgDto.setcSenderId(cSenderId);
                            msgDto.setcRecieverId(cRecieverId);
                            msgDto.setiMsgType(iMsgType);
                            msgDto.setiSendingChannel(iSendingChannel);
                            msgDto.setbSendStatus(bSendStatus);


                        } else {


//                        This messages will come on Right side of screen.

                            msgDto.setMsgType(ChatAppMsgDTO.MSG_TYPE_SENT);
                            msgDto.setMsgContent(cMessage);

                            msgDto.setlLogTime(lLogTime);
                            msgDto.setcSenderId(cSenderId);
                            msgDto.setcRecieverId(cRecieverId);
                            msgDto.setiMsgType(iMsgType);
                            msgDto.setiSendingChannel(iSendingChannel);
                            msgDto.setbSendStatus(bSendStatus);
                        }

                        msgDtoList.add(msgDto);

                        int newMsgPosition = msgDtoList.size() - 1;

                        // Notify recycler view insert one new data.
                        chatAppMsgAdapter.notifyItemInserted(newMsgPosition);
                        chatAppMsgAdapter.notifyDataSetChanged();

                        // Scroll RecyclerView to the last message.
//                    msgRecyclerView.scrollToPosition(newMsgPosition);

                    }

                }
                else
                {
                    toggleErrorLayout(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onEventMainThread(String icon) {


    }
}

