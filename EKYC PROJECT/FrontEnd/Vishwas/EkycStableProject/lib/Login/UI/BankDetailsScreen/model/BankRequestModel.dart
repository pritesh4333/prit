class BankRequestModel {
  BankDetailData data;

  BankRequestModel({this.data});

  BankRequestModel.fromJson(Map<String, dynamic> json) {
    data = json['data'] != null ? new BankDetailData.fromJson(json['data']) : null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    if (this.data != null) {
      data['data'] = this.data.toJson();
    }
    return data;
  }
}

class BankDetailData {
  String emailId;
  String mobileNo;
  String ifsccode;
  String accountnumber;
  String bankname;
  String banknames;
  String address;
  String micr;
  String bank_address_state;
  String bank_address_city;
  String bank_address_pincode;
  String bank_address_contactno;
  String bank_branch;

  BankDetailData({this.emailId, this.mobileNo, this.ifsccode, this.accountnumber, this.bankname, this.banknames, this.address, this.micr, this.bank_address_state, this.bank_address_city, this.bank_address_pincode, this.bank_address_contactno,this.bank_branch});

  BankDetailData.fromJson(Map<String, dynamic> json) {
    emailId = json['email_id'];
    mobileNo = json['mobile_no'];
    ifsccode = json['ifsccode'];
    accountnumber = json['accountnumber'];
    bankname = json['bankname'];
    banknames = json['banknames'];
    address = json['address'];
    micr = json['micr'];
    bank_address_state = json['bank_address_state'];
    bank_address_city = json['bank_address_city'];
    bank_address_pincode = json['bank_address_pincode'];
    bank_address_contactno = json['bank_address_contactno'];
    bank_branch = json['bank_branch'];

  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['email_id'] = this.emailId;
    data['mobile_no'] = this.mobileNo;
    data['ifsccode'] = this.ifsccode;
    data['accountnumber'] = this.accountnumber;
    data['bankname'] = this.bankname;
    data['banknames'] = this.banknames;
    data['address'] = this.address;
    data['micr'] = this.micr;
    data['bank_address_state'] = this.bank_address_state;
    data['bank_address_city'] = this.bank_address_city;
    data['bank_address_pincode'] = this.bank_address_pincode;
    data['bank_address_contactno'] = this.bank_address_contactno;
    data['bank_branch'] = this.bank_branch;


    return data;
  }
}
