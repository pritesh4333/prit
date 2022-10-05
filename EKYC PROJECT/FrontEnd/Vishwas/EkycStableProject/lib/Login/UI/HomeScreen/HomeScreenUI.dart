import 'package:e_kyc/Login/UI/HomeScreen/HomeScreenLarge.dart';
import 'package:e_kyc/Login/UI/HomeScreen/HomeScreenSmall.dart';
// import 'package:flutter/foundation.dart' show TargetPlatform;
import 'package:flutter/material.dart';

class HomeScreenUI extends StatefulWidget {
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
  _HomePage createState() => _HomePage();
}

class _HomePage extends State<HomeScreenUI> {
  // int _currentIndex = 0;
  @override
  void dispose() {
    // TODO: implement dispose
    print(" platform dispose");
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: LayoutBuilder(
        builder: (context, constraints) {
          var platform = Theme.of(context).platform;
          print(platform);
          print("i am from platform");
          if (constraints.maxWidth < 600) {
            return HomeScreenSmall();
          } else {
            return HomeScreenLarge();
          }
        },
      ),
    );
  }
}
