class UserDataResponse {
  Response? response;

  UserDataResponse({this.response});

  UserDataResponse.fromJson(Map<String, dynamic> json) {
    response = json['response'] != null ? new Response.fromJson(json['response']) : null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    if (this.response != null) {
      data['response'] = this.response!.toJson();
    }
    return data;
  }
}

class Response {
  String? svcName;
  int? error;
  Data? data;

  Response({this.svcName, this.error, this.data});

  Response.fromJson(Map<String, dynamic> json) {
    svcName = json['svcName'];
    error = json['error'];
    data = json['data'] != null ? new Data.fromJson(json['data']) : null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['svcName'] = this.svcName;
    data['error'] = this.error;
    if (this.data != null) {
      data['data'] = this.data!.toJson();
    }
    return data;
  }
}

class Data {
  List<Reportdata>? reportdata;

  Data({this.reportdata});

  Data.fromJson(Map<String, dynamic> json) {
    if (json['reportdata'] != null) {
      reportdata = <Reportdata>[];
      json['reportdata'].forEach((v) {
        reportdata!.add(new Reportdata.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    if (this.reportdata != null) {
      data['reportdata'] = this.reportdata!.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class Reportdata {
  int? uniqueId;
  String? stage;
  String? fullName;
  String? emailId;
  String? mobileNo;
  String? clientCode;
  String? mobileOtp;
  String? emailOtp;
  String? otpVerified;
  String? lextra1;
  String? lextra2;
  String? pan;
  String? panfullname;
  String? dob;
  String? pextra1;
  String? pextra2;
  String? nseCash;
  String? nseFo;
  String? nseCurrency;
  String? mcxCommodty;
  String? bseCash;
  String? bseFo;
  String? bseCurrency;
  String? ncdexCommodty;
  String? resAddr1;
  String? resAddr2;
  String? resAddrState;
  String? resAddrCity;
  String? resAddrPincode;
  String? parmAddr1;
  String? parmAddr2;
  String? parmAddrState;
  String? parmAddrCity;
  String? parmAddrPincode;
  String? nationality;
  String? gender;
  String? firstname1;
  String? middlename1;
  String? lastname1;
  String? maritalstatus;
  String? fatherspouse;
  String? firstname2;
  String? middlename2;
  String? lastname2;
  String? incomerange;
  String? occupation;
  String? action;
  String? perextra1;
  String? perextra2;
  String? ifsccode;
  String? accountnumber;
  String? bankname;
  String? verifiedId;
  String? beneficiaryNameWithBank;
  String? verifiedAt;
  String? bextra1;
  String? bextra2;
  String? pack;
  String? payextra1;
  String? payextra2;
  String? pancard;
  String? signature;
  String? bankproof;
  String? bankprooftype;
  String? addressproof;
  String? addressprooftype;
  String? incomeproof;
  String? incomeprooftype;
  String? photograph;
  String? uextra1;
  String? uextra2;
  String? ipv;
  String? iextra1;
  String? iextra2;
  String? esign;
  String? esignaddhar;
  String? ekycdocument;
  String? latitude;
  String? longitude;
  String? bankAddress;
  String? micrNo;
  String? bankCity;
  String? bankState;
  String? bankCountry;
  String? addressProofId;
  String? nomineeName;
  String? nomineeRelation;
  String? nomineeIdentityProof;

  Reportdata({this.uniqueId, this.stage, this.fullName, this.emailId, this.mobileNo, this.clientCode, this.mobileOtp, this.emailOtp, this.otpVerified, this.lextra1, this.lextra2, this.pan, this.panfullname, this.dob, this.pextra1, this.pextra2, this.nseCash, this.nseFo, this.nseCurrency, this.mcxCommodty, this.bseCash, this.bseFo, this.bseCurrency, this.ncdexCommodty, this.resAddr1, this.resAddr2, this.resAddrState, this.resAddrCity, this.resAddrPincode, this.parmAddr1, this.parmAddr2, this.parmAddrState, this.parmAddrCity, this.parmAddrPincode, this.nationality, this.gender, this.firstname1, this.middlename1, this.lastname1, this.maritalstatus, this.fatherspouse, this.firstname2, this.middlename2, this.lastname2, this.incomerange, this.occupation, this.action, this.perextra1, this.perextra2, this.ifsccode, this.accountnumber, this.bankname, this.verifiedId, this.beneficiaryNameWithBank, this.verifiedAt, this.bextra1, this.bextra2, this.pack, this.payextra1, this.payextra2, this.pancard, this.signature, this.bankproof, this.bankprooftype, this.addressproof, this.addressprooftype, this.incomeproof, this.incomeprooftype, this.photograph, this.uextra1, this.uextra2, this.ipv, this.iextra1, this.iextra2, this.esign, this.esignaddhar, this.ekycdocument, this.latitude, this.longitude, this.bankAddress, this.micrNo, this.bankCity, this.bankState, this.bankCountry, this.addressProofId, this.nomineeName, this.nomineeRelation, this.nomineeIdentityProof});

  Reportdata.fromJson(Map<String, dynamic> json) {
    uniqueId = json['unique_id'];
    stage = json['stage'];
    fullName = json['full_name'];
    emailId = json['email_id'];
    mobileNo = json['mobile_no'];
    clientCode = json['client_code'];
    mobileOtp = json['mobile_otp'];
    emailOtp = json['email_otp'];
    otpVerified = json['otp_verified'];
    lextra1 = json['lextra1'];
    lextra2 = json['lextra2'];
    pan = json['pan'];
    panfullname = json['panfullname'];
    dob = json['dob'];
    pextra1 = json['pextra1'];
    pextra2 = json['pextra2'];
    nseCash = json['nse_cash'];
    nseFo = json['nse_fo'];
    nseCurrency = json['nse_currency'];
    mcxCommodty = json['mcx_commodty'];
    bseCash = json['bse_cash'];
    bseFo = json['bse_fo'];
    bseCurrency = json['bse_currency'];
    ncdexCommodty = json['ncdex_commodty'];
    resAddr1 = json['res_addr_1'];
    resAddr2 = json['res_addr_2'];
    resAddrState = json['res_addr_state'];
    resAddrCity = json['res_addr_city'];
    resAddrPincode = json['res_addr_pincode'];
    parmAddr1 = json['parm_addr_1'];
    parmAddr2 = json['parm_addr_2'];
    parmAddrState = json['parm_addr_state'];
    parmAddrCity = json['parm_addr_city'];
    parmAddrPincode = json['parm_addr_pincode'];
    nationality = json['nationality'];
    gender = json['gender'];
    firstname1 = json['firstname1'];
    middlename1 = json['middlename1'];
    lastname1 = json['lastname1'];
    maritalstatus = json['maritalstatus'];
    fatherspouse = json['fatherspouse'];
    firstname2 = json['firstname2'];
    middlename2 = json['middlename2'];
    lastname2 = json['lastname2'];
    incomerange = json['incomerange'];
    occupation = json['occupation'];
    action = json['action'];
    perextra1 = json['perextra1'];
    perextra2 = json['perextra2'];
    ifsccode = json['ifsccode'];
    accountnumber = json['accountnumber'];
    bankname = json['bankname'];
    verifiedId = json['verified_id'];
    beneficiaryNameWithBank = json['beneficiary_name_with_bank'];
    verifiedAt = json['verified_at'];
    bextra1 = json['bextra1'];
    bextra2 = json['bextra2'];
    pack = json['pack'];
    payextra1 = json['payextra1'];
    payextra2 = json['payextra2'];
    pancard = json['pancard'];
    signature = json['signature'];
    bankproof = json['bankproof'];
    bankprooftype = json['bankprooftype'];
    addressproof = json['addressproof'];
    addressprooftype = json['addressprooftype'];
    incomeproof = json['incomeproof'];
    incomeprooftype = json['incomeprooftype'];
    photograph = json['photograph'];
    uextra1 = json['uextra1'];
    uextra2 = json['uextra2'];
    ipv = json['ipv'];
    iextra1 = json['iextra1'];
    iextra2 = json['iextra2'];
    esign = json['esign'];
    esignaddhar = json['esignaddhar'];
    ekycdocument = json['ekycdocument'];
    latitude = json['latitude'];
    longitude = json['longitude'];
    bankAddress = json['bank_address'];
    micrNo = json['micr_no'];
    bankCity = json['bank_city'];
    bankState = json['bank_state'];
    bankCountry = json['bank_country'];
    addressProofId = json['address_proof_id'];
    nomineeName = json['nominee_name'];
    nomineeRelation = json['nominee_relation'];
    nomineeIdentityProof = json['nominee_identity_proof'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['unique_id'] = this.uniqueId;
    data['stage'] = this.stage;
    data['full_name'] = this.fullName;
    data['email_id'] = this.emailId;
    data['mobile_no'] = this.mobileNo;
    data['client_code'] = this.clientCode;
    data['mobile_otp'] = this.mobileOtp;
    data['email_otp'] = this.emailOtp;
    data['otp_verified'] = this.otpVerified;
    data['lextra1'] = this.lextra1;
    data['lextra2'] = this.lextra2;
    data['pan'] = this.pan;
    data['panfullname'] = this.panfullname;
    data['dob'] = this.dob;
    data['pextra1'] = this.pextra1;
    data['pextra2'] = this.pextra2;
    data['nse_cash'] = this.nseCash;
    data['nse_fo'] = this.nseFo;
    data['nse_currency'] = this.nseCurrency;
    data['mcx_commodty'] = this.mcxCommodty;
    data['bse_cash'] = this.bseCash;
    data['bse_fo'] = this.bseFo;
    data['bse_currency'] = this.bseCurrency;
    data['ncdex_commodty'] = this.ncdexCommodty;
    data['res_addr_1'] = this.resAddr1;
    data['res_addr_2'] = this.resAddr2;
    data['res_addr_state'] = this.resAddrState;
    data['res_addr_city'] = this.resAddrCity;
    data['res_addr_pincode'] = this.resAddrPincode;
    data['parm_addr_1'] = this.parmAddr1;
    data['parm_addr_2'] = this.parmAddr2;
    data['parm_addr_state'] = this.parmAddrState;
    data['parm_addr_city'] = this.parmAddrCity;
    data['parm_addr_pincode'] = this.parmAddrPincode;
    data['nationality'] = this.nationality;
    data['gender'] = this.gender;
    data['firstname1'] = this.firstname1;
    data['middlename1'] = this.middlename1;
    data['lastname1'] = this.lastname1;
    data['maritalstatus'] = this.maritalstatus;
    data['fatherspouse'] = this.fatherspouse;
    data['firstname2'] = this.firstname2;
    data['middlename2'] = this.middlename2;
    data['lastname2'] = this.lastname2;
    data['incomerange'] = this.incomerange;
    data['occupation'] = this.occupation;
    data['action'] = this.action;
    data['perextra1'] = this.perextra1;
    data['perextra2'] = this.perextra2;
    data['ifsccode'] = this.ifsccode;
    data['accountnumber'] = this.accountnumber;
    data['bankname'] = this.bankname;
    data['verified_id'] = this.verifiedId;
    data['beneficiary_name_with_bank'] = this.beneficiaryNameWithBank;
    data['verified_at'] = this.verifiedAt;
    data['bextra1'] = this.bextra1;
    data['bextra2'] = this.bextra2;
    data['pack'] = this.pack;
    data['payextra1'] = this.payextra1;
    data['payextra2'] = this.payextra2;
    data['pancard'] = this.pancard;
    data['signature'] = this.signature;
    data['bankproof'] = this.bankproof;
    data['bankprooftype'] = this.bankprooftype;
    data['addressproof'] = this.addressproof;
    data['addressprooftype'] = this.addressprooftype;
    data['incomeproof'] = this.incomeproof;
    data['incomeprooftype'] = this.incomeprooftype;
    data['photograph'] = this.photograph;
    data['uextra1'] = this.uextra1;
    data['uextra2'] = this.uextra2;
    data['ipv'] = this.ipv;
    data['iextra1'] = this.iextra1;
    data['iextra2'] = this.iextra2;
    data['esign'] = this.esign;
    data['esignaddhar'] = this.esignaddhar;
    data['ekycdocument'] = this.ekycdocument;
    data['latitude'] = this.latitude;
    data['longitude'] = this.longitude;
    data['bank_address'] = this.bankAddress;
    data['micr_no'] = this.micrNo;
    data['bank_city'] = this.bankCity;
    data['bank_state'] = this.bankState;
    data['bank_country'] = this.bankCountry;
    data['address_proof_id'] = this.addressProofId;
    data['nominee_name'] = this.nomineeName;
    data['nominee_relation'] = this.nomineeRelation;
    data['nominee_identity_proof'] = this.nomineeIdentityProof;
    return data;
  }
}
