library loading;

import 'package:flutter/material.dart';
import 'package:ekyc_admin/Utilities/greek_animation/decorate/greek_decorate.dart';
import 'package:ekyc_admin/Utilities/greek_animation/greek_ball_clip_rotate_pukse.dart';

///34 different types animation enums.
enum Indicator {
  greekBallClipRotatePulse,
}

/// Entrance of the loading.
class GreekLoadingIndicator extends StatelessWidget {
  /// The color you draw on the greek_shape.
  final List<Color>? colors;
  final Color? backgroundColor;

  /// The stroke width of line.
  final double? strokeWidth;

  /// Applicable to which has cut edge of the greek_shape
  final Color? pathBackgroundColor;

  const GreekLoadingIndicator({
    Key? key,
    this.colors,
    this.backgroundColor,
    this.strokeWidth,
    this.pathBackgroundColor,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    List<Color> safeColors = colors == null || colors!.isEmpty
        ? [Theme.of(context).primaryColor]
        : colors!;
    return GreekDecorateContext(
      decorateData: GreekDecorateData(
        colors: safeColors,
        strokeWidth: strokeWidth,
        pathBackgroundColor: pathBackgroundColor,
      ),
      child: AspectRatio(
        aspectRatio: 2,
        child: Container(
          color: backgroundColor,
          child: const GreekBallClipRotatePulse(),
        ),
      ),
    );
  }
}
