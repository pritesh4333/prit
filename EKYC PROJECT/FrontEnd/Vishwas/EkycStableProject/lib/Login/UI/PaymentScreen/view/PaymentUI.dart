import 'package:e_kyc/Login/UI/PaymentScreen/view/PaymentScreenLarge.dart';
import 'package:e_kyc/Login/UI/PaymentScreen/view/PaymentScreenSmall.dart';
import 'package:flutter/material.dart';

class PaymentUI extends StatefulWidget {
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
  _PaymentUIStack createState() => _PaymentUIStack();
}

class _PaymentUIStack extends State<PaymentUI> {
  // int _currentIndex = 0;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: LayoutBuilder(
        builder: (context, constraints) {
          if (constraints.maxWidth < 600) {
            return PaymentScreenSmall();
          } else {
            return PaymentScreenLarge();
          }
        },
      ),
    );
  }
}
