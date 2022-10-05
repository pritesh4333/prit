// import 'dart:convert';
// import 'dart:html';
// import 'dart:io';
// import 'dart:typed_data';
// import 'dart:html' as html;
// import 'dart:typed_data';
// import 'dart:async';

// import 'package:http_parser/http_parser.dart';
// import 'package:e_kyc/Login/UI/Configuration/AppConfig.dart';
// import 'package:e_kyc/Login/UI/HomeScreen/HomeScreenUI.dart';
// import 'package:e_kyc/Login/UI/Login/View/LoginScreenSmall.dart';
// import 'package:e_kyc/Login/UI/Login/View/LoginUI.dart';
// import 'package:flutter/material.dart';
// import 'package:image_picker/image_picker.dart';
// import 'package:location/location.dart';
// import "package:http/http.dart" as http;
import 'dart:io';

import 'package:e_kyc/Login/UI/Login/View/LoginUI.dart';
import 'package:flutter/material.dart';

void main() {
  //// this class for handshake exception error solved in mmobile https url was not working after adding this work fine
  ///https://stackoverflow.com/questions/54285172/how-to-solve-flutter-certificate-verify-failed-error-while-performing-a-post-req
  HttpOverrides.global = new MyHttpOverrides();
  //// this class for handshake exception error solved in mmobile https url was not working after adding this work fine
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'E-KYC',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      // home: MyHomePage(title: 'Flutter Demo Home Page'),

      home: LoginUI(),
      // home: CameraExampleHome(),
      // home: ClickEvent(),
      // home: LandingScreen(),
    );
  }
}

//// this class for handshake exception error solved in mmobile https url was not working after adding this work fine
///https://stackoverflow.com/questions/54285172/how-to-solve-flutter-certificate-verify-failed-error-while-performing-a-post-req
class MyHttpOverrides extends HttpOverrides {
  @override
  HttpClient createHttpClient(SecurityContext context) {
    return super.createHttpClient(context)
      ..badCertificateCallback =
          (X509Certificate cert, String host, int port) => true;
  }
}
//// this class for handshake exception error solved in mmobile https url was not working after adding this work fine

// class MyHomePage extends StatefulWidget {
// //  MyHomePage({Key key, this.title}) : super(key: key);

//   //final String title;

//   @override
//   _FileUploadAppState createState() => _FileUploadAppState();
// }

//// image upload

// class _FileUploadAppState extends State<MyHomePage> {
//   List<int> _selectedFile;
//   Uint8List _bytesData;
//    Image fromPicker;
//   GlobalKey<FormState> _formKey = new GlobalKey<FormState>();

//   startWebFilePicker() async {
//     html.InputElement uploadInput = html.FileUploadInputElement();
//     uploadInput.multiple = true;
//     uploadInput.draggable = true;
//     uploadInput.click();

//     uploadInput.onChange.listen((e) {
//       final files = uploadInput.files;
//       final file = files[0];
//       final reader = new html.FileReader();

//       reader.onLoadEnd.listen((e) {
//         _handleResult(reader.result);
//       });
//       reader.readAsDataUrl(file);
//     });
//   }

//   void _handleResult(Object result) {
//     setState(() {
//       _bytesData = Base64Decoder().convert(result.toString().split(",").last);
//       _selectedFile = _bytesData;
//     });
//   }

//   Future<String> makeRequest() async {
//     var url = Uri.parse(
//         "http://192.168.23.10/upload_api/web/app_dev.php/api/save-file/");
//     var request = new http.MultipartRequest("POST", url);
//     // request.files.add(await http.MultipartFile.fromBytes('file', _selectedFile,
//     //     contentType: new MediaType('application', 'octet-stream'),
//     //     filename: "file_up"));
    

//     request.send().then((response) {
//       print("test");
//       print(response.statusCode);
//       if (response.statusCode == 200) print("Uploaded!");
//     });

    // showDialog(
    //     barrierDismissible: false,
    //     context: context,
    //     child: new AlertDialog(
    //       title: new Text("Details"),
    //       //content: new Text("Hello World"),
    //       content: new SingleChildScrollView(
    //         child: new ListBody(
    //           children: <Widget>[
    //             new Text("Upload successfull"),
    //           ],
    //         ),
    //       ),
    //       actions: <Widget>[
    //         new FlatButton(
    //           child: new Text('Aceptar'),
    //           onPressed: () {
    //             // Navigator.pushAndRemoveUntil(
    //             //   context,
    //             //   MaterialPageRoute(builder: (context) => UploadApp()),
    //             //   (Route<dynamic> route) => false,
    //             // );
    //           },
    //         ),
    //       ],
    //     ));
  // }

//   @override
//   Widget build(BuildContext context) {
//     // TODO: implement build

//     return SafeArea(
//       child: Scaffold(
//         appBar: AppBar(
//           title: Text('A Flutter Web file picker'),
//         ),
//         body: Container(
//           child: new Form(
//             autovalidate: true,
//             key: _formKey,
//             child: Padding(
//               padding: const EdgeInsets.only(top: 16.0, left: 28),
//               child: new Container(
//                   width: 350,
//                   child: Column(children: <Widget>[
//                     Column(
//                         crossAxisAlignment: CrossAxisAlignment.start,
//                         children: <Widget>[
//                           MaterialButton(
//                             color: Colors.pink,
//                             elevation: 8,
//                             highlightElevation: 2,
//                             shape: RoundedRectangleBorder(
//                                 borderRadius: BorderRadius.circular(8)),
//                             textColor: Colors.white,
//                             child: Text('Select a file'),
//                             onPressed: () async {
//                               // startWebFilePicker();
//                                 fromPicker = await ImagePickerWeb.getImage(
//                                   outputType: ImageType.widget);
//                               print(
//                                   "from picker " + fromPicker.image.toString());
//                             },
//                           ),
//                           Divider(
//                             color: Colors.teal,
//                           ),
//                           RaisedButton(
//                             color: Colors.purple,
//                             elevation: 8.0,
//                             textColor: Colors.white,
//                             onPressed: () {
//                               makeRequest();
//                             },
//                             child: Text('Send file to server'),
//                           ),
//                         ])
//                   ])),
//             ),
//           ),
//         ),
//       ),
//     );
//   }
// }
//// image upload

// class _HomePageState extends State<MyHomePage> {
//   Location location = Location();

//   var lat, long;

//   @override
//   void initState() {
//     super.initState();
//     location.onLocationChanged.listen((value) {
//       LocationData data = value;
//       setState(() {
//         lat = data.latitude;
//         long = data.longitude;
//         print("location Url :- https://maps.google.com/?q=$lat,$long");
//       });
//       // _getLocation(); //WEB NOT WORKING MOBILE WORKING FINE
//     });
//   }

//   // _getLocation() async {
//   //   final coordinates = new Coordinates(lat, long);
//   //   var addresses =
//   //       await Geocoder.local.findAddressesFromCoordinates(coordinates);
//   //   var first = addresses.first;
//   //   print(
//   //       ' ${first.locality}, ${first.adminArea},${first.subLocality}, ${first.subAdminArea},${first.addressLine}, ${first.featureName},${first.thoroughfare}, ${first.subThoroughfare}');
//   // }

//   @override
//   Widget build(BuildContext context) {
//     return Scaffold(
//       appBar: AppBar(),
//       body: Column(
//         children: <Widget>[
//           lat == null && long == null
//               ? CircularProgressIndicator()
//               : Text("Location:" + lat.toString() + "     " + long.toString()),
//           Text("https://maps.google.com/?q=$lat,$long"),
//           // RaisedButton(
//           //   textColor: Colors.white,
//           //   color: Colors.blue,
//           //   child: Text('Login Screen'),
//           //   onPressed: loginscreen,
//           // )
//         ],
//       ),
//     );
//   }

//   void loginscreen() {
//     Navigator.push(
//       context,
//       MaterialPageRoute(builder: (context) => LoginUI()),
//     );
//   }
// }

// import 'dart:async';
// import 'dart:io';

// import 'package:flutter/foundation.dart';
// import 'package:flutter/material.dart';
// import 'package:image_picker/image_picker.dart';
// import 'package:video_player/video_player.dart';

// void main() {
//   runApp(MyApp());
// }

// class MyApp extends StatelessWidget {
//   @override
//   Widget build(BuildContext context) {
//     return MaterialApp(
//       title: 'Image Picker Demo',
//       home: MyHomePage(title: 'Image Picker Example'),
//     );
//   }
// }

// class MyHomePage extends StatefulWidget {
//   MyHomePage({key, this.title}) : super(key: key);

//   final String title;

//   @override
//   _MyHomePageState createState() => _MyHomePageState();
// }

// class _MyHomePageState extends State<MyHomePage> {
//   List<XFile> _imageFileList;

//   set _imageFile(XFile value) {
//     _imageFileList = value == null ? null : [value];
//   }

//   dynamic _pickImageError;
//   bool isVideo = false;

//   VideoPlayerController _controller;
//   VideoPlayerController _toBeDisposed;
//   String _retrieveDataError;

//   final ImagePicker _picker = ImagePicker();
//   final TextEditingController maxWidthController = TextEditingController();
//   final TextEditingController maxHeightController = TextEditingController();
//   final TextEditingController qualityController = TextEditingController();

//   Future<void> _playVideo(XFile file) async {
//     if (file != null && mounted) {
//       await _disposeVideoController();
//         VideoPlayerController controller;
//       if (kIsWeb) {
//         controller = VideoPlayerController.network(file.path);
//       } else {
//         controller = VideoPlayerController.file(File(file.path));
//       }
//       _controller = controller;
//       // In web, most browsers won't honor a programmatic call to .play
//       // if the video has a sound track (and is not muted).
//       // Mute the video so it auto-plays in web!
//       // This is not needed if the call to .play is the result of user
//       // interaction (clicking on a "play" button, for example).
//       final double volume = kIsWeb ? 0.0 : 1.0;
//       await controller.setVolume(volume);
//       await controller.initialize();
//       await controller.setLooping(true);
//       await controller.play();
//       setState(() {});
//     }
//   }

//   void _onImageButtonPressed(ImageSource source,
//       {BuildContext context, bool isMultiImage = false}) async {
//     if (_controller != null) {
//       await _controller.setVolume(0.0);
//     }
//     if (isVideo) {
//       final XFile file = await _picker.pickVideo(
//           source: source, maxDuration: const Duration(seconds: 10));
//       await _playVideo(file);
//     } else if (isMultiImage) {
//       await _displayPickImageDialog(context,
//           (double maxWidth, double maxHeight, int quality) async {
//         try {
//           final pickedFileList = await _picker.pickMultiImage(
//             maxWidth: maxWidth,
//             maxHeight: maxHeight,
//             imageQuality: quality,
//           );
//           setState(() {
//             _imageFileList = pickedFileList;
//           });
//         } catch (e) {
//           setState(() {
//             _pickImageError = e;
//           });
//         }
//       });
//     } else {
//       await _displayPickImageDialog(context,
//           (double maxWidth, double maxHeight, int quality) async {
//         try {
//           final pickedFile = await _picker.pickImage(
//             source: source,
//             maxWidth: maxWidth,
//             maxHeight: maxHeight,
//             imageQuality: quality,
//           );
//           setState(() {
//             _imageFile = pickedFile;
//           });
//         } catch (e) {
//           setState(() {
//             _pickImageError = e;
//           });
//         }
//       });
//     }
//   }

//   @override
//   void deactivate() {
//     if (_controller != null) {
//       _controller.setVolume(0.0);
//       _controller.pause();
//     }
//     super.deactivate();
//   }

//   @override
//   void dispose() {
//     _disposeVideoController();
//     maxWidthController.dispose();
//     maxHeightController.dispose();
//     qualityController.dispose();
//     super.dispose();
//   }

//   Future<void> _disposeVideoController() async {
//     if (_toBeDisposed != null) {
//       await _toBeDisposed.dispose();
//     }
//     _toBeDisposed = _controller;
//     _controller = null;
//   }

//   Widget _previewVideo() {
//     final Text retrieveError = _getRetrieveErrorWidget();
//     if (retrieveError != null) {
//       return retrieveError;
//     }
//     if (_controller == null) {
//       return const Text(
//         'You have not yet picked a video',
//         textAlign: TextAlign.center,
//       );
//     }
//     return Padding(
//       padding: const EdgeInsets.all(10.0),
//       child: AspectRatioVideo(_controller),
//     );
//   }

//   Widget _previewImages() {
//     final Text retrieveError = _getRetrieveErrorWidget();
//     if (retrieveError != null) {
//       return retrieveError;
//     }
//     if (_imageFileList != null) {
//       return Semantics(
//           child: ListView.builder(
//             key: UniqueKey(),
//             itemBuilder: (context, index) {
//               // Why network for web?
//               // See https://pub.dev/packages/image_picker#getting-ready-for-the-web-platform
//               return Semantics(
//                 label: 'image_picker_example_picked_image',
//                 child: kIsWeb
//                     ? Image.network(_imageFileList[index].path)
//                     : Image.file(File(_imageFileList[index].path)),
//               );
//             },
//             itemCount: _imageFileList.length,
//           ),
//           label: 'image_picker_example_picked_images');
//     } else if (_pickImageError != null) {
//       return Text(
//         'Pick image error: $_pickImageError',
//         textAlign: TextAlign.center,
//       );
//     } else {
//       return const Text(
//         'You have not yet picked an image.',
//         textAlign: TextAlign.center,
//       );
//     }
//   }

//   Widget _handlePreview() {
//     if (isVideo) {
//       return _previewVideo();
//     } else {
//       return _previewImages();
//     }
//   }

//   Future<void> retrieveLostData() async {
//     final LostDataResponse response = await _picker.retrieveLostData();
//     if (response.isEmpty) {
//       return;
//     }
//     if (response.file != null) {
//       if (response.type == RetrieveType.video) {
//         isVideo = true;
//         await _playVideo(response.file);
//       } else {
//         isVideo = false;
//         setState(() {
//           _imageFile = response.file;
//           _imageFileList = response.files;
//         });
//       }
//     } else {
//       _retrieveDataError = response.exception.code;
//     }
//   }

//   @override
//   Widget build(BuildContext context) {
//     return Scaffold(
//       appBar: AppBar(
//         title: Text(widget.title),
//       ),
//       body: Center(
//         child: !kIsWeb && defaultTargetPlatform == TargetPlatform.android
//             ? FutureBuilder<void>(
//                 future: retrieveLostData(),
//                 builder: (BuildContext context, AsyncSnapshot<void> snapshot) {
//                   switch (snapshot.connectionState) {
//                     case ConnectionState.none:
//                     case ConnectionState.waiting:
//                       return const Text(
//                         'You have not yet picked an image.',
//                         textAlign: TextAlign.center,
//                       );
//                     case ConnectionState.done:
//                       return _handlePreview();
//                     default:
//                       if (snapshot.hasError) {
//                         return Text(
//                           'Pick image/video error: ${snapshot.error}}',
//                           textAlign: TextAlign.center,
//                         );
//                       } else {
//                         return const Text(
//                           'You have not yet picked an image.',
//                           textAlign: TextAlign.center,
//                         );
//                       }
//                   }
//                 },
//               )
//             : _handlePreview(),
//       ),
//       floatingActionButton: Column(
//         mainAxisAlignment: MainAxisAlignment.end,
//         children: <Widget>[
//           Semantics(
//             label: 'image_picker_example_from_gallery',
//             child: FloatingActionButton(
//               onPressed: () {
//                 isVideo = false;
//                 _onImageButtonPressed(ImageSource.gallery, context: context);
//               },
//               heroTag: 'image0',
//               tooltip: 'Pick Image from gallery',
//               child: const Icon(Icons.photo),
//             ),
//           ),
//           Padding(
//             padding: const EdgeInsets.only(top: 16.0),
//             child: FloatingActionButton(
//               onPressed: () {
//                 isVideo = false;
//                 _onImageButtonPressed(
//                   ImageSource.gallery,
//                   context: context,
//                   isMultiImage: true,
//                 );
//               },
//               heroTag: 'image1',
//               tooltip: 'Pick Multiple Image from gallery',
//               child: const Icon(Icons.photo_library),
//             ),
//           ),
//           Padding(
//             padding: const EdgeInsets.only(top: 16.0),
//             child: FloatingActionButton(
//               onPressed: () {
//                 isVideo = false;
//                 _onImageButtonPressed(ImageSource.camera, context: context);
//               },
//               heroTag: 'image2',
//               tooltip: 'Take a Photo',
//               child: const Icon(Icons.camera_alt),
//             ),
//           ),
//           Padding(
//             padding: const EdgeInsets.only(top: 16.0),
//             child: FloatingActionButton(
//               backgroundColor: Colors.red,
//               onPressed: () {
//                 isVideo = true;
//                 _onImageButtonPressed(ImageSource.gallery);
//               },
//               heroTag: 'video0',
//               tooltip: 'Pick Video from gallery',
//               child: const Icon(Icons.video_library),
//             ),
//           ),
//           Padding(
//             padding: const EdgeInsets.only(top: 16.0),
//             child: FloatingActionButton(
//               backgroundColor: Colors.red,
//               onPressed: () {
//                 isVideo = true;
//                 _onImageButtonPressed(ImageSource.camera);
//               },
//               heroTag: 'video1',
//               tooltip: 'Take a Video',
//               child: const Icon(Icons.videocam),
//             ),
//           ),
//         ],
//       ),
//     );
//   }

//   Text _getRetrieveErrorWidget() {
//     if (_retrieveDataError != null) {
//       final Text result = Text(_retrieveDataError);
//       _retrieveDataError = null;
//       return result;
//     }
//     return null;
//   }

//   Future<void> _displayPickImageDialog(
//       BuildContext context, OnPickImageCallback onPick) async {
//     return showDialog(
//         context: context,
//         builder: (context) {
//           return AlertDialog(
//             title: Text('Add optional parameters'),
//             content: Column(
//               children: <Widget>[
//                 TextField(
//                   controller: maxWidthController,
//                   keyboardType: TextInputType.numberWithOptions(decimal: true),
//                   decoration:
//                       InputDecoration(hintText: "Enter maxWidth if desired"),
//                 ),
//                 TextField(
//                   controller: maxHeightController,
//                   keyboardType: TextInputType.numberWithOptions(decimal: true),
//                   decoration:
//                       InputDecoration(hintText: "Enter maxHeight if desired"),
//                 ),
//                 TextField(
//                   controller: qualityController,
//                   keyboardType: TextInputType.number,
//                   decoration:
//                       InputDecoration(hintText: "Enter quality if desired"),
//                 ),
//               ],
//             ),
//             actions: <Widget>[
//               TextButton(
//                 child: const Text('CANCEL'),
//                 onPressed: () {
//                   Navigator.of(context).pop();
//                 },
//               ),
//               TextButton(
//                   child: const Text('PICK'),
//                   onPressed: () {
//                     double width = maxWidthController.text.isNotEmpty
//                         ? double.parse(maxWidthController.text)
//                         : null;
//                     double height = maxHeightController.text.isNotEmpty
//                         ? double.parse(maxHeightController.text)
//                         : null;
//                     int quality = qualityController.text.isNotEmpty
//                         ? int.parse(qualityController.text)
//                         : null;
//                     onPick(width, height, quality);
//                     Navigator.of(context).pop();
//                   }),
//             ],
//           );
//         });
//   }
// }

// typedef void OnPickImageCallback(
//     double maxWidth, double maxHeight, int quality);

// class AspectRatioVideo extends StatefulWidget {
//   AspectRatioVideo(this.controller);

//   final VideoPlayerController controller;

//   @override
//   AspectRatioVideoState createState() => AspectRatioVideoState();
// }

// class AspectRatioVideoState extends State<AspectRatioVideo> {
//   VideoPlayerController get controller => widget.controller;
//   bool initialized = false;

//   void _onVideoControllerUpdate() {
//     if (!mounted) {
//       return;
//     }
//     if (initialized != controller.value.isInitialized) {
//       initialized = controller.value.isInitialized;
//       setState(() {});
//     }
//   }

//   @override
//   void initState() {
//     super.initState();
//     controller.addListener(_onVideoControllerUpdate);
//   }

//   @override
//   void dispose() {
//     controller.removeListener(_onVideoControllerUpdate);
//     super.dispose();
//   }

//   @override
//   Widget build(BuildContext context) {
//     if (initialized) {
//       return Center(
//         child: AspectRatio(
//           aspectRatio: controller.value.aspectRatio,
//           child: VideoPlayer(controller),
//         ),
//       );
//     } else {
//       return Container();
//     }
//   }
// }
