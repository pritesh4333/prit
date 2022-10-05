import 'dart:js';

import 'package:ekyc_admin/Helper/greek_bloc.dart';
import 'package:ekyc_admin/Screens/DashBoardScreens/model/DashBoardResponseModel.dart';
import 'package:ekyc_admin/Screens/DashBoardScreens/repository/dashBoard_reposatory.dart';

import 'package:ekyc_admin/Utilities/App%20Storage/greek_session_storage.dart';
import 'package:flutter/cupertino.dart';
import 'package:rxdart/rxdart.dart';

class DashBoardBloc extends GreekBlocs {
  final _repository = DashBoardRepository();
  PublishSubject<List<DashBoardResponseModel>> reloadDashboard =
      PublishSubject<List<DashBoardResponseModel>>();
  final BuildContext _context;

  DashBoardBloc(this._context) {
    GreekSessionStorage().clearAllSessionData();
  }
  @override
  void disposeBloc() {}
  @override
  void activateBloc() {}

  @override
  void deactivateBloc() {}

  Future<Object?> getAllStats() async {
    var response = await _repository.GetAllStates(_context);
    if ((response != null) && response is Map) {
      final mainresponse = response["stats"];
      if (mainresponse is List) {
        final listresponse = mainresponse
            .map(
              (e) => DashBoardResponseModel.fromJson(e),
            )
            .toList();
        // final obj =
        //     DashBoardResponseModel.fromJson(response as Map<String, dynamic>);
        reloadDashboard.sink.add(listresponse);
        return [listresponse];
      }
    }
    return null;
  }

 void  personalDetailValidation(){

  }
}
