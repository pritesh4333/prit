class DashBoardResponseModel {
  Data? data;

  DashBoardResponseModel({this.data});

  DashBoardResponseModel.fromJson(Map<String, dynamic> json) {
    data = json['data'] != null ? Data.fromJson(json['data']) : null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = Map<String, dynamic>();
    if (this.data != null) {
      data['data'] = this.data!.toJson();
    }
    return data;
  }
}

class Data {
  List<Stats>? stats;

  Data({this.stats});

  Data.fromJson(Map<String, dynamic> json) {
    if (json['stats'] != null) {
      stats = <Stats>[];
      json['stats'].forEach((v) {
        stats!.add(Stats.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = Map<String, dynamic>();
    if (this.stats != null) {
      data['stats'] = stats!.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class Stats {
  String? stage;
  String? reporttitle;
  int? count;

  Stats({this.stage, this.reporttitle, this.count});

  Stats.fromJson(Map<String, dynamic> json) {
    stage = json['stage'];
    reporttitle = json['reporttitle'];
    count = json['count'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = Map<String, dynamic>();
    data['stage'] = stage;
    data['reporttitle'] = reporttitle;
    data['count'] = count;
    return data;
  }
}
