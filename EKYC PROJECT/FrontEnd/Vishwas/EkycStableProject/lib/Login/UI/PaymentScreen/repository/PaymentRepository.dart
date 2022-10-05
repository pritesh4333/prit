import "dart:convert";

import 'package:e_kyc/Login/UI/Configuration/AppConfig.dart';
import 'package:e_kyc/Login/UI/PaymentScreen/model/PaymentRequestModel.dart';
import 'package:e_kyc/Utilities/ResponseModel.dart';
import "package:http/http.dart" as http;

class PaymentRepository {
  savePaymentDetailsAPI(String email, String mobile, String pack) async {
    PaymentDetailData data = new PaymentDetailData();

    data.emailId = email;
    data.mobileNo = mobile;
    data.pack = pack;

    PaymentRequestModel request1 = new PaymentRequestModel();
    request1.data = data;

    final jsonRequest = json.encode(request1);
    print('Final E-Kyc Request - $jsonRequest');

    var headers = {'Content-Type': 'application/json'};
    var apiName = AppConfig().url + "SavePaymentDetails";
    var request = http.Request('POST', Uri.parse(apiName));

    request.body = jsonRequest;
    request.headers.addAll(headers);

    http.StreamedResponse response = await request.send();

    if (response.statusCode == 200) {
      final responseData = await response.stream.bytesToString();
      print('Response Data :- ${responseData.toString()}');
      final loginResponseObj = ResponseModel.fromJson(
        json.decode(
          responseData.toString(),
        ),
      );
      return loginResponseObj;
    } else {
      print(response.reasonPhrase);
    }
  }
}
