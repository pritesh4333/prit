import 'package:e_kyc/Login/UI/EsignScreen/repository/EsignRepository.dart';
import 'package:e_kyc/Login/UI/HomeScreen/HomeScreenLarge.dart';
import 'package:e_kyc/Login/UI/Login/View/LoginUI.dart';
import 'package:e_kyc/Login/UI/Login/repository/LoginRepository.dart';
import 'package:e_kyc/Login/UI/PaymentScreen/bloc/PaymentBloc.dart';
import 'package:e_kyc/Login/UI/ThemeColors.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';

class PaymentScreenLarge extends StatefulWidget {
  @override
  _PaymentScreenLargeState createState() => _PaymentScreenLargeState();
}

class _PaymentScreenLargeState extends State<PaymentScreenLarge> {
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
    HomeScreenLarge.percentageFlagLarge.add("0.605");
    if (EsignRepository.timerObj != null) {
      EsignRepository.timerObj.cancel();
      EsignRepository.timerObj = null;
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: SafeArea(
            child: SingleChildScrollView(
                child: Container(
      child: Column(
        children: [
          header(),
          paymentDetailsForm(),
          btn(),
        ],
      ),
    ))));
  }

  header() {
    return Container(
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          Container(
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
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                ],
              ),
            ),
          ),
          InkWell(
            onTap: () {
              LoginRepository.Esignflag = 0;
              // Navigator.push(
              //   context,
              //   MaterialPageRoute(builder: (context) => LoginUI()),
              // );
              Navigator.of(context).pushAndRemoveUntil(
                MaterialPageRoute(
                  builder: (context) => LoginUI(),
                ),
                (Route<dynamic> route) => false,
              );
            },
            child: Container(
              child: Row(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: [
                  Container(
                    width: 50,
                    height: 20,
                    child: SvgPicture.asset(
                      'asset/svg/signout.svg',
                      color: Colors.blue,
                      height: 25,
                      width: 25,
                    ),
                  ),
                  Container(
                    padding: EdgeInsets.only(right: 15),
                    child: Text(
                      "SIGN OUT",
                      style: TextStyle(
                          color: Color(0xFF0066CC),
                          fontFamily: 'century_gothic',
                          fontSize: 11,
                          fontWeight: FontWeight.w600),
                    ),
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
      padding: EdgeInsets.only(left: 20, top: 75),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.spaceAround,
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
                          padding: EdgeInsets.only(left: 50),
                          child: Text(
                            "BEGINNER PACK",
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
              Expanded(
                child: Container(
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: [
                      Expanded(
                        child: Container(
                          alignment: Alignment.centerLeft,
                          padding: EdgeInsets.only(left: 50),
                          child: Text(
                            "PROFESSIONAL PACK",
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
                          padding: EdgeInsets.only(right: 50),
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
                  margin: EdgeInsets.only(top: 15),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: [
                      Padding(
                        padding: const EdgeInsets.only(left: 50),
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
                                color: Color(0xFF000000),
                                fontSize: 12,
                                fontFamily: 'century_gothic',
                                fontWeight: FontWeight.w500),
                          ),
                        ),
                      ),
                      Expanded(
                        child: Container(
                          alignment: Alignment.centerRight,
                          child: Text(
                            "2nd Year onwards 999/-",
                            style: TextStyle(
                                color: Color(0xFF0066CC),
                                fontSize: 12,
                                fontFamily: 'century_gothic',
                                fontWeight: FontWeight.w800),
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
              Expanded(
                child: Container(
                  margin: EdgeInsets.only(top: 15),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: [
                      Padding(
                        padding: const EdgeInsets.only(left: 50),
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
                                color: Color(0xFF000000),
                                fontSize: 12,
                                fontFamily: 'century_gothic',
                                fontWeight: FontWeight.w500),
                          ),
                        ),
                      ),
                      Expanded(
                        child: Container(
                          alignment: Alignment.centerRight,
                          padding: EdgeInsets.only(right: 50),
                          child: Text(
                            "1599/-",
                            style: TextStyle(
                                color: Color(0xFF0066CC),
                                fontSize: 12,
                                fontFamily: 'century_gothic',
                                fontWeight: FontWeight.w800),
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
                  margin: EdgeInsets.only(top: 15),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceAround,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: [
                      Padding(
                        padding: const EdgeInsets.only(left: 50),
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
                                color: Color(0xFF000000),
                                fontSize: 12,
                                fontFamily: 'century_gothic',
                                fontWeight: FontWeight.w500),
                          ),
                        ),
                      ),
                      Expanded(
                        child: Container(
                          alignment: Alignment.centerRight,
                          child: Text(
                            "Free",
                            style: TextStyle(
                                color: Color(0xFF0066CC),
                                fontSize: 12,
                                fontFamily: 'century_gothic',
                                fontWeight: FontWeight.w800),
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
              Expanded(
                child: Container(
                  margin: EdgeInsets.only(top: 15),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: [
                      Padding(
                        padding: const EdgeInsets.only(left: 50),
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
                                color: Color(0xFF000000),
                                fontSize: 12,
                                fontFamily: 'century_gothic',
                                fontWeight: FontWeight.w500),
                          ),
                        ),
                      ),
                      Expanded(
                        child: Container(
                          alignment: Alignment.centerRight,
                          padding: EdgeInsets.only(right: 50),
                          child: Text(
                            "Free",
                            style: TextStyle(
                                color: Color(0xFF0066CC),
                                fontSize: 12,
                                fontFamily: 'century_gothic',
                                fontWeight: FontWeight.w800),
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
                  margin: EdgeInsets.only(top: 15),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceAround,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: [
                      Padding(
                        padding: const EdgeInsets.only(left: 50),
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
                                color: Color(0xFF000000),
                                fontSize: 12,
                                fontFamily: 'century_gothic',
                                fontWeight: FontWeight.w500),
                          ),
                        ),
                      ),
                      Expanded(
                        child: Container(
                          alignment: Alignment.centerRight,
                          child: Text(
                            "15 Rs Per Order",
                            style: TextStyle(
                                color: Color(0xFF0066CC),
                                fontSize: 12,
                                fontFamily: 'century_gothic',
                                fontWeight: FontWeight.w800),
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
              Expanded(
                child: Container(
                  margin: EdgeInsets.only(top: 15),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: [
                      Padding(
                        padding: const EdgeInsets.only(left: 50),
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
                                color: Color(0xFF000000),
                                fontSize: 12,
                                fontFamily: 'century_gothic',
                                fontWeight: FontWeight.w500),
                          ),
                        ),
                      ),
                      Expanded(
                        child: Container(
                          alignment: Alignment.centerRight,
                          padding: EdgeInsets.only(right: 50),
                          child: Text(
                            "15 Rs Per Order",
                            style: TextStyle(
                                fontSize: 12,
                                color: Color(0xFF0066CC),
                                fontFamily: 'century_gothic',
                                fontWeight: FontWeight.w800),
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
                          padding: EdgeInsets.only(left: 50),
                          child: Text(
                            "TOTAL PAYABLE",
                            style: TextStyle(
                                color: Color(0xFFFAB804),
                                fontFamily: 'century_gothic',
                                fontSize: 15,
                                fontWeight: FontWeight.w500),
                          ),
                        ),
                      ),
                      Expanded(
                        child: Container(
                          alignment: Alignment.centerRight,
                          child: Text(
                            "0",
                            style: TextStyle(
                                color: Color(0xFFFAB804),
                                fontFamily: 'century_gothic',
                                fontSize: 15,
                                fontWeight: FontWeight.w500),
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
              Expanded(
                child: Container(
                  margin: EdgeInsets.only(top: 30),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: [
                      Expanded(
                        child: Container(
                          alignment: Alignment.centerLeft,
                          padding: EdgeInsets.only(left: 50),
                          child: Text(
                            "TOTAL PAYABLE",
                            style: TextStyle(
                                color: Color(0xFFFAB804),
                                fontFamily: 'century_gothic',
                                fontSize: 15,
                                fontWeight: FontWeight.w500),
                          ),
                        ),
                      ),
                      Expanded(
                        child: Container(
                          alignment: Alignment.centerRight,
                          padding: EdgeInsets.only(right: 50),
                          child: Text(
                            "0",
                            style: TextStyle(
                                color: Color(0xFFFAB804),
                                fontFamily: 'century_gothic',
                                fontSize: 15,
                                fontWeight: FontWeight.w500),
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
      width: 235,
      height: 35,
      margin: EdgeInsets.only(top: 50, left: 4, bottom: 4, right: 4), //
      decoration: BoxDecoration(
          border: Border.all(
            color: Colors.grey[400],
          ),
          borderRadius: BorderRadius.circular(
              20) // use instead of BorderRadius.all(Radius.circular(20))
          ),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.center,
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Container(
            width: 100,
            margin: EdgeInsets.only(right: 25),
            child: TextButton(
              child: Text(
                "PAY NOW",
                style: TextStyle(
                    color: Color(0xFFFFFFFF),
                    fontFamily: 'century_gothic',
                    fontSize: 12,
                    fontWeight: FontWeight.w500),
              ),
              style: ButtonStyle(
                padding:
                    MaterialStateProperty.all<EdgeInsets>(EdgeInsets.all(1)),
                foregroundColor: MaterialStateProperty.all<Color>(Colors.white),
                shape: MaterialStateProperty.all<RoundedRectangleBorder>(
                    RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(15),
                        side: BorderSide(color: Color(0xFF0074C4)))),
                backgroundColor:
                    MaterialStateProperty.all<Color>(Color(0xFF0074C4)),
              ),
              onPressed: () => clickPayNowbtn(context),
            ),
          ),
          Container(
            width: 100,
            child: TextButton(
              child: Text(
                "PAY LATER",
                style: TextStyle(
                    color: Color(0xFFFFFFFF),
                    fontFamily: 'century_gothic',
                    fontSize: 12,
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
      this.paymentbloc.navtodocumentdetail(context, "0", "large");
    } else if (professionalchecked) {
      // if professional pack is selected we store 1 in DB
      this.paymentbloc.navtodocumentdetail(context, "1", "large");
    } else {}
  }

  clickPayLaterbtn(BuildContext context) {
    if (beginerchecked) {
      // if beginer pack is selected we store 0 in DB
      this.paymentbloc.navtodocumentdetail(context, "0", "large");
    } else if (professionalchecked) {
      // if professional pack is selected we store 1 in DB
      this.paymentbloc.navtodocumentdetail(context, "1", "large");
    } else {}
  }
}
