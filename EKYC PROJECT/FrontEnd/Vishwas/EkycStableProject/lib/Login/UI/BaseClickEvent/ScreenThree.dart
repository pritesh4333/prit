import 'package:e_kyc/Login/UI/BaseClickEvent/ClickEvent.dart';
import 'package:e_kyc/Login/UI/BaseClickEvent/ScreenOne.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class ScreenThree extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      color: Colors.blue,
      child: Center(
        child: TextButton(
            onPressed: () {
              ClickEvent.commonStream.sink.add(ScreenOne());
            },
            child: Text('Screen 3')),
      ),
    );
  }
}
