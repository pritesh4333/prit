import 'package:ekyc_admin/Configuration/app_config.dart';
import 'package:ekyc_admin/Screens/AdminData/ui/admin_data_grid.dart';
import 'package:ekyc_admin/Screens/AdminData/ui/all_report_web_screen.dart';
import 'package:ekyc_admin/Screens/home/widgets/constants.dart';
import 'package:flutter/material.dart';
import 'package:syncfusion_flutter_datagrid/datagrid.dart';
import 'package:tuple/tuple.dart';

import '../../home/screens/home_screen.dart';

class CommonDataGridSource extends DataGridSource {
  final List<String> columnList;
  final List<Tuple2<String, double>> columnWidthList;
  final List<Map<String, String?>> dataList;

  List<GridColumn> gridColumns = <GridColumn>[];
  List<DataGridRow> _gridRows = <DataGridRow>[];

  List<Map<String, String?>> tempList = [];
  CommonDataGridSource({
    required this.columnList,
    required this.columnWidthList,
    required this.dataList,
  }) {
    tempList = List<Map<String, String?>>.from(dataList);
    _prepareDataTable();
  }

  void _prepareDataTable() {
    gridColumns = columnWidthList.map(
      (e) {
        if (e.item1.toLowerCase().compareTo("check") == 0) {
          return GridColumn(
            columnName: e.item1.toString(),
            width: e.item2,
            maximumWidth: 65,
            minimumWidth: 65,
            label: Container(
              color: const Color(0xff7BA1C2),
              alignment: Alignment.center,
              padding: const EdgeInsets.all(8.0),
              child: Text(
                e.item1,
                overflow: TextOverflow.ellipsis,
                style: const TextStyle(color: Colors.white, fontFamily: 'Roboto', fontSize: 16, fontWeight: FontWeight.bold),
              ),
            ),
          );
        } else if (e.item1.toLowerCase().compareTo('mobile no') == 0) {
          return GridColumn(
            columnWidthMode: ColumnWidthMode.fill,
            maximumWidth: 120,
            minimumWidth: 100,
            width: e.item2,
            columnName: e.item1.toString(),
            label: Container(
              color: const Color(0xff7BA1C2),
              alignment: Alignment.center,
              padding: const EdgeInsets.all(8.0),
              child: Text(
                e.item1,
                overflow: TextOverflow.ellipsis,
                style: const TextStyle(color: Colors.white, fontFamily: 'Roboto', fontSize: 16, fontWeight: FontWeight.bold),
              ),
            ),
          );
        } else if (e.item1.toLowerCase().compareTo('status') == 0 || e.item1.toLowerCase().compareTo('remarks') == 0 || e.item1.toLowerCase().compareTo('last update') == 0 || e.item1.toLowerCase().compareTo('email') == 0) {
          return GridColumn(
            columnWidthMode: ColumnWidthMode.fill,
            // maximumWidth: 200,
            minimumWidth: 100,
            width: e.item2,
            columnName: e.item1.toString(),
            label: Container(
              color: const Color(0xff7BA1C2),
              alignment: Alignment.center,
              padding: const EdgeInsets.all(8.0),
              child: Text(
                e.item1,
                overflow: TextOverflow.ellipsis,
                style: const TextStyle(color: Colors.white, fontFamily: 'Roboto', fontSize: 16, fontWeight: FontWeight.bold),
              ),
            ),
          );
        } else {
          return GridColumn(
            columnWidthMode: ColumnWidthMode.fill,
            columnName: e.item1.toString(),
            // minimumWidth: 300,
            // maximumWidth: 300,
            width: e.item2,
            label: Container(
              color: const Color(0xff7BA1C2),
              alignment: Alignment.center,
              padding: const EdgeInsets.all(8.0),
              child: Text(
                e.item1,
                overflow: TextOverflow.ellipsis,
                style: const TextStyle(color: Colors.white, fontFamily: 'Roboto', fontSize: 16, fontWeight: FontWeight.bold),
              ),
            ),
          );
        }
      },
    ).toList();

    _gridRows = tempList
        .map<DataGridRow>(
          (item) => DataGridRow(
            cells: gridColumns
                .map<DataGridCell<String>>(
                  (e) => DataGridCell<String>(
                    columnName: e.columnName,
                    value: item[e.columnName]?.toString() ?? "",
                  ),
                )
                .toList(),
          ),
        )
        .toList();
    notifyDataSourceListeners();
  }

  void searchDataTable(BuildContext ctx, String name, String email, String number, String pan, String isreset) {
    tempList.clear();
    if (isreset.toLowerCase().compareTo("true") == 0) {
      tempList = List<Map<String, String?>>.from(dataList);
    } else {
      for (var i = 0; i < dataList.length; i++) {
        var Name = dataList[i]["Name"].toString().toLowerCase();
        var Number = dataList[i]["Mobile No"].toString().toLowerCase();
        var Email = dataList[i]['Email'].toString().toLowerCase();
        var Pan = dataList[i]["Pan No"].toString().toLowerCase();
        if (Name == name || Email == email || Number == number || Pan == pan) {
          tempList.add(dataList[i]);
        }
      }
    }
    if (tempList.isEmpty) {
      tempList = List<Map<String, String?>>.from(dataList);
      AppConfig().showAlert(ctx, "No Record Found");
    }
    _prepareDataTable();
  }

  void selectAllEvent({required bool isSelectedAll}) {
    HomeScreenState.isSelectAll.sink.add(isSelectedAll);

    for (var i = 0; i < dataList.length; i++) {
      dataList[i]["Check"] = isSelectedAll.toString();
    }

    _prepareDataTable();
  }

  @override
  List<DataGridRow> get rows => _gridRows;

  @override
  DataGridRowAdapter? buildRow(DataGridRow row) {
    return DataGridRowAdapter(
      cells: row.getCells().map<Widget>(
        (cellObj) {
          switch (cellObj.columnName.contains("Check")) {
            case true:
              final isSelected = (HomeScreenState.isSelectAll.value) ? true : (cellObj.value?.toString().toLowerCase().compareTo("true") == 0);
              return Container(
                color: Colors.white,
                child: Checkbox(
                  checkColor: Colors.black,
                  fillColor: MaterialStateProperty.resolveWith(_getColor),
                  side: MaterialStateBorderSide.resolveWith(
                    (states) => const BorderSide(
                      width: 1,
                      color: Color(0xffBCBCBC),
                    ),
                  ),
                  onChanged: (bool? value) {
                    if (value != null) {
                      if (!value) {
                        HomeScreenState.isSelectAll.sink.add(value);
                      }
                      final rowIndex = _gridRows.indexOf(row);
                      final cellIndex = row.getCells().indexOf(cellObj);

                      if ((rowIndex >= 0) && (cellIndex >= 0)) {
                        dataList[rowIndex]["check"] = value.toString();

                        final columnID = cellObj.columnName;
                        _gridRows[rowIndex].getCells()[cellIndex] = DataGridCell<String>(
                          columnName: columnID,
                          value: value.toString(),
                        );

                        notifyDataSourceListeners(
                          rowColumnIndex: RowColumnIndex(
                            rowIndex,
                            cellIndex,
                          ),
                        );
                      }
                    }
                  },
                  value: (HomeScreenState.isSelectAll.value) ? true : isSelected,
                ),
              );

            default:
              return Container(
                alignment: Alignment.center,
                padding: const EdgeInsets.symmetric(horizontal: 8.0),
                color: Colors.white,
                child: Text(
                  cellObj.value.toString(),
                  overflow: TextOverflow.ellipsis,
                  style: GreekTextStyle.gridTextStyle,
                ),
              );
          }
        },
      ).toList(),
    );
  }

  Color _getColor(Set<MaterialState> states) {
    const Set<MaterialState> interactiveState = <MaterialState>{
      MaterialState.pressed,
      MaterialState.hovered,
      MaterialState.focused,
    };
    if (states.any(interactiveState.contains)) {
      return Colors.black26;
    } else {
      return const Color(0xffFEAA03);
    }
  }
}
