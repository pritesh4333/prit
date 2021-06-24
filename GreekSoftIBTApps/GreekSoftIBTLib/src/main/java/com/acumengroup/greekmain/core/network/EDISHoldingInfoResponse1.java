package com.acumengroup.greekmain.core.network;

import java.util.ArrayList;

public class EDISHoldingInfoResponse1 {

    public Response response;


    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }


    public static class Response {

        public  String appID;
        public  Data Data;
        public  String infoID;
        public  String serverTime;
        public  String streaming_type;
        public  String svcName;


        public String getAppID() {
            return appID;
        }

        public void setAppID(String appID) {
            this.appID = appID;
        }

        public Response.Data getData() {
            return Data;
        }

        public void setData(Response.Data data) {
            Data = data;
        }

        public String getInfoID() {
            return infoID;
        }

        public void setInfoID(String infoID) {
            this.infoID = infoID;
        }

        public String getServerTime() {
            return serverTime;
        }

        public void setServerTime(String serverTime) {
            this.serverTime = serverTime;
        }

        public String getStreaming_type() {
            return streaming_type;
        }

        public void setStreaming_type(String streaming_type) {
            this.streaming_type = streaming_type;
        }

        public String getSvcName() {
            return svcName;
        }

        public void setSvcName(String svcName) {
            this.svcName = svcName;
        }


        public static class Data{

            public  String islast;
            public  String noofrecords;
            public ArrayList<StockDetail> stockDetails;

            public String getIslast() {
                return islast;
            }

            public void setIslast(String islast) {
                this.islast = islast;
            }

            public String getNoofrecords() {
                return noofrecords;
            }

            public void setNoofrecords(String noofrecords) {
                this.noofrecords = noofrecords;
            }

            public ArrayList<StockDetail> getStockDetails() {
                return stockDetails;
            }

            public void setStockDetails(ArrayList<StockDetail> stockDetails) {
                this.stockDetails = stockDetails;
            }


            public static class StockDetail{

                public  String HQty;
                public  String OpenAuthQty;
                public  String TodaySoldQty;
                public  String TodayAuthQty;
                public  String instrument;
                public  String isin;
                public  String symbol;
                public  String close;
                public  String token;
                public  String sellmarket;
                public  String freeQTY;
                public  String PledgedQty;
                public  String Rate;
                public  String Value;
                public boolean chekbox;

                public String getPledgedQty() {
                    return PledgedQty;
                }

                public void setPledgedQty(String pledgedQty) {
                    PledgedQty = pledgedQty;
                }

                public String getRate() {
                    return Rate;
                }

                public void setRate(String rate) {
                    Rate = rate;
                }

                public String getValue() {
                    return Value;
                }

                public void setValue(String value) {
                    Value = value;
                }

                public boolean isChekbox() {
                    return chekbox;
                }

                public void setChekbox(boolean chekbox) {
                    this.chekbox = chekbox;
                }


                public String getFreeQTY() {
                    return freeQTY;
                }

                public void setFreeQTY(String freeQTY) {
                    this.freeQTY = freeQTY;
                }

                public String getHQty() {
                    return HQty;
                }

                public void setHQty(String HQty) {
                    this.HQty = HQty;
                }

                public String getOpenAuthQty() {
                    return OpenAuthQty;
                }

                public void setOpenAuthQty(String openAuthQty) {
                    OpenAuthQty = openAuthQty;
                }

                public String getTodaySoldQty() {
                    return TodaySoldQty;
                }

                public void setTodaySoldQty(String todaySoldQty) {
                    TodaySoldQty = todaySoldQty;
                }

                public String getTodayAuthQty() {
                    return TodayAuthQty;
                }

                public void setTodayAuthQty(String todayAuthQty) {
                    TodayAuthQty = todayAuthQty;
                }

                public String getInstrument() {
                    return instrument;
                }

                public void setInstrument(String instrument) {
                    this.instrument = instrument;
                }

                public String getIsin() {
                    return isin;
                }

                public void setIsin(String isin) {
                    this.isin = isin;
                }

                public String getSymbol() {
                    return symbol;
                }

                public void setSymbol(String symbol) {
                    this.symbol = symbol;
                }

                public String getClose() {
                    return close;
                }

                public void setClose(String close) {
                    this.close = close;
                }

                public String getToken() {
                    return token;
                }

                public void setToken(String token) {
                    this.token = token;
                }

                public String getSellmarket() {
                    return sellmarket;
                }

                public void setSellmarket(String sellmarket) {
                    this.sellmarket = sellmarket;
                }


            }
        }

    }
}
