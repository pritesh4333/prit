/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acumengroup.greekmain.core.network;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.acumengroup.greekmain.core.network.MarketMoversByValueModel;
import com.acumengroup.greekmain.core.app.AccountDetails;
import com.acumengroup.greekmain.core.constants.ServiceConstants;
import com.acumengroup.greekmain.core.model.streamerbroadcast.MarketStatusResponse;
import com.acumengroup.greekmain.core.model.streamerbroadcast.OpenInterestResponse;
import com.bfsl.core.network.MarketMoversModel;
import com.bfsl.core.network.TopGainersModels;
import com.bfsl.core.network.TopLosersModel;
import com.google.gson.Gson;
import com.loopj.android.http.Base64;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.EventBus;

/**
 * Created by Arcadia
 */
public class TCPConnectionHandler implements ServiceConstants {
    private static int counter = 0;
    private static String host;
    private static int port;
    private static TCPConnectionHandler current;
    private static String requestData = "";
    private static String requestLoginData = "";
    private Timer readTimer = null;
    private TimerTask readTask = null;
    private Timer logTimer = null;
    private TimerTask logTask = null;
    private static Socket clientSocket = null;
    private static DataOutputStream socketOutputStream = null;
    private static BufferedReader socketInputStream = null;
    android.os.Handler handler = new android.os.Handler();
    private int reconnectCounter = 0;

    public static TCPConnectionHandler getInstance() {
        if (current == null) {
            current = new TCPConnectionHandler();
        }
        counter = 0;
        return current;
    }

    public void placeRequest(String host, int port, String reqData, boolean isLogin) {

        //Log.e("TCPApolloService", "\n==========>Apollo_StreamRequest==========>\n" + reqData + "\n    ");

        TCPConnectionHandler.host = host;
        TCPConnectionHandler.port = port;
        TCPConnectionHandler.requestData = reqData;
        if (isLogin) {
            requestLoginData = reqData;
        }
        stopReading();
        if (reqData.length() > 0) {
            new SocketCreator().execute(encodeToBase64(reqData));
        }
    }

    public void pauseStreamingRequest(String pauseReq) {
        if (AccountDetails.isIsApolloConnected()) {
            placeRequest(host, port, pauseReq, false);
        }

    }


    private boolean isConnected() {
        return clientSocket != null && !clientSocket.isClosed() && clientSocket.isConnected();
    }

    public void reConnect() {
        stopStreaming();
        if (requestLoginData != null && requestLoginData.length() > 0) {
            //GreekLog.msg("Re-login called:");
            placeRequest(host, port, requestLoginData, true);
        }
    }

    public void stopStreaming() {
        AccountDetails.setIsApolloConnected(false);
        stopReading();
        AccountDetails.forceClose = 0;
        AccountDetails.fClose = 1;
        if (clientSocket == null) {
            //GreekLog.error("No socket open");
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
                } catch (Exception ignored) {
                }
            }

            try {

                if (clientSocket != null) {
                    try {
                        clientSocket.close();
                    } catch (Exception ignored) {
                    }
                }
                socketOutputStream = null;
                socketInputStream = null;
                clientSocket = null;
            } catch (Exception er) {
                //GreekLog.error("PARSER_STREAMCLOSING ERROR: " + er);
                er.printStackTrace();
            }
        }
    }

    public void forceStopStreaming() {
        stopReading();

        if (clientSocket == null) {
            //GreekLog.error("No socket open");
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
                clientSocket.close();
                socketOutputStream = null;
                socketInputStream = null;
                clientSocket = null;

                //GreekLog.msg("Streaming stopped: ");
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
                //socketOutputStream.flush(); //changes done in code review for crash of apollo and iris
                socketOutputStream.writeBytes(request);
                socketOutputStream.flush();

                if (reconnectCounter > 0) {
                    reconnectCounter--;
                }
            }


        } catch (Exception e) {
            //GreekLog.error("Streaming Request --> " + e);
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
                AccountDetails.forceClose = 3;
            }
            return true;
        } catch (Exception er) {
            er.printStackTrace();
        }

        if (counter < 3) {
            createConnection();
        } else {
            //GreekLog.error("Failed: Number of attempts to create TCP connection exceeds..");
            //EventBus.getDefault().post("Socket Apollo Reconnect Attempts exceeds");
            AccountDetails.setIsApolloConnected(false);
        }
        reconnectCounter--;
        return false;
    }

    private synchronized void startReading() {
        //GreekLog.msg("startReading");
        if (readTimer == null || readTask == null) {
            readTimer = new Timer();
            readTask = new TimerTask() {
                @Override
                public void run() {
                    try {
                        if (isConnected()) {


                            //SomeTimes Socket is ready to read responses than Responses Json Parsing performed as in IF Condition.
                            if (socketInputStream != null && socketInputStream.ready()) {

                                String response = null;
                                try {
                                    response = socketInputStream.readLine();

                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                    Log.e("TCPConnectionHandler", "socketInputStream Exception " + e1.toString());
                                }
                                if (response != null && !response.isEmpty()) {
                                    broadcastCall(decodeBase64(response));
                                }
                                AccountDetails.fClose = 0;
                            } else {

                                //SomeTimes Socket is not ready to read responses than Responses Json Parsing performed as in ELSE Condition.

                                String response = null;
                                try {

                                    response = socketInputStream.readLine();

                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                    Log.e("TCPConnectionHandler", "socketInputStream Exception " + e1.toString());
                                }
                                if (response != null && !response.isEmpty()) {
                                    //Log.e("failedresp",response);
                                    broadcastCall(decodeBase64(response));
                                } else if (response == null && AccountDetails.forceClose == 0) {
                                    //Log.e("StreamResponse: ", "Disconnected");
                                    EventBus.getDefault().post("Force Stop");
                                    AccountDetails.setIsApolloConnected(false);
                                    stopStreaming();

                                } else if (response == null && (AccountDetails.forceClose == 2 || AccountDetails.forceClose == 3)) {
                                    EventBus.getDefault().post("Failed");
//                                  AccountDetails.setIsIrisConnected(false);  Reason--->>>0031724
                                    stopStreaming();

                                    if (AccountDetails.fClose != 0) {
                                        AccountDetails.fClose = 0;
                                        Log.e("StartReading", "Comes here...Socket has to Disconnect....Apollo");
                                        AccountDetails.setIsApolloConnected(false);
                                        if (handler != null) {
                                            handler.removeCallbacksAndMessages(null);
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (!AccountDetails.recoonectbroadcastServerAuthenticated) {
                                                        if (reconnectCounter < 3) {
                                                            reconnectCounter++;
                                                            reConnect();
                                                        } else {
                                                            Log.e("TCPConnection", "Attempts Exceed");
                                                        }
                                                    }
                                                    handler.postDelayed(this, 10 * 1000);
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

                private void broadcastCall(String resp) {
                    try {

                        if (resp.indexOf("{") != -1) {

                            String response = resp.substring(resp.indexOf("{"));

                          //  Log.e("TCPApolloService", "Apollo_StreamResponse==========>\n" + response + "\n    ");


                            if (response != null && !response.isEmpty()) {

                                StreamingResponse streamingResponse = new StreamingResponse(response);
                                if (streamingResponse.getStreamingType().equals("ltpinfo")) {
                                    //StreamerBroadcastResponse broadcastResponse = new StreamerBroadcastResponse();
                                    // broadcastResponse.fromJSON(streamingResponse.getResponse());
                                    EventBus.getDefault().post(streamingResponse);
                                } else if (streamingResponse.getStreamingType().equals("ohlc")) {
                                    //StreamerBroadcastResponse broadcastResponse = new StreamerBroadcastResponse();
                                    // broadcastResponse.fromJSON(streamingResponse.getResponse());
                                    EventBus.getDefault().post(streamingResponse);
                                } else if (streamingResponse.getStreamingType().equals("touchline")) {
                                    //StreamerBroadcastResponse broadcastResponse = new StreamerBroadcastResponse();
                                    // broadcastResponse.fromJSON(streamingResponse.getResponse());
                                    EventBus.getDefault().post(streamingResponse);
                                } else if (streamingResponse.getStreamingType().equalsIgnoreCase("MarketStatus")) {
                                    MarketStatusResponse marketStatusResponse = new MarketStatusResponse();
                                    marketStatusResponse.fromJSON(streamingResponse.getResponse());
                                    //Log.e("MarketStreamResponse: ", response);
                                    //generateNoteOnSd(response);
                                    //Thread.sleep(2000);
                                    EventBus.getDefault().post(marketStatusResponse);
                                } else if (streamingResponse.getStreamingType().equalsIgnoreCase("OpenInterest")) {
                                    OpenInterestResponse openInterestResponse = new OpenInterestResponse();
                                    openInterestResponse.fromJSON(streamingResponse.getResponse());
                                    EventBus.getDefault().post(openInterestResponse);
                                } else if (streamingResponse.getStreamingType().equals(LOGIN_RESPONSE)) {

                                    StreamingAuthResponse streamingAuthResponse = new StreamingAuthResponse();
                                    streamingAuthResponse.fromJSON(streamingResponse.getResponse());
                                    if (streamingAuthResponse.getError_code().equals("0")) {
                                        AccountDetails.setIsApolloConnected(true);
                                        AccountDetails.broadcastServerAuthenticated = true;
                                        AccountDetails.recoonectbroadcastServerAuthenticated = false;

                                        AccountDetails.forceClose = 2;

                                    } else {
                                        AccountDetails.broadcastServerAuthenticated = false;
                                        AccountDetails.setIsApolloConnected(false);
                                        AccountDetails.recoonectbroadcastServerAuthenticated = true;
                                        if (handler != null) {
                                            handler.removeCallbacksAndMessages(null);// Remove handle from background to reconnects CASE Analysis===============>>>
                                        }

                                    }

                                    Thread.sleep(2000);//wait to Run GreekBaseActivity and to setup Fragment screen.
                                    EventBus.getDefault().post(streamingAuthResponse);

                                } else if (streamingResponse.getStreamingType().equals("index")) {
                                    //StreamerBroadcastResponse broadcastResponse = new StreamerBroadcastResponse();
                                    //broadcastResponse.fromJSON(streamingResponse.getResponse());
                                    EventBus.getDefault().post(streamingResponse);
                                } else if (streamingResponse.getStreamingType().equalsIgnoreCase("marketPicture")) {
                                    //StreamerBroadcastResponse broadcastResponse = new StreamerBroadcastResponse();
                                    //broadcastResponse.fromJSON(streamingResponse.getResponse());
                                    EventBus.getDefault().post(streamingResponse);
                                } else if (streamingResponse.getStreamingType().equals("forcelogoff")) {
                                    //StreamerBroadcastResponse broadcastResponse = new StreamerBroadcastResponse();
                                    //broadcastResponse.fromJSON(streamingResponse.getResponse());
                                    EventBus.getDefault().post("forcelogoff");
                                } else if (streamingResponse.getStreamingType().equals("HeartBeat")) {
                                    //StreamerBroadcastResponse broadcastResponse = new StreamerBroadcastResponse();
                                    //broadcastResponse.fromJSON(streamingResponse.getResponse());
                                    HeartBeatStreamingResponse heartBeatStreamingResponse = new HeartBeatStreamingResponse();
                                    heartBeatStreamingResponse.fromJSON(streamingResponse.getResponse());
                                    EventBus.getDefault().post(heartBeatStreamingResponse);
                                } else if (streamingResponse.getStreamingType().equals("guestlogin")) {
                                    //StreamerBroadcastResponse broadcastResponse = new StreamerBroadcastResponse();
                                    //broadcastResponse.fromJSON(streamingResponse.getResponse());
                                    EventBus.getDefault().post("guestlogin");
                                } else if (streamingResponse.getStreamingType().equals("AlertExecuted")) {
                                    AlertExecutedStreamingResponse alertExecutedStreamingResponse = new AlertExecutedStreamingResponse();
                                    alertExecutedStreamingResponse.fromJSON(streamingResponse.getResponse());
                                    EventBus.getDefault().post(alertExecutedStreamingResponse);
                                } else if (streamingResponse.getStreamingType().equals("TopGainersResponse")) {

                                    Gson gson = new Gson();
                                    TopGainersModels topGainersModels = gson.fromJson(resp, TopGainersModels.class);
                                    EventBus.getDefault().post(topGainersModels);


                                } else if (streamingResponse.getStreamingType().equals("TopLosersResponse")) {
                                    Gson gson = new Gson();
                                    TopLosersModel topLosersModel = gson.fromJson(resp, TopLosersModel.class);
                                    EventBus.getDefault().post(topLosersModel);

                                } else if (streamingResponse.getStreamingType().equals("MarketMoversResponse")) {
                                    Gson gson = new Gson();
                                    MarketMoversModel marketMoversModel = gson.fromJson(resp, MarketMoversModel.class);
                                    EventBus.getDefault().post(marketMoversModel);

                                } else if (streamingResponse.getStreamingType().equals("MarketMoversByValueResponse")) {
                                    Gson gson = new Gson();
                                    MarketMoversByValueModel marketMoversByValueModel = gson.fromJson(resp, MarketMoversByValueModel.class);
                                    EventBus.getDefault().post(marketMoversByValueModel);
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
                sendRequest(arg0[0].concat("\n"));
                startReading();
            }
            return null;
        }
    }
}