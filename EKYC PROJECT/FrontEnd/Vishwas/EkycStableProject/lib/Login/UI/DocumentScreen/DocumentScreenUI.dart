import 'package:e_kyc/Login/UI/DocumentScreen/DocumentScreenLarge.dart';
import 'package:e_kyc/Login/UI/DocumentScreen/DocumentScreenSmall.dart';
import 'package:flutter/material.dart';

class DocumentScreenUI extends StatefulWidget {
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
  _DocumentScreenUIStack createState() => _DocumentScreenUIStack();
}

class _DocumentScreenUIStack extends State<DocumentScreenUI> {
  // int _currentIndex = 0;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: LayoutBuilder(
        builder: (context, constraints) {
          if (constraints.maxWidth < 600) {
            return DocumentScreenSmall();
          } else {
            return DocumentScreenLarge();
          }
        },
      ),
    );
  }
}
