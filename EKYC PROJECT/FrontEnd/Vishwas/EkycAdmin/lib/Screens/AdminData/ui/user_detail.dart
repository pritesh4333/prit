import 'package:ekyc_admin/Utilities/greek_textstyle.dart';
import 'package:flutter/material.dart';

class UserDetailScreen extends StatefulWidget {
  const UserDetailScreen({Key? key}) : super(key: key);

  @override
  State<UserDetailScreen> createState() => _UserDetailScreenState();
}

class _UserDetailScreenState extends State<UserDetailScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color.fromRGBO(244, 244, 244, 1),
      body: Column(
        children: [
          Container(
            padding: const EdgeInsets.symmetric(horizontal: 18.0),
            margin: const EdgeInsets.symmetric(horizontal: 18.0),
            decoration: BoxDecoration(
              borderRadius: BorderRadius.circular(5),
              color: Colors.white,
              //border: Border.all(color: Colors.red)
            ),
            child: Column(
              children: [
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: <Widget>[
                    const Text(
                      'COMPLETED USER DETAILS',
                      style: TextStyle(
                        color: Color.fromRGBO(0, 37, 142, 1),
                        fontFamily: 'Roboto',
                        fontSize: 12,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    Row(
                      children: <Widget>[
                        const SizedBox(width: 18.0),
                        IconButton(
                          color: Colors.grey,
                          onPressed: () {
                            Navigator.pop(context);
                          },
                          icon: const Icon(
                            Icons.close_outlined,
                          ),
                        ),
                      ],
                    ),
                  ],
                ),
                _tabSection(context),
              ],
            ),
          ),
        ],
      ),
    );
  }

  Widget _tabSection(BuildContext context) {
    return DefaultTabController(
      length: 11,
      child: Column(
        mainAxisSize: MainAxisSize.min,
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        children: <Widget>[
          Container(
            // height: 30,

            width: MediaQuery.of(context).size.width,
            color: const Color(0xFFF5F5F5),

            child: const TabBar(
              labelColor: Color.fromRGBO(250, 184, 4, 1),
              unselectedLabelColor: Color(0xFF000000),
              isScrollable: true,
              labelStyle: GreekTextStyle.userdetailtabstyle,
              indicatorSize: TabBarIndicatorSize.label,
              indicatorWeight: 5,
              indicatorColor: Color.fromRGBO(0, 37, 142, 1),
              tabs: [
                Text('Personal Details'),
                Text('Contact Details'),
                Text('Bank Details'),
                Text('Education / Income'),
                Text('Trading History'),
                Text('Document'),
                Text('IPV'),
                Text('Digio'),
                Text('GeoLocation'),
                Text('Mark Finish'),
                Text('Misc'),
              ],
            ),
          ),
          SizedBox(
            //Add this to give height

            height: MediaQuery.of(context).size.height - 80,
            child: const TabBarView(children: [
              Text("Personal Details"),
              Text("Contact Details"),
              Text("Bank Details"),
              Text("Education / Income"),
              Text("Trading History"),
              // ignore: avoid_unnecessary_containers
              Text("Document"),
              Text("IPV"),
              Text("Digio"),
              Text("GeoLocation"),
              Text("Mark Finish"),
              Text("Misc"),
            ]),
          ),
        ],
      ),
    );
  }
}
