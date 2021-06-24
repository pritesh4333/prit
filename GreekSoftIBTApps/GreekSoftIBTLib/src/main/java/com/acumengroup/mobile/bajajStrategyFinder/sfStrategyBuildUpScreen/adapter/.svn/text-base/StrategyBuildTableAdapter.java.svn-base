package com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyBuildUpScreen.model.SrategyBuildData;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.columnsliderlib.LinkedAdaptiveTableAdapter;
import com.acumengroup.columnsliderlib.ViewHolderImpl;

import java.util.ArrayList;


public class StrategyBuildTableAdapter extends LinkedAdaptiveTableAdapter<ViewHolderImpl> {

    private static final int[] COLORS = new int[]{
            0xffe62a10, 0xffe91e63, 0xff9c27b0, 0xff673ab7, 0xff3f51b5,
            0xff5677fc, 0xff03a9f4, 0xff00bcd4, 0xff009688, 0xff259b24,
            0xff8bc34a, 0xffcddc39, 0xffffeb3b, 0xffffc107, 0xffff9800, 0xffff5722};

    private final LayoutInflater mLayoutInflater;
    private final ArrayList<SrategyBuildData.Response.Data.BuildData> mTableDataSource;
    private final int mColumnWidth;
    private final int mRowHeight;
    private final int mHeaderHeight;
    private final int mHeaderWidth;

    public StrategyBuildTableAdapter(Context context, ArrayList<SrategyBuildData.Response.Data.BuildData> tableDataSource) {
        mLayoutInflater = LayoutInflater.from(context);
        mTableDataSource = tableDataSource;
        Resources res = context.getResources();
        mColumnWidth = res.getDimensionPixelSize(R.dimen.column_width2);
        mRowHeight = res.getDimensionPixelSize(R.dimen.row_height2);
        mHeaderHeight = res.getDimensionPixelSize(R.dimen.column_header_height2);
        mHeaderWidth = res.getDimensionPixelSize(R.dimen.row_header_width2);
    }

    public void setAppList(ArrayList<SrategyBuildData.Response.Data.BuildData> categoryModel) {

        mTableDataSource.clear();
        mTableDataSource.addAll(categoryModel);
        notifyDataSetChanged();
    }

    public SrategyBuildData.Response.Data.BuildData getItemScanData(int position) {
        return mTableDataSource.get(position);
    }

    @Override
    public int getRowCount() {
        return mTableDataSource.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @NonNull
    @Override
    public ViewHolderImpl onCreateItemViewHolder(@NonNull ViewGroup parent) {
        return new TestViewHolder(mLayoutInflater.inflate(R.layout.item_card, parent, false));
    }

    @NonNull
    @Override
    public ViewHolderImpl onCreateColumnHeaderViewHolder(@NonNull ViewGroup parent) {
        return new TestHeaderColumnViewHolder(mLayoutInflater.inflate(R.layout.item_header_column, parent, false));
    }

    @NonNull
    @Override
    public ViewHolderImpl onCreateRowHeaderViewHolder(@NonNull ViewGroup parent) {
        return new TestHeaderRowViewHolder(mLayoutInflater.inflate(R.layout.item_builder_header_row, parent, false));
    }

    @NonNull
    @Override
    public ViewHolderImpl onCreateLeftTopHeaderViewHolder(@NonNull ViewGroup parent) {
        return new TestHeaderLeftTopViewHolder(mLayoutInflater.inflate(R.layout.item_header_left_top, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderImpl viewHolder, int row, int column) {
        final TestViewHolder vh = (TestViewHolder) viewHolder;
        SrategyBuildData.Response.Data.BuildData itemData = mTableDataSource.get(row); // skip headers

        vh.dataTitle.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        vh.dataSubtitle.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);

        if (column == 1) {
            vh.dataTitle.setText(itemData.getCOptType());  // skip left top header
            vh.dataSubtitle.setText(itemData.getLNetQty());  // skip left top heade)r

        } else if (column == 2) {

            if (Integer.parseInt(itemData.getLNetQty()) != 0 && itemData.getLNetQty() != null
                    && itemData.getDTradeAmt() != null && Integer.parseInt(itemData.getLNetQty()) != 0) {

                double price = Double.parseDouble(itemData.getDTradeAmt()) / Integer.parseInt(itemData.getLNetQty());
                vh.dataTitle.setText(String.format("%.2f", price));

            } else {
                vh.dataTitle.setText("0.00");
            }

//            vh.dataTitle.setText(itemData.getDTradeAmt());
            vh.dataSubtitle.setText(String.format("%.2f", Double.parseDouble(itemData.getDLtp())));

        } else if (column == 3) {
            vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getDMotm())));
            vh.dataSubtitle.setText(String.format("%.2f", Double.parseDouble(itemData.getDDelta())));
        } else if (column == 4) {
            vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getDTheroticalPrice())));
            vh.dataSubtitle.setText(String.format("%.2f", Double.parseDouble(itemData.getDLastIV())));
        }

    }

    @Override
    public void onBindHeaderColumnViewHolder(@NonNull ViewHolderImpl viewHolder, final int column) {
        int color = COLORS[column % COLORS.length];
        TestHeaderColumnViewHolder vh = (TestHeaderColumnViewHolder) viewHolder;

        vh.headerColumnTitle.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        vh.headerColumnSubtitle.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);

        if (column == 1) {
            vh.headerColumnTitle.setText("Option ");  // skip left top header
            vh.headerColumnSubtitle.setText("Qty");  // skip left top header

        } else if (column == 2) {

            vh.headerColumnTitle.setText("Price");
            vh.headerColumnSubtitle.setText("LTP");
        } else if (column == 3) {
            vh.headerColumnTitle.setText("MtoM");
            vh.headerColumnSubtitle.setText("Delta");
        } else if (column == 4) {
            vh.headerColumnTitle.setText("Ther Price");
            vh.headerColumnSubtitle.setText("Strike IV");
        }


    }

    @Override
    public void onBindHeaderRowViewHolder(@NonNull ViewHolderImpl viewHolder, int row) {

        TestHeaderRowViewHolder vh = (TestHeaderRowViewHolder) viewHolder;
        vh.headerRowTitle.setText(mTableDataSource.get(row).getCExpiry());
        vh.headerRowSubtitle.setText(mTableDataSource.get(row).getDStrike());
    }

    @Override
    public void onBindLeftTopHeaderViewHolder(@NonNull ViewHolderImpl viewHolder) {
        TestHeaderLeftTopViewHolder vh = (TestHeaderLeftTopViewHolder) viewHolder;
        vh.leftTopTitle.setText("EXP");
        vh.leftTopSubtitle.setText("Strike Price");
    }

    @Override
    public int getColumnWidth(int column) {
        return mColumnWidth;
    }

    @Override
    public int getHeaderColumnHeight() {
        return mHeaderHeight;
    }

    @Override
    public int getRowHeight(int row) {
        return mRowHeight;
    }

    @Override
    public int getHeaderRowWidth() {
        return mHeaderWidth;
    }

    //------------------------------------- view holders ------------------------------------------

    private static class TestViewHolder extends ViewHolderImpl {
        GreekTextView dataSubtitle, dataTitle;
        RelativeLayout itemParent;
        View view, vline2;

        private TestViewHolder(@NonNull View itemView) {
            super(itemView);
            dataTitle = itemView.findViewById(R.id.dataTitle);
            dataSubtitle = itemView.findViewById(R.id.dataSubtitle);
            itemParent = itemView.findViewById(R.id.itemParent);
            view = itemView.findViewById(R.id.vLine);
            vline2 = itemView.findViewById(R.id.viewLine2);

            if (AccountDetails.getThemeFlag(itemView.getContext()).equalsIgnoreCase("white")) {
                vline2.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.black));
                view.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.black));
                itemParent.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.white));
                dataTitle.setTextColor(itemView.getContext().getResources().getColor(AccountDetails.textColorDropdown));
                dataSubtitle.setTextColor(itemView.getContext().getResources().getColor(AccountDetails.textColorDropdown));
            }
        }

    }

    private static class TestHeaderColumnViewHolder extends ViewHolderImpl {
        GreekTextView headerColumnSubtitle, headerColumnTitle;
        View vLine, vline2;
        RelativeLayout item_headerParent;

        private TestHeaderColumnViewHolder(@NonNull View itemView) {
            super(itemView);
            headerColumnTitle = itemView.findViewById(R.id.dataTitle);
            headerColumnSubtitle = itemView.findViewById(R.id.dataSubtitle);
            item_headerParent = itemView.findViewById(R.id.item_headerParent);
            vLine = itemView.findViewById(R.id.vLine);
            vline2 = itemView.findViewById(R.id.viewLine2);

            if (AccountDetails.getThemeFlag(itemView.getContext()).equalsIgnoreCase("white")) {
                vline2.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.black));
                vLine.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.black));
                item_headerParent.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.selectColor));
                //headerColumnSubtitle.setTextColor(itemView.getContext().getResources().getColor(AccountDetails.textColorDropdown));
                //headerColumnTitle.setTextColor(itemView.getContext().getResources().getColor(AccountDetails.textColorDropdown));
            }


        }
    }

    private static class TestHeaderRowViewHolder extends ViewHolderImpl {
        GreekTextView headerRowTitle, headerRowSubtitle;
        RelativeLayout item_builderparent;
        View viewLine1,vLine;

        TestHeaderRowViewHolder(@NonNull View itemView) {
            super(itemView);
            headerRowTitle = itemView.findViewById(R.id.title);
            headerRowSubtitle = itemView.findViewById(R.id.subTitle);
            item_builderparent = itemView.findViewById(R.id.item_builderparent);
            viewLine1 = itemView.findViewById(R.id.viewLine1);
            vLine = itemView.findViewById(R.id.vLine);

            if (AccountDetails.getThemeFlag(itemView.getContext()).equalsIgnoreCase("white")) {
                viewLine1.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.black));
                vLine.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.black));
                item_builderparent.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.white));
                headerRowTitle.setTextColor(itemView.getContext().getResources().getColor(AccountDetails.textColorDropdown));
                headerRowSubtitle.setTextColor(itemView.getContext().getResources().getColor(AccountDetails.textColorDropdown));
            }
        }
    }

    private static class TestHeaderLeftTopViewHolder extends ViewHolderImpl {
        GreekTextView leftTopSubtitle, leftTopTitle;
        RelativeLayout header_left_parent;
        View vLine, viewLine2;

        private TestHeaderLeftTopViewHolder(@NonNull View itemView) {
            super(itemView);
            leftTopTitle = itemView.findViewById(R.id.title);
            leftTopSubtitle = itemView.findViewById(R.id.subTitle);
            header_left_parent = itemView.findViewById(R.id.header_left_parent);
            viewLine2 = itemView.findViewById(R.id.viewLine2);
            vLine = itemView.findViewById(R.id.vLine);

            if (AccountDetails.getThemeFlag(itemView.getContext()).equalsIgnoreCase("white")) {
                viewLine2.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.black));
                vLine.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.black));
                header_left_parent.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.selectColor));
                //leftTopTitle.setTextColor(itemView.getContext().getResources().getColor(AccountDetails.textColorDropdown));
                //leftTopSubtitle.setTextColor(itemView.getContext().getResources().getColor(AccountDetails.textColorDropdown));
            }
        }
    }
}
