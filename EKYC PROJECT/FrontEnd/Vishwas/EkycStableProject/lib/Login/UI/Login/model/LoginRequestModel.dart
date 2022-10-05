class LoginRequestModel {
  Request request;

  LoginRequestModel({this.request});

  LoginRequestModel.fromJson(Map<String, dynamic> json) {
    request =
        json['request'] != null ? new Request.fromJson(json['request']) : null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    if (this.request != null) {
      data['request'] = this.request.toJson();
    }
    return data;
  }
}

class Request {
  Data data;
  String svcName;
  String svcGroup;

  Request({this.data, this.svcName, this.svcGroup});

  Request.fromJson(Map<String, dynamic> json) {
    data = json['data'] != null ? new Data.fromJson(json['data']) : null;
    svcName = json['svcName'];
    svcGroup = json['svcGroup'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    if (this.data != null) {
      data['data'] = this.data.toJson();
    }
    data['svcName'] = this.svcName;
    data['svcGroup'] = this.svcGroup;
    return data;
  }
}

class Data {
  String panDob;
  String deviceId;
  String gscid;
  String deviceDetails;
  String deviceType;
  String pass;
  String transPass;
  String userType;
  String brokerid;
  String passType;
  String versionNo;
  String encryptionType;

  Data(
      {this.panDob,
      this.deviceId,
      this.gscid,
      this.deviceDetails,
      this.deviceType,
      this.pass,
      this.transPass,
      this.userType,
      this.brokerid,
      this.passType,
      this.versionNo,
      this.encryptionType});

  Data.fromJson(Map<String, dynamic> json) {
    panDob = json['pan_dob'];
    deviceId = json['deviceId'];
    gscid = json['gscid'];
    deviceDetails = json['deviceDetails'];
    deviceType = json['deviceType'];
    pass = json['pass'];
    transPass = json['transPass'];
    userType = json['userType'];
    brokerid = json['brokerid'];
    passType = json['passType'];
    versionNo = json['version_no'];
    encryptionType = json['encryptionType'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['pan_dob'] = this.panDob;
    data['deviceId'] = this.deviceId;
    data['gscid'] = this.gscid;
    data['deviceDetails'] = this.deviceDetails;
    data['deviceType'] = this.deviceType;
    data['pass'] = this.pass;
    data['transPass'] = this.transPass;
    data['userType'] = this.userType;
    data['brokerid'] = this.brokerid;
    data['passType'] = this.passType;
    data['version_no'] = this.versionNo;
    data['encryptionType'] = this.encryptionType;
    return data;
  }
}
