import "dart:convert";
import 'package:e_kyc/Login/UI/Configuration/AppConfig.dart';
import 'package:e_kyc/Login/UI/Login/model/LoginDetailModeRequest.dart';
import 'package:e_kyc/Login/UI/Login/model/LoginUserDetailModelResponse.dart';
import 'package:e_kyc/Utilities/Location/location.dart';
import 'package:e_kyc/Utilities/ResponseModel.dart';
import "package:http/http.dart" as http;
// import 'package:location/location.dart';

class LoginRepository {
  static String loginFullName;
  static String loginEmailId;
  static String loginMobileNo;
  static int Esignflag = 0;

  static LoginUserDetailModelResponse loginDetailsResObjGlobal;
  static String globalCurrentStageTracker;
  static bool firstTimeLogin;
  static String latitude = "";
  static String longitude = "";
  static bool locationStatus = false;

  void getLocation() async {
    Location location = new Location();
    PermissionStatus _permissionGranted;
    bool _serviceEnabled;
    LocationData locationData;

    _serviceEnabled = await location.serviceEnabled();
    if (!_serviceEnabled) {
      _serviceEnabled = await location.requestService();
      if (!_serviceEnabled) {
        return;
      }
    }

    _permissionGranted = await location.hasPermission();
    if (_permissionGranted == PermissionStatus.denied) {
      _permissionGranted = await location.requestPermission();
      if (_permissionGranted != PermissionStatus.granted) {
        return;
      }
    }

    locationData = await location.getLocation();
    LoginRepository.latitude = locationData.latitude.toString();
    LoginRepository.longitude = locationData.longitude.toString();
  }

  getEkycUserDetails(String emaild, String mobile, String fullname) async {
    // ignore: unused_local_variable
    final loginAPIClient = http.Client();
    final url = AppConfig().url;
    var apiName = url +
        "getEkycUserDetails?email_id=" +
        emaild +
        "&mobile_no=" +
        mobile +
        "&full_name=" +
        fullname +
        "&latitude=" +
        LoginRepository.latitude +
        "&longitude=" +
        LoginRepository.longitude;
    print("URL api name " + apiName);
    final response = await http.get(
      Uri.parse(apiName), 
    );

    if (response.statusCode == 200) {
      loginEmailId = emaild;
      loginMobileNo = mobile;
      loginFullName = fullname;
      print(response.body);
      final loginResponseObj =
          LoginUserDetailModelResponse.fromJson(json.decode(response.body));
      globalCurrentStageTracker =
          loginResponseObj.response.data.message[0].stage;
      if (globalCurrentStageTracker == "") {
        firstTimeLogin = true;
      } else {
        firstTimeLogin = false;
      }
      return loginResponseObj;
    } else {
      print(response.statusCode);
    }
  }

  saveEkycLoginDetailsAPI(
      String fullname,
      String emailId,
      String mobileNo,
      String clientCODE,
      String mobileOTP,
      String emailOTP,
      String isOtpVerified) async {
    LoginRequestData data = new LoginRequestData();

    data.fullName = fullname;
    data.emailId = emailId;
    data.mobileNo = mobileNo;
    data.clientCode = clientCODE;
    data.mobileOtp = mobileOTP;
    data.emailOtp = emailOTP;
    data.isOtpVerified = isOtpVerified;

    LoginDetailModeRequest request1 = new LoginDetailModeRequest();
    request1.data = data;

    final jsonRequest = json.encode(request1);
    print('Final E-Kyc Request - $jsonRequest');

    var headers = {'Content-Type': 'application/json'};
    var apiName = AppConfig().url + "SaveEkycLoginDetails";
    var request = http.Request('POST', Uri.parse(apiName));
    request.body = jsonRequest;
    request.headers.addAll(headers);

    http.StreamedResponse response = await request.send();

    if (response.statusCode == 200) {
      final responseData = await response.stream.bytesToString();
      print('Response Data SaveEkycLoginDetails :- ${responseData.toString()}');
      loginFullName = fullname;
      loginEmailId = emailId;
      loginMobileNo = mobileNo;
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

  sendOtpToEmail(String emailId, String mobileNo, String isOtpVerified) async {
    LoginRequestData data = new LoginRequestData();

    data.emailId = emailId;
    data.mobileNo = mobileNo;
    data.isOtpVerified = isOtpVerified;

    LoginDetailModeRequest request1 = new LoginDetailModeRequest();
    request1.data = data;

    final jsonRequest = json.encode(request1);
    print('Final E-Kyc Request - $jsonRequest');

    var headers = {'Content-Type': 'application/json'};
    var apiName = AppConfig().url + "SendEmailOtp";
    var request = http.Request('POST', Uri.parse(apiName));
    request.body = jsonRequest;
    request.headers.addAll(headers);

    http.StreamedResponse response = await request.send();

    if (response.statusCode == 200) {
      final responseData = await response.stream.bytesToString();
      print('Response Data SendEmailOtp :- ${responseData.toString()}');
      loginEmailId = emailId;
      loginMobileNo = mobileNo;
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

  sendOtpToMobile(String emailId, String mobileNo, String isOtpVerified) async {
    LoginRequestData data = new LoginRequestData();

    data.emailId = emailId;
    data.mobileNo = mobileNo;
    data.isOtpVerified = isOtpVerified;

    LoginDetailModeRequest request1 = new LoginDetailModeRequest();
    request1.data = data;

    final jsonRequest = json.encode(request1);
    print('Final E-Kyc Request - $jsonRequest');

    var headers = {'Content-Type': 'application/json'};
    var apiName = AppConfig().url + "SendMobileOtp";
    var request = http.Request('POST', Uri.parse(apiName));
    request.body = jsonRequest;
    request.headers.addAll(headers);

    http.StreamedResponse response = await request.send();

    if (response.statusCode == 200) {
      final responseData = await response.stream.bytesToString();
      print('Response Data SendMobileOtp :- ${responseData.toString()}');
      loginEmailId = emailId;
      loginMobileNo = mobileNo;
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
