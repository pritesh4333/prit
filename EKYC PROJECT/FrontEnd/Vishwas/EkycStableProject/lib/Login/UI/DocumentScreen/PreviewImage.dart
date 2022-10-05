import 'dart:io';

import 'package:file_picker/file_picker.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';

// ignore: must_be_immutable
class PreviewImage extends StatelessWidget {
  PlatformFile preViewImage;
  String titleImage;

  PreviewImage(PlatformFile panCardImageicon, String s, this.titleImage) {
    this.preViewImage = panCardImageicon;
    this.titleImage = s;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(titleImage),
      ),

      // web code for preview
      body: Container(
        padding: EdgeInsets.only(top: 10),
        width: 200,
        height: 200,
        child: kIsWeb
            ? Image.network(
                "http://192.168.209.106:3000/viewDocuments?email_id=pritesh.parmar@greeksoft.co.in&mobile_no=8767957178&type=pancard")
            : Image.file(File(preViewImage.path)),
      ),
      // android code for preview
      // body: Container(
      //   width: 200,
      //   height: 200,
      //   decoration: BoxDecoration(color: Colors.red[200]),

      //   child: Image.file(
      //     this.preViewImage,
      //     width: 200,
      //     height: 200,
      //     fit: BoxFit.fitHeight,
      //   ),
      // ),

      // IOS
      // body: Container(
      //   child: PhotoView(
      //     // imageProvider: AssetImage('asset/images/backgroudicon.png'),
      //     imageProvider: AssetImage(this.preViewImage.path),
      //   ),
      // ),
    );
  }
}
