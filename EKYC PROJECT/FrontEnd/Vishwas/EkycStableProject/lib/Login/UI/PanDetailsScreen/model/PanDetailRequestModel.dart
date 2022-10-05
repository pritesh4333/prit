class PanDetailRequestModel {
  PanDetailData data;

  PanDetailRequestModel({this.data});

  PanDetailRequestModel.fromJson(Map<String, dynamic> json) {
    data = json['data'] != null ? new PanDetailData.fromJson(json['data']) : null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    if (this.data != null) {
      data['data'] = this.data.toJson();
    }
    return data;
  }
}

class PanDetailData {
  String emailId;
  String mobileNo;
  String pan;
  String panfullname;
  String dob;

  PanDetailData({this.emailId, this.mobileNo, this.pan,this.panfullname, this.dob});

  PanDetailData.fromJson(Map<String, dynamic> json) {
    emailId = json['email_id'];
    mobileNo = json['mobile_no'];
    pan = json['pan'];
     panfullname = json['panfullname'];
    dob = json['dob'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['email_id'] = this.emailId;
    data['mobile_no'] = this.mobileNo;
    data['pan'] = this.pan;
     data['panfullname'] = this.panfullname;
    data['dob'] = this.dob;
    return data;
  }
}