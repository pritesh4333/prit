import 'package:e_kyc/Login/UI/ThemeColors.dart';
import 'package:flutter/material.dart';

class ListViewExample extends StatefulWidget {
  @override
  _ListViewExample createState() => _ListViewExample();
}

class _ListViewExample extends State<ListViewExample> {
  final List<String> names = <String>[
    'Beginner Pack',
    'Professional Pack',
    'Ultimate Pack',
  ];
  // final List<int> msgCount = <int>[2, 0, 10];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: Container(
          child: ListView.builder(
            padding: const EdgeInsets.all(8),
            itemCount: names.length,
            itemBuilder: (BuildContext context, int index) {
              return listviewmtd(index);
            },
          ),
        ),
      ),
    );
  }

  // ignore: missing_return
  listviewmtd(int index) {
    return Container(
      child: Column(
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              Expanded(
                child: Container(
                  alignment: Alignment.centerLeft,
                  padding: EdgeInsets.only(left: 50),
                  child: Text(
                    '${names[index]}',
                    style: TextStyle(
                        color: Color(0xFF000000),
                        fontFamily: 'century_gothic',
                        fontSize: 12,
                        fontWeight: FontWeight.bold),
                  ),
                ),
              ),
              Expanded(
                child: Container(
                  alignment: Alignment.centerRight,
                  child: Checkbox(
                    value: true,
                    onChanged: (value) {
                      setState(() {
                        // if (professionalchecked != true) {
                        //   beginerchecked = value;
                        // } else {
                        //   beginerchecked = true;
                        //   professionalchecked = false;
                        // }
                      });
                    },
                  ),
                ),
              ),
            ],
          ),
        ],
      ),
    );
    // Container(
    //   height: 50,
    //   margin: EdgeInsets.all(2),
    //   color: msgCount[index] >= 10
    //       ? Colors.blue[400]
    //       : msgCount[index] > 3
    //           ? Colors.blue[100]
    //           : Colors.grey,
    //   child: Center(
    //       child: Text(
    //     '${names[index]} (${msgCount[index]})',
    //     style: TextStyle(fontSize: 18),
    //   )),
    // );
  }
}
