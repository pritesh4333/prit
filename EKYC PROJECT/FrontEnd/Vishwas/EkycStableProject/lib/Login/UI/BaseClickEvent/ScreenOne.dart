import 'package:e_kyc/Login/UI/BaseClickEvent/ClickEvent.dart';
import 'package:e_kyc/Login/UI/BaseClickEvent/ScreenTwo.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class ScreenOne extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      color: Colors.red,
      child: Center(
        child: TextButton(
            onPressed: () {
              ClickEvent.commonStream.sink.add(ScreenTwo(
                    screenTitle: 'Sandip',
                  ));
            },
            child: Text('Screen 1')),
      ),
    );
  }
}
