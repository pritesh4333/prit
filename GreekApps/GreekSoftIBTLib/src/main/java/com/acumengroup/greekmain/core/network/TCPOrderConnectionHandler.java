package com.acumengroup.greekmain.core.network;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.acumengroup.greekmain.core.network.EDISHoldingInfoResponse;
import com.acumengroup.greekmain.core.network.HoldingDetailResponse;
import com.acumengroup.greekmain.core.network.HoldingValueresponse;
import com.acumengroup.greekmain.core.network.HoldinginfoResponse;
import com.acumengroup.greekmain.core.network.SymbolVarMarginResponse;
import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.constants.ServiceConstants;
import com.acumengroup.greekmain.core.model.streamerbroadcast.MarketStatusResponse;
import com.acumengroup.greekmain.core.model.streamerorderconfirmation.AdminMessagesResponse;
import com.acumengroup.greekmain.core.model.streamerorderconfirmation.DPRUpdatedResponse;
import com.acumengroup.greekmain.core.model.streamerorderconfirmation.FundPayOutResponse;
import com.acumengroup.greekmain.core.model.streamerorderconfirmation.StreamerBannedResponse;
import com.acumengroup.greekmain.core.model.streamerorderconfirmation.StreamerConnectionStatusResponse;
import com.acumengroup.greekmain.core.model.streamerorderconfirmation.StreamerOrderConfirmationResponse;
import com.acumengroup.greekmain.core.model.streamerorderconfirmation.StreamerOrderRejectionResponse;
import com.acumengroup.greekmain.core.model.streamerorderconfirmation.StreamerOrderSubmitStatus;
import com.acumengroup.greekmain.core.model.streamerorderconfirmation.StreamerRmsRejectionResponse;
import com.acumengroup.greekmain.core.model.streamerorderconfirmation.StreamerSymbolVarMarginResponse;
import com.acumengroup.greekmain.core.model.streamerorderconfirmation.StreamerTradeResponse;
import com.acumengroup.greekmain.core.model.streamerorderconfirmation.StreamerTriggerResponse;
import com.acumengroup.greekmain.core.model.streamerorderconfirmation.UserDetailsModifiedResponse;
import com.acumengroup.mobile.model.UpdateNSDLAuthorizationResponse;
import com.bfsl.core.network.NPDetailResponse;
import com.google.gson.Gson;
import com.loopj.android.http.Base64;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.EventBus;

/**
 * Created by Arcadia on 2/23/2016.
 */
public class TCPOrderConnectionHandler implements ServiceConstants {
    private static int counter = 0;
    private static String host;
    private static int port;
    private static TCPOrderConnectionHandler current;
    private static String requestLoginData = "";
    private Timer readTimer = null;
    private Timer responseTimer = null;
    private TimerTask readTask = null;
    private Timer logTimer = null;
    private TimerTask logTask = null;
    private TimerTask responseTask = null;
    private static Socket clientSocket = null;
    private static DataOutputStream socketOutputStream = null;
    private static BufferedReader socketInputStream = null;
    private String resp;
    private Boolean connectionFlag = false;
    private ArrayList<String> responseList = new ArrayList<>();
    android.os.Handler handler = new android.os.Handler();
    private int reconnectCounter = 0;

    public static TCPOrderConnectionHandler getInstance() {
        if (current == null) {
            current = new TCPOrderConnectionHandler();
        }
        counter = 0;
        return current;
    }

    public void placeRequest(String host, int port, String reqData, boolean isLogin) {

        Log.e("TCPOrderIrisService", "\n==========>IRIS_StreamRequest==========>\n\n" + reqData);
        TCPOrderConnectionHandler.host = host;
        TCPOrderConnectionHandler.port = port;
        if (isLogin) {
            requestLoginData = reqData;
        }
        stopReading();
        if (reqData.length() > 0) {
            new SocketCreator().execute(encodeToBase64(reqData));
        }

    }

    public String decodeBase64(String decodeData) {
        String decodedString = new String(android.util.Base64.decode(decodeData, android.util.Base64.DEFAULT));
        return decodedString;
    }

    public String encodeToBase64(String stringToEncode) {
        byte[] data = new byte[0];
        try {
            data = stringToEncode.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String encyrpt = Base64.encodeToString(data, Base64.NO_WRAP);

        return encyrpt;
    }

    public void pauseStreamingRequest(String pauseReq) {
        placeRequest(host, port, pauseReq, false);
    }


    private boolean isConnected() {
        return clientSocket != null && !clientSocket.isClosed() && clientSocket.isConnected();
    }

    public void reConnect() {
        stopStreaming();
        if (requestLoginData != null && requestLoginData.length() > 0) {
            placeRequest(host, port, requestLoginData, true);
        }
    }

    public void stopStreaming() {
        stopReading();
        AccountDetails.orderforceClose = 0;
        AccountDetails.orderServerAuthenticated = false;
        AccountDetails.ofClose = 1;
        if (clientSocket == null) {
        } else {
            if (socketOutputStream != null) {
                try {
                    socketOutputStream.close();
                } catch (Exception ignored) {
                }
            }
            if (socketInputStream != null) {
                try {
                    socketInputStream.close();
                } catch (IOException ignored) {
                }
            }

            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }

                socketOutputStream = null;
                socketInputStream = null;
                clientSocket = null;
                //GreekLog.msg("Order Streaming stopped: ");
            } catch (Exception er) {
                //GreekLog.error("PARSER_STREAMCLOSING ERROR: " + er);
                er.printStackTrace();
            }
        }
    }

    private void sendRequest(String request) {

        try {
            if (!clientSocket.isClosed() && clientSocket.isConnected()) {
                socketOutputStream.writeInt(request.length());
                //socketOutputStream.flush();    //changes done in code review for crash of apollo and iris
                socketOutputStream.writeBytes(request);
                socketOutputStream.flush();

                if (reconnectCounter > 0) {
                    reconnectCounter--;
                }
                //TODO: Same need to handle for Order Modification Request too cases.
                if (request.indexOf("\"streaming_type\":\"" + NEW_ORDER_REQUEST + "\"") >= 0) {
                    String symbol = request.substring(request.indexOf("\"tradeSymbol\":\"") + 15, request.indexOf("\"lot\":") - 2);
                    StreamerOrderSubmitStatus status = new StreamerOrderSubmitStatus(symbol, true);
                    EventBus.getDefault().post(status);
                }

            }

        } catch (Exception e) {
            //GreekLog.error("Order Streaming Request --> " + e);
            //reConnect();
            e.printStackTrace();
        }
    }

    private boolean createConnection() {
        counter++;
        try {
            if (clientSocket == null || socketInputStream == null || socketOutputStream == null) {
                clientSocket = new Socket(InetAddress.getByName(host), port);
                socketOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                socketInputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                AccountDetails.orderforceClose = 3;
                Log.e("TCPOrderIrisService", "Iris_connection_success==========>\n");
            }

            return true;
        } catch (ConnectException er) {
            Log.e("TCPOrderIrisService", "Iris_connection_Exception");
            er.printStackTrace();
            AccountDetails.setIsIrisConnected(false);
        } catch (Exception er) {
            Log.e("TCPOrderIrisService", "Iris_connection_Exception");
            er.printStackTrace();
        }
        if (counter < 3) {
            createConnection();
        } else {
            //GreekLog.error("Order Failed: Number of attempts to create TCP connection exceeds..");
            //EventBus.getDefault().post("Socket IRIS Reconnect Attempts exceeds");
            AccountDetails.setIsIrisConnected(false);
        }
        reconnectCounter--;
        return false;
    }

    private synchronized void startReading() {

        if (readTimer == null || readTask == null) {
            readTimer = new Timer();
            readTask = new TimerTask() {
                @Override
                public void run() {
                    try {
                        if (isConnected()) {
                            if (socketInputStream.ready()) {

                                String resp = socketInputStream.readLine();
                                String decodedresponse = decodeBase64(resp);
                                orderBroadcastCall(decodedresponse);
                                AccountDetails.ofClose = 0;
                            } else {
                                String response = null;
                                String decodedresponse1 = null;
                                try {
                                    if (!clientSocket.isClosed() && clientSocket.isConnected()) {
                                        response = socketInputStream.readLine();
                                    }

                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                    Log.e("TCPOrderIrisService", "socketInputStream Exception====>>>\n " + e1.toString());

                                }

                                if (response != null && !response.isEmpty()) {
                                    decodedresponse1 = decodeBase64(response);
                                    orderBroadcastCall(decodedresponse1);
                                } else if (response == null & AccountDetails.orderforceClose == 0) {
                                    //EventBus.getDefault().post("Order Force Stop");
                                    AccountDetails.setIsIrisConnected(false);
                                    stopStreaming();
                                } else if (response == null && (AccountDetails.orderforceClose == 2 || AccountDetails.orderforceClose == 3)) {
                                    //EventBus.getDefault().post("Order Failed");
                                    AccountDetails.orderforceClose = 1;
                                    AccountDetails.setIsIrisConnected(false);
                                    stopStreaming();
                                    if (AccountDetails.ofClose != 0) {
                                        AccountDetails.ofClose = 0;
                                        AccountDetails.setIsIrisConnected(false);
                                        if (handler != null) {
                                            handler.removeCallbacksAndMessages(null);
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {

                                                    //TODO:Need to maintain different flag, separate from Apollo's flag
                                                    if (!AccountDetails.recoonectbroadcastServerAuthenticated) {
                                                        if (reconnectCounter < 3) {
                                                            reconnectCounter++;
                                                            reConnect();
                                                        } else {
                                                            Log.e("TCPOrderIrisService", " Iris Server Attempts Exceed=======>>\n");
                                                        }
                                                    }
                                                    handler.postDelayed(this, 10000);
                                                }
                                            }, 0);

                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        //GreekLog.msg("readTask" + e);
                        //reConnect();
                        e.printStackTrace();
                    }
                }

                private void responseHandler() {
                    if (responseTimer == null || responseTask == null) {
                        responseTimer = new Timer();
                        responseTask = new TimerTask() {

                            @Override
                            public void run() {
                                //Log.d("Timer activated", "Timer activated");
                                if (responseList.size() > 0) {
                                    handleOrderResponse(responseList.get(responseList.size() - 1));
                                }
                                responseList.clear();
                            }


                        };
                        responseTimer.scheduleAtFixedRate(responseTask, 0, 2000);
                    }
                }

                private void handleOrderResponse(String s) {
                    try {
                        StreamingResponse streamingResponse = new StreamingResponse(s);
                        Log.e("TCPOrderIrisService", "\nTCPIrisServiceResponse=======>>\n" + streamingResponse.getStreamingType());

                        if (streamingResponse.getStreamingType().equalsIgnoreCase(ORDER_RESPONSE)) {
                            StreamerOrderConfirmationResponse orderConfirmationResponse = new StreamerOrderConfirmationResponse();
                            orderConfirmationResponse.fromJSON(streamingResponse.getResponse());
                            EventBus.getDefault().post(orderConfirmationResponse);
                        } else if (streamingResponse.getStreamingType().equalsIgnoreCase("HeartBeat")) {
                            HeartBeatOrderResponse heartBeatOrderResponse = new HeartBeatOrderResponse();
                            heartBeatOrderResponse.fromJSON(streamingResponse.getResponse());
                            Log.e("TCPOrderIrisService", "HeartBeatIRISResponse ErrorCode==>" + heartBeatOrderResponse.getErrorCode());
                            if (heartBeatOrderResponse.getErrorCode().equals("1")) {
                                AccountDetails.isValidSession = false;
                            }
                            EventBus.getDefault().post(heartBeatOrderResponse);
                        } else if (streamingResponse.getStreamingType().equalsIgnoreCase(TRIGGER_RESPONSE)) {
                            //same fields as orderResponse
                            StreamerTriggerResponse streamerTriggerResponse = new StreamerTriggerResponse();
                            streamerTriggerResponse.fromJSON(streamingResponse.getResponse());
                            EventBus.getDefault().post(streamerTriggerResponse);
                        } else if (streamingResponse.getStreamingType().equalsIgnoreCase(ORDER_REJECTION_RESPONSE)) {
                            //same fields as orderResponse
                            StreamerOrderRejectionResponse streamerOrderRejectionResponse = new StreamerOrderRejectionResponse();
                            streamerOrderRejectionResponse.fromJSON(streamingResponse.getResponse());
                            EventBus.getDefault().post(streamerOrderRejectionResponse);
                        } else if (streamingResponse.getStreamingType().equalsIgnoreCase(TRADE_RESPONSE)) {
                            //
                            StreamerTradeResponse streamerTradeResponse = new StreamerTradeResponse();
                            streamerTradeResponse.fromJSON(streamingResponse.getResponse());
                            EventBus.getDefault().post(streamerTradeResponse);
                        } else if (streamingResponse.getStreamingType().equalsIgnoreCase(RMS_REJCTION_RESPONSE)) {
                            StreamerRmsRejectionResponse rmsRejectionResponse = new StreamerRmsRejectionResponse();
                            rmsRejectionResponse.fromJSON(streamingResponse.getResponse());
                            EventBus.getDefault().post(rmsRejectionResponse);
                        } else if (streamingResponse.getStreamingType().equalsIgnoreCase(CONNECTION_STATUS_RESPONSE)) {
                            StreamerConnectionStatusResponse streamerConnectionStatusResponse = new StreamerConnectionStatusResponse();
                            streamerConnectionStatusResponse.fromJSON(streamingResponse.getResponse());
                            EventBus.getDefault().post(streamerConnectionStatusResponse);
                        } else if (streamingResponse.getStreamingType().equalsIgnoreCase(SYMBOL_VAR_MARGIN_RESPONSE)) {
                            StreamerSymbolVarMarginResponse symbolVarMarginResponse = new StreamerSymbolVarMarginResponse();
                            symbolVarMarginResponse.fromJSON(streamingResponse.getResponse());
                            EventBus.getDefault().post(symbolVarMarginResponse);
                        } else if (streamingResponse.getStreamingType().equalsIgnoreCase(BANNED_RULE_RESPONSE) || streamingResponse.getStreamingType().equalsIgnoreCase(SQROFF_TIMER_HIT_RESPONSE) || streamingResponse.getStreamingType().equalsIgnoreCase(FUND_TRANSFER_PAYOUT_RESPONSE)) {
                            StreamerBannedResponse streamerBannedResponse = new StreamerBannedResponse();
                            streamerBannedResponse.fromJSON(streamingResponse.getResponse());
                            EventBus.getDefault().post(streamerBannedResponse);
                        } else if (streamingResponse.getStreamingType().equalsIgnoreCase(USERDETAILS_MODIFIED_RESPONSE)) {
                            UserDetailsModifiedResponse userDetailsModifiedResponse = new UserDetailsModifiedResponse();
                            userDetailsModifiedResponse.fromJSON(streamingResponse.getResponse());
                            EventBus.getDefault().post(userDetailsModifiedResponse);
                        } else if (streamingResponse.getStreamingType().equalsIgnoreCase(DPR_UPDATE_RESPONSE)) {
                            DPRUpdatedResponse dprUpdatedResponse = new DPRUpdatedResponse();
                            dprUpdatedResponse.fromJSON(streamingResponse.getResponse());
                            EventBus.getDefault().post(dprUpdatedResponse);
                        } else if (streamingResponse.getStreamingType().equalsIgnoreCase(PRODUCT_CHANGE_RESPONSE)) {
                            ProductChangeResponse productChangeResponse = new ProductChangeResponse();
                            productChangeResponse.fromJSON(streamingResponse.getResponse());
                            EventBus.getDefault().post(productChangeResponse);
                        } else if (streamingResponse.getStreamingType().equalsIgnoreCase("MarketStatus")) {
                            MarketStatusResponse marketStatusResponse = new MarketStatusResponse();
                            marketStatusResponse.fromJSON(streamingResponse.getResponse());
                            EventBus.getDefault().post(marketStatusResponse);
                        } else if (streamingResponse.getStreamingType().equalsIgnoreCase(SYMBOL_VAR_MARGIN_RESPONSE)) {
                            StreamerSymbolVarMarginResponse symbolVarMarginResponse = new StreamerSymbolVarMarginResponse();
                            symbolVarMarginResponse.fromJSON(streamingResponse.getResponse());
                            EventBus.getDefault().post(symbolVarMarginResponse);
                            //used in contract information
                        }

                    } catch (Exception e) {
                        Log.e("TCPOrderIrisService", e.getMessage());
                    }
                }

                private void orderBroadcastCall(String resp) {
                    try {

                        Log.e("TCPOrderIrisService", "\nTCP_IRIS_ServiceResponse========>>\n\n" + resp);


                        if (resp.indexOf("{") != -1) {
                            String response = resp.substring(resp.indexOf("{"));
                            //String resp1=resp.substring(resp.indexOf("{"),resp.indexOf("))"));
                            //Log.e(" Order substring: ", resp1);

                            if (resp.length() != response.length()) {
                                //Log.e("StreamResponse: ", response);
                            }
                            if (response != null && !response.isEmpty()) {

                                StreamingResponse streamingResponse = new StreamingResponse(response);

                                if (streamingResponse.getStreamingType().equals(LOGIN_RESPONSE)) { //to get login error response
                                    OrderStreamingAuthResponse orderStreamingAuthResponse = new OrderStreamingAuthResponse();
                                    orderStreamingAuthResponse.fromJSON(streamingResponse.getResponse());
                                    if (orderStreamingAuthResponse.getError_code().equals("0")) {
                                        AccountDetails.setIsIrisConnected(true);
                                        AccountDetails.orderServerAuthenticated = true;
                                        AccountDetails.orderforceClose = 2;

                                        handler.removeCallbacksAndMessages(null);
                                        /*if(orderStreamingAuthResponse.getReconnect().equalsIgnoreCase("0"))
                                        {
                                            AccountDetails.orderReconnectionFlag=false;
                                        }
                                        else
                                        {
                                            AccountDetails.orderReconnectionFlag=false;
                                        }*/
                                    } else {

                                        AccountDetails.setIsIrisConnected(false);
                                        AccountDetails.orderServerAuthenticated = false;
                                        if (handler != null) {
                                            handler.removeCallbacksAndMessages(null);
                                        }
                                    }
                                    Thread.sleep(2000);
                                    EventBus.getDefault().post(orderStreamingAuthResponse);
                                } else if (streamingResponse.getStreamingType().equalsIgnoreCase("HeartBeat")) {
                                    HeartBeatOrderResponse heartBeatOrderResponse = new HeartBeatOrderResponse();
                                    heartBeatOrderResponse.fromJSON(streamingResponse.getResponse());
                                  //  Log.e("TCPOrderIrisService", "HeartBeat_IRIS_Response ErrorCode====>>" + heartBeatOrderResponse.getErrorCode());

                                    /*IrisRejection irisRejection = new IrisRejection();
                                    irisRejection.fromJSON(streamingResponse.getResponse());*/
                                    if (heartBeatOrderResponse.getErrorCode().equals("1")) {
                                        AccountDetails.isValidSession = false;
                                    }

                                    EventBus.getDefault().post(heartBeatOrderResponse);
                                } else if (streamingResponse.getStreamingType().equalsIgnoreCase(ORDER_RESPONSE)) {
                                    StreamerOrderConfirmationResponse orderConfirmationResponse = new StreamerOrderConfirmationResponse();
                                    orderConfirmationResponse.fromJSON(streamingResponse.getResponse());
                                    EventBus.getDefault().post(orderConfirmationResponse);
                                } else if (streamingResponse.getStreamingType().equalsIgnoreCase(TRIGGER_RESPONSE)) {
                                    //same fields as orderResponse
                                    StreamerTriggerResponse streamerTriggerResponse = new StreamerTriggerResponse();
                                    streamerTriggerResponse.fromJSON(streamingResponse.getResponse());
                                    EventBus.getDefault().post(streamerTriggerResponse);
                                } else if (streamingResponse.getStreamingType().equalsIgnoreCase(ORDER_REJECTION_RESPONSE)) {
                                    //same fields as orderResponse
                                    StreamerOrderRejectionResponse streamerOrderRejectionResponse = new StreamerOrderRejectionResponse();
                                    streamerOrderRejectionResponse.fromJSON(streamingResponse.getResponse());
                                    EventBus.getDefault().post(streamerOrderRejectionResponse);
                                } else if (streamingResponse.getStreamingType().equalsIgnoreCase(TRADE_RESPONSE)) {
                                    //
                                    StreamerTradeResponse streamerTradeResponse = new StreamerTradeResponse();
                                    streamerTradeResponse.fromJSON(streamingResponse.getResponse());
                                    EventBus.getDefault().post(streamerTradeResponse);
                                } else if (streamingResponse.getStreamingType().equalsIgnoreCase(RMS_REJCTION_RESPONSE)) {
                                    StreamerRmsRejectionResponse rmsRejectionResponse = new StreamerRmsRejectionResponse();
                                    rmsRejectionResponse.fromJSON(streamingResponse.getResponse());
                                    EventBus.getDefault().post(rmsRejectionResponse);
                                } else if (streamingResponse.getStreamingType().equalsIgnoreCase(AVAILABLE_PAYOUT_RESPONSE)) {
                                    FundMarginDetailResponse marginDetailResponse = new FundMarginDetailResponse();
                                    marginDetailResponse.fromJSON(streamingResponse.getResponse());
                                    EventBus.getDefault().post(marginDetailResponse);
                                } else if (streamingResponse.getStreamingType().equalsIgnoreCase(FUND_TRANSFER_PAYOUT_RESPONSE)) {
                                    FundPayOutResponse streamerBannedResponse = new FundPayOutResponse();
                                    streamerBannedResponse.fromJSON(streamingResponse.getResponse());
                                    EventBus.getDefault().post(streamerBannedResponse);

                                } else if (streamingResponse.getStreamingType().equalsIgnoreCase(MARGIN_RESPONSE)) {
                                    MarginDetailResponse marginDetailResponse = new MarginDetailResponse();
                                    marginDetailResponse.fromJSON(streamingResponse.getResponse());
                                    EventBus.getDefault().post(marginDetailResponse);
                                } else if (streamingResponse.getStreamingType().equalsIgnoreCase(LOGGED_IN_CLIENTS_RESPONSE)) {
                                    LoggedInClientResponse loggedInClientResponse = new LoggedInClientResponse();
                                    loggedInClientResponse.fromJSON(streamingResponse.getResponse());
                                    EventBus.getDefault().post(loggedInClientResponse);
                                } else if (streamingResponse.getStreamingType().equalsIgnoreCase("KillSwitchResponse")) {
                                    KillSwitchResponse killSwitchResponse = new KillSwitchResponse();
                                    killSwitchResponse.fromJSON(streamingResponse.getResponse());
                                    EventBus.getDefault().post(killSwitchResponse);
                                } else if (streamingResponse.getStreamingType().equalsIgnoreCase("IrisRejection")) {
                                    IrisRejection irisRejection = new IrisRejection();
                                    irisRejection.fromJSON(streamingResponse.getResponse());
                                    EventBus.getDefault().post(irisRejection);
                                } else if (streamingResponse.getStreamingType().equalsIgnoreCase("LicenseResponse")) {
                                    LicenseResponse licenseResponse = new LicenseResponse();
                                    licenseResponse.fromJSON(streamingResponse.getResponse());
                                    Thread.sleep(1000);
                                    EventBus.getDefault().post(licenseResponse);
                                } else if (streamingResponse.getStreamingType().equals("GrossExposureResponse")) {
                                    GrossExposureResponse grossExposureResponse = new GrossExposureResponse();
                                    grossExposureResponse.fromJSON(streamingResponse.getResponse());
                                    EventBus.getDefault().post(grossExposureResponse);
                                } else if (streamingResponse.getStreamingType().equals("guestlogin")) {
                                    EventBus.getDefault().post("guestlogin");
                                } else if (streamingResponse.getStreamingType().equals("HoldingScripPositionInfoResp")) {

                                    Gson gson = new Gson();
                                    HoldingDetailResponse holdingDetailResponse = gson.fromJson(resp, HoldingDetailResponse.class);

                                    EventBus.getDefault().post(holdingDetailResponse);

                                } else if (streamingResponse.getStreamingType().equals("HoldingDetailsInfoResp")) {

                                    Gson gson = new Gson();
                                    HoldinginfoResponse edisHoldingInfoResponse = gson.fromJson(resp, HoldinginfoResponse.class);

                                    EventBus.getDefault().post(edisHoldingInfoResponse);

                                } else if (streamingResponse.getStreamingType().equals("HoldingValueInfoResp")) {


                                    Gson gson = new Gson();
                                    HoldingValueresponse holdingValueresponse = gson.fromJson(resp, HoldingValueresponse.class);

                                    EventBus.getDefault().post(holdingValueresponse);

                                } else if (streamingResponse.getStreamingType().equals("NPDetailResponse")) {

                                    Gson gson = new Gson();
                                    NPDetailResponse npDetailResponse = gson.fromJson(resp, NPDetailResponse.class);

                                    EventBus.getDefault().post(npDetailResponse);

                                } else if (streamingResponse.getStreamingType().equals("EDISHoldingInfoResponse")) {


                                    Gson gson = new Gson();
                                    EDISHoldingInfoResponse edisHoldingInfoResponse = gson.fromJson(resp, EDISHoldingInfoResponse.class);

                                    EventBus.getDefault().post(edisHoldingInfoResponse);

                                } else if (streamingResponse.getStreamingType().equals("EPledgeHoldingResponse")) {


                                    Gson gson = new Gson();
                                    EDISHoldingInfoResponse edisHoldingInfoResponse = gson.fromJson(resp, EDISHoldingInfoResponse.class);

                                    EventBus.getDefault().post(edisHoldingInfoResponse);

                                }
                                else if (streamingResponse.getStreamingType().equals(NSDL_AUTHORIZATION_RESPONSE)) {


//                                    Gson gson = new Gson();
//                                    UpdateNSDLAuthorizationResponse edisHoldingInfoResponse = gson.fromJson(resp, UpdateNSDLAuthorizationResponse.class);
                                    EventBus.getDefault().post(resp);

                                }
                                        else if (streamingResponse.getStreamingType().equals(NSDLPLEDGE_AUTHORIZATION_RESPONSE)) {


//                                    Gson gson = new Gson();
//                                    UpdateNSDLAuthorizationResponse edisHoldingInfoResponse = gson.fromJson(resp, UpdateNSDLAuthorizationResponse.class);
                                    EventBus.getDefault().post(resp);

                                }
                                else if (streamingResponse.getStreamingType().equals("SymbolVarMarginResponse")) {

                                    Gson gson = new Gson();
                                    SymbolVarMarginResponse placeOrderSecurityResponse = gson.fromJson(resp, SymbolVarMarginResponse.class);
                                    EventBus.getDefault().post(placeOrderSecurityResponse);
                                    //used in security tab in bottomsheet

                                } else if (streamingResponse.getStreamingType().equals("updateAuthorizationStatus")) {


                                    Gson gson = new Gson();
                                    EDISHoldingInfoResponse edisHoldingInfoResponse = gson.fromJson(resp, EDISHoldingInfoResponse.class);

                                    EventBus.getDefault().post(edisHoldingInfoResponse);

                                } else if (streamingResponse.getStreamingType().equals("updateAuthorizationStatus")) {

                                    EventBus.getDefault().post("updateAuthorizationStatus");

                                } else if (streamingResponse.getStreamingType().equalsIgnoreCase(SYMBOL_VAR_MARGIN_RESPONSE)) {
                                    StreamerSymbolVarMarginResponse symbolVarMarginResponse = new StreamerSymbolVarMarginResponse();
                                    symbolVarMarginResponse.fromJSON(streamingResponse.getResponse());
                                    EventBus.getDefault().post(symbolVarMarginResponse);
                                } else if (streamingResponse.getStreamingType().equalsIgnoreCase(ADMIN_MESSAGES_RESPONSE)) {
                                    AdminMessagesResponse adminMessagesResponse = new AdminMessagesResponse();
                                    adminMessagesResponse.fromJSON(streamingResponse.getResponse());
                                    adminMessagesResponse.setMessage(decodeBase64(adminMessagesResponse.getMessage()));
                                    /*char[] stringBuilder = response.toCharArray();
                                    for(int i = 0;i < stringBuilder.length; i++)
                                    {
                                        if((stringBuilder[i] == '\\'))
                                        {
                                            stringBuilder[i] = '\\\\\\\\\';
                                        }
                                    }*/
                                    //response = response.replaceAll("\"", Matcher.quoteReplacement("\\\\"));
                                    //JSONObject jsonObject = new JSONObject(response);
                                    EventBus.getDefault().post(adminMessagesResponse);
                                } else if (streamingResponse.getStreamingType().equalsIgnoreCase(USERDETAILS_MODIFIED_RESPONSE)) {
                                    UserDetailsModifiedResponse userDetailsModifiedResponse = new UserDetailsModifiedResponse();
                                    userDetailsModifiedResponse.fromJSON(streamingResponse.getResponse());
                                    EventBus.getDefault().post(userDetailsModifiedResponse);
                                } else if (streamingResponse.getStreamingType().equalsIgnoreCase(BANNED_RULE_RESPONSE) || streamingResponse.getStreamingType().equalsIgnoreCase(SQROFF_TIMER_HIT_RESPONSE)) {
                                    StreamerBannedResponse streamerBannedResponse = new StreamerBannedResponse();
                                    streamerBannedResponse.fromJSON(streamingResponse.getResponse());
                                    EventBus.getDefault().post(streamerBannedResponse);
                                } else if (streamingResponse.getStreamingType().equalsIgnoreCase(DPR_UPDATE_RESPONSE)) {
                                    DPRUpdatedResponse dprUpdatedResponse = new DPRUpdatedResponse();
                                    dprUpdatedResponse.fromJSON(streamingResponse.getResponse());
                                    EventBus.getDefault().post(dprUpdatedResponse);
                                } else if (streamingResponse.getStreamingType().equalsIgnoreCase(CONNECTION_STATUS_RESPONSE)) {
                                    StreamerConnectionStatusResponse streamerConnectionStatusResponse = new StreamerConnectionStatusResponse();
                                    streamerConnectionStatusResponse.fromJSON(streamingResponse.getResponse());
                                    EventBus.getDefault().post(streamerConnectionStatusResponse);
                                } else if (streamingResponse.getStreamingType().equalsIgnoreCase(PRODUCT_CHANGE_RESPONSE)) {
                                    ProductChangeResponse productChangeResponse = new ProductChangeResponse();
                                    productChangeResponse.fromJSON(streamingResponse.getResponse());
                                    EventBus.getDefault().post(productChangeResponse);
                                } else if (streamingResponse.getStreamingType().equalsIgnoreCase(LEG_TWO_ORDER_RESPONSE)) {

                                    StreamerOrderConfirmationResponse orderConfirmationResponse = new StreamerOrderConfirmationResponse();
                                    orderConfirmationResponse.fromJSON(streamingResponse.getResponse());
                                    EventBus.getDefault().post(orderConfirmationResponse);


//                                    LegTwoOrderResponse legTwoOrderResponse = new LegTwoOrderResponse();
//                                    legTwoOrderResponse.fromJSON(streamingResponse.getResponse());
//                                    EventBus.getDefault().post(legTwoOrderResponse);

                                } else if (streamingResponse.getStreamingType().equalsIgnoreCase(LEG_THIRD_ORDER_RESPONSE)) {
                                    StreamerOrderConfirmationResponse orderConfirmationResponse = new StreamerOrderConfirmationResponse();
                                    orderConfirmationResponse.fromJSON(streamingResponse.getResponse());
                                    EventBus.getDefault().post(orderConfirmationResponse);

                                } else if (streamingResponse.getStreamingType().equalsIgnoreCase("MarketStatus")) {
                                    MarketStatusResponse marketStatusResponse = new MarketStatusResponse();
                                    marketStatusResponse.fromJSON(streamingResponse.getResponse());
                                    //Log.e("MarketStreamResponse: ", response);
                                    //generateNoteOnSd(response);     IRIS response
                                    //Thread.sleep(2000);
                                    EventBus.getDefault().post(marketStatusResponse);
                                } else {
                                    responseList.add(response);
                                    responseHandler();
                                }
                            }
                        }

                    } catch (Exception e) {
                        //GreekLog.msg("readTask" + e);
                        //reConnect();
                        e.printStackTrace();
                    }
                }
            };
            readTimer.schedule(readTask, 0, 1);
        }
        if (logTimer == null || logTask == null) {
            logTimer = new Timer();
            logTask = new TimerTask() {
                @Override
                public void run() {
                }
            };
            logTimer.scheduleAtFixedRate(logTask, 15 * 1000, 15 * 1000);
        }
    }

    public void generateNoteOnSd(String error) {
        try {
            Random random = new Random();
            int value = random.nextInt();
            String h = String.valueOf(value);
            File root = new File(Environment.getExternalStorageDirectory(), "Logs");
            if (!root.exists()) {
                root.mkdir();
            }
            File filepath = new File(root, h + ".txt");
            FileWriter writer = new FileWriter(filepath);
            writer.append(error);
            writer.flush();
            writer.close();
        } catch (Exception e) {

        }
    }

    private synchronized void stopReading() {
        if (readTask == null) return;
        readTask.cancel();
        readTask = null;
        readTimer = null;
        if (logTask == null) return;
        logTask.cancel();
        logTimer = null;
        logTask = null;
    }


    private class SocketCreator extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... arg0) {
            if (createConnection() && arg0 != null && arg0.length > 0) {
                //Log.d("Request Sending",arg0[0]);
                sendRequest(arg0[0].concat("\n"));
                //sendRequest(arg0[0]);
                startReading();
            }
            return null;
        }
    }
}
