class EsignRequestModel {
  EsignDetailData data;

  EsignRequestModel({this.data});

  EsignRequestModel.fromJson(Map<String, dynamic> json) {
    data = json['data'] != null ? new EsignDetailData.fromJson(json['data']) : null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    if (this.data != null) {
      data['data'] = this.data.toJson();
    }
    return data;
  }
}

class EsignDetailData {
  String emailId;
  String mobileNo;
  String esign;
 

  EsignDetailData({this.emailId, this.mobileNo, this.esign});

  EsignDetailData.fromJson(Map<String, dynamic> json) {
    emailId = json['email_id'];
    mobileNo = json['mobile_no'];
    esign = json['esign'];
  
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['email_id'] = this.emailId;
    data['mobile_no'] = this.mobileNo;
    data['esign'] = this.esign;
 
    return data;
  }
}