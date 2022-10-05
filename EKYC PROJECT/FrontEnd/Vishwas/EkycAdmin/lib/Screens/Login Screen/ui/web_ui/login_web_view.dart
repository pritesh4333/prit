import 'package:ekyc_admin/Helper/constant_image_name.dart';
import 'package:ekyc_admin/Utilities/greek_textstyle.dart';
import 'package:flutter/material.dart';
import 'package:ekyc_admin/Screens/Login%20Screen/bloc/login_bloc.dart';
import 'package:flutter/services.dart';

import '../../../../Configuration/app_config.dart';
import '../../../../Utilities/UpperCaseTextFormatter.dart';

class LoginScreenWeb extends StatefulWidget {
  final LoginBloc? loginBloc;

  const LoginScreenWeb({Key? key, required this.loginBloc}) : super(key: key);

  @override
  State<LoginScreenWeb> createState() => _LoginScreenWebState();
}

class _LoginScreenWebState extends State<LoginScreenWeb> {
  double leftRightSpacing = 0.0;
  double topBottomSpacing = 0.0;
  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      onWillPop: () async => false,
      child: Scaffold(
        body: SafeArea(
          child: Container(
            color: Colors.white,
            child: Stack(
              children: [
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: [
                    Flexible(
                      flex: 2,
                      child: Container(
                        alignment: Alignment.center,
                        child: Image.asset(
                          LoginImage.login_bg_image.name,
                          fit: BoxFit.contain,
                        ),
                      ),
                    ),
                    Flexible(
                      fit: FlexFit.loose,
                      child: Center(
                        child: Container(
                          color: Colors.white,
                          child: Column(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: [
                              _buildLogo(),
                              _buildUserIdRow(),
                              _buildPasswordRow(),
                              _buildloginbutton(),
                            ],
                          ),
                        ),
                      ),
                    ),
                  ],
                ),
                const Align(
                  alignment: Alignment.bottomCenter,
                  child: SizedBox(
                    height: 35.0,
                    child: Text.rich(
                      TextSpan(
                        children: <TextSpan>[
                          TextSpan(
                            text: "This Program Copyrights are Protected by ",
                            style: GreekTextStyle.loginDiscription1TextStyle,
                          ),
                          TextSpan(
                            text: "Greeksoft Technologies Pvt. Ltd.",
                            style: GreekTextStyle.loginDiscription2TextStyle,
                          ),
                        ],
                      ),
                    ),
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }

  Widget _buildLogo() {
    return Center(
      child: Container(
        padding: const EdgeInsets.symmetric(
          vertical: 50,
        ),
        alignment: Alignment.center,
        child: Image.asset(
          CompanyImage.company_logo.name,
          fit: BoxFit.contain,
        ),
      ),
    );
  }

  Widget _buildUserIdRow() {
    return Container(
        padding: EdgeInsets.symmetric(vertical: 5),
        width: 250,
        child: TextField(
          keyboardType: TextInputType.text,
          inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z0-9]')), UpperCaseTextFormatter()],
          controller: widget.loginBloc?.userIDTextController,
          textInputAction: TextInputAction.go,
          onSubmitted: (value) {
            if (widget.loginBloc!.userIDTextController.text.isEmpty) {
              AppConfig().showAlert(context, "Please provide UserID");
            } else if (widget.loginBloc!.passwordTextController.text.isEmpty) {
              AppConfig().showAlert(context, "Please provide Password");
            } else {
              widget.loginBloc?.proceedButtonTaped();
            }
          },
          maxLength: 10,
          decoration: InputDecoration(
            border: OutlineInputBorder(borderRadius: BorderRadius.all(Radius.circular(10.0))),
            focusedBorder: OutlineInputBorder(
              borderSide: BorderSide(color: Color.fromRGBO(250, 184, 4, 1), width: 2.0),
            ),
            labelText: 'User ID',
            // hintText: 'Enter Name Here',
          ),
          autofocus: false,
        ));
  }

  Widget _buildPasswordRow() {
    return Container(
        padding: const EdgeInsets.symmetric(vertical: 10),
        width: 250,
        child: TextField(
          obscureText: true,
          textInputAction: TextInputAction.go,
          onSubmitted: (value) {
            if (widget.loginBloc!.userIDTextController.text.isEmpty) {
              AppConfig().showAlert(context, "Please provide UserID");
            } else if (widget.loginBloc!.passwordTextController.text.isEmpty) {
              AppConfig().showAlert(context, "Please provide Password");
            } else {
              widget.loginBloc?.proceedButtonTaped();
            }
          },
          keyboardType: TextInputType.text,
          controller: widget.loginBloc?.passwordTextController,
          maxLength: 12,
          decoration: const InputDecoration(
            border: OutlineInputBorder(borderRadius: BorderRadius.all(Radius.circular(10.0))),
            focusedBorder: OutlineInputBorder(
              borderSide: BorderSide(color: Color.fromRGBO(250, 184, 4, 1), width: 2.0),
            ),

            labelText: 'Password',
            // hintText: 'Enter Name Here',
          ),
          autofocus: false,
        ));
  }

  Widget _buildloginbutton() {
    return Padding(
      padding: const EdgeInsets.only(top: 15),
      child: Container(
        height: 50,
        width: 200,
        margin: const EdgeInsets.only(bottom: 10),
        decoration: BoxDecoration(
          color: const Color.fromRGBO(0, 37, 142, 1),
          borderRadius: BorderRadius.circular(30.0),
          boxShadow: const [
            BoxShadow(color: Colors.black12, spreadRadius: 3),
          ],
        ),
        child: TextButton(
          onPressed: () {
            widget.loginBloc?.proceedButtonTaped();
            // GreekNavigator.pushNamedAndRemoveUntil(context: context, newRouteName: GreekScreenNames.home_screen);
          },
          child: const Text(
            "LOGIN",
            textScaleFactor: 1.0,
            style: TextStyle(color: Colors.white),
          ),
        ),
      ),
    );
  }
}
