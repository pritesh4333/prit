import 'dart:math';
import 'dart:typed_data';
import 'package:e_kyc/Login/UI/Configuration/AppConfig.dart';
import 'package:e_kyc/Login/UI/Login/repository/LoginRepository.dart';
import 'package:e_kyc/Login/UI/PanDetailsScreen/repository/PanDetailRepository.dart';
import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';
// import 'package:flutter_vant_kit/main.dart';
import 'package:http/http.dart' as http;
import 'package:http_parser/http_parser.dart';

class DocumentRepository {
  var _chars = 'AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz1234567890';
  Random _rnd = Random();
  // uploadFile() async {
  //   var urls = AppConfig().url + "uploadDocuments";
  //   var postUri = Uri.parse(urls);
  //   var request = new http.MultipartRequest("POST", postUri);
  //   request.fields['user'] = 'blah';
  //   Uri url = Uri.parse(
  //       'http://127.0.0.1:57904/a811cc2a-1f4c-4a7c-b048-05189fd60387');
  //   request.files.add(
  //     new http.MultipartFile.fromBytes(
  //       'file',
  //       await File.fromUri(url).readAsBytes(),
  //     ),
  //   );

  //   request.send().then((response) {
  //     if (response.statusCode == 200) print("Uploaded!");
  //   });
  // }

  // SaveDocumentDetailsAPI(
  //     String email, String mobile, String type, String imageName) async {
  //   var url = AppConfig().url + "uploadDocuments";
  //   print('Request URl :-' + url);
  //   var request = http.MultipartRequest('POST', Uri.parse(url));
  //   request.fields.addAll({
  //     'email_id': email,
  //     'mobile_no': mobile,
  //     'type': type,
  //     'imageName': imageName
  //   });
  //   // request.files
  //   //     .add(await http.MultipartFile.fromPath('image_path', imagepath.path));
  //   Uri urls = Uri.parse(
  //       'http://127.0.0.1:57904/a811cc2a-1f4c-4a7c-b048-05189fd60387');
  //   // request.files.add(
  //   //   new http.MultipartFile.fromBytes(
  //   //     'file',
  //   //     await File.fromUri(urls).readAsBytes(),
  //   //   ),
  //   // );
  //   // request.files.add(await http.MultipartFile.fromPath('image', filepath));

  //   http.StreamedResponse response = await request.send();

  //   if (response.statusCode == 200) {
  //     final responseData = await response.stream.bytesToString();
  //     print('Response Data :- ${responseData.toString()}');
  //     final loginResponseObj = BankResponseModel.fromJson(
  //       json.decode(
  //         responseData.toString(),
  //       ),
  //     );
  //     return loginResponseObj;
  //   } else {
  //     print(response.reasonPhrase);
  //   }
  // }

  Future<int> saveImage(String docType, PlatformFile PlattobjFile, List<int> objFile, String proofType, String uniqueId, String extension) async {
    // FilePickerResult results = await FilePicker.platform.pickFiles();
    // File result = File(results.files, "pancard");

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

    var url = AppConfig().url + "uploadDocuments";

    Uri uri = Uri.parse(url);
    var request = http.MultipartRequest('POST', uri);
    // request.headers["<custom header>"] = "content";
    request.fields['email_id'] = LoginRepository.loginEmailId;
    request.fields['mobile_no'] = LoginRepository.loginMobileNo;
    request.fields['type'] = docType;
    request.fields['imageName'] = panDetails + docType + uniqueId;
    request.fields['prooftype'] = proofType;
    request.fields['extension'] = extension;
    request.fields['pan'] = panDetails;
    request.fields['uploadType']='self';

    if (objFile == null) {
      request.files.add(new http.MultipartFile("image_path", PlattobjFile.readStream, PlattobjFile.size, filename: PlattobjFile.name));
    } else {
      request.files.add(
        new http.MultipartFile.fromBytes('image_path', objFile, filename: 'dummy.png'),
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
/*
    request.send().then((response) {
      http.Response.fromStream(response).then(
        (onValue) {
          try {
            // get your response here...
            // print(response.toString());
            var flag = 0;
            if (response.statusCode == 200) {
              flag = 0;
              return flag;
            } else {
              flag = 1;
              return flag;
            }
          } catch (e) {
            // handle exeption
            print(e.toString());
            return 1;
          }
        },
      );
    });
    */
  }

  String previewImage(BuildContext context, String imageType) {
    var url = AppConfig().url;
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
    String finalUrl = url + 'viewDocuments?email_id=' + LoginRepository.loginEmailId + '&mobile_no=' + LoginRepository.loginMobileNo + '&type=' + imageType + '&pan=' + panDetails + '&rendom=' + getRandomString(5);
    print("latest change  " + finalUrl);

    print(finalUrl);
    evictImage(finalUrl);
    // List<Widget> imagesDemo = [Image.network(finalUrl)];
    return finalUrl;
  }

  String getRandomString(int length) => String.fromCharCodes(Iterable.generate(length, (_) => _chars.codeUnitAt(_rnd.nextInt(_chars.length))));

  void evictImage(String url) {
    final NetworkImage provider = NetworkImage(url);
    provider.evict().then<void>((bool success) {
      if (success) debugPrint('removed image!');
    });
  }

  uploadFileToServer(String email, String mobile, String type, String imageName, Uint8List imagepath) async {
    var url = AppConfig().url + "uploadDocuments";
    var request = new http.MultipartRequest("POST", Uri.parse(url));

    request.fields['email_id'] = email;
    request.fields['mobile_no'] = mobile;
    request.fields['type'] = type;
    request.fields['imageName'] = imageName;

    // request.files.add(
    //   new http.MultipartFile.fromBytes(
    //     'image_path',
    //     imagepath,
    //     contentType:
    //   ),
    // );
    // int length = imagepath.length;
    request.files.add(
      http.MultipartFile.fromBytes(
        'image_path',
        imagepath,
        contentType: new MediaType('image', 'png'),
      ),
    );
    request.send().then((response) {
      http.Response.fromStream(response).then((onValue) {
        try {
          // get your response here...
          print(response.toString());
        } catch (e) {
          // handle exeption
          print(e.toString());
        }
      });
    });
  }
}
