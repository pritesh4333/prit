import 'package:ekyc_admin/Helper/greek_bloc.dart';
import 'package:ekyc_admin/Screens/home/blocs.dart';
import 'package:ekyc_admin/Screens/home/response.dart';
import 'package:ekyc_admin/Utilities/greek_session_storage.dart';
import 'package:flutter/material.dart';
import 'package:rxdart/rxdart.dart';

import '../../DashBoardScreens/model/DashBoardResponseModel.dart';
import '../models/response/common_data_grid_table_response_model.dart';

@immutable
class AdminDataBloc extends GreekBlocs {
  final BuildContext _context;
  final _adminDataRepository = AdminDataRepository();
  PublishSubject<List<DashBoardResponseModel>> dataanalysisreport = PublishSubject<List<DashBoardResponseModel>>();
  AdminDataBloc(this._context) {
    GreekSessionStorage().clearAllSessionData();
  }

  @override
  void activateBloc() {}

  @override
  void disposeBloc() {}

  @override
  void deactivateBloc() {}

  List<CommonDataGridTableResponseModel> apiResponseList = <CommonDataGridTableResponseModel>[];

  // First Time User
  Future<List<CommonDataGridTableResponseModel>> getFirstTimeUserReportData() async {
    if (apiResponseList.isEmpty) {
      final response = await _adminDataRepository.getAllAdminReportData(_context);
      if ((response != null) && (response is Map)) {
        final mainResponseList = response["reportdata"];
        if (mainResponseList is List) {
          final modelList = mainResponseList
              .map(
                (e) => CommonDataGridTableResponseModel.fromJson(e),
              )
              .toList();

          apiResponseList = modelList;
          return apiResponseList;
        }
      } else {
        throw Exception(response.toString());
      }
      throw Exception(response.toString());
    } else {
      return apiResponseList;
    }
  }

  // OTP Verified
  Future<List<CommonDataGridTableResponseModel>> getOtpVerifiedReportData() async {
    if (apiResponseList.isEmpty) {
      final response = await _adminDataRepository.getAllAdminReportData(_context);
      if ((response != null) && (response is Map)) {
        final mainResponseList = response['reportdata'];
        if (mainResponseList is List) {
          final modelList = mainResponseList
              .map(
                (e) => CommonDataGridTableResponseModel.fromJson(e),
              )
              .toList();
          apiResponseList = modelList;
          return modelList;
        }
      } else {
        throw Exception(response.toString());
      }
      throw Exception(response.toString());
    } else {
      return apiResponseList;
    }
  }

  // PAN Verified
  Future<List<CommonDataGridTableResponseModel>> getPanVerifiedReportData() async {
    if (apiResponseList.isEmpty) {
      final response = await _adminDataRepository.getAllAdminReportData(_context);
      if ((response != null) && (response is Map)) {
        final mainResponseList = response['reportdata'];
        if (mainResponseList is List) {
          final modelList = mainResponseList
              .map(
                (e) => CommonDataGridTableResponseModel.fromJson(e),
              )
              .toList();
          apiResponseList = modelList;
          return modelList;
        }
      } else {
        throw Exception(response.toString());
      }
      throw Exception(response.toString());
    } else {
      return apiResponseList;
    }
  }

  // DATA ANALYSIS REPORT
  Future<List<CommonDataGridTableResponseModel>> getDataAnalysisReport() async {
    if (apiResponseList.isEmpty) {
      final response = await _adminDataRepository.getDataAnalysisReport(_context);
      if ((response != null) && (response is Map)) {
        final mainResponseList = response['reportdata'];
        if (mainResponseList is List) {
          final modelList = mainResponseList.map((e) => CommonDataGridTableResponseModel.fromJson(e)).toList();
          apiResponseList = modelList;
          return modelList;
        }
      } else {
        throw Exception(response.toString());
      }
      throw Exception(response.toString());
    } else {
      return apiResponseList;
    }
  }

  // IN PROCESS
  Future<List<CommonDataGridTableResponseModel>> getInProcessReportData() async {
    if (apiResponseList.isEmpty) {
      final response = await _adminDataRepository.getAllAdminReportData(_context);
      if ((response != null) && (response is Map)) {
        final mainResponseList = response['reportdata'];
        if (mainResponseList is List) {
          final modelList = mainResponseList
              .map(
                (e) => CommonDataGridTableResponseModel.fromJson(e),
              )
              .toList();
          apiResponseList = modelList;
          return modelList;
        }
      } else {
        throw Exception(response.toString());
      }
      throw Exception(response.toString());
    } else {
      return apiResponseList;
    }
  }

  Future<List<DashBoardResponseModel>?> getAllStats() async {
    var response = await _adminDataRepository.getDataAnalysisReport(_context);
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
        dataanalysisreport.sink.add(listresponse);
        return listresponse;
      }
    }
    return null;
  }

  // COMPLETED USERS
  Future<List<CommonDataGridTableResponseModel>> getCompletedUsersReportData() async {
    if (apiResponseList.isEmpty) {
      final response = await _adminDataRepository.getAllAdminReportData(_context);
      if ((response != null) && (response is Map)) {
        final mainResponseList = response['reportdata'];
        if (mainResponseList is List) {
          final modelList = mainResponseList
              .map(
                (e) => CommonDataGridTableResponseModel.fromJson(e),
              )
              .toList();
          apiResponseList = modelList;
          return modelList;
        }
      } else {
        throw Exception(response.toString());
      }
      throw Exception(response.toString());
    } else {
      return apiResponseList;
    }
  }

  // FINISH USERS
  Future<List<CommonDataGridTableResponseModel>> getFinishedUsersReportData() async {
    if (apiResponseList.isEmpty) {
      final response = await _adminDataRepository.getAllAdminReportData(_context);
      if ((response != null) && (response is Map)) {
        final mainResponseList = response['reportdata'];
        if (mainResponseList is List) {
          final modelList = mainResponseList
              .map(
                (e) => CommonDataGridTableResponseModel.fromJson(e),
              )
              .toList();
          apiResponseList = modelList;
          return modelList;
        }
      } else {
        throw Exception(response.toString());
      }
      throw Exception(response.toString());
    } else {
      return apiResponseList;
    }
  }

  // AUTHORIZE USERS
  Future<List<CommonDataGridTableResponseModel>> getAuthoizedUsersReportData() async {
    if (apiResponseList.isEmpty) {
      final response = await _adminDataRepository.getAuthorizeUserReport(_context);
      if ((response != null) && (response is Map)) {
        final mainResponseList = response['reportdata'];
        if (mainResponseList is List) {
          final modelList = mainResponseList.map((e) => CommonDataGridTableResponseModel.fromJson(e)).toList();
          apiResponseList = modelList;
          return modelList;
        }
      } else {
        throw Exception(response.toString());
      }
      throw Exception(response.toString());
    } else {
      return apiResponseList;
    }
  }

  // STATUS REPORT
  Future<List<CommonDataGridTableResponseModel>> getStatusReportData() async {
    if (apiResponseList.isEmpty) {
      final response = await _adminDataRepository.getAdminStatusReport(_context);
      if ((response != null) && (response is Map)) {
        final mainResponseList = response['reportdata'];
        if (mainResponseList is List) {
          final modelList = mainResponseList.map((e) => CommonDataGridTableResponseModel.fromJson(e)).toList();
          apiResponseList = modelList;
          return modelList;
        }
      } else {
        throw Exception(response.toString());
      }
      throw Exception(response.toString());
    } else {
      return apiResponseList;
    }
  }

  // GLOBAL SEARCH REPORT
  Future<List<CommonDataGridTableResponseModel>> getGlobalSearchReport() async {
    if (apiResponseList.isEmpty) {
      final response = await _adminDataRepository.getGlobalSearchReport(_context);
      if ((response != null) && (response is Map)) {
        final mainResponseList = response['reportdata'];
        if (mainResponseList is List) {
          final modelList = mainResponseList.map((e) => CommonDataGridTableResponseModel.fromJson(e)).toList();
          apiResponseList = modelList;
          return modelList;
        }
      } else {
        throw Exception(response.toString());
      }
      throw Exception(response.toString());
    } else {
      return apiResponseList;
    }
  }

  // PAYMENT REPORT
  Future<List<CommonDataGridTableResponseModel>> getPaymentReport() async {
    if (apiResponseList.isEmpty) {
      final response = await _adminDataRepository.getPaymentReport(_context);
      if ((response != null) && (response is Map)) {
        final mainResponseList = response['reportdata'];
        if (mainResponseList is List) {
          final modelList = mainResponseList.map((e) => CommonDataGridTableResponseModel.fromJson(e)).toList();
          apiResponseList = modelList;
          return modelList;
        }
      } else {
        throw Exception(response.toString());
      }
      throw Exception(response.toString());
    } else {
      return apiResponseList;
    }
  }

  // Referal Master REPORT need to change
  Future<List<CommonDataGridTableResponseModel>> getReferalMasterReport() async {
    if (apiResponseList.isEmpty) {
      final response = await _adminDataRepository.getReferalRepots(_context);
      if ((response != null) && (response is Map)) {
        final mainResponseList = response['reportdata'];
        if (mainResponseList is List) {
          final modelList = mainResponseList.map((e) => CommonDataGridTableResponseModel.fromJson(e)).toList();
          apiResponseList = modelList;
          return modelList;
        }
      } else {
        throw Exception(response.toString());
      }
      throw Exception(response.toString());
    } else {
      return apiResponseList;
    }
  }

  // User Rights REPORT need to change
  Future<List<CommonDataGridTableResponseModel>> getUserRightsReport() async {
    if (apiResponseList.isEmpty) {
      final response = await _adminDataRepository.getUserRightsReport(_context);
      if ((response != null) && (response is Map)) {
        final mainResponseList = response['reportdata'];
        if (mainResponseList is List) {
          final modelList = mainResponseList.map((e) => CommonDataGridTableResponseModel.fromJson(e)).toList();
          apiResponseList = modelList;
          return modelList;
        }
      } else {
        throw Exception(response.toString());
      }
      throw Exception(response.toString());
    } else {
      return apiResponseList;
    }
  }

  // RE IPV REPORT
  Future<List<CommonDataGridTableResponseModel>> getReIpvReport() async {
    if (apiResponseList.isEmpty) {
      final response = await _adminDataRepository.getReIpvReport(_context);
      if ((response != null) && (response is Map)) {
        final mainResponseList = response['reportdata'];
        if (mainResponseList is List) {
          final modelList = mainResponseList.map((e) => CommonDataGridTableResponseModel.fromJson(e)).toList();
          apiResponseList = modelList;
          return modelList;
        }
      } else {
        throw Exception(response.toString());
      }
      throw Exception(response.toString());
    } else {
      return apiResponseList;
    }
  }

  // AUTHORIZE REPORT
  Future<List<CommonDataGridTableResponseModel>> getAuthorizeUserReport() async {
    if (apiResponseList.isEmpty) {
      final response = await _adminDataRepository.getAuthorizeUserReport(_context);
      if ((response != null) && (response is Map)) {
        final mainResponseList = response['reportdata'];
        if (mainResponseList is List) {
          final modelList = mainResponseList.map((e) => CommonDataGridTableResponseModel.fromJson(e)).toList();
          apiResponseList = modelList;
          return modelList;
        }
      } else {
        throw Exception(response.toString());
      }
      throw Exception(response.toString());
    } else {
      return apiResponseList;
    }
  }

  // REJECTION REPORT
  Future<List<CommonDataGridTableResponseModel>> getRejectionReport() async {
    if (apiResponseList.isEmpty) {
      final response = await _adminDataRepository.getRejectionReport(_context);
      if ((response != null) && (response is Map)) {
        final mainResponseList = response['reportdata'];
        if (mainResponseList is List) {
          final modelList = mainResponseList.map((e) => CommonDataGridTableResponseModel.fromJson(e)).toList();
          apiResponseList = modelList;
          return modelList;
        }
      } else {
        throw Exception(response.toString());
      }
      throw Exception(response.toString());
    } else {
      return apiResponseList;
    }
  }

  // PENNY DROP REPORT
  Future<List<CommonDataGridTableResponseModel>> getPennyDropReportData() async {
    if (apiResponseList.isEmpty) {
      final response = await _adminDataRepository.getPennyDropReport(_context);
      if ((response != null) && (response is Map)) {
        final mainResponseList = response['reportdata'];
        if (mainResponseList is List) {
          final modelList = mainResponseList.map((e) => CommonDataGridTableResponseModel.fromJson(e)).toList();
          apiResponseList = modelList;
          return modelList;
        }
      } else {
        throw Exception(response.toString());
      }
      throw Exception(response.toString());
    } else {
      return apiResponseList;
    }
  }

  // REKYC STATUS REPORT
  Future<List<CommonDataGridTableResponseModel>> getReKycStatusReportData() async {
    if (apiResponseList.isEmpty) {
      final response = await _adminDataRepository.getAdminReKycStatusReport(_context);
      if ((response != null) && (response is Map)) {
        final mainResponseList = response['reportdata'];
        if (mainResponseList is List) {
          final modelList = mainResponseList.map((e) => CommonDataGridTableResponseModel.fromJson(e)).toList();
          apiResponseList = modelList;
          return modelList;
        }
      } else {
        throw Exception(response.toString());
      }
      throw Exception(response.toString());
    } else {
      return apiResponseList;
    }
  }

  // [REKYC DATA ANALYSIS REPORT]
  Future<List<CommonDataGridTableResponseModel>> getAdminReKycDataAnalysisReport() async {
    if (apiResponseList.isEmpty) {
      final response = await _adminDataRepository.getAdminReKycDataAnalysisReport(_context);
      if ((response != null) && (response is Map)) {
        final mainResponseList = response['reportdata'];
        if (mainResponseList is List) {
          final modelList = mainResponseList.map((e) => CommonDataGridTableResponseModel.fromJson(e)).toList();
          apiResponseList = modelList;
          return modelList;
        }
      } else {
        throw Exception(response.toString());
      }
      throw Exception(response.toString());
    } else {
      return apiResponseList;
    }
  }

  // [REKYC GLOBAL SEARCH REPORT]
  Future<List<CommonDataGridTableResponseModel>> getReKycGlobalSearchReport() async {
    if (apiResponseList.isEmpty) {
      final response = await _adminDataRepository.getReKycGlobalSearchReport(_context);
      if ((response != null) && (response is Map)) {
        final mainResponseList = response['reportdata'];
        if (mainResponseList is List) {
          final modelList = mainResponseList.map((e) => CommonDataGridTableResponseModel.fromJson(e)).toList();
          apiResponseList = modelList;
          return modelList;
        }
      } else {
        throw Exception(response.toString());
      }
      throw Exception(response.toString());
    } else {
      return apiResponseList;
    }
  }
}
