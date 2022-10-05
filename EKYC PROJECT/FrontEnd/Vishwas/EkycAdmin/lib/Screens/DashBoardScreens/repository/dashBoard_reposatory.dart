import 'dart:convert';

import 'package:ekyc_admin/Configuration/app_config.dart';
import 'package:ekyc_admin/Extension_Enum/greek_enum.dart';
import 'package:ekyc_admin/Network%20Manager/Models/network_manager.dart';
import 'package:flutter/cupertino.dart';

class DashBoardRepository {
  Future<Object?> GetAllStates(BuildContext context) => NetworkManager(context)
          .postAPI(apiName: APINames.getAllStats, postBodyData: {
        "gscid": AppConfig().clientName,
        "sessionId": AppConfig().token,
      });
  /*  Future<DashBoardResponseModel?> GetAllStates() async {
    // var headers = {'Content-Type': 'application/json'};
    var apiName = APINames.getAllStats;
    var request = http.Request(
        'POST', Uri.parse("http://192.168.209.11:3006/getAllStats"));
    request.body =
        "{\"request\":{\"data\":{\"gscid\":\"ABCD2\",\"sessionId\":" +
            AppConfig().token.toString() +
            "\"},\"svcName\":\"getAllStats\"}}";

    // request.headers.addAll(headers);

    http.StreamedResponse response = await request.send();

    if (response.statusCode == 200) {
      final obj =
          DashBoardResponseModel.fromJson(json.decode(response.toString()));
      print(obj);
      // reloadDashboard.sink.add(obj);
      return obj;
    } else {
      print(response.reasonPhrase);
    }
  }
 */
}
