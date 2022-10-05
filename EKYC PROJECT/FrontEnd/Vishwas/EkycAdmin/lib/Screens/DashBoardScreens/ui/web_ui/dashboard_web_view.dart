import 'dart:async';

import 'package:ekyc_admin/Configuration/app_config.dart';
import 'package:ekyc_admin/Screens/DashBoardScreens/block/dashboard_bloc.dart';
import 'package:ekyc_admin/Screens/DashBoardScreens/model/DashBoardResponseModel.dart';
import 'package:ekyc_admin/Screens/home/screens/home_screen.dart';
import 'package:ekyc_admin/Utilities/greek_textstyle.dart';
import 'package:flutter/material.dart';

class DashBoardWebScreen extends StatefulWidget {
  final DashBoardBloc? dashBoardBloc;
  final String selectedScreen;

  const DashBoardWebScreen({
    Key? key,
    this.dashBoardBloc,
    required this.selectedScreen,
  }) : super(key: key);

  @override
  State<DashBoardWebScreen> createState() => _DashBoardWebScreenState();
}

class _DashBoardWebScreenState extends State<DashBoardWebScreen> {
  List<String> reportLeftValues = ["0"];
  var selectedDropDownItem = "";
  Timer? timer;
  int start = 0;
  double percentage = 0.0;
  String percentagetxt = "0%";

  @override
  void initState() {
    super.initState();
    widget.dashBoardBloc?.getAllStats();
  }

  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      onWillPop: () async => false,
      child: SingleChildScrollView(
        child: SafeArea(
          child: Container(
            color: const Color.fromRGBO(244, 244, 244, 1),
            child: Column(
              children: [
                Padding(
                  padding: const EdgeInsets.all(15.0),
                  child: Column(
                    children: [
                      Container(
                        child: Column(
                          children: [
                            RichText(
                              text: const TextSpan(
                                text: 'Welcome To ',
                                style: TextStyle(fontFamily: 'Roboto', color: Color.fromRGBO(250, 184, 4, 1), fontSize: 35, fontWeight: FontWeight.bold),
                                children: <TextSpan>[
                                  TextSpan(
                                    text: ' Vishwas',
                                    style: TextStyle(fontFamily: 'Roboto', color: Color.fromRGBO(14, 49, 148, 1), fontSize: 35, fontWeight: FontWeight.bold),
                                  ),
                                ],
                              ),
                            ),
                            const Text("E-KYC Platform", style: TextStyle(fontFamily: 'Roboto', color: Color.fromARGB(255, 95, 93, 93), fontSize: 15, fontWeight: FontWeight.normal, letterSpacing: 2)),
                          ],
                        ),
                      ),
                      Container(
                        // width: MediaQuery.of(context).size.width - 80,
                        decoration: BoxDecoration(borderRadius: BorderRadius.circular(26.0), color: Colors.white),
                        margin: const EdgeInsets.symmetric(horizontal: 150, vertical: 50),

                        child: StreamBuilder<List<DashBoardResponseModel>>(
                            stream: widget.dashBoardBloc?.reloadDashboard,
                            builder: (context, snapshot) {
                              if (snapshot.hasData) {
                                String panvarifycount = "0";
                                String panverifystage = "";

                                String firstusercount = "0";
                                String firstuserstage = "";

                                String otpvarifiedcount = "0";
                                String otpvarifiedstage = "";

                                String inproceescount = "0";
                                String inprocessstage = "";

                                String completedusercount = "0";
                                String completeuserStage = "";

                                String finishusercount = "0";
                                String finishuserstage = "";

                                String authorizedusercount = "0";
                                String authoriseduserstage = "";

                                for (final element in (snapshot.data ?? [])) {
                                  if (element.reporttitle == "First Time User") {
                                    firstuserstage = element.stage;
                                  }

                                  if (element.stage == firstuserstage) {
                                    firstusercount = element.count?.toString() ?? "0";
                                  }

                                  if (element.reporttitle == "PAN VERIFIED") {
                                    panverifystage = element.stage;
                                  }
                                  if (element.stage == panverifystage) {
                                    panvarifycount = element.count?.toString() ?? "0";
                                  }

                                  if (element.reporttitle == "OTP VERIFIED") {
                                    otpvarifiedstage = element.stage;
                                  }

                                  if (element.stage == otpvarifiedstage) {
                                    otpvarifiedcount = element.count?.toString() ?? "0";
                                  }

                                  if (element.reporttitle == "IN PROCESS") {
                                    inprocessstage = element.stage;
                                  }

                                  if (element.stage == inprocessstage) {
                                    inproceescount = element.count?.toString() ?? "0";
                                  }

                                  if (element.reporttitle == "COMPLETED USERS") {
                                    completeuserStage = element.stage;
                                  }

                                  if (element.stage == completeuserStage) {
                                    completedusercount = element.count?.toString() ?? "0";
                                  }

                                  if (element.reporttitle == "FINISH USERS") {
                                    finishuserstage = element.stage;
                                  }

                                  if (element.stage == finishuserstage) {
                                    finishusercount = element.count?.toString() ?? "0";
                                  }

                                  if (element.reporttitle == "AUTHORIZED USERS") {
                                    authoriseduserstage = element.stage;
                                  }

                                  if (element.stage == authoriseduserstage) {
                                    authorizedusercount = element.count?.toString() ?? "0";
                                  }
                                }
                                return Column(
                                  children: [
                                    Padding(
                                      padding: const EdgeInsets.all(10),
                                      child: Row(
                                        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                        children: [
                                          Flexible(
                                            fit: FlexFit.loose,
                                            child: InkWell(
                                              onTap: () {
                                                
                                                AppConfig().stageclick = firstuserstage;
                                                AppConfig().sectionTitle = "FIRST USER";
                                                AppConfig().selectedScreen = '';

                                                HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.gridscreen);
                                              },
                                              child: Stack(
                                                alignment: Alignment.topCenter,
                                                children: [
                                                  Container(
                                                    height: 180,
                                                    width: 180,
                                                    decoration: BoxDecoration(
                                                      border: Border.all(color: Colors.transparent),
                                                    ),
                                                    child: Container(
                                                        decoration: BoxDecoration(
                                                          borderRadius: BorderRadius.circular(10.0),
                                                          border: Border.all(color: const Color.fromRGBO(216, 216, 216, 1)),
                                                        ),
                                                        margin: const EdgeInsets.symmetric(horizontal: 20, vertical: 30),
                                                        child: Padding(
                                                          padding: const EdgeInsets.only(top: 50.0),
                                                          child: Column(
                                                            children: [
                                                              Text(
                                                                firstusercount,
                                                                style: const TextStyle(fontSize: 18, color: Color.fromRGBO(0, 37, 142, 1), fontWeight: FontWeight.bold),
                                                              ),
                                                              const Padding(
                                                                padding: EdgeInsets.all(8.0),
                                                                child: Text(
                                                                  "FIRST TIME USER",
                                                                  style: GreekTextStyle.dashboradHeadingtext,
                                                                  textAlign: TextAlign.center,
                                                                ),
                                                              ),
                                                            ],
                                                          ),
                                                        )),
                                                  ),
                                                  Container(
                                                    width: 70,
                                                    height: 70,
                                                    decoration: BoxDecoration(
                                                      color: Colors.white,
                                                      borderRadius: BorderRadius.circular(60),
                                                      border: Border.all(color: const Color.fromRGBO(216, 216, 216, 1)),
                                                    ),
                                                    child: Image.asset(
                                                      "assets/images/firstuser.png",
                                                      height: 150,
                                                      width: 150,
                                                    ),
                                                  ),
                                                ],
                                              ),
                                            ),
                                          ),
                                          Flexible(
                                            fit: FlexFit.loose,
                                            child: InkWell(
                                              onTap: () {
                                                //OTP Verified
                                                AppConfig().stageclick = otpvarifiedstage;
                                                AppConfig().selectedScreen = 'otpverified';
                                                AppConfig().sectionTitle = 'OTP VERIFIED REPORT';
                                                HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.gridscreen);
                                              },
                                              child: Stack(
                                                alignment: Alignment.topCenter,
                                                children: [
                                                  Container(
                                                    height: 180,
                                                    width: 180,
                                                    decoration: BoxDecoration(
                                                      border: Border.all(color: Colors.transparent),
                                                    ),
                                                    child: Container(
                                                        decoration: BoxDecoration(
                                                          borderRadius: BorderRadius.circular(10.0),
                                                          border: Border.all(color: const Color.fromRGBO(216, 216, 216, 1)),
                                                        ),
                                                        margin: const EdgeInsets.symmetric(horizontal: 20, vertical: 30),
                                                        child: Padding(
                                                          padding: const EdgeInsets.only(top: 50.0),
                                                          child: Column(
                                                            children: [
                                                              Text(
                                                                otpvarifiedcount,
                                                                style: const TextStyle(fontSize: 18, color: Color.fromRGBO(0, 37, 142, 1), fontWeight: FontWeight.bold),
                                                              ),
                                                              const Padding(
                                                                padding: EdgeInsets.all(8.0),
                                                                child: Text(
                                                                  "OTP VERIFIED",
                                                                  style: GreekTextStyle.dashboradHeadingtext,
                                                                  textAlign: TextAlign.center,
                                                                ),
                                                              ),
                                                            ],
                                                          ),
                                                        )),
                                                  ),
                                                  Container(
                                                    width: 70,
                                                    height: 70,
                                                    decoration: BoxDecoration(
                                                      color: Colors.white,
                                                      borderRadius: BorderRadius.circular(60),
                                                      border: Border.all(color: const Color.fromRGBO(216, 216, 216, 1)),
                                                    ),
                                                    child: Image.asset(
                                                      "assets/images/otpverified.png",
                                                      height: 150,
                                                      width: 150,
                                                    ),
                                                  ),
                                                ],
                                              ),
                                            ),
                                          ),
                                          Flexible(
                                            fit: FlexFit.loose,
                                            child: InkWell(
                                              onTap: () {
                                                AppConfig().stageclick = panverifystage;
                                                //PAN Verified
                                                AppConfig().selectedScreen = 'panverified';
                                                AppConfig().sectionTitle = 'PAN VERIFIED REPORT';
                                                HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.gridscreen);
                                              },
                                              child: Stack(
                                                alignment: Alignment.topCenter,
                                                children: [
                                                  Container(
                                                    height: 180,
                                                    width: 180,
                                                    decoration: BoxDecoration(
                                                      border: Border.all(color: Colors.transparent),
                                                    ),
                                                    child: Container(
                                                        decoration: BoxDecoration(
                                                          borderRadius: BorderRadius.circular(10.0),
                                                          border: Border.all(color: const Color.fromRGBO(216, 216, 216, 1)),
                                                        ),
                                                        margin: const EdgeInsets.symmetric(horizontal: 20, vertical: 30),
                                                        child: Padding(
                                                          padding: const EdgeInsets.only(top: 50.0),
                                                          child: Column(
                                                            children: [
                                                              Text(
                                                                panvarifycount,
                                                                style: const TextStyle(fontSize: 18, color: Color.fromRGBO(0, 37, 142, 1), fontWeight: FontWeight.bold),
                                                              ),
                                                              const Padding(
                                                                padding: EdgeInsets.all(8.0),
                                                                child: Text(
                                                                  "PAN VERIFIED",
                                                                  style: GreekTextStyle.dashboradHeadingtext,
                                                                  textAlign: TextAlign.center,
                                                                ),
                                                              ),
                                                            ],
                                                          ),
                                                        )),
                                                  ),
                                                  Container(
                                                    width: 70,
                                                    height: 70,
                                                    decoration: BoxDecoration(
                                                      color: Colors.white,
                                                      borderRadius: BorderRadius.circular(60),
                                                      border: Border.all(color: const Color.fromRGBO(216, 216, 216, 1)),
                                                    ),
                                                    child: Image.asset(
                                                      "assets/images/panverified.png",
                                                      height: 150,
                                                      width: 150,
                                                    ),
                                                  ),
                                                ],
                                              ),
                                            ),
                                          ),
                                          Flexible(
                                            fit: FlexFit.loose,
                                            child: InkWell(
                                              onTap: () {
                                                AppConfig().stageclick = inprocessstage;
                                                //In Process
                                                AppConfig().selectedScreen = 'inprocess';
                                                AppConfig().sectionTitle = 'IN PROCESS';
                                                HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.gridscreen);
                                              },
                                              child: Stack(
                                                alignment: Alignment.topCenter,
                                                children: [
                                                  Container(
                                                    height: 180,
                                                    width: 180,
                                                    decoration: BoxDecoration(
                                                      border: Border.all(color: Colors.transparent),
                                                    ),
                                                    child: Container(
                                                        decoration: BoxDecoration(
                                                          borderRadius: BorderRadius.circular(10.0),
                                                          border: Border.all(color: const Color.fromRGBO(216, 216, 216, 1)),
                                                        ),
                                                        margin: const EdgeInsets.symmetric(horizontal: 20, vertical: 30),
                                                        child: Padding(
                                                          padding: const EdgeInsets.only(top: 50.0),
                                                          child: Column(
                                                            children: [
                                                              Text(
                                                                inproceescount,
                                                                style: const TextStyle(fontSize: 18, color: Color.fromRGBO(0, 37, 142, 1), fontWeight: FontWeight.bold),
                                                              ),
                                                              const Padding(
                                                                padding: EdgeInsets.all(8.0),
                                                                child: Text(
                                                                  "IN PROCESS",
                                                                  style: GreekTextStyle.dashboradHeadingtext,
                                                                  textAlign: TextAlign.center,
                                                                ),
                                                              ),
                                                            ],
                                                          ),
                                                        )),
                                                  ),
                                                  Container(
                                                    width: 70,
                                                    height: 70,
                                                    decoration: BoxDecoration(
                                                      color: Colors.white,
                                                      borderRadius: BorderRadius.circular(60),
                                                      border: Border.all(color: const Color.fromRGBO(216, 216, 216, 1)),
                                                      /*   boxShadow: [
                                                        BoxShadow(
                                                          color: Colors.black26,
                                                          offset: Offset(0.0, 1),
                                                          blurRadius: 2.0,
                                                        )
                                                      ] */
                                                    ),
                                                    child: /* Image.asset(
                                                  DashboardImage
                                                      .home_first_time_user_icon
                                                      .name,
                                                  fit: BoxFit.contain,
                                                    ), */
                                                        Image.asset(
                                                      "assets/images/inprogress.png",
                                                      height: 150,
                                                      width: 150,
                                                    ),
                                                  ),
                                                ],
                                              ),
                                            ),
                                          ),
                                        ],
                                      ),
                                    ),
                                    Padding(
                                      padding: const EdgeInsets.all(30.0),
                                      child: Row(
                                        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                        children: [
                                          Flexible(
                                            fit: FlexFit.loose,
                                            child: InkWell(
                                              onTap: () {
                                                AppConfig().stageclick = completeuserStage;
                                                //OTP Verified
                                                AppConfig().selectedScreen = 'completeduser';
                                                AppConfig().sectionTitle = 'COMPLETED USER';
                                                HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.gridscreen);
                                              },
                                              child: Stack(
                                                alignment: Alignment.topCenter,
                                                children: [
                                                  Container(
                                                    height: 180,
                                                    width: 180,
                                                    decoration: BoxDecoration(
                                                      border: Border.all(color: Colors.transparent),
                                                    ),
                                                    child: Container(
                                                        decoration: BoxDecoration(
                                                          borderRadius: BorderRadius.circular(10.0),
                                                          border: Border.all(color: const Color.fromRGBO(216, 216, 216, 1)),
                                                        ),
                                                        margin: const EdgeInsets.symmetric(horizontal: 20, vertical: 30),
                                                        child: Padding(
                                                          padding: const EdgeInsets.only(top: 50.0),
                                                          child: Column(
                                                            children: [
                                                              Text(
                                                                completedusercount,
                                                                style: const TextStyle(fontSize: 18, color: Color.fromRGBO(0, 37, 142, 1), fontWeight: FontWeight.bold),
                                                              ),
                                                              const Padding(
                                                                padding: EdgeInsets.all(8.0),
                                                                child: Text(
                                                                  "COMPLETED USER",
                                                                  style: GreekTextStyle.dashboradHeadingtext,
                                                                  textAlign: TextAlign.center,
                                                                ),
                                                              ),
                                                            ],
                                                          ),
                                                        )),
                                                  ),
                                                  Container(
                                                    width: 70,
                                                    height: 70,
                                                    decoration: BoxDecoration(
                                                      color: Colors.white,
                                                      borderRadius: BorderRadius.circular(60),
                                                      border: Border.all(color: const Color.fromRGBO(216, 216, 216, 1)),
                                                    ),
                                                    child: Image.asset(
                                                      "assets/images/completedusers.png",
                                                      height: 150,
                                                      width: 150,
                                                    ),
                                                  ),
                                                ],
                                              ),
                                            ),
                                          ),
                                          Flexible(
                                            fit: FlexFit.loose,
                                            child: InkWell(
                                              onTap: () {
                                                AppConfig().stageclick = finishuserstage;
                                                //OTP Verified
                                                AppConfig().selectedScreen = 'finishuser';
                                                AppConfig().sectionTitle = 'FINISHED USER';
                                                HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.gridscreen);
                                              },
                                              child: Stack(
                                                alignment: Alignment.topCenter,
                                                children: [
                                                  Container(
                                                    height: 180,
                                                    width: 180,
                                                    decoration: BoxDecoration(
                                                      borderRadius: BorderRadius.circular(10.0),
                                                      border: Border.all(color: Colors.transparent),
                                                    ),
                                                    child: Container(
                                                        decoration: BoxDecoration(
                                                          borderRadius: BorderRadius.circular(10.0),
                                                          border: Border.all(color: const Color.fromRGBO(216, 216, 216, 1)),
                                                        ),
                                                        margin: const EdgeInsets.symmetric(horizontal: 20, vertical: 30),
                                                        child: Padding(
                                                          padding: const EdgeInsets.only(top: 50.0),
                                                          child: Column(
                                                            children: [
                                                              Text(
                                                                finishusercount,
                                                                style: const TextStyle(fontSize: 18, color: Color.fromRGBO(0, 37, 142, 1), fontWeight: FontWeight.bold),
                                                              ),
                                                              const Padding(
                                                                padding: EdgeInsets.all(8.0),
                                                                child: Text(
                                                                  "FINISH USER",
                                                                  style: GreekTextStyle.dashboradHeadingtext,
                                                                  textAlign: TextAlign.center,
                                                                ),
                                                              ),
                                                            ],
                                                          ),
                                                        )),
                                                  ),
                                                  Container(
                                                    width: 70,
                                                    height: 70,
                                                    decoration: BoxDecoration(
                                                      color: Colors.white,
                                                      borderRadius: BorderRadius.circular(60),
                                                      border: Border.all(color: const Color.fromRGBO(216, 216, 216, 1)),
                                                    ),
                                                    child: Image.asset(
                                                      "assets/images/finishusers.png",
                                                      height: 150,
                                                      width: 150,
                                                    ),
                                                  ),
                                                ],
                                              ),
                                            ),
                                          ),
                                          Flexible(
                                            fit: FlexFit.loose,
                                            child: InkWell(
                                              onTap: () {
                                                AppConfig().stageclick = authoriseduserstage;
                                                //OTP Verified
                                                AppConfig().selectedScreen = 'authorizeduser';
                                                AppConfig().sectionTitle = 'AUTHORIZED USER';
                                                HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.gridscreen);
                                              },
                                              child: Stack(
                                                alignment: Alignment.topCenter,
                                                children: [
                                                  Container(
                                                    height: 180,
                                                    width: 180,
                                                    decoration: BoxDecoration(
                                                      border: Border.all(color: Colors.transparent),
                                                    ),
                                                    child: Container(
                                                        decoration: BoxDecoration(
                                                          borderRadius: BorderRadius.circular(10.0),
                                                          border: Border.all(color: const Color.fromRGBO(216, 216, 216, 1)),
                                                        ),
                                                        margin: const EdgeInsets.symmetric(horizontal: 20, vertical: 30),
                                                        child: Padding(
                                                          padding: const EdgeInsets.only(top: 50.0),
                                                          child: Column(
                                                            children: [
                                                              Text(
                                                                authorizedusercount,
                                                                style: const TextStyle(fontSize: 18, color: Color.fromRGBO(0, 37, 142, 1), fontWeight: FontWeight.bold),
                                                              ),
                                                              const Padding(
                                                                padding: EdgeInsets.all(8.0),
                                                                child: Text(
                                                                  "AUTHORISED USER",
                                                                  style: GreekTextStyle.dashboradHeadingtext,
                                                                  textAlign: TextAlign.center,
                                                                ),
                                                              ),
                                                            ],
                                                          ),
                                                        )),
                                                  ),
                                                  Container(
                                                    width: 70,
                                                    height: 70,
                                                    decoration: BoxDecoration(
                                                      color: Colors.white,
                                                      borderRadius: BorderRadius.circular(60),
                                                      border: Border.all(color: const Color.fromRGBO(216, 216, 216, 1)),
                                                      /*   boxShadow: [
                                                        BoxShadow(
                                                          color: Colors.black26,
                                                          offset: Offset(0.0, 1),
                                                          blurRadius: 2.0,
                                                        )
                                                      ] */
                                                    ),
                                                    child: Image.asset(
                                                      "assets/images/authoriesusers.png",
                                                      height: 150,
                                                      width: 150,
                                                    ),
                                                  ),
                                                ],
                                              ),
                                            ),
                                          ),
                                        ],
                                      ),
                                    ),
                                  ],
                                );
                              } else {
                                return Container();
                              }
                            }),
                      )
                    ],
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }

  void startTimer() {
    const oneSec = Duration(seconds: 1);
    timer = Timer.periodic(
      oneSec,
      (Timer timer) {
        if (start == 10) {
          setState(() {
            timer.cancel();
            start = 0;
            percentage = 0.00;
            percentagetxt = "0%";
          });
        } else {
          setState(() {
            start++;
            if (start == 1) {
              percentagetxt = "10%";
            } else if (start == 2) {
              percentagetxt = "20%";
            } else if (start == 3) {
              percentagetxt = "30%";
            } else if (start == 4) {
              percentagetxt = "40%";
            } else if (start == 5) {
              percentagetxt = "50%";
            } else if (start == 6) {
              percentagetxt = "60%";
            } else if (start == 7) {
              percentagetxt = "70%";
            } else if (start == 8) {
              percentagetxt = "80%";
            } else if (start == 9) {
              percentagetxt = "90%";
            } else if (start == 10) {
              percentagetxt = "100%";
            }
            var total = "0." + start.toString();
            if (total == "0.10") {
              percentage = 1;
            } else {
              percentage = double.parse(total);
            }
          });
        }
      },
    );
  }

  @override
  void dispose() {
    timer?.cancel();
    super.dispose();
  }
}
