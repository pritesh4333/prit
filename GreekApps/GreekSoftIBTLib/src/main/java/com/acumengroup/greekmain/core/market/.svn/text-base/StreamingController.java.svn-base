package com.acumengroup.greekmain.core.market;

import android.content.Context;

import com.acumengroup.greekmain.core.network.StreamingRequest.RequestType;
import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.constants.GreekConstants;
import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.marketsglobalindices.MarketsGlobalIndicesResponse;
import com.acumengroup.greekmain.core.model.marketsindianindices.MarketsIndianIndicesResponse;
import com.acumengroup.greekmain.core.model.streamerbroadcast.StreamerBroadcastRequest;
import com.acumengroup.greekmain.core.model.streamerbroadcast.Symbol;
import com.acumengroup.greekmain.core.network.ServiceResponseListener;
import com.acumengroup.greekmain.core.network.StreamingRequest;
import com.acumengroup.greekmain.core.request.GreekJSONRequest;
import com.acumengroup.greekmain.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StreamingController implements GreekConstants {

    public void sendStreamingRequest(Context context, ArrayList<String> symbolList, String type, String serviceGroup, String serviceName, boolean depthRequired) {
        if (Util.getPrefs(context).getBoolean("GREEK_DATA_STREAMING_TOGGLE", true) && symbolList.size() > 0) {
            StreamerBroadcastRequest broadcastRequest = new StreamerBroadcastRequest();
            broadcastRequest.setSymbols(addActionBarTickers(symbolList));
            try {
                StreamingRequest request = new StreamingRequest(context, broadcastRequest.toJSONObject());
                request.setService(serviceGroup, serviceName);
                if (AccountDetails.getLogin_user_type() == null) {
                    //if (AccountDetails.getUsertype(context).equalsIgnoreCase("Open")) {
                    request.setLoginData(AccountDetails.getToken(context), AccountDetails.getClientCode(context));
                    //}
                } else if (AccountDetails.getLogin_user_type().equalsIgnoreCase("Open")) {
                    request.setLoginData(AccountDetails.getToken(context), AccountDetails.getClientCode(context));
                } else {
                    request.setLoginData(AccountDetails.getUsername(context), AccountDetails.getClientCode(context));
                }
                request.setHost(AccountDetails.getApollo_IP());
                request.setRequestType(RequestType.SUBSCRIBE);
                request.setPort(AccountDetails.getApollo_Port());
                request.setStreamingType(type);

                if (AccountDetails.isIsApolloConnected()) {
                    ServiceManager.getInstance(context).sendStreamingRequest(request, false);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMarketStatusStreamingRequest(Context context, String type, String gcid, String serviceGroup, String serviceName, boolean depthRequired) {
        if (Util.getPrefs(context).getBoolean("GREEK_DATA_STREAMING_TOGGLE", true)) {
            StreamerBroadcastRequest broadcastRequest = new StreamerBroadcastRequest();
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("gcid", gcid);
                StreamingRequest request = new StreamingRequest(context, jsonObject);
                request.setService(serviceGroup, serviceName);
                request.setHost(AccountDetails.getApollo_IP());
                request.setRequestType(RequestType.SUBSCRIBE);
                request.setPort(AccountDetails.getApollo_Port());
                request.setStreamingType(type);
                ServiceManager.getInstance(context).sendStreamingRequest(request, false);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public void sendTOPgainersRequest(Context context, String marketIDSelected, String gscid, String sessionId, String gcid) {
        try {
            if (gscid == null || gscid.length() == 0) {
                gscid = Util.getPrefs(context).getString("SharedPref_USERNAME", " ");
            }
            if (gcid == null || gcid.length() == 0) {
                gcid = Util.getPrefs(context).getString("SharedPref_CLIENTCODE", " ");
            }
            if (sessionId == null || sessionId.length() == 0) {
                sessionId = Util.getPrefs(context).getString("SharedPref_SESSIONID", " ");
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sessionId", sessionId);
            jsonObject.put("gscid", gscid);
            jsonObject.put("gcid", gcid);
            jsonObject.put("marketid", marketIDSelected);

            StreamingRequest request = new StreamingRequest(context, jsonObject);
            request.setHost(AccountDetails.getApollo_IP());
            request.setRequestType(RequestType.SUBSCRIBE);
            request.setPort(AccountDetails.getApollo_Port());
            request.setStreamingType("topgainers");
            ServiceManager.getInstance(context).sendStreamingRequest(request, false);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendTOPlosersRequest(Context context, String marketIDSelected, String gscid, String sessionId, String gcid) {
        try {
            if (gscid == null || gscid.length() == 0) {
                gscid = Util.getPrefs(context).getString("SharedPref_USERNAME", " ");
            }
            if (gcid == null || gcid.length() == 0) {
                gcid = Util.getPrefs(context).getString("SharedPref_CLIENTCODE", " ");
            }
            if (sessionId == null || sessionId.length() == 0) {
                sessionId = Util.getPrefs(context).getString("SharedPref_SESSIONID", " ");
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sessionId", sessionId);
            jsonObject.put("gscid", gscid);
            jsonObject.put("gcid", gcid);
            jsonObject.put("marketid", marketIDSelected);


            StreamingRequest request = new StreamingRequest(context, jsonObject);
            request.setHost(AccountDetails.getApollo_IP());
            request.setRequestType(RequestType.SUBSCRIBE);
            request.setPort(AccountDetails.getApollo_Port());
            request.setStreamingType("toplosers");
            ServiceManager.getInstance(context).sendStreamingRequest(request, false);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendMarketmoverRequest(Context context, String marketIDSelected, String gscid, String sessionId, String gcid) {
        try {
            if (gscid == null || gscid.length() == 0) {
                gscid = Util.getPrefs(context).getString("SharedPref_USERNAME", " ");
            }
            if (gcid == null || gcid.length() == 0) {
                gcid = Util.getPrefs(context).getString("SharedPref_CLIENTCODE", " ");
            }
            if (sessionId == null || sessionId.length() == 0) {
                sessionId = Util.getPrefs(context).getString("SharedPref_SESSIONID", " ");
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sessionId", sessionId);
            jsonObject.put("gscid", gscid);
            jsonObject.put("gcid", gcid);
            jsonObject.put("marketid", marketIDSelected);


            StreamingRequest request = new StreamingRequest(context, jsonObject);
            request.setHost(AccountDetails.getApollo_IP());
            request.setRequestType(RequestType.SUBSCRIBE);
            request.setPort(AccountDetails.getApollo_Port());
            request.setStreamingType("marketmovers");
            ServiceManager.getInstance(context).sendStreamingRequest(request, false);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void sendMostActiveVolumeRequest(Context context, String marketIDSelected, String gscid, String sessionId, String gcid) {
        try {
            if (gscid == null || gscid.length() == 0) {
                gscid = Util.getPrefs(context).getString("SharedPref_USERNAME", " ");
            }
            if (gcid == null || gcid.length() == 0) {
                gcid = Util.getPrefs(context).getString("SharedPref_CLIENTCODE", " ");
            }
            if (sessionId == null || sessionId.length() == 0) {
                sessionId = Util.getPrefs(context).getString("SharedPref_SESSIONID", " ");
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sessionId", sessionId);
            jsonObject.put("gscid", gscid);
            jsonObject.put("gcid", gcid);
            jsonObject.put("marketid", marketIDSelected);


            StreamingRequest request = new StreamingRequest(context, jsonObject);
            request.setHost(AccountDetails.getApollo_IP());
            request.setRequestType(RequestType.SUBSCRIBE);
            request.setPort(AccountDetails.getApollo_Port());
            request.setStreamingType("marketmovers");
            ServiceManager.getInstance(context).sendStreamingRequest(request, false);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void sendMostActiveValueRequest(Context context, String marketIDSelected, String gscid, String sessionId, String gcid) {
        try {
            if (gscid == null || gscid.length() == 0) {
                gscid = Util.getPrefs(context).getString("SharedPref_USERNAME", " ");
            }
            if (gcid == null || gcid.length() == 0) {
                gcid = Util.getPrefs(context).getString("SharedPref_CLIENTCODE", " ");
            }
            if (sessionId == null || sessionId.length() == 0) {
                sessionId = Util.getPrefs(context).getString("SharedPref_SESSIONID", " ");
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sessionId", sessionId);
            jsonObject.put("gscid", gscid);
            jsonObject.put("gcid", gcid);
            jsonObject.put("marketid", marketIDSelected);


            StreamingRequest request = new StreamingRequest(context, jsonObject);
            request.setHost(AccountDetails.getApollo_IP());
            request.setRequestType(RequestType.SUBSCRIBE);
            request.setPort(AccountDetails.getApollo_Port());
            request.setStreamingType("marketmoversByValue");
            ServiceManager.getInstance(context).sendStreamingRequest(request, false);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //To maintain heart beat services
    public void sendStreamingHeartBeatRequest(Context context, String clientCode, String sessionId) {
        if (Util.getPrefs(context).getBoolean("GREEK_DATA_STREAMING_TOGGLE", true)) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("gcid", clientCode);
                jsonObject.put("sessionId", sessionId);
                StreamingRequest request = new StreamingRequest(context, jsonObject);
                request.setHost(AccountDetails.getApollo_IP());
                request.setRequestType(RequestType.SUBSCRIBE);
                request.setPort(AccountDetails.getApollo_Port());
                request.setStreamingType("HeartBeat");
                ServiceManager.getInstance(context).sendStreamingRequest(request, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendStreamingLoginRequest(Context context, String gscid, String clientCode, String sessionId, String deviceId, String serviceGroup, String serviceName) {
        if (Util.getPrefs(context).getBoolean("GREEK_DATA_STREAMING_TOGGLE", true)) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("gscid", gscid);
                jsonObject.put("gcid", clientCode);
                jsonObject.put("sessionId", sessionId);
                jsonObject.put("deviceId", deviceId);
                StreamingRequest request = new StreamingRequest(context, jsonObject);
                request.setService(serviceGroup, serviceName);
                request.setHost(AccountDetails.getApollo_IP());
                request.setRequestType(RequestType.SUBSCRIBE);
                request.setPort(AccountDetails.getApollo_Port());
                request.setStreamingType("login");
                ServiceManager.getInstance(context).sendStreamingRequest(request, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendStreamingLogoffRequest(Context context, String gscid, String clientCode, String sessionId, String deviceId, String serviceGroup, String serviceName) {
        if (Util.getPrefs(context).getBoolean("GREEK_DATA_STREAMING_TOGGLE", true)) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("gscid", gscid);
                jsonObject.put("gcid", clientCode);
                jsonObject.put("sessionId", sessionId);
//                jsonObject.put("deviceId", deviceId);
                StreamingRequest request = new StreamingRequest(context, jsonObject);
                request.setService(serviceGroup, serviceName);
                request.setHost(AccountDetails.getApollo_IP());
                request.setRequestType(RequestType.SUBSCRIBE);
                request.setPort(AccountDetails.getApollo_Port());
                request.setStreamingType("logoff");
                //TODO:Need to Case analysis on isLogin flag value as True value.=====================>>>
                ServiceManager.getInstance(context).sendStreamingRequest(request, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //TODO: Service for guest login
    public void sendStreamingGuestLoginBroadcastRequest(Context context, String gscid, String clientCode, String sessionId, String serviceGroup, String serviceName) {
        if (Util.getPrefs(context).getBoolean("GREEK_DATA_STREAMING_TOGGLE", true)) {
            try {
                JSONObject jsonObject = new JSONObject();
                //In guest login DeviceId  considered as a gscid.
                jsonObject.put("gscid", gscid);
                jsonObject.put("gcid", clientCode);
                jsonObject.put("sessionId", sessionId);
                jsonObject.put("loginType", "guest");
                StreamingRequest request = new StreamingRequest(context, jsonObject);
                request.setService(serviceGroup, serviceName);
                request.setHost(AccountDetails.getApollo_IP());
                request.setRequestType(RequestType.SUBSCRIBE);
                request.setPort(AccountDetails.getApollo_Port());
                request.setStreamingType("login");
                ServiceManager.getInstance(context).sendStreamingRequest(request, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //TODO: service for alert
    public void sendStreamingAlertBroadcastRequest(Context context, String clientCode, String sessionId, String ruleId, String rangeValue, String direction, String gToken, String ruleType, String operation, String serviceGroup, String serviceName) {
        if (Util.getPrefs(context).getBoolean("GREEK_DATA_STREAMING_TOGGLE", true)) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("gcid", clientCode);
                jsonObject.put("sessionId", sessionId);
                jsonObject.put("rule_id", ruleId);
                jsonObject.put("range_value", rangeValue);
                jsonObject.put("direction", direction);
                jsonObject.put("gtoken", gToken);
                jsonObject.put("rule_type", ruleType);
                jsonObject.put("operation", operation);
                StreamingRequest request = new StreamingRequest(context, jsonObject);
                request.setService(serviceGroup, serviceName);
                request.setHost(AccountDetails.getApollo_IP());
                request.setRequestType(RequestType.SUBSCRIBE);
                request.setPort(AccountDetails.getApollo_Port());
                request.setStreamingType("UserAlert");
                ServiceManager.getInstance(context).sendStreamingRequest(request, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Add Nifty And Sensex Tickers.
    private List<Symbol> addActionBarTickers(ArrayList<String> symbolList) {
        boolean hasNifty = false;
        boolean hasSensex = false;
        List<Symbol> updatedList = new ArrayList<>();
        for (String symbol : symbolList) {
            Symbol symbolOb = new Symbol();
            if ("S&P_CNX_Nifty".equals(symbol)) {
                hasNifty = true;
            }
            if ("SENSEX".equals(symbol)) {
                hasSensex = true;
            }
            symbolOb.setSymbol(symbol);
            updatedList.add(symbolOb);
        }

        return updatedList;
    }

    public void pauseStreaming(Context context, String type, ArrayList<String> symbolList) {
        try {
            if (symbolList.size() == 0) {
                return;
            }

            StreamerBroadcastRequest broadcastRequest = new StreamerBroadcastRequest();
            broadcastRequest.setSymbols(addActionBarTickers(symbolList));
            try {
                StreamingRequest request = new StreamingRequest(context, broadcastRequest.toJSONObject());
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("gscid", AccountDetails.getUsername(context));
                jsonObject.put("gcid", AccountDetails.getClientCode(context));

                request.setHost(AccountDetails.getApollo_IP());
                request.setRequestType(RequestType.UNSUBSCRIBE);
                request.setPort(AccountDetails.getApollo_Port());
                request.setStreamingType(type);
                ServiceManager.getInstance(context).sendStreamingRequest(request, false);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendIndianIndicesRequest(Context context, ServiceResponseListener serviceResponseListener) {
        GreekJSONRequest jsonRequest;
        jsonRequest = new GreekJSONRequest(context, new JSONObject());
        jsonRequest.setResponseClass(MarketsIndianIndicesResponse.class);
        jsonRequest.setService(MARKETS_SVC_GROUP, INDIAN_INDICES_SVC_NAME);
        ServiceManager.getInstance(context).sendRequest(jsonRequest, serviceResponseListener);
    }

    public void sendIndianIndicesRequesNew(Context context, ServiceResponseListener serviceResponseListener) {
        GreekJSONRequest jsonRequest;
        jsonRequest = new GreekJSONRequest(context, new JSONObject());
        jsonRequest.setResponseClass(MarketsIndianIndicesResponse.class);
        jsonRequest.setService(MARKETS_SVC_GROUP, INDIAN_INDICES_SVC_NAME_NEW);
        ServiceManager.getInstance(context).sendRequest(jsonRequest, serviceResponseListener);
    }

    public void sendIndianIndicesRequesForUser(Context context, ServiceResponseListener serviceResponseListener) {
        GreekJSONRequest jsonRequest;
        jsonRequest = new GreekJSONRequest(context, new JSONObject());

        if (AccountDetails.getLogin_user_type().equalsIgnoreCase("openuser")) {
            jsonRequest.setData("gscid", AccountDetails.getDeviceID(context));
        } else {
            jsonRequest.setData("gscid", AccountDetails.getUsername(context));
        }
        jsonRequest.setResponseClass(MarketsIndianIndicesResponse.class);
//        jsonObject.put("gcid", AccountDetails.getClientCode(context));
//        jsonObject.put("gscid", AccountDetails.getDeviceID(context));
        jsonRequest.setService(MARKETS_SVC_GROUP, "getIndianIndicesDataForUser");
        ServiceManager.getInstance(context).sendRequest(jsonRequest, serviceResponseListener);
    }

    public void sendIndianIndicesRequesForCommodityCurrency(Context context, String assetType, ServiceResponseListener serviceResponseListener) {
        GreekJSONRequest jsonRequest;
        jsonRequest = new GreekJSONRequest(context, new JSONObject());

        if (AccountDetails.getLogin_user_type().equalsIgnoreCase("openuser")) {
            jsonRequest.setData("gscid", AccountDetails.getDeviceID(context));
        } else {
            jsonRequest.setData("gscid", AccountDetails.getUsername(context));
        }

        jsonRequest.setData("assetType", assetType);
        jsonRequest.setResponseClass(MarketsIndianIndicesResponse.class);
        jsonRequest.setService(MARKETS_SVC_GROUP, "getIndianIndicesDataForUserV2");
        ServiceManager.getInstance(context).sendRequest(jsonRequest, serviceResponseListener);
    }

    public void sendGlobalIndicesRequest(Context context, ServiceResponseListener serviceResponseListener) {
        GreekJSONRequest jsonRequest;
        jsonRequest = new GreekJSONRequest(context, new JSONObject());
        jsonRequest.setResponseClass(MarketsGlobalIndicesResponse.class);
        jsonRequest.setService(MARKETS_SVC_GROUP, GLOBAL_INDICES_SVC_NAME);
        ServiceManager.getInstance(context).sendRequest(jsonRequest, serviceResponseListener);
    }
}
