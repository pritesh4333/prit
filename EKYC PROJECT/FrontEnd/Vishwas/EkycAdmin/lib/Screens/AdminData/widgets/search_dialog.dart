import 'package:ekyc_admin/Configuration/app_config.dart';
import 'package:ekyc_admin/Configuration/greek_navigation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:tuple/tuple.dart';

import '../../../Utilities/UpperCaseTextFormatter.dart';

class SearchActionDialog extends StatelessWidget {
  const SearchActionDialog({
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    var nameController = TextEditingController();
    var emailController = TextEditingController();
    var panController = TextEditingController();
    var mobilenoController = TextEditingController();

    return Dialog(
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(12.0),
      ),
      elevation: 16.0,
      child: SizedBox(
          height: 250,
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
                      GreekNavigator.pop(context: context, popArguments: null);
                    },
                  ),
                ],
              ),
              Row(
                children: [
                  Container(
                    width: 200,
                    height: 50,
                    margin: const EdgeInsets.symmetric(horizontal: 25),
                    child: TextField(
                      style: TextStyle(fontSize: 14.0, fontFamily: 'Roboto', color: Color(0xff3E3E3E)),
                      textAlign: TextAlign.start,
                      keyboardType: TextInputType.text,
                      controller: nameController,
                      maxLength: 20,
                      inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z0-9 ,./()-]')), UpperCaseTextFormatter()],
                      decoration: InputDecoration(
                        border: OutlineInputBorder(borderRadius: BorderRadius.all(Radius.circular(6.0))),
                        labelText: 'Name',
                        counterText: "",
                        // hintText: 'Enter Name Here',
                      ),
                      autofocus: false,
                    ),
                  ),
                  Container(
                    width: 200,
                    height: 50,
                    margin: const EdgeInsets.symmetric(horizontal: 25, vertical: 15),
                    child: TextField(
                      style: TextStyle(fontSize: 14.0, fontFamily: 'Roboto', color: Color(0xff3E3E3E)),
                      textAlign: TextAlign.start,
                      keyboardType: TextInputType.text,
                      controller: emailController,
                      inputFormatters: [UpperCaseTextFormatter()],
                      decoration: InputDecoration(
                        border: OutlineInputBorder(borderRadius: BorderRadius.all(Radius.circular(6.0))),
                        labelText: 'Email',
                        counterText: "",
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
                    width: 200,
                    height: 50,
                    margin: const EdgeInsets.symmetric(horizontal: 25),
                    child: TextField(
                      style: TextStyle(fontSize: 14.0, fontFamily: 'Roboto', color: Color(0xff3E3E3E)),
                      textAlign: TextAlign.start,
                      keyboardType: TextInputType.text,
                      controller: mobilenoController,
                      inputFormatters: <TextInputFormatter>[
                        FilteringTextInputFormatter.allow(RegExp(r'[0-9]')),
                      ],
                      maxLength: 10,
                      decoration: InputDecoration(
                        border: OutlineInputBorder(borderRadius: BorderRadius.all(Radius.circular(6.0))),
                        labelText: 'Mobile No',
                        counterText: "",
                        // hintText: 'Enter Name Here',
                      ),
                      autofocus: false,
                    ),
                  ),
                  Container(
                    width: 200,
                    height: 50,
                    margin: const EdgeInsets.symmetric(horizontal: 25, vertical: 15),
                    child: TextField(
                      style: TextStyle(fontSize: 14.0, fontFamily: 'Roboto', color: Color(0xff3E3E3E)),
                      textAlign: TextAlign.start,
                      keyboardType: TextInputType.text,
                      controller: panController,
                      maxLength: 10,
                      inputFormatters: <TextInputFormatter>[FilteringTextInputFormatter.allow(RegExp("[0-9a-zA-Z]")), UpperCaseTextFormatter()],
                      decoration: InputDecoration(
                        border: OutlineInputBorder(borderRadius: BorderRadius.all(Radius.circular(6.0))),
                        labelText: 'PAN',
                        counterText: "",
                        // hintText: 'Enter Name Here',
                      ),
                      autofocus: false,
                    ),
                  ),
                ],
              ),
              Row(
                mainAxisAlignment: MainAxisAlignment.end,
                children: [
                  Container(
                    margin: const EdgeInsets.symmetric(horizontal: 25.0),
                    child: ElevatedButton(
                      onPressed: () {
                        //CommonDataGridSource.searchDataTable("prit", "parmarprit100@gmail.com", "8767957178", "BWXPP5879A");
                        GreekNavigator.pop(
                          context: context,
                          popArguments: const Tuple5("", "", "", "", "true"),
                        );
                      },
                      style: ButtonStyle(
                        backgroundColor: MaterialStateProperty.all<Color>(const Color(0xff00258E)),
                        fixedSize: MaterialStateProperty.all<Size>(const Size(75, 30)),
                        shape: MaterialStateProperty.all<RoundedRectangleBorder>(
                          RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(30),
                          ),
                        ),
                      ),
                      // style: ElevatedButton.styleFrom(primary: const Color(0xff00258E), fixedSize: const Size(75, 30)),
                      child: const Text(
                        'Reset',
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
                  ),
                  Container(
                    margin: const EdgeInsets.symmetric(horizontal: 25.0),
                    child: ElevatedButton(
                      onPressed: () {
                        //CommonDataGridSource.searchDataTable("prit", "parmarprit100@gmail.com", "8767957178", "BWXPP5879A");
                        if (nameController.text.isEmpty && emailController.text.isEmpty && mobilenoController.text.isEmpty && panController.text.isEmpty) {
                          AppConfig().showAlert(context, "No search field is specified at least one field is required");
                        } else {
                          GreekNavigator.pop(
                            context: context,
                            popArguments: Tuple5(nameController.value.text, emailController.value.text, mobilenoController.value.text, panController.value.text, "false"),
                          );
                        }
                      },
                      style: ButtonStyle(
                        backgroundColor: MaterialStateProperty.all<Color>(const Color(0xff00258E)),
                        fixedSize: MaterialStateProperty.all<Size>(const Size(75, 30)),
                        shape: MaterialStateProperty.all<RoundedRectangleBorder>(
                          RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(30),
                          ),
                        ),
                      ),
                      // style: ElevatedButton.styleFrom(primary: const Color(0xff00258E), fixedSize: const Size(75, 30)),
                      child: const Text(
                        'Search',
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
                  ),
                ],
              )
            ],
          )),
    );
  }
}
