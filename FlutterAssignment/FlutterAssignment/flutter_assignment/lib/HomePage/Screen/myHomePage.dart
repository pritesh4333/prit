import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_assignment/HomePage/Screen/AnimDetails.dart';
import 'package:flutter_assignment/HomePage/bloc/homePageBloc.dart';

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key, required this.title}) : super(key: key);

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  HomePageBloc _homePageBloc = HomePageBloc();
  final TextEditingController withdrawAmountController = TextEditingController();

  @override
  void initState() {
    // TODO: implement initState
    _homePageBloc.fetchAnime();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Scaffold(
        appBar: AppBar(
          title: Text(widget.title),
          backgroundColor: Colors.blue,
        ),
        body: Container(
          color: Colors.black,
          child: Column(
            mainAxisAlignment: MainAxisAlignment.start,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              const SizedBox(height: 15.0),
              Container(
                padding: const EdgeInsets.only(left: 10.0, right: 10.0),
                alignment: Alignment.centerLeft,
                child: Card(
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(8.0),
                  ),
                  elevation: 5.0,
                  child: TextField(
                    controller: withdrawAmountController,
                    inputFormatters: [FilteringTextInputFormatter.allow(RegExp(r'[a-zA-Z ]'))],
                    decoration: InputDecoration(
                      border: InputBorder.none,
                      hintText: "Search",
                      prefixIcon: InkWell(
                        onTap: () {
                          _homePageBloc.serachAnim(withdrawAmountController.text, context);
                        },
                        child: Icon(
                          Icons.search,
                          color: Colors.black.withOpacity(0.45),
                          size: 28.0,
                        ),
                      ),
                      suffixIcon: IconButton(
                        icon: const Icon(Icons.cancel_rounded),
                        highlightColor: Colors.transparent,
                        splashColor: Colors.transparent,
                        onPressed: clearButtonTapped,
                      ),
                    ),
                  ),
                ),
              ),
              const SizedBox(height: 15.0),
              StreamBuilder<bool>(
                  stream: _homePageBloc.mainStream.stream,
                  builder: (context, snapshot) {
                    if ((snapshot.hasData == true) && (_homePageBloc.futureStream.isNotEmpty)) {
                      return Expanded(
                        child: ListView.builder(
                          //Optional
                          shrinkWrap: true,
                          itemCount: _homePageBloc.futureStream.length,
                          itemBuilder: (context, index) {
                            if (snapshot.data != null) {
                              return TextButton(
                                onPressed: () {
                                  Navigator.push(
                                      context,
                                      MaterialPageRoute(
                                        builder: (context) => AnimDetails(arguments: _homePageBloc.futureStream[index].value.data![index].malId.toString()),
                                      ));
                                },
                                child: Column(
                                  mainAxisAlignment: MainAxisAlignment.end,
                                  children: [
                                    Row(
                                      children: [
                                        ClipOval(
                                          child: SizedBox.fromSize(
                                            size: Size.fromRadius(35), // Image radius
                                            child: Image.network(
                                              _homePageBloc.futureStream[index].value.data![index].images!.jpg!.largeImageUrl.toString(),
                                              fit: BoxFit.cover,
                                              height: 60,
                                              width: 60,
                                            ),
                                          ),
                                        ),
                                        Container(
                                          height: 60,
                                          width: 300,
                                          padding: EdgeInsets.only(left: 10),
                                          child: Column(
                                            crossAxisAlignment: CrossAxisAlignment.start,
                                            children: [
                                              Flexible(
                                                fit: FlexFit.loose,
                                                child: ListView(
                                                  physics: const AlwaysScrollableScrollPhysics(),
                                                  scrollDirection: Axis.horizontal,
                                                  children: [
                                                    Text(
                                                      _homePageBloc.futureStream[index].value.data![index].title.toString(),
                                                      style: TextStyle(color: Colors.blue, fontSize: 15),
                                                    ),
                                                  ],
                                                ),
                                              ),
                                              Text(
                                                _homePageBloc.futureStream[index].value.data![index].status.toString(),
                                                style: TextStyle(color: Colors.white, fontSize: 13),
                                              ),
                                              Text(
                                                _homePageBloc.futureStream[index].value.data![index].score.toString(),
                                                style: TextStyle(color: Colors.white, fontSize: 13),
                                              ),
                                              Text(
                                                _homePageBloc.futureStream[index].value.data![index].rating.toString(),
                                                style: TextStyle(color: Colors.white, fontSize: 13),
                                              ),
                                            ],
                                          ),
                                        ),
                                      ],
                                    ),
                                  ],
                                ),
                              );
                            } else {
                              return Container(
                                color: Colors.black,
                                child: SizedBox(
                                  height: MediaQuery.of(context).size.height / 2,
                                  child: const Center(
                                      child: Text(
                                    "No Data Available.",
                                    textAlign: TextAlign.center,
                                    style: TextStyle(
                                      fontWeight: FontWeight.bold,
                                      color: Colors.white,
                                      fontSize: 15,
                                    ),
                                  )),
                                ),
                              );
                            }
                          },
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
    );
  }

  clearButtonTapped() {
    FocusScope.of(context).unfocus();
    withdrawAmountController.clear();
    _homePageBloc.fetchAnime();
  }
}
