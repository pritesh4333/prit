import 'package:flutter/material.dart';

import '../../../Network Manager/Models/network_manager.dart';

class LoginRepository {
  Future<Object?> singinToApp(BuildContext context, String userName, String password) => NetworkManager(context).authenticateUser(gscid: userName, pass: password);
}
