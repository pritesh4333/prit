import 'package:flutter/src/widgets/framework.dart';
import 'package:flutter_assignment/HomePage/Repository/AnimeRepository.dart';
 import 'package:flutter_assignment/HomePage/model/AdimeModelDetails.dart';
import 'package:flutter_assignment/HomePage/model/AnimImagesModel.dart';
import 'package:flutter_assignment/HomePage/model/AnimeModel.dart';

class AnimDetailsBloc {
  AnimeRepository _animeRepository = AnimeRepository();

  List<String> imageslist = [];
  Future<AnimDetailsModel> fetchAnimeDetails(String string, BuildContext context) async {
    // AppConfig().showLoaderDialog(context);
    var animeModel = await _animeRepository.getAnimeDetails(string);
    // AppConfig().dismissLoaderDialog(context);

    return animeModel;
  }

  Future<List<String>> fetchAnimDetailsimages(String arguments, BuildContext context) async {
    // AppConfig().showLoaderDialog(context);
    AnimImagesModel animeModel = await _animeRepository.getAnimeImages(arguments);
    for (var i = 0; i < animeModel.data!.length; i++) {
      imageslist.add(animeModel.data![i].jpg!.largeImageUrl.toString());
    }
    // AppConfig().dismissLoaderDialog(context);
    return imageslist;
  }
}
