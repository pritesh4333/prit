import 'dart:html';

import 'package:ekyc_admin/Network%20Manager/AppURLs/app_url_main.dart';
import 'package:flutter/material.dart';

import 'package:google_maps/google_maps.dart';
import 'dart:ui' as ui;

import '../models/response/common_data_grid_table_response_model.dart';
import 'admin_data_grid.dart';

class GetLocation extends StatefulWidget {
  const GetLocation({Key? key}) : super(key: key);

  @override
  State<GetLocation> createState() => _GetLocationState();
}

class _GetLocationState extends State<GetLocation> {
  CommonDataGridTableResponseModel? globalRespObj = AdminDataGridScreen.adminDetailGlobalResponseObj;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: Container(
          color: Colors.white,
          padding: EdgeInsets.all(22),
          child: getMap(),
        ),
      ),
    );
  }

  Widget getMap() {
    String htmlId = "7";

    // ignore: undefined_prefixed_name
    ui.platformViewRegistry.registerViewFactory(htmlId, (int viewId) {
      final myLatlng = new LatLng(double.parse(globalRespObj!.latitude), double.parse(globalRespObj!.longitude));

      final mapOptions = new MapOptions()
        ..zoom = 12
        ..scrollwheel = true
        ..streetViewControl = true
        ..backgroundColor = "#000000"
        ..center = new LatLng(double.parse(globalRespObj!.latitude), double.parse(globalRespObj!.longitude));

      final elem = DivElement()
        ..id = htmlId
        ..style.width = "100%"
        ..style.height = "100%"
        ..style.border = '100%';
      var markerImage = getBaseAPIUrl() + 'images/location.png';

      final map = new GMap(elem, mapOptions);
      Marker(MarkerOptions()
        ..map = map
        ..icon = markerImage
        ..title = globalRespObj!.panfullname
        ..position = myLatlng);

      return elem;
    });

    return HtmlElementView(viewType: htmlId);
  }
}
