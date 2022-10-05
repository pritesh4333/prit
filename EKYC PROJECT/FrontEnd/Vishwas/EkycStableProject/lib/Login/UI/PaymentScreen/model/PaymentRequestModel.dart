class PaymentRequestModel {
  PaymentDetailData data;

  PaymentRequestModel({this.data});

  PaymentRequestModel.fromJson(Map<String, dynamic> json) {
    data = json['data'] != null ? new PaymentDetailData.fromJson(json['data']) : null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    if (this.data != null) {
      data['data'] = this.data.toJson();
    }
    return data;
  }
}

class PaymentDetailData {
  String emailId;
  String mobileNo;
  String pack;
 

  PaymentDetailData({this.emailId, this.mobileNo, this.pack});

  PaymentDetailData.fromJson(Map<String, dynamic> json) {
    emailId = json['email_id'];
    mobileNo = json['mobile_no'];
    pack = json['pack'];
  
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['email_id'] = this.emailId;
    data['mobile_no'] = this.mobileNo;
    data['pack'] = this.pack;
 
    return data;
  }
}