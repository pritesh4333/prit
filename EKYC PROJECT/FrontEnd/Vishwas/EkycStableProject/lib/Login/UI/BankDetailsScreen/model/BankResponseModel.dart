class BankResponseModel {
  BankResponseResponse response;

  BankResponseModel({this.response});

  BankResponseModel.fromJson(Map<String, dynamic> json) {
    response = json['response'] != null
        ? new BankResponseResponse.fromJson(json['response'])
        : null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    if (this.response != null) {
      data['response'] = this.response.toJson();
    }
    return data;
  }
}

class BankResponseResponse {
  String errorCode;
  BankResponseResponseData data;

  BankResponseResponse({this.errorCode, this.data});

  BankResponseResponse.fromJson(Map<String, dynamic> json) {
    errorCode = json['error_code'];
    data = json['data'] != null ? new BankResponseResponseData.fromJson(json['data']) : null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['error_code'] = this.errorCode;
    if (this.data != null) {
      data['data'] = this.data.toJson();
    }
    return data;
  }
}

class BankResponseResponseData {
  String status;
  String details;
  String code;
  String message;

  BankResponseResponseData({this.status, this.details, this.code, this.message});

  BankResponseResponseData.fromJson(Map<String, dynamic> json) {
    status = json['status'];
    details = json['details'];
    code = json['code'];
    message = json['message'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['status'] = this.status;
    data['details'] = this.details;
    data['code'] = this.code;
    data['message'] = this.message;
    return data;
  }
}