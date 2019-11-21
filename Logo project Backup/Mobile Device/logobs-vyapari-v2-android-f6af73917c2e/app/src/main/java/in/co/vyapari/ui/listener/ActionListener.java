package in.co.vyapari.ui.listener;

/**
 * Created by bekirdursun on 22.12.2017.
 */

public abstract class ActionListener<T> implements IActionListener<T> {

    public abstract void results(boolean isPreloaded, T data);

    @Override
    public void start() {
    }

    public void results(T data) {
        results(false, data);
    }
}