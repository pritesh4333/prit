class DigioBodyResponse {
  String id;
  bool isAgreement;
  String agreementType;
  String agreementStatus;
  String fileName;
  String createdAt;
  bool selfSigned;
  String selfSignType;
  int noOfPages;
  List<SigningParties> signingParties;
  SignRequestDetails signRequestDetails;
  String channel;
  OtherDocDetails otherDocDetails;

  DigioBodyResponse(
      {this.id,
      this.isAgreement,
      this.agreementType,
      this.agreementStatus,
      this.fileName,
      this.createdAt,
      this.selfSigned,
      this.selfSignType,
      this.noOfPages,
      this.signingParties,
      this.signRequestDetails,
      this.channel,
      this.otherDocDetails});

  DigioBodyResponse.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    isAgreement = json['is_agreement'];
    agreementType = json['agreement_type'];
    agreementStatus = json['agreement_status'];
    fileName = json['file_name'];
    createdAt = json['created_at'];
    selfSigned = json['self_signed'];
    selfSignType = json['self_sign_type'];
    noOfPages = json['no_of_pages'];
    if (json['signing_parties'] != null) {
      signingParties = <SigningParties>[];
      json['signing_parties'].forEach((v) {
        signingParties.add(new SigningParties.fromJson(v));
      });
    }
    signRequestDetails = json['sign_request_details'] != null
        ? new SignRequestDetails.fromJson(json['sign_request_details'])
        : null;
    channel = json['channel'];
    otherDocDetails = json['other_doc_details'] != null
        ? new OtherDocDetails.fromJson(json['other_doc_details'])
        : null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['is_agreement'] = this.isAgreement;
    data['agreement_type'] = this.agreementType;
    data['agreement_status'] = this.agreementStatus;
    data['file_name'] = this.fileName;
    data['created_at'] = this.createdAt;
    data['self_signed'] = this.selfSigned;
    data['self_sign_type'] = this.selfSignType;
    data['no_of_pages'] = this.noOfPages;
    if (this.signingParties != null) {
      data['signing_parties'] =
          this.signingParties.map((v) => v.toJson()).toList();
    }
    if (this.signRequestDetails != null) {
      data['sign_request_details'] = this.signRequestDetails.toJson();
    }
    data['channel'] = this.channel;
    if (this.otherDocDetails != null) {
      data['other_doc_details'] = this.otherDocDetails.toJson();
    }
    return data;
  }
}

class SigningParties {
  String name;
  String status;
  String type;
  String signatureType;
  String identifier;
  String reason;
  String expireOn;

  SigningParties(
      {this.name,
      this.status,
      this.type,
      this.signatureType,
      this.identifier,
      this.reason,
      this.expireOn});

  SigningParties.fromJson(Map<String, dynamic> json) {
    name = json['name'];
    status = json['status'];
    type = json['type'];
    signatureType = json['signature_type'];
    identifier = json['identifier'];
    reason = json['reason'];
    expireOn = json['expire_on'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['name'] = this.name;
    data['status'] = this.status;
    data['type'] = this.type;
    data['signature_type'] = this.signatureType;
    data['identifier'] = this.identifier;
    data['reason'] = this.reason;
    data['expire_on'] = this.expireOn;
    return data;
  }
}

class SignRequestDetails {
  String name;
  String requestedOn;
  String expireOn;
  String identifier;
  String requesterType;

  SignRequestDetails(
      {this.name,
      this.requestedOn,
      this.expireOn,
      this.identifier,
      this.requesterType});

  SignRequestDetails.fromJson(Map<String, dynamic> json) {
    name = json['name'];
    requestedOn = json['requested_on'];
    expireOn = json['expire_on'];
    identifier = json['identifier'];
    requesterType = json['requester_type'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['name'] = this.name;
    data['requested_on'] = this.requestedOn;
    data['expire_on'] = this.expireOn;
    data['identifier'] = this.identifier;
    data['requester_type'] = this.requesterType;
    return data;
  }
}

class OtherDocDetails {
  OtherDocDetails();

  OtherDocDetails.fromJson(Map<String, dynamic> json) {}

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    return data;
  }
}
