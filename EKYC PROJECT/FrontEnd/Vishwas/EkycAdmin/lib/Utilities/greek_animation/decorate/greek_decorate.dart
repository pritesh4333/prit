import 'package:flutter/material.dart';

const double _kDefaultStrokeWidth = 2;

/// Information about a piece of animation (e.g., color).
@immutable
class GreekDecorateData {
  final Color? backgroundColor;

  /// It will promise at least one value in the collection.
  final List<Color> colors;
  final double? _strokeWidth;

  /// Applicable to which has cut edge of the greek_shape
  final Color? pathBackgroundColor;

  const GreekDecorateData({
    required this.colors,
    this.backgroundColor,
    double? strokeWidth,
    this.pathBackgroundColor,
  })  : _strokeWidth = strokeWidth,
        assert(colors.length > 0);

  double get strokeWidth => _strokeWidth ?? _kDefaultStrokeWidth;

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is GreekDecorateData &&
          runtimeType == other.runtimeType &&
          backgroundColor == other.backgroundColor &&
          colors == other.colors &&
          strokeWidth == other.strokeWidth;

  @override
  int get hashCode =>
      backgroundColor.hashCode ^ colors.hashCode ^ strokeWidth.hashCode;

  @override
  String toString() {
    return 'DecorateData{backgroundColor: $backgroundColor, colors: $colors, strokeWidth: $strokeWidth}';
  }
}

/// Establishes a subtree in which decorate queries resolve to the given data.
class GreekDecorateContext extends InheritedWidget {
  final GreekDecorateData decorateData;

  const GreekDecorateContext({
    Key? key,
    required this.decorateData,
    required Widget child,
  }) : super(key: key, child: child);

  @override
  bool updateShouldNotify(GreekDecorateContext oldWidget) =>
      oldWidget.decorateData == decorateData;

  static GreekDecorateContext? of(BuildContext context) {
    return context.dependOnInheritedWidgetOfExactType();
  }
}
