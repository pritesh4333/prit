import 'dart:convert';
import 'dart:html';

import 'package:e_kyc/Login/UI/Configuration/AppConfig.dart';
import 'package:e_kyc/Login/UI/EsignScreen/repository/EsignRepository.dart';
import 'package:e_kyc/Login/UI/HomeScreen/HomeScreenUI.dart';
import 'package:e_kyc/Login/UI/Login/View/LoginUI.dart';
import 'package:e_kyc/Login/UI/Login/View/pdfwritescreen.dart';
import 'package:e_kyc/Login/UI/Login/bloc/LoginBloc.dart';
import 'package:e_kyc/Login/UI/Login/repository/LoginRepository.dart';
// import 'package:e_kyc/Login/UI/Login/repository/LoginRepository.dart';
import 'package:e_kyc/Login/UI/ThemeColors.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:flutter/services.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:pin_code_fields/pin_code_fields.dart';
import 'package:e_kyc/Utilities/Location/location.dart';

import "package:http/http.dart" as http;

class LoginScreenLarge extends StatefulWidget {
  @override
  _LoginScreenState createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreenLarge> {
  LoginBloc loginbloc = new LoginBloc();

  var validfullname = "", validemail = "", validmobile = "", validclientcode = "";

  var mobileOtpTxt = "";
  var emailOtpTxt = "";
  var alertCounter = 0;

  final formKey = GlobalKey<FormState>();
  @override
  void dispose() {
    this.loginbloc.dispose();

    super.dispose();
  }

  @override
  void initState() {
    super.initState();
    loginbloc.checkPermissions();
    loginbloc.requestPermission();
    var urlname = AppConfig().url;
    print(urlname);

    this.loginbloc.validatefullname.listen((event) {
      setState(() {
        this.validfullname = event;
      });
    });
    this.loginbloc.validateemail.listen((event) {
      setState(() {
        this.validemail = event;
      });
    });
    this.loginbloc.validatemobile.listen((event) {
      setState(() {
        this.validmobile = event;
      });
    });

    this.loginbloc.validateclientcode.listen((event) {
      setState(() {
        this.validclientcode = event;
      });
    });

    this.loginbloc.mobileotp.listen((value) {});
    this.loginbloc.validateMobileOtp.listen((event) {
      setState(() {
        this.mobileOtpTxt = event;
      });
    });

    this.loginbloc.validateEmailOtp.listen((event) {
      setState(() {
        this.emailOtpTxt = event;
      });
    });
    this.loginbloc.fullnameTextController.addListener(() {
      setState(() {
        final text = this.loginbloc.fullnameTextController.text.toUpperCase();
        this.loginbloc.fullnameTextController.value = this.loginbloc.fullnameTextController.value.copyWith(
              text: text,
              selection: TextSelection(baseOffset: text.length, extentOffset: text.length),
            );
      });
    });
    if (EsignRepository.timerObj != null) {
      EsignRepository.timerObj.cancel();
      EsignRepository.timerObj = null;
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: SafeArea(
            child: SingleChildScrollView(
                child: Column(
      children: [
        header(context),
        headericon(context),
        Container(
          padding: EdgeInsets.all(10),
          child: Text(
            "Version :21.09.2022+1",
            style: TextStyle(
              fontFamily: 'century_gothic',
              color: Colors.black,
              fontSize: 14.0,
            ),
          ),
        ),
      ],
    ))));
  }

  header(BuildContext context) {
    return Container(
      height: 60,
      decoration: new BoxDecoration(
        color: Color(0xFF0074C4),
        borderRadius: new BorderRadius.only(
          bottomLeft: const Radius.circular(20.0),
          bottomRight: const Radius.circular(20.0),
        ),
      ),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Container(
            padding: EdgeInsets.only(left: 25),
            child: Text.rich(
              TextSpan(
                // with no TextStyle it will have default text style
                style: TextStyle(
                  color: Color(0xFFFFFFFF),
                  fontSize: 20.0,
                ),
                children: <TextSpan>[
                  TextSpan(text: 'E - K Y C', style: TextStyle(fontFamily: 'century_gothic', fontSize: 25, fontWeight: FontWeight.bold)),
                ],
              ),
            ),
          ),
          Container(
            padding: EdgeInsets.only(right: 25),
            child: Row(
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                Container(width: 150, height: 135, child: Image.asset('asset/images/vishwaslogo.png')),
              ],
            ),
          ),
        ],
      ),
    );
  }

  headericon(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
      crossAxisAlignment: CrossAxisAlignment.center,
      children: [
        Visibility(
          visible: this.loginbloc.loginfieldsvisiblity,
          child: Expanded(
            child: loginfield(context),
          ),
        ),
        Visibility(
          visible: this.loginbloc.emailOtpVerification,
          child: Expanded(
            child: otpfield(context),
          ),
        ),
        Visibility(
          visible: this.loginbloc.otpVerifiedVisiblity,
          child: Expanded(
            child: otpverifiedpopup(context),
          ),
        ),
        Expanded(
          child: Container(
              padding: EdgeInsets.all(80),
              child: Column(
                children: [
                  Image.asset(
                    'asset/images/backgroudicon.png',
                    height: 400,
                    width: 500,
                  ),
                  Container(
                    padding: EdgeInsets.all(10),
                    child: Text(
                      "Note: Please ensure your mobile no is link with Adhar\nplease ensure your mobile/pc browser have permission for access drive\nplease ensure your mobile/pc browser have permission for access camera\nFor support call us on - 9311343143",
                      style: TextStyle(
                        fontFamily: 'century_gothic',
                        color: Color(0xFF9B9B9B),
                        fontSize: 11.0,
                      ),
                    ),
                  ),
                ],
              )),
        )
      ],
    );
  }

  loginfield(BuildContext context) {
    return Container(
      child: Container(
        margin: EdgeInsets.only(top: 10, left: 50, right: 50),
        decoration: BoxDecoration(
            border: Border.all(
              color: Colors.grey[400],
            ),
            borderRadius: BorderRadius.all(Radius.circular(20))),
        child: Column(
          children: [
            Container(
              margin: EdgeInsets.only(top: 15),
              padding: EdgeInsets.all(10),
              child: Text.rich(
                TextSpan(
                  // with no TextStyle it will have default text style

                  children: <TextSpan>[
                    TextSpan(text: 'OPEN YOUR ACCOUNT IN 10 MINS', style: TextStyle(fontFamily: 'century_gothic', color: Color(0xFF000000), fontWeight: FontWeight.bold)),
                  ],
                ),
              ),
            ),
            Container(
              padding: EdgeInsets.only(left: 15, right: 15, top: 15),
              width: 500,
              child: TextField(
                controller: this.loginbloc.fullnameTextController,
                style: TextStyle(fontFamily: 'century_gothic'),
                inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z ]'))],
                decoration: InputDecoration(
                  contentPadding: EdgeInsets.only(top: 15, left: 10), // add padding to adjust text
                  isDense: true,
                  hintText: "FULL NAME",

                  hintStyle: TextStyle(fontSize: 18, fontFamily: 'century_gothic', color: Colors.grey[600]),
                  errorText: validfullname.isEmpty ? null : validfullname,

                  prefixIcon: Padding(
                    padding: EdgeInsets.all(10), // add padding to adjust icon
                    child: SvgPicture.asset(
                      'asset/svg/full_name.svg',
                      color: Colors.black,
                      height: 22,
                      width: 22,
                    ),
                  ),
                ),
              ),
            ),
            Container(
              width: 500,
              padding: EdgeInsets.only(left: 15, right: 15, top: 15),
              child: TextField(
                controller: this.loginbloc.emailTextController,
                style: TextStyle(fontFamily: 'century_gothic'),
                decoration: InputDecoration(
                  contentPadding: EdgeInsets.only(top: 15, left: 10), // add padding to adjust text
                  isDense: true,
                  hintText: "EMAIL ID",
                  hintStyle: TextStyle(fontSize: 18, fontFamily: 'century_gothic', color: Colors.grey[600]),
                  errorText: validemail.isEmpty ? null : validemail,
                  prefixIcon: Padding(
                    padding: EdgeInsets.all(10), // add padding to adjust icon
                    child: SvgPicture.asset(
                      'asset/svg/email_id.svg',
                      color: Colors.black,
                      height: 22,
                      width: 22,
                    ),
                  ),
                ),
              ),
            ),
            Container(
              width: 500,
              padding: EdgeInsets.only(left: 15, right: 15, top: 15),
              child: TextField(
                controller: this.loginbloc.mobileTextController,
                keyboardType: TextInputType.number,
                style: TextStyle(fontFamily: 'century_gothic'),
                inputFormatters: <TextInputFormatter>[
                  FilteringTextInputFormatter.allow(RegExp(r'[0-9]')),
                ],
                maxLength: 10,
                decoration: InputDecoration(
                  contentPadding: EdgeInsets.only(top: 15, left: 10), // add padding to adjust text
                  isDense: true,
                  hintText: "MOBILE NUMBER",
                  hintStyle: TextStyle(fontSize: 18, fontFamily: 'century_gothic', color: Colors.grey[600]),
                  errorText: validmobile.isEmpty ? null : validmobile,
                  prefixIcon: Padding(
                    padding: EdgeInsets.all(10), // add padding to adjust icon
                    child: SvgPicture.asset(
                      'asset/svg/mobile.svg',
                      color: Colors.black,
                      height: 22,
                      width: 22,
                    ),
                  ),
                ),
              ),
            ),
            Container(
              width: 500,
              child: Container(
                padding: EdgeInsets.only(left: 15, right: 15, top: 15),
                child: Row(
                  children: [
                    Row(
                      children: [
                        Checkbox(
                          value: this.loginbloc.checkboxcheck,
                          onChanged: (value) {
                            setState(() {
                              this.loginbloc.checkboxcheck = value;
                            });
                          },
                        ),
                        Text(
                          "THROUGH REFERRAL",
                          style: TextStyle(
                            fontFamily: 'century_gothic',
                            fontWeight: FontWeight.w600,
                          ),
                        ),
                      ],
                    ),
                  ],
                ),
              ),
            ),
            Container(
              width: 500,
              alignment: Alignment.topLeft,
              padding: EdgeInsets.only(left: 15, right: 15),
              child: Visibility(
                visible: loginbloc.checkboxcheck,
                child: TextField(
                  controller: this.loginbloc.clientcodeTextController,
                  keyboardType: TextInputType.number,
                  style: TextStyle(fontFamily: 'century_gothic'),
                  decoration: InputDecoration(
                    contentPadding: EdgeInsets.only(top: 15), // add padding to adjust text
                    isDense: true,
                    hintText: "ENTER CLIENT CODE",
                    hintStyle: TextStyle(fontSize: 18, fontFamily: 'century_gothic', color: Colors.grey[400]),
                    errorText: validclientcode.isEmpty ? null : validclientcode,
                    prefixIcon: Padding(
                      padding: EdgeInsets.only(top: 15), // add padding to adjust icon
                    ),
                  ),
                ),
              ),
            ),
            Container(
              alignment: Alignment.centerRight,
              child: Container(
                height: 45,
                width: 110,
                margin: EdgeInsets.all(20),

                padding: EdgeInsets.only(top: 4, left: 4, bottom: 4, right: 4), //
                decoration: BoxDecoration(
                    border: Border.all(
                      color: Colors.blue[200],
                    ),
                    borderRadius: BorderRadius.circular(20) // use instead of BorderRadius.all(Radius.circular(20))
                    ),

                child: TextButton(
                  child: Text("CONTINUE".toUpperCase(), style: TextStyle(fontSize: 14, fontFamily: 'century_gothic')),
                  style: ButtonStyle(
                    padding: MaterialStateProperty.all<EdgeInsets>(EdgeInsets.all(1)),
                    foregroundColor: MaterialStateProperty.all<Color>(Colors.white),
                    shape: MaterialStateProperty.all<RoundedRectangleBorder>(RoundedRectangleBorder(borderRadius: BorderRadius.circular(15), side: BorderSide(color: Colors.blue[200]))),
                    backgroundColor: MaterialStateProperty.all<Color>(Color(0xFF0074C4)),
                  ),
                  // onPressed: () => continuebtnclick(context),
                  onPressed: () => proceedFurtherforLoggingIn(context),
                  // pdfwrite(),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

  // pdfwrite() {
  //   Navigator.push(
  //     context,
  //     MaterialPageRoute(builder: (context) => pdfwritescreen()),
  //   );
  // }

  otpfield(BuildContext context) {
    return Container(
      margin: EdgeInsets.only(top: 100, left: 50, right: 100),
      decoration: BoxDecoration(
          border: Border.all(
            color: Colors.grey[400],
          ),
          borderRadius: BorderRadius.all(Radius.circular(20))),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: [
          Container(
            margin: EdgeInsets.only(top: 15, left: 20),
            padding: EdgeInsets.all(10),
            alignment: Alignment.center,
            child: Text.rich(
              TextSpan(
                // with no TextStyle it will have default text style

                children: <TextSpan>[
                  TextSpan(text: 'PLEASE ENTER OTP YOU RECEIVED ON EMAIL AND MOBILE', style: TextStyle(fontFamily: 'century_gothic', color: Color(0xFF000000), fontWeight: FontWeight.bold)),
                ],
              ),
            ),
          ),
          Container(
            margin: EdgeInsets.only(left: 20),
            padding: EdgeInsets.all(5),
            alignment: Alignment.centerLeft,
            child: Text("OTP RECEIVED VIA MOBILE", style: TextStyle(fontFamily: 'century_gothic', color: Color(0xFF000000), fontWeight: FontWeight.normal)),
          ),
          Container(
            padding: EdgeInsets.all(5),
            alignment: Alignment.center,
            child: Text(this.mobileOtpTxt, style: TextStyle(fontFamily: 'century_gothic', color: getColor(this.mobileOtpTxt), fontWeight: FontWeight.normal)),
          ),
          Container(
            padding: EdgeInsets.only(left: 20, right: 20, top: 10),
            child: PinCodeTextField(
              enabled: loginbloc.enableDisableOTPMobileTextField,
              controller: this.loginbloc.mobileOtpTextFieldController,
              appContext: context,
              length: 6,
              // inputFormatters: [WhitelistingTextInputFormatter.digitsOnly],
              inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[0-9]'))],

              //   obscureText: this._isObscurePin,
              obscureText: true,
              enablePinAutofill: false,
              onSaved: null,
              keyboardType: TextInputType.number,
              obscuringCharacter: '*',
              pinTheme: PinTheme(
                borderRadius: BorderRadius.circular(5.0),
                fieldHeight: 40,
                fieldWidth: 40,
                borderWidth: 1,
                fieldOuterPadding: EdgeInsets.all(0),
                shape: PinCodeFieldShape.box,
                activeColor: Colors.black,
                selectedColor: Colors.black,
                inactiveColor: Colors.black,
                disabledColor: Colors.black,
                activeFillColor: Colors.black,
                selectedFillColor: Colors.black,
                inactiveFillColor: Colors.black,
                errorBorderColor: Colors.black,
              ),
              onChanged: (string) {
                print(string);
                if (string.length == 6) {
                  setState(() => this.loginbloc?.isEnableMobileVerifyButton = true);
                }
              },
            ),
          ),
          Container(
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Container(
                  margin: EdgeInsets.only(left: 20),
                  alignment: Alignment.topRight,
                  child: TextButton(
                    child: Text("VERIFY".toUpperCase(), style: TextStyle(fontSize: 12, fontFamily: 'century_gothic')),
                    style: ButtonStyle(
                      padding: MaterialStateProperty.all<EdgeInsets>(EdgeInsets.all(16)),
                      foregroundColor: MaterialStateProperty.all<Color>(Colors.white),
                      shape: MaterialStateProperty.all<RoundedRectangleBorder>(RoundedRectangleBorder(borderRadius: BorderRadius.circular(10), side: BorderSide(color: Colors.blue[200]))),
                      backgroundColor: loginbloc.enableDisableOTPMobileTextField == true ? MaterialStateProperty.all<Color>(Color(0xFF0074C4)) : MaterialStateProperty.all<Color>(Color(0xFF606263)),
                    ),
                    onPressed: () => this.loginbloc.isEnableMobileVerifyButton
                        ? {
                            if (this.loginbloc.isEnableMobileVerifyButton)
                              {
                                print(" Mobile verfy btn pressed"),
                                if (loginbloc.enableDisableOTPMobileTextField)
                                  {
                                    this.loginbloc.validatioOTPMobile(context),
                                  }
                              }
                            else
                              {
                                print(" Mobile OTP"),
                              }
                          }
                        : null,
                  ),
                ),
                Container(
                  alignment: Alignment.topRight,
                  padding: EdgeInsets.all(10),
                  margin: EdgeInsets.only(right: 10),
                  child: GestureDetector(
                    onTap: () => loginbloc.enableDisableOTPMobileTextField ? resendOtpMobile() : null,
                    child: Text(
                      "Resend OTP",
                      style: TextStyle(
                        fontFamily: 'century_gothic',
                        color: Color(0xFF0066CC),
                        fontWeight: FontWeight.normal,
                      ),
                    ),
                  ),
                ),
              ],
            ),
          ),
          Container(
            margin: EdgeInsets.only(left: 20, top: 10),
            child: Text("OTP RECEIVED VIA EMAIL", style: TextStyle(fontFamily: 'century_gothic', color: Color(0xFF000000), fontWeight: FontWeight.normal)),
          ),
          Container(
            padding: EdgeInsets.all(5),
            alignment: Alignment.center,
            child: Text(this.emailOtpTxt, style: TextStyle(fontFamily: 'century_gothic', color: getColor(this.emailOtpTxt), fontWeight: FontWeight.normal)),
          ),
          Container(
            padding: EdgeInsets.only(left: 20, right: 20, top: 10),
            child: PinCodeTextField(
              enabled: loginbloc.enableDisableOTPEmailTextField,
              controller: this.loginbloc.emailOtpTextFieldController,
              appContext: context,
              length: 6,
              // inputFormatters: [WhitelistingTextInputFormatter.digitsOnly],
              inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[0-9]'))],
              //   obscureText: this._isObscurePin,
              obscureText: true,
              enablePinAutofill: false,
              onSaved: null,
              keyboardType: TextInputType.number,
              obscuringCharacter: '*',
              pinTheme: PinTheme(
                borderRadius: BorderRadius.circular(5.0),
                fieldHeight: 40,
                fieldWidth: 40,
                borderWidth: 1,
                fieldOuterPadding: EdgeInsets.zero,
                shape: PinCodeFieldShape.box,
                activeColor: Colors.black,
                selectedColor: Colors.black,
                inactiveColor: Colors.black,
                disabledColor: Colors.black,
                activeFillColor: Colors.black,
                selectedFillColor: Colors.black,
                inactiveFillColor: Colors.black,
                errorBorderColor: Colors.black,
              ),
              onChanged: (string) {
                print(string);
                if (string.length == 6) {
                  setState(() => this.loginbloc?.isEnableemailVerifyButton = true);
                }
              },
            ),
          ),
          Container(
            margin: EdgeInsets.only(left: 20),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Container(
                  alignment: Alignment.topRight,
                  child: TextButton(
                    child: Text("VERIFY".toUpperCase(), style: TextStyle(fontSize: 12, fontFamily: 'century_gothic')),
                    style: ButtonStyle(
                      padding: MaterialStateProperty.all<EdgeInsets>(EdgeInsets.all(16)),
                      foregroundColor: MaterialStateProperty.all<Color>(Colors.white),
                      shape: MaterialStateProperty.all<RoundedRectangleBorder>(RoundedRectangleBorder(borderRadius: BorderRadius.circular(10), side: BorderSide(color: Colors.blue[200]))),
                      backgroundColor: loginbloc.enableDisableOTPEmailTextField == true ? MaterialStateProperty.all<Color>(Color(0xFF0074C4)) : MaterialStateProperty.all<Color>(Color(0xFF606263)),
                    ),
                    onPressed: () => this.loginbloc.isEnableemailVerifyButton
                        ? {
                            if (this.loginbloc.isEnableemailVerifyButton)
                              {
                                print("Email verify btn pressed"),
                                if (loginbloc.enableDisableOTPEmailTextField)
                                  {
                                    this.loginbloc.validatioOTPEmail(context),
                                  }
                              }
                            else
                              {
                                print(" Email OTP"),
                              }
                          }
                        : null,
                  ),
                ),
                Container(
                  alignment: Alignment.topRight,
                  padding: EdgeInsets.all(10),
                  margin: EdgeInsets.only(right: 10),
                  child: GestureDetector(
                    onTap: () => loginbloc.enableDisableOTPEmailTextField ? resendOtpEmail() : null,
                    child: Text("Resend OTP", style: TextStyle(fontFamily: 'century_gothic', color: Color(0xFF0066CC), fontWeight: FontWeight.normal)),
                  ),
                ),
              ],
            ),
          ),
          Container(
            alignment: Alignment.center,
            margin: EdgeInsets.only(left: 20),
            child: Container(
              alignment: Alignment.center,
              child: Container(
                height: 45,
                width: 110,
                margin: EdgeInsets.all(20),

                padding: EdgeInsets.only(top: 4, left: 4, bottom: 4, right: 4), //
                decoration: BoxDecoration(
                    border: Border.all(
                      color: Colors.blue[200],
                    ),
                    borderRadius: BorderRadius.circular(20) // use instead of BorderRadius.all(Radius.circular(20))
                    ),

                child: TextButton(
                  child: Text("CONTINUE".toUpperCase(), style: TextStyle(fontSize: 14, fontFamily: 'century_gothic')),
                  style: ButtonStyle(
                    padding: MaterialStateProperty.all<EdgeInsets>(EdgeInsets.all(1)),
                    foregroundColor: MaterialStateProperty.all<Color>(Colors.white),
                    shape: MaterialStateProperty.all<RoundedRectangleBorder>(RoundedRectangleBorder(borderRadius: BorderRadius.circular(15), side: BorderSide(color: Colors.blue[200]))),
                    backgroundColor: MaterialStateProperty.all<Color>(Color(0xFF0074C4)),
                  ),
                  onPressed: () => otpbtnclick(context),
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }

  Color getColor(status) {
    if (status == "Verified Successfully") {
      return Colors.green[900];
    } else {
      return Color(0XFFff0000);
    }
  }

  otpverifiedpopup(BuildContext context) {
    return Container(
      child: Column(
        children: [
          Container(
            child: Container(width: 150, height: 135, child: Image.asset('asset/images/success_otp.png')),
          ),
          Container(
            alignment: Alignment.center,
            margin: EdgeInsets.only(top: 10),
            padding: EdgeInsets.all(10),
            child: Text("OTP Verified Successfull", style: TextStyle(fontFamily: 'century_gothic', color: Color(0xFF000000), fontWeight: FontWeight.normal)),
          ),
        ],
      ),
    );
  }

  proceedFurtherforLoggingIn(BuildContext context) {
    if (validateLocationDetails()) {
      continuebtnclick(context);
    }
  }

  bool validateLocationDetails() {
    String lat = LoginRepository.latitude;
    String long = LoginRepository.longitude;
    if ((lat.isNotEmpty && lat.length > 1) && (long.isNotEmpty && long.length > 1)) {
      //Got the location data now proceed further for E-Signing
      return true;
    } else {
      //Look for permission
      switch (LoginBloc.permissionGranted) {
        case PermissionStatus.granted:
          showAlert(context, 'Permission Granted');
          LoginBloc().getLocation();
          return false;
          break;
        case PermissionStatus.denied:
          showAlert(context, 'Please allow location to proceed.');
          LoginBloc().requestPermission();
          return false;
          break;
        case PermissionStatus.grantedLimited:
          showAlert(context, 'Permission Granted Limited');
          LoginBloc().requestPermission();
          return false;
          break;
        case PermissionStatus.deniedForever:
          showAlert(context, 'Please allow location service and refresh it to fetch location');
          LoginBloc().requestPermission();
          return false;
          break;
      }
      return false;
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

  continuebtnclick(BuildContext context) async {
    var obj = await loginbloc.validationcheck(context);
    LoginRepository().getLocation();
    if (obj) {
      var getuserdetailsobj = await loginbloc.getEkycUserDetails(context);
      if (getuserdetailsobj && LoginRepository.loginDetailsResObjGlobal.response.data.message[0].emailOtp != "" && LoginRepository.loginDetailsResObjGlobal.response.data.message[0].emailOtp != null && LoginRepository.loginDetailsResObjGlobal.response.data.message[0].mobileOtp != "" && LoginRepository.loginDetailsResObjGlobal.response.data.message[0].mobileOtp != null && LoginRepository.loginDetailsResObjGlobal.response.data.message[0].isOtpVerified != "" && LoginRepository.loginDetailsResObjGlobal.response.data.message[0].isOtpVerified != null && LoginRepository.loginDetailsResObjGlobal.response.data.message[0].isOtpVerified != "1") {
        Navigator.pushAndRemoveUntil(
          context,
          MaterialPageRoute(builder: (context) => HomeScreenUI()),
          (route) => true,
        );
        // setState(() {
        //   this.loginbloc.loginfieldsvisiblity = true;
        //   this.loginbloc.emailOtpVerification = false;
        // });
      } else {
        setState(() {
          this.loginbloc.loginfieldsvisiblity = false;
          this.loginbloc.emailOtpVerification = true;
          loginbloc.sendOtpToEmail(context);
          loginbloc.sendOtpToMobile(context);
        });
      }
    }
  }

  // continuebtnclick1(BuildContext context) async {
  //   var obj = await loginbloc.validationcheck(context);
  //   if (LoginRepository.locationStatus) {
  //     if (LoginRepository.latitude.isNotEmpty &&
  //         LoginRepository.longitude.isNotEmpty) {
  //       LoginRepository.latitude =
  //           LoginRepository.latitude.split("Latitude:")[1];
  //       LoginRepository.longitude =
  //           LoginRepository.longitude.split("Longitude:")[1];
  //       print("Proceed to login");
  //       if (obj) {
  //         var getuserdetailsobj = await loginbloc.getEkycUserDetails(context);
  //         if (getuserdetailsobj &&
  //             LoginRepository.loginDetailsResObjGlobal.response.data.message[0]
  //                     .emailOtp !=
  //                 "" &&
  //             LoginRepository.loginDetailsResObjGlobal.response.data.message[0]
  //                     .emailOtp !=
  //                 null &&
  //             LoginRepository.loginDetailsResObjGlobal.response.data.message[0]
  //                     .mobileOtp !=
  //                 "" &&
  //             LoginRepository.loginDetailsResObjGlobal.response.data.message[0]
  //                     .mobileOtp !=
  //                 null) {
  //           Navigator.pushAndRemoveUntil(
  //             context,
  //             MaterialPageRoute(builder: (context) => HomeScreenUI()),
  //             (route) => true,
  //           );
  //         } else {
  //           setState(() {
  //             this.loginbloc.loginfieldsvisiblity = false;
  //             this.loginbloc.emailOtpVerification = true;
  //             loginbloc.sendOtpToEmail(context);
  //             loginbloc.sendOtpToMobile(context);
  //           });
  //         }
  //       }
  //     } else {
  //       this.loginbloc.showAlertLocationStatus(context,
  //           "Please allow location and press continue to fetch your location");
  //     }
  //   } else {
  //     if (LoginRepository.latitude.isNotEmpty &&
  //         LoginRepository.longitude.isNotEmpty) {
  //       print("Proceed to login");
  //       LoginRepository.latitude =
  //           LoginRepository.latitude.split("Latitude:")[1];
  //       LoginRepository.longitude =
  //           LoginRepository.longitude.split("Longitude:")[1];
  //       if (obj) {
  //         var getuserdetailsobj = await loginbloc.getEkycUserDetails(context);
  //         if (getuserdetailsobj &&
  //             LoginRepository.loginDetailsResObjGlobal.response.data.message[0]
  //                     .emailOtp !=
  //                 "" &&
  //             LoginRepository.loginDetailsResObjGlobal.response.data.message[0]
  //                     .emailOtp !=
  //                 null &&
  //             LoginRepository.loginDetailsResObjGlobal.response.data.message[0]
  //                     .mobileOtp !=
  //                 "" &&
  //             LoginRepository.loginDetailsResObjGlobal.response.data.message[0]
  //                     .mobileOtp !=
  //                 null) {
  //           Navigator.pushAndRemoveUntil(
  //             context,
  //             MaterialPageRoute(builder: (context) => HomeScreenUI()),
  //             (route) => true,
  //           );
  //         } else {
  //           setState(() {
  //             this.loginbloc.loginfieldsvisiblity = false;
  //             this.loginbloc.emailOtpVerification = true;
  //             loginbloc.sendOtpToEmail(context);
  //             loginbloc.sendOtpToMobile(context);
  //           });
  //         }
  //       }
  //     } else {
  //       this.loginbloc.showAlertLocationStatus(context,
  //           "Please allow location and press continue to fetch your location");
  //     }
  //   }
  //   LoginRepository().getLocation();
  // }

  otpbtnclick(BuildContext context) async {
    loginbloc.isEmailandMobileOtpVerifiedCheck = (loginbloc.isMobileOTPVerified && loginbloc.isEmailOTPVerified) ? true : false;
    if (loginbloc.isEmailandMobileOtpVerifiedCheck) {
      // check with if email and mobile opt is verified or not if verified then click contiue
      var obj = await loginbloc.otpValidation(context);
      if (obj) {
        setState(() {
          this.loginbloc.emailOtpVerification = false;
          this.loginbloc.loginfieldsvisiblity = false;
          this.loginbloc.otpVerifiedVisiblity = true;
        });
        //  await Future.delayed(Duration(seconds: 1));
        this.loginbloc.saveLoginFormDetails(context);
      } else {}
    } else {
      this.loginbloc.showAlert(context, "Please Verify OTP");
      // this.loginbloc.mobileotp.sink.add("Please Verify Mobile OTP");
      // this.loginbloc.emailotp.sink.add("Please Verify Email OTP");
    }
  }

  resendOtpMobile() {
    loginbloc.sendOtpToMobile(context);
  }

  resendOtpEmail() {
    loginbloc.sendOtpToEmail(context);
  }
}
