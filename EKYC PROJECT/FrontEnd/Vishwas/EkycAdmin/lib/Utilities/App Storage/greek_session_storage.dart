// ignore_for_file: no_leading_underscores_for_local_identifiers

class GreekSessionStorage {
  Map<String, String> appStorage = <String, String>{};

  // Singleton object
  static final GreekSessionStorage _singleton = GreekSessionStorage._internal();

  factory GreekSessionStorage() => _singleton;

  GreekSessionStorage._internal();

  String getValueFromSessionStorage({required String keyName}) {
    if (appStorage.containsKey(keyName)) {
      return appStorage[keyName]?.toString() ?? '';
    }

    return '';
  }

  void setValueFromSessionStorage(
      {required String keyName, required String valName}) {
    appStorage[keyName] = valName;
  }

  void removeKeyPairValueFromSessionStorage({required String keyName}) {
    final _ = appStorage.remove(keyName);
  }

  void clearAllSessionData() {
    appStorage.clear();
  }
}
