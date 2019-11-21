package in.co.vyaparienterprise.util;

import java.util.ArrayList;
import java.util.HashMap;

import in.co.vyaparienterprise.model.Currency;

import static java.text.NumberFormat.getCurrencyInstance;

public class CurrencyUtil {

    public static CharSequence doubleToCurrency(double price, Currency currency) {
        currency = new Currency("INR", "₹");
        CharSequence calCurrency;

        String formatted = getCurrencyInstance(DateUtil.getIndiaLocale()).format(price);
        String cleanString = formatted.replaceAll("₹", "");
        cleanString = cleanString.replace(" ", "");

        if (currency == null) {
            return formatted;
        }

        String currencySymbol = currency.getValue();
        calCurrency = currencySymbol + cleanString;

        return calCurrency;
    }


    public static HashMap<String, String> createCurrencyHashMap(ArrayList<Currency> currencies) {
        HashMap<String, String> hashMap = new HashMap<>();
        for (Currency c : currencies) {
            String code = c.getKey();
            String symbol = c.getValue() != null && !c.getValue().isEmpty() ? c.getValue() : c.getKey();
            hashMap.put(code, symbol);
        }
        return hashMap;
    }
    public static CharSequence indianCurrency(Double s) {

        CharSequence calCurrency;

        String formatted = getCurrencyInstance(DateUtil.getIndiaLocale()).format(s);
        String cleanString = formatted.replaceAll("₹", "");
        cleanString = cleanString.replace("Rs.", "");
        String cleanStrings = cleanString.replace(" ", "");

        calCurrency =  cleanStrings;

        return ((String) calCurrency).substring(1);
    }

}
