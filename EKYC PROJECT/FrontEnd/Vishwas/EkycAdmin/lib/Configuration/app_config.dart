// ignore_for_file: file_names, constant_identifier_names, non_constant_identifier_names

import 'dart:io' show Platform;
import 'package:flutter/foundation.dart' show kIsWeb;
import 'package:ekyc_admin/Extension_Enum/greek_enum.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

class AppConfig {
  // Singleton object
  static final AppConfig _singleton = AppConfig._internal();

  factory AppConfig() {
    return _singleton;
  }

  AppConfig._internal() {
    if (kIsWeb) {
      currentPlatform = AppPlatform.web;
    } else {
      if ((Platform.isIOS) || (Platform.isAndroid)) {
        currentPlatform = AppPlatform.mobile;
      }
    }
  }

  String appName = 'Greek';

  AppPlatform currentPlatform = AppPlatform.web;

  bool isAdmin = false;
  String userID = '';
  String token = '';
  String clientName = '';
  String stageclick = "";
  String selectedScreen = '';
  String sectionTitle = '';
  String selectedReportType = '';

  /* void refreshPage(BuildContext context, Widget widget) {
    Navigator.pushReplacement(
      context,
      PageRouteBuilder(
        transitionDuration: Duration.zero,
        pageBuilder: (_, __, ___) => widget,
      ),
    );
  } */
  void showLoaderDialog(BuildContext context) {
    showDialog(
      barrierDismissible: false,
      context: context,
      builder: (BuildContext context) {
        return Container(
          child: Center(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.center,
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                Container(
                  margin: const EdgeInsets.only(top: 10, bottom: 10),
                  child: const Text(
                    "Please wait",
                    style: TextStyle(color: Colors.black, fontFamily: 'century_gothic', fontSize: 15, fontWeight: FontWeight.w600),
                  ),
                ),
                CircularProgressIndicator(),
              ],
            ),
          ),
        );
      },
    );
  }

  void dismissLoaderDialog(BuildContext context) {
    try {
      Navigator.pop(context); //// dismiss loader
    } catch (exception) {}
  }

  Future<dynamic> showAlert(BuildContext ctx, String msg) {
    return showDialog(
      context: ctx,
      builder: (ctx) => AlertDialog(
        title: Text("E-KYC Admin"),
        content: Text(msg),
        actions: <Widget>[
          TextButton(
            onPressed: () async {
              Navigator.of(ctx).pop();
            },
            child: Text("OK"),
          ),
        ],
      ),
    );
  }
}
