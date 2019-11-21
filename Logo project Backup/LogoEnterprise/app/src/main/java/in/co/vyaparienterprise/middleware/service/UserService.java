package in.co.vyaparienterprise.middleware.service;

import android.util.Log;

import com.google.gson.Gson;

import in.co.vyaparienterprise.VyapariApp;
import in.co.vyaparienterprise.middleware.ServiceManager;
import in.co.vyaparienterprise.middleware.ServiceManagerV;
import in.co.vyaparienterprise.middleware.listener.ServiceCall;
import in.co.vyaparienterprise.middleware.listener.ServiceCallV;
import in.co.vyaparienterprise.model.Company;
import in.co.vyaparienterprise.model.Login;
import in.co.vyaparienterprise.model.Register;
import in.co.vyaparienterprise.model.request.ImageUpload;
import in.co.vyaparienterprise.model.response.BaseModel;
import in.co.vyaparienterprise.model.response.BaseModelV;
import in.co.vyaparienterprise.model.response.ErrorModel;
import in.co.vyaparienterprise.model.response.dto.CompanyDTO;
import in.co.vyaparienterprise.model.response.dto.LoginDTO;
import retrofit2.Call;


/**
 * Created by bekirdursun on 2.02.2018.
 */

public class UserService {

    public static synchronized void doLogin(final Login login, ServiceCall<BaseModel<LoginDTO>> serviceCall) {
        serviceCall.start();
        doLoginCall(login, serviceCall);
    }

    private static synchronized void doLoginCall(final Login login, final ServiceCall<BaseModel<LoginDTO>> serviceCall) {
        Gson gson = new Gson();
        String jsn= gson.toJson(login);
        Log.e("JSON OBJECT-",jsn);
        Call<BaseModel<LoginDTO>> call = VyapariApp.getApiService().doLogin(login);
        new ServiceManager<BaseModel<LoginDTO>>().ServiceCall(call, new ServiceCall<BaseModel<LoginDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<LoginDTO> response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
            }
        });
    }

    private static void doLoginCallSQLite(Login login, ServiceCall<BaseModel<LoginDTO>> serviceCall) {
    }


    public static synchronized void doRegister(final Register register, ServiceCallV<BaseModelV<String>> serviceCall) {
        serviceCall.start();
        doRegisterCall(register, serviceCall);
    }

    private static synchronized void doRegisterCall(final Register register, final ServiceCallV<BaseModelV<String>> serviceCall) {
        Gson gson = new Gson();

        String jsn= gson.toJson(register);
        Log.e("JSON OBJECT-",jsn);
        Call<BaseModelV<String>> call = VyapariApp.getPortalApiService().doRegister(register);
        new ServiceManagerV<BaseModelV<String>>().ServiceCall(call, new ServiceCallV<BaseModelV<String>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModelV<String> response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, ErrorModel error) {
                serviceCall.onFailure(true, error);

            }
        });
    }

    public static synchronized void getUsageContract(ServiceCall<BaseModelV<String>> serviceCall) {
        serviceCall.start();
        getUsageContractCall(serviceCall);
    }

    private static synchronized void getUsageContractCall(final ServiceCall<BaseModelV<String>> serviceCall) {
        Call<BaseModelV<String>> call = VyapariApp.getVyapariApiService().getUsageContract();
        new ServiceManager<BaseModelV<String>>().ServiceCall(call, new ServiceCall<BaseModelV<String>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModelV<String> response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(true, throwable);
            }
        });
    }

    public static synchronized void updateCompany(Company companyRequest, ServiceCall<BaseModel<CompanyDTO>> serviceCall) {
        serviceCall.start();
        updateCompanyCall(companyRequest, serviceCall);
    }

    private static synchronized void updateCompanyCall(Company companyRequest, final ServiceCall<BaseModel<CompanyDTO>> serviceCall) {
        Gson gson = new Gson();
        String jsn= gson.toJson(companyRequest);
        Log.e("JSON OBJECT-",jsn);
        Call<BaseModel<CompanyDTO>> call = VyapariApp.getApiService().updateCompany(companyRequest);
        new ServiceManager<BaseModel<CompanyDTO>>().ServiceCall(call, new ServiceCall<BaseModel<CompanyDTO>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<CompanyDTO> response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(false, throwable);
            }
        });
    }

    private static void updateCompanySQLite(Company companyRequest, ServiceCall<BaseModel<CompanyDTO>> serviceCall) {
    }


    public static synchronized void uploadLogo(ImageUpload imageUpload, ServiceCall<BaseModel<String>> serviceCall) {
        serviceCall.start();
        uploadLogoCall(imageUpload, serviceCall);
    }

    private static synchronized void uploadLogoCall(ImageUpload imageUpload, final ServiceCall<BaseModel<String>> serviceCall) {
        Gson gson = new Gson();
        String jsn= gson.toJson(imageUpload);
        Log.e("JSON OBJECT-",jsn);
        Call<BaseModel<String>> call = VyapariApp.getApiService().uploadLogo(imageUpload);
        new ServiceManager<BaseModel<String>>().ServiceCall(call, new ServiceCall<BaseModel<String>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<String> response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(false, throwable);
            }
        });
    }

    private static void uploadLogoSQLite(ImageUpload imageUpload, ServiceCall<BaseModel<String>> serviceCall) {
    }


    public static synchronized void removeLogo(ServiceCall<BaseModel<String>> serviceCall) {
        serviceCall.start();
        removeLogoCall(serviceCall);
    }

    private static synchronized void removeLogoCall(final ServiceCall<BaseModel<String>> serviceCall) {
        Call<BaseModel<String>> call = VyapariApp.getApiService().removeLogo();
        new ServiceManager<BaseModel<String>>().ServiceCall(call, new ServiceCall<BaseModel<String>>() {
            @Override
            public void onResponse(boolean isOnline, BaseModel<String> response) {
                serviceCall.onResponse(true, response);
            }

            @Override
            public void onFailure(boolean isOnline, Throwable throwable) {
                serviceCall.onFailure(false, throwable);
            }
        });
    }

    private static void removeLogoSQLite(ImageUpload imageUpload, ServiceCall<BaseModel<String>> serviceCall) {
    }
}