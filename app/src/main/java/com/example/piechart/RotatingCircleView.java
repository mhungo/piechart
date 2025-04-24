package com.example.piechart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

public class RotatingCircleView extends View {

    private Paint yellowPaint, bluePaint, centerPaint, textPaint;
    private RectF innerOval, outerOval;
    private float centerX, centerY, innerRadius, outerRadius;
    private float[] outerAngles = {0, 90, 180}; // Góc ban đầu của 3 nút màu vàng
    private float blueAngle = 10; // Góc cố định của nút màu xanh
    private boolean isRotating = false;
    private String[] labels = {"1", "2", "3"}; // Nhãn cho 3 nút

    public RotatingCircleView(Context context) {
        super(context);
        init();
    }

    public RotatingCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        yellowPaint = new Paint();
        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setStyle(Paint.Style.FILL);

        bluePaint = new Paint();
        bluePaint.setColor(Color.BLUE);
        bluePaint.setStyle(Paint.Style.FILL);

        centerPaint = new Paint();
        centerPaint.setColor(Color.RED);
        centerPaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(40);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2f;
        centerY = h / 2f;
        innerRadius = Math.min(w, h) / 6f;
        outerRadius = Math.min(w, h) / 3f;
        innerOval = new RectF(centerX - innerRadius, centerY - innerRadius, centerX + innerRadius, centerY + innerRadius);
        outerOval = new RectF(centerX - outerRadius, centerY - outerRadius, centerX + outerRadius, centerY + outerRadius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Vẽ vòng tròn trung tâm
        canvas.drawCircle(centerX, centerY, innerRadius / 2, centerPaint);

        // Vẽ tầng 1: Nút màu xanh
        canvas.drawArc(innerOval, blueAngle, 360, true, bluePaint);

        // Vẽ tầng 2: 3 nút màu vàng
        for (int i = 0; i < 3; i++) {
            canvas.drawArc(outerOval, outerAngles[i], 90, true, yellowPaint);
            float textAngle = (outerAngles[i] + 45) % 360;
            float textX = centerX + (outerRadius * 0.7f) * (float) Math.cos(Math.toRadians(textAngle));
            float textY = centerY + (outerRadius * 0.7f) * (float) Math.sin(Math.toRadians(textAngle));
            canvas.drawText(labels[i], textX, textY, textPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && !isRotating) {
            float x = event.getX();
            float y = event.getY();

            // Kiểm tra nhấn vào nút xanh
            float distance = (float) Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));
            if (distance <= innerRadius) {
                rotateYellowButtons();
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    private void rotateYellowButtons() {
        isRotating = true;

        // Xoay 3 nút vàng
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, centerX, centerY);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                isRotating = false;
                invalidate();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        startAnimation(rotateAnimation);
    }
}