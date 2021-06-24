package com.acumengroup.greekmain.core.parser;

import android.content.Context;


import com.acumengroup.greekmain.util.logger.GreekLog;
import com.acumengroup.greekmain.util.operation.GreekHashtable;
import com.acumengroup.mobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Created by Arcadia
 * <br>
 * <p/>
 * Edited Rinoy <br>
 * <br>
 */
public class GreekConfig {

    public static final String INFO = "info";
    public static final String APP = "app";
    public static final String MENU = "menu";
    public static final String LABEL = "label";
    public static final String VERSIONDETAIL = "versionDetail";

    public static final String MENU_DISPLAYORDER = "displayOrder";
    public static final String MENU_PARENT = "parent";

    public static final String CONFIG = "config";
    public static final String TIME_STAMP = "tstamp";
    public static final String DATA = "data";
    public static final String RESPONSE = "response";
    public static final String KEY = "key";
    public static final String VALUE = "value";

    static GreekHashtable configHashMap = new GreekHashtable();
    static LinkedHashMap<String, ArrayList<MenuDetails>> menuMap = new LinkedHashMap<>();
    static LinkedHashMap<String, MenuDetails> menuDetails = new LinkedHashMap<>();

    /**
     * Parses the data from inputstream and stores in respective config files
     * and configMap
     *
     * @param data
     * @param ctx
     * @throws IOException
     */
    public static void writeResponse(String data, Context ctx) {
        try {
            readConfig(ctx, data);

            for (String configName : getConfigNames(ctx)) {
                writeToFile(configName, (GreekHashtable) configHashMap.get(configName), ctx);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @param files
     * @return JSONObject to form the request.
     * <p/>
     * This creates a config JSONObject without comparing with the old
     * data.
     */
    public static JSONObject getConfigRequestJsonObject(Context context, String files[]) {
        JSONObject configJsonObject = new JSONObject();
        try {
            for (String configName : getConfigNames(context)) {
                // By default config timestamp is Zero, if it doesn't have any
                // data in file
                configJsonObject.put(configName, "0");
                // Check files exists or not
                for (String currentFileName : files) {
                    if (currentFileName.startsWith(configName + "-")) {
                        // fileName = ConfigName + "-" + timestamp
                        // Split the file name and get the timestampe alone
                        configJsonObject.put(configName, currentFileName.split("-")[1]);
                        break;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return configJsonObject;
    }

    /**
     * @param newJsonObject      - current config got from platform
     * @param existingJsonObject - local config
     * @return
     */
    public static JSONObject getConfigRequestJsonObject(Context context, JSONObject newJsonObject, JSONObject existingJsonObject) {
        JSONObject configJsonObject = new JSONObject();
        try {
            for (String configName : getConfigNames(context)) {

                if ((!newJsonObject.has(configName)) || (!existingJsonObject.has(configName)))
                    continue;

                String newTimeStamp = newJsonObject.getString(configName);
                String oldTimeStamp = existingJsonObject.getString(configName);

                // Compare old and new time stamp, if it differs, send the old
                // time stamp in request and get the updated data in config
                // response
                if (!newTimeStamp.equalsIgnoreCase(oldTimeStamp)) {
                    configJsonObject.put(configName, oldTimeStamp);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return configJsonObject;
    }

    /**
     * @param context Set the config Names <br>
     *                Ex:- App <br>
     *                label <br>
     *                menu <br>
     *                info <br>
     */
    /*public static void addConfigs(final List<String> configName) {
        configNames = configName;
	}*/
    private static String[] getConfigNames(Context context) {
        return context.getResources().getStringArray(R.array.CONFIG_NAMES);
    }

    /**
     * Write the given json content to the given file Append the timestamp along
     * with the file name So while sending the request, we don't need to read
     * the file to get the timestamp, just filename is enough.
     *
     * @param configName
     * @param content
     * @param ctx
     * @throws IOException
     */
    private static void writeToFile(String configName, GreekHashtable content, Context ctx) throws IOException {
        if (content != null) {

            String timeStamp = "0";
            if (content.containsKey(TIME_STAMP)) timeStamp = "" + content.get(TIME_STAMP);

            // fileName = ConfigName + "-" + timestamp
            // Ex:- app-230
            String fileName = configName + "-" + timeStamp;

            FileOutputStream out = ctx.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream oStream = new ObjectOutputStream(out);

            oStream.writeObject(content);
            oStream.flush();
            oStream.close();

        }
    }

    /**
     * @param context
     * @param data
     * @throws Exception <br>
     *                   <br>
     *                   Read config file
     */
    public static void readConfig(Context context, String data) throws Exception {

        GreekLog.msg("Config Response=" + data);

        JSONObject jo = new JSONObject(data);

        // Get response from full
        jo = jo.optJSONObject(RESPONSE);
        jo = jo.optJSONObject(DATA);

        String respConfigNames[] = new String[jo.length()];

        int index = 0;
        Iterator itr = jo.keys();
        while (itr.hasNext()) {
            respConfigNames[index] = (String) itr.next();
            index++;
        }

        String files[] = context.fileList();

        for (String configName : respConfigNames) {

            GreekHashtable dataHash = new GreekHashtable();

            String fileName = null;

            // Check files exists or not
            for (String currentFileName : files) {

                if (currentFileName.startsWith(configName + "-")) {
                    // If exists, read the file
                    FileInputStream inStream = context.openFileInput(currentFileName);

                    if (inStream.available() != 0) {

                        ObjectInputStream oStream = new ObjectInputStream(inStream);

                        try {
                            // Get the saved data from file.
                            dataHash = (GreekHashtable) oStream.readObject();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        configHashMap.put(configName, dataHash);

                    }

                    fileName = currentFileName;
                    break;
                }
            }

            JSONObject configObj = jo.optJSONObject(configName);
            if (configObj != null) {

                // For App config - Delete old data and add it newly. No replace
                // or update.
                if (configName.equalsIgnoreCase(APP) && configObj.has(CONFIG)) {

                    JSONArray jsonArray = (JSONArray) configObj.get(CONFIG);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject json_data = jsonArray.getJSONObject(i);
                        String key = json_data.getString(KEY);
                        if (dataHash.containsKey(CONFIG)) {

                            if (((GreekHashtable) dataHash.get(CONFIG)).containsKey(key)) {
                                ((GreekHashtable) dataHash.get(CONFIG)).remove(key);
                            }
                        }
                    }
                }

                // handle the new data and merge with old data. Save the merged
                // data in config map.
                configHashMap.put(configName, merge(dataHash, handleJSONObject(configObj)));

                // Delete the existing file. Gonna create new file with merged
                // data(old & new).
                if (fileName != null) context.deleteFile(fileName);

            }
        }
    }

    public static Object getAppConfigData(Context context, String key) {
        try {
            return GreekConfig.getParam(context, GreekConfig.APP + "/" + GreekConfig.CONFIG + "/" + key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static LinkedHashMap<String, ArrayList<MenuDetails>> handleMenuConfig(Context context) {
        try {
            Hashtable hashtable = (Hashtable) getParam(context, MENU);
            hashtable = (Hashtable) hashtable.get(CONFIG);
            menuMap.clear();
            menuDetails.clear();
            Enumeration e = hashtable.keys();
            while (e.hasMoreElements()) {
                String key = (String) e.nextElement();
                Hashtable values = (Hashtable) hashtable.get(key);
                MenuDetails details = new MenuDetails();
                details.setKey(key);
                details.setDisplayOrder((Integer) values.get(MENU_DISPLAYORDER));
                String parent = (String) values.get(MENU_PARENT);
                details.setParent(parent);

                menuDetails.put(key, details);

                if (key.equalsIgnoreCase("RT")) continue;

                if (menuMap.containsKey(parent)) {
                    ArrayList<MenuDetails> menuDetails = menuMap.get(parent);
                    menuDetails.add(details);

                    Collections.sort(menuDetails, new Comparator<MenuDetails>() {

                        @Override
                        public int compare(MenuDetails lhs, MenuDetails rhs) {
                            return lhs.getDisplayOrder() - rhs.getDisplayOrder();

                        }
                    });

                    menuMap.put(parent, menuDetails);
                } else {
                    // Add newly
                    ArrayList<MenuDetails> menuDetails = new ArrayList<>();
                    menuDetails.add(details);
                    menuMap.put(parent, menuDetails);
                }

            }
            return menuMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<MenuDetails> getGroupMenuDetails(Context context, String key) {
        if (menuMap == null || (!menuMap.containsKey(key))) {
            try {
                handleMenuConfig(context);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return menuMap.get(key);
    }

    /**
     * @param context
     * @param key
     * @return MenuDetails Get MenuDetails like parent, key & displayOrder
     */
    public static MenuDetails getMenuDetails(Context context, String key) {
        if (menuDetails == null || (!menuDetails.containsKey(key))) {
            try {
                handleMenuConfig(context);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return menuDetails.get(key);
    }

    /**
     * Merge two hashtables.
     *
     * @param oldMap
     * @param newMap
     * @return
     */
    public static GreekHashtable merge(GreekHashtable oldMap, GreekHashtable newMap) {
        Enumeration e = newMap.keys();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            Object oldValue = oldMap.get(key);
            Object newValue = newMap.get(key);
            if (oldValue instanceof GreekHashtable && newValue instanceof GreekHashtable) {
                merge((GreekHashtable) oldValue, (GreekHashtable) newValue);
            } else {
                oldMap.put(key, newValue);
            }
        }
        return oldMap;
    }

    /**
     * Handle Json Array.
     *
     * @param dataArray
     * @param dataValuesHash
     * @return
     * @throws Exception
     */
    private static GreekHashtable handleJSONArray(JSONArray dataArray, GreekHashtable dataValuesHash) throws Exception {
        for (int i = 0; i < dataArray.length(); i++) {
            JSONObject json_data = dataArray.getJSONObject(i);
            String key = json_data.getString(KEY);
            Object data = json_data.get(VALUE);
            if (data instanceof JSONObject) {
                dataValuesHash.put(key, handleJSONObject((JSONObject) data));
            } else if (data instanceof JSONArray) {
                JSONArray currArray = (JSONArray) data;
                if (currArray.length() > 0) {
                    if (currArray.get(0) instanceof JSONObject) {
                        dataValuesHash.put(key, handleJSONArray(currArray, new GreekHashtable()));
                    } else if (currArray.get(0) instanceof String) {
                        ArrayList<String> values = new ArrayList<>();
                        for (int j = 0; j < currArray.length(); j++) {
                            values.add(currArray.getString(j));
                        }
                        dataValuesHash.put(key, values);
                    } else if (currArray.get(0) instanceof Integer) {
                        ArrayList<Integer> values = new ArrayList<>();
                        for (int j = 0; j < currArray.length(); j++) {
                            values.add(currArray.getInt(j));
                        }
                        dataValuesHash.put(key, values);
                    }
                }

            } else {
                dataValuesHash.put(key, data);
            }

        }
        return dataValuesHash;
    }

    /**
     * Handle Json Object
     *
     * @param dataObject
     * @return
     * @throws Exception
     */
    private static GreekHashtable handleJSONObject(JSONObject dataObject) throws Exception {
        GreekHashtable dataValuesHash = new GreekHashtable();
        Iterator iterator = dataObject.keys();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            Object data = dataObject.get(key);

            if (data instanceof JSONObject) {
                dataValuesHash.put(key, handleJSONObject(dataObject.optJSONObject(key)));
            } else if (data instanceof JSONArray) {
                dataValuesHash.put(key, handleJSONArray(dataObject.optJSONArray(key), new GreekHashtable()));
            } else {
                dataValuesHash.put(key, dataObject.get(key));
            }

        }
        return dataValuesHash;
    }

    /**
     * @param context
     * @throws Exception Internal File -> Hashtable
     */
    public static void setAllConfigDataFromFiles(Context context) {
        setAllConfigDataFromFiles(context, getConfigNames(context));
    }

    /**
     * @param context
     * @throws Exception Internal File -> Hashtable
     */
    private static void setAllConfigDataFromFiles(Context context, String[] configNames) {
        try {
            // String configname will be APP or Config or MENU etc
            // File list contains APP,CONFIG,MENU etc in the config file
            String files[] = context.fileList();

            // Check particular files exists or not(EX:APP or MENu etc)
            for (String configName : configNames) {

                String deleteFileName = null;

                // Check files exists or not
                for (String currentFileName : files) {

                    if (currentFileName.startsWith(configName + "-")) {

                        GreekHashtable dataHash;

                        // If exists, read the file
                        FileInputStream inStream = context.openFileInput(currentFileName);

                        if (inStream.available() != 0) {

                            ObjectInputStream oStream = new ObjectInputStream(inStream);
                            // Get the saved data from file.
                            dataHash = (GreekHashtable) oStream.readObject();

                            configHashMap.put(configName, dataHash);

                            Hashtable data = (Hashtable) configHashMap.get(configName);
                            if (data == null) {
                                // File doesn't contain proper data. Delete the
                                // file.
                                GreekLog.error(configName + " File doesn't contain proper data. Delete the file.");
                                deleteFileName = currentFileName;
                            } else {
                                // Version detail block doesn't have timestamp
                                // and
                                // config. so delete file for other config names
                                // except
                                // this
                                if (!configName.equalsIgnoreCase(VERSIONDETAIL)) {
                                    if ((!data.containsKey(TIME_STAMP)) || (!data.containsKey(CONFIG))) {

                                        // File doesn't contain proper data.
                                        // Delete the
                                        // file.
                                        GreekLog.error(configName + " File doesn't contain proper data. Delete the file.");
                                        deleteFileName = currentFileName;
                                    }
                                }
                            }

                        }
                        break;
                    }
                }

                // Delete the in proper data file
                if (deleteFileName != null) {
                    context.deleteFile(deleteFileName);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object getParam(Context context, String configName, String key) throws Exception {
        return getParam(context, configName + "/" + key);
    }

    public static boolean containsKey(String configName) throws Exception {
        return configHashMap.containsKey(configName);
    }

    public static Object getParam(Context context, String key) throws Exception {

        String configName = key.split("/")[0];
        if (!configHashMap.containsKey(configName)) {
            // May be due to cache clear Config doesn't exists now.
            // so read from files.
            setAllConfigDataFromFiles(context, new String[]{configName});
        } else {
            // Version detail block doesn't have timestamp and
            // config. so delete file for other config names except
            // this
            if (!configName.equalsIgnoreCase(VERSIONDETAIL)) {
                // Check hashatable contains tstamp & config key.
                Hashtable data = (Hashtable) configHashMap.get(configName);
                if (data == null || (!data.containsKey(TIME_STAMP)) || (!data.containsKey(CONFIG))) {
                    GreekLog.error(configName + " config hashtable missing tstamp or config, So reading from internal file");
                    setAllConfigDataFromFiles(context, new String[]{configName});
                }
            }
        }

        GreekHashtable mainObject = configHashMap;

        for (String ss : key.split("/")) {
            if (mainObject.get(ss) instanceof GreekHashtable) {
                mainObject = (GreekHashtable) mainObject.get(ss);
            } else {
                return mainObject.get(ss);
            }
        }
        return mainObject;
    }

    public static GreekHashtable getConfigHashtable() {
        return configHashMap;
    }

}
