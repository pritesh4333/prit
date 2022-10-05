import 'dart:async';

import 'package:e_kyc/Login/UI/Configuration/AppConfig.dart';
import 'package:e_kyc/Login/UI/HomeScreen/HomeScreenUI.dart';
import 'package:e_kyc/Login/UI/Login/model/LoginUserDetailModelResponse.dart';
import 'package:e_kyc/Login/UI/Login/repository/LoginRepository.dart';
import 'package:e_kyc/Utilities/CommonDialog.dart';
import 'package:e_kyc/Utilities/Location/location.dart';
import 'package:e_kyc/Utilities/ResponseModel.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
// import 'package:location/location.dart';
import 'package:rxdart/rxdart.dart';

class LoginBloc {
//Login Repository
  LoginRepository _repository = new LoginRepository();
  // final fullnameTextController = TextEditingController(text: '');
  // final emailTextController = TextEditingController(text: '');
  // final mobileTextController = TextEditingController(text: '');

  final fullnameTextController = TextEditingController(text: kReleaseMode ? '' : 'prit');
  final emailTextController = TextEditingController(text: kReleaseMode ? '' : 'pritesh.parmar@greeksoft.co.in');
  final mobileTextController = TextEditingController(text: kReleaseMode ? '' : '8767957178');

  final clientcodeTextController = TextEditingController(text: '');

  final BehaviorSubject<String> _fullname = BehaviorSubject<String>();
  Stream<String> get validatefullname => this._fullname.stream;

  final BehaviorSubject<String> _email = BehaviorSubject<String>();
  Stream<String> get validateemail => this._email.stream;

  final BehaviorSubject<String> _mobile = BehaviorSubject<String>();
  Stream<String> get validatemobile => this._mobile.stream;

  final BehaviorSubject<String> _clientcode = BehaviorSubject<String>();
  Stream<String> get validateclientcode => this._clientcode.stream;

  final BehaviorSubject<String> mobileotp = BehaviorSubject<String>();
  Stream<String> get validateMobileOtp => this.mobileotp.stream;

  final BehaviorSubject<String> emailotp = BehaviorSubject<String>();
  Stream<String> get validateEmailOtp => this.emailotp.stream;

  var formvalidation;
  var otpvalidation;

  var ctx;
  var checkboxcheck = false;
  var emailOtpVerification = false;
  var loginfieldsvisiblity = true;
  var otpVerifiedVisiblity = false;
  var isEnableMobileVerifyButton = false;
  var isEnableemailVerifyButton = false;
  var emailOtpTextFieldController = TextEditingController(text: '');
  var mobileOtpTextFieldController = TextEditingController(text: '');
  var emailOTP, mobileOTP;
  var isEmailandMobileOtpVerifiedCheck = false;
  var isMobileOTPVerified = false;
  var isEmailOTPVerified = false;
  var enableDisableOTPMobileTextField = true;
  var enableDisableOTPEmailTextField = true;

  final Location location = Location();
  LocationData _location;
  static PermissionStatus permissionGranted;

  // Checking Permission if it's granted or denied
  Future<void> checkPermissions() async {
    final PermissionStatus permissionGrantedResult = await location.hasPermission();
    permissionGranted = permissionGrantedResult;
  }

//Asking for permission it shows alert if it's not the it is denied forever.
  Future<void> requestPermission() async {
    if (permissionGranted != PermissionStatus.granted) {
      final PermissionStatus permissionRequestedResult = await location.requestPermission();
      permissionGranted = permissionRequestedResult;
      if (PermissionStatus.granted == permissionRequestedResult) {
        getLocation();
      }
    }
  }

  //Fetching Latitude & Longitude if permission has provided.
  Future<void> getLocation() async {
    try {
      final LocationData _locationResult = await location.getLocation();
      _location = _locationResult;
      LoginRepository.latitude = _location.latitude.toString();
      LoginRepository.longitude = _location.longitude.toString();
    } on PlatformException catch (err) {
      print("Error fetching Location coordinates... ==>>$err");
    }
  }

  void dispose() {
    this._fullname.close();
    this._email.close();
    this._mobile.close();
    this._clientcode.close();
    this.mobileotp.close();
    this.emailotp.close();

    this.fullnameTextController.dispose();
    this.emailTextController.dispose();
    this.mobileTextController.dispose();
    this.clientcodeTextController.dispose();
    this.emailOtpTextFieldController.dispose();
    this.mobileOtpTextFieldController.dispose();
  }

  validationcheck(BuildContext context) async {
    this.ctx = context;
    formvalidation = true;
    var fullName = fullnameTextController.text.trim();

    if (fullnameTextController.text.length == 0 || fullName.isEmpty) {
      this._fullname.sink.add("Please enter full name");
      formvalidation = false;
    } else {
      this._fullname.sink.add("");
    }

    if (emailTextController.text.length == 0) {
      this._email.sink.add("Please enter email");
      formvalidation = false;
    } else if (emailTextController.text.length > 0) {
      // RegExp regex = new RegExp(r"^[a-zA-Z0-9.a-zA-Z0-9.!#$%&'*+-/=?^_`{|}~]+@[a-zA-Z0-9]+\.[a-zA-Z]+");
      RegExp regex = new RegExp(r"^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$");
      if (!regex.hasMatch(emailTextController.text.toString()) || emailTextController.text.length == null) {
        this._email.sink.add("Please enter valid email");
        formvalidation = false;
      } else {
        this._email.sink.add("");
      }
    } else {
      this._email.sink.add("");
    }

    if (mobileTextController.text.length == 0) {
      this._mobile.sink.add("Please enter mobile no");
      formvalidation = false;
    } else if (mobileTextController.text.length < 10) {
      this._mobile.sink.add("Please enter valid mobile no");
      formvalidation = false;
    } else {
      this._mobile.sink.add("");
    }

    if (checkboxcheck && clientcodeTextController.hasListeners == true) {
      if (clientcodeTextController.text.length == 0) {
        this._clientcode.sink.add("Please enter client code");
        formvalidation = false;
      } else {
        this._clientcode.sink.add("");
      }
    } else {
      this._clientcode.sink.add("");
    }
    return formvalidation;
    // callLoginAPI(context);

    // if (formvalidation) {
    //       SaveEkycLoginDetailsAPI(context,fullnameTextController.text,emailTextController.text,mobileTextController.text, int.parse(clientcodeTextController.text),123123,321321);
    //  }
  }

  otpValidation(BuildContext context) async {
    this.ctx = context;
    otpvalidation = true;
    print(mobileOtpTextFieldController.text.length);
    if (mobileOtpTextFieldController.text.length != 6) {
      otpvalidation = false;
      this.mobileotp.sink.add("Wrong Mobile OTP");
    } else if (emailOtpTextFieldController.text.length != 6) {
      otpvalidation = false;
      this.emailotp.sink.add("Wrong Email OTP");
    }
    return otpvalidation;
  }

  getEkycUserDetails(BuildContext context) async {
    try {
      AppConfig().showLoaderDialog(context); // show loader
      LoginUserDetailModelResponse obj = await this._repository.getEkycUserDetails(emailTextController.text, mobileTextController.text, fullnameTextController.text);
      LoginRepository.loginDetailsResObjGlobal = obj; // setting user response in static variable for globle use
      print(LoginRepository.loginDetailsResObjGlobal.response.errorCode);
      AppConfig().dismissLoaderDialog(context); //// dismiss loader
      if (obj.response.errorCode == "0") {
        return true;
      } else if (obj.response.errorCode == "1") {
        return false;
      } else if (obj.response.errorCode == "2") {
        return false;
      } else if (obj.response.errorCode == "3") {
        return false;
      }
    } catch (exception) {
      CommonErrorDialog.showServerErrorDialog(context, 'Server not responding');
      print("no data" + exception);
    }
  }

  saveEkycLoginDetailsAPI(BuildContext context, String fullname, String email, String mobile, String clientcode, String mobileotp, String emailotp, String isOtpVerified) async {
    try {
      AppConfig().showLoaderDialog(context); // show loader
      ResponseModel obj = await this._repository.saveEkycLoginDetailsAPI(fullname, email, mobile, clientcode, mobileotp, emailotp, isOtpVerified);

      AppConfig().dismissLoaderDialog(context); //// dismiss loader
      if (obj.response.errorCode == "0") {
        // Navigator.pop(context);

        Navigator.pushAndRemoveUntil(
          context,
          MaterialPageRoute(builder: (context) => HomeScreenUI()),
          (route) => true,
        );
        return true;
      } else if (obj.response.errorCode == "1") {
        print("Save data failed");
        return false;
      }
    } catch (exception) {
      return false;
      // AppConfig().DismissLoaderDialog(context); //// dismiss loader
      CommonErrorDialog.showServerErrorDialog(context, 'Server no responding');
      print("no data" + exception);
    }
  }

  saveLoginFormDetails(BuildContext context) async {
    var clientcode;
    if (clientcodeTextController.text == "") {
      clientcode = "0";
    } else {
      clientcode = clientcodeTextController.text;
    }
    var flag = await saveEkycLoginDetailsAPI(context, fullnameTextController.text.trim(), emailTextController.text.trim(), mobileTextController.text.trim(), clientcode, mobileOtpTextFieldController.text, emailOtpTextFieldController.text, '0');
    return flag;
  }

  void validatioOTPMobile(BuildContext context) {
    var otpvalidationflag = true;

    if (mobileOtpTextFieldController.text != mobileOTP) {
      otpvalidationflag = false;
      this.mobileotp.sink.add("Wrong Mobile OTP Enter");
    } else if (mobileOtpTextFieldController.text.length != 6) {
      otpvalidation = false;
      this.mobileotp.sink.add("Enter Mobile OTP");
    }

    if (otpvalidationflag) {
      this.mobileotp.sink.add("Verified Successfully");
      this.enableDisableOTPMobileTextField = false;
      this.isMobileOTPVerified = true;
      if (emailOtpTextFieldController.text.length != 6) {
        isEmailandMobileOtpVerifiedCheck = false;
      } else {
        isEmailandMobileOtpVerifiedCheck = true;
      }
    } else {
      this.isMobileOTPVerified = false;
    }
  }

  void validatioOTPEmail(BuildContext context) {
    var otpvalidationflag = true;

    if (emailOtpTextFieldController.text != emailOTP) {
      otpvalidationflag = false;
      this.emailotp.sink.add("Wrong Email OTP Enter");
    } else if (emailOtpTextFieldController.text.length != 6) {
      otpvalidationflag = false;
      this.emailotp.sink.add("Enter Email OTP");
    }

    if (otpvalidationflag) {
      this.emailotp.sink.add("Verified Successfully");
      this.enableDisableOTPEmailTextField = false;
      this.isEmailOTPVerified = true;
      if (mobileOtpTextFieldController.text.length != 6) {
        isEmailandMobileOtpVerifiedCheck = false;
      } else {
        isEmailandMobileOtpVerifiedCheck = true;
      }
    } else {
      this.isEmailOTPVerified = false;
    }
  }

  Future<void> sendOtpToEmail(BuildContext context) async {
    try {
      AppConfig().showLoaderDialog(context); // show loader
      ResponseModel obj = await this._repository.sendOtpToEmail(emailTextController.text, mobileTextController.text, "1");

      AppConfig().dismissLoaderDialog(context); //// dismiss loader
      if (obj.response.errorCode == "0") {
        // showAlert(context, "OTP Send Successfull");
        emailOTP = obj.response.data.message;
      } else if (obj.response.errorCode == "1") {
        // showAlert(context, "OTP Send Failed");

      }
    } catch (exception) {
      // AppConfig().DismissLoaderDialog(context); //// dismiss loader
      print("no data" + exception);
      // showAlert(context, "Email OTP Send Failed");
      CommonErrorDialog.showServerErrorDialog(context, 'Email OTP Send Failed');
    }
  }

  Future<void> sendOtpToMobile(BuildContext context) async {
    try {
      AppConfig().showLoaderDialog(context); // show loader
      ResponseModel obj = await this._repository.sendOtpToMobile(emailTextController.text, mobileTextController.text, "1");

      AppConfig().dismissLoaderDialog(context); //// dismiss loader
      if (obj.response.errorCode == "0") {
        // showAlert(context, "OTP Send Successfull");
        mobileOTP = obj.response.data.message;
      } else if (obj.response.errorCode == "1") {
        // showAlert(context, "OTP Send Failed");
      }
    } catch (exception) {
      // AppConfig().DismissLoaderDialog(context); //// dismiss loader
      print("no data" + exception);
      // showAlert(context, "Mobile OTP Send Failed");
      CommonErrorDialog.showServerErrorDialog(context, 'Mobile OTP Send Failed');
    }
  }

  Future<dynamic> showAlert(BuildContext ctx, String msg) {
    return showDialog(
      context: ctx,
      builder: (ctx) => AlertDialog(
        title: Text("E-KYC"),
        content: Text(msg),
        actions: <Widget>[
          TextButton(
            onPressed: () {
              Navigator.of(ctx).pop();
            },
            child: Text("OK"),
          ),
        ],
      ),
    );
  }

  Future<dynamic> showAlertLocationStatus(BuildContext ctx, String msg) {
    return showDialog(
      context: ctx,
      builder: (ctx) => AlertDialog(
        title: Text("E-KYC"),
        content: Text(msg),
        actions: <Widget>[
          TextButton(
            onPressed: () {
              Navigator.of(ctx).pop();
            },
            child: Text("OK"),
          ),
        ],
      ),
    );
  }
}
