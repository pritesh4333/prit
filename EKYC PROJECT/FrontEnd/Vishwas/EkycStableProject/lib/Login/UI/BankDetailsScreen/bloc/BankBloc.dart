// ignore_for_file: unused_import

import 'package:e_kyc/Login/UI/BankDetailsScreen/model/BankResponseModel.dart';
import 'package:e_kyc/Login/UI/BankDetailsScreen/repository/BankRepository.dart';
import 'package:e_kyc/Login/UI/Configuration/AppConfig.dart';
import 'package:e_kyc/Login/UI/HomeScreen/HomeScreenLarge.dart';
import 'package:e_kyc/Login/UI/HomeScreen/HomeScreenSmall.dart';
import 'package:e_kyc/Login/UI/Login/model/LoginUserDetailModelResponse.dart';
import 'package:e_kyc/Login/UI/Login/repository/LoginRepository.dart';
import 'package:e_kyc/Utilities/CommonDialog.dart';
import 'package:e_kyc/Utilities/ResponseModel.dart';
import 'package:flutter/material.dart';
import 'package:rxdart/rxdart.dart';

class BankBloc {
  BankRepository _repository = new BankRepository();

  var bankvalidation;
  var ctx;

  var ifscTextController = TextEditingController(text: '');
  var accontnumberTextController = TextEditingController(text: '');
  var banknameTextController = TextEditingController(text: '');

  final BehaviorSubject<String> _ifsc = BehaviorSubject<String>();
  Stream<String> get validateifsc => this._ifsc.stream;

  final BehaviorSubject<String> _accountnumber = BehaviorSubject<String>();
  Stream<String> get validateaccountnumber => this._accountnumber.stream;

  final BehaviorSubject<String> _bankname = BehaviorSubject<String>();
  Stream<String> get validatebankname => this._bankname.stream;

  void dispose() {
    _ifsc.close();
    _accountnumber.close();
    _bankname.close();
  }

  bankvalidationcheck(BuildContext context, String screenname) async {
    this.ctx = context;
    bankvalidation = true;

    if (ifscTextController.text.length == 0) {
      this._ifsc.sink.add("Please enter ifsc code");
      bankvalidation = false;
    } else if (!validateIFSCcode(ifscTextController.text)) {
      this._ifsc.sink.add("Please enter valid ifsc code");
      bankvalidation = false;
    } else {
      this._ifsc.sink.add("");
    }

    if (accontnumberTextController.text.length == 0) {
      this._accountnumber.sink.add("Please enter bank account number");
      bankvalidation = false;
    } else {
      this._accountnumber.sink.add("");
    }

    if (banknameTextController.text.length == 0) {
      this._bankname.sink.add("Please enter bank name");
      bankvalidation = false;
    } else {
      this._bankname.sink.add("");
    }

    if (bankvalidation) {
      print("save bank data");
      navtopaymentdetail(context, screenname);
    } else {}
  }

  navtopaymentdetail(BuildContext context, String screenname) async {
    try {
      AppConfig().showLoaderDialog(context); // show loader

      BankResponseModel obj = await this._repository.saveBankDetailsAPI(
          LoginRepository.loginEmailId,
          LoginRepository.loginMobileNo,
          ifscTextController.text,
          accontnumberTextController.text,
          banknameTextController.text);
      AppConfig().dismissLoaderDialog(context);
      if (obj == null) {
        showAlert("Invalid IFSC Code.");
      } //// dismiss loader
      else if (obj.response.errorCode == "0") {
        LoginUserDetailModelResponse obj = await LoginRepository()
            .getEkycUserDetails(LoginRepository.loginEmailId,
                LoginRepository.loginMobileNo, LoginRepository.loginFullName);
        LoginRepository.loginDetailsResObjGlobal = obj;
        if (screenname.toString().contains("small")) {
          HomeScreenSmall.screensStreamSmall.sink
              .add(Ekycscreenamesmall.paymentdetailscreen);
        } else {
          HomeScreenLarge.screensStreamLarge.sink
              .add(Ekycscreenamelarge.paymentdetailscreen);
        }
      } else {
        if (obj.response.data.message != null) {
          showAlert(obj.response.data.message);
        } else {
          showAlert(obj.response.data.status);
        }
      }
    } catch (exception) {
      print("no data" + exception);
      CommonErrorDialog.showServerErrorDialog(context, 'Something went wrong');
      // showAlert("failed");
    }
  }

  Future<dynamic> showAlert(String msg) {
    return showDialog(
      context: ctx,
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

  validateIFSCcode(String text) {
    RegExp regExp = new RegExp("^[A-Za-z]{4}0[A-Z0-9a-z]{6}");
    if (regExp.hasMatch(text)) {
      return true;
    } else {
      return false;
    }
  }
}
