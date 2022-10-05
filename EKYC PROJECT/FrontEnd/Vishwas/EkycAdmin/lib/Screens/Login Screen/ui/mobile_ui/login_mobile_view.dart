import 'package:flutter/material.dart';
import 'package:ekyc_admin/Screens/Login%20Screen/bloc/login_bloc.dart';

class LoginScreenMobile extends StatefulWidget {
  final LoginBloc? loginBloc;
  const LoginScreenMobile({Key? key, required this.loginBloc})
      : super(key: key);

  @override
  State<LoginScreenMobile> createState() => _LoginScreenMobileState();
}

class _LoginScreenMobileState extends State<LoginScreenMobile> {
  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      onWillPop: () async => false,
      child: Scaffold(
        appBar: AppBar(
          backgroundColor: Colors.amber,
        ),
        body: Container(
          color: Colors.blue,
        ),
      ),
    );
  }
}
