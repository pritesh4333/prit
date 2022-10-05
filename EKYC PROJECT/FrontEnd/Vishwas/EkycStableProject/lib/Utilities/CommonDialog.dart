import 'dart:async';

import 'package:e_kyc/Login/UI/Configuration/AppConfig.dart';
import 'package:flutter/material.dart';

class CommonErrorDialog {
  static showServerErrorDialog(BuildContext ctx, String message) {
    Timer(Duration(seconds: 30), () {
      AppConfig().dismissLoaderDialog(ctx); //// dismiss loader
      showDialog(
        context: ctx,
        builder: (ctx) => AlertDialog(
          title: Text("E-KYC"),
          content: Text(message),
          actions: <Widget>[
            TextButton(
              onPressed: () {
                Navigator.of(ctx).pop();
              },
              child: Text("OK"),
            ),
          ],
        ),
      );
    });
  }
}
