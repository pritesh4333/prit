package in.co.vyaparienterprise.middleware.listener;

import in.co.vyaparienterprise.model.response.ErrorModel;

/**
 * Created by bekirdursun on 2.02.2018.
 */

public abstract class ServiceCallV<T> implements IServiceCallV<T> {

    public abstract void onResponse(boolean isOnline, T response);

    public abstract void onFailure(boolean isOnline, ErrorModel error);

    @Override
    public void start() {
    }

    public void onResponse(T response) {
        onResponse(true, response);
    }

    public void onFailure(ErrorModel error) {
        onFailure(true, error);
    }
}