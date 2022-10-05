import 'package:e_kyc/Login/UI/EsignScreen/repository/EsignRepository.dart';
import 'package:e_kyc/Login/UI/HomeScreen/HomeScreenLarge.dart';
import 'package:e_kyc/Login/UI/Login/View/LoginUI.dart';
import 'package:e_kyc/Login/UI/Login/repository/LoginRepository.dart';

import 'package:e_kyc/Login/UI/PanDetailsScreen/bloc/PanDetailsBloc.dart';
import 'package:e_kyc/Login/UI/ThemeColors.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_svg/flutter_svg.dart';

import 'package:intl/intl.dart';

class PanDetailsScreenLarge extends StatefulWidget {
  @override
  _PanDetailsScreenLargeState createState() => _PanDetailsScreenLargeState();
}

///////
class _PanDetailsScreenLargeState extends State<PanDetailsScreenLarge> {
  PanDetailsBloc panbloc = new PanDetailsBloc();
  DateTime selectedDate = DateTime.now();
  var panerrormsg = "";
  var fullnameerrormsg = "";
  var doberrormsg = "";
  var globalRespObj = LoginRepository.loginDetailsResObjGlobal;

  @override
  void dispose() {
    this.panbloc.dispose();
    this.panbloc.panTextController.dispose();
    this.panbloc.fullnameTextController.dispose();
    this.panbloc.dobTextController.dispose();
    super.dispose();
  }

  @override
  void initState() {
    HomeScreenLarge.percentageFlagLarge.add("0.142");
    if (globalRespObj != null) {
      print(globalRespObj.response.errorCode);
      if (globalRespObj.response.errorCode == "0") {
        var stage = int.parse(globalRespObj.response.data.message[0].stage);
        if (stage >= 0) {
          this.panbloc.panTextController.text =
              (globalRespObj.response.data.message[0].pan).toUpperCase();
          this.panbloc.fullnameTextController.text =
              (globalRespObj.response.data.message[0].panfullname)
                  .toUpperCase();
          this.panbloc.dobTextController.text =
              globalRespObj.response.data.message[0].dob;
        }
      }
    }

    // this.panbloc.panTextController.addListener(() {
    //   setState(() {
    //     final text = this.panbloc.panTextController.text.toUpperCase();
    //     this.panbloc.panTextController.value =
    //         this.panbloc.panTextController.value.copyWith(
    //               text: text,
    //               selection: TextSelection(
    //                   baseOffset: text.length, extentOffset: text.length),
    //             );
    //   });
    // });
    // this.panbloc.fullnameTextController.addListener(() {
    //   setState(() {
    //     final text = this.panbloc.fullnameTextController.text.toUpperCase();
    //     this.panbloc.fullnameTextController.value =
    //         this.panbloc.fullnameTextController.value.copyWith(
    //               text: text,
    //               selection: TextSelection(
    //                   baseOffset: text.length, extentOffset: text.length),
    //             );
    //   });
    // });
    this.panbloc.validatepan.listen((event) {
      setState(() {
        this.panerrormsg = event;
      });
    });
    this.panbloc.validatefullname.listen((event) {
      setState(() {
        this.fullnameerrormsg = event;
      });
    });
    this.panbloc.validatedob.listen((event) {
      setState(() {
        this.doberrormsg = event;
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
                    child: Container(
                        child: Column(
      children: [
        header(context),
        padDetailsForm(context),
        continuebtn(context),
      ],
    ))))));
  }

  header(BuildContext context) {
    return Container(
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Container(
            width: 100,
            height: 60,
            child: Image.asset(
              'asset/images/Checklist.png',
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
                    "PAN DETAILS",
                    style: TextStyle(
                      color: Color(0xFFFAB804),
                      fontFamily: 'century_gothic',
                      fontSize: 15,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                ],
              ),
            ),
          ),
          InkWell(
            onTap: () {
              LoginRepository.Esignflag = 0;
              // Navigator.push(
              //   context,
              //   MaterialPageRoute(builder: (context) => LoginUI()),
              // );
              Navigator.of(context).pushAndRemoveUntil(
                MaterialPageRoute(
                  builder: (context) => LoginUI(),
                ),
                (Route<dynamic> route) => false,
              );
            },
            child: Container(
              child: Row(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: [
                  Container(
                    width: 50,
                    height: 20,
                    child: SvgPicture.asset(
                      'asset/svg/signout.svg',
                      color: Colors.blue,
                      height: 25,
                      width: 25,
                    ),
                  ),
                  Container(
                    padding: EdgeInsets.only(right: 15),
                    child: Text(
                      "SIGN OUT",
                      style: TextStyle(
                          color: Color(0xFF0066CC),
                          fontFamily: 'century_gothic',
                          fontSize: 11,
                          fontWeight: FontWeight.w600),
                    ),
                  ),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }

  padDetailsForm(BuildContext context) {
    return Container(
      alignment: Alignment.centerLeft,
      margin: EdgeInsets.only(left: 55, top: 100),
      child: Container(
        child: Column(
          children: [
            Container(
              width: 300,
              child: Column(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Container(
                    child: Text(
                      "ENTER  YOUR  PAN",
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
                      controller: this.panbloc.panTextController,
                      onChanged: (value) {
                        this.panbloc.panTextController.value = TextEditingValue(
                            text: value.toUpperCase(),
                            selection:
                                this.panbloc.panTextController.selection);
                      },
                      maxLength: 10,
                      inputFormatters: <TextInputFormatter>[
                        FilteringTextInputFormatter.allow(
                            RegExp("[0-9a-zA-Z]")),
                      ],
                      decoration: InputDecoration(
                        hintText: "AAAAA5879E",
                        hintStyle: TextStyle(
                            fontSize: 15.0,
                            fontFamily: 'century_gothic',
                            color: Colors.grey[400]),
                        contentPadding: EdgeInsets.only(
                            top: 20,
                            left: 0,
                            bottom: 10), // add padding to adjust text
                        isDense: true,
                        errorText: panerrormsg.isEmpty ? null : panerrormsg,
                      ),
                    ),
                  ),
                  Container(
                    child: Text(
                      "ENTER  FULL  NAME",
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
                      controller: this.panbloc.fullnameTextController,
                      onChanged: (value) {
                        this.panbloc.fullnameTextController.value =
                            TextEditingValue(
                                text: value.toUpperCase(),
                                selection: this
                                    .panbloc
                                    .fullnameTextController
                                    .selection);
                      },
                      style: TextStyle(fontFamily: 'century_gothic'),
                      inputFormatters: [
                        FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z ]'))
                      ],
                      decoration: InputDecoration(
                        hintText: "FULL NAME",
                        hintStyle: TextStyle(
                            fontSize: 15.0,
                            fontFamily: 'century_gothic',
                            color: Colors.grey[400]),
                        contentPadding: EdgeInsets.only(
                            top: 20,
                            left: 0,
                            bottom: 10), // add padding to adjust text
                        isDense: true,
                        errorText:
                            fullnameerrormsg.isEmpty ? null : fullnameerrormsg,
                      ),
                    ),
                  ),
                  Container(
                    margin: EdgeInsets.only(top: 20),
                    child: Text(
                      "DATE  OF  BIRTH",
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
                      onTap: () {
                        _selectDate(context);
                      },
                      textAlign: TextAlign.left,
                      readOnly: true,
                      controller: this.panbloc.dobTextController,
                      decoration: InputDecoration(
                        hintText: "DD-MM-YYYY",
                        hintStyle: TextStyle(
                            fontSize: 15.0,
                            fontFamily: 'century_gothic',
                            color: Colors.grey[400]),
                        contentPadding: EdgeInsets.only(
                            top: 20,
                            left: 0,
                            bottom: 10), // add padding to adjust text
                        isDense: true,
                        suffixIcon: Padding(
                          padding: EdgeInsets.only(top: 15, bottom: 10),
                          // add padding to adjust icon
                          child: Image.asset(
                            'asset/images/calendar.png',
                            height: 8,
                            width: 8,
                          ),
                        ),
                        errorText: doberrormsg.isEmpty ? null : doberrormsg,
                      ),
                    ),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }

  continuebtn(BuildContext context) {
    return Container(
      margin: EdgeInsets.only(right: 100, top: 100),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.end,
        mainAxisAlignment: MainAxisAlignment.end,
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
                foregroundColor: MaterialStateProperty.all<Color>(Colors.white),
                shape: MaterialStateProperty.all<RoundedRectangleBorder>(
                    RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(15),
                        side: BorderSide(color: Colors.blue[200]))),
                backgroundColor:
                    MaterialStateProperty.all<Color>(Color(0xFF0074C4)),
              ),
              onPressed: () {
                savPandetails(context);
              },
            ),
          ),
        ],
      ),
    );
  }

  _selectDate(BuildContext context) async {
    final ThemeData theme = Theme.of(context);
    assert(theme.platform != null);
    switch (theme.platform) {
      case TargetPlatform.android:
      case TargetPlatform.fuchsia:
      case TargetPlatform.linux:
      case TargetPlatform.windows:
        return buildMaterialDatePicker(context);
      case TargetPlatform.iOS:
      case TargetPlatform.macOS:
        return buildCupertinoDatePicker(context);
    }
  }

  /// This builds material date picker in Android
  buildMaterialDatePicker(BuildContext context) async {
    var newDate =
        new DateTime(selectedDate.year, selectedDate.month, selectedDate.day);
    final DateTime picked = await showDatePicker(
      context: context,
      initialDate: newDate,
      firstDate: DateTime(1950),
      lastDate: newDate,
      builder: (context, child) {
        return Theme(
          data: ThemeData.light(),
          child: child,
        );
      },
    );
    if (picked != null && picked != newDate) {
      newDate = picked;
      final dateformate = DateFormat("dd-MM-yyyy");
      final date = dateformate.format(newDate);

      setState(() {
        this.panbloc.dobTextController = TextEditingController(text: date);
      });
    }
  }

  /// This builds cupertion date picker in iOS
  buildCupertinoDatePicker(BuildContext context) {
    var newDate =
        new DateTime(selectedDate.year, selectedDate.month, selectedDate.day);
    showModalBottomSheet(
        context: context,
        builder: (BuildContext builder) {
          return Container(
            height: MediaQuery.of(context).copyWith().size.height / 3,
            color: Colors.white,
            child: CupertinoDatePicker(
              mode: CupertinoDatePickerMode.date,
              onDateTimeChanged: (picked) {
                if (picked != null && picked != newDate) newDate = picked;

                final dateformate = DateFormat("dd-MM-yyyy");
                final date = dateformate.format(newDate);

                setState(() {
                  this.panbloc.dobTextController =
                      TextEditingController(text: date);
                });
              },
              initialDateTime: newDate,
              maximumDate: DateTime.now(),
            ),
          );
        });
  }

  void savPandetails(BuildContext context) {
    this.panbloc.panvalidationcheck(context, "large");
  }
}
