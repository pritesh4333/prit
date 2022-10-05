import 'package:ekyc_admin/Screens/AdminData/models/response/common_data_grid_table_response_model.dart';
import 'package:ekyc_admin/Screens/AdminData/ui/admin_data_grid.dart';
import 'package:ekyc_admin/Screens/DashBoardScreens/block/dashboard_bloc.dart';
import 'package:ekyc_admin/Screens/home/response.dart';
import 'package:ekyc_admin/Utilities/greek_textstyle.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

import '../../../Utilities/UpperCaseTextFormatter.dart';
import 'reports/bloc/personal_detail_bloc.dart';

class ContactDetails extends StatefulWidget {
  final DashBoardBloc? dashBoardBloc;
  const ContactDetails({Key? key, this.dashBoardBloc}) : super(key: key);

  @override
  State<ContactDetails> createState() => _ContactDetailsState();
}

class _ContactDetailsState extends State<ContactDetails> {
  personal_detail_bloc personal_bloc = new personal_detail_bloc();

  CommonDataGridTableResponseModel? globalRespObj = AdminDataGridScreen.adminDetailGlobalResponseObj;
  var validresiadd1 = "";
  var validresiadd2 = "";
  var validresistate = "";
  var validresicity = "";
  var validresipincode = "";
  var validparamadd1 = "";
  var validparamadd2 = "";
  var validparamstate = "";
  var validparamcity = "";
  var validparampincode = "";
  @override
  void initState() {
    super.initState();
    personal_bloc.resiadd1Controller.text = globalRespObj!.resAddr1.toString();
    personal_bloc.ressadd2Controller.text = globalRespObj!.resAddr2.toString();
    personal_bloc.resstateController.text = globalRespObj!.resAddrState.toString();
    personal_bloc.resicityController.text = globalRespObj!.resAddrCity.toString();
    personal_bloc.resipincodeController.text = globalRespObj!.resAddrPincode.toString();
    personal_bloc.parmadd1Controller.text = globalRespObj!.parmAddr1.toString();
    personal_bloc.paramadd2Controller.text = globalRespObj!.parmAddr2.toString();
    personal_bloc.paramstateController.text = globalRespObj!.parmAddrState.toString();
    personal_bloc.parmarcityController.text = globalRespObj!.parmAddrCity.toString();
    personal_bloc.parmarpincodeController.text = globalRespObj!.parmAddrPincode.toString();

    //Contact Segment
    personal_bloc.validateResiAdd1.listen((event) {
      setState(() {
        validresiadd1 = event;
      });
    });

    personal_bloc.validateResiadd2.listen((event) {
      setState(() {
        validresiadd2 = event;
      });
    });

    personal_bloc.validateResiState.listen((event) {
      setState(() {
        validresistate = event;
      });
    });
    personal_bloc.validateResiCity.listen((event) {
      setState(() {
        validresicity = event;
      });
    });
    personal_bloc.validateResiPincode.listen((event) {
      setState(() {
        validresipincode = event;
      });
    });
    personal_bloc.validateParamAdd1.listen((event) {
      setState(() {
        validparamadd1 = event;
      });
    });

    personal_bloc.validateParamadd2.listen((event) {
      setState(() {
        validparamadd2 = event;
      });
    });

    personal_bloc.validateParamState.listen((event) {
      setState(() {
        validparamstate = event;
      });
    });
    personal_bloc.validateParamCity.listen((event) {
      setState(() {
        validparamcity = event;
      });
    });
    personal_bloc.validateParamPincode.listen((event) {
      setState(() {
        validparampincode = event;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: Container(
          color: Colors.white,
          padding: const EdgeInsets.all(22),
          //  color: const Color.fromRGBO(244, 244, 244, 1),
          child: Row(
            children: [
              Expanded(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    const Text(
                      "Residential address",
                      style: GreekTextStyle.personalDetailsHeading,
                    ),
                    Container(
                      margin: const EdgeInsets.all(5),
                      decoration: BoxDecoration(
                        shape: BoxShape.rectangle,
                        borderRadius: BorderRadius.circular(5),
                        border: Border.all(
                          // color: const Color(0xFFE3E3E3),
                          color: const Color(0x7CAFAFAF),
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
                                const Expanded(
                                  child: SizedBox(
                                    width: 240,
                                    child: Text(
                                      "Address Line1",
                                      style: GreekTextStyle.textFieldHeading,
                                    ),
                                  ),
                                ),
                                const Expanded(
                                  child: SizedBox(
                                    width: 240,
                                    child: Text(
                                      "Address Line2",
                                      style: GreekTextStyle.textFieldHeading,
                                    ),
                                  ),
                                ),
                                Expanded(
                                  child: Container(
                                    padding: const EdgeInsets.all(5),
                                    width: 240,
                                    child: const Text(
                                      "State",
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
                                      style: GreekTextStyle.textFieldHeading1,
                                      maxLength: 60,
                                      // onChanged: (value) {
                                      //   this.personalDetailsBloc.resAddressLine1TextController.value = TextEditingValue(text: value.toUpperCase(), selection: this.personalDetailsBloc.resAddressLine1TextController.selection);
                                      // },
                                      controller: personal_bloc.resiadd1Controller,
                                      inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z0-9 ,./()-]')), UpperCaseTextFormatter()],
                                      decoration: InputDecoration(
                                        counterText: "",
                                        border: InputBorder.none,
                                        contentPadding: EdgeInsets.only(left: 8), // add padding to adjust text
                                        isDense: false,
                                        // hintText: "Address line 1",
                                        errorText: validresiadd1.isEmpty ? null : validresiadd1,
                                      ),
                                    ),
                                  ),
                                ),
                                Expanded(
                                  child: Container(
                                    color: const Color.fromARGB(255, 238, 237, 237),
                                    width: 240.0,
                                    height: 40,
                                    margin: const EdgeInsets.only(left: 0, right: 18, top: 0),
                                    child: TextField(
                                      maxLength: 60,
                                      textCapitalization: TextCapitalization.words,
                                      style: GreekTextStyle.textFieldHeading1,
                                      controller: personal_bloc.ressadd2Controller,
                                      inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z0-9 ,./()-]')), UpperCaseTextFormatter()],
                                      decoration: InputDecoration(
                                        counterText: "",
                                        border: InputBorder.none,
                                        contentPadding: EdgeInsets.only(left: 8), // add padding to adjust text
                                        isDense: false,
                                        errorText: validresiadd2.isEmpty ? null : validresiadd2,
                                      ),
                                    ),
                                  ),
                                ),
                                Expanded(
                                  child: Container(
                                    color: const Color.fromARGB(255, 238, 237, 237),
                                    width: 240.0,
                                    height: 40,
                                    margin: const EdgeInsets.only(left: 0, right: 18, top: 0),
                                    child: TextField(
                                      maxLength: 20,
                                      textCapitalization: TextCapitalization.words,
                                      style: GreekTextStyle.textFieldHeading1,
                                      controller: personal_bloc.resstateController,
                                      inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z0-9 ,./()-]')), UpperCaseTextFormatter()],
                                      decoration: InputDecoration(
                                        counterText: "",
                                        border: InputBorder.none,
                                        contentPadding: EdgeInsets.only(left: 8), // add padding to adjust text
                                        isDense: false,
                                        errorText: validresistate.isEmpty ? null : validresistate,
                                      ),
                                    ),
                                  ),
                                ),
                              ],
                            ),
                            Container(
                              margin: const EdgeInsets.all(8.0),
                              child: Row(
                                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                children: const [
                                  Expanded(
                                    child: SizedBox(
                                      width: 240,
                                      child: Text(
                                        "City",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: SizedBox(
                                      width: 240,
                                      child: Text(
                                        "Pin Code",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                ],
                              ),
                            ),
                            Row(
                              mainAxisAlignment: MainAxisAlignment.spaceBetween,
                              children: [
                                Expanded(
                                  child: Container(
                                    color: const Color.fromARGB(255, 238, 237, 237),
                                    width: 240.0,
                                    height: 40,
                                    margin: const EdgeInsets.only(left: 0, right: 18, top: 0),
                                    child: TextField(
                                      maxLength: 15,
                                      textCapitalization: TextCapitalization.words,
                                      style: GreekTextStyle.textFieldHeading1,
                                      controller: personal_bloc.resicityController,
                                      inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z0-9 ,./()-]')), UpperCaseTextFormatter()],
                                      decoration: InputDecoration(
                                        counterText: "",
                                        border: InputBorder.none,
                                        contentPadding: EdgeInsets.only(left: 8), // add padding to adjust text
                                        isDense: false,
                                        // hintText: "Address line 1",
                                        errorText: validresicity.isEmpty ? null : validresicity,
                                      ),
                                    ),
                                  ),
                                ),
                                Expanded(
                                  child: Container(
                                    color: const Color.fromARGB(255, 238, 237, 237),
                                    width: 240.0,
                                    height: 40,
                                    margin: const EdgeInsets.only(left: 0, right: 18, top: 0),
                                    child: TextField(
                                      maxLength: 6,
                                      textCapitalization: TextCapitalization.words,
                                      keyboardType: TextInputType.number,
                                      style: GreekTextStyle.textFieldHeading1,
                                      controller: personal_bloc.resipincodeController,
                                      inputFormatters: <TextInputFormatter>[FilteringTextInputFormatter.digitsOnly],

                                      // onChanged: (value) {
                                      //   this.personalDetailsBloc.resAddressLine1TextController.value = TextEditingValue(text: value.toUpperCase(), selection: this.personalDetailsBloc.resAddressLine1TextController.selection);
                                      // },
                                      // controller: personalDetailsBloc.resAddressLine1TextController,
                                      /*  inputFormatters: [
                                        FilteringTextInputFormatter.allow(
                                            RegExp(r'[a-zA-Z0-9 ,./()-]'))
                                      ], */
                                      decoration: InputDecoration(
                                        border: InputBorder.none,
                                        contentPadding: EdgeInsets.only(left: 8), // add padding to adjust text
                                        isDense: false,
                                        // hintText: "Address line 1",
                                        errorText: validresipincode.isEmpty ? null : validresipincode,
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
                    const Text(
                      "Permanent Address",
                      style: GreekTextStyle.personalDetailsHeading,
                    ),
                    Container(
                      margin: const EdgeInsets.all(5),
                      decoration: BoxDecoration(
                        shape: BoxShape.rectangle,
                        borderRadius: BorderRadius.circular(5),
                        border: Border.all(
                          // color: const Color(0xFFE3E3E3),
                          color: const Color(0x7CAFAFAF),
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
                                const Expanded(
                                  child: SizedBox(
                                    width: 240,
                                    child: Text(
                                      "Address Line1",
                                      style: GreekTextStyle.textFieldHeading,
                                    ),
                                  ),
                                ),
                                const Expanded(
                                  child: SizedBox(
                                    width: 240,
                                    child: Text(
                                      "Address Line2",
                                      style: GreekTextStyle.textFieldHeading,
                                    ),
                                  ),
                                ),
                                Expanded(
                                  child: Container(
                                    padding: const EdgeInsets.all(5),
                                    width: 240,
                                    child: const Text(
                                      "State",
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
                                    color: const Color.fromARGB(255, 238, 237, 237),
                                    width: 240.0,
                                    height: 40,
                                    margin: const EdgeInsets.only(left: 0, right: 18, top: 0),
                                    child: TextField(
                                      maxLength: 60,
                                      textCapitalization: TextCapitalization.words,
                                      style: GreekTextStyle.textFieldHeading1,
                                      controller: personal_bloc.parmadd1Controller,
                                      inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z0-9 ,./()-]')), UpperCaseTextFormatter()],

                                      // onChanged: (value) {
                                      //   this.personalDetailsBloc.resAddressLine1TextController.value = TextEditingValue(text: value.toUpperCase(), selection: this.personalDetailsBloc.resAddressLine1TextController.selection);
                                      // },
                                      // controller: personalDetailsBloc.resAddressLine1TextController,
                                      /* inputFormatters: [
                                        FilteringTextInputFormatter.allow(
                                            RegExp(r'[a-zA-Z0-9 ,./()-]'))
                                      ], */
                                      decoration: InputDecoration(
                                        counterText: "",
                                        border: InputBorder.none,
                                        contentPadding: EdgeInsets.only(left: 8), // add padding to adjust text
                                        isDense: false,
                                        // hintText: "Address line 1",
                                        errorText: validparamadd1.isEmpty ? null : validparamadd1,
                                      ),
                                    ),
                                  ),
                                ),
                                Expanded(
                                  child: Container(
                                    color: const Color.fromARGB(255, 238, 237, 237),
                                    width: 240.0,
                                    height: 40,
                                    margin: const EdgeInsets.only(left: 0, right: 18, top: 0),
                                    child: TextField(
                                      maxLength: 60,
                                      textCapitalization: TextCapitalization.words,
                                      style: GreekTextStyle.textFieldHeading1,
                                      controller: personal_bloc.paramadd2Controller,
                                      inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z0-9 ,./()-]')), UpperCaseTextFormatter()],

                                      // onChanged: (value) {
                                      //   this.personalDetailsBloc.resAddressLine1TextController.value = TextEditingValue(text: value.toUpperCase(), selection: this.personalDetailsBloc.resAddressLine1TextController.selection);
                                      // },
                                      // controller: personalDetailsBloc.resAddressLine1TextController,
                                      /*  inputFormatters: [
                                        FilteringTextInputFormatter.allow(
                                            RegExp(r'[a-zA-Z0-9 ,./()-]'))
                                      ], */
                                      decoration: InputDecoration(
                                        counterText: "",
                                        border: InputBorder.none,
                                        contentPadding: EdgeInsets.only(left: 8), // add padding to adjust text
                                        isDense: false,
                                        // hintText: "Address line 1",
                                        errorText: validparamadd2.isEmpty ? null : validparamadd2,
                                      ),
                                    ),
                                  ),
                                ),
                                Expanded(
                                  child: Container(
                                    color: const Color.fromARGB(255, 238, 237, 237),
                                    width: 240.0,
                                    height: 40,
                                    margin: const EdgeInsets.only(left: 0, right: 18, top: 0),
                                    child: TextField(
                                      maxLength: 25,
                                      textCapitalization: TextCapitalization.words,
                                      style: GreekTextStyle.textFieldHeading1,
                                      controller: personal_bloc.paramstateController,
                                      inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z0-9 ,./()-]')), UpperCaseTextFormatter()],

                                      // onChanged: (value) {
                                      //   this.personalDetailsBloc.resAddressLine1TextController.value = TextEditingValue(text: value.toUpperCase(), selection: this.personalDetailsBloc.resAddressLine1TextController.selection);
                                      // },
                                      // controller: personalDetailsBloc.resAddressLine1TextController,
                                      /*  inputFormatters: [
                                        FilteringTextInputFormatter.allow(
                                            RegExp(r'[a-zA-Z0-9 ,./()-]'))
                                      ], */
                                      decoration: InputDecoration(
                                        counterText: "",
                                        border: InputBorder.none,
                                        contentPadding: EdgeInsets.only(left: 8), // add padding to adjust text
                                        isDense: false,
                                        // hintText: "Address line 1",
                                        errorText: validparamstate.isEmpty ? null : validparamstate,
                                      ),
                                    ),
                                  ),
                                ),
                              ],
                            ),
                            Container(
                              margin: EdgeInsets.all(8),
                              child: Row(
                                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                children: const [
                                  Expanded(
                                    child: SizedBox(
                                      width: 240,
                                      child: Text(
                                        "City",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                  Expanded(
                                    child: SizedBox(
                                      width: 240,
                                      child: Text(
                                        "Pin Code",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                ],
                              ),
                            ),
                            Row(
                              mainAxisAlignment: MainAxisAlignment.spaceBetween,
                              children: [
                                Expanded(
                                  child: Container(
                                    color: const Color.fromARGB(255, 238, 237, 237),
                                    width: 240.0,
                                    height: 40,
                                    margin: const EdgeInsets.only(left: 0, right: 18, top: 0),
                                    child: TextField(
                                      maxLength: 15,
                                      textCapitalization: TextCapitalization.words,
                                      style: GreekTextStyle.textFieldHeading1,
                                      controller: personal_bloc.parmarcityController,
                                      inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z0-9 ,./()-]')), UpperCaseTextFormatter()],

                                      // onChanged: (value) {
                                      //   this.personalDetailsBloc.resAddressLine1TextController.value = TextEditingValue(text: value.toUpperCase(), selection: this.personalDetailsBloc.resAddressLine1TextController.selection);
                                      // },
                                      // controller: personalDetailsBloc.resAddressLine1TextController,
                                      /* inputFormatters: [
                                        FilteringTextInputFormatter.allow(
                                            RegExp(r'[a-zA-Z0-9 ,./()-]'))
                                      ], */
                                      decoration: InputDecoration(
                                        counterText: "",
                                        border: InputBorder.none,
                                        contentPadding: EdgeInsets.only(left: 8), // add padding to adjust text
                                        isDense: false,
                                        // hintText: "Address line 1",
                                        errorText: validparamcity.isEmpty ? null : validparamcity,
                                      ),
                                    ),
                                  ),
                                ),
                                Expanded(
                                  child: Container(
                                    color: const Color.fromARGB(255, 238, 237, 237),
                                    width: 240.0,
                                    height: 40,
                                    margin: const EdgeInsets.only(left: 0, right: 18, top: 0),
                                    child: TextField(
                                      maxLength: 6,
                                      textCapitalization: TextCapitalization.words,
                                      keyboardType: TextInputType.number,
                                      style: GreekTextStyle.textFieldHeading1,
                                      controller: personal_bloc.parmarpincodeController,
                                      inputFormatters: <TextInputFormatter>[FilteringTextInputFormatter.digitsOnly],

                                      // onChanged: (value) {
                                      //   this.personalDetailsBloc.resAddressLine1TextController.value = TextEditingValue(text: value.toUpperCase(), selection: this.personalDetailsBloc.resAddressLine1TextController.selection);
                                      // },
                                      // controller: personalDetailsBloc.resAddressLine1TextController,
                                      /*  inputFormatters: [
                                        FilteringTextInputFormatter.allow(
                                            RegExp(r'[a-zA-Z0-9 ,./()-]'))
                                      ], */
                                      decoration: InputDecoration(
                                        border: InputBorder.none,
                                        contentPadding: EdgeInsets.only(left: 8), // add padding to adjust text
                                        isDense: false,
                                        // hintText: "Address line 1",
                                        errorText: validparampincode.isEmpty ? null : validparampincode,
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
                      margin: const EdgeInsets.all(5),
                      decoration: BoxDecoration(
                        shape: BoxShape.rectangle,
                        borderRadius: BorderRadius.circular(5),
                        border: Border.all(
                          // color: const Color(0xFFE3E3E3),
                          color: const Color(0x7CAFAFAF),
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
                                const Expanded(
                                  child: SizedBox(
                                    width: 240,
                                    child: Text(
                                      "Telephone (Residential)",
                                      style: GreekTextStyle.textFieldHeading,
                                    ),
                                  ),
                                ),
                                const Expanded(
                                  child: SizedBox(
                                    width: 240,
                                    child: Text(
                                      "Telephone (Office)",
                                      style: GreekTextStyle.textFieldHeading,
                                    ),
                                  ),
                                ),
                                Expanded(
                                  child: Container(
                                    padding: const EdgeInsets.all(5),
                                    width: 240,
                                    child: const Text(
                                      "Mobile",
                                      style: GreekTextStyle.textFieldHeading,
                                    ),
                                  ),
                                ),
                                Expanded(
                                  child: Container(
                                    padding: const EdgeInsets.all(5),
                                    width: 240,
                                    child: const Text(
                                      "E-mail ID",
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
                                    color: const Color.fromARGB(255, 238, 237, 237),
                                    height: 40,
                                    width: 240.0,
                                    alignment: Alignment.centerLeft,
                                    margin: const EdgeInsets.only(left: 0, right: 18, top: 0),
                                    child: Padding(
                                      padding: const EdgeInsets.only(left: 5, right: 5),
                                      child: Text(
                                        globalRespObj?.mobileNo ?? "",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                ),
                                Expanded(
                                  child: Container(
                                    color: const Color.fromARGB(255, 238, 237, 237),
                                    height: 40,
                                    width: 240.0,
                                    alignment: Alignment.centerLeft,
                                    margin: const EdgeInsets.only(left: 0, right: 18, top: 0),
                                    child: Padding(
                                      padding: const EdgeInsets.only(left: 5, right: 5),
                                      child: Text(
                                        globalRespObj?.mobileNo ?? "",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                ),
                                Expanded(
                                  child: Container(
                                    color: const Color.fromARGB(255, 238, 237, 237),
                                    height: 40,
                                    width: 240.0,
                                    alignment: Alignment.centerLeft,
                                    margin: const EdgeInsets.only(left: 0, right: 18, top: 0),
                                    child: Padding(
                                      padding: const EdgeInsets.only(left: 5, right: 5),
                                      child: Text(
                                        globalRespObj?.mobileNo ?? "",
                                        style: GreekTextStyle.textFieldHeading,
                                      ),
                                    ),
                                  ),
                                ),
                                Expanded(
                                  child: Container(
                                    color: const Color.fromARGB(255, 238, 237, 237),
                                    height: 40,
                                    width: 240.0,
                                    alignment: Alignment.centerLeft,
                                    margin: const EdgeInsets.only(left: 0, right: 18, top: 0),
                                    child: Padding(
                                      padding: const EdgeInsets.only(left: 5, right: 5),
                                      child: Text(
                                        globalRespObj?.emailId ?? "",
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
                            personal_bloc.contatcDetailValidation(context, globalRespObj);
                          },
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
