package in.co.vyaparienterprise.middleware.listener;

/**
 * Created by bekirdursun on 2.02.2018.
 */

public abstract class ServiceCall<T> implements IServiceCall<T> {

    public abstract void onResponse(boolean isOnline, T response);

    public abstract void onFailure(boolean isOnline, Throwable throwable);

    @Override
    public void start() {
    }

    public void onResponse(T response) {
        onResponse(true, response);
    }

    public void onFailure(Throwable throwable) {
        onFailure(true, throwable);
    }
}