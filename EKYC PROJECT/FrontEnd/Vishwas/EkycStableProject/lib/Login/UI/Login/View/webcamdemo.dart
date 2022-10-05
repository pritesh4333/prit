import 'dart:async';
import 'dart:convert';
import 'dart:typed_data';

import 'package:e_kyc/Login/UI/IPVScreen/bloc/IpvScreenBlock.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'dart:ui' as ui;
import 'dart:html' as html;
import 'dart:js' as js;
import 'package:http/http.dart' as http;
import 'package:web_browser_detect/web_browser_detect.dart';

class webcamdemo extends StatefulWidget {
  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<webcamdemo> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Container(),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () async {
          await showDialog(
              context: context,
              builder: (BuildContext context) {
                return FractionallySizedBox(
                  heightFactor: 0.5,
                  widthFactor: 0.5,
                  child: WebCam(),
                );
              });
        },
        // tooltip: 'Increment',
        child: Icon(Icons.add),
      ),
    );
  }
}

class WebCam extends StatefulWidget {
  @override
  _WebCamState createState() => _WebCamState();
}

class _WebCamState extends State<WebCam> {
  static html.VideoElement _webcamVideoElement = html.VideoElement();
  html.MediaStream streamHandle;
  html.MediaRecorder _mediaRecorder;
  final browser = Browser();
  @override
  void initState() {
    super.initState();

    // Register a webcam
    // ignore: undefined_prefixed_name
    ui.platformViewRegistry.registerViewFactory('webcamVideoElement',
        (int viewId) {
      getMedia();
      return _webcamVideoElement;
    });
  }

  getMedia() {
    html.window.navigator.mediaDevices
        ?.getUserMedia({"video": true, "audio": true}).then(
      (streamHandle) {
        _webcamVideoElement
          ..srcObject = streamHandle
          ..autoplay = true;
        _webcamVideoElement.volume;
        print('${browser.browser} ${browser.version}');
        String browserType = browser.browser;
        String mimeType = '';
        if (browserType.toLowerCase() == "chrome") {
          mimeType = 'video/webm';
        } else if (browserType.toLowerCase() == 'safari') {
          mimeType = 'video/mp4';
        } else {
          mimeType = 'video/webm';
        }
        _mediaRecorder =
            new html.MediaRecorder(streamHandle, {mimeType: 'video/webm;codecs=h264'});

        _mediaRecorder.addEventListener(
          'dataavailable',
          (html.Event event) async {
            final chunks = <html.Blob>[];
            Completer<String> _completer = Completer<String>();
            final html.Blob blob = js.JsObject.fromBrowserObject(event)['data'];

            if (blob.size > 0) {
              chunks.add(blob);
            }

            print('chunks :: ${chunks}');
// send this project on mial
            if (_mediaRecorder.state == 'inactive') {
              final blob = html.Blob(chunks, 'video/mp4');
              _completer.complete(html.Url.createObjectUrlFromBlob(blob));

              String pathFile = await _completer.future;
              final imgBase64Str = await networkImageToBase64(pathFile);
              Uint8List bytes = base64.decode(imgBase64Str);
              // IpvScreenBlock ipcBloc = new IpvScreenBlock();
              // ipcBloc.saveIPVDocument(context, "large", bytes, "");
              print('url of video :: ${pathFile}');

              html.window.open(pathFile, '');
            }
          },
        );
      },
    ).catchError(
      (onError) {
        print(onError);
      },
    );
  }

  Future<String> networkImageToBase64(String imageUrl) async {
    Uri myUri = Uri.parse(imageUrl);
    http.Response response = await http.get(myUri);
    final bytes = response?.bodyBytes;
    return (bytes != null ? base64Encode(bytes) : null);
  }

  switchCameraOff() {
    bool val = _webcamVideoElement.srcObject?.active;
    if (_webcamVideoElement.srcObject != null && val) {
      var tracks = _webcamVideoElement.srcObject?.getTracks();

      //stopping tracks and setting srcObject to null to switch camera off
      _webcamVideoElement.srcObject = null;

      tracks?.forEach((track) {
        track.stop();
      });
    }
  }

  @override
  void dispose() {
    print("i am dispose webcamdemo");
    switchCameraOff();

    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
        children: [
          Container(
              child: HtmlElementView(
            key: UniqueKey(),
            viewType: 'webcamVideoElement',
          )),
          Container(
            child: Column(
              children: [
                RaisedButton(
                  child: Text('Play/Pause'),
                  onPressed: () async {
                    if (_webcamVideoElement.paused) {
                      _webcamVideoElement.play();
                    } else {
                      _webcamVideoElement.pause();
                    }
                  },
                ),
                RaisedButton(
                  child: Text('Switch off'),
                  onPressed: () {
                    switchCameraOff();
                  },
                ),
                RaisedButton(
                  child: Text('Switch on'),
                  onPressed: () {
                    if (_webcamVideoElement.srcObject == null) getMedia();
                  },
                ),
                RaisedButton(
                  child: Text('Start Recording'),
                  onPressed: () {
                    _mediaRecorder.start();
                  },
                ),
                RaisedButton(
                  child: Text('Stop Recording'),
                  onPressed: () {
                    _mediaRecorder.stop();
                  },
                )
              ],
            ),
          ),
        ],
      ),
    );
  }
}
