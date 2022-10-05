import 'package:e_kyc/Login/UI/Configuration/AppConfig.dart';
import 'package:e_kyc/Login/UI/EsignScreen/repository/EsignRepository.dart';
import 'package:e_kyc/Login/UI/HomeScreen/HomeScreenUI.dart';
import 'package:e_kyc/Login/UI/Login/bloc/LoginBloc.dart';
import 'package:e_kyc/Login/UI/Login/repository/LoginRepository.dart';
import 'package:e_kyc/Utilities/Location/location.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_svg/svg.dart';
import 'package:flutter_vant_kit/main.dart';
import 'package:pin_code_fields/pin_code_fields.dart';
import 'package:e_kyc/Utilities/Location/get_location.dart';

class LoginScreen extends StatefulWidget {
  @override
  _LoginScreenState createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  LoginBloc loginbloc = new LoginBloc();
  var validfullname = "", validemail = "", validmobile = "", validclientcode = "";

  var mobileOtpTxt = "";
  var emailOtpTxt = "";

// Fetching Location Start
  final Location location = Location();
  PermissionStatus _permissionGranted;
  LocationData _location;
  String _error;

// // Checking Permission if it's granted or denied
//   Future<void> _checkPermissions() async {
//     final PermissionStatus permissionGrantedResult =
//         await location.hasPermission();
//     setState(
//       () {
//         _permissionGranted = permissionGrantedResult;
//       },
//     );
//   }

// //Asking for permission it shows alert if it's not the it is denied forever.
//   Future<void> _requestPermission() async {
//     if (_permissionGranted != PermissionStatus.granted) {
//       final PermissionStatus permissionRequestedResult =
//           await location.requestPermission();
//       setState(
//         () {
//           _permissionGranted = permissionRequestedResult;
//           // getLocation();
//           loginbloc.getLocation();
//         },
//       );
//     }
//   }

  //Fetching Latitude & Longitude if permission has provided.
  // Future<void> getLocation() async {
  //   try {
  //     final LocationData _locationResult = await location.getLocation();
  //     setState(
  //       () {
  //         print('Got the Location lat & long');
  //         _location = _locationResult;
  //         LoginRepository.latitude = _location.latitude.toString();
  //         LoginRepository.longitude = _location.longitude.toString();
  //       },
  //     );
  //   } on PlatformException catch (err) {
  //     setState(
  //       () {
  //         print('Error fetching location (lat & long)');
  //         _error = err.code;
  //       },
  //     );
  //   }
  // }

  // Fetching Location End

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
        child: GestureDetector(
          onTap: () {
            FocusManager.instance.primaryFocus?.unfocus();
          },
          child: Column(
            children: [
              header(),
              Container(
                child: Expanded(
                  child: SingleChildScrollView(
                    scrollDirection: Axis.vertical,
                    child: Column(
                      children: [
                        Container(
                          child: headericon(),
                        ),
                        Visibility(
                          visible: this.loginbloc.loginfieldsvisiblity,
                          child: Container(
                            child: loginfield(),
                          ),
                        ),
                        Visibility(
                          visible: this.loginbloc.emailOtpVerification,
                          child: Container(
                            child: otpfield(),
                          ),
                        ),
                        Visibility(
                          visible: this.loginbloc.otpVerifiedVisiblity,
                          child: Container(
                            child: otpverifiedpopup(),
                          ),
                        ),
                      ],
                    ),
                  ),
                ),
              ),
              Container(
                padding: EdgeInsets.all(10),
                child: Text(
                  "Version : 21.09.2022+1",
                  style: TextStyle(
                    fontFamily: 'century_gothic',
                    color: Colors.black,
                    fontSize: 14.0,
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  header() {
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
            child: Text(
              "E - KYC",
              style: TextStyle(color: Color(0xFFFFFFFF), fontFamily: 'century_gothic', fontSize: 25, fontWeight: FontWeight.bold),
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

  headericon() {
    return Row(
      mainAxisAlignment: MainAxisAlignment.center,
      children: <Widget>[
        Center(
          child: Container(margin: EdgeInsets.only(top: 25), width: 300, height: 200, child: Image.asset('asset/images/backgroudicon.png')),
        ),
      ],
    );
  }

  loginfield() {
    return SingleChildScrollView(
      child: Container(
        margin: EdgeInsets.only(top: 10, left: 20, right: 20, bottom: 20),
        decoration: BoxDecoration(
            border: Border.all(
              color: Colors.grey[400],
            ),
            borderRadius: BorderRadius.all(Radius.circular(20))),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Container(
              margin: EdgeInsets.only(top: 15),
              child: Text.rich(
                TextSpan(
                  // with no TextStyle it will have default text style

                  children: <TextSpan>[
                    TextSpan(text: 'OPEN YOUR ACCOUNT IN 10 MINS', style: TextStyle(fontWeight: FontWeight.bold)),
                  ],
                ),
              ),
            ),
            Container(
              padding: EdgeInsets.only(left: 15, right: 15, top: 5),
              child: TextField(
                controller: this.loginbloc.fullnameTextController,
                keyboardType: TextInputType.name,
                onSubmitted: (str) {
                  print('Done or return button tapped');
                },
                inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z ]'))],
                decoration: InputDecoration(
                  contentPadding: EdgeInsets.only(top: 15, left: 10), // add padding to adjust text
                  isDense: true,
                  hintText: "Full Name",
                  hintStyle: TextStyle(fontSize: 18, fontFamily: 'century_gothic', color: Colors.grey[400]),
                  errorText: validfullname.isEmpty ? null : validfullname,
                  prefixIcon: Padding(
                    padding: EdgeInsets.all(10), // add padding to adjust icon
                    child: SvgPicture.asset(
                      'asset/svg/full_name.svg',
                      // color: Colors.black,
                      height: 22,
                      width: 22,
                    ),
                  ),
                ),
              ),
            ),
            Container(
              padding: EdgeInsets.only(left: 15, right: 15, top: 15),
              child: TextField(
                controller: this.loginbloc.emailTextController,
                decoration: InputDecoration(
                  contentPadding: EdgeInsets.only(top: 15, left: 10), // add padding to adjust text
                  isDense: true,
                  hintText: "Email ID",
                  hintStyle: TextStyle(fontSize: 18, fontFamily: 'century_gothic', color: Colors.grey[400]),
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
              padding: EdgeInsets.only(left: 15, right: 15, top: 15),
              child: TextField(
                controller: this.loginbloc.mobileTextController,
                keyboardType: TextInputType.numberWithOptions(signed: true, decimal: true),
                inputFormatters: <TextInputFormatter>[
                  FilteringTextInputFormatter.allow(RegExp(r'[0-9]')),
                ],
                maxLength: 10,
                decoration: InputDecoration(
                  contentPadding: EdgeInsets.only(top: 15, left: 10), // add padding to adjust text
                  isDense: true,
                  hintText: "Mobile Number",
                  hintStyle: TextStyle(fontSize: 18, fontFamily: 'century_gothic', color: Colors.grey[400]),
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
              padding: EdgeInsets.only(left: 15, right: 15, top: 15),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                crossAxisAlignment: CrossAxisAlignment.center,
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
                        "Through Referral",
                        style: TextStyle(
                          fontFamily: 'century_gothic',
                          fontWeight: FontWeight.w600,
                        ),
                      ),
                    ],
                  ),
                  Container(
                    margin: EdgeInsets.only(right: 10),
                    child: Row(
                      children: [
                        Text(
                          "Disclaimer",
                          style: TextStyle(
                            fontFamily: 'century_gothic',
                            fontWeight: FontWeight.w600,
                          ),
                        ),
                      ],
                    ),
                  ),
                ],
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
                child: Text("CONTINUE".toUpperCase(), style: TextStyle(fontSize: 14)),
                style: ButtonStyle(
                  padding: MaterialStateProperty.all<EdgeInsets>(EdgeInsets.all(1)),
                  foregroundColor: MaterialStateProperty.all<Color>(Colors.white),
                  shape: MaterialStateProperty.all<RoundedRectangleBorder>(RoundedRectangleBorder(borderRadius: BorderRadius.circular(15), side: BorderSide(color: Colors.blue[200]))),
                  backgroundColor: MaterialStateProperty.all<Color>(Color(0xFF0074C4)),
                ),
                // onPressed: () => continuebtnclick(context),
                onPressed: () => proceedFurtherforLoggingIn(context),
              ),
            ),
          ],
        ),
      ),
    );
  }

  otpfield() {
    return Container(
      margin: EdgeInsets.only(top: 20, left: 20, right: 10, bottom: 20),
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
                  TextSpan(text: 'PLEASE ENTER OTP YOU RECEIVED ON EMAIL AND MOBILE', style: TextStyle(fontFamily: 'century_gothic', color: Color(0xFF000000), fontSize: 12, fontWeight: FontWeight.bold)),
                ],
              ),
            ),
          ),
          Container(
            margin: EdgeInsets.only(left: 20),
            padding: EdgeInsets.all(5),
            alignment: Alignment.centerLeft,
            child: Text("OTP RECEIVED VIA MOBILE", style: TextStyle(fontFamily: 'century_gothic', color: Color(0xFF000000), fontSize: 12, fontWeight: FontWeight.normal)),
          ),
          Container(
            padding: EdgeInsets.all(5),
            alignment: Alignment.center,
            child: Text(this.mobileOtpTxt, style: TextStyle(fontFamily: 'century_gothic', color: getColor(this.mobileOtpTxt), fontSize: 11, fontWeight: FontWeight.normal)),
          ),
          Container(
            padding: EdgeInsets.only(left: 10, right: 10, top: 10),
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
                fieldHeight: 30,
                fieldWidth: 30,
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
                    child: Text("VERIFY".toUpperCase(), style: TextStyle(fontSize: 10, fontFamily: 'century_gothic')),
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
                                if (loginbloc.enableDisableOTPMobileTextField) {this.loginbloc.validatioOTPMobile(context)}
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
                    child: Text("Resend OTP", style: TextStyle(fontFamily: 'century_gothic', fontSize: 12, color: Color(0xFF0066CC), fontWeight: FontWeight.normal)),
                  ),
                ),
              ],
            ),
          ),
          Container(
            margin: EdgeInsets.only(left: 20, top: 10),
            child: Text("OTP RECEIVED VIA EMAIL", style: TextStyle(fontFamily: 'century_gothic', color: Color(0xFF000000), fontSize: 12, fontWeight: FontWeight.normal)),
          ),
          Container(
            padding: EdgeInsets.all(5),
            alignment: Alignment.center,
            child: Text(this.emailOtpTxt, style: TextStyle(fontFamily: 'century_gothic', color: getColor(this.emailOtpTxt), fontSize: 11, fontWeight: FontWeight.normal)),
          ),
          Container(
            padding: EdgeInsets.only(left: 10, right: 10, top: 10),
            child: PinCodeTextField(
              enabled: loginbloc.enableDisableOTPEmailTextField,
              controller: this.loginbloc.emailOtpTextFieldController,
              appContext: context,
              length: 6,
              // inputFormatters: [WhitelistingTextInputFormatter.digitsOnly],
              inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[0-9]'))],

              obscureText: true,
              keyboardType: TextInputType.number,
              enablePinAutofill: false,
              onSaved: null,
              obscuringCharacter: '*',
              pinTheme: PinTheme(
                borderRadius: BorderRadius.circular(5.0),
                fieldHeight: 30,
                fieldWidth: 30,
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
                    child: Text("VERIFY".toUpperCase(), style: TextStyle(fontSize: 10, fontFamily: 'century_gothic')),
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
                                print("Email verfy btn pressed"),
                                if (loginbloc.enableDisableOTPEmailTextField) {this.loginbloc.validatioOTPEmail(context)}
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
                    child: Text("Resend OTP", style: TextStyle(fontFamily: 'century_gothic', fontSize: 12, color: Color(0xFF0066CC), fontWeight: FontWeight.normal)),
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
                  child: Text("CONTINUE".toUpperCase(), style: TextStyle(fontSize: 12, fontFamily: 'century_gothic')),
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

  otpverifiedpopup() {
    return Container(
      child: Column(
        children: [
          Container(
            margin: EdgeInsets.only(top: 25),
            child: Container(width: 150, height: 135, child: Image.asset('asset/images/success_otp.png')),
          ),
          Container(
            alignment: Alignment.center,
            margin: EdgeInsets.only(top: 25),
            padding: EdgeInsets.all(10),
            child: Text("OTP Verified Successfull", style: TextStyle(fontFamily: 'century_gothic', color: Color(0xFF000000), fontWeight: FontWeight.normal)),
          ),
          Container(
            height: 45,
            width: 110,
            margin: EdgeInsets.all(20),

            padding: EdgeInsets.only(top: 4, left: 4, bottom: 4, right: 4), //

            child: TextButton(
              child: Text("CONTINUE".toUpperCase(), style: TextStyle(fontSize: 12, fontFamily: 'century_gothic')),
              style: ButtonStyle(
                padding: MaterialStateProperty.all<EdgeInsets>(EdgeInsets.all(1)),
                foregroundColor: MaterialStateProperty.all<Color>(Colors.white),
                shape: MaterialStateProperty.all<RoundedRectangleBorder>(RoundedRectangleBorder(borderRadius: BorderRadius.circular(15), side: BorderSide(color: Colors.blue[200]))),
                backgroundColor: MaterialStateProperty.all<Color>(Color(0xFF0074C4)),
              ),
              onPressed: () => otpVerifiedBtnClick(),
            ),
          ),
        ],
      ),
    );
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

  continuebtnclick(BuildContext context) async {
    var obj = await loginbloc.validationcheck(context);
    if (obj) {
      // ignore: unused_local_variable
      var getuserdetailsobj = await loginbloc.getEkycUserDetails(context);
      if (getuserdetailsobj && LoginRepository.loginDetailsResObjGlobal.response.data.message[0].emailOtp != "" && LoginRepository.loginDetailsResObjGlobal.response.data.message[0].emailOtp != null && LoginRepository.loginDetailsResObjGlobal.response.data.message[0].mobileOtp != "" && LoginRepository.loginDetailsResObjGlobal.response.data.message[0].mobileOtp != null && LoginRepository.loginDetailsResObjGlobal.response.data.message[0].isOtpVerified != "" && LoginRepository.loginDetailsResObjGlobal.response.data.message[0].isOtpVerified != null && LoginRepository.loginDetailsResObjGlobal.response.data.message[0].isOtpVerified != "1") {
        Navigator.pushAndRemoveUntil(
          context,
          MaterialPageRoute(builder: (context) => HomeScreenUI()),
          (route) => true,
        );
      } else {
        setState(() {
          this.loginbloc.loginfieldsvisiblity = false;
          this.loginbloc.emailOtpVerification = true;
          loginbloc.sendOtpToEmail(context);
          loginbloc.sendOtpToMobile(context);
        });
      }
    }
    LoginRepository().getLocation();
  }

  Color getColor(status) {
    if (status == "Verified Successfully") {
      return Colors.green[900];
    } else {
      return Color(0XFFff0000);
    }
  }

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

      } else {}
    } else {
      this.loginbloc.showAlert(context, "Please Verify OTP");
      // this.loginbloc.mobileotp.sink.add("Please Verify Mobile OTP");
      // this.loginbloc.emailotp.sink.add("Please Verify Email OTP");
    }
  }

  otpVerifiedBtnClick() async {
    print('OTP Verifed click');
    bool flag = await this.loginbloc.saveLoginFormDetails(context);

    setState(() {
      this.loginbloc.emailOtpVerification = false;
      this.loginbloc.loginfieldsvisiblity = true;
      this.loginbloc.otpVerifiedVisiblity = false;
    });
  }

  resendOtpMobile() {
    loginbloc.sendOtpToMobile(context);
  }

  resendOtpEmail() {
    loginbloc.sendOtpToEmail(context);
  }
}
