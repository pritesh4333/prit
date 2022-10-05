import 'dart:async';
import 'dart:html';
import 'dart:io';
// import 'dart:html' as html;
//  import 'dart:js' as js;

// import "package:http/http.dart" as http;

import 'package:ekyc_admin/Configuration/app_config.dart';
import 'package:ekyc_admin/Configuration/greek_navigation.dart';
import 'package:ekyc_admin/Screens/AdminData/ui/admin_data_grid.dart';
import 'package:ekyc_admin/Screens/AdminData/ui/all_report_web_screen.dart';
import 'package:ekyc_admin/Screens/AdminData/ui/master_report_web_screen.dart';
import 'package:ekyc_admin/Screens/AdminData/ui/reports/authorize_user_report_web_screen.dart';
import 'package:ekyc_admin/Screens/AdminData/ui/reports/data_analysis_report_web_screen.dart';
import 'package:ekyc_admin/Screens/AdminData/ui/reports/global_search_web_screen.dart';
import 'package:ekyc_admin/Screens/AdminData/ui/reports/payment_report_web_screen.dart';
import 'package:ekyc_admin/Screens/AdminData/ui/reports/penny_drop_report_web_screen.dart';
import 'package:ekyc_admin/Screens/AdminData/ui/reports/re_ipv_report_web_screen.dart';
import 'package:ekyc_admin/Screens/AdminData/ui/reports/rejection_report_web_screen.dart';
import 'package:ekyc_admin/Screens/AdminData/ui/reports/status_report_web_screen.dart';
import 'package:ekyc_admin/Screens/DashBoardScreens/ui/dashboard_view_screen.dart';
import 'package:ekyc_admin/Screens/Login%20Screen/ui/login_view_screen.dart';
import 'package:ekyc_admin/Screens/home/screen.dart';
import 'package:ekyc_admin/Screens/home/screens/OnHover.dart';
import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:rxdart/rxdart.dart';

import '../../../Extension_Enum/greek_enum.dart';
import '../../../Network Manager/AppURLs/app_url_main.dart';
import '../widgets/constants.dart';

enum AdminScreenLarge {
  dashboardscreen,
  gridscreen,
  backupscreen,
  userdetailscreen,
  bocodemasterscreen,
  // allreport,
  rekycreport,
  referalmaster,
  userrights,
  statusreport,
  globalsearch,
  paymentreport,
  dataanalysisreport,
  reipvreport,
  authorizeuserreport,
  rejectionreport,
  pennydropreport,
}

class HomeScreen extends StatefulWidget {
  const HomeScreen({Key? key}) : super(key: key);

  @override
  State<HomeScreen> createState() => HomeScreenState();
}

class HomeScreenState extends State<HomeScreen> {
  String selectedScreen = '';
  static final screensStreamLarge = PublishSubject<AdminScreenLarge>();
  Timer? timer;
  int start = 0;
  double percentage = 0.0;
  String percentagetxt = "0%";
  static String screenfrom = "";
  static BehaviorSubject<bool> isSelectAll = BehaviorSubject.seeded(false);

  // int _total = 0, _received = 0;
  // late http.StreamedResponse _response;
  // File? _image;
  // final List<int> _bytes = [];

  @override
  void dispose() {
    timer?.cancel();
    super.dispose();
  }

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      onWillPop: () async => false,
      child: Scaffold(
        appBar: AppBar(
          automaticallyImplyLeading: false,
          toolbarHeight: 70.0,
          flexibleSpace: Container(
            color: Colors.white,
            child: Row(
              children: [
                Flexible(
                  flex: 1,
                  child: GestureDetector(
                    onTap: () => HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.dashboardscreen),
                    child: Container(
                      alignment: Alignment.centerRight,
                      padding: const EdgeInsets.all(5),
                      child: Image.asset(
                        CompanyImage.company_logo.name,
                        height: 40,
                      ),
                    ),
                  ),
                ),
                Flexible(
                  flex: 6,
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceAround,
                    children: [
                      ShowPopOverDialog(
                        height: 240,
                        width: 280,
                        widgetList: reportPopupMenu(),
                        view: const SizedBox(
                          child: Text(
                            "Report",
                            style: GreekTextStyle.dashboradHeadingtext,
                          ),
                        ),
                      ),
                      // ShowPopOverDialog(
                      //   height: 200,
                      //   width: 125,
                      //   widgetList: masterPopupMenu(context),
                      //   view: const SizedBox(
                      //     child: Text(
                      //       "Master",
                      //       style: GreekTextStyle.dashboradHeadingtext,
                      //     ),
                      //   ),
                      // ),
                      // ShowPopOverDialog(
                      //   height: 200,
                      //   width: 125,
                      //   widgetList: rekycPopupMenu(),
                      //   view: const SizedBox(
                      //     child: Text(
                      //       "REKYC Report",
                      //       style: GreekTextStyle.dashboradHeadingtext,
                      //     ),
                      //   ),
                      // ),
                      Text(
                        "Master",
                        style: GreekTextStyle.dashboradHeadingtext,
                      ),

                      Text(
                        "REKYC Report",
                        style: GreekTextStyle.dashboradHeadingtext,
                      ),

                      InkWell(
                        onTap: () {
                          showDialog(
                              context: context,
                              builder: (BuildContext context) {
                                return StatefulBuilder(
                                  builder: (BuildContext context, StateSetter setState) {
                                    return customDialogmethod(context, setState);
                                  },
                                );
                              });
                        },
                        child: Container(
                          padding: const EdgeInsets.all(5),
                          child: const Text(
                            "Backup",
                            style: GreekTextStyle.dashboradHeadingtext,
                          ),
                        ),
                      ),
                      Container(
                        padding: const EdgeInsets.all(5),
                        child: const Text(
                          "  ",
                          style: GreekTextStyle.dashboradHeadingtext,
                        ),
                      ),
                      Container(
                        padding: const EdgeInsets.all(5),
                        child: const Text(
                          "  ",
                          style: GreekTextStyle.dashboradHeadingtext,
                        ),
                      ),
                      Container(
                        padding: const EdgeInsets.all(5),
                        child: const Text(
                          "  ",
                          style: GreekTextStyle.dashboradHeadingtext,
                        ),
                      ),
                    ],
                  ),
                ),
                Flexible(
                  flex: 1,
                  child: Container(
                    alignment: Alignment.center,
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceAround,
                      children: [
                        Container(
                          padding: const EdgeInsets.all(5),
                          child: Image.asset(
                            DashboardImage.phonecall.name,
                            height: 24,
                          ),
                        ),
                        Container(
                          padding: const EdgeInsets.all(5),
                          child: Image.asset(
                            DashboardImage.email.name,
                            height: 24,
                          ),
                        ),
                        InkWell(
                          onTap: () {
                            GreekNavigator.pushNamedAndRemoveUntil(
                              context: context,
                              newRouteName: GreekScreenNames.login_page,
                            );
                          },
                          child: Container(
                            padding: const EdgeInsets.all(5),
                            child: Image.asset(
                              DashboardImage.log_out.name,
                              height: 24,
                            ),
                          ),
                        ),
                      ],
                    ),
                  ),
                ),
              ],
            ),
          ),
        ),
        body: SafeArea(
            child: StreamBuilder<AdminScreenLarge>(
          stream: screensStreamLarge.stream,
          builder: (context, snapshot) {
            switch (snapshot.data) {
              case AdminScreenLarge.gridscreen:
                return AdminDataGridScreen(selectedScreen: AppConfig().selectedScreen, title: AppConfig().sectionTitle);
              case AdminScreenLarge.backupscreen:
                return Container();
              case AdminScreenLarge.userdetailscreen:
                return const AdminDetailsScreen();
              case AdminScreenLarge.bocodemasterscreen:
                return const BoCodeMasterScreen();
              // case AdminScreenLarge.allreport:
              //   return AllReportsWebScreen(title: AppConfig().sectionTitle);
              case AdminScreenLarge.rekycreport:
                AppConfig().selectedScreen == "rekycreport";
                return ReKycReportsWebScreen(title: AppConfig().sectionTitle);
              case AdminScreenLarge.referalmaster:
                AppConfig().selectedScreen == "referalmaster";
                AppConfig().sectionTitle = "Referal Master";
                return MasterReportWebScreen(title: AppConfig().sectionTitle);
              case AdminScreenLarge.userrights:
                AppConfig().sectionTitle = "User Rights";
                return MasterReportWebScreen(title: AppConfig().sectionTitle);
              case AdminScreenLarge.statusreport:
                AppConfig().selectedScreen == "statusreport";
                return StatusReportWebScreen(title: AppConfig().sectionTitle);
              case AdminScreenLarge.globalsearch:
                AppConfig().selectedScreen == "globalsearch";
                return GlobalSearchReportWebScreen(title: AppConfig().sectionTitle);
              case AdminScreenLarge.paymentreport:
                AppConfig().selectedScreen == "paymentreport";
                return PaymentReportWebScreen(title: AppConfig().sectionTitle);
              case AdminScreenLarge.dataanalysisreport:
                AppConfig().selectedScreen == "dataanalysisreport";
                return DataAnalysisReportWebScreen(title: AppConfig().sectionTitle);
              case AdminScreenLarge.reipvreport:
                AppConfig().selectedScreen == "reipvreport";
                return ReIPVReportWebScreen(title: AppConfig().sectionTitle);
              case AdminScreenLarge.authorizeuserreport:
                AppConfig().selectedScreen == "authorizeuserreport";
                return AuthorizeUserReportWebScreen(title: AppConfig().sectionTitle);
              case AdminScreenLarge.rejectionreport:
                AppConfig().selectedScreen == "rejectionreport";
                return RejectionReportWebScreen(title: AppConfig().sectionTitle);
              case AdminScreenLarge.pennydropreport:
                AppConfig().selectedScreen == "pennydropreport";
                return PennyDropReportWebScreen(title: AppConfig().sectionTitle);
              default:
                return DashBoardScreen(selectedScreen: selectedScreen);
            }
          },
        )),
      ),
    );
  }

  reportPopupMenu() {
    return SizedBox(
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        children: [
          Column(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: [
              OnHover(builder: (isHovered) {
                return InkWell(
                  onTap: () {
                    AppConfig().selectedReportType = 'statusreport';
                    AppConfig().sectionTitle = "STATUS REPORT";
                    GreekNavigator.pop(context: context);
                    HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.statusreport);
                  },
                  child: Container(
                    width: 110,
                    margin: const EdgeInsets.all(5),
                    decoration: BoxDecoration(
                      shape: BoxShape.rectangle,
                      borderRadius: BorderRadius.circular(5),
                      color: isHovered ? Color(0xFF003399) : const Color(0xFFFFFFFF),
                      border: Border.all(
                        color: isHovered ? Color(0xFF003399) : const Color(0x7CAFAFAF),
                        width: 1,
                      ),
                    ),
                    alignment: Alignment.center,
                    padding: const EdgeInsets.only(left: 0, top: 10, right: 0, bottom: 10),
                    child: Text(
                      "Status Report",
                      style: TextStyle(
                        fontFamily: "RobotoRegular",
                        fontWeight: FontWeight.normal,
                        fontSize: 10.0,
                        color: isHovered ? Colors.white : Colors.black,
                      ),
                    ),
                  ),
                );
              }),
              OnHover(builder: (isHovered) {
                return InkWell(
                  onTap: () {
                    AppConfig().selectedReportType = 'globalsearch';
                    AppConfig().sectionTitle = "GLOBAL SEARCH";
                    GreekNavigator.pop(context: context);
                    HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.globalsearch);
                  },
                  child: Container(
                    width: 110,
                    margin: const EdgeInsets.all(5),
                    decoration: BoxDecoration(
                      shape: BoxShape.rectangle,
                      borderRadius: BorderRadius.circular(5),
                      color: isHovered ? Color(0xFF003399) : const Color(0xFFFFFFFF),
                      border: Border.all(
                        color: isHovered ? Color(0xFF003399) : const Color(0x7CAFAFAF),
                        width: 1,
                      ),
                    ),
                    alignment: Alignment.center,
                    padding: const EdgeInsets.only(left: 0, top: 10, right: 0, bottom: 10),
                    child: Text(
                      'Global Search',
                      style: TextStyle(
                        fontFamily: "RobotoRegular",
                        fontWeight: FontWeight.normal,
                        fontSize: 10.0,
                        color: isHovered ? Colors.white : Colors.black,
                      ),
                    ),
                  ),
                );
              }),
              OnHover(builder: (isHovered) {
                return InkWell(
                  onTap: () {
                    AppConfig().selectedReportType = 'paymentreport';
                    AppConfig().sectionTitle = "PAYMENT REPORT";
                    GreekNavigator.pop(context: context);
                    HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.paymentreport);
                  },
                  child: Container(
                    width: 110,
                    margin: const EdgeInsets.all(5),
                    decoration: BoxDecoration(
                      shape: BoxShape.rectangle,
                      borderRadius: BorderRadius.circular(5),
                      color: isHovered ? Color(0xFF003399) : const Color(0xFFFFFFFF),
                      border: Border.all(
                        color: isHovered ? Color(0xFF003399) : const Color(0x7CAFAFAF),
                        width: 1,
                      ),
                    ),
                    alignment: Alignment.center,
                    padding: const EdgeInsets.only(left: 0, top: 10, right: 0, bottom: 10),
                    child: Text(
                      'Payment Report',
                      style: TextStyle(
                        fontFamily: "RobotoRegular",
                        fontWeight: FontWeight.normal,
                        fontSize: 10.0,
                        color: isHovered ? Colors.white : Colors.black,
                      ),
                    ),
                  ),
                );
              }),
              OnHover(builder: (isHovered) {
                return InkWell(
                  onTap: () {
                    AppConfig().selectedReportType = 'dataanalysisreport';
                    AppConfig().sectionTitle = "DATA ANALYSIS REPORT";
                    GreekNavigator.pop(context: context);
                    HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.dataanalysisreport);
                  },
                  child: Container(
                    width: 110,
                    margin: const EdgeInsets.all(5),
                    decoration: BoxDecoration(
                      shape: BoxShape.rectangle,
                      borderRadius: BorderRadius.circular(5),
                      color: isHovered ? Color(0xFF003399) : const Color(0xFFFFFFFF),
                      border: Border.all(
                        color: isHovered ? Color(0xFF003399) : const Color(0x7CAFAFAF),
                        width: 1,
                      ),
                    ),
                    alignment: Alignment.center,
                    padding: const EdgeInsets.only(left: 0, top: 10, right: 0, bottom: 10),
                    child: Text(
                      'Data Analysis Report',
                      style: TextStyle(
                        fontFamily: "RobotoRegular",
                        fontWeight: FontWeight.normal,
                        fontSize: 10.0,
                        color: isHovered ? Colors.white : Colors.black,
                      ),
                    ),
                  ),
                );
              }),
            ],
          ),
          Column(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: [
              OnHover(builder: (isHovered) {
                return InkWell(
                  onTap: () {
                    AppConfig().selectedReportType = 'reipvreport';
                    AppConfig().sectionTitle = "RE IPV REPORT";
                    GreekNavigator.pop(context: context);
                    HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.reipvreport);
                  },
                  child: Container(
                    width: 110,
                    margin: const EdgeInsets.all(5),
                    decoration: BoxDecoration(
                      shape: BoxShape.rectangle,
                      borderRadius: BorderRadius.circular(5),
                      color: isHovered ? Color(0xFF003399) : const Color(0xFFFFFFFF),
                      border: Border.all(
                        color: isHovered ? Color(0xFF003399) : const Color(0x7CAFAFAF),
                        width: 1,
                      ),
                    ),
                    alignment: Alignment.center,
                    padding: const EdgeInsets.only(left: 0, top: 10, right: 0, bottom: 10),
                    child: Text(
                      "Re IPV Report",
                      style: TextStyle(
                        fontFamily: "RobotoRegular",
                        fontWeight: FontWeight.normal,
                        fontSize: 10.0,
                        color: isHovered ? Colors.white : Colors.black,
                      ),
                    ),
                  ),
                );
              }),
              OnHover(builder: (isHovered) {
                return InkWell(
                  onTap: () {
                    AppConfig().selectedReportType = 'authorizeuserreport';
                    AppConfig().sectionTitle = "AUTHORIZE USER REPORT";
                    GreekNavigator.pop(context: context);
                    HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.authorizeuserreport);
                  },
                  child: Container(
                    width: 110,
                    margin: const EdgeInsets.all(5),
                    decoration: BoxDecoration(
                      shape: BoxShape.rectangle,
                      borderRadius: BorderRadius.circular(5),
                      color: isHovered ? Color(0xFF003399) : const Color(0xFFFFFFFF),
                      border: Border.all(
                        color: isHovered ? Color(0xFF003399) : const Color(0x7CAFAFAF),
                        width: 1,
                      ),
                    ),
                    alignment: Alignment.center,
                    padding: const EdgeInsets.only(left: 0, top: 10, right: 0, bottom: 10),
                    child: Text(
                      'Authorize User Report',
                      style: TextStyle(
                        fontFamily: "RobotoRegular",
                        fontWeight: FontWeight.normal,
                        fontSize: 10.0,
                        color: isHovered ? Colors.white : Colors.black,
                      ),
                    ),
                  ),
                );
              }),
              OnHover(builder: (isHovered) {
                return InkWell(
                  onTap: () {
                    AppConfig().selectedReportType = 'rejectionreport';
                    AppConfig().sectionTitle = "REJECTION REPORT";
                    GreekNavigator.pop(context: context);
                    HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.rejectionreport);
                  },
                  child: Container(
                    width: 110,
                    margin: const EdgeInsets.all(5),
                    decoration: BoxDecoration(
                      shape: BoxShape.rectangle,
                      borderRadius: BorderRadius.circular(5),
                      color: isHovered ? Color(0xFF003399) : const Color(0xFFFFFFFF),
                      border: Border.all(
                        color: isHovered ? Color(0xFF003399) : const Color(0x7CAFAFAF),
                        width: 1,
                      ),
                    ),
                    alignment: Alignment.center,
                    padding: const EdgeInsets.only(left: 0, top: 10, right: 0, bottom: 10),
                    child: Text(
                      'Rejection Report',
                      style: TextStyle(
                        fontFamily: "RobotoRegular",
                        fontWeight: FontWeight.normal,
                        fontSize: 10.0,
                        color: isHovered ? Colors.white : Colors.black,
                      ),
                    ),
                  ),
                );
              }),
              OnHover(builder: (isHovered) {
                return InkWell(
                  onTap: () {
                    AppConfig().selectedReportType = 'pennydropreport';
                    AppConfig().sectionTitle = "PENNY DROP REPORT";
                    GreekNavigator.pop(context: context);

                    HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.pennydropreport);
                  },
                  child: Container(
                    width: 110,
                    margin: const EdgeInsets.all(5),
                    decoration: BoxDecoration(
                      shape: BoxShape.rectangle,
                      borderRadius: BorderRadius.circular(5),
                      color: isHovered ? Color(0xFF003399) : const Color(0xFFFFFFFF),
                      border: Border.all(
                        color: isHovered ? Color(0xFF003399) : const Color(0x7CAFAFAF),
                        width: 1,
                      ),
                    ),
                    alignment: Alignment.center,
                    padding: const EdgeInsets.only(left: 0, top: 10, right: 0, bottom: 10),
                    child: Text(
                      'Penny Drop Report',
                      style: TextStyle(
                        fontFamily: "RobotoRegular",
                        fontWeight: FontWeight.normal,
                        fontSize: 10.0,
                        color: isHovered ? Colors.white : Colors.black,
                      ),
                    ),
                  ),
                );
              }),
            ],
          ),
        ],
      ),
    );
  }

  masterPopupMenu(BuildContext context) {
    return SizedBox(
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        children: [
          Column(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: [
              Container(
                width: 110,
                margin: const EdgeInsets.all(5),
                decoration: BoxDecoration(
                  shape: BoxShape.rectangle,
                  borderRadius: BorderRadius.circular(5),
                  border: Border.all(
                    // color: const Color(0xFFE3E3E3),
                    color: const Color(0x7CAFAFAF),
                    width: 1,
                  ),
                ),
                alignment: Alignment.center,
                padding: const EdgeInsets.only(left: 0, top: 10, right: 0, bottom: 10),
                child: GestureDetector(
                  onTap: () {
                    print('Referal Master');
                    AppConfig().selectedReportType = 'referalmaster';
                    GreekNavigator.pop(context: context);
                    HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.referalmaster);
                  },
                  child: const Text(
                    "Referal Master",
                    style: GreekTextStyle.popupmenutext,
                  ),
                ),
              ),
              Container(
                width: 110,
                margin: const EdgeInsets.all(5),
                decoration: BoxDecoration(
                  shape: BoxShape.rectangle,
                  borderRadius: BorderRadius.circular(5),
                  border: Border.all(
                    // color: const Color(0xFFE3E3E3),
                    color: const Color(0x7CAFAFAF),
                    width: 1,
                  ),
                ),
                alignment: Alignment.center,
                padding: const EdgeInsets.only(left: 0, top: 10, right: 0, bottom: 10),
                child: GestureDetector(
                  onTap: () {
                    print('User Rights');
                    AppConfig().selectedReportType = 'userrights';
                    GreekNavigator.pop(context: context);
                    HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.userrights);
                  },
                  child: const Text(
                    'User Rights',
                    style: GreekTextStyle.popupmenutext,
                  ),
                ),
              ),
              GestureDetector(
                onTap: () {
                  //BO Code Master
                  Navigator.pop(context);
                  screensStreamLarge.sink.add(AdminScreenLarge.bocodemasterscreen);
                },
                child: Container(
                  width: 110,
                  margin: const EdgeInsets.all(5),
                  decoration: BoxDecoration(
                    shape: BoxShape.rectangle,
                    borderRadius: BorderRadius.circular(5),
                    border: Border.all(
                      // color: const Color(0xFFE3E3E3),
                      color: const Color(0x7CAFAFAF),
                      width: 1,
                    ),
                  ),
                  alignment: Alignment.center,
                  padding: const EdgeInsets.only(left: 0, top: 10, right: 0, bottom: 10),
                  child: const Text(
                    'Bo Code Master',
                    style: GreekTextStyle.popupmenutext,
                  ),
                ),
              ),
            ],
          ),
        ],
      ),
    );
  }

  rekycPopupMenu() {
    return SizedBox(
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        children: [
          Column(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: [
              Container(
                width: 110,
                margin: const EdgeInsets.all(5),
                decoration: BoxDecoration(
                  shape: BoxShape.rectangle,
                  borderRadius: BorderRadius.circular(5),
                  border: Border.all(
                    // color: const Color(0xFFE3E3E3),
                    color: const Color(0x7CAFAFAF),
                    width: 1,
                  ),
                ),
                alignment: Alignment.center,
                padding: const EdgeInsets.only(left: 0, top: 10, right: 0, bottom: 10),
                child: GestureDetector(
                  onTap: () {
                    AppConfig().selectedReportType = 'rekycstatusreport';
                    AppConfig().sectionTitle = "STATUS REPORT";
                    GreekNavigator.pop(context: context);
                    HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.rekycreport);
                  },
                  child: const Text(
                    "Status Report",
                    style: GreekTextStyle.popupmenutext,
                  ),
                ),
              ),
              Container(
                width: 110,
                margin: const EdgeInsets.all(5),
                decoration: BoxDecoration(
                  shape: BoxShape.rectangle,
                  borderRadius: BorderRadius.circular(5),
                  border: Border.all(
                    // color: const Color(0xFFE3E3E3),
                    color: const Color(0x7CAFAFAF),
                    width: 1,
                  ),
                ),
                alignment: Alignment.center,
                padding: const EdgeInsets.only(left: 0, top: 10, right: 0, bottom: 10),
                child: GestureDetector(
                  onTap: () {
                    AppConfig().selectedReportType = 'rekycdataanalysis';
                    AppConfig().sectionTitle = "DATA ANALYSIS";
                    GreekNavigator.pop(context: context);
                    HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.rekycreport);
                  },
                  child: const Text(
                    'Data Analysis Report',
                    style: GreekTextStyle.popupmenutext,
                  ),
                ),
              ),
              Container(
                width: 110,
                margin: const EdgeInsets.all(5),
                decoration: BoxDecoration(
                  shape: BoxShape.rectangle,
                  borderRadius: BorderRadius.circular(5),
                  border: Border.all(
                    // color: const Color(0xFFE3E3E3),
                    color: const Color(0x7CAFAFAF),
                    width: 1,
                  ),
                ),
                alignment: Alignment.center,
                padding: const EdgeInsets.only(left: 0, top: 10, right: 0, bottom: 10),
                child: GestureDetector(
                  onTap: () {
                    AppConfig().selectedReportType = 'rekycglobalsearch';
                    AppConfig().sectionTitle = "GLOBAL SEARCH";
                    GreekNavigator.pop(context: context);
                    HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.rekycreport);
                  },
                  child: const Text(
                    'Global Search',
                    style: GreekTextStyle.popupmenutext,
                  ),
                ),
              ),
            ],
          ),
        ],
      ),
    );
  }

  customDialogmethod(BuildContext context, StateSetter setState) {
    return AlertDialog(
      content: Container(
        width: 500,
        height: 200,
        decoration: BoxDecoration(
          shape: BoxShape.rectangle,
          borderRadius: BorderRadius.circular(5),
          border: Border.all(
            // color: const Color(0xFFE3E3E3),
            color: const Color(0x7CAFAFAF),
            width: 1,
          ),
        ),
        child: Column(
          //mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Container(
                  padding: const EdgeInsets.all(15),
                  child: const Text(
                    'BACKUP DETAILS',
                    style: TextStyle(
                      fontFamily: GreekTextStyle.fontFamilyRobotoRegular,
                      fontSize: 10,
                      fontWeight: FontWeight.w500,
                      color: Colors.black,
                    ),
                  ),
                ),
                Container(
                  padding: const EdgeInsets.only(right: 10),
                  child: IconButton(
                    color: Colors.grey,
                    onPressed: () {
                      Navigator.pop(context);
                    },
                    icon: const Icon(
                      Icons.close_outlined,
                    ),
                  ),
                ),
              ],
            ),
            Container(
              height: 1,
              color: const Color(0x7CAFAFAF),
            ),
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                Column(
                  children: [
                    Container(
                      height: 50,
                    ),
                    InkWell(
                      onTap: () {
                        var url = getBaseAPIUrl() + APINames.downloadZipBackup.name;
                        AnchorElement anchorElement = AnchorElement(href: url);
                        anchorElement.download = url;
                        anchorElement.click();
                        //_downloadImage();
                      },
                      child: Container(
                        padding: EdgeInsets.all(10),
                        decoration: BoxDecoration(
                          shape: BoxShape.rectangle,
                          borderRadius: BorderRadius.circular(5),
                          border: Border.all(
                            // color: const Color(0xFFE3E3E3),
                            color: Colors.grey,
                            width: 1,
                          ),
                        ),
                        alignment: Alignment.center,
                        child: Text(
                          "Click hear to download your backup",
                          style: TextStyle(fontFamily: 'Roboto', color: Color.fromARGB(255, 21, 21, 24), fontSize: 15, fontWeight: FontWeight.bold),
                        ),
                      ),
                    ),
                  ],
                ),
              ],
            )
            // Row(
            //   mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            //   children: [
            //     Container(
            //       margin: const EdgeInsets.only(top: 10),
            //       child: DottedBorder(
            //         borderType: BorderType.RRect,
            //         radius: const Radius.circular(20),
            //         dashPattern: const [10, 10],
            //         color: Colors.grey,
            //         strokeWidth: 2,
            //         child: Card(
            //           shape: RoundedRectangleBorder(
            //             borderRadius: BorderRadius.circular(10),
            //           ),
            //           child: SizedBox(
            //             width: 300,
            //             height: 300,
            //             child: Column(
            //               mainAxisAlignment: MainAxisAlignment.center,
            //               children: [
            //                 Container(
            //                   padding: const EdgeInsets.only(right: 0),
            //                   child: const Icon(
            //                     Icons.arrow_downward_outlined,
            //                     size: 50,
            //                     color: Color(0xFF00258E),
            //                   ),
            //                 ),
            //                 Container(
            //                   padding: const EdgeInsets.all(15),
            //                   child: const Text(
            //                     'Choose Backup Path',
            //                     style: TextStyle(
            //                       fontFamily: GreekTextStyle.fontFamilyRobotoRegular,
            //                       fontSize: 15,
            //                       fontWeight: FontWeight.w500,
            //                       color: Colors.black,
            //                     ),
            //                   ),
            //                 ),
            //                 Container(
            //                   width: 250,
            //                   margin: const EdgeInsets.only(top: 10),
            //                   decoration: BoxDecoration(
            //                     shape: BoxShape.rectangle,
            //                     borderRadius: BorderRadius.circular(5),
            //                     border: Border.all(
            //                       // color: const Color(0xFFE3E3E3),
            //                       color: const Color.fromARGB(123, 177, 189, 6),
            //                       width: 1,
            //                     ),
            //                   ),
            //                   child: Container(
            //                     alignment: Alignment.center,
            //                     padding: const EdgeInsets.all(15),
            //                     child: const Text(
            //                       'No File Chosen',
            //                       style: TextStyle(
            //                         fontFamily: GreekTextStyle.fontFamilyRobotoRegular,
            //                         fontSize: 10,
            //                         fontWeight: FontWeight.w500,
            //                         color: Colors.black,
            //                       ),
            //                     ),
            //                   ),
            //                 ),
            //                 InkWell(
            //                   onTap: () async {
            //                     var url = getBaseAPIUrl() + APINames.downloadBackup.name;
            //                     AnchorElement anchorElement = AnchorElement(href: url);
            //                     anchorElement.download = url;
            //                     anchorElement.click();
            //                     // _downloadImage();
            //                   },
            //                   child: Container(
            //                     width: 110,
            //                     margin: const EdgeInsets.only(top: 15),
            //                     decoration: BoxDecoration(
            //                       shape: BoxShape.rectangle,
            //                       borderRadius: BorderRadius.circular(5),
            //                       color: const Color(0xFF00258E),
            //                     ),
            //                     alignment: Alignment.center,
            //                     padding: const EdgeInsets.only(left: 0, top: 0, right: 0, bottom: 0),
            //                     child: Container(
            //                       padding: const EdgeInsets.all(10),
            //                       child: const Text(
            //                         'Start',
            //                         style: TextStyle(
            //                           fontFamily: GreekTextStyle.fontFamilyRobotoRegular,
            //                           fontSize: 10,
            //                           fontWeight: FontWeight.w500,
            //                           color: Colors.white,
            //                         ),
            //                       ),
            //                     ),
            //                   ),
            //                 ),
            //               ],
            //             ),
            //           ),
            //         ),
            //       ),
            //     ),
            //     SizedBox(
            //       width: 300,
            //       height: 300,
            //       child: Column(
            //         mainAxisAlignment: MainAxisAlignment.center,
            //         children: [
            //           Container(
            //             padding: const EdgeInsets.all(15),
            //             child: const Text(
            //               'Download Files',
            //               style: TextStyle(
            //                 fontFamily: GreekTextStyle.fontFamilyRobotoRegular,
            //                 fontSize: 10,
            //                 fontWeight: FontWeight.w500,
            //                 color: Colors.black,
            //               ),
            //             ),
            //           ),
            //           Expanded(
            //             child: CircularPercentIndicator(
            //               radius: 75.0,
            //               lineWidth: 5.0,
            //               animation: true,
            //               percent: percentage,
            //               animateFromLastPercent: true,
            //               center: Column(
            //                 mainAxisAlignment: MainAxisAlignment.center,
            //                 crossAxisAlignment: CrossAxisAlignment.center,
            //                 children: [
            //                   Text(
            //                     percentagetxt,
            //                     style: const TextStyle(
            //                       fontWeight: FontWeight.bold,
            //                       fontSize: 20.0,
            //                       color: Color(0xFF00258E),
            //                     ),
            //                   ),
            //                   const Padding(
            //                     padding: EdgeInsets.all(8.0),
            //                     child: Text(
            //                       "Downloading...",
            //                       style: TextStyle(
            //                         fontWeight: FontWeight.bold,
            //                         fontSize: 12.0,
            //                         color: Colors.black,
            //                       ),
            //                     ),
            //                   ),
            //                 ],
            //               ),
            //               circularStrokeCap: CircularStrokeCap.round,
            //               progressColor: const Color(0XffFAB804),
            //             ),
            //           ),
            //           Row(
            //             mainAxisAlignment: MainAxisAlignment.spaceBetween,
            //             children: [
            //               InkWell(
            //                 onTap: () {
            //                   timer!.cancel();
            //                 },
            //                 child: Container(
            //                   width: 110,
            //                   height: 30,
            //                   margin: const EdgeInsets.only(left: 15, top: 15),
            //                   decoration: BoxDecoration(
            //                     shape: BoxShape.rectangle,
            //                     borderRadius: BorderRadius.circular(5),
            //                     color: const Color(0x7CAFAFAF),
            //                   ),
            //                   alignment: Alignment.center,
            //                   padding: const EdgeInsets.only(left: 0, top: 0, right: 0, bottom: 0),
            //                   child: Container(
            //                     padding: const EdgeInsets.all(10),
            //                     child: const Text(
            //                       'Pause',
            //                       style: TextStyle(
            //                         fontFamily: GreekTextStyle.fontFamilyRobotoRegular,
            //                         fontSize: 10,
            //                         fontWeight: FontWeight.w500,
            //                         color: Colors.black,
            //                       ),
            //                     ),
            //                   ),
            //                 ),
            //               ),
            //               InkWell(
            //                 onTap: () {
            //                   timer!.cancel();
            //                 },
            //                 child: Container(
            //                   width: 110,
            //                   height: 30,
            //                   margin: const EdgeInsets.only(right: 15, top: 15),
            //                   decoration: BoxDecoration(
            //                     shape: BoxShape.rectangle,
            //                     borderRadius: BorderRadius.circular(5),
            //                     color: const Color(0x7CAFAFAF),
            //                   ),
            //                   alignment: Alignment.center,
            //                   padding: const EdgeInsets.only(left: 0, top: 0, right: 0, bottom: 0),
            //                   child: Container(
            //                     padding: const EdgeInsets.all(10),
            //                     child: const Text(
            //                       'Cancel',
            //                       style: TextStyle(
            //                         fontFamily: GreekTextStyle.fontFamilyRobotoRegular,
            //                         fontSize: 10,
            //                         fontWeight: FontWeight.w500,
            //                         color: Colors.black,
            //                       ),
            //                     ),
            //                   ),
            //                 ),
            //               ),
            //             ],
            //           ),
            //         ],
            //       ),
            //     ),
            //   ],
            // ),
          ],
        ),
      ),
    );
  }

  void startTimer(StateSetter setState) {
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

  Future<void> _downloadImage() async {
    final url = "http://192.168.209.106:3000/downloadFileVideo?pan=BWXPP5879A&videopath=BWXPP5879AIPV.mp4";

    // var request = http.Request('GET', Uri.parse(url));

    // // final response = await request.send();
    // final httpClient = http.Client();
    // final http.StreamedResponse response = await httpClient.send(request);
    // List<int> bytes = [];
    // final contentLength = response.contentLength;

    // response.stream.listen(
    //   (value) {
    //     bytes.addAll(value);
    //     final downloadedLength = bytes.length;
    //     percentage = downloadedLength / contentLength!;
    //     print(percentage);
    //     // notifyListeners();
    //   },
    //   onDone: () async {
    //     print("download completed.");
    //     // _progress = 0;
    //     // notifyListeners();
    //     // await file.writeAsBytes(bytes);
    //   },
    //   onError: (e) {
    //     print(e);
    //   },
    //   cancelOnError: true,
    // );

    // _response = await http.Client().send(http.Request('GET', Uri.parse(url)));
    // _total = _response.contentLength ?? 0;
    // // print(_total);

    // _response.stream.listen((value) {
    //   // setState(() {
    //   _bytes.addAll(value);
    //   _received += value.length;
    //   print('${_received ~/ 1024}/${_total ~/ 1024} KB');
    //   // });
    // }).onDone(() async {
    //   print("download completed.");
    // });

    /*if (response.statusCode == 200) {
      response.stream.listen(
        (List<int> newBytes) {
          bytes.addAll(newBytes);
          final downloadedLength = bytes.length;
          percentage = downloadedLength / contentLength!;
          print(percentage);
          // notifyListeners();
        },
        onDone: () async {
          print("download completed.");
          // _progress = 0;
          // notifyListeners();
          // await file.writeAsBytes(bytes);
        },
        onError: (e) {
          print(e);
        },
        cancelOnError: true,
      );
    } else {
      print(response.reasonPhrase);
    }*/
  }
}
