// ignore_for_file: unused_import

// import 'dart:ffi';
import 'dart:io';

import 'package:e_kyc/Login/UI/Configuration/AppConfig.dart';
import 'package:e_kyc/Login/UI/DocumentScreen/repository/DocumentRepository.dart';
import 'package:e_kyc/Login/UI/HomeScreen/HomeScreenLarge.dart';
import 'package:e_kyc/Login/UI/HomeScreen/HomeScreenSmall.dart';
import 'package:e_kyc/Login/UI/Login/model/LoginUserDetailModelResponse.dart';
import 'package:e_kyc/Login/UI/Login/repository/LoginRepository.dart';
import 'package:e_kyc/Login/UI/PersonalDetailsScreen/bloc/PersonalDetailsBloc.dart';
import 'package:e_kyc/Login/UI/ThemeColors.dart';
import 'package:e_kyc/Utilities/ResponseModel.dart';
// ignore: implementation_imports
import 'package:file_picker/src/platform_file.dart';
import 'package:file_picker/file_picker.dart';
import 'package:flutter_cache_manager/flutter_cache_manager.dart';
import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:image_picker/image_picker.dart';
import 'package:path_provider/path_provider.dart';
import 'package:video_player/video_player.dart';

class DocumentBloc {
  ImagePicker _imagePicker = ImagePicker();

  // VideoPlayerController _videoPlayerController;
  // VideoPlayerController _cameraVideoPlayerController;

  DocumentRepository _repository = new DocumentRepository();
  // PersonalDetailsBloc _personalDetailsBloc = new PersonalDetailsBloc();
  var cameraVideo;
  var galleryVideo;
  var ctx;
  // XFile panCardImage,
  //     signatureImage,
  //     bankProofImage,
  //     addressProffImage,
  //     incomProofImage,
  //     photographImage;

  List<XFile> signatureImage,
      bankProofImage,
      addressProffImage,
      incomProofImage,
      photographImage;

  //Flag  to validate what document has uploaded
  bool docPan,
      docSignature,
      docBankProof,
      docAddressProof,
      docIncomeProof,
      docPhotograph = false;

  //Bank Proof
  var bankProof = "";
  List<String> bankProofList = <String>[
    "Cancelled Cheque",
    "Bank Passbook",
    "Latest Bank Statement"
  ];

  //Address Proof
  var addressProof = "";
  List<String> addressProofList = <String>[
    "Voter ID",
    "Driving Licence",
    "Aadhar",
    "Passport",
    "NREGA job Card"
  ];

  //Income Proof
  var incomeProof = "";
  List<String> incomeProofList = <String>[
    "ITR Current\nFY-AY",
    "PASSBOOK COPY\n6 MONTHS",
    "BANK STATEMENT\n6 MONTHS",
    "FORM 16",
    "SALARY SLIP"
  ];

  PlatformFile panCardImageicon;

  void dispose() {}

  // void uploadDocumentsnew(BuildContext context, String s, String t, String u,
  //     List<XFile> pickedFileList) {}
  // Future<void> uploadDocuments(BuildContext context, String screenname,
  //     String type, String imageName, XFile imagepath) async {
  //   try {
  //     this.ctx = context;
  //     AppConfig().ShowLoaderDialog(context); //// show loader
  //     ResponseModel obj = await this._repository.SaveDocumentDetailsAPI(
  //           LoginRepository.login_email_id,
  //           LoginRepository.login_mobile_no,
  //           type,
  //           imageName,
  //           imagepath,
  //         );

  //     AppConfig().DismissLoaderDialog(context); //// dismiss loader
  //     if (obj.response.errorCode == "0") {
  //       LoginUserDetailModelResponse obj = await LoginRepository()
  //           .getEkycUserDetails(LoginRepository.login_email_id,
  //               LoginRepository.login_mobile_no);
  //       LoginRepository.loginDetailsResObjGlobal = obj;
  //       if (screenname.toString().contains("small")) {
  //         HomeScreenSmall.screensStreamSmall.sink
  //             .add(Ekycscreenamesmall.paymentdetailscreen);
  //       } else {
  //         HomeScreenLarge.screensStreamLarge.sink
  //             .add(Ekycscreenamelarge.paymentdetailscreen);
  //       }
  //     } else {
  //       AppConfig().DismissLoaderDialog(context); //// dismiss loader
  //       showAlert(obj.response.data.message);
  //     }
  //   } catch (exception) {
  //     AppConfig().DismissLoaderDialog(context); //// dismiss loader
  //     print("no data" + exception);
  //     showAlert("failed" + exception);
  //   }
  // }

  Future<dynamic> showAlert(BuildContext ctx, String msg) {
    return showDialog(
      context: ctx,
      builder: (ctx) => AlertDialog(
        title: Text("E-KYC"),
        content: Text(msg),
        actions: <Widget>[
          TextButton(
            onPressed: () async {
              LoginRepository _repository = new LoginRepository();
              LoginUserDetailModelResponse obj =
                  await _repository.getEkycUserDetails(
                      LoginRepository.loginEmailId,
                      LoginRepository.loginMobileNo,
                      LoginRepository.loginFullName);
              LoginRepository.loginDetailsResObjGlobal =
                  obj; // setting user response in static variable for globle use
              print(
                  LoginRepository.loginDetailsResObjGlobal.response.errorCode);
              Navigator.of(ctx).pop();
            },
            child: Text("OK"),
          ),
        ],
      ),
    );
  }

// Get image from gallery
  Future<File> pickImageFromGallery() async {
    XFile _galleryImage = await _imagePicker.pickImage(
      source: ImageSource.gallery,
      imageQuality: 50,
    );
    return File(_galleryImage.path);
  }

// Pick image from Camera
  Future<File> pickImageFromCamera() async {
    XFile _cameraImage = await _imagePicker.pickImage(
      source: ImageSource.camera,
      imageQuality: 50,
    );
    return File(_cameraImage.path);
  }

  // This funcion will helps you to pick a Video from Camera.
  Future<File> pickVideoFromCamera() async {
    XFile video = await _imagePicker.pickVideo(source: ImageSource.camera);
    cameraVideo = video;
    return File(cameraVideo.path);

//Below part should go into widget class
    // _videoPlayerController = VideoPlayerController.file(cameraVideo)
    //   ..initialize().then(
    //     (_) {
    //       setState(() {});
    //       _videoPlayerController?.play();
    //     },
    //   );
  }

  // This funcion will helps you to pick a Video from Gallery.
  Future<File> pickVideoFromGallery() async {
    XFile video = await _imagePicker.pickVideo(source: ImageSource.gallery);
    galleryVideo = video;
    return File(galleryVideo.path);

//Below part should go into widget class
    // _videoPlayerController = VideoPlayerController., [PlatformFile objFile], PlatformFile objFilefile(cameraVideo)
    //   ..initialize().then(
    //     (_) {
    //       setState(() {});
    //       _videoPlayerController?.play();
  }

  Future saveDocument(
      BuildContext context,
      String docType,
      String screenName,
      PlatformFile PlattobjFile,
      List<int> objFile,
      String proofType,
      String uniqueId) async {
    var extension = '';
    try {
      if (PlattobjFile.name.contains('.pdf') ||
          PlattobjFile.name.contains('.PDF')) {
        extension = '.pdf';
      } else {
        extension = '.png';
      }
      AppConfig().showLoaderDialog(context); // show loader
      /*this._repository.saveImage(docType, objFile).then((value) {
        print(value.toString());
        print(value.toString());
      });*/
      final obj = await this._repository.saveImage(
          docType, PlattobjFile, objFile, proofType, uniqueId, extension);

      docType = docType.toUpperCase();
      if (obj == 0) {
        AppConfig().dismissLoaderDialog(context); //// dismiss loader
        updateFlagDocument(docType);
        showAlert(context, "$docType uploaded successfully");
      } else if (obj == 1) {
        AppConfig().dismissLoaderDialog(context); //// dismiss loader
        showAlert(context, "$docType upload failed");
      }
    } catch (exception) {
      AppConfig().dismissLoaderDialog(context); //// dismiss loader
      print("no data" + exception);
      showAlert(context, "$docType upload failed");
    }
  }

  void updateFlagDocument(String docType) {
    switch (docType.toLowerCase()) {
      case "pancard":
        this.docPan = true;
        break;
      case "signature":
        this.docSignature = true;
        break;
      case "bankproof":
        this.docBankProof = true;
        break;
      case "addressproof":
        this.docAddressProof = true;
        break;
      case "incomeproof":
        this.docIncomeProof = true;
        break;
      case "photograph":
        this.docPhotograph = true;
        break;

      default:
    }
  }

  Future<void> evictImage(String url) async {
    await DefaultCacheManager().emptyCache();
    PaintingBinding.instance.imageCache.clear();
  }

  downloadImageToPreview(BuildContext context, String docType) {
    String url = this._repository.previewImage(context, docType);
    print(url);
    print("clearing image");

    showDialog(
      context: context,
      builder: (BuildContext context) {
        return Center(
          child: customDialogmethodPreviewImage(url),
        );
      },
    );
  }

  customDialogmethodPreviewImage(String url) {
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
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              Expanded(
                child: Container(
                  margin: EdgeInsets.all(10),
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Image.network(
                        url,
                        fit: BoxFit.contain,
                        width: 280,
                        height: 250,
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
