import "dart:convert";

import 'package:e_kyc/Login/UI/Configuration/AppConfig.dart';
import 'package:e_kyc/Login/UI/Login/model/LoginUserDetailModelResponse.dart';
import 'package:e_kyc/Login/UI/Login/repository/LoginRepository.dart';
import 'package:e_kyc/Login/UI/PanDetailsScreen/model/PanDetailRequestModel.dart';
import 'package:e_kyc/Utilities/ResponseModel.dart';
import "package:http/http.dart" as http;

class PanDetailRepository {
  static String userPanNumber;

  savePanDetailsAPI(String email, String mobile, String pan, String fullname,
      String dob) async {
    PanDetailData data = new PanDetailData();

    data.emailId = email;
    data.mobileNo = mobile;
    data.pan = pan;
    data.panfullname = fullname;
    data.dob = dob;

    PanDetailRequestModel request1 = new PanDetailRequestModel();
    request1.data = data;

    final jsonRequest = json.encode(request1);
    print('Final E-Kyc Request - $jsonRequest');

    var headers = {'Content-Type': 'application/json'};
    var apiName = AppConfig().url + "SavePanDetails";
    var request = http.Request('POST', Uri.parse(apiName));
    request.body = jsonRequest;
    request.headers.addAll(headers);

    http.StreamedResponse response = await request.send();

    if (response.statusCode == 200) {
      userPanNumber = pan;
      final responseData = await response.stream.bytesToString();
      print('Response Data :- ${responseData.toString()}');
      final loginResponseObj = ResponseModel.fromJson(
        json.decode(
          responseData.toString(),
        ),
      );
      LoginRepository _repository = new LoginRepository();
      LoginUserDetailModelResponse obj = await _repository.getEkycUserDetails(
          LoginRepository.loginEmailId,
          LoginRepository.loginMobileNo,
          LoginRepository.loginFullName);
      LoginRepository.loginDetailsResObjGlobal =
          obj; // setting user response in static variable for globle use
      print(LoginRepository.loginDetailsResObjGlobal.response.errorCode);
      return loginResponseObj;
    } else {
      print(response.reasonPhrase);
    }
  }
}
