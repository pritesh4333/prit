import 'package:ekyc_admin/Helper/constant_image_name.dart';
import 'package:ekyc_admin/Network%20Manager/AppURLs/app_url_main.dart';
import 'package:ekyc_admin/Screens/AdminData/models/response/common_data_grid_table_response_model.dart';
import 'package:ekyc_admin/Screens/AdminData/ui/admin_data_grid.dart';
import 'package:ekyc_admin/Utilities/greek_textstyle.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter/src/foundation/key.dart';
import 'package:flutter/src/widgets/framework.dart';
import 'package:video_player/video_player.dart';
import 'dart:html';

import '../../../Extension_Enum/greek_enum.dart';

class IPVWebScreen extends StatefulWidget {
  const IPVWebScreen({Key? key}) : super(key: key);

  @override
  State<IPVWebScreen> createState() => _IPVWebScreenState();
}

class _IPVWebScreenState extends State<IPVWebScreen> {
  late VideoPlayerController _controller;
  late Future<void> _initializeVideoPlayerFuture;
  CommonDataGridTableResponseModel? globalRespObj = AdminDataGridScreen.adminDetailGlobalResponseObj;

  @override
  void initState() {
    super.initState();

    _controller = VideoPlayerController.network(
      getBaseAPIUrl() + "downloadFileVideo?pan=" + globalRespObj!.pan.toString() + "&videopath=" + globalRespObj!.ipv.toString() + ".mp4",
    );

    // Initialize the controller and store the Future for later use.
    _initializeVideoPlayerFuture = _controller.initialize();

    // Use the controller to loop the video.
    _controller.setLooping(true);
    _controller.setVolume(10.0);
    _controller.play();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: Container(
          color: Colors.white,
          padding: EdgeInsets.all(22),
          child: Row(
            children: [
              Expanded(
                flex: 3,
                child: Column(
                  children: [
                    Flexible(
                      flex: 5,
                      child: Container(
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
                        child: Column(
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: [
                            Container(
                              child: FutureBuilder(
                                future: _initializeVideoPlayerFuture,
                                builder: (context, snapshot) {
                                  if (snapshot.connectionState == ConnectionState.done) {
                                    // If the VideoPlayerController has finished initialization, use
                                    // the data it provides to limit the aspect ratio of the video.
                                    return Container(
                                      height: 410,
                                      width: 550,
                                      alignment: Alignment.center,
                                      // Use the VideoPlayer widget to display the video.
                                      child: VideoPlayer(_controller),
                                    );
                                  } else {
                                    // If the VideoPlayerController is still initializing, show a
                                    // loading spinner.
                                    return Container(
                                      height: 410,
                                      child: Column(
                                        crossAxisAlignment: CrossAxisAlignment.center,
                                        mainAxisAlignment: MainAxisAlignment.center,
                                        children: [
                                          CircularProgressIndicator(),
                                        ],
                                      ),
                                    );
                                  }
                                },
                              ),
                            ),
                          ],
                        ),
                      ),
                    ),
                    Flexible(
                      flex: 1,
                      child: Container(
                        alignment: Alignment.center,
                        child: Padding(
                          padding: const EdgeInsets.all(0.0),
                          child: Row(
                            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                            children: [
                              Flexible(
                                flex: 1,
                                fit: FlexFit.loose,
                                child: Container(
                                  margin: EdgeInsets.all(3),
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
                                  child: Row(
                                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                    children: [
                                      Flexible(
                                        child: Row(
                                          mainAxisAlignment: MainAxisAlignment.spaceAround,
                                          children: [
                                            Flexible(
                                              flex: 1,
                                              child: InkWell(
                                                onTap: () {
                                                  _controller.play();
                                                },
                                                child: Container(
                                                  margin: EdgeInsets.all(0),
                                                  decoration: BoxDecoration(
                                                    color: Color(0xFF00258E),
                                                    shape: BoxShape.circle,
                                                  ),
                                                  child: Icon(
                                                    Icons.play_arrow,
                                                    color: Colors.white,
                                                  ),
                                                ),
                                              ),
                                            ),
                                            Flexible(
                                              flex: 4,
                                              child: Container(
                                                child: VideoProgressIndicator(_controller,
                                                    allowScrubbing: true,
                                                    colors: VideoProgressColors(
                                                      backgroundColor: Color.fromARGB(123, 236, 182, 5),
                                                      playedColor: Color(0xFF00258E),
                                                      bufferedColor: Color(0xFFFABA0D),
                                                    )),
                                              ),
                                            ),
                                            Flexible(
                                              flex: 1,
                                              child: InkWell(
                                                onTap: () {
                                                  _controller.setVolume(0);
                                                },
                                                child: Container(
                                                  margin: EdgeInsets.all(0),
                                                  decoration: BoxDecoration(
                                                      border: Border.all(
                                                        color: Color(0xFF00258E),
                                                      ),
                                                      borderRadius: BorderRadius.all(Radius.circular(20))),
                                                  child: Icon(
                                                    Icons.volume_up_rounded,
                                                    color: Color(0xFF00258E),
                                                  ),
                                                ),
                                              ),
                                            ),
                                            Flexible(
                                              flex: 1,
                                              child: InkWell(
                                                onTap: () {
                                                  _controller.pause();
                                                },
                                                child: Container(
                                                  decoration: BoxDecoration(
                                                      border: Border.all(
                                                        color: Color(0xFF00258E),
                                                      ),
                                                      borderRadius: BorderRadius.all(Radius.circular(20))),
                                                  child: Icon(
                                                    Icons.pause,
                                                    color: Color(0xFF00258E),
                                                  ),
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
                              Flexible(
                                flex: 2,
                                fit: FlexFit.loose,
                                child: Container(
                                  margin: EdgeInsets.all(3),
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
                                  padding: const EdgeInsets.only(left: 0, top: 5, right: 0, bottom: 0),
                                  child: Row(
                                    mainAxisAlignment: MainAxisAlignment.spaceAround,
                                    children: [
                                      Row(
                                        mainAxisAlignment: MainAxisAlignment.spaceAround,
                                        children: [
                                          Row(
                                            mainAxisAlignment: MainAxisAlignment.spaceAround,
                                            children: [
                                              Container(
                                                margin: EdgeInsets.all(5),
                                                child: Transform.scale(
                                                  scale: 1.5,
                                                  child: Checkbox(
                                                    checkColor: Colors.blue,
                                                    activeColor: Color.fromARGB(255, 238, 237, 237),
                                                    value: true,
                                                    onChanged: (value) {
                                                      // setState(
                                                      //   () {
                                                      //     personalDetailsBloc.sameAddressCheckbox = value;
                                                      //     print('same address checkbox ${personalDetailsBloc.sameAddressCheckbox}');
                                                      //     if (personalDetailsBloc.sameAddressCheckbox) {
                                                      //       updatePermanentAddressData();
                                                      //     } else {
                                                      //       addRemoveAddressData();
                                                      //     }
                                                      //   },
                                                      // );
                                                    },
                                                  ),
                                                ),
                                              ),
                                              Container(
                                                padding: EdgeInsets.only(left: 10),
                                                child: Text(
                                                  'IPV Not Clear ?',
                                                  style: GreekTextStyle.textFieldHeading,
                                                ),
                                              ),
                                            ],
                                          ),
                                        ],
                                      ),
                                      Container(
                                        height: 35,
                                        width: 225,
                                        decoration: BoxDecoration(
                                          shape: BoxShape.rectangle,
                                          borderRadius: BorderRadius.circular(10),
                                          border: Border.all(
                                            // color: const Color(0xFFE3E3E3),
                                            color: Color.fromARGB(123, 236, 182, 5),
                                            width: 1,
                                          ),
                                        ),
                                        alignment: Alignment.center,
                                        padding: const EdgeInsets.only(left: 0, top: 10, right: 0, bottom: 10),
                                        child: Text(
                                          "Send request to client for Re-IPV",
                                          style: GreekTextStyle.personalDetailsHeading,
                                        ),
                                      ),
                                      InkWell(
                                        onTap: () {
                                          var url = getBaseAPIUrl() + APINames.downloadUserDoc.name + "?pan=" + globalRespObj!.pan + "&downloadType=ipv";
                                          AnchorElement anchorElement = AnchorElement(href: url);
                                          anchorElement.download = url;
                                          anchorElement.click();
                                        },
                                        child: Container(
                                          height: 35,
                                          width: 200,
                                          decoration: BoxDecoration(
                                            shape: BoxShape.rectangle,
                                            borderRadius: BorderRadius.circular(10),
                                            border: Border.all(
                                              // color: const Color(0xFFE3E3E3),
                                              color: const Color.fromARGB(123, 236, 182, 5),
                                              width: 1,
                                            ),
                                          ),
                                          alignment: Alignment.center,
                                          padding: const EdgeInsets.only(left: 0, top: 10, right: 0, bottom: 10),
                                          child: const Text(
                                            "Download Zip",
                                            style: GreekTextStyle.personalDetailsHeading,
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
                    )
                  ],
                ),
              ),
              // Expanded(
              //   flex: 1,
              //   child: Column(
              //     children: [
              //       Flexible(
              //         flex: 2,
              //         child: Container(
              //           margin: EdgeInsets.only(left: 10, top: 5, right: 10, bottom: 10),
              //           decoration: BoxDecoration(
              //             shape: BoxShape.rectangle,
              //             borderRadius: BorderRadius.circular(5),
              //             border: Border.all(
              //               // color: const Color(0xFFE3E3E3),
              //               color: Color(0x7CAFAFAF),
              //               width: 1,
              //             ),
              //           ),
              //           alignment: Alignment.center,
              //           padding: const EdgeInsets.only(left: 0, top: 0, right: 0, bottom: 5),
              //           child: Padding(
              //             padding: const EdgeInsets.all(10.0),
              //             child: Column(
              //               crossAxisAlignment: CrossAxisAlignment.start,
              //               mainAxisAlignment: MainAxisAlignment.start,
              //               children: [
              //                 // Row(
              //                 //   mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              //                 //   children: [
              //                 //     Expanded(
              //                 //       child: Container(
              //                 //         width: 110,
              //                 //         margin: EdgeInsets.all(5),
              //                 //         decoration: BoxDecoration(
              //                 //           shape: BoxShape.rectangle,
              //                 //           borderRadius: BorderRadius.circular(5),
              //                 //           color: Color(0xFFF5F5F5),
              //                 //           border: Border.all(
              //                 //             // color: const Color(0xFFE3E3E3),
              //                 //             color: Color(0xFFF5F5F5),
              //                 //             width: 1,
              //                 //           ),
              //                 //         ),
              //                 //         alignment: Alignment.center,
              //                 //         padding: const EdgeInsets.only(left: 0, top: 10, right: 0, bottom: 10),
              //                 //         child: Text(
              //                 //           "Raw PDF",
              //                 //           style: GreekTextStyle.rightboxbuttontext,
              //                 //         ),
              //                 //       ),
              //                 //     ),
              //                 //     Expanded(
              //                 //       child: Container(
              //                 //         width: 110,
              //                 //         margin: EdgeInsets.all(5),
              //                 //         decoration: BoxDecoration(
              //                 //           shape: BoxShape.rectangle,
              //                 //           borderRadius: BorderRadius.circular(5),
              //                 //           color: Color(0xFFF5F5F5),
              //                 //           border: Border.all(
              //                 //             // color: const Color(0xFFE3E3E3),
              //                 //             color: Color(0xFFF5F5F5),
              //                 //             width: 1,
              //                 //           ),
              //                 //         ),
              //                 //         alignment: Alignment.center,
              //                 //         padding: const EdgeInsets.only(left: 0, top: 10, right: 0, bottom: 10),
              //                 //         child: Text(
              //                 //           "Raw PDF COMM",
              //                 //           style: GreekTextStyle.rightboxbuttontext,
              //                 //         ),
              //                 //       ),
              //                 //     ),
              //                 //   ],
              //                 // ),
              //                 // Row(
              //                 //   mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              //                 //   children: [
              //                 //     Expanded(
              //                 //       child: Container(
              //                 //         width: 110,
              //                 //         margin: EdgeInsets.all(5),
              //                 //         decoration: BoxDecoration(
              //                 //           shape: BoxShape.rectangle,
              //                 //           borderRadius: BorderRadius.circular(5),
              //                 //           color: Color(0xFFF5F5F5),
              //                 //           border: Border.all(
              //                 //             // color: const Color(0xFFE3E3E3),
              //                 //             color: Color(0xFFF5F5F5),
              //                 //             width: 1,
              //                 //           ),
              //                 //         ),
              //                 //         alignment: Alignment.center,
              //                 //         padding: const EdgeInsets.only(left: 0, top: 10, right: 0, bottom: 10),
              //                 //         child: Text(
              //                 //           "Sign PDF",
              //                 //           style: GreekTextStyle.rightboxbuttontext,
              //                 //         ),
              //                 //       ),
              //                 //     ),
              //                 //     Expanded(
              //                 //       child: Container(
              //                 //         width: 110,
              //                 //         margin: EdgeInsets.all(5),
              //                 //         decoration: BoxDecoration(
              //                 //           shape: BoxShape.rectangle,
              //                 //           borderRadius: BorderRadius.circular(5),
              //                 //           color: Color(0xFFF5F5F5),
              //                 //           border: Border.all(
              //                 //             // color: const Color(0xFFE3E3E3),
              //                 //             color: Color(0xFFF5F5F5),
              //                 //             width: 1,
              //                 //           ),
              //                 //         ),
              //                 //         alignment: Alignment.center,
              //                 //         padding: const EdgeInsets.only(left: 0, top: 10, right: 0, bottom: 10),
              //                 //         child: Text(
              //                 //           "Sign PDF COMM",
              //                 //           style: GreekTextStyle.rightboxbuttontext,
              //                 //         ),
              //                 //       ),
              //                 //     ),
              //                 //   ],
              //                 // ),
              //                 // Row(
              //                 //   mainAxisAlignment: MainAxisAlignment.center,
              //                 //   children: [
              //                 //     Flexible(
              //                 //       flex: 1,
              //                 //       child: Container(
              //                 //         width: 150,
              //                 //         margin: EdgeInsets.only(left: 60, right: 60, top: 10, bottom: 10),
              //                 //         decoration: BoxDecoration(
              //                 //           shape: BoxShape.rectangle,
              //                 //           borderRadius: BorderRadius.circular(5),
              //                 //           color: Color(0xFFF5F5F5),
              //                 //           border: Border.all(
              //                 //             // color: const Color(0xFFE3E3E3),
              //                 //             color: Color(0xFFF5F5F5),
              //                 //             width: 1,
              //                 //           ),
              //                 //         ),
              //                 //         alignment: Alignment.center,
              //                 //         padding: const EdgeInsets.only(left: 0, top: 10, right: 0, bottom: 10),
              //                 //         child: Text(
              //                 //           "KYC PDF",
              //                 //           style: GreekTextStyle.rightboxbuttontext,
              //                 //         ),
              //                 //       ),
              //                 //     ),
              //                 //   ],
              //                 // ),

              //                 // Flexible(
              //                 //   child: Row(
              //                 //     mainAxisAlignment: MainAxisAlignment.center,
              //                 //     children: [
              //                 //       Flexible(
              //                 //         child: Container(
              //                 //           height: 35,
              //                 //           width: 200,
              //                 //           margin: EdgeInsets.only(left: 0, right: 0, top: 10, bottom: 10),
              //                 //           decoration: BoxDecoration(
              //                 //             shape: BoxShape.rectangle,
              //                 //             borderRadius: BorderRadius.circular(20),
              //                 //             border: Border.all(
              //                 //               // color: const Color(0xFFE3E3E3),
              //                 //               color: Color.fromARGB(123, 236, 182, 5),
              //                 //               width: 1,
              //                 //             ),
              //                 //           ),
              //                 //           alignment: Alignment.center,
              //                 //           padding: const EdgeInsets.only(left: 0, top: 10, right: 0, bottom: 10),
              //                 //           child: Text(
              //                 //             "Download Zip",
              //                 //             style: GreekTextStyle.popupmenutext,
              //                 //           ),
              //                 //         ),
              //                 //       ),
              //                 //     ],
              //                 //   ),
              //                 // ),
              //                 // // Flexible(
              //                 //   child: Row(
              //                 //     crossAxisAlignment: CrossAxisAlignment.center,
              //                 //     mainAxisAlignment: MainAxisAlignment.center,
              //                 //     children: [
              //                 //       Flexible(
              //                 //         child: Container(
              //                 //           height: 100,
              //                 //           margin: EdgeInsets.all(10),
              //                 //           child: Transform.scale(
              //                 //             scale: 1.5,
              //                 //             child: Checkbox(
              //                 //               checkColor: Colors.blue,
              //                 //               activeColor: Color(0xFFF5F5F5),
              //                 //               value: true,
              //                 //               onChanged: (value) {
              //                 //                 // setState(
              //                 //                 //   () {
              //                 //                 //     personalDetailsBloc.sameAddressCheckbox = value;
              //                 //                 //     print('same address checkbox ${personalDetailsBloc.sameAddressCheckbox}');
              //                 //                 //     if (personalDetailsBloc.sameAddressCheckbox) {
              //                 //                 //       updatePermanentAddressData();
              //                 //                 //     } else {
              //                 //                 //       addRemoveAddressData();
              //                 //                 //     }
              //                 //                 //   },
              //                 //                 // );
              //                 //               },
              //                 //             ),
              //                 //           ),
              //                 //         ),
              //                 //       ),
              //                 //       Flexible(
              //                 //         flex: 2,
              //                 //         child: Container(
              //                 //           padding: EdgeInsets.only(left: 10),
              //                 //           child: Text(
              //                 //             'Transfer to Back office',
              //                 //             style: GreekTextStyle.textFieldHeading,
              //                 //           ),
              //                 //         ),
              //                 //       ),
              //                 //       Flexible(
              //                 //         child: Container(
              //                 //           padding: EdgeInsets.only(left: 10),
              //                 //           child: Transform.scale(
              //                 //             scale: 1.5,
              //                 //             child: Checkbox(
              //                 //               checkColor: Colors.blue,
              //                 //               activeColor: Color(0xFFF5F5F5),
              //                 //               value: true,
              //                 //               onChanged: (value) {
              //                 //                 // setState(
              //                 //                 //   () {
              //                 //                 //     personalDetailsBloc.sameAddressCheckbox = value;
              //                 //                 //     print('same address checkbox ${personalDetailsBloc.sameAddressCheckbox}');
              //                 //                 //     if (personalDetailsBloc.sameAddressCheckbox) {
              //                 //                 //       updatePermanentAddressData();
              //                 //                 //     } else {
              //                 //                 //       addRemoveAddressData();
              //                 //                 //     }
              //                 //                 //   },
              //                 //                 // );
              //                 //               },
              //                 //             ),
              //                 //           ),
              //                 //         ),
              //                 //       ),
              //                 //       Flexible(
              //                 //         flex: 2,
              //                 //         child: Container(
              //                 //           padding: EdgeInsets.only(left: 10),
              //                 //           child: Text(
              //                 //             'Rejection',
              //                 //             style: GreekTextStyle.textFieldHeading,
              //                 //           ),
              //                 //         ),
              //                 //       ),
              //                 //     ],
              //                 //   ),
              //                 // ),
              //                 // Flexible(
              //                 //   flex: 3,
              //                 //   child: Container(
              //                 //     height: 500,
              //                 //     decoration: BoxDecoration(
              //                 //       shape: BoxShape.rectangle,
              //                 //       borderRadius: BorderRadius.circular(5),
              //                 //       border: Border.all(
              //                 //         // color: const Color(0xFFE3E3E3),
              //                 //         color: Color(0x7CAFAFAF),
              //                 //         width: 1,
              //                 //       ),
              //                 //     ),
              //                 //     margin: EdgeInsets.all(10),
              //                 //     child: TextField(
              //                 //       textCapitalization: TextCapitalization.words,
              //                 //       textAlign: TextAlign.center,
              //                 //       // onChanged: (value) {
              //                 //       //   this.personalDetailsBloc.resAddressLine1TextController.value = TextEditingValue(text: value.toUpperCase(), selection: this.personalDetailsBloc.resAddressLine1TextController.selection);
              //                 //       // },
              //                 //       // controller: personalDetailsBloc.resAddressLine1TextController,
              //                 //       inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z0-9 ,./()-]'))],
              //                 //       maxLines: 5,
              //                 //       decoration: InputDecoration(
              //                 //           border: InputBorder.none,
              //                 //           contentPadding: EdgeInsets.only(left: 10, bottom: 10, top: 85, right: 10), // add padding to adjust text
              //                 //           isDense: false,
              //                 //           hintText: "Type rejection reason",
              //                 //           hintStyle: TextStyle(
              //                 //             fontWeight: FontWeight.w100,
              //                 //             fontSize: 10,
              //                 //             color: Color.fromARGB(255, 207, 202, 202),
              //                 //           )
              //                 //           // errorText: validResAddress1.isEmpty ? null : validResAddress1,
              //                 //           ),
              //                 //     ),
              //                 //   ),
              //                 // ),
              //                 // Flexible(
              //                 //   child: Row(
              //                 //     mainAxisAlignment: MainAxisAlignment.center,
              //                 //     children: [
              //                 //       Flexible(
              //                 //         child: Container(
              //                 //           height: 35,
              //                 //           width: 200,
              //                 //           margin: EdgeInsets.only(top: 10),
              //                 //           decoration: BoxDecoration(
              //                 //             shape: BoxShape.rectangle,
              //                 //             borderRadius: BorderRadius.circular(20),
              //                 //             color: Color(0xFF00258E),
              //                 //             border: Border.all(
              //                 //               // color: const Color(0xFFE3E3E3),
              //                 //               color: Color.fromARGB(123, 236, 182, 5),
              //                 //               width: 1,
              //                 //             ),
              //                 //           ),
              //                 //           alignment: Alignment.center,
              //                 //           padding: const EdgeInsets.only(left: 0, top: 10, right: 0, bottom: 10),
              //                 //           child: Text(
              //                 //             "Send Message",
              //                 //             style: TextStyle(fontWeight: FontWeight.w300, fontSize: 10, color: Colors.white),
              //                 //           ),
              //                 //         ),
              //                 //       ),
              //                 //     ],
              //                 //   ),
              //                 // ),

              //               ],
              //             ),
              //           ),
              //         ),
              //       )
              //     ],
              //   ),
              // )
            ],
          ),
        ),
      ),
    );
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }
}
