import 'package:e_kyc/Login/UI/ThemeColors.dart';
import 'package:flutter/material.dart';

class CustomTextStyles {
  static const String FontNameDefault = 'century_gothic';

  // This is for Header title
  static const TextStyle1 = TextStyle(
    fontFamily: FontNameDefault,
    fontWeight: FontWeight.w900,
    fontSize: 15,
    color: Color(0xFF0066CC),
    letterSpacing: 0.2,
  );

  // Sub Titles
  static const TextStyle2 = TextStyle(
    fontFamily: FontNameDefault,
    fontWeight: FontWeight.w900,
    fontSize: 14,
    color: Color(0xFFFAB804),
    letterSpacing: 0.2,
  );

  //Text Content Style
  static const TextStyle3 = TextStyle(
    fontFamily: FontNameDefault,
    fontWeight: FontWeight.w900,
    fontSize: 14,
    color: Color(0xFF333333),
  );
  static const TextStyle4 = TextStyle(
    fontFamily: FontNameDefault,
    fontWeight: FontWeight.w600,
    fontSize: 13,
    color: Color(0xFF333333),
  );
}
