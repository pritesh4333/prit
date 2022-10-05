// import 'dart:html';

import 'package:e_kyc/Login/UI/IPVScreen/IPVScreenLarge.dart';
import 'package:e_kyc/Login/UI/IPVScreen/IPVScreenSmall.dart';
import 'package:e_kyc/Login/UI/IPVScreen/IPVScreenSmallIOS.dart';
import 'package:flutter/foundation.dart';

import 'package:flutter/material.dart';

class IPVSCreenUI extends StatefulWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'E - KYC',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
    );
  }

  @override
  _IPVSCreenUIStack createState() => _IPVSCreenUIStack();
}

class _IPVSCreenUIStack extends State<IPVSCreenUI> {
  // int _currentIndex = 0;
  var isWebMobile;
  @override
  void initState() {
    super.initState();
    isWebMobile = kIsWeb && (defaultTargetPlatform == TargetPlatform.iOS);
    print('isWebMobile:- ' + isWebMobile.toString());
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: LayoutBuilder(
        builder: (context, constraints) {
          if (constraints.maxWidth < 600) {
            if (isWebMobile == true) {
              return IPVScreenSmallIOS();
            } else {
              return IPVScreenSmall();
            }
          } else {
            return IPVScreenLarge();
          }
        },
      ),
    );
  }
}
