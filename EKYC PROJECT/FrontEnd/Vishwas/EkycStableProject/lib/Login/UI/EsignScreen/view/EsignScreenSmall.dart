import 'package:e_kyc/Login/UI/EsignScreen/bloc/EsignBloc.dart';
import 'package:e_kyc/Login/UI/EsignScreen/repository/EsignRepository.dart';
import 'package:e_kyc/Login/UI/HomeScreen/HomeScreenSmall.dart';
import 'package:e_kyc/Login/UI/Login/View/pdfwritescreen.dart';
import 'package:e_kyc/Login/UI/Login/bloc/LoginBloc.dart';
import 'package:e_kyc/Login/UI/Login/model/LoginUserDetailModelResponse.dart';
import 'package:e_kyc/Login/UI/Login/repository/LoginRepository.dart';
import 'package:e_kyc/Utilities/Location/location.dart';
import 'package:file_picker/file_picker.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class EsignScreenSmall extends StatefulWidget {
  @override
  _EsignScreenSmallState createState() => _EsignScreenSmallState();
}

class _EsignScreenSmallState extends State<EsignScreenSmall> {
  EsignBloc esignbloc = new EsignBloc();
  var globalRespObj = LoginRepository.loginDetailsResObjGlobal;

  @override
  void dispose() {
    this.esignbloc.dispose();
    super.dispose();
  }

  @override
  void initState() {
    super.initState();
    HomeScreenSmall.percentageFlagSmall.add("1");
    if (globalRespObj != null) {
      print(globalRespObj.response.errorCode);
      if (globalRespObj.response.errorCode == "0") {
        var responseData = globalRespObj.response.data.message[0];
        var stage = int.parse(globalRespObj.response.data.message[0].stage);
        if (stage > 7) {
          esignbloc.addharselected = responseData.esign == "0" ? true : false;
          esignbloc.emudraselected = responseData.esign == "1" ? true : false;
          esignbloc.manuallyselected = responseData.esign == "2" ? true : false;
          print("flag" + LoginRepository.Esignflag.toString());
          if (LoginRepository.Esignflag == 0) {
            WidgetsBinding.instance.addPostFrameCallback(
              (_) => showDialog(
                context: context,
                builder: (BuildContext context) {
                  return Center(child: customDialogmethod(context));
                },
              ),
            );
            LoginRepository.Esignflag++;
          } else {}
        }
      }
    }
    if (EsignRepository.timerObj != null) {
      EsignRepository.timerObj.cancel();
      EsignRepository.timerObj = null;
    }
  }

  @override
  Widget build(BuildContext context) {
    print("i am build Esign small");
    print(HomeScreenSmall.screensStreamSmall.stream.value);
    // if (globalRespObj.response.data.message[0].stage.contains("7")) {
    //   // call back method after build method load complete
    //   WidgetsBinding.instance.addPostFrameCallback(
    //     (timeStamp) {
    //       showDialog(
    //         context: context,
    //         builder: (BuildContext context) {
    //           return Center(child: customDialogmethod());
    //         },
    //       );
    //     },
    //   );
    // }
    return Scaffold(
        body: SafeArea(
            child: SingleChildScrollView(
                child: Container(
      child: Column(
        children: [
          header(),
          eSignDetailsForm(),
          // pdfwrite(context),
          btnprocess(context),
        ],
      ),
    ))));
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
                    style: TextStyle(color: Color(0xFF0066CC), fontSize: 15, fontFamily: 'century_gothic', fontWeight: FontWeight.bold),
                  ),
                  Text(
                    "E-SIGN",
                    style: TextStyle(color: Color(0xFFFAB804), fontFamily: 'century_gothic', fontSize: 15, fontWeight: FontWeight.w300),
                  ),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }

  eSignDetailsForm() {
    return Container(
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Row(
            children: [
              Expanded(
                child: InkWell(
                  onTap: () {
                    setState(() {
                      esignbloc.addharselected = true;
                      esignbloc.emudraselected = false;
                      esignbloc.manuallyselected = false;
                    });
                  },
                  child: Container(
                    margin: EdgeInsets.only(left: 10, right: 10, bottom: 10, top: 30),
                    decoration: new BoxDecoration(
                      color: Color(0xFFFFFFFF),
                      shape: BoxShape.rectangle,
                      borderRadius: BorderRadius.circular(5),
                      boxShadow: [
                        BoxShadow(
                          color: Color(0xFF9B9B9B),
                          blurRadius: 10.0,
                          offset: const Offset(10.10, 10.10),
                        ),
                      ],
                    ),
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      crossAxisAlignment: CrossAxisAlignment.center,
                      children: [
                        Expanded(
                          child: Container(
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.center,
                              children: [
                                Container(
                                  alignment: Alignment.center,
                                  margin: EdgeInsets.only(top: 20),
                                  child: Text(
                                    "PROCEED TO SIGN VIA",
                                    style: TextStyle(color: Color(0xFF0066CC), fontFamily: 'century_gothic', fontSize: 12, fontWeight: FontWeight.w600),
                                  ),
                                ),
                                Container(
                                  alignment: Alignment.center,
                                  margin: EdgeInsets.only(top: 10, bottom: 20),
                                  child: Text(
                                    "AADHAAR",
                                    style: TextStyle(color: Color(0xFFFAB804), fontFamily: 'century_gothic', fontSize: 12, fontWeight: FontWeight.w600),
                                  ),
                                ),
                              ],
                            ),
                          ),
                        ),
                        Expanded(
                          child: Container(
                            alignment: Alignment.centerRight,
                            padding: EdgeInsets.only(right: 20, top: 10),
                            child: this.esignbloc.addharselected
                                ? Image.asset(
                                    'asset/images/esignselected.png',
                                    height: 25,
                                    width: 25,
                                  )
                                : Image.asset(
                                    'asset/images/esign.png',
                                    height: 25,
                                    width: 25,
                                  ),
                          ),
                        ),
                      ],
                    ),
                  ),
                ),
              ),
            ],
          ),
          Row(
            children: [
              Expanded(
                child: InkWell(
                  onTap: () {
                    setState(() {
                      esignbloc.addharselected = false;
                      esignbloc.emudraselected = true;
                      esignbloc.manuallyselected = false;
                    });
                  },
                  child: Container(
                    margin: EdgeInsets.all(10),
                    decoration: new BoxDecoration(
                      color: Color(0xFFFFFFFF),
                      shape: BoxShape.rectangle,
                      borderRadius: BorderRadius.circular(5),
                      boxShadow: [
                        BoxShadow(
                          color: Color(0xFF9B9B9B),
                          blurRadius: 10.0,
                          offset: const Offset(10.10, 10.10),
                        ),
                      ],
                    ),
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      crossAxisAlignment: CrossAxisAlignment.center,
                      children: [
                        Expanded(
                          child: Container(
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.center,
                              children: [
                                Container(
                                  alignment: Alignment.center,
                                  margin: EdgeInsets.only(top: 20),
                                  child: Text(
                                    "PROCEED TO SIGN VIA",
                                    style: TextStyle(color: Color(0xFF0066CC), fontFamily: 'century_gothic', fontSize: 12, fontWeight: FontWeight.w600),
                                  ),
                                ),
                                Container(
                                  alignment: Alignment.center,
                                  margin: EdgeInsets.only(top: 10, bottom: 20),
                                  child: Text(
                                    "E-MUDRA",
                                    style: TextStyle(color: Color(0xFFFAB804), fontFamily: 'century_gothic', fontSize: 12, fontWeight: FontWeight.w600),
                                  ),
                                ),
                              ],
                            ),
                          ),
                        ),
                        Expanded(
                          child: Container(
                            alignment: Alignment.centerRight,
                            padding: EdgeInsets.only(right: 20, top: 10),
                            child: this.esignbloc.emudraselected
                                ? Image.asset(
                                    'asset/images/esignselected.png',
                                    height: 25,
                                    width: 25,
                                  )
                                : Image.asset(
                                    'asset/images/esign.png',
                                    height: 25,
                                    width: 25,
                                  ),
                          ),
                        ),
                      ],
                    ),
                  ),
                ),
              ),
            ],
          ),
          Row(
            children: [
              Expanded(
                child: InkWell(
                  onTap: () {
                    setState(() {
                      esignbloc.addharselected = false;
                      esignbloc.emudraselected = false;
                      esignbloc.manuallyselected = true;
                    });
                  },
                  child: Container(
                    margin: EdgeInsets.all(10),
                    decoration: new BoxDecoration(
                      color: Color(0xFFFFFFFF),
                      shape: BoxShape.rectangle,
                      borderRadius: BorderRadius.circular(5),
                      boxShadow: [
                        BoxShadow(
                          color: Color(0xFF9B9B9B),
                          blurRadius: 10.0,
                          offset: const Offset(10.10, 10.10),
                        ),
                      ],
                    ),
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      crossAxisAlignment: CrossAxisAlignment.center,
                      children: [
                        Expanded(
                          child: Container(
                            child: Column(
                              crossAxisAlignment: CrossAxisAlignment.center,
                              children: [
                                Container(
                                  alignment: Alignment.center,
                                  margin: EdgeInsets.only(top: 20),
                                  child: Text(
                                    "DOWNLOAD AND",
                                    style: TextStyle(color: Color(0xFF0066CC), fontFamily: 'century_gothic', fontSize: 12, fontWeight: FontWeight.w600),
                                  ),
                                ),
                                Container(
                                  alignment: Alignment.center,
                                  margin: EdgeInsets.only(top: 10, bottom: 20),
                                  child: Text(
                                    "CURIER MANUALLY",
                                    style: TextStyle(color: Color(0xFFFAB804), fontFamily: 'century_gothic', fontSize: 12, fontWeight: FontWeight.w600),
                                  ),
                                ),
                              ],
                            ),
                          ),
                        ),
                        Expanded(
                          child: Container(
                            alignment: Alignment.centerRight,
                            padding: EdgeInsets.only(right: 20, top: 10),
                            child: this.esignbloc.manuallyselected
                                ? Image.asset(
                                    'asset/images/esignselected.png',
                                    height: 25,
                                    width: 25,
                                  )
                                : Image.asset(
                                    'asset/images/esign.png',
                                    height: 25,
                                    width: 25,
                                  ),
                          ),
                        ),
                      ],
                    ),
                  ),
                ),
              ),
            ],
          ),
        ],
      ),
    );
  }

  btnprocess(BuildContext context) {
    return Container(
      child: Row(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Container(
            alignment: Alignment.centerRight,
            margin: EdgeInsets.only(right: 15, top: 50),
            child: Container(
              height: 45,
              width: 125,
              margin: EdgeInsets.all(20),
              padding: EdgeInsets.only(top: 4, left: 4, bottom: 4, right: 4),
              decoration: BoxDecoration(
                  border: Border.all(
                    color: Colors.blue[200],
                  ),
                  borderRadius: BorderRadius.circular(20) // use instead of BorderRadius.all(Radius.circular(20))
                  ),
              child: TextButton(
                child: Text(
                  "PROCEED",
                  style: TextStyle(color: Color(0xFFFFFFFF), fontFamily: 'century_gothic', fontSize: 12, fontWeight: FontWeight.w600),
                ),
                style: ButtonStyle(
                  padding: MaterialStateProperty.all<EdgeInsets>(EdgeInsets.all(1)),
                  foregroundColor: MaterialStateProperty.all<Color>(Colors.white),
                  shape: MaterialStateProperty.all<RoundedRectangleBorder>(RoundedRectangleBorder(borderRadius: BorderRadius.circular(15), side: BorderSide(color: Colors.blue[200]))),
                  backgroundColor: MaterialStateProperty.all<Color>(Color(0xFF0074C4)),
                ),
                // onPressed: () =>
                //     HomeScreenLarge.screensStreamLarge.sink.add(Pandetails()),
                onPressed: () => saveEsignDetails(context),
                // onPressed: () => proceedFurtherforESigning(context),
              ),
            ),
          ),
        ],
      ),
    );
  }

  pdfwrite(BuildContext context) {
    return Container(
      child: Row(
        mainAxisAlignment: MainAxisAlignment.end,
        crossAxisAlignment: CrossAxisAlignment.end,
        children: [
          Container(
            alignment: Alignment.centerRight,
            margin: EdgeInsets.only(right: 15, top: 100),
            child: Container(
              height: 45,
              width: 125,
              margin: EdgeInsets.all(20),
              padding: EdgeInsets.only(top: 4, left: 4, bottom: 4, right: 4),
              decoration: BoxDecoration(
                  border: Border.all(
                    color: Colors.blue[200],
                  ),
                  borderRadius: BorderRadius.circular(20) // use instead of BorderRadius.all(Radius.circular(20))
                  ),
              child: TextButton(
                child: Text(
                  "Generate PDF",
                  style: TextStyle(color: Color(0xFFFFFFFF), fontFamily: 'century_gothic', fontSize: 12, fontWeight: FontWeight.w600),
                ),
                style: ButtonStyle(
                  padding: MaterialStateProperty.all<EdgeInsets>(EdgeInsets.all(1)),
                  foregroundColor: MaterialStateProperty.all<Color>(Colors.white),
                  shape: MaterialStateProperty.all<RoundedRectangleBorder>(RoundedRectangleBorder(borderRadius: BorderRadius.circular(15), side: BorderSide(color: Colors.blue[200]))),
                  backgroundColor: MaterialStateProperty.all<Color>(Color(0xFF0074C4)),
                ),
                // onPressed: () =>
                //     HomeScreenLarge.screensStreamLarge.sink.add(Pandetails()),
                onPressed: () => Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => PdfWriteScreen()),
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }

//Commented out for future Cause

  // proceedFurtherforESigning(BuildContext context) {
  //   if (validateLocationDetails()) {
  //     saveEsignDetails(context);
  //   }
  // }

  // bool validateLocationDetails() {
  //   String lat = LoginRepository.latitude;
  //   String long = LoginRepository.longitude;
  //   if ((lat.isNotEmpty && lat.length > 1) &&
  //       (long.isNotEmpty && long.length > 1)) {
  //     //Got the location data now proceed further for E-Signing
  //     return true;
  //   } else {
  //     //Look for permission
  //     switch (LoginBloc.permissionGranted) {
  //       case PermissionStatus.granted:
  //         showAlert(context, 'Permission Granted');
  //         LoginBloc().getLocation();
  //         return false;
  //         break;
  //       case PermissionStatus.denied:
  //         showAlert(context, 'Please allow location to proceed.');
  //         LoginBloc().requestPermission();
  //         return false;
  //         break;
  //       case PermissionStatus.grantedLimited:
  //         showAlert(context, 'Permission Granted Limited');
  //         LoginBloc().requestPermission();
  //         return false;
  //         break;
  //       case PermissionStatus.deniedForever:
  //         showAlert(context,
  //             'Please allow location service and refresh it to fetch location');
  //         LoginBloc().requestPermission();
  //         return false;
  //         break;
  //     }
  //     return false;
  //   }
  // }

  saveEsignDetails(BuildContext context) async {
    var esignselectionflag;
    LoginRepository loginRepository = new LoginRepository();
    var response = globalRespObj.response.data.message[0];

    LoginUserDetailModelResponse obj = await loginRepository.getEkycUserDetails(response.emailId, response.mobileNo, response.fullName);
    if (this.esignbloc.addharselected) {
      esignselectionflag = "0";

      // if (LoginRepository.latitude != null &&
      //     LoginRepository.latitude.isNotEmpty &&
      //     LoginRepository.latitude != "undefined" &&
      //     LoginRepository.longitude != null &&
      //     LoginRepository.longitude.isNotEmpty &&
      //     LoginRepository.longitude != "undefined") {
      if (obj.response.data.message[0].ekycdocument != "" && int.parse(obj.response.data.message[0].stage) > 7) {
        showDialog(
            context: context,
            builder: (BuildContext context) {
              return Center(child: customDialogmethod(context));
            });
      } else {
        // stop upload pdf to digio if issue like you dont have credit
        EsignRepository esignRepository = new EsignRepository();
        esignRepository.signPDF(context, "large");
      }
      // } else {
      //   showAlert(context,
      //       "Please allow location and wait while fetching your location");
      //   await LoginRepository().getLocation();
      // }
    } else if (this.esignbloc.emudraselected) {
      esignselectionflag = "1";
      if (obj.response.data.message[0].ekycdocument != "" && int.parse(obj.response.data.message[0].stage) > 7) {
        showDialog(
            context: context,
            builder: (BuildContext context) {
              return Center(child: customDialogmethod(context));
            });
      }
    } else if (this.esignbloc.manuallyselected) {
      esignselectionflag = "2";
      if (obj.response.data.message[0].ekycdocument != "" && int.parse(obj.response.data.message[0].stage) > 7) {
        showDialog(
            context: context,
            builder: (BuildContext context) {
              return Center(child: customDialogmethod(context));
            });
      }
    }
    // var obj = await this
    //     .esignbloc
    //     .navtoesigndetail(context, esignselectionflag, "small");
    // if (obj) {
    //   showDialog(
    //       context: context,
    //       builder: (BuildContext context) {
    //         return Center(child: CustomDialogmethod());
    //       });
    // } else {}
  }

  Future<void> selectPdfFile(BuildContext context) async {
    PlatformFile objFile;
    FilePickerResult result = await FilePicker.platform.pickFiles(
      type: FileType.custom,
      withReadStream: true,
      allowedExtensions: ['pdf'],
    );
    if (result != null) {
      objFile = result.files.single;
      if (objFile.name.contains('.pdf') || objFile.name.contains('.PDF')) {
        //   esignbloc.sendPDF(context, objFile, null, "large");
      } else {
        showAlert(context, "Only PDF supported");
      }
    }
  }

  showAlert(BuildContext ctx, String msg) {
    return showDialog(
      context: ctx,
      builder: (ctx) => AlertDialog(
        title: Text("E-KYC"),
        content: Text(msg),
        actions: <Widget>[
          TextButton(
            onPressed: () async {
              Navigator.of(ctx).pop();
            },
            child: Text("OK"),
          ),
        ],
      ),
    );
  }

  customDialogmethod(BuildContext context) {
    return Container(
      margin: EdgeInsets.only(top: 50),
      width: 300,
      height: 300,
      decoration: new BoxDecoration(
        color: Color(0xFFFFFFFF),
        shape: BoxShape.rectangle,
        borderRadius: BorderRadius.circular(5),
        boxShadow: [
          BoxShadow(
            color: Color(0xFF9B9B9B),
            blurRadius: 10.0,
            offset: const Offset(10.10, 10.10),
          ),
        ],
      ),
      child: Column(
        //mainAxisAlignment: MainAxisAlignment.center,
        // crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.end,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Material(
                child: InkWell(
                  onTap: () {
                    Navigator.pop(context);
                  },
                  child: Container(
                    alignment: Alignment.topRight,
                    child: Icon(
                      Icons.close,
                      color: Colors.black,
                    ),
                  ),
                ),
              ),
            ],
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              Expanded(
                child: Container(
                  margin: EdgeInsets.all(25),
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Container(
                        alignment: Alignment.centerLeft,
                        margin: EdgeInsets.only(top: 50),
                        child: Text(
                          "Congratulation ! Your E-KYC is now complete",
                          style: TextStyle(color: Color(0xFF0066CC), fontFamily: 'century_gothic', fontSize: 13, fontWeight: FontWeight.w600),
                        ),
                      ),
                      Container(
                        alignment: Alignment.centerLeft,
                        margin: EdgeInsets.only(top: 15),
                        child: Text(
                          "Our representative will get in touch with you soon",
                          style: TextStyle(color: Color(0xFF0066CC), fontFamily: 'century_gothic', fontSize: 13, fontWeight: FontWeight.w600),
                        ),
                      ),
                      Container(
                        alignment: Alignment.centerLeft,
                        margin: EdgeInsets.only(top: 50),
                        child: Text(
                          "Team,",
                          style: TextStyle(color: Color(0xFF0066CC), fontFamily: 'century_gothic', fontSize: 13, fontWeight: FontWeight.w600),
                        ),
                      ),
                      Container(
                        alignment: Alignment.centerLeft,
                        margin: EdgeInsets.only(top: 5),
                        child: Text(
                          "Vishwas",
                          style: TextStyle(color: Color(0xFF0066CC), fontFamily: 'century_gothic', fontSize: 13, fontWeight: FontWeight.w600),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ],
          ),
        ],
      ),
    );
  }
}
