package in.co.vyapari.database;

/**
 * Created by bekirdursun on 7.02.2018.
 */

class DbSQL {
    static final String PRODUCT_LIST_LIKE = "SELECT * FROM Product WHERE searchTags LIKE '%/%s%' ORDER BY searchTags DESC LIMIT %s OFFSET %s";
    static final String PRODUCT_LIST = "SELECT * FROM Product ORDER BY searchTags DESC LIMIT %s OFFSET %s";
    static final String GET_PRODUCT = "SELECT * FROM Product WHERE id=\"%s\"";

    static final String FIRM_LIST_LIKE = "SELECT * FROM Firm WHERE searchTags LIKE '%/%s%' ORDER BY searchTags DESC LIMIT %s OFFSET %s";
    static final String FIRM_LIST = "SELECT * FROM Firm ORDER BY searchTags DESC LIMIT %s OFFSET %s";
    static final String GET_FIRM = "SELECT * FROM Firm WHERE id=\"%s\"";

    static final String GET_KV_LIST_LIKE = "SELECT * FROM %s WHERE Value LIKE '%/%s%'";
    static final String GET_KV_LIST_PARENT = "SELECT * FROM %s WHERE searchTags = \"%s\" OR searchTags IS NULL";
    static final String GET_KV_LIST = "SELECT * FROM %s";

    static final String INVOICE_LIST_LIKE = "SELECT * FROM Invoice WHERE invoiceType = %s AND searchTags LIKE '%/%s%' ORDER BY invoiceDate DESC LIMIT %s OFFSET %s";
    static final String INVOICE_LIST = "SELECT * FROM Invoice WHERE invoiceType = %s ORDER BY invoiceDate DESC LIMIT %s OFFSET %s";
    static final String GET_INVOICE = "SELECT * FROM Invoice WHERE id=\"%s\"";
}