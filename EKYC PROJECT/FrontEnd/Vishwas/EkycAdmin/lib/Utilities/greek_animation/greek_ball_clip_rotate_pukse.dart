// ignore_for_file: library_private_types_in_public_api

import 'dart:math';
import 'package:flutter/material.dart';
import 'package:ekyc_admin/Utilities/greek_animation/greek_shape/greek_indicator_painter.dart';

/// BallClipRotatePulse.
class GreekBallClipRotatePulse extends StatefulWidget {
  const GreekBallClipRotatePulse({Key? key}) : super(key: key);

  @override
  _GreekBallClipRotatePulseState createState() =>
      _GreekBallClipRotatePulseState();
}

class _GreekBallClipRotatePulseState extends State<GreekBallClipRotatePulse>
    with SingleTickerProviderStateMixin {
  late AnimationController _animationController;
  late Animation<double> _outCircleScale;
  late Animation<double> _outCircleRotate;
  late Animation<double> _innerCircle;

  @override
  void initState() {
    super.initState();
    const cubic = Cubic(0.09, 0.57, 0.49, 0.9);
    _animationController =
        AnimationController(vsync: this, duration: const Duration(seconds: 1));

    _outCircleScale = TweenSequence([
      TweenSequenceItem(tween: Tween(begin: 1.0, end: 0.6), weight: 1),
      TweenSequenceItem(tween: Tween(begin: 0.6, end: 1.0), weight: 1),
    ]).animate(CurvedAnimation(parent: _animationController, curve: cubic));
    _outCircleRotate = TweenSequence([
      TweenSequenceItem(tween: Tween(begin: 0.0, end: pi), weight: 1),
      TweenSequenceItem(tween: Tween(begin: pi, end: 2 * pi), weight: 1),
    ]).animate(CurvedAnimation(parent: _animationController, curve: cubic));
    _innerCircle = TweenSequence([
      TweenSequenceItem(tween: Tween(begin: 1.0, end: 0.3), weight: 30),
      TweenSequenceItem(tween: Tween(begin: 0.3, end: 1.0), weight: 70),
    ]).animate(CurvedAnimation(parent: _animationController, curve: cubic));
    _animationController.repeat();
  }

  @override
  void dispose() {
    _animationController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return AnimatedBuilder(
      animation: _animationController,
      builder: (_, child) => Stack(
        alignment: Alignment.center,
        fit: StackFit.expand,
        children: <Widget>[
          Transform(
            alignment: Alignment.center,
            transform: Matrix4.identity()
              ..scale(_outCircleScale.value)
              ..rotateZ(_outCircleRotate.value),
            child: const GreekIndicatorShapeWidget(
              shape: GreekShape.ringTwoHalfVertical,
              index: 0,
            ),
          ),
          Transform.scale(
            scale: _innerCircle.value * 0.3,
            child: Container(
              color: Colors.white,
              height: 90,
              child: Image.asset(
                "",
                fit: BoxFit.fitWidth,
              ),
            ),
          ),
        ],
      ),
    );
  }
}