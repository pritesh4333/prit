import 'package:flutter/material.dart';
import 'package:flutter_assignment/HomePage/Repository/AnimeRepository.dart';
import 'package:flutter_assignment/HomePage/model/AnimeModel.dart';
import 'package:rxdart/rxdart.dart';

class HomePageBloc {
  AnimeRepository _animeRepository = AnimeRepository();

  List<BehaviorSubject<AnimeModel>> futureStream = <BehaviorSubject<AnimeModel>>[];
  final mainStream = BehaviorSubject.seeded(false);

  fetchAnime() async {
    AnimeModel animeModel = await _animeRepository.getAnime();
    futureStream = List.generate(
      animeModel.data!.length,
      (index) {
        return BehaviorSubject<AnimeModel>.seeded(animeModel);
      },
    );

    mainStream.sink.add(true);
  }

  serachAnim(String text, BuildContext context) async {
    AnimeModel animModel = await _animeRepository.getAnimeSearch(text);
    futureStream = List.generate(
      animModel.data!.length,
      (index) {
        return BehaviorSubject<AnimeModel>.seeded(animModel);
      },
    );

    mainStream.sink.add(true);
  }
}
