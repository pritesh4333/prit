import 'package:e_kyc/Login/UI/Configuration/AppConfig.dart';
import 'package:e_kyc/Login/UI/DocumentScreen/Bloc/DocumentBloc.dart';
import 'package:e_kyc/Login/UI/EsignScreen/repository/EsignRepository.dart';
import 'package:e_kyc/Login/UI/HomeScreen/HomeScreenLarge.dart';
import 'package:e_kyc/Login/UI/Login/View/LoginUI.dart';
import 'package:e_kyc/Login/UI/Login/repository/LoginRepository.dart';
import 'package:e_kyc/Login/UI/PersonalDetailsScreen/bloc/PersonalDetailsBloc.dart';
import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:image/image.dart' as img;

class PersonalDetailsScreenLarge extends StatefulWidget {
  @override
  _PersonalDetailsScreenLargeState createState() => _PersonalDetailsScreenLargeState();
}

class _PersonalDetailsScreenLargeState extends State<PersonalDetailsScreenLarge> {
  DocumentBloc documentBloc = new DocumentBloc();
  // int _val = -1;
  String _chosenValue, _chosenValue1, _chosenValue2, _chosenValue3;
  PersonalDetailsBloc personalDetailsBloc = new PersonalDetailsBloc();
  //1
  var validFirstName1 = "";
  var validMiddleName1 = "";
  var validLastName1 = "";
  //2
  var validFirstName2 = "";
  var validMiddleName2 = "";
  var validLastName2 = "";

  //Mother
  var validMotherFirstName = "";
  var validMotherMiddleName = "";
  var validMotherLastName = "";

  //Nominee
  var validNomineeName = "";

  //PastRegulatoryAction
  var validPastRegulatoryAction = "";

  //Aadhaar
  var validAadhaarNumber = "";

  //Nominee Email
  var validNomineeEmail = "";

  //Residential Address
  var validResAddress1 = "";
  var validResAddress2 = "";
  var validResState = "";
  var validResCity = "";
  var validResPinCode = "";

  //Nominee Residential Address
  var validNomineeAddress1 = "";
  var validNomineeAddress2 = "";
  var validNomineeState = "";
  var validNomineeCity = "";
  var validNomineePinCode = "";

  //Permanent Address
  var validPerAddress1 = "";
  var validPerAddress2 = "";
  var validPerState = "";
  var validPerCity = "";
  var validPerPinCode = "";

  var globalRespObj = LoginRepository.loginDetailsResObjGlobal;

  @override
  void dispose() {
    this.personalDetailsBloc.dispose();
    super.dispose();
  }

  @override
  void initState() {
    super.initState();

    if (globalRespObj != null) {
      print(globalRespObj.response.errorCode);
      if (globalRespObj.response.errorCode == "0") {
        var stage = int.parse(globalRespObj.response.data.message[0].stage);
        if (stage >= 1) {
          var responseData = globalRespObj.response.data.message[0];
          this.personalDetailsBloc.nseCashCheckBox = responseData.nseCash == "0" ? true : false;
          this.personalDetailsBloc.nseFNOCheckBox = responseData.nseFo == "1" ? true : false;
          this.personalDetailsBloc.nseCurrencyCheckBox = responseData.nseCurrency == "2" ? true : false;
          this.personalDetailsBloc.bseCashCheckBox = responseData.bseCash == "3" ? true : false;
          this.personalDetailsBloc.bseFNOCheckBox = responseData.bseFo == "4" ? true : false;
          this.personalDetailsBloc.bseCurencyCheckBox = responseData.bseCurrency == "5" ? true : false;
          this.personalDetailsBloc.mcxCommodityCheckBox = responseData.mcxCommodty == "6" ? true : false;
          this.personalDetailsBloc.ncdexComodityCheckBox = responseData.ncdexCommodty == "7" ? true : false;

          if (this.personalDetailsBloc.nseCashCheckBox && this.personalDetailsBloc.nseFNOCheckBox && this.personalDetailsBloc.nseCurrencyCheckBox && this.personalDetailsBloc.bseCashCheckBox && this.personalDetailsBloc.bseFNOCheckBox && this.personalDetailsBloc.bseCurencyCheckBox && this.personalDetailsBloc.mcxCommodityCheckBox && this.personalDetailsBloc.ncdexComodityCheckBox) {
            this.personalDetailsBloc.selectAllCheckBox = true;
          }

          if (responseData.gender.isNotEmpty) {
            if (responseData.gender.toLowerCase() == "male") {
              this.personalDetailsBloc.genderCheck = 0;
            } else if (responseData.gender.toLowerCase() == "female") {
              this.personalDetailsBloc.genderCheck = 1;
            } else {
              this.personalDetailsBloc.genderCheck = 2;
            }
          }

          this.personalDetailsBloc.firstNameTextController1.text = responseData.firstname1;
          this.personalDetailsBloc.middleNameTextController1.text = responseData.middlename1;
          this.personalDetailsBloc.lastNameTextController1.text = responseData.lastname1;

          // Nominee
          this.personalDetailsBloc.nomineeNameTextController.text = responseData.nomineeName;

          //  Residential Address
          this.personalDetailsBloc.resAddressLine1TextController.text = responseData.resAddr1;
          this.personalDetailsBloc.resAddressLine2TextController.text = responseData.resAddr2;
          this.personalDetailsBloc.resStateTextController.text = responseData.resAddrState;
          this.personalDetailsBloc.resCityTextController.text = responseData.resAddrCity;
          this.personalDetailsBloc.resPinCodeTextController.text = responseData.resAddrPincode;

          // Permanent Address
          this.personalDetailsBloc.perAddressLine1TextController.text = responseData.parmAddr1;
          this.personalDetailsBloc.perAddressLine2TextController.text = responseData.parmAddr2;
          this.personalDetailsBloc.perStateTextController.text = responseData.parmAddrState;
          this.personalDetailsBloc.perCityTextController.text = responseData.parmAddrCity;
          this.personalDetailsBloc.perPinCodeTextController.text = responseData.parmAddrPincode;

          // Nominee Residential Address
          this.personalDetailsBloc.nomineeAddressLine1TextController.text = responseData.nomAddr1;
          this.personalDetailsBloc.nomineeAddressLine2TextController.text = responseData.nomAddr2;
          this.personalDetailsBloc.nomineeStateTextController.text = responseData.nomAddrState;
          this.personalDetailsBloc.nomineeCityTextController.text = responseData.nomAddrCity;
          this.personalDetailsBloc.nomineePinCodeTextController.text = responseData.nomAddrPincode;

          // Nominee Email
          this.personalDetailsBloc.nomineeEmailTextController.text = responseData.nomineeEmail;

          // Aadhaar
          this.personalDetailsBloc.aadharTextController.text = responseData.aadhaarNumber.toString();

          // Mother Details

          this.personalDetailsBloc.motherFirstNameTextController.text = responseData.firstname_mother.toString();
          this.personalDetailsBloc.motherMiddleNameTextController.text = responseData.middlename_mother.toString();
          this.personalDetailsBloc.motherLastNameTextController.text = responseData.lastname_mother.toString();

          // Past Regulatory Action Details
          this.personalDetailsBloc.pastRegulatoryActionController.text = responseData.pastRegulatoryActionDetails;

          //Marital status
          if (responseData.maritalstatus.isNotEmpty) {
            if (responseData.maritalstatus == "Single") {
              this.personalDetailsBloc.maritalStatus = 0;
            } else if (responseData.maritalstatus == "Married") {
              this.personalDetailsBloc.maritalStatus = 1;
            } else {
              this.personalDetailsBloc.maritalStatus = 2;
            }
          }

          //Father Spouse
          if (responseData.fatherspouse.isNotEmpty) {
            if (responseData.fatherspouse.toLowerCase() == "father") {
              this.personalDetailsBloc.fatherSpouseCheck = 0;
            } else if (responseData.fatherspouse.toLowerCase() == "spouse") {
              this.personalDetailsBloc.fatherSpouseCheck = 1;
            }
          }

          //Name segment
          this.personalDetailsBloc.firstNameTextController2.text = responseData.firstname2;
          this.personalDetailsBloc.middleNameTextController2.text = responseData.middlename2;
          this.personalDetailsBloc.lastNameTextController2.text = responseData.lastname2;

          // Past Regulatory Action/ Politically Exposed Person
          //Accordingly show / hide 'Past Regulatory Action' textfield.
          if (responseData.action.isNotEmpty) {
            this.personalDetailsBloc.actionCheck = responseData.action == "Politically Exposed Person"
                ? 0
                : responseData.action == "Past Regulatory Action"
                    ? 1
                    : 3;
            if (this.personalDetailsBloc.actionCheck == 0) {
              personalDetailsBloc.isShowHidePEP = true;
            } else if (this.personalDetailsBloc.actionCheck == 1) {
              personalDetailsBloc.isShowHidePRA = true;
            }
          } else {
            personalDetailsBloc.actionCheck == 3;
            personalDetailsBloc.pastRegulatoryActionController.text = "";
          }

          //Nationality , incomeRange, occupation
          if (responseData.nationality.isNotEmpty) {
            _chosenValue = responseData.nationality;
            personalDetailsBloc.nationality = _chosenValue;
          }

          if (responseData.incomerange.isNotEmpty) {
            _chosenValue1 = responseData.incomerange;
            personalDetailsBloc.incomeRange = _chosenValue1;
          }

          if (responseData.occupation.isNotEmpty) {
            _chosenValue2 = responseData.occupation;
            personalDetailsBloc.occupation = _chosenValue2;
          }

          // Nominee Relation
          if (responseData.nomineeRelation.isNotEmpty) {
            switch (responseData.nomineeRelation) {
              case "0":
                _chosenValue3 = "Father";
                break;
              case "1":
                _chosenValue3 = "Mother";
                break;
              case "2":
                _chosenValue3 = "Wife";
                break;
              case "3":
                _chosenValue3 = "Brother";
                break;
              case "4":
                _chosenValue3 = "Sister";
                break;
              case "5":
                _chosenValue3 = "Other";
                break;
              default:
                break;
            }
            personalDetailsBloc.nomineeRelation = _chosenValue3;
          }

          // Nominee Proof
          if (responseData.nomineeIdentityProof.isNotEmpty) {
            personalDetailsBloc.nomineeDoc = true;
          }
        }
      }
    }

    HomeScreenLarge.percentageFlagLarge.add("0.205");
    //1
    this.personalDetailsBloc.validateFirstName1.listen((event) {
      setState(() {
        this.validFirstName1 = event;
      });
    });
    this.personalDetailsBloc.validateMiddleName1.listen((event) {
      setState(() {
        this.validMiddleName1 = event;
      });
    });
    this.personalDetailsBloc.validateLastName1.listen((event) {
      setState(() {
        this.validLastName1 = event;
      });
    });
    //2
    this.personalDetailsBloc.validateFirstName2.listen((event) {
      setState(() {
        this.validFirstName2 = event;
      });
    });
    this.personalDetailsBloc.validateMiddleName2.listen((event) {
      setState(() {
        this.validMiddleName2 = event;
      });
    });
    this.personalDetailsBloc.validateLastName2.listen((event) {
      setState(() {
        this.validLastName2 = event;
      });
    });

    //Mother Segment
    this.personalDetailsBloc.validateMotherFirstName.listen((event) {
      setState(() {
        this.validMotherFirstName = event;
      });
    });

    this.personalDetailsBloc.validateMotherMiddleName.listen((event) {
      setState(() {
        this.validMotherMiddleName = event;
      });
    });

    this.personalDetailsBloc.validateMotherLastName.listen((event) {
      setState(() {
        this.validMotherLastName = event;
      });
    });

    //Residential Address Stream Handler
    this.personalDetailsBloc.validateResAddress1.listen((event) {
      setState(() {
        this.validResAddress1 = event;
      });
    });

    this.personalDetailsBloc.validateResAddress2.listen((event) {
      setState(() {
        this.validResAddress2 = event;
      });
    });

    this.personalDetailsBloc.validateResState.listen((event) {
      setState(() {
        this.validResState = event;
      });
    });

    this.personalDetailsBloc.validateResCity.listen((event) {
      setState(() {
        this.validResCity = event;
      });
    });

    this.personalDetailsBloc.validateResPinCode.listen((event) {
      setState(() {
        this.validResPinCode = event;
      });
    });

    //Nominee Residential Address Stream Handler
    this.personalDetailsBloc.validateNomineeAddress1.listen((event) {
      setState(() {
        this.validNomineeAddress1 = event;
      });
    });

    this.personalDetailsBloc.validateNomineeAddress2.listen((event) {
      setState(() {
        this.validNomineeAddress2 = event;
      });
    });

    this.personalDetailsBloc.validateNomineeState.listen((event) {
      setState(() {
        this.validNomineeState = event;
      });
    });

    this.personalDetailsBloc.validateNomineeCity.listen((event) {
      setState(() {
        this.validNomineeCity = event;
      });
    });

    this.personalDetailsBloc.validateNomineePinCode.listen((event) {
      setState(() {
        this.validNomineePinCode = event;
      });
    });

    //Permanent Address Stream Handler
    this.personalDetailsBloc.validatePerAddress1.listen((event) {
      setState(() {
        this.validPerAddress1 = event;
      });
    });

    this.personalDetailsBloc.validatePerAddress2.listen((event) {
      setState(() {
        this.validPerAddress2 = event;
      });
    });

    this.personalDetailsBloc.validatePerState.listen((event) {
      setState(() {
        this.validPerState = event;
      });
    });

    this.personalDetailsBloc.validatePerCity.listen((event) {
      setState(() {
        this.validPerCity = event;
      });
    });

    this.personalDetailsBloc.validatePerPinCode.listen((event) {
      setState(() {
        this.validPerPinCode = event;
      });
    });

    //Nominee
    this.personalDetailsBloc.validateNomineeName.listen((event) {
      setState(() {
        this.validNomineeName = event;
      });
    });

    //Past Regulatory Action
    this.personalDetailsBloc.validatePastRegulatoryAction.listen((event) {
      setState(() {
        this.validPastRegulatoryAction = event;
      });
    });

    //Aadhaar Number
    this.personalDetailsBloc.validateAadharNumber.listen((event) {
      setState(() {
        this.validAadhaarNumber = event;
      });
    });

    //Nominee Email Number
    this.personalDetailsBloc.validateNomineeEmail.listen((event) {
      setState(() {
        this.validNomineeEmail = event;
      });
    });

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
                  _header(),
                  _personalDetailsForm(),
                  // continuebtn(),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }
  /*
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: Container(
          child: Column(
            children: [
              _header(),
              SingleChildScrollView(
                child: Container(
                  child: _personalDetailsForm(),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

   */

  Widget _header() {
    return Container(
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Container(
            width: 100,
            height: 60,
            child: Image.asset(
              'asset/images/personal_header.png',
            ),
          ),
          Expanded(
            child: Container(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    "ONLINE ACCOUNT OPENING E - KYC",
                    style: TextStyle(color: Color(0xFF0066CC), fontSize: 15, fontFamily: 'century_gothic', fontWeight: FontWeight.bold),
                  ),
                  Text(
                    "PERSONAL DETAILS",
                    style: TextStyle(
                      color: Colors.yellow[700],
                      fontFamily: 'century_gothic',
                      fontWeight: FontWeight.bold,
                      fontSize: 15,
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
              //   MaterialPageRoute(
              //     builder: (context) => LoginUI(),
              //   ),
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
                      style: TextStyle(color: Color(0xFF0066CC), fontFamily: 'century_gothic', fontSize: 11, fontWeight: FontWeight.w600),
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

  Widget _personalDetailsForm() {
    return Container(
      padding: EdgeInsets.only(left: 20, top: 15),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        children: [
          Container(
            child: Row(
              children: [
                Container(
                  child: Text(
                    "SELECT SEGMENTS",
                    style: TextStyle(
                      color: Colors.yellow[700],
                      fontFamily: 'century_gothic',
                      fontSize: 15,
                    ),
                  ),
                ),
                Container(margin: EdgeInsets.only(left: 15), child: Text("Select All")),
                Container(
                  margin: EdgeInsets.only(left: 15),
                  child: Checkbox(
                    value: personalDetailsBloc.selectAllCheckBox,
                    onChanged: (value) {
                      setState(() {
                        personalDetailsBloc.selectAllCheckBox = value;
                        personalDetailsBloc.nseCashCheckBox = value;
                        personalDetailsBloc.nseFNOCheckBox = value;
                        personalDetailsBloc.nseCurrencyCheckBox = value;
                        personalDetailsBloc.mcxCommodityCheckBox = value;
                        personalDetailsBloc.bseCashCheckBox = value;
                        personalDetailsBloc.bseFNOCheckBox = value;
                        personalDetailsBloc.bseCurencyCheckBox = value;
                        personalDetailsBloc.ncdexComodityCheckBox = value;
                      });
                    },
                  ),
                ),
              ],
            ),
          ),
          nseSegment(),
          bseSegment(),
          residentialAddress(),
          permanentAddress(),
          nationalitySegment(),
          Container(
            child: Row(
              children: [
                Container(
                  margin: EdgeInsets.only(right: 30),
                  width: 120,
                  child: DropdownButton<String>(
                    value: _chosenValue,
                    style: TextStyle(color: Colors.black),
                    items: personalDetailsBloc.nationalityItems.map((String value) {
                      return DropdownMenuItem<String>(
                        value: value,
                        child: Text(value),
                      );
                    }).toList(),
                    hint: Text("Select Country"),
                    onChanged: (String value) {
                      setState(() {
                        _chosenValue = value;
                        personalDetailsBloc.nationality = _chosenValue;
                        print(personalDetailsBloc.nationality);
                      });
                    },
                  ),
                ),
                Container(
                  child: Radio(
                    value: 0,
                    groupValue: personalDetailsBloc.genderCheck,
                    onChanged: (value) {
                      setState(() {
                        personalDetailsBloc.genderCheck = value;
                        print(personalDetailsBloc.genderCheck);
                      });
                    },
                  ),
                ),
                Container(
                  margin: EdgeInsets.only(
                    right: 15,
                  ),
                  child: Text('Male'),
                ),
                Container(
                  child: Radio(
                    value: 1,
                    groupValue: personalDetailsBloc.genderCheck,
                    onChanged: (value) {
                      setState(() {
                        personalDetailsBloc.genderCheck = value;
                        print(personalDetailsBloc.genderCheck);
                      });
                    },
                  ),
                ),
                Container(
                  margin: EdgeInsets.only(
                    right: 15,
                  ),
                  child: Text('Female'),
                ),
                Container(
                  child: Radio(
                    value: 2,
                    groupValue: personalDetailsBloc.genderCheck,
                    onChanged: (value) {
                      setState(() {
                        personalDetailsBloc.genderCheck = value;
                        print(personalDetailsBloc.genderCheck);
                      });
                    },
                  ),
                ),
                Container(
                  margin: EdgeInsets.only(right: 15),
                  child: Text(
                    'Other',
                  ),
                ),
                Container(
                  margin: EdgeInsets.only(left: 42),
                  width: (MediaQuery.of(context).size.width / 4) / 2,
                  child: TextField(
                    maxLength: 14,
                    textCapitalization: TextCapitalization.words,
                    controller: personalDetailsBloc.aadharTextController,
                    onChanged: (value) {
                      this.personalDetailsBloc.aadharTextController.value = TextEditingValue(
                        text: value.toUpperCase(),
                        selection: this.personalDetailsBloc.aadharTextController.selection,
                      );
                    },
                    enableInteractiveSelection: false,
                    inputFormatters: [
                      FilteringTextInputFormatter.allow(
                        RegExp(r'[0-9]'),
                      ),
                    ],
                    decoration: InputDecoration(
                      contentPadding: EdgeInsets.only(left: 4), // add padding to adjust text
                      isDense: false,
                      hintStyle: TextStyle(color: Colors.black26, letterSpacing: 1.0),
                      hintText: " Enter Aadhaar No",
                      errorText: validAadhaarNumber.isEmpty ? null : validAadhaarNumber,
                    ),
                  ),
                ),
              ],
            ),
          ),
          nameSegment1(),
          Container(
            child: Row(
              children: [
                Expanded(
                  child: Container(
                    margin: EdgeInsets.only(top: 18),
                    child: Text(
                      "MARITAL STATUS",
                      style: TextStyle(
                        color: Colors.yellow[700],
                        fontFamily: 'century_gothic',
                        fontSize: 15,
                      ),
                    ),
                  ),
                ),
                Expanded(
                  child: Container(
                    margin: EdgeInsets.only(top: 18),
                    child: Text(
                      "FATHER / SPOUSE",
                      style: TextStyle(
                        color: Colors.yellow[700],
                        fontFamily: 'century_gothic',
                        fontSize: 15,
                      ),
                    ),
                  ),
                ),
              ],
            ),
          ),
          Container(
            margin: EdgeInsets.only(top: 18.0),
            child: Row(
              children: [
                Expanded(
                  child: Row(
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: [
                      Container(
                        child: Radio(
                          value: 0,
                          groupValue: personalDetailsBloc.maritalStatus,
                          onChanged: (value) {
                            setState(() {
                              personalDetailsBloc.maritalStatus = value;
                            });
                          },
                          activeColor: Colors.blue,
                        ),
                      ),
                      Container(
                        margin: EdgeInsets.only(right: 30),
                        child: Text(
                          'Single',
                        ),
                      ),
                      Container(
                        child: Radio(
                          value: 1,
                          groupValue: personalDetailsBloc.maritalStatus,
                          onChanged: (value) {
                            setState(() {
                              personalDetailsBloc.maritalStatus = value;
                            });
                          },
                          activeColor: Colors.blue,
                        ),
                      ),
                      Container(
                        margin: EdgeInsets.only(right: 30),
                        child: Text('Married'),
                      ),
                      Container(
                        child: Radio(
                          value: 2,
                          groupValue: personalDetailsBloc.maritalStatus,
                          onChanged: (value) {
                            setState(() {
                              personalDetailsBloc.maritalStatus = value;
                              print(personalDetailsBloc.maritalStatus);
                            });
                          },
                          activeColor: Colors.blue,
                        ),
                      ),
                      Container(
                        margin: EdgeInsets.only(right: 0),
                        child: Text('Other'),
                      ),
                    ],
                  ),
                ),
                Expanded(
                  child: Container(
                    child: Row(
                      crossAxisAlignment: CrossAxisAlignment.center,
                      children: [
                        Container(
                          child: Radio(
                            value: 0,
                            groupValue: personalDetailsBloc.fatherSpouseCheck,
                            onChanged: (value) {
                              setState(() {
                                personalDetailsBloc.fatherSpouseCheck = value;
                              });
                            },
                            activeColor: Colors.blue,
                          ),
                        ),
                        Container(
                          margin: EdgeInsets.only(right: 30),
                          child: Text(
                            'FATHER',
                          ),
                        ),
                        Container(
                          child: Radio(
                            value: 1,
                            groupValue: personalDetailsBloc.fatherSpouseCheck,
                            onChanged: (value) {
                              setState(() {
                                personalDetailsBloc.fatherSpouseCheck = value;
                              });
                            },
                            activeColor: Colors.blue,
                          ),
                        ),
                        Container(
                          child: Text(
                            'SPOUSE',
                          ),
                        ),
                      ],
                    ),
                  ),
                ),
              ],
            ),
          ),
          nameSegment2(),
          motherNameSegment(),
          motherDetailsSegment(),
          nomineeSegment(),
          nomineeNameSegment(),
          nomineeResidentialAddress(),
          Container(
            margin: EdgeInsets.only(top: 30),
            child: Row(
              children: [
                Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Container(
                      child: Text(
                        'INCOME RANGE',
                        style: TextStyle(
                          color: Colors.yellow[700],
                          fontFamily: 'century_gothic',
                          fontSize: 15,
                        ),
                      ),
                    ),
                    Container(
                      child: DropdownButton(
                        value: _chosenValue1,
                        style: TextStyle(color: Colors.black),
                        items: personalDetailsBloc.incomeRangeItems.map((String value) {
                          return DropdownMenuItem<String>(
                            value: value,
                            child: Text(value),
                          );
                        }).toList(),
                        hint: Text(
                          globalRespObj.response.data.message[0].incomerange.isEmpty ? "Select Income Range" : globalRespObj.response.data.message[0].incomerange,
                        ),
                        onChanged: (String value) {
                          setState(() {
                            _chosenValue1 = value;
                            personalDetailsBloc.incomeRange = _chosenValue1;
                            print(personalDetailsBloc.incomeRange);
                          });
                        },
                      ),
                    ),
                  ],
                ),
                Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Container(
                      margin: EdgeInsets.only(left: 35.0),
                      child: Text(
                        'OCCUPATION',
                        style: TextStyle(
                          color: Colors.yellow[700],
                          fontFamily: 'century_gothic',
                          fontSize: 15,
                        ),
                      ),
                    ),
                    Container(
                      margin: EdgeInsets.only(left: 35.0),
                      child: DropdownButton(
                        value: _chosenValue2,
                        style: TextStyle(color: Colors.black),
                        items: personalDetailsBloc.occupationItems.map((String value) {
                          return DropdownMenuItem<String>(
                            value: value,
                            child: Text(value),
                          );
                        }).toList(),
                        hint: Text(
                          globalRespObj.response.data.message[0].occupation.isEmpty ? "Select occupation" : globalRespObj.response.data.message[0].occupation,
                        ),
                        onChanged: (String value) {
                          setState(() {
                            _chosenValue2 = value;
                            personalDetailsBloc.occupation = _chosenValue2;
                            print(personalDetailsBloc.occupation);
                          });
                        },
                      ),
                    ),
                  ],
                ),
              ],
            ),
          ),
          Container(
            margin: EdgeInsets.only(
              top: 18,
            ),
            child: Row(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                Row(
                  children: [
                    Container(
                      child: Checkbox(
                        value: personalDetailsBloc.isShowHidePEP,
                        onChanged: (value) {
                          setState(() {
                            personalDetailsBloc.isShowHidePEP = value;
                            if (value) {
                              personalDetailsBloc.actionCheck = 0;
                              personalDetailsBloc.isShowHidePRA = false;
                            } else {
                              personalDetailsBloc.actionCheck = 3;
                            }
                          });
                        },
                        activeColor: Colors.blue,
                      ),
                    ),
                    Container(
                      margin: EdgeInsets.only(right: 30.0),
                      child: Text(
                        'Politically Exposed Person',
                      ),
                    ),
                    Container(
                      child: Checkbox(
                        value: personalDetailsBloc.isShowHidePRA,
                        onChanged: (value) {
                          setState(() {
                            personalDetailsBloc.isShowHidePRA = value;
                            if (value) {
                              personalDetailsBloc.actionCheck = 1;
                              personalDetailsBloc.isShowHidePEP = false;
                              personalDetailsBloc.pastRegulatoryActionController.text = "";
                            } else {
                              personalDetailsBloc.actionCheck = 3;
                              personalDetailsBloc.pastRegulatoryActionController.text = "";
                            }
                          });
                        },
                        activeColor: Colors.blue,
                      ),
                    ),
                    Container(
                      child: Text('Past Regulatory Action'),
                    ),
                    const SizedBox(width: 10),
                  ],
                ),
              ],
            ),
          ),
          Visibility(
            visible: personalDetailsBloc.isShowHidePRA,
            child: Container(
              width: 400,
              child: TextField(
                maxLength: 150,
                textCapitalization: TextCapitalization.words,
                controller: personalDetailsBloc.pastRegulatoryActionController,
                onChanged: (value) {
                  this.personalDetailsBloc.pastRegulatoryActionController.value = TextEditingValue(
                    text: value.toUpperCase(),
                    selection: this.personalDetailsBloc.pastRegulatoryActionController.selection,
                  );
                },
                inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z0-9]'))],
                decoration: InputDecoration(
                  contentPadding: EdgeInsets.only(left: 8), // add padding to adjust text
                  isDense: false, hintStyle: TextStyle(color: Colors.black26),
                  hintText: "Past Regulatory Details",
                  errorText: validPastRegulatoryAction.isEmpty ? null : validPastRegulatoryAction,
                  // add padding to adjust icon
                ),
              ),
            ),
          ),
          continuebtn(),
        ],
      ),
    );
  }

  Container nomineeSegment() {
    return Container(
      child: Row(
        children: [
          Expanded(
            child: Container(
              margin: EdgeInsets.only(top: 18),
              child: Row(
                children: [
                  Text(
                    "NOMINEE DETAILS",
                    style: TextStyle(
                      color: Colors.yellow[700],
                      fontFamily: 'century_gothic',
                      fontSize: 15,
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

  Container nomineeNameSegment() {
    return Container(
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        mainAxisAlignment: MainAxisAlignment.start,
        children: [
          Expanded(
            flex: 1,
            child: Container(
              width: 200,
              padding: EdgeInsets.only(left: 0, right: 18, top: 0),
              child: TextField(
                textCapitalization: TextCapitalization.words,
                controller: personalDetailsBloc.nomineeNameTextController,
                onChanged: (value) {
                  this.personalDetailsBloc.nomineeNameTextController.value = TextEditingValue(
                    text: value.toUpperCase(),
                    selection: this.personalDetailsBloc.nomineeNameTextController.selection,
                  );
                },
                inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z ]'))],
                decoration: InputDecoration(
                  contentPadding: EdgeInsets.only(left: 8), // add padding to adjust text
                  isDense: false,
                  hintText: "Full Name",
                  errorText: validNomineeName.isEmpty ? null : validNomineeName,
                  // add padding to adjust icon
                ),
              ),
            ),
          ),
          Container(
            width: 120,
            padding: EdgeInsets.only(left: 0, right: 0, top: 8),
            child: DropdownButton(
              value: _chosenValue3,
              style: TextStyle(color: Colors.black),
              items: personalDetailsBloc.nomineeRelationItmes.map((String value) {
                return DropdownMenuItem<String>(
                  value: value,
                  child: Text(value),
                );
              }).toList(),
              hint: Text(
                globalRespObj.response.data.message[0].nomineeRelation.isEmpty ? "Select Relation" : globalRespObj.response.data.message[0].nomineeRelation,
              ),
              onChanged: (String value) {
                setState(() {
                  _chosenValue3 = value;
                  personalDetailsBloc.nomineeRelation = _chosenValue3;
                  print(personalDetailsBloc.nomineeRelation);
                });
              },
            ),
          ),
          Expanded(
            flex: 2,
            child: Container(
              margin: EdgeInsets.only(left: 70, right: 50, top: 5),
              child: TextField(
                controller: this.personalDetailsBloc.nomineeEmailTextController,
                style: TextStyle(fontFamily: 'century_gothic'),
                maxLength: 35,
                decoration: InputDecoration(
                  contentPadding: EdgeInsets.only(top: 15, left: 10), // add padding to adjust text
                  isDense: true,
                  hintText: "EMAIL ID",
                  hintStyle: TextStyle(fontSize: 18, fontFamily: 'century_gothic', color: Colors.grey[600]),
                  errorText: validNomineeEmail.isEmpty ? null : validNomineeEmail,
                  prefixIcon: Padding(
                    padding: EdgeInsets.all(10), // add padding to adjust icon
                    child: SvgPicture.asset(
                      'asset/svg/email_id.svg',
                      color: Colors.black,
                      height: 22,
                      width: 22,
                    ),
                  ),
                ),
              ),
            ),
          ),
          Container(
            // width: 150,
            alignment: Alignment.center,
            padding: EdgeInsets.only(left: 10, right: 0, top: 20),
            child: Text(
              "Upload Nominee Identity Proof *",
              style: TextStyle(fontSize: 12, color: Color(0xFF000000), fontFamily: 'century_gothic', fontWeight: FontWeight.w600),
            ),
          ),
          Expanded(
            flex: 1,
            child: GestureDetector(
              onTap: () async {
                print('Pressed Nominee Button');
                AppConfig().showLoaderDialog(context);
                selectImageFile(
                  "",
                  "",
                  globalRespObj.response.data.message[0].uniqueId,
                  ['png', 'jpeg', 'PNG', 'jpg', 'JPG'],
                );
              },
              child: Column(
                children: [
                  Container(
                    alignment: Alignment.center,
                    // width: 30,
                    margin: EdgeInsets.only(right: 20, left: 30, top: 5),
                    decoration: new BoxDecoration(
                      color: Color(0xFFFFFFFF),
                      shape: BoxShape.rectangle,
                      borderRadius: BorderRadius.circular(5),
                      boxShadow: [
                        BoxShadow(
                          color: Color(0xFF9B9B9B),
                          blurRadius: 5.0,
                          offset: const Offset(0, 1.0),
                        ),
                      ],
                    ),
                    child: TextButton(
                      child: Text(
                        'UPLOAD',
                        style: TextStyle(color: Colors.blue, fontFamily: 'century_gothic', fontSize: 11, fontWeight: FontWeight.w500),
                      ),
                      onPressed: () async {
                        print('Pressed Nominee Button');
                        AppConfig().showLoaderDialog(context);
                        selectImageFile(
                          "nominee_identity_proof",
                          "",
                          globalRespObj.response.data.message[0].uniqueId,
                          ['png', 'jpeg', 'PNG', 'JPEG', 'jpg', 'JPG'],
                        );
                      },
                    ),
                  ),
                  Container(
                    // width: MediaQuery.of(context).size.width / ,
                    alignment: Alignment.center,
                    padding: EdgeInsets.only(left: 30, right: 0, top: 10),
                    child: Text(
                      "Only PNG/JPEG/JPG supported *",
                      style: TextStyle(fontSize: 10, color: Colors.grey, fontFamily: 'century_gothic', fontWeight: FontWeight.w600),
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

  Container motherNameSegment() {
    return Container(
      child: Row(
        children: [
          Expanded(
            child: Container(
              margin: EdgeInsets.only(top: 18),
              child: Row(
                children: [
                  Text(
                    "MOTHER DETAILS",
                    style: TextStyle(
                      color: Colors.yellow[700],
                      fontFamily: 'century_gothic',
                      fontSize: 15,
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

  Widget nomineeResidentialAddress() {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Container(
          margin: EdgeInsets.only(
            top: 8,
          ),
          child: Text(
            "NOMINEE RESIDENTIAL ADDRESS",
            style: TextStyle(
              color: Colors.yellow[700],
              fontFamily: 'century_gothic',
              fontSize: 15,
            ),
          ),
        ),
        Container(
          child: Row(
            children: [
              Expanded(
                child: Container(
                  width: 240.0,
                  padding: EdgeInsets.only(left: 0, right: 18, top: 0),
                  child: TextField(
                    textCapitalization: TextCapitalization.words,
                    onChanged: (value) {
                      this.personalDetailsBloc.nomineeAddressLine1TextController.value = TextEditingValue(
                        text: value.toUpperCase(),
                        selection: this.personalDetailsBloc.nomineeAddressLine1TextController.selection,
                      );
                    },
                    controller: personalDetailsBloc.nomineeAddressLine1TextController,
                    inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z0-9 ,./()-]'))],
                    decoration: InputDecoration(
                      contentPadding: EdgeInsets.only(left: 8), // add padding to adjust text
                      isDense: false,
                      hintText: "Address line 1",
                      errorText: validNomineeAddress1.isEmpty ? null : validNomineeAddress1,
                    ),
                  ),
                ),
              ),
              Expanded(
                child: Container(
                  width: 240.0,
                  padding: EdgeInsets.only(left: 0, right: 18, top: 0),
                  child: TextField(
                    textCapitalization: TextCapitalization.words,
                    onChanged: (value) {
                      this.personalDetailsBloc.nomineeAddressLine2TextController.value = TextEditingValue(
                        text: value.toUpperCase(),
                        selection: this.personalDetailsBloc.nomineeAddressLine2TextController.selection,
                      );
                    },
                    controller: personalDetailsBloc.nomineeAddressLine2TextController,
                    inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z0-9 ,./()-]'))],
                    decoration: InputDecoration(
                      contentPadding: EdgeInsets.only(left: 8), // add padding to adjust text
                      isDense: false,
                      hintText: "Address line 2",
                      errorText: validNomineeAddress2.isEmpty ? null : validNomineeAddress2,
                    ),
                  ),
                ),
              ),
              Expanded(
                child: Container(
                  width: 240.0,
                  padding: EdgeInsets.only(left: 0, right: 18, top: 0),
                  child: TextField(
                    textCapitalization: TextCapitalization.words,
                    onChanged: (value) {
                      this.personalDetailsBloc.nomineeStateTextController.value = TextEditingValue(
                        text: value.toUpperCase(),
                        selection: this.personalDetailsBloc.nomineeStateTextController.selection,
                      );
                    },
                    controller: personalDetailsBloc.nomineeStateTextController,
                    inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z .-]'))],
                    decoration: InputDecoration(
                      contentPadding: EdgeInsets.only(left: 8), // add padding to adjust text
                      isDense: false,
                      hintText: "State",
                      errorText: validNomineeState.isEmpty ? null : validNomineeState,
                    ),
                  ),
                ),
              ),
              Expanded(
                child: Container(
                  width: 240.0,
                  padding: EdgeInsets.only(left: 0, right: 18, top: 0),
                  child: TextField(
                    onChanged: (value) {
                      this.personalDetailsBloc.nomineeCityTextController.value = TextEditingValue(
                        text: value.toUpperCase(),
                        selection: this.personalDetailsBloc.nomineeCityTextController.selection,
                      );
                    },
                    textCapitalization: TextCapitalization.words,
                    controller: personalDetailsBloc.nomineeCityTextController,
                    inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z .-]'))],
                    decoration: InputDecoration(
                      contentPadding: EdgeInsets.only(left: 8), // add padding to adjust text
                      isDense: false,
                      hintText: "City",
                      errorText: validNomineeCity.isEmpty ? null : validNomineeCity,
                    ),
                  ),
                ),
              ),
              Expanded(
                child: Container(
                  width: 240.0,
                  padding: EdgeInsets.only(left: 0, right: 18, top: 22),
                  child: TextField(
                    textCapitalization: TextCapitalization.words,
                    controller: personalDetailsBloc.nomineePinCodeTextController,
                    inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[0-9]'))],
                    maxLength: 6,
                    decoration: InputDecoration(
                      contentPadding: EdgeInsets.only(left: 8), // add padding to adjust text
                      isDense: false,
                      hintText: "Pin Code",
                      errorText: validNomineePinCode.isEmpty ? null : validNomineePinCode,
                    ),
                  ),
                ),
              ),
            ],
          ),
        ),
      ],
    );
  }

  Future<void> selectImageFile(String docType, String proofType, String uniqueId, List<String> extensionAllowed) async {
    PlatformFile objFile;
    var result = await FilePicker.platform.pickFiles(
      type: FileType.custom,
      allowedExtensions: extensionAllowed,
    );

    print(result);
    if (result != null) {
      objFile = result.files.single;
      double sizeInMb = result.files[0].size / (1024 * 1024);
      // show loader

      if (docType.toString() == "nominee_identity_proof") {
        if (objFile.name.contains('.png') || objFile.name.contains('.jpeg') || objFile.name.contains('.PNG') || objFile.name.contains('.JPEG') || objFile.name.contains('.jpg') || objFile.name.contains('.JPG')) {
          if (sizeInMb > 2) {
            // This file is Longer the
            print('file is big');
            AppConfig().dismissLoaderDialog(context); // show loader
            documentBloc.showAlert(context, 'Maximum upload file size: 2 MB');
          } else {
            print('file is pefect');

            final image = img.decodeImage(objFile.bytes);
            // Resize the image to a 120x? thumbnail (maintaining the aspect ratio).
            var widthsize = image.width.toInt() / 2;
            var heightsize = image.height.toInt() / 2;
            final thumbnail = img.copyResize(image, width: widthsize.toInt(), height: heightsize.toInt());

            // Save the thumbnail as a PNG.
            List<int> filess = img.encodePng(thumbnail);
            //print(filess);
            AppConfig().dismissLoaderDialog(context); //
            personalDetailsBloc.saveDocument(context, docType, "large", objFile, filess, proofType, uniqueId);
          }
        } else {
          AppConfig().dismissLoaderDialog(context); // show loader

          documentBloc.showAlert(context, "Only 'png','PNG' 'jpeg', 'JPEG' ,'jpg' ,'JPG' supported");
        }
      }
    } else {
      AppConfig().dismissLoaderDialog(context); // show loader

      // documentBloc.showAlert(context, "Please upload all documents");
    }
  }

  Widget residentialAddress() {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Container(
          margin: EdgeInsets.only(
            top: 8,
          ),
          child: Text(
            "RESIDENTIAL ADDRESS",
            style: TextStyle(
              color: Colors.yellow[700],
              fontFamily: 'century_gothic',
              fontSize: 15,
            ),
          ),
        ),
        Container(
          child: Row(
            children: [
              Expanded(
                child: Container(
                  width: 240.0,
                  padding: EdgeInsets.only(left: 0, right: 18, top: 0),
                  child: TextField(
                    textCapitalization: TextCapitalization.words,
                    onChanged: (value) {
                      this.personalDetailsBloc.resAddressLine1TextController.value = TextEditingValue(text: value.toUpperCase(), selection: this.personalDetailsBloc.resAddressLine1TextController.selection);
                    },
                    controller: personalDetailsBloc.resAddressLine1TextController,
                    inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z0-9 ,./()-]'))],
                    decoration: InputDecoration(
                      contentPadding: EdgeInsets.only(left: 8), // add padding to adjust text
                      isDense: false,
                      hintText: "Address line 1",
                      errorText: validResAddress1.isEmpty ? null : validResAddress1,
                    ),
                  ),
                ),
              ),
              Expanded(
                child: Container(
                  width: 240.0,
                  padding: EdgeInsets.only(left: 0, right: 18, top: 0),
                  child: TextField(
                    textCapitalization: TextCapitalization.words,
                    onChanged: (value) {
                      this.personalDetailsBloc.resAddressLine2TextController.value = TextEditingValue(text: value.toUpperCase(), selection: this.personalDetailsBloc.resAddressLine2TextController.selection);
                    },
                    controller: personalDetailsBloc.resAddressLine2TextController,
                    inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z0-9 ,./()-]'))],
                    decoration: InputDecoration(
                      contentPadding: EdgeInsets.only(left: 8), // add padding to adjust text
                      isDense: false,
                      hintText: "Address line 2",
                      errorText: validPerAddress2.isEmpty ? null : validResAddress2,
                    ),
                  ),
                ),
              ),
              Expanded(
                child: Container(
                  width: 240.0,
                  padding: EdgeInsets.only(left: 0, right: 18, top: 0),
                  child: TextField(
                    textCapitalization: TextCapitalization.words,
                    onChanged: (value) {
                      this.personalDetailsBloc.resStateTextController.value = TextEditingValue(text: value.toUpperCase(), selection: this.personalDetailsBloc.resStateTextController.selection);
                    },
                    controller: personalDetailsBloc.resStateTextController,
                    inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z .-]'))],
                    decoration: InputDecoration(
                      contentPadding: EdgeInsets.only(left: 8), // add padding to adjust text
                      isDense: false,
                      hintText: "State",
                      errorText: validResState.isEmpty ? null : validResState,
                    ),
                  ),
                ),
              ),
              Expanded(
                child: Container(
                  width: 240.0,
                  padding: EdgeInsets.only(left: 0, right: 18, top: 0),
                  child: TextField(
                    onChanged: (value) {
                      this.personalDetailsBloc.resCityTextController.value = TextEditingValue(text: value.toUpperCase(), selection: this.personalDetailsBloc.resCityTextController.selection);
                    },
                    textCapitalization: TextCapitalization.words,
                    controller: personalDetailsBloc.resCityTextController,
                    inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z .-]'))],
                    decoration: InputDecoration(
                      contentPadding: EdgeInsets.only(left: 8), // add padding to adjust text
                      isDense: false,
                      hintText: "City",
                      errorText: validResCity.isEmpty ? null : validResCity,
                    ),
                  ),
                ),
              ),
              Expanded(
                child: Container(
                  width: 240.0,
                  padding: EdgeInsets.only(left: 0, right: 18, top: 22),
                  child: TextField(
                    textCapitalization: TextCapitalization.words,
                    controller: personalDetailsBloc.resPinCodeTextController,
                    inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[0-9]'))],
                    maxLength: 6,
                    decoration: InputDecoration(
                      contentPadding: EdgeInsets.only(left: 8), // add padding to adjust text
                      isDense: false,
                      hintText: "Pin Code",

                      errorText: validResPinCode.isEmpty ? null : validResPinCode,
                    ),
                  ),
                ),
              ),
            ],
          ),
        ),
      ],
    );
  }

  Widget permanentAddress() {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Container(
          margin: EdgeInsets.only(top: 8),
          child: Text(
            "PERMENANT ADDRESS",
            style: TextStyle(
              color: Colors.yellow[700],
              fontFamily: 'century_gothic',
              fontSize: 15,
            ),
          ),
        ),
        Container(
          margin: EdgeInsets.only(top: 0),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.start,
            children: [
              Container(
                child: Checkbox(
                  value: personalDetailsBloc.sameAddressCheckbox,
                  onChanged: (value) {
                    setState(
                      () {
                        personalDetailsBloc.sameAddressCheckbox = value;
                        print('same address checkbox ${personalDetailsBloc.sameAddressCheckbox}');
                        if (personalDetailsBloc.sameAddressCheckbox) {
                          updatePermanentAddressData();
                        } else {
                          addRemoveAddressData();
                        }
                      },
                    );
                  },
                ),
              ),
              Container(
                child: Text('Residential & Permanent Address Same'),
              ),
            ],
          ),
        ),
        Container(
          child: Row(
            children: [
              Expanded(
                child: Container(
                  width: 240.0,
                  padding: EdgeInsets.only(left: 0, right: 18, top: 0),
                  child: TextField(
                    textCapitalization: TextCapitalization.words,
                    onChanged: (value) {
                      this.personalDetailsBloc.perAddressLine1TextController.value = TextEditingValue(text: value.toUpperCase(), selection: this.personalDetailsBloc.perAddressLine1TextController.selection);
                    },
                    controller: personalDetailsBloc.perAddressLine1TextController,
                    inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z0-9 ,./()-]'))],
                    decoration: InputDecoration(
                      contentPadding: EdgeInsets.only(left: 8), // add padding to adjust text
                      isDense: false,
                      hintText: "Address line 1",
                      errorText: validPerAddress1.isEmpty ? null : validPerAddress1,
                    ),
                  ),
                ),
              ),
              Expanded(
                child: Container(
                  width: 240.0,
                  padding: EdgeInsets.only(left: 0, right: 18, top: 0),
                  child: TextField(
                    textCapitalization: TextCapitalization.words,
                    onChanged: (value) {
                      this.personalDetailsBloc.perAddressLine2TextController.value = TextEditingValue(text: value.toUpperCase(), selection: this.personalDetailsBloc.perAddressLine2TextController.selection);
                    },
                    controller: personalDetailsBloc.perAddressLine2TextController,
                    inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z0-9 ,./()-]'))],
                    decoration: InputDecoration(
                      contentPadding: EdgeInsets.only(left: 8), // add padding to adjust text
                      isDense: false,
                      hintText: "Address line 2",
                      errorText: validPerAddress2.isEmpty ? null : validPerAddress2,
                    ),
                  ),
                ),
              ),
              Expanded(
                child: Container(
                  width: 240.0,
                  padding: EdgeInsets.only(left: 0, right: 18, top: 0),
                  child: TextField(
                    textCapitalization: TextCapitalization.words,
                    onChanged: (value) {
                      this.personalDetailsBloc.perStateTextController.value = TextEditingValue(text: value.toUpperCase(), selection: this.personalDetailsBloc.perStateTextController.selection);
                    },
                    controller: personalDetailsBloc.perStateTextController,
                    inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z .-]'))],
                    decoration: InputDecoration(
                      contentPadding: EdgeInsets.only(left: 8), // add padding to adjust text
                      isDense: false,
                      hintText: "State",
                      errorText: validPerState.isEmpty ? null : validPerState,
                    ),
                  ),
                ),
              ),
              Expanded(
                child: Container(
                  width: 240.0,
                  padding: EdgeInsets.only(left: 0, right: 18, top: 0),
                  child: TextField(
                    onChanged: (value) {
                      this.personalDetailsBloc.perCityTextController.value = TextEditingValue(text: value.toUpperCase(), selection: this.personalDetailsBloc.perCityTextController.selection);
                    },
                    textCapitalization: TextCapitalization.words,
                    controller: personalDetailsBloc.perCityTextController,
                    inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z .-]'))],
                    decoration: InputDecoration(
                      contentPadding: EdgeInsets.only(left: 8), // add padding to adjust text
                      isDense: false,
                      hintText: "City",
                      errorText: validPerCity.isEmpty ? null : validPerCity,
                    ),
                  ),
                ),
              ),
              Expanded(
                child: Container(
                  width: 240.0,
                  padding: EdgeInsets.only(left: 0, right: 18, top: 22),
                  child: TextField(
                    textCapitalization: TextCapitalization.words,
                    controller: personalDetailsBloc.perPinCodeTextController,
                    inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[0-9]'))],
                    maxLength: 6,
                    decoration: InputDecoration(
                      contentPadding: EdgeInsets.only(left: 8), // add padding to adjust text
                      isDense: false,
                      hintText: "Pin Code",
                      errorText: validPerPinCode.isEmpty ? null : validPerPinCode,
                    ),
                  ),
                ),
              ),
            ],
          ),
        ),
      ],
    );
  }

  Container nameSegment1() {
    return Container(
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.center,
        mainAxisAlignment: MainAxisAlignment.start,
        children: [
          Expanded(
            child: Container(
              width: 200.0,
              padding: EdgeInsets.only(left: 0, right: 18, top: 0),
              child: TextField(
                textCapitalization: TextCapitalization.words,
                onChanged: (value) {
                  this.personalDetailsBloc.firstNameTextController1.value = TextEditingValue(text: value.toUpperCase(), selection: this.personalDetailsBloc.firstNameTextController1.selection);
                },
                controller: personalDetailsBloc.firstNameTextController1,
                inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z]'))],
                decoration: InputDecoration(
                  contentPadding: EdgeInsets.only(left: 8), // add padding to adjust text
                  isDense: false,
                  hintText: "First Name",
                  errorText: validFirstName1.isEmpty ? null : validFirstName1,
                  // add padding to adjust icon
                ),
              ),
            ),
          ),
          Expanded(
            child: Container(
              width: 200.0,
              padding: EdgeInsets.only(left: 0, right: 18, top: 0),
              child: TextField(
                textCapitalization: TextCapitalization.words,
                onChanged: (value) {
                  this.personalDetailsBloc.middleNameTextController1.value = TextEditingValue(text: value.toUpperCase(), selection: this.personalDetailsBloc.middleNameTextController1.selection);
                },
                controller: personalDetailsBloc.middleNameTextController1,
                inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z]'))],
                decoration: InputDecoration(
                  contentPadding: EdgeInsets.only(left: 8), // add padding to adjust text
                  isDense: false,
                  hintText: "Middle Name",
                  errorText: validMiddleName1.isEmpty ? null : validMiddleName1,
                  // add padding to adjust icon
                ),
              ),
            ),
          ),
          Expanded(
            child: Container(
              width: 200.0,
              padding: EdgeInsets.only(left: 0, right: 18, top: 0),
              child: TextField(
                textCapitalization: TextCapitalization.words,
                onChanged: (value) {
                  this.personalDetailsBloc.lastNameTextController1.value = TextEditingValue(text: value.toUpperCase(), selection: this.personalDetailsBloc.lastNameTextController1.selection);
                },
                controller: personalDetailsBloc.lastNameTextController1,
                inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z]'))],
                decoration: InputDecoration(
                  contentPadding: EdgeInsets.only(left: 8), // add padding to adjust text
                  isDense: false,
                  hintText: "Last Name",
                  errorText: validLastName1.isEmpty ? null : validLastName1,
                  // add padding to adjust icon
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }

  Container nameSegment2() {
    return Container(
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.center,
        mainAxisAlignment: MainAxisAlignment.start,
        children: [
          Expanded(
            child: Container(
              width: 200.0,
              padding: EdgeInsets.only(left: 0, right: 18, top: 0),
              child: TextField(
                textCapitalization: TextCapitalization.words,
                onChanged: (value) {
                  this.personalDetailsBloc.firstNameTextController2.value = TextEditingValue(text: value.toUpperCase(), selection: this.personalDetailsBloc.firstNameTextController2.selection);
                },
                controller: personalDetailsBloc.firstNameTextController2,
                inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z]'))],
                decoration: InputDecoration(
                  contentPadding: EdgeInsets.only(left: 8), // add padding to adjust text
                  isDense: false,
                  hintText: "First Name",
                  errorText: validFirstName2.isEmpty ? null : validFirstName2,
                  // add padding to adjust icon
                ),
              ),
            ),
          ),
          Expanded(
            child: Container(
              width: 200.0,
              padding: EdgeInsets.only(left: 0, right: 18, top: 0),
              child: TextField(
                textCapitalization: TextCapitalization.words,
                onChanged: (value) {
                  this.personalDetailsBloc.middleNameTextController2.value = TextEditingValue(text: value.toUpperCase(), selection: this.personalDetailsBloc.middleNameTextController2.selection);
                },
                controller: personalDetailsBloc.middleNameTextController2,
                inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z]'))],
                decoration: InputDecoration(
                  contentPadding: EdgeInsets.only(left: 8), // add padding to adjust text
                  isDense: false,
                  hintText: "Middle Name",
                  errorText: validMiddleName2.isEmpty ? null : validMiddleName2,
                  // add padding to adjust icon
                ),
              ),
            ),
          ),
          Expanded(
            child: Container(
              width: 200.0,
              padding: EdgeInsets.only(left: 0, right: 18, top: 0),
              child: TextField(
                textCapitalization: TextCapitalization.words,
                onChanged: (value) {
                  this.personalDetailsBloc.lastNameTextController2.value = TextEditingValue(text: value.toUpperCase(), selection: this.personalDetailsBloc.lastNameTextController2.selection);
                },
                controller: personalDetailsBloc.lastNameTextController2,
                inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z]'))],
                decoration: InputDecoration(
                  contentPadding: EdgeInsets.only(left: 8), // add padding to adjust text
                  isDense: false,
                  hintText: "Last Name",
                  errorText: validLastName2.isEmpty ? null : validLastName2,
                  // add padding to adjust icon
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }

  Container motherDetailsSegment() {
    return Container(
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.center,
        mainAxisAlignment: MainAxisAlignment.start,
        children: [
          Expanded(
            child: Container(
              width: 200.0,
              padding: EdgeInsets.only(left: 0, right: 18, top: 0),
              child: TextField(
                textCapitalization: TextCapitalization.words,
                onChanged: (value) {
                  this.personalDetailsBloc.motherFirstNameTextController.value = TextEditingValue(
                    text: value.toUpperCase(),
                    selection: this.personalDetailsBloc.motherFirstNameTextController.selection,
                  );
                },
                controller: personalDetailsBloc.motherFirstNameTextController,
                inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z]'))],
                decoration: InputDecoration(
                  contentPadding: EdgeInsets.only(left: 8), // add padding to adjust text
                  isDense: false,
                  hintText: "Mother First Name",
                  errorText: validMotherFirstName.isEmpty ? null : validMotherFirstName,
                  // add padding to adjust icon
                ),
              ),
            ),
          ),
          Expanded(
            child: Container(
              width: 200.0,
              padding: EdgeInsets.only(left: 0, right: 18, top: 0),
              child: TextField(
                textCapitalization: TextCapitalization.words,
                onChanged: (value) {
                  this.personalDetailsBloc.motherMiddleNameTextController.value = TextEditingValue(
                    text: value.toUpperCase(),
                    selection: this.personalDetailsBloc.motherMiddleNameTextController.selection,
                  );
                },
                controller: personalDetailsBloc.motherMiddleNameTextController,
                inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z]'))],
                decoration: InputDecoration(
                  contentPadding: EdgeInsets.only(left: 8), // add padding to adjust text
                  isDense: false,
                  hintText: "Mother Middle Name",
                  errorText: validMotherMiddleName.isEmpty ? null : validMotherMiddleName,
                  // add padding to adjust icon
                ),
              ),
            ),
          ),
          Expanded(
            child: Container(
              width: 200.0,
              padding: EdgeInsets.only(left: 0, right: 18, top: 0),
              child: TextField(
                textCapitalization: TextCapitalization.words,
                onChanged: (value) {
                  this.personalDetailsBloc.motherLastNameTextController.value = TextEditingValue(
                    text: value.toUpperCase(),
                    selection: this.personalDetailsBloc.motherLastNameTextController.selection,
                  );
                },
                controller: personalDetailsBloc.motherLastNameTextController,
                inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z]'))],
                decoration: InputDecoration(
                  contentPadding: EdgeInsets.only(left: 8), // add padding to adjust text
                  isDense: false,
                  hintText: "Mother Last Name",
                  errorText: validMotherLastName.isEmpty ? null : validMotherLastName,
                  // add padding to adjust icon
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }

  Widget nationalitySegment() {
    return Container(
      child: Row(
        children: [
          Container(
            margin: EdgeInsets.only(top: 8),
            child: Text(
              "NATIONALITY",
              style: TextStyle(
                color: Colors.yellow[700],
                fontFamily: 'century_gothic',
                fontSize: 15,
              ),
            ),
          ),
          Container(
            margin: EdgeInsets.only(top: 8, left: 65),
            child: Text(
              "GENDER",
              style: TextStyle(
                color: Colors.yellow[700],
                fontFamily: 'century_gothic',
                fontSize: 15,
              ),
            ),
          ),
          Container(
            margin: EdgeInsets.only(top: 8, left: 230),
            width: MediaQuery.of(context).size.width / 5,
            child: Text(
              'AADHAAR (optional)',
              style: TextStyle(
                color: Colors.yellow.shade700,
                fontFamily: 'century_gothic',
                fontSize: 15,
              ),
            ),
          ),
        ],
      ),
    );
  }

  Widget nseSegment() {
    return Container(
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Expanded(
            child: Container(
              child: Row(
                children: [
                  Container(
                    child: Checkbox(
                      value: personalDetailsBloc.nseCashCheckBox,
                      onChanged: (value) {
                        setState(() {
                          personalDetailsBloc.nseCashCheckBox = value;
                          personalDetailsBloc.updateSelectAllCheckbox();
                        });
                      },
                    ),
                  ),
                  Expanded(
                    child: Container(
                      child: Text("NSE CASH"),
                    ),
                  ),
                ],
              ),
            ),
          ),
          Expanded(
            child: Container(
              child: Row(
                children: [
                  Container(
                    child: Checkbox(
                      value: personalDetailsBloc.nseFNOCheckBox,
                      onChanged: (value) {
                        setState(() {
                          personalDetailsBloc.nseFNOCheckBox = value;
                          personalDetailsBloc.updateSelectAllCheckbox();
                        });
                      },
                    ),
                  ),
                  Expanded(
                    child: Container(
                      child: Text("NSE F&O"),
                    ),
                  ),
                ],
              ),
            ),
          ),
          Expanded(
            child: Container(
              child: Row(
                children: [
                  Container(
                    child: Checkbox(
                      value: personalDetailsBloc.nseCurrencyCheckBox,
                      onChanged: (value) {
                        setState(() {
                          personalDetailsBloc.nseCurrencyCheckBox = value;
                          personalDetailsBloc.updateSelectAllCheckbox();
                        });
                      },
                    ),
                  ),
                  Expanded(
                    child: Container(
                      child: Text("NSE CURRENCY"),
                    ),
                  ),
                ],
              ),
            ),
          ),
          Expanded(
            child: Container(
              child: Row(
                children: [
                  Container(
                    child: Checkbox(
                      value: personalDetailsBloc.mcxCommodityCheckBox,
                      onChanged: (value) {
                        setState(() {
                          personalDetailsBloc.mcxCommodityCheckBox = value;
                          personalDetailsBloc.updateSelectAllCheckbox();
                        });
                      },
                    ),
                  ),
                  Expanded(
                    child: Container(
                      child: Text("MCX COMMODITY"),
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

  Widget bseSegment() {
    return Container(
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Expanded(
            child: Container(
              child: Row(
                children: [
                  Container(
                    child: Checkbox(
                      value: personalDetailsBloc.bseCashCheckBox,
                      onChanged: (value) {
                        setState(() {
                          personalDetailsBloc.bseCashCheckBox = value;
                          personalDetailsBloc.updateSelectAllCheckbox();
                        });
                      },
                    ),
                  ),
                  Expanded(
                    child: Container(
                      child: Text("BSE CASH"),
                    ),
                  ),
                ],
              ),
            ),
          ),
          Expanded(
            child: Container(
              child: Row(
                children: [
                  Container(
                    child: Checkbox(
                      value: personalDetailsBloc.bseFNOCheckBox,
                      onChanged: (value) {
                        setState(() {
                          personalDetailsBloc.bseFNOCheckBox = value;
                          personalDetailsBloc.updateSelectAllCheckbox();
                        });
                      },
                    ),
                  ),
                  Expanded(
                    child: Container(
                      child: Text("BSE F&O"),
                    ),
                  ),
                ],
              ),
            ),
          ),
          Expanded(
            child: Container(
              child: Row(
                children: [
                  Container(
                    child: Checkbox(
                      value: personalDetailsBloc.bseCurencyCheckBox,
                      onChanged: (value) {
                        setState(() {
                          personalDetailsBloc.bseCurencyCheckBox = value;
                          personalDetailsBloc.updateSelectAllCheckbox();
                        });
                      },
                    ),
                  ),
                  Expanded(
                    child: Container(
                      child: Text("BSE CURRENCY"),
                    ),
                  ),
                ],
              ),
            ),
          ),
          Expanded(
            child: Container(
              child: Row(
                children: [
                  Container(
                    child: Checkbox(
                      value: personalDetailsBloc.ncdexComodityCheckBox,
                      onChanged: (value) {
                        setState(() {
                          personalDetailsBloc.ncdexComodityCheckBox = value;
                          personalDetailsBloc.updateSelectAllCheckbox();
                        });
                      },
                    ),
                  ),
                  Expanded(
                    child: Container(
                      child: Text("NCDEX COMMODITY"),
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

  Widget continuebtn() {
    return Container(
      margin: EdgeInsets.only(right: 100, top: 0),
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
                borderRadius: BorderRadius.circular(20) // use instead of BorderRadius.all(Radius.circular(20))
                ),

            child: TextButton(
              child: Text(
                "PROCEED".toUpperCase(),
                style: TextStyle(color: Color(0xFFFFFFFF), fontFamily: 'century_gothic', fontSize: 14, fontWeight: FontWeight.w500),
              ),
              style: ButtonStyle(
                padding: MaterialStateProperty.all<EdgeInsets>(EdgeInsets.all(1)),
                foregroundColor: MaterialStateProperty.all<Color>(Colors.white),
                shape: MaterialStateProperty.all<RoundedRectangleBorder>(RoundedRectangleBorder(borderRadius: BorderRadius.circular(15), side: BorderSide(color: Colors.blue[200]))),
                backgroundColor: MaterialStateProperty.all<Color>(Color(0xFF0074C4)),
              ),
              onPressed: () => this.personalDetailsBloc.validatePersonalDetails(context) == true
                  ? personalDetailsBloc.callPersonalDetailsAPI(context, "large")
                  : showAlert(
                      personalDetailsBloc.genericMessage,
                    ),
            ),
          ),
        ],
      ),
    );
  }

  Future<dynamic> showAlert(String msg) {
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

  void updatePermanentAddressData() {
    setState(() {
      personalDetailsBloc.perAddressLine1TextController.text = personalDetailsBloc.resAddressLine1TextController.text;
      personalDetailsBloc.perAddressLine2TextController.text = personalDetailsBloc.resAddressLine2TextController.text;
      personalDetailsBloc.perStateTextController.text = personalDetailsBloc.resStateTextController.text;
      personalDetailsBloc.perCityTextController.text = personalDetailsBloc.resCityTextController.text;
      personalDetailsBloc.perPinCodeTextController.text = personalDetailsBloc.resPinCodeTextController.text;
    });
  }

  void addRemoveAddressData() {
    setState(
      () {
        if (personalDetailsBloc.perAddressLine1TextController.text == personalDetailsBloc.resAddressLine1TextController.text && personalDetailsBloc.perAddressLine2TextController.text == personalDetailsBloc.resAddressLine2TextController.text && personalDetailsBloc.perStateTextController.text == personalDetailsBloc.resStateTextController.text && personalDetailsBloc.perCityTextController.text == personalDetailsBloc.resCityTextController.text && personalDetailsBloc.perPinCodeTextController.text == personalDetailsBloc.resPinCodeTextController.text) {
          //Removing the data permanent address
          personalDetailsBloc.perAddressLine1TextController.text = '';
          personalDetailsBloc.perAddressLine2TextController.text = '';
          personalDetailsBloc.perStateTextController.text = '';
          personalDetailsBloc.perCityTextController.text = '';
          personalDetailsBloc.perPinCodeTextController.text = '';
        }
      },
    );
  }
}
