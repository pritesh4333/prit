import 'package:e_kyc/Login/UI/BankDetailsScreen/View/BankScreenUI.dart';
import 'package:e_kyc/Login/UI/DocumentScreen/DocumentScreenUI.dart';
import 'package:e_kyc/Login/UI/EsignScreen/repository/EsignRepository.dart';
import 'package:e_kyc/Login/UI/EsignScreen/view/EsignScreeUI.dart';
import 'package:e_kyc/Login/UI/IPVScreen/IPVScreenUI.dart';
import 'package:e_kyc/Login/UI/Login/repository/LoginRepository.dart';
import 'package:e_kyc/Login/UI/PanDetailsScreen/PandetailsUI.dart';
import 'package:e_kyc/Login/UI/PaymentScreen/view/PaymentUI.dart';
import 'package:e_kyc/Login/UI/PersonalDetailsScreen/view/PersonalDetailsUI.dart';
import 'package:e_kyc/Login/UI/ThemeColors.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/svg.dart';
import 'package:percent_indicator/linear_percent_indicator.dart';
import 'package:rxdart/subjects.dart';

enum Ekycscreenamelarge {
  pandetailscreen,
  personaldetailscreen,
  bankdetailscreen,
  paymentdetailscreen,
  documentdetailscreen,
  ipvdetailscreen,
  esigndetailscreen,
}

class HomeScreenLarge extends StatefulWidget {
  // static final _screensStream = BehaviorSubject<Widget>.seeded(PanDetailsScreenLarge());
  static final screensStreamLarge = BehaviorSubject<Ekycscreenamelarge>.seeded(Ekycscreenamelarge.pandetailscreen);

  static final percentageFlagLarge = BehaviorSubject<String>.seeded("0.142");

  @override
  HomeScreenLargeState createState() => HomeScreenLargeState();

  void dispose() {
    screensStreamLarge.close();
    percentageFlagLarge.close();
  }
}

class HomeScreenLargeState extends State<HomeScreenLarge> {
  var globalRespObj = LoginRepository.loginDetailsResObjGlobal;
  // Menu selecion color change
  bool _panPressed = false;
  bool _personalDetailPressed = true;
  bool _bankDetailsPressed = true;
  bool _paymentDetailsPressed = true;
  bool _documentPressed = true;
  bool _iPVPressed = true;
  bool _eSignPressed = true;

  // processdone show icon
  bool _panProcess = true;
  bool _personalProcess = false;
  bool _bankProcess = false;
  bool _paymentProcess = false;
  bool _documentProcess = false;
  bool _iPVProcess = false;
  bool _eSignProcess = false;

  int leftMenuSelection = 1;
  var fullName;

  int currentStageTracker = 0;
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
      var screenName = Ekycscreenamelarge.pandetailscreen;
      print("Screen name " + screenName.toString());
      if (globalRespObj.response.data.message[0].stage.contains("1")) {
        screenName = Ekycscreenamelarge.personaldetailscreen;
      } else if (globalRespObj.response.data.message[0].stage.contains("2")) {
        screenName = Ekycscreenamelarge.bankdetailscreen;
      } else if (globalRespObj.response.data.message[0].stage.contains("3")) {
        screenName = Ekycscreenamelarge.paymentdetailscreen;
      } else if (globalRespObj.response.data.message[0].stage.contains("4")) {
        screenName = Ekycscreenamelarge.documentdetailscreen;
      } else if (globalRespObj.response.data.message[0].stage.contains("5")) {
        var docObj = globalRespObj.response.data.message[0];
        if (docObj.pancard == "" || docObj.signature == "" || docObj.addressproof == "" || docObj.bankproof == "" || docObj.incomeproof == "" || docObj.photograph == "") {
          screenName = Ekycscreenamelarge.documentdetailscreen;
          _documentUpdateFlag = false;
        } else {
          screenName = Ekycscreenamelarge.ipvdetailscreen;
          _documentUpdateFlag = true;
        }
      } else if (globalRespObj.response.data.message[0].stage.contains("6")) {
        screenName = Ekycscreenamelarge.esigndetailscreen;
      } else if (globalRespObj.response.data.message[0].stage.contains("7")) {
        screenName = Ekycscreenamelarge.esigndetailscreen;
      }
      HomeScreenLarge.screensStreamLarge.sink.add(screenName);
      // screen selection as per stage
    } else {
      fullName = LoginRepository.loginFullName;
      leftMenuSelection = 1;
      HomeScreenLarge.screensStreamLarge.sink.add(Ekycscreenamelarge.pandetailscreen);
    }
    if (EsignRepository.timerObj != null) {
      EsignRepository.timerObj.cancel();
      EsignRepository.timerObj = null;
    }
  }

  Widget getSVG() {
    String assetName = 'assets/images/bank.svg';
    Widget svgIcon = new SvgPicture.asset(
      assetName,
      color: Colors.red,
    );
    return svgIcon;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: null,
      body: SafeArea(
        child: Container(
          child: Container(
            child: Row(
              children: [
                Container(
                  decoration: new BoxDecoration(
                    color: Color(0xFF0074C4),
                    borderRadius: new BorderRadius.only(
                      topRight: const Radius.circular(25.0),
                      bottomRight: const Radius.circular(25.0),
                    ),
                  ),
                  child: Column(
                    children: [
                      Row(
                        children: [
                          Container(
                            width: 220,
                            child: Row(
                              mainAxisAlignment: MainAxisAlignment.center,
                              children: [
                                Container(
                                  padding: EdgeInsets.all(10),
                                  child: Text(
                                    "Welcome ! " + fullName,
                                    style: TextStyle(color: Colors.white, fontSize: 14, fontFamily: 'century_gothic', fontWeight: FontWeight.bold),
                                  ),
                                ),
                              ],
                            ),
                          ),
                        ],
                      ),
                      StreamBuilder<String>(
                          stream: HomeScreenLarge.percentageFlagLarge,
                          builder: (context, snapshot) {
                            if (snapshot.hasData && snapshot.hasData != null) {
                              var percentageTxt = "0%";
                              if (snapshot.data.contains("0.142")) {
                                percentageTxt = "15%";
                              } else if (snapshot.data.contains("0.205")) {
                                percentageTxt = "30%";
                              } else if (snapshot.data.contains("0.405")) {
                                percentageTxt = "45%";
                              } else if (snapshot.data.contains("0.605")) {
                                percentageTxt = "60%";
                              } else if (snapshot.data.contains("0.805")) {
                                percentageTxt = "75%";
                              } else if (snapshot.data.contains("0.905")) {
                                percentageTxt = "90%";
                              } else if (snapshot.data.toString() == "1") {
                                percentageTxt = "100%";
                              }

                              return Container(
                                width: 200,
                                padding: EdgeInsets.all(15),
                                child: Row(
                                  mainAxisAlignment: MainAxisAlignment.center,
                                  children: [
                                    Text(
                                      "PROCESS COMPLETED $percentageTxt",
                                      style: TextStyle(color: Colors.white, fontFamily: 'century_gothic', fontSize: 10, fontWeight: FontWeight.bold),
                                    ),
                                  ],
                                ),
                              );
                            } else {
                              return Container();
                            }
                          }),
                      Container(
                        child: StreamBuilder<String>(
                            stream: HomeScreenLarge.percentageFlagLarge,
                            builder: (context, snapshot) {
                              if (snapshot.hasData && snapshot.hasData != null) {
                                return Container(
                                  padding: EdgeInsets.only(left: 20, right: 20),
                                  child: Row(
                                    mainAxisAlignment: MainAxisAlignment.center,
                                    children: [
                                      new LinearPercentIndicator(
                                        width: 200,
                                        animation: true,
                                        lineHeight: 15.0,
                                        animationDuration: 1000,
                                        percent: double.parse(snapshot.data),
                                        linearStrokeCap: LinearStrokeCap.roundAll,
                                        backgroundColor: Colors.grey[200],
                                        progressColor: Color(0xFFFAB804),
                                      ),
                                    ],
                                  ),
                                );
                              } else {
                                return Container();
                              }
                            }),
                      ),
                      Container(
                        child: StreamBuilder<Ekycscreenamelarge>(
                            stream: HomeScreenLarge.screensStreamLarge.stream,
                            builder: (context, snapshot) {
                              return Container(
                                child: Column(
                                  children: [
                                    InkWell(
                                      onTap: () {
                                        if (currentStageTracker >= 1 || int.parse(LoginRepository.globalCurrentStageTracker) + 1 >= 1) {
                                          if ((int.parse(LoginRepository.globalCurrentStageTracker) + 1) > currentStageTracker) {
                                            currentStageTracker = (int.parse(LoginRepository.globalCurrentStageTracker) + 1);
                                          }
                                          pandetailclick();
                                          handleLeftMenuSelection(currentStageTracker, 1);
                                          HomeScreenLarge.screensStreamLarge.sink.add(Ekycscreenamelarge.pandetailscreen);
                                          LoginRepository.Esignflag = 0;
                                        }
                                      },
                                      child: Container(
                                        alignment: Alignment.centerLeft,
                                        width: 250,
                                        child: Row(
                                          crossAxisAlignment: CrossAxisAlignment.center,
                                          children: [
                                            Container(
                                              padding: EdgeInsets.only(left: 10, top: 10, bottom: 10),
                                              margin: EdgeInsets.only(top: 20),
                                              width: 235,
                                              color: _panPressed ? Color(0xFF0074C4) : Color(0XFF4d9ed6),
                                              child: Row(
                                                children: [
                                                  Container(
                                                    width: 22,
                                                    height: 22,
                                                    // child: Image.asset(
                                                    //   'asset/images/pan.png',
                                                    // ),
                                                    child: SvgPicture.asset(
                                                      'asset/svg/pancard_svg.svg',
                                                      color: Colors.white,
                                                      height: 22,
                                                      width: 22,
                                                    ),
                                                  ),
                                                  Container(
                                                    padding: EdgeInsets.only(left: 15),
                                                    alignment: Alignment.centerLeft,
                                                    child: Center(
                                                      child: Text(
                                                        "PAN DETAILS",
                                                        style: TextStyle(fontFamily: 'century_gothic', color: _panPressed ? Color(0xFFFFFFFF) : Color(0xFFFAB804), fontSize: 11, fontWeight: FontWeight.bold),
                                                      ),
                                                    ),
                                                  ),
                                                  Expanded(
                                                    child: Visibility(
                                                      visible: _panProcess,
                                                      child: Container(
                                                        padding: EdgeInsets.only(
                                                          right: 10,
                                                        ),
                                                        alignment: Alignment.centerRight,
                                                        width: 50,
                                                        height: 20,
                                                        // child: Image.asset(
                                                        //   'asset/images/processcomplete.png',
                                                        // ),
                                                        child: SvgPicture.asset(
                                                          'asset/svg/processcomplete_svg.svg',
                                                          width: 20,
                                                        ),
                                                      ),
                                                    ),
                                                  ),
                                                ],
                                              ),
                                            ),
                                            Container(
                                              margin: EdgeInsets.only(top: 20),
                                              alignment: Alignment.center,
                                              width: 15,
                                              height: 50,
                                              color: _panPressed ? Color(0xFF0074C4) : Color(0xFFFAB804),
                                            ),
                                          ],
                                        ),
                                      ),
                                    ),
                                    Container(
                                      width: 250,
                                      height: 1,
                                      color: Colors.grey,
                                    ),
                                    InkWell(
                                      onTap: () {
                                        if (currentStageTracker >= 2 || int.parse(LoginRepository.globalCurrentStageTracker) + 1 >= 2) {
                                          if ((int.parse(LoginRepository.globalCurrentStageTracker) + 1) > currentStageTracker) {
                                            currentStageTracker = (int.parse(LoginRepository.globalCurrentStageTracker) + 1);
                                          }
                                          persondetailclick();
                                          handleLeftMenuSelection(currentStageTracker, 2);
                                          HomeScreenLarge.screensStreamLarge.sink.add(Ekycscreenamelarge.personaldetailscreen);
                                          LoginRepository.Esignflag = 0;
                                        }
                                      },
                                      child: Container(
                                        alignment: Alignment.centerLeft,
                                        width: 250,
                                        child: Row(
                                          crossAxisAlignment: CrossAxisAlignment.center,
                                          children: [
                                            Container(
                                              padding: EdgeInsets.only(left: 10, top: 10, bottom: 10),
                                              width: 235,
                                              color: _personalDetailPressed ? Color(0xFF0074C4) : Color(0XFF4d9ed6),
                                              child: Row(
                                                children: [
                                                  Container(
                                                    width: 22,
                                                    height: 22,
                                                    // child: Image.asset(
                                                    //   'asset/images/personaldetails.png',
                                                    // ),
                                                    child: SvgPicture.asset(
                                                      'asset/svg/personal_details_svg.svg',
                                                      color: Colors.white,
                                                    ),
                                                  ),
                                                  Container(
                                                    padding: EdgeInsets.only(left: 15),
                                                    alignment: Alignment.centerLeft,
                                                    child: Center(
                                                      child: Text(
                                                        "PERSONAL DETAILS",
                                                        style: TextStyle(fontFamily: 'century_gothic', color: _personalDetailPressed ? Color(0xFFFFFFFF) : Color(0xFFFAB804), fontSize: 11, fontWeight: FontWeight.bold),
                                                      ),
                                                    ),
                                                  ),
                                                  Expanded(
                                                    child: Visibility(
                                                      visible: _personalProcess,
                                                      child: Container(
                                                        padding: EdgeInsets.only(
                                                          right: 10,
                                                        ),
                                                        alignment: Alignment.centerRight,
                                                        width: 50,
                                                        height: 20,
                                                        child: SvgPicture.asset(
                                                          'asset/svg/processcomplete_svg.svg',
                                                        ),
                                                        // child: Image.asset(
                                                        //   'asset/images/processcomplete.png',
                                                        // ),
                                                      ),
                                                    ),
                                                  ),
                                                ],
                                              ),
                                            ),
                                            Container(
                                              alignment: Alignment.center,
                                              width: 15,
                                              height: 50,
                                              color: _personalDetailPressed ? Color(0xFF0074C4) : Color(0xFFFAB804),
                                            ),
                                          ],
                                        ),
                                      ),
                                    ),
                                    Container(
                                      width: 250,
                                      height: 1,
                                      color: Colors.grey,
                                    ),
                                    InkWell(
                                      onTap: () {
                                        if (currentStageTracker >= 3 || int.parse(LoginRepository.globalCurrentStageTracker) + 1 >= 3) {
                                          if ((int.parse(LoginRepository.globalCurrentStageTracker) + 1) > currentStageTracker) {
                                            currentStageTracker = (int.parse(LoginRepository.globalCurrentStageTracker) + 1);
                                          }
                                          bankdetailclick();
                                          handleLeftMenuSelection(currentStageTracker, 3);
                                          HomeScreenLarge.screensStreamLarge.sink.add(Ekycscreenamelarge.bankdetailscreen);
                                          LoginRepository.Esignflag = 0;
                                        }
                                      },
                                      child: Container(
                                        alignment: Alignment.centerLeft,
                                        width: 250,
                                        child: Row(
                                          crossAxisAlignment: CrossAxisAlignment.center,
                                          children: [
                                            Container(
                                              padding: EdgeInsets.only(left: 10, top: 10, bottom: 10),
                                              width: 235,
                                              color: _bankDetailsPressed ? Color(0xFF0074C4) : Color(0XFF4d9ed6),
                                              child: Row(
                                                children: [
                                                  Container(
                                                    width: 22,
                                                    height: 22,
                                                    child: SvgPicture.asset(
                                                      'asset/svg/bank_svg.svg',
                                                      color: Colors.white,
                                                      width: 40,
                                                    ),
                                                    // child: Image.asset(
                                                    //   'asset/images/bank.svg',
                                                    // ),
                                                  ),
                                                  Container(
                                                    padding: EdgeInsets.only(left: 15),
                                                    alignment: Alignment.centerLeft,
                                                    child: Center(
                                                      child: Text(
                                                        "BANK DETAILS",
                                                        style: TextStyle(fontFamily: 'century_gothic', color: _bankDetailsPressed ? Color(0xFFFFFFFF) : Color(0xFFFAB804), fontSize: 11, fontWeight: FontWeight.bold),
                                                      ),
                                                    ),
                                                  ),
                                                  Expanded(
                                                    child: Visibility(
                                                      visible: _bankProcess,
                                                      child: Container(
                                                        padding: EdgeInsets.only(
                                                          right: 10,
                                                        ),
                                                        alignment: Alignment.centerRight,
                                                        width: 50,
                                                        height: 20,
                                                        child: SvgPicture.asset(
                                                          'asset/svg/processcomplete_svg.svg',
                                                          width: 20,
                                                        ),
                                                        // child: Image.asset(
                                                        //   'asset/images/processcomplete.png',
                                                        // ),
                                                      ),
                                                    ),
                                                  ),
                                                ],
                                              ),
                                            ),
                                            Container(
                                              alignment: Alignment.center,
                                              width: 15,
                                              height: 50,
                                              color: _bankDetailsPressed ? Color(0xFF0074C4) : Color(0xFFFAB804),
                                            ),
                                          ],
                                        ),
                                      ),
                                    ),
                                    Container(
                                      width: 250,
                                      height: 1,
                                      color: Colors.grey,
                                    ),
                                    InkWell(
                                      onTap: () {
                                        if (currentStageTracker >= 4 || int.parse(LoginRepository.globalCurrentStageTracker) + 1 >= 4) {
                                          if ((int.parse(LoginRepository.globalCurrentStageTracker) + 1) > currentStageTracker) {
                                            currentStageTracker = (int.parse(LoginRepository.globalCurrentStageTracker) + 1);
                                          }
                                          paymentdetailclick();
                                          handleLeftMenuSelection(currentStageTracker, 4);
                                          HomeScreenLarge.screensStreamLarge.sink.add(Ekycscreenamelarge.paymentdetailscreen);
                                          LoginRepository.Esignflag = 0;
                                        }
                                      },
                                      child: Container(
                                        alignment: Alignment.centerLeft,
                                        width: 250,
                                        child: Row(
                                          crossAxisAlignment: CrossAxisAlignment.center,
                                          children: [
                                            Container(
                                              padding: EdgeInsets.only(left: 10, top: 10, bottom: 10),
                                              width: 235,
                                              color: _paymentDetailsPressed ? Color(0xFF0074C4) : Color(0XFF4d9ed6),
                                              child: Row(
                                                children: [
                                                  Container(
                                                    width: 22,
                                                    height: 22,
                                                    // child: Image.asset(
                                                    //   'asset/images/payment.png',
                                                    // ),
                                                    child: SvgPicture.asset(
                                                      'asset/svg/payment_svg.svg',
                                                      color: Colors.white,
                                                    ),
                                                  ),
                                                  Container(
                                                    padding: EdgeInsets.only(left: 15),
                                                    alignment: Alignment.centerLeft,
                                                    child: Center(
                                                      child: Text(
                                                        "PAYMENT DETAILS",
                                                        style: TextStyle(fontFamily: 'century_gothic', color: _paymentDetailsPressed ? Color(0xFFFFFFFF) : Color(0xFFFAB804), fontSize: 11, fontWeight: FontWeight.bold),
                                                      ),
                                                    ),
                                                  ),
                                                  Expanded(
                                                    child: Visibility(
                                                      visible: _paymentProcess,
                                                      child: Container(
                                                        padding: EdgeInsets.only(
                                                          right: 10,
                                                        ),
                                                        alignment: Alignment.centerRight,
                                                        width: 50,
                                                        height: 20,
                                                        child: SvgPicture.asset(
                                                          'asset/svg/processcomplete_svg.svg',
                                                        ),
                                                        // child: Image.asset(
                                                        //   'asset/images/processcomplete.png',
                                                        // ),
                                                      ),
                                                    ),
                                                  ),
                                                ],
                                              ),
                                            ),
                                            Container(
                                              alignment: Alignment.center,
                                              width: 15,
                                              height: 50,
                                              color: _paymentDetailsPressed ? Color(0xFF0074C4) : Color(0xFFFAB804),
                                            ),
                                          ],
                                        ),
                                      ),
                                    ),
                                    Container(
                                      width: 250,
                                      height: 1,
                                      color: Colors.grey,
                                    ),
                                    InkWell(
                                      onTap: () {
                                        if (currentStageTracker >= 5 || int.parse(LoginRepository.globalCurrentStageTracker) + 1 >= 5) {
                                          if ((int.parse(LoginRepository.globalCurrentStageTracker) + 1) > currentStageTracker) {
                                            currentStageTracker = (int.parse(LoginRepository.globalCurrentStageTracker) + 1);
                                          }
                                          documentdetailclick();
                                          handleLeftMenuSelection(currentStageTracker, 5);
                                          HomeScreenLarge.screensStreamLarge.sink.add(Ekycscreenamelarge.documentdetailscreen);
                                          LoginRepository.Esignflag = 0;
                                        }
                                      },
                                      child: Container(
                                        alignment: Alignment.centerLeft,
                                        width: 250,
                                        child: Row(
                                          crossAxisAlignment: CrossAxisAlignment.center,
                                          children: [
                                            Container(
                                              padding: EdgeInsets.only(left: 10, top: 10, bottom: 10),
                                              width: 235,
                                              color: _documentPressed ? Color(0xFF0074C4) : Color(0XFF4d9ed6),
                                              child: Row(
                                                children: [
                                                  Container(
                                                    width: 22,
                                                    height: 22,
                                                    child: SvgPicture.asset(
                                                      'asset/svg/document_svg.svg',
                                                      color: Colors.white,
                                                    ),
                                                    // child: Image.asset(
                                                    //   'asset/images/document.png',
                                                    // ),
                                                  ),
                                                  Container(
                                                    padding: EdgeInsets.only(left: 15),
                                                    alignment: Alignment.centerLeft,
                                                    child: Center(
                                                      child: Text(
                                                        "DOCUMENT",
                                                        style: TextStyle(fontFamily: 'century_gothic', color: _documentPressed ? Color(0xFFFFFFFF) : Color(0xFFFAB804), fontSize: 11, fontWeight: FontWeight.bold),
                                                      ),
                                                    ),
                                                  ),
                                                  Expanded(
                                                    child: Visibility(
                                                      visible: _documentProcess,
                                                      child: Container(
                                                        padding: EdgeInsets.only(
                                                          right: 10,
                                                        ),
                                                        alignment: Alignment.centerRight,
                                                        width: 50,
                                                        height: 20,
                                                        child: SvgPicture.asset(
                                                          'asset/svg/processcomplete_svg.svg',
                                                          width: 20,
                                                        ),
                                                        // child: Image.asset(
                                                        //   'asset/images/processcomplete.png',
                                                        // ),
                                                      ),
                                                    ),
                                                  ),
                                                ],
                                              ),
                                            ),
                                            Container(
                                              alignment: Alignment.center,
                                              width: 15,
                                              height: 50,
                                              color: _documentPressed ? Color(0xFF0074C4) : Color(0xFFFAB804),
                                            ),
                                          ],
                                        ),
                                      ),
                                    ),
                                    Container(
                                      width: 250,
                                      height: 1,
                                      color: Colors.grey,
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
                                            HomeScreenLarge.screensStreamLarge.sink.add(Ekycscreenamelarge.ipvdetailscreen);
                                            LoginRepository.Esignflag = 0;
                                          }
                                        }
                                      },
                                      child: Container(
                                        alignment: Alignment.centerLeft,
                                        width: 250,
                                        child: Row(
                                          crossAxisAlignment: CrossAxisAlignment.center,
                                          children: [
                                            Container(
                                              padding: EdgeInsets.only(left: 10, top: 10, bottom: 10),
                                              width: 235,
                                              color: _iPVPressed ? Color(0xFF0074C4) : Color(0XFF4d9ed6),
                                              child: Row(
                                                children: [
                                                  Container(
                                                    width: 22,
                                                    height: 22,
                                                    child: SvgPicture.asset(
                                                      'asset/svg/ipv_svg.svg',
                                                      color: Colors.white,
                                                    ),
                                                    // child: Image.asset(
                                                    //   'asset/images/ipv.png',
                                                    // ),
                                                  ),
                                                  Container(
                                                    padding: EdgeInsets.only(left: 15),
                                                    alignment: Alignment.centerLeft,
                                                    child: Center(
                                                      child: Text(
                                                        "IPV",
                                                        style: TextStyle(fontFamily: 'century_gothic', color: _iPVPressed ? Color(0xFFFFFFFF) : Color(0xFFFAB804), fontSize: 11, fontWeight: FontWeight.bold),
                                                      ),
                                                    ),
                                                  ),
                                                  Expanded(
                                                    child: Visibility(
                                                      visible: _iPVProcess,
                                                      child: Container(
                                                        padding: EdgeInsets.only(
                                                          right: 10,
                                                        ),
                                                        alignment: Alignment.centerRight,
                                                        width: 50,
                                                        height: 20,
                                                        child: SvgPicture.asset(
                                                          'asset/svg/processcomplete_svg.svg',
                                                          width: 20,
                                                        ),
                                                        // child: Image.asset(
                                                        //   'asset/images/processcomplete.png',
                                                        // ),
                                                      ),
                                                    ),
                                                  ),
                                                ],
                                              ),
                                            ),
                                            Container(
                                              alignment: Alignment.center,
                                              width: 15,
                                              height: 50,
                                              color: _iPVPressed ? Color(0xFF0074C4) : Color(0xFFFAB804),
                                            ),
                                          ],
                                        ),
                                      ),
                                    ),
                                    Container(
                                      width: 250,
                                      height: 1,
                                      color: Colors.grey,
                                    ),
                                    InkWell(
                                      onTap: () {
                                        if (currentStageTracker >= 7 || int.parse(LoginRepository.globalCurrentStageTracker) + 1 >= 7) {
                                          if ((int.parse(LoginRepository.globalCurrentStageTracker) + 1) > currentStageTracker) {
                                            currentStageTracker = (int.parse(LoginRepository.globalCurrentStageTracker) + 1);
                                          }
                                          esigndetailclick();
                                          handleLeftMenuSelection(currentStageTracker, 7);
                                          HomeScreenLarge.screensStreamLarge.sink.add(Ekycscreenamelarge.esigndetailscreen);
                                          LoginRepository.Esignflag = 0;
                                        }
                                      },
                                      child: Container(
                                        alignment: Alignment.centerLeft,
                                        width: 250,
                                        child: Row(
                                          crossAxisAlignment: CrossAxisAlignment.center,
                                          children: [
                                            Container(
                                              padding: EdgeInsets.only(left: 10, top: 10, bottom: 10),
                                              width: 235,
                                              color: _eSignPressed ? Color(0xFF0074C4) : Color(0XFF4d9ed6),
                                              child: Row(
                                                children: [
                                                  Container(
                                                    width: 22,
                                                    height: 22,
                                                    child: SvgPicture.asset(
                                                      'asset/svg/esign_svg.svg',
                                                      color: Colors.white,
                                                    ),
                                                    // child: Image.asset(
                                                    //   'asset/images/esign.png',
                                                    // ),
                                                  ),
                                                  Container(
                                                    padding: EdgeInsets.only(left: 15),
                                                    alignment: Alignment.centerLeft,
                                                    child: Center(
                                                      child: Text(
                                                        "E - SIGN",
                                                        style: TextStyle(fontFamily: 'century_gothic', color: _eSignPressed ? Color(0xFFFFFFFF) : Color(0xFFFAB804), fontSize: 11, fontWeight: FontWeight.bold),
                                                      ),
                                                    ),
                                                  ),
                                                  Expanded(
                                                    child: Visibility(
                                                      visible: _eSignProcess,
                                                      child: Container(
                                                        padding: EdgeInsets.only(
                                                          right: 10,
                                                        ),
                                                        alignment: Alignment.centerRight,
                                                        width: 50,
                                                        height: 20,
                                                        child: SvgPicture.asset(
                                                          'asset/svg/processcomplete_svg.svg',
                                                          width: 20,
                                                        ),
                                                        // child: Image.asset(
                                                        //   'asset/images/processcomplete.png',
                                                        // ),
                                                      ),
                                                    ),
                                                  ),
                                                ],
                                              ),
                                            ),
                                            Container(
                                              alignment: Alignment.center,
                                              width: 15,
                                              height: 50,
                                              color: _eSignPressed ? Color(0xFF0074C4) : Color(0xFFFAB804),
                                            ),
                                          ],
                                        ),
                                      ),
                                    ),
                                    Container(
                                      width: 250,
                                      height: 1,
                                      color: Colors.grey,
                                    ),
                                  ],
                                ),
                              );
                            }),
                      ),
                      Expanded(
                        child: Container(
                          // padding: EdgeInsets.only(top: 50,bottom: 20),
                          width: 170,
                          height: 90,
                          child: Image.asset(
                            'asset/images/vishwaslogo.png',
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
                Container(
                  child: Expanded(
                    child: Container(
                      child: StreamBuilder<Ekycscreenamelarge>(
                        stream: HomeScreenLarge.screensStreamLarge.stream,
                        builder: (context, snapshote) {
                          switch (snapshote.data) {
                            case Ekycscreenamelarge.pandetailscreen:
                              pandetailclick();
                              return PandetailsUI();
                            case Ekycscreenamelarge.personaldetailscreen:
                              persondetailclick();
                              return PersonalDetailsUI();
                            case Ekycscreenamelarge.bankdetailscreen:
                              bankdetailclick();
                              return BankScreenUI();
                            case Ekycscreenamelarge.paymentdetailscreen:
                              paymentdetailclick();
                              return PaymentUI();
                            case Ekycscreenamelarge.documentdetailscreen:
                              documentdetailclick();
                              return DocumentScreenUI();
                            case Ekycscreenamelarge.ipvdetailscreen:
                              ipvdetailclick();
                              return IPVSCreenUI();
                            case Ekycscreenamelarge.esigndetailscreen:
                              esigndetailclick();
                              return EsignScreenUI();
                          }
                          return Container();
                        },
                      ),
                    ),
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }

  void persondetailclick() {
    //setState(() {
    leftMenuSelection = 2;
    _personalDetailPressed = false;
    _panPressed = true;
    _bankDetailsPressed = true;
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
    //  });
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

    _panProcess = true;
    _personalProcess = false;
    _bankProcess = false;
    _paymentProcess = false;
    _documentProcess = false;
    _iPVProcess = false;
    _eSignProcess = false;
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
    _bankProcess = true;
    _paymentProcess = false;
    _documentProcess = false;
    _iPVProcess = false;
    _eSignProcess = false;
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
    _paymentProcess = true;
    _documentProcess = false;
    _iPVProcess = false;
    _eSignProcess = false;
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
    _documentProcess = true;
    _iPVProcess = false;
    _eSignProcess = false;
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
    _iPVProcess = true;
    _eSignProcess = false;
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
    _eSignProcess = true;
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
          if (_documentUpdateFlag) {
            _iPVPressed = true;
          }
          _eSignPressed = true;
          break;
        case 2:
          _panPressed = true;
          _personalDetailPressed = false;
          _bankDetailsPressed = true;
          _paymentDetailsPressed = true;
          _documentPressed = true;
          if (_documentUpdateFlag) {
            _iPVPressed = true;
          }
          _eSignPressed = true;
          break;
        case 3:
          _panPressed = true;
          _personalDetailPressed = true;
          _bankDetailsPressed = false;
          _paymentDetailsPressed = true;
          _documentPressed = true;
          if (_documentUpdateFlag) {
            _iPVPressed = true;
          }
          _eSignPressed = true;
          break;
        case 4:
          _panPressed = true;
          _personalDetailPressed = true;
          _bankDetailsPressed = true;
          _paymentDetailsPressed = false;
          _documentPressed = true;
          if (_documentUpdateFlag) {
            _iPVPressed = true;
          }
          _eSignPressed = true;
          break;
        case 5:
          _panPressed = true;
          _personalDetailPressed = true;
          _bankDetailsPressed = true;
          _paymentDetailsPressed = true;
          _documentPressed = false;
          if (_documentUpdateFlag) {
            _iPVPressed = true;
          }
          _eSignPressed = true;
          break;
        case 6:
          _panPressed = true;
          _personalDetailPressed = true;
          _bankDetailsPressed = true;
          _paymentDetailsPressed = true;
          _documentPressed = true;
          if (_documentUpdateFlag) {
            _iPVPressed = false;
          }
          _eSignPressed = true;
          break;
        case 7:
          _panPressed = true;
          _personalDetailPressed = true;
          _bankDetailsPressed = true;
          _paymentDetailsPressed = true;
          _documentPressed = true;
          if (_documentUpdateFlag) {
            _iPVPressed = true;
          }
          _eSignPressed = false;
          break;
        default:
      }
    });
  }
}
