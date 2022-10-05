// ignore_for_file: unused_import

import 'package:e_kyc/Login/UI/BankDetailsScreen/bloc/BankBloc.dart';
import 'package:e_kyc/Login/UI/EsignScreen/repository/EsignRepository.dart';
import 'package:e_kyc/Login/UI/HomeScreen/HomeScreenSmall.dart';
import 'package:e_kyc/Login/UI/Login/bloc/LoginBloc.dart';
import 'package:e_kyc/Login/UI/Login/repository/LoginRepository.dart';
import 'package:e_kyc/Login/UI/ThemeColors.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class BankScreenSmall extends StatefulWidget {
  @override
  _BankScreenSmallState createState() => _BankScreenSmallState();
}

class _BankScreenSmallState extends State<BankScreenSmall> {
  BankBloc bankbloc = new BankBloc();

  var ifscerrormsg = "";
  var accountnumbererrormsg = "";
  var banknameerrormsg = "";
  var globalRespObj = LoginRepository.loginDetailsResObjGlobal;

  @override
  void dispose() {
    this.bankbloc.dispose();
    this.bankbloc.ifscTextController.dispose();
    this.bankbloc.accontnumberTextController.dispose();
    this.bankbloc.banknameTextController.dispose();
    super.dispose();
  }

  @override
  void initState() {
    HomeScreenSmall.percentageFlagSmall.add("0.405");
    if (globalRespObj != null) {
      print(globalRespObj.response.errorCode);
      if (globalRespObj.response.errorCode == "0") {
        var stage = int.parse(globalRespObj.response.data.message[0].stage);
        if (stage >= 2) {
          var responseData = globalRespObj.response.data.message[0];
          this.bankbloc.ifscTextController.text = responseData.ifsccode;
          this.bankbloc.accontnumberTextController.text =
              responseData.accountnumber;
          this.bankbloc.banknameTextController.text = responseData.bankname;
        }
      }
    }
    // this.bankbloc.ifscTextController.addListener(() {
    //   setState(() {
    //     final text = this.bankbloc.ifscTextController.text.toUpperCase();
    //     this.bankbloc.ifscTextController.value =
    //         this.bankbloc.ifscTextController.value.copyWith(
    //               text: text,
    //               selection: TextSelection(
    //                   baseOffset: text.length, extentOffset: text.length),
    //             );
    //   });
    // });
    // this.bankbloc.accontnumberTextController.addListener(() {
    //   setState(() {
    //     final text =
    //         this.bankbloc.accontnumberTextController.text.toUpperCase();
    //     this.bankbloc.accontnumberTextController.value =
    //         this.bankbloc.accontnumberTextController.value.copyWith(
    //               text: text,
    //               selection: TextSelection(
    //                   baseOffset: text.length, extentOffset: text.length),
    //             );
    //   });
    // });
    // this.bankbloc.banknameTextController.addListener(() {
    //   setState(() {
    //     final text = this.bankbloc.banknameTextController.text.toUpperCase();
    //     this.bankbloc.banknameTextController.value =
    //         this.bankbloc.banknameTextController.value.copyWith(
    //               text: text,
    //               selection: TextSelection(
    //                   baseOffset: text.length, extentOffset: text.length),
    //             );
    //   });
    // });
    this.bankbloc.validateifsc.listen((event) {
      setState(() {
        this.ifscerrormsg = event;
      });
    });
    this.bankbloc.validateaccountnumber.listen((event) {
      setState(() {
        this.accountnumbererrormsg = event;
      });
    });
    this.bankbloc.validatebankname.listen((event) {
      setState(() {
        this.banknameerrormsg = event;
      });
    });
    super.initState();
    if (EsignRepository.timerObj != null) {
      EsignRepository.timerObj.cancel();
      EsignRepository.timerObj = null;
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: SafeArea(
            child: SingleChildScrollView(
      child: Container(
          child: Column(
        children: [
          header(),
          bankDetailsForm(),
          continuebtn(),
        ],
      )),
    )));
  }

  header() {
    return Container(
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Container(
            padding: EdgeInsets.only(top: 5),
            width: 100,
            height: 60,
            child: Image.asset(
              'asset/images/bankheader.png',
            ),
          ),
          Expanded(
            child: Container(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    "ONLINE ACCOUNT OPENING E - KYC",
                    style: TextStyle(
                        color: Color(0xFF0066CC),
                        fontSize: 15,
                        fontFamily: 'century_gothic',
                        fontWeight: FontWeight.bold),
                  ),
                  Text(
                    "BANK DETAILS",
                    style: TextStyle(
                        color: Color(0xFFFAB804),
                        fontFamily: 'century_gothic',
                        fontSize: 15,
                        fontWeight: FontWeight.w300),
                  ),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }

  bankDetailsForm() {
    return Container(
      alignment: Alignment.centerLeft,
      margin: EdgeInsets.only(left: 20, top: 50, right: 20),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Container(
            child: Text(
              "IFSC CODE",
              style: TextStyle(
                color: Color(0xFF000000),
                fontFamily: 'century_gothic',
                fontSize: 12,
                fontWeight: FontWeight.w600,
              ),
            ),
          ),
          Container(
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                Expanded(
                  child: Container(
                    child: TextField(
                      maxLength: 11,
                      controller: this.bankbloc.ifscTextController,
                      onChanged: (value) {
                        this.bankbloc.ifscTextController.value =
                            TextEditingValue(
                                text: value.toUpperCase(),
                                selection:
                                    this.bankbloc.ifscTextController.selection);
                      },
                      inputFormatters: <TextInputFormatter>[
                        FilteringTextInputFormatter.allow(
                            RegExp("[0-9a-zA-Z]")),
                      ],
                      decoration: InputDecoration(
                        hintText: "Enter IFSC Code",
                        hintStyle: TextStyle(
                            fontSize: 15.0,
                            fontFamily: 'century_gothic',
                            color: Colors.grey[400]),
                        contentPadding: EdgeInsets.only(
                            top: 20, bottom: 10), // add padding to adjust text
                        isDense: true,
                        errorText: ifscerrormsg.isEmpty ? null : ifscerrormsg,
                      ),
                    ),
                  ),
                ),
                // Container(
                //   child: Text(
                //     "Search IFSC Code",
                //     style: TextStyle(
                //         color: Color(0xFF0066CC),
                //         fontFamily: 'century_gothic',
                //         fontSize: 12,
                //         fontWeight: FontWeight.w600),
                //   ),
                // ),
              ],
            ),
          ),
          Container(
            margin: EdgeInsets.only(top: 10),
            child: Text(
              "BANK ACCOUNT NUMBER",
              style: TextStyle(
                color: Color(0xFF000000),
                fontFamily: 'century_gothic',
                fontSize: 12,
                fontWeight: FontWeight.w600,
              ),
            ),
          ),
          Container(
            child: TextField(
              controller: this.bankbloc.accontnumberTextController,
              onChanged: (value) {
                this.bankbloc.accontnumberTextController.value =
                    TextEditingValue(
                        text: value.toUpperCase(),
                        selection:
                            this.bankbloc.accontnumberTextController.selection);
              },
              maxLength: 18,
              inputFormatters: <TextInputFormatter>[
                FilteringTextInputFormatter.allow(RegExp(r'[0-9]')),
              ],
              decoration: InputDecoration(
                hintText: "Enter Bank Account Number",
                hintStyle: TextStyle(
                    fontSize: 15.0,
                    fontFamily: 'century_gothic',
                    color: Colors.grey[400]),
                contentPadding: EdgeInsets.only(
                    top: 20, left: 0, bottom: 10), // add padding to adjust text
                isDense: true,
                errorText: accountnumbererrormsg.isEmpty
                    ? null
                    : accountnumbererrormsg,
              ),
            ),
          ),
          Container(
            margin: EdgeInsets.only(top: 10),
            child: Text(
              "BANK NAME",
              style: TextStyle(
                color: Color(0xFF000000),
                fontFamily: 'century_gothic',
                fontSize: 12,
                fontWeight: FontWeight.w600,
              ),
            ),
          ),
          Container(
            child: TextField(
              controller: this.bankbloc.banknameTextController,
              onChanged: (value) {
                this.bankbloc.banknameTextController.value = TextEditingValue(
                    text: value.toUpperCase(),
                    selection: this.bankbloc.banknameTextController.selection);
              },
              inputFormatters: <TextInputFormatter>[
                FilteringTextInputFormatter.allow(RegExp("[a-zA-Z ]")),
              ],
              decoration: InputDecoration(
                hintText: "Enter Bank Name",
                hintStyle: TextStyle(
                    fontSize: 15.0,
                    fontFamily: 'century_gothic',
                    color: Colors.grey[400]),
                contentPadding: EdgeInsets.only(
                    top: 20, left: 0, bottom: 10), // add padding to adjust text
                isDense: true,
                errorText: banknameerrormsg.isEmpty ? null : banknameerrormsg,
              ),
            ),
          ),
        ],
      ),
    );
  }

  continuebtn() {
    return Container(
      margin: EdgeInsets.only(top: 10),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.center,
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Container(
            height: 45,
            width: 110,
            margin: EdgeInsets.all(20),
            padding: EdgeInsets.only(top: 4, left: 4, bottom: 4, right: 4), //
            decoration: BoxDecoration(
                border: Border.all(
                  color: Colors.blue[200],
                ),
                borderRadius: BorderRadius.circular(
                    20) // use instead of BorderRadius.all(Radius.circular(20))
                ),

            child: TextButton(
                child: Text(
                  "PROCEED".toUpperCase(),
                  style: TextStyle(
                      color: Color(0xFFFFFFFF),
                      fontFamily: 'century_gothic',
                      fontSize: 14,
                      fontWeight: FontWeight.w600),
                ),
                style: ButtonStyle(
                  padding:
                      MaterialStateProperty.all<EdgeInsets>(EdgeInsets.all(1)),
                  foregroundColor:
                      MaterialStateProperty.all<Color>(Colors.white),
                  shape: MaterialStateProperty.all<RoundedRectangleBorder>(
                      RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(15),
                          side: BorderSide(color: Colors.blue[200]))),
                  backgroundColor:
                      MaterialStateProperty.all<Color>(Color(0xFF0074C4)),
                ),
                onPressed: () {
                  savBankdetails(context);
                }),
          ),
        ],
      ),
    );
  }

  void savBankdetails(BuildContext context) {
    this.bankbloc.bankvalidationcheck(context, "small");
  }
}
