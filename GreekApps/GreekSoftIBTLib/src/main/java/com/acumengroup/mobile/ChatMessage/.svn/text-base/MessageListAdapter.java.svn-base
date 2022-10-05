package com.acumengroup.mobile.ChatMessage;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.acumengroup.mobile.R;
import com.acumengroup.greekmain.util.date.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<ChatAppMsgViewHolder> {

    private List<ChatAppMsgDTO> msgDtoList = null;

    public MessageListAdapter(List<ChatAppMsgDTO> msgDtoList) {

        this.msgDtoList = msgDtoList;
    }

    @Override
    public void onBindViewHolder(ChatAppMsgViewHolder holder, int position) {

        ChatAppMsgDTO msgDto = this.msgDtoList.get(position);
        // If the message is a received message.
        if(ChatAppMsgDTO.MSG_TYPE_RECEIVED.equals(msgDto.getMsgType()))
        {
            // Show received message in left linearlayout.
            holder.leftMsgLayout.setVisibility(LinearLayout.VISIBLE);
            holder.leftMsgTextView.setText(msgDto.getMsgContent());

            holder.txt_time.setText(DateTimeFormatter.getDateFromTimeStampForchatMSG(msgDto.getlLogTime(),"dd/MM/yyyy HH:mm","nse") );
            holder.txt_sender.setText(msgDto.getcSenderId()+":");
            // Remove left linearlayout.The value should be GONE, can not be INVISIBLE
            // Otherwise each iteview's distance is too big.
            holder.rightMsgLayout.setVisibility(LinearLayout.GONE);
        }
        // If the message is a sent message.
        else if(ChatAppMsgDTO.MSG_TYPE_SENT.equals(msgDto.getMsgType()))
        {
            // Show sent message in right linearlayout.
            holder.rightMsgLayout.setVisibility(LinearLayout.VISIBLE);
            holder.rightMsgTextView.setText(msgDto.getMsgContent());
            holder.txt_timeSend.setText(DateTimeFormatter.getDateFromTimeStamp(msgDto.getlLogTime(),"dd/MM/yyyy HH:mm","nse") );

            // Remove left linearlayout.The value should be GONE, can not be INVISIBLE
            // Otherwise each iteview's distance is too big.
            holder.leftMsgLayout.setVisibility(LinearLayout.GONE);
        }
    }

    @Override
    public ChatAppMsgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.fragment_chat_app_item_view, parent, false);
        return new ChatAppMsgViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if(msgDtoList==null)
        {
            msgDtoList = new ArrayList<ChatAppMsgDTO>();
        }
        return msgDtoList.size();
    }
}
