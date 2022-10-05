import 'package:ekyc_admin/Helper/greek_bloc.dart';
import 'package:ekyc_admin/Screens/AdminData/models/response/common_data_grid_table_response_model.dart';

import 'package:flutter/material.dart';
import 'package:rxdart/rxdart.dart';

import '../../../repository/admin_data_repository.dart';

@immutable
class personal_detail_bloc extends GreekBlocs {
  //personal details
  var motherfirstnameController = TextEditingController();
  var mothermiddlenameController = TextEditingController();
  var motherlastnameController = TextEditingController();
  final _adminDataRepository = AdminDataRepository();

  // contact detials
  var resiadd1Controller = TextEditingController();
  var ressadd2Controller = TextEditingController();
  var resstateController = TextEditingController();
  var resicityController = TextEditingController();
  var resipincodeController = TextEditingController();

  var parmadd1Controller = TextEditingController();
  var paramadd2Controller = TextEditingController();
  var paramstateController = TextEditingController();
  var parmarcityController = TextEditingController();
  var parmarpincodeController = TextEditingController();

  //Mother Details
  final BehaviorSubject<String> _motherFirstName = BehaviorSubject<String>();
  Stream<String> get validateMotherFirstName => _motherFirstName.stream;
  final BehaviorSubject<String> _motherMiddleName = BehaviorSubject<String>();
  Stream<String> get validateMotherMiddleName => _motherMiddleName.stream;
  final BehaviorSubject<String> _motherLastName = BehaviorSubject<String>();
  Stream<String> get validateMotherLastName => _motherLastName.stream;

  // contact details
  final BehaviorSubject<String> _resiad1 = BehaviorSubject<String>();
  Stream<String> get validateResiAdd1 => _resiad1.stream;
  final BehaviorSubject<String> _resiadd2 = BehaviorSubject<String>();
  Stream<String> get validateResiadd2 => _resiadd2.stream;
  final BehaviorSubject<String> _resistate = BehaviorSubject<String>();
  Stream<String> get validateResiState => _resistate.stream;
  final BehaviorSubject<String> _resicity = BehaviorSubject<String>();
  Stream<String> get validateResiCity => _resicity.stream;
  final BehaviorSubject<String> _resipincode = BehaviorSubject<String>();
  Stream<String> get validateResiPincode => _resipincode.stream;

  final BehaviorSubject<String> _parmad1 = BehaviorSubject<String>();
  Stream<String> get validateParamAdd1 => _parmad1.stream;
  final BehaviorSubject<String> _parmaradd2 = BehaviorSubject<String>();
  Stream<String> get validateParamadd2 => _parmaradd2.stream;
  final BehaviorSubject<String> _parmarstate = BehaviorSubject<String>();
  Stream<String> get validateParamState => _parmarstate.stream;
  final BehaviorSubject<String> _parmarcity = BehaviorSubject<String>();
  Stream<String> get validateParamCity => _parmarcity.stream;
  final BehaviorSubject<String> _parampincode = BehaviorSubject<String>();
  Stream<String> get validateParamPincode => _parampincode.stream;

  String genericMessage = "";
  List<String> maritalItems = <String>["Marital Status", "Single", "Married", "Other"];

  //  'Marital status'
  var chosenValue = "Marital Status";
  @override
  void disposeBloc() {}
  @override
  void activateBloc() {}

  @override
  void deactivateBloc() {}

  void personalDetailValidation(BuildContext context, CommonDataGridTableResponseModel? globalRespObj) {
//Mother Details
    var validation = true;
    if (motherfirstnameController.text.isEmpty) {
      _motherFirstName.sink.add('Enter  mothers First Name');
      validation = false;
      genericMessage = "Enter mother's first Name";
    } else {
      _motherFirstName.sink.add("");
    }

    if (mothermiddlenameController.text.isEmpty) {
      _motherMiddleName.sink.add('Enter  mothers Middle Name');
      validation = false;
      genericMessage = "Enter mother's middle Name";
    } else {
      _motherMiddleName.sink.add("");
    }

    if (motherlastnameController.text.isEmpty) {
      _motherLastName.sink.add('Enter  mothers Last Name');
      validation = false;
      genericMessage = "Enter mother's last Name";
    } else {
      _motherLastName.sink.add("");
    }
    if (chosenValue == "Marital Status") {
      validation = false;
      showAlert("Please Select Marital Status", context);
    }

    if (validation) {
      callPersonalDetailsAPI(context, motherfirstnameController.text, mothermiddlenameController.text, motherlastnameController.text, chosenValue, globalRespObj);
    }
  }

// Call PErsonal Details Submit API
  void callPersonalDetailsAPI(BuildContext context, String first, String middle, String last, String chosenValue, CommonDataGridTableResponseModel? globalRespObj) async {
    final response = await _adminDataRepository.updateEkycFromAdminPersonalDetails(context, first, middle, last, chosenValue, globalRespObj);
    if ((response != null) && (response is Map)) {
      final mainResponseList = response['reportdata'];
      if (mainResponseList == "Rows Updated") {
        globalRespObj!.firstname_mother = first;
        globalRespObj.middlename_mother = middle;
        globalRespObj.lastname_mother = last;
        globalRespObj.maritalstatus = chosenValue;
        showAlert("Record Update Sucessfully", context);
      } else {
        showAlert("Record Update fail", context);
      }
    } else {
      throw Exception(response.toString());
    }
  }

  Future<void> callContactDetailAPI(
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
  ) async {
    final response = await _adminDataRepository.updateEkycFromAdminContactDetails(context, resAddr1, resAddr2, resAddrstate, resAddcity, resAddpincode, paramadd1, paramadd2, paramaddstate, paramaddcity, paramaddpincode, globalRespObj);
    if ((response != null) && (response is Map)) {
      final mainResponseList = response['reportdata'];
      if (mainResponseList == "Rows Updated") {
        globalRespObj!.resAddr1 = resAddr1;
        globalRespObj.resAddr2 = resAddr2;
        globalRespObj.resAddrState = resAddrstate;
        globalRespObj.resAddrCity = resAddcity;
        globalRespObj.resAddrPincode = resAddpincode;
        globalRespObj.parmAddr1 = paramadd1;
        globalRespObj.parmAddr2 = paramadd2;
        globalRespObj.parmAddrCity = paramaddcity;
        globalRespObj.parmAddrState = paramaddstate;
        globalRespObj.parmAddrPincode = paramaddpincode;
        showAlert("Record Update Sucessfully", context);
      } else {
        showAlert("Record Update fail", context);
      }
    } else {
      throw Exception(response.toString());
    }
  }

  Future<dynamic> showAlert(String msg, BuildContext context) {
    return showDialog(
      context: context,
      builder: (ctx) => AlertDialog(
        title: Text("E-KYC"),
        content: Text(msg),
        actions: <Widget>[
          TextButton(
            onPressed: () {
              Navigator.of(ctx).pop();
            },
            child: Text("OK"),
          ),
        ],
      ),
    );
  }

  void contatcDetailValidation(BuildContext context, CommonDataGridTableResponseModel? globalRespObj) {
    //Contact  Details
    var validation = true;
    if (resiadd1Controller.text.isEmpty) {
      _resiad1.sink.add('Enter  Address Line 1');
      validation = false;
      genericMessage = "Enter  Address Line 1";
    } else {
      _resiad1.sink.add("");
    }

    if (ressadd2Controller.text.isEmpty) {
      _resiadd2.sink.add('Enter  Address Line 2');
      validation = false;
      genericMessage = "Enter  Address Line 2";
    } else {
      _resiadd2.sink.add("");
    }

    if (resstateController.text.isEmpty) {
      _resistate.sink.add('Enter Residentail State');
      validation = false;
      genericMessage = "Enter Residentail State";
    } else {
      _resistate.sink.add("");
    }
    if (resicityController.text.isEmpty) {
      _resicity.sink.add('Enter Residentail City');
      validation = false;
      genericMessage = "Enter Residentail City";
    } else {
      _resicity.sink.add("");
    }

    if (resipincodeController.text.isEmpty) {
      _resipincode.sink.add('Enter Residentail Pincode');
      validation = false;
      genericMessage = "Enter Residentail Pincode";
    } else {
      _resipincode.sink.add("");
    }

    if (parmadd1Controller.text.isEmpty) {
      _parmad1.sink.add('Enter  Address Line 1');
      validation = false;
      genericMessage = "Enter  Address Line 1";
    } else {
      _parmad1.sink.add("");
    }

    if (paramadd2Controller.text.isEmpty) {
      _parmaradd2.sink.add('Enter  Address Line 2');
      validation = false;
      genericMessage = "Enter  Address Line 2";
    } else {
      _parmaradd2.sink.add("");
    }

    if (paramstateController.text.isEmpty) {
      _parmarstate.sink.add('Enter Permanent State');
      validation = false;
      genericMessage = "Enter Residentail State";
    } else {
      _parmarstate.sink.add("");
    }
    if (parmarcityController.text.isEmpty) {
      _parmarcity.sink.add('Enter Permanent City');
      validation = false;
      genericMessage = "Enter Residentail City";
    } else {
      _parmarcity.sink.add("");
    }

    if (parmarpincodeController.text.isEmpty) {
      _parampincode.sink.add('Enter Permanent Pincode');
      validation = false;
      genericMessage = "Enter Residentail Pincode";
    } else {
      _parampincode.sink.add("");
    }

    if (validation) {
      callContactDetailAPI(context, resiadd1Controller.text, ressadd2Controller.text, resstateController.text, resicityController.text, resipincodeController.text, parmadd1Controller.text, paramadd2Controller.text, paramstateController.text, parmarcityController.text, parmarpincodeController.text, globalRespObj);
    }
  }
}
