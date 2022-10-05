import 'package:ekyc_admin/Helper/constant_image_name.dart';
import 'package:flutter/material.dart';
import 'package:ekyc_admin/Helper/constant_messages.dart';
import 'package:ekyc_admin/Utilities/greek_animation/greek_loading_indicator.dart';
import 'package:ekyc_admin/Utilities/greek_textstyle.dart';

class GreekBase {
  //  For Loader
  BuildContext? loaderContext;

  // Singleton object
  static final GreekBase _singleton = GreekBase._internal();

  factory GreekBase() {
    return _singleton;
  }

  GreekBase._internal();

  noDataAvailableView({String? msg}) => SingleChildScrollView(
        physics: const NeverScrollableScrollPhysics(),
        child: Column(
          children: [
            const SizedBox(
              height: 80,
            ),
            Padding(
              padding: const EdgeInsets.only(left: 30.0, right: 30.0),
              child: Center(
                child: Image.asset(
                  CommonImage.empty_cart.name,
                  fit: BoxFit.cover,
                ),
              ),
            ),
            Text(
              ((msg != null) && (msg.isNotEmpty))
                  ? msg
                  : ConstantMessages.GREEK_NO_DATA_MSG,
              textAlign: TextAlign.center,
              style: GreekTextStyle.noDataAvailableTextStyle,
            ),
          ],
        ),
      );

  showLoader() {
    return Container(
      color: const Color(0x80000000),
      child: Center(
        child: Container(
          height: 100.0,
          width: 100.0,
          decoration: BoxDecoration(
            borderRadius: BorderRadius.circular(55),
            color: Colors.white,
          ),
          child: const GreekLoadingIndicator(
            colors: [
              Colors.red,
              Colors.blue,
            ],
          ),
        ),
      ),
    );
  }
}
