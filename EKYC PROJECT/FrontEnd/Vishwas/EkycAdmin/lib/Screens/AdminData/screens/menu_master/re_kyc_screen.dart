import 'package:ekyc_admin/Configuration/app_config.dart';
import 'package:ekyc_admin/Extension_Enum/greek_enum.dart';
import 'package:ekyc_admin/Screens/AdminData/ui/re_kyc_report_web_screen.dart';
import 'package:ekyc_admin/Screens/home/screen.dart';
import 'package:flutter/material.dart';

class ReKycMasterScreen extends StatelessWidget {
  const ReKycMasterScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    switch (AppConfig().currentPlatform) {
      case AppPlatform.mobile:
        return ReKycReportsWebScreen(title: AppConfig().sectionTitle);
      case AppPlatform.web:
        return LayoutBuilder(builder: (buildContext, boxConstraints) {
          final screenWidth = boxConstraints.maxWidth;
          final screenHeight = boxConstraints.maxHeight;

          if (screenWidth < 800 || (screenHeight < 600)) {
            return ReKycReportsWebScreen(title: AppConfig().sectionTitle);
          } else {
            return ReKycReportsWebScreen(title: AppConfig().sectionTitle);
          }
        });
    }
  }
}
