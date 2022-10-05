import 'package:e_kyc/Login/UI/PersonalDetailsScreen/view/PersonalDetailsScreenLarge.dart';
import 'package:e_kyc/Login/UI/PersonalDetailsScreen/view/PersonalDetailsScreenSmall.dart';
import 'package:flutter/material.dart';

class PersonalDetailsUI extends StatefulWidget {
  @override
  _PersonalDetailsUIState createState() => _PersonalDetailsUIState();
}

class _PersonalDetailsUIState extends State<PersonalDetailsUI> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: LayoutBuilder(
        builder: (context, constraints) {
          if (constraints.maxWidth < 600) {
            return PersonalDetailsScreenSmall();
          } else {
            return PersonalDetailsScreenLarge();
          }
        },
      ),
    );
  }
}
