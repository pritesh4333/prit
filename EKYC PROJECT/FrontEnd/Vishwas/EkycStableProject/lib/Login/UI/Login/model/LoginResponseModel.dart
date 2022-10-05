class LoginResponseModel {
  Response response;
  Config config;

  LoginResponseModel({this.response, this.config});

  LoginResponseModel.fromJson(Map<String, dynamic> json) {
    response = json['response'] != null
        ? new Response.fromJson(json['response'])
        : null;
    config =
        json['config'] != null ? new Config.fromJson(json['config']) : null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    if (this.response != null) {
      data['response'] = this.response.toJson();
    }
    if (this.config != null) {
      data['config'] = this.config.toJson();
    }
    return data;
  }
}

class Response {
  String svcName;
  String serverTime;
  String infoID;
  String appID;
  String svcVersion;
  String msgID;
  String svcGroup;
  String sessionId;
  int errorCode;
  ResponseData data;

  Response(
      {this.svcName,
      this.serverTime,
      this.infoID,
      this.appID,
      this.svcVersion,
      this.msgID,
      this.svcGroup,
      this.sessionId,
      this.errorCode,
      this.data});

  Response.fromJson(Map<String, dynamic> json) {
    svcName = json['svcName'];
    serverTime = json['serverTime'];
    infoID = json['infoID'];
    appID = json['appID'];
    svcVersion = json['svcVersion'];
    msgID = json['msgID'];
    svcGroup = json['svcGroup'];
    sessionId = json['sessionId'];
    errorCode = json['ErrorCode'];
    data = json['data'] != null ? new ResponseData.fromJson(json['data']) : null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['svcName'] = this.svcName;
    data['serverTime'] = this.serverTime;
    data['infoID'] = this.infoID;
    data['appID'] = this.appID;
    data['svcVersion'] = this.svcVersion;
    data['msgID'] = this.msgID;
    data['svcGroup'] = this.svcGroup;
    data['sessionId'] = this.sessionId;
    data['ErrorCode'] = this.errorCode;
    if (this.data != null) {
      data['data'] = this.data.toJson();
    }
    return data;
  }
}

class ResponseData {
  int clientCode;
  int executioncode;
  int errorCode;
  String orderTime;
  String theme;
  List<AllowedMarket> allowedMarket;
  String defaultProduct;
  String holdingFlag;
  String validate2FA;
  String validateTransaction;
  String cCategory;
  int mandateId;
  int userType;
  String panNo;
  String kYCStatus;
  String dob;
  String gscid;
  String quote;
  String clientName;
  String arachneIP;
  String apolloIP;
  String irisIP;
  int arachnePort;
  int orderSenderPort;
  int broadcastSenderPort;
  int irisPort;
  int apolloPort;
  String chartSetting;
  String isStrategyProduct;
  String isEDISProduct;
  String isSameDevice;
  String isValidateSecondary;
  String isRedisEnabled;
  String isMPINSet;
  String isBOReport;
  String isStrategyLogin;
  String dPType;

  ResponseData(
      {this.clientCode,
      this.executioncode,
      this.errorCode,
      this.orderTime,
      this.theme,
      this.allowedMarket,
      this.defaultProduct,
      this.holdingFlag,
      this.validate2FA,
      this.validateTransaction,
      this.cCategory,
      this.mandateId,
      this.userType,
      this.panNo,
      this.kYCStatus,
      this.dob,
      this.gscid,
      this.quote,
      this.clientName,
      this.arachneIP,
      this.apolloIP,
      this.irisIP,
      this.arachnePort,
      this.orderSenderPort,
      this.broadcastSenderPort,
      this.irisPort,
      this.apolloPort,
      this.chartSetting,
      this.isStrategyProduct,
      this.isEDISProduct,
      this.isSameDevice,
      this.isValidateSecondary,
      this.isRedisEnabled,
      this.isMPINSet,
      this.isBOReport,
      this.isStrategyLogin,
      this.dPType});

  ResponseData.fromJson(Map<String, dynamic> json) {
    clientCode = json['ClientCode'];
    executioncode = json['Executioncode'];
    errorCode = json['ErrorCode'];
    orderTime = json['OrderTime'];
    theme = json['Theme'];
    if (json['AllowedMarket'] != null) {
      allowedMarket = <AllowedMarket>[];
      json['AllowedMarket'].forEach((v) {
        allowedMarket.add(new AllowedMarket.fromJson(v));
      });
    }
    defaultProduct = json['defaultProduct'];
    holdingFlag = json['holdingFlag'];
    validate2FA = json['validate2FA'];
    validateTransaction = json['validateTransaction'];
    cCategory = json['cCategory'];
    mandateId = json['mandateId'];
    userType = json['userType'];
    panNo = json['panNo'];
    kYCStatus = json['KYCStatus'];
    dob = json['dob'];
    gscid = json['gscid'];
    quote = json['quote'];
    clientName = json['clientName'];
    arachneIP = json['Arachne_IP'];
    apolloIP = json['Apollo_IP'];
    irisIP = json['Iris_IP'];
    arachnePort = json['Arachne_Port'];
    orderSenderPort = json['OrderSender_Port'];
    broadcastSenderPort = json['BroadcastSender_Port'];
    irisPort = json['Iris_Port'];
    apolloPort = json['Apollo_Port'];
    chartSetting = json['ChartSetting'];
    isStrategyProduct = json['IsStrategyProduct'];
    isEDISProduct = json['IsEDISProduct'];
    isSameDevice = json['IsSameDevice'];
    isValidateSecondary = json['IsValidateSecondary'];
    isRedisEnabled = json['IsRedisEnabled'];
    isMPINSet = json['isMPINSet'];
    isBOReport = json['IsBOReport'];
    isStrategyLogin = json['isStrategyLogin'];
    dPType = json['DPType'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['ClientCode'] = this.clientCode;
    data['Executioncode'] = this.executioncode;
    data['ErrorCode'] = this.errorCode;
    data['OrderTime'] = this.orderTime;
    data['Theme'] = this.theme;
    if (this.allowedMarket != null) {
      data['AllowedMarket'] =
          this.allowedMarket.map((v) => v.toJson()).toList();
    }
    data['defaultProduct'] = this.defaultProduct;
    data['holdingFlag'] = this.holdingFlag;
    data['validate2FA'] = this.validate2FA;
    data['validateTransaction'] = this.validateTransaction;
    data['cCategory'] = this.cCategory;
    data['mandateId'] = this.mandateId;
    data['userType'] = this.userType;
    data['panNo'] = this.panNo;
    data['KYCStatus'] = this.kYCStatus;
    data['dob'] = this.dob;
    data['gscid'] = this.gscid;
    data['quote'] = this.quote;
    data['clientName'] = this.clientName;
    data['Arachne_IP'] = this.arachneIP;
    data['Apollo_IP'] = this.apolloIP;
    data['Iris_IP'] = this.irisIP;
    data['Arachne_Port'] = this.arachnePort;
    data['OrderSender_Port'] = this.orderSenderPort;
    data['BroadcastSender_Port'] = this.broadcastSenderPort;
    data['Iris_Port'] = this.irisPort;
    data['Apollo_Port'] = this.apolloPort;
    data['ChartSetting'] = this.chartSetting;
    data['IsStrategyProduct'] = this.isStrategyProduct;
    data['IsEDISProduct'] = this.isEDISProduct;
    data['IsSameDevice'] = this.isSameDevice;
    data['IsValidateSecondary'] = this.isValidateSecondary;
    data['IsRedisEnabled'] = this.isRedisEnabled;
    data['isMPINSet'] = this.isMPINSet;
    data['IsBOReport'] = this.isBOReport;
    data['isStrategyLogin'] = this.isStrategyLogin;
    data['DPType'] = this.dPType;
    return data;
  }
}

class AllowedMarket {
  int marketId;

  AllowedMarket({this.marketId});

  AllowedMarket.fromJson(Map<String, dynamic> json) {
    marketId = json['market_id'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['market_id'] = this.marketId;
    return data;
  }
}

class Config {
  int label;
  int message;
  int app;

  Config({this.label, this.message, this.app});

  Config.fromJson(Map<String, dynamic> json) {
    label = json['label'];
    message = json['message'];
    app = json['app'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['label'] = this.label;
    data['message'] = this.message;
    data['app'] = this.app;
    return data;
  }
}
