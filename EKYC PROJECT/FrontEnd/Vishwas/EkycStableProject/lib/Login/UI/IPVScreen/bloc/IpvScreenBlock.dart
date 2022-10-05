import 'package:e_kyc/Login/UI/Configuration/AppConfig.dart';
import 'package:e_kyc/Login/UI/HomeScreen/HomeScreenLarge.dart';
import 'package:e_kyc/Login/UI/HomeScreen/HomeScreenSmall.dart';
import 'package:e_kyc/Login/UI/IPVScreen/repository/IpvScreenRepository.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

class IpvScreenBlock {
  var ctx;
  IpvScreenRepository _repository = new IpvScreenRepository();

  void dispose() {}

  Future<dynamic> showAlert(BuildContext ctx, String msg, String screenname) {
    return showDialog(
      context: ctx,
      builder: (ctx) => AlertDialog(
        title: Text("E-KYC"),
        content: Text(msg),
        actions: <Widget>[
          TextButton(
            onPressed: () {
              Navigator.of(ctx).pop();
              if (msg == "Video uploaded") {
                if (screenname.toString().contains("small")) {
                  HomeScreenSmall.screensStreamSmall.sink
                      .add(Ekycscreenamesmall.esigndetailscreen);
                } else {
                  HomeScreenLarge.screensStreamLarge.sink
                      .add(Ekycscreenamelarge.esigndetailscreen);
                }
              }
            },
            child: Text("OK"),
          ),
        ],
      ),
    );
  }

  saveIPVDocument(
    BuildContext context,
    String screenName,
    List<int> selectedFile,
    String mobilevideopath,
  ) async {
    try {
      AppConfig().showLoaderDialog(context); // show loader

      final obj = await this
          ._repository
          .saveIPVvideo(screenName, selectedFile, mobilevideopath);
      AppConfig().dismissLoaderDialog(context); //// dismiss loader
      if (obj == 0) {
        print("ERROR CODE 0");
        showAlert(context, "Video uploaded", screenName);
        return true;
      } else if (obj == 1) {
        print("ERROR CODE 0");
        showAlert(context, "Video upload failed", screenName);
        return false;
      }
    } catch (exception) {
      AppConfig().dismissLoaderDialog(context); //// dismiss loader
      print("no data" + exception.toString());

      showAlert(context, "Video upload failed", screenName);
      return false;
    }
  }

  
}
