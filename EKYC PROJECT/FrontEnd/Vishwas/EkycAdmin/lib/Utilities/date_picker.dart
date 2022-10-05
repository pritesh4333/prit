import 'package:flutter/material.dart';
import 'package:intl/intl.dart';

///Date picker imports
import 'package:syncfusion_flutter_datepicker/datepicker.dart' as picker;

/// Builds the date range picker inside a pop-up based on the properties passed,
/// and return the selected date or range based on the tripe mode selected.
class DateRangePicker extends StatefulWidget {
  /// Creates Date range picker
  const DateRangePicker(this.date, this.range,
      {Key? key, this.minDate, this.maxDate, this.displayDate})
      : super(key: key);

  /// Holds date value
  final dynamic date;

  /// Holds date range value
  final dynamic range;

  /// Holds minimum date value
  final dynamic minDate;

  /// Holds maximum date value
  final dynamic maxDate;

  /// Holds showable date value
  final dynamic displayDate;

  @override
  State<StatefulWidget> createState() {
    return _DateRangePickerState();
  }
}

class _DateRangePickerState extends State<DateRangePicker> {
  dynamic _date;
  dynamic _controller;
  dynamic _range;
  late bool _isWeb;

  @override
  void initState() {
    _date = widget.date;
    _range = widget.range;

    _controller = picker.DateRangePickerController();

    _isWeb = false;
    super.initState();
  }

  @override
  void didChangeDependencies() {
    //// Extra small devices (phones, 600px and down)
//// @media only screen and (max-width: 600px) {...}
////
//// Small devices (portrait tablets and large phones, 600px and up)
//// @media only screen and (min-width: 600px) {...}
////
//// Medium devices (landscape tablets, 768px and up)
//// media only screen and (min-width: 768px) {...}
////
//// Large devices (laptops/desktops, 992px and up)
//// media only screen and (min-width: 992px) {...}
////
//// Extra large devices (large laptops and desktops, 1200px and up)
//// media only screen and (min-width: 1200px) {...}
//// Default width to render the mobile UI in web, if the device width exceeds
//// the given width agenda view will render the web UI.
    _isWeb = MediaQuery.of(context).size.width > 767;

    super.didChangeDependencies();
  }

  @override
  Widget build(BuildContext context) {
    final Widget selectedDateWidget = Container(
        color: Colors.transparent,
        padding: const EdgeInsets.symmetric(vertical: 16.0),
        child: Container(
            height: 30,
            padding: const EdgeInsets.symmetric(horizontal: 4.0),
            child: _range == null ||
                    _range.startDate == null ||
                    _range.endDate == null ||
                    _range.startDate == _range.endDate
                ? Text(
                    DateFormat('dd MMM, yyyy').format(_range == null
                        ? _date
                        : (_range.startDate ?? _range.endDate)),
                    textAlign: TextAlign.center,
                    style: const TextStyle(
                        fontSize: 18,
                        fontWeight: FontWeight.w600,
                        color: Colors.black),
                  )
                : Row(
                    children: <Widget>[
                      Expanded(
                        flex: 5,
                        child: Text(
                          DateFormat('dd MMM, yyyy').format(
                              _range.startDate.isAfter(_range.endDate) == true
                                  ? _range.endDate
                                  : _range.startDate),
                          maxLines: 1,
                          overflow: TextOverflow.ellipsis,
                          textAlign: TextAlign.center,
                          style: const TextStyle(
                              fontSize: 18,
                              fontWeight: FontWeight.w600,
                              color: Colors.black),
                        ),
                      ),
                      const VerticalDivider(
                        thickness: 1,
                      ),
                      Expanded(
                        flex: 5,
                        child: Text(
                          DateFormat('dd MMM, yyyy').format(
                              _range.startDate.isAfter(_range.endDate) == true
                                  ? _range.startDate
                                  : _range.endDate),
                          maxLines: 1,
                          overflow: TextOverflow.ellipsis,
                          textAlign: TextAlign.center,
                          style: const TextStyle(
                              fontSize: 18,
                              fontWeight: FontWeight.w600,
                              color: Colors.black),
                        ),
                      ),
                    ],
                  )));

    _controller.selectedDate = _date;
    _controller.selectedRange = _range;
    Widget pickerWidget;

    pickerWidget = picker.SfDateRangePicker(
      controller: _controller,
      initialDisplayDate: widget.displayDate,
      showNavigationArrow: true,
      showActionButtons: true,
      view: picker.DateRangePickerView.month,
      onCancel: () => Navigator.pop(context, null),
      enableMultiView: _range != null && _isWeb,
      selectionMode: _range == null
          ? picker.DateRangePickerSelectionMode.single
          : picker.DateRangePickerSelectionMode.range,
      minDate: widget.minDate,
      maxDate: widget.maxDate,
      showTodayButton: true,
      todayHighlightColor: Colors.blueAccent,
      headerStyle: const picker.DateRangePickerHeaderStyle(
          textAlign: TextAlign.center,
          textStyle: TextStyle(color: Colors.black, fontSize: 15)),
      onSubmit: (Object? value) {
        if (_range == null) {
          Navigator.pop(context, _date);
        } else {
          Navigator.pop(context, _range);
        }
      },
      onSelectionChanged: (picker.DateRangePickerSelectionChangedArgs details) {
        setState(() {
          if (_range == null) {
            _date = details.value;
          } else {
            _range = details.value;
          }
        });
      },
    );

    return Dialog(
        backgroundColor: Colors.white,
        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(4)),
        child: Container(
            height: 400,
            width: _range != null && _isWeb ? 500 : 300,
            color: Colors.white,
            child: Column(
              mainAxisSize: MainAxisSize.min,
              crossAxisAlignment: CrossAxisAlignment.stretch,
              children: <Widget>[
                selectedDateWidget,
                Flexible(
                    child: Padding(
                        padding: const EdgeInsets.symmetric(
                            vertical: 0, horizontal: 5),
                        child: pickerWidget)),
              ],
            )));
  }
}
