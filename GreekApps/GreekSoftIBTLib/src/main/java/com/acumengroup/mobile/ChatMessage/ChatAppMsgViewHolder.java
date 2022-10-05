package com.acumengroup.mobile.ChatMessage;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.acumengroup.mobile.R;
import com.acumengroup.ui.textview.GreekTextView;

public class ChatAppMsgViewHolder extends RecyclerView.ViewHolder {

    public LinearLayout leftMsgLayout;

    public LinearLayout rightMsgLayout;

    public GreekTextView leftMsgTextView;
    public GreekTextView txt_sender;
    public GreekTextView txt_time;
    public GreekTextView txt_timeSend;
    public GreekTextView txt_timeSends;

    public GreekTextView rightMsgTextView;

    public ChatAppMsgViewHolder(View itemView) {
        super(itemView);

        if (itemView != null) {

            leftMsgLayout = itemView.findViewById(R.id.chat_left_msg_layout);
            rightMsgLayout = itemView.findViewById(R.id.chat_right_msg_layout);
            leftMsgTextView = itemView.findViewById(R.id.chat_left_msg_text_view);
            txt_sender = itemView.findViewById(R.id.txt_sender);
            txt_time = itemView.findViewById(R.id.txt_time);
            txt_timeSend = itemView.findViewById(R.id.txt_timeSend);
            rightMsgTextView = itemView.findViewById(R.id.chat_right_msg_text_view);
        }
    }
}
