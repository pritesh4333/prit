import 'package:e_kyc/Login/UI/PanDetailsScreen/PanDetailsScreenLarge.dart';
import 'package:e_kyc/Login/UI/PanDetailsScreen/PanDetailsScreenSmall.dart';
import 'package:flutter/material.dart';

class PandetailsUI extends StatefulWidget {
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
  _PandetailsState createState() => _PandetailsState();
}

class _PandetailsState extends State<PandetailsUI> {
  // int _currentIndex = 0;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: LayoutBuilder(
        builder: (context, constraints) {
          if (constraints.maxWidth < 600) {
            return PanDetailsScreenSmall();
          } else {
            return PanDetailsScreenLarge();
          }
        },
      ),
    );
  }
}
