import 'dart:async';
import 'dart:typed_data';
import 'package:camera/camera.dart';
import 'package:e_kyc/Login/UI/EsignScreen/repository/EsignRepository.dart';
import 'package:e_kyc/Login/UI/HomeScreen/HomeScreenSmall.dart';
import 'package:e_kyc/Login/UI/IPVScreen/VideoRecorderExample.dart';
import 'package:e_kyc/Login/UI/IPVScreen/bloc/IpvScreenBlock.dart';
import 'package:e_kyc/Login/UI/Login/repository/LoginRepository.dart';
import 'package:e_kyc/Login/UI/PanDetailsScreen/repository/PanDetailRepository.dart';
import 'package:http_parser/http_parser.dart';
import 'package:e_kyc/Login/UI/Configuration/AppConfig.dart';
import 'package:e_kyc/Login/UI/HomeScreen/HomeScreenLarge.dart';
import 'package:e_kyc/Login/UI/Login/View/LoginUI.dart';
import 'package:e_kyc/Login/UI/Login/bloc/LoginBloc.dart';
import 'package:e_kyc/Login/UI/ThemeColors.dart';
import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';
import 'package:path_provider/path_provider.dart';
import 'package:rxdart/rxdart.dart';
import 'package:video_player/video_player.dart';

import 'package:e_kyc/Login/UI/IPVScreen/platformspec/mobiledevice.dart'
    if (dart.library.html) "package:e_kyc/Login/UI/IPVScreen/platformspec/webdevice.dart"
    as newWindow;

class IPVScreenSmallIOS extends StatefulWidget {
  @override
  _IPVScreenLargeState createState() => _IPVScreenLargeState();
}

class _IPVScreenLargeState extends State<IPVScreenSmallIOS> {
  Timer _timer;
  int _start = null;
  String time = "00m : 00s";
  IpvScreenBlock ipcBloc = new IpvScreenBlock();
  var globalRespObj = LoginRepository.loginDetailsResObjGlobal;
  ScrollController _scrollController = ScrollController();
  Uint8List fileBytes;
  FilePickerResult result;
  var validfullname = "",
      validemail = "",
      validmobile = "",
      validclientcode = "";
  List<int> _selectedFile;
  bool recordbtnflag = false;
  VideoPlayerController _controller;
  Future<void> _initializeVideoPlayerFuture;
  static CameraController cameraController;
  List<CameraDescription> cameras;
  int selectedCameraIdx;
  XFile rawVideo;
  var cameraViewFlag = true;
  var playerViewFlag = false;
  var videoPath;
  var panName;
  var fullName;
  var isvideoRecording = false;
  static var isvideouploadedsmall = false;
  String url;
  final BehaviorSubject<String> recoicon = BehaviorSubject<String>();
  Stream<String> get recoiconstream => this.recoicon.stream;
  @override
  void dispose() {
    // Ensure disposing of the VideoPlayerController to free up resources.
    _timer.cancel();
    if (cameraController != null) {
      cameraController.dispose();
      cameraController = null;
    }
    // if (_controller != null) {
    //   _controller.dispose();
    //   _controller = null;
    // }

    print("CAMERA dispose ");
    super.dispose();
  }

  @override
  void initState() {
    super.initState();
    print("i am inti ipv SCREEN SMALL IOS");
    HomeScreenSmall.percentageFlagSmall.add("0.905");

    //_controller = null;

    if (globalRespObj != null) {
      print('Data already is in global object');
      if (globalRespObj.response.errorCode == "0") {
        videoPath = globalRespObj.response.data.message[0].ipv;
        panName = globalRespObj.response.data.message[0].pan;
        fullName = globalRespObj.response.data.message[0].fullName;

        if (videoPath != "") {
          print("INTI method call ");
          playerViewFlag = true;
          url = AppConfig().url.toString() +
              'images/' +
              videoPath.toString() +
              '.mp4';
        } else {}
      }
    } else {
      panName = PanDetailRepository.userPanNumber;
      fullName = LoginRepository.loginFullName;
      // Get the listonNewCameraSelected of available cameras.
      // Then set the first camera as selected.

    }
    camerainit();
    if (EsignRepository.timerObj != null) {
      EsignRepository.timerObj.cancel();
      EsignRepository.timerObj = null;
    }
  }

  void camerainit() {
    availableCameras().then((availableCameras) {
      cameras = availableCameras;

      if (cameras.length > 0) {
        setState(() {
          selectedCameraIdx = 0;
        });

        _onCameraSwitched(cameras[selectedCameraIdx]).then((void v) {});
      }
    }).catchError((err) {
      print('IOS Error: $err.code\nError Message: $err.message');
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: SafeArea(
            child: SingleChildScrollView(
                child: Container(
      child: Column(
        children: [
          header(context),
          iPVDetailsForm(context),
          videoTimer(context),
          //btn(context),
          btnprocess(context),
        ],
      ),
    ))));
  }

  header(BuildContext context) {
    return Container(
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Container(
            padding: EdgeInsets.only(top: 5),
            width: 100,
            height: 60,
            child: Image.asset(
              'asset/images/ipvheader.png',
            ),
          ),
          Expanded(
            child: Container(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    "ONLINE ACCOUNT OPENING E - KYC",
                    style: TextStyle(
                        color: Color(0xFF0066CC),
                        fontSize: 15,
                        fontFamily: 'century_gothic',
                        fontWeight: FontWeight.bold),
                  ),
                  Text(
                    "IPV | IN PERSONE VERIFICATION",
                    style: TextStyle(
                        color: Color(0xFFFAB804),
                        fontFamily: 'century_gothic',
                        fontSize: 15,
                        fontWeight: FontWeight.bold),
                  ),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }

  iPVDetailsForm(BuildContext context) {
    return Container(
      padding: EdgeInsets.only(left: 10, top: 50, right: 10),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.spaceAround,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Row(
            children: [
              Expanded(
                child: Container(
                  alignment: Alignment.center,
                  margin: EdgeInsets.only(top: 15),
                  child: Text(
                    "In order to continue with IPV, You must allow this webpage access to your Camera and Microphone. Kindly allow browser to access your Camera and Microphone.",
                    style: TextStyle(
                        color: Color(0xFFFAB804),
                        fontFamily: 'century_gothic',
                        fontSize: 15,
                        fontWeight: FontWeight.bold),
                  ),
                ),
              ),
            ],
          ),
          Row(
            children: [
              Expanded(
                child: Container(
                  margin: EdgeInsets.only(top: 10, left: 5, right: 5),
                  alignment: Alignment.center,
                  child: Text(
                    "Read aloud the following script while recording the video",
                    style: TextStyle(
                        color: Color(0xFF000000),
                        fontFamily: 'century_gothic',
                        fontSize: 15,
                        fontWeight: FontWeight.w600),
                  ),
                ),
              ),
            ],
          ),
          Row(
            children: [
              Expanded(
                child: Container(
                  alignment: Alignment.center,
                  margin: EdgeInsets.only(top: 15, left: 5, right: 5),
                  child: Text(
                    "MY NAME IS " + fullName + " AND MY PAN IS " + panName,
                    style: TextStyle(
                        color: Color(0xFF0066CC),
                        fontFamily: 'century_gothic',
                        fontSize: 15,
                        fontWeight: FontWeight.w800),
                  ),
                ),
              ),
            ],
          ),
          ipvVideoPlayer(),
        ],
      ),
    );
  }

  videoTimer(BuildContext context) {
    return Container(
      width: 250,
      height: 15,
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.center,
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Container(
            color: Colors.white,
            child: Text("Time : ",
                style: TextStyle(
                  color: Color(0xFF000000),
                  fontFamily: 'century_gothic',
                  fontSize: 14,
                )),
          ),
          Container(
            color: Colors.white,
            child: Text(time,
                style: TextStyle(
                  color: Color(0XFFff0000),
                  fontFamily: 'century_gothic',
                  fontSize: 14,
                )),
          ),
        ],
      ),
    );
  }

  btn(BuildContext context) {
    return Container(
      width: 285,
      margin: EdgeInsets.only(top: 20, left: 100, bottom: 4, right: 100), //
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.center,
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Expanded(
            child: Container(
              width: 125,
              height: 35,
              child: TextButton(
                child: Text(
                  "RECORD AGAIN",
                  style: TextStyle(
                      color: Color(0xFFFFFFFF),
                      fontFamily: 'century_gothic',
                      fontSize: 12,
                      fontWeight: FontWeight.w600),
                ),
                style: ButtonStyle(
                  padding:
                      MaterialStateProperty.all<EdgeInsets>(EdgeInsets.all(1)),
                  foregroundColor:
                      MaterialStateProperty.all<Color>(Colors.white),
                  shape: MaterialStateProperty.all<RoundedRectangleBorder>(
                    RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(15),
                      side: BorderSide(
                        color: Color(0xFF0074C4),
                      ),
                    ),
                  ),
                  backgroundColor: MaterialStateProperty.all<Color>(
                    Color(0xFF0074C4),
                  ),
                ),
                onPressed: () {
                  setState(() {
                    cameraViewFlag = true;
                    playerViewFlag = false;
                  });
                  // Get the listonNewCameraSelected of available cameras.
                  // Then set the first camera as selected.
                  camerainit();
                  // _onRecordButtonPressed();
                  //startWebFilePicker(context);
                },
              ),
            ),
          ),
          // Expanded(
          //   child: Container(
          //     width: 125,
          //     height: 35,
          //     margin: EdgeInsets.only(left: 25),
          //     child: TextButton(
          //       child: Text(
          //         "VIEW",
          //         style: TextStyle(
          //             color: Color(ThemeColors1.white),
          //             fontFamily: 'century_gothic',
          //             fontSize: 12,
          //             fontWeight: FontWeight.w600),
          //       ),
          //       style: ButtonStyle(
          //         padding:
          //             MaterialStateProperty.all<EdgeInsets>(EdgeInsets.all(1)),
          //         foregroundColor:
          //             MaterialStateProperty.all<Color>(Colors.white),
          //         shape: MaterialStateProperty.all<RoundedRectangleBorder>(
          //             RoundedRectangleBorder(
          //                 borderRadius: BorderRadius.circular(15),
          //                 side: BorderSide(
          //                     color:
          //                         Color(ThemeColors1.kButtonBackgroundColor)))),
          //         backgroundColor: MaterialStateProperty.all<Color>(
          //             Color(ThemeColors1.kButtonBackgroundColor)),
          //       ),
          //       onPressed: () => _showVideo(),
          //     ),
          //   ),
          // ),
        ],
      ),
    );
  }

  btnprocess(BuildContext context) {
    return Container(
      child: Row(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Container(
            alignment: Alignment.centerRight,
            margin: EdgeInsets.only(top: 15),
            child: Container(
              height: 45,
              width: 125,
              margin: EdgeInsets.all(20),
              padding: EdgeInsets.only(top: 4, left: 4, bottom: 4, right: 4),
              decoration: BoxDecoration(
                  border: Border.all(
                    color: Colors.blue[200],
                  ),
                  borderRadius: BorderRadius.circular(
                      20) // use instead of BorderRadius.all(Radius.circular(20))
                  ),
              child: TextButton(
                child: Text(
                  "PROCEED",
                  style: TextStyle(
                      color: Color(0xFFFFFFFF),
                      fontFamily: 'century_gothic',
                      fontSize: 12,
                      fontWeight: FontWeight.w600),
                ),
                style: ButtonStyle(
                  padding:
                      MaterialStateProperty.all<EdgeInsets>(EdgeInsets.all(1)),
                  foregroundColor:
                      MaterialStateProperty.all<Color>(Colors.white),
                  shape: MaterialStateProperty.all<RoundedRectangleBorder>(
                      RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(15),
                          side: BorderSide(color: Colors.blue[200]))),
                  backgroundColor: MaterialStateProperty.all<Color>(
                    Color(0xFF0074C4),
                  ),
                ),
                // onPressed: () =>
                //     HomeScreenLarge.screensStreamLarge.sink.add(Pandetails()),
                onPressed: () {
                  if (isvideouploadedsmall) {
                    HomeScreenSmall.screensStreamSmall.sink
                        .add(Ekycscreenamesmall.esigndetailscreen);
                  } else {
                    showAlert(context, "Please record video to Proceed");
                  }
                },
              ),
            ),
          ),
        ],
      ),
    );
  }

  startWebFilePicker(BuildContext context) async {
    result = await FilePicker.platform.pickFiles();
  }

  saveIPVVideo(BuildContext context) async {
    //  cameraController.dispose();
    print('save ipv rawvideo' + rawVideo.path);
    if (rawVideo != "" && rawVideo != null) {
      // var fileBytes = result.files.single.bytes;
      print('row video found');
      final path = rawVideo.path;
      final bytes = await rawVideo.readAsBytes();
      // List<int> _selectedFile = fileBytes;
      var getuserdetailsobj =
          await ipcBloc.saveIPVDocument(context, "small", bytes, "");
      if (getuserdetailsobj) {
        isvideouploadedsmall = true;
      } else {
        isvideouploadedsmall = false;
      }
    } else {
      // User canceled the picker
      // showAlert(context, "Please record IPV and upload");

      HomeScreenSmall.screensStreamSmall.sink
          .add(Ekycscreenamesmall.esigndetailscreen);
    }
  }

  Future<dynamic> showAlert(BuildContext ctx, String msg) {
    return showDialog(
      context: ctx,
      builder: (ctx) => AlertDialog(
        title: Text("E-KYC"),
        content: Text(msg),
        actions: <Widget>[
          FlatButton(
            onPressed: () {
              Navigator.of(ctx).pop();
            },
            child: Text("OK"),
          ),
        ],
      ),
    );
  }

  ipvVideoPlayer() {
    return Visibility(
      visible: cameraViewFlag,
      child: Container(
        child: Container(
          child: SingleChildScrollView(
            controller: _scrollController,
            scrollDirection: Axis.vertical,
            child: Column(
              children: [
                Container(
                  width: 250,
                  height: 200,
                  margin: EdgeInsets.only(top: 15, left: 20, right: 20),
                  child: Padding(
                    padding: const EdgeInsets.all(1.0),
                    child: Center(
                      child: _cameraPreviewWidget(),
                    ),
                  ),
                  decoration: BoxDecoration(
                    color: Colors.black,
                    border: Border.all(
                      color: cameraController != null &&
                              cameraController.value.isRecordingVideo
                          ? Colors.redAccent
                          : Colors.grey,
                      width: 3.0,
                    ),
                  ),
                ),
                Container(
                  width: 250,
                  height: 50,
                  child: Row(
                    crossAxisAlignment: CrossAxisAlignment.center,
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      //_cameraTogglesRowWidget(),
                      Container(
                        margin: EdgeInsets.only(top: 0),
                        child: Row(
                          crossAxisAlignment: CrossAxisAlignment.center,
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: [
                            Container(
                              width: 30,
                              height: 30,
                              child: StreamBuilder<String>(
                                  stream: recoiconstream,
                                  builder: (context, snapshot) {
                                    if (snapshot.data == "true") {
                                      return FloatingActionButton(
                                        child: new IconTheme(
                                          data: new IconThemeData(
                                              color: Colors.blueGrey[400]),
                                          child:
                                              new Icon(Icons.videocam_rounded),
                                        ),
                                        onPressed: () {
                                          if (recordbtnflag == false) {
                                            recordbtnflag = true;
                                            recoicon.sink.add("true");
                                            cameraController != null &&
                                                    cameraController
                                                        .value.isInitialized &&
                                                    !cameraController
                                                        .value.isRecordingVideo
                                                ? _onRecordButtonPressed()
                                                : null;
                                          }
                                        },
                                      );
                                    } else {
                                      return FloatingActionButton(
                                        child: new IconTheme(
                                          data: new IconThemeData(
                                              color: Colors.white),
                                          child: new Icon(
                                            Icons.videocam_rounded,
                                          ),
                                        ),
                                        onPressed: () {
                                          if (recordbtnflag == false) {
                                            recordbtnflag = true;
                                            recoicon.sink.add("true");
                                            cameraController != null &&
                                                    cameraController
                                                        .value.isInitialized &&
                                                    !cameraController
                                                        .value.isRecordingVideo
                                                ? _onRecordButtonPressed()
                                                : null;
                                          }
                                        },
                                      );
                                    }
                                  }),
                            ),
                            Container(
                              width: 15,
                            ),
                            Container(
                              width: 30,
                              height: 30,
                              child: FloatingActionButton(
                                child: new IconTheme(
                                  data: new IconThemeData(color: Colors.red),
                                  child: new Icon(
                                    Icons.stop_circle,
                                  ),
                                ),
                                onPressed: () {
                                  recordbtnflag = false;
                                  recoicon.sink.add("false");
                                  cameraController != null &&
                                          cameraController
                                              .value.isInitialized &&
                                          cameraController
                                              .value.isRecordingVideo
                                      ? _onStopButtonPressed()
                                      : null;
                                  _timer.cancel();
                                  _timer = null;
                                },
                              ),
                            ),
                          ],
                        ),
                      ),
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

  IconData _getCameraLensIcon(CameraLensDirection direction) {
    switch (direction) {
      case CameraLensDirection.back:
        return Icons.camera_rear;
      case CameraLensDirection.front:
        return Icons.camera_front;
      case CameraLensDirection.external:
        return Icons.camera;
      default:
        return Icons.device_unknown;
    }
  }

  // Display 'Loading' text when the camera is still loading.
  Widget _cameraPreviewWidget() {
    if (cameraController == null || !cameraController.value.isInitialized) {
      return const Text(
        'Loading',
        style: TextStyle(
          color: Colors.white,
          fontSize: 20.0,
          fontWeight: FontWeight.w900,
        ),
      );
    } else {
      print('loading Done show camera');
      if (mounted) {
        setState(() {
          print('set state 1');

          if (_scrollController.hasClients) {
            if (isvideoRecording == false) {
              _scrollController.jumpTo(10.0);
            }

            // _scrollController.animateTo(
            //     _scrollController.position.maxScrollExtent - 10,
            //     duration: Duration(milliseconds: 100),
            //     curve: Curves.ease);
          }
        });
      }
      if (mounted) {
        setState(() {
          print('set state 1');
        });
      }

      return Container(
        child: CameraPreview(cameraController),
      );
    }
  }

  /// Display a row of toggle to select the camera (or a message if no camera is available).
  Widget _cameraTogglesRowWidget() {
    if (cameras == null) {
      return Row();
    }

    CameraDescription selectedCamera = cameras[selectedCameraIdx];
    CameraLensDirection lensDirection = selectedCamera.lensDirection;

    return Container(
      child: Align(
        alignment: Alignment.centerLeft,
        child: FlatButton.icon(
            onPressed: _onSwitchCamera,
            icon: Icon(_getCameraLensIcon(lensDirection)),
            label: Text(
                "${lensDirection.toString().substring(lensDirection.toString().indexOf('.') + 1)}")),
      ),
    );
  }

  /// Display the control bar with buttons to record videos.

  String timestamp() => DateTime.now().millisecondsSinceEpoch.toString();

  Future<void> _onCameraSwitched(CameraDescription cameraDescription) async {
    if (cameraController != null) {
      await cameraController.dispose();
    }

    cameraController =
        CameraController(cameraDescription, ResolutionPreset.low);

    // If the controller is updated then update the UI.
    cameraController.addListener(() {
      print(mounted);
      if (mounted) {
        setState(() {
          print('set state 1');
        });
      }

      // if (controller.value.hasError) {
      //   print("CAMERA error");
      // }
    });

    try {
      await cameraController.initialize();
    } on CameraException catch (e) {
      _showCameraException(e);
    }

    // if (mounted) {
    //   setState(() {
    //     print('set state 2');
    //   });
    // }
  }

  void _onSwitchCamera() {
    selectedCameraIdx =
        selectedCameraIdx < cameras.length - 1 ? selectedCameraIdx + 1 : 0;
    CameraDescription selectedCamera = cameras[selectedCameraIdx];

    _onCameraSwitched(selectedCamera);

    setState(() {
      selectedCameraIdx = selectedCameraIdx;
    });
  }

  void _onRecordButtonPressed() {
    print("_onRecordButtonPressed");
    videoRecordTimer();
    isvideoRecording = true;
    _startVideoRecording().then((String filePath) {
      if (filePath != null) {
        print("Record video started");
      }
    });
  }

  void _onStopButtonPressed() {
    _timer.cancel();
    isvideoRecording = false;
    _stopVideoRecording().then((_) {
      if (mounted) setState(() {});
      print("video record to " + videoPath);
    });
  }

  Future<String> _startVideoRecording() async {
    if (!cameraController.value.isInitialized) {
      print("Please wait");

      return null;
    }

    // Do nothing if a recording is on progress
    if (cameraController.value.isRecordingVideo) {
      return null;
    }

    try {
      await cameraController.startVideoRecording();
    } on CameraException catch (e) {
      _showCameraException(e);
      return null;
    }
  }

  Future<void> _stopVideoRecording() async {
    if (!cameraController.value.isRecordingVideo) {
      return null;
    }

    try {
      rawVideo = await cameraController.stopVideoRecording();

      // final path = rawVideo.path;
      // final bytes = await rawVideo.readAsBytes();
      // print("Video path  " + path);
      // print("Video path bytes " + bytes.toString());
      saveIPVVideo(context);
      // Uri myUri = Uri.parse(rawVideo.path);
      // File audioFile = new File.fromUri(myUri);
      // var bytes;
      // await audioFile.readAsBytes().then((audioFile) {
      //   bytes = audioFile;
      //   print(bytes);
      //   List<int> _selectedFile = bytes;
      //   IpvScreenBlock().saveIPVDocument(context, "small", _selectedFile, "");
      // });
      // File videoFile = File(rawVideo.path);

      // int currentUnix = DateTime.now().millisecondsSinceEpoch;

      // final directory = await getExternalStorageDirectory();

      // String fileFormat = videoFile.path.split('.').last;

      // File _videoFile = await videoFile.copy(
      //   '${directory.path}/$currentUnix.$fileFormat',
      // );
      // // var fileBytes = _videoFile.path;

      // // var _selectedFile = fileBytes;
      // List<int> list = new List<
      //     int>(); // blank object send to handle video upoad in repository class
      // IpvScreenBlock().saveIPVDocument(context, "large", bytes, "");
    } on CameraException catch (e) {
      print('camera exception' + e.description);
      _showCameraException(e);
      return null;
    }
  }

  void _showCameraException(CameraException e) {
    String errorText = 'Error: ${e.code}\nError Message: ${e.description}';
    print(errorText);

    print("CAMERA error IOS" + errorText);
  }

  // ignore: unused_element
  _showVideo() {
    if (globalRespObj.response.data.message[0].ipv != "" &&
        globalRespObj.response.data.message[0].ipv != null) {
      // html.window.open(url, "_blank");
      newWindow.openNewTab(url);
    } else {
      // User canceled the picker
      showAlert(context, "Please record IPV and upload");
    }
  }

  videoRecordTimer() {
    if (_timer != null) {
      _timer = null;
    }
    _start = 10;
    time = "00m : 00s";
    const oneSec = const Duration(seconds: 1);
    _timer = new Timer.periodic(
      oneSec,
      (Timer timer) {
        if (_start == 0) {
          setState(() {
            timer.cancel();
            time = "00m : 00s";
            _stopVideoRecording();
          });
        } else {
          setState(() {
            _start--;
            print(_start);

            time = "00m : 0" + _start.toString() + "s";
          });
        }
      },
    );
  }
}
