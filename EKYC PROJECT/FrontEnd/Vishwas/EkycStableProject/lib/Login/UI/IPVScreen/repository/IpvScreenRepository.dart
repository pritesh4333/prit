import 'dart:io';
import 'package:e_kyc/Login/UI/Configuration/AppConfig.dart';
import 'package:e_kyc/Login/UI/Login/repository/LoginRepository.dart';
import 'package:e_kyc/Login/UI/PanDetailsScreen/repository/PanDetailRepository.dart';
// import 'package:file_picker/file_picker.dart';
import 'package:http/http.dart' as http;
import 'package:http_parser/http_parser.dart';

class IpvScreenRepository {
  Future<int> saveIPVvideo(String screenname, List<int> selectedFile, String mobilevideopath) async {
    // FilePickerResult results = await FilePicker.platform.pickFiles();
    // File result = File(results.files, "pancard");

    var ipvDetails;

    var globalRespObj = LoginRepository.loginDetailsResObjGlobal;
    if (PanDetailRepository.userPanNumber != null && PanDetailRepository.userPanNumber != "") {
      ipvDetails = PanDetailRepository.userPanNumber;
    } else {
      if (globalRespObj != null) {
        if (globalRespObj.response.errorCode == "0") {
          ipvDetails = globalRespObj.response.data.message[0].pan;
        }
      }
    }
    var url = AppConfig().url + "uploadIpvVideo";

    Uri uri = Uri.parse(url);
    var request = http.MultipartRequest('POST', uri);
    // request.headers["<custom header>"] = "content";
    request.fields['email_id'] = LoginRepository.loginEmailId;
    request.fields['mobile_no'] = LoginRepository.loginMobileNo;
    request.fields['imageName'] = ipvDetails + "IPV";
    request.fields['pan'] = ipvDetails;
    request.fields['latitude'] = LoginRepository.latitude;
    request.fields['longitude'] = LoginRepository.longitude;
    var headers = {'Content-Type': 'application/json'};
    request.headers.addAll(headers);
    if (screenname.contains("large")) {
      // for web video upload
      print("large chrome webview");
      request.files.add(
        await http.MultipartFile.fromBytes('video', selectedFile, contentType: new MediaType('application', 'octet-stream'), filename: 'test.mp4'),
      );
    } else {
      // for mobile video upload
      print("mobile webview");
      // List<int> imageData = File(mobilevideopath).readAsBytesSync();
      // request.files.add(
      //   await http.MultipartFile.fromBytes('video', imageData,
      //       contentType: new MediaType('application', 'octet-stream'),
      //       filename: 'test.mp4'),
      // );
      request.files.add(
        await http.MultipartFile.fromBytes('video', selectedFile, contentType: new MediaType('application', 'octet-stream'), filename: 'test.mp4'),
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

    // request.send().then((response) {
    //   http.Response.fromStream(response).then(
    //     (onValue) {
    //       try {
    //         // get your response here...
    //         // print(response.toString());
    //         var flag = 0;
    //         if (response.statusCode == 200) {
    //           flag = 0;
    //           return flag;
    //         } else {
    //           flag = 1;
    //           return flag;
    //         }
    //       } catch (e) {
    //         // handle exeption
    //         print(e.toString());
    //         return 1;
    //       }
    //     },
    //   );
    // });
  }
}
