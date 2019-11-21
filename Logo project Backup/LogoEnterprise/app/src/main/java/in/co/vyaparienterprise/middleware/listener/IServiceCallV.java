package in.co.vyaparienterprise.middleware.listener;

import in.co.vyaparienterprise.model.response.ErrorModel;

/**
 * Created by bekirdursun on 2.02.2018.
 */

public interface IServiceCallV<T> {
    void start();

    void onResponse(T response);

    void onFailure(ErrorModel error);
}