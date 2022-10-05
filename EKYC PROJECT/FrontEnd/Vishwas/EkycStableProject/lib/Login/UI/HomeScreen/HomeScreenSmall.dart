import 'package:e_kyc/Login/UI/BankDetailsScreen/View/BankScreenUI.dart';
import 'package:e_kyc/Login/UI/DocumentScreen/DocumentScreenUI.dart';
import 'package:e_kyc/Login/UI/EsignScreen/repository/EsignRepository.dart';
import 'package:e_kyc/Login/UI/EsignScreen/view/EsignScreeUI.dart';
import 'package:e_kyc/Login/UI/EsignScreen/view/EsignScreenSmall.dart';
import 'package:e_kyc/Login/UI/IPVScreen/IPVScreenUI.dart';
import 'package:e_kyc/Login/UI/Login/View/LoginUI.dart';
import 'package:e_kyc/Login/UI/Login/repository/LoginRepository.dart';
import 'package:e_kyc/Login/UI/PanDetailsScreen/PandetailsUI.dart';
import 'package:e_kyc/Login/UI/PaymentScreen/view/PaymentUI.dart';
import 'package:e_kyc/Login/UI/PersonalDetailsScreen/view/PersonalDetailsUI.dart';
import 'package:e_kyc/Login/UI/ThemeColors.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/svg.dart';
import 'package:percent_indicator/linear_percent_indicator.dart';
import 'package:rxdart/subjects.dart';

enum Ekycscreenamesmall {
  pandetailscreen,
  personaldetailscreen,
  bankdetailscreen,
  paymentdetailscreen,
  documentdetailscreen,
  ipvdetailscreen,
  esigndetailscreen,
}

class HomeScreenSmall extends StatefulWidget {
  static final screensStreamSmall = BehaviorSubject<Ekycscreenamesmall>.seeded(Ekycscreenamesmall.pandetailscreen);

  static final percentageFlagSmall = BehaviorSubject<String>.seeded("0.142");
  @override
  _HomeScreenSmallState createState() => _HomeScreenSmallState();
}

class _HomeScreenSmallState extends State<HomeScreenSmall> {
  var globalRespObj = LoginRepository.loginDetailsResObjGlobal;
  int leftMenuSelection = 1;
  int currentStageTracker = 0;

  // Menu selecion color change flags
  bool _panPressed = true;
  bool _personalDetailPressed = false;
  bool _bankDetailsPressed = false;
  bool _paymentDetailsPressed = false;
  bool _documentPressed = false;
  bool _iPVPressed = false;
  bool _eSignPressed = false;

  // processdone show icon flags
  bool _panProcess = false;
  bool _personalProcess = false;
  bool _bankProcess = false;
  bool _paymentProcess = false;
  bool _documentProcess = false;
  bool _iPVProcess = false;
  bool _eSignProcess = false;

  // namevisible flags
  bool _panText = true;
  bool _personalText = false;
  bool _bankText = false;
  bool _paymentText = false;
  bool _documentText = false;
  bool _iPVText = false;
  bool _eSignText = false;

  var fullName;
  var _documentUpdateFlag = true;

  @override
  void dispose() {
    super.dispose();
  }

  @override
  void initState() {
    super.initState();
    if (LoginRepository.firstTimeLogin) {
      globalRespObj.response.data.message[0].stage = "0";
    } else {
      globalRespObj.response.data.message[0].stage = LoginRepository.globalCurrentStageTracker;
    }
    if (globalRespObj.response.data.message[0].stage != "") {
      leftMenuSelection = int.parse(globalRespObj.response.data.message[0].stage) + 1;
      print("globalRespObj :- " + globalRespObj.response.data.message[0].stage);
      fullName = globalRespObj.response.data.message[0].fullName;
      if (fullName == "") {
        fullName = LoginRepository.loginFullName;
      }
      currentStageTracker = leftMenuSelection;

// screen selection as per stage
      var screenName = Ekycscreenamesmall.pandetailscreen;
      print("Screen name " + screenName.toString());
      if (globalRespObj.response.data.message[0].stage.contains("1")) {
        screenName = Ekycscreenamesmall.personaldetailscreen;
      } else if (globalRespObj.response.data.message[0].stage.contains("2")) {
        screenName = Ekycscreenamesmall.bankdetailscreen;
      } else if (globalRespObj.response.data.message[0].stage.contains("3")) {
        screenName = Ekycscreenamesmall.paymentdetailscreen;
      } else if (globalRespObj.response.data.message[0].stage.contains("4")) {
        screenName = Ekycscreenamesmall.documentdetailscreen;
      } else if (globalRespObj.response.data.message[0].stage.contains("5")) {
        var docObj = globalRespObj.response.data.message[0];
        if (docObj.pancard == "" || docObj.signature == "" || docObj.addressproof == "" || docObj.bankproof == "" || docObj.incomeproof == "" || docObj.photograph == "") {
          screenName = Ekycscreenamesmall.documentdetailscreen;
          _documentUpdateFlag = false;
        } else {
          screenName = Ekycscreenamesmall.ipvdetailscreen;
          _documentUpdateFlag = true;
        }
      } else if (globalRespObj.response.data.message[0].stage.contains("6")) {
        screenName = Ekycscreenamesmall.esigndetailscreen;
      } else if (globalRespObj.response.data.message[0].stage.contains("7")) {
        screenName = Ekycscreenamesmall.esigndetailscreen;
      }
      HomeScreenSmall.screensStreamSmall.sink.add(screenName);

      // screen selection as per stage
    } else {
      fullName = LoginRepository.loginFullName;
      leftMenuSelection = 1;
      HomeScreenSmall.screensStreamSmall.sink.add(Ekycscreenamesmall.pandetailscreen);
    }
    if (EsignRepository.timerObj != null) {
      EsignRepository.timerObj.cancel();
      EsignRepository.timerObj = null;
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: null,
      body: SafeArea(
        child: Column(
          children: [
            header(),
            menuselection(),
            progress(),
            menuselectedview(),
          ],
        ),
      ),
    );
  }

  header() {
    return Container(
      color: Color(0xFF0074C4),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Expanded(
            child: Container(
              padding: EdgeInsets.all(10),
              child: Text(
                "Welcome ! " + fullName,
                style: TextStyle(fontFamily: 'century_gothic', color: Colors.white, fontSize: 15, fontWeight: FontWeight.bold),
              ),
            ),
          ),
          Container(
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                Container(
                  width: 20,
                  height: 20,
                  child: SvgPicture.asset(
                    'asset/svg/signout.svg',
                    color: Colors.white,
                    height: 25,
                    width: 25,
                  ),
                ),
                InkWell(
                  onTap: () {
                    LoginRepository.Esignflag = 0;
                    // Navigator.push(
                    //   context,
                    //   MaterialPageRoute(builder: (context) => LoginUI()),
                    // );
                    Navigator.of(context).pushAndRemoveUntil(
                      MaterialPageRoute(
                        builder: (context) => LoginUI(),
                      ),
                      (Route<dynamic> route) => false,
                    );
                  },
                  child: Container(
                    width: 80,
                    padding: EdgeInsets.only(right: 10, left: 10),
                    child: Text(
                      "SIGN OUT",
                      style: TextStyle(fontFamily: 'century_gothic', color: Colors.white, fontSize: 10, fontWeight: FontWeight.bold),
                    ),
                  ),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }

  menuselection() {
    return Container(
      color: Color(0xFF0074C4),
      child: Column(
        children: [
          Container(
            height: 1,
            color: Colors.grey,
          ),
          Container(
            padding: EdgeInsets.only(left: 5, right: 5, bottom: 5, top: 5),
            child: StreamBuilder<Ekycscreenamesmall>(
                stream: HomeScreenSmall.screensStreamSmall.stream,
                builder: (context, snapshot) {
                  return SingleChildScrollView(
                    scrollDirection: Axis.horizontal,
                    child: Row(
                      children: [
                        InkWell(
                          onTap: () {
                            if (currentStageTracker >= 1 || int.parse(LoginRepository.globalCurrentStageTracker) + 1 >= 1) {
                              if ((int.parse(LoginRepository.globalCurrentStageTracker) + 1) > currentStageTracker) {
                                currentStageTracker = (int.parse(LoginRepository.globalCurrentStageTracker) + 1);
                              }
                              pandetailclick();
                              handleLeftMenuSelection(currentStageTracker, 1);
                              HomeScreenSmall.screensStreamSmall.sink.add(Ekycscreenamesmall.pandetailscreen);
                              LoginRepository.Esignflag = 0;
                            }
                          },
                          child: Container(
                            width: 50,
                            margin: EdgeInsets.only(),
                            child: Column(
                              mainAxisAlignment: MainAxisAlignment.center,
                              crossAxisAlignment: CrossAxisAlignment.center,
                              children: [
                                Container(
                                  margin: EdgeInsets.all(2),
                                  child: _panPressed
                                      ? SvgPicture.asset(
                                          'asset/svg/pancard_svg.svg',
                                          color: Colors.white,
                                          width: 17,
                                          height: 17,
                                        )
                                      : SvgPicture.asset(
                                          'asset/svg/pancard_svg.svg',
                                          color: Colors.yellow[700],
                                          width: 18,
                                          height: 18,
                                        ),
                                  //   child: _panPressed
                                  //       ? Image.asset(
                                  //           'asset/images/pan.png',
                                  //           height: 25,
                                  //           width: 25,
                                  //         )
                                  //       : Image.asset(
                                  //           'asset/images/pan_selected.png',
                                  //           height: 25,
                                  //           width: 25,
                                  //         ),
                                ),
                                Visibility(
                                  visible: _panText,
                                  child: Container(
                                    margin: EdgeInsets.all(1),
                                    child: Center(
                                      child: Text(
                                        "PAN",
                                        style: TextStyle(fontFamily: 'century_gothic', color: _panPressed ? Color(0xFFFFFFFF) : Color(0xFFFAB804), fontSize: 8, fontWeight: FontWeight.bold),
                                      ),
                                    ),
                                  ),
                                ),
                                Visibility(
                                  visible: _panProcess,
                                  child: Container(
                                    width: 15,
                                    height: 15,
                                    child: SvgPicture.asset(
                                      'asset/svg/processcomplete_svg.svg',
                                      width: 20,
                                    ),
                                    // child: Image.asset(
                                    //   'asset/images/processcomplete.png',
                                    // ),
                                  ),
                                ),
                              ],
                            ),
                          ),
                        ),
                        InkWell(
                          onTap: () {
                            if (currentStageTracker >= 2 || int.parse(LoginRepository.globalCurrentStageTracker) + 1 >= 2) {
                              if ((int.parse(LoginRepository.globalCurrentStageTracker) + 1) > currentStageTracker) {
                                currentStageTracker = (int.parse(LoginRepository.globalCurrentStageTracker) + 1);
                              }
                              persondetailclick();
                              handleLeftMenuSelection(currentStageTracker, 2);
                              HomeScreenSmall.screensStreamSmall.sink.add(Ekycscreenamesmall.personaldetailscreen);
                              LoginRepository.Esignflag = 0;
                            }
                          },
                          child: Container(
                            width: 50,
                            child: Column(
                              mainAxisAlignment: MainAxisAlignment.center,
                              crossAxisAlignment: CrossAxisAlignment.center,
                              children: [
                                Container(
                                  margin: EdgeInsets.all(2),
                                  // child: _personalDetailPressed
                                  //     ? Image.asset(
                                  //         'asset/images/personaldetails.png',
                                  //         height: 25,
                                  //         width: 25,
                                  //       )
                                  //     : Image.asset(
                                  //         'asset/images/personal_selected.png',
                                  //         height: 25,
                                  //         width: 25,
                                  //       ),
                                  child: _personalDetailPressed
                                      ? SvgPicture.asset(
                                          'asset/svg/personal_details_svg.svg',
                                          color: Colors.white,
                                          width: 17,
                                          height: 17,
                                        )
                                      : SvgPicture.asset(
                                          'asset/svg/personal_details_svg.svg',
                                          color: Colors.yellow[700],
                                          width: 18,
                                          height: 18,
                                        ),
                                ),
                                Visibility(
                                  visible: _personalText,
                                  child: Container(
                                    margin: EdgeInsets.all(1),
                                    child: Center(
                                      child: Text(
                                        "PERSONAL",
                                        style: TextStyle(fontFamily: 'century_gothic', color: _personalDetailPressed ? Color(0xFFFFFFFF) : Color(0xFFFAB804), fontSize: 8, fontWeight: FontWeight.bold),
                                      ),
                                    ),
                                  ),
                                ),
                                Visibility(
                                  visible: _personalProcess,
                                  child: Container(
                                    width: 15,
                                    height: 15,
                                    // child: Image.asset(
                                    //   'asset/images/processcomplete.png',
                                    // ),
                                    child: SvgPicture.asset(
                                      'asset/svg/processcomplete_svg.svg',
                                      width: 20,
                                    ),
                                  ),
                                ),
                              ],
                            ),
                          ),
                        ),
                        InkWell(
                          onTap: () {
                            if (currentStageTracker >= 3 || int.parse(LoginRepository.globalCurrentStageTracker) + 1 >= 3) {
                              if ((int.parse(LoginRepository.globalCurrentStageTracker) + 1) > currentStageTracker) {
                                currentStageTracker = (int.parse(LoginRepository.globalCurrentStageTracker) + 1);
                              }
                              bankdetailclick();
                              handleLeftMenuSelection(currentStageTracker, 3);
                              HomeScreenSmall.screensStreamSmall.sink.add(Ekycscreenamesmall.bankdetailscreen);
                              LoginRepository.Esignflag = 0;
                            }
                          },
                          child: Container(
                            width: 50,
                            child: Column(
                              mainAxisAlignment: MainAxisAlignment.center,
                              crossAxisAlignment: CrossAxisAlignment.center,
                              children: [
                                Container(
                                  margin: EdgeInsets.all(2),
                                  child: _bankDetailsPressed
                                      ? SvgPicture.asset(
                                          'asset/svg/bank_svg.svg',
                                          color: Colors.white,
                                          width: 17,
                                          height: 17,
                                        )
                                      : SvgPicture.asset(
                                          'asset/svg/bank_svg.svg',
                                          color: Colors.yellow[700],
                                          width: 18,
                                          height: 18,
                                        ),
                                  //   child: _bankDetailsPressed
                                  //       ? Image.asset(
                                  //           'asset/images/bank.png',
                                  //           height: 25,
                                  //           width: 25,
                                  //         )
                                  //       : Image.asset(
                                  //           'asset/images/bank_selected.png',
                                  //           height: 25,
                                  //           width: 25,
                                  //         ),
                                ),
                                Visibility(
                                  visible: _bankText,
                                  child: Container(
                                    margin: EdgeInsets.all(1),
                                    child: Center(
                                      child: Text(
                                        "BANK",
                                        style: TextStyle(fontFamily: 'century_gothic', color: _bankDetailsPressed ? Color(0xFFFFFFFF) : Color(0xFFFAB804), fontSize: 8, fontWeight: FontWeight.bold),
                                      ),
                                    ),
                                  ),
                                ),
                                Visibility(
                                  visible: _bankProcess,
                                  child: Container(
                                    width: 15,
                                    height: 15,
                                    // child: Image.asset(
                                    //   'asset/images/processcomplete.png',
                                    // ),
                                    child: SvgPicture.asset(
                                      'asset/svg/processcomplete_svg.svg',
                                      width: 20,
                                    ),
                                  ),
                                ),
                              ],
                            ),
                          ),
                        ),
                        InkWell(
                          onTap: () {
                            if (currentStageTracker >= 4 || int.parse(LoginRepository.globalCurrentStageTracker) + 1 >= 4) {
                              if ((int.parse(LoginRepository.globalCurrentStageTracker) + 1) > currentStageTracker) {
                                currentStageTracker = (int.parse(LoginRepository.globalCurrentStageTracker) + 1);
                              }
                              paymentdetailclick();
                              handleLeftMenuSelection(currentStageTracker, 4);
                              HomeScreenSmall.screensStreamSmall.sink.add(Ekycscreenamesmall.paymentdetailscreen);
                              LoginRepository.Esignflag = 0;
                            }
                          },
                          child: Container(
                            width: 50,
                            child: Column(
                              mainAxisAlignment: MainAxisAlignment.center,
                              crossAxisAlignment: CrossAxisAlignment.center,
                              children: [
                                Container(
                                  margin: EdgeInsets.all(2),
                                  // child: _paymentDetailsPressed
                                  //     ? Image.asset(
                                  //         'asset/images/payment.png',
                                  //         height: 20,
                                  //         width: 20,
                                  //       )
                                  //     : Image.asset(
                                  //         'asset/images/payment_selected.png',
                                  //         height: 20,
                                  //         width: 20,
                                  //       ),
                                  child: _paymentDetailsPressed
                                      ? SvgPicture.asset(
                                          'asset/svg/payment_svg.svg',
                                          color: Colors.white,
                                          width: 17,
                                          height: 17,
                                        )
                                      : SvgPicture.asset(
                                          'asset/svg/payment_svg.svg',
                                          color: Colors.yellow[700],
                                          width: 18,
                                          height: 18,
                                        ),
                                ),
                                Visibility(
                                  visible: _paymentText,
                                  child: Container(
                                    margin: EdgeInsets.all(1),
                                    child: Center(
                                      child: Text(
                                        "PAYMENT",
                                        style: TextStyle(fontFamily: 'century_gothic', color: _paymentDetailsPressed ? Color(0xFFFFFFFF) : Color(0xFFFAB804), fontSize: 8, fontWeight: FontWeight.bold),
                                      ),
                                    ),
                                  ),
                                ),
                                Visibility(
                                  visible: _paymentProcess,
                                  child: Container(
                                    width: 15,
                                    height: 15,
                                    // child: Image.asset(
                                    //   'asset/images/processcomplete.png',
                                    // ),
                                    child: SvgPicture.asset(
                                      'asset/svg/processcomplete_svg.svg',
                                      width: 20,
                                    ),
                                  ),
                                ),
                              ],
                            ),
                          ),
                        ),
                        InkWell(
                          onTap: () {
                            if (currentStageTracker >= 5 || int.parse(LoginRepository.globalCurrentStageTracker) + 1 >= 5) {
                              if ((int.parse(LoginRepository.globalCurrentStageTracker) + 1) > currentStageTracker) {
                                currentStageTracker = (int.parse(LoginRepository.globalCurrentStageTracker) + 1);
                              }
                              documentdetailclick();
                              handleLeftMenuSelection(currentStageTracker, 5);
                              HomeScreenSmall.screensStreamSmall.sink.add(Ekycscreenamesmall.documentdetailscreen);
                              LoginRepository.Esignflag = 0;
                            }
                          },
                          child: Container(
                            width: 55,
                            child: Column(
                              mainAxisAlignment: MainAxisAlignment.center,
                              crossAxisAlignment: CrossAxisAlignment.center,
                              children: [
                                Container(
                                  margin: EdgeInsets.all(2),
                                  // child: _documentPressed
                                  //     ? Image.asset(
                                  //         'asset/images/document.png',
                                  //         height: 20,
                                  //         width: 20,
                                  //       )
                                  //     : Image.asset(
                                  //         'asset/images/document_selected.png',
                                  //         height: 20,
                                  //         width: 20,
                                  //       ),
                                  child: _documentPressed
                                      ? SvgPicture.asset(
                                          'asset/svg/document_svg.svg',
                                          color: Colors.white,
                                          width: 15,
                                          height: 15,
                                        )
                                      : SvgPicture.asset(
                                          'asset/svg/document_svg.svg',
                                          color: Colors.yellow[700],
                                          width: 17,
                                          height: 17,
                                        ),
                                ),
                                Visibility(
                                  visible: _documentText,
                                  child: Container(
                                    margin: EdgeInsets.all(1),
                                    child: Center(
                                      child: Text(
                                        "DOCUMENTS",
                                        style: TextStyle(fontFamily: 'century_gothic', color: _documentPressed ? Color(0xFFFFFFFF) : Color(0xFFFAB804), fontSize: 8, fontWeight: FontWeight.bold),
                                      ),
                                    ),
                                  ),
                                ),
                                Visibility(
                                  visible: _documentProcess,
                                  child: Container(
                                    width: 15,
                                    height: 15,
                                    // child: Image.asset(
                                    //   'asset/images/processcomplete.png',
                                    // ),
                                    child: SvgPicture.asset(
                                      'asset/svg/processcomplete_svg.svg',
                                      width: 20,
                                    ),
                                  ),
                                ),
                              ],
                            ),
                          ),
                        ),
                        InkWell(
                          onTap: () {
                            if (this._documentUpdateFlag) {
                              if (currentStageTracker >= 6 || int.parse(LoginRepository.globalCurrentStageTracker) + 1 >= 6) {
                                if ((int.parse(LoginRepository.globalCurrentStageTracker) + 1) > currentStageTracker) {
                                  currentStageTracker = (int.parse(LoginRepository.globalCurrentStageTracker) + 1);
                                }
                                ipvdetailclick();
                                handleLeftMenuSelection(currentStageTracker, 6);
                                HomeScreenSmall.screensStreamSmall.sink.add(Ekycscreenamesmall.ipvdetailscreen);
                                LoginRepository.Esignflag = 0;
                              }
                            }
                          },
                          child: Container(
                            width: 50,
                            child: Column(
                              mainAxisAlignment: MainAxisAlignment.center,
                              crossAxisAlignment: CrossAxisAlignment.center,
                              children: [
                                Container(
                                  margin: EdgeInsets.all(2),
                                  // child: _iPVPressed
                                  //     ? Image.asset(
                                  //         'asset/images/ipv.png',
                                  //         height: 20,
                                  //         width: 20,
                                  //       )
                                  //     : Image.asset(
                                  //         'asset/images/ipv_selected.png',
                                  //         height: 20,
                                  //         width: 20,
                                  //       ),
                                  child: _iPVPressed
                                      ? SvgPicture.asset(
                                          'asset/svg/ipv_svg.svg',
                                          color: Colors.white,
                                          width: 17,
                                          height: 17,
                                        )
                                      : SvgPicture.asset(
                                          'asset/svg/ipv_svg.svg',
                                          color: Colors.yellow[700],
                                          width: 18,
                                          height: 18,
                                        ),
                                ),
                                Visibility(
                                  visible: _iPVText,
                                  child: Container(
                                    margin: EdgeInsets.all(1),
                                    child: Center(
                                      child: Text(
                                        "IPV",
                                        style: TextStyle(fontFamily: 'century_gothic', color: _iPVPressed ? Color(0xFFFFFFFF) : Color(0xFFFAB804), fontSize: 8, fontWeight: FontWeight.bold),
                                      ),
                                    ),
                                  ),
                                ),
                                Visibility(
                                  visible: _iPVProcess,
                                  child: Container(
                                    width: 15,
                                    height: 15,
                                    // child: Image.asset(
                                    //   'asset/images/processcomplete.png',
                                    // ),
                                    child: SvgPicture.asset(
                                      'asset/svg/processcomplete_svg.svg',
                                      width: 20,
                                    ),
                                  ),
                                ),
                              ],
                            ),
                          ),
                        ),
                        InkWell(
                          onTap: () {
                            if (currentStageTracker >= 7 || int.parse(LoginRepository.globalCurrentStageTracker) + 1 >= 7) {
                              if ((int.parse(LoginRepository.globalCurrentStageTracker) + 1) > currentStageTracker) {
                                currentStageTracker = (int.parse(LoginRepository.globalCurrentStageTracker) + 1);
                              }
                              esigndetailclick();
                              handleLeftMenuSelection(currentStageTracker, 7);
                              HomeScreenSmall.screensStreamSmall.sink.add(Ekycscreenamesmall.esigndetailscreen);
                              LoginRepository.Esignflag = 0;
                            }
                          },
                          child: Container(
                            width: 50,
                            child: Column(
                              mainAxisAlignment: MainAxisAlignment.center,
                              crossAxisAlignment: CrossAxisAlignment.center,
                              children: [
                                Container(
                                  margin: EdgeInsets.all(2),
                                  // child: _eSignPressed
                                  //     ? Image.asset(
                                  //         'asset/images/esign.png',
                                  //         height: 15,
                                  //         width: 15,
                                  //       )
                                  //     : Image.asset(
                                  //         'asset/images/Esign_selected.png',
                                  //         height: 15,
                                  //         width: 15,
                                  //       ),
                                  child: _eSignPressed
                                      ? SvgPicture.asset(
                                          'asset/svg/esign_svg.svg',
                                          color: Colors.white,
                                          width: 17,
                                          height: 17,
                                        )
                                      : SvgPicture.asset(
                                          'asset/svg/esign_svg.svg',
                                          color: Colors.yellow[700],
                                          width: 18,
                                          height: 18,
                                        ),
                                ),
                                Visibility(
                                  visible: _eSignText,
                                  child: Container(
                                    margin: EdgeInsets.all(1),
                                    child: Center(
                                      child: Text(
                                        "E-SIGN",
                                        style: TextStyle(fontFamily: 'century_gothic', color: _eSignPressed ? Color(0xFFFFFFFF) : Color(0xFFFAB804), fontSize: 8, fontWeight: FontWeight.bold),
                                      ),
                                    ),
                                  ),
                                ),
                                Visibility(
                                  visible: _eSignProcess,
                                  child: Container(
                                    width: 15,
                                    height: 15,
                                    // child: Image.asset(
                                    //   'asset/images/processcomplete.png',
                                    // ),
                                    child: SvgPicture.asset(
                                      'asset/svg/processcomplete_svg.svg',
                                      width: 20,
                                    ),
                                  ),
                                ),
                              ],
                            ),
                          ),
                        ),
                      ],
                    ),
                  );
                }),
          ),
        ],
      ),
    );
  }

  progress() {
    return Container(
      child: StreamBuilder<String>(
          stream: HomeScreenSmall.percentageFlagSmall,
          builder: (context, snapshot) {
            if (snapshot.hasData && snapshot.hasData != null) {
              return Container(
                padding: EdgeInsets.only(top: 5),
                decoration: new BoxDecoration(
                  color: Color(0xFF0074C4),
                  borderRadius: new BorderRadius.only(
                    bottomRight: const Radius.circular(10),
                    bottomLeft: const Radius.circular(10),
                  ),
                ),
                child: new LinearPercentIndicator(
                  animation: true,
                  lineHeight: 15.0,
                  animationDuration: 1000,
                  percent: double.parse(snapshot.data),
                  linearStrokeCap: LinearStrokeCap.roundAll,
                  backgroundColor: Colors.grey[200],
                  progressColor: Color(0xFFFAB804),
                ),
              );
            } else {
              return Container(
                padding: EdgeInsets.only(top: 5),
                decoration: new BoxDecoration(
                  color: Color(0xFF0074C4),
                  borderRadius: new BorderRadius.only(
                    bottomRight: const Radius.circular(10),
                    bottomLeft: const Radius.circular(10),
                  ),
                ),
                child: new LinearPercentIndicator(
                  animation: true,
                  lineHeight: 15.0,
                  animationDuration: 1000,
                  percent: 0.1,
                  linearStrokeCap: LinearStrokeCap.roundAll,
                  backgroundColor: Colors.grey[200],
                  progressColor: Color(0xFFFAB804),
                ),
              );
            }
          }),
    );
  }

  menuselectedview() {
    return Container(
      child: Expanded(
        child: Container(
          margin: EdgeInsets.only(top: 20),
          child: StreamBuilder<Ekycscreenamesmall>(
              stream: HomeScreenSmall.screensStreamSmall.stream,
              builder: (context, snapshote) {
                switch (snapshote.data) {
                  case Ekycscreenamesmall.pandetailscreen:
                    pandetailclick();
                    return PandetailsUI();
                  case Ekycscreenamesmall.personaldetailscreen:
                    persondetailclick();
                    return PersonalDetailsUI();
                  case Ekycscreenamesmall.bankdetailscreen:
                    bankdetailclick();
                    return BankScreenUI();
                  case Ekycscreenamesmall.paymentdetailscreen:
                    paymentdetailclick();
                    return PaymentUI();
                  case Ekycscreenamesmall.documentdetailscreen:
                    documentdetailclick();
                    return DocumentScreenUI();
                  case Ekycscreenamesmall.ipvdetailscreen:
                    ipvdetailclick();
                    return IPVSCreenUI();
                  case Ekycscreenamesmall.esigndetailscreen:
                    esigndetailclick();
                    return EsignScreenUI();
                }

                return Container();
              }),
        ),
      ),
    );
  }

  void pandetailclick() {
    leftMenuSelection = 1;
    _panPressed = false;
    _personalDetailPressed = true;
    _bankDetailsPressed = true;
    _paymentDetailsPressed = true;
    _documentPressed = true;
    _iPVPressed = true;
    _eSignPressed = true;

    _panProcess = false;
    _personalProcess = false;
    _bankProcess = false;
    _paymentProcess = false;
    _documentProcess = false;
    _iPVProcess = false;
    _eSignProcess = false;

    _panText = true;
    _personalText = false;
    _bankText = false;
    _paymentText = false;
    _documentText = false;
    _iPVText = false;
    _eSignText = false;
  }

  void persondetailclick() {
    leftMenuSelection = 2;
    _personalDetailPressed = false;
    _panPressed = true;
    _bankDetailsPressed = true;
    _paymentDetailsPressed = true;
    _documentPressed = true;
    _iPVPressed = true;
    _eSignPressed = true;

    _panProcess = true;
    _personalProcess = false;
    _bankProcess = false;
    _paymentProcess = false;
    _documentProcess = false;
    _iPVProcess = false;
    _eSignProcess = false;

    _panText = false;
    _personalText = true;
    _bankText = false;
    _paymentText = false;
    _documentText = false;
    _iPVText = false;
    _eSignText = false;
  }

  void bankdetailclick() {
    leftMenuSelection = 3;
    _bankDetailsPressed = false;

    _panPressed = true;
    _personalDetailPressed = true;
    _paymentDetailsPressed = true;
    _documentPressed = true;
    _iPVPressed = true;
    _eSignPressed = true;

    _panProcess = true;
    _personalProcess = true;
    _bankProcess = false;
    _paymentProcess = false;
    _documentProcess = false;
    _iPVProcess = false;
    _eSignProcess = false;

    _panText = false;
    _personalText = false;
    _bankText = true;
    _paymentText = false;
    _documentText = false;
    _iPVText = false;
    _eSignText = false;
  }

  void paymentdetailclick() {
    leftMenuSelection = 4;
    _paymentDetailsPressed = false;

    _panPressed = true;
    _personalDetailPressed = true;
    _bankDetailsPressed = true;
    _documentPressed = true;
    _iPVPressed = true;
    _eSignPressed = true;

    _panProcess = true;
    _personalProcess = true;
    _bankProcess = true;
    _paymentProcess = false;
    _documentProcess = false;
    _iPVProcess = false;
    _eSignProcess = false;

    _panText = false;
    _personalText = false;
    _bankText = false;
    _paymentText = true;
    _documentText = false;
    _iPVText = false;
    _eSignText = false;
  }

  void documentdetailclick() {
    leftMenuSelection = 5;
    _documentPressed = false;

    _panPressed = true;
    _personalDetailPressed = true;
    _bankDetailsPressed = true;
    _paymentDetailsPressed = true;
    _iPVPressed = true;
    _eSignPressed = true;

    _panProcess = true;
    _personalProcess = true;
    _bankProcess = true;
    _paymentProcess = true;
    _documentProcess = false;
    _iPVProcess = false;
    _eSignProcess = false;

    _panText = false;
    _personalText = false;
    _bankText = false;
    _paymentText = false;
    _documentText = true;
    _iPVText = false;
    _eSignText = false;
  }

  void ipvdetailclick() {
    leftMenuSelection = 6;
    _iPVPressed = false;

    _panPressed = true;
    _personalDetailPressed = true;
    _bankDetailsPressed = true;
    _paymentDetailsPressed = true;
    _documentPressed = true;
    _eSignPressed = true;

    _panProcess = true;
    _personalProcess = true;
    _bankProcess = true;
    _paymentProcess = true;
    _documentProcess = true;
    _iPVProcess = false;
    _eSignProcess = false;

    _panText = false;
    _personalText = false;
    _bankText = false;
    _paymentText = false;
    _documentText = false;
    _iPVText = true;
    _eSignText = false;
  }

  void esigndetailclick() {
    leftMenuSelection = 7;
    _eSignPressed = false;

    _panPressed = true;
    _personalDetailPressed = true;
    _bankDetailsPressed = true;
    _paymentDetailsPressed = true;
    _documentPressed = true;
    _iPVPressed = true;

    _panProcess = true;
    _personalProcess = true;
    _bankProcess = true;
    _paymentProcess = true;
    _documentProcess = true;
    _iPVProcess = true;
    _eSignProcess = false;

    _panText = false;
    _personalText = false;
    _bankText = false;
    _paymentText = false;
    _documentText = false;
    _iPVText = false;
    _eSignText = true;
  }

  void handleLeftMenuSelection(int currentStage, int destStage) {
    print("Pan Clicked");
    print("\n Current Stage = $currentStage\n dest Stage = $destStage");

    switch (currentStage) {
      case 1:
        setState(
          () {
            _panPressed = false;
            _personalDetailPressed = true;
            _bankDetailsPressed = true;
            _paymentDetailsPressed = true;
            _documentPressed = true;
            _eSignPressed = true;

            _panProcess = false;
            _personalProcess = false;
            _bankProcess = false;
            _paymentProcess = false;
            _documentProcess = false;
            _eSignProcess = false;

            if (_documentUpdateFlag) {
              _iPVPressed = true;
              _iPVProcess = false;
            }
          },
        );
        break;
      case 2:
        setState(
          () {
            _panPressed = false;
            _personalDetailPressed = true;
            _bankDetailsPressed = true;
            _paymentDetailsPressed = true;
            _documentPressed = true;
            _eSignPressed = true;

            _panProcess = true;
            _personalProcess = false;
            _bankProcess = false;
            _paymentProcess = false;
            _documentProcess = false;
            _eSignProcess = false;

            if (_documentUpdateFlag) {
              _iPVPressed = true;
              _iPVProcess = false;
            }
          },
        );
        break;
      case 3:
        setState(
          () {
            _panPressed = false;
            _personalDetailPressed = true;
            _bankDetailsPressed = true;
            _paymentDetailsPressed = true;
            _documentPressed = true;
            _eSignPressed = true;

            _panProcess = true;
            _personalProcess = true;
            _bankProcess = false;
            _paymentProcess = false;
            _documentProcess = false;
            _eSignProcess = false;

            if (_documentUpdateFlag) {
              _iPVPressed = true;
              _iPVProcess = false;
            }
          },
        );
        break;
      case 4:
        setState(
          () {
            _panPressed = false;
            _personalDetailPressed = true;
            _bankDetailsPressed = true;
            _paymentDetailsPressed = true;
            _documentPressed = true;
            _eSignPressed = true;

            _panProcess = true;
            _personalProcess = true;
            _bankProcess = true;
            _paymentProcess = false;
            _documentProcess = false;
            _eSignProcess = false;

            if (_documentUpdateFlag) {
              _iPVPressed = true;
              _iPVProcess = false;
            }
          },
        );
        break;
      case 5:
        setState(
          () {
            _panPressed = false;
            _personalDetailPressed = true;
            _bankDetailsPressed = true;
            _paymentDetailsPressed = true;
            _documentPressed = true;
            _eSignPressed = true;

            _panProcess = true;
            _personalProcess = true;
            _bankProcess = true;
            _paymentProcess = true;
            _documentProcess = true;
            _eSignProcess = false;

            if (_documentUpdateFlag) {
              _iPVPressed = true;
              _iPVProcess = false;
            }
          },
        );
        break;
      case 6:
        setState(
          () {
            _panPressed = false;
            _personalDetailPressed = true;
            _bankDetailsPressed = true;
            _paymentDetailsPressed = true;
            _documentPressed = true;
            _eSignPressed = true;

            _panProcess = true;
            _personalProcess = true;
            _bankProcess = true;
            _paymentProcess = true;
            _documentProcess = true;
            _eSignProcess = false;

            if (_documentUpdateFlag) {
              _iPVPressed = true;
              _iPVProcess = true;
            }
          },
        );

        break;
      case 7:
        setState(
          () {
            _panPressed = false;
            _personalDetailPressed = true;
            _bankDetailsPressed = true;
            _paymentDetailsPressed = true;
            _documentPressed = true;
            _eSignPressed = true;

            _panProcess = true;
            _personalProcess = true;
            _bankProcess = true;
            _paymentProcess = true;
            _documentProcess = true;
            _eSignProcess = true;

            if (_documentUpdateFlag) {
              _iPVPressed = true;
              _iPVProcess = true;
            }
          },
        );

        break;
      default:
    }

    //Set background of selection
    setState(() {
      switch (destStage) {
        case 1:
          _panPressed = false;
          _personalDetailPressed = true;
          _bankDetailsPressed = true;
          _paymentDetailsPressed = true;
          _documentPressed = true;
          _eSignPressed = true;

          if (_documentUpdateFlag) {
            _iPVPressed = true;
          }

          break;
        case 2:
          _panPressed = true;
          _personalDetailPressed = false;
          _bankDetailsPressed = true;
          _paymentDetailsPressed = true;
          _documentPressed = true;
          _eSignPressed = true;

          if (_documentUpdateFlag) {
            _iPVPressed = true;
          }

          break;
        case 3:
          _panPressed = true;
          _personalDetailPressed = true;
          _bankDetailsPressed = false;
          _paymentDetailsPressed = true;
          _documentPressed = true;
          _eSignPressed = true;

          if (_documentUpdateFlag) {
            _iPVPressed = true;
          }

          break;
        case 4:
          _panPressed = true;
          _personalDetailPressed = true;
          _bankDetailsPressed = true;
          _paymentDetailsPressed = false;
          _documentPressed = true;
          _eSignPressed = true;

          if (_documentUpdateFlag) {
            _iPVPressed = true;
          }

          break;
        case 5:
          _panPressed = true;
          _personalDetailPressed = true;
          _bankDetailsPressed = true;
          _paymentDetailsPressed = true;
          _documentPressed = false;
          _eSignPressed = true;

          if (_documentUpdateFlag) {
            _iPVPressed = true;
          }

          break;
        case 6:
          _panPressed = true;
          _personalDetailPressed = true;
          _bankDetailsPressed = true;
          _paymentDetailsPressed = true;
          _documentPressed = true;
          _eSignPressed = true;

          if (_documentUpdateFlag) {
            _iPVPressed = false;
          }

          break;
        case 7:
          _panPressed = true;
          _personalDetailPressed = true;
          _bankDetailsPressed = true;
          _paymentDetailsPressed = true;
          _documentPressed = true;
          _eSignPressed = false;

          if (_documentUpdateFlag) {
            _iPVPressed = true;
          }
          break;
        default:
      }
    });
  }
}
