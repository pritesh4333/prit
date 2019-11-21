package in.co.vyapari.ui.listener;

/**
 * Created by bekirdursun on 22.12.2017.
 */

public interface IActionListener<T> {
    void start();

    void results(boolean isPreloaded, T data);
}