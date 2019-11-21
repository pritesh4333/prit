package in.co.vyapari.middleware.service;

import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import in.co.vyapari.BuildConfig;
import in.co.vyapari.VyapariApp;
import in.co.vyapari.constant.Constants;
import in.co.vyapari.middleware.ServiceManager;
import in.co.vyapari.middleware.ServiceManagerV;
import in.co.vyapari.middleware.listener.ServiceCall;
import in.co.vyapari.middleware.listener.ServiceCallV;
import in.co.vyapari.model.KeyValue;
import in.co.vyapari.model.UpdateLogo;
import in.co.vyapari.model.UpdatePassword;
import in.co.vyapari.model.VersionControl;
import in.co.vyapari.model.request.Crash;
import in.co.vyapari.model.request.NotificationConfig;
import in.co.vyapari.model.response.BaseModel;
import in.co.vyapari.model.response.BaseModelV;
import in.co.vyapari.model.response.ErrorModel;
import in.co.vyapari.model.response.dto.GstinDTO;
import in.co.vyapari.model.response.dto.GstinForVyapariDTO;
import in.co.vyapari.model.response.dto.SectorDTO;
import in.co.vyapari.model.response.dto.UserDTO;
import in.co.vyapari.model.response.dto.promocodeDTO;
import retrofit2.Call;

/**
 * Created by bekirdursun on 5.02.2018.
 */

public class CommonService {
    //Log
    public static synchronized void addCrashLog(String crashPage, String log) {
        if (BuildConfig.DEBUG) {
            return;
        }
        ServiceCall<Boolean> serviceCall = new ServiceCall<Boolean>() {
            @Override
            public void onResponse(boolean isOnline, Boolean response) {

            }

            @Override
            public void onFailure(boolean isOnline, Throwable error) {

            }
        };
        serviceCall.start();
        Crash crash = new Crash(crashPage, log);
        addCrashLogCall(crash, serviceCall);
    }

    private static synchronized void addCrashLogCall(Crash crash, final ServiceCall<Boolean> serviceCall) {
        Call<Boolean> call = VyapariApp.getApiService().addCrashLog(crash);
        new ServiceManager<Boolean>().ServiceCall(call, new ServiceCall<Boolean>() {
            @Override
            public void onResponse(boolean isOnline, Boolean response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
                //addCrashLogSQLite(serviceCall);
            }
        });
    }

    private static void addCrashLogSQLite(ServiceCall<BaseModel<ArrayList<KeyValue>>> serviceCall) {
    }


    public static synchronized void getVersionControl(ServiceCall<BaseModel<VersionControl>> serviceCall) {
        serviceCall.start();
        getVersionControlCall(serviceCall);
    }

    private static synchronized void getVersionControlCall(final ServiceCall<BaseModel<VersionControl>> serviceCall) {

        Call<BaseModel<VersionControl>> call = VyapariApp.getApiService().getVersionControl();
        new ServiceManager<BaseModel<VersionControl>>().ServiceCall(call, new ServiceCall<BaseModel<VersionControl>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<VersionControl> response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable error) {
                serviceCall.onFailure(true, error);
            }
        });
    }

    public static synchronized void setNotificationConfig(NotificationConfig notificationConfig, ServiceCall<BaseModel<Boolean>> serviceCall) {
        serviceCall.start();
        setNotificationConfigCall(notificationConfig, serviceCall);
    }

    private static synchronized void setNotificationConfigCall(NotificationConfig notificationConfig, final ServiceCall<BaseModel<Boolean>> serviceCall) {
        Call<BaseModel<Boolean>> call = VyapariApp.getApiService().setNotificationConfig(notificationConfig);
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
    //Log

    //Firm
    public static synchronized void getWarehouses(ServiceCall<BaseModel<ArrayList<KeyValue>>> serviceCall) {
        serviceCall.start();
        //TODO Offline
        getWarehousesCall(serviceCall);
    }

    private static synchronized void getWarehousesCall(final ServiceCall<BaseModel<ArrayList<KeyValue>>> serviceCall) {
        Call<BaseModel<ArrayList<KeyValue>>> call = VyapariApp.getApiService().getWarehouses();
        new ServiceManager<BaseModel<ArrayList<KeyValue>>>().ServiceCall(call, new ServiceCall<BaseModel<ArrayList<KeyValue>>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<ArrayList<KeyValue>> response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
                //getWarehousesSQLite(serviceCall);
            }
        });
    }

    private static void getWarehousesSQLite(ServiceCall<BaseModel<ArrayList<KeyValue>>> serviceCall) {
        //VyapariApp.getDbBridge().getKVList(DbConstants.WAREHOUSES, serviceCall);
    }


    public static void validGSTIN(String gstin, ServiceCall<BaseModel<GstinDTO>> serviceCall) {
        serviceCall.start();
        validGSTINCall(gstin, serviceCall);
    }

    private static void validGSTINCall(String gstin, final ServiceCall<BaseModel<GstinDTO>> serviceCall) {

        //Log.e("JSON OBJECT-",gstin);
        Call<BaseModel<GstinDTO>> call = VyapariApp.getApiService().validGSTIN(gstin);

        new ServiceManager<BaseModel<GstinDTO>>().ServiceCall(call, new ServiceCall<BaseModel<GstinDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<GstinDTO> response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable error) {
                serviceCall.onFailure(true, error);
            }
        });
    }

    public static void validPromoUpdate(String promocode,String tententid, ServiceCallV<BaseModelV<String>> serviceCall) {
        serviceCall.start();
        validPromoUpdatecall(promocode,  tententid, serviceCall);
    }

    private static void validPromoUpdatecall(String promocode,String tententid, final ServiceCallV<BaseModelV<String>> serviceCall) {

        //Log.e("JSON OBJECT-",promocode+"id"+tententid);
        Call<BaseModelV<String>>  call = VyapariApp.getPortalApiService().validPromoCodecall(tententid,promocode);
        new ServiceManagerV<BaseModelV<String>> ().ServiceCall(call, new ServiceCallV<BaseModelV<String>> () {
            @Override
            public void onResponse(boolean isOnline, BaseModelV<String>  response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, ErrorModel error) {
                serviceCall.onFailure(true, error);
            }
        });
    }
    public static void UpdateCompanyLogo(UpdateLogo UpdateLogo,ServiceCallV<BaseModelV<String>> serviceCall) {
        serviceCall.start();
        UpdateCompanyLogocall(UpdateLogo, serviceCall);
    }

    private static void UpdateCompanyLogocall(UpdateLogo UpdateLogo, final ServiceCallV<BaseModelV<String>> serviceCall) {

        Gson gson = new Gson();
        String jsn= gson.toJson(UpdateLogo);
        //Log.e("JSON OBJECT-",jsn);
        Call<BaseModelV<String>>  call = VyapariApp.getPortalApiService().updatecompanylogo(UpdateLogo);
        new ServiceManagerV<BaseModelV<String>> ().ServiceCall(call, new ServiceCallV<BaseModelV<String>> () {
            @Override
            public void onResponse(boolean isOnline, BaseModelV<String>  response) {
                serviceCall.onResponse(true, response);

            }

            @Override
            public void onFailure(boolean isOnline,  ErrorModel error) {
                serviceCall.onFailure(true, error);
            }
        });
    }

    public static void UpdatePassword(UpdatePassword updatepass, ServiceCallV<BaseModelV<String>> serviceCall) {
        serviceCall.start();
        UpdatePasswordcall(updatepass, serviceCall);
    }

    private static void UpdatePasswordcall(UpdatePassword updatepass, final ServiceCallV<BaseModelV<String>> serviceCall) {

        Gson gson = new Gson();
        String jsn= gson.toJson(updatepass);
        //Log.e("JSON OBJECT-",jsn);

        Call<BaseModelV<String>>  call = VyapariApp.getPortalApiService().updatepassword(updatepass);
        new ServiceManagerV<BaseModelV<String>>().ServiceCall(call, new ServiceCallV<BaseModelV<String>> () {
            @Override
            public void onResponse(boolean isOnline, BaseModelV<String>  response) {
                serviceCall.onResponse(true, response);

            }

            @Override
            public void onFailure(boolean isOnline, ErrorModel error) {
                serviceCall.onFailure(true, error);
            }


        });
    }
    public static synchronized void getTokenForPortal(ServiceCall<BaseModel<KeyValue>> serviceCall) {
        serviceCall.start();
        getTokenForPortalCall( serviceCall);
    }

    private static synchronized void getTokenForPortalCall( final ServiceCall<BaseModel<KeyValue>> serviceCall) {


        Call<BaseModel< KeyValue>> call = VyapariApp.getApiService().GetPortalToken();
        new ServiceManager<BaseModel<KeyValue>>().ServiceCall(call, new ServiceCall<BaseModel<KeyValue>>() {


            @Override
            public void onResponse(boolean isOnline, BaseModel<KeyValue> response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
            }
        });
    }

    public static void getUserData(String portalToken,  ServiceCallV<BaseModelV<UserDTO>> serviceCall) {
        serviceCall.start();
        getUserDatacall(portalToken, serviceCall);
    }

    private static void getUserDatacall(final String portalToken,final ServiceCallV<BaseModelV<UserDTO>> serviceCall) {




        Call<BaseModelV<UserDTO>>  call = VyapariApp.getPortalApiService().getUserDatacall(portalToken);
        new ServiceManagerV<BaseModelV<UserDTO>> ().ServiceCall(call, new ServiceCallV<BaseModelV<UserDTO>> () {
            @Override
            public void onResponse(boolean isOnline, BaseModelV<UserDTO>  response) {
                serviceCall.onResponse(true, response);

            }

            @Override
            public void onFailure(boolean isOnline, ErrorModel throwable) {
                serviceCall.onFailure(true, throwable);
            }
        });
    }
    public static void Deactiveaccount(String tenentid, String state, ServiceCallV<BaseModelV<String>> serviceCall) {
        serviceCall.start();
        Deactiveaccountcall(tenentid ,state, serviceCall);
    }

    private static void Deactiveaccountcall(String tenentid, String state, final ServiceCallV<BaseModelV<String>> serviceCall) {




        Call<BaseModelV<String>>  call = VyapariApp.getPortalApiService().Deactiveaccount(tenentid,state);
        new ServiceManagerV<BaseModelV<String>> ().ServiceCall(call, new ServiceCallV<BaseModelV<String>> () {
            @Override
            public void onResponse(boolean isOnline, BaseModelV<String>  response) {
                serviceCall.onResponse(true, response);

            }

            @Override
            public void onFailure(boolean isOnline, ErrorModel throwable) {
                serviceCall.onFailure(true, throwable);
            }
        });
    }
    public static void validPromoCode(String promo, ServiceCall<BaseModelV<promocodeDTO>> serviceCall) {
        serviceCall.start();
        validetPromoCode(promo, serviceCall);
    }

    private static void validetPromoCode(String promo, final ServiceCall<BaseModelV<promocodeDTO>> serviceCall) {

        //Log.e("JSON OBJECT-",promo);
        Call<BaseModelV<promocodeDTO>>  call = VyapariApp.getPortalApiService().validPromoCode(promo);
        new ServiceManager<BaseModelV<promocodeDTO>> ().ServiceCall(call, new ServiceCall<BaseModelV<promocodeDTO>> () {
            @Override
            public void onResponse(boolean isOnline, BaseModelV<promocodeDTO>  response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
            }
        });
    }

    public static void validGSTINForVyapari(String gstin, ServiceCall<BaseModel<GstinForVyapariDTO>> serviceCall) {
        serviceCall.start();
        validGSTINForVyapariCall(gstin, serviceCall);
    }

    private static void validGSTINForVyapariCall(String gstin, final ServiceCall<BaseModel<GstinForVyapariDTO>> serviceCall) {

        //Log.e("JSON OBJECT-",gstin);
        Call<BaseModel<GstinForVyapariDTO>> call = VyapariApp.getApiService().validGSTINForVyapari(gstin);
        new ServiceManager<BaseModel<GstinForVyapariDTO>>().ServiceCall(call, new ServiceCall<BaseModel<GstinForVyapariDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<GstinForVyapariDTO> response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
            }
        });
    }

    public static void getSectorList(ServiceCall<BaseModelV<List<SectorDTO>>> serviceCall) {
        serviceCall.start();
        getSectorListCall(serviceCall);
    }

    private static void getSectorListCall(final ServiceCall<BaseModelV<List<SectorDTO>>> serviceCall) {
        Call<BaseModelV<List<SectorDTO>>> call = VyapariApp.getPortalApiService().getSectorList();
        new ServiceManager<BaseModelV<List<SectorDTO>>>().ServiceCall(call, new ServiceCall<BaseModelV<List<SectorDTO>>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModelV<List<SectorDTO>> response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
            }
        });
    }
    //Firm

    //Location
    public static synchronized void getCountries(ServiceCall<BaseModel<ArrayList<KeyValue>>> serviceCall) {
        serviceCall.start();
        getCountriesCall(serviceCall);
    }

    private static synchronized void getCountriesCall(final ServiceCall<BaseModel<ArrayList<KeyValue>>> serviceCall) {
        Call<BaseModel<ArrayList<KeyValue>>> call = VyapariApp.getApiService().getCountries();
        new ServiceManager<BaseModel<ArrayList<KeyValue>>>().ServiceCall(call, new ServiceCall<BaseModel<ArrayList<KeyValue>>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<ArrayList<KeyValue>> response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
                //getCountriesSQLite(serviceCall);
            }
        });
    }

    private static void getCountriesSQLite(ServiceCall<BaseModel<ArrayList<KeyValue>>> serviceCall) {
        //VyapariApp.getDbBridge().getKVList(DbConstants.COUNTRIES, serviceCall);
    }


    public static synchronized void getStates(String parentId, ServiceCall<BaseModel<ArrayList<KeyValue>>> serviceCall) {
        serviceCall.start();
        getStatesCall(parentId, serviceCall);
    }

    private static void getStatesCall(final String parentId, final ServiceCall<BaseModel<ArrayList<KeyValue>>> serviceCall) {
        Call<BaseModel<ArrayList<KeyValue>>> call = VyapariApp.getApiService().getStates(parentId);
        new ServiceManager<BaseModel<ArrayList<KeyValue>>>().ServiceCall(call, new ServiceCall<BaseModel<ArrayList<KeyValue>>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<ArrayList<KeyValue>> response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
                //getStatesSQLite(parentId, serviceCall);
            }
        });
    }

    private static void getStatesSQLite(String parentId, ServiceCall<BaseModel<ArrayList<KeyValue>>> serviceCall) {
        //VyapariApp.getDbBridge().getKVList(DbConstants.STATES, null, parentId, serviceCall);
    }


    public static synchronized void getCities(String parentId, ServiceCall<BaseModel<ArrayList<KeyValue>>> serviceCall) {
        serviceCall.start();
        getCitiesCall(parentId, serviceCall);
    }

    private static synchronized void getCitiesCall(final String parentId, final ServiceCall<BaseModel<ArrayList<KeyValue>>> serviceCall) {
        Call<BaseModel<ArrayList<KeyValue>>> call = VyapariApp.getApiService().getCities(parentId);
        new ServiceManager<BaseModel<ArrayList<KeyValue>>>().ServiceCall(call, new ServiceCall<BaseModel<ArrayList<KeyValue>>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<ArrayList<KeyValue>> response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
                //getCitiesSQLite(parentId, serviceCall);
            }
        });
    }

    private static void getCitiesSQLite(String parentId, ServiceCall<BaseModel<ArrayList<KeyValue>>> serviceCall) {
        //VyapariApp.getDbBridge().getKVList(DbConstants.CITIES, null, parentId, serviceCall);
    }


    public static synchronized void getDistricts(String parentId, ServiceCall<BaseModel<ArrayList<KeyValue>>> serviceCall) {
        serviceCall.start();
        getDistrictsCall(parentId, serviceCall);
    }

    private static synchronized void getDistrictsCall(final String parentId, final ServiceCall<BaseModel<ArrayList<KeyValue>>> serviceCall) {
        Call<BaseModel<ArrayList<KeyValue>>> call = VyapariApp.getApiService().getDistricts(parentId);
        new ServiceManager<BaseModel<ArrayList<KeyValue>>>().ServiceCall(call, new ServiceCall<BaseModel<ArrayList<KeyValue>>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<ArrayList<KeyValue>> response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
                //getDistrictsSQLite(parentId, serviceCall);
            }
        });
    }

    private static void getDistrictsSQLite(String parentId, ServiceCall<BaseModel<ArrayList<KeyValue>>> serviceCall) {
        //VyapariApp.getDbBridge().getKVList(DbConstants.DISTRICT, null, parentId, serviceCall);
    }
    //Location

    //Product
    public static synchronized void getProductTypes(ServiceCall<BaseModel<ArrayList<KeyValue>>> serviceCall) {
        serviceCall.start();
        getProductTypesCall(serviceCall);
    }

    private static synchronized void getProductTypesCall(final ServiceCall<BaseModel<ArrayList<KeyValue>>> serviceCall) {
        Call<BaseModel<ArrayList<KeyValue>>> call = VyapariApp.getApiService().getProductTypes();
        new ServiceManager<BaseModel<ArrayList<KeyValue>>>().ServiceCall(call, new ServiceCall<BaseModel<ArrayList<KeyValue>>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<ArrayList<KeyValue>> response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
                //getProductTypesSQLite(serviceCall);
            }
        });
    }

    private static void getProductTypesSQLite(ServiceCall<BaseModel<ArrayList<KeyValue>>> serviceCall) {
        //VyapariApp.getDbBridge().getKVList(DbConstants.PRODUCT_TYPES, serviceCall);
    }


    public static synchronized void getHSNSACCodes(String productType, String searchText, ServiceCall<BaseModel<ArrayList<KeyValue>>> serviceCall) {
        serviceCall.start();
        getHSNSACCodesCall(productType, searchText, serviceCall);
    }

    private static synchronized void getHSNSACCodesCall(String productType, final String searchText, final ServiceCall<BaseModel<ArrayList<KeyValue>>> serviceCall) {
        Call<BaseModel<ArrayList<KeyValue>>> call = VyapariApp.getApiService().getHSNSACCodes(productType, searchText);
        new ServiceManager<BaseModel<ArrayList<KeyValue>>>().ServiceCall(call, new ServiceCall<BaseModel<ArrayList<KeyValue>>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<ArrayList<KeyValue>> response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
                //getHSNSACCodesSQLite(searchText, serviceCall);
            }
        });
    }

    private static void getHSNSACCodesSQLite(String searchText, ServiceCall<BaseModel<ArrayList<KeyValue>>> serviceCall) {
        //VyapariApp.getDbBridge().getKVList(DbConstants.HSNSAC, searchText, serviceCall);
    }


    public static synchronized void getCESSList(ServiceCall<BaseModel<ArrayList<KeyValue>>> serviceCall) {
        serviceCall.start();
        getCESSCall(serviceCall);
    }

    private static synchronized void getCESSCall(final ServiceCall<BaseModel<ArrayList<KeyValue>>> serviceCall) {
        Call<BaseModel<ArrayList<KeyValue>>> call = VyapariApp.getApiService().getCESS();
        new ServiceManager<BaseModel<ArrayList<KeyValue>>>().ServiceCall(call, new ServiceCall<BaseModel<ArrayList<KeyValue>>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<ArrayList<KeyValue>> response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
                //getCESSSQLite(serviceCall);
            }
        });
    }

    private static void getCESSSQLite(ServiceCall<BaseModel<ArrayList<KeyValue>>> serviceCall) {
        //VyapariApp.getDbBridge().getKVList(DbConstants.CESS, serviceCall);
    }


    public static synchronized void getGSTList(ServiceCall<BaseModel<ArrayList<KeyValue>>> serviceCall) {
        serviceCall.start();
        getGSTListCall(serviceCall);
    }

    private static synchronized void getGSTListCall(final ServiceCall<BaseModel<ArrayList<KeyValue>>> serviceCall) {
        Call<BaseModel<ArrayList<KeyValue>>> call = VyapariApp.getApiService().getGSTCodes();
        new ServiceManager<BaseModel<ArrayList<KeyValue>>>().ServiceCall(call, new ServiceCall<BaseModel<ArrayList<KeyValue>>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<ArrayList<KeyValue>> response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
                //getGSTListSQLite(serviceCall);
            }
        });
    }

    private static void getGSTListSQLite(ServiceCall<BaseModel<ArrayList<KeyValue>>> serviceCall) {
        //VyapariApp.getDbBridge().getKVList(DbConstants.GST, serviceCall);
    }


    public static synchronized void getUnitTypes(ServiceCall<BaseModel<ArrayList<KeyValue>>> serviceCall) {
        serviceCall.start();
        getUnitTypesCall(serviceCall);
    }

    private static void getUnitTypesCall(final ServiceCall<BaseModel<ArrayList<KeyValue>>> serviceCall) {
        Call<BaseModel<ArrayList<KeyValue>>> call = VyapariApp.getApiService().getUnitTypes();
        new ServiceManager<BaseModel<ArrayList<KeyValue>>>().ServiceCall(call, new ServiceCall<BaseModel<ArrayList<KeyValue>>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<ArrayList<KeyValue>> response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
                //getUnitTypesSQLite(serviceCall);
            }
        });
    }

    public static synchronized void getSubUnitTypes(String unitKey, ServiceCall<BaseModel<ArrayList<KeyValue>>> serviceCall) {
        serviceCall.start();
        getSubUnitTypesCall(unitKey, serviceCall);
    }

    private static void getSubUnitTypesCall(String unitKey, final ServiceCall<BaseModel<ArrayList<KeyValue>>> serviceCall) {
        Call<BaseModel<ArrayList<KeyValue>>> call = VyapariApp.getApiService().getSubUnitTypes(unitKey);
        new ServiceManager<BaseModel<ArrayList<KeyValue>>>().ServiceCall(call, new ServiceCall<BaseModel<ArrayList<KeyValue>>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<ArrayList<KeyValue>> response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
            }
        });
    }
    //Product

    //Invoice
    public static synchronized void getInvoiceNumber(int type, ServiceCall<BaseModel<String>> serviceCall) {
        serviceCall.start();
        getInvoiceNumberCall(type, serviceCall);
    }

    private static synchronized void getInvoiceNumberCall(int type, final ServiceCall<BaseModel<String>> serviceCall) {
        Call<BaseModel<String>> call = null;
        switch (type) {
            case Constants.SALES_INVOICE:
                call = VyapariApp.getApiService().getInvoiceNumber();
                break;
            case Constants.PURCHASE_INVOICE:
                call = VyapariApp.getApiService().getPurchaseInvoiceNumber();
                break;
        }
        if (call == null) {
            serviceCall.onFailure(true, null);
            return;
        }
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
    //Invoice
}