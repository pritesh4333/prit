package in.co.vyaparienterprise.middleware.listener;

/**
 * Created by bekirdursun on 2.02.2018.
 */

public interface IServiceCall<T> {
    void start();

    void onResponse(T response);

    void onFailure(Throwable ignored);
}