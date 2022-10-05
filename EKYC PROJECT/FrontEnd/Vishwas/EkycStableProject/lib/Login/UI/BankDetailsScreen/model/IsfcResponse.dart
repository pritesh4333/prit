class IsfcResponse {
  String aDDRESS;
  String bANK;
  String bANKCODE;
  String bRANCH;
  String cENTRE;
  String cITY;
  String cONTACT;
  String dISTRICT;
  String iFSC;
  bool iMPS;
  String mICR;
  bool nEFT;
  bool rTGS;
  String sTATE;
  String sWIFT;
  bool uPI;

  IsfcResponse(
      {this.aDDRESS,
      this.bANK,
      this.bANKCODE,
      this.bRANCH,
      this.cENTRE,
      this.cITY,
      this.cONTACT,
      this.dISTRICT,
      this.iFSC,
      this.iMPS,
      this.mICR,
      this.nEFT,
      this.rTGS,
      this.sTATE,
      this.sWIFT,
      this.uPI});

  IsfcResponse.fromJson(Map<String, dynamic> json) {
    aDDRESS = json['ADDRESS'];
    bANK = json['BANK'];
    bANKCODE = json['BANKCODE'];
    bRANCH = json['BRANCH'];
    cENTRE = json['CENTRE'];
    cITY = json['CITY'];
    cONTACT = json['CONTACT'];
    dISTRICT = json['DISTRICT'];
    iFSC = json['IFSC'];
    iMPS = json['IMPS'];
    mICR = json['MICR'];
    nEFT = json['NEFT'];
    rTGS = json['RTGS'];
    sTATE = json['STATE'];
    sWIFT = json['SWIFT'];
    uPI = json['UPI'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['ADDRESS'] = this.aDDRESS;
    data['BANK'] = this.bANK;
    data['BANKCODE'] = this.bANKCODE;
    data['BRANCH'] = this.bRANCH;
    data['CENTRE'] = this.cENTRE;
    data['CITY'] = this.cITY;
    data['CONTACT'] = this.cONTACT;
    data['DISTRICT'] = this.dISTRICT;
    data['IFSC'] = this.iFSC;
    data['IMPS'] = this.iMPS;
    data['MICR'] = this.mICR;
    data['NEFT'] = this.nEFT;
    data['RTGS'] = this.rTGS;
    data['STATE'] = this.sTATE;
    data['SWIFT'] = this.sWIFT;
    data['UPI'] = this.uPI;
    return data;
  }
}
