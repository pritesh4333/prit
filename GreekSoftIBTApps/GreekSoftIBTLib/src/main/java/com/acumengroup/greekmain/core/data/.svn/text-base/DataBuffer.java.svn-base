package com.acumengroup.greekmain.core.data;

import android.content.Context;
import android.util.Log;

import com.acumengroup.greekmain.util.logger.GreekLog;

import java.io.Serializable;
import java.util.Hashtable;

public class DataBuffer implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static final String key = "DataBuffer";
    private static DataBuffer currentObj;
    private static Context context;
    // To determine, whether we need to write this object in to Preference.
    private boolean hasDirtyData = false;
    private Hashtable<String, Object> storage;

    private DataBuffer() {
        storage = new Hashtable<String, Object>();
    }

    public static DataBuffer getInstance(Context c) {
        context = c;
        if (currentObj == null) {
            GreekLog.msg("DataBuffer Created");
            DataBuffer obj = (DataBuffer) GreekSingleton.getObj(c, key);
            if (obj != null) {
                currentObj = obj;
            } else {
                currentObj = new DataBuffer();
            }
        }
        return currentObj;
    }

    public void put(final String key, final Object value) {
        storage.put(key, value);
        hasDirtyData = true;
    }

    public Object get(final String key) {
        if (storage.containsKey(key)) return storage.get(key);
        else return null;
    }

    public boolean contains(final String key) {
        return storage.containsKey(key);
    }

    public void clearKeyValuepair(final String key) {
        if (storage.containsKey(key)) storage.remove(key);
        hasDirtyData = true;
    }

    /**
     * Call this method on every Logout and Application exit event.
     */
    public void clearCache() {
        Log.d("cache", "Clear Cache==== clearrrrrrrr saved!.");
        currentObj = null;
        storage.clear();
        hasDirtyData = true;
        persist();
    }

    public void persist() {
        if (hasDirtyData) {
            GreekSingleton.storeObj(context, key, currentObj);
            GreekLog.msg("Persist==== DataBuffer saved!.");
            hasDirtyData = false;
        } else GreekLog.msg("Persist==== DataBuffer does not hold any dirty data...");
    }

}
