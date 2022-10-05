import 'package:ekyc_admin/Screens/AdminData/helper/common_data_grid_source.dart';
import 'package:ekyc_admin/Utilities/greek_textstyle.dart';
import 'package:flutter/material.dart';
import 'package:syncfusion_flutter_datagrid/datagrid.dart';

class NoDataAvailabelGridMessage extends StatelessWidget {
  const NoDataAvailabelGridMessage({
    Key? key,
    required this.keys,
    required CommonDataGridSource? dataGridSource,
    required DataGridController dataGridController,
  })  : _dataGridSource = dataGridSource,
        _dataGridController = dataGridController,
        super(key: key);

  final GlobalKey<SfDataGridState> keys;
  final CommonDataGridSource? _dataGridSource;
  final DataGridController _dataGridController;

  @override
  Widget build(BuildContext context) {
    return Stack(
      children: [
        Positioned(
          top: MediaQuery.of(context).size.height / 3,
          left: MediaQuery.of(context).size.width / 2.5,
          child: Container(
            width: 150,
            height: 50,
            decoration: BoxDecoration(
              borderRadius: BorderRadius.circular(20),
              color: Colors.white,
              boxShadow: const [
                BoxShadow(
                  blurRadius: 2.0,
                  color: Colors.black38,
                  // offset: Offset(1, 1),
                )
              ],
            ),
            child: const Center(
                child: Text(
              'No Data Available',
              style: GreekTextStyle.gridTextStyle,
            )),
          ),
        ),
        SfDataGrid(
          key: keys,
          columns: _dataGridSource?.gridColumns ?? [],
          source: _dataGridSource!,
          gridLinesVisibility: GridLinesVisibility.both,
          columnWidthMode: ColumnWidthMode.fill,
          controller: _dataGridController,
          headerGridLinesVisibility: GridLinesVisibility.both,
          navigationMode: GridNavigationMode.row,
        ),
      ],
    );
  }
}
