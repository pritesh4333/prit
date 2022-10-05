import 'package:e_kyc/Login/UI/EsignScreen/repository/EsignRepository.dart';
import 'package:e_kyc/Login/UI/HomeScreen/HomeScreenSmall.dart';
import 'package:e_kyc/Login/UI/Login/repository/LoginRepository.dart';
import 'package:e_kyc/Login/UI/PaymentScreen/bloc/PaymentBloc.dart';
import 'package:e_kyc/Login/UI/ThemeColors.dart';
import 'package:flutter/material.dart';

class PaymentScreenSmall extends StatefulWidget {
  @override
  _PaymentScreenSmallState createState() => _PaymentScreenSmallState();
}

class _PaymentScreenSmallState extends State<PaymentScreenSmall> {
  PaymentBloc paymentbloc = new PaymentBloc();

  var beginerchecked = false;
  var professionalchecked = false;
  var globalRespObj = LoginRepository.loginDetailsResObjGlobal;

  @override
  void dispose() {
    this.paymentbloc.dispose();
    super.dispose();
  }

  @override
  void initState() {
    super.initState();
    if (globalRespObj != null) {
      print(globalRespObj.response.errorCode);
      if (globalRespObj.response.errorCode == "0") {
        var responseData = globalRespObj.response.data.message[0];
        var stage = int.parse(globalRespObj.response.data.message[0].stage);
        if (stage >= 3) {
          if (responseData.pack.isNotEmpty) {
            this.beginerchecked = responseData.pack == "0" ? true : false;
            this.professionalchecked = responseData.pack == "1" ? true : false;
          }
        }
      }
    }
    HomeScreenSmall.percentageFlagSmall.add("0.605");
    if (EsignRepository.timerObj != null) {
      EsignRepository.timerObj.cancel();
      EsignRepository.timerObj = null;
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: SafeArea(
            child: GestureDetector(
      onTap: () {
        FocusManager.instance.primaryFocus?.unfocus();
      },
      child: SingleChildScrollView(
          child: Container(
        child: Column(
          children: [
            header(),
            paymentDetailsForm(),
            btn(),
          ],
        ),
      )),
    )));
  }

  header() {
    return Container(
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Container(
            padding: EdgeInsets.only(top: 5),
            width: 100,
            height: 60,
            child: Image.asset(
              'asset/images/paymentheader.png',
            ),
          ),
          Expanded(
            child: Container(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    "ONLINE ACCOUNT OPENING E - KYC",
                    style: TextStyle(
                        color: Color(0xFF0066CC),
                        fontSize: 15,
                        fontFamily: 'century_gothic',
                        fontWeight: FontWeight.bold),
                  ),
                  Text(
                    "PAYMENT",
                    style: TextStyle(
                        color: Color(0xFFFAB804),
                        fontFamily: 'century_gothic',
                        fontSize: 15,
                        fontWeight: FontWeight.w300),
                  ),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }

  paymentDetailsForm() {
    return Container(
      padding: EdgeInsets.all(10),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Row(
            children: [
              Expanded(
                child: Container(
                  // color: Colors.red,
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: [
                      Expanded(
                        child: Container(
                          alignment: Alignment.centerLeft,
                          padding: EdgeInsets.only(left: 10),
                          child: Text(
                            "BEGINNER PACK",
                            style: TextStyle(
                                fontFamily: 'century_gothic',
                                color: Colors.black,
                                fontSize: 13,
                                fontWeight: FontWeight.bold),
                          ),
                        ),
                      ),
                      Expanded(
                        child: Container(
                          alignment: Alignment.centerRight,
                          child: Checkbox(
                            value: beginerchecked,
                            onChanged: (value) {
                              setState(() {
                                if (professionalchecked != true) {
                                  beginerchecked = value;
                                } else {
                                  beginerchecked = true;
                                  professionalchecked = false;
                                }
                              });
                            },
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ],
          ),
          Row(
            children: [
              Expanded(
                child: Container(
                  margin: EdgeInsets.only(top: 10),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: [
                      Padding(
                        padding: const EdgeInsets.only(left: 10),
                        child: Icon(
                          Icons.circle,
                          size: 10,
                          color: Colors.blue[200],
                        ),
                      ),
                      Expanded(
                        child: Container(
                          alignment: Alignment.centerLeft,
                          padding: EdgeInsets.only(left: 10),
                          child: Text(
                            "Annual Subscription Charge",
                            style: TextStyle(
                                fontFamily: 'century_gothic',
                                color: Colors.black,
                                fontSize: 12,
                                fontWeight: FontWeight.normal),
                          ),
                        ),
                      ),
                      Expanded(
                        child: Container(
                          padding: const EdgeInsets.only(right: 10),
                          alignment: Alignment.centerLeft,
                          child: Text(
                            "2nd Year onwards 999/-",
                            style: TextStyle(
                                fontFamily: 'century_gothic',
                                color: Colors.blue,
                                fontSize: 12,
                                fontWeight: FontWeight.bold),
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ],
          ),
          Row(
            children: [
              Expanded(
                child: Container(
                  margin: EdgeInsets.only(top: 10),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: [
                      Padding(
                        padding: const EdgeInsets.only(left: 10),
                        child: Icon(
                          Icons.circle,
                          size: 10,
                          color: Colors.blue[200],
                        ),
                      ),
                      Expanded(
                        child: Container(
                          alignment: Alignment.centerLeft,
                          padding: EdgeInsets.only(left: 10),
                          child: Text(
                            "Demat Account Charges",
                            style: TextStyle(
                                fontFamily: 'century_gothic',
                                color: Colors.black,
                                fontSize: 12,
                                fontWeight: FontWeight.normal),
                          ),
                        ),
                      ),
                      Expanded(
                        child: Container(
                          padding: const EdgeInsets.only(right: 10),
                          alignment: Alignment.centerLeft,
                          child: Text(
                            "Free",
                            style: TextStyle(
                                fontFamily: 'century_gothic',
                                color: Colors.blue,
                                fontSize: 12,
                                fontWeight: FontWeight.bold),
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ],
          ),
          Row(
            children: [
              Expanded(
                child: Container(
                  margin: EdgeInsets.only(top: 10),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: [
                      Padding(
                        padding: const EdgeInsets.only(left: 10),
                        child: Icon(
                          Icons.circle,
                          size: 10,
                          color: Colors.blue[200],
                        ),
                      ),
                      Expanded(
                        child: Container(
                          alignment: Alignment.centerLeft,
                          padding: EdgeInsets.only(left: 10),
                          child: Text(
                            "Products - EQ, FO, CDSL",
                            style: TextStyle(
                                fontFamily: 'century_gothic',
                                color: Colors.black,
                                fontSize: 12,
                                fontWeight: FontWeight.normal),
                          ),
                        ),
                      ),
                      Expanded(
                        child: Container(
                          padding: const EdgeInsets.only(right: 10),
                          alignment: Alignment.centerLeft,
                          child: Text(
                            "15 Rs Per Order",
                            style: TextStyle(
                                fontFamily: 'century_gothic',
                                color: Colors.blue,
                                fontSize: 12,
                                fontWeight: FontWeight.bold),
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ],
          ),
          Row(
            children: [
              Expanded(
                child: Container(
                  margin: EdgeInsets.only(top: 30),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceAround,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: [
                      Expanded(
                        child: Container(
                          alignment: Alignment.centerLeft,
                          padding: EdgeInsets.only(left: 10),
                          child: Text(
                            "TOTAL PAYABLE",
                            style: TextStyle(
                                fontFamily: 'century_gothic',
                                color: Color(0xFFFAB804),
                                fontSize: 12,
                                fontWeight: FontWeight.bold),
                          ),
                        ),
                      ),
                      Expanded(
                        child: Container(
                          padding: const EdgeInsets.only(right: 10),
                          alignment: Alignment.centerLeft,
                          child: Text(
                            "0",
                            style: TextStyle(
                                fontFamily: 'century_gothic',
                                color: Color(0xFFFAB804),
                                fontSize: 12,
                                fontWeight: FontWeight.bold),
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ],
          ),
          Row(
            children: [
              Expanded(
                child: Container(
                  // color: Colors.red,
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: [
                      Expanded(
                        child: Container(
                          alignment: Alignment.centerLeft,
                          padding: EdgeInsets.only(left: 10),
                          child: Text(
                            "PROFESSIONAL PACK",
                            style: TextStyle(
                                fontFamily: 'century_gothic',
                                color: Colors.black,
                                fontSize: 13,
                                fontWeight: FontWeight.bold),
                          ),
                        ),
                      ),
                      Expanded(
                        child: Container(
                          alignment: Alignment.centerRight,
                          child: Checkbox(
                            value: professionalchecked,
                            onChanged: (value) {
                              setState(() {
                                if (beginerchecked != true) {
                                  professionalchecked = value;
                                } else {
                                  professionalchecked = true;
                                  beginerchecked = false;
                                }
                              });
                            },
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ],
          ),
          Row(
            children: [
              Expanded(
                child: Container(
                  margin: EdgeInsets.only(top: 10),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: [
                      Padding(
                        padding: const EdgeInsets.only(left: 10),
                        child: Icon(
                          Icons.circle,
                          size: 10,
                          color: Colors.blue[200],
                        ),
                      ),
                      Expanded(
                        child: Container(
                          alignment: Alignment.centerLeft,
                          padding: EdgeInsets.only(left: 10),
                          child: Text(
                            "Annual Subscription Charge",
                            style: TextStyle(
                                fontFamily: 'century_gothic',
                                color: Colors.black,
                                fontSize: 12,
                                fontWeight: FontWeight.normal),
                          ),
                        ),
                      ),
                      Expanded(
                        child: Container(
                          padding: const EdgeInsets.only(right: 10),
                          alignment: Alignment.centerLeft,
                          child: Text(
                            "1599/-",
                            style: TextStyle(
                                fontFamily: 'century_gothic',
                                color: Colors.blue,
                                fontSize: 12,
                                fontWeight: FontWeight.bold),
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ],
          ),
          Row(
            children: [
              Expanded(
                child: Container(
                  margin: EdgeInsets.only(top: 10),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: [
                      Padding(
                        padding: const EdgeInsets.only(left: 10),
                        child: Icon(
                          Icons.circle,
                          size: 10,
                          color: Colors.blue[200],
                        ),
                      ),
                      Expanded(
                        child: Container(
                          alignment: Alignment.centerLeft,
                          padding: EdgeInsets.only(left: 10),
                          child: Text(
                            "Demat Account Charges",
                            style: TextStyle(
                                fontFamily: 'century_gothic',
                                color: Colors.black,
                                fontSize: 12,
                                fontWeight: FontWeight.normal),
                          ),
                        ),
                      ),
                      Expanded(
                        child: Container(
                          padding: const EdgeInsets.only(right: 10),
                          alignment: Alignment.centerLeft,
                          child: Text(
                            "Free",
                            style: TextStyle(
                                fontFamily: 'century_gothic',
                                color: Colors.blue,
                                fontSize: 12,
                                fontWeight: FontWeight.bold),
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ],
          ),
          Row(
            children: [
              Expanded(
                child: Container(
                  margin: EdgeInsets.only(top: 10),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: [
                      Padding(
                        padding: const EdgeInsets.only(left: 10),
                        child: Icon(
                          Icons.circle,
                          size: 10,
                          color: Colors.blue[200],
                        ),
                      ),
                      Expanded(
                        child: Container(
                          alignment: Alignment.centerLeft,
                          padding: EdgeInsets.only(left: 10),
                          child: Text(
                            "Products - EQ, FO, CDSL",
                            style: TextStyle(
                                fontFamily: 'century_gothic',
                                color: Colors.black,
                                fontSize: 12,
                                fontWeight: FontWeight.normal),
                          ),
                        ),
                      ),
                      Expanded(
                        child: Container(
                          padding: const EdgeInsets.only(right: 10),
                          alignment: Alignment.centerLeft,
                          child: Text(
                            "15 Rs Per Order",
                            style: TextStyle(
                                fontFamily: 'century_gothic',
                                color: Colors.blue,
                                fontSize: 12,
                                fontWeight: FontWeight.bold),
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ],
          ),
          Row(
            children: [
              Expanded(
                child: Container(
                  margin: EdgeInsets.only(top: 30),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceAround,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: [
                      Expanded(
                        child: Container(
                          alignment: Alignment.centerLeft,
                          padding: EdgeInsets.only(left: 10),
                          child: Text(
                            "TOTAL PAYABLE",
                            style: TextStyle(
                                color: Color(0xFFFAB804),
                                fontSize: 12,
                                fontFamily: 'century_gothic',
                                fontWeight: FontWeight.bold),
                          ),
                        ),
                      ),
                      Expanded(
                        child: Container(
                          padding: const EdgeInsets.only(right: 10),
                          alignment: Alignment.centerLeft,
                          child: Text(
                            "0",
                            style: TextStyle(
                                color: Color(0xFFFAB804),
                                fontSize: 12,
                                fontFamily: 'century_gothic',
                                fontWeight: FontWeight.bold),
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ],
          ),
        ],
      ),
    );
  }

  btn() {
    return Container(
      margin: EdgeInsets.only(top: 10),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.center,
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Container(
            height: 45,
            width: 110,
            margin: EdgeInsets.all(20),
            padding: EdgeInsets.only(top: 4, left: 4, bottom: 4, right: 4), //
            decoration: BoxDecoration(
                border: Border.all(
                  color: Colors.blue[200],
                ),
                borderRadius: BorderRadius.circular(
                    20) // use instead of BorderRadius.all(Radius.circular(20))
                ),

            child: TextButton(
              child: Text(
                "PAY NOW".toUpperCase(),
                style: TextStyle(
                    color: Color(0xFFFFFFFF),
                    fontFamily: 'century_gothic',
                    fontSize: 14,
                    fontWeight: FontWeight.w500),
              ),
              style: ButtonStyle(
                padding:
                    MaterialStateProperty.all<EdgeInsets>(EdgeInsets.all(1)),
                foregroundColor: MaterialStateProperty.all<Color>(Colors.white),
                shape: MaterialStateProperty.all<RoundedRectangleBorder>(
                    RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(15),
                        side: BorderSide(color: Colors.blue[200]))),
                backgroundColor:
                    MaterialStateProperty.all<Color>(Color(0xFF0074C4)),
              ),
              onPressed: () => clickPayNowbtn(context),
            ),
          ),
          Container(
            height: 45,
            width: 110,
            margin: EdgeInsets.all(20),
            padding: EdgeInsets.only(top: 4, left: 4, bottom: 4, right: 4), //
            decoration: BoxDecoration(
                border: Border.all(
                  color: Colors.blue[200],
                ),
                borderRadius: BorderRadius.circular(
                    20) // use instead of BorderRadius.all(Radius.circular(20))
                ),

            child: TextButton(
              child: Text(
                "PAY LATER".toUpperCase(),
                style: TextStyle(
                    color: Color(0xFFFFFFFF),
                    fontFamily: 'century_gothic',
                    fontSize: 14,
                    fontWeight: FontWeight.w500),
              ),
              style: ButtonStyle(
                padding:
                    MaterialStateProperty.all<EdgeInsets>(EdgeInsets.all(1)),
                foregroundColor: MaterialStateProperty.all<Color>(Colors.white),
                shape: MaterialStateProperty.all<RoundedRectangleBorder>(
                    RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(15),
                        side: BorderSide(color: Colors.blue[200]))),
                backgroundColor:
                    MaterialStateProperty.all<Color>(Color(0xFF0074C4)),
              ),
              onPressed: () => clickPayLaterbtn(context),
            ),
          ),
        ],
      ),
    );
  }

  clickPayNowbtn(BuildContext context) {
    if (beginerchecked) {
      // if beginer pack is selected we store 0 in DB
      this.paymentbloc.navtodocumentdetail(context, "0", "small");
    } else if (professionalchecked) {
      // if professional pack is selected we store 1 in DB
      this.paymentbloc.navtodocumentdetail(context, "1", "small");
    } else {}
  }

  clickPayLaterbtn(BuildContext context) {
    if (beginerchecked) {
      // if beginer pack is selected we store 0 in DB
      this.paymentbloc.navtodocumentdetail(context, "0", "small");
    } else if (professionalchecked) {
      // if professional pack is selected we store 1 in DB
      this.paymentbloc.navtodocumentdetail(context, "1", "small");
    } else {}
  }
}
