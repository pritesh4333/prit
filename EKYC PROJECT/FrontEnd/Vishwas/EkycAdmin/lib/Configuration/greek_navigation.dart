// ignore_for_file: constant_identifier_names

import 'package:ekyc_admin/Screens/DashBoardScreens/ui/dashboard_view_screen.dart';
import 'package:ekyc_admin/Screens/home/screens/home_screen.dart';
import 'package:flutter/material.dart';
import 'package:ekyc_admin/Screens/Login%20Screen/ui/login_view_screen.dart';

class GreekScreenNames {
  static const login_page = 'login_page';
  static const home_screen = 'home_screen';
  static const dashboard_page = 'dashboard_page';
  static const license_page = 'license_page';
  static const admin_license_request_list_page = 'admin_license_request_list_page';
}

class GreekNavigator {
  static final appRoutes = <String, WidgetBuilder>{
    GreekScreenNames.login_page: (BuildContext context) => LoginScreen(),
    GreekScreenNames.dashboard_page: (context) => DashBoardScreen(selectedScreen: ''),
    GreekScreenNames.home_screen: (context) => HomeScreen(),
  };

  static pushNamed({
    required BuildContext context,
    required String routeName,
    Object? arguments,
  }) async {
    return await Navigator.of(context).pushNamed(
      routeName,
      arguments: arguments,
    );
  }

  static pushReplacementNamed({
    required BuildContext context,
    required String routeName,
    Object? arguments,
  }) async {
    return await Navigator.of(context).pushReplacementNamed(
      routeName,
      arguments: arguments,
    );
  }

  static pushNamedAndRemoveUntil({
    required BuildContext context,
    required String newRouteName,
    Object? arguments,
  }) async {
    return await Navigator.of(context).pushNamedAndRemoveUntil(
      newRouteName,
      (route) {
        return true;
      },
      arguments: arguments,
    );
  }

  static void pop({required BuildContext context, dynamic popArguments}) {
    Navigator.of(context).pop(popArguments);
  }

  static popAndPushNamed({
    required BuildContext context,
    required String routeName,
    Object? arguments,
  }) async {
    return await Navigator.of(context).popAndPushNamed(
      routeName,
      arguments: arguments,
    );
  }

  static void popUntil({required BuildContext context, required String routeName}) {
    Navigator.of(context).popUntil(
      ModalRoute.withName(routeName),
    );
  }
}
