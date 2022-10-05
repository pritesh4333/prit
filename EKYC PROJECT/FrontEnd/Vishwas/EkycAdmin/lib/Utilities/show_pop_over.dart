import 'package:flutter/material.dart';
import 'package:popover/popover.dart';

class ShowPopOverDialog extends StatelessWidget {
  final Widget widgetList;
  final Widget view;
  final double? width;
  final double? height;
  final Color barrierColor;
  final Color backgroundColor;
  const ShowPopOverDialog({
    Key? key,
    required this.widgetList,
    required this.view,
    this.width,
    this.height,
    this.barrierColor = const Color(0x80000000),
    this.backgroundColor = const Color(0x8FFFFFFFF),
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        showPopover(
          context: context,
          transitionDuration: const Duration(milliseconds: 150),
          bodyBuilder: (context) => widgetList,
          direction: PopoverDirection.bottom,
          width: (width == null) ? 130 : width,
          height: (height == null) ? 130 : height,
          arrowHeight: 15,
          arrowWidth: 30,
          barrierColor: barrierColor,
          backgroundColor: backgroundColor,
          shadow: [
            const BoxShadow(
              color: Colors.grey,
              offset: Offset(0.0, 1.0),
              blurRadius: 4.0,
            ),
          ],
        );
      },
      child: view,
    );
  }
}
