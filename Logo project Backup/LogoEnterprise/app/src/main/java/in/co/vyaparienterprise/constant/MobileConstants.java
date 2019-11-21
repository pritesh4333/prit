package in.co.vyaparienterprise.constant;

import java.util.ArrayList;

import in.co.vyaparienterprise.model.Currency;
import in.co.vyaparienterprise.model.KeyValue;

/**
 * Created by Bekir.Dursun on 4.10.2017.
 */

public class MobileConstants {
    public static String portalToken = "";
    public static String accessToken = "";
    public static String UserName = "";

    public static Currency defaultCurrency = new Currency("28", "INR");
    public static KeyValue defaultCountry = new KeyValue("IN", "INDIA");

    public static final String HMAC_KEY = "CsqMbiLlYPytprN3v_bmaGfX1DtjTg8L6IHtsfAH";

    public static String invoiceId = null;
    public static String productId = null;
    public static String firmId = null;

    public static ArrayList<KeyValue> COUNTRIES = null;
    public static ArrayList<Currency> CURRENCIES = null;

    public static ArrayList<KeyValue> PRODUCT_TYPES = null;
    public static ArrayList<KeyValue> UNIT_TYPES = null;

    public static ArrayList<KeyValue> CESS_LIST = null;
    public static ArrayList<KeyValue> GST_LIST = null;

    public static ArrayList<KeyValue> WAREHOUSES = null;
    public static boolean isLoginService = false;
}