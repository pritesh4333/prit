import 'package:ekyc_admin/Screens/AdminData/models/response/common_data_grid_table_response_model.dart';
import 'package:ekyc_admin/Screens/AdminData/ui/admin_data_grid.dart';
import 'package:ekyc_admin/Screens/AdminData/ui/reports/bloc/personal_detail_bloc.dart';

import 'package:ekyc_admin/Screens/DashBoardScreens/block/dashboard_bloc.dart';
import 'package:ekyc_admin/Utilities/greek_textstyle.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

import '../../../Utilities/UpperCaseTextFormatter.dart';

class personalDetails_web_view extends StatefulWidget {
  final DashBoardBloc? dashBoardBloc;

  const personalDetails_web_view({Key? key, this.dashBoardBloc}) : super(key: key);

  @override
  State<personalDetails_web_view> createState() => personalDetailsState();
}

class personalDetailsState extends State<personalDetails_web_view> {
  personal_detail_bloc personal_bloc = new personal_detail_bloc();
  CommonDataGridTableResponseModel? globalRespObj = AdminDataGridScreen.adminDetailGlobalResponseObj;
  //Mother
  var validMotherFirstName = "";
  var validMotherMiddleName = "";
  var validMotherLastName = "";
  @override
  void initState() {
    super.initState();
    personal_bloc.motherfirstnameController.text = globalRespObj!.firstname_mother.toString();
    personal_bloc.mothermiddlenameController.text = globalRespObj!.middlename_mother.toString();
    personal_bloc.motherlastnameController.text = globalRespObj!.lastname_mother.toString();
    //Marital status
    if (globalRespObj!.maritalstatus.isNotEmpty) {
      if (globalRespObj!.maritalstatus == "Single") {
        personal_bloc.chosenValue = "Single";
      } else if (globalRespObj!.maritalstatus == "Married") {
        personal_bloc.chosenValue = "Married";
      } else {
        personal_bloc.chosenValue = "Other";
      }
    }
    //Mother Segment
    personal_bloc.validateMotherFirstName.listen((event) {
      setState(() {
        validMotherFirstName = event;
      });
    });

    personal_bloc.validateMotherMiddleName.listen((event) {
      setState(() {
        validMotherMiddleName = event;
      });
    });

    personal_bloc.validateMotherLastName.listen((event) {
      setState(() {
        validMotherLastName = event;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: Container(
          color: Colors.white,
          padding: EdgeInsets.all(22),
          child: Row(
            children: [
              Flexible(
                fit: FlexFit.loose,
                child: SingleChildScrollView(
                  scrollDirection: Axis.vertical,
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        "Username",
                        style: GreekTextStyle.personalDetailsHeading,
                      ),
                      Container(
                        margin: EdgeInsets.all(5),
                        decoration: BoxDecoration(
                          shape: BoxShape.rectangle,
                          borderRadius: BorderRadius.circular(5),
                          border: Border.all(
                            // color: const Color(0xFFE3E3E3),
                            color: Color(0x7CAFAFAF),
                            width: 1,
                          ),
                        ),
                        alignment: Alignment.center,
                        padding: const EdgeInsets.only(left: 0, top: 5, right: 0, bottom: 5),
                        child: Padding(
                          padding: const EdgeInsets.all(10.0),
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            mainAxisAlignment: MainAxisAlignment.start,
                            children: [
                              Row(
                                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                children: [
                                  Expanded(
                                    child: Container(
                                      width: 240,
                                      child: Text(
                                        "First Name",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: Container(
                                      width: 240,
                                      child: Text(
                                        "Middle Name",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: Container(
                                      padding: EdgeInsets.all(5),
                                      width: 240,
                                      child: Text(
                                        "Last Name",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                ],
                              ),
                              Row(
                                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                children: [
                                  Expanded(
                                    child: Container(
                                      color: Color.fromARGB(255, 238, 237, 237),
                                      height: 40,
                                      width: 240.0,
                                      alignment: Alignment.centerLeft,
                                      margin: EdgeInsets.only(left: 0, right: 18, top: 0),
                                      child: Padding(
                                        padding: const EdgeInsets.only(left: 5, right: 5),
                                        child: Text(
                                          globalRespObj!.firstname1.toString(),
                                          style: GreekTextStyle.textFieldHeading,
                                        ),
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: Container(
                                      color: Color.fromARGB(255, 238, 237, 237),
                                      height: 40,
                                      width: 240.0,
                                      alignment: Alignment.centerLeft,
                                      margin: EdgeInsets.only(left: 0, right: 18, top: 0),
                                      child: Padding(
                                        padding: const EdgeInsets.only(left: 5, right: 5),
                                        child: Text(
                                          globalRespObj!.middlename1.toString(),
                                          style: GreekTextStyle.textFieldHeading,
                                        ),
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: Container(
                                      color: Color.fromARGB(255, 238, 237, 237),
                                      height: 40,
                                      width: 240.0,
                                      alignment: Alignment.centerLeft,
                                      margin: EdgeInsets.only(left: 0, right: 18, top: 0),
                                      child: Padding(
                                        padding: const EdgeInsets.only(left: 5, right: 5),
                                        child: Text(
                                          globalRespObj!.lastname1.toString(),
                                          style: GreekTextStyle.textFieldHeading,
                                        ),
                                      ),
                                    ),
                                  ),
                                ],
                              )
                            ],
                          ),
                        ),
                      ),
                      Text(
                        "Father Name",
                        style: GreekTextStyle.personalDetailsHeading,
                      ),
                      Container(
                        margin: EdgeInsets.all(5),
                        decoration: BoxDecoration(
                          shape: BoxShape.rectangle,
                          borderRadius: BorderRadius.circular(5),
                          border: Border.all(
                            // color: const Color(0xFFE3E3E3),
                            color: Color(0x7CAFAFAF),
                            width: 1,
                          ),
                        ),
                        alignment: Alignment.center,
                        padding: const EdgeInsets.only(left: 0, top: 5, right: 0, bottom: 5),
                        child: Padding(
                          padding: const EdgeInsets.all(10.0),
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            mainAxisAlignment: MainAxisAlignment.start,
                            children: [
                              Row(
                                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                children: [
                                  Expanded(
                                    child: Container(
                                      width: 240,
                                      child: Text(
                                        "First Name",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: Container(
                                      width: 240,
                                      child: Text(
                                        "Middle Name",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: Container(
                                      padding: EdgeInsets.all(5),
                                      width: 240,
                                      child: Text(
                                        "Last Name",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                ],
                              ),
                              Row(
                                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                children: [
                                  Expanded(
                                    child: Container(
                                      color: Color.fromARGB(255, 238, 237, 237),
                                      height: 40,
                                      width: 240.0,
                                      alignment: Alignment.centerLeft,
                                      margin: EdgeInsets.only(left: 0, right: 18, top: 0),
                                      child: Padding(
                                        padding: const EdgeInsets.only(left: 5, right: 5),
                                        child: Text(
                                          globalRespObj!.fatherspouse.toString() == "father" ? globalRespObj!.firstname2.toString() : "",
                                          style: GreekTextStyle.textFieldHeading,
                                        ),
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: Container(
                                      color: Color.fromARGB(255, 238, 237, 237),
                                      height: 40,
                                      width: 240.0,
                                      alignment: Alignment.centerLeft,
                                      margin: EdgeInsets.only(left: 0, right: 18, top: 0),
                                      child: Padding(
                                        padding: const EdgeInsets.only(left: 5, right: 5),
                                        child: Text(
                                          globalRespObj!.fatherspouse.toString() == "father" ? globalRespObj!.middlename2.toString() : "",
                                          style: GreekTextStyle.textFieldHeading,
                                        ),
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: Container(
                                      color: Color.fromARGB(255, 238, 237, 237),
                                      height: 40,
                                      width: 240.0,
                                      alignment: Alignment.centerLeft,
                                      margin: EdgeInsets.only(left: 0, right: 18, top: 0),
                                      child: Padding(
                                        padding: const EdgeInsets.only(left: 5, right: 5),
                                        child: Text(
                                          globalRespObj!.fatherspouse.toString() == "father" ? globalRespObj!.lastname2.toString() : "",
                                          style: GreekTextStyle.textFieldHeading,
                                        ),
                                      ),
                                    ),
                                  ),
                                ],
                              )
                            ],
                          ),
                        ),
                      ),
                      Text(
                        "Mother Name",
                        style: GreekTextStyle.personalDetailsHeading,
                      ),
                      Container(
                        margin: EdgeInsets.all(5),
                        decoration: BoxDecoration(
                          shape: BoxShape.rectangle,
                          borderRadius: BorderRadius.circular(5),
                          border: Border.all(
                            // color: const Color(0xFFE3E3E3),
                            color: Color(0x7CAFAFAF),
                            width: 1,
                          ),
                        ),
                        alignment: Alignment.center,
                        padding: const EdgeInsets.only(left: 0, top: 5, right: 0, bottom: 5),
                        child: Padding(
                          padding: const EdgeInsets.all(10.0),
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            mainAxisAlignment: MainAxisAlignment.start,
                            children: [
                              Row(
                                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                children: [
                                  Expanded(
                                    child: Container(
                                      width: 240,
                                      child: Text(
                                        "First Name",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: Container(
                                      width: 240,
                                      child: Text(
                                        "Middle Name",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: Container(
                                      padding: EdgeInsets.all(5),
                                      width: 240,
                                      child: Text(
                                        "Last Name",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                ],
                              ),
                              Row(
                                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                children: [
                                  Expanded(
                                    child: Container(
                                      color: Color.fromARGB(255, 238, 237, 237),
                                      width: 240.0,
                                      height: 40,
                                      margin: EdgeInsets.only(left: 0, right: 18, top: 0),
                                      child: TextField(
                                        maxLength: 25,
                                        style: GreekTextStyle.textFieldHeading1,
                                        textCapitalization: TextCapitalization.words,
                                        // onChanged: (value) {
                                        //   this.personalDetailsBloc.resAddressLine1TextController.value = TextEditingValue(text: value.toUpperCase(), selection: this.personalDetailsBloc.resAddressLine1TextController.selection);
                                        // },
                                        controller: personal_bloc.motherfirstnameController,
                                        inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z0-9 ,./()-]')), UpperCaseTextFormatter()],
                                        decoration: InputDecoration(
                                          counterText: "",
                                          border: InputBorder.none,
                                          contentPadding: EdgeInsets.only(left: 8), // add padding to adjust text
                                          isDense: false,
                                          // hintText: "Mother First Name",
                                          errorText: validMotherFirstName.isEmpty ? null : validMotherFirstName,
                                        ),
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: Container(
                                      color: Color.fromARGB(255, 238, 237, 237),
                                      width: 240.0,
                                      height: 40,
                                      margin: EdgeInsets.only(left: 0, right: 18, top: 0),
                                      child: TextField(
                                        maxLength: 25,
                                        textCapitalization: TextCapitalization.words,
                                        style: GreekTextStyle.textFieldHeading1, // onChanged: (value) {
                                        //   this.personalDetailsBloc.resAddressLine1TextController.value = TextEditingValue(text: value.toUpperCase(), selection: this.personalDetailsBloc.resAddressLine1TextController.selection);
                                        // },
                                        controller: personal_bloc.mothermiddlenameController,
                                        inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z0-9 ,./()-]')), UpperCaseTextFormatter()],
                                        decoration: InputDecoration(
                                          counterText: "",
                                          border: InputBorder.none,
                                          contentPadding: EdgeInsets.only(left: 8), // add padding to adjust text
                                          isDense: false,
                                          // hintText: "Mother Middle Name",
                                          errorText: validMotherMiddleName.isEmpty ? null : validMotherMiddleName,
                                        ),
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: Container(
                                      color: Color.fromARGB(255, 238, 237, 237),
                                      width: 240.0,
                                      height: 40,
                                      margin: EdgeInsets.only(left: 0, right: 18, top: 0),
                                      child: TextField(
                                        maxLength: 25,
                                        style: GreekTextStyle.textFieldHeading1, // onChanged: (value) {
                                        //   this.personalDetailsBloc.resAddressLine1TextController.value = TextEditingValue(text: value.toUpperCase(), selection: this.personalDetailsBloc.resAddressLine1TextController.selection);
                                        // },
                                        controller: personal_bloc.motherlastnameController,
                                        inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z0-9 ,./()-]')), UpperCaseTextFormatter()],
                                        decoration: InputDecoration(
                                          counterText: "",
                                          border: InputBorder.none,
                                          contentPadding: EdgeInsets.only(left: 8), // add padding to adjust text
                                          isDense: false,
                                          // hintText: "Mother Last Name",
                                          errorText: validMotherLastName.isEmpty ? null : validMotherLastName,
                                        ),
                                      ),
                                    ),
                                  ),
                                ],
                              )
                            ],
                          ),
                        ),
                      ),
                      Container(
                        margin: EdgeInsets.all(5),
                        decoration: BoxDecoration(
                          shape: BoxShape.rectangle,
                          borderRadius: BorderRadius.circular(5),
                          border: Border.all(
                            // color: const Color(0xFFE3E3E3),
                            color: Color(0x7CAFAFAF),
                            width: 1,
                          ),
                        ),
                        alignment: Alignment.center,
                        padding: const EdgeInsets.only(left: 0, top: 5, right: 0, bottom: 5),
                        child: Padding(
                          padding: const EdgeInsets.all(10.0),
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            mainAxisAlignment: MainAxisAlignment.start,
                            children: [
                              Row(
                                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                children: [
                                  Expanded(
                                    child: Container(
                                      width: 240,
                                      child: Text(
                                        "Date of Birth",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: Container(
                                      width: 240,
                                      child: Text(
                                        "Gender",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: Container(
                                      padding: EdgeInsets.all(5),
                                      width: 240,
                                      child: Text(
                                        "Marital Status",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: Container(
                                      padding: EdgeInsets.all(5),
                                      width: 240,
                                      child: Text(
                                        "PAN No",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: Container(
                                      padding: EdgeInsets.all(5),
                                      width: 240,
                                      child: Text(
                                        "Aadhaar No",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                ],
                              ),
                              Row(
                                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                children: [
                                  Expanded(
                                    child: Container(
                                      color: Color.fromARGB(255, 238, 237, 237),
                                      height: 40,
                                      width: 240.0,
                                      alignment: Alignment.centerLeft,
                                      margin: EdgeInsets.only(left: 0, right: 18, top: 0),
                                      child: Padding(
                                        padding: const EdgeInsets.only(left: 5, right: 5),
                                        child: Text(
                                          globalRespObj!.dob.toString(),
                                          style: GreekTextStyle.textFieldHeading,
                                        ),
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: Container(
                                      color: Color.fromARGB(255, 238, 237, 237),
                                      height: 40,
                                      width: 240.0,
                                      alignment: Alignment.centerLeft,
                                      margin: EdgeInsets.only(left: 0, right: 18, top: 0),
                                      child: Padding(
                                        padding: const EdgeInsets.only(left: 5, right: 5),
                                        child: Text(
                                          globalRespObj!.gender.toString(),
                                          style: GreekTextStyle.textFieldHeading,
                                        ),
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: Container(
                                      color: Color.fromARGB(255, 238, 237, 237),
                                      height: 40,
                                      width: 240.0,
                                      alignment: Alignment.centerLeft,
                                      margin: EdgeInsets.only(left: 0, right: 18, top: 0),
                                      child: Padding(
                                        padding: const EdgeInsets.all(8.0),
                                        child: DropdownButton<String>(
                                          underline: SizedBox(),
                                          icon: SizedBox.shrink(),
                                          isExpanded: true,
                                          value: personal_bloc.chosenValue,
                                          style: GreekTextStyle.textFieldHeading,
                                          items: personal_bloc.maritalItems.map((String value) {
                                            return DropdownMenuItem<String>(
                                              value: value,
                                              child: Text(value),
                                            );
                                          }).toList(),
                                          onChanged: (value) {
                                            setState(() {
                                              personal_bloc.chosenValue = value.toString();
                                            });
                                          },
                                        ),
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: Container(
                                      color: Color.fromARGB(255, 238, 237, 237),
                                      height: 40,
                                      width: 240.0,
                                      alignment: Alignment.centerLeft,
                                      margin: EdgeInsets.only(left: 0, right: 18, top: 0),
                                      child: Padding(
                                        padding: const EdgeInsets.only(left: 5, right: 5),
                                        child: Text(
                                          globalRespObj!.pan.toString(),
                                          style: GreekTextStyle.textFieldHeading,
                                        ),
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: Container(
                                      color: Color.fromARGB(255, 238, 237, 237),
                                      height: 40,
                                      width: 240.0,
                                      alignment: Alignment.centerLeft,
                                      margin: EdgeInsets.only(left: 0, right: 18, top: 0),
                                      child: Padding(
                                        padding: const EdgeInsets.only(left: 5, right: 5),
                                        child: Text(
                                          globalRespObj!.aadhar.toString(),
                                          style: GreekTextStyle.textFieldHeading,
                                        ),
                                      ),
                                    ),
                                  ),
                                ],
                              )
                            ],
                          ),
                        ),
                      ),
                      Container(
                        margin: EdgeInsets.all(10),
                        decoration: BoxDecoration(
                          shape: BoxShape.rectangle,
                          borderRadius: BorderRadius.circular(5),
                          border: Border.all(
                            // color: const Color(0xFFE3E3E3),
                            color: Color(0x7CAFAFAF),
                            width: 1,
                          ),
                        ),
                        alignment: Alignment.center,
                        padding: const EdgeInsets.only(left: 0, top: 5, right: 0, bottom: 5),
                        child: Padding(
                          padding: const EdgeInsets.all(10.0),
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            mainAxisAlignment: MainAxisAlignment.start,
                            children: [
                              Row(
                                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                children: [
                                  Expanded(
                                    child: Container(
                                      width: 240,
                                      child: Text(
                                        "Residential State",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: Container(
                                      width: 240,
                                      child: Text(
                                        " ",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: Container(
                                      width: 240,
                                      child: Text(
                                        " ",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: Container(
                                      width: 240,
                                      child: Text(
                                        "Referal Code",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                ],
                              ),
                              Container(
                                margin: EdgeInsets.all(5),
                                child: Row(
                                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                  children: [
                                    Expanded(
                                      child: Container(
                                        color: Color.fromARGB(255, 238, 237, 237),
                                        height: 40,
                                        width: 240.0,
                                        alignment: Alignment.centerLeft,
                                        margin: EdgeInsets.only(left: 0, right: 18, top: 0),
                                        child: Padding(
                                          padding: const EdgeInsets.only(left: 5, right: 5),
                                          child: Text(
                                            " ",
                                            style: GreekTextStyle.textFieldHeading,
                                          ),
                                        ),
                                      ),
                                    ),
                                    Expanded(
                                      child: Container(
                                        margin: EdgeInsets.only(top: 0),
                                        child: Row(
                                          mainAxisAlignment: MainAxisAlignment.start,
                                          children: [
                                            Container(
                                              child: Transform.scale(
                                                scale: 1.5,
                                                child: Checkbox(
                                                  checkColor: Colors.blue,
                                                  activeColor: Color.fromARGB(255, 238, 237, 237),
                                                  value: false,
                                                  onChanged: (value) {
                                                    // setState(
                                                    //   () {
                                                    //     personalDetailsBloc.sameAddressCheckbox = value;
                                                    //     print('same address checkbox ${personalDetailsBloc.sameAddressCheckbox}');
                                                    //     if (personalDetailsBloc.sameAddressCheckbox) {
                                                    //       updatePermanentAddressData();
                                                    //     } else {
                                                    //       addRemoveAddressData();
                                                    //     }
                                                    //   },
                                                    // );
                                                  },
                                                ),
                                              ),
                                            ),
                                            Container(
                                              padding: EdgeInsets.only(left: 10),
                                              child: Text(
                                                'KRA Exists',
                                                style: GreekTextStyle.textFieldHeading,
                                              ),
                                            ),
                                          ],
                                        ),
                                      ),
                                    ),
                                    Expanded(
                                      child: Container(
                                        margin: EdgeInsets.only(top: 0),
                                        child: Row(
                                          mainAxisAlignment: MainAxisAlignment.start,
                                          children: [
                                            Container(
                                              child: Transform.scale(
                                                scale: 1.5,
                                                child: Checkbox(
                                                  checkColor: Colors.blue,
                                                  activeColor: Color.fromARGB(255, 238, 237, 237),
                                                  value: false,
                                                  onChanged: (value) {
                                                    // setState(
                                                    //   () {
                                                    //     personalDetailsBloc.sameAddressCheckbox = value;
                                                    //     print('same address checkbox ${personalDetailsBloc.sameAddressCheckbox}');
                                                    //     if (personalDetailsBloc.sameAddressCheckbox) {
                                                    //       updatePermanentAddressData();
                                                    //     } else {
                                                    //       addRemoveAddressData();
                                                    //     }
                                                    //   },
                                                    // );
                                                  },
                                                ),
                                              ),
                                            ),
                                            Container(
                                              padding: EdgeInsets.only(left: 10),
                                              child: Text(
                                                'Fact Client',
                                                style: GreekTextStyle.textFieldHeading,
                                              ),
                                            ),
                                          ],
                                        ),
                                      ),
                                    ),
                                    Expanded(
                                      child: Container(
                                        color: Color.fromARGB(255, 238, 237, 237),
                                        height: 40,
                                        width: 240.0,
                                        alignment: Alignment.centerLeft,
                                        margin: EdgeInsets.only(left: 0, right: 18, top: 0),
                                        child: Padding(
                                          padding: const EdgeInsets.only(left: 5, right: 5),
                                          child: Text(
                                            " ",
                                            style: GreekTextStyle.textFieldHeading,
                                          ),
                                        ),
                                      ),
                                    ),
                                  ],
                                ),
                              )
                            ],
                          ),
                        ),
                      ),
                      Container(
                        margin: EdgeInsets.all(5),
                        decoration: BoxDecoration(
                          shape: BoxShape.rectangle,
                          borderRadius: BorderRadius.circular(5),
                          border: Border.all(
                            // color: const Color(0xFFE3E3E3),
                            color: Color(0x7CAFAFAF),
                            width: 1,
                          ),
                        ),
                        alignment: Alignment.center,
                        padding: const EdgeInsets.only(left: 0, top: 5, right: 0, bottom: 5),
                        child: Padding(
                          padding: const EdgeInsets.all(10.0),
                          child: Row(
                            children: [
                              Flexible(
                                flex: 5,
                                fit: FlexFit.loose,
                                child: Column(
                                  crossAxisAlignment: CrossAxisAlignment.start,
                                  children: [
                                    Row(
                                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                      crossAxisAlignment: CrossAxisAlignment.start,
                                      children: [
                                        Expanded(
                                          child: Container(
                                            child: Text(
                                              "Mobile No Associate Code",
                                              style: GreekTextStyle.textFieldHeading,
                                            ),
                                          ),
                                        ),
                                        Expanded(
                                          child: Container(
                                            child: Text(
                                              "Trading Code",
                                              style: GreekTextStyle.textFieldHeading,
                                            ),
                                          ),
                                        ),
                                      ],
                                    ),
                                    Row(
                                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                      children: [
                                        Expanded(
                                          child: Container(
                                            color: Color.fromARGB(255, 238, 237, 237),
                                            height: 40,
                                            width: 240.0,
                                            alignment: Alignment.centerLeft,
                                            margin: EdgeInsets.only(left: 0, right: 18, top: 0),
                                            child: Padding(
                                              padding: const EdgeInsets.only(left: 5, right: 5),
                                              child: Text(
                                                " ",
                                                style: GreekTextStyle.textFieldHeading,
                                              ),
                                            ),
                                          ),
                                        ),
                                        Expanded(
                                          child: Container(
                                            color: Color.fromARGB(255, 238, 237, 237),
                                            height: 40,
                                            width: 240.0,
                                            alignment: Alignment.centerLeft,
                                            margin: EdgeInsets.only(left: 0, right: 18, top: 0),
                                            child: Padding(
                                              padding: const EdgeInsets.only(left: 5, right: 5),
                                              child: Text(
                                                " ",
                                                style: GreekTextStyle.textFieldHeading,
                                              ),
                                            ),
                                          ),
                                        ),
                                      ],
                                    ),
                                    Row(
                                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                      children: [
                                        Expanded(
                                          child: Container(
                                            margin: EdgeInsets.all(5),
                                            child: Text(
                                              "Email Id Associate Code",
                                              style: GreekTextStyle.textFieldHeading,
                                            ),
                                          ),
                                        ),
                                        Expanded(
                                          child: Container(
                                            child: Text(
                                              "Back Office Code",
                                              style: GreekTextStyle.textFieldHeading,
                                            ),
                                          ),
                                        ),
                                      ],
                                    ),
                                    Row(
                                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                      children: [
                                        Expanded(
                                          child: Container(
                                            color: Color.fromARGB(255, 238, 237, 237),
                                            height: 40,
                                            width: 240.0,
                                            alignment: Alignment.centerLeft,
                                            margin: EdgeInsets.only(left: 0, right: 18, top: 0),
                                            child: Padding(
                                              padding: const EdgeInsets.only(left: 5, right: 5),
                                              child: Text(
                                                " ",
                                                style: GreekTextStyle.textFieldHeading,
                                              ),
                                            ),
                                          ),
                                        ),
                                        Expanded(
                                          child: Container(
                                            color: Color.fromARGB(255, 238, 237, 237),
                                            height: 40,
                                            width: 240.0,
                                            alignment: Alignment.centerLeft,
                                            margin: EdgeInsets.only(left: 0, right: 18, top: 0),
                                            child: Padding(
                                              padding: const EdgeInsets.only(left: 5, right: 5),
                                              child: Text(
                                                " ",
                                                style: GreekTextStyle.textFieldHeading,
                                              ),
                                            ),
                                          ),
                                        ),
                                      ],
                                    ),
                                  ],
                                ),
                              ),
                              Flexible(
                                flex: 1,
                                fit: FlexFit.loose,
                                child: Container(
                                  height: 130,
                                  alignment: Alignment.topLeft,
                                  child: Column(
                                    crossAxisAlignment: CrossAxisAlignment.start,
                                    children: [
                                      Container(
                                        child: Text(
                                          "Suggested Code",
                                          style: GreekTextStyle.textFieldHeading,
                                        ),
                                      ),
                                      Container(
                                        width: 150,
                                        height: 105,
                                        margin: EdgeInsets.all(5),
                                        decoration: BoxDecoration(
                                          shape: BoxShape.rectangle,
                                          borderRadius: BorderRadius.circular(5),
                                          border: Border.all(
                                            // color: const Color(0xFFE3E3E3),
                                            color: Color(0xFF00258E),

                                            width: 1,
                                          ),
                                        ),
                                        alignment: Alignment.center,
                                        padding: const EdgeInsets.only(left: 0, top: 10, right: 0, bottom: 10),
                                        child: Text(
                                          "1254475SFD",
                                          style: TextStyle(
                                            fontWeight: FontWeight.normal,
                                            fontSize: 10,
                                            color: Color(0xFF00258E),
                                          ),
                                        ),
                                      ),
                                    ],
                                  ),
                                ),
                              ),
                            ],
                          ),
                        ),
                      ),
                      Container(
                        alignment: Alignment.centerRight,
                        margin: EdgeInsets.only(right: 15, top: 0),
                        child: Container(
                          height: 45,
                          width: 125,
                          margin: EdgeInsets.all(20),
                          padding: EdgeInsets.only(top: 4, left: 4, bottom: 4, right: 4),
                          decoration: BoxDecoration(
                              border: Border.all(
                                color: Colors.blue,
                              ),
                              borderRadius: BorderRadius.circular(20) // use instead of BorderRadius.all(Radius.circular(20))
                              ),
                          child: TextButton(
                            child: Text(
                              "UPDATE",
                              style: TextStyle(color: Color(0xFFFFFFFF), fontFamily: 'century_gothic', fontSize: 12, fontWeight: FontWeight.w600),
                            ),
                            style: ButtonStyle(
                              padding: MaterialStateProperty.all<EdgeInsets>(EdgeInsets.all(1)),
                              foregroundColor: MaterialStateProperty.all<Color>(Colors.white),
                              shape: MaterialStateProperty.all<RoundedRectangleBorder>(RoundedRectangleBorder(borderRadius: BorderRadius.circular(15), side: BorderSide(color: Colors.blue))),
                              backgroundColor: MaterialStateProperty.all<Color>(Color(0xFF0074C4)),
                            ),
                            onPressed: () {
                              personal_bloc.personalDetailValidation(context, globalRespObj);
                            },
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  @override
  void dispose() {
    super.dispose();
  }
}
