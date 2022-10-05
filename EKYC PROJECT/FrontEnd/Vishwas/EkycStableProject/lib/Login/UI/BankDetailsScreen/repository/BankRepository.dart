// ignore_for_file: unused_import

import "dart:convert";

import 'package:e_kyc/Login/UI/BankDetailsScreen/model/BankRequestModel.dart';
import 'package:e_kyc/Login/UI/BankDetailsScreen/model/BankResponseModel.dart';
import 'package:e_kyc/Login/UI/BankDetailsScreen/model/IsfcResponse.dart';
import 'package:e_kyc/Login/UI/Configuration/AppConfig.dart';
import 'package:e_kyc/Utilities/ResponseModel.dart';
import "package:http/http.dart" as http;

class BankRepository {
  saveBankDetailsAPI(String email, String mobile, String ifsccode, String accountnumber, String bankname) async {
    // bank details getting from ifsc code
    IsfcResponse isfcdetails = await FechBankDetailsISFCcode(ifsccode);

    if (isfcdetails != null) {
      BankDetailData data = new BankDetailData();

      data.emailId = email;
      data.mobileNo = mobile;
      data.ifsccode = ifsccode;
      data.accountnumber = accountnumber;
      data.bankname = bankname;
      data.banknames = isfcdetails.bANK.replaceAll('\'', "");
      data.address = isfcdetails.aDDRESS.replaceAll('\'', "");
      data.micr = isfcdetails.mICR.toString();
      data.bank_address_state = isfcdetails.sTATE.toString();
      data.bank_address_city = isfcdetails.cITY.toString();
      data.bank_address_pincode = data.address.substring(data.address.length - 6);
      data.bank_address_contactno = isfcdetails.cONTACT.toString();
      data.bank_branch = isfcdetails.bRANCH.toString();

      BankRequestModel request1 = new BankRequestModel();
      request1.data = data;

      final jsonRequest = json.encode(request1);
      print('Final E-Kyc Request - $jsonRequest');

      var headers = {'Content-Type': 'application/json'};
      var apiName = AppConfig().url + "SaveBankDetails";
      var request = http.Request('POST', Uri.parse(apiName));
      request.body = jsonRequest;
      request.headers.addAll(headers);

      http.StreamedResponse response = await request.send();

      if (response.statusCode == 200) {
        final responseData = await response.stream.bytesToString();
        print('Response Data :- ${responseData.toString()}');
        final loginResponseObj = BankResponseModel.fromJson(
          json.decode(
            responseData.toString(),
          ),
        );
        return loginResponseObj;
      } else {
        print(response.reasonPhrase);
      }
    } else {
      return null;
    }
  }
}

Future<IsfcResponse> FechBankDetailsISFCcode(String ifsccode) async {
  var request = http.Request('GET', Uri.parse('https://bank-apis.justinclicks.com/API/V1/IFSC/' + ifsccode + ''));

  http.StreamedResponse response = await request.send();

  if (response.statusCode == 200) {
    final isfcresponse = await response.stream.bytesToString();
    final responseData = IsfcResponse.fromJson(json.decode(isfcresponse.toString()));
    return responseData;
  } else {
    return null;
  }
}
