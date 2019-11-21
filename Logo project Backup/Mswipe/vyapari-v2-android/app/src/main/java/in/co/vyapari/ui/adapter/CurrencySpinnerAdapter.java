package in.co.vyapari.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import in.co.vyapari.R;
import in.co.vyapari.model.Currency;

import java.util.ArrayList;

public class CurrencySpinnerAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Currency> currencies;
    private LayoutInflater inflter;

    public CurrencySpinnerAdapter(Context applicationContext, ArrayList<Currency> currencies) {
        this.context = applicationContext;
        if (currencies == null) {
            currencies = new ArrayList<>();
        }
        this.currencies = currencies;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return currencies.size();
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
        View row = View.inflate(context, R.layout.item_currency, null);
        TextView label = row.findViewById(R.id.item_currency_value);
        label.setText(currencies.get(position).getValue());
        return row;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = View.inflate(context, R.layout.item_currency, null);
        TextView label = row.findViewById(R.id.item_currency_value);
        label.setText(currencies.get(position).getValue());
        return row;
    }

}
