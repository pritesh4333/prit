import 'package:ekyc_admin/Configuration/app_config.dart';
import 'package:ekyc_admin/Screens/AdminData/helper/common_data_grid_source.dart';
import 'package:ekyc_admin/Screens/AdminData/ui/admin_data_grid.dart';
import 'package:ekyc_admin/Screens/AdminData/widgets/search_dialog.dart';
import 'package:ekyc_admin/Screens/AdminData/widgets/widgets.dart';
import 'package:ekyc_admin/Screens/home/blocs.dart';
import 'package:ekyc_admin/Screens/home/response.dart';
import 'package:ekyc_admin/Screens/home/screens/home_screen.dart';
import 'package:ekyc_admin/Screens/home/widgets/constants.dart';
import 'package:flutter/material.dart';
import 'package:flutter_animated_dialog/flutter_animated_dialog.dart';
import 'package:syncfusion_flutter_datagrid/datagrid.dart';
import 'package:tuple/tuple.dart';

import '../../DashBoardScreens/model/DashBoardResponseModel.dart';

class AllReportsWebScreen extends StatefulWidget {
  final String title;
  const AllReportsWebScreen({
    Key? key,
    required this.title,
  }) : super(key: key);

  @override
  State<AllReportsWebScreen> createState() => AllReportsWebScreenState();
}

class AllReportsWebScreenState extends State<AllReportsWebScreen> {
  AdminDataBloc? _adminDataBloc;

  final DataGridController _dataGridController = DataGridController();

  CommonDataGridSource? _dataGridSource;

  final GlobalKey<SfDataGridState> keys = GlobalKey<SfDataGridState>();
  List<Tuple2<String, double>> columnWidth = <Tuple2<String, double>>[];

  @override
  Widget build(BuildContext context) {
    _adminDataBloc = AdminDataBloc(context);
    return Container(
      color: const Color.fromARGB(255, 234, 233, 233),
      child: Container(
        margin: const EdgeInsets.all(14),
        decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(20),
          color: Colors.white,
        ),
        child: Column(
          children: [
            Container(
              height: 50,
              decoration: BoxDecoration(
                borderRadius: BorderRadius.circular(8.0),
                color: Colors.white,
                boxShadow: const [
                  BoxShadow(
                    color: Colors.grey,
                    blurRadius: 1.2,
                    offset: Offset(0.0, 1.0),
                  )
                ],
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
                          'Send Mail',
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
                            animationType: DialogTransitionType.slideFromBottom,
                            // curve: Curves.ease,
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
                                  /*
                                  //Exporting grid to excel
                                  final xlsio.Workbook workbook = keys.currentState!.exportToExcelWorkbook();
                                  final List<int> bytes = workbook.saveAsStream();
                                  workbook.dispose();
                                  await helper.FileSaveHelper.saveAndLaunchFile(bytes, 'DataGrid.xlsx');
                                  //TODO: Format the excel as you wish.
                                  GreekNavigator.pop(context: context);
                                  */
                                },
                                child: const Text("Excel"),
                              ),
                              InkWell(
                                focusColor: Colors.transparent,
                                hoverColor: Colors.transparent,
                                splashColor: Colors.transparent,
                                highlightColor: Colors.transparent,
                                onTap: () async {
                                  /*
                                  //Exporting Grid Data to PDF.
                                  SfDataGridState newstate = keys.currentState!;
if (newstate.widget.columns[0].columnName == "Check") {
                                    newstate.widget.columns.removeAt(0);
                                  }                                    PdfDocument document = newstate.exportToPdfDocument();
                                    final List<int> bytes = document.saveSync();
                                    document.dispose();
                                  await helper.FileSaveHelper.saveAndLaunchFile(bytes, 'DataGridPdf.pdf');
                                  GreekNavigator.pop(context: context);
                                  */
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
            getReportAsMenuSelected(),
          ],
        ),
      ),
    );
  }

  Expanded getReportAsMenuSelected() {
    HomeScreenState.screenfrom = "report_data_grid";

    switch (AppConfig().selectedReportType) {
      case "globalsearch":
        return getGlobalSearchData();
      case "paymentreport":
        return getPaymentReport();
      case "dataanalysisreport":
        return getDataAnalysisReport();
      case "reipvreport":
        return getReIpvReport();
      case "authorizeuserreport":
        return getAuthorizeUserReport();
      case "rejectionreport":
        return getRejectionReport();
      case "pennydropreport":
        return getPennyDropReportData();
      default:
        //statusreport
        return getStatusReportData();
    }
  }

//[STATUS REPORT]
  Expanded getStatusReportData() {
    return Expanded(
      child: FutureBuilder<List<CommonDataGridTableResponseModel>>(
        future: _adminDataBloc?.getStatusReportData(),
        builder: (context, snapshot) {
          if ((snapshot.data != null) && (snapshot.data!.isNotEmpty)) {
            final columnList = snapshot.data?.first.toJsonForStatusReport().keys.toList() ?? [];
            if (columnWidth.isEmpty) {
              columnWidth = columnList.map((e) => Tuple2(e, double.nan)).toList();
            }
            final dataList = snapshot.data?.map((e) => e.toJsonForStatusReport()).toList() ?? [];
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
            final columnList = errorData[0].toJsonForStatusReport().keys.toList();
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

// [GLOBAL SEARCH]
  Expanded getGlobalSearchData() {
    return Expanded(
      child: FutureBuilder<List<CommonDataGridTableResponseModel>>(
        future: _adminDataBloc?.getGlobalSearchReport(),
        builder: (context, snapshot) {
          if ((snapshot.data != null) && (snapshot.data!.isNotEmpty)) {
            final columnList = snapshot.data?.first.toJsonForGlobalSearchReport().keys.toList() ?? [];
            if (columnWidth.isEmpty) {
              columnWidth = columnList.map((e) => Tuple2(e, double.nan)).toList();
            }
            final dataList = snapshot.data?.map((e) => e.toJsonForGlobalSearchReport()).toList() ?? [];
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
            {
              List<CommonDataGridTableResponseModel>? errorData = [CommonDataGridTableResponseModel(accountnumber: "")];
              final columnList = errorData[0].toJsonForGlobalSearchReport().keys.toList();
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
            }
          } else {
            return const Center(
              child: CircularProgressIndicator(),
            );
          }
        },
      ),
    );
  }

// PAYMENT REPORT
  Expanded getPaymentReport() {
    return Expanded(
      child: FutureBuilder<List<CommonDataGridTableResponseModel>>(
        future: _adminDataBloc?.getPaymentReport(),
        builder: (context, snapshot) {
          if ((snapshot.data != null) && (snapshot.data!.isNotEmpty)) {
            final columnList = snapshot.data?.first.toJsonForPaymentReport().keys.toList() ?? [];
            if (columnWidth.isEmpty) {
              columnWidth = columnList.map((e) => Tuple2(e, double.nan)).toList();
            }
            final dataList = snapshot.data?.map((e) => e.toJsonForPaymentReport()).toList() ?? [];
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
            {
              List<CommonDataGridTableResponseModel>? errorData = [CommonDataGridTableResponseModel(accountnumber: "")];
              final columnList = errorData[0].toJsonForPaymentReport().keys.toList();
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
            }
          } else {
            return const Center(
              child: CircularProgressIndicator(),
            );
          }
        },
      ),
    );
  }

  // DATA ANALYSIS REPORT
  Expanded getDataAnalysisReport() {
    return Expanded(
      child: FutureBuilder<List<DashBoardResponseModel>?>(
        future: _adminDataBloc?.getAllStats(),
        builder: (context, snapshot) {
          if ((snapshot.data != null) && (snapshot.hasData)) {
            List<String> columnList = [];

            columnList.add("OTP Genrated");
            columnList.add("OTP Verified");
            columnList.add("PAN Verified");
            columnList.add("Under Process");
            columnList.add("Finish");
            columnList.add("Total");
            columnList.add("Mismatch");
            if (columnWidth.isEmpty) {
              columnWidth = columnList.map((e) => Tuple2(e, double.nan)).toList();
            }
            final List<Map<String, String?>> dataList = [];
            String panvarifycount = "0";

            String firstusercount = "0";

            String otpvarifiedcount = "0";

            String inproceescount = "0";

            String completedusercount = "0";

            String finishusercount = "0";

            String authorizedusercount = "0";

            for (final element in (snapshot.data ?? [])) {
              if (element.reporttitle == "First Time User") {
                firstusercount = element.count?.toString() ?? "0";
              }

              if (element.reporttitle == "PAN VERIFIED") {
                panvarifycount = element.count?.toString() ?? "0";
              }

              if (element.reporttitle == "OTP NOT VERIFIED" || element.reporttitle == "OTP VERIFIED") {
                otpvarifiedcount = element.count?.toString() ?? "0";
              }

              if (element.reporttitle == "IN PROCESS") {
                inproceescount = element.count?.toString() ?? "0";
              }

              if (element.reporttitle == "COMPLETED USERS") {
                completedusercount = element.count?.toString() ?? "0";
              }

              if (element.reporttitle == "FINISH USERS") {
                finishusercount = element.count?.toString() ?? "0";
              }

              if (element.reporttitle == "AUTHORIZED USERS") {
                authorizedusercount = element.count?.toString() ?? "0";
              }
            }
            final Map<String, String> data = <String, String>{};
            data["OTP Genrated"] = firstusercount;
            data["OTP Verified"] = otpvarifiedcount;
            data["PAN Verified"] = panvarifycount;
            data['Under Process'] = inproceescount;
            data['Finish'] = completedusercount;
            data['Total'] = finishusercount;
            data['Mismatch'] = "0";
            dataList.add(data);
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
                // final index = _dataGridSource!.rows.indexOf(newRows.last);
                // if (index >= 0) {
                //   final selectedModelObj = snapshot.data![index];
                //   AdminDataGridScreen.adminDetailGlobalResponseObj = selectedModelObj;
                // }
                // HomeScreenState.screensStreamLarge.sink.add(AdminScreenLarge.userdetailscreen);
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
            {
              List<CommonDataGridTableResponseModel>? errorData = [CommonDataGridTableResponseModel(accountnumber: "")];
              final columnList = errorData[0].toJsonForgetDataAnalysisReport().keys.toList();
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
            }
          } else {
            return const Center(
              child: CircularProgressIndicator(),
            );
          }
        },
      ),
    );
  }

  // RE IPV REPORT
  Expanded getReIpvReport() {
    return Expanded(
      child: FutureBuilder<List<CommonDataGridTableResponseModel>>(
        future: _adminDataBloc?.getReIpvReport(),
        builder: (context, snapshot) {
          if ((snapshot.data != null) && (snapshot.data!.isNotEmpty)) {
            final columnList = snapshot.data?.first.toJsonForReIpvReport().keys.toList() ?? [];
            if (columnWidth.isEmpty) {
              columnWidth = columnList.map((e) => Tuple2(e, double.nan)).toList();
            }
            final dataList = snapshot.data?.map((e) => e.toJsonForReIpvReport()).toList() ?? [];
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
            {
              List<CommonDataGridTableResponseModel>? errorData = [CommonDataGridTableResponseModel(accountnumber: "")];
              final columnList = errorData[0].toJsonForReIpvReport().keys.toList();
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
            }
          } else {
            return const Center(
              child: CircularProgressIndicator(),
            );
          }
        },
      ),
    );
  }

  // AUTHORIZE REPORT
  Expanded getAuthorizeUserReport() {
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
            {
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
            }
          } else {
            return const Center(
              child: CircularProgressIndicator(),
            );
          }
        },
      ),
    );
  }

  // REJECTION REPORT
  Expanded getRejectionReport() {
    return Expanded(
      child: FutureBuilder<List<CommonDataGridTableResponseModel>>(
        future: _adminDataBloc?.getRejectionReport(),
        builder: (context, snapshot) {
          if ((snapshot.data != null) && (snapshot.data!.isNotEmpty)) {
            final columnList = snapshot.data?.first.toJsonForRejectionReport().keys.toList() ?? [];
            if (columnWidth.isEmpty) {
              columnWidth = columnList.map((e) => Tuple2(e, double.nan)).toList();
            }
            final dataList = snapshot.data?.map((e) => e.toJsonForRejectionReport()).toList() ?? [];
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
            {
              List<CommonDataGridTableResponseModel>? errorData = [CommonDataGridTableResponseModel(accountnumber: "")];
              final columnList = errorData[0].toJsonForRejectionReport().keys.toList();
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
            }
          } else {
            return const Center(
              child: CircularProgressIndicator(),
            );
          }
        },
      ),
    );
  }

  // REJECTION REPORT
  Expanded getPennyDropReportData() {
    return Expanded(
      child: FutureBuilder<List<CommonDataGridTableResponseModel>>(
        future: _adminDataBloc?.getPennyDropReportData(),
        builder: (context, snapshot) {
          if ((snapshot.data != null) && (snapshot.data!.isNotEmpty)) {
            final columnList = snapshot.data?.first.toJsonForPennyDropReport().keys.toList() ?? [];
            if (columnWidth.isEmpty) {
              columnWidth = columnList.map((e) => Tuple2(e, double.nan)).toList();
            }
            final dataList = snapshot.data?.map((e) => e.toJsonForPennyDropReport()).toList() ?? [];
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
            {
              List<CommonDataGridTableResponseModel>? errorData = [CommonDataGridTableResponseModel(accountnumber: "")];
              final columnList = errorData[0].toJsonForPennyDropReport().keys.toList();
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
            }
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
