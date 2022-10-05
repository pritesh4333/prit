import 'package:flutter/material.dart';
import 'package:ekyc_admin/Configuration/app_config.dart';
import 'package:ekyc_admin/Helper/constant_key_pair_value.dart';
import 'package:ekyc_admin/Helper/greek_base.dart';
import 'package:ekyc_admin/Utilities/greek_animation/greek_loading_indicator.dart';
import 'package:ekyc_admin/Utilities/App%20Storage/greek_session_storage.dart';

abstract class GreekBlocs {
  GreekBlocs() {
    final isAdmin = GreekSessionStorage()
        .getValueFromSessionStorage(keyName: ConstantKeyPairValue.isAdminUser);
    AppConfig().isAdmin = (isAdmin.isNotEmpty)
        ? (isAdmin.toLowerCase().compareTo('true') == 0)
        : false;

    final userID = GreekSessionStorage()
        .getValueFromSessionStorage(keyName: ConstantKeyPairValue.userid);
    AppConfig().userID = userID;
  }

  void disposeBloc();

  void deactivateBloc() {}

  void activateBloc() {}

  void showLoader({required BuildContext context}) {
    showGeneralDialog(
      barrierDismissible: false,
      //barrierColor: Colors.white.withOpacity(0.3),
      context: context,
      pageBuilder: (_, __, ___) {
        GreekBase().loaderContext = context;
        return Center(
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
        );
      },
    );
  }

  void hideLoader() {
    if (GreekBase().loaderContext != null) {
      Navigator.of(GreekBase().loaderContext!).pop();
      GreekBase().loaderContext = null;
    }
  }
}
