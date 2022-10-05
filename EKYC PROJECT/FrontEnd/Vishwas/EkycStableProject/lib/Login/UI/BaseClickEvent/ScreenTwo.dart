import 'package:e_kyc/Login/UI/BaseClickEvent/ClickEvent.dart';
import 'package:e_kyc/Login/UI/BaseClickEvent/ScreenThree.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class ScreenTwo extends StatelessWidget {
  final String screenTitle;

  const ScreenTwo({Key key, @required this.screenTitle}) : super(key: key);
  @override
  Widget build(BuildContext context) {
    return Container(
      color: Colors.green,
      child: Center(
        child: TextButton(
            onPressed: () {
              ClickEvent.commonStream.sink.add(ScreenThree());
            },
            child: Text(this.screenTitle)),
      ),
    );
  }
}
