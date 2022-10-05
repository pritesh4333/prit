// ignore_for_file: avoid_web_libraries_in_flutter, constant_identifier_names, unused_field, avoid_print, non_constant_identifier_names

import "dart:convert";
import 'dart:html';
import 'package:flutter/foundation.dart';
import 'package:ekyc_admin/Extension_Enum/greek_enum.dart';

class APIResponseModel {
  Response response;

  APIResponseModel({required this.response});

  factory APIResponseModel.fromBase64({
    required int apiNo,
    required APINames apiName,
    required String base64BodyData,
  }) {
    /*var base64DecodedData;

    if (NetworkConstants.isBase64Encoding) {
      base64DecodedData = utf8.decode(
        base64.decode(base64BodyData),
      );
    } else {
      base64DecodedData = base64BodyData;
    }*/

    var base64DecodedData = utf8.decode(
      base64.decode(base64BodyData),
    );

    if (kDebugMode) {
      print(
          "\n---------------$apiNo. Response - (${apiName.name})---------------\n${base64DecodedData.toString()}\n------------------------------------------------------------------------");
    }

    final jsonObj = json.decode(base64DecodedData.toString());

    //final responseResult = jsonObj["response"];
    //print("--------Responseresult-----${Response.fromJSON(responseResult)}");

    if (jsonObj is Map) {
      if (jsonObj.containsKey('response')) {
        final responseResult = jsonObj["response"];
        return APIResponseModel(
          response: Response.fromJSON(responseResult),
        );
      } else if (jsonObj.containsKey('data')) {
        final errorCode =
            int.tryParse(jsonObj['ErrorCode']?.toString() ?? '0') ?? 0;

        final dataResponse = Response(
            sessionId: jsonObj['sessionId'],
            errorCode: errorCode,
            data: jsonObj['data']);
        return APIResponseModel(
          response: dataResponse,
        );
      }
    }
    final dataResponse = Response(sessionId: "", errorCode: 1, data: {});
    return APIResponseModel(
      response: dataResponse,
    );

    // return APIResponseModel(
    //   response: Response.fromJSON(responseResult),
    // );
  }
}

class Response {
  int? errorCode;
  Object? data;
  String? sessionId;

  Response({
    required this.errorCode,
    required this.data,
    required this.sessionId,
  });

  //factory Response.fromJSON(Map<String, Object> json) {
  factory Response.fromJSON(Map json) {
    final sesseisioID = json["sessionId"]?.toString() ?? "";
    final errorcode = int.tryParse(json["ErrorCode"]?.toString() ?? '0') ?? 0;
    final data = json["data"];

    data["sessionId"] = sesseisioID;

    return Response(
      sessionId: sesseisioID,
      errorCode: errorcode, // ErrorCode: json["ErrorCode"],
      data: data,
    );
  }
}
