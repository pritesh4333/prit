import 'dart:convert';

import 'package:e_kyc/Login/UI/Configuration/AppConfig.dart';
import 'package:e_kyc/Login/UI/Login/repository/LoginRepository.dart';
import 'package:e_kyc/Login/UI/PanDetailsScreen/repository/PanDetailRepository.dart';
import 'package:e_kyc/Login/UI/PersonalDetailsScreen/bloc/PersonalDetailsBloc.dart';
import 'package:e_kyc/Utilities/ResponseModel.dart';
import 'package:file_picker/file_picker.dart';
import 'package:http/http.dart' as http;
import 'package:e_kyc/Login/UI/PersonalDetailsScreen/model/PersonalDetailsRequestModel.dart';

/*
0 - NSE CASH,
1 - NSE FNO,
2 - NSE CURRENCY,
3 - BSE CASH,
4 = BSE  FNO,
5 - BSE CURRENCY,
6 - MCX COMMODITY,
7 - NCDEX COMMODITY,
*/

class PersonalDetailsRepository {
  // 'ePersonalDetails' [API]
  callingPersonalDetailsAPI(String email, String mobile, PersonalDetailsBloc personalDetailsBloc) async {
    PersonaDetailRequestData personaDetailRequestData = new PersonaDetailRequestData();

    personaDetailRequestData.emailId = email;
    personaDetailRequestData.mobileNo = mobile;
    personaDetailRequestData.nseCash = personalDetailsBloc.nseCashCheckBox ? "0" : "";
    personaDetailRequestData.nseFo = personalDetailsBloc.nseFNOCheckBox ? "1" : "";
    personaDetailRequestData.nseCurrency = personalDetailsBloc.nseCurrencyCheckBox ? "2" : "";
    personaDetailRequestData.bseCash = personalDetailsBloc.bseCashCheckBox ? "3" : "";
    personaDetailRequestData.bseFo = personalDetailsBloc.bseFNOCheckBox ? "4" : "";
    personaDetailRequestData.bseCurrency = personalDetailsBloc.bseCurencyCheckBox ? "5" : "";
    personaDetailRequestData.mcxCommodity = personalDetailsBloc.mcxCommodityCheckBox ? "6" : "";
    personaDetailRequestData.ncdexCommodity = personalDetailsBloc.ncdexComodityCheckBox ? "7" : "";
    personaDetailRequestData.nationality = personalDetailsBloc.nationality;

    String gender = "";
    if (personalDetailsBloc.genderCheck == 0) {
      gender = "Male";
    } else if (personalDetailsBloc.genderCheck == 1) {
      gender = "Female";
    } else {
      gender = "Other";
    }
    personaDetailRequestData.gender = gender;
    // personalDetailsBloc.genderCheck == 0 ? "Male" : "Female";
    personaDetailRequestData.firstname1 = personalDetailsBloc.firstNameTextController1.text;
    personaDetailRequestData.middlename1 = personalDetailsBloc.middleNameTextController1.text;
    personaDetailRequestData.lastname1 = personalDetailsBloc.lastNameTextController1.text;

    // Nominee Relation
    personaDetailRequestData.nomineeName = personalDetailsBloc.nomineeNameTextController.text;

    String nomineeRelation = personalDetailsBloc.nomineeRelation;
    switch (nomineeRelation) {
      case "Father":
        personaDetailRequestData.nomineeRelation = "0";
        break;
      case "Mother":
        personaDetailRequestData.nomineeRelation = "1";
        break;
      case "Wife":
        personaDetailRequestData.nomineeRelation = "2";
        break;
      case "Brother":
        personaDetailRequestData.nomineeRelation = "3";
        break;
      case "Sister":
        personaDetailRequestData.nomineeRelation = "4";
        break;
      case "Other":
        personaDetailRequestData.nomineeRelation = "5";
        break;
      default:
        break;
    }

    personaDetailRequestData.resAddr1 = personalDetailsBloc.resAddressLine1TextController.text;
    personaDetailRequestData.resAddr2 = personalDetailsBloc.resAddressLine2TextController.text;
    personaDetailRequestData.resAddrState = personalDetailsBloc.resStateTextController.text;
    personaDetailRequestData.resAddrCity = personalDetailsBloc.resCityTextController.text;
    personaDetailRequestData.resAddrPincode = personalDetailsBloc.resPinCodeTextController.text;

    personaDetailRequestData.parmAddr1 = personalDetailsBloc.perAddressLine1TextController.text;
    personaDetailRequestData.parmAddr2 = personalDetailsBloc.perAddressLine2TextController.text;
    personaDetailRequestData.parmAddrState = personalDetailsBloc.perStateTextController.text;
    personaDetailRequestData.parmAddrCity = personalDetailsBloc.perCityTextController.text;
    personaDetailRequestData.parmAddrPincode = personalDetailsBloc.perPinCodeTextController.text;

    var tempMaritalStatus = personalDetailsBloc.maritalStatus;
    var maritalStatus = "";
    if (tempMaritalStatus == 0) {
      maritalStatus = "Single";
    } else if (tempMaritalStatus == 1) {
      maritalStatus = "Married";
    } else {
      maritalStatus = "Other";
    }
    personaDetailRequestData.maritalstatus = maritalStatus;
    personaDetailRequestData.fatherspouse = personalDetailsBloc.fatherSpouseCheck == 0 ? "father" : "spouse";
    personaDetailRequestData.firstname2 = personalDetailsBloc.firstNameTextController2.text;
    personaDetailRequestData.middlename2 = personalDetailsBloc.middleNameTextController2.text;
    personaDetailRequestData.lastname2 = personalDetailsBloc.lastNameTextController2.text;
    personaDetailRequestData.incomerange = personalDetailsBloc.incomeRange;
    personaDetailRequestData.occupation = personalDetailsBloc.occupation;
    personaDetailRequestData.action = personalDetailsBloc.actionCheck == 0
        ? "Politically Exposed Person"
        : personalDetailsBloc.actionCheck == 1
            ? "Past Regulatory Action"
            : "";
    personaDetailRequestData.perextra1 = "";
    personaDetailRequestData.perextra2 = "";

    personaDetailRequestData.aadhaarNumber = personalDetailsBloc.aadharTextController.text;
    personaDetailRequestData.pastRegulatoryActionDetails = personalDetailsBloc.pastRegulatoryActionController.text;
    personaDetailRequestData.nomineeEmail = personalDetailsBloc.nomineeEmailTextController.text;
    personaDetailRequestData.nomAddr1 = personalDetailsBloc.nomineeAddressLine1TextController.text;
    personaDetailRequestData.nomAddr2 = personalDetailsBloc.nomineeAddressLine2TextController.text;
    personaDetailRequestData.nomAddrState = personalDetailsBloc.nomineeStateTextController.text;
    personaDetailRequestData.nomAddrCity = personalDetailsBloc.nomineeCityTextController.text;
    personaDetailRequestData.nomAddrPincode = personalDetailsBloc.nomineePinCodeTextController.text;

    personaDetailRequestData.firstname_mother = personalDetailsBloc.motherFirstNameTextController.text;
    personaDetailRequestData.middlename_mother = personalDetailsBloc.motherMiddleNameTextController.text;
    personaDetailRequestData.lastname_mother = personalDetailsBloc.motherLastNameTextController.text;

    PersonaDetailsRequestModel requestModel = new PersonaDetailsRequestModel();
    requestModel.data = personaDetailRequestData;

    print(jsonEncode(requestModel));
    final jsonRequest = json.encode(requestModel);

    var headers = {'Content-Type': 'application/json'};
    var apiName = AppConfig().url + "saveEKYCPersonalDetails";
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
    request.fields['uploadType'] = 'self';

    if (objFile.isNotEmpty) {
      request.files.add(
        new http.MultipartFile.fromBytes('image_path', objFile, filename: 'dummy.png'),
      );
      //  request.files.add(new http.MultipartFile("image_path", PlattobjFile.readStream, PlattobjFile.size, filename: PlattobjFile.name));
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
}
