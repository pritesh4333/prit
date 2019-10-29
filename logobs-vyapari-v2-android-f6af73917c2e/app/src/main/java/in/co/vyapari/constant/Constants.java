package in.co.vyapari.constant;

import android.os.Build;

import in.co.vyapari.BuildConfig;
import in.co.vyapari.model.KeyValue;
import in.co.vyapari.util.Utils;

/**
 * Created by Bekir.Dursun on 3.10.2017.
 */

public class Constants {
    //public static String BASE_URL = "http://172.16.40.159:8080/";
     public static   String VYAPARI_URL;
    public static  String BASE_URL;
    //production
//    public static String VYAPARI_URL = "https://vyapari.co.in";
//    public static String BASE_URL = "https://mobile.vyapari.co.in";
    public static String BASE_SIGNUP_URL = "https://vyapari.co.in/signup";
    public static String BASE_FORGET_PASS_URL = "https://vyapari.co.in/forget-password";
    public static final String APP_VERSION = BuildConfig.VERSION_NAME;
    public static final String APP_VERSION_CODE = String.valueOf(BuildConfig.VERSION_CODE);
    public static final String DEVICE_TYPE = "Android";
    public static final String DEVICE_ID = Utils.getHashAndroidId();
    public static final String OS_VERSION = Build.VERSION.RELEASE;
    public static final String LANG_S = "en-US";//Locale.getDefault().getLanguage();

    public static final String LOGIN_INFO = "loginInfo";
    public static final String LOGIN_RESPONSE_INFO = "loginResponseInfo";
    public static final String DEFAULT_ACCOUNT_INFO = "DefaultAccountinfo";

    public static final int MAX_PAGE_SIZE = 50;

    public static final int NAME_MIN_SIZE = 3;
    public static final int SURNAME_MIN_SIZE = 3;
    public static final int COMPANY_MIN_SIZE = 3;
    public static final int PASSWORD_MIN_LIMIT = 4;

    public static final int SALES_INVOICE = 1;
    public static final int PURCHASE_INVOICE = 2;

    public static final String AMOUNT = "AMOUNT";
    public static final String PERCENT = "PERCENT";

    public static final int NON_CODE = 0;
    public static final int AMOUNT_CODE = 1;
    public static final int PERCENT_CODE = 2;

    public static final String IS_NEW_PRODUCT = "isNewProduct";

    public static final String LAST_MOFIDIED_CODE = "lastModifiedCode";
    public static final String ALL_BULK_DATA = "allBulkData";
    public static final String PRODUCT = "product";
    public static final int REFRESH_CODE = 101;
    public static final String REFRESH = "refresh";
    public static final int TYPE_PHONE = 0;
    public static final int TYPE_EMAIL = 1;
    public static final String EMPTY = "";
    public static final KeyValue EMPTY_KEYVALUE = new KeyValue();

    public static final String EMPLOYEE_LIST = "employeeList";
    public static final String ADDRESS_LIST = "addressList";
    public static final String BANK_LIST = "bankList";

    public static final String EMPLOYEE = "employee";
    public static final String ADDRESS = "address";
    public static final String WAREHOUSE = "warehouse";
    public static final String ALL_ADDRESSES = "allAddresses";
    public static final String BANK = "bank";

    public static final int EMPLOYEE_CODE = 201;
    public static final int ADDRESS_CODE = 202;
    public static final int BANK_CODE = 203;

    public static final String ACTION = "action";
    public static final int ADD = 1;
    public static final int REMOVE = 2;
    public static final int PERMISSION_READ = 123;
    public static final int GALLERY_CODE = 500;
    public static final int SELECT_FIRM_FOR_INVOICE = 300;
    public static final int SELECT_DELIVERY_ADDRESS_FOR_INVOICE = 301;
    public static final int SELECT_PRODUCT_FOR_INVOICE = 302;
    public static final int SELECT_PRODUCT_DETAIL_FOR_INVOICE = 303;
    public static final int EDIT_PRODUCT_FOR_INVOICE = 304;

    public static final String FIRM_ID = "firmId";
    public static final String FIRM = "firm";
    public static final String INVOICE_LINE = "invoiceLine";
    public static final String INVOICE_TYPE = "invoiceType";

    public static final String HSNSAC = "HSNCode";
    public static final String GST = "GSTCode";
    public static final String CESS = "CESSCode";

    public static final int HSNSAC_CODE = 401;
    public static final int GST_CODE = 402;
    public static final int CESS_CODE = 403;

    public static final String INVOICE = "Invoice";
    public static final String FIRMS = "firms";
    public static final String PRODUCTS = "products";
    public static final String SELECT_FIRMS = "selectFirms";
    public static final String SELECT_PRODUCTS = "selectProducts";
    public static final String ADD_TO_INVOICE = "addToInvoice";
    public static final String INVOICE_DETAIL = "invoiceDetail";
    public static final String INVOICE_PDF_DETAIL = "invoicePDFDetail";

    public static final String URL = "url";
    public static final String SECTOR_OTHER = "99";

    public static final int UNAUTHORIZED = 401;
}