import 'package:ekyc_admin/Extension_Enum/greek_enum.dart';
import 'package:intl/intl.dart';

extension APIExtension on String {}

extension GreekDate on DateTime {
  String toStringWithFormated({required String dateFormat}) {
    final formate = DateFormat(dateFormat);
    final resultDate = formate.format(this);
    return resultDate;
  }

  DateTime addDayinCurrentDate({required int days}) {
    final currentDate = this;
    final newDate =
        DateTime(currentDate.year, currentDate.month, (currentDate.day + days));
    return newDate;
  }

  DateTime minusDayinCurrentDate({required int days}) {
    final currentDate = this;
    final newDate =
        DateTime(currentDate.year, currentDate.month, (currentDate.day - days));
    return newDate;
  }

  DateTime getLicenseExpiryDate(
      {required GreekLicenseValidity licenseValidity}) {
    final currentDay = day;
    DateTime expiryDate = this;

    if (currentDay < 25) {
      expiryDate = DateTime(
          year,
          (month + ((licenseValidity == GreekLicenseValidity.monthly) ? 1 : 3)),
          5);
    } else {
      expiryDate = DateTime(
          year,
          (month + ((licenseValidity == GreekLicenseValidity.monthly) ? 2 : 4)),
          5);
    }

    return expiryDate;
  }
}

extension StringExtension on String {
  String capitalizeFirstofEach() {
    final text = this;
    if (text.isEmpty) {
      return '';
    }

    final l = text.split(" ").where((element) => element.isNotEmpty).toList();
    if (l.isNotEmpty) {
      final joinString = l.map((str) {
        final s = '${str[0].toUpperCase()}${str.substring(1).toLowerCase()}';
        return s;
      }).join(" ");

      return joinString.trim();
    } else if ((text.isNotEmpty) && (l.isEmpty)) {
      return '${text[0].toUpperCase()}${text.substring(1).toLowerCase()}'
          .trim();
    }

    return '';
  }

  LicenseRequestStatus toLicenseRequestEnumValue() {
    for (var item in LicenseRequestStatus.values) {
      if (item.name.toLowerCase().compareTo(toLowerCase()) == 0) {
        return item;
      }
    }

    return LicenseRequestStatus.Pending;
  }

  DateTime? toDate() {
    return DateTime.tryParse(this);
  }
}
