import 'package:ekyc_admin/Configuration/greek_navigation.dart';
import 'package:ekyc_admin/Screens/home/screen.dart';
import 'package:flutter/material.dart';

import '../../../home/widgets/constants.dart';
// import 'package:ekyc_admin/Screens/AdminData/helper/save_file_web.dart' as helper;
// import 'package:syncfusion_flutter_xlsio/xlsio.dart' as xlsio;

class BoCodeMasterWebScreen extends StatelessWidget {
  const BoCodeMasterWebScreen({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      color: const Color(0xffF2F2F2),
      child: Container(
        margin: const EdgeInsets.all(18.0),
        decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(10.0),
          color: Colors.white,
        ),
        child: Column(
          children: [
            Row(
              children: [
                Expanded(
                  child: Container(
                    // margin: const EdgeInsets.symmetric(horizontal: 25, vertical: 25),
                    decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(8.0),
                      color: Colors.white,
                      boxShadow: [
                        BoxShadow(
                          color: Colors.grey,
                          blurRadius: 1.2,
                          offset: Offset(0.0, 1.0),
                        )
                      ],
                    ),
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: <Widget>[
                        const Padding(
                          padding: EdgeInsets.only(left: 14.0),
                          child: Text(
                            'BO CODE',
                            style: TextStyle(
                              color: Color(0xff00258E),
                              fontFamily: 'Roboto',
                              fontSize: 16,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                        ),
                        Row(
                          children: [
                            ShowPopOverDialog(
                              barrierColor: Colors.transparent,
                              view: const ImageIcon(
                                AssetImage("assets/images/download.png"),
                                color: Colors.grey,
                                size: 20,
                              ),
                              widgetList: SizedBox(
                                child: Column(
                                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                  children: [
                                    InkWell(
                                      focusColor: Colors.transparent,
                                      hoverColor: Colors.transparent,
                                      splashColor: Colors.transparent,
                                      highlightColor: Colors.transparent,
                                      onTap: () async {
                                        print("Excel");
                                        //Exporting grid to excel
                                        // final xlsio.Workbook workbook = key.currentState!.exportToExcelWorkbook();
                                        // final List<int> bytes = workbook.saveAsStream();
                                        // workbook.dispose();
                                        // await helper.FileSaveHelper.saveAndLaunchFile(bytes, 'DataGrid.xlsx');
                                        //TODO: Format the excel as you wish.
                                        GreekNavigator.pop(context: context);
                                      },
                                      child: const Text("Excel"),
                                    ),
                                    InkWell(
                                      focusColor: Colors.transparent,
                                      hoverColor: Colors.transparent,
                                      splashColor: Colors.transparent,
                                      highlightColor: Colors.transparent,
                                      onTap: () async {
                                        //Exporting Grid Data to PDF.
                                        // PdfDocument document = key.currentState!.exportToPdfDocument();
                                        // final List<int> bytes = document.saveSync();
                                        // document.dispose();
                                        // await helper.FileSaveHelper.saveAndLaunchFile(bytes, 'DataGridPdf.pdf');
                                        GreekNavigator.pop(context: context);
                                      },
                                      child: const Text("PDF"),
                                    ),
                                  ],
                                ),
                              ),
                            ),
                            const SizedBox(width: 20.0),
                            Padding(
                              padding: const EdgeInsets.only(right: 20.0),
                              child: IconButton(
                                color: Colors.grey,
                                onPressed: () {
                                  HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.dashboardscreen);
                                },
                                icon: const Icon(
                                  Icons.close_outlined,
                                ),
                              ),
                            ),
                          ],
                        ),
                      ],
                    ),
                  ),
                ),
              ],
            ),
            const SizedBox(height: 15),
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                Row(
                  children: [
                    ElevatedButton(
                      onPressed: () {},
                      style: ButtonStyle(
                        backgroundColor: MaterialStateProperty.all<Color>(const Color(0xff00258E)),
                        fixedSize: MaterialStateProperty.all<Size>(const Size(160, 40)),
                        shape: MaterialStateProperty.all<RoundedRectangleBorder>(
                          RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(30),
                          ),
                        ),
                      ),
                      child: const Text(
                        'Add Bo Code',
                        style: TextStyle(
                          letterSpacing: 0.8,
                          color: Colors.white,
                          fontFamily: 'Roboto',
                          fontSize: 16,
                          fontWeight: FontWeight.w600,
                        ),
                        textAlign: TextAlign.start,
                      ),
                    ),
                    const SizedBox(width: 30),
                    ElevatedButton(
                      onPressed: () {},
                      style: ButtonStyle(
                        backgroundColor: MaterialStateProperty.all<Color>(Colors.grey),
                        fixedSize: MaterialStateProperty.all<Size>(const Size(160, 40)),
                        shape: MaterialStateProperty.all<RoundedRectangleBorder>(
                          RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(30),
                          ),
                        ),
                      ),
                      child: const Text(
                        'Bo Code Details',
                        style: TextStyle(
                          letterSpacing: 0.8,
                          color: Colors.black,
                          fontFamily: 'Roboto',
                          fontSize: 16,
                          fontWeight: FontWeight.w600,
                        ),
                        textAlign: TextAlign.start,
                      ),
                    ),
                  ],
                ),
              ],
            ),
            const SizedBox(height: 35.0),
            Container(
              height: 270,
              width: 700,
              decoration: BoxDecoration(
                borderRadius: BorderRadius.circular(20),
                color: Colors.white,
                boxShadow: [
                  const BoxShadow(
                    color: Colors.grey,
                    blurRadius: 1.2,
                    offset: Offset(0, 0.5),
                  )
                ],
              ),
              child: Column(
                children: [
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    children: [
                      Container(
                        width: 180,
                        height: 40,
                        margin: const EdgeInsets.symmetric(horizontal: 10, vertical: 20),
                        child: const TextField(
                          style: TextStyle(fontSize: 14.0, fontFamily: 'Roboto', color: Color(0xff3E3E3E)),
                          textAlign: TextAlign.start,
                          keyboardType: TextInputType.text,
                          decoration: InputDecoration(
                            border: OutlineInputBorder(borderRadius: BorderRadius.all(Radius.circular(6.0))),
                            labelText: 'Prefix',
                            // hintText: 'Enter Name Here',
                          ),
                          autofocus: false,
                        ),
                      ),
                      Container(
                        width: 180,
                        height: 40,
                        margin: const EdgeInsets.symmetric(horizontal: 10, vertical: 15),
                        child: const TextField(
                          style: TextStyle(fontSize: 14.0, fontFamily: 'Roboto', color: Color(0xff3E3E3E)),
                          textAlign: TextAlign.start,
                          keyboardType: TextInputType.text,
                          decoration: InputDecoration(
                            border: OutlineInputBorder(borderRadius: BorderRadius.all(Radius.circular(6.0))),
                            labelText: 'End Number',
                            // hintText: 'Enter Name Here',
                          ),
                          autofocus: false,
                        ),
                      ),
                      Container(
                        width: 180,
                        height: 40,
                        margin: const EdgeInsets.symmetric(horizontal: 10, vertical: 15),
                        child: const TextField(
                          style: TextStyle(fontSize: 14.0, fontFamily: 'Roboto', color: Color(0xff3E3E3E)),
                          textAlign: TextAlign.start,
                          keyboardType: TextInputType.text,
                          decoration: InputDecoration(
                            border: OutlineInputBorder(borderRadius: BorderRadius.all(Radius.circular(6.0))),
                            labelText: 'Start Number',
                            // hintText: 'Enter Name Here',
                          ),
                          autofocus: false,
                        ),
                      ),
                    ],
                  ),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    children: [
                      Container(
                        width: 180,
                        height: 40,
                        margin: const EdgeInsets.symmetric(horizontal: 10),
                        child: const TextField(
                          style: TextStyle(fontSize: 14.0, fontFamily: 'Roboto', color: Color(0xff3E3E3E)),
                          textAlign: TextAlign.start,
                          keyboardType: TextInputType.text,
                          decoration: InputDecoration(
                            border: OutlineInputBorder(borderRadius: BorderRadius.all(Radius.circular(6.0))),
                            labelText: 'Group Code',
                            // hintText: 'Enter Name Here',
                          ),
                          autofocus: false,
                        ),
                      ),
                      Container(
                        width: 180,
                        height: 40,
                        margin: const EdgeInsets.symmetric(horizontal: 10),
                        child: const TextField(
                          style: TextStyle(fontSize: 14.0, fontFamily: 'Roboto', color: Color(0xff3E3E3E)),
                          textAlign: TextAlign.start,
                          keyboardType: TextInputType.text,
                          decoration: InputDecoration(
                            border: OutlineInputBorder(borderRadius: BorderRadius.all(Radius.circular(6.0))),
                            labelText: 'Branch Code',
                            // hintText: 'Enter Name Here',
                          ),
                          autofocus: false,
                        ),
                      ),
                      Container(
                        width: 180,
                        height: 40,
                        margin: const EdgeInsets.symmetric(horizontal: 10),
                        child: const TextField(
                          style: TextStyle(fontSize: 14.0, fontFamily: 'Roboto', color: Color(0xff3E3E3E)),
                          textAlign: TextAlign.start,
                          keyboardType: TextInputType.text,
                          decoration: InputDecoration(
                            border: OutlineInputBorder(borderRadius: BorderRadius.all(Radius.circular(6.0))),
                            labelText: 'Type',
                            // hintText: 'Enter Name Here',
                          ),
                          autofocus: false,
                        ),
                      ),
                    ],
                  ),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: <Widget>[
                      Container(
                        width: 180,
                        height: 40,
                        margin: const EdgeInsets.symmetric(horizontal: 10, vertical: 20),
                        child: const TextField(
                          style: TextStyle(fontSize: 14.0, fontFamily: 'Roboto', color: Color(0xff3E3E3E)),
                          textAlign: TextAlign.start,
                          keyboardType: TextInputType.text,
                          decoration: InputDecoration(
                            border: OutlineInputBorder(borderRadius: BorderRadius.all(Radius.circular(6.0))),
                            labelText: 'Referral Code',
                            // hintText: 'Enter Name Here',
                          ),
                          autofocus: false,
                        ),
                      ),
                    ],
                  ),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: <Widget>[
                      Container(
                        width: 180,
                        height: 40,
                        margin: const EdgeInsets.symmetric(horizontal: 10, vertical: 10),
                        child: ElevatedButton(
                          onPressed: () {},
                          style: ButtonStyle(
                            backgroundColor: MaterialStateProperty.all<Color>(const Color(0xff00258E)),
                            fixedSize: MaterialStateProperty.all<Size>(const Size(160, 40)),
                            shape: MaterialStateProperty.all<RoundedRectangleBorder>(
                              RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(30),
                              ),
                            ),
                          ),
                          child: const Text(
                            'Search',
                            style: TextStyle(
                              letterSpacing: 0.8,
                              color: Colors.white,
                              fontFamily: 'Roboto',
                              fontSize: 16,
                              fontWeight: FontWeight.w600,
                            ),
                            textAlign: TextAlign.start,
                          ),
                        ),
                      ),
                    ],
                  )
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
