import 'package:ekyc_admin/Screens/DashBoardScreens/block/dashboard_bloc.dart';
import 'package:flutter/material.dart';

class DashBoardMobileScreen extends StatefulWidget {
  final DashBoardBloc? dashBoardBloc;

  const DashBoardMobileScreen({Key? key, this.dashBoardBloc}) : super(key: key);

  @override
  State<DashBoardMobileScreen> createState() => _DashBoardMobileScreenState();
}

class _DashBoardMobileScreenState extends State<DashBoardMobileScreen> {
  @override
  Widget build(BuildContext context) {
    return Container(
      color: Colors.red,
    );
  }
}
