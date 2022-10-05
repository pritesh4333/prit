// ignore_for_file: must_be_immutable

import 'package:ekyc_admin/Screens/DashBoardScreens/block/dashboard_bloc.dart';
import 'package:ekyc_admin/Screens/DashBoardScreens/ui/mobile_ui/dashboard_mobile_view.dart';
import 'package:ekyc_admin/Screens/DashBoardScreens/ui/web_ui/dashboard_web_view.dart';
import 'package:flutter/material.dart';
import 'package:ekyc_admin/Configuration/app_config.dart';
import 'package:ekyc_admin/Extension_Enum/greek_enum.dart';

class DashBoardScreen extends StatelessWidget {
  final String selectedScreen;
  DashBoardScreen({
    Key? key,
    required this.selectedScreen,
  }) : super(key: key);

  DashBoardBloc? _dashBoardBloc;

  @override
  Widget build(BuildContext context) {
    _dashBoardBloc ??= DashBoardBloc(context);

    switch (AppConfig().currentPlatform) {
      case AppPlatform.mobile:
        return DashBoardWebScreen(dashBoardBloc: _dashBoardBloc, selectedScreen: selectedScreen);
      case AppPlatform.web:
        return LayoutBuilder(
          builder: (buildContext, boxConstraints) {
            final screenWidth = boxConstraints.maxWidth;
            final screenHeight = boxConstraints.maxHeight;

            if ((screenWidth < 800) || (screenHeight < 600)) {
              return DashBoardWebScreen(dashBoardBloc: _dashBoardBloc, selectedScreen: selectedScreen);
            } else {
              return DashBoardWebScreen(dashBoardBloc: _dashBoardBloc, selectedScreen: selectedScreen);
            }
          },
        );
    }
  }
}
