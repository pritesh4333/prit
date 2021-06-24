package com.acumengroup.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arcadia
 */
@Deprecated
public class CommonAdapter extends BaseAdapter {

    private List<CommonRowData> elements, content;
    private Context mContext;

    private int layoutID = 0;
    private int[] viewIds;
    private int lastSelectedPosition;
    private int selectedRowColor = -1;
    private int unSelectedRowColor = Color.parseColor("#00000000");
    private int selectedResourceRowColor = -1;
    private ValueFilter valueFilter;
    private int oddColor = -1;
    private int evenColor = -1;

    private PopulationListener listener;

    public CommonAdapter(Context context, List<CommonRowData> elem) {
        this.mContext = context;
        this.elements = elem;
    }

    public void setLayoutTextViews(int layoutID, int[] viewIDs) {
        this.layoutID = layoutID;
        this.viewIds = viewIDs;
    }

    public void clear() {
        elements.clear();
    }

    public void add(CommonRowData row) {
        elements.add(row);
    }

    public void add(int position, CommonRowData row) {
        elements.add(position, row);
    }

    public void addAll(List<CommonRowData> rows) {
        elements.addAll(rows);
    }

    public void remove(int position) {
        elements.remove(position);
    }

    public int getCount() {
        return elements.size();
    }

    public Object getItem(int position) {
        if (getCount() > 0) return elements.get(position);
        return null;
    }

    public void setItem(int position, CommonRowData commonRowData) {
        elements.set(position, commonRowData);
    }


    public long getItemId(int position) {
        return position;
    }

    public void setPopulationListener(PopulationListener listener) {
        this.listener = listener;
    }

    public View getView(int position, View row, ViewGroup parent) {
        RowHolder holder = null;

        if (row == null) {
            row = LayoutInflater.from(mContext).inflate(layoutID, parent, false);
            holder = new RowHolder(row, viewIds);
            listener.onRowCreate(holder.getViews());
            row.setTag(holder);
        } else {
            holder = (RowHolder) row.getTag();
        }

        if (oddColor != -1) {
            if (position % 2 == 1) {
                row.setBackgroundColor(oddColor);
            } else {
                row.setBackgroundColor(evenColor);
            }
        }

        if (selectedRowColor != -1) {
            if (lastSelectedPosition == position) {
                // Selected Row Color
                row.setBackgroundColor(selectedRowColor);
            } else {
                // UnSelected Row Color
                row.setBackgroundColor(unSelectedRowColor);
            }
        }

        if (selectedResourceRowColor != -1) {
            if (lastSelectedPosition == position) {
                // Selected Row Color
                row.setBackgroundResource(selectedResourceRowColor);
            } else {
                // UnSelected Row Color
                row.setBackgroundColor(unSelectedRowColor);
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
        try {
            row.setTag(layoutID, position);

            listener.populateFrom(row, position, elements.get(position), holder.getViews());


        } catch (Exception e) {
            Log.d("COMMONADAPTER", e.getMessage());
        }
        return (row);
    }

    public void setLastSelectedPosition(int position) {
        lastSelectedPosition = position;
    }

    public void setRowColor(int selectedRowColor) {
        this.selectedRowColor = selectedRowColor;
    }

    public void setUnSelectedRowColor(int unSelectedRowColor) {
        this.unSelectedRowColor = unSelectedRowColor;
    }

    public void setResourceRowColor(int selectedResourceRowColor) {
        this.selectedResourceRowColor = selectedResourceRowColor;
    }

    public void setAlternativeRowColor(int oddColor, int evenColor) {
        this.oddColor = oddColor;
        this.evenColor = evenColor;
    }

    public Filter getFilter(boolean contains) {
        if (valueFilter == null) {
            this.content = elements;
            valueFilter = new ValueFilter(contains);
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {

        private boolean contains;

        public ValueFilter(boolean contains) {
            this.contains = contains;
        }

        // Invoked in a worker thread to filter the data according to the
        // constraint.
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                List<CommonRowData> filterList = new ArrayList<>();
                for (int i = 0; i < content.size(); i++) {
                    if (contains) {
                        // Contains
                        if (content.get(i).getHead1().toLowerCase().contains(constraint.toString().toLowerCase())) {
                            filterList.add(content.get(i));
                        }
                    } else {
                        // Starts With
                        if (content.get(i).getHead1().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                            filterList.add(content.get(i));
                        }
                    }
                }

                results.count = filterList.size();
                results.values = filterList;

            } else {
                results.count = content.size();
                results.values = content;
            }

            return results;
        }

        // Invoked in the UI thread to publish the filtering results in the user
        // interface.
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            elements = (List<CommonRowData>) results.values;
            notifyDataSetChanged();

        }

    }

}
