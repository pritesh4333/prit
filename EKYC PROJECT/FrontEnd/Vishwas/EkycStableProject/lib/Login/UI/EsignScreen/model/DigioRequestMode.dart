class DigioRequestMode {
  Dataobj response;

  DigioRequestMode({this.response});

  DigioRequestMode.fromJson(Map<String, dynamic> json) {
    response = json['data'] != null ? new Dataobj.fromJson(json['data']) : null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    if (this.response != null) {
      data['data'] = this.response.toJson();
    }
    return data;
  }
}

class Dataobj {
  String emailId;
  String mobileNo;
  String name;
  String pdfpath;

  Dataobj({this.emailId, this.mobileNo, this.name, this.pdfpath});

  Dataobj.fromJson(Map<String, dynamic> json) {
    emailId = json['email_id'];
    mobileNo = json['mobile_no'];
    name = json['name'];
    pdfpath = json['pdfpath'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['email_id'] = this.emailId;
    data['mobile_no'] = this.mobileNo;
    data['name'] = this.name;
    data['pdfpath'] = this.pdfpath;
    return data;
  }
}