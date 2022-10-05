package com.acumengroup.mobile.bajajStrategyFinder.sfStrategyDataScreen.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.bajajStrategyFinder.sfStrategyDataScreen.model.ScanDataResponse;
import com.acumengroup.ui.textview.GreekTextView;
import com.acumengroup.columnsliderlib.LinkedAdaptiveTableAdapter;
import com.acumengroup.columnsliderlib.ViewHolderImpl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class StrategyDataTableAdapter extends LinkedAdaptiveTableAdapter<ViewHolderImpl> {

    private final LayoutInflater mLayoutInflater;
    private ArrayList<ScanDataResponse.Response.Data.ScanData> mTableDataSource;
    private final int mColumnWidth;
    private final int mRowHeight;
    private final int mHeaderHeight;
    private final int mHeaderWidth;
    private Context context;

    public StrategyDataTableAdapter(Context context, ArrayList<ScanDataResponse.Response.Data.ScanData> tableDataSource) {
        mLayoutInflater = LayoutInflater.from(context);
        mTableDataSource = new ArrayList<>();
        mTableDataSource.clear();
        mTableDataSource = tableDataSource;
        Resources res = context.getResources();
        mColumnWidth = res.getDimensionPixelSize(R.dimen.column_widths);
        mRowHeight = res.getDimensionPixelSize(R.dimen.row_heights);
        mHeaderHeight = res.getDimensionPixelSize(R.dimen.column_header_heights);
        mHeaderWidth = res.getDimensionPixelSize(R.dimen.row_header_widths);
    }

    public void setAppList(ArrayList<ScanDataResponse.Response.Data.ScanData> categoryModel) {

        mTableDataSource.clear();
        mTableDataSource.addAll(categoryModel);
        notifyDataSetChanged();
    }

    public ScanDataResponse.Response.Data.ScanData getItemScanData(int position) {
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
        return new TestHeaderRowViewHolder(mLayoutInflater.inflate(R.layout.item_header_row, parent, false));
    }

    @NonNull
    @Override
    public ViewHolderImpl onCreateLeftTopHeaderViewHolder(@NonNull ViewGroup parent) {
        return new TestHeaderLeftTopViewHolder(mLayoutInflater.inflate(R.layout.item_header_left_top, parent, false));
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ViewHolderImpl viewHolder, int row, int column) {
        final TestViewHolder vh = (TestViewHolder) viewHolder;
        ScanDataResponse.Response.Data.ScanData itemData = mTableDataSource.get(row); // skip headers

        vh.dataTitle.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        vh.dataSubtitle.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);


        if (column == 1) {

            if (itemData.getDMaxGain() != null && itemData.getDMaxGain().equalsIgnoreCase("0.00")) {
                vh.dataTitle.setText("Unlimited");
            } else {
                String d = itemData.getDMaxGain();
                DecimalFormat df2 = new DecimalFormat("#.##");
                vh.dataTitle.setText(df2.format(Double.parseDouble(itemData.getDMaxGain())));
            }

            if (itemData.getDMaxLoss() != null && itemData.getDMaxLoss().equalsIgnoreCase("0.00")) {
                vh.dataSubtitle.setText("Unlimited");

            } else {
                DecimalFormat df2 = new DecimalFormat("#.##");
                vh.dataSubtitle.setText(df2.format(Double.parseDouble(itemData.getDMaxLoss())));
            }

        } else if (column == 2) {
            if (itemData != null && itemData.getDBEP() != null && itemData.getDInvestment() != null) {
                Log.e("Stratergy getDBEP", "getDBEP ==========> ");
                vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getDBEP())));
                vh.dataSubtitle.setText(String.format("%.2f", Double.parseDouble(itemData.getDInvestment())));
            }

        } else if (column == 3) {
            vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getDNetPremium())));
            vh.dataSubtitle.setText(String.format("%.2f", Double.parseDouble(itemData.getDRiskRatio())));

        } else if (column == 4) {

            vh.dataTitle.setText(String.format("%.2f", Double.parseDouble(itemData.getDStrikeDiff())));
            vh.dataSubtitle.setText(String.format("%.2f", Double.parseDouble(itemData.getIVolatility())));
        }
    }

    @Override
    public void onBindHeaderColumnViewHolder(@NonNull ViewHolderImpl viewHolder, final int column) {
        TestHeaderColumnViewHolder vh = (TestHeaderColumnViewHolder) viewHolder;

        vh.headerColumnTitle.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        vh.headerColumnSubtitle.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);

        if (column == 1) {

            vh.headerColumnTitle.setText("Max Gain");  // skip left top header
            vh.headerColumnSubtitle.setText("Max Loss");  // skip left top header

        } else if (column == 2) {

            vh.headerColumnTitle.setText("BEP");
            vh.headerColumnSubtitle.setText("Investment");

        } else if (column == 3) {

            vh.headerColumnTitle.setText("Net Premium");
            vh.headerColumnSubtitle.setText("R.R.Ratio");


        } else if (column == 4) {

            vh.headerColumnTitle.setText("Spread Diff");
            vh.headerColumnSubtitle.setText("Volatility");
        }


    }

    @Override
    public void onBindHeaderRowViewHolder(@NonNull ViewHolderImpl viewHolder, int row) {
        TestHeaderRowViewHolder vh = (TestHeaderRowViewHolder) viewHolder;
        vh.headerRowTitle.setText(mTableDataSource.get(row).getIStrategyType());
        
        try {
            String[] items = mTableDataSource.get(row).getCTradeDetails().split("\n");
            List<String> itemList = new ArrayList<String>();
            for (String item : items) {
                itemList.add(item);
            }
            SpannableStringBuilder longDescription = new SpannableStringBuilder();

            String str1 = "BUY";
            String str2 = "SELL";
            int index = 0;

            if (itemList.size() > 0) {
                for (int i = 0; i < itemList.size(); i++) {
                    if (itemList.get(i).contains(str1)) {
                        longDescription.append(itemList.get(i));


                        longDescription.setSpan(new ForegroundColorSpan(Color.rgb(51, 204, 51)), index, index + 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        longDescription.append("\n");
                    }
                    index = longDescription.length();

                    if (itemList.get(i).contains(str2)) {
                        longDescription.append(itemList.get(i));
                        if (index > 0) {
                            index = index - 1;

                        }
                        longDescription.setSpan(new ForegroundColorSpan(Color.rgb(255, 51, 0)), index, index + 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        longDescription.append("\n");
                    }
                    index = longDescription.length();

                }
            }

            vh.headerRowSubtitle.setText(longDescription);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBindLeftTopHeaderViewHolder(@NonNull ViewHolderImpl viewHolder) {
        TestHeaderLeftTopViewHolder vh = (TestHeaderLeftTopViewHolder) viewHolder;
        vh.leftTopTitle.setText("TYPE");
        vh.leftTopSubtitle.setText("Trade Detail");
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
            vLine = itemView.findViewById(R.id.vLine);
            item_headerParent = itemView.findViewById(R.id.item_headerParent);
            vLine = itemView.findViewById(R.id.vLine);
            vline2 = itemView.findViewById(R.id.viewLine2);

            if (AccountDetails.getThemeFlag(itemView.getContext()).equalsIgnoreCase("white")) {
                vline2.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.black));
                vLine.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.black));
                item_headerParent.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.selectColor));

            }

            headerColumnTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.e("Strategy", "OnClick=====Title======>>");
                }
            });

            headerColumnSubtitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.e("Strategy", "OnClick=====SubTitle======>>");

                }
            });


        }
    }

    private static class TestHeaderRowViewHolder extends ViewHolderImpl {
        GreekTextView headerRowTitle, headerRowSubtitle;
        RelativeLayout item_builderparent;
        View viewLine2, vline;

        TestHeaderRowViewHolder(@NonNull View itemView) {
            super(itemView);
            headerRowTitle = itemView.findViewById(R.id.title);
            headerRowSubtitle = itemView.findViewById(R.id.subTitle);
            item_builderparent = itemView.findViewById(R.id.item_builderparent);
            viewLine2 = itemView.findViewById(R.id.viewLine2);
            vline = itemView.findViewById(R.id.vline);

            if (AccountDetails.getThemeFlag(itemView.getContext()).equalsIgnoreCase("white")) {
                viewLine2.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.black));
                vline.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.black));
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

            }
        }
    }
}
