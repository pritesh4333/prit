import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

import '../Bloc/login_bloc.dart';

TextEditingController userNameController = TextEditingController(text: "");
TextEditingController passWordController = TextEditingController(text: "");

class LoginPage extends StatelessWidget {
  const LoginPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
       appBar: AppBar(
        title: const Text('Fluter Bloc & Cubit'),
      ), 
      body: SafeArea(
        child: Column(
          children: [
            Container(
              margin: const EdgeInsets.all(20),
              child: const Text("Welcome"),
            ),
            Container(
              margin: const EdgeInsets.all(15),
              child: BlocBuilder<LoginBloc, LoginObj>(
                builder: (context, state) {
                  return TextField(
                    controller: userNameController,
                    decoration: InputDecoration(
                        hintText: 'Email ID*',
                        errorText: state.userNameError.isEmpty
                            ? null
                            : state.userNameError),
                  );
                },
              ),
            ),
            Container(
              margin: const EdgeInsets.all(15),
              child: BlocBuilder<LoginBloc, LoginObj>(
                builder: (context, state) {
                  return TextField(
                    controller: passWordController,
                    decoration: InputDecoration(
                        hintText: 'Password*',
                        errorText: state.passWordError.isEmpty
                            ? null
                            : state.passWordError),
                  );
                },
              ),
            ),
            ElevatedButton(
              style: ElevatedButton.styleFrom(
                  primary: Colors.green,
                  padding: EdgeInsets.symmetric(horizontal: 20, vertical: 10),
                  textStyle:
                      TextStyle(fontSize: 12, fontWeight: FontWeight.bold)),
              onPressed: () async {
                
                context
                    .read<LoginBloc>()
                    .loginValidation(userNameController, passWordController,context);
              },
              child: const Text("Login"),
            )
          ],
        ),
      ),
    );
  }

}
