// ignore_for_file: avoid_web_libraries_in_flutter, constant_identifier_names, unused_field, avoid_print

import 'dart:math' as io_math;
import 'package:awesome_dialog/awesome_dialog.dart';
import 'package:flutter/material.dart';
import 'package:ekyc_admin/Configuration/greek_navigation.dart';
import 'package:ekyc_admin/Helper/greek_base.dart';
import 'package:ekyc_admin/Network%20Manager/AppURLs/app_url_main.dart';
import 'package:ekyc_admin/Network%20Manager/Models/api_request_model.dart';
import 'package:ekyc_admin/Network%20Manager/Models/api_response_model.dart';
import 'package:ekyc_admin/Utilities/greek_dialog_popup_view.dart';
import "package:http/http.dart" as http;
import 'package:ekyc_admin/Extension_Enum/greek_enum.dart';

class NetworkURLs {
  static const flutter_server_url = 'https://flutter.greeksoft.in:14301/';
  static const test_server_url = 'http://192.168.209.52:14301/';
  static const live_server_url = 'http://license.greeksoft.in/';
  static const tester_url = 'http://192.168.209.11:6006/';
  static const satendra_url = 'http://192.168.209.116:6006/';
}

class NetworkManager {
  final _baseURL = getBaseAPIUrl();

  static bool _isValidToken = true;

  final BuildContext _networkContext;
  NetworkManager(this._networkContext);
  static NetworkManager of(BuildContext networkContext) {
    return NetworkManager(networkContext);
  }

  void _hideAppLoader() {
    if (GreekBase().loaderContext != null) {
      Navigator.of(GreekBase().loaderContext!).pop();
      GreekBase().loaderContext = null;
    }
  }

  Future<Object?> authenticateUser({
    required String gscid,
    required String pass,
  }) async {
    _isValidToken = true;

    final apiNo = io_math.Random().nextInt(999);

    final body = APIRequestModel.JSONFrom(
      apiNo: apiNo,
      apiName: APINames.eKycAdminLogin,
      bodyData: {
        "gscid": gscid,
        "pass": pass,
      },
    );

    print('-----BaseURL----------$_baseURL )---------------');
    print('\n---------------$_baseURL \n${APINames.eKycAdminLogin.name} )---------------\n------------------------------------------------------------------------');

    try {
      final loginAPIClient = http.Client();
      final response = await loginAPIClient.post(
        Uri.parse(_baseURL + APINames.eKycAdminLogin.name),
        body: body.base64Body(),
      );
      loginAPIClient.close();

      if (response.statusCode == 200) {
        final obj = APIResponseModel.fromBase64(
          apiNo: apiNo,
          apiName: APINames.eKycAdminLogin,
          base64BodyData: response.body,
        );

        // return obj.response.data;
        return obj.response.data;
      }

      return null;
    } catch (exception) {
      print('\n---------------$apiNo. API Exception - (${APINames.eKycAdminLogin.name})---------------\n${exception.toString()}\n------------------------------------------------------------------------');
      // body.base64Body();
      print('\n-------Encrypted--------${body.base64Body()}---------------\n------------------------------------------------------------------------');

      _hideAppLoader();

      GreekDialogPopupView.awesomeMessageDialog(
        context: _networkContext,
        msg: 'Could not connect to server, Try again',
        dialogType: DialogType.WARNING,
        onPressed: (_) {},
      );

      return null;
    }
  }

  Future<Object?> postAPI({
    required APINames apiName,
    required dynamic postBodyData,
    Object? params,
  }) async {
    print('-----BaseURL----------$_baseURL )---------------');
    if (_isValidToken) {
      _isValidToken = (apiName != APINames.logout);

      final apiNo = io_math.Random().nextInt(999);

      final bodyData = APIRequestModel.JSONFrom(
        apiNo: apiNo,
        apiName: apiName,
        bodyData: postBodyData,
        params: params,
      );

      try {
        final postAPIClient = http.Client();
        final response = await postAPIClient.post(
          Uri.parse(_baseURL + apiName.name),
          body: bodyData.base64Body(),
        );
        postAPIClient.close();

        if (response.statusCode == 200) {
          final obj = APIResponseModel.fromBase64(
            apiNo: apiNo,
            apiName: apiName,
            base64BodyData: response.body,
          );

          if (obj.response.errorCode == 0) {
            print('---in if---Response --- ${obj.response.data}');
            return obj.response.data;
          }
        }
        //  Invalid Token
        else if (response.statusCode == 401) {
          _hideAppLoader();

          _isValidToken = false;

          GreekDialogPopupView.awesomeMessageDialog(
            context: _networkContext,
            msg: 'Invalid Session',
            dialogType: DialogType.ERROR,
            onPressed: (dialogContext) {
              GreekNavigator.pushNamedAndRemoveUntil(
                context: dialogContext,
                newRouteName: GreekScreenNames.login_page,
              );
            },
          );
        }
        //  A token is required for authentication
        else if (response.statusCode == 403) {
          _hideAppLoader();

          _isValidToken = false;

          GreekDialogPopupView.awesomeMessageDialog(
            context: _networkContext,
            msg: 'Session token is required for authentication. Please relogin',
            dialogType: DialogType.ERROR,
            onPressed: (dialogContext) {
              GreekNavigator.pushNamedAndRemoveUntil(
                context: dialogContext,
                newRouteName: GreekScreenNames.login_page,
              );
            },
          );
        }

        return null;
      } catch (exception) {
        print('\n---------------$apiNo. API Exception - (${apiName.name})---------------\n${exception.toString()}\n------------------------------------------------------------------------');

        _hideAppLoader();

        GreekDialogPopupView.awesomeMessageDialog(
          context: _networkContext,
          msg: 'Could not connect to server, Try again',
          dialogType: DialogType.WARNING,
          onPressed: (_) {},
        );

        return null;
      }
    }

    return null;
  }
}
