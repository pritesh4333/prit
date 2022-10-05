import 'dart:html';
import 'dart:math';
import 'dart:typed_data';

import 'package:e_kyc/Login/UI/Configuration/AppConfig.dart';
import 'package:e_kyc/Login/UI/DocumentScreen/bloc/DocumentBloc.dart';
import 'package:e_kyc/Login/UI/EsignScreen/repository/EsignRepository.dart';
import 'package:e_kyc/Login/UI/HomeScreen/HomeScreenLarge.dart';
import 'package:e_kyc/Login/UI/Login/View/LoginUI.dart';
import 'package:e_kyc/Login/UI/Login/repository/LoginRepository.dart';
import 'package:e_kyc/Login/UI/PanDetailsScreen/repository/PanDetailRepository.dart';
import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'dart:async';
import 'package:http/http.dart' as http;
import 'package:e_kyc/Login/UI/IPVScreen/platformspec/mobiledevice.dart' if (dart.library.html) "package:e_kyc/Login/UI/IPVScreen/platformspec/webdevice.dart" as newWindow;
import 'package:image/image.dart' as img;

class DocumentScreenLarge extends StatefulWidget {
  @override
  _DocumentScreenLargeState createState() => _DocumentScreenLargeState();
}

class _DocumentScreenLargeState extends State<DocumentScreenLarge> {
  DocumentBloc documentBloc = new DocumentBloc();
  var _chars = 'AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz1234567890';
  Random _rnd = Random();
  var globalRespObj = LoginRepository.loginDetailsResObjGlobal;

  var galleryImage;
  // var _pickedImages;
  // final _pickedVideos = <dynamic>[];
  var validfullname = "", validemail = "", validmobile = "", validclientcode = "";
  var panDetails;
  var _chosenValue, _chosenValue1, _chosenValue2;

  @override
  void dispose() {
    super.dispose();
  }

  @override
  void initState() {
    super.initState();
    HomeScreenLarge.percentageFlagLarge.add("0.805");

    //Setting predefine data from DB
    if (globalRespObj != null) {
      print('Data already is in global object');
      if (globalRespObj.response.errorCode == "0") {
        var stage = int.parse(globalRespObj.response.data.message[0].stage);
        panDetails = globalRespObj.response.data.message[0].pan;
        print('Stage = $stage');
        var docObj = globalRespObj.response.data.message[0];
        documentBloc.docPan = docObj.pancard.contains('pancard') ? true : false;
        documentBloc.docSignature = docObj.signature.contains('signature') ? true : false;
        documentBloc.docBankProof = docObj.bankproof.contains('bankproof') ? true : false;
        documentBloc.docAddressProof = docObj.addressproof.contains('addressproof') ? true : false;
        documentBloc.docIncomeProof = docObj.incomeproof.contains('incomeproof') ? true : false;
        documentBloc.docPhotograph = docObj.photograph.contains('photograph') ? true : false;

        //Replace this code with below code
        int bankProof = 0, addressProof = 0, incomeProof = 0;
        if (docObj.bankprroftype != null && docObj.bankprroftype != "") {
          bankProof = int.parse(docObj.bankprroftype);
        }
        if (docObj.addressprroftype != null && docObj.addressprroftype != "") {
          addressProof = int.parse(docObj.addressprroftype);
        }
        if (docObj.incomeprroftype != null && docObj.incomeprroftype != "") {
          incomeProof = int.parse(docObj.incomeprroftype);
        }

        //BankProof, address proof, income proof dropdown
        _chosenValue = documentBloc.bankProofList[bankProof];
        documentBloc.bankProof = _chosenValue;
        _chosenValue1 = documentBloc.addressProofList[addressProof];
        documentBloc.addressProof = _chosenValue1;
        _chosenValue2 = documentBloc.incomeProofList[incomeProof];
        documentBloc.incomeProof = _chosenValue2;
      } else {
        panDetails = PanDetailRepository.userPanNumber;
      }
    }
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
          header(context),
          documentDetailsForm(context),
          deviderline(context),
          documentDetailsFormtwo(context),
          btnprocess(context),
        ],
      ),
    ))));
  }

  header(BuildContext context) {
    return Container(
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Container(
            padding: EdgeInsets.only(top: 5),
            width: 100,
            height: 60,
            child: Image.asset(
              'asset/images/uploadheader.png',
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
                    "UPLOAD DOCUMENTS ",
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

  documentDetailsForm(BuildContext context) {
    return Container(
      padding: EdgeInsets.only(left: 25, top: 25, right: 25),
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
                    margin: EdgeInsets.only(top: 10),
                    child: Text(
                      "PAN CARD*",
                      style: TextStyle(fontSize: 12, color: Color(0xFF000000), fontFamily: 'century_gothic', fontWeight: FontWeight.w600),
                    ),
                  ),
                  Container(
                    alignment: Alignment.center,
                    margin: EdgeInsets.only(top: 10),
                    child: Text(
                      "Upload front side of your pan card",
                      style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 12, fontWeight: FontWeight.w300),
                    ),
                  ),
                  Container(
                    padding: EdgeInsets.only(top: 10),
                    width: 100,
                    height: 60,
                    child: Image.asset(
                      'asset/images/uploadblack.png',
                    ),
                  ),
                  Container(
                    child: Row(
                      crossAxisAlignment: CrossAxisAlignment.center,
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: [
                        Expanded(
                          child: Container(
                            margin: EdgeInsets.all(15),
                            child: Row(
                              crossAxisAlignment: CrossAxisAlignment.center,
                              mainAxisAlignment: MainAxisAlignment.spaceBetween,
                              children: [
                                Expanded(
                                  child: Container(
                                    // padding:EdgeInsets.all(1),
                                    margin: EdgeInsets.only(right: 10, left: 10),
                                    decoration: new BoxDecoration(
                                      color: Color(0xFFFFFFFF),
                                      shape: BoxShape.rectangle,
                                      borderRadius: BorderRadius.circular(5),
                                      boxShadow: [
                                        BoxShadow(
                                          color: Color(0xFF9B9B9B),
                                          blurRadius: 5.0,
                                          offset: const Offset(5.0, 5.0),
                                        ),
                                      ],
                                    ),
                                    child: TextButton(
                                      child: Text(
                                        'UPLOAD',
                                        style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 11, fontWeight: FontWeight.w500),
                                      ),
                                      onPressed: () async {
                                        print('Pressed PAN Card');
                                        customOnPressed(101, "");
                                      },
                                    ),
                                  ),
                                ),
                                Expanded(
                                  child: Container(
                                    // width: 100,
                                    margin: EdgeInsets.only(right: 10, left: 10),
                                    // padding:EdgeInsets.all(10),
                                    decoration: new BoxDecoration(
                                      color: Color(0xFF9B9B9B),
                                      shape: BoxShape.rectangle,
                                      borderRadius: BorderRadius.circular(5),
                                      boxShadow: [
                                        BoxShadow(
                                          color: Color(0xFF9B9B9B),
                                          blurRadius: 5.0,
                                          offset: const Offset(5.0, 5.0),
                                        ),
                                      ],
                                    ),
                                    child: TextButton(
                                      child: Text(
                                        'VIEW',
                                        style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 11, fontWeight: FontWeight.w500),
                                      ),
                                      onPressed: () {
                                        print('Pressed');
                                        if (documentBloc.docPan != null) {
                                          if (documentBloc.docPan) {
                                            openUploadedImage(101, context);
                                          }
                                        }
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
                  Container(
                    alignment: Alignment.center,
                    margin: EdgeInsets.only(top: 10),
                    child: Column(
                      children: [
                        Text(
                          "Maximum upload file size: 2 MB",
                          style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 12, fontWeight: FontWeight.w300),
                        ),
                        Text(
                          "Only PNG/JPEG/JPG supported",
                          style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 12, fontWeight: FontWeight.w300),
                        ),
                      ],
                    ),
                  ),
                ],
              ),
            ),
          ),
          Expanded(
            child: Container(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.center,
                children: [
                  Container(
                    alignment: Alignment.center,
                    margin: EdgeInsets.only(top: 10),
                    child: Text(
                      "SIGNATURE",
                      style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 12, fontWeight: FontWeight.w600),
                    ),
                  ),
                  Container(
                    alignment: Alignment.center,
                    margin: EdgeInsets.only(top: 10),
                    child: Text(
                      "Sign in white paper and upload it",
                      style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 12, fontWeight: FontWeight.w300),
                    ),
                  ),
                  Container(
                    padding: EdgeInsets.only(top: 10),
                    width: 100,
                    height: 60,
                    child: Image.asset(
                      'asset/images/uploadblack.png',
                    ),
                  ),
                  Container(
                    child: Row(
                      crossAxisAlignment: CrossAxisAlignment.center,
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: [
                        Expanded(
                          child: Container(
                            margin: EdgeInsets.all(15),
                            child: Row(
                              crossAxisAlignment: CrossAxisAlignment.center,
                              mainAxisAlignment: MainAxisAlignment.spaceBetween,
                              children: [
                                Expanded(
                                  child: Container(
                                    // padding:EdgeInsets.all(1),
                                    margin: EdgeInsets.only(right: 10, left: 10),
                                    decoration: new BoxDecoration(
                                      color: Color(0xFFFFFFFF),
                                      shape: BoxShape.rectangle,
                                      borderRadius: BorderRadius.circular(5),
                                      boxShadow: [
                                        BoxShadow(
                                          color: Color(0xFF9B9B9B),
                                          blurRadius: 5.0,
                                          offset: const Offset(5.0, 5.0),
                                        ),
                                      ],
                                    ),
                                    child: TextButton(
                                        child: Text(
                                          'UPLOAD',
                                          style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 11, fontWeight: FontWeight.w500),
                                        ),
                                        onPressed: () async {
                                          print('Pressed');
                                          customOnPressed(102, "");
                                        }),
                                  ),
                                ),
                                Expanded(
                                  child: Container(
                                    // width: 100,
                                    margin: EdgeInsets.only(right: 10, left: 10),
                                    // padding:EdgeInsets.all(10),
                                    decoration: new BoxDecoration(
                                      color: Color(0xFF9B9B9B),
                                      shape: BoxShape.rectangle,
                                      borderRadius: BorderRadius.circular(5),
                                      boxShadow: [
                                        BoxShadow(
                                          color: Color(0xFF9B9B9B),
                                          blurRadius: 5.0,
                                          offset: const Offset(5.0, 5.0),
                                        ),
                                      ],
                                    ),
                                    child: TextButton(
                                      child: Text(
                                        'VIEW',
                                        style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 11, fontWeight: FontWeight.w500),
                                      ),
                                      onPressed: () {
                                        print('Pressed');
                                        if (documentBloc.docSignature != null) {
                                          if (documentBloc.docSignature) {
                                            openUploadedImage(102, context);
                                          }
                                        }
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
                  Container(
                    alignment: Alignment.center,
                    margin: EdgeInsets.only(top: 10),
                    child: Column(
                      children: [
                        Text(
                          "Maximum upload file size: 2 MB",
                          style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 12, fontWeight: FontWeight.w300),
                        ),
                        Text(
                          "Only PNG/JPEG/JPG supported",
                          style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 12, fontWeight: FontWeight.w300),
                        ),
                      ],
                    ),
                  ),
                ],
              ),
            ),
          ),
          Expanded(
            child: Container(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.center,
                children: [
                  Container(
                    alignment: Alignment.center,
                    margin: EdgeInsets.only(top: 10),
                    child: Text(
                      "BANK PROOF",
                      style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 12, fontWeight: FontWeight.w600),
                    ),
                  ),
                  Container(
                    height: 30,
                    padding: EdgeInsets.only(left: 10, right: 10),
                    margin: EdgeInsets.only(left: 15, right: 15, top: 30, bottom: 25),
                    color: Color(0XFFEBEBEB),
                    child: DropdownButtonHideUnderline(
                      child: DropdownButton<String>(
                        value: _chosenValue,
                        hint: Text(
                          "Select Bank Proof",
                          style: TextStyle(
                            color: Color(0xFF000000),
                            fontFamily: 'century_gothic',
                            fontSize: 12,
                            fontWeight: FontWeight.w400,
                          ),
                        ),
                        items: documentBloc.bankProofList.map((String value) {
                          return DropdownMenuItem(
                            value: value,
                            child: Text(
                              value,
                              style: TextStyle(
                                color: Color(0xFF000000),
                                fontFamily: 'century_gothic',
                                fontSize: 12,
                                fontWeight: FontWeight.w400,
                              ),
                            ),
                          );
                        }).toList(),
                        onChanged: (String value) {
                          setState(() {
                            _chosenValue = value;
                            documentBloc.bankProof = _chosenValue;
                            print(documentBloc.bankProof);
                          });
                        },
                      ),
                    ),
                  ),
                  Container(
                    child: Row(
                      crossAxisAlignment: CrossAxisAlignment.center,
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: [
                        Expanded(
                          child: Container(
                            margin: EdgeInsets.all(15),
                            child: Row(
                              crossAxisAlignment: CrossAxisAlignment.center,
                              mainAxisAlignment: MainAxisAlignment.spaceBetween,
                              children: [
                                Expanded(
                                  child: Container(
                                    // padding:EdgeInsets.all(1),
                                    margin: EdgeInsets.only(right: 10, left: 10),
                                    decoration: new BoxDecoration(
                                      color: Color(0xFFFFFFFF),
                                      shape: BoxShape.rectangle,
                                      borderRadius: BorderRadius.circular(5),
                                      boxShadow: [
                                        BoxShadow(
                                          color: Color(0xFF9B9B9B),
                                          blurRadius: 5.0,
                                          offset: const Offset(5.0, 5.0),
                                        ),
                                      ],
                                    ),
                                    child: TextButton(
                                      child: Text(
                                        'UPLOAD',
                                        style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 11, fontWeight: FontWeight.w500),
                                      ),
                                      onPressed: () {
                                        print('Pressed');
                                        if (documentBloc.bankProof.isNotEmpty) {
                                          String proofType = "";
                                          switch (documentBloc.bankProof) {
                                            case "Cancelled Cheque":
                                              proofType = "0";
                                              break;
                                            case "Bank Passbook":
                                              proofType = "1";
                                              break;
                                            case "Latest Bank Statement":
                                              proofType = "2";
                                              break;
                                            default:
                                          }
                                          customOnPressed(103, proofType);
                                        } else {
                                          documentBloc.showAlert(context, "Select Bank Proof");
                                        }
                                      },
                                    ),
                                  ),
                                ),
                                Expanded(
                                  child: Container(
                                    // width: 100,
                                    margin: EdgeInsets.only(right: 10, left: 10),
                                    // padding:EdgeInsets.all(10),
                                    decoration: new BoxDecoration(
                                      color: Color(0xFF9B9B9B),
                                      shape: BoxShape.rectangle,
                                      borderRadius: BorderRadius.circular(5),
                                      boxShadow: [
                                        BoxShadow(
                                          color: Color(0xFF9B9B9B),
                                          blurRadius: 5.0,
                                          offset: const Offset(5.0, 5.0),
                                        ),
                                      ],
                                    ),
                                    child: TextButton(
                                      child: Text(
                                        'VIEW',
                                        style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 11, fontWeight: FontWeight.w500),
                                      ),
                                      onPressed: () {
                                        print('Pressed');
                                        if (documentBloc.docBankProof != null) {
                                          if (documentBloc.docBankProof) {
                                            openUploadedImage(
                                              103,
                                              context,
                                            );
                                          }
                                        }
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
                  Container(
                    alignment: Alignment.center,
                    margin: EdgeInsets.only(top: 10),
                    child: Column(
                      children: [
                        Text(
                          "Maximum upload file size: 2 MB",
                          style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 12, fontWeight: FontWeight.w300),
                        ),
                        Text(
                          "Only PDF supported",
                          style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 12, fontWeight: FontWeight.w300),
                        ),
                      ],
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

  void customOnPressed(int id, String proofType) {
    var docObj = globalRespObj.response.data.message[0];
    switch (id) {
      case 101:
        print('PAN Card :$id');
        openChoiceBottomsheetImagePicker(id, 'pancard', proofType, docObj.uniqueId, ['png', 'jpeg', 'PNG', 'JPEG', 'jpg', 'JPG']);
        break;
      case 102:
        print(' Signature :$id');
        openChoiceBottomsheetImagePicker(id, 'signature', proofType, docObj.uniqueId, ['png', 'jpeg', 'PNG', 'JPEG', 'jpg', 'JPG']);
        break;
      case 103:
        print('Bank Proof :$id');
        openChoiceBottomsheetImagePicker(id, 'bankproof', proofType, docObj.uniqueId, ['pdf', 'PDF']);
        break;
      case 104:
        print('Address Proof :$id');
        openChoiceBottomsheetImagePicker(id, 'addressproof', proofType, docObj.uniqueId, ['png', 'jpeg', 'PNG', 'JPEG', 'jpg', 'JPG']);
        break;
      case 105:
        print('Income Proof :$id');
        openChoiceBottomsheetImagePicker(id, 'incomeproof', proofType, docObj.uniqueId, ['pdf', 'PDF']);
        break;
      case 106:
        print('Photograph :$id');
        openChoiceBottomsheetImagePicker(id, 'photograph', proofType, docObj.uniqueId, ['png', 'jpeg', 'PNG', 'JPEG', 'jpg', 'JPG']);
        break;

      default:
    }
  }

  void openChoiceBottomsheetImagePicker(int id, String docType, String proofType, String uniqueId, List<String> allowedExtension) {
    AppConfig().showLoaderDialog(context);
    selectImageFile(docType, proofType, uniqueId, allowedExtension);
    /*
    return showModalBottomSheet(
      context: this.context,
      builder: (BuildContext bc) {
        return SafeArea(
          child: Container(
            height: 150,
            child: Column(
              mainAxisSize: MainAxisSize.min,
              children: <Widget>[
                ListTile(
                  leading: Icon(Icons.camera_alt),
                  title: Text('Camera'),
                  onTap: () async {
                    Navigator.pop(bc);
                    // _pickImage(id, this.context);
                    _saveImage(docType);
                  },
                ),
                ListTile(
                  leading: Icon(Icons.collections),
                  title: Text('Gallery'),
                  onTap: () async {
                    Navigator.pop(bc);
                    // _pickImage(id, this.context);
                    // _saveImage(docType);
                    selectImageFile(docType, proofType);
                  },
                )
              ],
            ),
          ),
        );
      },
    );
    */
  }

  Future<void> selectImageFile(String docType, String proofType, String uniqueId, List<String> extensionAllowed) async {
    PlatformFile objFile;
    var result = await FilePicker.platform.pickFiles(
      type: FileType.custom,
      allowedExtensions: extensionAllowed,
    );
    if (result != null) {
      objFile = result.files.single;
      double sizeInMb = result.files[0].size / (1024 * 1024);
      // show loader
      if (docType.toString() == "bankproof" || docType.toString() == "incomeproof") {
        if (objFile.name.contains('.pdf') || objFile.name.contains('.PDF')) {
          if (sizeInMb > 2) {
            // This file is Longer the
            print('file is big');
            AppConfig().dismissLoaderDialog(context); //
            documentBloc.showAlert(context, 'Maximum upload file size: 2 MB');
          } else {
            print('file is pefect');
            AppConfig().dismissLoaderDialog(context); //
            documentBloc.saveDocument(context, docType, "large", objFile, objFile.bytes, proofType, uniqueId);
          }
        } else {
          AppConfig().dismissLoaderDialog(context); //

          documentBloc.showAlert(context, "Only PDF File supported");
        }
      } else {
        if (docType.toString() == "pancard" || docType.toString() == "signature" || docType.toString() == "addressproof" || docType.toString() == "photograph") {
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
              documentBloc.saveDocument(context, docType, "large", objFile, filess, proofType, uniqueId);
            }
          } else {
            AppConfig().dismissLoaderDialog(context); // show loader

            documentBloc.showAlert(context, "Only 'png','PNG' 'jpeg', 'JPEG' ,'jpg' ,'JPG' supported");
          }
        }
      }
    } else {
      AppConfig().dismissLoaderDialog(context); // show loader

      // documentBloc.showAlert(context, "Please upload all documents");
    }
  }

  bool validateDocumentUploaded(var document) {
    if (document != null) {
      return true;
    }
    return false;
  }

//This is for Preview PDF files.
  previewPDFImage(String fileName) {
    var panDetails;

    var globalRespObj = LoginRepository.loginDetailsResObjGlobal;
    if (PanDetailRepository.userPanNumber != null && PanDetailRepository.userPanNumber != "") {
      panDetails = PanDetailRepository.userPanNumber;
    } else {
      if (globalRespObj != null) {
        if (globalRespObj.response.errorCode == "0") {
          panDetails = globalRespObj.response.data.message[0].pan;
        }
      }
    }
    String finalUrl = AppConfig().url + 'viewDocuments?email_id=' + LoginRepository.loginEmailId + '&mobile_no=' + LoginRepository.loginMobileNo + '&type=' + fileName + '&pan=' + panDetails + '&rendom=' + getRandomString(5);
    print("latest change  " + finalUrl);
    newWindow.openNewTab(finalUrl);
  }

  String getRandomString(int length) => String.fromCharCodes(Iterable.generate(length, (_) => _chars.codeUnitAt(_rnd.nextInt(_chars.length))));

//This is for preview Image files.
  openUploadedImage(int id, BuildContext context) {
    switch (id) {
      case 101:
        print('PAN Card :$id');
        var fileName = LoginRepository.loginDetailsResObjGlobal.response.data.message[0].pancard;
        if (fileName.contains('.pdf') || fileName.contains('.PDF')) {
          previewPDFImage(fileName);
        } else {
          documentBloc.downloadImageToPreview(context, "pancard");
        }
        break;
      case 102:
        print(' Signature :$id');
        var fileName = LoginRepository.loginDetailsResObjGlobal.response.data.message[0].signature;
        if (fileName.contains('.pdf') || fileName.contains('.PDF')) {
          previewPDFImage("signature");
        } else {
          documentBloc.downloadImageToPreview(context, "signature");
        }
        break;
      case 103:
        print('Bank Proof :$id');
        var fileName = LoginRepository.loginDetailsResObjGlobal.response.data.message[0].bankproof;
        if (fileName.contains('.pdf') || fileName.contains('.PDF')) {
          previewPDFImage("bankproof");
        } else {
          documentBloc.downloadImageToPreview(context, "bankproof");
        }
        break;
      case 104:
        print('Address Proof :$id');
        var fileName = LoginRepository.loginDetailsResObjGlobal.response.data.message[0].addressproof;
        if (fileName.contains('.pdf') || fileName.contains('.PDF')) {
          previewPDFImage("addressproof");
        } else {
          documentBloc.downloadImageToPreview(context, "addressproof");
        }
        break;
      case 105:
        print('Income Proof :$id');
        var fileName = LoginRepository.loginDetailsResObjGlobal.response.data.message[0].incomeproof;
        if (fileName.contains('.pdf') || fileName.contains('.PDF')) {
          previewPDFImage("incomeproof");
        } else {
          documentBloc.downloadImageToPreview(context, "incomeproof");
        }
        break;
      case 106:
        print('Photograph :$id');
        var fileName = LoginRepository.loginDetailsResObjGlobal.response.data.message[0].photograph;
        if (fileName.contains('.pdf') || fileName.contains('.PDF')) {
          previewPDFImage(fileName);
        } else {
          documentBloc.downloadImageToPreview(context, "photograph");
        }
        break;
      default:
    }
  }

  // previewImage(BuildContext context, String imageType) {
  //   var url = AppConfig().url;
  //   List<Widget> imagesDemo = [
  //     Image.network(url + 'downloadFileV2?imageName=prit'),
  //   ];
  //   showDialog(
  //     context: context,
  //     builder: (BuildContext context) {
  //       return ImagePreview(
  //           showIndex: true, closeable: false, images: imagesDemo);
  //     },
  //   );
  // }

  documentDetailsFormtwo(BuildContext context) {
    return Container(
      padding: EdgeInsets.only(left: 25, top: 25, right: 25),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceAround,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Expanded(
            child: Container(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.center,
                children: [
                  Container(
                    alignment: Alignment.center,
                    margin: EdgeInsets.only(top: 10),
                    child: Text(
                      "ADDRESS PROOF",
                      style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 12, fontWeight: FontWeight.w600),
                    ),
                  ),
                  Container(
                    height: 30,
                    padding: EdgeInsets.only(left: 10, right: 10),
                    margin: EdgeInsets.only(left: 15, right: 15, top: 30, bottom: 25),
                    color: Color(0XFFEBEBEB),
                    child: DropdownButtonHideUnderline(
                      child: DropdownButton<String>(
                        value: _chosenValue1,
                        hint: Text(
                          "Select Address Proof",
                          style: TextStyle(
                            color: Color(0xFF000000),
                            fontFamily: 'century_gothic',
                            fontSize: 12,
                            fontWeight: FontWeight.w400,
                          ),
                        ),
                        items: documentBloc.addressProofList.map((String value) {
                          return DropdownMenuItem(
                            value: value,
                            child: Text(
                              value,
                              style: TextStyle(
                                color: Color(0xFF000000),
                                fontFamily: 'century_gothic',
                                fontSize: 12,
                                fontWeight: FontWeight.w400,
                              ),
                            ),
                          );
                        }).toList(),
                        onChanged: (String value) {
                          setState(() {
                            _chosenValue1 = value;
                            documentBloc.addressProof = _chosenValue1;
                            print(documentBloc.addressProof);
                          });
                        },
                      ),
                    ),
                  ),
                  Container(
                    child: Row(
                      crossAxisAlignment: CrossAxisAlignment.center,
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: [
                        Expanded(
                          child: Container(
                            margin: EdgeInsets.all(15),
                            child: Row(
                              crossAxisAlignment: CrossAxisAlignment.center,
                              mainAxisAlignment: MainAxisAlignment.spaceBetween,
                              children: [
                                Expanded(
                                  child: Container(
                                    // padding:EdgeInsets.all(1),
                                    margin: EdgeInsets.only(right: 10, left: 10),
                                    decoration: new BoxDecoration(
                                      color: Color(0xFFFFFFFF),
                                      shape: BoxShape.rectangle,
                                      borderRadius: BorderRadius.circular(5),
                                      boxShadow: [
                                        BoxShadow(
                                          color: Color(0xFF9B9B9B),
                                          blurRadius: 5.0,
                                          offset: const Offset(5.0, 5.0),
                                        ),
                                      ],
                                    ),
                                    child: TextButton(
                                        child: Text(
                                          'UPLOAD',
                                          style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 11, fontWeight: FontWeight.w500),
                                        ),
                                        onPressed: () {
                                          print('Pressed');
                                          String proofType = "";
                                          if (documentBloc.addressProof.isNotEmpty) {
                                            switch (documentBloc.addressProof) {
                                              case "Voter ID":
                                                proofType = "0";
                                                break;
                                              case "Driving Licence":
                                                proofType = "1";
                                                break;
                                              case "Aadhar":
                                                proofType = "2";
                                                break;
                                              case "Passport":
                                                proofType = "3";
                                                break;
                                              case "NREGA job Card":
                                                proofType = "4";
                                                break;
                                              default:
                                            }
                                            customOnPressed(104, proofType);
                                          } else {
                                            documentBloc.showAlert(context, "Select Address Proof");
                                          }
                                        }),
                                  ),
                                ),
                                Expanded(
                                  child: Container(
                                    // width: 100,
                                    margin: EdgeInsets.only(right: 10, left: 10),
                                    // padding:EdgeInsets.all(10),
                                    decoration: new BoxDecoration(
                                      color: Color(0xFF9B9B9B),
                                      shape: BoxShape.rectangle,
                                      borderRadius: BorderRadius.circular(5),
                                      boxShadow: [
                                        BoxShadow(
                                          color: Color(0xFF9B9B9B),
                                          blurRadius: 5.0,
                                          offset: const Offset(5.0, 5.0),
                                        ),
                                      ],
                                    ),
                                    child: TextButton(
                                      child: Text(
                                        'VIEW',
                                        style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 11, fontWeight: FontWeight.w500),
                                      ),
                                      onPressed: () {
                                        print('Pressed');
                                        if (documentBloc.docAddressProof != null) {
                                          if (documentBloc.docAddressProof) {
                                            openUploadedImage(104, context);
                                          }
                                        }
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
                  Container(
                    alignment: Alignment.center,
                    margin: EdgeInsets.only(top: 10),
                    child: Column(
                      children: [
                        Text(
                          "Maximum upload file size: 2 MB",
                          style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 12, fontWeight: FontWeight.w300),
                        ),
                        Text(
                          "Only PNG/JPEG/JPG supported",
                          style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 12, fontWeight: FontWeight.w300),
                        ),
                      ],
                    ),
                  ),
                ],
              ),
            ),
          ),
          Expanded(
            child: Container(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.center,
                children: [
                  Container(
                    alignment: Alignment.center,
                    margin: EdgeInsets.only(top: 10),
                    child: Text(
                      "INCOME PROOF",
                      style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 12, fontWeight: FontWeight.w600),
                    ),
                  ),
                  Container(
                    height: 30,
                    padding: EdgeInsets.only(left: 10, right: 10),
                    margin: EdgeInsets.only(left: 15, right: 15, top: 30, bottom: 25),
                    color: Color(0XFFEBEBEB),
                    child: DropdownButtonHideUnderline(
                      child: DropdownButton<String>(
                        value: _chosenValue2,
                        hint: Text(
                          "Select Income Proof",
                          style: TextStyle(
                            color: Color(0xFF000000),
                            fontFamily: 'century_gothic',
                            fontSize: 12,
                            fontWeight: FontWeight.w400,
                          ),
                        ),
                        items: documentBloc.incomeProofList.map((String value) {
                          return DropdownMenuItem(
                            value: value,
                            child: Text(
                              value,
                              style: TextStyle(
                                color: Color(0xFF000000),
                                fontFamily: 'century_gothic',
                                fontSize: 10,
                                fontWeight: FontWeight.w400,
                              ),
                            ),
                          );
                        }).toList(),
                        onChanged: (String value) {
                          setState(() {
                            _chosenValue2 = value;
                            documentBloc.incomeProof = _chosenValue2;
                            print(documentBloc.bankProof);
                          });
                        },
                      ),
                    ),
                  ),
                  Container(
                    child: Row(
                      crossAxisAlignment: CrossAxisAlignment.center,
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: [
                        Expanded(
                          child: Container(
                            margin: EdgeInsets.all(15),
                            child: Row(
                              crossAxisAlignment: CrossAxisAlignment.center,
                              mainAxisAlignment: MainAxisAlignment.spaceBetween,
                              children: [
                                Expanded(
                                  child: Container(
                                    // padding:EdgeInsets.all(1),
                                    margin: EdgeInsets.only(right: 10, left: 10),
                                    decoration: new BoxDecoration(
                                      color: Color(0xFFFFFFFF),
                                      shape: BoxShape.rectangle,
                                      borderRadius: BorderRadius.circular(5),
                                      boxShadow: [
                                        BoxShadow(
                                          color: Color(0xFF9B9B9B),
                                          blurRadius: 5.0,
                                          offset: const Offset(5.0, 5.0),
                                        ),
                                      ],
                                    ),
                                    child: TextButton(
                                        child: Text(
                                          'UPLOAD',
                                          style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 11, fontWeight: FontWeight.w500),
                                        ),
                                        onPressed: () {
                                          print('Pressed');
                                          if (documentBloc.incomeProof.isNotEmpty) {
                                            String proofType = "";
                                            switch (documentBloc.incomeProof) {
                                              case "ITR Current\nFY-AY":
                                                proofType = "0";
                                                break;
                                              case "PASSBOOK COPY\n6 MONTHS":
                                                proofType = "1";
                                                break;
                                              case "BANK STATEMENT\n6 MONTHS":
                                                proofType = "2";
                                                break;
                                              case "FORM 16":
                                                proofType = "3";
                                                break;
                                              case "SALARY SLIP":
                                                proofType = "4";
                                                break;
                                              default:
                                            }
                                            customOnPressed(105, proofType);
                                          } else {
                                            documentBloc.showAlert(context, "Select Income Proof");
                                          }
                                        }),
                                  ),
                                ),
                                Expanded(
                                  child: Container(
                                    // width: 100,
                                    margin: EdgeInsets.only(right: 10, left: 10),
                                    // padding:EdgeInsets.all(10),
                                    decoration: new BoxDecoration(
                                      color: Color(0xFF9B9B9B),
                                      shape: BoxShape.rectangle,
                                      borderRadius: BorderRadius.circular(5),
                                      boxShadow: [
                                        BoxShadow(
                                          color: Color(0xFF9B9B9B),
                                          blurRadius: 5.0,
                                          offset: const Offset(5.0, 5.0),
                                        ),
                                      ],
                                    ),
                                    child: TextButton(
                                      child: Text(
                                        'VIEW',
                                        style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 11, fontWeight: FontWeight.w500),
                                      ),
                                      onPressed: () {
                                        print('Pressed');
                                        if (documentBloc.docIncomeProof != null) {
                                          if (documentBloc.docIncomeProof) {
                                            openUploadedImage(105, context);
                                          }
                                        }
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
                  Container(
                    alignment: Alignment.center,
                    margin: EdgeInsets.only(top: 10),
                    child: Column(
                      children: [
                        Text(
                          "Maximum upload file size: 2 MB",
                          style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 12, fontWeight: FontWeight.w300),
                        ),
                        Text(
                          "Only PDF supported",
                          style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 12, fontWeight: FontWeight.w300),
                        ),
                      ],
                    ),
                  ),
                ],
              ),
            ),
          ),
          Expanded(
            child: Container(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.center,
                children: [
                  Container(
                    alignment: Alignment.center,
                    margin: EdgeInsets.only(top: 10),
                    child: Text(
                      "PHOTOGRAPH*",
                      style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 12, fontWeight: FontWeight.w600),
                    ),
                  ),
                  Container(
                    alignment: Alignment.center,
                    margin: EdgeInsets.only(top: 10),
                    child: Text(
                      "REQUIRES YOUR CAMERA PERMISSION",
                      style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 12, fontWeight: FontWeight.w300),
                    ),
                  ),
                  Container(
                    padding: EdgeInsets.only(top: 10),
                    width: 100,
                    height: 60,
                    child: Image.asset(
                      'asset/images/uploadblack.png',
                    ),
                  ),
                  Container(
                    child: Row(
                      crossAxisAlignment: CrossAxisAlignment.center,
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: [
                        Expanded(
                          child: Container(
                            margin: EdgeInsets.all(15),
                            child: Row(
                              crossAxisAlignment: CrossAxisAlignment.center,
                              mainAxisAlignment: MainAxisAlignment.spaceBetween,
                              children: [
                                Expanded(
                                  child: Container(
                                    // padding:EdgeInsets.all(1),
                                    margin: EdgeInsets.only(right: 10, left: 10),
                                    decoration: new BoxDecoration(
                                      color: Color(0xFF0066CC),
                                      shape: BoxShape.rectangle,
                                      borderRadius: BorderRadius.circular(5),
                                      boxShadow: [
                                        BoxShadow(
                                          color: Color(0xFF9B9B9B),
                                          blurRadius: 5.0,
                                          offset: const Offset(5.0, 5.0),
                                        ),
                                      ],
                                    ),
                                    child: TextButton(
                                        child: Text(
                                          'TAKEN',
                                          style: TextStyle(color: Color(0xFFFFFFFF), fontFamily: 'century_gothic', fontSize: 11, fontWeight: FontWeight.w500),
                                        ),
                                        onPressed: () {
                                          print('Pressed');
                                          customOnPressed(106, "");
                                        }),
                                  ),
                                ),
                                Expanded(
                                  child: Container(
                                    // width: 100,
                                    margin: EdgeInsets.only(right: 10, left: 10),
                                    // padding:EdgeInsets.all(10),
                                    decoration: new BoxDecoration(
                                      color: Color(0xFF9B9B9B),
                                      shape: BoxShape.rectangle,
                                      borderRadius: BorderRadius.circular(5),
                                      boxShadow: [
                                        BoxShadow(
                                          color: Color(0xFF9B9B9B),
                                          blurRadius: 5.0,
                                          offset: const Offset(5.0, 5.0),
                                        ),
                                      ],
                                    ),
                                    child: TextButton(
                                      child: Text(
                                        'VIEW',
                                        style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 11, fontWeight: FontWeight.w500),
                                      ),
                                      onPressed: () {
                                        print('Pressed');
                                        if (documentBloc.docPhotograph != null) {
                                          if (documentBloc.docPhotograph) {
                                            openUploadedImage(106, context);
                                          }
                                        }
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
                  Container(
                    alignment: Alignment.center,
                    margin: EdgeInsets.only(top: 10),
                    child: Column(
                      children: [
                        Text(
                          "Maximum upload file size: 2 MB",
                          style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 12, fontWeight: FontWeight.w300),
                        ),
                        Text(
                          "Only PNG/JPEG/JPG supported",
                          style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 12, fontWeight: FontWeight.w300),
                        ),
                      ],
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

  deviderline(BuildContext context) {
    return Container(
      margin: EdgeInsets.only(top: 30, left: 40, right: 40),
      height: 2,
      color: Color(0XFFEBEBEB),
    );
  }

  btnprocess(BuildContext context) {
    return Container(
      child: Row(
        mainAxisAlignment: MainAxisAlignment.end,
        crossAxisAlignment: CrossAxisAlignment.end,
        children: [
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
                onPressed: () {
                  if (documentBloc.docPan && documentBloc.docSignature && documentBloc.docBankProof && documentBloc.docAddressProof && documentBloc.docIncomeProof && documentBloc.docPhotograph) {
                    HomeScreenLarge.screensStreamLarge.sink.add(Ekycscreenamelarge.ipvdetailscreen);
                  } else {
                    var errorMessage = 'Please upload all documents';
                    if (documentBloc.docPan == false) {
                      errorMessage = 'Please upload Pan card.';
                    } else if (documentBloc.docSignature == false) {
                      errorMessage = 'Please upload Signature.';
                    } else if (documentBloc.docBankProof == false) {
                      errorMessage = 'Please upload Bank Proof.';
                    } else if (documentBloc.docAddressProof == false) {
                      errorMessage = 'Please upload Address Proof.';
                    } else if (documentBloc.docIncomeProof == false) {
                      errorMessage = 'Please upload Income Proof.';
                    } else if (documentBloc.docPhotograph == false) {
                      errorMessage = 'Please upload Photograph.';
                    }
                    documentBloc.showAlert(context, errorMessage);
                  }
                  // showDialog(
                  //     context: context,
                  //     builder: (BuildContext context) {
                  //       return Center(child: CustomDialogmethod());
                  //     });
                },
              ),
            ),
          ),
        ],
      ),
    );
  }

  Future<void> picimage() async {
    //    final ImagePicker _picker = ImagePicker();
    // final pickedFile = await _picker.getImage(source: ImageSource.gallery);
    // File result = (await FilePicker.platform.pickFiles()) as webFile.File;
  }

  // ignore: unused_element
  _saveImage(String docType) async {
    // FilePickerResult results = await FilePicker.platform.pickFiles();
    // File result = File(results.files, "pancard");
    PlatformFile objFile;
    var result = await FilePicker.platform.pickFiles(
      withReadStream: true, // this will return PlatformFile object with read stream
    );
    if (result != null) {
      objFile = result.files.single;
      var url = AppConfig().url + "uploadDocuments";

      Uri uri = Uri.parse(url);
      var request = http.MultipartRequest('POST', uri);
      // request.headers["<custom header>"] = "content";
      request.fields['email_id'] = LoginRepository.loginEmailId;
      request.fields['mobile_no'] = LoginRepository.loginMobileNo;
      request.fields['type'] = "pancard";
      request.fields['imageName'] = "pancardimage";
      // request.files.add(http.MultipartFile.fromBytes(
      //     'image_path', (await _getHtmlFileContent(result)),
      //     filename: result.name));
      request.files.add(new http.MultipartFile("image_path", objFile.readStream, objFile.size, filename: objFile.name));
      request.send().then((response) {
        http.Response.fromStream(response).then((onValue) {
          try {
            // get your response here...
            // print(response.toString());
            print('$docType succssfully uploaded...');
          } catch (e) {
            // handle exeption
            print(e.toString());
          }
        });
      });
    }
  }

  // ignore: unused_element
  Future<void> _pickImage(int id, BuildContext context) async {
    print("pick image method call");
    try {
      // final ImagePicker _picker = ImagePicker();
      // var pickedFileList = await _picker.pickMultiImage(
      //   maxWidth: 100,
      //   maxHeight: 100,
      //   imageQuality: 100,
      // );
      PlatformFile objFile;
      var result = await FilePicker.platform.pickFiles(
        withReadStream: true, // this will return PlatformFile object with read stream
      );
      if (result != null) {
        objFile = result.files.first;

        setState(() async {
          // print("images picked is " + pickedFileList.toString());
          switch (id) {
            case 101:
              documentBloc.panCardImageicon = objFile;

              // documentBloc.uploadDocumentsnew(
              //     context, "large", "pancard", "bwxpp5879a", pickedFileList);

              break;
            // case 102:
            //   documentBloc.signatureImage = pickedFileList;
            //   break;
            // case 103:
            //   documentBloc.bankProofImage = pickedFileList;
            //   break;
            // case 104:
            //   documentBloc.addressProffImage = pickedFileList;
            //   break;
            // case 105:
            //   documentBloc.incomProofImage = pickedFileList;
            //   break;
            // case 106:
            //   documentBloc.photographImage = pickedFileList;
            //   break;
            default:
          }
        });
      }
    } catch (e) {
      setState(() {
        switch (id) {
          // case 101:
          //   documentBloc.panCardImage = e;
          //   break;
          case 102:
            documentBloc.signatureImage = e;
            break;
          case 103:
            documentBloc.bankProofImage = e;
            break;
          case 104:
            documentBloc.addressProffImage = e;
            break;
          case 105:
            documentBloc.incomProofImage = e;
            break;
          case 106:
            documentBloc.photographImage = e;
            break;
          default:
        }
      });
    }

    // Image fromPicker =
    //     await ImagePickerWeb.getImage(outputType: ImageType.widget);

    // if (fromPicker != null) {
    //   setState(() {
    //     // _pickedImages.clear();
    //     documentBloc.panCardImage = fromPicker;
    //     print("From picker image " + fromPicker.toString());
    //   });
    // }
  }

  // Future<void> _pickVideo() async {
  //   final videoMetaData =
  //       await ImagePickerWeb.getVideo(outputType: VideoType.bytes);
  //   if (videoMetaData != null) {
  //     setState(() {
  //       _pickedVideos.clear();
  //       _pickedVideos.add(videoMetaData);
  //       print("From picker video " + videoMetaData.toString());
  //     });
  //   }
  // }

  /// unused code for image upload for reference
  ///   // Future<Uint8List> _getHtmlFileContent(File blob) async {
  //   Uint8List file;
  //   final reader = FileReader();
  //   reader.readAsDataUrl(blob.slice(0, blob.size, blob.type));
  //   reader.onLoadEnd.listen((event) {
  //     Uint8List data =
  //         Base64Decoder().convert(reader.result.toString().split(",").last);
  //     file = data;
  //   }).onData((data) {
  //     file = Base64Decoder().convert(reader.result.toString().split(",").last);
  //     return file;
  //   });
  //   while (file == null) {
  //     await new Future.delayed(const Duration(milliseconds: 1));
  //     if (file != null) {
  //       break;
  //     }
  //   }
  //   return file;
  // }

  // Future<void> uploadImagenew() async {
  //   // Uint8List fileBytes;
  //   //   FilePickerResult result = await FilePicker.platform.pickFiles();

  //   //   if (result != null) {
  //   //     //  html.File file = html.File(result.files.single.path);
  //   //     PlatformFile file = result.files.first;

  //   //     print(file.name);

  //   //     fileBytes = result.files.first.bytes;
  //   //     String fileName = result.files.first.name;

  //   //     print(fileName);
  //   //     // print(fileBytes);
  //   //   } else {
  //   //     // User canceled the picker
  //   //   }
  //   final ImagePicker _picker = ImagePicker();
  //   final pickedFile = await _picker.getImage(source: ImageSource.gallery);
  //   String url = AppConfig().url + "uploadDocuments";
  //   PickedFile imageFile = PickedFile(pickedFile.path);
  //   var stream =
  //       new http.ByteStream(DelegatingStream.typed(pickedFile.openRead()));

  //   var uri = Uri.parse(url);
  //   // int length = imageFile.length;
  //   var request = new http.MultipartRequest("POST", uri);
  //   var multipartFile = new http.MultipartFile('image_path', stream, 10000,
  //       filename: basename(imageFile.path),
  //       contentType: MediaType('image', 'png'));

  //   request.files.add(multipartFile);
  //   var response = await request.send();
  //   print(response.statusCode);
  //   response.stream.transform(utf8.decoder).listen((value) {
  //     print(value);
  //   });
  // }

//   Future<void> uploadimage() async {
//     try {
//       Uint8List fileBytes;
//       FilePickerResult result = await FilePicker.platform.pickFiles();

//       if (result != null) {
//         //  html.File file = html.File(result.files.single.path);
//         PlatformFile file = result.files.first;

//         print(file.name);

//         fileBytes = result.files.first.bytes;
//         String fileName = result.files.first.name;

//         print(fileName);
//         // print(fileBytes);
//       } else {
//         // User canceled the picker
//       }
//         final ImagePicker _picker = ImagePicker();
//       var pickedFileList = await _picker.pickMultiImage(
//         maxWidth: 100,
//         maxHeight: 100,
//         imageQuality: 100,
//       );
//       File _image;
//       final picker = ImagePicker();
//       FilePickerResult result = await FilePicker.platform.pickFiles();

// if (result != null) {
//   File file = File(result.files,"pancard");
// } else {
//   // User canceled the picker
// }
  // final pickedFile = await picker.getImage(source: ImageSource.gallery);
  //       _image = File(pickedFile.path,"pancard");
  //        var stream =
  //     new http.ByteStream(DelegatingStream.typed(pickedFile.openRead()));
  // // get file length
  // var length = await pickedFile.length();
  //     ResponseModel obj = await DocumentRepository().uploadFileToServer(
  //         LoginRepository.login_email_id,
  //         LoginRepository.login_mobile_no,
  //         "pancard",
  //         "bwxpp5879a",
  //         pickedFile.path);

  //     if (obj.response.errorCode == "0") {
  //       LoginUserDetailModelResponse obj = await LoginRepository()
  //           .getEkycUserDetails(LoginRepository.login_email_id,
  //               LoginRepository.login_mobile_no);
  //       LoginRepository.loginDetailsResObjGlobal = obj;
  //       if ("large".contains("small")) {
  //         HomeScreenSmall.screensStreamSmall.sink
  //             .add(Ekycscreenamesmall.paymentdetailscreen);
  //       } else {
  //         HomeScreenLarge.screensStreamLarge.sink
  //             .add(Ekycscreenamelarge.paymentdetailscreen);
  //       }
  //     } else {}
  //   } catch (err) {
  //     print("Err uload image" + err.toString());
  //   }
  // }
}
