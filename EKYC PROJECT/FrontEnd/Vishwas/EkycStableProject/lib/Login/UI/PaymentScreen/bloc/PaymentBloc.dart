import 'package:e_kyc/Login/UI/Configuration/AppConfig.dart';
import 'package:e_kyc/Login/UI/HomeScreen/HomeScreenLarge.dart';
import 'package:e_kyc/Login/UI/HomeScreen/HomeScreenSmall.dart';
import 'package:e_kyc/Login/UI/Login/model/LoginUserDetailModelResponse.dart';
import 'package:e_kyc/Login/UI/Login/repository/LoginRepository.dart';
import 'package:e_kyc/Login/UI/PaymentScreen/repository/PaymentRepository.dart';
import 'package:e_kyc/Utilities/CommonDialog.dart';
import 'package:e_kyc/Utilities/ResponseModel.dart';
import 'package:flutter/material.dart';

class PaymentBloc {
  PaymentRepository _repository = new PaymentRepository();
  var ctx;

  void dispose() {}

  navtodocumentdetail(
      BuildContext context, String pack, String screenname) async {
    try {
      this.ctx = context;
      AppConfig().showLoaderDialog(context); // show loader

      ResponseModel obj = await this._repository.savePaymentDetailsAPI(
            LoginRepository.loginEmailId,
            LoginRepository.loginMobileNo,
            pack,
          );
      AppConfig().dismissLoaderDialog(context); //// dismiss loader
      if (obj.response.errorCode == "0") {
        LoginUserDetailModelResponse obj = await LoginRepository()
            .getEkycUserDetails(
                LoginRepository.loginEmailId,
                LoginRepository.loginMobileNo,
                LoginRepository.loginFullName);
        LoginRepository.loginDetailsResObjGlobal = obj;
        if (screenname.toString().contains("small")) {
          HomeScreenSmall.screensStreamSmall.sink
              .add(Ekycscreenamesmall.documentdetailscreen);
        } else {
          HomeScreenLarge.screensStreamLarge.sink
              .add(Ekycscreenamelarge.documentdetailscreen);
        }
      } else {
        print("no data");
        showAlert("failed");
      }
    } catch (exception) {
      print("no data" + exception);
      // showAlert("failed");
      CommonErrorDialog.showServerErrorDialog(context, 'Something went wrong');
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
}
