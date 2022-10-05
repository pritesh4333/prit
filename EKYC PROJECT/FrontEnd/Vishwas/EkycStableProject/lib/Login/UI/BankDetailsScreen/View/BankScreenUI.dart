import 'package:e_kyc/Login/UI/BankDetailsScreen/View/BankScreenLarge.dart';
import 'package:e_kyc/Login/UI/BankDetailsScreen/View/BankScreenSmall.dart';
import 'package:flutter/material.dart';

class BankScreenUI extends StatefulWidget {
  // @override
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
  _BankPage createState() => _BankPage();
}

class _BankPage extends State<BankScreenUI> {
  // int _currentIndex = 0;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: LayoutBuilder(
        builder: (context, constraints) {
          if (constraints.maxWidth < 600) {
            return BankScreenSmall();
          } else {
            return BankScreenLarge();
          }
        },
      ),
    );
  }
}
