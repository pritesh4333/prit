// ignore_for_file: avoid_web_libraries_in_flutter, constant_identifier_names, unused_field, avoid_print, non_constant_identifier_names

import "dart:convert";
import 'package:flutter/foundation.dart';
import 'package:ekyc_admin/Extension_Enum/greek_enum.dart';

class APIRequestModel {
  final Request _request;

  factory APIRequestModel.JSONFrom({
    required int apiNo,
    required APINames apiName,
    required Object? bodyData,
    Object? params,
  }) {
    final obj = APIRequestModel(
      Request(
        data: bodyData,
        svcName: apiName.name,
        params: params,
      ),
    );

    if (kDebugMode) {
      print('\n---------------$apiNo. Request - (${apiName.name})---------------\n${obj.jsonString()}\n------------------------------------------------------------------------');
    }

    return obj;
  }

  APIRequestModel(this._request);

  Object toJson() => {
        "request": _request.toJson(),
      };

  String jsonString() {
    final s1 = json.encode(toJson());
    return s1;
  }

  String base64Body() {
    //if (NetworkConstant.isBase64Encoding)
    return base64.encode(utf8.encode(this.jsonString()));
    /* else
      return this.jsonString();

    return jsonString(); */
  }
}

class Request {
  final Object? data;
  final Object? params;
  String svcName;

  Request({
    required this.data,
    required this.svcName,
    required this.params,
  });

  Object? toJson() {
    final dic = {
      "params": params,
      "data": (data == null) ? {} : data,
      "svcName": svcName,
    };

    dic.removeWhere(
      (key, value) {
        if (value == null) {
          return true;
        } else {
          return false;
        }
      },
    );
    return dic;
  }
}
