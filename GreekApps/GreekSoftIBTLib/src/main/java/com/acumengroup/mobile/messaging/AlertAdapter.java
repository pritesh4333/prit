package com.acumengroup.mobile.messaging;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.market.StreamingController;
import com.acumengroup.mobile.BottomTabScreens.PendingTabFragment;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.alert.AlertData;
import com.acumengroup.ui.textview.GreekTextView;

import java.util.ArrayList;

public class AlertAdapter  extends RecyclerView.Adapter<AlertAdapter.ViewHolder>{

    public ArrayList<AlertData> alertData;
    private Activity activity;
    AlertDialog.Builder alert;
    private StreamingController streamingController;
    public String alerttype;
    RelativeLayout errorLayout;


    public AlertAdapter(AppCompatActivity mainActivity, ArrayList<AlertData> alertDataBookModel, String alertType, RelativeLayout errorMsgLayout) {
    this.alertData=alertDataBookModel;
    this.activity=mainActivity;
    this.alerttype=alertType;
    this.errorLayout=errorMsgLayout;
    }

    public void addItem(AlertData alertData1)
    {
        alertData.add(alertData1);
    }

    @NonNull
    @Override
    public AlertAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_alert_book, parent, false);
        return new AlertAdapter.ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull AlertAdapter.ViewHolder holder, final int position) {
        AlertData alertDataitem =alertData.get(position);

        if (AccountDetails.getThemeFlag(activity).equalsIgnoreCase("white")) {
            holder.row11.setTextColor(activity.getColor(R.color.black));
            holder.row12.setTextColor(activity.getColor(R.color.black));
            holder.row13.setTextColor(activity.getColor(R.color.black));
            holder.row21.setTextColor(activity.getColor(R.color.black));
            holder.row22.setTextColor(activity.getColor(R.color.black));
        }


            holder.row11.setText(alertDataitem.getDescription());
        holder.row21.setText(alertDataitem.getExchange() +"-" +alertDataitem.getSeries_instname() );
        if (alertDataitem.getAlertType().equals("1")) {
            holder.row12.setText("Price");
        } else if (alertDataitem.getAlertType().equals("2")) {
            holder.row12.setText("Percent");
        } else if (alertDataitem.getAlertType().equals("3")) {
            holder.row12.setText("Volume");
         }

        if (alertDataitem.getDirectionFlag().equals("0")) {
            holder.row13.setText("up");

        } else if (alertDataitem.getDirectionFlag().equals("1")) {
            holder.row13.setText("down");
        }
        holder.row22.setText(String.format("%.2f", Double.parseDouble(String.valueOf(alertDataitem.getRange()))));
        if (alertDataitem.getIsExecuted().equalsIgnoreCase("0")) {

            holder.row23.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_thumb_down_red_24dp, 0);

        } else if (alertDataitem.getIsExecuted().equalsIgnoreCase("1")) {
            holder.row23.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_thumb_up_green_24dp, 0);
        }

        holder.row_linear_layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                removeItemFromList(position);
                return true;
            }
        });
    }
    //TODO: To delete item from list
    private void removeItemFromList(final int position) {
        final int deletePosition = position;

        if (AccountDetails.getThemeFlag(activity).equalsIgnoreCase("white")) {
            alert = new AlertDialog.Builder( activity, R.style.AlertDialogTheme);
        } else {

           // alert = new AlertDialog.Builder( activity, R.style.AlertDialogThemeDark);
        }

        AlertDialog.Builder alert = new AlertDialog.Builder( activity);
        alert = new AlertDialog.Builder( activity, R.style.AlertDialogTheme);

        alert.setTitle("Delete Alert");
        alert.setMessage("Do you want delete this item?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                streamingController = new StreamingController();
                streamingController.sendStreamingAlertBroadcastRequest( activity, AccountDetails.getClientCode( activity),
                        AccountDetails.getSessionId( activity), alertData.get(position).getRuleNo(),
                        alertData.get(position).getRange(),alertData.get(position).getDirectionFlag(),
                        alertData.get(position).getLourToken(), alerttype, "1", null, null);
                alertData.remove(position);
                notifyDataSetChanged();
                if(alertData.size() < 1)
                {
                    errorLayout.setVisibility(View.VISIBLE);
                }
                else
                {
                    errorLayout.setVisibility(View.GONE);
                }
             }
        });

        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    @Override
    public int getItemCount() {
        return alertData.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        GreekTextView  row11,  row12,  row13,  row21,  row22,  row23;
        LinearLayout row_linear_layout;
        public ViewHolder(View itemView) {
            super(itemView);

            row11=(GreekTextView)itemView.findViewById(R.id.row11);
            row12=(GreekTextView)itemView.findViewById(R.id.row12);
            row13=(GreekTextView)itemView.findViewById(R.id.row13);
            row21=(GreekTextView)itemView.findViewById(R.id.row21);
            row22=(GreekTextView)itemView.findViewById(R.id.row22);
            row23=(GreekTextView)itemView.findViewById(R.id.row23);
            row_linear_layout=(LinearLayout) itemView.findViewById(R.id.row_linear_layout);


        }
    }
}
