import 'package:ekyc_admin/Screens/AdminData/models/response/common_data_grid_table_response_model.dart';
import 'package:ekyc_admin/Screens/AdminData/ui/admin_data_grid.dart';
import 'package:ekyc_admin/Screens/home/response.dart';
import 'package:ekyc_admin/Utilities/greek_textstyle.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class TradingHistoryWebScreen extends StatefulWidget {
  const TradingHistoryWebScreen({Key? key}) : super(key: key);

  @override
  State<TradingHistoryWebScreen> createState() => _TradingHistoryWebScreenState();
}

class _TradingHistoryWebScreenState extends State<TradingHistoryWebScreen> {
  CommonDataGridTableResponseModel? _globalRespObj;

  @override
  void initState() {
    super.initState();

    _globalRespObj = AdminDataGridScreen.adminDetailGlobalResponseObj;
  }

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
                child: Center(
                  child: Container(
                    decoration: const BoxDecoration(
                      color: Color.fromRGBO(255, 255, 255, 1),
                    ),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.center,
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        Row(
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: [
                            Container(
                              width: 100.0,
                              margin: const EdgeInsets.all(10),
                              decoration: BoxDecoration(
                                shape: BoxShape.rectangle,
                                borderRadius: BorderRadius.circular(5),
                                color: const Color(0x7CAFAFAF),
                                border: Border.all(
                                  // color: const Color(0xFFE3E3E3),
                                  color: const Color(0x7CAFAFAF),
                                  width: 1,
                                ),
                              ),
                              alignment: Alignment.center,
                              padding: const EdgeInsets.only(left: 0, top: 10, right: 0, bottom: 10),
                              child: const Text(
                                "NO",
                                style: GreekTextStyle.popupmenutext,
                              ),
                            ),
                            Container(
                              margin: const EdgeInsets.all(10),
                              /* decoration: BoxDecoration(
                                shape: BoxShape.rectangle,
                                borderRadius: BorderRadius.circular(5),
                                color: Color(0x7CAFAFAF),
                                border: Border.all(
                                  // color: const Color(0xFFE3E3E3),
                                  color: Color(0x7CAFAFAF),
                                  width: 1,
                                ),
                              ), */
                              alignment: Alignment.centerLeft,
                              padding: const EdgeInsets.only(left: 0, top: 10, right: 0, bottom: 10),
                              child: const Text(
                                "Are You dealing with any stack broker?",
                                style: GreekTextStyle.transactionhistorylable,
                              ),
                            ),
                          ],
                        ),
                        Container(
                          margin: const EdgeInsets.only(left: 150, right: 150),
                          decoration: BoxDecoration(
                            color: Colors.white,
                            shape: BoxShape.rectangle,
                            borderRadius: BorderRadius.circular(20),
                            border: Border.all(
                              // color: const Color(0xFFE3E3E3),
                              color: const Color(0x7CAFAFAF),
                              width: 1,
                            ),
                          ),
                          alignment: Alignment.center,
                          padding: const EdgeInsets.only(left: 80.0, top: 5.0, right: 40.0, bottom: 5.0),
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.center,
                            mainAxisAlignment: MainAxisAlignment.start,
                            children: [
                              Row(
                                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                children: [
                                  Flexible(
                                    flex: 1,
                                    child: Row(
                                      children: [
                                        Container(
                                          width: 20.0,
                                          height: 20.0,
                                          decoration: BoxDecoration(
                                            color: const Color(0xFFF3F3F3),
                                            borderRadius: BorderRadius.circular(2.0),
                                            border: Border.all(
                                              color: const Color(0xFFC2C2C2),
                                              width: 1.0,
                                            ),
                                          ),
                                          alignment: Alignment.center,
                                          child: (_globalRespObj?.nseCash == "0")
                                              ? const Icon(
                                                  Icons.check_rounded,
                                                  color: Colors.blue,
                                                  size: 17.0,
                                                )
                                              : null,
                                        ),
                                        Container(
                                          padding: const EdgeInsets.only(left: 10),
                                          child: const Text(
                                            'NSE CASH',
                                            style: GreekTextStyle.textFieldHeading,
                                          ),
                                        ),
                                      ],
                                    ),
                                  ),
                                  Flexible(
                                    flex: 1,
                                    child: Row(
                                      children: [
                                        Container(
                                          width: 20.0,
                                          height: 20.0,
                                          decoration: BoxDecoration(
                                            color: const Color(0xFFF3F3F3),
                                            borderRadius: BorderRadius.circular(2.0),
                                            border: Border.all(
                                              color: const Color(0xFFC2C2C2),
                                              width: 1.0,
                                            ),
                                          ),
                                          alignment: Alignment.center,
                                          child: (_globalRespObj?.nseFo == "1")
                                              ? const Icon(
                                                  Icons.check_rounded,
                                                  color: Colors.blue,
                                                  size: 17.0,
                                                )
                                              : null,
                                        ),
                                        Container(
                                          padding: const EdgeInsets.only(left: 10),
                                          child: const Text(
                                            'NSE FO',
                                            style: GreekTextStyle.textFieldHeading,
                                          ),
                                        ),
                                      ],
                                    ),
                                  ),
                                  Flexible(
                                    flex: 1,
                                    child: Row(
                                      children: [
                                        Container(
                                          width: 20.0,
                                          height: 20.0,
                                          decoration: BoxDecoration(
                                            color: const Color(0xFFF3F3F3),
                                            borderRadius: BorderRadius.circular(2.0),
                                            border: Border.all(
                                              color: const Color(0xFFC2C2C2),
                                              width: 1.0,
                                            ),
                                          ),
                                          alignment: Alignment.center,
                                          child: (_globalRespObj?.nseCurrency == "2")
                                              ? const Icon(
                                                  Icons.check_rounded,
                                                  color: Colors.blue,
                                                  size: 17.0,
                                                )
                                              : null,
                                        ),
                                        Container(
                                          padding: const EdgeInsets.only(left: 10),
                                          child: const Text(
                                            'NSE CURRENCY',
                                            style: GreekTextStyle.textFieldHeading,
                                          ),
                                        ),
                                      ],
                                    ),
                                  ),
                                  Flexible(
                                    flex: 1,
                                    child: Row(
                                      children: [
                                        Container(
                                          width: 20.0,
                                          height: 20.0,
                                          decoration: BoxDecoration(
                                            color: const Color(0xFFF3F3F3),
                                            borderRadius: BorderRadius.circular(2.0),
                                            border: Border.all(
                                              color: const Color(0xFFC2C2C2),
                                              width: 1.0,
                                            ),
                                          ),
                                          alignment: Alignment.center,
                                          /*child: (globalRespObj?.nseComm
                                                      .compareTo("0") ==
                                                  0)
                                              ? const Icon(
                                                  Icons.check_rounded,
                                                  color: Colors.blue,
                                                  size: 17.0,
                                                )
                                              : null,*/
                                        ),
                                        Container(
                                          padding: const EdgeInsets.only(left: 10),
                                          child: const Text(
                                            'NSE COMM',
                                            style: GreekTextStyle.textFieldHeading,
                                          ),
                                        ),
                                      ],
                                    ),
                                  ),
                                ],
                              ),
                              const SizedBox(
                                height: 10,
                              ),
                              Row(
                                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                children: [
                                  Flexible(
                                    flex: 1,
                                    child: Row(
                                      children: [
                                        Container(
                                          width: 20.0,
                                          height: 20.0,
                                          decoration: BoxDecoration(
                                            color: const Color(0xFFF3F3F3),
                                            borderRadius: BorderRadius.circular(2.0),
                                            border: Border.all(
                                              color: const Color(0xFFC2C2C2),
                                              width: 1.0,
                                            ),
                                          ),
                                          alignment: Alignment.center,
                                          child: (_globalRespObj?.bseCash == "3")
                                              ? const Icon(
                                                  Icons.check_rounded,
                                                  color: Colors.blue,
                                                  size: 17.0,
                                                )
                                              : null,
                                        ),
                                        Container(
                                          padding: const EdgeInsets.only(left: 10),
                                          child: const Text(
                                            'BSE CASH',
                                            style: GreekTextStyle.textFieldHeading,
                                          ),
                                        ),
                                      ],
                                    ),
                                  ),
                                  Flexible(
                                    flex: 1,
                                    child: Row(
                                      children: [
                                        Container(
                                          width: 20.0,
                                          height: 20.0,
                                          decoration: BoxDecoration(
                                            color: const Color(0xFFF3F3F3),
                                            borderRadius: BorderRadius.circular(2.0),
                                            border: Border.all(
                                              color: const Color(0xFFC2C2C2),
                                              width: 1.0,
                                            ),
                                          ),
                                          alignment: Alignment.center,
                                          child: (_globalRespObj?.bseFo == "4")
                                              ? const Icon(
                                                  Icons.check_rounded,
                                                  color: Colors.blue,
                                                  size: 17.0,
                                                )
                                              : null,
                                        ),
                                        Container(
                                          padding: const EdgeInsets.only(left: 10),
                                          child: const Text(
                                            'BSE FO',
                                            style: GreekTextStyle.textFieldHeading,
                                          ),
                                        ),
                                      ],
                                    ),
                                  ),
                                  Flexible(
                                    flex: 1,
                                    child: Row(
                                      children: [
                                        Container(
                                          width: 20.0,
                                          height: 20.0,
                                          decoration: BoxDecoration(
                                            color: const Color(0xFFF3F3F3),
                                            borderRadius: BorderRadius.circular(2.0),
                                            border: Border.all(
                                              color: const Color(0xFFC2C2C2),
                                              width: 1.0,
                                            ),
                                          ),
                                          alignment: Alignment.center,
                                          child: (_globalRespObj?.bseCurrency == "5")
                                              ? const Icon(
                                                  Icons.check_rounded,
                                                  color: Colors.blue,
                                                  size: 17.0,
                                                )
                                              : null,
                                        ),
                                        Container(
                                          padding: const EdgeInsets.only(left: 10),
                                          child: const Text(
                                            'BSE CURRENCY',
                                            style: GreekTextStyle.textFieldHeading,
                                          ),
                                        ),
                                      ],
                                    ),
                                  ),
                                  Flexible(
                                    flex: 1,
                                    child: Row(
                                      children: [
                                        Container(
                                          width: 20.0,
                                          height: 20.0,
                                          decoration: BoxDecoration(
                                            color: const Color(0xFFF3F3F3),
                                            borderRadius: BorderRadius.circular(2.0),
                                            border: Border.all(
                                              color: const Color(0xFFC2C2C2),
                                              width: 1.0,
                                            ),
                                          ),
                                          alignment: Alignment.center,
                                          /*child: (globalRespObj?.bseComm
                                                      .compareTo("0") ==
                                                  0)
                                              ? const Icon(
                                                  Icons.check_rounded,
                                                  color: Colors.blue,
                                                  size: 17.0,
                                                )
                                              : null,*/
                                        ),
                                        Container(
                                          padding: const EdgeInsets.only(left: 10),
                                          child: const Text(
                                            'BSE COMMODITY',
                                            style: GreekTextStyle.textFieldHeading,
                                          ),
                                        ),
                                      ],
                                    ),
                                  ),
                                ],
                              ),
                              const SizedBox(
                                height: 10,
                              ),
                              Row(
                                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                children: [
                                  Flexible(
                                    flex: 1,
                                    child: Row(
                                      children: [
                                        Container(
                                          width: 20.0,
                                          height: 20.0,
                                          decoration: BoxDecoration(
                                            color: const Color(0xFFF3F3F3),
                                            borderRadius: BorderRadius.circular(2.0),
                                            border: Border.all(
                                              color: const Color(0xFFC2C2C2),
                                              width: 1.0,
                                            ),
                                          ),
                                          alignment: Alignment.center,
                                          /*child: (globalRespObj?.mcxCash
                                                      .compareTo("0") ==
                                                  0)
                                              ? const Icon(
                                                  Icons.check_rounded,
                                                  color: Colors.blue,
                                                  size: 17.0,
                                                )
                                              : null,*/
                                        ),
                                        Container(
                                          padding: const EdgeInsets.only(left: 10),
                                          child: const Text(
                                            'MCX CASH',
                                            style: GreekTextStyle.textFieldHeading,
                                          ),
                                        ),
                                      ],
                                    ),
                                  ),
                                  Flexible(
                                    flex: 1,
                                    child: Row(
                                      children: [
                                        Container(
                                          width: 20.0,
                                          height: 20.0,
                                          decoration: BoxDecoration(
                                            color: const Color(0xFFF3F3F3),
                                            borderRadius: BorderRadius.circular(2.0),
                                            border: Border.all(
                                              color: const Color(0xFFC2C2C2),
                                              width: 1.0,
                                            ),
                                          ),
                                          alignment: Alignment.center,
                                          /*child: (globalRespObj?.mcxFo
                                                      .compareTo("0") ==
                                                  0)
                                              ? const Icon(
                                                  Icons.check_rounded,
                                                  color: Colors.blue,
                                                  size: 17.0,
                                                )
                                              : null,*/
                                        ),
                                        Container(
                                          padding: const EdgeInsets.only(left: 10),
                                          child: const Text(
                                            'MCX FO',
                                            style: GreekTextStyle.textFieldHeading,
                                          ),
                                        ),
                                      ],
                                    ),
                                  ),
                                  Flexible(
                                    flex: 1,
                                    child: Row(
                                      children: [
                                        Container(
                                          width: 20.0,
                                          height: 20.0,
                                          decoration: BoxDecoration(
                                            color: const Color(0xFFF3F3F3),
                                            borderRadius: BorderRadius.circular(2.0),
                                            border: Border.all(
                                              color: const Color(0xFFC2C2C2),
                                              width: 1.0,
                                            ),
                                          ),
                                          alignment: Alignment.center,
                                          child: (_globalRespObj?.mcxCommodty == "6")
                                              ? const Icon(
                                                  Icons.check_rounded,
                                                  color: Colors.blue,
                                                  size: 17.0,
                                                )
                                              : null,
                                        ),
                                        Container(
                                          padding: const EdgeInsets.only(left: 10),
                                          child: const Text(
                                            'MCX COMMODIY',
                                            style: GreekTextStyle.textFieldHeading,
                                          ),
                                        ),
                                      ],
                                    ),
                                  ),
                                  Flexible(
                                    flex: 1,
                                    child: Row(
                                      children: [
                                        Container(
                                          width: 20.0,
                                          height: 20.0,
                                          decoration: BoxDecoration(
                                            color: const Color(0xFFF3F3F3),
                                            borderRadius: BorderRadius.circular(2.0),
                                            border: Border.all(
                                              color: const Color(0xFFC2C2C2),
                                              width: 1.0,
                                            ),
                                          ),
                                          alignment: Alignment.center,
                                          child: (_globalRespObj?.ncdexCommodty == "7")
                                              ? const Icon(
                                                  Icons.check_rounded,
                                                  color: Colors.blue,
                                                  size: 17.0,
                                                )
                                              : null,
                                        ),
                                        Container(
                                          padding: const EdgeInsets.only(left: 10),
                                          child: const Text(
                                            'NCDEX COMMODIY',
                                            style: GreekTextStyle.textFieldHeading,
                                          ),
                                        ),
                                      ],
                                    ),
                                  ),
                                ],
                              ),
                            ],
                          ),
                        ),
                      ],
                    ),
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
