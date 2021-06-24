package com.acumengroup.mobile.symbolsearch;


import com.acumengroup.greekmain.core.app.AccountDetails;
import com.loopj.android.http.Base64;
import com.acumengroup.greekmain.core.constants.ServiceConstants;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;


/**
 * Created by hiren on 17-09-2015.
 */
public class WebServiceDirectCall {
    public static JSONObject getData(String url) {
        try {

//            String baseUrl = ServiceConstants.CUSTOM_SERVER_IP;
            url = AccountDetails.getIsSecure() + "://" + AccountDetails.getArachne_IP() + ":" + AccountDetails.getArachne_Port() + "/" + url;
            if (url.contains("?")) {
                String svcName = url.substring(0, url.indexOf("?"));
                String svcParameters = url.substring(url.indexOf("?") + 1);
                String encryptSvcParameters = encodeToBase64(svcParameters);
                url = svcName + "?" + encryptSvcParameters;
            }

            URLConnection conn = new URL(url).openConnection();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;
            StringBuilder buffer = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                buffer.append(line);
            }
            rd.close();
            String decryptResponse = decodeBase64(buffer.toString());
            return new JSONObject(decryptResponse);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new JSONObject();
    }


    public static InputStream getDataPdf() {
        InputStream rd = null;
        try {

            String urlpdf = "getHelp";
            String baseUrl = ServiceConstants.CUSTOM_SERVER_IP;
            urlpdf = baseUrl + "/" + urlpdf;

            URLConnection conn = new URL(urlpdf).openConnection();
            rd = new BufferedInputStream(conn.getInputStream());

            /*String line;
            StringBuilder buffer = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                buffer.append(line);
            }
            rd.close();
            String decryptResponse = buffer.toString();*/
            return rd;

        }
        catch (MalformedURLException ex)
        {

        }
        catch (IOException ex)
        {

        }
        return rd;
    }

    public static String decodeBase64(String decodeData) {
        String decodedString = new String(android.util.Base64.decode(decodeData, android.util.Base64.DEFAULT));
        return decodedString;
    }

    public static String encodeToBase64(String stringToEncode) {
        byte[] data = new byte[0];
        data = stringToEncode.getBytes(StandardCharsets.UTF_8);
        String encyrpt = Base64.encodeToString(data, Base64.NO_WRAP);
        return encyrpt;
    }
}