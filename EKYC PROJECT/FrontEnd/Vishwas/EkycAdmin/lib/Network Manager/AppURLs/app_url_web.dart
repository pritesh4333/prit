// ignore_for_file: avoid_web_libraries_in_flutter

import 'dart:html';
import 'package:ekyc_admin/Network%20Manager/Models/network_manager.dart';
import 'package:flutter/foundation.dart';

String getBaseAPIUrl() {
  return (kReleaseMode)
      ? window.location.href.split('#').first
      : (kProfileMode)
          ? NetworkURLs.tester_url
          : NetworkURLs.tester_url;
}
