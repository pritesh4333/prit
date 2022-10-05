package com.acumengroup.greekmain.core.market;

import android.content.Context;

import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.constants.GreekConstants;
import com.acumengroup.greekmain.core.data.ServiceManager;
import com.acumengroup.greekmain.core.model.tradecancelorder.TradeCancelOrderRequest;
import com.acumengroup.greekmain.core.model.trademodifyorder.TradeModifyOrderRequest;
import com.acumengroup.greekmain.core.model.tradesendneworder.TradeSendNewOrderRequest;
import com.acumengroup.greekmain.core.network.MarginDetailRequest;
import com.acumengroup.greekmain.core.network.ProductChangeRequest;
import com.acumengroup.greekmain.core.network.StreamingRequest;
import com.acumengroup.greekmain.util.Util;

import org.json.JSONObject;

/**
 * Created by Arcadia on 2/23/2016.
 * This class will handle the order related TCP streaming
 */
public class OrderStreamingController implements GreekConstants {

    public void sendStreamingLoginRequest(Context context, String gscid, String clientCode, String sessionId, String deviceId) {
        //public void sendStreamingLoginRequest(Context context, LoginDetails logindetail) {
        if (Util.getPrefs(context).getBoolean("GREEK_DATA_STREAMING_TOGGLE", true)) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("gscid", gscid);
                jsonObject.put("gcid", clientCode);
                jsonObject.put("sessionId", sessionId);
                jsonObject.put("device_id", deviceId);
                jsonObject.put("device_type", "0");
                StreamingRequest request = new StreamingRequest(context, jsonObject);
                // request.setLoginData(gscid, clientCode, sessionId);
//                request.setHost(BASE_ORDER_STREAMING_URL);
                request.setHost(AccountDetails.getIris_IP());
                request.setRequestType(StreamingRequest.RequestType.SUBSCRIBE);
                request.setPort(AccountDetails.getIris_Port());
                request.setStreamingType("login");
                ServiceManager.getInstance(context).sendOrderStreamingRequest(request, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendStreamingLogoffRequest(Context context, String gscid, String clientCode, String sessionId, String deviceId) {
        //public void sendStreamingLoginRequest(Context context, LoginDetails logindetail) {
        if (Util.getPrefs(context).getBoolean("GREEK_DATA_STREAMING_TOGGLE", true)) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("gscid", gscid);
                jsonObject.put("gcid", clientCode);
                jsonObject.put("sessionId", sessionId);
//                jsonObject.put("device_id", deviceId);
//                jsonObject.put("device_type", "0");
                StreamingRequest request = new StreamingRequest(context, jsonObject);
                // request.setLoginData(gscid, clientCode, sessionId);
//                request.setHost(BASE_ORDER_STREAMING_URL);
                request.setHost(AccountDetails.getIris_IP());
                request.setRequestType(StreamingRequest.RequestType.SUBSCRIBE);
                request.setPort(AccountDetails.getIris_Port());
                request.setStreamingType("logoff");
                ServiceManager.getInstance(context).sendOrderStreamingRequest(request, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //TODO: service for guest login
    public void sendStreamingGuestLoginOrderRequest(Context context, String gscid, String clientCode, String gcmToken, String serverApiKey, String sessionId) {

        if (Util.getPrefs(context).getBoolean("GREEK_DATA_STREAMING_TOGGLE", true)) {
            try {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("gscid", gscid);
                jsonObject.put("gcid", clientCode);
                jsonObject.put("sessionId", sessionId);
                jsonObject.put("loginType", "guest");
                jsonObject.put("gcmToken", gcmToken);
                jsonObject.put("serverApiKey", serverApiKey);

                StreamingRequest request = new StreamingRequest(context, jsonObject);

                request.setHost(AccountDetails.getIris_IP());
                request.setRequestType(StreamingRequest.RequestType.SUBSCRIBE);
                request.setPort(AccountDetails.getIris_Port());
                request.setStreamingType("login");
                ServiceManager.getInstance(context).sendOrderStreamingRequest(request, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // TODO: 01-Feb-17 service for grossexposure
    public void sendStreamingGrossExposureRequest(Context context, String clientCode) {
        if (Util.getPrefs(context).getBoolean("GREEK_DATA_STREAMING_TOGGLE", true)) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("gcid", clientCode);
                StreamingRequest request = new StreamingRequest(context, jsonObject);
                request.setHost(AccountDetails.getIris_IP());
                request.setPort(AccountDetails.getIris_Port());
                request.setRequestType(StreamingRequest.RequestType.SUBSCRIBE);
                request.setStreamingType("GrossExposureRequest");
                ServiceManager.getInstance(context).sendOrderStreamingRequest(request, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //TODO:HB services to Iris
    public void sendStreamingHeartBeatIrisRequest(Context context, String clientCode, String sessionId) {
        //public void sendStreamingLoginRequest(Context context, LoginDetails logindetail) {
        if (Util.getPrefs(context).getBoolean("GREEK_DATA_STREAMING_TOGGLE", true)) {
            try {
                //StreamingRequest request = new StreamingRequest(context, new JSONObject());
                JSONObject jsonObject = new JSONObject();
                //   jsonObject.put("gscid", gscid);
                jsonObject.put("gcid", clientCode);
                jsonObject.put("sessionId", sessionId);
                //     jsonObject.put("deviceId", deviceId);
                StreamingRequest request = new StreamingRequest(context, jsonObject);
                // request.setLoginData(gscid, clientCode, sessionId);
                request.setHost(AccountDetails.getIris_IP());
                request.setRequestType(StreamingRequest.RequestType.SUBSCRIBE);
                request.setPort(AccountDetails.getIris_Port());
                request.setStreamingType("HeartBeat");
                ServiceManager.getInstance(context).sendOrderStreamingRequest(request, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//    public void sendSecurityInfo(Context context, String sessionId, String clientCode, String token) {
//        try {
//
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("gcid", clientCode);
//            jsonObject.put("gtoken", token);
//            jsonObject.put("sessionId", sessionId);
//
//            StreamingRequest request = new StreamingRequest(context, jsonObject);
//            request.setHost(AccountDetails.getIris_IP());
//            request.setRequestType(StreamingRequest.RequestType.SUBSCRIBE);
//            request.setPort(AccountDetails.getIris_Port());
//            request.setStreamingType("SymbolVarMarginRequest");
//            ServiceManager.getInstance(context).sendOrderStreamingRequest(request, false);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    public void sendStreamingSendOrderRequest(Context context, TradeSendNewOrderRequest data) {
        if (Util.getPrefs(context).getBoolean("GREEK_DATA_STREAMING_TOGGLE", true)) {
            try {
                StreamingRequest request = new StreamingRequest(context, data.toJSONObject());
                request.setHost(AccountDetails.getIris_IP());
                request.setRequestType(StreamingRequest.RequestType.SUBSCRIBE);
                request.setPort(AccountDetails.getIris_Port());
                request.setStreamingType(NEW_ORDER_REQUEST);
                ServiceManager.getInstance(context).sendOrderStreamingRequest(request, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendStreamingSendOrderIOCRequest(Context context, JSONObject data) {
        if (Util.getPrefs(context).getBoolean("GREEK_DATA_STREAMING_TOGGLE", true)) {
            try {
                StreamingRequest request = new StreamingRequest(context, data);
                request.setHost(AccountDetails.getIris_IP());
                request.setRequestType(StreamingRequest.RequestType.SUBSCRIBE);
                request.setPort(AccountDetails.getIris_Port());
                request.setStreamingType(NEW_ORDER_REQUEST);
                ServiceManager.getInstance(context).sendOrderStreamingRequest(request, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //TO send GCM token to IRIS
    public void sendStreamingGCMInfoRequest(Context context, String gscid, String clientCode, String sessionId, String flag) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("gscid", gscid);
            jsonObject.put("gcid", clientCode);
            jsonObject.put("sessionId", sessionId);
            jsonObject.put("gcmToken", Util.getPrefs(context).getString("GCMToken", ""));
            jsonObject.put("serverApiKey", "AIzaSyDeGsEYbrze2PPLs22EOl6TBy5PG5mqNEU");
            jsonObject.put("statusFlag", flag);
            StreamingRequest request = new StreamingRequest(context, jsonObject);
            request.setHost(AccountDetails.getIris_IP());
            request.setRequestType(StreamingRequest.RequestType.SUBSCRIBE);
            request.setPort(AccountDetails.getIris_Port());
            request.setStreamingType("GCMInfo");
            ServiceManager.getInstance(context).sendOrderStreamingRequest(request, false);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //     }
    }

    public void sendStreamingModifyOrderRequest(Context context, TradeModifyOrderRequest data) {
        if (Util.getPrefs(context).getBoolean("GREEK_DATA_STREAMING_TOGGLE", true)) {
            try {
                StreamingRequest request = new StreamingRequest(context, data.toJSONObject());
                request.setHost(AccountDetails.getIris_IP());
                request.setRequestType(StreamingRequest.RequestType.SUBSCRIBE);
                request.setPort(AccountDetails.getIris_Port());
                request.setStreamingType(MODIFY_ORDER_REQUEST);
                ServiceManager.getInstance(context).sendOrderStreamingRequest(request, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendStreamingCancelOrderRequest(Context context, TradeCancelOrderRequest data) {
        if (Util.getPrefs(context).getBoolean("GREEK_DATA_STREAMING_TOGGLE", true)) {
            try {
                StreamingRequest request = new StreamingRequest(context, data.toJSONObject());
                request.setHost(AccountDetails.getIris_IP());
                request.setRequestType(StreamingRequest.RequestType.SUBSCRIBE);
                request.setPort(AccountDetails.getIris_Port());
                request.setStreamingType(CANCEL_ORDER_REQUEST);
                ServiceManager.getInstance(context).sendOrderStreamingRequest(request, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMarginDetailRequest(Context context, MarginDetailRequest data) {
        try {
            StreamingRequest request = new StreamingRequest(context, data.toJSONObject());
            request.setHost(AccountDetails.getIris_IP());
            request.setRequestType(StreamingRequest.RequestType.SUBSCRIBE);
            request.setPort(AccountDetails.getIris_Port());
            request.setStreamingType(MARGIN_DETAIL_REQUEST);
            ServiceManager.getInstance(context).sendOrderStreamingRequest(request, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendAvailableFundsRequest(Context context, MarginDetailRequest data) {
        try {
            StreamingRequest request = new StreamingRequest(context, data.toJSONObject());
            request.setHost(AccountDetails.getIris_IP());
            request.setRequestType(StreamingRequest.RequestType.SUBSCRIBE);
            request.setPort(AccountDetails.getIris_Port());
            request.setStreamingType(AVAILABLE_PAYOUT_REQUEST);
            ServiceManager.getInstance(context).sendOrderStreamingRequest(request, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendNPDetailRequest(Context context, String gscid, String sessionId, String token) {
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("gscid", gscid);
            jsonObject.put("sessionId", sessionId);

            StreamingRequest request = new StreamingRequest(context, jsonObject);
            request.setHost(AccountDetails.getIris_IP());
            request.setRequestType(StreamingRequest.RequestType.SUBSCRIBE);
            request.setPort(AccountDetails.getIris_Port());
            request.setStreamingType("NPDetailRequest");
            ServiceManager.getInstance(context).sendOrderStreamingRequest(request, false);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendProductChangeRequest(Context context, ProductChangeRequest data) {
        try {
            StreamingRequest request = new StreamingRequest(context, data.toJSONObject());
            request.setHost(AccountDetails.getIris_IP());
            request.setRequestType(StreamingRequest.RequestType.SUBSCRIBE);
            request.setPort(AccountDetails.getIris_Port());
            request.setStreamingType(PRODUCT_CHANGE_REQUEST);
            ServiceManager.getInstance(context).sendOrderStreamingRequest(request, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendFundTransferDetailsRequest(Context context, String uniqueId, String gscid, String amount, String order_state, String gcid, String segment) {
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("our_trans_id", uniqueId);
            jsonObject.put("gscid", gscid);
            jsonObject.put("amount", amount);
            jsonObject.put("order_state", order_state);
            jsonObject.put("gcid", gcid);
            jsonObject.put("segment", segment);

            StreamingRequest request = new StreamingRequest(context, jsonObject);
            request.setHost(AccountDetails.getIris_IP());
            request.setRequestType(StreamingRequest.RequestType.SUBSCRIBE);
            request.setPort(AccountDetails.getIris_Port());
            request.setStreamingType(FUND_TRANSFER_DETAILS);
            ServiceManager.getInstance(context).sendOrderStreamingRequest(request, false);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendEDISHoldingInfoRequest(Context context, String sessionId, String gscid) {
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sessionId", sessionId);
            jsonObject.put("gscid", gscid);

            StreamingRequest request = new StreamingRequest(context, jsonObject);
            request.setHost(AccountDetails.getIris_IP());
            request.setRequestType(StreamingRequest.RequestType.SUBSCRIBE);
            request.setPort(AccountDetails.getIris_Port());
            request.setStreamingType("getEDISHoldingInfo");
            ServiceManager.getInstance(context).sendOrderStreamingRequest(request, false);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendEPledgeHoldingInfoRequest(Context context, String sessionId, String gscid) {
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sessionId", sessionId);
            jsonObject.put("gscid", gscid);

            StreamingRequest request = new StreamingRequest(context, jsonObject);
            request.setHost(AccountDetails.getIris_IP());
            request.setRequestType(StreamingRequest.RequestType.SUBSCRIBE);
            request.setPort(AccountDetails.getIris_Port());
            request.setStreamingType("EPledgeHoldingInfo");
            ServiceManager.getInstance(context).sendOrderStreamingRequest(request, false);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendHoldingValueInfo(Context context, String gscid, String sessionId, String token) {
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("gscid", gscid);
            jsonObject.put("token", token);
            jsonObject.put("sessionId", sessionId);
            StreamingRequest request = new StreamingRequest(context, jsonObject);
            request.setHost(AccountDetails.getIris_IP());
            request.setRequestType(StreamingRequest.RequestType.SUBSCRIBE);
            request.setPort(AccountDetails.getIris_Port());
            request.setStreamingType("HoldingValueInfo");
            ServiceManager.getInstance(context).sendOrderStreamingRequest(request, false);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendHoldingDetailsInfo(Context context, String gscid, String sessionId, String token) {
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("gscid", gscid);
            jsonObject.put("sessionId", sessionId);

            StreamingRequest request = new StreamingRequest(context, jsonObject);
            request.setHost(AccountDetails.getIris_IP());
            request.setRequestType(StreamingRequest.RequestType.SUBSCRIBE);
            request.setPort(AccountDetails.getIris_Port());
            request.setStreamingType("HoldingDetailsInfo");
            ServiceManager.getInstance(context).sendOrderStreamingRequest(request, false);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendSecurityInfo(Context context, String sessionId, String clientCode, String token) {
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("gcid", clientCode);
            jsonObject.put("gtoken", token);
            jsonObject.put("sessionId", sessionId);

            StreamingRequest request = new StreamingRequest(context, jsonObject);
            request.setHost(AccountDetails.getIris_IP());
            request.setRequestType(StreamingRequest.RequestType.SUBSCRIBE);
            request.setPort(AccountDetails.getIris_Port());
            request.setStreamingType("SymbolVarMarginRequest");
            ServiceManager.getInstance(context).sendOrderStreamingRequest(request, false);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendHoldingScripPositionInfo(Context context, String gscid, String sessionId, String token) {
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("gscid", gscid);
            jsonObject.put("token", token);
            jsonObject.put("sessionId", sessionId);

            StreamingRequest request = new StreamingRequest(context, jsonObject);
            request.setHost(AccountDetails.getIris_IP());
            request.setRequestType(StreamingRequest.RequestType.SUBSCRIBE);
            request.setPort(AccountDetails.getIris_Port());
            request.setStreamingType("HoldingScripPositionInfo");
            ServiceManager.getInstance(context).sendOrderStreamingRequest(request, false);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendHoldingValueinfo(Context context, String gscid, String sessionId) {
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("gscid", gscid);
            jsonObject.put("sessionId", sessionId);

            StreamingRequest request = new StreamingRequest(context, jsonObject);
            request.setHost(AccountDetails.getIris_IP());
            request.setRequestType(StreamingRequest.RequestType.SUBSCRIBE);
            request.setPort(AccountDetails.getIris_Port());
            request.setStreamingType("HoldingValueInfo");
            ServiceManager.getInstance(context).sendOrderStreamingRequest(request, false);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendCDSLTransferDetailsRequest(Context context, JSONObject jsonObject, String ReqId, String gscid, String sessionId) {
        try {

            StreamingRequest request = new StreamingRequest(context, jsonObject, ReqId, gscid, sessionId);
            request.setHost(AccountDetails.getIris_IP());
            request.setRequestType(StreamingRequest.RequestType.SUBSCRIBE);
            request.setPort(AccountDetails.getIris_Port());
            request.setStreamingType("updateAuthorizationStatus");
            ServiceManager.getInstance(context).sendOrderStreamingRequest(request, false);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void updateBlockPledgeData(Context context, JSONObject jsonObject, String ReqId, String gscid, String sessionId) {
        try {

            StreamingRequest request = new StreamingRequest(context, jsonObject, ReqId, gscid, sessionId);
            request.setHost(AccountDetails.getIris_IP());
            request.setRequestType(StreamingRequest.RequestType.SUBSCRIBE);
            request.setPort(AccountDetails.getIris_Port());
            request.setStreamingType("updateBlockPledgeData");
            ServiceManager.getInstance(context).sendOrderStreamingRequest(request, false);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendStreamingRmsRejectionRequest(Context context, String clientCode, String token, String reason, String rejectedbBy) {
        //public void sendStreamingLoginRequest(Context context, LoginDetails logindetail) {
        if (Util.getPrefs(context).getBoolean("GREEK_DATA_STREAMING_TOGGLE", true)) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("gcid", clientCode);
                jsonObject.put("gtoken", token);
                jsonObject.put("reason", reason);
                jsonObject.put("code", rejectedbBy);
                StreamingRequest request = new StreamingRequest(context, jsonObject);
                request.setHost(AccountDetails.getIris_IP());
                request.setRequestType(StreamingRequest.RequestType.SUBSCRIBE);
                request.setPort(AccountDetails.getIris_Port());
                request.setStreamingType("RMSRejectionMessage");
                ServiceManager.getInstance(context).sendOrderStreamingRequest(request, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendStreamingContractInfoRequest(Context context, String clientCode, String token) {
        //public void sendStreamingLoginRequest(Context context, LoginDetails logindetail) {
        if (Util.getPrefs(context).getBoolean("GREEK_DATA_STREAMING_TOGGLE", true)) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("gcid", clientCode);
                jsonObject.put("gtoken", token);
                StreamingRequest request = new StreamingRequest(context, jsonObject);
                request.setHost(AccountDetails.getIris_IP());
                request.setRequestType(StreamingRequest.RequestType.SUBSCRIBE);
                request.setPort(AccountDetails.getIris_Port());
                request.setStreamingType("SymbolVarMarginRequest");
                ServiceManager.getInstance(context).sendOrderStreamingRequest(request, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendFundTransferPayoutRequest(Context context, String gcid, String commodityamt, String noncommodityamt) {
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("gcid", gcid);
            jsonObject.put("payout_amount_com", commodityamt);
            jsonObject.put("payout_amount", noncommodityamt);


            StreamingRequest request = new StreamingRequest(context, jsonObject);
            request.setHost(AccountDetails.getIris_IP());
            request.setRequestType(StreamingRequest.RequestType.SUBSCRIBE);
            request.setPort(AccountDetails.getIris_Port());
            request.setStreamingType(FUND_TRANSFER_PAYOUT_REQUEST);
            ServiceManager.getInstance(context).sendOrderStreamingRequest(request, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
