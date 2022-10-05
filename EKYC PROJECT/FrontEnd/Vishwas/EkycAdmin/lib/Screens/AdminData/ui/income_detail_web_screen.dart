import 'package:ekyc_admin/Screens/AdminData/models/response/common_data_grid_table_response_model.dart';
import 'package:ekyc_admin/Screens/home/response.dart';
import 'package:ekyc_admin/Screens/AdminData/ui/admin_data_grid.dart';
import 'package:ekyc_admin/Utilities/greek_textstyle.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class IncomeDetailWebScreen extends StatefulWidget {
  const IncomeDetailWebScreen({Key? key}) : super(key: key);

  @override
  State<IncomeDetailWebScreen> createState() => _IncomeDetailWebScreenState();
}

class _IncomeDetailWebScreenState extends State<IncomeDetailWebScreen> {
  CommonDataGridTableResponseModel? globalRespObj = AdminDataGridScreen.adminDetailGlobalResponseObj;
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: Container(
          color: Colors.white,
          padding: const EdgeInsets.all(22),
          child: Row(
            children: [
              Expanded(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    const Text(
                      "Education & Income Details",
                      style: GreekTextStyle.personalDetailsHeading,
                    ),
                    Container(
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
                      padding: const EdgeInsets.only(left: 0, top: 5, right: 0, bottom: 5),
                      child: Padding(
                        padding: const EdgeInsets.all(10.0),
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          mainAxisAlignment: MainAxisAlignment.start,
                          children: [
                            Container(
                              padding: EdgeInsets.only(top: 10, bottom: 10, left: 5),
                              child: Row(
                                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                children: [
                                  const Expanded(
                                    child: SizedBox(
                                      width: 240,
                                      child: Text(
                                        "Income Range",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                  const Expanded(
                                    child: SizedBox(
                                      width: 240,
                                      child: Text(
                                        "Net worth in RS",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                  const Expanded(
                                    child: SizedBox(
                                      width: 240,
                                      child: const Text(
                                        "As On (Date)",
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
                                    color: const Color.fromARGB(255, 238, 237, 237),
                                    height: 40,
                                    width: 240.0,
                                    alignment: Alignment.centerLeft,
                                    margin: const EdgeInsets.only(left: 0, right: 18, top: 0),
                                    child: Padding(
                                      padding: const EdgeInsets.only(left: 5, right: 5),
                                      child: Text(
                                        globalRespObj?.incomerange ?? "",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                ),
                                Expanded(
                                  child: Container(
                                    color: const Color.fromARGB(255, 238, 237, 237),
                                    height: 40,
                                    width: 240.0,
                                    alignment: Alignment.centerLeft,
                                    margin: const EdgeInsets.only(left: 0, right: 18, top: 0),
                                    child: const Padding(
                                      padding: EdgeInsets.only(left: 5, right: 5),
                                      child: Text(
                                        "",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                ),
                                Expanded(
                                  child: Container(
                                    color: const Color.fromARGB(255, 238, 237, 237),
                                    height: 40,
                                    width: 240.0,
                                    alignment: Alignment.centerLeft,
                                    margin: const EdgeInsets.only(left: 0, right: 18, top: 0),
                                    child: Padding(
                                      padding: const EdgeInsets.only(left: 5, right: 5),
                                      child: Text(
                                        globalRespObj?.verifiedAt ?? "",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                ),
                              ],
                            ),
                            Container(
                              padding: EdgeInsets.only(top: 10, bottom: 10, left: 5),
                              child: Row(
                                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                children: const [
                                  Expanded(
                                    child: SizedBox(
                                      width: 240,
                                      child: Text(
                                        "Occupation",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: SizedBox(
                                      width: 240,
                                      child: Text(
                                        "Education",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: SizedBox(
                                      width: 240,
                                      child: Text(
                                        "PEP",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                ],
                              ),
                            ),
                            Row(
                              mainAxisAlignment: MainAxisAlignment.spaceBetween,
                              children: [
                                Expanded(
                                  child: Container(
                                    color: const Color.fromARGB(255, 238, 237, 237),
                                    height: 40,
                                    width: 240.0,
                                    alignment: Alignment.centerLeft,
                                    margin: const EdgeInsets.only(left: 0, right: 18, top: 0),
                                    child: Padding(
                                      padding: const EdgeInsets.only(left: 5, right: 5),
                                      child: Text(
                                        globalRespObj?.occupation ?? "",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                ),
                                Expanded(
                                  child: Container(
                                    color: const Color.fromARGB(255, 238, 237, 237),
                                    height: 40,
                                    width: 240.0,
                                    alignment: Alignment.centerLeft,
                                    margin: const EdgeInsets.only(left: 0, right: 18, top: 0),
                                    child: const Padding(
                                      padding: EdgeInsets.only(left: 5, right: 5),
                                      child: Text(
                                        "",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                ),
                                Expanded(
                                  child: Container(
                                    color: const Color.fromARGB(255, 238, 237, 237),
                                    height: 40,
                                    width: 240.0,
                                    alignment: Alignment.centerLeft,
                                    margin: const EdgeInsets.only(left: 0, right: 18, top: 0),
                                    child: Padding(
                                      padding: const EdgeInsets.only(left: 5, right: 5),
                                      child: Text(
                                        globalRespObj?.action ?? "",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                ),
                              ],
                            )
                          ],
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
