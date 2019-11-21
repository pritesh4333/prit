package in.co.vyaparienterprise.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import in.co.vyaparienterprise.R;
import in.co.vyaparienterprise.model.KeyValue;

import java.util.ArrayList;

public class PhoneCallAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private ArrayList<KeyValue> dataList;

    public PhoneCallAdapter(Context context, ArrayList<KeyValue> dataList) {
        if (dataList == null) {
            dataList = new ArrayList<>();
        }
        layoutInflater = LayoutInflater.from(context);
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_bottom_dialog_list, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.textView = view.findViewById(R.id.phone_call_name);
            viewHolder.imageView = view.findViewById(R.id.phone_call_image);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        KeyValue data = dataList.get(position);
        viewHolder.textView.setText(data.getKey());
        viewHolder.imageView.setImageResource(R.drawable.icon_phone);

        return view;
    }

    static class ViewHolder {
        TextView textView;
        ImageView imageView;
    }
}
