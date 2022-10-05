class DashBoardResponseModel {
  String? stage;
  String? reporttitle;
  int? count;

  DashBoardResponseModel({this.stage, this.reporttitle, this.count});

  DashBoardResponseModel.fromJson(Map<String, dynamic> json) {
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
