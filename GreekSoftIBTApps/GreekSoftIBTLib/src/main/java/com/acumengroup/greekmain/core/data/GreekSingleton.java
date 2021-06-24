package com.acumengroup.greekmain.core.data;

import android.content.Context;

import com.acumengroup.greekmain.util.Util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class GreekSingleton {

    public static void storeObj(Context context, String className, Serializable obj) {
        // Storing object to preferences. key - class name

        try {

            Util.getPrefs(context).edit().putString(className, toString(obj)).commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object getObj(Context context, String className) {
        // Fetching object from preferences.
        try {
            String serialized = Util.getPrefs(context).getString(className, null);
            if (serialized != null) return fromString(serialized);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Read the object from Base64 string.
     */
    private static Object fromString(String s) throws IOException, ClassNotFoundException {
        byte[] data = Base64Coder.decode(s);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        Object o = ois.readObject();
        ois.close();
        return o;
    }

    /**
     * Write the object to a Base64 string.
     */
    private static String toString(Serializable o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.close();
        return new String(Base64Coder.encode(baos.toByteArray()));
    }

}
