import 'package:flutter/material.dart';

class ActionDialog extends StatelessWidget {
  const ActionDialog({
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Dialog(
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(12.0),
      ),
      elevation: 16.0,
      child: SizedBox(
          height: 210,
          width: 500,
          child: Column(
            children: [
              Row(
                crossAxisAlignment: CrossAxisAlignment.start,
                mainAxisAlignment: MainAxisAlignment.end,
                children: [
                  IconButton(
                    hoverColor: Colors.transparent,
                    icon: const Icon(Icons.close),
                    color: Colors.black,
                    onPressed: () {
                      Navigator.pop(context);
                    },
                  ),
                ],
              ),
              Container(
                height: 40,
                margin: const EdgeInsets.symmetric(horizontal: 25),
                child: const TextField(
                  style: TextStyle(fontSize: 14.0, fontFamily: 'Roboto', color: Color(0xff3E3E3E)),
                  textAlign: TextAlign.start,
                  keyboardType: TextInputType.text,
                  decoration: InputDecoration(
                    border: OutlineInputBorder(borderRadius: BorderRadius.all(Radius.circular(6.0))),
                    labelText: 'Name',
                    // hintText: 'Enter Name Here',
                  ),
                  autofocus: false,
                ),
              ),
              Row(
                children: [
                  Container(
                    width: 200,
                    height: 40,
                    margin: const EdgeInsets.symmetric(horizontal: 25, vertical: 15),
                    child: const TextField(
                      style: TextStyle(fontSize: 14.0, fontFamily: 'Roboto', color: Color(0xff3E3E3E)),
                      textAlign: TextAlign.start,
                      keyboardType: TextInputType.text,
                      decoration: InputDecoration(
                        border: OutlineInputBorder(borderRadius: BorderRadius.all(Radius.circular(6.0))),
                        labelText: 'Status',
                        // hintText: 'Enter Name Here',
                      ),
                      autofocus: false,
                    ),
                  ),
                  Container(
                    width: 200,
                    height: 40,
                    margin: const EdgeInsets.symmetric(horizontal: 25, vertical: 15),
                    child: const TextField(
                      style: TextStyle(fontSize: 14.0, fontFamily: 'Roboto', color: Color(0xff3E3E3E)),
                      textAlign: TextAlign.start,
                      keyboardType: TextInputType.text,
                      decoration: InputDecoration(
                        border: OutlineInputBorder(borderRadius: BorderRadius.all(Radius.circular(6.0))),
                        labelText: 'Remarks',
                        // hintText: 'Enter Name Here',
                      ),
                      autofocus: false,
                    ),
                  ),
                ],
              ),
              Row(
                children: [
                  Container(
                    margin: const EdgeInsets.symmetric(horizontal: 25.0),
                    height: 50,
                    width: 200,
                    color: Colors.white,
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Row(
                          children: [
                            Checkbox(
                              value: false,
                              onChanged: (_) {},
                            ),
                            const Text(
                              'Reject',
                              style: TextStyle(
                                color: Colors.white,
                                fontFamily: 'Roboto',
                                fontSize: 14,
                                fontWeight: FontWeight.w600,
                              ),
                            ),
                          ],
                        ),
                        ElevatedButton(
                          onPressed: () {},
                          style: ButtonStyle(
                            backgroundColor: MaterialStateProperty.all<Color>(const Color(0xffFAB804)),
                            fixedSize: MaterialStateProperty.all<Size>(const Size(85, 15)),
                            shape: MaterialStateProperty.all<RoundedRectangleBorder>(
                              RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(30),
                              ),
                            ),
                          ),
                          // style: ElevatedButton.styleFrom(
                          //   primary: const Color(0xffFAB804),
                          //   fixedSize: Size(85, 15),
                          // ),
                          child: const Text(
                            'Update',
                            style: TextStyle(
                              letterSpacing: 0.8,
                              color: Colors.white,
                              fontFamily: 'Roboto',
                              fontSize: 12,
                              fontWeight: FontWeight.w600,
                            ),
                            textAlign: TextAlign.start,
                          ),
                        ),
                      ],
                    ),
                  ),
                  Container(
                    margin: const EdgeInsets.symmetric(horizontal: 25.0),
                    width: 200,
                    color: Colors.white,
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.end,
                      children: [
                        Row(
                          children: [
                            ElevatedButton(
                              onPressed: () {},
                              style: ButtonStyle(
                                backgroundColor: MaterialStateProperty.all<Color>(const Color(0xff00258E)),
                                fixedSize: MaterialStateProperty.all<Size>(const Size(160, 30)),
                                shape: MaterialStateProperty.all<RoundedRectangleBorder>(
                                  RoundedRectangleBorder(
                                    borderRadius: BorderRadius.circular(30),
                                  ),
                                ),
                              ),
                              // style: ElevatedButton.styleFrom(
                              //   primary: const Color(0xff00258E),
                              //   fixedSize: Size(160, 30),
                              // ),
                              child: const Text(
                                'Send Follow up Mail',
                                style: TextStyle(
                                  letterSpacing: 0.8,
                                  color: Colors.white,
                                  fontFamily: 'Roboto',
                                  fontSize: 12,
                                  fontWeight: FontWeight.w600,
                                ),
                                textAlign: TextAlign.start,
                              ),
                            ),
                          ],
                        )
                      ],
                    ),
                  ),
                ],
              ),
            ],
          )),
    );
  }
}
