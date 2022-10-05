class EkycLoginDetailsResponseModel {
  List<Response> response;

  EkycLoginDetailsResponseModel({this.response});

  EkycLoginDetailsResponseModel.fromJson(Map<String, dynamic> json) {
    if (json['response'] != null) {
      response = <Response>[];
      json['response'].forEach((v) {
        response.add(new Response.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    if (this.response != null) {
      data['response'] = this.response.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class Response {
  String fullName;
  String emailId;
  String mobileNo;
  int clientCode;
  int mobileOtp;
  int emailOtp;

  Response(
      {this.fullName,
      this.emailId,
      this.mobileNo,
      this.clientCode,
      this.mobileOtp,
      this.emailOtp});

  Response.fromJson(Map<String, dynamic> json) {
    fullName = json['full_name'];
    emailId = json['email_id'];
    mobileNo = json['mobile_no'];
    clientCode = json['client_code'];
    mobileOtp = json['mobile_otp'];
    emailOtp = json['email_otp'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['full_name'] = this.fullName;
    data['email_id'] = this.emailId;
    data['mobile_no'] = this.mobileNo;
    data['client_code'] = this.clientCode;
    data['mobile_otp'] = this.mobileOtp;
    data['email_otp'] = this.emailOtp;
    return data;
  }
}
