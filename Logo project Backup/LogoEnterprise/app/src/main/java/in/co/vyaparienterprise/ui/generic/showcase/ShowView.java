package in.co.vyaparienterprise.ui.generic.showcase;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import in.co.vyaparienterprise.ui.generic.showcase.shapes.Circle;
import in.co.vyaparienterprise.ui.generic.showcase.shapes.RoundRect;
import in.co.vyaparienterprise.ui.generic.showcase.shapes.Shape;

import java.util.ArrayList;
import java.util.List;

class ShowView extends View {

    static final int DEFAULT_ALPHA_COLOR = 200;
    int backgroundOverlayColor = Color.argb(DEFAULT_ALPHA_COLOR, 0, 0, 0);
    List<Shape> shapes;

    public ShowView(Context context) {
        super(context);
        initialize();
    }

    public ShowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public ShowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    public void addCircle(Circle circle) {
        this.shapes.add(circle);
    }

    public void addRoundRect(RoundRect roundRect) {
        this.shapes.add(roundRect);
    }

    public int getBackgroundOverlayColor() {
        return backgroundOverlayColor;
    }

    public void setBackgroundOverlayColor(int backgroundOverlayColor) {
        this.backgroundOverlayColor = backgroundOverlayColor;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(backgroundOverlayColor);
        for (Shape shape : shapes) {
            shape.drawOn(canvas);
        }

    }

    private void initialize() {
        shapes = new ArrayList<>();

        setDrawingCacheEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

    }
}
