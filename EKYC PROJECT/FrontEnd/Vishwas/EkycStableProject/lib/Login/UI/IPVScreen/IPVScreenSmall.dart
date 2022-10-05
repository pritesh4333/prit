// ignore_for_file: unused_import

import 'dart:async';
import 'dart:convert';
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
import 'package:e_kyc/Utilities/Location/location.dart';

import 'dart:ui' as ui;
import 'dart:html' as html;
import 'dart:js' as js;
import 'package:http/http.dart' as http;
import 'package:web_browser_detect/web_browser_detect.dart';
import 'package:e_kyc/Login/UI/IPVScreen/platformspec/mobiledevice.dart' if (dart.library.html) "package:e_kyc/Login/UI/IPVScreen/platformspec/webdevice.dart" as newWindow;

class IPVScreenSmall extends StatefulWidget {
  @override
  _IPVScreenLargeState createState() => _IPVScreenLargeState();
}

class _IPVScreenLargeState extends State<IPVScreenSmall> {
  Timer _timer;
  int _start = null;
  String time = "00m : 00s";
  IpvScreenBlock ipcBloc = new IpvScreenBlock();
  var globalRespObj = LoginRepository.loginDetailsResObjGlobal;
  Uint8List fileBytes;
  FilePickerResult result;
  var validfullname = "", validemail = "", validmobile = "", validclientcode = "";
  List<int> _selectedFile;
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
  static var ipvisvideouploadedsmall = false;

  String url;
  static html.VideoElement _webcamVideoElement = html.VideoElement();
  html.MediaStream streamHandle;
  html.MediaRecorder _mediaRecorder;
  final browser = Browser();
  @override
  void dispose() {
    // Ensure disposing of the VideoPlayerController to free up resources.

    print("CAMERA dispose ");
    super.dispose();
  }

  @override
  void initState() {
    super.initState();
    HomeScreenSmall.percentageFlagSmall.add("0.905");

    _controller = null;

    if (globalRespObj != null) {
      print('Data already is in global object');
      if (globalRespObj.response.errorCode == "0") {
        videoPath = globalRespObj.response.data.message[0].ipv;
        panName = globalRespObj.response.data.message[0].pan;
        fullName = globalRespObj.response.data.message[0].fullName;

        if (videoPath != "") {
          print("INTI method call ");
          playerViewFlag = true;
          url = AppConfig().url.toString() + 'images/' + videoPath.toString() + '.mp4';
        } else {}
      }
    } else {
      panName = PanDetailRepository.userPanNumber;
      fullName = LoginRepository.loginFullName;
      // Get the listonNewCameraSelected of available cameras.
      // Then set the first camera as selected.

    }

    if (EsignRepository.timerObj != null) {
      EsignRepository.timerObj.cancel();
      EsignRepository.timerObj = null;
    }
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
          // videoTimer(context),
          // btn(context),
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
                    style: TextStyle(color: Color(0xFF0066CC), fontSize: 15, fontFamily: 'century_gothic', fontWeight: FontWeight.bold),
                  ),
                  Text(
                    "IPV | IN PERSONE VERIFICATION",
                    style: TextStyle(color: Color(0xFFFAB804), fontFamily: 'century_gothic', fontSize: 15, fontWeight: FontWeight.w300),
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
                    style: TextStyle(color: Color(0xFFFAB804), fontFamily: 'century_gothic', fontSize: 15, fontWeight: FontWeight.w300),
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
                    style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 15, fontWeight: FontWeight.w600),
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
                    style: TextStyle(color: Color(0xFF0066CC), fontFamily: 'century_gothic', fontSize: 15, fontWeight: FontWeight.w800),
                  ),
                ),
              ),
            ],
          ),
          ipvVideoPlayer(context),
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
            child: Text("Time : ",
                style: TextStyle(
                  color: Color(0xFF000000),
                  fontFamily: 'century_gothic',
                  fontSize: 14,
                )),
          ),
          Container(
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
                  style: TextStyle(color: Color(0xFFFFFFFF), fontFamily: 'century_gothic', fontSize: 12, fontWeight: FontWeight.w600),
                ),
                style: ButtonStyle(
                  padding: MaterialStateProperty.all<EdgeInsets>(EdgeInsets.all(1)),
                  foregroundColor: MaterialStateProperty.all<Color>(Colors.white),
                  shape: MaterialStateProperty.all<RoundedRectangleBorder>(RoundedRectangleBorder(borderRadius: BorderRadius.circular(15), side: BorderSide(color: Color(0xFF0074C4)))),
                  backgroundColor: MaterialStateProperty.all<Color>(Color(0xFF0074C4)),
                ),
                onPressed: () {
                  setState(() {
                    cameraViewFlag = true;
                    playerViewFlag = false;
                  });
                  // Get the listonNewCameraSelected of available cameras.
                  // Then set the first camera as selected.
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
          //             color: Color(0xFFFFFFFF),
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
          //                         Color(0xFF0074C4)))),
          //         backgroundColor: MaterialStateProperty.all<Color>(
          //             Color(0xFF0074C4)),
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
                  borderRadius: BorderRadius.circular(20) // use instead of BorderRadius.all(Radius.circular(20))
                  ),
              child: TextButton(
                child: Text(
                  "PROCEED",
                  style: TextStyle(color: Color(0xFFFFFFFF), fontFamily: 'century_gothic', fontSize: 12, fontWeight: FontWeight.w600),
                ),
                style: ButtonStyle(
                  padding: MaterialStateProperty.all<EdgeInsets>(EdgeInsets.all(1)),
                  foregroundColor: MaterialStateProperty.all<Color>(Colors.white),
                  shape: MaterialStateProperty.all<RoundedRectangleBorder>(RoundedRectangleBorder(borderRadius: BorderRadius.circular(15), side: BorderSide(color: Colors.blue[200]))),
                  backgroundColor: MaterialStateProperty.all<Color>(Color(0xFF0074C4)),
                ),
                // onPressed: () =>
                //     HomeScreenLarge.screensStreamLarge.sink.add(Pandetails()),
                onPressed: () {
                  // if (isvideoRecording) {
                  //   showAlert(
                  //       context, "Please stop video recording to proceed");
                  // } else {
                  if (ipvisvideouploadedsmall) {
                    HomeScreenSmall.screensStreamSmall.sink.add(Ekycscreenamesmall.esigndetailscreen);
                  } else {
                    showAlert(context, "Please record video to Proceed");
                  }
                  // }
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

  static Future<void> saveIPVVideo(BuildContext context, Uint8List bytes) async {
    IpvScreenBlock ipcBloc = new IpvScreenBlock();
    var getuserdetailsobj = await ipcBloc.saveIPVDocument(context, "small", bytes, "");
    if (getuserdetailsobj) {
      ipvisvideouploadedsmall = true;
    } else {
      ipvisvideouploadedsmall = false;
    }
  }
}

showAlert(BuildContext ctx, String msg) {
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

ipvVideoPlayer(BuildContext context) {
  return Container(
    child: Column(
      children: [
        Container(
          margin: EdgeInsets.only(top: 15),
          width: 300,
          height: 390,
          child: WebCam(),
        ),
      ],
    ),
  );
}

class WebCam extends StatefulWidget {
  @override
  _WebCamState createState() => _WebCamState();
}

class _WebCamState extends State<WebCam> {
  static BuildContext _videoContext;
  static html.VideoElement _webcamVideoElement = html.VideoElement();
  html.MediaStream streamHandle;
  static html.MediaRecorder _mediaRecorder;
  final browser = Browser();
  final GlobalKey<ScaffoldState> _scaffoldKey = GlobalKey<ScaffoldState>();
  Timer _timer;
  bool recordbtnflag = false;
  int _start = null;
  String time = "00m : 00s";

  var timer = "00m : 00s";
  bool isPressed = true;
  String pathFile;
  final BehaviorSubject<String> timersubject = BehaviorSubject<String>();
  Stream<String> get timerstream => this.timersubject.stream;

  final BehaviorSubject<String> recoicon = BehaviorSubject<String>();
  Stream<String> get recoiconstream => this.recoicon.stream;

  @override
  void initState() {
    super.initState();
    print("i am inti ipv SCREEN SMALL");
    // Register a webcam
    // ignore: undefined_prefixed_name
    ui.platformViewRegistry.registerViewFactory('webcamVideoElement', (int viewId) {
      getMedia();
      return _webcamVideoElement;
    });
  }

  getMedia() {
    html.window.navigator.mediaDevices?.getUserMedia({"video": true, "audio": true}).then(
      (streamHandle) {
        _webcamVideoElement
          ..srcObject = streamHandle
          ..autoplay = true;
        _webcamVideoElement.volume;
        print('${browser.browser} ${browser.version}');
        String browserType = browser.browser;
        String mimeType = '';
        if (browserType.toLowerCase() == "chrome") {
          mimeType = 'video/webm';
        } else if (browserType.toLowerCase() == 'safari') {
          mimeType = 'video/mp4';
        } else {
          mimeType = 'video/webm';
        }
        _mediaRecorder = new html.MediaRecorder(streamHandle, {'mimeType': mimeType});

        _mediaRecorder.addEventListener(
          'dataavailable',
          (html.Event event) async {
            final chunks = <html.Blob>[];
            Completer<String> _completer = Completer<String>();
            final html.Blob blob = js.JsObject.fromBrowserObject(event)['data'];

            if (blob.size > 0) {
              chunks.add(blob);
            }

            print('chunks :: ${chunks}');
// send this project on mial
            if (_mediaRecorder.state == 'inactive') {
              final blob = html.Blob(chunks, mimeType);
              _completer.complete(html.Url.createObjectUrlFromBlob(blob));

              pathFile = await _completer.future;
              final imgBase64Str = await networkImageToBase64(pathFile);
              Uint8List bytes = base64.decode(imgBase64Str);
              // print(bytes);
              if (validateLocationDetails()) {
                _IPVScreenLargeState.saveIPVVideo(_videoContext, bytes);
              }

              print('url of video :: ${pathFile}');
              //switchCameraOff();
              //  showAlertMessage(_videoContext, "video done");

              // html.window.open(pathFile, '');
            }
          },
        );
      },
    ).catchError(
      (onError) {
        print('camera error samll');
        print(onError);
      },
    );
  }

  Future<String> networkImageToBase64(String imageUrl) async {
    Uri myUri = Uri.parse(imageUrl);
    http.Response response = await http.get(myUri);
    final bytes = response?.bodyBytes;
    return (bytes != null ? base64Encode(bytes) : null);
  }

  switchCameraOff() {
    bool val = _webcamVideoElement.srcObject?.active;
    if (_webcamVideoElement.srcObject != null && val) {
      var tracks = _webcamVideoElement.srcObject?.getTracks();

      //stopping tracks and setting srcObject to null to switch camera off
      _webcamVideoElement.srcObject = null;

      tracks?.forEach((track) {
        track.stop();
      });
    }
  }

  @override
  void dispose() {
    super.dispose();
    print("i am dispose");
    // _mediaRecorder.stop();
    // _mediaRecorder = null;
    switchCameraOff();
  }

  @override
  Widget build(BuildContext context) {
    _videoContext = context;
    return Scaffold(
      body: Stack(
        children: [
          Container(
            child: HtmlElementView(
              key: _scaffoldKey,
              viewType: 'webcamVideoElement',
            ),
          ),
          Container(
            margin: EdgeInsets.only(top: 290),
            child: Row(
              crossAxisAlignment: CrossAxisAlignment.center,
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Container(
                  width: 40,
                  height: 40,
                  child: StreamBuilder<String>(
                      stream: recoiconstream,
                      builder: (context, snapshot) {
                        if (snapshot.data == "true") {
                          return FloatingActionButton(
                            child: new IconTheme(
                              data: new IconThemeData(color: Colors.blueGrey[400]),
                              child: new Icon(Icons.videocam_rounded),
                            ),
                            onPressed: () {
                              if (recordbtnflag == false) {
                                recordbtnflag = true;
                                recoicon.sink.add("true");
                                videoRecordTimer();
                                _mediaRecorder.start();
                              }
                            },
                          );
                        } else {
                          return FloatingActionButton(
                            child: new IconTheme(
                              data: new IconThemeData(color: Colors.white),
                              child: new Icon(
                                Icons.videocam_rounded,
                              ),
                            ),
                            onPressed: () {
                              if (recordbtnflag == false) {
                                recordbtnflag = true;
                                recoicon.sink.add("true");
                                videoRecordTimer();
                                _mediaRecorder.start();
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
                  width: 40,
                  height: 40,
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
                      _mediaRecorder.stop();
                      switchCameraOff();
                      _timer.cancel();
                      _timer = null;
                    },
                  ),
                ),
              ],
            ),
          ),
          Container(
            width: 300,
            height: 15,
            margin: EdgeInsets.only(top: 370),
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
                  child: StreamBuilder<String>(
                      stream: timerstream,
                      builder: (context, snapshot) {
                        if (true) {}
                        return Text(snapshot.data ?? '00m : 00s',
                            style: TextStyle(
                              color: Color(0XFFff0000),
                              fontFamily: 'century_gothic',
                              fontSize: 14,
                            ));
                      }),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }

  videoRecordTimer() {
    if (_timer != null) {
      _timer.cancel();
      _timer = null;
    }
    _start = 10;
    time = "00m : 00s";
    const oneSec = const Duration(seconds: 1);
    _timer = new Timer.periodic(
      oneSec,
      (Timer timer) {
        if (_start == 0) {
          timersubject.sink.add("00m : 00s");
          timer.cancel();
          _mediaRecorder.stop();
        } else {
          _start--;
          print(_start);

          time = "00m : 0" + _start.toString() + "s";

          timersubject.sink.add(time);
        }
      },
    );
  }

  void showAlertMessage(BuildContext ctxx, String s) {
    showDialog(
      context: ctxx,
      builder: (ctx) => AlertDialog(
        title: Text("E-KYC"),
        content: Text(s),
        actions: <Widget>[
          FlatButton(
            onPressed: () async {
              Navigator.of(ctx).pop();
            },
            child: Text("OK"),
          ),
        ],
      ),
    );
  }

  void saveIPVVideo() {}
  bool validateLocationDetails() {
    String lat = LoginRepository.latitude;
    String long = LoginRepository.longitude;
    if ((lat.isNotEmpty && lat.length > 1) && (long.isNotEmpty && long.length > 1)) {
      //Got the location data now proceed further for E-Signing
      return true;
    } else {
      //Look for permission
      switch (LoginBloc.permissionGranted) {
        case PermissionStatus.granted:
          showAlert(context, 'Permission Granted');
          LoginBloc().getLocation();
          return false;
          break;
        case PermissionStatus.denied:
          showAlert(context, 'Please allow location to proceed.');
          LoginBloc().requestPermission();
          return false;
          break;
        case PermissionStatus.grantedLimited:
          showAlert(context, 'Permission Granted Limited');
          LoginBloc().requestPermission();
          return false;
          break;
        case PermissionStatus.deniedForever:
          showAlert(context, 'Please allow location service and refresh it to fetch location');
          LoginBloc().requestPermission();
          return false;
          break;
      }
      return false;
    }
  }
}
