import 'package:ekyc_admin/Screens/AdminData/ui/admin_data_grid.dart';
import 'package:ekyc_admin/Screens/home/response.dart';
import 'package:ekyc_admin/Screens/home/widgets/constants.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class DIGIOWebScreen extends StatefulWidget {
  const DIGIOWebScreen({Key? key}) : super(key: key);

  @override
  State<DIGIOWebScreen> createState() => _DIGIOWebScreenState();
}

class _DIGIOWebScreenState extends State<DIGIOWebScreen> {
  CommonDataGridTableResponseModel? globalRespObj = AdminDataGridScreen.adminDetailGlobalResponseObj;
  final String digioSignResponse = """
{
  "request": {
    "svcVersion": "1.0.0",
    "svcGroup": "",
    "svcName": "jloginNew",
    "gscid": "",
    "assetType": "",
    "data": {
      "version_no": "1.0.1.10",
      "passType": 0,
      "brokerid": 1,
      "userType": "Customer",
      "encryptionType": 1,
      "deviceType": "2",
      "pan_dob": "01/01/1901",
      "gscid": "E019",
      "pass": "cb9bd014b0be9e5c8bc79596cf238a48",
      "deviceDetails": "iPhone iOS 15.4",
      "deviceId": "D572727E85A5",
      "transPass": ""
    }
  }
}
{
  "request": {
    "svcVersion": "1.0.0",
    "svcGroup": "",
    "svcName": "jloginNew",
    "gscid": "",
    "assetType": "",
    "data": {
      "version_no": "1.0.1.10",
      "passType": 0,
      "brokerid": 1,
      "userType": "Customer",
      "encryptionType": 1,
      "deviceType": "2",
      "pan_dob": "01/01/1901",
      "gscid": "E019",
      "pass": "cb9bd014b0be9e5c8bc79596cf238a48",
      "deviceDetails": "iPhone iOS 15.4",
      "deviceId": "D572727E85A5",
      "transPass": ""
    }
  }
}
{
  "request": {
    "svcVersion": "1.0.0",
    "svcGroup": "",
    "svcName": "jloginNew",
    "gscid": "",
    "assetType": "",
    "data": {
      "version_no": "1.0.1.10",
      "passType": 0,
      "brokerid": 1,
      "userType": "Customer",
      "encryptionType": 1,
      "deviceType": "2",
      "pan_dob": "01/01/1901",
      "gscid": "E019",
      "pass": "cb9bd014b0be9e5c8bc79596cf238a48",
      "deviceDetails": "iPhone iOS 15.4",
      "deviceId": "D572727E85A5",
      "transPass": ""
    }
  }
}
""";
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
                  children: [
                    Expanded(
                      flex: 3,
                      child: Row(
                        children: [
                          Flexible(
                            flex: 3,
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                const Text(
                                  'Digio Sign Response',
                                  style: GreekTextStyle.personalDetailsHeading,
                                ),
                                Flexible(
                                  child: Container(
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
                                      child: SingleChildScrollView(
                                        child: Text(
                                          globalRespObj?.json_digiosign_response ?? "",
                                          style: GreekTextStyle.popupmenutext,
                                        ),
                                      )
                                      // child: Column(
                                      //   crossAxisAlignment: CrossAxisAlignment.start,
                                      //   mainAxisAlignment: MainAxisAlignment.start,
                                      //   children: [
                                      //     SingleChildScrollView(
                                      //       child: Container(
                                      //         child: Text(digioSignResponse),
                                      //       ),
                                      //     )
                                      //   ],
                                      // ),
                                      ),
                                ),
                              ],
                            ),
                          ),
                          Flexible(
                            flex: 5,
                            fit: FlexFit.loose,
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                const Text(
                                  'Penny Drop Response',
                                  style: GreekTextStyle.personalDetailsHeading,
                                ),
                                Flexible(
                                  flex: 2,
                                  child: Container(
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
                                    alignment: Alignment.centerLeft,
                                    padding: const EdgeInsets.only(left: 0, top: 5, right: 0, bottom: 5),
                                    child: SingleChildScrollView(
                                      child: Text(
                                        globalRespObj?.json_pennydrop_response ?? "",
                                        style: GreekTextStyle.popupmenutext,
                                      ),
                                    ),
                                  ),
                                ),
                                const Text(
                                  'Atom Response',
                                  style: GreekTextStyle.personalDetailsHeading,
                                ),
                                Flexible(
                                  flex: 1,
                                  child: Container(
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
                                    alignment: Alignment.centerLeft,
                                    padding: const EdgeInsets.only(left: 0, top: 5, right: 0, bottom: 5),
                                    child: SingleChildScrollView(
                                      child: Text(globalRespObj?.json_atom_response ?? "", style: GreekTextStyle.popupmenutext),
                                    ),
                                  ),
                                ),
                                
                              ],
                            ),
                          ),
                        ],
                      ),
                    ),

                    // Flexible(
                    //   flex: 1,
                    //   child: Container(
                    //     margin: EdgeInsets.all(5),
                    //     decoration: BoxDecoration(
                    //       shape: BoxShape.rectangle,
                    //       borderRadius: BorderRadius.circular(5),
                    //       border: Border.all(
                    //         // color: const Color(0xFFE3E3E3),
                    //         color: Color(0x7CAFAFAF),
                    //         width: 1,
                    //       ),
                    //     ),
                    //     alignment: Alignment.center,
                    //     padding: const EdgeInsets.only(left: 0, top: 5, right: 0, bottom: 5),
                    //     child: Padding(
                    //       padding: const EdgeInsets.all(10.0),
                    //       child: Row(
                    //         mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    //         children: [
                    //           Flexible(
                    //             fit: FlexFit.loose,
                    //             child: Column(
                    //               crossAxisAlignment: CrossAxisAlignment.start,
                    //               children: [
                    //                 Expanded(
                    //                   child: Container(
                    //                     alignment: Alignment.center,
                    //                     child: Text(
                    //                       "Upload Doucment Folder as a D Drive",
                    //                       style: GreekTextStyle.textFieldHeading,
                    //                     ),
                    //                   ),
                    //                 ),
                    //                 Expanded(
                    //                   child: Container(
                    //                     margin: EdgeInsets.all(10),
                    //                     decoration: BoxDecoration(
                    //                       shape: BoxShape.rectangle,
                    //                       borderRadius: BorderRadius.circular(20),
                    //                       border: Border.all(
                    //                         // color: const Color(0xFFE3E3E3),
                    //                         color: Color(0x7CAFAFAF),
                    //                         width: 1,
                    //                       ),
                    //                     ),
                    //                     alignment: Alignment.center,
                    //                     padding: const EdgeInsets.only(left: 0, top: 0, right: 0, bottom: 0),
                    //                     child: Text(
                    //                       "Open File Location",
                    //                       style: GreekTextStyle.personalDetailsHeading,
                    //                     ),
                    //                   ),
                    //                 ),
                    //               ],
                    //             ),
                    //           ),
                    //           Flexible(
                    //             child: Column(
                    //               crossAxisAlignment: CrossAxisAlignment.start,
                    //               children: [
                    //                 Row(
                    //                   children: [
                    //                     Container(
                    //                       margin: EdgeInsets.all(5),
                    //                       child: Transform.scale(
                    //                         scale: 1.5,
                    //                         child: Checkbox(
                    //                           checkColor: Colors.blue,
                    //                           activeColor: Color.fromARGB(255, 238, 237, 237),
                    //                           value: true,
                    //                           onChanged: (value) {
                    //                             // setState(
                    //                             //   () {
                    //                             //     personalDetailsBloc.sameAddressCheckbox = value;
                    //                             //     print('same address checkbox ${personalDetailsBloc.sameAddressCheckbox}');
                    //                             //     if (personalDetailsBloc.sameAddressCheckbox) {
                    //                             //       updatePermanentAddressData();
                    //                             //     } else {
                    //                             //       addRemoveAddressData();
                    //                             //     }
                    //                             //   },
                    //                             // );
                    //                           },
                    //                         ),
                    //                       ),
                    //                     ),
                    //                     Container(
                    //                       padding: EdgeInsets.only(left: 10),
                    //                       child: Text(
                    //                         'Received POA',
                    //                         style: GreekTextStyle.textFieldHeading,
                    //                       ),
                    //                     ),
                    //                   ],
                    //                 ),
                    //                 Row(
                    //                   children: [
                    //                     Container(
                    //                       margin: EdgeInsets.all(5),
                    //                       child: Transform.scale(
                    //                         scale: 1.5,
                    //                         child: Checkbox(
                    //                           checkColor: Colors.blue,
                    //                           activeColor: Color.fromARGB(255, 238, 237, 237),
                    //                           value: true,
                    //                           onChanged: (value) {
                    //                             // setState(
                    //                             //   () {
                    //                             //     personalDetailsBloc.sameAddressCheckbox = value;
                    //                             //     print('same address checkbox ${personalDetailsBloc.sameAddressCheckbox}');
                    //                             //     if (personalDetailsBloc.sameAddressCheckbox) {
                    //                             //       updatePermanentAddressData();
                    //                             //     } else {
                    //                             //       addRemoveAddressData();
                    //                             //     }
                    //                             //   },
                    //                             // );
                    //                           },
                    //                         ),
                    //                       ),
                    //                     ),
                    //                     Container(
                    //                       padding: EdgeInsets.only(left: 10),
                    //                       child: Text(
                    //                         'Received Nominee',
                    //                         style: GreekTextStyle.textFieldHeading,
                    //                       ),
                    //                     ),
                    //                   ],
                    //                 ),
                    //               ],
                    //             ),
                    //           ),
                    //           Flexible(
                    //             fit: FlexFit.loose,
                    //             child: Container(
                    //               height: 35,
                    //               width: 100,
                    //               decoration: BoxDecoration(
                    //                 shape: BoxShape.rectangle,
                    //                 borderRadius: BorderRadius.circular(15),
                    //                 color: Color(0x7CAFAFAF),
                    //                 border: Border.all(
                    //                   // color: const Color(0xFFE3E3E3),
                    //                   color: Color(0x7CAFAFAF),
                    //                   width: 1,
                    //                 ),
                    //               ),
                    //               alignment: Alignment.center,
                    //               padding: const EdgeInsets.only(left: 0, top: 10, right: 0, bottom: 10),
                    //               child: Text(
                    //                 "Save",
                    //                 style: GreekTextStyle.personalDetailsHeading,
                    //               ),
                    //             ),
                    //           ),
                    //           Flexible(
                    //             fit: FlexFit.loose,
                    //             child: Container(
                    //               height: 35,
                    //               width: 200,
                    //               decoration: BoxDecoration(
                    //                 shape: BoxShape.rectangle,
                    //                 borderRadius: BorderRadius.circular(10),
                    //                 border: Border.all(
                    //                   // color: const Color(0xFFE3E3E3),
                    //                   color: Color.fromARGB(123, 236, 182, 5),
                    //                   width: 1,
                    //                 ),
                    //               ),
                    //               alignment: Alignment.center,
                    //               padding: const EdgeInsets.only(left: 0, top: 10, right: 0, bottom: 10),
                    //               child: Text(
                    //                 "SEND PENDING DOC EMAIL",
                    //                 style: GreekTextStyle.personalDetailsHeading,
                    //               ),
                    //             ),
                    //           ),
                    //         ],
                    //       ),
                    //     ),
                    //   ),
                    // )
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
