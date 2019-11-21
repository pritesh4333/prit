package in.co.vyapari.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.co.vyapari.R;
import in.co.vyapari.model.KeyValue;
import in.co.vyapari.model.response.dto.CollectionTypeDTO;
import in.co.vyapari.model.response.dto.SectorDTO;

public class OneLineSpinnerAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<KeyValue> strings;
    private List<CollectionTypeDTO> collection;
    private LayoutInflater inflter;
    private int color;

    public OneLineSpinnerAdapter(Context applicationContext, ArrayList<KeyValue> strings) {
        this.context = applicationContext;
        if (strings == null) {
            strings = new ArrayList<>();
        }
        this.strings = strings;
        this.color = Color.parseColor("#000000");
        inflter = (LayoutInflater.from(applicationContext));
    }

    public OneLineSpinnerAdapter(Context applicationContext, List<SectorDTO> strings) {
        this.context = applicationContext;
        if (strings == null) {
            strings = new ArrayList<>();
        }
        this.strings = new ArrayList<>();
        for (SectorDTO sectorDTO : strings) {
            this.strings.add(new KeyValue(sectorDTO.getCode(), sectorDTO.getTitle()));
        }
        this.color = Color.parseColor("#ffffff");
        inflter = (LayoutInflater.from(applicationContext));
    }

    public OneLineSpinnerAdapter(Context applicationContext, ArrayList<KeyValue> strings, int color) {
        this.context = applicationContext;
        this.strings = strings;
        this.color = color;
        inflter = (LayoutInflater.from(applicationContext));
    }

    public OneLineSpinnerAdapter(Context applicationContext, List<CollectionTypeDTO> arapNo,int color) {
        this.context = applicationContext;
        if (strings == null) {
            strings = new ArrayList<>();
        }
        this.strings = new ArrayList<>();
        for (CollectionTypeDTO collectiondto : arapNo) {
            this.strings.add(new KeyValue(collectiondto.getCode(), collectiondto.getDescription()));
        }
        this.color = color;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return strings.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View row = View.inflate(context, R.layout.item_oneline_sp, null);
        TextView label = row.findViewById(R.id.item_title);
        label.setText(strings.get(position).getValue());
        return row;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = View.inflate(context, R.layout.item_oneline_sp_view, null);
        TextView label = row.findViewById(R.id.item_title_view);
        label.setText(strings.get(position).getValue());
        label.setTextColor(color);
        return row;
    }
}
