package com.example.piechart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

public class CircleView extends View {
    private Paint bluePaint;
    private RectF oval;
    private float centerX, centerY, radius;

    public CircleView(Context context) {
        super(context);
        init();
    }

    private void init() {
        bluePaint = new Paint();
        bluePaint.setColor(Color.BLUE);
        bluePaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        centerX = w / 2f;
        centerY = h / 2f;
        radius = Math.min(w, h) / 3f;
        oval = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Vẽ nút màu xanh chiếm 1/4 vòng tròn (90 độ)
        canvas.drawArc(oval, 270, 90, true, bluePaint);
    }
}