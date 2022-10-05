// ignore_for_file: unused_import

// import 'dart:ffi';
import 'dart:io';
import 'dart:typed_data';

import 'package:ekyc_admin/Configuration/app_config.dart';
import 'package:ekyc_admin/Screens/AdminData/models/response/common_data_grid_table_response_model.dart';
import 'package:ekyc_admin/Screens/AdminData/ui/document_web_screen.dart';
import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';

import '../repository/admin_data_repository.dart';

class documentBloc {
  final _adminDataRepository = AdminDataRepository();

  void dispose() {}

       saveDocument(BuildContext context, String docType, String screenName, Uint8List PlattobjFile, List<int> objFile, String proofType, String uniqueId, CommonDataGridTableResponseModel? globalRespObj, String name) async {
    var extension = '';
    try {
      if (name.contains('.pdf') || name.contains('.PDF')) {
        extension = '.pdf';
      } else {
        extension = '.png';
      }
      AppConfig().showLoaderDialog(context); // show loader

      final obj = await this._adminDataRepository.saveImage(docType, PlattobjFile, objFile, proofType, uniqueId, extension, globalRespObj);

      docType = docType.toUpperCase();
      if (obj == 0) {
        AppConfig().dismissLoaderDialog(context); //// dismiss loader
        AppConfig().showAlert(context, "$docType uploaded successfully");
        return 0;
      } else if (obj == 1) {
        AppConfig().dismissLoaderDialog(context); //// dismiss loader
        AppConfig().showAlert(context, "$docType upload failed");
        return 1;
      }
    } catch (exception) {
      AppConfig().dismissLoaderDialog(context); //// dismiss loader
      AppConfig().showAlert(context, "$docType upload failed");
      return 1;
    }
  }
}
