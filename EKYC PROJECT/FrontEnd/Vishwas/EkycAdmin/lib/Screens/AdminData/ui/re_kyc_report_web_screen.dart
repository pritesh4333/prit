import 'package:ekyc_admin/Configuration/app_config.dart';
import 'package:ekyc_admin/Screens/AdminData/helper/common_data_grid_source.dart';
import 'package:ekyc_admin/Screens/AdminData/ui/admin_data_grid.dart';
import 'package:ekyc_admin/Screens/AdminData/widgets/search_action_rekyc.dart';
import 'package:ekyc_admin/Screens/AdminData/widgets/widgets.dart';
import 'package:ekyc_admin/Screens/home/blocs.dart';
import 'package:ekyc_admin/Screens/home/response.dart';
import 'package:ekyc_admin/Screens/home/screen.dart';
import 'package:ekyc_admin/Screens/home/widgets/constants.dart';
import 'package:flutter/material.dart';
import 'package:flutter_animated_dialog/flutter_animated_dialog.dart';
import 'package:syncfusion_flutter_datagrid/datagrid.dart';
import 'package:tuple/tuple.dart';

class ReKycReportsWebScreen extends StatefulWidget {
  final String title;
  const ReKycReportsWebScreen({
    Key? key,
    required this.title,
  }) : super(key: key);

  @override
  State<ReKycReportsWebScreen> createState() => _AllReportsWebScreenState();
}

class _AllReportsWebScreenState extends State<ReKycReportsWebScreen> {
  AdminDataBloc? _adminDataBloc;

  final DataGridController _dataGridController = DataGridController();

  CommonDataGridSource? _dataGridSource;

  bool isSelectAll = false;

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
                      Visibility(
                        visible: AppConfig().selectedReportType == 'rekycstatusreport' ? false : true,
                        child: Row(
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
                      ),
                      const SizedBox(width: 20.0),
                      Visibility(
                        visible: AppConfig().selectedReportType == 'rekycstatusreport' ? false : true,
                        child: ElevatedButton(
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
                      ),
                      const SizedBox(width: 18.0),
                      IconButton(
                        color: Colors.grey,
                        onPressed: () {
                          //Show Alert
                          showAnimatedDialog(
                            context: context,
                            barrierDismissible: true,
                            barrierColor: Colors.transparent,
                            builder: (context) {
                              return const ReKycSearchActionDialog();
                            },
                            animationType: DialogTransitionType.slideFromBottom,
                            // curve: Curves.ease,
                            duration: const Duration(seconds: 1),
                          );
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
                                  PdfDocument document = keys.currentState!.exportToPdfDocument();
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
    switch (AppConfig().selectedReportType) {
      case "rekycdataanalysis":
        return getAdminReKycDataAnalysisReport();
      case "rekycglobalsearch":
        return getReKycGlobalSearchReport();
      default:
        return getReKycStatusReportData();
    }
  }

  //[REKYC STATUS REPORT]
  Expanded getReKycStatusReportData() {
    return Expanded(
      child: FutureBuilder<List<CommonDataGridTableResponseModel>>(
        future: _adminDataBloc?.getReKycStatusReportData(),
        builder: (context, snapshot) {
          if ((snapshot.data != null) && (snapshot.data!.isNotEmpty)) {
            final columnList = snapshot.data?.first.toJsonForReKycStatusReport().keys.toList() ?? [];
            if (columnWidth.isEmpty) {
              columnWidth = columnList.map((e) => Tuple2(e, double.nan)).toList();
            }
            final dataList = snapshot.data?.map((e) => e.toJsonForReKycStatusReport()).toList() ?? [];
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
            return Text('${snapshot.error}');
          } else {
            return const Center(
              child: CircularProgressIndicator(),
            );
          }
        },
      ),
    );
  }

  // [REKYC DATA ANALYSIS REPORT]
  Expanded getAdminReKycDataAnalysisReport() {
    return Expanded(
      child: FutureBuilder<List<CommonDataGridTableResponseModel>>(
        future: _adminDataBloc?.getAdminReKycDataAnalysisReport(),
        builder: (context, snapshot) {
          if ((snapshot.data != null) && (snapshot.data!.isNotEmpty)) {
            final columnList = snapshot.data?.first.toJsonForReKycDataAnalysisReport().keys.toList() ?? [];
            if (columnWidth.isEmpty) {
              columnWidth = columnList.map((e) => Tuple2(e, double.nan)).toList();
            }
            final dataList = snapshot.data?.map((e) => e.toJsonForReKycDataAnalysisReport()).toList() ?? [];
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
            return Text('${snapshot.error}');
          } else {
            return const Center(
              child: CircularProgressIndicator(),
            );
          }
        },
      ),
    );
  }

  // [REKYC GLOBAL SEARCH REPORT]
  Expanded getReKycGlobalSearchReport() {
    return Expanded(
      child: FutureBuilder<List<CommonDataGridTableResponseModel>>(
        future: _adminDataBloc?.getReKycGlobalSearchReport(),
        builder: (context, snapshot) {
          if ((snapshot.data != null) && (snapshot.data!.isNotEmpty)) {
            final columnList = snapshot.data?.first.toJsonForReKycGlobalSearchReport().keys.toList() ?? [];
            if (columnWidth.isEmpty) {
              columnWidth = columnList.map((e) => Tuple2(e, double.nan)).toList();
            }
            final dataList = snapshot.data?.map((e) => e.toJsonForReKycGlobalSearchReport()).toList() ?? [];
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
            return Text('${snapshot.error}');
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
