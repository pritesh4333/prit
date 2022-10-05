import 'package:ekyc_admin/Screens/AdminData/models/response/common_data_grid_table_response_model.dart';
import 'package:ekyc_admin/Screens/AdminData/ui/admin_data_grid.dart';
import 'package:ekyc_admin/Screens/DashBoardScreens/block/dashboard_bloc.dart';
import 'package:ekyc_admin/Utilities/greek_textstyle.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class BankDetailsWebScreen extends StatefulWidget {
  final DashBoardBloc? dashBoardBloc;

  const BankDetailsWebScreen({Key? key, this.dashBoardBloc}) : super(key: key);

  @override
  State<BankDetailsWebScreen> createState() => bankDetailsState();
}

class bankDetailsState extends State<BankDetailsWebScreen> {
  CommonDataGridTableResponseModel? globalRespObj = AdminDataGridScreen.adminDetailGlobalResponseObj;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: Container(
          color: Colors.white,
          padding: EdgeInsets.all(22),
          // color: Color.fromRGBO(244, 244, 244, 1),
          child: Row(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Flexible(
                fit: FlexFit.loose,
                child: SingleChildScrollView(
                  scrollDirection: Axis.vertical,
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        "Bank Account Details",
                        style: GreekTextStyle.personalDetailsHeading,
                      ),
                      Container(
                        margin: EdgeInsets.all(5),
                        decoration: BoxDecoration(
                          shape: BoxShape.rectangle,
                          borderRadius: BorderRadius.circular(5),
                          border: Border.all(
                            // color: const Color(0xFFE3E3E3),
                            color: Color(0x7CAFAFAF),
                            width: 1,
                          ),
                        ),
                        alignment: Alignment.center,
                        padding: const EdgeInsets.only(left: 0, top: 5, right: 0, bottom: 5),
                        child: Padding(
                          padding: const EdgeInsets.all(10.0),
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            mainAxisAlignment: MainAxisAlignment.start,
                            children: [
                              Row(
                                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                children: [
                                  Expanded(
                                    child: Container(
                                      width: 240,
                                      child: Text(
                                        "Account No",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: Container(
                                      width: 240,
                                      child: Text(
                                        "IFSC Code",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: Container(
                                      padding: EdgeInsets.all(5),
                                      width: 240,
                                      child: Text(
                                        "MICR Number",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                ],
                              ),
                              Row(
                                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                children: [
                                  Expanded(
                                    child: Container(
                                      color: Color.fromARGB(255, 238, 237, 237),
                                      height: 40,
                                      width: 240.0,
                                      alignment: Alignment.centerLeft,
                                      margin: EdgeInsets.only(left: 0, right: 18, top: 0),
                                      child: Padding(
                                        padding: const EdgeInsets.only(left: 5, right: 5),
                                        child: Text(
                                          globalRespObj!.accountnumber.toString(),
                                          style: GreekTextStyle.textFieldHeading,
                                        ),
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: Container(
                                      color: Color.fromARGB(255, 238, 237, 237),
                                      height: 40,
                                      width: 240.0,
                                      alignment: Alignment.centerLeft,
                                      margin: EdgeInsets.only(left: 0, right: 18, top: 0),
                                      child: Padding(
                                        padding: const EdgeInsets.only(left: 5, right: 5),
                                        child: Text(
                                          globalRespObj!.ifsccode.toString(),
                                          style: GreekTextStyle.textFieldHeading,
                                        ),
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: Container(
                                      color: Color.fromARGB(255, 238, 237, 237),
                                      height: 40,
                                      width: 240.0,
                                      alignment: Alignment.centerLeft,
                                      margin: EdgeInsets.only(left: 0, right: 18, top: 0),
                                      child: Padding(
                                        padding: const EdgeInsets.only(left: 5, right: 5),
                                        child: Text(
                                          globalRespObj!.micrNo.toString(),
                                          style: GreekTextStyle.textFieldHeading,
                                        ),
                                      ),
                                    ),
                                  ),
                                ],
                              ),
                              Container(
                                margin: EdgeInsets.all(5),
                                child: Row(
                                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                  children: [
                                    Expanded(
                                      child: Container(
                                        width: 240,
                                        child: Text(
                                          "Bank Name",
                                          style: GreekTextStyle.textFieldHeading,
                                        ),
                                      ),
                                    ),
                                    Expanded(
                                      child: Container(
                                        width: 240,
                                        child: Text(
                                          "Branch",
                                          style: GreekTextStyle.textFieldHeading,
                                        ),
                                      ),
                                    ),
                                    Expanded(
                                      child: Container(
                                        width: 240,
                                        child: Text(
                                          " ",
                                          style: GreekTextStyle.textFieldHeading,
                                        ),
                                      ),
                                    ),
                                  ],
                                ),
                              ),
                              Row(
                                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                children: [
                                  Expanded(
                                    child: Container(
                                      color: Color.fromARGB(255, 238, 237, 237),
                                      height: 40,
                                      width: 240.0,
                                      alignment: Alignment.centerLeft,
                                      margin: EdgeInsets.only(left: 0, right: 18, top: 0),
                                      child: Padding(
                                        padding: const EdgeInsets.only(left: 5, right: 5),
                                        child: Text(
                                          globalRespObj!.bankname.toString(),
                                          style: GreekTextStyle.textFieldHeading,
                                        ),
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: Container(
                                      color: Color.fromARGB(255, 238, 237, 237),
                                      height: 40,
                                      width: 240.0,
                                      alignment: Alignment.centerLeft,
                                      margin: EdgeInsets.only(left: 0, right: 18, top: 0),
                                      child: Padding(
                                        padding: const EdgeInsets.only(left: 5, right: 5),
                                        child: Text(
                                          globalRespObj!.bank_branch.toString(),
                                          style: GreekTextStyle.textFieldHeading,
                                        ),
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: Container(
                                      height: 40,
                                      width: 240.0,
                                      alignment: Alignment.centerLeft,
                                      margin: EdgeInsets.only(left: 0, right: 18, top: 0),
                                      child: Padding(
                                        padding: const EdgeInsets.only(left: 5, right: 5),
                                        child: Text(
                                          " ",
                                          style: GreekTextStyle.textFieldHeading,
                                        ),
                                      ),
                                    ),
                                  ),
                                ],
                              ),
                            ],
                          ),
                        ),
                      ),
                      Text(
                        "Bank Address Details",
                        style: GreekTextStyle.personalDetailsHeading,
                      ),
                      Container(
                        margin: EdgeInsets.all(5),
                        decoration: BoxDecoration(
                          shape: BoxShape.rectangle,
                          borderRadius: BorderRadius.circular(5),
                          border: Border.all(
                            // color: const Color(0xFFE3E3E3),
                            color: Color(0x7CAFAFAF),
                            width: 1,
                          ),
                        ),
                        alignment: Alignment.center,
                        padding: const EdgeInsets.only(left: 0, top: 5, right: 0, bottom: 5),
                        child: Padding(
                          padding: const EdgeInsets.all(10.0),
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            mainAxisAlignment: MainAxisAlignment.start,
                            children: [
                              Row(
                                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                children: [
                                  Expanded(
                                    child: Container(
                                      width: 240,
                                      child: Text(
                                        "Address Line 1",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: Container(
                                      padding: EdgeInsets.all(5),
                                      width: 240,
                                      child: Text(
                                        "State",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: Container(
                                      width: 240,
                                      child: Text(
                                        "City",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                ],
                              ),
                              Row(
                                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                children: [
                                  Expanded(
                                    child: Container(
                                      color: Color.fromARGB(255, 238, 237, 237),
                                      height: 40,
                                      width: 240.0,
                                      alignment: Alignment.centerLeft,
                                      margin: EdgeInsets.only(left: 0, right: 18, top: 0),
                                      child: Padding(
                                        padding: const EdgeInsets.only(left: 5, right: 5),
                                        child: Text(
                                          globalRespObj!.bankAddress.toString(),
                                          style: GreekTextStyle.textFieldHeading,
                                        ),
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: Container(
                                      color: Color.fromARGB(255, 238, 237, 237),
                                      height: 40,
                                      width: 240.0,
                                      alignment: Alignment.centerLeft,
                                      margin: EdgeInsets.only(left: 0, right: 18, top: 0),
                                      child: Padding(
                                        padding: const EdgeInsets.only(left: 5, right: 5),
                                        child: Text(
                                          globalRespObj!.bank_address_state.toString(),
                                          style: GreekTextStyle.textFieldHeading,
                                        ),
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: Container(
                                      color: Color.fromARGB(255, 238, 237, 237),
                                      height: 40,
                                      width: 240.0,
                                      alignment: Alignment.centerLeft,
                                      margin: EdgeInsets.only(left: 0, right: 18, top: 0),
                                      child: Padding(
                                        padding: const EdgeInsets.only(left: 5, right: 5),
                                        child: Text(
                                          globalRespObj!.bank_address_city.toString(),
                                          style: GreekTextStyle.textFieldHeading,
                                        ),
                                      ),
                                    ),
                                  ),
                                ],
                              ),
                              Container(
                                margin: EdgeInsets.all(5),
                                child: Row(
                                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                  children: [
                                    Expanded(
                                      child: Container(
                                        width: 240,
                                        child: Text(
                                          "Pincode",
                                          style: GreekTextStyle.textFieldHeading,
                                        ),
                                      ),
                                    ),
                                    Expanded(
                                      child: Container(
                                        width: 240,
                                        child: Text(
                                          "Contact Number",
                                          style: GreekTextStyle.textFieldHeading,
                                        ),
                                      ),
                                    ),
                                    Expanded(
                                      child: Container(
                                        width: 240.0,
                                        alignment: Alignment.centerLeft,
                                        margin: EdgeInsets.only(left: 0, right: 18, top: 0),
                                        child: Padding(
                                          padding: const EdgeInsets.only(left: 5, right: 5),
                                          child: Text(
                                            " ",
                                            style: GreekTextStyle.textFieldHeading,
                                          ),
                                        ),
                                      ),
                                    ),
                                  ],
                                ),
                              ),
                              Row(
                                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                children: [
                                  Expanded(
                                    child: Container(
                                      color: Color.fromARGB(255, 238, 237, 237),
                                      height: 40,
                                      width: 240.0,
                                      alignment: Alignment.centerLeft,
                                      margin: EdgeInsets.only(left: 0, right: 18, top: 0),
                                      child: Padding(
                                        padding: const EdgeInsets.only(left: 5, right: 5),
                                        child: Text(
                                          globalRespObj!.bank_address_pincode.toString(),
                                          style: GreekTextStyle.textFieldHeading,
                                        ),
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: Container(
                                      color: Color.fromARGB(255, 238, 237, 237),
                                      height: 40,
                                      width: 240.0,
                                      alignment: Alignment.centerLeft,
                                      margin: EdgeInsets.only(left: 0, right: 18, top: 0),
                                      child: Padding(
                                        padding: const EdgeInsets.only(left: 5, right: 5),
                                        child: Text(
                                          globalRespObj!.bank_address_contactno.toString(),
                                          style: GreekTextStyle.textFieldHeading,
                                        ),
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: Container(
                                      height: 40,
                                      width: 240.0,
                                      alignment: Alignment.centerLeft,
                                      margin: EdgeInsets.only(left: 0, right: 18, top: 0),
                                      child: Padding(
                                        padding: const EdgeInsets.only(left: 5, right: 5),
                                        child: Text(
                                          " ",
                                          style: GreekTextStyle.textFieldHeading,
                                        ),
                                      ),
                                    ),
                                  ),
                                ],
                              ),
                            ],
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
    );
  }

  @override
  void dispose() {
    super.dispose();
  }
}
