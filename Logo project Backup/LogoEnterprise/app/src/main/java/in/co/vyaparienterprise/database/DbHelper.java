package in.co.vyaparienterprise.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import in.co.vyaparienterprise.database.invoice.DbInvoiceObject;
import in.co.vyaparienterprise.database.invoice.DbInvoiceObjectCV;
import in.co.vyaparienterprise.model.Firm;
import in.co.vyaparienterprise.model.Invoice;
import in.co.vyaparienterprise.model.KeyValue;
import in.co.vyaparienterprise.model.Product;
import in.co.vyaparienterprise.model.request.filter.Filter;
import in.co.vyaparienterprise.model.response.dto.FirmDTO;
import in.co.vyaparienterprise.model.response.dto.InvoiceDTO;
import in.co.vyaparienterprise.model.response.dto.ProductDTO;
import in.co.vyaparienterprise.model.response.summary.FirmSum;
import in.co.vyaparienterprise.model.response.summary.InvoiceSum;
import in.co.vyaparienterprise.model.response.summary.ProductSum;

/**
 * Created by bekirdursun on 5.02.2018.
 */

public class DbHelper {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DbHelper instance;

    private DbHelper(Context context) {
        this.openHelper = new DbOpenHelper(context);
    }

    public static DbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DbHelper(context);
        }
        return instance;
    }

    private void open() {
        this.database = openHelper.getWritableDatabase();
    }

    private void close() {
        if (database != null) {
            this.database.close();
        }
    }

    //COMMON
    ArrayList<KeyValue> getKVList(String columnName, String parentId, String searchText) {
        open();
        String sql;
        if (searchText != null) {
            sql = String.format(DbSQL.GET_KV_LIST_LIKE, columnName, searchText);
        } else {
            if (parentId != null) {
                sql = String.format(DbSQL.GET_KV_LIST_PARENT, columnName, parentId);
            } else {
                sql = String.format(DbSQL.GET_KV_LIST, columnName);
            }
        }

        ArrayList<KeyValue> keyValues = new ArrayList<>();
        Cursor c = null;

        try {
            c = database.rawQuery(sql, null);
            c.moveToFirst();
            Gson gson = new GsonBuilder().serializeNulls().create();

            while (!c.isAfterLast()) {
                String data = c.getString(c.getColumnIndex("data"));
                if (data != null) {
                    KeyValue keyValue = gson.fromJson(data, KeyValue.class);
                    keyValues.add(keyValue);
                }
                c.moveToNext();
            }
        } catch (Exception ignored) {
        }

        if (c != null) {
            c.close();
        }
        close();
        return keyValues;
    }
    //COMMON

    //PRODUCT
    boolean addProduct(DbObject<Product> dbObject) {
        open();
        DbObjectCV<Product> dbObjectCV = new DbObjectCV<>(dbObject);
        ContentValues values = new ContentValues();
        values.put(DbConstants.Detail.ID, dbObjectCV.getId());
        values.put(DbConstants.Detail.DATA, dbObjectCV.getData());
        values.put(DbConstants.Detail.SEARCH_TAGS, dbObjectCV.getSearchTags());
        values.put(DbConstants.Detail.CREATED_DATE, dbObjectCV.getCreatedDate());
        values.put(DbConstants.Detail.MODIFIED_DATE, dbObjectCV.getModifiedDate());
        values.put(DbConstants.Detail.SYNCHRONIZED_DATE, dbObjectCV.getSynchronizedDate());
        values.put(DbConstants.Detail.IS_SYNCHRONIZED, dbObjectCV.getIsSynchronized());
        values.put(DbConstants.Detail.IS_DELETED, dbObjectCV.getIsDeleted());

        boolean status;
        status = database.insert(DbConstants.PRODUCT, null, values) > 0;
        close();
        return status;
    }


    boolean updateProduct(DbObject<Product> dbObject) {
        open();
        DbObjectCV<Product> dbObjectCV = new DbObjectCV<>(dbObject);
        ContentValues values = new ContentValues();
        values.put(DbConstants.Detail.ID, dbObjectCV.getId());
        values.put(DbConstants.Detail.DATA, dbObjectCV.getData());
        values.put(DbConstants.Detail.SEARCH_TAGS, dbObjectCV.getSearchTags());
        //values.put(DbConstants.Detail.CREATED_DATE, dbObjectCV.getCreatedDate());
        values.put(DbConstants.Detail.MODIFIED_DATE, dbObjectCV.getModifiedDate());
        values.put(DbConstants.Detail.SYNCHRONIZED_DATE, dbObjectCV.getSynchronizedDate());
        values.put(DbConstants.Detail.IS_SYNCHRONIZED, dbObjectCV.getIsSynchronized());
        values.put(DbConstants.Detail.IS_DELETED, dbObjectCV.getIsDeleted());

        boolean status;
        status = database.update(DbConstants.PRODUCT, values, DbConstants.Detail.ID + " = ?", new String[]{String.valueOf(dbObjectCV.getId())}) > 0;
        close();
        return status;
    }


    boolean deleteProduct(String id) {
        open();
        boolean status;
        status = database.delete(DbConstants.PRODUCT, DbConstants.Detail.ID + " = ?", new String[]{id}) > 0;
        close();
        return status;
    }


    ProductDTO getProduct(String id) {
        open();
        String sql = String.format(DbSQL.GET_PRODUCT, id);

        ProductDTO productDTO = null;
        Product product = null;
        Cursor c = null;

        try {
            c = database.rawQuery(sql, null);
            c.moveToFirst();
            Gson gson = new GsonBuilder().serializeNulls().create();

            while (!c.isAfterLast()) {
                String data = c.getString(c.getColumnIndex("data"));
                if (data != null) {
                    product = gson.fromJson(data, Product.class);
                    product.setId(id);
                    break;
                }
                c.moveToNext();
            }
        } catch (Exception ignored) {
        }

        if (c != null) {
            c.close();
        }
        close();

        if (product != null) {
            productDTO = new ProductDTO(product);
        }

        return productDTO;
    }


    ArrayList<ProductSum> getProductList(Filter filter) {
        open();
        String sql;
        if (filter.getSearchText() != null) {
            sql = String.format(DbSQL.PRODUCT_LIST_LIKE, filter.getSearchText(), filter.getPaging().getPageSize(), (filter.getPaging().getPageSize() * (filter.getPaging().getCurrentPage() - 1)));
        } else {
            sql = String.format(DbSQL.PRODUCT_LIST, filter.getPaging().getPageSize(), (filter.getPaging().getPageSize() * (filter.getPaging().getCurrentPage() - 1)));
        }

        ArrayList<ProductSum> productSums = new ArrayList<>();
        Cursor c = null;

        try {
            c = database.rawQuery(sql, null);
            c.moveToFirst();
            Gson gson = new GsonBuilder().serializeNulls().create();

            while (!c.isAfterLast()) {
                String id = c.getString(c.getColumnIndex("id"));
                String data = c.getString(c.getColumnIndex("data"));
                if (data != null) {
                    Product product = gson.fromJson(data, Product.class);
                    product.setId(id);
                    ProductSum productSum = new ProductSum(product);
                    productSums.add(productSum);
                }
                c.moveToNext();
            }
        } catch (Exception ignored) {
        }

        if (c != null) {
            c.close();
        }
        close();

        return productSums;
    }
    //PRODUCT


    //FIRM
    boolean addFirm(DbObject<Firm> dbObject) {
        open();
        DbObjectCV<Firm> dbObjectCV = new DbObjectCV<>(dbObject);
        ContentValues values = new ContentValues();
        values.put(DbConstants.Detail.ID, dbObjectCV.getId());
        values.put(DbConstants.Detail.DATA, dbObjectCV.getData());
        values.put(DbConstants.Detail.SEARCH_TAGS, dbObjectCV.getSearchTags());
        values.put(DbConstants.Detail.CREATED_DATE, dbObjectCV.getCreatedDate());
        values.put(DbConstants.Detail.MODIFIED_DATE, dbObjectCV.getModifiedDate());
        values.put(DbConstants.Detail.SYNCHRONIZED_DATE, dbObjectCV.getSynchronizedDate());
        values.put(DbConstants.Detail.IS_SYNCHRONIZED, dbObjectCV.getIsSynchronized());
        values.put(DbConstants.Detail.IS_DELETED, dbObjectCV.getIsDeleted());

        boolean status;
        status = database.insert(DbConstants.FIRM, null, values) > 0;
        close();
        return status;
    }


    boolean updateFirm(DbObject<Firm> dbObject) {
        open();
        DbObjectCV<Firm> dbObjectCV = new DbObjectCV<>(dbObject);
        ContentValues values = new ContentValues();
        values.put(DbConstants.Detail.ID, dbObjectCV.getId());
        values.put(DbConstants.Detail.DATA, dbObjectCV.getData());
        values.put(DbConstants.Detail.SEARCH_TAGS, dbObjectCV.getSearchTags());
        //values.put(DbConstants.Detail.CREATED_DATE, dbObjectCV.getCreatedDate());
        values.put(DbConstants.Detail.MODIFIED_DATE, dbObjectCV.getModifiedDate());
        values.put(DbConstants.Detail.SYNCHRONIZED_DATE, dbObjectCV.getSynchronizedDate());
        values.put(DbConstants.Detail.IS_SYNCHRONIZED, dbObjectCV.getIsSynchronized());
        values.put(DbConstants.Detail.IS_DELETED, dbObjectCV.getIsDeleted());

        boolean status;
        status = database.update(DbConstants.FIRM, values, DbConstants.Detail.ID + " = ?", new String[]{String.valueOf(dbObjectCV.getId())}) > 0;
        close();
        return status;
    }


    boolean deleteFirm(String id) {
        open();
        boolean status;
        status = database.delete(DbConstants.FIRM, DbConstants.Detail.ID + " = ?", new String[]{id}) > 0;
        close();
        return status;
    }


    FirmDTO getFirm(String id) {
        open();
        String sql = String.format(DbSQL.GET_FIRM, id);

        FirmDTO firmDTO = null;
        Firm firm = null;
        Cursor c = null;

        try {
            c = database.rawQuery(sql, null);
            c.moveToFirst();
            Gson gson = new GsonBuilder().serializeNulls().create();

            while (!c.isAfterLast()) {
                String data = c.getString(c.getColumnIndex("data"));
                if (data != null) {
                    firm = gson.fromJson(data, Firm.class);
                    firm.setId(id);
                    break;
                }
                c.moveToNext();
            }
        } catch (Exception ignored) {
        }

        if (c != null) {
            c.close();
        }
        close();

        if (firm != null) {
            firmDTO = new FirmDTO(firm);
        }

        return firmDTO;
    }


    ArrayList<FirmSum> getFirmList(Filter filter) {
        open();
        String sql;
        if (filter.getSearchText() != null) {
            sql = String.format(DbSQL.FIRM_LIST_LIKE, filter.getSearchText(), filter.getPaging().getPageSize(), (filter.getPaging().getPageSize() * (filter.getPaging().getCurrentPage() - 1)));
        } else {
            sql = String.format(DbSQL.FIRM_LIST, filter.getPaging().getPageSize(), (filter.getPaging().getPageSize() * (filter.getPaging().getCurrentPage() - 1)));
        }

        ArrayList<FirmSum> firmSums = new ArrayList<>();
        Cursor c = null;

        try {
            c = database.rawQuery(sql, null);
            c.moveToFirst();
            Gson gson = new GsonBuilder().serializeNulls().create();

            while (!c.isAfterLast()) {
                String id = c.getString(c.getColumnIndex("id"));
                String data = c.getString(c.getColumnIndex("data"));
                if (data != null) {
                    Firm firm = gson.fromJson(data, Firm.class);
                    firm.setId(id);
                    FirmSum firmSum = new FirmSum(firm);
                    firmSums.add(firmSum);
                }
                c.moveToNext();
            }
        } catch (Exception ignored) {
        }

        if (c != null) {
            c.close();
        }
        close();

        return firmSums;
    }
    //FIRM


    //INVOICE
    boolean addInvoice(DbInvoiceObject<Invoice> dbInvoiceObject) {
        open();
        DbInvoiceObjectCV<Invoice> dbInvoiceObjectCV = new DbInvoiceObjectCV<>(dbInvoiceObject);
        ContentValues values = new ContentValues();
        values.put(DbConstants.Detail.ID, dbInvoiceObjectCV.getId());
        values.put(DbConstants.Detail.DATA, dbInvoiceObjectCV.getData());
        values.put(DbConstants.Detail.SEARCH_TAGS, dbInvoiceObjectCV.getSearchTags());
        values.put(DbConstants.Detail.INVOICE_TYPE, dbInvoiceObjectCV.getInvoiceType());
        values.put(DbConstants.Detail.CREATED_DATE, dbInvoiceObjectCV.getCreatedDate());
        //values.put(DbConstants.Detail.MODIFIED_DATE, dbObjectCV.getModifiedDate());
        values.put(DbConstants.Detail.SYNCHRONIZED_DATE, dbInvoiceObjectCV.getSynchronizedDate());
        values.put(DbConstants.Detail.IS_SYNCHRONIZED, dbInvoiceObjectCV.getIsSynchronized());
        values.put(DbConstants.Detail.IS_DELETED, dbInvoiceObjectCV.getIsDeleted());

        boolean status;
        status = database.insert(DbConstants.INVOICE, null, values) > 0;
        close();
        return status;
    }


    boolean updateInvoice(DbInvoiceObject<Invoice> dbInvoiceObject) {
        open();
        DbInvoiceObjectCV<Invoice> dbInvoiceObjectCV = new DbInvoiceObjectCV<>(dbInvoiceObject);
        ContentValues values = new ContentValues();
        values.put(DbConstants.Detail.ID, dbInvoiceObjectCV.getId());
        values.put(DbConstants.Detail.DATA, dbInvoiceObjectCV.getData());
        values.put(DbConstants.Detail.SEARCH_TAGS, dbInvoiceObjectCV.getSearchTags());
        values.put(DbConstants.Detail.INVOICE_TYPE, dbInvoiceObjectCV.getInvoiceType());
        //values.put(DbConstants.Detail.CREATED_DATE, dbObjectCV.getCreatedDate());
        values.put(DbConstants.Detail.MODIFIED_DATE, dbInvoiceObjectCV.getModifiedDate());
        values.put(DbConstants.Detail.SYNCHRONIZED_DATE, dbInvoiceObjectCV.getSynchronizedDate());
        values.put(DbConstants.Detail.IS_SYNCHRONIZED, dbInvoiceObjectCV.getIsSynchronized());
        values.put(DbConstants.Detail.IS_DELETED, dbInvoiceObjectCV.getIsDeleted());

        boolean status;
        status = database.update(DbConstants.INVOICE, values, DbConstants.Detail.ID + " = ?", new String[]{String.valueOf(dbInvoiceObjectCV.getId())}) > 0;
        close();
        return status;
    }


    boolean deleteInvoice(String id) {
        open();
        boolean status;
        status = database.delete(DbConstants.INVOICE, DbConstants.Detail.ID + " = ?", new String[]{id}) > 0;
        close();
        return status;
    }


    InvoiceDTO getInvoice(String id) {
        open();
        String sql = String.format(DbSQL.GET_INVOICE, id);

        InvoiceDTO invoiceDTO = null;
        Invoice invoice = null;
        Cursor c = null;

        try {
            c = database.rawQuery(sql, null);
            c.moveToFirst();
            Gson gson = new GsonBuilder().serializeNulls().create();

            while (!c.isAfterLast()) {
                String data = c.getString(c.getColumnIndex("data"));
                if (data != null) {
                    invoice = gson.fromJson(data, Invoice.class);
                    invoice.setId(id);
                    break;
                }
                c.moveToNext();
            }
        } catch (Exception ignored) {
        }

        if (c != null) {
            c.close();
        }
        close();

        if (invoice != null) {
            invoiceDTO = new InvoiceDTO(invoice);
        }

        return invoiceDTO;
    }


    ArrayList<InvoiceSum> getInvoiceList(Filter filter) {
        open();
        int invoiceType = filter.getExtraFilters().get("InvoiceType");

        String sql;
        if (filter.getSearchText() != null) {
            sql = String.format(DbSQL.INVOICE_LIST_LIKE, invoiceType, filter.getSearchText(), filter.getPaging().getPageSize(), (filter.getPaging().getPageSize() * (filter.getPaging().getCurrentPage() - 1)));

        } else {
            sql = String.format(DbSQL.INVOICE_LIST, invoiceType, filter.getPaging().getPageSize(), (filter.getPaging().getPageSize() * (filter.getPaging().getCurrentPage() - 1)));
        }

        ArrayList<InvoiceSum> invoiceSums = new ArrayList<>();
        Cursor c = null;

        try {
            c = database.rawQuery(sql, null);
            c.moveToFirst();
            Gson gson = new GsonBuilder().serializeNulls().create();

            while (!c.isAfterLast()) {
                String id = c.getString(c.getColumnIndex("id"));
                String data = c.getString(c.getColumnIndex("data"));
                if (data != null) {
                    Invoice invoice = gson.fromJson(data, Invoice.class);
                    invoice.setId(id);
                    InvoiceSum invoiceSum = new InvoiceSum(invoice);
                    invoiceSums.add(invoiceSum);
                }
                c.moveToNext();
            }
        } catch (Exception ignored) {
        }

        if (c != null) {
            c.close();
        }
        close();

        return invoiceSums;
    }

}