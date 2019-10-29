package in.co.vyapari.util;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Bekir.Dursun on 6.11.2017.
 */

public class DataUtil {

    public static <T> T getBundleAndRemove(Class<T> eventType) {
        T object = EventBus.getDefault().getStickyEvent(eventType);
        EventBus.getDefault().removeStickyEvent(eventType);
        return object;
    }

    public static <T> T getBundle(Class<T> eventType) {
        return EventBus.getDefault().getStickyEvent(eventType);
    }

    public static void post(Object object) {
        EventBus.getDefault().postSticky(object);
    }
}
