package com.bocai.tiantianquan.tx;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by yuanfei on 2018/1/9.
 */

public class DelayMeterView extends View {

    Paint dottedPoint;//最外层画笔
    float time;//动画时间

    float dottedRadius;//外围圆的半径

    public float getTime() {
        return time;
    }

    public void setDottedRadius(float dottedRadius) {
        this.dottedRadius = dottedRadius;
    }

    public void setTime(float time) {
        this.time = time;
        invalidate();
    }

    ObjectAnimator objectAnimator;//动画

    public DelayMeterView(Context context) {
        this(context, null);
    }

    public DelayMeterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DelayMeterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        dottedPoint = new Paint(Paint.ANTI_ALIAS_FLAG);

        objectAnimator = ObjectAnimator.ofFloat(this, "time", 0, 60);
        objectAnimator.setDuration(1000 * 5);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("onDraw", "onDraw333");
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvasDotted(canvas);
    }


    public void canvasDotted(Canvas canvas) {
        PathEffect pathEffect = new DashPathEffect(new float[]{10, 5}, 10);//画虚线
        dottedPoint.setPathEffect(pathEffect);
        dottedPoint.setColor(Color.parseColor("#C4E7D5"));
        dottedPoint.setStyle(Paint.Style.STROKE);
        dottedPoint.setStrokeWidth(10);
        RectF rectF = new RectF(-dottedRadius, -dottedRadius, dottedRadius, dottedRadius);

        canvas.drawArc(rectF, 135, 270 * (time / 60), false, dottedPoint);
    }

    public void setAnimaStart() {
        Log.e("", "");
        objectAnimator.start();
    }

    public void stopAniam() {
        objectAnimator.end();
    }


}
