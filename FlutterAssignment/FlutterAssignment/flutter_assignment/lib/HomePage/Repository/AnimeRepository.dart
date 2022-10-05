// ignore: file_names
import 'package:flutter_assignment/HomePage/model/AdimeModelDetails.dart';
import 'package:flutter_assignment/HomePage/model/AnimImagesModel.dart';
import 'package:flutter_assignment/HomePage/model/AnimeModel.dart';
import "package:http/http.dart" as http;
import "dart:convert";

class AnimeRepository {
  final url = "https://api.jikan.moe/v4/anime";
  final APIClient = http.Client();
  getAnime() async {
    final response = await http.get(
      Uri.parse(url),
    );

    if (response.statusCode == 200) {
      print(response.body);
      var getAnimeResponseObj = AnimeModel.fromJson(json.decode(response.body));

      return getAnimeResponseObj;
    } else {
      return null;
    }
  }

  getAnimeDetails(id) async {
    final response = await http.get(
      Uri.parse(url + "/" + id),
    );

    if (response.statusCode == 200) {
      print(response.body);
      var getAnimeDetailsResponseObj = AnimDetailsModel.fromJson(json.decode(response.body));

      return getAnimeDetailsResponseObj;
    } else {
      return null;
    }
  }

  getAnimeImages(id) async {
    final response = await http.get(
      Uri.parse(url + "/" + id + "/pictures"),
    );

    if (response.statusCode == 200) {
      print(response.body);
      var getAnimeImagesResponseObj = AnimImagesModel.fromJson(json.decode(response.body));

      return getAnimeImagesResponseObj;
    } else {
      return null;
    }
  }

  getAnimeSearch(String text) async {
    final response = await http.get(
      Uri.parse(url + "?q=" + text),
    );

    if (response.statusCode == 200) {
      print(response.body);
      var getAnimeSearchResponseObj = AnimeModel.fromJson(json.decode(response.body));

      return getAnimeSearchResponseObj;
    } else {
      return null;
    }
  }
}
