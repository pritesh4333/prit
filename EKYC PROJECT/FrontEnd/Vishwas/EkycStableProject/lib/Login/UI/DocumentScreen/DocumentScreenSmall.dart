// import 'dart:ffi';

import 'dart:math';

import 'package:e_kyc/Login/UI/Configuration/AppConfig.dart';
import 'package:e_kyc/Login/UI/DocumentScreen/Bloc/DocumentBloc.dart';
import 'package:e_kyc/Login/UI/EsignScreen/repository/EsignRepository.dart';
import 'package:e_kyc/Login/UI/HomeScreen/HomeScreenSmall.dart';
import 'package:e_kyc/Login/UI/Login/repository/LoginRepository.dart';
import 'package:e_kyc/Login/UI/PanDetailsScreen/repository/PanDetailRepository.dart';
import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';
import 'package:flutter_vant_kit/widgets/imagePreview.dart';
import 'package:image_picker/image_picker.dart';
import 'package:e_kyc/Login/UI/IPVScreen/platformspec/mobiledevice.dart' if (dart.library.html) "package:e_kyc/Login/UI/IPVScreen/platformspec/webdevice.dart" as newWindow;
import 'package:image/image.dart' as img;
import 'package:syncfusion_flutter_pdf/pdf.dart';

class DocumentScreenSmall extends StatefulWidget {
  @override
  _DocumentScreenSmallState createState() => _DocumentScreenSmallState();
}

class _DocumentScreenSmallState extends State<DocumentScreenSmall> {
  DocumentBloc _documentBloc = new DocumentBloc();
  var panDetails;
  var globalRespObj = LoginRepository.loginDetailsResObjGlobal;
  var _chars = 'AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz1234567890';
  Random _rnd = Random();
  List<XFile> _imageFileList;
  set _imageFile(XFile value) {
    _imageFileList = value == null ? null : [value];
  }

  var galleryImage;

  var validfullname = "", validemail = "", validmobile = "", validclientcode = "";

  String _chosenValue, _chosenValue1, _chosenValue2;
  @override
  void dispose() {
    super.dispose();
  }

  @override
  void initState() {
    HomeScreenSmall.percentageFlagSmall.add("0.805");
    super.initState();

    //Setting predefine data from DB
    if (globalRespObj != null) {
      print('Data already is in global object');
      if (globalRespObj.response.errorCode == "0") {
        var stage = int.parse(globalRespObj.response.data.message[0].stage);
        panDetails = globalRespObj.response.data.message[0].pan;
        print('Stage = $stage');
        var docObj = globalRespObj.response.data.message[0];
        _documentBloc.docPan = docObj.pancard.contains('pancard') ? true : false;
        _documentBloc.docSignature = docObj.signature.contains('signature') ? true : false;
        _documentBloc.docBankProof = docObj.bankproof.contains('bankproof') ? true : false;
        _documentBloc.docAddressProof = docObj.addressproof.contains('addressproof') ? true : false;
        _documentBloc.docIncomeProof = docObj.incomeproof.contains('incomeproof') ? true : false;
        _documentBloc.docPhotograph = docObj.photograph.contains('photograph') ? true : false;

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

        //BankProof, address proof,
        //proof dropdown
        _chosenValue = _documentBloc.bankProofList[bankProof];
        _documentBloc.bankProof = _chosenValue;
        _chosenValue1 = _documentBloc.addressProofList[addressProof];
        _documentBloc.addressProof = _chosenValue1;
        _chosenValue2 = _documentBloc.incomeProofList[incomeProof];
        _documentBloc.incomeProof = _chosenValue2;
      }
    } else {
      panDetails = PanDetailRepository.userPanNumber;
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
          header(),
          documentDetailsForm(),
          deviderline(),
          documentDetailsFormtwo(),
          deviderline(),
          documentDetailsFormthree(),
          btnprocess(context, _documentBloc),
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
                      fontWeight: FontWeight.w300,
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

  documentDetailsForm() {
    return Container(
      padding: EdgeInsets.only(left: 25, top: 25, right: 25),
      child: Row(
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
                      "PAN CARD*",
                      style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 12, fontWeight: FontWeight.bold),
                    ),
                  ),
                  Container(
                    alignment: Alignment.center,
                    margin: EdgeInsets.only(top: 10),
                    child: Text(
                      "Upload front side of",
                      style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 12, fontWeight: FontWeight.w300),
                    ),
                  ),
                  Container(
                    alignment: Alignment.center,
                    margin: EdgeInsets.only(top: 5),
                    child: Text(
                      "your pan card",
                      style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 12, fontWeight: FontWeight.w300),
                    ),
                  ),
                  Container(
                    padding: EdgeInsets.only(top: 20),
                    width: 100,
                    height: 60,
                    child: Image.asset(
                      'asset/images/uploadblack.png',
                    ),
                  ),
                  Container(
                    margin: EdgeInsets.only(top: 10),
                    child: Row(
                      crossAxisAlignment: CrossAxisAlignment.center,
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: [
                        Expanded(
                          child: Container(
                            margin: EdgeInsets.all(15),
                            child: Row(
                              crossAxisAlignment: CrossAxisAlignment.center,
                              //mainAxisAlignment: MainAxisAlignment.spaceBetween,
                              children: [
                                Expanded(
                                  child: Container(
                                    width: 600,
                                    height: 30,
                                    alignment: Alignment.center,
                                    //padding: EdgeInsets.only(left: 5, right: 5),
                                    decoration: new BoxDecoration(
                                      color: Color(0xFFFFFFFF),
                                      shape: BoxShape.rectangle,
                                      borderRadius: BorderRadius.circular(5),
                                      boxShadow: [
                                        BoxShadow(
                                          color: Color(0xFF9B9B9B),
                                          blurRadius: 5.0,
                                          offset: const Offset(1.0, 1.0),
                                        ),
                                      ],
                                    ),
                                    child: TextButton(
                                      child: Text(
                                        'UPLOAD',
                                        style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 8, fontWeight: FontWeight.bold),
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
                                    width: 200,
                                    height: 30,
                                    //padding: EdgeInsets.only(left: 5, right: 5),
                                    margin: EdgeInsets.only(left: 10),
                                    // padding:EdgeInsets.all(10),
                                    decoration: new BoxDecoration(
                                      color: Color(0xFF9B9B9B),
                                      shape: BoxShape.rectangle,
                                      borderRadius: BorderRadius.circular(5),
                                      boxShadow: [
                                        BoxShadow(
                                          color: Color(0xFF9B9B9B),
                                          blurRadius: 5.0,
                                          offset: const Offset(1.0, 1.0),
                                        ),
                                      ],
                                    ),
                                    child: TextButton(
                                      child: Text(
                                        'VIEW',
                                        style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 8, fontWeight: FontWeight.bold),
                                      ),
                                      onPressed: () {
                                        print('Pressed');
                                        if (_documentBloc.docPan != null) {
                                          if (_documentBloc.docPan) {
                                            openUploadedImage(101, context);
                                          }
                                        }

                                        // if (validateDocumentUploaded(
                                        //     _documentBloc.panCardImageicon)) {
                                        //   Navigator.push(
                                        //     context,
                                        //     MaterialPageRoute(
                                        //       builder: (context) =>
                                        //           PreviewImage(
                                        //         preViewImage: _documentBloc
                                        //             .panCardImageicon,
                                        //         titleImage: 'PAN CARD',
                                        //       ),
                                        //     ),
                                        //   );
                                        // }
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
                          style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 10, fontWeight: FontWeight.w300),
                        ),
                        Text(
                          "Only PNG/JPEG/JPG supported",
                          style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 10, fontWeight: FontWeight.w300),
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
                    margin: EdgeInsets.only(top: 20),
                    child: Text(
                      "SIGNATURE",
                      style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 12, fontWeight: FontWeight.w600),
                    ),
                  ),
                  Container(
                    alignment: Alignment.center,
                    margin: EdgeInsets.only(top: 10),
                    child: Text(
                      "Sign in white paper",
                      style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 12, fontWeight: FontWeight.w300),
                    ),
                  ),
                  Container(
                    alignment: Alignment.center,
                    margin: EdgeInsets.only(top: 5),
                    child: Text(
                      "and upload it",
                      style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 12, fontWeight: FontWeight.w300),
                    ),
                  ),
                  Container(
                    padding: EdgeInsets.only(top: 20),
                    width: 100,
                    height: 60,
                    child: Image.asset(
                      'asset/images/uploadblack.png',
                    ),
                  ),
                  Container(
                    margin: EdgeInsets.only(top: 10),
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
                                    width: 600,
                                    height: 30,
                                    alignment: Alignment.center,
                                    decoration: new BoxDecoration(
                                      color: Color(0xFFFFFFFF),
                                      shape: BoxShape.rectangle,
                                      borderRadius: BorderRadius.circular(5),
                                      boxShadow: [
                                        BoxShadow(
                                          color: Color(0xFF9B9B9B),
                                          blurRadius: 5.0,
                                          offset: const Offset(1.0, 1.0),
                                        ),
                                      ],
                                    ),
                                    child: TextButton(
                                        child: Text(
                                          'UPLOAD',
                                          style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 8, fontWeight: FontWeight.bold),
                                        ),
                                        onPressed: () async {
                                          print('Pressed');
                                          customOnPressed(102, "");
                                        }),
                                  ),
                                ),
                                Expanded(
                                  child: Container(
                                    width: 200,
                                    height: 30,
                                    //padding: EdgeInsets.only(left: 5, right: 5),
                                    margin: EdgeInsets.only(left: 10),
                                    // padding:EdgeInsets.all(10),
                                    decoration: new BoxDecoration(
                                      color: Color(0xFF9B9B9B),
                                      shape: BoxShape.rectangle,
                                      borderRadius: BorderRadius.circular(5),
                                      boxShadow: [
                                        BoxShadow(
                                          color: Color(0xFF9B9B9B),
                                          blurRadius: 5.0,
                                          offset: const Offset(1.0, 1.0),
                                        ),
                                      ],
                                    ),
                                    child: TextButton(
                                      child: Text(
                                        'VIEW',
                                        style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 8, fontWeight: FontWeight.bold),
                                      ),
                                      onPressed: () {
                                        print('Pressed');
                                        if (_documentBloc.docSignature != null) {
                                          if (_documentBloc.docSignature) {
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
                          style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 10, fontWeight: FontWeight.w300),
                        ),
                        Text(
                          "Only PNG/JPEG/JPG supported",
                          style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 10, fontWeight: FontWeight.w300),
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

  openUploadedImage(int id, BuildContext context) {
    switch (id) {
      case 101:
        print('PAN Card :$id');
        var fileName = LoginRepository.loginDetailsResObjGlobal.response.data.message[0].pancard;
        if (fileName.contains('.pdf') || fileName.contains('.PDF')) {
          previewPDFImage(fileName);
        } else {
          _documentBloc.downloadImageToPreview(context, "pancard");
        }
        break;
      case 102:
        print(' Signature :$id');
        var fileName = LoginRepository.loginDetailsResObjGlobal.response.data.message[0].signature;
        if (fileName.contains('.pdf') || fileName.contains('.PDF')) {
          previewPDFImage(fileName);
        } else {
          _documentBloc.downloadImageToPreview(context, "signature");
        }
        break;
      case 103:
        print('Bank Proof :$id');
        var fileName = LoginRepository.loginDetailsResObjGlobal.response.data.message[0].bankproof;
        if (fileName.contains('.pdf') || fileName.contains('.PDF')) {
          previewPDFImage("bankproof");
        } else {
          _documentBloc.downloadImageToPreview(context, "bankproof");
        }
        break;
      case 104:
        print('Address Proof :$id');
        var fileName = LoginRepository.loginDetailsResObjGlobal.response.data.message[0].addressproof;
        if (fileName.contains('.pdf') || fileName.contains('.PDF')) {
          previewPDFImage("addressproof");
        } else {
          _documentBloc.downloadImageToPreview(context, "addressproof");
        }
        break;
      case 105:
        print('Income Proof :$id');
        var fileName = LoginRepository.loginDetailsResObjGlobal.response.data.message[0].incomeproof;
        if (fileName.contains('.pdf') || fileName.contains('.PDF')) {
          previewPDFImage("incomeproof");
        } else {
          _documentBloc.downloadImageToPreview(context, "incomeproof");
        }
        break;
      case 106:
        print('Photograph :$id');
        var fileName = LoginRepository.loginDetailsResObjGlobal.response.data.message[0].photograph;
        if (fileName.contains('.pdf') || fileName.contains('.PDF')) {
          previewPDFImage(fileName);
        } else {
          _documentBloc.downloadImageToPreview(context, "photograph");
        }
        break;
      default:
    }
  }

  String getRandomString(int length) => String.fromCharCodes(Iterable.generate(length, (_) => _chars.codeUnitAt(_rnd.nextInt(_chars.length))));

  previewImage(BuildContext context, String imageType) {
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
    var url = AppConfig().url;
    String finalUrl = url + 'viewDocuments?email_id=' + LoginRepository.loginEmailId + '&mobile_no=' + LoginRepository.loginMobileNo + '&type=' + imageType + '&pan=' + panDetails + '&rendom=' + getRandomString(5);
    print("latest change  " + finalUrl);
    List<Widget> imagesDemo = [Image.network(finalUrl)];

    showDialog(
      context: context,
      builder: (BuildContext context) {
        return ImagePreview(showIndex: true, closeable: false, images: imagesDemo);
      },
    );
  }

//Useful for Web
  // Future<void> _pickImage() async {
  //   print("pick image method call");
  //   try {
  //     final ImagePicker _picker = ImagePicker();
  //     final pickedFileList = await _picker.pickMultiImage(
  //       maxWidth: 100,
  //       maxHeight: 100,
  //       imageQuality: 100,
  //     );
  //     setState(() {
  //       print("images picked is " + pickedFileList.toString());
  //       _documentBloc.panCardImage = pickedFileList;
  //     });
  //   } catch (e) {
  //     setState(() {
  //       _documentBloc.panCardImage = e;
  //     });
  //   }
  // }

  //For mobile
  Future _pickImageMobile(int id, ImageSource source) async {
    try {
      final ImagePicker _picker = ImagePicker();
      final pickedFile = await _picker.pickImage(source: source, imageQuality: 50, maxWidth: 100, maxHeight: 100);
      setState(() {
        _imageFile = pickedFile;

        switch (id) {
          case 101:
            // _documentBloc.panCardImage = _imageFileList;
            break;
          case 102:
            _documentBloc.signatureImage = _imageFileList;
            break;
          case 103:
            _documentBloc.bankProofImage = _imageFileList;
            break;
          case 104:
            _documentBloc.addressProffImage = _imageFileList;
            break;
          case 105:
            _documentBloc.incomProofImage = _imageFileList;
            break;
          case 106:
            _documentBloc.photographImage = _imageFileList;
            break;
          default:
        }
      });
    } catch (e) {
      setState(() {
        print('Error in image picking : $e');
      });
    }
  }

  void customOnPressed(int id, String proofType) {
    var docObj = globalRespObj.response.data.message[0];
    AppConfig().showLoaderDialog(context);
    switch (id) {
      case 101:
        print('PAN Card :$id');

        selectImageFile('pancard', proofType, docObj.uniqueId, ['png', 'jpeg', 'PNG', 'JPEG', 'jpg', 'JPG']);
        // openChoiceBottomsheetImagePicker(id, 'pancard', proofType);
        break;
      case 102:
        print(' Signature :$id');
        selectImageFile('signature', proofType, docObj.uniqueId, ['png', 'jpeg', 'PNG', 'JPEG', 'jpg', 'JPG']);
        // openChoiceBottomsheetImagePicker(id, 'signature', proofType);
        break;
      case 103:
        print('Bank Proof :$id');
        selectImageFile('bankproof', proofType, docObj.uniqueId, ['pdf', 'PDF']);
        // openChoiceBottomsheetImagePicker(id, 'bankproof', proofType);
        break;
      case 104:
        print('Address Proof :$id');
        selectImageFile('addressproof', proofType, docObj.uniqueId, ['png', 'jpeg', 'PNG', 'JPEG', 'jpg', 'JPG']);
        // openChoiceBottomsheetImagePicker(id, 'addressproof', proofType);
        break;
      case 105:
        print('Income Proof :$id');
        selectImageFile('incomeproof', proofType, docObj.uniqueId, ['pdf', 'PDF']);
        // openChoiceBottomsheetImagePicker(id, 'incomeproof', proofType);
        break;
      case 106:
        print('Photograph :$id');
        selectImageFile('photograph', proofType, docObj.uniqueId, ['png', 'jpeg', 'PNG', 'JPEG', 'jpg', 'JPG']);
        // openChoiceBottomsheetImagePicker(id, 'photograph', proofType);
        break;

      default:
    }
  }

  Future<dynamic> openChoiceBottomsheetImagePicker(int id, String docType, String proofType) {
    return showModalBottomSheet(
      context: context,
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
                    await _pickImageMobile(id, ImageSource.camera);
                  },
                ),
                ListTile(
                  leading: Icon(Icons.collections),
                  title: Text('Gallery'),
                  onTap: () async {
                    Navigator.pop(bc);
                    // await _pickImageMobile(id, ImageSource.gallery);
                    // _saveImage(docType);

                    // selectImageFile(docType, proofType,
                    //     globalRespObj.response.data.message[0].uniqueId);
                  },
                )
              ],
            ),
          ),
        );
      },
    );
  }

  documentDetailsFormtwo() {
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
                      "BANK PROOF",
                      style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 12, fontWeight: FontWeight.w600),
                    ),
                  ),
                  Container(
                    height: 30,
                    padding: EdgeInsets.only(left: 5),
                    margin: EdgeInsets.only(top: 30, bottom: 25),
                    color: Color(0XFFEBEBEB),
                    child: DropdownButtonHideUnderline(
                      child: DropdownButton<String>(
                        value: _chosenValue,
                        hint: Text(
                          "Select Bank Proof",
                          style: TextStyle(
                            fontSize: 10,
                          ),
                        ),
                        items: _documentBloc.bankProofList.map((String value) {
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
                            _chosenValue = value;
                            _documentBloc.bankProof = _chosenValue;
                            print(_documentBloc.bankProof);
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
                                    width: 600,
                                    height: 30,
                                    alignment: Alignment.center,
                                    decoration: new BoxDecoration(
                                      color: Color(0xFFFFFFFF),
                                      shape: BoxShape.rectangle,
                                      borderRadius: BorderRadius.circular(5),
                                      boxShadow: [
                                        BoxShadow(
                                          color: Color(0xFF9B9B9B),
                                          blurRadius: 5.0,
                                          offset: const Offset(1.0, 1.0),
                                        ),
                                      ],
                                    ),
                                    child: TextButton(
                                        child: Text(
                                          'UPLOAD',
                                          style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 8, fontWeight: FontWeight.bold),
                                        ),
                                        onPressed: () async {
                                          print('Pressed Bank Proof');
                                          if (_documentBloc.bankProof.isNotEmpty) {
                                            String proofType = "";
                                            switch (_documentBloc.bankProof) {
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
                                            _documentBloc.showAlert(context, "Select Bank Proof");
                                          }
                                        }),
                                  ),
                                ),
                                Expanded(
                                  child: Container(
                                    // width: 100,
                                    width: 200,
                                    height: 30,
                                    //padding: EdgeInsets.only(left: 5, right: 5),
                                    margin: EdgeInsets.only(left: 10),
                                    // padding:EdgeInsets.all(10),
                                    decoration: new BoxDecoration(
                                      color: Color(0xFF9B9B9B),
                                      shape: BoxShape.rectangle,
                                      borderRadius: BorderRadius.circular(5),
                                      boxShadow: [
                                        BoxShadow(
                                          color: Color(0xFF9B9B9B),
                                          blurRadius: 5.0,
                                          offset: const Offset(1.0, 1.0),
                                        ),
                                      ],
                                    ),
                                    child: TextButton(
                                      child: Text(
                                        'VIEW',
                                        style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 8, fontWeight: FontWeight.bold),
                                      ),
                                      onPressed: () {
                                        print('Pressed bank proof');
                                        if (_documentBloc.docBankProof != null) {
                                          if (_documentBloc.docBankProof) {
                                            openUploadedImage(103, context);
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
                          style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 10, fontWeight: FontWeight.w300),
                        ),
                        Text(
                          "Only PDF supported",
                          style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 10, fontWeight: FontWeight.w300),
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
                      "ADDRESS PROOF",
                      style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 12, fontWeight: FontWeight.w600),
                    ),
                  ),
                  Container(
                    height: 30,
                    padding: EdgeInsets.only(left: 5),
                    margin: EdgeInsets.only(left: 10, top: 30, bottom: 25),
                    color: Color(0XFFEBEBEB),
                    child: DropdownButtonHideUnderline(
                      child: DropdownButton<String>(
                        value: _chosenValue1,
                        hint: Text(
                          "Select Address Proof",
                          style: TextStyle(
                            fontSize: 10,
                          ),
                        ),
                        items: _documentBloc.addressProofList.map((String value) {
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
                            _chosenValue1 = value;
                            _documentBloc.addressProof = _chosenValue1;
                            print(_documentBloc.addressProof);
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
                                    width: 600,
                                    height: 30,
                                    alignment: Alignment.center,
                                    decoration: new BoxDecoration(
                                      color: Color(0xFFFFFFFF),
                                      shape: BoxShape.rectangle,
                                      borderRadius: BorderRadius.circular(5),
                                      boxShadow: [
                                        BoxShadow(
                                          color: Color(0xFF9B9B9B),
                                          blurRadius: 5.0,
                                          offset: const Offset(1.0, 1.0),
                                        ),
                                      ],
                                    ),
                                    child: TextButton(
                                        child: Text(
                                          'UPLOAD',
                                          style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 8, fontWeight: FontWeight.bold),
                                        ),
                                        onPressed: () async {
                                          print('Pressed');
                                          String proofType = "";
                                          if (_documentBloc.addressProof.isNotEmpty) {
                                            switch (_documentBloc.addressProof) {
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
                                            _documentBloc.showAlert(context, "Select bank Proof");
                                          }
                                        }),
                                  ),
                                ),
                                Expanded(
                                  child: Container(
                                    // width: 100,
                                    width: 200,
                                    height: 30,
                                    //padding: EdgeInsets.only(left: 5, right: 5),
                                    margin: EdgeInsets.only(left: 10),
                                    // padding:EdgeInsets.all(10),
                                    decoration: new BoxDecoration(
                                      color: Color(0xFF9B9B9B),
                                      shape: BoxShape.rectangle,
                                      borderRadius: BorderRadius.circular(5),
                                      boxShadow: [
                                        BoxShadow(
                                          color: Color(0xFF9B9B9B),
                                          blurRadius: 5.0,
                                          offset: const Offset(1.0, 1.0),
                                        ),
                                      ],
                                    ),
                                    child: TextButton(
                                      child: Text(
                                        'VIEW',
                                        style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 8, fontWeight: FontWeight.bold),
                                      ),
                                      onPressed: () {
                                        print('Pressed');
                                        if (_documentBloc.docAddressProof != null) {
                                          if (_documentBloc.docAddressProof) {
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
                          style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 10, fontWeight: FontWeight.w300),
                        ),
                        Text(
                          "Only PNG/JPEG/JPG supported",
                          style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 10, fontWeight: FontWeight.w300),
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

  documentDetailsFormthree() {
    return Container(
      padding: EdgeInsets.only(left: 25, top: 15, right: 25),
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
                      "INCOME PROOF",
                      style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 12, fontWeight: FontWeight.w600),
                    ),
                  ),
                  Container(
                    height: 30,
                    padding: EdgeInsets.only(left: 5),
                    margin: EdgeInsets.only(
                      top: 32,
                      bottom: 25,
                    ),
                    color: Color(0XFFEBEBEB),
                    child: DropdownButtonHideUnderline(
                      child: DropdownButton<String>(
                        value: _chosenValue2,
                        hint: Text(
                          "Select Income Proof",
                          style: TextStyle(
                            fontSize: 10,
                          ),
                        ),
                        items: _documentBloc.incomeProofList.map((String value) {
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
                            _documentBloc.incomeProof = _chosenValue2;
                            print(_documentBloc.bankProof);
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
                                    width: 600,
                                    height: 30,
                                    margin: EdgeInsets.only(top: 10),
                                    alignment: Alignment.center,
                                    decoration: new BoxDecoration(
                                      color: Color(0xFFFFFFFF),
                                      shape: BoxShape.rectangle,
                                      borderRadius: BorderRadius.circular(5),
                                      boxShadow: [
                                        BoxShadow(
                                          color: Color(0xFF9B9B9B),
                                          blurRadius: 5.0,
                                          offset: const Offset(1.0, 1.0),
                                        ),
                                      ],
                                    ),
                                    child: TextButton(
                                        child: Text(
                                          'UPLOAD',
                                          style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 8, fontWeight: FontWeight.bold),
                                        ),
                                        onPressed: () async {
                                          print('Pressed');
                                          if (_documentBloc.incomeProof.isNotEmpty) {
                                            String proofType = "";
                                            switch (_documentBloc.incomeProof) {
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
                                            _documentBloc.showAlert(context, "Select Income Proof");
                                          }
                                        }),
                                  ),
                                ),
                                Expanded(
                                  child: Container(
                                    // width: 100,
                                    width: 200,
                                    height: 30,
                                    //padding: EdgeInsets.only(left: 5, right: 5),
                                    margin: EdgeInsets.only(left: 10, top: 10),
                                    // padding:EdgeInsets.all(10),
                                    decoration: new BoxDecoration(
                                      color: Color(0xFF9B9B9B),
                                      shape: BoxShape.rectangle,
                                      borderRadius: BorderRadius.circular(5),
                                      boxShadow: [
                                        BoxShadow(
                                          color: Color(0xFF9B9B9B),
                                          blurRadius: 5.0,
                                          offset: const Offset(1.0, 1.0),
                                        ),
                                      ],
                                    ),
                                    child: TextButton(
                                      child: Text(
                                        'VIEW',
                                        style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 8, fontWeight: FontWeight.bold),
                                      ),
                                      onPressed: () {
                                        print('Pressed');
                                        if (_documentBloc.docIncomeProof != null) {
                                          if (_documentBloc.docIncomeProof) {
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
                          style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 10, fontWeight: FontWeight.w300),
                        ),
                        Text(
                          "Only PDF supported",
                          style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 10, fontWeight: FontWeight.w300),
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
                mainAxisAlignment: MainAxisAlignment.center,
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
                    padding: EdgeInsets.only(left: 5, right: 5),
                    margin: EdgeInsets.only(top: 10),
                    child: Text(
                      "REQUIRES YOUR",
                      style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 9, fontWeight: FontWeight.w300),
                    ),
                  ),
                  Container(
                    alignment: Alignment.center,
                    padding: EdgeInsets.only(left: 5, right: 5),
                    margin: EdgeInsets.only(top: 5),
                    child: Text(
                      "CAMERA PERMISSION",
                      style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 9, fontWeight: FontWeight.w300),
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
                                    width: 600,
                                    height: 30,
                                    alignment: Alignment.center,
                                    decoration: new BoxDecoration(
                                      color: Color(0xFFFFFFFF),
                                      shape: BoxShape.rectangle,
                                      borderRadius: BorderRadius.circular(5),
                                      boxShadow: [
                                        BoxShadow(
                                          color: Color(0xFF9B9B9B),
                                          blurRadius: 5.0,
                                          offset: const Offset(1.0, 1.0),
                                        ),
                                      ],
                                    ),
                                    child: TextButton(
                                        child: Text(
                                          'UPLOAD',
                                          style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 8, fontWeight: FontWeight.bold),
                                        ),
                                        onPressed: () async {
                                          print('Pressed');
                                          customOnPressed(106, "");
                                        }),
                                  ),
                                ),
                                Expanded(
                                  child: Container(
                                    // width: 100,
                                    width: 200,
                                    height: 30,
                                    //padding: EdgeInsets.only(left: 5, right: 5),
                                    margin: EdgeInsets.only(left: 10),
                                    // padding:EdgeInsets.all(10),
                                    decoration: new BoxDecoration(
                                      color: Color(0xFF9B9B9B),
                                      shape: BoxShape.rectangle,
                                      borderRadius: BorderRadius.circular(5),
                                      boxShadow: [
                                        BoxShadow(
                                          color: Color(0xFF9B9B9B),
                                          blurRadius: 5.0,
                                          offset: const Offset(1.0, 1.0),
                                        ),
                                      ],
                                    ),
                                    child: TextButton(
                                      child: Text(
                                        'VIEW',
                                        style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 8, fontWeight: FontWeight.bold),
                                      ),
                                      onPressed: () {
                                        print('Pressed');
                                        if (_documentBloc.docPhotograph != null) {
                                          if (_documentBloc.docPhotograph) {
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
                          style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 10, fontWeight: FontWeight.w300),
                        ),
                        Text(
                          "Only PNG/JPEG/JPG supported",
                          style: TextStyle(color: Color(0xFF000000), fontFamily: 'century_gothic', fontSize: 10, fontWeight: FontWeight.w300),
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

  deviderline() {
    return Container(
      margin: EdgeInsets.only(
        top: 30,
      ),
      height: 2,
      color: Color(0XFFEBEBEB),
    );
  }

  Future<void> selectImageFile(String docType, String proofType, String uniqueId, List<String> extensionAllowed) async {
    PlatformFile objFile;
    var result = await FilePicker.platform.pickFiles(
      type: FileType.custom,
      allowedExtensions: extensionAllowed,
      // withReadStream:
      //     true, // this will return PlatformFile object with read stream
    );
    if (result != null) {
      objFile = result.files.single;
      double sizeInMb = result.files[0].size / (1024 * 1024);
      if (docType.toString() == "bankproof" || docType.toString() == "incomeproof") {
        if (objFile.name.contains('.pdf') || objFile.name.contains('.PDF')) {
          if (sizeInMb > 2) {
            // This file is Longer the
            print('file is big');
            AppConfig().dismissLoaderDialog(context); //
            _documentBloc.showAlert(context, 'Maximum upload file size: 2 MB');
          } else {
            print('file is pefect');
            AppConfig().dismissLoaderDialog(context); //
            _documentBloc.saveDocument(context, docType, "small", objFile, objFile.bytes, proofType, uniqueId);
          }
        } else {
          AppConfig().dismissLoaderDialog(context); //
          _documentBloc.showAlert(context, "Only PDF supported");
        }
      } else {
        if (docType.toString() == "pancard" || docType.toString() == "signature" || docType.toString() == "addressproof" || docType.toString() == "photograph") {
          if (objFile.name.contains('.png') || objFile.name.contains('.jpeg') || objFile.name.contains('.PNG') || objFile.name.contains('.JPEG') || objFile.name.contains('.jpg') || objFile.name.contains('.JPG')) {
            if (sizeInMb > 2) {
              // This file is Longer the
              print('file is big');
              AppConfig().dismissLoaderDialog(context); //
              _documentBloc.showAlert(context, 'Maximum upload file size: 2 MB');
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
              _documentBloc.saveDocument(context, docType, "small", objFile, filess, proofType, uniqueId);
            }
          } else {
            AppConfig().dismissLoaderDialog(context); // show loader
            _documentBloc.showAlert(context, "Only 'png','PNG' 'jpeg', 'JPEG' ,'jpg' ,'JPG' supported");
          }
        }
      }
    } else {
      AppConfig().dismissLoaderDialog(context); // show loader
      // _documentBloc.showAlert(context, "Please upload all documents");
    }
  }
}

btnprocess(BuildContext context, DocumentBloc _documentBloc) {
  return Container(
    child: Row(
      mainAxisAlignment: MainAxisAlignment.center,
      crossAxisAlignment: CrossAxisAlignment.center,
      children: [
        Container(
          alignment: Alignment.centerRight,
          margin: EdgeInsets.only(right: 15, top: 0, bottom: 25),
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
                if (_documentBloc.docPan && _documentBloc.docSignature && _documentBloc.docBankProof && _documentBloc.docAddressProof && _documentBloc.docIncomeProof && _documentBloc.docPhotograph) {
                  HomeScreenSmall.screensStreamSmall.sink.add(Ekycscreenamesmall.ipvdetailscreen);
                } else {
                  var errorMessage = 'Please upload all documents';
                  if (_documentBloc.docPan == false) {
                    errorMessage = 'Please upload Pan card.';
                  } else if (_documentBloc.docSignature == false) {
                    errorMessage = 'Please upload Signature.';
                  } else if (_documentBloc.docBankProof == false) {
                    errorMessage = 'Please upload Bank Proof.';
                  } else if (_documentBloc.docAddressProof == false) {
                    errorMessage = 'Please upload Address Proof.';
                  } else if (_documentBloc.docIncomeProof == false) {
                    errorMessage = 'Please upload Income Proof.';
                  } else if (_documentBloc.docPhotograph == false) {
                    errorMessage = 'Please upload Photograph.';
                  }
                  _documentBloc.showAlert(
                    context,
                    errorMessage,
                  );
                }
                // showDialog(
                //   context: context,
                //   builder: (BuildContext context) {
                //     return Center(child: CustomDialogmethod());
                //   },
                // );
              },
            ),
          ),
        ),
      ],
    ),
  );
}
