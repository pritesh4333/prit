import 'dart:math';

import 'package:flutter/material.dart';
import 'package:ekyc_admin/Utilities/greek_animation/decorate/greek_decorate.dart';

const double _kMinIndicatorSize = 36.0;

/// Basic greek_shape.
enum GreekShape {
  circle,
  ringThirdFour,
  rectangle,
  ringTwoHalfVertical,
  ring,
  line,
  triangle,
  arc,
  circleSemi,
}

/// Wrapper class for basic greek_shape.
class GreekIndicatorShapeWidget extends StatelessWidget {
  final GreekShape shape;
  final double? data;

  /// The index of greek_shape in the widget.
  final int index;

  const GreekIndicatorShapeWidget({
    Key? key,
    required this.shape,
    this.data,
    this.index = 0,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final GreekDecorateData decorateData =
        GreekDecorateContext.of(context)!.decorateData;
    final color = decorateData.colors[index % decorateData.colors.length];

    return Container(
      constraints: const BoxConstraints(
        minWidth: _kMinIndicatorSize,
        minHeight: _kMinIndicatorSize,
      ),
      child: CustomPaint(
        painter: _ShapePainter(
          color,
          shape,
          data,
          decorateData.strokeWidth,
          pathColor: decorateData.pathBackgroundColor,
        ),
      ),
    );
  }
}

class _ShapePainter extends CustomPainter {
  final Color color;
  final GreekShape shape;
  final Paint _paint;
  final double? data;
  final double strokeWidth;
  final Color? pathColor;

  _ShapePainter(
    this.color,
    this.shape,
    this.data,
    this.strokeWidth, {
    this.pathColor,
  })  : _paint = Paint()..isAntiAlias = true,
        super();

  @override
  void paint(Canvas canvas, Size size) {
    switch (shape) {
      case GreekShape.circle:
        _paint
          ..color = color
          ..style = PaintingStyle.fill;
        canvas.drawCircle(
          Offset(size.width / 2, size.height / 2),
          size.shortestSide / 2,
          _paint,
        );
        break;
      case GreekShape.ringThirdFour:
        if (pathColor != null) {
          _paint
            ..color = pathColor!
            ..strokeWidth = strokeWidth
            ..style = PaintingStyle.stroke;
          canvas.drawCircle(
            Offset(size.width / 2, size.height / 2),
            size.shortestSide / 2,
            _paint,
          );
        }
        _paint
          ..color = color
          ..style = PaintingStyle.stroke
          ..strokeWidth = strokeWidth;
        canvas.drawArc(
          Rect.fromCircle(
            center: Offset(size.width / 2, size.height / 2),
            radius: size.shortestSide / 2,
          ),
          -3 * pi / 4,
          3 * pi / 2,
          false,
          _paint,
        );
        break;
      case GreekShape.rectangle:
        _paint
          ..color = color
          ..style = PaintingStyle.fill;
        canvas.drawRect(Offset.zero & size, _paint);
        break;
      case GreekShape.ringTwoHalfVertical:
        _paint
          ..color = color
          ..strokeWidth = strokeWidth
          ..style = PaintingStyle.stroke;
        final rect = Rect.fromLTWH(
            size.width / 4, size.height / 4, size.width / 2, size.height / 2);
        canvas.drawArc(rect, -3 * pi / 4, pi / 2, false, _paint);
        canvas.drawArc(rect, 3 * pi / 4, -pi / 2, false, _paint);
        break;
      case GreekShape.ring:
        _paint
          ..color = color
          ..strokeWidth = strokeWidth
          ..style = PaintingStyle.stroke;
        canvas.drawCircle(Offset(size.width / 2, size.height / 2),
            size.shortestSide / 2, _paint);
        break;
      case GreekShape.line:
        _paint
          ..color = color
          ..style = PaintingStyle.fill;
        canvas.drawRRect(
            RRect.fromRectAndRadius(
                Rect.fromLTWH(0, 0, size.width, size.height),
                Radius.circular(size.shortestSide / 2)),
            _paint);
        break;
      case GreekShape.triangle:
        final offsetY = size.height / 4;
        _paint
          ..color = color
          ..style = PaintingStyle.fill;
        Path path = Path()
          ..moveTo(0, size.height - offsetY)
          ..lineTo(size.width / 2, size.height / 2 - offsetY)
          ..lineTo(size.width, size.height - offsetY)
          ..close();
        canvas.drawPath(path, _paint);
        break;
      case GreekShape.arc:
        assert(data != null);
        _paint
          ..color = color
          ..style = PaintingStyle.fill;
        canvas.drawArc(
            Offset.zero & size, data!, pi * 2 - 2 * data!, true, _paint);
        break;
      case GreekShape.circleSemi:
        _paint
          ..color = color
          ..style = PaintingStyle.fill;
        canvas.drawArc(Offset.zero & size, -pi * 6, -2 * pi / 3, false, _paint);
        break;
    }
  }

  @override
  bool shouldRepaint(_ShapePainter oldDelegate) =>
      shape != oldDelegate.shape ||
      color != oldDelegate.color ||
      data != oldDelegate.data ||
      strokeWidth != oldDelegate.strokeWidth ||
      pathColor != oldDelegate.pathColor;
}
