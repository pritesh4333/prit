import 'package:e_kyc/Login/UI/BaseClickEvent/ScreenOne.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:rxdart/rxdart.dart';

// class StaticClickEvent {
//   static final commonStream = BehaviorSubject<Widget>.seeded(ScreenOne());

//   /*static final StaticClickEvent _singleton = StaticClickEvent._internal();

//   factory StaticClickEvent() {
//     return _singleton;
//   }

//   StaticClickEvent._internal();*/
// }

class ClickEvent extends StatefulWidget {
  static final commonStream = BehaviorSubject<Widget>.seeded(ScreenOne());
  @override
  _ClickEventState createState() => _ClickEventState();

  void dispose() {
    commonStream.close();
  }
}

class _ClickEventState extends State<ClickEvent> {
  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      /*appBar: AppBar(
        backgroundColor: Colors.white,
      ),*/
      body: Container(
        child: StreamBuilder<Widget>(
          //stream: StaticClickEvent().commonStream.stream,
          stream: ClickEvent.commonStream.stream,
          builder: (context, snapshot) {
            print(snapshot.data.toString());
            return snapshot.hasData
                ? snapshot.data
                : Container(color: Colors.pink);
          },
        ),
      ),
    );
  }
}
