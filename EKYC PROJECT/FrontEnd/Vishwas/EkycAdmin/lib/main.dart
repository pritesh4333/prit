import 'dart:io';

import 'package:flutter/material.dart';
import 'package:ekyc_admin/Configuration/greek_navigation.dart';
import 'package:ekyc_admin/Utilities/App%20Storage/greek_session_storage.dart';

import 'package:http/http.dart';

void main() {
  GreekSessionStorage().clearAllSessionData();
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  Widget build(BuildContext context) {
    // return MaterialApp(
    //   home: Scaffold(
    //     appBar: AppBar(),
    //     body: Container(
    //       color: Colors.amber,
    //       child: Center(
    //         child: TextButton(
    //           onPressed: () async {
    //             final downloadClient = Client();
    //             final request = Request(
    //               "GET",
    //               Uri.parse('https://www.learningcontainer.com/wp-content/uploads/2020/07/Large-Sample-Image-download-for-Testing.jpg'),
    //             );
    //             final responseStream = await downloadClient.send(request);

    //             responseStream.stream.listen(
    //               (value) {
    //                 print("Downloading... + ${value.toString()}");
    //               },
    //               onDone: () {
    //                 print("Downloaded");
    //               },
    //               onError: (e) {
    //                 print(e);
    //               },
    //               cancelOnError: true,
    //             );
    //           },
    //           child: const Text("Download"),
    //         ),
    //       ),
    //     ),
    //   ),
    // );
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      initialRoute: GreekScreenNames.login_page,
      routes: GreekNavigator.appRoutes,
      builder: (context, child) => MediaQuery(
        data: MediaQueryData.fromWindow(WidgetsBinding.instance.window).copyWith(
          boldText: false,
          textScaleFactor: 1.0,
          devicePixelRatio: 1.0,
        ),
        child: child ?? Container(color: Colors.red),
      ),
    );
  }
}
