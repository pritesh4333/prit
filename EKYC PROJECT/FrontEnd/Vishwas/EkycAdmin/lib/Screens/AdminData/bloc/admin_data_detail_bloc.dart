import 'package:ekyc_admin/Helper/greek_bloc.dart';
import 'package:ekyc_admin/Screens/AdminData/models/response/common_data_grid_table_response_model.dart';
import 'package:ekyc_admin/Screens/AdminData/repository/admin_data_repository.dart';
import 'package:ekyc_admin/Screens/home/response.dart';
import 'package:ekyc_admin/Utilities/greek_session_storage.dart';
import 'package:flutter/material.dart';
import 'package:rxdart/rxdart.dart';

@immutable
class AdminDataDetailBloc extends GreekBlocs {
  final BuildContext _context;
  final _adminDataRepository = AdminDataRepository();
  BehaviorSubject<bool?> adminDataDetailSubject = BehaviorSubject.seeded(null);

  AdminDataDetailBloc(this._context) {
    GreekSessionStorage().clearAllSessionData();
    getUserDetailsData().then((value) {
      print(value);
    });
  }

//Check data and create model.
  Future<List<CommonDataGridTableResponseModel>> getUserDetailsData() async {
    final response = await _adminDataRepository.getAdminUserDetailData(_context);
    if ((response != null) && (response is Map)) {
      final mainResponseList = response["reportdata"];
      if (mainResponseList is List) {
        final modelList = mainResponseList
            .map(
              (e) => CommonDataGridTableResponseModel.fromJson(e),
            )
            .toList();

        return modelList;
      }
    } else {
      throw Exception(response.toString());
    }
    throw Exception(response.toString());
  }

  @override
  void activateBloc() {}

  @override
  void disposeBloc() {}

  @override
  void deactivateBloc() {}
}
