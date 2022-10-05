import 'dart:async';

import 'package:camera/camera.dart';
import 'package:e_kyc/Login/UI/IPVScreen/bloc/IpvScreenBlock.dart';
import 'package:flutter/material.dart';

class VideoRecorderExample extends StatefulWidget {
  @override
  _VideoRecorderExampleState createState() {
    return _VideoRecorderExampleState();
  }
}

class _VideoRecorderExampleState extends State<VideoRecorderExample> {
  CameraController controller;
  String videoPath;

  List<CameraDescription> cameras;
  int selectedCameraIdx;
  ScrollController _scrollController = ScrollController();
  final GlobalKey<ScaffoldState> _scaffoldKey = GlobalKey<ScaffoldState>();

  @override
  void initState() {
    super.initState();

    // Get the listonNewCameraSelected of available cameras.
    // Then set the first camera as selected.
    availableCameras().then((availableCameras) {
      cameras = availableCameras;

      if (cameras.length > 0) {
        setState(() {
          selectedCameraIdx = 0;
        });

        _onCameraSwitched(cameras[selectedCameraIdx]).then((void v) {});
      }
    }).catchError((err) {
      print('Error: $err.code\nError Message: $err.message');
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      key: _scaffoldKey,
      appBar: AppBar(
        title: const Text('Camera example'),
      ),
      body: Container(
        child: SingleChildScrollView(
          controller: _scrollController,
          scrollDirection: Axis.vertical,
          child: Column(
            children: <Widget>[
              Container(
                height: 300,
                width: 150,
                child: Padding(
                  padding: const EdgeInsets.all(1.0),
                  child: Center(
                    child: _cameraPreviewWidget(),
                  ),
                ),
                decoration: BoxDecoration(
                  color: Colors.black,
                  border: Border.all(
                    color:
                        controller != null && controller.value.isRecordingVideo
                            ? Colors.redAccent
                            : Colors.grey,
                    width: 3.0,
                  ),
                ),
              ),
              Padding(
                padding: const EdgeInsets.all(5.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.start,
                  children: <Widget>[
                    _cameraTogglesRowWidget(),
                    _captureControlRowWidget(),
                    Expanded(
                      child: SizedBox(),
                    ),
                  ],
                ),
              ),
              Container(
                height: 45,
                width: 110,
                margin: EdgeInsets.all(20),

                padding:
                    EdgeInsets.only(top: 4, left: 4, bottom: 4, right: 4), //

                child: TextButton(
                  child: Text("CONTINUE".toUpperCase(),
                      style: TextStyle(
                          fontSize: 12, fontFamily: 'century_gothic')),
                  style: ButtonStyle(
                    padding: MaterialStateProperty.all<EdgeInsets>(
                        EdgeInsets.all(1)),
                    foregroundColor:
                        MaterialStateProperty.all<Color>(Colors.white),
                    shape: MaterialStateProperty.all<RoundedRectangleBorder>(
                        RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(15),
                            side: BorderSide(color: Colors.blue[200]))),
                    backgroundColor:
                        MaterialStateProperty.all<Color>(Color(0xFF0074C4)),
                  ),
                  onPressed: () => opencamera(),
                ),
              ),
            ],
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
    if (controller == null || !controller.value.isInitialized) {
      print('loading');
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
          _scrollController.animateTo(
              _scrollController.position.maxScrollExtent,
              duration: Duration(milliseconds: 100),
              curve: Curves.ease);
        });
      }
      if (mounted) {
        setState(() {
          print('set state 1');
        });
      }

      return Container(
        child: CameraPreview(controller),
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

    return Expanded(
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
  Widget _captureControlRowWidget() {
    return Expanded(
      child: Align(
        alignment: Alignment.center,
        child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          mainAxisSize: MainAxisSize.max,
          children: <Widget>[
            IconButton(
              icon: const Icon(Icons.videocam),
              color: Colors.blue,
              onPressed: controller != null &&
                      controller.value.isInitialized &&
                      !controller.value.isRecordingVideo
                  ? _onRecordButtonPressed
                  : null,
            ),
            IconButton(
              icon: const Icon(Icons.stop),
              color: Colors.red,
              onPressed: controller != null &&
                      controller.value.isInitialized &&
                      controller.value.isRecordingVideo
                  ? _onStopButtonPressed
                  : null,
            ),
          ],
        ),
      ),
    );
  }

  String timestamp() => DateTime.now().millisecondsSinceEpoch.toString();

  Future<void> _onCameraSwitched(CameraDescription cameraDescription) async {
    if (controller != null) {
      await controller.dispose();
    }

    controller = CameraController(cameraDescription, ResolutionPreset.low);

    // If the controller is updated then update the UI.
    controller.addListener(() {
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
      await controller.initialize();
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
    print('Rescord press');
    _startVideoRecording().then((String filePath) {
      if (filePath != null) {
        print("Record video started");
      }
    });
  }

  void _onStopButtonPressed() {
    print('Stop press');
    _stopVideoRecording().then((_) {
      if (mounted) setState(() {});
      print("video record to " + videoPath);
    });
  }

  Future<String> _startVideoRecording() async {
    if (!controller.value.isInitialized) {
      print("Please wait");

      return null;
    }

    // Do nothing if a recording is on progress
    if (controller.value.isRecordingVideo) {
      return null;
    }

    // final Directory appDirectory = await getApplicationDocumentsDirectory();
    // final String videoDirectory = '${appDirectory.path}/Videos';
    // await Directory(videoDirectory).create(recursive: true);
    // final String currentTime = DateTime.now().millisecondsSinceEpoch.toString();
    final String filePath = 'D:/Images/sample.mp4';

    try {
      await controller.startVideoRecording();
      videoPath = filePath;
    } on CameraException catch (e) {
      _showCameraException(e);
      return null;
    }
  }

  Future<void> _stopVideoRecording() async {
    if (!controller.value.isRecordingVideo) {
      return null;
    }

    try {
      XFile rawVideo = await controller.stopVideoRecording();
      // ignore: unused_local_variable
      final path = rawVideo.path;
      final bytes = await rawVideo.readAsBytes();

      // print("Video path  " + bytes.toString());
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
      // ignore: unused_local_variable
      List<int> list = <
          int>[]; // blank object send to handle video upoad in repository class
      IpvScreenBlock().saveIPVDocument(context, "large", bytes, "");
    } on CameraException catch (e) {
      _showCameraException(e);
      return null;
    }
  }

  void _showCameraException(CameraException e) {
    String errorText = 'Error: ${e.code}\nError Message: ${e.description}';
    print(errorText);

    print("CAMERA error" + errorText);
  }

  opencamera() {
    setState(() {
      print('camera open set state 1');
    });
  }
}

class VideoRecorderApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: VideoRecorderExample(),
    );
  }
}

Future<void> main() async {
  runApp(VideoRecorderApp());
}
