package warehousedelivery.taaxgenie.in.resagitpvtltd;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class callrecord extends RecyclerView.Adapter<callrecord.ViewHolder> {

    private ArrayList<GetSet> orgCompListItems;
    private Context context;
    private LayoutInflater inflater;

    public callrecord(CallRecords callRecords, ArrayList<GetSet> organisationlist) {
        this.context = callRecords;
        this.orgCompListItems = organisationlist;

    }

    @Override
    public callrecord.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        View view = null;
        if (inflater != null) {
            view = inflater.inflate(R.layout.adapter_getset, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.name.setText(orgCompListItems.get(i).getName());
        viewHolder.number.setText(orgCompListItems.get(i).getNumber());
        viewHolder.type.setText(orgCompListItems.get(i).getType());
        viewHolder.date.setText(orgCompListItems.get(i).getDate());
        viewHolder.duration.setText(orgCompListItems.get(i).getDureation());

    }


    @Override
    public int getItemCount() {
        return orgCompListItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, number, type, date, duration;


        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            number = itemView.findViewById(R.id.number);
            type = itemView.findViewById(R.id.type);
            date = itemView.findViewById(R.id.date);
            duration = itemView.findViewById(R.id.duration);

        }
    }


}
