import 'package:awesome_dialog/awesome_dialog.dart';
import 'package:ekyc_admin/Network%20Manager/Models/network_manager.dart';
import 'package:flutter/material.dart';
import 'package:ekyc_admin/Configuration/app_config.dart';
import 'package:ekyc_admin/Configuration/greek_navigation.dart';
import 'package:ekyc_admin/Extension_Enum/greek_enum.dart';
import 'package:ekyc_admin/Utilities/greek_textstyle.dart';

class GreekDialogPopupView {
  //=====================================================================
  ///
  /// Show Awesome Dialog with custome message
  //
  /// message - Custome message
  ///
  //=====================================================================
  static awesomeMessageDialog({
    required BuildContext context,
    required String msg,
    required DialogType dialogType,
    required ValueChanged<BuildContext> onPressed,
  }) {
    final dialogWidth = (AppConfig().currentPlatform == AppPlatform.web)
        ? (MediaQuery.of(context).size.width / 4)
        : null;
    AwesomeDialog(
      width: dialogWidth,
      animType: AnimType.SCALE,
      dialogType: dialogType,
      dismissOnBackKeyPress: false,
      context: context,
      title: AppConfig().appName,
      desc: msg,
      titleTextStyle: GreekTextStyle.dialogTextStyle,
      descTextStyle: GreekTextStyle.dialogTextStyle,
      btnOkText: 'OK',
      btnOkOnPress: () => onPressed(context),
      btnOkColor: Colors.lightBlue.shade600,
    ).show();
  }

//=====================================================================

  //=====================================================================
  ///
  /// Show Awesome Dialog with custome message and cancel dialog option
  //
  /// message - Custome message
  ///
  //=====================================================================
  static awesomeMessageCancellableDialog({
    required BuildContext context,
    required String msg,
    required String cancelMsg,
    required DialogType dialogType,
    required ValueChanged<BuildContext> onOKPressed,
    required ValueChanged<BuildContext> onCancelPressed,
  }) {
    final dialogWidth = (AppConfig().currentPlatform == AppPlatform.web)
        ? (MediaQuery.of(context).size.width / 4)
        : null;
    AwesomeDialog(
      width: dialogWidth,
      animType: AnimType.SCALE,
      dialogType: dialogType,
      dismissOnBackKeyPress: false,
      context: context,
      title: AppConfig().appName,
      desc: msg,
      titleTextStyle: GreekTextStyle.dialogTextStyle,
      descTextStyle: GreekTextStyle.dialogTextStyle,
      btnOkText: 'OK',
      btnOkOnPress: () => onOKPressed(context),
      btnCancelText: cancelMsg,
      btnCancelOnPress: () => onCancelPressed(context),
      btnOkColor: Colors.lightBlue.shade600,
    ).show();
  }

//=====================================================================

  //=====================================================================
  ///
  /// Show Awesome Dialog for `logout`
  //
  /// message - Custome message
  ///
  //=====================================================================
  static showLogoutDialog({required BuildContext logoutContext}) {
    final dialogWidth = (AppConfig().currentPlatform == AppPlatform.web)
        ? (MediaQuery.of(logoutContext).size.width / 4)
        : null;
    AwesomeDialog(
      width: dialogWidth,
      animType: AnimType.SCALE,
      dialogType: DialogType.WARNING,
      dismissOnBackKeyPress: false,
      context: logoutContext,
      title: AppConfig().appName,
      desc: 'Are you sure, you want to Logout ?',
      titleTextStyle: GreekTextStyle.dialogTextStyle,
      descTextStyle: GreekTextStyle.dialogTextStyle,
      btnOkText: 'OK',
      btnOkOnPress: () {
        NetworkManager.of(logoutContext).postAPI(
          apiName: APINames.logout,
          postBodyData: {
            'user_name': AppConfig().userID,
            'session_token': AppConfig().token,
          },
        );

        GreekNavigator.pushNamedAndRemoveUntil(
          context: logoutContext,
          newRouteName: GreekScreenNames.login_page,
        );
      },
      btnCancelText: 'Cancel',
      btnCancelOnPress: () {},
      btnOkColor: Colors.lightBlue.shade600,
    ).show();
  }

//=====================================================================

  //=====================================================================
  ///
  /// Show Awesome Dialog For Admin new license creation
  //
  /// widget - List of client name (Listview)
  ///
  //=====================================================================
  static awesomeDialogAdminNewLicenseCreate({
    required BuildContext context,
    required DialogType dialogType,
    required Widget widget,
    required ValueChanged<BuildContext> onCancelPressed,
  }) {
    final dialogWidth = (AppConfig().currentPlatform == AppPlatform.web)
        ? (MediaQuery.of(context).size.width / 3.5)
        : null;
    AwesomeDialog(
      width: dialogWidth,
      animType: AnimType.SCALE,
      dialogType: dialogType,
      dismissOnBackKeyPress: false,
      context: context,
      body: widget,
      titleTextStyle: GreekTextStyle.dialogTextStyle,
      descTextStyle: GreekTextStyle.dialogTextStyle,
      btnCancelOnPress: () => onCancelPressed,
      btnCancelText: 'Cancel',
    ).show();
  }

//=====================================================================
}
