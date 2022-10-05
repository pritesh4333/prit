import 'package:ekyc_admin/Configuration/greek_navigation.dart';
import 'package:ekyc_admin/Screens/Login%20Screen/models/login_response_model.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:ekyc_admin/Configuration/app_config.dart';
import 'package:ekyc_admin/Helper/constant_key_pair_value.dart';
import 'package:ekyc_admin/Helper/greek_bloc.dart';
import 'package:ekyc_admin/Screens/Login%20Screen/repository/login_repository.dart';
import 'package:ekyc_admin/Utilities/App%20Storage/greek_session_storage.dart';

import '../../../Utilities/greek_textstyle.dart';

class LoginBloc extends GreekBlocs {
  final BuildContext _context;

  LoginBloc(this._context) {
    GreekSessionStorage().clearAllSessionData();
  }

  final _repository = LoginRepository();

  //final brokerIDTextController = TextEditingController();
  // final userIDTextController = TextEditingController(text: (kReleaseMode) ? '' : '');
  // final passwordTextController = TextEditingController(text: (kReleaseMode) ? '' : '');

  final userIDTextController = TextEditingController(text: (kReleaseMode) ? '' : 'TEST001');
  final passwordTextController = TextEditingController(text: (kReleaseMode) ? '' : 'a@1111111111');

  String clientName = '';

  @override
  void disposeBloc() {}

  @override
  void activateBloc() {}

  @override
  void deactivateBloc() {}

  Future<int?> proceedButtonTaped() async {
    if (userIDTextController.text.isEmpty) {
      AppConfig().showAlert(_context, "Please provide UserID");
    } else if (passwordTextController.text.isEmpty) {
      AppConfig().showAlert(_context, "Please provide Password");
    } else {
      GreekSessionStorage().clearAllSessionData();

      final response = await _repository.singinToApp(
        _context,
        userIDTextController.text,
        passwordTextController.text,
      );

      if ((response != null) && response is Map) {
        final loginModelObj = LoginResponseModel.fromJson((response as Map<String, dynamic>));

        if (loginModelObj.errorCode != null) {
          /*  GreekSessionStorage().setValueFromSessionStorage(
          keyName: ConstantKeyPairValue.adminDashboradScreenRefreshTimer,
          valName: loginModelObj.data.adminDashbordTimer.toString(),
        ); */
          /* GreekSessionStorage().setValueFromSessionStorage(
          keyName: ConstantKeyPairValue.sessionToken,
          valName: loginModelObj.data..toString(),
        ); */

          /*  GreekSessionStorage().setValueFromSessionStorage(
          keyName: ConstantKeyPairValue.isAdminUser,
          valName: (loginModelObj.isAdmin == 1) ? 'true' : 'false',
        );
        final sessionVal = GreekSessionStorage().getValueFromSessionStorage(
            keyName: ConstantKeyPairValue.isAdminUser);
        AppConfig().isAdmin = (sessionVal.isNotEmpty)
            ? (sessionVal.toLowerCase().compareTo('true') == 0)
            : false;

        AppConfig().userID = loginModelObj.userID ?? '';
        GreekSessionStorage().setValueFromSessionStorage(
          keyName: ConstantKeyPairValue.userid,
          valName: AppConfig().userID,
        );

        GreekSessionStorage().setValueFromSessionStorage(
          keyName: ConstantKeyPairValue.sessionToken,
          valName: loginModelObj.token.toString(),
        );
        final sessionToken = GreekSessionStorage().getValueFromSessionStorage(
            keyName: ConstantKeyPairValue.sessionToken);
        AppConfig().token = sessionToken;

        if (!(AppConfig().isAdmin)) {
          clientName = loginModelObj.clientName ?? '';
          GreekSessionStorage().setValueFromSessionStorage(
            keyName: ConstantKeyPairValue.clientName,
            valName: clientName,
          );
        }

        return loginModelObj.errorcode; */

          if (loginModelObj.errorCode == 0) {
            GreekSessionStorage().setValueFromSessionStorage(
              keyName: ConstantKeyPairValue.sessionToken,
              valName: loginModelObj.sessionId.toString(),
            );

            GreekSessionStorage().setValueFromSessionStorage(
              keyName: ConstantKeyPairValue.clientName,
              valName: loginModelObj.gscid.toString(),
            );

            final sessionToken = GreekSessionStorage().getValueFromSessionStorage(keyName: ConstantKeyPairValue.sessionToken);
            AppConfig().token = sessionToken;

            final gscid = GreekSessionStorage().getValueFromSessionStorage(keyName: ConstantKeyPairValue.clientName);
            AppConfig().clientName = gscid;

            GreekNavigator.pushNamedAndRemoveUntil(
              context: _context,
              newRouteName: GreekScreenNames.home_screen,
            );
          } else {
            _showErrorDialog(_context);
          }
        }
      }
    }

    return null;
  }

  _showErrorDialog(BuildContext context) {
    showDialog<void>(
      context: context,
      barrierDismissible: false,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text(
            "No Login Details Found",
            style: GreekTextStyle.personalDetailsHeading,
          ),
          actions: <Widget>[
            TextButton(
              child: const Text(
                "OK",
                style: GreekTextStyle.textFieldHeading,
              ),
              onPressed: () {
                Navigator.of(context).pop(false);
              },
            )
          ],
        );
      },
    );
  }

  void signupButtonTaped() {}
}
