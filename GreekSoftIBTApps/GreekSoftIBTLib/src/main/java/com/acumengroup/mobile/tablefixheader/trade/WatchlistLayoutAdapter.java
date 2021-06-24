package com.acumengroup.mobile.tablefixheader.trade;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.tablefixheader.adapters.BaseTableAdapter;
import com.acumengroup.greekmain.core.constants.GreekConstants;
import com.acumengroup.ui.textview.GreekTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Arcadia
 */
public class WatchlistLayoutAdapter extends BaseTableAdapter {
    private final ArrayList<HashMap<String, String>> hashArray;
    private final ArrayList<String> tokenList;
    private final List<RowData> familys;
    private final String[] headers;
    private final int[] widths;
    private final float density;
    private final Handler handler = new Handler();
    private int ltpColumn = -1;
    private boolean isStreamingLtp = false;
    private List<Integer> streamCols = new ArrayList<>();
    private boolean sortingEnabled = false;
    private String clickedOn = "";
    private boolean ascending = false;
    private LayoutInflater inflater = null;
    private TableItemClickListener clickListener;
    private Context context;
    private TableSortListener sortListener;
    private final Runnable sortCallback = new Runnable() {
        @Override
        public void run() {
            if (sortListener != null) sortListener.onSortListener();
        }
    };

    public WatchlistLayoutAdapter(Context context, String[] headers, int[] widths) {
        hashArray = new ArrayList<>();
        tokenList = new ArrayList<>();
        familys = new ArrayList<>();
        this.context = context;
        this.headers = headers;
        this.widths = widths;
        density = context.getResources().getDisplayMetrics().density;
        inflater = LayoutInflater.from(context);
    }

    public ArrayList<String> getSymbolTable() {
        return tokenList;
    }

    public void clear() {
        hashArray.clear();
        tokenList.clear();
        familys.clear();
        notifyDataSetChanged();
    }

    //LTP Column(1-based index)
    public void setLTPColumn(int column, boolean isStreaming) {
        //Converting to 0-based index
        ltpColumn = column;
        isStreamingLtp = isStreaming;
    }

    public void setStreamingColumns(Integer[] columns) {
        streamCols = Arrays.asList(columns);
    }

    public void setSortingEnabled(boolean enabled) {
        this.sortingEnabled = enabled;
    }

    public boolean containsSymbol(String symbol) {
        return tokenList.contains(symbol);
    }

    public int indexOf(String symbol) {
        return tokenList.indexOf(symbol);
    }

    public void add(HashMap<String, String> row) {
        hashArray.add(row);
        tokenList.add(row.get(GreekConstants.TOKEN));
        switch (headers.length) {
            case 4:
                familys.add(new RowData(row.get(headers[0]), row.get(headers[1]), row.get(headers[2]), row.get(headers[3])));
                break;
            case 6:
                familys.add(new RowData(row.get(headers[0]), row.get(headers[1]), row.get(headers[2]), row.get(headers[3]), row.get(headers[4]), row.get(headers[5])));
                break;
            case 7:
                familys.add(new RowData(row.get(headers[0]), row.get(headers[1]), row.get(headers[2]), row.get(headers[3]), row.get(headers[4]), row.get(headers[5]), row.get(headers[6])));
                break;
            /*case 8:
                familys.add(new RowData(row.get(headers[0]), row.get(headers[1]), row.get(headers[2]), row.get(headers[3]), row.get(headers[4]) + "#" + row.get("NewChange"), row.get(headers[5]), row.get(headers[6]), row.get(headers[7]), "", "", ""));
                break;
            case 10:
                familys.add(new RowData(row.get(headers[0]), row.get(headers[1]) + "#" + row.get("NewChange"), row.get(headers[2]), row.get(headers[3]), row.get(headers[4]), row.get(headers[5]), row.get(headers[6]), row.get(headers[7]), row.get(headers[8]), row.get(headers[9]), ""));
                break;
            case 11:
                familys.add(new RowData(row.get(headers[0]), row.get(headers[1]) + "#" + row.get("NewChange"), row.get(headers[2]), row.get(headers[3]), row.get(headers[4]), row.get(headers[5]), row.get(headers[6]), row.get(headers[7]), row.get(headers[8]), row.get(headers[9]), row.get(headers[10])));
                break;*/
        }
    }

    public int size() {
        return familys.size();
    }

    public HashMap<String, String> getItem(int position) {
        return hashArray.get(position);
    }

    public void setItem(int position, HashMap<String, String> row) {
        hashArray.set(position, row);
        switch (headers.length) {
            case 4:
                familys.set(position, new RowData(row.get(headers[0]), row.get(headers[1]) + "#" + row.get("NewChange"), row.get(headers[2]), row.get(headers[3])));
                break;
           /* case 6:
                familys.add(new RowData(row.get(headers[0]), row.get(headers[1]), row.get(headers[2]), row.get(headers[3]), row.get(headers[4]), row.get(headers[5]), "", "", "", "", ""));
                break;
            case 8:
                familys.set(position, new RowData(row.get(headers[0]), row.get(headers[1]), row.get(headers[2]), row.get(headers[3]), row.get(headers[4]) + "#" + row.get("NewChange"), row.get(headers[5]), row.get(headers[6]), row.get(headers[7]), "", "", ""));
                break;
            case 10:
                familys.set(position, new RowData(row.get(headers[0]), row.get(headers[1]) + "#" + row.get("NewChange"), row.get(headers[2]), row.get(headers[3]), row.get(headers[4]), row.get(headers[5]), row.get(headers[6]), row.get(headers[7]), row.get(headers[8]), row.get(headers[9]), ""));
                break;
            case 11:
                familys.set(position, new RowData(row.get(headers[0]), row.get(headers[1]) + "#" + row.get("NewChange"), row.get(headers[2]), row.get(headers[3]), row.get(headers[4]), row.get(headers[5]), row.get(headers[6]), row.get(headers[7]), row.get(headers[8]), row.get(headers[9]), row.get(headers[10])));
                break;*/
        }
    }

    public void setOnRowClickListener(TableItemClickListener listener) {
        this.clickListener = listener;
    }

    public void setOnSortListener(TableSortListener sortListener) {
        this.sortListener = sortListener;
    }

    @Override
    public int getRowCount() {
        return familys.size();
    }

    @Override
    public int getColumnCount() {
        return headers.length;
    }

    @Override
    public View getView(final int row, int column, View convertView, ViewGroup parent) {
        final View view;

        switch (getItemViewType(row, column)) {
            case 0:
                view = getFirstHeader(convertView, parent);
                if (sortingEnabled) view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickedOn = headers[0];
                        ascending = !ascending;
                        sortBySymbol(false, clickedOn, ascending);
                    }
                });
                break;
            case 1:
                view = getHeader(column + 1, convertView, parent);
                if (sortingEnabled)
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickedOn = ((TextView) v).getText().toString();
                        ascending = !ascending;
                        if (clickedOn.equalsIgnoreCase(GreekConstants.GREEK_VIEW)) {
                            clickedOn = headers[0];
                            //Sorting by Symbol
                            sortBySymbol(false, clickedOn, ascending);
                        } else {
                            sortBySymbol(true, clickedOn, ascending);
                        }
                    }
                });
                break;
            case 2:
                view = getFirstBody(row, column + 1, convertView, parent);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (clickListener != null) clickListener.onClick(row, view);
                    }
                });
                break;
            case 3:
                view = getBody(row, column + 1, convertView, parent);
                if (column == 9) {
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (clickListener != null) clickListener.onGreekViewClick(row, view);
                        }
                    });
                } else {
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (clickListener != null) clickListener.onClick(row, view);
                        }
                    });
                }
                break;
            default:
                throw new RuntimeException("runtime exception");
        }
        return view;
    }

    private View getFirstHeader(View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_grid_first_header, parent, false);
        }
        TextView head = ((TextView) convertView);//.findViewById(android.R.id.text1));
        head.setText(headers[0]);
        head.setTextSize(12);
        if (sortingEnabled) if (clickedOn.equals(headers[0]) && !ascending) {
            head.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow4_up, 0);
        } else {
            head.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow4_down, 0);
        }
        if (AccountDetails.getThemeFlag(context).equalsIgnoreCase("white")) {
            head.setBackgroundResource(R.color.gray_background);
        }
        else
        {
            head.setBackgroundResource(R.color.common_red_bg);
        }
        return convertView;
    }

    private View getHeader(int column, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_grid_header, parent, false);
        }
        TextView head = (TextView) convertView;
        if (sortingEnabled) if (clickedOn.equals(headers[column]) && !ascending) {
            head.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow4_up, 0);
        } else {
            head.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow4_down, 0);
        }
        if (AccountDetails.getThemeFlag(context).equalsIgnoreCase("white")) {
            head.setBackgroundResource(R.color.gray_background);
        }
        else
        {
            head.setBackgroundResource(R.color.common_red_bg);
        }
        head.setText(headers[column]);
        return convertView;
    }

    private String getItem(int row, int pos) {
        return familys.get(row).data[pos];
    }

    private View getFirstBody(int row, int column, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_grid_first_body, parent, false);
        }
        ((TextView) convertView).setText(getItem(row, column));
        //convertView.setBackgroundResource(row % 2 == 0 ? R.drawable.bottom_border : R.drawable.bottom_border);
        if (AccountDetails.getThemeFlag(context).equalsIgnoreCase("white")) {
            convertView.setBackgroundResource(row % 2 == 0 ? R.drawable.bottom_border : R.drawable.bottom_border);
            ((TextView) convertView).setTextColor(Color.BLACK);
        }
        else
        {
            convertView.setBackgroundResource(row % 2 == 0 ? R.color.market_grey_light : R.color.market_grey_dark);
            ((TextView) convertView).setTextColor(Color.WHITE);
        }
        return convertView;
    }

    private View getBody(final int row, int column, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_grid_body, parent, false);
        }
        GreekTextView text1 = (GreekTextView) convertView;
        boolean isOdd = (row % 2 == 0);
        if (column == ltpColumn) {
            String ltp = getItem(row, column);
            if (isStreamingLtp) {
                String[] parts = ltp.split("#");
                text1.setColorForLTP(isOdd, parts[1].equals("green"));
                text1.setText(parts[0]);
                text1.setFont(Typeface.BOLD);
                text1.setTextColor(Color.WHITE);
            } else {
                String toSet = ltp.contains("#") ? ltp.split("#")[0] : ltp;
                //text1.setBackgroundResource(isOdd ? R.drawable.bottom_border : R.drawable.bottom_border);
                text1.setText(toSet);
                text1.setFont(Typeface.NORMAL);
                text1.setTextColor(Color.BLACK);
                text1.setTextSize(12);
                if (AccountDetails.getThemeFlag(context).equalsIgnoreCase("white")) {
                    text1.setBackgroundResource(isOdd ? R.drawable.bottom_border : R.drawable.bottom_border);
                    ((TextView) convertView).setTextColor(Color.BLACK);
                }
                else
                {
                    text1.setBackgroundResource(isOdd ? R.color.market_grey_light : R.color.market_grey_dark);
                    ((TextView) convertView).setTextColor(Color.WHITE);
                }
            }
        } else {
            if (streamCols.contains(column)) {
                text1.setChangeColor(isOdd, getItem(row, column));
                text1.setText(getItem(row, column));
                text1.setFont(Typeface.BOLD);
                text1.setTextColor(Color.WHITE);
            } else if (column == 10) {
                text1.setGreekView(isOdd, getItem(row, column));
            } else {
                //text1.setBackgroundResource(isOdd ? R.drawable.bottom_border : R.drawable.bottom_border);
                text1.setText(getItem(row, column));
                text1.setFont(Typeface.NORMAL);
                text1.setTextColor(Color.BLACK);
                text1.setTextSize(12);
                if (AccountDetails.getThemeFlag(context).equalsIgnoreCase("white")) {
                    text1.setBackgroundResource(isOdd ? R.drawable.bottom_border : R.drawable.bottom_border);
                    ((TextView) convertView).setTextColor(Color.BLACK);
                }
                else
                {
                    text1.setBackgroundResource(isOdd ? R.color.market_grey_light : R.color.market_grey_dark);
                    ((TextView) convertView).setTextColor(Color.WHITE);
                }
            }
        }

        return convertView;
    }

    @Override
    public int getWidth(int column) {
        if (column < widths.length - 1) return Math.round(widths[column + 1] * density);
        else return 0;
    }

    @Override
    public int getHeight(int row) {
        int height;
        if (row == -1) height = 35;
        else height = 40;
        return Math.round(height * density);
    }

    @Override
    public int getItemViewType(int row, int column) {
        int itemViewType;
        if (row == -1 && column == -1) {
            itemViewType = 0;
        } else if (row == -1) {
            itemViewType = 1;
        } else if (column == -1) {
            itemViewType = 2;
        } else {
            itemViewType = 3;
        }
        return itemViewType;
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    private void sortBySymbol(boolean isDouble, String sortCol, boolean ascending) {
        if (isDouble) {
            ArrayList<Double> out = new ArrayList<>();

            for (int j = 0; j < hashArray.size(); j++) {
                LinkedHashMap linkedHashMap = (LinkedHashMap) hashArray.get(j);
                String val = (String) linkedHashMap.get(sortCol);
                Double value = Double.valueOf(val);
                out.add(value);
            }

            Collections.sort(out, new Comparator<Double>() {
                public int compare(Double c1, Double c2) {
                    return c1.compareTo(c2);
                }
            });
            if (ascending) {
                Collections.reverse(out);
            }

            ArrayList<HashMap<String, String>> tempHashArray = (ArrayList<HashMap<String, String>>) hashArray.clone();
            clear();

            for (int i = 0; i < out.size(); i++) {
                Double sortedValue = out.get(i);
                for (int j = 0; j < tempHashArray.size(); j++) {
                    LinkedHashMap sortLinkedMap = (LinkedHashMap) tempHashArray.get(j);
                    String sortColVal = (String) sortLinkedMap.get(sortCol);
                    Double sortColValue = Double.valueOf(sortColVal);
                    int retval = Double.compare(sortColValue, sortedValue);
                    if (retval == 0) {
                        add(sortLinkedMap);
                        tempHashArray.remove(sortLinkedMap);
                    }
                }
            }
            notifyDataSetChanged();
        } else {
            ArrayList<String> out = new ArrayList<>();
            for (int j = 0; j < hashArray.size(); j++) {
                LinkedHashMap linkedHashMap = (LinkedHashMap) hashArray.get(j);
                String val = (String) linkedHashMap.get(sortCol);
                out.add(val);
            }

            Collections.sort(out, new Comparator<String>() {
                public int compare(String c1, String c2) {
                    return c1.compareTo(c2);
                }
            });
            if (ascending) {
                Collections.reverse(out);
            }

            ArrayList<HashMap<String, String>> tempHashArray = (ArrayList<HashMap<String, String>>) hashArray.clone();
            clear();

            for (int i = 0; i < out.size(); i++) {
                String sortedValue = out.get(i);
                for (int j = 0; j < tempHashArray.size(); j++) {
                    LinkedHashMap sortLinkedMap = (LinkedHashMap) tempHashArray.get(j);
                    String sortColVal = (String) sortLinkedMap.get(sortCol);
                    if (sortColVal.equals(sortedValue)) {
                        add(sortLinkedMap);
                        tempHashArray.remove(sortLinkedMap);
                    }
                }
            }
            notifyDataSetChanged();
        }
        //Cancel last callback if still waiting
        handler.removeCallbacks(sortCallback);
        handler.postDelayed(sortCallback, 1000);
    }

    public interface TableSortListener {
        void onSortListener();
    }

    public interface TableItemClickListener {
        void onClick(int row, View view);

        void onGreekViewClick(int row, View view);
    }

    public class RowData {
        private final String[] data;

        RowData(String col1, String col2, String col3, String col4) {
            data = new String[]{col1, col2, col3, col4};
        }

        RowData(String col1, String col2, String col3, String col4, String col5, String col6) {
            data = new String[]{col1, col2, col3, col4, col5, col6};
        }

        RowData(String col1, String col2, String col3, String col4, String col5, String col6, String col7) {
            data = new String[]{col1, col2, col3, col4, col5, col6, col7};
        }
    }
}
