package com.acumengroup.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.mobile.R;
import com.acumengroup.mobile.model.ScripModel;
import com.acumengroup.ui.textview.GreekTextView;

import java.util.List;

public class LastSearchSymbolAdapter extends BaseAdapter {


    List<ScripModel> scanDataList;
    Context context;
    String assetType;

    public LastSearchSymbolAdapter(Context activity, List<ScripModel> scanList, String assetType) {
//         super(activity,scanList);
        this.context = activity;
        this.scanDataList = scanList;
        this.assetType = assetType;

    }

    @Override
    public int getCount() {


        if (scanDataList.size() < 10) {
            return scanDataList.size();
        } else {
            return 10;
        }
    }

    @Override
    public ScripModel getItem(int position) {
        return scanDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder {
        GreekTextView lastsymbolsearchTxt, ltp_text, change_text, maxGain, maxLoss, breakeven, margine, prob;
        GreekTextView txtdetail_one, getTxtdetail_two, tradeDetail;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(context).inflate(R.layout.row_lastsearch_symbol, parent, false);
            holder.lastsymbolsearchTxt = convertView.findViewById(R.id.symbolname_text);
            holder.ltp_text = convertView.findViewById(R.id.ltp_text);
            holder.change_text = convertView.findViewById(R.id.change_text);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        if (AccountDetails.getThemeFlag(context).equalsIgnoreCase("white")) {
            holder.lastsymbolsearchTxt.setTextColor(context.getResources().getColor(AccountDetails.textColorDropdown));
            holder.ltp_text.setTextColor(context.getResources().getColor(AccountDetails.textColorDropdown));
            holder.change_text.setTextColor(context.getResources().getColor(AccountDetails.textColorDropdown));

        }

        ScripModel scan = getItem(position);

        /*if (AccountDetails.getShowDescription()) {


            if (scan.getExchange().equalsIgnoreCase("nse")) {


                holder.lastsymbolsearchTxt.setText(scan.getScriptName() + "-" + "N");
//                holder.expiryTxt.setText(DateTimeFormatter.getDateFromTimeStamp(scan.getExpiryDate(), "dd MMM yyyy", "nse"));


            } else if (scan.getExchange().equalsIgnoreCase("bse")) {

                holder.lastsymbolsearchTxt.setText(scan.getScriptName() + "-" + "B");
//                 holder.expiryTxt.setText(DateTimeFormatter.getDateFromTimeStamp(scan.getExpiryDate(), "dd MMM yyyy", "bse"));

            } else if (scan.getAssetType().equalsIgnoreCase("mcx")) {

                if (scan.getOptionType().equalsIgnoreCase("XX")) {

                    holder.lastsymbolsearchTxt.setText(scan.getScriptName() + "" + DateTimeFormatter.getDateFromTimeStamp(scan.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "-M" + " - " + scan.getInstrumentName());
//                    holder.expiryTxt.setText(DateTimeFormatter.getDateFromTimeStamp(scan.getExpiryDate(),"ddMMyyyy",scan.getExch()));
                } else {

                    holder.lastsymbolsearchTxt.setText(scan.getScriptName() + "" + DateTimeFormatter.getDateFromTimeStamp(scan.getExpiryDate(), "yyMMM", "bse").toUpperCase() + scan.getStrickPrice() + "" + scan.getOptionType() + "-M" + " - " + scan.getInstrumentName());
//                    holder.expiryTxt.setText(DateTimeFormatter.getDateFromTimeStamp(scan.getExpiryDate(),"ddMMyyyy",scan.getExch()));
                }


            } else if (scan.getExchange().equalsIgnoreCase("ncdex")) {

                holder.lastsymbolsearchTxt.setText(scan.getScriptName() + "-" + "NDX");
//                holder.expiryTxt.setText(DateTimeFormatter.getDateFromTimeStamp(scan.getExpiryDate(),"ddMMyyyy",scan.getExch()));
            }
        } else {
            if (scan.getExchange().equalsIgnoreCase("nse")) {

                holder.lastsymbolsearchTxt.setText(scan.getScriptName() + "-" + "N");
//                 holder.expiryTxt.setText(DateTimeFormatter.getDateFromTimeStamp(scan.getExpiryDate(), "dd MMM yyyy", "nse"));

            } else if (scan.getExchange().equalsIgnoreCase("bse")) {

                holder.lastsymbolsearchTxt.setText(scan.getScriptName() + "-" + "B");
//                 holder.expiryTxt.setText(DateTimeFormatter.getDateFromTimeStamp(scan.getExpiryDate(), "dd MMM yyyy", "bse"));

            } else if (scan.getExchange().equalsIgnoreCase("mcx")) {
                if (scan.getOptionType().equalsIgnoreCase("XX")) {

                    holder.lastsymbolsearchTxt.setText(scan.getScriptName() + "" + DateTimeFormatter.getDateFromTimeStamp(scan.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "-" + "M" + " - " + scan.getInstrumentName());
//                    holder.expiryTxt.setText(DateTimeFormatter.getDateFromTimeStamp(scan.getExpiryDate(),"ddMMyyyy",scan.getExch()));
                } else {

                    holder.lastsymbolsearchTxt.setText(scan.getSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(scan.getExpiryDate(), "yyMMM", "bse").toUpperCase() + scan.getStrickPrice() + scan.getOptionType() + "-" + "M" + " - " + scan.getInstrumentName());
//                    holder.expiryTxt.setText(DateTimeFormatter.getDateFromTimeStamp(scan.getExpiryDate(),"ddMMyyyy",scan.getExch()));
                }


            } else if (scan.getExchange().equalsIgnoreCase("ncdex")) {

                holder.lastsymbolsearchTxt.setText(scan.getName() + "-" + "NDX");
//                holder.expiryTxt.setText(DateTimeFormatter.getDateFromTimeStamp(scan.getExpiryDate(),"ddMMyyyy",scan.getExch()));
            }
        }*/

      /*  if (AccountDetails.getShowDescription()) {

        if (scan.getExchange().equalsIgnoreCase("nse")) {
            holder.lastsymbolsearchTxt.setText(scan.getSymbol() + "-" + "N");


        } else if (scan.getExchange().equalsIgnoreCase("bse")) {
            holder.lastsymbolsearchTxt.setText(GreekConstants.SYMBOL, quoteList.getSymbol() + "-" + "B");

        } else if (scan.getExchange().equalsIgnoreCase("mcx")) {

            if(scan.getExchange().getOptionType().equalsIgnoreCase("XX"))
            {
                holder.lastsymbolsearchTxt.setText(GreekConstants.SYMBOL, quoteList.getSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(quoteList.getExpiryDate(), "yyMMM", "bse").toUpperCase() + "-M" + " - " + quoteList.getInstrumentName());
            }
            else {
                holder.lastsymbolsearchTxt.setText(GreekConstants.SYMBOL, quoteList.getSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(quoteList.getExpiryDate(), "yyMMM", "bse").toUpperCase() + quoteList.getStrikePrice()+""+quoteList.getOptionType() + "-M" + " - " + quoteList.getInstrumentName());
            }



        } else if (scan.getExchange().equalsIgnoreCase("ncdex")) {
            holder.lastsymbolsearchTxt.setText(GreekConstants.SYMBOL, quoteList.getSymbol() + "-" + "NDX");
        }
    } else {
        if (scan.getExchange().equalsIgnoreCase("nse")) {
            holder.lastsymbolsearchTxt.setText(GreekConstants.SYMBOL, quoteList.getName() + "-" + "N");

        } else if (scan.getExchange().equalsIgnoreCase("bse")) {
            holder.lastsymbolsearchTxt.setText(GreekConstants.SYMBOL, quoteList.getName() + "-" + "B");

        } else if (scan.getExchange().equalsIgnoreCase("mcx")) {
            if(quoteList.getOptionType().equalsIgnoreCase("XX"))
            {
                holder.lastsymbolsearchTxt.setText(GreekConstants.SYMBOL, quoteList.getSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(quoteList.getExpiryDate(), "yyMMM", "bse").toUpperCase() +"-" + "M" + " - " + quoteList.getInstrumentName());
            }
            else {
                holder.lastsymbolsearchTxt.setText(GreekConstants.SYMBOL, quoteList.getSymbol() + "" + DateTimeFormatter.getDateFromTimeStamp(quoteList.getExpiryDate(), "yyMMM", "bse").toUpperCase() + quoteList.getStrikePrice()+quoteList.getOptionType()+"-" + "M" + " - " + quoteList.getInstrumentName());
            }


        } else if (scan.getExchange().equalsIgnoreCase("ncdex")) {
            holder.lastsymbolsearchTxt.setText(GreekConstants.SYMBOL, quoteList.getName() + "-" + "NDX");
        }
    }*/


//        Toast.makeText(context,scan.getScriptname(),Toast.LENGTH_LONG).show();
        /*Equity
--------------------
scriptname - exchange - instrumentName

NCDEX
--------------------
scriptname - NDX - instrumentName

MCX
--------------------
scriptname, expiryDate, strikePrice, optionType - instrumentName

Others(else)
--------------------
name - exchange - instrumentName*/


        holder.lastsymbolsearchTxt.setText(scan.getDescription());
        holder.ltp_text.setText(scan.getLtp());

        if (scan.getChange() != null && scan.getP_change() != null) {
            holder.change_text.setText(String.format("%.2f", Double.parseDouble(String.valueOf(scan.getChange())))
                    + " (" + String.format("%.2f", Double.parseDouble(String.valueOf(scan.getP_change()))) + "%)");

            if (scan.getChange().startsWith("-")) {

                holder.change_text.setTextColor(context.getResources().getColor(R.color.dark_red_negative));

            } else {
                if (AccountDetails.getThemeFlag(context).equalsIgnoreCase("white")) {
                    holder.change_text.setTextColor(context.getResources().getColor(R.color.whitetheambuyColor));
                }else {
                    holder.change_text.setTextColor(context.getResources().getColor(R.color.dark_green_positive));
                }
            }
        }


//         if(scan.getAssetType().equalsIgnoreCase("Equity")){
//
//            holder.lastsymbolsearchTxt.setText(scan.getScriptName()+" - "+scan.getExchange()+" - "+scan.getInstrumentName());
//
//         }else if(scan.getAssetType().equalsIgnoreCase("NCDEX")){
//             holder.lastsymbolsearchTxt.setText(scan.getScriptName()+" - NDX - "+scan.getInstrumentName());
//
//         }else if(scan.getAssetType().equalsIgnoreCase("MCX")){
//             holder.lastsymbolsearchTxt.setText(scan.getDescription()+" "+scan.getInstrumentName()+" - "+scan.getInstrumentName());
//
//         }else{
//
//             holder.lastsymbolsearchTxt.setText(scan.getDescription()+"  - "+scan.getExchange()+" - "+scan.getInstrumentName());
//         }

     /*   if (!scan.getExpiryDate().toString().equalsIgnoreCase("0")) {
            holder.expiryTxt.setText(DateTimeFormatter.getDateFromTimeStamp(scan.getExpiryDate(), "dd MMM yyyy", scan.getExpiryDate().toLowerCase()));
        }
*/

        return convertView;
    }

}
