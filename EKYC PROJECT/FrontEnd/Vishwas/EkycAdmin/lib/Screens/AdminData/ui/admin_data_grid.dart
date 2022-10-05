// ignore_for_file: import_of_legacy_library_into_null_safe, depend_on_referenced_packages

import 'package:ekyc_admin/Configuration/greek_navigation.dart';
import 'package:ekyc_admin/Screens/AdminData/helper/common_data_grid_source.dart';
import 'package:ekyc_admin/Screens/AdminData/models/response/common_data_grid_table_response_model.dart';
import 'package:ekyc_admin/Screens/AdminData/widgets/widgets.dart';
import 'package:ekyc_admin/Screens/home/response.dart';
import 'package:ekyc_admin/Screens/AdminData/widgets/search_dialog.dart';
import 'package:ekyc_admin/Screens/home/blocs.dart';
import 'package:ekyc_admin/Screens/home/screens/home_screen.dart';
import 'package:flutter/material.dart';
import 'package:flutter_animated_dialog/flutter_animated_dialog.dart';
import 'package:rxdart/rxdart.dart';
import 'package:syncfusion_flutter_datagrid/datagrid.dart';
import 'package:tuple/tuple.dart';

import '../../home/widgets/constants.dart';
import 'package:syncfusion_flutter_datagrid_export/export.dart';
import 'package:ekyc_admin/Screens/AdminData/helper/save_file_web.dart' as helper;
import 'package:syncfusion_flutter_xlsio/xlsio.dart' as xlsio;
import 'package:syncfusion_flutter_pdf/pdf.dart';

class AdminDataGridScreen extends StatefulWidget {
  static CommonDataGridTableResponseModel? adminDetailGlobalResponseObj;
  final String selectedScreen;
  final String title;

  AdminDataGridScreen({
    Key? key,
    required this.selectedScreen,
    required this.title,
  }) : super(key: key);

  @override
  State<AdminDataGridScreen> createState() => AdminDataGridScreenState();
}

class AdminDataGridScreenState extends State<AdminDataGridScreen> {
  AdminDataBloc? _adminDataBloc;

  final DataGridController _dataGridController = DataGridController();

  CommonDataGridSource? _dataGridSource;

  final GlobalKey<SfDataGridState> keys = GlobalKey<SfDataGridState>();
  List<Tuple2<String, double>> columnWidth = <Tuple2<String, double>>[];

  @override
  Widget build(BuildContext context) {
    _adminDataBloc ??= AdminDataBloc(context);

    return Container(
      color: const Color.fromARGB(255, 234, 233, 233),
      child: Container(
        margin: const EdgeInsets.all(14),
        decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(20),
          color: Colors.white,
        ),
        child: Container(
          margin: const EdgeInsets.symmetric(horizontal: 9, vertical: 9),
          child: Column(
            children: [
              Container(
                height: 50,
                decoration: BoxDecoration(
                  borderRadius: BorderRadius.circular(20),
                  color: Colors.white,
                ),
                padding: const EdgeInsets.symmetric(horizontal: 18.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    Text(
                      widget.title,
                      style: const TextStyle(
                        color: Color(0xff00258E),
                        fontFamily: 'Roboto',
                        fontSize: 16,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    Row(
                      children: <Widget>[
                        Row(
                          children: [
                            StreamBuilder<bool>(
                                stream: HomeScreenState.isSelectAll,
                                builder: (context, snapshot) {
                                  if (snapshot.hasData && snapshot.data != null) {
                                    return StatefulBuilder(builder: (context, myState) {
                                      return Checkbox(
                                        checkColor: Colors.black,
                                        side: MaterialStateBorderSide.resolveWith(
                                          (states) => const BorderSide(
                                            width: 1,
                                            color: Color(0xffBCBCBC),
                                          ),
                                        ),
                                        onChanged: (bool? value) {
                                          _dataGridSource?.selectAllEvent(
                                            isSelectedAll: value!,
                                          );
                                          myState(() {});
                                        },
                                        value: snapshot.data,
                                      );
                                    });
                                  } else {
                                    return Container();
                                  }
                                }),
                            const Text(
                              'Select All',
                              style: TextStyle(
                                color: Colors.black,
                                fontFamily: 'Roboto',
                                fontSize: 16,
                                fontWeight: FontWeight.bold,
                              ),
                            ),
                          ],
                        ),
                        const SizedBox(width: 20.0),
                        ElevatedButton(
                          onPressed: () {
                            // Commented by sushant for future cause
                            /*
                            final selectedDataModelList = <CommonDataGridTableResponseModel>[];
                            if (isSelectAll) {
                              selectedDataModelList.addAll(List.from(_adminDataBloc!.apiResponseList));
                            } else {
                              for (int i = 0; i < _dataGridSource!.rows.length; i++) {
                                final isSelected = _dataGridSource!.rows[i].getCells()[0].value.toString();

                                if (isSelected.toLowerCase().compareTo('true') == 0) {
                                  selectedDataModelList.add(_adminDataBloc!.apiResponseList.elementAt(i));
                                }
                              }
                            }
                            print(selectedDataModelList);
                            */
                            //Get the update values in array and sent to server for updating information and recalls API.
                            //Show Alert
                            showAnimatedDialog(
                              context: context,
                              barrierDismissible: true,
                              builder: (context) {
                                return const ActionDialog();
                              },
                              animationType: DialogTransitionType.slideFromBottom,
                              // curve: Curves.ease,
                              duration: const Duration(seconds: 1),
                            );
                          },
                          style: ElevatedButton.styleFrom(
                            primary: Colors.grey.shade200, // Background color
                          ),
                          child: const Text(
                            'Action',
                            style: TextStyle(
                              color: Color(0xff00258E),
                              fontFamily: 'Roboto',
                              fontSize: 16,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                        ),
                        const SizedBox(width: 18.0),
                        IconButton(
                          color: Colors.grey,
                          onPressed: () async {
                            //Show Alert
                            final result = await showAnimatedDialog(
                              context: context,
                              barrierDismissible: true,
                              builder: (context) {
                                return const SearchActionDialog();
                              },
                              // animationType: DialogTransitionType.slideFromBottom,
                              // // curve: Curves.ease,
                              // duration: const Duration(seconds: 1),
                            );

                            if ((result != null) && (result is Tuple5)) {
                              _dataGridSource?.searchDataTable(
                                context,
                                result.item1.toString().toLowerCase(),
                                result.item2.toString().toLowerCase(),
                                result.item3.toString().toLowerCase(),
                                result.item4.toString().toLowerCase(),
                                result.item5.toString().toLowerCase(),
                              );
                            }
                          },
                          icon: const Icon(
                            Icons.search_outlined,
                          ),
                        ),
                        const SizedBox(width: 18.0),
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
                                    var excelFileName;
                                    switch (widget.selectedScreen.toLowerCase()) {
                                      case 'otpverified':
                                        excelFileName = "OtpVerifiedReport.xlsx";
                                        break;
                                      case 'panverified':
                                        excelFileName = "PanVerifiedReport.xlsx";
                                        break;
                                      case 'inprocess':
                                        excelFileName = "InProgressReport.xlsx";
                                        break;
                                      case 'completeduser':
                                        excelFileName = "CompletedUserReport.xlsx";
                                        break;
                                      case 'finishuser':
                                        excelFileName = "FinishUserReport.xlsx";
                                        break;
                                      case 'authorizeduser':
                                        excelFileName = "AuthorizedUserReport.xlsx";
                                        break;
                                      default:
                                        excelFileName = "Report.xlsx";
                                    }
                                    //Exporting grid to excel
                                    //Exporting grid to excel
                                    SfDataGridState newstate = keys.currentState!;
                                    if (newstate.widget.columns[0].columnName == "Check") {
                                      newstate.widget.columns.removeAt(0);
                                    }
                                    xlsio.Workbook document = newstate.exportToExcelWorkbook();
                                    final List<int> bytes = document.saveAsStream();
                                    document.dispose();
                                    await helper.FileSaveHelper.saveAndLaunchFile(bytes, excelFileName);
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
                                    var pdfFileName;
                                    switch (widget.selectedScreen.toLowerCase()) {
                                      case 'otpverified':
                                        pdfFileName = "OtpVerifiedReport.pdf";
                                        break;
                                      case 'panverified':
                                        pdfFileName = "PanVerifiedReport.pdf";
                                        break;
                                      case 'inprocess':
                                        pdfFileName = "InProgressReport.pdf";
                                        break;
                                      case 'completeduser':
                                        pdfFileName = "CompletedUserReport.pdf";
                                        break;
                                      case 'finishuser':
                                        pdfFileName = "FinishUserReport.pdf";
                                        break;
                                      case 'authorizeduser':
                                        pdfFileName = "AuthorizedUserReport.pdf";
                                        break;
                                      default:
                                        pdfFileName = "Report.pdf";
                                    }
                                    //Exporting Grid Data to PDF.
                                    SfDataGridState newstate = keys.currentState!;
                                    if (newstate.widget.columns[0].columnName == "Check") {
                                      newstate.widget.columns.removeAt(0);
                                    }
                                    PdfDocument document = newstate.exportToPdfDocument(
                                      fitAllColumnsInOnePage: true,
                                      autoColumnWidth: true,
                                    );
                                    final List<int> bytes = document.saveSync();
                                    document.dispose();
                                    await helper.FileSaveHelper.saveAndLaunchFile(bytes, pdfFileName);
                                    GreekNavigator.pop(context: context);
                                  },
                                  child: const Text("PDF"),
                                ),
                              ],
                            ),
                          ),
                        ),
                        const SizedBox(width: 18.0),
                        IconButton(
                          color: Colors.grey,
                          onPressed: () {
                            HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.dashboardscreen);
                          },
                          icon: const Icon(
                            Icons.close_outlined,
                          ),
                        ),
                      ],
                    ),
                  ],
                ),
              ),
              //Grid Formation
              getGridAsPerSelectedMenu(selectedMenu: widget.selectedScreen),
            ],
          ),
        ),
      ),
    );
  }

  Widget getGridAsPerSelectedMenu({required String selectedMenu}) {
    HomeScreenState.screenfrom = "admin_data_grid";
    switch (selectedMenu.toLowerCase()) {
      case 'otpverified':
        return fetchOtpVerifiedDataGrid();
      case 'panverified':
        return fetchPanVerifiedDataGrid();
      case 'inprocess':
        return fetchInProcessdDataGrid();
      case 'completeduser':
        return fetchCompletedUsersDataGrids();
      case 'finishuser':
        return fetchFinishedUsersDataGrid();
      case 'authorizeduser':
        return fetchAuthorizedUsersDataGrid();
      default:
        //By defualt 'First time Users'
        return fetchFirstTimeUsersDataGrid();
    }
  }

  // First Time Users
  Widget fetchFirstTimeUsersDataGrid() {
    return Expanded(
      child: FutureBuilder<List<CommonDataGridTableResponseModel>>(
        future: _adminDataBloc?.getFirstTimeUserReportData(),
        builder: (context, snapshot) {
          if ((snapshot.data != null) && (snapshot.data!.isNotEmpty)) {
            final columnList = snapshot.data?.first.toJsonForFirstTimeUser().keys.toList() ?? [];
            if (columnWidth.isEmpty) {
              columnWidth = columnList.map((e) => Tuple2(e, double.nan)).toList();
            }
            final dataList = snapshot.data?.map((e) => e.toJsonForFirstTimeUser()).toList() ?? [];
            _dataGridSource = CommonDataGridSource(
              columnList: columnList,
              columnWidthList: columnWidth,
              dataList: dataList,
            );

            return SfDataGrid(
              key: keys,
              columns: _dataGridSource?.gridColumns ?? [],
              source: _dataGridSource!,
              gridLinesVisibility: GridLinesVisibility.both,
              columnWidthMode: ColumnWidthMode.fill,
              controller: _dataGridController,
              headerGridLinesVisibility: GridLinesVisibility.both,
              onSelectionChanged: (List<DataGridRow> newRows, List<DataGridRow> previousRows) {
                final index = _dataGridSource!.rows.indexOf(newRows.last);
                if (index >= 0) {
                  final selectedModelObj = snapshot.data![index];
                  AdminDataGridScreen.adminDetailGlobalResponseObj = selectedModelObj;
                }
                HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.userdetailscreen);
              },
              selectionMode: SelectionMode.singleDeselect,
              navigationMode: GridNavigationMode.row,
              allowColumnsResizing: true,
              columnResizeMode: ColumnResizeMode.onResize,
              onColumnResizeUpdate: (ColumnResizeUpdateDetails details) {
                for (int i = 0; i < columnWidth.length; i++) {
                  final element = columnWidth[i];

                  if (element.item1.toLowerCase().compareTo(details.column.columnName.toLowerCase()) == 0) {
                    Tuple2<String, double> t = Tuple2(element.item1, details.width);
                    setState(
                      () {
                        columnWidth[i] = t;
                        _dataGridSource?.columnWidthList[i] = t;
                      },
                    );
                    break;
                  }
                }
                return true;
              },
            );
          } else if (snapshot.hasError) {
            List<CommonDataGridTableResponseModel>? errorData = [CommonDataGridTableResponseModel(accountnumber: "")];
            final columnList = errorData[0].toJsonForFirstTimeUser().keys.toList();
            if (columnWidth.isEmpty) {
              columnWidth = columnList.map((e) => Tuple2(e, double.nan)).toList();
            }
            _dataGridSource = CommonDataGridSource(
              columnList: columnList,
              columnWidthList: columnWidth,
              dataList: [],
            );
            return NoDataAvailabelGridMessage(
              keys: keys,
              dataGridSource: _dataGridSource,
              dataGridController: _dataGridController,
            );
          } else {
            return const Center(
              child: CircularProgressIndicator(),
            );
          }
        },
      ),
    );
  }

//OTP Verifed
  Widget fetchOtpVerifiedDataGrid() {
    return Expanded(
      child: FutureBuilder<List<CommonDataGridTableResponseModel>>(
        future: _adminDataBloc?.getOtpVerifiedReportData(),
        builder: (context, snapshot) {
          if ((snapshot.data != null) && (snapshot.data!.isNotEmpty)) {
            final columnList = snapshot.data?.first.toJsonForOTPVerified().keys.toList() ?? [];
            if (columnWidth.isEmpty) {
              columnWidth = columnList
                  .map(
                    (e) => Tuple2(e, double.nan),
                  )
                  .toList();
            }
            final dataList = snapshot.data
                    ?.map(
                      (e) => e.toJsonForOTPVerified(),
                    )
                    .toList() ??
                [];
            _dataGridSource = CommonDataGridSource(
              columnList: columnList,
              columnWidthList: columnWidth,
              dataList: dataList,
            );
            return SfDataGrid(
              key: keys,
              columns: _dataGridSource?.gridColumns ?? [],
              source: _dataGridSource!,
              gridLinesVisibility: GridLinesVisibility.both,
              columnWidthMode: ColumnWidthMode.fill,
              controller: _dataGridController,
              headerGridLinesVisibility: GridLinesVisibility.both,
              // showCheckboxColumn: true,
              onSelectionChanged: (List<DataGridRow> newRows, List<DataGridRow> previousRows) {
                final index = _dataGridSource!.rows.indexOf(newRows.last);

                if (index >= 0) {
                  final selectedModelObj = snapshot.data![index];
                  AdminDataGridScreen.adminDetailGlobalResponseObj = selectedModelObj;
                }
                HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.userdetailscreen);
              },
              selectionMode: SelectionMode.singleDeselect,
              navigationMode: GridNavigationMode.row,
              allowColumnsResizing: true,
              columnResizeMode: ColumnResizeMode.onResize,
              onColumnResizeUpdate: (ColumnResizeUpdateDetails details) {
                for (int i = 0; i < columnWidth.length; i++) {
                  final element = columnWidth[i];

                  if (element.item1.toLowerCase().compareTo(details.column.columnName.toLowerCase()) == 0) {
                    Tuple2<String, double> t = Tuple2(element.item1, details.width);
                    setState(
                      () {
                        columnWidth[i] = t;
                        _dataGridSource?.columnWidthList[i] = t;
                      },
                    );
                    break;
                  }
                }
                return true;
              },
            );
          } else if (snapshot.hasError) {
            List<CommonDataGridTableResponseModel>? errorData = [CommonDataGridTableResponseModel(accountnumber: "")];
            final columnList = errorData[0].toJsonForOTPVerified().keys.toList();
            if (columnWidth.isEmpty) {
              columnWidth = columnList.map((e) => Tuple2(e, double.nan)).toList();
            }
            _dataGridSource = CommonDataGridSource(
              columnList: columnList,
              columnWidthList: columnWidth,
              dataList: [],
            );
            return NoDataAvailabelGridMessage(
              keys: keys,
              dataGridSource: _dataGridSource,
              dataGridController: _dataGridController,
            );
          } else {
            return const Center(
              child: CircularProgressIndicator(),
            );
          }
        },
      ),
    );
  }

  //Pan Verified
  Expanded fetchPanVerifiedDataGrid() {
    return Expanded(
      child: FutureBuilder<List<CommonDataGridTableResponseModel>>(
        future: _adminDataBloc?.getPanVerifiedReportData(),
        builder: (context, snapshot) {
          if ((snapshot.data != null) && (snapshot.data!.isNotEmpty)) {
            final columnList = snapshot.data?.first.toJsonForPANVerified().keys.toList() ?? [];
            if (columnWidth.isEmpty) {
              columnWidth = columnList.map((e) => Tuple2(e, double.nan)).toList();
            }
            final dataList = snapshot.data
                    ?.map(
                      (e) => e.toJsonForPANVerified(),
                    )
                    .toList() ??
                [];
            _dataGridSource = CommonDataGridSource(
              columnList: columnList,
              columnWidthList: columnWidth,
              dataList: dataList,
            );
            return SfDataGrid(
              key: keys,
              columns: _dataGridSource?.gridColumns ?? [],
              source: _dataGridSource!,
              gridLinesVisibility: GridLinesVisibility.both,
              columnWidthMode: ColumnWidthMode.fill,
              controller: _dataGridController,
              headerGridLinesVisibility: GridLinesVisibility.both,
              // showCheckboxColumn: true,
              onSelectionChanged: (List<DataGridRow> newRows, List<DataGridRow> previousRows) {
                final index = _dataGridSource!.rows.indexOf(newRows.last);

                if (index >= 0) {
                  final selectedModelObj = snapshot.data![index];
                  AdminDataGridScreen.adminDetailGlobalResponseObj = selectedModelObj;
                }
                HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.userdetailscreen);
              },
              selectionMode: SelectionMode.singleDeselect,
              navigationMode: GridNavigationMode.row,
              allowColumnsResizing: true,
              columnResizeMode: ColumnResizeMode.onResize,
              onColumnResizeUpdate: (ColumnResizeUpdateDetails details) {
                for (int i = 0; i < columnWidth.length; i++) {
                  final element = columnWidth[i];

                  if (element.item1.toLowerCase().compareTo(details.column.columnName.toLowerCase()) == 0) {
                    Tuple2<String, double> t = Tuple2(element.item1, details.width);
                    setState(
                      () {
                        columnWidth[i] = t;
                        _dataGridSource?.columnWidthList[i] = t;
                      },
                    );
                    break;
                  }
                }
                return true;
              },
            );
          } else if (snapshot.hasError) {
            List<CommonDataGridTableResponseModel>? errorData = [CommonDataGridTableResponseModel(accountnumber: "")];
            final columnList = errorData[0].toJsonForPANVerified().keys.toList();
            if (columnWidth.isEmpty) {
              columnWidth = columnList.map((e) => Tuple2(e, double.nan)).toList();
            }
            _dataGridSource = CommonDataGridSource(
              columnList: columnList,
              columnWidthList: columnWidth,
              dataList: [],
            );
            return NoDataAvailabelGridMessage(
              keys: keys,
              dataGridSource: _dataGridSource,
              dataGridController: _dataGridController,
            );
          } else {
            return const Center(
              child: CircularProgressIndicator(),
            );
          }
        },
      ),
    );
  }

  //In Process
  Widget fetchInProcessdDataGrid() {
    return Expanded(
      child: FutureBuilder<List<CommonDataGridTableResponseModel>>(
        future: _adminDataBloc?.getInProcessReportData(),
        builder: (context, snapshot) {
          if ((snapshot.data != null) && (snapshot.data!.isNotEmpty)) {
            final columnList = snapshot.data?.first.toJsonForINProcess().keys.toList() ?? [];
            if (columnWidth.isEmpty) {
              columnWidth = columnList
                  .map(
                    (e) => Tuple2(e, double.nan),
                  )
                  .toList();
            }
            final dataList = snapshot.data
                    ?.map(
                      (e) => e.toJsonForINProcess(),
                    )
                    .toList() ??
                [];
            _dataGridSource = CommonDataGridSource(
              columnList: columnList,
              columnWidthList: columnWidth,
              dataList: dataList,
            );
            return SfDataGrid(
              key: keys,
              columns: _dataGridSource?.gridColumns ?? [],
              source: _dataGridSource!,
              gridLinesVisibility: GridLinesVisibility.both,
              columnWidthMode: ColumnWidthMode.fill,
              controller: _dataGridController,
              headerGridLinesVisibility: GridLinesVisibility.both,
              // showCheckboxColumn: true,
              onSelectionChanged: (List<DataGridRow> newRows, List<DataGridRow> previousRows) {
                final index = _dataGridSource!.rows.indexOf(newRows.last);

                if (index >= 0) {
                  final selectedModelObj = snapshot.data![index];
                  AdminDataGridScreen.adminDetailGlobalResponseObj = selectedModelObj;
                }
                HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.userdetailscreen);
              },
              selectionMode: SelectionMode.singleDeselect,
              navigationMode: GridNavigationMode.row,
              allowColumnsResizing: true,
              columnResizeMode: ColumnResizeMode.onResize,
              onColumnResizeUpdate: (ColumnResizeUpdateDetails details) {
                for (int i = 0; i < columnWidth.length; i++) {
                  final element = columnWidth[i];

                  if (element.item1.toLowerCase().compareTo(details.column.columnName.toLowerCase()) == 0) {
                    Tuple2<String, double> t = Tuple2(element.item1, details.width);
                    setState(
                      () {
                        columnWidth[i] = t;
                        _dataGridSource?.columnWidthList[i] = t;
                      },
                    );
                    break;
                  }
                }
                return true;
              },
            );
          } else if (snapshot.hasError) {
            List<CommonDataGridTableResponseModel>? errorData = [CommonDataGridTableResponseModel(accountnumber: "")];
            final columnList = errorData[0].toJsonForINProcess().keys.toList();
            if (columnWidth.isEmpty) {
              columnWidth = columnList.map((e) => Tuple2(e, double.nan)).toList();
            }
            _dataGridSource = CommonDataGridSource(
              columnList: columnList,
              columnWidthList: columnWidth,
              dataList: [],
            );
            return NoDataAvailabelGridMessage(
              keys: keys,
              dataGridSource: _dataGridSource,
              dataGridController: _dataGridController,
            );
          } else {
            return const Center(
              child: CircularProgressIndicator(),
            );
          }
        },
      ),
    );
  }

  //Completed Users
  Widget fetchCompletedUsersDataGrids() {
    return Expanded(
      child: FutureBuilder<List<CommonDataGridTableResponseModel>>(
        future: _adminDataBloc?.getCompletedUsersReportData(),
        builder: (context, snapshot) {
          if ((snapshot.data != null) && (snapshot.data!.isNotEmpty)) {
            final columnList = snapshot.data?.first.toJsonForCompletedUsers().keys.toList() ?? [];
            if (columnWidth.isEmpty) {
              columnWidth = columnList
                  .map(
                    (e) => Tuple2(e, double.nan),
                  )
                  .toList();
            }
            final dataList = snapshot.data
                    ?.map(
                      (e) => e.toJsonForCompletedUsers(),
                    )
                    .toList() ??
                [];
            _dataGridSource = CommonDataGridSource(
              columnList: columnList,
              columnWidthList: columnWidth,
              dataList: dataList,
            );
            return SfDataGrid(
              key: keys,
              columns: _dataGridSource?.gridColumns ?? [],
              source: _dataGridSource!,
              gridLinesVisibility: GridLinesVisibility.both,
              columnWidthMode: ColumnWidthMode.fill,
              controller: _dataGridController,
              headerGridLinesVisibility: GridLinesVisibility.both,
              // showCheckboxColumn: true,
              onSelectionChanged: (List<DataGridRow> newRows, List<DataGridRow> previousRows) {
                final index = _dataGridSource!.rows.indexOf(newRows.last);

                if (index >= 0) {
                  final selectedModelObj = snapshot.data![index];
                  AdminDataGridScreen.adminDetailGlobalResponseObj = selectedModelObj;
                }
                HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.userdetailscreen);
              },
              selectionMode: SelectionMode.singleDeselect,
              navigationMode: GridNavigationMode.row,
              allowColumnsResizing: true,
              columnResizeMode: ColumnResizeMode.onResize,
              onColumnResizeUpdate: (ColumnResizeUpdateDetails details) {
                for (int i = 0; i < columnWidth.length; i++) {
                  final element = columnWidth[i];

                  if (element.item1.toLowerCase().compareTo(details.column.columnName.toLowerCase()) == 0) {
                    Tuple2<String, double> t = Tuple2(element.item1, details.width);
                    setState(
                      () {
                        columnWidth[i] = t;
                        _dataGridSource?.columnWidthList[i] = t;
                      },
                    );
                    break;
                  }
                }
                return true;
              },
            );
          } else if (snapshot.hasError) {
            List<CommonDataGridTableResponseModel>? errorData = [CommonDataGridTableResponseModel(accountnumber: "")];
            final columnList = errorData[0].toJsonForCompletedUsers().keys.toList();
            if (columnWidth.isEmpty) {
              columnWidth = columnList.map((e) => Tuple2(e, double.nan)).toList();
            }
            _dataGridSource = CommonDataGridSource(
              columnList: columnList,
              columnWidthList: columnWidth,
              dataList: [],
            );
            return NoDataAvailabelGridMessage(
              keys: keys,
              dataGridSource: _dataGridSource,
              dataGridController: _dataGridController,
            );
          } else {
            return const Center(
              child: CircularProgressIndicator(),
            );
          }
        },
      ),
    );
  }

  //Finish Users
  Widget fetchFinishedUsersDataGrid() {
    return Expanded(
      child: FutureBuilder<List<CommonDataGridTableResponseModel>>(
        future: _adminDataBloc?.getFinishedUsersReportData(),
        builder: (context, snapshot) {
          if ((snapshot.data != null) && (snapshot.data!.isNotEmpty)) {
            final columnList = snapshot.data?.first.toJsonForFinishUsers().keys.toList() ?? [];
            if (columnWidth.isEmpty) {
              columnWidth = columnList
                  .map(
                    (e) => Tuple2(e, double.nan),
                  )
                  .toList();
            }
            final dataList = snapshot.data
                    ?.map(
                      (e) => e.toJsonForFinishUsers(),
                    )
                    .toList() ??
                [];
            _dataGridSource = CommonDataGridSource(
              columnList: columnList,
              dataList: dataList,
              columnWidthList: columnWidth,
            );
            return SfDataGrid(
              key: keys,
              columns: _dataGridSource?.gridColumns ?? [],
              source: _dataGridSource!,
              gridLinesVisibility: GridLinesVisibility.both,
              columnWidthMode: ColumnWidthMode.fill,
              controller: _dataGridController,
              headerGridLinesVisibility: GridLinesVisibility.both,
              // showCheckboxColumn: true,
              onSelectionChanged: (List<DataGridRow> newRows, List<DataGridRow> previousRows) {
                final index = _dataGridSource!.rows.indexOf(newRows.last);

                if (index >= 0) {
                  final selectedModelObj = snapshot.data![index];
                  AdminDataGridScreen.adminDetailGlobalResponseObj = selectedModelObj;
                }
                HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.userdetailscreen);
              },
              selectionMode: SelectionMode.singleDeselect,
              navigationMode: GridNavigationMode.row,
              allowColumnsResizing: true,
              columnResizeMode: ColumnResizeMode.onResize,
              onColumnResizeUpdate: (ColumnResizeUpdateDetails details) {
                for (int i = 0; i < columnWidth.length; i++) {
                  final element = columnWidth[i];

                  if (element.item1.toLowerCase().compareTo(details.column.columnName.toLowerCase()) == 0) {
                    Tuple2<String, double> t = Tuple2(element.item1, details.width);
                    setState(
                      () {
                        columnWidth[i] = t;
                        _dataGridSource?.columnWidthList[i] = t;
                      },
                    );
                    break;
                  }
                }
                return true;
              },
            );
          } else if (snapshot.hasError) {
            List<CommonDataGridTableResponseModel>? errorData = [CommonDataGridTableResponseModel(accountnumber: "")];
            final columnList = errorData[0].toJsonForFinishUsers().keys.toList();
            if (columnWidth.isEmpty) {
              columnWidth = columnList.map((e) => Tuple2(e, double.nan)).toList();
            }
            _dataGridSource = CommonDataGridSource(
              columnList: columnList,
              columnWidthList: columnWidth,
              dataList: [],
            );
            return NoDataAvailabelGridMessage(
              keys: keys,
              dataGridSource: _dataGridSource,
              dataGridController: _dataGridController,
            );
          } else {
            return const Center(
              child: CircularProgressIndicator(),
            );
          }
        },
      ),
    );
  }

  //Authorize Users
  Widget fetchAuthorizedUsersDataGrid() {
    return Expanded(
      child: FutureBuilder<List<CommonDataGridTableResponseModel>>(
        future: _adminDataBloc?.getAuthoizedUsersReportData(),
        builder: (context, snapshot) {
          if ((snapshot.data != null) && (snapshot.data!.isNotEmpty)) {
            final columnList = snapshot.data?.first.toJsonForAuthorizeUsers().keys.toList() ?? [];
            if (columnWidth.isEmpty) {
              columnWidth = columnList.map((e) => Tuple2(e, double.nan)).toList();
            }
            final dataList = snapshot.data?.map((e) => e.toJsonForAuthorizeUsers()).toList() ?? [];
            _dataGridSource = CommonDataGridSource(
              columnList: columnList,
              columnWidthList: columnWidth,
              dataList: dataList,
            );

            return SfDataGrid(
              key: keys,
              columns: _dataGridSource?.gridColumns ?? [],
              source: _dataGridSource!,
              gridLinesVisibility: GridLinesVisibility.both,
              columnWidthMode: ColumnWidthMode.fill,
              controller: _dataGridController,
              headerGridLinesVisibility: GridLinesVisibility.both,
              // showCheckboxColumn: true,
              onSelectionChanged: (List<DataGridRow> newRows, List<DataGridRow> previousRows) {
                final index = _dataGridSource!.rows.indexOf(newRows.last);
                if (index >= 0) {
                  final selectedModelObj = snapshot.data![index];
                  AdminDataGridScreen.adminDetailGlobalResponseObj = selectedModelObj;
                }
                HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.userdetailscreen);
              },
              selectionMode: SelectionMode.singleDeselect,
              navigationMode: GridNavigationMode.row,
              allowColumnsResizing: true,
              columnResizeMode: ColumnResizeMode.onResize,
              onColumnResizeUpdate: (ColumnResizeUpdateDetails details) {
                for (int i = 0; i < columnWidth.length; i++) {
                  final element = columnWidth[i];

                  if (element.item1.toLowerCase().compareTo(details.column.columnName.toLowerCase()) == 0) {
                    Tuple2<String, double> t = Tuple2(element.item1, details.width);
                    setState(
                      () {
                        columnWidth[i] = t;
                        _dataGridSource?.columnWidthList[i] = t;
                      },
                    );
                    break;
                  }
                }
                return true;
              },
            );
          } else if (snapshot.hasError) {
            List<CommonDataGridTableResponseModel>? errorData = [CommonDataGridTableResponseModel(accountnumber: "")];
            final columnList = errorData[0].toJsonForAuthorizeUsers().keys.toList();
            if (columnWidth.isEmpty) {
              columnWidth = columnList.map((e) => Tuple2(e, double.nan)).toList();
            }
            _dataGridSource = CommonDataGridSource(
              columnList: columnList,
              columnWidthList: columnWidth,
              dataList: [],
            );
            return NoDataAvailabelGridMessage(
              keys: keys,
              dataGridSource: _dataGridSource,
              dataGridController: _dataGridController,
            );
          } else {
            return const Center(
              child: CircularProgressIndicator(),
            );
          }
        },
      ),
    );
  }
}
