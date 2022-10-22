import 'package:flutter/material.dart';

class HomePage extends StatelessWidget {
  const HomePage({Key? key, required this.name}) : super(key: key);
  final String name;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Fluter Bloc & Cubit'),
        automaticallyImplyLeading: false,
      ),
      body: Center(
        child: Text(
          "Welcome " + name,
          style: TextStyle(
            fontSize: 24,
            color: Colors.green,
          ),
        ),
      ),
    );
  }
}
