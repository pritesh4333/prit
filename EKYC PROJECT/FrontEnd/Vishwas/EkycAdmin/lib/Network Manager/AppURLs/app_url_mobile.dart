import 'package:ekyc_admin/Network%20Manager/Models/network_manager.dart';
import 'package:flutter/foundation.dart';

String getBaseAPIUrl() {
  return (kReleaseMode)
      ? NetworkURLs.live_server_url
      : (kProfileMode)
          ? NetworkURLs.tester_url
          : NetworkURLs.tester_url;
}
