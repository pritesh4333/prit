package in.co.vyaparienterprise.database;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import in.co.vyaparienterprise.database.invoice.DbInvoiceObject;
import in.co.vyaparienterprise.middleware.listener.ServiceCall;
import in.co.vyaparienterprise.model.Firm;
import in.co.vyaparienterprise.model.Invoice;
import in.co.vyaparienterprise.model.KeyValue;
import in.co.vyaparienterprise.model.Product;
import in.co.vyaparienterprise.model.request.filter.Filter;
import in.co.vyaparienterprise.model.response.BaseModel;
import in.co.vyaparienterprise.model.response.dto.FirmDTO;
import in.co.vyaparienterprise.model.response.dto.FirmsSumDTO;
import in.co.vyaparienterprise.model.response.dto.InvoiceDTO;
import in.co.vyaparienterprise.model.response.dto.InvoicesSumDTO;
import in.co.vyaparienterprise.model.response.dto.ProductDTO;
import in.co.vyaparienterprise.model.response.dto.ProductsSumDTO;

/**
 * Created by bekirdursun on 5.02.2018.
 */

public class DbBridge {

    private DbHelper dbHelper;

    public DbBridge(Context context) {
        dbHelper = DbHelper.getInstance(context);
    }

    //COMMON
    public void getKVList(String columnName, ServiceCall<BaseModel<ArrayList<KeyValue>>> serviceCall) {
        getKVList(columnName, null, null, serviceCall);
    }

    public void getKVList(String columnName, String searchText, ServiceCall<BaseModel<ArrayList<KeyValue>>> serviceCall) {
        getKVList(columnName, searchText, null, serviceCall);
    }

    public void getKVList(String columnName, String searchText, String parentId, ServiceCall<BaseModel<ArrayList<KeyValue>>> serviceCall) {
        ArrayList<KeyValue> keyValues = dbHelper.getKVList(columnName, parentId, searchText);

        if (keyValues != null) {
            BaseModel<ArrayList<KeyValue>> response = new BaseModel<>(keyValues);
            serviceCall.onResponse(false, response);
        } else {
            serviceCall.onFailure(false, null);
        }
    }
    //COMMON


    //PRODUCT
    public void addProduct(Product product, ServiceCall<BaseModel<ProductDTO>> serviceCall) {
        DbObject<Product> dbObject = new DbObject<>(product);
        Date now = new Date();

        ProductDTO productDTO = new ProductDTO(product);
        BaseModel<ProductDTO> response = new BaseModel<>(productDTO);

        String tags = productDTO.getName();
        String uniqueID = UUID.randomUUID().toString();

        dbObject.setSearchTags(tags);
        dbObject.setCreatedDate(now);

        if (serviceCall != null) {
            dbObject.setId(uniqueID);
            dbObject.getData().setId(uniqueID);
            dbObject.setSynchronized(false);
        } else {
            dbObject.setId(product.getId());
            dbObject.setSynchronized(true);
            dbObject.setSynchronizedDate(now);
        }

        boolean isSaved = dbHelper.addProduct(dbObject);

        if (serviceCall != null) {
            if (isSaved) {
                serviceCall.onResponse(false, response);
            } else {
                serviceCall.onFailure(false, null);
            }
        }
    }

    public void updateProduct(Product product, ServiceCall<BaseModel<ProductDTO>> serviceCall) {
        DbObject<Product> dbObject = new DbObject<>(product);
        Date now = new Date();

        ProductDTO productDTO = new ProductDTO(product);
        BaseModel<ProductDTO> response = new BaseModel<>(productDTO);

        String tags = productDTO.getName();

        dbObject.setId(product.getId());
        dbObject.setSearchTags(tags);
        dbObject.setModifiedDate(now);

        if (serviceCall != null) {
            dbObject.setSynchronized(false);
        } else {
            dbObject.setSynchronized(true);
            dbObject.setSynchronizedDate(now);
        }

        boolean isSaved = dbHelper.updateProduct(dbObject);

        if (serviceCall != null) {
            if (isSaved) {
                serviceCall.onResponse(false, response);
            } else {
                serviceCall.onFailure(false, null);
            }
        }
    }

    public void deleteProduct(String id, ServiceCall<BaseModel<ProductDTO>> serviceCall) {
        Product product = new Product(id);
        ProductDTO productDTO = new ProductDTO(product);
        BaseModel<ProductDTO> response = new BaseModel<>(productDTO);

        boolean isSaved = dbHelper.deleteProduct(id);

        if (serviceCall != null) {
            if (isSaved) {
                serviceCall.onResponse(false, response);
            } else {
                serviceCall.onFailure(false, null);
            }
        }
    }

    public void getProduct(String id, ServiceCall<BaseModel<ProductDTO>> serviceCall) {
        ProductDTO productDTO = dbHelper.getProduct(id);

        if (productDTO != null) {
            BaseModel<ProductDTO> response = new BaseModel<>(productDTO);
            serviceCall.onResponse(false, response);
        } else {
            serviceCall.onFailure(false, null);
        }
    }

    public void getProductList(Filter filter, ServiceCall<BaseModel<ProductsSumDTO>> serviceCall) {
        ProductsSumDTO productsSumDTO = new ProductsSumDTO(dbHelper.getProductList(filter));

        if (productsSumDTO != null) {
            BaseModel<ProductsSumDTO> response = new BaseModel<>(productsSumDTO);
            serviceCall.onResponse(false, response);
        } else {
            serviceCall.onFailure(false, null);
        }
    }
    //PRODUCT


    //FIRM
    public void addFirm(Firm firm, ServiceCall<BaseModel<FirmDTO>> serviceCall) {
        DbObject<Firm> dbObject = new DbObject<>(firm);
        Date now = new Date();

        FirmDTO firmDTO = new FirmDTO(firm);
        BaseModel<FirmDTO> response = new BaseModel<>(firmDTO);

        String tags = firmDTO.getName();
        String uniqueID = UUID.randomUUID().toString();

        dbObject.setSearchTags(tags);
        dbObject.setCreatedDate(now);

        if (serviceCall != null) {
            dbObject.setId(uniqueID);
            dbObject.getData().setId(uniqueID);
            dbObject.setSynchronized(false);
        } else {
            dbObject.setId(firm.getId());
            dbObject.setSynchronized(true);
            dbObject.setSynchronizedDate(now);
        }

        boolean isSaved = dbHelper.addFirm(dbObject);

        if (serviceCall != null) {
            if (isSaved) {
                serviceCall.onResponse(false, response);
            } else {
                serviceCall.onFailure(false, null);
            }
        }
    }


    public void updateFirm(Firm firm, ServiceCall<BaseModel<FirmDTO>> serviceCall) {
        DbObject<Firm> dbObject = new DbObject<>(firm);
        Date now = new Date();

        FirmDTO firmDTO = new FirmDTO(firm);
        BaseModel<FirmDTO> response = new BaseModel<>(firmDTO);

        String tags = firmDTO.getName();

        dbObject.setId(firm.getId());
        dbObject.setSearchTags(tags);
        dbObject.setModifiedDate(now);

        if (serviceCall != null) {
            dbObject.setSynchronized(false);
        } else {
            dbObject.setSynchronized(true);
            dbObject.setSynchronizedDate(now);
        }

        boolean isSaved = dbHelper.updateFirm(dbObject);

        if (serviceCall != null) {
            if (isSaved) {
                serviceCall.onResponse(false, response);
            } else {
                serviceCall.onFailure(false, null);
            }
        }
    }


    public void deleteFirm(String id, ServiceCall<BaseModel<FirmDTO>> serviceCall) {
        Firm firm = new Firm(id);
        FirmDTO firmDTO = new FirmDTO(firm);
        BaseModel<FirmDTO> response = new BaseModel<>(firmDTO);

        boolean isSaved = dbHelper.deleteFirm(id);

        if (serviceCall != null) {
            if (isSaved) {
                serviceCall.onResponse(false, response);
            } else {
                serviceCall.onFailure(false, null);
            }
        }
    }


    public void getFirm(String id, ServiceCall<BaseModel<FirmDTO>> serviceCall) {
        FirmDTO firmDTO = dbHelper.getFirm(id);

        if (firmDTO != null) {
            BaseModel<FirmDTO> response = new BaseModel<>(firmDTO);
            serviceCall.onResponse(false, response);
        } else {
            serviceCall.onFailure(false, null);
        }
    }

    public void getFirmList(Filter filter, ServiceCall<BaseModel<FirmsSumDTO>> serviceCall) {
        FirmsSumDTO firmsSumDTO = new FirmsSumDTO(dbHelper.getFirmList(filter));

        if (firmsSumDTO != null) {
            BaseModel<FirmsSumDTO> response = new BaseModel<>(firmsSumDTO);
            serviceCall.onResponse(false, response);
        } else {
            serviceCall.onFailure(false, null);
        }
    }
    //FIRM


    //INVOICE
    public void addInvoice(Invoice invoice, ServiceCall<BaseModel<InvoiceDTO>> serviceCall) {
        DbInvoiceObject<Invoice> dbInvoiceObject = new DbInvoiceObject<>(invoice);
        Date now = new Date();

        InvoiceDTO invoiceDTO = new InvoiceDTO(invoice);
        BaseModel<InvoiceDTO> response = new BaseModel<>(invoiceDTO);

        String tags = invoiceDTO.getInvoiceNumber() + " - " + invoiceDTO.getFirm().getName();
        String uniqueID = UUID.randomUUID().toString();

        dbInvoiceObject.setSearchTags(tags);
        dbInvoiceObject.setCreatedDate(now);
        dbInvoiceObject.setInvoiceType(invoice.getInvoiceType());
        dbInvoiceObject.setInvoiceDate(invoice.getInvoiceDate());

        if (serviceCall != null) {
            dbInvoiceObject.setId(uniqueID);
            dbInvoiceObject.getData().setId(uniqueID);
            dbInvoiceObject.setSynchronized(false);
        } else {
            dbInvoiceObject.setId(invoice.getId());
            dbInvoiceObject.setSynchronized(true);
            dbInvoiceObject.setSynchronizedDate(now);
        }

        boolean isSaved = dbHelper.addInvoice(dbInvoiceObject);

        if (serviceCall != null) {
            if (isSaved) {
                serviceCall.onResponse(false, response);
            } else {
                serviceCall.onFailure(false, null);
            }
        }
    }

    public void updateInvoice(Invoice invoice, ServiceCall<BaseModel<InvoiceDTO>> serviceCall) {
        DbInvoiceObject<Invoice> dbInvoiceObject = new DbInvoiceObject<>(invoice);
        Date now = new Date();

        InvoiceDTO invoiceDTO = new InvoiceDTO(invoice);
        BaseModel<InvoiceDTO> response = new BaseModel<>(invoiceDTO);

        String tags = invoiceDTO.getInvoiceNumber() + " - " + invoiceDTO.getFirm().getName();

        dbInvoiceObject.setId(invoiceDTO.getId());
        dbInvoiceObject.setSearchTags(tags);
        dbInvoiceObject.setModifiedDate(now);
        dbInvoiceObject.setInvoiceType(invoice.getInvoiceType());
        dbInvoiceObject.setInvoiceDate(invoice.getInvoiceDate());

        if (serviceCall != null) {
            dbInvoiceObject.setSynchronized(false);
        } else {
            dbInvoiceObject.setSynchronized(true);
            dbInvoiceObject.setSynchronizedDate(now);
        }

        boolean isSaved = dbHelper.updateInvoice(dbInvoiceObject);

        if (serviceCall != null) {
            if (isSaved) {
                serviceCall.onResponse(false, response);
            } else {
                serviceCall.onFailure(false, null);
            }
        }
    }

    public void deleteInvoice(String id, ServiceCall<BaseModel<InvoiceDTO>> serviceCall) {
        Invoice invoice = new Invoice(id);
        InvoiceDTO invoiceDTO = new InvoiceDTO(invoice);
        BaseModel<InvoiceDTO> response = new BaseModel<>(invoiceDTO);

        boolean isSaved = dbHelper.deleteInvoice(id);

        if (serviceCall != null) {
            if (isSaved) {
                serviceCall.onResponse(false, response);
            } else {
                serviceCall.onFailure(false, null);
            }
        }
    }

    public void getInvoice(String id, ServiceCall<BaseModel<InvoiceDTO>> serviceCall) {
        InvoiceDTO invoiceDTO = dbHelper.getInvoice(id);

        if (invoiceDTO != null) {
            BaseModel<InvoiceDTO> response = new BaseModel<>(invoiceDTO);
            serviceCall.onResponse(false, response);
        } else {
            serviceCall.onFailure(false, null);
        }
    }

    public void getInvoiceList(Filter filter, ServiceCall<BaseModel<InvoicesSumDTO>> serviceCall) {
        InvoicesSumDTO invoicesSumDTO = new InvoicesSumDTO(dbHelper.getInvoiceList(filter));

        if (invoicesSumDTO != null) {
            BaseModel<InvoicesSumDTO> response = new BaseModel<>(invoicesSumDTO);
            serviceCall.onResponse(false, response);
        } else {
            serviceCall.onFailure(false, null);
        }
    }
    //INVOICE
}