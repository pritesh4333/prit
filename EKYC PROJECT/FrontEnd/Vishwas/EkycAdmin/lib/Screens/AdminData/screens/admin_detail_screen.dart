import 'package:ekyc_admin/Screens/AdminData/ui/bank_detail_web_screen.dart';
import 'package:ekyc_admin/Screens/AdminData/ui/contact_detail.dart';
import 'package:ekyc_admin/Screens/AdminData/ui/digio_web_screen.dart';
import 'package:ekyc_admin/Screens/AdminData/ui/document_web_screen.dart';
import 'package:ekyc_admin/Screens/AdminData/ui/getLocation.dart';

import 'package:ekyc_admin/Screens/AdminData/ui/income_detail_web_screen.dart';
import 'package:ekyc_admin/Screens/AdminData/ui/ipv_web_screen.dart';
import 'package:ekyc_admin/Screens/AdminData/ui/personalDetails_web_view.dart';

import 'package:ekyc_admin/Screens/AdminData/ui/tradinghistory_web_screen.dart';
import 'package:ekyc_admin/Screens/home/blocs.dart';
import 'package:ekyc_admin/Screens/home/screen.dart';
import 'package:flutter/material.dart';

import '../../../Configuration/app_config.dart';

class AdminDetailsScreen extends StatefulWidget {
  const AdminDetailsScreen({Key? key}) : super(key: key);

  @override
  State<AdminDetailsScreen> createState() => _AdminDetailsScreenState();
}

class _AdminDetailsScreenState extends State<AdminDetailsScreen> {
  AdminDataBloc? _adminDataBloc;
  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    _adminDataBloc ??= AdminDataBloc(context);

    return Scaffold(
      body: SafeArea(
        child: Container(
          color: Color(0x7CAFAFAF),
          child: Container(
            margin: EdgeInsets.all(10),
            decoration: BoxDecoration(
              shape: BoxShape.rectangle,
              borderRadius: BorderRadius.circular(5),
            ),
            child: Column(
              children: [
                Container(
                  padding: const EdgeInsets.symmetric(horizontal: 18.0),
                  margin: const EdgeInsets.symmetric(horizontal: 18.0),
                  decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(5),
                    color: Colors.white,
                    //border: Border.all(color: Colors.red)
                  ),
                  child: Column(
                    children: [
                      Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: <Widget>[
                          const Text(
                            'COMPLETED USER DETAILS',
                            style: TextStyle(
                              color: Color.fromRGBO(0, 37, 142, 1),
                              fontFamily: 'Roboto',
                              fontSize: 17,
                              fontWeight: FontWeight.w300,
                            ),
                          ),
                          Row(
                            children: <Widget>[
                              const SizedBox(width: 18.0),
                              IconButton(
                                color: Colors.grey,
                                onPressed: () {
                                  if (HomeScreenState.screenfrom == "admin_data_grid") {
                                    HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.gridscreen);
                                  } else if (HomeScreenState.screenfrom == "report_data_grid") {
                                    if (AppConfig().selectedScreen == "rekycreport") {
                                      HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.rekycreport);
                                    } else if (AppConfig().selectedScreen == "referalmaster") {
                                      HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.referalmaster);
                                    } else if (AppConfig().selectedScreen == "statusreport") {
                                      HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.statusreport);
                                    } else if (AppConfig().selectedScreen == "globalsearch") {
                                      HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.globalsearch);
                                    } else if (AppConfig().selectedScreen == "paymentreport") {
                                      HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.paymentreport);
                                    } else if (AppConfig().selectedScreen == "dataanalysisreport") {
                                      HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.dataanalysisreport);
                                    } else if (AppConfig().selectedScreen == "reipvreport") {
                                      HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.reipvreport);
                                    } else if (AppConfig().selectedScreen == "authorizeuserreport") {
                                      HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.authorizeuserreport);
                                    } else if (AppConfig().selectedScreen == "rejectionreport") {
                                      HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.rejectionreport);
                                    } else if (AppConfig().selectedScreen == "pennydropreport") {
                                      HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.pennydropreport);
                                    }
                                  } else {
                                    HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.gridscreen);
                                  }
                                },
                                icon: const Icon(
                                  Icons.close_outlined,
                                ),
                              ),
                            ],
                          ),
                        ],
                      ),
                      Container(
                        height: 1,
                        color: Colors.grey,
                      ),
                      _tabSection(context),
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

  Widget _tabSection(BuildContext context) {
    return DefaultTabController(
      length: 9,
      child: Column(
        mainAxisSize: MainAxisSize.min,
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        children: <Widget>[
          Container(
            height: 45,
            width: MediaQuery.of(context).size.width,
            // color: const Color(0xFFF5F5F5),
            // margin: const EdgeInsets.only(top: 10.0, bottom: 0.0),
            // padding: const EdgeInsets.only(top: 10, bottom: 10),
            child: const TabBar(
              // controller: tabController,
              //labelStyle: GreekTextStyle.ordertabheading,
              labelColor: Color.fromRGBO(250, 184, 4, 1),

              // unselectedLabelStyle: GreekTextStyle.ordertabheading_2,
              unselectedLabelColor: Color(0xFF000000), //unselectedLabelColor: const Color(0xFFC1D6E2),
              isScrollable: true,
              labelStyle: TextStyle(
                fontFamily: "RobotoRegular",
                fontWeight: FontWeight.normal,
                fontSize: 15,
                color: Colors.black,
              ),
              /*  indicator: BoxDecoration(
                // color: const Color(0xFFFFFFFF),
                borderRadius: BorderRadius.circular(0.0),
              ), */
              indicatorColor: Color.fromRGBO(0, 37, 142, 1),
              tabs: [
                Text('Personal Details'),
                Text('Contact Details'),
                Text('Bank Details'),
                Text('Education / Income'),
                Text('Trading History'),
                Text('Document'),
                Text('IPV'),
                Text('Digio'),
                Text('GeoLocation'),
                // Text('Mark Finish'),
                // Text('Misc'),
              ],
            ),
          ),
          Container(
            //Add this to give height
            height: MediaQuery.of(context).size.height - 180,
            child: TabBarView(children: [
              personalDetails_web_view(),
              ContactDetails(),
              BankDetailsWebScreen(),
              IncomeDetailWebScreen(),
              TradingHistoryWebScreen(),
              DocumentWebScreen(),
              IPVWebScreen(),
              DIGIOWebScreen(),
              GetLocation(),
              // Container(
              //   child: Text("Mark Finish"),
              // ),
              // Container(
              //   child: Text("Misc"),
              // ),
            ]),
          ),
        ],
      ),
    );
  }
}
