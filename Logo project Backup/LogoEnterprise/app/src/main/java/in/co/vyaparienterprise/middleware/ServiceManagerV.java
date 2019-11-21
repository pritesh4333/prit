package in.co.vyaparienterprise.middleware;

import android.content.Intent;

import in.co.vyaparienterprise.VyapariApp;
import in.co.vyaparienterprise.constant.Constants;
import in.co.vyaparienterprise.constant.MobileConstants;
import in.co.vyaparienterprise.middleware.listener.ServiceCallV;
import in.co.vyaparienterprise.model.response.ErrorModel;
import in.co.vyaparienterprise.ui.activity.app.SplashActivity;
import in.co.vyaparienterprise.util.ErrorUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bekirdursun on 3.02.2018.
 */

public class ServiceManagerV<T> {

    private String key = null, value = null;
    private boolean queueSuccess;

    public void ServiceCall(Call<T> call, final ServiceCallV<T> serviceCall) {
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
                    ErrorModel error = new ErrorModel();
                    try {
                        error = ErrorUtils.parseError(response);
                    } catch (Exception e) {
                        error.setMessage(e.getMessage());
                    }
                    if (error.getCode() == Constants.UNAUTHORIZED && !MobileConstants.isLoginService) {
                        Intent i = new Intent(VyapariApp.getInstance().getBaseContext(), SplashActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        VyapariApp.getInstance().getBaseContext().startActivity(i);
                    }
                    serviceCall.onFailure(true, error);
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable throwable) {
                if (queueSuccess) {
                    VyapariApp.getServiceQueue().removeService(key, value);
                }
                ErrorModel error = new ErrorModel(throwable.getMessage());
                serviceCall.onFailure(true, error);
            }
        });
    }
}