class LoginResponseModel {
  int? clientCode;
  int? executioncode;
  int? errorCode;
  int? allowedMarket;
  String? cCategory;
  Object? mandateId;
  int? userType;
  String? panNo;
  String? kYCStatus;
  String? dob;
  String? gscid;
  String? clientName;
  String? isSameDevice;
  String? sessionId;

  LoginResponseModel(
      {this.clientCode,
      this.executioncode,
      this.errorCode,
      this.allowedMarket,
      this.cCategory,
      this.mandateId,
      this.userType,
      this.panNo,
      this.kYCStatus,
      this.dob,
      this.gscid,
      this.clientName,
      this.isSameDevice,
      this.sessionId});

  LoginResponseModel.fromJson(Map<String, dynamic> json) {
    clientCode = json['ClientCode'];
    executioncode = json['Executioncode'];
    errorCode = json['ErrorCode'];
    allowedMarket = json['AllowedMarket'];
    cCategory = json['cCategory'];
    mandateId = json['mandateId'];
    userType = json['userType'];
    panNo = json['panNo'];
    kYCStatus = json['KYCStatus'];
    dob = json['dob'];
    gscid = json['gscid'];
    clientName = json['clientName'];
    isSameDevice = json['IsSameDevice'];
    sessionId = json['sessionId'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['ClientCode'] = clientCode;
    data['Executioncode'] = executioncode;
    data['ErrorCode'] = errorCode;
    data['AllowedMarket'] = allowedMarket;
    data['cCategory'] = cCategory;
    data['mandateId'] = mandateId;
    data['userType'] = userType;
    data['panNo'] = panNo;
    data['KYCStatus'] = kYCStatus;
    data['dob'] = dob;
    data['gscid'] = gscid;
    data['clientName'] = clientName;
    data['IsSameDevice'] = isSameDevice;
    data['sessionId'] = sessionId;
    return data;
  }
}
