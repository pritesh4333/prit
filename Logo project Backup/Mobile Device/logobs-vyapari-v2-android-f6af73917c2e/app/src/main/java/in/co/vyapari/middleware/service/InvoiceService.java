package in.co.vyapari.middleware.service;

import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import in.co.vyapari.VyapariApp;
import in.co.vyapari.middleware.ServiceManager;
import in.co.vyapari.middleware.listener.ServiceCall;
import in.co.vyapari.model.Invoice;
import in.co.vyapari.model.KeyValue;
import in.co.vyapari.model.request.filter.Filter;
import in.co.vyapari.model.response.BaseModel;
import in.co.vyapari.model.response.dto.ArApCollectionDTO;
import in.co.vyapari.model.response.dto.CollectionListDTO;
import in.co.vyapari.model.response.dto.InvoiceDTO;
import in.co.vyapari.model.response.dto.InvoicesSumDTO;
import in.co.vyapari.model.response.dto.PlaceofSupplyDTO;
import in.co.vyapari.model.response.summary.CollectionSum;
import retrofit2.Call;

/**
 * Created by bekirdursun on 5.02.2018.
 */

public class InvoiceService {

    public static synchronized void calculateInvoice(Invoice invoice, ServiceCall<BaseModel<InvoiceDTO>> serviceCall) {
        serviceCall.start();
        calculateInvoiceCall(invoice, serviceCall);
    }

    private static synchronized void calculateInvoiceCall(final Invoice invoice, final ServiceCall<BaseModel<InvoiceDTO>> serviceCall) {
        Gson gson = new Gson();
        String jsn= gson.toJson(invoice);
        Log.e("JSON OBJECT-",jsn);
        Call<BaseModel<InvoiceDTO>> call = VyapariApp.getApiService().calculateInvoice(invoice);
        new ServiceManager<BaseModel<InvoiceDTO>>().ServiceCall(call, new ServiceCall<BaseModel<InvoiceDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<InvoiceDTO> response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
            }
        });
    }

    private static void calculateInvoiceSQLite(Invoice invoice, ServiceCall<BaseModel<InvoiceDTO>> serviceCall) {
    }




    public static synchronized void getPrintUrl(String id, ServiceCall<BaseModel<String>> serviceCall) {
        serviceCall.start();
        getPrintUrlCall(id, serviceCall);
    }

    private static synchronized void getPrintUrlCall(String id, final ServiceCall<BaseModel<String>> serviceCall) {

        Log.e("JSON OBJECT-",id);
        Call<BaseModel<String>> call = VyapariApp.getApiService().getPrintUrl(id);
        new ServiceManager<BaseModel<String>>().ServiceCall(call, new ServiceCall<BaseModel<String>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<String> response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
            }
        });
    }

    private static void getPrintUrlSQLite(String id, ServiceCall<BaseModel<String>> serviceCall) {
    }


    public static synchronized void getInvoices(Filter filter, ServiceCall<BaseModel<InvoicesSumDTO>> serviceCall) {
        serviceCall.start();
        getInvoicesCall(filter, serviceCall);
    }

    private static synchronized void getInvoicesCall(final Filter filter, final ServiceCall<BaseModel<InvoicesSumDTO>> serviceCall) {
        Gson gson = new Gson();
        String jsn= gson.toJson(filter);
        Log.e("JSON OBJECT-",jsn);
        Call<BaseModel<InvoicesSumDTO>> call = VyapariApp.getApiService().getInvoices(filter);
        new ServiceManager<BaseModel<InvoicesSumDTO>>().ServiceCall(call, new ServiceCall<BaseModel<InvoicesSumDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<InvoicesSumDTO> response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
                //getInvoicesSQLite(filter, serviceCall);
            }
        });
    }
    public static synchronized void getCollections(Filter filter, ServiceCall<BaseModel<List<CollectionSum>>> serviceCall) {
        serviceCall.start();
        getCollectionsCall(filter, serviceCall);
    }

    private static synchronized void getCollectionsCall(final Filter filter, final ServiceCall<BaseModel<List<CollectionSum>>> serviceCall) {
        Gson gson = new Gson();
        String jsn= gson.toJson(filter);
        Log.e("JSON OBJECT-",jsn);
        Call<BaseModel<List<CollectionSum>>> call = VyapariApp.getApiService().getARAPSlips(filter);
        new ServiceManager<BaseModel<List<CollectionSum>>>().ServiceCall(call, new ServiceCall<BaseModel<List<CollectionSum>>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<List<CollectionSum>> response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
                //getInvoicesSQLite(filter, serviceCall);
            }
        });
    }
    private static void getInvoicesSQLite(Filter filter, ServiceCall<BaseModel<InvoicesSumDTO>> serviceCall) {
        //VyapariApp.getDbBridge().getInvoiceList(filter, serviceCall);
    }
    public static synchronized void getPlaseofSupply(String id, ServiceCall<BaseModel<PlaceofSupplyDTO>> serviceCall) {
        serviceCall.start();
        getPlaseofSupplyCall(id, serviceCall);
    }

    private static synchronized void getPlaseofSupplyCall(final String id, final ServiceCall<BaseModel<PlaceofSupplyDTO>> serviceCall) {
        Call<BaseModel<PlaceofSupplyDTO>> call = VyapariApp.getApiService().InvoicPrintInfo(id);
        new ServiceManager<BaseModel<PlaceofSupplyDTO>>().ServiceCall(call, new ServiceCall<BaseModel<PlaceofSupplyDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<PlaceofSupplyDTO> response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
                //getInvoiceDetailSQLite(id, serviceCall);
            }
        });
    }

    public static synchronized void getARAPDetail(String id,  ServiceCall<BaseModel<ArApCollectionDTO>> serviceCall) {
        serviceCall.start();
        getARAPDetailCall(id,  serviceCall);
    }

    private static synchronized void getARAPDetailCall(final String id,  final ServiceCall<BaseModel<ArApCollectionDTO>> serviceCall) {

        Log.e("JSON OBJECT-",id);
        Call<BaseModel<ArApCollectionDTO>> call = VyapariApp.getApiService().getArpSlip(id);
        new ServiceManager<BaseModel<ArApCollectionDTO>>().ServiceCall(call, new ServiceCall<BaseModel<ArApCollectionDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<ArApCollectionDTO> response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
                //getInvoiceDetailSQLite(id, serviceCall);
            }
        });
    }

    public static synchronized void getInvoiceDetailEdit(String id, int invoiceType, ServiceCall<BaseModel<Invoice>> serviceCall) {
        serviceCall.start();
        getInvoiceDetailEditCall(id, invoiceType, serviceCall);
    }

    private static synchronized void getInvoiceDetailEditCall(final String id, int invoiceType, final ServiceCall<BaseModel<Invoice>> serviceCall) {

        Log.e("JSON OBJECT-",id);
        Call<BaseModel<Invoice>> call = VyapariApp.getApiService().getInvoiceDetailEdit(id, invoiceType);
        new ServiceManager<BaseModel<Invoice>>().ServiceCall(call, new ServiceCall<BaseModel<Invoice>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<Invoice> response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
                //getInvoiceDetailSQLite(id, serviceCall);
            }
        });
    }
    public static synchronized void getInvoiceDetail(String id, int invoiceType, ServiceCall<BaseModel<InvoiceDTO>> serviceCall) {
        serviceCall.start();
        getInvoiceDetailCall(id, invoiceType, serviceCall);
    }

    private static synchronized void getInvoiceDetailCall(final String id, int invoiceType, final ServiceCall<BaseModel<InvoiceDTO>> serviceCall) {

        Log.e("JSON OBJECT-",id);
        Call<BaseModel<InvoiceDTO>> call = VyapariApp.getApiService().getInvoiceDetail(id, invoiceType);
        new ServiceManager<BaseModel<InvoiceDTO>>().ServiceCall(call, new ServiceCall<BaseModel<InvoiceDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<InvoiceDTO> response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
                //getInvoiceDetailSQLite(id, serviceCall);
            }
        });
    }

    private static void getInvoiceDetailSQLite(String id, ServiceCall<BaseModel<InvoiceDTO>> serviceCall) {
        //VyapariApp.getDbBridge().getInvoice(id, serviceCall);
    }


    public static synchronized void addInvoice(Invoice invoice, ServiceCall<BaseModel<InvoiceDTO>> serviceCall) {
        serviceCall.start();
        addInvoiceCall(invoice, serviceCall);
    }

    private static synchronized void addInvoiceCall(final Invoice invoice, final ServiceCall<BaseModel<InvoiceDTO>> serviceCall) {
        Gson gson = new Gson();
        String jsn= gson.toJson(invoice);
        Log.e("JSON OBJECT-",jsn);
        Call<BaseModel<InvoiceDTO>> call = VyapariApp.getApiService().addInvoice(invoice);
        new ServiceManager<BaseModel<InvoiceDTO>>().ServiceCall(call, new ServiceCall<BaseModel<InvoiceDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<InvoiceDTO> response) {
                /*if (!response.isError()) {
                    invoice.setId(response.getData().getId());
                    addInvoiceSQLite(invoice, null);
                }*/
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
                //addInvoiceSQLite(invoice, serviceCall);
            }
        });
    }

    private static void addInvoiceSQLite(Invoice invoice, ServiceCall<BaseModel<InvoiceDTO>> serviceCall) {
        //VyapariApp.getDbBridge().addInvoice(invoice, serviceCall);
    }


    public static synchronized void updateInvoice(Invoice invoice, ServiceCall<BaseModel<InvoiceDTO>> serviceCall) {
        serviceCall.start();
        updateInvoiceCall(invoice, serviceCall);
    }

    private static synchronized void updateInvoiceCall(final Invoice invoice, final ServiceCall<BaseModel<InvoiceDTO>> serviceCall) {
        Gson gson = new Gson();
        String jsn= gson.toJson(invoice);
        Log.e("JSON OBJECT-",jsn);
        Call<BaseModel<InvoiceDTO>> call = VyapariApp.getApiService().updateInvoice(invoice);
        new ServiceManager<BaseModel<InvoiceDTO>>().ServiceCall(call, new ServiceCall<BaseModel<InvoiceDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<InvoiceDTO> response) {
                /*if (!response.isError()) {
                    invoice.setId(response.getData().getId());
                    updateInvoiceSQLite(invoice, null);
                }*/
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
                //updateInvoiceSQLite(invoice, serviceCall);
            }
        });
    }

    private static void updateInvoiceSQLite(Invoice invoice, ServiceCall<BaseModel<InvoiceDTO>> serviceCall) {
        //VyapariApp.getDbBridge().updateInvoice(invoice, serviceCall);
    }


    public static synchronized void deleteInvoice(String id, int invoiceType, ServiceCall<BaseModel<InvoiceDTO>> serviceCall) {
        serviceCall.start();
        deleteInvoiceCall(id, invoiceType, serviceCall);
    }

    private static synchronized void deleteInvoiceCall(final String id, int invoiceType, final ServiceCall<BaseModel<InvoiceDTO>> serviceCall) {

        Log.e("JSON OBJECT-",id);
        Call<BaseModel<InvoiceDTO>> call = VyapariApp.getApiService().deleteInvoice(id, invoiceType);
        new ServiceManager<BaseModel<InvoiceDTO>>().ServiceCall(call, new ServiceCall<BaseModel<InvoiceDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<InvoiceDTO> response) {
                /*if (!response.isError()) {
                    deleteInvoiceSQLite(id, serviceCall);
                }*/
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
                //serviceCall.onFailure(true, throwable);
            }
        });
    }

    private static void deleteInvoiceSQLite(String id, ServiceCall<BaseModel<InvoiceDTO>> serviceCall) {
        //VyapariApp.getDbBridge().deleteInvoice(id, serviceCall);
    }

    public static synchronized void changeStatus(String id, int invoiceType, ServiceCall<BaseModel<Boolean>> serviceCall) {
        serviceCall.start();
        changeStatusCall(id, invoiceType, serviceCall);
    }

    private static synchronized void changeStatusCall(final String id, int invoiceType, final ServiceCall<BaseModel<Boolean>> serviceCall) {

        Log.e("JSON OBJECT-",id);
        Call<BaseModel<Boolean>> call = VyapariApp.getApiService().changeStatus(id, invoiceType);
        new ServiceManager<BaseModel<Boolean>>().ServiceCall(call, new ServiceCall<BaseModel<Boolean>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<Boolean> response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
            }
        });
    }
}
