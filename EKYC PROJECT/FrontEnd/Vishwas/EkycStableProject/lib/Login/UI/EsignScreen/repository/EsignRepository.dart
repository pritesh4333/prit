import 'dart:async';
import "dart:convert";
import 'dart:io';
import 'dart:math';
import 'dart:typed_data';
import 'dart:ui';

import 'package:e_kyc/Login/UI/Configuration/AppConfig.dart';
import 'package:e_kyc/Login/UI/EsignScreen/bloc/EsignBloc.dart';
import 'package:e_kyc/Login/UI/EsignScreen/model/DigioRequestMode.dart';
import 'package:e_kyc/Login/UI/EsignScreen/model/Digiouploadresponse.dart';
import 'package:e_kyc/Login/UI/EsignScreen/model/EsignRequestModel.dart';
import 'package:e_kyc/Login/UI/HomeScreen/HomeScreenLarge.dart';
import 'package:e_kyc/Login/UI/HomeScreen/HomeScreenSmall.dart';
import 'package:e_kyc/Login/UI/IPVScreen/bloc/IpvScreenBlock.dart';
import 'package:e_kyc/Login/UI/Login/bloc/LoginBloc.dart';
import 'package:e_kyc/Login/UI/Login/model/LoginUserDetailModelResponse.dart';
import 'package:e_kyc/Login/UI/Login/repository/LoginRepository.dart';
import 'package:e_kyc/Login/UI/PanDetailsScreen/repository/PanDetailRepository.dart';
import 'package:e_kyc/Utilities/ResponseModel.dart';
import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
// ignore: implementation_imports
import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter/widgets.dart';
import 'package:http/http.dart' as http;
import 'package:intl/intl.dart';
import 'package:syncfusion_flutter_pdf/pdf.dart';
import 'package:e_kyc/Login/UI/Login/View/save_file_mobile.dart' if (dart.library.html) 'package:e_kyc/Login/UI/Login/View/save_file_web.dart';
import 'package:e_kyc/Login/UI/IPVScreen/platformspec/mobiledevice.dart' if (dart.library.html) "package:e_kyc/Login/UI/IPVScreen/platformspec/webdevice.dart" as newWindow;
import 'package:universal_html/html.dart' as html;
import 'package:http_parser/http_parser.dart';
// import 'package:http_parser/http_parser.dart';

class EsignRepository {
  LoginBloc loginbloc = new LoginBloc();
  String uRl = AppConfig().url;
  var globalRespObj = LoginRepository.loginDetailsResObjGlobal;
  static Timer timerObj;
  static String documentID;
  var signature;
  http.Response response;
  var _chars = 'AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz1234567890';
  Random _rnd = Random();
  String getRandomString(int length) => String.fromCharCodes(Iterable.generate(length, (_) => _chars.codeUnitAt(_rnd.nextInt(_chars.length))));

  saveEsignDetailsAPI(String email, String mobile, String esign) async {
    EsignDetailData data = new EsignDetailData();

    data.emailId = email;
    data.mobileNo = mobile;
    data.esign = esign;

    EsignRequestModel request1 = new EsignRequestModel();
    request1.data = data;

    final jsonRequest = json.encode(request1);
    print('Final E-Kyc Request - $jsonRequest');

    var headers = {'Content-Type': 'application/json'};
    var apiName = AppConfig().url + "SaveEsignDetails";
    var request = http.Request('POST', Uri.parse(apiName));

    request.body = jsonRequest;
    request.headers.addAll(headers);

    http.StreamedResponse response = await request.send();

    if (response.statusCode == 200) {
      final responseData = await response.stream.bytesToString();
      print('Response Data :- ${responseData.toString()}');
      final loginResponseObj = ResponseModel.fromJson(
        json.decode(
          responseData.toString(),
        ),
      );
      return loginResponseObj;
    } else {
      print(response.reasonPhrase);
    }
  }

  // SendEsignPDFDetailsAPI(List<int> selectedFile) async {
  //   var headers = {
  //     'Authorization':
  //         'Basic QUlPOE9TTVE3VVFHTFpGQkU0WVJBQ0ZIVE9PS0VQOUI6NDhMR1BYNVpJUFdOVVExRjUyUFNHUUE5V1ZER1FENFI='
  //   };

  //   var request = http.MultipartRequest('POST',
  //       Uri.parse('https://ext.digio.in:444/v2/client/document/upload'));
  //   // request.fields.addAll({
  //   //   "request":
  //   //       "{"'signers'": [{"'identifier'": "'parmarprit100@gmail.com'","'name'":"'SANKET NAYAK'","'sign_type'": "'aadhaar'","'reason'": "'Loan Agreement'"}],"'expire_in_days'": 10,"'display_on_page'": "'all'"}"
  //   // });
  //   // request.files.add(await http.MultipartFile.fromPath('file',
  //   //     '/C:/Users/user/Downloads/photo-1565234574056-1c53de66ecb9.pdf'));
  //   request.files.add(
  //     await http.MultipartFile.fromBytes('file', selectedFile,
  //         contentType: new MediaType('application', 'octet-stream'),
  //         filename: 'test.mp4'),
  //   );
  //   request.headers.addAll(headers);

  //   http.StreamedResponse response = await request.send();

  //   if (response.statusCode == 200) {
  //     print(response.stream.bytesToString());
  //   } else {
  //     print(response.reasonPhrase);
  //   }
  // }

  sendEsignPDFDetailsAPI(String screenName, String email, String mobile, String pan) async {
    Dataobj data = new Dataobj();

    data.emailId = email;
    data.mobileNo = mobile;
    data.name = pan;
    data.pdfpath = pan + "FinalEKycDocument.pdf";

    DigioRequestMode request1 = new DigioRequestMode();
    request1.response = data;

    final jsonRequest = json.encode(request1);

    var headers = {'Content-Type': 'application/json'};

    var url = AppConfig().url + "uploadpdftodigio";
    var request = http.Request('POST', Uri.parse(url));

    request.body = jsonRequest;
    request.headers.addAll(headers);

    http.StreamedResponse response = await request.send();

    if (response.statusCode == 200) {
      final responseData = await response.stream.bytesToString();
      print(responseData);
      final loginResponseObj = Digiouploadresponse.fromJson(
        json.decode(
          responseData.toString(),
        ),
      );

      return loginResponseObj;
    } else {
      print(response.reasonPhrase);
    }
  }

  void showLoaderDialog(BuildContext context) {
    showDialog(
      barrierDismissible: false,
      context: context,
      builder: (BuildContext context) {
        return Container(
          child: Center(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.center,
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                Container(
                  margin: EdgeInsets.only(top: 10, bottom: 10),
                  child: Text(
                    "Please wait",
                    style: TextStyle(color: Colors.black, fontFamily: 'century_gothic', fontSize: 15, fontWeight: FontWeight.w600),
                  ),
                ),
                CircularProgressIndicator(),
              ],
            ),
          ),
        );
      },
    );
  }

  void dismissLoaderDialog(BuildContext context) {
    try {
      Navigator.pop(context); //// dismiss loader
    } catch (exception) {
      timerObj.cancel();
      print("Loader dissmiss" + exception);
    } //// dismiss loader
  }

  void pdfDownloadshowLoaderDialog(BuildContext context) {
    showDialog(
      barrierDismissible: false,
      context: context,
      builder: (BuildContext context) {
        return WillPopScope(
          onWillPop: () {
            print("back pressed");
            timerObj.cancel();
            timerObj = null;
            Navigator.pop(context);
          },
          child: Container(
            child: Center(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.center,
                mainAxisAlignment: MainAxisAlignment.center,
                children: <Widget>[
                  Container(
                    margin: EdgeInsets.only(top: 10, bottom: 10),
                    child: Text(
                      "Do not press back button or refresh while completing E-sign process",
                      style: TextStyle(color: Colors.black, fontFamily: 'century_gothic', fontSize: 15, fontWeight: FontWeight.w600),
                    ),
                  ),
                  CircularProgressIndicator(),
                ],
              ),
            ),
          ),
        );
      },
    );
  }

  void pdfDownloadMessageLoader(BuildContext context) {
    showDialog(
      barrierDismissible: false,
      context: context,
      builder: (BuildContext context) {
        return WillPopScope(
          onWillPop: () {
            print("back pressed");
            timerObj.cancel();
            timerObj = null;
            Navigator.pop(context);
          },
          child: Container(
            child: Center(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.center,
                mainAxisAlignment: MainAxisAlignment.center,
                children: <Widget>[
                  Container(
                    margin: EdgeInsets.only(top: 10, bottom: 10),
                    child: Text(
                      "Generating pdf.. ",
                      style: TextStyle(color: Colors.black, fontFamily: 'century_gothic', fontSize: 15, fontWeight: FontWeight.w600),
                    ),
                  ),
                  CircularProgressIndicator(),
                ],
              ),
            ),
          ),
        );
      },
    );
  }

  fetchResults(BuildContext context, String screenName) async {
    // Your fetchResult logic here
    // ignore: unused_local_variable
    pdfDownloadshowLoaderDialog(context); //// show loader
    final loginAPIClient = http.Client();
    final url = AppConfig().url;
    var apiName = url + "getEkycUserDetails?email_id=" + LoginRepository.loginEmailId + "&mobile_no=" + LoginRepository.loginMobileNo + "";

    final response = await http.get(
      Uri.parse(apiName),
    );

    if (response.statusCode == 200) {
      print(response.body);

      final loginResponseObj = LoginUserDetailModelResponse.fromJson(json.decode(response.body));

      if (loginResponseObj.response.data.message[0].esign == "0") {
        print("You can now download pdf");
        timerObj?.cancel();
        timerObj = null;

        DownloadPdf(context, screenName, loginResponseObj);
      } else {
        timerObj ??= Timer.periodic(Duration(seconds: 3), (timer) {
          print("waiting for response");
          dismissLoaderDialog(context); //// hide loader

          fetchResults(context, screenName);
        });
        // await Future.delayed(Duration(seconds: 3));

      }
    } else {}
  }

  Future<List<int>> _readDocumentData(String name, [bool isImage = false]) async {
    final ByteData data = await rootBundle.load(isImage ? 'asset/images/$name' : 'asset/pdf/$name');
    return data.buffer.asUint8List(data.offsetInBytes, data.lengthInBytes);
  }

  signPDF(BuildContext context, String screenName) async {
    if (globalRespObj != null) {
      pdfDownloadMessageLoader(context); // show loader
      print('Data already is in global object');
      if (globalRespObj.response.errorCode == "0") {
        //Load the PDF document.
        final PdfDocument document = PdfDocument(inputBytes: await _readDocumentData('MasterFormSilverStream.pdf'));

        //Get the signature field.
        // final PdfSignatureField signatureField =
        //     document.form.fields[6] as PdfSignatureField;

        //Gets the first page from the document
        PdfPage page = document.pages[4];

        // Draw image
        page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(234, 179, 5, 5));

        page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(235, 207, 5, 5));
//Draw the text
        page.graphics.drawString('' + globalRespObj.response.data.message[0].firstname1 + '', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(180, 240, 200, 20));

        page.graphics.drawString('' + globalRespObj.response.data.message[0].middlename1 + '', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(320, 240, 200, 20));
        page.graphics.drawString('' + globalRespObj.response.data.message[0].lastname1 + '', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(455, 240, 200, 20));

        page.graphics.drawString('' + globalRespObj.response.data.message[0].firstname2 + '', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(180, 265, 200, 20));
        page.graphics.drawString('' + globalRespObj.response.data.message[0].middlename2 + '', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(320, 265, 200, 20));
        page.graphics.drawString('' + globalRespObj.response.data.message[0].lastname2 + '', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(455, 265, 200, 20));

        page.graphics.drawString('' + globalRespObj.response.data.message[0].firstname_mother + '', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(180, 280, 200, 20));
        page.graphics.drawString('' + globalRespObj.response.data.message[0].middlename_mother + '', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(320, 280, 200, 20));
        page.graphics.drawString('' + globalRespObj.response.data.message[0].lastname_mother + '', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(455, 280, 200, 20));

        page.graphics.drawString('' + globalRespObj.response.data.message[0].dob + '', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(150, 297, 200, 20));

        if (globalRespObj.response.data.message[0].gender == "male" || globalRespObj.response.data.message[0].gender == "Male") {
          page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(139, 316, 5, 5));
        } else if (globalRespObj.response.data.message[0].gender == "female" || globalRespObj.response.data.message[0].gender == "Female") {
          page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(263, 316, 5, 5));
        } else {
          page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(345, 316, 5, 5));
        }

        if (globalRespObj.response.data.message[0].maritalstatus == "married" || globalRespObj.response.data.message[0].maritalstatus == "Married") {
          page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(139, 332, 5, 5));
        } else if (globalRespObj.response.data.message[0].maritalstatus == "single" || globalRespObj.response.data.message[0].maritalstatus == "Single") {
          page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(263, 332, 5, 5));
        } else {
          page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(345, 332, 5, 5));
        }
        if (globalRespObj.response.data.message[0].nationality == "Indian" || globalRespObj.response.data.message[0].nationality == "indian") {
          page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(139, 346, 5, 5));
        }

        if (globalRespObj.response.data.message[0].occupation != "" || globalRespObj.response.data.message[0].occupation != null) {
          if (globalRespObj.response.data.message[0].occupation == "S-Service") {
            page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(139, 380, 5, 5));
          }
          if (globalRespObj.response.data.message[0].occupation == "Private Sector") {
            page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(194, 380, 5, 5));
          }
          if (globalRespObj.response.data.message[0].occupation == "Public Sector") {
            page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(264, 380, 5, 5));
          }
          if (globalRespObj.response.data.message[0].occupation == "Government Sector") {
            page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(332, 380, 5, 5));
          }
          if (globalRespObj.response.data.message[0].occupation == "O-Others") {
            page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(139, 392, 5, 5));
          }
          if (globalRespObj.response.data.message[0].occupation == "Professional") {
            page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(194, 392, 5, 5));
          }
          if (globalRespObj.response.data.message[0].occupation == "Self Employed") {
            page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(264, 392, 5, 5));
          }
          if (globalRespObj.response.data.message[0].occupation == "Retired") {
            page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(332, 392, 5, 5));
          }
          if (globalRespObj.response.data.message[0].occupation == "Housewife") {
            page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(376, 392, 5, 5));
          }
          if (globalRespObj.response.data.message[0].occupation == "Student") {
            page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(428, 392, 5, 5));
          }
          if (globalRespObj.response.data.message[0].occupation == "B-Business") {
            page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(139, 404, 5, 5));
          }
          if (globalRespObj.response.data.message[0].occupation == "X- Not Categorized") {
            page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(139, 414, 5, 5));
          }
        }
        if (globalRespObj.response.data.message[0].pan != "" || globalRespObj.response.data.message[0].pan != null) {
          page.graphics.drawString('' + globalRespObj.response.data.message[0].pan + '', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(139, 485, 200, 20));
        }
        // Fetching image start
        try {
          // http://192.168.209.11:7006/viewDocuments?email_id=pritesh.parmar@greeksoft.co.in&mobile_no=8767957178&type=pancard&pan=BWXPP5879A&rendom=i1jUq
          Uri uri1 = Uri.parse(uRl + "viewDocuments?email_id=" + globalRespObj.response.data.message[0].emailId + "&mobile_no=" + globalRespObj.response.data.message[0].mobileNo + "&type=photograph&pan=" + globalRespObj.response.data.message[0].pan + "&rendom=" + getRandomString(5));
          http.Response response1 = await http.get(
            uri1,
          );
          Uint8List photograph = response1.bodyBytes;

          photograph.buffer.asUint8List(photograph.offsetInBytes, photograph.lengthInBytes);
          page.graphics.drawImage(PdfBitmap(photograph), const Rect.fromLTWH(502, 318, 79, 85));
        } catch (error) {
          print('error fetching photograph:$error');
        }
// Fetching image
        try {
          // Uri uri = Uri.parse(uRl +
          //     "images/" +
          //     globalRespObj.response.data.message[0].signature);
          // response = await http.get(
          //   uri,
          // );
          // signature = response.bodyBytes;
          // signature.buffer
          //     .asUint8List(signature.offsetInBytes, signature.lengthInBytes);
          // page.graphics.drawImage(
          //     PdfBitmap(signature), const Rect.fromLTWH(501, 405, 80, 20));
          dynamic currentTime = DateFormat.jm().format(DateTime.now());
          var now = new DateTime.now();
          var formatter = new DateFormat('dd-MM-yyyy');
          String formattedDate = formatter.format(now);
          signature = ' Signed by : ' + globalRespObj.response.data.message[0].firstname1 + ' ' + globalRespObj.response.data.message[0].middlename1 + ' ' + globalRespObj.response.data.message[0].lastname1 + ' \n Reason: EKYC From by Vitt Fincap Services Pvt Ltd. \n eSigned using Aadhaar (digo.in) \n Date: ' + formattedDate + '  ' + currentTime;
          page.graphics.drawString('' + signature + '', PdfStandardFont(PdfFontFamily.helvetica, 3), bounds: Rect.fromLTWH(501, 405, 80, 20));
        } catch (error) {
          print('error fetching signature:$error');
        }

        page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(139, 364, 5, 5));

        page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(35, 490, 5, 5));

        page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(237, 611, 5, 5));

        var obj = globalRespObj.response.data.message[0];
        var addressProofType = obj.addressprroftype.toString();
        var addressProofId = "";
        if (obj.addressProofId != null && obj.addressProofId != "") {
          if (obj.addressProofId.length > 4) {
            var totalLength = obj.addressProofId.toString().length;
            var startPos = (totalLength - 4);
            var endPos = totalLength;
            var firstPart = ""; //= obj.addressProofId.substring(0, starPos);
            var count = 0;
            firstPart.replaceAll("", ",");
            do {
              firstPart += "*";
              count++;
            } while (startPos > count);
            var secondPart = obj.addressProofId.substring(startPos, endPos);
            addressProofId = firstPart + secondPart;
          }
        }

        switch (addressProofType) {
          case "0":
            print("Voter ID");
            //Voter ID Card
            page.graphics.drawImage(
              PdfBitmap(await _readDocumentData('right_tick.png', true)),
              const Rect.fromLTWH(115, 636, 4, 4),
            );
            page.graphics.drawString(
              addressProofId,
              PdfStandardFont(PdfFontFamily.helvetica, 11),
              bounds: Rect.fromLTWH(400, 634, 100, 20),
            );
            break;
          case "1":
            print("Driving License");
            //Driving License
            page.graphics.drawImage(
              PdfBitmap(await _readDocumentData('right_tick.png', true)),
              const Rect.fromLTWH(237, 626, 4, 4),
            );
            page.graphics.drawString(
              addressProofId,
              PdfStandardFont(PdfFontFamily.helvetica, 11),
              bounds: Rect.fromLTWH(400, 634, 100, 20),
            );
            break;
          case "2":
            print("Aadhar");
            //Aadhar
            page.graphics.drawImage(
              PdfBitmap(await _readDocumentData('right_tick.png', true)),
              const Rect.fromLTWH(335, 626, 4, 4),
            );
            page.graphics.drawString(
              addressProofId,
              PdfStandardFont(PdfFontFamily.helvetica, 11),
              bounds: Rect.fromLTWH(400, 634, 100, 20),
            );
            break;
          case "3":
            print("Passport");
            //passport
            page.graphics.drawImage(
              PdfBitmap(await _readDocumentData('right_tick.png', true)),
              const Rect.fromLTWH(115, 626, 4, 4),
            );
            page.graphics.drawString(
              addressProofId,
              PdfStandardFont(PdfFontFamily.helvetica, 11),
              bounds: Rect.fromLTWH(400, 634, 100, 20),
            );
            break;
          case "4":
            print("NREGA JOB Card");
            //NREGA JOB Card
            page.graphics.drawImage(
              PdfBitmap(await _readDocumentData('right_tick.png', true)),
              const Rect.fromLTWH(237, 636, 4, 4),
            );
            page.graphics.drawString(
              addressProofId,
              PdfStandardFont(PdfFontFamily.helvetica, 11),
              bounds: Rect.fromLTWH(400, 634, 100, 20),
            );
            break;
          default:
            print("Other");
            //Other
            page.graphics.drawImage(
              PdfBitmap(await _readDocumentData('right_tick.png', true)),
              const Rect.fromLTWH(335, 636, 4, 4),
            );
            page.graphics.drawString(
              addressProofId,
              PdfStandardFont(PdfFontFamily.helvetica, 11),
              bounds: Rect.fromLTWH(400, 634, 100, 20),
            );
            break;
        }

        String resAddress = '';
        if (obj.resAddr1 != "" && obj.resAddr1 != null && obj.resAddr2 != "" && obj.resAddr2 != null) {
          resAddress = ' ' + obj.resAddr1 + ' ' + obj.resAddr2 + ' ';
        }
        page.graphics.drawString(
          resAddress,
          PdfStandardFont(PdfFontFamily.helvetica, 11),
          bounds: Rect.fromLTWH(139, 670, 150, 15),
        );
        if (obj.resAddrCity != "" && obj.resAddrCity != null) {
          page.graphics.drawString(
            obj.resAddrCity,
            PdfStandardFont(PdfFontFamily.helvetica, 11),
            bounds: Rect.fromLTWH(478, 695, 150, 15),
          );
        }
        if (obj.resAddrState != "" && obj.resAddrState != null) {
          page.graphics.drawString(
            obj.resAddrState,
            PdfStandardFont(PdfFontFamily.helvetica, 11),
            bounds: Rect.fromLTWH(122, 705, 150, 15),
          );
        }
        if (obj.resAddrPincode != "" && obj.resAddrPincode != null) {
          page.graphics.drawString(
            obj.resAddrPincode,
            PdfStandardFont(PdfFontFamily.helvetica, 11),
            bounds: Rect.fromLTWH(275, 705, 150, 15),
          );
        }
        page.graphics.drawString(
          obj.nationality,
          PdfStandardFont(PdfFontFamily.helvetica, 11),
          bounds: Rect.fromLTWH(525, 705, 150, 15),
        );

        page = document.pages[5];

        page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(33, 106, 5, 5));
        if (obj.resAddr1 != "" && obj.resAddr1 != null && obj.resAddr2 != "" && obj.resAddr2 != null) {
          resAddress = ' ' + obj.resAddr1 + ' ' + obj.resAddr2 + ' ';
        }
        page.graphics.drawString(
          resAddress,
          PdfStandardFont(PdfFontFamily.helvetica, 11),
          bounds: Rect.fromLTWH(100, 112, 150, 15),
        );
        if (obj.resAddrCity != "" && obj.resAddrCity != null) {
          page.graphics.drawString(
            obj.resAddrCity,
            PdfStandardFont(PdfFontFamily.helvetica, 11),
            bounds: Rect.fromLTWH(478, 140, 150, 15),
          );
        }
        if (obj.resAddrState != "" && obj.resAddrState != null) {
          page.graphics.drawString(
            obj.resAddrState,
            PdfStandardFont(PdfFontFamily.helvetica, 11),
            bounds: Rect.fromLTWH(122, 155, 150, 15),
          );
        }
        if (obj.resAddrPincode != "" && obj.resAddrPincode != null) {
          page.graphics.drawString(
            obj.resAddrPincode,
            PdfStandardFont(PdfFontFamily.helvetica, 11),
            bounds: Rect.fromLTWH(275, 155, 150, 15),
          );
        }
        page.graphics.drawString(
          obj.nationality,
          PdfStandardFont(PdfFontFamily.helvetica, 11),
          bounds: Rect.fromLTWH(560, 155, 150, 15),
        );
        page.graphics.drawString('' + globalRespObj.response.data.message[0].mobileNo + '', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(470, 195, 200, 20));

        page.graphics.drawString('' + globalRespObj.response.data.message[0].emailId + '', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(300, 205, 200, 20));
        try {
          // signature = response.bodyBytes;
          // signature.buffer
          //     .asUint8List(signature.offsetInBytes, signature.lengthInBytes);
          // page.graphics.drawImage(
          //     PdfBitmap(signature), const Rect.fromLTWH(465, 335, 110, 30));
          page.graphics.drawString('' + signature + '', PdfStandardFont(PdfFontFamily.helvetica, 3), bounds: Rect.fromLTWH(465, 335, 110, 30));
        } catch (error) {
          print('error fetching signature::$error');
        }
        dynamic currentTime = DateFormat.jm().format(DateTime.now());
        var now = new DateTime.now();
        var formatter = new DateFormat('dd-MM-yyyy');
        String formattedDate = formatter.format(now);
        page.graphics.drawString('' + formattedDate + " " + currentTime + '', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(60, 370, 200, 20));

        if (obj.resAddrCity != "" && obj.resAddrCity != null) {
          page.graphics.drawString(
            obj.resAddrCity,
            PdfStandardFont(PdfFontFamily.helvetica, 11),
            bounds: Rect.fromLTWH(275, 370, 200, 20),
          );
        }

        if (obj.latitude != "" && obj.latitude != null && obj.longitude != "" && obj.longitude != null) {
          var latitude = "Latitude  " + obj.latitude;
          var longitude = "Longitude  " + obj.longitude;
          var finalLocation = latitude + "\t\t\t" + longitude;
          page.graphics.drawString(
            finalLocation,
            PdfStandardFont(PdfFontFamily.helvetica, 11),
            bounds: Rect.fromLTWH(80, 590, 400, 20),
          );
        }

        page = document.pages[6];
        if (globalRespObj.response.data.message[0].bankname != "" || globalRespObj.response.data.message[0].bankname != null) {
          page.graphics.drawString('' + globalRespObj.response.data.message[0].bankname + '', PdfStandardFont(PdfFontFamily.helvetica, 8), bounds: Rect.fromLTWH(28, 168, 200, 20));
        }

        if (globalRespObj.response.data.message[0].bank_address != null && globalRespObj.response.data.message[0].bank_address != "") {
          var bankAddress = globalRespObj.response.data.message[0].bank_address;
          page.graphics.drawString(
            " " + ((bankAddress.split(",").toString().replaceAll(",", "\n")).replaceAll("[", " ")).replaceAll("]", ""),
            PdfStandardFont(PdfFontFamily.helvetica, 7),
            bounds: Rect.fromLTWH(115, 155, 200, 150),
          );
        }

        if (globalRespObj.response.data.message[0].accountnumber != "" || globalRespObj.response.data.message[0].accountnumber != null) {
          page.graphics.drawString('' + globalRespObj.response.data.message[0].accountnumber + '', PdfStandardFont(PdfFontFamily.helvetica, 8), bounds: Rect.fromLTWH(262, 168, 200, 20));
        }

        page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(350, 159, 7, 7));

        if (globalRespObj.response.data.message[0].micr_no != null && globalRespObj.response.data.message[0].micr_no != "") {
          var micrNo = globalRespObj.response.data.message[0].micr_no;
          page.graphics.drawString(
            micrNo,
            PdfStandardFont(PdfFontFamily.helvetica, 8),
            bounds: Rect.fromLTWH(440, 168, 200, 20),
          );
        }

        if (globalRespObj.response.data.message[0].ifsccode != "" || globalRespObj.response.data.message[0].ifsccode != null) {
          page.graphics.drawString('' + globalRespObj.response.data.message[0].ifsccode + '', PdfStandardFont(PdfFontFamily.helvetica, 8), bounds: Rect.fromLTWH(520, 168, 200, 20));
        }

        if (globalRespObj.response.data.message[0].nseCash != "" || globalRespObj.response.data.message[0].nseCash != null) {
          page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(35, 435, 7, 7));
          page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(35, 449, 7, 7));

          // page.graphics.drawImage(
          //     PdfBitmap(signature), const Rect.fromLTWH(75, 432, 85, 25));
          page.graphics.drawString('' + signature + '', PdfStandardFont(PdfFontFamily.helvetica, 3), bounds: Rect.fromLTWH(75, 432, 85, 25));
        }
        if (globalRespObj.response.data.message[0].bseCash != "" || globalRespObj.response.data.message[0].bseCash != null) {
          page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(35, 471, 7, 7));
          page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(35, 485, 7, 7));
          // page.graphics.drawImage(
          //     PdfBitmap(signature), const Rect.fromLTWH(75, 466, 85, 25));
          page.graphics.drawString('' + signature + '', PdfStandardFont(PdfFontFamily.helvetica, 3), bounds: Rect.fromLTWH(75, 466, 85, 25));
        }
        if (globalRespObj.response.data.message[0].nseFo != "" || globalRespObj.response.data.message[0].nseFo != null) {
          page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(173, 435, 7, 7));
          page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(173, 451, 7, 7));
          // page.graphics.drawImage(
          //     PdfBitmap(signature), const Rect.fromLTWH(213, 435, 85, 25));
          page.graphics.drawString('' + signature + '', PdfStandardFont(PdfFontFamily.helvetica, 3), bounds: Rect.fromLTWH(213, 435, 85, 25));
        }
        if (globalRespObj.response.data.message[0].bseFo != "" || globalRespObj.response.data.message[0].bseFo != null) {
          page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(173, 473, 7, 7));
          page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(173, 490, 7, 7));

          // page.graphics.drawImage(
          //     PdfBitmap(signature), const Rect.fromLTWH(213, 469, 85, 25));
          page.graphics.drawString('' + signature + '', PdfStandardFont(PdfFontFamily.helvetica, 3), bounds: Rect.fromLTWH(213, 469, 85, 25));
        }
        if (globalRespObj.response.data.message[0].nseCurrency != "" || globalRespObj.response.data.message[0].nseCurrency != null) {
          page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(310, 435, 7, 7));
          page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(312, 448, 7, 7));

          // page.graphics.drawImage(
          //     PdfBitmap(signature), const Rect.fromLTWH(350, 435, 85, 25));
          page.graphics.drawString('' + signature + '', PdfStandardFont(PdfFontFamily.helvetica, 3), bounds: Rect.fromLTWH(350, 435, 85, 25));
        }
        if (globalRespObj.response.data.message[0].bseCurrency != "" || globalRespObj.response.data.message[0].bseCurrency != null) {
          page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(312, 473, 7, 7));
          page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(313, 487, 7, 7));

          // page.graphics.drawImage(
          //     PdfBitmap(signature), const Rect.fromLTWH(349, 468, 85, 25));

          page.graphics.drawString('' + signature + '', PdfStandardFont(PdfFontFamily.helvetica, 3), bounds: Rect.fromLTWH(349, 468, 85, 25));
        }
        if (globalRespObj.response.data.message[0].mcxCommodty != "" || globalRespObj.response.data.message[0].mcxCommodty != null) {
          page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(35, 507, 7, 7));

          // page.graphics.drawImage(
          //     PdfBitmap(signature), const Rect.fromLTWH(75, 500, 85, 20));

          page.graphics.drawString('' + signature + '', PdfStandardFont(PdfFontFamily.helvetica, 3), bounds: Rect.fromLTWH(75, 500, 85, 20));
        }

        if (globalRespObj.response.data.message[0].incomerange == "Below 1 Lakh" && globalRespObj.response.data.message[0].incomerange != null) {
          page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(39, 565, 7, 7));
        } else if (globalRespObj.response.data.message[0].incomerange == "1 Lakh TO 5 Lakh" && globalRespObj.response.data.message[0].incomerange != null) {
          page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(127, 566, 7, 7));
        } else if (globalRespObj.response.data.message[0].incomerange == "5 Lakh TO 10 Lakh" && globalRespObj.response.data.message[0].incomerange != null) {
          page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(221, 566, 7, 7));
        } else if (globalRespObj.response.data.message[0].incomerange == "10 Lakh TO 25 Lakh" && globalRespObj.response.data.message[0].incomerange != null) {
          page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(321, 566, 7, 7));
        } else if (globalRespObj.response.data.message[0].incomerange == "Above 25 Lakh" && globalRespObj.response.data.message[0].incomerange != null) {
          page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(425, 566, 7, 7));
        } else {
          page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(530, 566, 7, 7));
        }

        if (globalRespObj.response.data.message[0].occupation != "" || globalRespObj.response.data.message[0].occupation != null) {
          if (globalRespObj.response.data.message[0].occupation == "Private Sector") {
            page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(39, 634, 7, 7));
          }
          if (globalRespObj.response.data.message[0].occupation == "Public Sector") {
            page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(136, 634, 7, 7));
          }
          if (globalRespObj.response.data.message[0].occupation == "Government Sector") {
            page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(235, 634, 7, 7));
          }

          if (globalRespObj.response.data.message[0].occupation == "B-Business") {
            page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(364, 634, 7, 7));
          }

          if (globalRespObj.response.data.message[0].occupation == "Professional") {
            page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(446, 634, 7, 7));
          }
          if (globalRespObj.response.data.message[0].occupation == "Retired") {
            page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(39, 647, 7, 7));
          }
          if (globalRespObj.response.data.message[0].occupation == "Housewife") {
            page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(136, 647, 7, 7));
          }
          if (globalRespObj.response.data.message[0].occupation == "Student") {
            page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(235, 647, 7, 7));
          }
          if (globalRespObj.response.data.message[0].occupation == "O-Others") {
            page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(364, 647, 7, 7));
          }
        }

        if (globalRespObj.response.data.message[0].action == "Politically Exposed Person" && globalRespObj.response.data.message[0].action != null) {
          page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(42, 692, 7, 7));
        }

        page.graphics.drawString(' N/A ', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(150, 708, 200, 20));

        page = document.pages[7];

        page.graphics.drawString(
          '' + globalRespObj.response.data.message[0].firstname1 + '   ' + globalRespObj.response.data.message[0].middlename1 + '  ' + globalRespObj.response.data.message[0].lastname1 + '',
          PdfStandardFont(PdfFontFamily.helvetica, 11),
          bounds: Rect.fromLTWH(140, 155, 200, 20),
        );

        page.graphics.drawString('' + globalRespObj.response.data.message[0].pan + ' ', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(140, 200, 200, 20));

        page.graphics.drawString(
          '' + globalRespObj.response.data.message[0].firstname1 + '   ' + globalRespObj.response.data.message[0].middlename1 + '  ' + globalRespObj.response.data.message[0].lastname1 + '',
          PdfStandardFont(PdfFontFamily.helvetica, 11),
          bounds: Rect.fromLTWH(135, 630, 200, 20),
        );

        page.graphics.drawString('' + signature + '', PdfStandardFont(PdfFontFamily.helvetica, 3), bounds: Rect.fromLTWH(135, 655, 85, 30));
        page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(125, 717, 7, 7));

        page = document.pages[8];

        page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(480, 602, 7, 7));

        page.graphics.drawString('' + globalRespObj.response.data.message[0].emailId + ' ', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(225, 622, 200, 20));

        page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(494, 652, 7, 7));

        page = document.pages[9];

        page.graphics.drawString('' + globalRespObj.response.data.message[0].resAddrCity + ' ', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(70, 404, 200, 20));
        page.graphics.drawString('' + formattedDate + " " + currentTime + '', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(70, 434, 200, 20));
        page.graphics.drawString('' + signature + '', PdfStandardFont(PdfFontFamily.helvetica, 3), bounds: Rect.fromLTWH(440, 425, 85, 30));
        page.graphics.drawString('0', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(322, 757, 200, 13));
        page = document.pages[10];
        page.graphics.drawString('1', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(322, 757, 200, 30));

        page = document.pages[11];

        page.graphics.drawString('' + formattedDate + " " + currentTime + '', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(410, 126, 100, 20));

        page.graphics.drawString('' + globalRespObj.response.data.message[0].pan + ' ', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(410, 208, 200, 20));

        page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(38, 418, 7, 7));

        page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(147, 399, 7, 7));

        page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(445, 555, 7, 7));

        page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(445, 581, 7, 7));

        page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(513, 612, 7, 7));
        page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(445, 638, 7, 7));
        page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(402, 660, 7, 7));
        page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(448, 700, 7, 7));
        page.graphics.drawString('2', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(322, 757, 200, 15));

        page = document.pages[12];

        if (globalRespObj.response.data.message[0].micr_no != null && globalRespObj.response.data.message[0].micr_no != "") {
          var micrNo = globalRespObj.response.data.message[0].micr_no;
          page.graphics.drawString(
            micrNo,
            PdfStandardFont(PdfFontFamily.helvetica, 11),
            bounds: Rect.fromLTWH(190, 100, 100, 20),
          );
        }

        page.graphics.drawString('' + globalRespObj.response.data.message[0].ifsccode + ' ', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(190, 118, 200, 20));

        page.graphics.drawString('' + globalRespObj.response.data.message[0].accountnumber + ' ', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(190, 136, 300, 20));

        page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(221, 155, 7, 7));

        page.graphics.drawString('' + globalRespObj.response.data.message[0].bankname + ' ', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(190, 172, 300, 20));

        // bank name
        var str = globalRespObj.response.data.message[0].bank_address;
        page.graphics.drawString(str, PdfStandardFont(PdfFontFamily.helvetica, 10), bounds: Rect.fromLTWH(190, 207, 330, 150));

        //City
        var bankCity = globalRespObj.response.data.message[0].bank_address_city;
        page.graphics.drawString(
          bankCity,
          PdfStandardFont(PdfFontFamily.helvetica, 10),
          bounds: Rect.fromLTWH(200, 242, 150, 20),
        );
        //State
        var bankState = globalRespObj.response.data.message[0].bank_address_state;
        page.graphics.drawString(
          bankState,
          PdfStandardFont(PdfFontFamily.helvetica, 10),
          bounds: Rect.fromLTWH(350, 242, 150, 20),
        );

        // Country
        page.graphics.drawString('' + globalRespObj.response.data.message[0].nationality == "Indian" ? "India" : "Other" + ' ', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(500, 242, 300, 20));

        if (globalRespObj.response.data.message[0].resAddr2 == "" && globalRespObj.response.data.message[0].resAddr2 != null) {
          page.graphics.drawString('' + globalRespObj.response.data.message[0].resAddr2 + ' ', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(190, 223, 300, 20));
        }
        if (globalRespObj.response.data.message[0].resAddrCity == "" && globalRespObj.response.data.message[0].resAddrCity != null) {
          page.graphics.drawString('' + globalRespObj.response.data.message[0].resAddrCity + ' ', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(200, 242, 300, 20));
        }
        if (globalRespObj.response.data.message[0].resAddrState == "" && globalRespObj.response.data.message[0].resAddrState != null) {
          page.graphics.drawString('' + globalRespObj.response.data.message[0].resAddrState + ' ', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(350, 242, 300, 20));
        }

        if (globalRespObj.response.data.message[0].resAddrPincode == "" && globalRespObj.response.data.message[0].resAddrPincode != null) {
          page.graphics.drawString('' + globalRespObj.response.data.message[0].resAddrPincode + ' ', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(110, 261, 300, 20));
        }

        page.graphics.drawString('' + globalRespObj.response.data.message[0].mobileNo + ' ', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(300, 352, 300, 20));

        page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(428, 355, 7, 7));

        page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(173, 432, 7, 7));

        page.graphics.drawString('' + signature + '', PdfStandardFont(PdfFontFamily.helvetica, 3), bounds: Rect.fromLTWH(140, 700, 85, 30));

        page = document.pages[13];

        page.graphics.drawString('0.20', PdfStandardFont(PdfFontFamily.helvetica, 11), brush: PdfBrushes.red, bounds: Rect.fromLTWH(300, 141, 300, 20));

        page.graphics.drawString('0.02', PdfStandardFont(PdfFontFamily.helvetica, 11), brush: PdfBrushes.red, bounds: Rect.fromLTWH(500, 141, 300, 20));

        page.graphics.drawString('0.02', PdfStandardFont(PdfFontFamily.helvetica, 11), brush: PdfBrushes.red, bounds: Rect.fromLTWH(300, 160, 300, 20));

        page.graphics.drawString('0.02', PdfStandardFont(PdfFontFamily.helvetica, 11), brush: PdfBrushes.red, bounds: Rect.fromLTWH(500, 160, 300, 20));

        page.graphics.drawString('0.02', PdfStandardFont(PdfFontFamily.helvetica, 11), brush: PdfBrushes.red, bounds: Rect.fromLTWH(265, 217, 300, 20));

        page.graphics.drawString('0.02', PdfStandardFont(PdfFontFamily.helvetica, 11), brush: PdfBrushes.red, bounds: Rect.fromLTWH(390, 217, 300, 20));

        page.graphics.drawString('30/-', PdfStandardFont(PdfFontFamily.helvetica, 11), brush: PdfBrushes.red, bounds: Rect.fromLTWH(500, 217, 300, 20));

        page.graphics.drawString('0.02', PdfStandardFont(PdfFontFamily.helvetica, 11), brush: PdfBrushes.red, bounds: Rect.fromLTWH(265, 235, 300, 20));

        page.graphics.drawString('0.02', PdfStandardFont(PdfFontFamily.helvetica, 11), brush: PdfBrushes.red, bounds: Rect.fromLTWH(390, 235, 300, 20));

        page.graphics.drawString('30/-', PdfStandardFont(PdfFontFamily.helvetica, 11), brush: PdfBrushes.red, bounds: Rect.fromLTWH(500, 235, 300, 20));

        page.graphics.drawString('0.01', PdfStandardFont(PdfFontFamily.helvetica, 11), brush: PdfBrushes.red, bounds: Rect.fromLTWH(300, 300, 300, 20));

        page.graphics.drawString('10/-', PdfStandardFont(PdfFontFamily.helvetica, 11), brush: PdfBrushes.red, bounds: Rect.fromLTWH(500, 300, 300, 20));

        page.graphics.drawString('0.01', PdfStandardFont(PdfFontFamily.helvetica, 11), brush: PdfBrushes.red, bounds: Rect.fromLTWH(265, 370, 300, 20));

        page.graphics.drawString('0.01', PdfStandardFont(PdfFontFamily.helvetica, 11), brush: PdfBrushes.red, bounds: Rect.fromLTWH(390, 370, 300, 20));

        page.graphics.drawString('0.01', PdfStandardFont(PdfFontFamily.helvetica, 11), brush: PdfBrushes.red, bounds: Rect.fromLTWH(500, 370, 300, 20));

        page.graphics.drawString('0.01', PdfStandardFont(PdfFontFamily.helvetica, 11), brush: PdfBrushes.red, bounds: Rect.fromLTWH(265, 385, 300, 20));

        page.graphics.drawString('0.01', PdfStandardFont(PdfFontFamily.helvetica, 11), brush: PdfBrushes.red, bounds: Rect.fromLTWH(390, 385, 300, 20));

        page.graphics.drawString('0.01', PdfStandardFont(PdfFontFamily.helvetica, 11), brush: PdfBrushes.red, bounds: Rect.fromLTWH(500, 385, 300, 20));

        page.graphics.drawString('' + signature + '', PdfStandardFont(PdfFontFamily.helvetica, 3), bounds: Rect.fromLTWH(420, 730, 85, 30));

        // page = document.pages[14];

        // page.graphics.drawString('' + formattedDate + " " + currentTime + '', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(475, 121, 100, 20));

        // page.graphics.drawString('' + signature + '', PdfStandardFont(PdfFontFamily.helvetica, 3), bounds: Rect.fromLTWH(120, 670, 85, 30));

        page = document.pages[14];

        page.graphics.drawString('' + formattedDate + " " + currentTime + '', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(475, 100, 100, 20));
        page.graphics.drawString('' + signature + '', PdfStandardFont(PdfFontFamily.helvetica, 3), bounds: Rect.fromLTWH(455, 605, 85, 30));

        page = document.pages[15];

        page.graphics.drawString('' + formattedDate + " " + currentTime + '', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(475, 365, 100, 20));

        page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(48, 586, 7, 7));

        page.graphics.drawString('' + signature + '', PdfStandardFont(PdfFontFamily.helvetica, 3), bounds: Rect.fromLTWH(120, 310, 85, 30));

        page.graphics.drawString('' + signature + '', PdfStandardFont(PdfFontFamily.helvetica, 3), bounds: Rect.fromLTWH(120, 600, 85, 30));

        page = document.pages[16];

        page.graphics.drawString('' + formattedDate + " " + currentTime + '', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(475, 136, 100, 20));

        page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(164, 323, 7, 7));

        page.graphics.drawString('' + signature + '', PdfStandardFont(PdfFontFamily.helvetica, 3), bounds: Rect.fromLTWH(125, 460, 85, 30));

        page = document.pages[17];

        page.graphics.drawString('' + formattedDate + " " + currentTime + '', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(475, 130, 100, 20));
        page.graphics.drawString('' + globalRespObj.response.data.message[0].emailId + ' ', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(55, 322, 200, 20));

        page.graphics.drawString('' + signature + '', PdfStandardFont(PdfFontFamily.helvetica, 3), bounds: Rect.fromLTWH(150, 550, 85, 30));

        page = document.pages[18];

        page.graphics.drawString('' + formattedDate + " " + currentTime + '', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(475, 132, 100, 20));
        page.graphics.drawString('' + globalRespObj.response.data.message[0].firstname1 + '   ' + globalRespObj.response.data.message[0].middlename1 + '  ' + globalRespObj.response.data.message[0].lastname1 + '', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(370, 185, 200, 20));

        page.graphics.drawString('' + globalRespObj.response.data.message[0].accountnumber + ' ', PdfStandardFont(PdfFontFamily.helvetica, 10), bounds: Rect.fromLTWH(165, 374, 200, 20));

        page.graphics.drawString('' + globalRespObj.response.data.message[0].pan + ' ', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(498, 362, 200, 20));

        page.graphics.drawString('' + signature + '', PdfStandardFont(PdfFontFamily.helvetica, 3), bounds: Rect.fromLTWH(100, 630, 85, 30));

        page = document.pages[19];
        page.graphics.drawString('' + signature + '', PdfStandardFont(PdfFontFamily.helvetica, 3), bounds: Rect.fromLTWH(170, 745, 85, 30));

        page = document.pages[21];

        page.graphics.drawString('' + globalRespObj.response.data.message[0].mobileNo + '', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(180, 324, 200, 20));

        page.graphics.drawString('' + globalRespObj.response.data.message[0].emailId + '', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(150, 390, 200, 20));
        if (globalRespObj.response.data.message[0].parmAddrCity != "" && globalRespObj.response.data.message[0].parmAddrCity != null) {
          page.graphics.drawString('' + globalRespObj.response.data.message[0].parmAddrCity + '', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(86, 487, 100, 20));
        }

        page.graphics.drawString('' + formattedDate + '', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(477, 489, 100, 20));

        page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(63, 532, 7, 7));

        page.graphics.drawString(
          '' + signature + '',
          PdfStandardFont(PdfFontFamily.helvetica, 3),
          bounds: Rect.fromLTWH(170, 460, 85, 30),
        );

        page.graphics.drawString(
          '' + signature + '',
          PdfStandardFont(PdfFontFamily.helvetica, 3),
          bounds: Rect.fromLTWH(170, 750, 85, 30),
        );

        page = document.pages[22];
        page.graphics.drawString(
          '' + signature + '',
          PdfStandardFont(PdfFontFamily.helvetica, 3),
          bounds: Rect.fromLTWH(400, 750, 85, 30),
        );

        page = document.pages[23];

        page.graphics.drawString('' + globalRespObj.response.data.message[0].pan + '', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(130, 134, 200, 20));

        //Show  DP Code instead pancard number
        // page.graphics.drawString(
        //     '' + globalRespObj.response.data.message[0].pan + '',
        //     PdfStandardFont(PdfFontFamily.helvetica, 11),
        //     bounds: Rect.fromLTWH(480, 134, 200, 20));

        page.graphics.drawString('' + globalRespObj.response.data.message[0].nationality + '', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(140, 199, 100, 20));

        page.graphics.drawString('' + globalRespObj.response.data.message[0].nationality + '', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(420, 178, 100, 20));

        if (globalRespObj.response.data.message[0].incomerange == "Below 1 Lakh" && globalRespObj.response.data.message[0].incomerange != null) {
          page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(154, 223, 7, 7));
        } else if (globalRespObj.response.data.message[0].incomerange == "1 Lakh TO 5 Lakh" && globalRespObj.response.data.message[0].incomerange != null) {
          page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(275, 223, 7, 7));
        } else if (globalRespObj.response.data.message[0].incomerange == "5 Lakh TO 10 Lakh" && globalRespObj.response.data.message[0].incomerange != null) {
          page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(407, 223, 7, 7));
        } else if (globalRespObj.response.data.message[0].incomerange == "10 Lakh TO 25 Lakh" && globalRespObj.response.data.message[0].incomerange != null) {
          page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(154, 239, 7, 7));
        } else if (globalRespObj.response.data.message[0].incomerange == "Above 25 Lakh" && globalRespObj.response.data.message[0].incomerange != null) {
          page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(275, 239, 7, 7));
        } else {
          page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(407, 239, 7, 7));
        }

        page.graphics.drawString('' + formattedDate + '', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(470, 260, 100, 20));

        if (globalRespObj.response.data.message[0].occupation != "" || globalRespObj.response.data.message[0].occupation != null) {
          if (globalRespObj.response.data.message[0].occupation == "S-Service") {
            page.graphics.drawImage(
              PdfBitmap(await _readDocumentData('right_tick.png', true)),
              const Rect.fromLTWH(139, 380, 5, 5),
            );
          }
          if (globalRespObj.response.data.message[0].occupation == "B-Business") {
            page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(140, 294, 7, 7));
          }
          if (globalRespObj.response.data.message[0].occupation == "Private Sector") {
            page.graphics.drawImage(
              PdfBitmap(await _readDocumentData('right_tick.png', true)),
              const Rect.fromLTWH(211, 294, 7, 7),
            );
          }
          if (globalRespObj.response.data.message[0].occupation == "Housewife") {
            page.graphics.drawImage(
              PdfBitmap(await _readDocumentData('right_tick.png', true)),
              const Rect.fromLTWH(211, 309, 7, 7),
            );
          }
          if (globalRespObj.response.data.message[0].occupation == "Student") {
            page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(276, 309, 7, 7));
          }
          if (globalRespObj.response.data.message[0].occupation == "Retired") {
            page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(330, 309, 7, 7));
          }
          if (globalRespObj.response.data.message[0].occupation == "O-Others") {
            page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(461, 309, 7, 7));
          }
          if (globalRespObj.response.data.message[0].occupation == "Professional") {
            page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(298, 294, 7, 7));
          }
          if (globalRespObj.response.data.message[0].occupation == "Government Sector") {
            page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(386, 294, 7, 7));
          }
          if (globalRespObj.response.data.message[0].occupation == "Public Sector") {
            page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(498, 294, 7, 7));
          }
          if (globalRespObj.response.data.message[0].occupation == "Self Employed") {
            page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(264, 392, 5, 5));
          }
          if (globalRespObj.response.data.message[0].occupation == "X- Not Categorized") {
            page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(139, 414, 5, 5));
          }
        }

        page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(369, 353, 7, 7));

        page.graphics.drawString('' + formattedDate + '', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(135, 627, 100, 20));
        if (globalRespObj.response.data.message[0].parmAddrCity != "" && globalRespObj.response.data.message[0].parmAddrCity != null) {
          page.graphics.drawString('' + globalRespObj.response.data.message[0].parmAddrCity + '', PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(450, 627, 100, 20));
        }

        page.graphics.drawString(
          '' + signature + '',
          PdfStandardFont(PdfFontFamily.helvetica, 3),
          bounds: Rect.fromLTWH(170, 605, 85, 30),
        );

        page = document.pages[24];

        page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(174, 619, 7, 7));

        page.graphics.drawString(
          '' + signature + '',
          PdfStandardFont(PdfFontFamily.helvetica, 3),
          bounds: Rect.fromLTWH(120, 670, 85, 30),
        );

        page = document.pages[25];

        page.graphics.drawString('' + formattedDate + '', PdfStandardFont(PdfFontFamily.helvetica, 9), bounds: Rect.fromLTWH(103, 456, 100, 20));
        if (globalRespObj.response.data.message[0].parmAddrCity != "" && globalRespObj.response.data.message[0].parmAddrCity != null) {
          page.graphics.drawString('' + globalRespObj.response.data.message[0].parmAddrCity + '', PdfStandardFont(PdfFontFamily.helvetica, 9), bounds: Rect.fromLTWH(190, 456, 100, 20));
        }

        page.graphics.drawString(
          '' + signature + '',
          PdfStandardFont(PdfFontFamily.helvetica, 3),
          bounds: Rect.fromLTWH(430, 480, 85, 30),
        );

        // START Edited by sushant
        page = document.pages[26];
        //Draw the text
        String fatherSpouse = ' ' + globalRespObj.response.data.message[0].firstname2 + ' ' + globalRespObj.response.data.message[0].middlename2 + ' ' + globalRespObj.response.data.message[0].lastname2;
        page.graphics.drawString(
          fatherSpouse,
          PdfStandardFont(PdfFontFamily.helvetica, 11),
          bounds: Rect.fromLTWH(170, 120, 200, 15),
        );
        try {
          Uri uri11 = Uri.parse(uRl + "viewDocuments?email_id=" + globalRespObj.response.data.message[0].emailId + "&mobile_no=" + globalRespObj.response.data.message[0].mobileNo + "&type=photograph&pan=" + globalRespObj.response.data.message[0].pan + "&rendom=" + getRandomString(5));
          http.Response response11 = await http.get(
            uri11,
          );
          Uint8List data1 = response11.bodyBytes;
          data1.buffer.asUint8List(data1.offsetInBytes, data1.lengthInBytes);
          page.graphics.drawImage(
            PdfBitmap(data1),
            const Rect.fromLTWH(467, 120, 79, 85),
          );
        } catch (error) {
          print('error fetching photograph :$error');
        }
        if (globalRespObj.response.data.message[0].gender == "Male" && globalRespObj.response.data.message[0].gender != null) {
          page.graphics.drawImage(
            PdfBitmap(await _readDocumentData('right_tick.png', true)),
            const Rect.fromLTWH(126, 149, 4, 4),
          );
        } else {
          page.graphics.drawImage(
            PdfBitmap(await _readDocumentData('right_tick.png', true)),
            const Rect.fromLTWH(155, 149, 4, 4),
          );
        }

        if (globalRespObj.response.data.message[0].maritalstatus == "Single" && globalRespObj.response.data.message[0].maritalstatus != null) {
          page.graphics.drawImage(
            PdfBitmap(await _readDocumentData('right_tick.png', true)),
            const Rect.fromLTWH(240, 150, 4, 4),
          );
        } else {
          page.graphics.drawImage(
            PdfBitmap(await _readDocumentData('right_tick.png', true)),
            const Rect.fromLTWH(266, 150, 4, 4),
          );
        }

        page.graphics.drawString(
          ' ' + globalRespObj.response.data.message[0].dob + ' ',
          PdfStandardFont(PdfFontFamily.helvetica, 11),
          bounds: Rect.fromLTWH(349, 144, 100, 15),
        );

        if (globalRespObj.response.data.message[0].nationality == "Indian" && globalRespObj.response.data.message[0].nationality != null) {
          page.graphics.drawImage(
            PdfBitmap(await _readDocumentData('right_tick.png', true)),
            const Rect.fromLTWH(137, 163, 3, 3),
          );
        } else {
          page.graphics.drawImage(
            PdfBitmap(await _readDocumentData('right_tick.png', true)),
            const Rect.fromLTWH(166, 163, 3, 3),
          );

          page.graphics.drawString(
            ' ' + globalRespObj.response.data.message[0].nationality + ' ',
            PdfStandardFont(PdfFontFamily.helvetica, 11),
            bounds: Rect.fromLTWH(190, 154, 100, 15),
          );
        }

        page.graphics.drawImage(
          PdfBitmap(await _readDocumentData('right_tick.png', true)),
          const Rect.fromLTWH(162, 176, 4, 4),
        );

        page.graphics.drawString(
          ' ' + globalRespObj.response.data.message[0].pan + ' ',
          PdfStandardFont(PdfFontFamily.helvetica, 11),
          bounds: Rect.fromLTWH(113, 187, 100, 13),
        );

        if (obj.resAddr1 != "" && obj.resAddr1 != null && obj.resAddr2 != "" && obj.resAddr2 != null) {
          resAddress = ' ' + obj.resAddr1 + ' ' + obj.resAddr2 + ' ';
        }
        page.graphics.drawString(
          resAddress,
          PdfStandardFont(PdfFontFamily.helvetica, 11),
          bounds: Rect.fromLTWH(113, 255, 150, 15),
        );
        if (obj.resAddrCity != "" && obj.resAddrCity != null) {
          page.graphics.drawString(
            obj.resAddrCity,
            PdfStandardFont(PdfFontFamily.helvetica, 11),
            bounds: Rect.fromLTWH(152, 268, 150, 15),
          );
        }
        if (obj.resAddrState != "" && obj.resAddrState != null) {
          page.graphics.drawString(
            obj.resAddrState,
            PdfStandardFont(PdfFontFamily.helvetica, 11),
            bounds: Rect.fromLTWH(122, 2105, 150, 15),
          );
        }
        if (obj.resAddrPincode != "" && obj.resAddrPincode != null) {
          page.graphics.drawString(
            obj.resAddrPincode,
            PdfStandardFont(PdfFontFamily.helvetica, 11),
            bounds: Rect.fromLTWH(240, 285, 150, 15),
          );
        }
        page.graphics.drawString(
          obj.nationality,
          PdfStandardFont(PdfFontFamily.helvetica, 11),
          bounds: Rect.fromLTWH(467, 278, 150, 15),
        );

        page.graphics.drawString(
          obj.mobileNo,
          PdfStandardFont(PdfFontFamily.helvetica, 11),
          bounds: Rect.fromLTWH(145, 320, 150, 15),
        );

        page.graphics.drawString(
          obj.emailId,
          PdfStandardFont(PdfFontFamily.helvetica, 11),
          bounds: Rect.fromLTWH(145, 330, 250, 15),
        );
        String perAddress = '';
        if (obj.parmAddr1 != "" && obj.parmAddr1 != null && obj.parmAddr2 != "" && obj.parmAddr2 != null) {
          perAddress = ' ' + obj.parmAddr1 + ' ' + obj.parmAddr2 + ' ';
        }
        page.graphics.drawString(
          perAddress,
          PdfStandardFont(PdfFontFamily.helvetica, 11),
          bounds: Rect.fromLTWH(114, 400, 150, 15),
        );
        if (obj.parmAddrCity != "" && obj.parmAddrCity != null) {
          page.graphics.drawString(
            obj.parmAddrCity,
            PdfStandardFont(PdfFontFamily.helvetica, 11),
            bounds: Rect.fromLTWH(149, 420, 150, 15),
          );
        }
        if (obj.parmAddrState != "" && obj.parmAddrState != null) {
          page.graphics.drawString(
            obj.parmAddrState,
            PdfStandardFont(PdfFontFamily.helvetica, 11),
            bounds: Rect.fromLTWH(122, 430, 150, 15),
          );
        }
        if (obj.parmAddrPincode != "" && obj.parmAddrPincode != null) {
          page.graphics.drawString(
            obj.parmAddrPincode,
            PdfStandardFont(PdfFontFamily.helvetica, 11),
            bounds: Rect.fromLTWH(478, 420, 150, 15),
          );
        }
        page.graphics.drawString(
          obj.nationality,
          PdfStandardFont(PdfFontFamily.helvetica, 11),
          bounds: Rect.fromLTWH(365, 427, 150, 15),
        );

        if (globalRespObj.response.data.message[0].incomerange == "Below 1 Lakh" && globalRespObj.response.data.message[0].incomerange != null) {
          page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(256, 509, 4, 4));
        } else if (globalRespObj.response.data.message[0].incomerange == "1 Lakh TO 5 Lakh" && globalRespObj.response.data.message[0].incomerange != null) {
          page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(313, 509, 4, 4));
        } else if (globalRespObj.response.data.message[0].incomerange == "5 Lakh TO 10 Lakh" && globalRespObj.response.data.message[0].incomerange != null) {
          page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(355, 509, 4, 4));
        } else if (globalRespObj.response.data.message[0].incomerange == "10 Lakh TO 25 Lakh" && globalRespObj.response.data.message[0].incomerange != null) {
          page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(401, 509, 4, 4));
        } else if (globalRespObj.response.data.message[0].incomerange == "Above 25 Lakh" && globalRespObj.response.data.message[0].incomerange != null) {
          page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(450, 509, 4, 4));
        }

        page.graphics.drawString(
          '' + formattedDate + " ",
          PdfStandardFont(PdfFontFamily.helvetica, 11),
          bounds: Rect.fromLTWH(375, 522, 100, 20),
        );
        if (obj.resAddrCity != "" && obj.resAddrCity != null) {
          page.graphics.drawString(
            obj.resAddrCity,
            PdfStandardFont(PdfFontFamily.helvetica, 11),
            bounds: Rect.fromLTWH(110, 650, 100, 20),
          );
        }
        page.graphics.drawString(
          '' + formattedDate + '',
          PdfStandardFont(PdfFontFamily.helvetica, 11),
          bounds: Rect.fromLTWH(330, 650, 100, 20),
        );

        if (globalRespObj.response.data.message[0].occupation != "" || globalRespObj.response.data.message[0].occupation != null) {
          if (globalRespObj.response.data.message[0].occupation == "Private Sector") {
            page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(105, 556, 4, 4));
          }
          if (globalRespObj.response.data.message[0].occupation == "Public Sector") {
            page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(190, 556, 4, 4));
          }
          if (globalRespObj.response.data.message[0].occupation == "Government Sector") {
            page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(251, 556, 4, 4));
          }
          if (globalRespObj.response.data.message[0].occupation == "B-Business") {
            page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(334, 556, 4, 4));
          }
          if (globalRespObj.response.data.message[0].occupation == "Professional") {
            page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(383, 556, 4, 4));
          }
          if (globalRespObj.response.data.message[0].occupation == "Agriculturist") {
            page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(440, 556, 4, 4));
          }
          if (globalRespObj.response.data.message[0].occupation == "Retired") {
            page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(497, 556, 4, 4));
          }
          if (globalRespObj.response.data.message[0].occupation == "Housewife") {
            page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(104, 567, 4, 4));
          }
          if (globalRespObj.response.data.message[0].occupation == "Student") {
            page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(158, 567, 4, 4));
          }
          if (globalRespObj.response.data.message[0].occupation == "Forex dealer") {
            page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(202, 567, 4, 4));
          }
          if (globalRespObj.response.data.message[0].occupation == "O-Other") {
            page.graphics.drawImage(PdfBitmap(await _readDocumentData('right_tick.png', true)), const Rect.fromLTWH(251, 567, 4, 4));
          }
        }

        if (globalRespObj.response.data.message[0].action == "Past Regulatory Action") {
          page.graphics.drawImage(
            PdfBitmap(await _readDocumentData('right_tick.png', true)),
            const Rect.fromLTWH(299, 579, 4, 4),
          );
        } else {
          page.graphics.drawImage(
            PdfBitmap(await _readDocumentData('right_tick.png', true)),
            const Rect.fromLTWH(199, 579, 4, 4),
          );
        }

        page.graphics.drawString(
          '' + signature + '',
          PdfStandardFont(PdfFontFamily.helvetica, 3),
          bounds: Rect.fromLTWH(430, 640, 85, 30),
        );

        page = document.pages[29];
        if (obj.resAddrCity != "" && obj.resAddrCity != null) {
          page.graphics.drawString(
            obj.resAddrCity,
            PdfStandardFont(PdfFontFamily.helvetica, 11),
            bounds: Rect.fromLTWH(100, 270, 100, 20),
          );
        }
        page.graphics.drawString(
          formattedDate,
          PdfStandardFont(PdfFontFamily.helvetica, 11),
          bounds: Rect.fromLTWH(274, 270, 100, 20),
        );

        page.graphics.drawString(
          '' + signature + '',
          PdfStandardFont(PdfFontFamily.helvetica, 3),
          bounds: Rect.fromLTWH(140, 337, 85, 30),
        );

        page.graphics.drawString(
          '' + signature + '',
          PdfStandardFont(PdfFontFamily.helvetica, 3),
          bounds: Rect.fromLTWH(125, 730, 85, 30),
        );
        // Nominee details
        page = document.pages[28];

        page.graphics.drawString(
          obj.nomineeName,
          PdfStandardFont(PdfFontFamily.helvetica, 11),
          bounds: Rect.fromLTWH(180, 257, 100, 20),
        );

        var nomineeAddress1 = obj.nomAddr1;
        page.graphics.drawString(nomineeAddress1, PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(175, 310, 100, 18));

        var nomineeAddress2 = obj.nomAddr2;
        page.graphics.drawString(nomineeAddress2, PdfStandardFont(PdfFontFamily.helvetica, 11), bounds: Rect.fromLTWH(175, 323, 300, 18));

        var nomineeState = obj.nomAddrState;
        page.graphics.drawString(nomineeState, PdfStandardFont(PdfFontFamily.helvetica, 10), bounds: Rect.fromLTWH(175, 350, 100, 18));

        var nomineeCity = obj.nomAddrCity;
        page.graphics.drawString(nomineeCity, PdfStandardFont(PdfFontFamily.helvetica, 10), bounds: Rect.fromLTWH(175, 361, 100, 18));

        var nomineePin = obj.nomAddrPincode;
        page.graphics.drawString(nomineePin, PdfStandardFont(PdfFontFamily.helvetica, 10), bounds: Rect.fromLTWH(175, 371, 100, 18));

        var nomineeEmail = obj.nomineeEmail;
        page.graphics.drawString(nomineeEmail, PdfStandardFont(PdfFontFamily.helvetica, 10), bounds: Rect.fromLTWH(175, 436, 200, 18));

        var _chosenValue3 = "";
        if (obj.nomineeRelation != "" && obj.nomineeRelation != "null") {
          switch (obj.nomineeRelation) {
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
        }
        page.graphics.drawString(
          _chosenValue3,
          PdfStandardFont(PdfFontFamily.helvetica, 11),
          bounds: Rect.fromLTWH(180, 455, 100, 20),
        );

        page = document.pages[30];

        page.graphics.drawString(
          formattedDate,
          PdfStandardFont(PdfFontFamily.helvetica, 11),
          bounds: Rect.fromLTWH(170, 100, 100, 20),
        );

        page.graphics.drawString(
          obj.mobileNo,
          PdfStandardFont(PdfFontFamily.helvetica, 11),
          bounds: Rect.fromLTWH(170, 220, 100, 20),
        );

        page.graphics.drawString(
          obj.emailId,
          PdfStandardFont(PdfFontFamily.helvetica, 11),
          bounds: Rect.fromLTWH(170, 234, 200, 20),
        );

        page.graphics.drawString(
          '' + signature + '',
          PdfStandardFont(PdfFontFamily.helvetica, 3),
          bounds: Rect.fromLTWH(110, 700, 85, 30),
        );

        page = document.pages[31];
        page.graphics.drawString(
          '' + signature + '',
          PdfStandardFont(PdfFontFamily.helvetica, 3),
          bounds: Rect.fromLTWH(170, 325, 85, 30),
        );

        page.graphics.drawString(
          '' + signature + '',
          PdfStandardFont(PdfFontFamily.helvetica, 3),
          bounds: Rect.fromLTWH(130, 450, 85, 30),
        );

        page = document.pages[32];

        page.graphics.drawString(
          formattedDate,
          PdfStandardFont(PdfFontFamily.helvetica, 11),
          bounds: Rect.fromLTWH(510, 51, 100, 20),
        );

        page.graphics.drawString(
          obj.mobileNo,
          PdfStandardFont(PdfFontFamily.helvetica, 11),
          bounds: Rect.fromLTWH(100, 351, 100, 20),
        );

        page.graphics.drawString(
          obj.emailId,
          PdfStandardFont(PdfFontFamily.helvetica, 11),
          bounds: Rect.fromLTWH(100, 373, 200, 20),
        );

        page.graphics.drawString(
          obj.emailId,
          PdfStandardFont(PdfFontFamily.helvetica, 11),
          bounds: Rect.fromLTWH(130, 742, 200, 15),
        );

        page.graphics.drawString(
          '' + signature + '',
          PdfStandardFont(PdfFontFamily.helvetica, 3),
          bounds: Rect.fromLTWH(120, 300, 85, 30),
        );

        page.graphics.drawString(
          '' + signature + '',
          PdfStandardFont(PdfFontFamily.helvetica, 3),
          bounds: Rect.fromLTWH(485, 765, 85, 30),
        );

        page = document.pages[50];

        page.graphics.drawString(
          obj.emailId,
          PdfStandardFont(PdfFontFamily.helvetica, 11),
          bounds: Rect.fromLTWH(235, 400, 200, 20),
        );

        page.graphics.drawString(
          obj.pan,
          PdfStandardFont(PdfFontFamily.helvetica, 11),
          bounds: Rect.fromLTWH(130, 567, 100, 20),
        );

        page.graphics.drawString(
          formattedDate,
          PdfStandardFont(PdfFontFamily.helvetica, 11),
          bounds: Rect.fromLTWH(120, 600, 200, 20),
        );
        if (obj.resAddrCity != "" && obj.resAddrCity != null) {
          page.graphics.drawString(
            obj.resAddrCity,
            PdfStandardFont(PdfFontFamily.helvetica, 11),
            bounds: Rect.fromLTWH(120, 612, 200, 15),
          );
        }

        page.graphics.drawString(
          '' + signature + '',
          PdfStandardFont(PdfFontFamily.helvetica, 3),
          bounds: Rect.fromLTWH(140, 690, 85, 30),
        );

        //END by sushant
        // Nominee proof
        try {
          Uri uri1 = Uri.parse(uRl + "viewDocuments?email_id=" + globalRespObj.response.data.message[0].emailId + "&mobile_no=" + globalRespObj.response.data.message[0].mobileNo + "&type=nominee&pan=" + globalRespObj.response.data.message[0].pan + "&rendom=" + getRandomString(5));
          http.Response response1 = await http.get(
            uri1,
          );
          Uint8List nomineeId = response1.bodyBytes;

          nomineeId.buffer.asUint8List(nomineeId.offsetInBytes, nomineeId.lengthInBytes);
          document.pages.add().graphics.drawImage(PdfBitmap(nomineeId), const Rect.fromLTWH(50, 25, 400, 800));
        } catch (error) {
          print('error fetching nomineeId:$error');
        }
        // Nominee proof
        try {
          Uri uri1 = Uri.parse(uRl + "viewDocuments?email_id=" + globalRespObj.response.data.message[0].emailId + "&mobile_no=" + globalRespObj.response.data.message[0].mobileNo + "&type=photograph&pan=" + globalRespObj.response.data.message[0].pan + "&rendom=" + getRandomString(5));
          http.Response response1 = await http.get(
            uri1,
          );
          Uint8List photograph = response1.bodyBytes;

          photograph.buffer.asUint8List(photograph.offsetInBytes, photograph.lengthInBytes);
          document.pages.add().graphics.drawImage(PdfBitmap(photograph), const Rect.fromLTWH(50, 25, 400, 800));
        } catch (error) {
          print('error fetching photograph:$error');
        }
        // add pan image
        try {
          Uri uri2 = Uri.parse(uRl + "viewDocuments?email_id=" + globalRespObj.response.data.message[0].emailId + "&mobile_no=" + globalRespObj.response.data.message[0].mobileNo + "&type=pancard&pan=" + globalRespObj.response.data.message[0].pan + "&rendom=" + getRandomString(5));
          http.Response response2 = await http.get(
            uri2,
          );
          Uint8List pan = response2.bodyBytes;
          pan.buffer.asUint8List(pan.offsetInBytes, pan.lengthInBytes);
          document.pages.add().graphics.drawImage(PdfBitmap(pan), const Rect.fromLTWH(50, 25, 400, 800));
        } catch (error) {
          print('error fetching pancard :$error');
        }
        // add pan image
        try {
          // add addhar image
          Uri uri3 = Uri.parse(uRl + "viewDocuments?email_id=" + globalRespObj.response.data.message[0].emailId + "&mobile_no=" + globalRespObj.response.data.message[0].mobileNo + "&type=addressproof&pan=" + globalRespObj.response.data.message[0].pan + "&rendom=" + getRandomString(5));
          http.Response response3 = await http.get(
            uri3,
          );
          Uint8List addhar = response3.bodyBytes;
          addhar.buffer.asUint8List(addhar.offsetInBytes, addhar.lengthInBytes);
          document.pages.add().graphics.drawImage(PdfBitmap(addhar), const Rect.fromLTWH(50, 25, 400, 800));
        } catch (error) {
          print('error fetching addressproof :$error');
        }
        // add pan image

        // add pan image

        // add signature image
        try {
          Uri uri = Uri.parse(uRl + "viewDocuments?email_id=" + globalRespObj.response.data.message[0].emailId + "&mobile_no=" + globalRespObj.response.data.message[0].mobileNo + "&type=signature&pan=" + globalRespObj.response.data.message[0].pan + "&rendom=" + getRandomString(5));
          response = await http.get(
            uri,
          );
          var signaturepic = response.bodyBytes;
          signaturepic.buffer.asUint8List(signaturepic.offsetInBytes, signaturepic.lengthInBytes);
          document.pages.add().graphics.drawImage(PdfBitmap(signaturepic), const Rect.fromLTWH(50, 25, 400, 800));
        } catch (error) {
          print('error fetching signature :$error');
        }

        // Two pdf will merge from backend

        // // add bankproof image
        // try {
        //   Uri uri4 = Uri.parse(uRl +
        //       "images/" +
        //       globalRespObj.response.data.message[0].pan +
        //       "bankproof" +
        //       globalRespObj.response.data.message[0].uniqueId +
        //       ".pdf");
        //   http.Response response4 = await http.get(
        //     uri4,
        //   );
        //   Uint8List bankproof = response4.bodyBytes;
        //   bankproof.buffer
        //       .asUint8List(bankproof.offsetInBytes, bankproof.lengthInBytes);
        //   document.pages.add().graphics.drawImage(
        //       PdfBitmap(bankproof), const Rect.fromLTWH(50, 25, 400, 800));
        // } catch (error) {
        //   print('error fetching bankproof :$error');
        // }
        // // add incomeproof image
        // try {
        //   Uri uri5 = Uri.parse(uRl +
        //       "images/" +
        //       globalRespObj.response.data.message[0].pan +
        //       "incomeproof" +
        //       globalRespObj.response.data.message[0].uniqueId +
        //       ".pdf");
        //   http.Response response5 = await http.get(
        //     uri5,
        //   );

        //   Uint8List incomeproof = response5.bodyBytes;

        //   document.pages.add().graphics.drawImage(
        //       PdfBitmap(incomeproof), const Rect.fromLTWH(50, 25, 400, 800));
        // } catch (error) {
        //   print('error fetching incomeproof :$error');
        // }
        //    add incomeproof image

        //Save the PDF document
        final List<int> bytes = document.save();
        //Dispose the document.
        document.dispose();
        // AppConfig().dismissLoaderDialog(context); //// dismiss loader
        // await FileSaveHelper.saveAndLaunchFile(
        //     bytes, globalRespObj.response.data.message[0].pan + '.pdf');
        var flag = await sendKycPDFDocument(context, "large", bytes, "", globalRespObj.response.data.message[0].bankproof, globalRespObj.response.data.message[0].incomeproof, globalRespObj.response.data.message[0].pan);
        //Save and launch file.
        if (flag == "0") {
          dismissLoaderDialog(context); //// dismiss loader
          EsignBloc esignbloc = new EsignBloc();
          esignbloc.sendPDF(context, screenName, globalRespObj.response.data.message[0].emailId, globalRespObj.response.data.message[0].mobileNo, globalRespObj.response.data.message[0].pan);
          // try {
          //   Uri uri4 = Uri.parse(uRl +
          //       "images/" +
          //       globalRespObj.response.data.message[0].pan +
          //       "FinalEKycDocument.pdf");
          //   http.Response response4 = await http.get(
          //     uri4,
          //   );
          //   Uint8List bankproof = response4.bodyBytes;
          //   bankproof.buffer
          //       .asUint8List(bankproof.offsetInBytes, bankproof.lengthInBytes);

          //   await FileSaveHelper.saveAndLaunchFile(
          //       bankproof, globalRespObj.response.data.message[0].pan + '.pdf');
          //   dismissLoaderDialog(context); //// dismiss loader
          //   showAlert(context, "Download completed successfully", screenName);
          // } catch (error) {
          //   dismissLoaderDialog(context); //// dismiss loader
          //   showAlert(context, "Try again" + error.toString(), screenName);
          //   print('error fetching kycdocument :$error');
          // }
        } else {
          dismissLoaderDialog(context); //// dismiss loader
          showAlert(context, "Esign authorization failed. ", screenName);
        }
      }
    } else {
      dismissLoaderDialog(context); //// dismiss loader
    }
  }

  sendKycPDFDocument(
    BuildContext context,
    String screenName,
    List<int> selectedFile,
    String mobilevideopath,
    String bank_proof,
    String income_proof,
    String pan,
  ) async {
    try {
      // AppConfig().showLoaderDialog(context); // show loader

      final obj = await sendKycPDFDocumentRequest(screenName, selectedFile, mobilevideopath);
      // AppConfig().dismissLoaderDialog(context); //// dismiss loader
      if (obj == 0) {
        ResponseModel obj = await pdfMergeRequest(bank_proof, income_proof, pan);

        if (obj.response.errorCode == "0") {
          return "0";
        } else if (obj.response.errorCode == "1") {
          showAlert(context, "Esign authorization failed. ", screenName);
          return "1";
        }
      } else if (obj == 1) {
        showAlert(context, "Esign authorization failed. ", screenName);
        return "1";
      }
    } catch (exception) {
      // AppConfig().dismissLoaderDialog(context); //// dismiss loader
      print("no data" + exception.toString());

      showAlert(context, "Try again" + exception.toString(), screenName);
      return "1";
    }
  }

  Future<int> sendKycPDFDocumentRequest(String screenname, List<int> selectedFile, String mobilevideopath) async {
    // FilePickerResult results = await FilePicker.platform.pickFiles();
    // File result = File(results.files, "pancard");

    var userPanNumber;

    var globalRespObj = LoginRepository.loginDetailsResObjGlobal;
    if (PanDetailRepository.userPanNumber != null && PanDetailRepository.userPanNumber != "") {
      userPanNumber = PanDetailRepository.userPanNumber;
    } else {
      if (globalRespObj != null) {
        if (globalRespObj.response.errorCode == "0") {
          userPanNumber = globalRespObj.response.data.message[0].pan;
        }
      }
    }
    var url = AppConfig().url + "uploadKYCPdFFile";

    Uri uri = Uri.parse(url);
    var request = http.MultipartRequest('POST', uri);

    //request.headers.addAll(requestHeaders);
    // request.headers["<custom header>"] = "content";
    request.fields['email_id'] = LoginRepository.loginEmailId;
    request.fields['mobile_no'] = LoginRepository.loginMobileNo;
    request.fields['pan'] = userPanNumber;
    request.fields['imageName'] = userPanNumber + "KycDocument";

    if (screenname.contains("large")) {
      // for web video upload
      // print("pdf large bytes data" + selectedFile.toString());

      request.files.add(
        await http.MultipartFile.fromBytes('pdf_path', selectedFile, contentType: new MediaType('application', 'octet-stream'), filename: 'test.pdf'),
      );

      // request.files.add(new http.MultipartFile(
      //     "pdf_path", selectedFile.readStream, selectedFile.size,
      //     filename: selectedFile.name));
    } else {
      // for mobile video upload
      List<int> imageData = File(mobilevideopath).readAsBytesSync();
      request.files.add(
        await http.MultipartFile.fromBytes('pdf_path', imageData, contentType: new MediaType('application', 'octet-stream'), filename: 'test.pdf'),
      );
    }

    final uploadResponse = await request.send();
    final response = await http.Response.fromStream(uploadResponse);

    var flag = 0;
    if (response.statusCode == 200) {
      flag = 0;
      return flag;
    } else {
      flag = 1;
      return flag;
    }
  }

  Future<dynamic> showAlert(BuildContext ctx, String msg, String screenname) {
    return showDialog(
      context: ctx,
      builder: (ctx) => AlertDialog(
        title: Text("E-KYC"),
        content: Text(msg),
        actions: <Widget>[
          TextButton(
            onPressed: () {
              Navigator.of(ctx).pop();
              if (msg == "Video uploaded") {
                if (screenname.toString().contains("small")) {
                  HomeScreenSmall.screensStreamSmall.sink.add(Ekycscreenamesmall.esigndetailscreen);
                } else {
                  HomeScreenLarge.screensStreamLarge.sink.add(Ekycscreenamelarge.esigndetailscreen);
                }
              }
            },
            child: Text("OK"),
          ),
        ],
      ),
    );
  }

  pdfMergeRequest(String bank_proof, String income_proof, String pan) async {
    final url = AppConfig().url;
    var apiName = url + "pdfMerger?bank_proof=" + bank_proof + "&income_proof=" + income_proof + "&pan=" + pan;

    final response = await http.get(
      Uri.parse(apiName),
    );

    if (response.statusCode == 200) {
      final loginResponseObj = ResponseModel.fromJson(json.decode(response.body));

      return loginResponseObj;
    } else {
      print(response.reasonPhrase);
    }
  }

  Future<void> DownloadPdf(BuildContext context, String screenName, LoginUserDetailModelResponse loginResponseObj) async {
    try {
      Uri uri4 = Uri.parse(uRl + "downloadUserDoc?pan=" + globalRespObj.response.data.message[0].pan + "&downloadType=document");
      http.Response response4 = await http.get(
        uri4,
      );
      Uint8List bankproof = response4.bodyBytes;
      bankproof.buffer.asUint8List(bankproof.offsetInBytes, bankproof.lengthInBytes);

      await FileSaveHelper.saveAndLaunchFile(bankproof, globalRespObj.response.data.message[0].pan + '.pdf');
      //// dismiss loader
      //showAlert(context, "Download completed successfully", screenName);
      ResponseModel flag = await sendDatatoCDSL(context, loginResponseObj);
      dismissLoaderDialog(context);
      showAlert(context, "KRA request have been submitted with below response\n" + "\n" + "Error code:- " + flag.response.errorCode + "\n" + "Message:- " + flag.response.data.message, screenName);
    } catch (error) {
      dismissLoaderDialog(context); //// dismiss loader
      showAlert(context, "Try again" + error.toString(), screenName);
      print('error fetching kycdocument :$error');
    }
  }

  sendDatatoCDSL(BuildContext context, LoginUserDetailModelResponse loginResponseObj) async {
    final jsonRequest = json.encode(loginResponseObj);
    var headers = {'Content-Type': 'application/json'};
    var apiName = AppConfig().url + "SendDatatoCDSLKYC";
    var request = http.Request('POST', Uri.parse(apiName));
    request.body = jsonRequest;
    request.headers.addAll(headers);

    http.StreamedResponse response = await request.send();

    if (response.statusCode == 200) {
      final responseData = await response.stream.bytesToString();
      print('Response Data :- ${responseData.toString()}');
      var loginResponseObj = ResponseModel.fromJson(
        json.decode(
          responseData.toString(),
        ),
      );
      print(loginResponseObj.response.data.message);

      return loginResponseObj;
    } else {
      return loginResponseObj;
    }
  }
}
