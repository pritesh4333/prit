import 'dart:typed_data';

import 'package:ekyc_admin/Configuration/app_config.dart';
import 'package:ekyc_admin/Extension_Enum/greek_enum.dart';
import 'package:ekyc_admin/Network%20Manager/Models/network_manager.dart';
import 'package:ekyc_admin/Screens/AdminData/models/response/common_data_grid_table_response_model.dart';
import 'package:flutter/material.dart';

import "package:http/http.dart" as http;

import '../../../Network Manager/AppURLs/app_url_web.dart';

class AdminDataRepository {
  Future<Object?> getAllAdminReportData(BuildContext context) => NetworkManager(context).postAPI(
        apiName: APINames.getReportData,
        postBodyData: {
          "gscid": AppConfig().clientName,
          "sessionId": AppConfig().token,
          "stage": AppConfig().stageclick,
        },
      );

  Future<Object?> getAdminUserDetailData(BuildContext context) => NetworkManager(context).postAPI(
        apiName: APINames.getUserDetails,
        postBodyData: {
          "uniqueid": AppConfig().clientName,
        },
      );

// [STATUS REPORT]
  Future<Object?> getAdminStatusReport(BuildContext context) {
    return NetworkManager(context).postAPI(
      apiName: APINames.getStatusReport,
      postBodyData: {
        "gscid": AppConfig().clientName,
        "sessionId": AppConfig().token,
      },
    );
  }

  // [GLOBAL SEARCH REPORT]
  Future<Object?> getGlobalSearchReport(BuildContext context) {
    return NetworkManager(context).postAPI(
      apiName: APINames.getStatusReport,
      postBodyData: {
        "gscid": AppConfig().clientName,
        "sessionId": AppConfig().token,
      },
    );
  }

  // [PAYMENT REPORT]
  Future<Object?> getPaymentReport(BuildContext context) {
    return NetworkManager(context).postAPI(
      apiName: APINames.getPaymentReport,
      postBodyData: {
        "gscid": AppConfig().clientName,
        "sessionId": AppConfig().token,
      },
    );
  }

  // [Referal Master Report]
  Future<Object?> getReferalRepots(BuildContext context) {
    return NetworkManager(context).postAPI(
      apiName: APINames.getReferalReport,
      postBodyData: {
        "gscid": AppConfig().clientName,
        "sessionId": AppConfig().token,
      },
    );
  }

  // [User rights Report]
  Future<Object?> getUserRightsReport(BuildContext context) {
    return NetworkManager(context).postAPI(
      apiName: APINames.getuserRights,
      postBodyData: {
        "gscid": AppConfig().clientName,
        "sessionId": AppConfig().token,
      },
    );
  }

  // [DATA ANALYSIS REPORT]
  Future<Object?> getDataAnalysisReport(BuildContext context) {
    return NetworkManager(context).postAPI(
      apiName: APINames.getAllStats,
      postBodyData: {
        "gscid": AppConfig().clientName,
        "sessionId": AppConfig().token,
      },
    );
  }

  // [Re IPV REPORT]
  Future<Object?> getReIpvReport(BuildContext context) {
    return NetworkManager(context).postAPI(
      apiName: APINames.getReIpvReport,
      postBodyData: {
        "gscid": AppConfig().clientName,
        "sessionId": AppConfig().token,
      },
    );
  }

  // [AUTHORIZE USER REPORT]
  Future<Object?> getAuthorizeUserReport(BuildContext context) {
    return NetworkManager(context).postAPI(
      apiName: APINames.getAuthorizeUserReport,
      postBodyData: {
        "gscid": AppConfig().clientName,
        "sessionId": AppConfig().token,
      },
    );
  }

  // [REJECTION REPORT]
  Future<Object?> getRejectionReport(BuildContext context) {
    return NetworkManager(context).postAPI(
      apiName: APINames.getRejectionReport,
      postBodyData: {
        "gscid": AppConfig().clientName,
        "sessionId": AppConfig().token,
      },
    );
  }

  // [PENNY DROP REPORT]
  Future<Object?> getPennyDropReport(BuildContext context) {
    return NetworkManager(context).postAPI(
      apiName: APINames.getPennyDropReport,
      postBodyData: {
        "gscid": AppConfig().clientName,
        "sessionId": AppConfig().token,
      },
    );
  }

// [REKYC STATUS REPORT]
  Future<Object?> getAdminReKycStatusReport(BuildContext context) {
    return NetworkManager(context).postAPI(
      apiName: APINames.getReKycStatusReport,
      postBodyData: {
        "gscid": AppConfig().clientName,
        "sessionId": AppConfig().token,
      },
    );
  }

  // [REKYC DATA ANALYSIS REPORT]
  Future<Object?> getAdminReKycDataAnalysisReport(BuildContext context) {
    return NetworkManager(context).postAPI(
      apiName: APINames.getReKycDataAnalysisReport,
      postBodyData: {
        "gscid": AppConfig().clientName,
        "sessionId": AppConfig().token,
      },
    );
  }

  // [REKYC GLOBAL SEARCH REPORT]
  Future<Object?> getReKycGlobalSearchReport(BuildContext context) {
    return NetworkManager(context).postAPI(
      apiName: APINames.getReKycGlobalSearchReport,
      postBodyData: {
        "gscid": AppConfig().clientName,
        "sessionId": AppConfig().token,
      },
    );
  }

  Future<int> saveImage(String docType, Uint8List PlattobjFile, List<int> objFile, String proofType, String uniqueId, String extension, CommonDataGridTableResponseModel? globalRespObj) async {
    var url = getBaseAPIUrl() + "uploadDocuments";

    Uri uri = Uri.parse(url);
    var request = http.MultipartRequest('POST', uri);
    // request.headers["<custom header>"] = "content";
    request.fields['email_id'] = globalRespObj!.emailId;
    request.fields['mobile_no'] = globalRespObj.mobileNo;
    request.fields['type'] = docType;
    request.fields['imageName'] = globalRespObj.pan + docType + uniqueId;
    request.fields['prooftype'] = proofType;
    request.fields['extension'] = extension;
    request.fields['pan'] = globalRespObj.pan;
    request.fields['uploadType'] = 'admin';

    request.files.add(
      new http.MultipartFile.fromBytes('image_path', objFile, filename: 'dummy.png'),
    );

    final uploadResponse = await request.send();
    final response = await http.Response.fromStream(uploadResponse);

    var flag = 0;
    if (response.statusCode == 200) {
      flag = 0;
      return flag;
    } else {
      flag = 1;
      return flag;
    }
  }

  updateEkycFromAdminPersonalDetails(BuildContext context, String first, String middle, String last, String chosenValue, CommonDataGridTableResponseModel? globalRespObj) {
    return NetworkManager(context).postAPI(
      apiName: APINames.updateEkycFromAdmin,
      postBodyData: {
        "firstname_mother": first,
        "middlename_mother": middle,
        "lastname_mother": last,
        "maritalstatus": chosenValue,
      },
      params: {
        "email_id": globalRespObj!.emailId.toString(),
        "mobile_no": globalRespObj.mobileNo.toString(),
        "pan": globalRespObj.pan.toString(),
      },
    );
  }

  updateEkycFromAdminContactDetails(
    BuildContext context,
    String resAddr1,
    String resAddr2,
    String resAddrstate,
    String resAddcity,
    String resAddpincode,
    String paramadd1,
    String paramadd2,
    String paramaddstate,
    String paramaddcity,
    String paramaddpincode,
    CommonDataGridTableResponseModel? globalRespObj,
  ) {
    return NetworkManager(context).postAPI(
      apiName: APINames.updateEkycFromAdmin,
      postBodyData: {
        "res_addr_1": resAddr1,
        "res_addr_2": resAddr2,
        "res_addr_state": resAddrstate,
        "res_addr_city": resAddcity,
        "res_addr_pincode": resAddpincode,
        "parm_addr_1": paramadd1,
        "parm_addr_2": paramadd2,
        "parm_addr_state": paramaddstate,
        "parm_addr_city": paramaddcity,
        "parm_addr_pincode": paramaddpincode,
      },
      params: {
        "email_id": globalRespObj!.emailId.toString(),
        "mobile_no": globalRespObj.mobileNo.toString(),
        "pan": globalRespObj.pan.toString(),
      },
    );
  }
}
