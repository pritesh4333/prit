 

import 'package:e_kyc/Login/UI/EsignScreen/view/EsignScreenLarge.dart';
import 'package:e_kyc/Login/UI/EsignScreen/view/EsignScreenSmall.dart';
import 'package:flutter/material.dart';

class EsignScreenUI extends StatefulWidget {
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
  _EsignScreenUIStack createState() => _EsignScreenUIStack();
}

class _EsignScreenUIStack extends State<EsignScreenUI> {
  // int _currentIndex = 0;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: LayoutBuilder(
        builder: (context, constraints) {
          if (constraints.maxWidth < 600) {
            return EsignScreenSmall();
          } else {
            return EsignScreenLarge();
          }
        },
      ),
    );
  }
}
