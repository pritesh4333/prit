import 'package:flutter/material.dart';
import 'package:flutter_animated_dialog/flutter_animated_dialog.dart';
import 'package:intl/intl.dart';

class ReKycSearchActionDialog extends StatefulWidget {
  const ReKycSearchActionDialog({
    Key? key,
  }) : super(key: key);

  @override
  State<ReKycSearchActionDialog> createState() => _ReKycSearchActionDialogState();
}

class _ReKycSearchActionDialogState extends State<ReKycSearchActionDialog> {
  @override
  void initState() {
    super.initState();
  }

  DateTime selectedDate = DateTime.now();
  TextEditingController dateInputFrom = TextEditingController();
  TextEditingController dateInputTo = TextEditingController();

  Future<void> selectDate(BuildContext context, String from) async {
    final DateTime? picked = await showDatePicker(
      context: context,
      initialDate: selectedDate,
      firstDate: DateTime(2015, 8),
      lastDate: DateTime(2101),
    );
    if (picked != null && picked != selectedDate) {
      setState(() {
        selectedDate = picked;
        String formattedDate = DateFormat('dd-MM-yyyy').format(selectedDate);
        if (from.toLowerCase() == 'from') {
          dateInputFrom.text = formattedDate;
        } else {
          dateInputTo.text = formattedDate;
        }
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Dialog(
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(12.0),
      ),
      elevation: 16.0,
      child: SizedBox(
          height: 220,
          width: 350,
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
              Row(
                children: [
                  Container(
                    width: 280,
                    height: 40,
                    margin: const EdgeInsets.fromLTRB(30, 5, 30, 12),
                    child: TextField(
                      style: const TextStyle(fontSize: 14.0, fontFamily: 'Roboto', color: Color(0xff3E3E3E)),
                      controller: dateInputFrom,
                      readOnly: true,
                      decoration: InputDecoration(
                        suffixIcon: Icon(
                          Icons.calendar_month_outlined,
                          color: Colors.blue.shade300,
                        ),
                        border: const OutlineInputBorder(borderRadius: BorderRadius.all(Radius.circular(6.0))),
                        labelText: 'From',
                      ),
                      onTap: () {
                        selectDate(context, "from");
                      },
                    ),
                  ),
                ],
              ),
              Row(
                children: [
                  Container(
                    width: 280,
                    height: 40,
                    margin: const EdgeInsets.symmetric(horizontal: 30, vertical: 10),
                    child: TextField(
                      style: const TextStyle(fontSize: 14.0, fontFamily: 'Roboto', color: Color(0xff3E3E3E)),
                      controller: dateInputTo,
                      readOnly: true,
                      decoration: InputDecoration(
                        suffixIcon: Icon(
                          Icons.calendar_month_outlined,
                          color: Colors.blue.shade300,
                        ),
                        border: const OutlineInputBorder(borderRadius: BorderRadius.all(Radius.circular(6.0))),
                        labelText: 'To',
                      ),
                      onTap: () {
                        selectDate(context, "to");
                      },
                    ),
                  ),
                ],
              ),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Container(
                    margin: const EdgeInsets.symmetric(horizontal: 30.0, vertical: 10),
                    child: ElevatedButton(
                      onPressed: () {},
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
