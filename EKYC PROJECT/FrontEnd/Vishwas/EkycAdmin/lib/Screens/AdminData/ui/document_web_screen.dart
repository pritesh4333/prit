import 'dart:convert';
import 'dart:math';
import 'dart:typed_data';
import 'package:ekyc_admin/Configuration/app_config.dart';
import 'package:ekyc_admin/Extension_Enum/greek_enum.dart';
import 'package:ekyc_admin/Helper/constant_image_name.dart';
import 'package:ekyc_admin/Screens/AdminData/bloc/document_bloc.dart';
import 'package:ekyc_admin/Screens/AdminData/models/response/common_data_grid_table_response_model.dart';
import 'package:ekyc_admin/Screens/AdminData/ui/admin_data_grid.dart';
import 'package:ekyc_admin/Utilities/greek_textstyle.dart';
import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:ekyc_admin/Network%20Manager/AppURLs/app_url_main.dart';
import 'package:rxdart/rxdart.dart';
import 'package:syncfusion_flutter_pdfviewer/pdfviewer.dart';
import 'package:image/image.dart' as img;
import 'dart:html';
import '../bloc/admin_data_bloc.dart';

class DocumentWebScreen extends StatefulWidget {
  const DocumentWebScreen({Key? key}) : super(key: key);

  @override
  State<DocumentWebScreen> createState() => _DocumentWebScreenState();
}

class _DocumentWebScreenState extends State<DocumentWebScreen> {
  final GlobalKey<SfPdfViewerState> _pdfViewerKey = GlobalKey();
  final _pdfViewerController = PdfViewerController();
  CommonDataGridTableResponseModel? _globalRespObj;
  documentBloc? _documentdatabloc;

  final _docTypeSubject = BehaviorSubject<DocumentType>.seeded(DocumentType.pancard);
  var id = 101, proofType = "";
  @override
  void dispose() {
    _docTypeSubject.close();
    super.dispose();
  }

  @override
  void initState() {
    super.initState();
    _pdfViewerController.zoomLevel = 3.0;
    _globalRespObj = AdminDataGridScreen.adminDetailGlobalResponseObj;
    final jsonstring = json.encode(_globalRespObj!.toJson());
    print(jsonstring);
    _documentdatabloc ??= documentBloc();
  }

  String _getDocumentURL({
    required BuildContext docContext,
    required String emailID,
    required String mobileNo,
    required DocumentType? docType,
    required String panNo,
  }) {
    String baseURL = getBaseAPIUrl();
    const chars = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz1234567890";
    String randomString = String.fromCharCodes(
      Iterable.generate(
        5,
        (_) => chars.codeUnitAt(
          Random().nextInt(chars.length),
        ),
      ),
    );

    String mainURL = "$baseURL${APINames.viewDocuments.name}?email_id=$emailID&mobile_no=$mobileNo&type=${docType?.name ?? ""}&pan=$panNo&rendom=$randomString";
    return mainURL;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: StreamBuilder<DocumentType>(
            stream: _docTypeSubject.stream,
            builder: (streamContext, snapshot) {
              return Container(
                color: Colors.white,
                padding: const EdgeInsets.all(22),
                child: Row(
                  children: [
                    Expanded(
                      flex: 3,
                      child: Column(
                        children: [
                          Expanded(
                            flex: 3,
                            child: Row(
                              children: [
                                Flexible(
                                  flex: 2,
                                  child: Column(
                                    crossAxisAlignment: CrossAxisAlignment.start,
                                    children: [
                                      Flexible(
                                        fit: FlexFit.tight,
                                        child: Container(
                                          margin: const EdgeInsets.all(5),
                                          decoration: BoxDecoration(
                                            shape: BoxShape.rectangle,
                                            color: Colors.white,
                                            borderRadius: BorderRadius.circular(5),
                                            border: Border.all(
                                              // color: const Color(0xFFE3E3E3),
                                              color: const Color(0x7CAFAFAF),
                                              width: 1,
                                            ),
                                          ),
                                          //alignment: Alignment.center,
                                          padding: const EdgeInsets.only(top: 5, bottom: 5),
                                          child: SingleChildScrollView(
                                            scrollDirection: Axis.vertical,
                                            child: Column(
                                              crossAxisAlignment: CrossAxisAlignment.start,
                                              mainAxisAlignment: MainAxisAlignment.start,
                                              children: [
                                                Container(
                                                  height: 50.0,
                                                  alignment: Alignment.center,
                                                  child: const Text(
                                                    "Document List",
                                                    textAlign: TextAlign.center,
                                                    style: TextStyle(
                                                      fontSize: 15,
                                                      fontWeight: FontWeight.w600,
                                                      color: Color(0xFFFF9100),
                                                    ),
                                                  ),
                                                ),
                                                Container(
                                                  height: 1,
                                                  color: const Color(0x7CAFAFAF),
                                                ),
                                                SizedBox(
                                                  height: 50.0,
                                                  child: InkWell(
                                                    focusColor: Colors.transparent,
                                                    hoverColor: Colors.transparent,
                                                    splashColor: Colors.transparent,
                                                    highlightColor: Colors.transparent,
                                                    overlayColor: MaterialStateProperty.resolveWith(
                                                      (_) => Colors.transparent,
                                                    ),
                                                    onTap: () {
                                                      id = 101;
                                                      proofType = "";
                                                      _docTypeSubject.sink.add(DocumentType.pancard);
                                                    },
                                                    child: Container(
                                                      color: (snapshot.data == DocumentType.pancard) ? const Color(0xFF00258E) : Colors.transparent,
                                                      child: Row(
                                                        mainAxisAlignment: MainAxisAlignment.start,
                                                        children: [
                                                          Container(
                                                            padding: const EdgeInsets.only(top: 5, bottom: 5),
                                                            child: Container(
                                                              width: 10,
                                                              height: 40,
                                                              color: (snapshot.data == DocumentType.pancard) ? const Color(0xFFFEAA03) : Colors.transparent,
                                                            ),
                                                          ),
                                                          Expanded(
                                                            child: Container(
                                                              alignment: Alignment.centerLeft,
                                                              margin: const EdgeInsets.all(5),
                                                              child: Text(
                                                                "Pan Card",
                                                                style: (snapshot.data == DocumentType.pancard) ? GreekTextStyle.documentFieldHeading : GreekTextStyle.textFieldHeading,
                                                              ),
                                                            ),
                                                          ),
                                                          Expanded(
                                                            child: Container(
                                                              alignment: Alignment.centerLeft,
                                                              margin: const EdgeInsets.only(left: 15, top: 5, bottom: 5, right: 5),
                                                              child: Text(
                                                                "Pan Card",
                                                                style: (snapshot.data == DocumentType.pancard) ? GreekTextStyle.documentFieldHeading1 : GreekTextStyle.textFieldHeading,
                                                              ),
                                                            ),
                                                          ),
                                                        ],
                                                      ),
                                                    ),
                                                  ),
                                                ),
                                                Container(
                                                  height: 1,
                                                  color: const Color(0x7CAFAFAF),
                                                ),
                                                SizedBox(
                                                  height: 50.0,
                                                  child: InkWell(
                                                    focusColor: Colors.transparent,
                                                    hoverColor: Colors.transparent,
                                                    splashColor: Colors.transparent,
                                                    highlightColor: Colors.transparent,
                                                    overlayColor: MaterialStateProperty.resolveWith(
                                                      (_) => Colors.transparent,
                                                    ),
                                                    onTap: () {
                                                      id = 102;
                                                      proofType = "";
                                                      _docTypeSubject.sink.add(DocumentType.signature);
                                                    },
                                                    child: Container(
                                                      color: (snapshot.data == DocumentType.signature) ? const Color(0xFF00258E) : Colors.transparent,
                                                      child: Row(
                                                        mainAxisAlignment: MainAxisAlignment.start,
                                                        children: [
                                                          Container(
                                                            padding: const EdgeInsets.only(top: 5, bottom: 5),
                                                            child: Container(
                                                              width: 10,
                                                              height: 40,
                                                              color: (snapshot.data == DocumentType.signature) ? const Color(0xFFFEAA03) : Colors.transparent,
                                                            ),
                                                          ),
                                                          Expanded(
                                                            child: Container(
                                                              alignment: Alignment.centerLeft,
                                                              margin: const EdgeInsets.all(5),
                                                              child: Text(
                                                                "Signature",
                                                                style: (snapshot.data == DocumentType.signature) ? GreekTextStyle.documentFieldHeading : GreekTextStyle.textFieldHeading,
                                                              ),
                                                            ),
                                                          ),
                                                          Expanded(
                                                            child: Container(
                                                              alignment: Alignment.centerLeft,
                                                              margin: const EdgeInsets.only(left: 15, top: 5, bottom: 5, right: 5),
                                                              child: Text(
                                                                "Signature",
                                                                style: (snapshot.data == DocumentType.signature) ? GreekTextStyle.documentFieldHeading1 : GreekTextStyle.textFieldHeading,
                                                              ),
                                                            ),
                                                          ),
                                                        ],
                                                      ),
                                                    ),
                                                  ),
                                                ),
                                                Container(
                                                  height: 1,
                                                  color: const Color(0x7CAFAFAF),
                                                ),
                                                SizedBox(
                                                  height: 50.0,
                                                  child: InkWell(
                                                    focusColor: Colors.transparent,
                                                    hoverColor: Colors.transparent,
                                                    splashColor: Colors.transparent,
                                                    highlightColor: Colors.transparent,
                                                    overlayColor: MaterialStateProperty.resolveWith(
                                                      (_) => Colors.transparent,
                                                    ),
                                                    onTap: () {
                                                      id = 103;
                                                      proofType = _globalRespObj!.bankprooftype;
                                                      _docTypeSubject.sink.add(DocumentType.bankproof);
                                                    },
                                                    child: Container(
                                                      color: (snapshot.data == DocumentType.bankproof) ? const Color(0xFF00258E) : Colors.transparent,
                                                      child: Row(
                                                        mainAxisAlignment: MainAxisAlignment.start,
                                                        children: [
                                                          Container(
                                                            padding: const EdgeInsets.only(top: 5, bottom: 5),
                                                            child: Container(
                                                              width: 10,
                                                              height: 40,
                                                              color: (snapshot.data == DocumentType.bankproof) ? const Color(0xFFFEAA03) : Colors.transparent,
                                                            ),
                                                          ),
                                                          Expanded(
                                                            child: Container(
                                                              alignment: Alignment.centerLeft,
                                                              margin: const EdgeInsets.all(5),
                                                              child: Text(
                                                                "Bank Proof",
                                                                style: (snapshot.data == DocumentType.bankproof) ? GreekTextStyle.documentFieldHeading : GreekTextStyle.textFieldHeading,
                                                              ),
                                                            ),
                                                          ),
                                                          Expanded(
                                                            child: Container(
                                                              alignment: Alignment.centerLeft,
                                                              margin: const EdgeInsets.only(left: 15, top: 5, bottom: 5, right: 5),
                                                              child: Text(
                                                                int.parse(_globalRespObj?.bankprooftype == "" || _globalRespObj?.bankprooftype == "N/A" ? "5" : _globalRespObj!.bankprooftype) == 0
                                                                    ? "Cancelled Cheque"
                                                                    : int.parse(_globalRespObj?.bankprooftype == "" || _globalRespObj?.bankprooftype == "N/A" ? "5" : _globalRespObj!.bankprooftype) == 1
                                                                        ? "Bank Passbook"
                                                                        : int.parse(_globalRespObj?.bankprooftype == "" || _globalRespObj?.bankprooftype == "N/A" ? "5" : _globalRespObj!.bankprooftype) == 2
                                                                            ? "Latest Bank Statement"
                                                                            : "",
                                                                style: (snapshot.data == DocumentType.bankproof) ? GreekTextStyle.documentFieldHeading1 : GreekTextStyle.textFieldHeading,
                                                              ),
                                                            ),
                                                          ),
                                                        ],
                                                      ),
                                                    ),
                                                  ),
                                                ),
                                                Container(
                                                  height: 1,
                                                  color: const Color(0x7CAFAFAF),
                                                ),
                                                SizedBox(
                                                  height: 50.0,
                                                  child: InkWell(
                                                    focusColor: Colors.transparent,
                                                    hoverColor: Colors.transparent,
                                                    splashColor: Colors.transparent,
                                                    highlightColor: Colors.transparent,
                                                    overlayColor: MaterialStateProperty.resolveWith(
                                                      (_) => Colors.transparent,
                                                    ),
                                                    onTap: () {
                                                      String proofType = "";

                                                      id = 104;
                                                      proofType = _globalRespObj!.addressprooftype;

                                                      _docTypeSubject.sink.add(DocumentType.addressproof);
                                                    },
                                                    child: Container(
                                                      color: (snapshot.data == DocumentType.addressproof) ? const Color(0xFF00258E) : Colors.transparent,
                                                      child: Row(
                                                        mainAxisAlignment: MainAxisAlignment.start,
                                                        children: [
                                                          Container(
                                                            padding: const EdgeInsets.only(top: 5, bottom: 5),
                                                            child: Container(
                                                              width: 10,
                                                              height: 40,
                                                              color: (snapshot.data == DocumentType.addressproof) ? const Color(0xFFFEAA03) : Colors.transparent,
                                                            ),
                                                          ),
                                                          Expanded(
                                                            child: Container(
                                                              alignment: Alignment.centerLeft,
                                                              margin: const EdgeInsets.all(5),
                                                              child: Text(
                                                                "Address Proof",
                                                                style: (snapshot.data == DocumentType.addressproof) ? GreekTextStyle.documentFieldHeading : GreekTextStyle.textFieldHeading,
                                                              ),
                                                            ),
                                                          ),
                                                          Expanded(
                                                            child: Container(
                                                              alignment: Alignment.centerLeft,
                                                              margin: const EdgeInsets.only(left: 15, top: 5, bottom: 5, right: 5),
                                                              child: Text(
                                                                int.parse(_globalRespObj?.addressprooftype == "" || _globalRespObj?.addressprooftype == "N/A" ? "5" : _globalRespObj!.addressprooftype) == 0
                                                                    ? "Voter ID"
                                                                    : int.parse(_globalRespObj?.addressprooftype == "" || _globalRespObj?.addressprooftype == "N/A" ? "5" : _globalRespObj!.addressprooftype) == 1
                                                                        ? "Driving Licence"
                                                                        : int.parse(_globalRespObj?.addressprooftype == "" || _globalRespObj?.addressprooftype == "N/A" ? "5" : _globalRespObj!.addressprooftype) == 2
                                                                            ? "Aadhar"
                                                                            : int.parse(_globalRespObj?.addressprooftype == "" || _globalRespObj?.addressprooftype == "N/A" ? "5" : _globalRespObj!.addressprooftype) == 3
                                                                                ? "Passport"
                                                                                : int.parse(_globalRespObj?.addressprooftype == "" || _globalRespObj?.addressprooftype == "N/A" ? "5" : _globalRespObj!.addressprooftype) == 4
                                                                                    ? "NREGA job Card"
                                                                                    : "",
                                                                style: (snapshot.data == DocumentType.addressproof) ? GreekTextStyle.documentFieldHeading1 : GreekTextStyle.textFieldHeading,
                                                              ),
                                                            ),
                                                          ),
                                                        ],
                                                      ),
                                                    ),
                                                  ),
                                                ),
                                                Container(
                                                  height: 1,
                                                  color: const Color(0x7CAFAFAF),
                                                ),
                                                SizedBox(
                                                  height: 50.0,
                                                  child: InkWell(
                                                    focusColor: Colors.transparent,
                                                    hoverColor: Colors.transparent,
                                                    splashColor: Colors.transparent,
                                                    highlightColor: Colors.transparent,
                                                    overlayColor: MaterialStateProperty.resolveWith(
                                                      (_) => Colors.transparent,
                                                    ),
                                                    onTap: () {
                                                      id = 105;
                                                      proofType = _globalRespObj!.incomeprooftype;
                                                      _docTypeSubject.sink.add(DocumentType.incomeproof);
                                                    },
                                                    child: Container(
                                                      color: (snapshot.data == DocumentType.incomeproof) ? const Color(0xFF00258E) : Colors.transparent,
                                                      child: Row(
                                                        mainAxisAlignment: MainAxisAlignment.start,
                                                        children: [
                                                          Container(
                                                            padding: const EdgeInsets.only(top: 5, bottom: 5),
                                                            child: Container(
                                                              width: 10,
                                                              height: 40,
                                                              color: (snapshot.data == DocumentType.incomeproof) ? const Color(0xFFFEAA03) : Colors.transparent,
                                                            ),
                                                          ),
                                                          Expanded(
                                                            child: Container(
                                                              alignment: Alignment.centerLeft,
                                                              margin: const EdgeInsets.all(5),
                                                              child: Text(
                                                                "Income Proof",
                                                                style: (snapshot.data == DocumentType.incomeproof) ? GreekTextStyle.documentFieldHeading : GreekTextStyle.textFieldHeading,
                                                              ),
                                                            ),
                                                          ),
                                                          Expanded(
                                                            child: Container(
                                                              alignment: Alignment.centerLeft,
                                                              margin: const EdgeInsets.only(left: 15, top: 5, bottom: 5, right: 5),
                                                              child: Text(
                                                                int.parse(_globalRespObj?.incomeprooftype == "" || _globalRespObj?.incomeprooftype == "N/A" ? "5" : _globalRespObj!.incomeprooftype) == 0
                                                                    ? "ITR Current FY-AY"
                                                                    : int.parse(_globalRespObj?.incomeprooftype == "" || _globalRespObj?.incomeprooftype == "N/A" ? "5" : _globalRespObj!.incomeprooftype) == 1
                                                                        ? "PASSBOOK COPY - 6 MONTHS"
                                                                        : int.parse(_globalRespObj?.incomeprooftype == "" || _globalRespObj?.incomeprooftype == "N/A" ? "5" : _globalRespObj!.incomeprooftype) == 2
                                                                            ? "BANK STATEMENT - 6 MONTHS"
                                                                            : int.parse(_globalRespObj?.incomeprooftype == "" || _globalRespObj?.incomeprooftype == "N/A" ? "5" : _globalRespObj!.incomeprooftype) == 3
                                                                                ? "FORM 16"
                                                                                : int.parse(_globalRespObj?.incomeprooftype == "" || _globalRespObj?.incomeprooftype == "N/A" ? "5" : _globalRespObj!.incomeprooftype) == 4
                                                                                    ? "SALARY SLIP"
                                                                                    : "",
                                                                style: (snapshot.data == DocumentType.incomeproof) ? GreekTextStyle.documentFieldHeading1 : GreekTextStyle.textFieldHeading,
                                                              ),
                                                            ),
                                                          ),
                                                        ],
                                                      ),
                                                    ),
                                                  ),
                                                ),
                                                Container(
                                                  height: 1,
                                                  color: const Color(0x7CAFAFAF),
                                                ),
                                                SizedBox(
                                                  height: 50.0,
                                                  child: InkWell(
                                                    focusColor: Colors.transparent,
                                                    hoverColor: Colors.transparent,
                                                    splashColor: Colors.transparent,
                                                    highlightColor: Colors.transparent,
                                                    overlayColor: MaterialStateProperty.resolveWith(
                                                      (_) => Colors.transparent,
                                                    ),
                                                    onTap: () {
                                                      id = 106;
                                                      proofType = "";
                                                      _docTypeSubject.sink.add(DocumentType.photograph);
                                                    },
                                                    child: Container(
                                                      color: (snapshot.data == DocumentType.photograph) ? const Color(0xFF00258E) : Colors.transparent,
                                                      child: Row(
                                                        mainAxisAlignment: MainAxisAlignment.start,
                                                        children: [
                                                          Container(
                                                            padding: const EdgeInsets.only(top: 5, bottom: 5),
                                                            child: Container(
                                                              width: 10,
                                                              height: 40,
                                                              color: (snapshot.data == DocumentType.photograph) ? const Color(0xFFFEAA03) : Colors.transparent,
                                                            ),
                                                          ),
                                                          Expanded(
                                                            child: Container(
                                                              alignment: Alignment.centerLeft,
                                                              margin: const EdgeInsets.all(5),
                                                              child: Text(
                                                                "Photograph",
                                                                style: (snapshot.data == DocumentType.photograph) ? GreekTextStyle.documentFieldHeading : GreekTextStyle.textFieldHeading,
                                                              ),
                                                            ),
                                                          ),
                                                          Expanded(
                                                            child: Container(
                                                              alignment: Alignment.centerLeft,
                                                              margin: const EdgeInsets.only(left: 15, top: 5, bottom: 5, right: 5),
                                                              child: Text(
                                                                "Photograph",
                                                                style: (snapshot.data == DocumentType.photograph) ? GreekTextStyle.documentFieldHeading1 : GreekTextStyle.textFieldHeading,
                                                              ),
                                                            ),
                                                          ),
                                                        ],
                                                      ),
                                                    ),
                                                  ),
                                                ),
                                                Container(
                                                  height: 1,
                                                  color: const Color(0x7CAFAFAF),
                                                ),
                                              ],
                                            ),
                                          ),
                                        ),
                                      ),
                                    ],
                                  ),
                                ),
                                Flexible(
                                  flex: 5,
                                  fit: FlexFit.loose,
                                  child: Column(
                                    crossAxisAlignment: CrossAxisAlignment.start,
                                    children: [
                                      Flexible(
                                        fit: FlexFit.tight,
                                        child: Container(
                                          margin: const EdgeInsets.all(5),
                                          padding: const EdgeInsets.all(20.0),
                                          alignment: Alignment.center,
                                          decoration: BoxDecoration(
                                            shape: BoxShape.rectangle,
                                            borderRadius: BorderRadius.circular(5),
                                            border: Border.all(
                                              // color: const Color(0xFFE3E3E3),
                                              color: const Color(0x7CAFAFAF),
                                              width: 1,
                                            ),
                                          ),
                                          child: Center(
                                            child: Container(
                                              padding: const EdgeInsets.all(10),
                                              alignment: Alignment.center,
                                              child: ((snapshot.hasData) && (snapshot.data != null))
                                                  ? ((snapshot.data! == DocumentType.bankproof) || (snapshot.data! == DocumentType.incomeproof))
                                                      ? SfPdfViewer.network(
                                                          _getDocumentURL(
                                                            docContext: streamContext,
                                                            emailID: _globalRespObj?.emailId ?? "",
                                                            mobileNo: _globalRespObj?.mobileNo ?? "",
                                                            docType: snapshot.data,
                                                            panNo: _globalRespObj?.pan ?? "",
                                                          ),
                                                          key: _pdfViewerKey,
                                                          controller: _pdfViewerController,
                                                          //initialZoomLevel: 3.0,
                                                          enableDoubleTapZooming: true,
                                                          scrollDirection: PdfScrollDirection.vertical,
                                                          pageLayoutMode: PdfPageLayoutMode.continuous,
                                                          interactionMode: PdfInteractionMode.pan,
                                                        )
                                                      : FadeInImage.assetNetwork(
                                                          placeholderFit: BoxFit.contain,
                                                          placeholder: CommonImage.placeholder_doc_image.name,
                                                          fit: BoxFit.contain,
                                                          imageErrorBuilder: (context, error, stackTrace) {
                                                            return Image.asset(CommonImage.placeholder_doc_image.name, width: 100, height: 100);
                                                          },
                                                          image: _getDocumentURL(
                                                            docContext: streamContext,
                                                            emailID: _globalRespObj?.emailId ?? "",
                                                            mobileNo: _globalRespObj?.mobileNo ?? "",
                                                            docType: snapshot.data,
                                                            panNo: _globalRespObj?.pan ?? "",
                                                          ),
                                                        )
                                                  : Image.asset(
                                                      CommonImage.placeholder_doc_image.name,
                                                      fit: BoxFit.contain,
                                                    ),
                                            ),
                                          ),
                                        ),
                                      ),
                                    ],
                                  ),
                                ),
                              ],
                            ),
                          ),
                          Flexible(
                            flex: 1,
                            child: Container(
                              margin: const EdgeInsets.all(5),
                              decoration: BoxDecoration(
                                shape: BoxShape.rectangle,
                                borderRadius: BorderRadius.circular(5),
                                border: Border.all(
                                  // color: const Color(0xFFE3E3E3),
                                  color: const Color(0x7CAFAFAF),
                                  width: 1,
                                ),
                              ),
                              alignment: Alignment.center,
                              padding: const EdgeInsets.only(left: 0, top: 5, right: 0, bottom: 5),
                              child: Padding(
                                padding: const EdgeInsets.all(10.0),
                                child: Row(
                                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                  children: [
                                    Column(
                                      crossAxisAlignment: CrossAxisAlignment.start,
                                      children: [
                                        Expanded(
                                          child: Container(
                                            alignment: Alignment.center,
                                            child: const Text(
                                              "Upload Doucment Folder as a D Drive",
                                              style: GreekTextStyle.textFieldHeading,
                                            ),
                                          ),
                                        ),
                                        Expanded(
                                          child: InkWell(
                                            onTap: () {
                                              customOnPressed(id, proofType);
                                            },
                                            child: Container(
                                              height: 35,
                                              width: 150,
                                              margin: const EdgeInsets.all(0),
                                              decoration: BoxDecoration(
                                                shape: BoxShape.rectangle,
                                                borderRadius: BorderRadius.circular(20),
                                                border: Border.all(
                                                  // color: const Color(0xFFE3E3E3),
                                                  color: const Color(0x7CAFAFAF),
                                                  width: 1,
                                                ),
                                              ),
                                              alignment: Alignment.center,
                                              padding: const EdgeInsets.only(left: 0, top: 0, right: 0, bottom: 0),
                                              child: const Text(
                                                "Open File Location",
                                                style: GreekTextStyle.personalDetailsHeading,
                                              ),
                                            ),
                                          ),
                                        ),
                                      ],
                                    ),
                                    InkWell(
                                      onTap: () {},
                                      child: Container(
                                        height: 35,
                                        width: 100,
                                        decoration: BoxDecoration(
                                          shape: BoxShape.rectangle,
                                          borderRadius: BorderRadius.circular(15),
                                          color: const Color(0x7CAFAFAF),
                                          border: Border.all(
                                            // color: const Color(0xFFE3E3E3),
                                            color: const Color(0x7CAFAFAF),
                                            width: 1,
                                          ),
                                        ),
                                        alignment: Alignment.center,
                                        padding: const EdgeInsets.only(left: 0, top: 10, right: 0, bottom: 10),
                                        child: const Text(
                                          "Save",
                                          style: GreekTextStyle.personalDetailsHeading,
                                        ),
                                      ),
                                    ),
                                    InkWell(
                                      onTap: () {},
                                      child: Container(
                                        height: 35,
                                        width: 200,
                                        decoration: BoxDecoration(
                                          shape: BoxShape.rectangle,
                                          borderRadius: BorderRadius.circular(10),
                                          border: Border.all(
                                            // color: const Color(0xFFE3E3E3),
                                            color: const Color.fromARGB(123, 236, 182, 5),
                                            width: 1,
                                          ),
                                        ),
                                        alignment: Alignment.center,
                                        padding: const EdgeInsets.only(left: 0, top: 10, right: 0, bottom: 10),
                                        child: const Text(
                                          "SEND PENDING DOC EMAIL",
                                          style: GreekTextStyle.personalDetailsHeading,
                                        ),
                                      ),
                                    ),
                                    InkWell(
                                      onTap: () {
                                        try {
                                          if (_globalRespObj!.ekycdocument == "") {
                                            AppConfig().showAlert(context, "No document found");
                                          } else {
                                            var url = getBaseAPIUrl() + APINames.downloadUserDoc.name + "?pan=" + _globalRespObj!.pan + "&downloadType=document";
                                            AnchorElement anchorElement = AnchorElement(href: url);
                                            anchorElement.download = url;
                                            anchorElement.click();
                                          }
                                        } catch (e) {
                                          print(e.toString());
                                        }
                                      },
                                      child: Container(
                                        height: 35,
                                        width: 200,
                                        decoration: BoxDecoration(
                                          shape: BoxShape.rectangle,
                                          borderRadius: BorderRadius.circular(10),
                                          border: Border.all(
                                            // color: const Color(0xFFE3E3E3),
                                            color: const Color.fromARGB(123, 236, 182, 5),
                                            width: 1,
                                          ),
                                        ),
                                        alignment: Alignment.center,
                                        padding: const EdgeInsets.only(left: 0, top: 10, right: 0, bottom: 10),
                                        child: const Text(
                                          "Download File",
                                          style: GreekTextStyle.personalDetailsHeading,
                                        ),
                                      ),
                                    ),
                                  ],
                                ),
                              ),
                            ),
                          )
                        ],
                      ),
                    ),
                    //   Expanded(
                    //     flex: 1,
                    //     child: Column(
                    //       children: [
                    //         Flexible(
                    //           flex: 2,
                    //           child: Container(
                    //             margin: const EdgeInsets.only(left: 10, top: 5, right: 10, bottom: 10),
                    //             decoration: BoxDecoration(
                    //               shape: BoxShape.rectangle,
                    //               borderRadius: BorderRadius.circular(5),
                    //               border: Border.all(
                    //                 // color: const Color(0xFFE3E3E3),
                    //                 color: const Color(0x7CAFAFAF),
                    //                 width: 1,
                    //               ),
                    //             ),
                    //             alignment: Alignment.center,
                    //             padding: const EdgeInsets.only(left: 0, top: 0, right: 0, bottom: 5),
                    //             child: Padding(
                    //               padding: const EdgeInsets.all(10.0),
                    //               child: Column(
                    //                 crossAxisAlignment: CrossAxisAlignment.start,
                    //                 mainAxisAlignment: MainAxisAlignment.start,
                    //                 children: [
                    //                   // Row(
                    //                   //   mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    //                   //   children: [
                    //                   //     Expanded(
                    //                   //       child: Container(
                    //                   //         width: 110,
                    //                   //         margin: const EdgeInsets.all(5),
                    //                   //         decoration: BoxDecoration(
                    //                   //           shape: BoxShape.rectangle,
                    //                   //           borderRadius: BorderRadius.circular(5),
                    //                   //           color: const Color(0xFFF5F5F5),
                    //                   //           border: Border.all(
                    //                   //             // color: const Color(0xFFE3E3E3),
                    //                   //             color: const Color(0xFFF5F5F5),
                    //                   //             width: 1,
                    //                   //           ),
                    //                   //         ),
                    //                   //         alignment: Alignment.center,
                    //                   //         padding: const EdgeInsets.only(left: 0, top: 10, right: 0, bottom: 10),
                    //                   //         child: const Text(
                    //                   //           "Raw PDF",
                    //                   //           style: GreekTextStyle.rightboxbuttontext,
                    //                   //         ),
                    //                   //       ),
                    //                   //     ),
                    //                   //     Expanded(
                    //                   //       child: Container(
                    //                   //         width: 110,
                    //                   //         margin: const EdgeInsets.all(5),
                    //                   //         decoration: BoxDecoration(
                    //                   //           shape: BoxShape.rectangle,
                    //                   //           borderRadius: BorderRadius.circular(5),
                    //                   //           color: const Color(0xFFF5F5F5),
                    //                   //           border: Border.all(
                    //                   //             // color: const Color(0xFFE3E3E3),
                    //                   //             color: const Color(0xFFF5F5F5),
                    //                   //             width: 1,
                    //                   //           ),
                    //                   //         ),
                    //                   //         alignment: Alignment.center,
                    //                   //         padding: const EdgeInsets.only(left: 0, top: 10, right: 0, bottom: 10),
                    //                   //         child: const Text(
                    //                   //           "Raw PDF COMM",
                    //                   //           style: GreekTextStyle.rightboxbuttontext,
                    //                   //         ),
                    //                   //       ),
                    //                   //     ),
                    //                   //   ],
                    //                   // ),
                    //                   // Row(
                    //                   //   mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    //                   //   children: [
                    //                   //     Expanded(
                    //                   //       child: Container(
                    //                   //         width: 110,
                    //                   //         margin: const EdgeInsets.all(5),
                    //                   //         decoration: BoxDecoration(
                    //                   //           shape: BoxShape.rectangle,
                    //                   //           borderRadius: BorderRadius.circular(5),
                    //                   //           color: const Color(0xFFF5F5F5),
                    //                   //           border: Border.all(
                    //                   //             // color: const Color(0xFFE3E3E3),
                    //                   //             color: const Color(0xFFF5F5F5),
                    //                   //             width: 1,
                    //                   //           ),
                    //                   //         ),
                    //                   //         alignment: Alignment.center,
                    //                   //         padding: const EdgeInsets.only(left: 0, top: 10, right: 0, bottom: 10),
                    //                   //         child: const Text(
                    //                   //           "Sign PDF",
                    //                   //           style: GreekTextStyle.rightboxbuttontext,
                    //                   //         ),
                    //                   //       ),
                    //                   //     ),
                    //                   //     Expanded(
                    //                   //       child: Container(
                    //                   //         width: 110,
                    //                   //         margin: const EdgeInsets.all(5),
                    //                   //         decoration: BoxDecoration(
                    //                   //           shape: BoxShape.rectangle,
                    //                   //           borderRadius: BorderRadius.circular(5),
                    //                   //           color: const Color(0xFFF5F5F5),
                    //                   //           border: Border.all(
                    //                   //             // color: const Color(0xFFE3E3E3),
                    //                   //             color: const Color(0xFFF5F5F5),
                    //                   //             width: 1,
                    //                   //           ),
                    //                   //         ),
                    //                   //         alignment: Alignment.center,
                    //                   //         padding: const EdgeInsets.only(left: 0, top: 10, right: 0, bottom: 10),
                    //                   //         child: const Text(
                    //                   //           "Sign PDF COMM",
                    //                   //           style: GreekTextStyle.rightboxbuttontext,
                    //                   //         ),
                    //                   //       ),
                    //                   //     ),
                    //                   //   ],
                    //                   // ),
                    //                   // Row(
                    //                   //   mainAxisAlignment: MainAxisAlignment.center,
                    //                   //   children: [
                    //                   //     Flexible(
                    //                   //       flex: 1,
                    //                   //       child: Container(
                    //                   //         width: 150,
                    //                   //         margin: const EdgeInsets.only(left: 60, right: 60, top: 10, bottom: 10),
                    //                   //         decoration: BoxDecoration(
                    //                   //           shape: BoxShape.rectangle,
                    //                   //           borderRadius: BorderRadius.circular(5),
                    //                   //           color: const Color(0xFFF5F5F5),
                    //                   //           border: Border.all(
                    //                   //             // color: const Color(0xFFE3E3E3),
                    //                   //             color: const Color(0xFFF5F5F5),
                    //                   //             width: 1,
                    //                   //           ),
                    //                   //         ),
                    //                   //         alignment: Alignment.center,
                    //                   //         padding: const EdgeInsets.only(left: 0, top: 10, right: 0, bottom: 10),
                    //                   //         child: const Text(
                    //                   //           "KYC PDF",
                    //                   //           style: GreekTextStyle.rightboxbuttontext,
                    //                   //         ),
                    //                   //       ),
                    //                   //     ),
                    //                   //   ],
                    //                   // ),
                    //                   Flexible(
                    //                     child: Row(
                    //                       mainAxisAlignment: MainAxisAlignment.center,
                    //                       children: [
                    //                         Flexible(
                    //                           child: Container(
                    //                             height: 35,
                    //                             width: 200,
                    //                             margin: const EdgeInsets.only(left: 0, right: 0, top: 10, bottom: 10),
                    //                             decoration: BoxDecoration(
                    //                               shape: BoxShape.rectangle,
                    //                               borderRadius: BorderRadius.circular(20),
                    //                               border: Border.all(
                    //                                 // color: const Color(0xFFE3E3E3),
                    //                                 color: const Color.fromARGB(123, 236, 182, 5),
                    //                                 width: 1,
                    //                               ),
                    //                             ),
                    //                             alignment: Alignment.center,
                    //                             padding: const EdgeInsets.only(left: 0, top: 10, right: 0, bottom: 10),
                    //                             child: const Text(
                    //                               "Download Zip",
                    //                               style: GreekTextStyle.popupmenutext,
                    //                             ),
                    //                           ),
                    //                         ),
                    //                       ],
                    //                     ),
                    //                   ),
                    //                   // Flexible(
                    //                   //   child: Row(
                    //                   //     crossAxisAlignment: CrossAxisAlignment.center,
                    //                   //     mainAxisAlignment: MainAxisAlignment.center,
                    //                   //     children: [
                    //                   //       Flexible(
                    //                   //         child: Container(
                    //                   //           height: 100,
                    //                   //           margin: const EdgeInsets.all(10),
                    //                   //           child: Transform.scale(
                    //                   //             scale: 1.5,
                    //                   //             child: Checkbox(
                    //                   //               checkColor: Colors.blue,
                    //                   //               activeColor: const Color(0xFFF5F5F5),
                    //                   //               value: true,
                    //                   //               onChanged: (value) {
                    //                   //                 // setState(
                    //                   //                 //   () {
                    //                   //                 //     personalDetailsBloc.sameAddressCheckbox = value;
                    //                   //                 //     print('same address checkbox ${personalDetailsBloc.sameAddressCheckbox}');
                    //                   //                 //     if (personalDetailsBloc.sameAddressCheckbox) {
                    //                   //                 //       updatePermanentAddressData();
                    //                   //                 //     } else {
                    //                   //                 //       addRemoveAddressData();
                    //                   //                 //     }
                    //                   //                 //   },
                    //                   //                 // );
                    //                   //               },
                    //                   //             ),
                    //                   //           ),
                    //                   //         ),
                    //                   //       ),
                    //                   //       Flexible(
                    //                   //         flex: 2,
                    //                   //         child: Container(
                    //                   //           padding: const EdgeInsets.only(left: 10),
                    //                   //           child: const Text(
                    //                   //             'Transfer to Back office',
                    //                   //             style: GreekTextStyle.textFieldHeading,
                    //                   //           ),
                    //                   //         ),
                    //                   //       ),
                    //                   //       Flexible(
                    //                   //         child: Container(
                    //                   //           padding: const EdgeInsets.only(left: 10),
                    //                   //           child: Transform.scale(
                    //                   //             scale: 1.5,
                    //                   //             child: Checkbox(
                    //                   //               checkColor: Colors.blue,
                    //                   //               activeColor: const Color(0xFFF5F5F5),
                    //                   //               value: true,
                    //                   //               onChanged: (value) {
                    //                   //                 // setState(
                    //                   //                 //   () {
                    //                   //                 //     personalDetailsBloc.sameAddressCheckbox = value;
                    //                   //                 //     print('same address checkbox ${personalDetailsBloc.sameAddressCheckbox}');
                    //                   //                 //     if (personalDetailsBloc.sameAddressCheckbox) {
                    //                   //                 //       updatePermanentAddressData();
                    //                   //                 //     } else {
                    //                   //                 //       addRemoveAddressData();
                    //                   //                 //     }
                    //                   //                 //   },
                    //                   //                 // );
                    //                   //               },
                    //                   //             ),
                    //                   //           ),
                    //                   //         ),
                    //                   //       ),
                    //                   //       Flexible(
                    //                   //         flex: 2,
                    //                   //         child: Container(
                    //                   //           padding: const EdgeInsets.only(left: 10),
                    //                   //           child: const Text(
                    //                   //             'Rejection',
                    //                   //             style: GreekTextStyle.textFieldHeading,
                    //                   //           ),
                    //                   //         ),
                    //                   //       ),
                    //                   //     ],
                    //                   //   ),
                    //                   // ),
                    //                   // Flexible(
                    //                   //   flex: 3,
                    //                   //   child: Container(
                    //                   //     height: 500,
                    //                   //     decoration: BoxDecoration(
                    //                   //       shape: BoxShape.rectangle,
                    //                   //       borderRadius: BorderRadius.circular(5),
                    //                   //       border: Border.all(
                    //                   //         // color: const Color(0xFFE3E3E3),
                    //                   //         color: const Color(0x7CAFAFAF),
                    //                   //         width: 1,
                    //                   //       ),
                    //                   //     ),
                    //                   //     margin: const EdgeInsets.all(10),
                    //                   //     child: TextField(
                    //                   //       textCapitalization: TextCapitalization.words,
                    //                   //       textAlign: TextAlign.center,
                    //                   //       // onChanged: (value) {
                    //                   //       //   this.personalDetailsBloc.resAddressLine1TextController.value = TextEditingValue(text: value.toUpperCase(), selection: this.personalDetailsBloc.resAddressLine1TextController.selection);
                    //                   //       // },
                    //                   //       // controller: personalDetailsBloc.resAddressLine1TextController,
                    //                   //       inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z0-9 ,./()-]'))],
                    //                   //       maxLines: 5,
                    //                   //       decoration: const InputDecoration(
                    //                   //           border: InputBorder.none,
                    //                   //           contentPadding: EdgeInsets.only(left: 10, bottom: 10, top: 85, right: 10), // add padding to adjust text
                    //                   //           isDense: false,
                    //                   //           hintText: "Type rejection reason",
                    //                   //           hintStyle: TextStyle(
                    //                   //             fontWeight: FontWeight.w100,
                    //                   //             fontSize: 10,
                    //                   //             color: Color.fromARGB(255, 207, 202, 202),
                    //                   //           )
                    //                   //           // errorText: validResAddress1.isEmpty ? null : validResAddress1,
                    //                   //           ),
                    //                   //     ),
                    //                   //   ),
                    //                   // ),
                    //                   //  Flexible(
                    //                   //   child: Row(
                    //                   //     mainAxisAlignment: MainAxisAlignment.center,
                    //                   //     children: [
                    //                   //       Flexible(
                    //                   //         child: Container(
                    //                   //           height: 35,
                    //                   //           width: 200,
                    //                   //           margin: const EdgeInsets.only(top: 10),
                    //                   //           decoration: BoxDecoration(
                    //                   //             shape: BoxShape.rectangle,
                    //                   //             borderRadius: BorderRadius.circular(20),
                    //                   //             color: const Color(0xFF00258E),
                    //                   //             border: Border.all(
                    //                   //               // color: const Color(0xFFE3E3E3),
                    //                   //               color: const Color.fromARGB(123, 236, 182, 5),
                    //                   //               width: 1,
                    //                   //             ),
                    //                   //           ),
                    //                   //           alignment: Alignment.center,
                    //                   //           padding: const EdgeInsets.only(left: 0, top: 10, right: 0, bottom: 10),
                    //                   //           child: const Text(
                    //                   //             "Send Message",
                    //                   //             style: TextStyle(fontWeight: FontWeight.w300, fontSize: 10, color: Colors.white),
                    //                   //           ),
                    //                   //         ),
                    //                   //       ),
                    //                   //     ],
                    //                   //   ),
                    //                   // ),
                    //                 ],
                    //               ),
                    //             ),
                    //           ),
                    //         )
                    //       ],
                    //     ),
                    //   )
                  ],
                ),
              );
            }),
      ),
    );
  }

  void customOnPressed(int id, String proofType) {
    var docObj = _globalRespObj?.uniqueId;
    switch (id) {
      case 101:
        print('PAN Card :$id');
        openChoiceBottomsheetImagePicker(id, 'pancard', proofType, docObj.toString(), ['png', 'jpeg', 'PNG', 'JPEG', 'jpg', 'JPG']);
        break;
      case 102:
        print(' Signature :$id');
        openChoiceBottomsheetImagePicker(id, 'signature', proofType, docObj.toString(), ['png', 'jpeg', 'PNG', 'JPEG', 'jpg', 'JPG']);
        break;
      case 103:
        print('Bank Proof :$id');
        openChoiceBottomsheetImagePicker(id, 'bankproof', proofType, docObj.toString(), ['pdf', 'PDF']);
        break;
      case 104:
        print('Address Proof :$id');
        openChoiceBottomsheetImagePicker(id, 'addressproof', proofType, docObj.toString(), ['png', 'jpeg', 'PNG', 'JPEG', 'jpg', 'JPG']);
        break;
      case 105:
        print('Income Proof :$id');
        openChoiceBottomsheetImagePicker(id, 'incomeproof', proofType, docObj.toString(), ['pdf', 'PDF']);
        break;
      case 106:
        print('Photograph :$id');
        openChoiceBottomsheetImagePicker(id, 'photograph', proofType, docObj.toString(), ['png', 'jpeg', 'PNG', 'JPEG', 'jpg', 'JPG']);
        break;

      default:
    }
  }

  void openChoiceBottomsheetImagePicker(int id, String docType, String proofType, String uniqueId, List<String> allowedExtension) {
    AppConfig().showLoaderDialog(context);
    selectImageFile(docType, proofType, uniqueId, allowedExtension, id);
  }

  Future<void> selectImageFile(String docType, String proofType, String uniqueId, List<String> extensionAllowed, int id) async {
    Uint8List objFile;
    var result = await FilePicker.platform.pickFiles(
      type: FileType.custom,
      allowedExtensions: extensionAllowed,
    );
    if (result != null) {
      objFile = result.files.first.bytes!;
      double sizeInMb = result.files[0].size / (1024 * 1024);
      // show loader
      if (docType.toString() == "bankproof" || docType.toString() == "incomeproof") {
        if (result.files.first.name.contains('.pdf') || result.files.first.name.contains('.PDF')) {
          if (sizeInMb > 2) {
            // This file is Longer the
            print('file is big');
            AppConfig().dismissLoaderDialog(context); //
            AppConfig().showAlert(context, 'Maximum upload file size: 2 MB');
          } else {
            print('file is pefect');
            AppConfig().dismissLoaderDialog(context); //
            var flag = await _documentdatabloc?.saveDocument(context, docType, "large", objFile, objFile, proofType, uniqueId, _globalRespObj, result.files.first.name);
            if (flag == 0) {
              switch (id) {
                case 101:
                  print('PAN Card :$id');
                  _docTypeSubject.sink.add(DocumentType.pancard);

                  break;
                case 102:
                  print(' Signature :$id');
                  _docTypeSubject.sink.add(DocumentType.signature);

                  break;
                case 103:
                  print('Bank Proof :$id');
                  _docTypeSubject.sink.add(DocumentType.bankproof);
                  break;
                case 104:
                  print('Address Proof :$id');
                  _docTypeSubject.sink.add(DocumentType.addressproof);
                  break;
                case 105:
                  print('Income Proof :$id');
                  _docTypeSubject.sink.add(DocumentType.incomeproof);
                  break;
                case 106:
                  print('Photograph :$id');
                  _docTypeSubject.sink.add(DocumentType.photograph);
                  break;

                default:
              }
            }
          }
        } else {
          AppConfig().dismissLoaderDialog(context); //

          AppConfig().showAlert(context, "Only PDF File supported");
        }
      } else {
        if (docType.toString() == "pancard" || docType.toString() == "signature" || docType.toString() == "addressproof" || docType.toString() == "photograph") {
          if (result.files.first.name.contains('.png') || result.files.first.name.contains('.jpeg') || result.files.first.name.contains('.PNG') || result.files.first.name.contains('.JPEG') || result.files.first.name.contains('.jpg') || result.files.first.name.contains('.JPG')) {
            if (sizeInMb > 2) {
              // This file is Longer the
              print('file is big');
              AppConfig().dismissLoaderDialog(context); // show loader
              AppConfig().showAlert(context, 'Maximum upload file size: 2 MB');
            } else {
              print('file is pefect');

              final image = img.decodeImage(objFile);

              // Resize the image to a 120x? thumbnail (maintaining the aspect ratio).
              var widthsize = image!.width.toInt() / 2;
              var heightsize = image.height.toInt() / 2;
              final thumbnail = img.copyResize(image, width: widthsize.toInt(), height: heightsize.toInt());

              // Save the thumbnail as a PNG.
              List<int> filess = img.encodePng(thumbnail);
              //print(filess);
              AppConfig().dismissLoaderDialog(context); //
              var flag = await _documentdatabloc?.saveDocument(context, docType, "large", objFile, filess, proofType, uniqueId, _globalRespObj, result.files.first.name);
              if (flag == 0) {
                switch (id) {
                  case 101:
                    print('PAN Card :$id');
                    _docTypeSubject.sink.add(DocumentType.pancard);

                    break;
                  case 102:
                    print(' Signature :$id');
                    _docTypeSubject.sink.add(DocumentType.signature);

                    break;
                  case 103:
                    print('Bank Proof :$id');
                    _docTypeSubject.sink.add(DocumentType.bankproof);
                    break;
                  case 104:
                    print('Address Proof :$id');
                    _docTypeSubject.sink.add(DocumentType.addressproof);
                    break;
                  case 105:
                    print('Income Proof :$id');
                    _docTypeSubject.sink.add(DocumentType.incomeproof);
                    break;
                  case 106:
                    print('Photograph :$id');
                    _docTypeSubject.sink.add(DocumentType.photograph);
                    break;

                  default:
                }
              }
            }
          } else {
            AppConfig().dismissLoaderDialog(context); // show loader

            AppConfig().showAlert(context, "Only 'png','PNG' 'jpeg', 'JPEG' ,'jpg' ,'JPG' supported");
          }
        }
      }
    } else {
      AppConfig().dismissLoaderDialog(context); // show loader

      // documentBloc.showAlert(context, "Please upload all documents");
    }
  }
}
