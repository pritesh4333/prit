package in.co.vyaparienterprise.middleware.service;

import android.util.Log;

import com.google.gson.Gson;

import java.util.List;

import in.co.vyaparienterprise.VyapariApp;
import in.co.vyaparienterprise.middleware.ServiceManager;
import in.co.vyaparienterprise.middleware.listener.ServiceCall;
import in.co.vyaparienterprise.model.Invoice;
import in.co.vyaparienterprise.model.request.filter.Filter;
import in.co.vyaparienterprise.model.response.BaseModel;
import in.co.vyaparienterprise.model.response.dto.ArApCollectionDTO;
import in.co.vyaparienterprise.model.response.dto.InvoiceDTO;
import in.co.vyaparienterprise.model.response.dto.InvoicesSumDTO;
import in.co.vyaparienterprise.model.response.dto.PlaceofSupplyDTO;
import in.co.vyaparienterprise.model.response.summary.CollectionSum;
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
    public static synchronized void calculateOrder(Invoice invoice, ServiceCall<BaseModel<InvoiceDTO>> serviceCall) {
        serviceCall.start();
        calculateOrderCall(invoice, serviceCall);
    }

    private static synchronized void calculateOrderCall(final Invoice invoice, final ServiceCall<BaseModel<InvoiceDTO>> serviceCall) {
        Gson gson = new Gson();
        String jsn= gson.toJson(invoice);
        Log.e("JSON OBJECT-",jsn);
        Call<BaseModel<InvoiceDTO>> call = VyapariApp.getApiService().calculateOrder(invoice);
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
    public static synchronized void calculateDispatch(Invoice invoice, ServiceCall<BaseModel<InvoiceDTO>> serviceCall) {
        serviceCall.start();
        calculateDispatchCall(invoice, serviceCall);
    }

    private static synchronized void calculateDispatchCall(final Invoice invoice, final ServiceCall<BaseModel<InvoiceDTO>> serviceCall) {
        Gson gson = new Gson();
        String jsn= gson.toJson(invoice);
        Log.e("JSON OBJECT-",jsn);
        Call<BaseModel<InvoiceDTO>> call = VyapariApp.getApiService().calculateDispatch(invoice);
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
    public static synchronized void getDispatch(Filter filter, ServiceCall<BaseModel<InvoicesSumDTO>> serviceCall) {
        serviceCall.start();
        getDispatchCall(filter, serviceCall);
    }

    private static synchronized void getDispatchCall(final Filter filter, final ServiceCall<BaseModel<InvoicesSumDTO>> serviceCall) {
        Gson gson = new Gson();
        String jsn= gson.toJson(filter);
        Log.e("JSON OBJECT-",jsn);
        Call<BaseModel<InvoicesSumDTO>> call = VyapariApp.getApiService().getDispatch(filter);
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
    public static synchronized void getOrders(Filter filter, ServiceCall<BaseModel<InvoicesSumDTO>> serviceCall) {
        serviceCall.start();
        getOrdersCall(filter, serviceCall);
    }

    private static synchronized void getOrdersCall(final Filter filter, final ServiceCall<BaseModel<InvoicesSumDTO>> serviceCall) {
        Gson gson = new Gson();
        String jsn= gson.toJson(filter);
        Log.e("JSON OBJECT-",jsn);
        Call<BaseModel<InvoicesSumDTO>> call = VyapariApp.getApiService().getOrders(filter);
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
    public static synchronized void getOrderDetailEdit(String id, int invoiceType, ServiceCall<BaseModel<Invoice>> serviceCall) {
        serviceCall.start();
        getOrderDetailEditCall(id, invoiceType, serviceCall);
    }

    private static synchronized void getOrderDetailEditCall(final String id, int invoiceType, final ServiceCall<BaseModel<Invoice>> serviceCall) {

        Log.e("JSON OBJECT-",id);
        Call<BaseModel<Invoice>> call = VyapariApp.getApiService().getOrderDetailEdit(id, invoiceType);
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
    public static synchronized void getDispatchDetailEdit(String id, int invoiceType, ServiceCall<BaseModel<Invoice>> serviceCall) {
        serviceCall.start();
        getDispatchDetailEditCall(id, invoiceType, serviceCall);
    }

    private static synchronized void getDispatchDetailEditCall(final String id, int invoiceType, final ServiceCall<BaseModel<Invoice>> serviceCall) {

        Log.e("JSON OBJECT-",id);
        Call<BaseModel<Invoice>> call = VyapariApp.getApiService().getDispatchDetailEdit(id, invoiceType);
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
    public static synchronized void getOrderDetail(String id, int invoiceType, ServiceCall<BaseModel<InvoiceDTO>> serviceCall) {
        serviceCall.start();
        getOrderDetailCall(id, invoiceType, serviceCall);
    }

    private static synchronized void getOrderDetailCall(final String id, int invoiceType, final ServiceCall<BaseModel<InvoiceDTO>> serviceCall) {

        Log.e("JSON OBJECT-",id+"Invice Type"+invoiceType);
        Call<BaseModel<InvoiceDTO>> call = VyapariApp.getApiService().getOrderDetail(id, invoiceType);
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
    public static synchronized void getDispatchDetail(String id, int invoiceType, ServiceCall<BaseModel<InvoiceDTO>> serviceCall) {
        serviceCall.start();
        getDispatchDetailCall(id, invoiceType, serviceCall);
    }

    private static synchronized void getDispatchDetailCall(final String id, int invoiceType, final ServiceCall<BaseModel<InvoiceDTO>> serviceCall) {

        Log.e("JSON OBJECT-",id);
        Call<BaseModel<InvoiceDTO>> call = VyapariApp.getApiService().getDispatchDetail(id, invoiceType);
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
    public static synchronized void OrdertoInvoicebilling(Invoice invoice, ServiceCall<BaseModel<InvoiceDTO>> serviceCall) {
        serviceCall.start();
        OrdertoInvoicebillingCall(invoice, serviceCall);
    }

    private static synchronized void OrdertoInvoicebillingCall(final Invoice invoice, final ServiceCall<BaseModel<InvoiceDTO>> serviceCall) {
        Gson gson = new Gson();
        String jsn= gson.toJson(invoice);
        Log.e("JSON OBJECT-",jsn);
        Call<BaseModel<InvoiceDTO>> call = VyapariApp.getApiService().addOrderToInvoice(invoice);
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
    public static synchronized void addOrder(Invoice invoice, ServiceCall<BaseModel<InvoiceDTO>> serviceCall) {
        serviceCall.start();
        addOrderCall(invoice, serviceCall);
    }

    private static synchronized void addOrderCall(final Invoice invoice, final ServiceCall<BaseModel<InvoiceDTO>> serviceCall) {
        Gson gson = new Gson();
        String jsn= gson.toJson(invoice);
        Log.e("JSON OBJECT-",jsn);
        Call<BaseModel<InvoiceDTO>> call = VyapariApp.getApiService().addOrder(invoice);
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
    public static synchronized void addDispatch(Invoice invoice, ServiceCall<BaseModel<InvoiceDTO>> serviceCall) {
        serviceCall.start();
        addDispatchCall(invoice, serviceCall);
    }

    private static synchronized void addDispatchCall(final Invoice invoice, final ServiceCall<BaseModel<InvoiceDTO>> serviceCall) {
        Gson gson = new Gson();
        String jsn= gson.toJson(invoice);
        Log.e("JSON OBJECT-",jsn);
        Call<BaseModel<InvoiceDTO>> call = VyapariApp.getApiService().addDispatch(invoice);
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


    public static synchronized void updateOrder(Invoice invoice, ServiceCall<BaseModel<InvoiceDTO>> serviceCall) {
        serviceCall.start();
        updateOrderCall(invoice, serviceCall);
    }

    private static synchronized void updateOrderCall(final Invoice invoice, final ServiceCall<BaseModel<InvoiceDTO>> serviceCall) {
        Gson gson = new Gson();
        String jsn= gson.toJson(invoice);
        Log.e("JSON OBJECT-",jsn);
        Call<BaseModel<InvoiceDTO>> call = VyapariApp.getApiService().updateOrder(invoice);
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
    public static synchronized void updateDispatch(Invoice invoice, ServiceCall<BaseModel<InvoiceDTO>> serviceCall) {
        serviceCall.start();
        updateDispatchCall(invoice, serviceCall);
    }

    private static synchronized void updateDispatchCall(final Invoice invoice, final ServiceCall<BaseModel<InvoiceDTO>> serviceCall) {
        Gson gson = new Gson();
        String jsn= gson.toJson(invoice);
        Log.e("JSON OBJECT-",jsn);
        Call<BaseModel<InvoiceDTO>> call = VyapariApp.getApiService().updateDispatch(invoice);
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
    public static synchronized void deleteOrder(String id, int invoiceType, ServiceCall<BaseModel<InvoiceDTO>> serviceCall) {
        serviceCall.start();
        deleteOrderCall(id, invoiceType, serviceCall);
    }

    private static synchronized void deleteOrderCall(final String id, int invoiceType, final ServiceCall<BaseModel<InvoiceDTO>> serviceCall) {

        Log.e("JSON OBJECT-",id);
        Call<BaseModel<InvoiceDTO>> call = VyapariApp.getApiService().deleteOrder(id,invoiceType);
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

    public static synchronized void deleteDispatch(String id, int invoiceType, ServiceCall<BaseModel<InvoiceDTO>> serviceCall) {
        serviceCall.start();
        deleteDispatchCall(id, invoiceType, serviceCall);
    }

    private static synchronized void deleteDispatchCall(final String id, int invoiceType, final ServiceCall<BaseModel<InvoiceDTO>> serviceCall) {

        Log.e("JSON OBJECT-",id);
        Call<BaseModel<InvoiceDTO>> call = VyapariApp.getApiService().deleteDispatch(id);
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

    public static synchronized void changeStatus(String id, int invoiceType, int statusType, ServiceCall<BaseModel<Boolean>> serviceCall) {
        serviceCall.start();
        changeStatusCall(id, invoiceType,  statusType, serviceCall);
    }

    private static synchronized void changeStatusCall(final String id, int invoiceType,int statusType, final ServiceCall<BaseModel<Boolean>> serviceCall) {

        Log.e("JSON OBJECT-",id);
        Call<BaseModel<Boolean>> call = VyapariApp.getApiService().changeStatus(id, invoiceType,statusType);
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
    public static synchronized void changeStatusOrder(String id, int invoiceType,int statusType, ServiceCall<BaseModel<Boolean>> serviceCall) {
        serviceCall.start();
        changeStatusOrderCall(id, invoiceType,  statusType, serviceCall);
    }

    private static synchronized void changeStatusOrderCall(final String id, int invoiceType, int statusType,final ServiceCall<BaseModel<Boolean>> serviceCall) {

        Log.e("JSON OBJECT-",id);
        Call<BaseModel<Boolean>> call = VyapariApp.getApiService().OrderchangeStatus(id, invoiceType, statusType);
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
    public static synchronized void changeStatusDispatch(String id, int invoiceType,int statusType, ServiceCall<BaseModel<Boolean>> serviceCall) {
        serviceCall.start();
        changeStatusDispatchCall(id, invoiceType,  statusType, serviceCall);
    }

    private static synchronized void changeStatusDispatchCall(final String id, int invoiceType,int statusType, final ServiceCall<BaseModel<Boolean>> serviceCall) {

        Log.e("JSON OBJECT-",id);
        Call<BaseModel<Boolean>> call = VyapariApp.getApiService().DispatchhangeStatus(id, invoiceType, statusType);
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
