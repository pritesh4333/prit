// ignore_for_file: must_be_immutable

import 'package:ekyc_admin/Screens/AdminData/ui/admin_data_grid.dart';
import 'package:flutter/material.dart';
import 'package:ekyc_admin/Configuration/app_config.dart';
import 'package:ekyc_admin/Extension_Enum/greek_enum.dart';
import 'package:ekyc_admin/Screens/Login%20Screen/bloc/login_bloc.dart';
import 'package:ekyc_admin/Screens/Login%20Screen/ui/mobile_ui/login_mobile_view.dart';
import 'package:ekyc_admin/Screens/Login%20Screen/ui/web_ui/login_web_view.dart';

class AdminScreen extends StatelessWidget {
  AdminScreen({Key? key}) : super(key: key);

  LoginBloc? _loginBloc;

  @override
  Widget build(BuildContext context) {
    _loginBloc ??= LoginBloc(context);

    switch (AppConfig().currentPlatform) {
      case AppPlatform.mobile:
        return LoginScreenMobile(loginBloc: _loginBloc);
      case AppPlatform.web:
        return LayoutBuilder(
          builder: (buildContext, boxConstraints) {
            final screenWidth = boxConstraints.maxWidth;
            final screenHeight = boxConstraints.maxHeight;

            if ((screenWidth < 800) || (screenHeight < 600)) {
              return LoginScreenMobile(loginBloc: _loginBloc);
            } else {
              return LoginScreenWeb(loginBloc: _loginBloc);
            }
          },
        );
    }
  }
}
