package in.co.vyapari.middleware;

import in.co.vyapari.VyapariApp;
import in.co.vyapari.middleware.listener.ServiceCall;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bekirdursun on 3.02.2018.
 */

public class ServiceManager<T> {

    private String key = null, value = null;
    private boolean queueSuccess;

    public void ServiceCall(Call<T> call, final ServiceCall<T> serviceCall) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        if (stackTraceElements != null) {
            try {
                StackTraceElement s = stackTraceElements[5];
                if (s != null && s.getClassName().contains(VyapariApp.getInstance().getPackageName())) {
                    key = s.getFileName();
                    value = s.getMethodName();
                    VyapariApp.getServiceQueue().addService(key, value);
                    queueSuccess = true;
                } else {
                    queueSuccess = false;
                }
            } catch (Exception ignored) {
                queueSuccess = false;
            }
        } else {
            queueSuccess = false;
        }

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (queueSuccess) {
                    VyapariApp.getServiceQueue().removeService(key, value);
                }
                if (response.isSuccessful()) {
                    serviceCall.onResponse(true, response.body());
                } else {
                    serviceCall.onFailure(true, null);
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable throwable) {
                if (queueSuccess) {
                    VyapariApp.getServiceQueue().removeService(key, value);
                }
                serviceCall.onFailure(true, throwable);
            }
        });
    }
}