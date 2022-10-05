package com.acumengroup.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @param <T> Created by Arcadia
 */
public class GreekCommonAdapter<T> extends BaseAdapter {

    private List<T> elements;
    private ArrayList<T> reqElements;
    private ArrayList<T> filterElement;
    private Context mContext;

    private int layoutID = 0;
    private int[] viewIds;
    private int lastSelectedPosition;
    private int selectedRowBGColor = -1;
    private int unSelectedRowBGColor = Color.parseColor("#00000000");
    private int selectedRowBGResource = -1;
    private int unSelectedRowBGResource = -1;
    private int oddRowColor = -1;
    private int evenRowColor = -1;
    ArrayList<String> arrayList;

    private GreekPopulationListener<T> listener;
    private OnRowCreateListener<T> rowListener;
    private String symbol;

    public GreekCommonAdapter(Context context, final List<T> elem) {
        this.mContext = context;
        this.elements = elem;
        arrayList = new ArrayList<>();
        reqElements = new ArrayList<>();
        filterElement = new ArrayList<>();

    }

    public void setLayoutTextViews(int layoutID, int[] viewIDs) {
        this.layoutID = layoutID;
        this.viewIds = viewIDs;
    }

    public int indexOf(String symbol) {
        return arrayList.indexOf(symbol);
    }

    public void clear() {
        elements.clear();
        reqElements.clear();
        filterElement.clear();
        arrayList.clear();
    }

    public void clearReqElement() {
        reqElements.clear();
    }

    public void add(final T row) {
        elements.add(row);
        reqElements.add(row);
        filterElement.add(row);
    }

    public void addSymbol(String symbol) {
        arrayList.add(symbol);
    }

    public void add(int position, final T row) {
        elements.add(position, row);

    }

    public void set(int position, final T row) {

        elements.set(position, row);
    }

    public void setSingleItem(String ltpString) {

        reqElements.set(5, (T) ltpString);

    }

    public void addAll(final List<T> rows) {
        elements.addAll(rows);
    }

    public void set(final List<T> rows) {
        elements = rows;
    }

    public void remove(int position) {
        elements.remove(position);
    }

    public void remove(T item) {
        elements.remove(item);
    }

    public int getCount() {
        return elements.size();
    }

    public int getCountElem() {
        return reqElements.size();
    }

    public T getItem(int position) {
        if (getCount() > 0) return elements.get(position);
        return null;
    }

    public T getItemElem(int position) {
        if (getCountElem() > 0) return reqElements.get(position);
        return null;
    }

    public T getFilterItem(int position) {
        return filterElement.get(position);

    }


    public List<T> getItems() {
        return elements;
    }

    public long getItemId(int position) {
        return position;
    }

    public void setPopulationListener(GreekPopulationListener<T> listener) {
        this.listener = listener;
    }

    public void setOnRowCreateListener(OnRowCreateListener<T> listener) {
        this.rowListener = listener;
    }

    public View getView(int position, View row, ViewGroup parent) {
        RowHolder holder = null;

        if (row == null) {

            row = LayoutInflater.from(mContext).inflate(layoutID, parent, false);

            holder = new RowHolder(row, viewIds);
            if (listener != null) {
                listener.onRowCreate(holder.getViews());
            }
            if (rowListener != null) {
                rowListener.onRowCreate(row, position, elements.get(position), holder.getViews());

            }
            row.setTag(holder);

        } else {
            holder = (RowHolder) row.getTag();
        }

        // Set alternative row color
        if (oddRowColor != -1) {
            if (position % 2 == 1) {
                row.setBackgroundColor(oddRowColor);
            } else {
                row.setBackgroundColor(evenRowColor);
            }
        }

        // Selected row
        if (selectedRowBGColor != -1) {
            if (lastSelectedPosition == position) {
                // Selected Row Color
                row.setBackgroundColor(selectedRowBGColor);
            } else {
                // UnSelected Row Color
                row.setBackgroundColor(unSelectedRowBGColor);
            }
        } else if (selectedRowBGResource != -1) {
            if (lastSelectedPosition == position) {
                // Selected Row Resource
                row.setBackgroundResource(selectedRowBGResource);
            } else {
                // UnSelected Row Resource
                row.setBackgroundResource(unSelectedRowBGResource);
            }
        }

        /**
         * Here we store the position value with key as layoutID to handle
         * alternate colors for list rows.
         *
         * please refer OnItemClickListener (onListClick in watch list) for
         * listView.
         *
         */
        row.setTag(layoutID, position);

        if (listener != null) {
            listener.populateFrom(row, position, elements.get(position), holder.getViews());
        }

        return (row);
    }

    public void setLastSelectedPosition(int position) {
        lastSelectedPosition = position;
    }

    public void setRowBackgroundColor(int selectedRowBGColor, int unSelectedRowBGColor) {
        this.selectedRowBGColor = selectedRowBGColor;
        this.unSelectedRowBGColor = unSelectedRowBGColor;
    }

    public void setRowBackgroundResource(int selectedRowBGResource, int unSelectedRowBGResource) {
        this.selectedRowBGResource = selectedRowBGResource;
        this.unSelectedRowBGResource = unSelectedRowBGResource;
    }

    public void setAlternativeRowColor(int oddRowColor, int evenRowColor) {
        this.oddRowColor = oddRowColor;
        this.evenRowColor = evenRowColor;
    }

    public ArrayList<String> getSymbolList() {
        return arrayList;
    }

    public boolean containsSymbol(String symbol) {
        return arrayList.contains(symbol);
    }


    public interface OnRowCreateListener<T> {
        void onRowCreate(View v, int position, final T row, View[] views);

    }

}
