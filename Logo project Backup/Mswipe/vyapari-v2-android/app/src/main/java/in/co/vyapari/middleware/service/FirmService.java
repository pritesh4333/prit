package in.co.vyapari.middleware.service;

import android.util.Log;

import com.google.gson.Gson;

import in.co.vyapari.VyapariApp;
import in.co.vyapari.middleware.ServiceManager;
import in.co.vyapari.middleware.listener.ServiceCall;
import in.co.vyapari.model.Firm;
import in.co.vyapari.model.request.filter.Filter;
import in.co.vyapari.model.response.BaseModel;
import in.co.vyapari.model.response.dto.FirmDTO;
import in.co.vyapari.model.response.dto.FirmsSumDTO;
import retrofit2.Call;

/**
 * Created by bekirdursun on 5.02.2018.
 */

public class FirmService {

    public static synchronized void getFirms(Filter filter, ServiceCall<BaseModel<FirmsSumDTO>> serviceCall) {
        serviceCall.start();
        getFirmsCall(filter, serviceCall);
    }

    private static synchronized void getFirmsCall(final Filter filter, final ServiceCall<BaseModel<FirmsSumDTO>> serviceCall) {
        Gson gson = new Gson();
        String jsn= gson.toJson(filter);
        //Log.e("JSON OBJECT-",jsn);
        Call<BaseModel<FirmsSumDTO>> call = VyapariApp.getApiService().getFirms(filter);
        new ServiceManager<BaseModel<FirmsSumDTO>>().ServiceCall(call, new ServiceCall<BaseModel<FirmsSumDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<FirmsSumDTO> response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
                //getFirmListSQLite(filter, serviceCall);
            }
        });
    }

    private static void getFirmListSQLite(Filter filter, ServiceCall<BaseModel<FirmsSumDTO>> serviceCall) {
        //VyapariApp.getDbBridge().getFirmList(filter, serviceCall);
    }

    public static synchronized void getFirmDetail(String id, ServiceCall<BaseModel<FirmDTO>> serviceCall) {
        serviceCall.start();
        getFirmDetailCall(id, serviceCall);
    }

    private static synchronized void getFirmDetailCall(final String id, final ServiceCall<BaseModel<FirmDTO>> serviceCall) {

        //Log.e("JSON OBJECT-",id);
        Call<BaseModel<FirmDTO>> call = VyapariApp.getApiService().getFirmDetail(id);
        new ServiceManager<BaseModel<FirmDTO>>().ServiceCall(call, new ServiceCall<BaseModel<FirmDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<FirmDTO> response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
                //getFirmDetailSQLite(id, serviceCall);
            }
        });
    }

    private static void getFirmDetailSQLite(String id, ServiceCall<BaseModel<FirmDTO>> serviceCall) {
        //VyapariApp.getDbBridge().getFirm(id, serviceCall);
    }

    public static synchronized void addFirm(Firm firm, ServiceCall<BaseModel<FirmDTO>> serviceCall) {
        serviceCall.start();
        addFirmCall(firm, serviceCall);
    }

    private static synchronized void addFirmCall(final Firm firm, final ServiceCall<BaseModel<FirmDTO>> serviceCall) {
        Gson gson = new Gson();
        String jsn= gson.toJson(firm);
        //Log.e("JSON OBJECT-",jsn);
        Call<BaseModel<FirmDTO>> call = VyapariApp.getApiService().addFirm(firm);
        new ServiceManager<BaseModel<FirmDTO>>().ServiceCall(call, new ServiceCall<BaseModel<FirmDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<FirmDTO> response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
                //addFirmSQLite(firm, serviceCall);
            }
        });
    }

    private static void addFirmSQLite(Firm firm, ServiceCall<BaseModel<FirmDTO>> serviceCall) {
        //VyapariApp.getDbBridge().addFirm(firm, serviceCall);
    }


    public static synchronized void updateFirm(Firm firmRequest, ServiceCall<BaseModel<FirmDTO>> serviceCall) {
        serviceCall.start();
        updateFirmCall(firmRequest, serviceCall);
    }

    private static synchronized void updateFirmCall(final Firm firm, final ServiceCall<BaseModel<FirmDTO>> serviceCall) {
        Gson gson = new Gson();
        String jsn= gson.toJson(firm);
        //Log.e("JSON OBJECT-",jsn);
        Call<BaseModel<FirmDTO>> call = VyapariApp.getApiService().updateFirm(firm);
        new ServiceManager<BaseModel<FirmDTO>>().ServiceCall(call, new ServiceCall<BaseModel<FirmDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<FirmDTO> response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
                //updateFirmSQLite(firm, serviceCall);
            }
        });
    }

    private static void updateFirmSQLite(Firm firm, ServiceCall<BaseModel<FirmDTO>> serviceCall) {
        //VyapariApp.getDbBridge().updateFirm(firm, serviceCall);
    }

    public static synchronized void deleteFirm(String id, ServiceCall<BaseModel<FirmDTO>> serviceCall) {
        serviceCall.start();
        deleteFirmCall(id, serviceCall);
    }

    private static synchronized void deleteFirmCall(final String id, final ServiceCall<BaseModel<FirmDTO>> serviceCall) {


        //Log.e("JSON OBJECT-",id);
        Call<BaseModel<FirmDTO>> call = VyapariApp.getApiService().deleteFirm(id);
        new ServiceManager<BaseModel<FirmDTO>>().ServiceCall(call, new ServiceCall<BaseModel<FirmDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<FirmDTO> response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
                //serviceCall.onFailure(true, throwable);
            }
        });
    }

    private static void deleteFirmSQLite(String id, ServiceCall<BaseModel<FirmDTO>> serviceCall) {
        //VyapariApp.getDbBridge().deleteFirm(id, serviceCall);
    }

    public static synchronized void deleteAddress(String id, String firmId, ServiceCall<BaseModel<String>> serviceCall) {
        serviceCall.start();
        deleteAddressCall(id, firmId, serviceCall);
    }

    private static synchronized void deleteAddressCall(String id, String firmId, final ServiceCall<BaseModel<String>> serviceCall) {

        //Log.e("JSON OBJECT-",id +" Firm id "+firmId);
        Call<BaseModel<String>> call = VyapariApp.getApiService().deleteAddress(id, firmId);
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

    private static void deleteAddressSQLite(String id, ServiceCall<BaseModel<String>> serviceCall) {
    }

    public static synchronized void deleteEmployee(String id, ServiceCall<BaseModel<String>> serviceCall) {
        serviceCall.start();
        deleteEmployeeCall(id, serviceCall);
    }

    private static synchronized void deleteEmployeeCall(String id, final ServiceCall<BaseModel<String>> serviceCall) {

        //Log.e("JSON OBJECT-",id);
        Call<BaseModel<String>> call = VyapariApp.getApiService().deleteEmployee(id);
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

    private static void deleteEmployeeSQLite(String id, ServiceCall<BaseModel<String>> serviceCall) {
    }
}
