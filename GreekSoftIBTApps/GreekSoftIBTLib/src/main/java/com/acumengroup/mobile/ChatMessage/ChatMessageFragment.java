package com.acumengroup.mobile.ChatMessage;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.model.ChatMessage.ChatMessageRequest;
import com.acumengroup.greekmain.core.parser.JSONResponse;
import com.acumengroup.mobile.GreekBaseFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.ui.edittext.GreekEditText;
import com.acumengroup.ui.textview.GreekTextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChatMessageFragment extends GreekBaseFragment {

    private View mChangePwdView;
    private GreekTextView text_news_label;
    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private RecyclerView msgRecyclerView;
    private GreekEditText msgInputText;
    private ImageButton msgSendButton;
    private List<ChatAppMsgDTO> msgDtoList;
    private MessageListAdapter chatAppMsgAdapter;
    private RelativeLayout errorLayout;

    @Override
    public void onFragmentResume() {
        super.onFragmentResume();

    }

    @Override
    public void onResume() {
        super.onResume();
        sendChatMessage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mChangePwdView = super.onCreateView(inflater, container, savedInstanceState);

        if (AccountDetails.getThemeFlag(getMainActivity()).equalsIgnoreCase("white")) {
            attachLayout(R.layout.fragment_chat_message).setBackgroundColor(getResources().getColor(AccountDetails.backgroundColor));

        } else {
            attachLayout(R.layout.fragment_chat_message).setBackground(getResources().getDrawable(AccountDetails.backgroundColor));

        }

        AccountDetails.currentFragment = NAV_TO_CHAT_MESSAGE_SCREEN;

        text_news_label = mChangePwdView.findViewById(R.id.text_news_recomm);
        //text_news_label.setTextColor(getResources().getColor(AccountDetails.textColorDropdown));
        setAppTitle(getClass().toString(), "Chat Messages");

        sendChatMessage();

        errorLayout = mChangePwdView.findViewById(R.id.showmsgLayout);
        ((GreekTextView) errorLayout.findViewById(R.id.errorHeader)).setText("No data available.");

        msgRecyclerView = mChangePwdView.findViewById(R.id.chat_recycler_view);
        msgInputText = mChangePwdView.findViewById(R.id.chat_input_msg);
        msgSendButton = mChangePwdView.findViewById(R.id.chat_send_msg);

        // Set RecyclerView layout manager.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getMainActivity());
        msgRecyclerView.setLayoutManager(linearLayoutManager);

        // Create the initial data list.
        msgDtoList = new ArrayList<ChatAppMsgDTO>();


        // Create the data adapter with above data list.
        chatAppMsgAdapter = new MessageListAdapter(msgDtoList);

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

    private void sendChatMessage() {

        ChatMessageRequest.sendRequest(AccountDetails.getUsername(getMainActivity()), "10", getMainActivity(), serviceResponseHandler);
    }

    @Override
    public void handleResponse(Object response) {

        JSONResponse jsonResponse = (JSONResponse) response;
        msgDtoList.clear();

        if (LOGIN_SVC_GROUP.equals(jsonResponse.getServiceGroup()) && CHATMESSAGE_SVC_NAME.equals(jsonResponse.getServiceName())) {
            try {

                toggleErrorLayout(false);
                //ChatMessageResponse chatMessageResponse = (ChatMessageResponse) jsonResponse.getResponse();
                JSONObject jsonOBData = jsonResponse.getResponseData();

                if(jsonOBData.optJSONArray("SymbolName") != null) {
                    JSONArray jsonArrayData = jsonOBData.getJSONArray("SymbolName");
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
}
