import 'dart:math';

import 'package:bloc/bloc.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_application_1/LoginScreen/Screen/home_page.dart';

class LoginBloc extends Cubit<LoginObj> {
  LoginBloc() : super(LoginObj());

  void loginValidation(TextEditingController userNameController,
      TextEditingController passWordController, BuildContext context) {
    bool validation = true;
    if (userNameController.text.isEmpty) {
      validation = false;
      state.userNameError = "Please Enter Email ID";
      emit(LoginObj(userNameError: state.userNameError));
    } else if (passWordController.text.isEmpty) {
      validation = false;
      state.passWordError = "Please Enter Passowrd";
      emit(LoginObj(passWordError: state.passWordError));
    }
    if (validation) {
      print("Go to next screen");
      if (userNameController.text == "abc@gmail.com" &&
          passWordController.text == "123456") {
        Navigator.push(
          context,
          MaterialPageRoute(
            builder: (context) => HomePage(name: userNameController.text),
          ),
        );
      } else {
        showAlertDialog(context);
      }
    }
  }

  showAlertDialog(BuildContext context) {
    // set up the button
    Widget okButton = TextButton(
      child: Text("OK"),
      onPressed: () {
        Navigator.pop(context);
      },
    );

    // set up the AlertDialog
    AlertDialog alert = AlertDialog(
      title: Text("Flutter Bloc & Cubit"),
      content: Text("invalid credentials"),
      actions: [
        okButton,
      ],
    );

    // show the dialog
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return alert;
      },
    );
  }
}

class LoginObj {
  String userNameError = "";
  String passWordError = "";

  LoginObj({this.userNameError = "", this.passWordError = ""});
}
