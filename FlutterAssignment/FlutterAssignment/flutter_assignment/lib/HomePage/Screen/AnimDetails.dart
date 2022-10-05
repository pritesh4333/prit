import 'package:carousel_slider/carousel_slider.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_assignment/HomePage/bloc/AnimDetailsBloc.dart';
import 'package:flutter_assignment/HomePage/bloc/homePageBloc.dart';
import 'package:flutter_assignment/HomePage/model/AdimeModelDetails.dart';
import 'package:flutter_assignment/HomePage/model/AnimImagesModel.dart';
import 'package:flutter_assignment/HomePage/model/AnimeModel.dart';

class AnimDetails extends StatefulWidget {
  final String title = "Details";
  final String? arguments;
  const AnimDetails({Key? key, this.arguments}) : super(key: key);

  @override
  State<AnimDetails> createState() => AnimDetailsState();
}

class AnimDetailsState extends State<AnimDetails> {
  AnimDetailsBloc _DetailsPageBloc = AnimDetailsBloc();
  int _current = 0;
  final CarouselController _controller = CarouselController();
  @override
  void initState() {
    // _DetailsPageBloc.fetchAnimDetailsimages(widget.arguments.toString());

    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
        toolbarHeight: 50,
      ),
      body: SafeArea(
        child: Container(
          color: Colors.black,
          child: Center(
            child: Column(
              children: [
                FutureBuilder<List<String>>(
                    future: _DetailsPageBloc.fetchAnimDetailsimages(widget.arguments.toString(), context),
                    builder: (context, snapshot) {
                      if (snapshot.hasData && snapshot.data!.isNotEmpty) {
                        return Container(
                          color: Colors.black,
                          padding: EdgeInsets.only(top: 10),
                          child: CarouselSlider(
                            options: CarouselOptions(
                              autoPlay: true,
                              aspectRatio: 2.0,
                              enlargeCenterPage: true,
                            ),
                            items: snapshot.data!
                                .map((item) => Container(
                                      margin: EdgeInsets.all(0.0),
                                      child: ClipRRect(
                                          borderRadius: BorderRadius.all(Radius.circular(5.0)),
                                          child: Stack(
                                            children: <Widget>[
                                              Image.network(item, fit: BoxFit.cover, width: 1500.0),
                                              Positioned(
                                                bottom: 0.0,
                                                left: 0.0,
                                                right: 0.0,
                                                child: Container(
                                                  decoration: const BoxDecoration(
                                                    gradient: LinearGradient(
                                                      colors: [Color.fromARGB(200, 0, 0, 0), Color.fromARGB(0, 0, 0, 0)],
                                                      begin: Alignment.bottomCenter,
                                                      end: Alignment.topCenter,
                                                    ),
                                                  ),
                                                  padding: EdgeInsets.symmetric(vertical: 20.0, horizontal: 20.0),
                                                ),
                                              ),
                                            ],
                                          )),
                                    ))
                                .toList(),
                          ),
                        );
                      } else {
                        return Container();
                      }
                    }),
                FutureBuilder<AnimDetailsModel>(
                    future: _DetailsPageBloc.fetchAnimeDetails(widget.arguments.toString(), context),
                    builder: (context, snapshot) {
                      if (snapshot.hasData && snapshot.data!.data!.title!.isNotEmpty) {
                        return Flexible(
                          fit: FlexFit.loose,
                          child: Container(
                            color: Colors.black,
                            padding: const EdgeInsets.all(3),
                            child: Padding(
                              padding: const EdgeInsets.symmetric(horizontal: 5, vertical: 10),
                              child: ListView(
                                shrinkWrap: true,
                                physics: AlwaysScrollableScrollPhysics(),
                                children: [
                                  Row(
                                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                    crossAxisAlignment: CrossAxisAlignment.start,
                                    children: [
                                      Flexible(
                                        child: Text(
                                          snapshot.data!.data!.title.toString(),
                                          style: const TextStyle(fontSize: 20, fontWeight: FontWeight.w600, color: Colors.blue),
                                        ),
                                      ),
                                      Flexible(
                                        child: Text(
                                          '(${snapshot.data!.data!.rating.toString()})',
                                          style: const TextStyle(fontSize: 14, fontWeight: FontWeight.w500, color: Colors.white),
                                        ),
                                      ),
                                    ],
                                  ),
                                  Container(
                                    margin: EdgeInsets.only(top: 6),
                                    child: Row(
                                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                      crossAxisAlignment: CrossAxisAlignment.start,
                                      children: [
                                        Flexible(
                                          fit: FlexFit.tight,
                                          child: Text(
                                            snapshot.data!.data!.status.toString(),
                                            style: const TextStyle(fontSize: 15, fontWeight: FontWeight.w500, color: Colors.white),
                                          ),
                                        ),
                                        Flexible(
                                          child: Text(
                                            'Score - (${snapshot.data!.data!.score.toString()})',
                                            style: const TextStyle(fontSize: 15, fontWeight: FontWeight.w500, color: Colors.white),
                                          ),
                                        ),
                                      ],
                                    ),
                                  ),
                                  const SizedBox(
                                    height: 10,
                                  ),
                                  Container(
                                    padding: EdgeInsets.all(5),
                                    decoration: BoxDecoration(
                                      borderRadius: BorderRadius.circular(2),
                                      border: Border.all(color: Colors.grey, width: 2),
                                    ),
                                    child: Column(
                                      crossAxisAlignment: CrossAxisAlignment.start,
                                      children: [
                                        Text(
                                          'Synopsis',
                                          style: const TextStyle(fontSize: 16, fontWeight: FontWeight.w600, color: Colors.white),
                                        ),
                                        Padding(
                                          padding: const EdgeInsets.all(8.0),
                                          child: Text(
                                            '(${snapshot.data!.data!.synopsis.toString()})',
                                            style: TextStyle(fontSize: 15, fontWeight: FontWeight.w400, color: Colors.white),
                                          ),
                                        ),
                                      ],
                                    ),
                                  ),
                                  const SizedBox(
                                    height: 10,
                                  ),
                                  (snapshot.data!.data!.background != null)
                                      ? Container(
                                          padding: EdgeInsets.all(5),
                                          decoration: BoxDecoration(
                                            borderRadius: BorderRadius.circular(2),
                                            border: Border.all(color: Colors.grey, width: 2),
                                          ),
                                          child: Column(
                                            crossAxisAlignment: CrossAxisAlignment.start,
                                            children: [
                                              const Text(
                                                'Background',
                                                style: TextStyle(fontSize: 16, fontWeight: FontWeight.w600, color: Colors.white),
                                              ),
                                              Padding(
                                                padding: const EdgeInsets.all(8.0),
                                                child: Text(
                                                  '${snapshot.data?.data?.background ?? ""}',
                                                  style: const TextStyle(fontSize: 15, fontWeight: FontWeight.w400, color: Colors.white),
                                                ),
                                              ),
                                            ],
                                          ),
                                        )
                                      : Container(
                                          color: Colors.black,
                                        ),
                                ],
                              ),
                            ),
                          ),
                        );
                      } else {
                        return Container(
                          height: MediaQuery.of(context).size.height / 2,
                          alignment: Alignment.center,
                          color: Colors.black,
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.center,
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: [
                              Text(
                                "Please Wait",
                                textAlign: TextAlign.center,
                                style: TextStyle(
                                  fontWeight: FontWeight.bold,
                                  color: Colors.white,
                                  fontSize: 15,
                                ),
                              ),
                              Container(
                                margin: EdgeInsets.all(10),
                                child: const CircularProgressIndicator(),
                              ),
                            ],
                          ),
                        );
                      }
                    }),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
