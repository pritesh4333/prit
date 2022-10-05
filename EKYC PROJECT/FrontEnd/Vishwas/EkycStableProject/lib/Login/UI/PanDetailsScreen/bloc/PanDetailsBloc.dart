import 'package:e_kyc/Login/UI/Configuration/AppConfig.dart';
import 'package:e_kyc/Login/UI/HomeScreen/HomeScreenLarge.dart';
import 'package:e_kyc/Login/UI/HomeScreen/HomeScreenSmall.dart';
import 'package:e_kyc/Login/UI/Login/repository/LoginRepository.dart';
import 'package:e_kyc/Login/UI/PanDetailsScreen/repository/PanDetailRepository.dart';
import 'package:e_kyc/Utilities/ResponseModel.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:rxdart/rxdart.dart';

class PanDetailsBloc {
  PanDetailRepository _repository = new PanDetailRepository();

  var panvalidation;
  var ctx;

  var panTextController = TextEditingController();
  var fullnameTextController = TextEditingController();
  var dobTextController = TextEditingController();

  final BehaviorSubject<String> _pan = BehaviorSubject<String>();
  Stream<String> get validatepan => this._pan.stream;

  final BehaviorSubject<String> _fullname = BehaviorSubject<String>();
  Stream<String> get validatefullname => this._fullname.stream;

  final BehaviorSubject<String> _dob = BehaviorSubject<String>();
  Stream<String> get validatedob => this._dob.stream;

  void dispose() {
    this._pan.close();
    this._fullname.close();
    this._dob.close();

    this.panTextController.dispose();
    this.fullnameTextController.dispose();
    this.dobTextController.dispose();
  }

  panvalidationcheck(BuildContext context, String screenname) async {
    this.ctx = context;
    panvalidation = true;

    if (panTextController.text.length == 0) {
      this._pan.sink.add("Please enter pan no");
      panvalidation = false;
    } else if (!validatePAN(panTextController.text)) {
      this._pan.sink.add("Please enter valid pan no");
      panvalidation = false;
    } else {
      this._pan.sink.add("");
    }

    var fullName = fullnameTextController.text.trim();
    if (fullnameTextController.text.length == 0 || fullName.isEmpty) {
      this._fullname.sink.add("Please enter full name");
      panvalidation = false;
    } else {
      this._fullname.sink.add("");
    }

    if (dobTextController.text.length == 0) {
      this._dob.sink.add("Please enter date of birth");
      panvalidation = false;
    } else {
      this._dob.sink.add("");
    }

    var selectedDate = dobTextController.text;
    String datePattern = "dd-MM-yyyy";
    DateTime birthDate = DateFormat(datePattern).parse(selectedDate);
    DateTime adultDate =
        DateTime(birthDate.year + 18, birthDate.month, birthDate.day);

    if (DateTime.now().isBefore(adultDate)) {
      print('Your age is below 18 years');
      this._dob.sink.add('Please select DOB above 18+ years.');
      panvalidation = false;
    } else {
      print('Your age is above 18 years');
      this._dob.sink.add('');
    }

    if (panvalidation) {
      print("save pan data");
      navtopersonaldetail(context, screenname);
    } else {}
  }

  navtopersonaldetail(BuildContext context, String screenname) async {
    try {
      AppConfig().showLoaderDialog(context); // show loader
      ResponseModel obj = await this._repository.savePanDetailsAPI(
          LoginRepository.loginEmailId,
          LoginRepository.loginMobileNo,
          panTextController.text,
          fullnameTextController.text,
          dobTextController.text);
      AppConfig().dismissLoaderDialog(context); //// dismiss loader
      if (obj.response.errorCode == "0") {
        if (screenname.toString().contains("small")) {
          HomeScreenSmall.screensStreamSmall.sink
              .add(Ekycscreenamesmall.personaldetailscreen);
        } else {
          HomeScreenLarge.screensStreamLarge.sink
              .add(Ekycscreenamelarge.personaldetailscreen);
        }
      } else if (obj.response.errorCode == "1") {
        print("no data" + obj.response.errorCode);
        this._pan.sink.add("Please enter valid pan no");
      } else if (obj.response.errorCode == "2") {
        print("no data" + obj.response.errorCode);
        this._fullname.sink.add("Please enter valid full name");
      } else if (obj.response.errorCode == "3") {
        print("no data" + obj.response.errorCode);
        this._pan.sink.add("Please enter valid pan no");
      } else if (obj.response.errorCode == "4") {
        print("no data" + obj.response.errorCode);
        showAlert("No data found");
      }
    } catch (exception) {
      print("no data" + exception);
      showAlert("No data found");
    }
  }

  validatePAN(String text) {
    RegExp regExp = new RegExp("[A-Z]{5}[0-9]{4}[A-Z]{1}");
    if (regExp.hasMatch(text)) {
      return true;
    } else {
      return false;
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
