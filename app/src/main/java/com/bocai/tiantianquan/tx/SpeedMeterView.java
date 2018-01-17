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
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * Created by yuanfei on 2017/12/26.
 */

public class SpeedMeterView extends View {

    Paint unitPaint;//单位的画笔

    Paint pointerPaint;//指针画笔

    Paint textPaint;//实际网速的画笔


    String textUnit = "KB/s";


    int value = 0;//默认网速

    Rect textRect = new Rect();

    float preAngle = 0;//前一个指针偏转的角度

    float nextAngle = 0;//前一个指针偏转的角度

    float smallRadius = 120; //小圆半径

    ObjectAnimator objectAnimator;

    float time;

    float angle = 0;//偏转角度


    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
        angle = preAngle + (nextAngle - preAngle) * time / 100;
        invalidate();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if (!objectAnimator.isRunning()) {
            this.value = value;
            setAngle();
            objectAnimator.start();
        }
    }


    /**
     * 计算偏转角度  角度按照50 45 40 35 30 25 20 分配
     */
    public void setAngle() {
        if (value < 64) {
            nextAngle = value * 50 / 64;
        } else if (value < 128) {
            nextAngle = 50 + (value - 64) * 45 / 64;
        } else if (value < 256) {
            nextAngle = 50 + 45 + (value - 128) * 40 / 128;
        } else if (value < 512) {
            nextAngle = 50 + 45 + 40 + (value - 256) * 35 / 256;
        } else if (value < 1024) {
            nextAngle = 50 + 45 + 40 + 35 + (value - 512) * 30 / 512;
        } else if (value < 5120) {
            nextAngle = 50 + 45 + 40 + 35 + 30 + value * 25 / 4096;
        } else {
            nextAngle = 50 + 45 + 40 + 35 + 30 + 25 + value * 20 / 4096;
        }
    }

    public SpeedMeterView(Context context) {
        this(context, null);
    }

    public SpeedMeterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpeedMeterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        pointerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        unitPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        pointerPaint.setStrokeWidth(6);

        objectAnimator = ObjectAnimator.ofFloat(this, "time", 0, 100);
        objectAnimator.setDuration(300);
        objectAnimator.setInterpolator(new LinearInterpolator());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("onDraw", "onDraw222");
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvasNum(canvas);
        canvasUnit(canvas);
        canvasPointer(canvas);
    }

    /**
     * 绘制网速数字
     *
     * @param canvas
     */
    public void canvasNum(Canvas canvas) {
        textPaint.setColor(Color.parseColor("#000000"));
        textPaint.setFakeBoldText(true);
        String num = value + "";

        textPaint.setTextSize(50);
        textPaint.getTextBounds(num, 0, num.length(), textRect);
        canvas.drawText(num, -textRect.width() / 2, textRect.height() / 2, textPaint);
    }

    /**
     * 绘制单位
     *
     * @param canvas
     */
    public void canvasUnit(Canvas canvas) {

        unitPaint.setColor(Color.parseColor("#8f8f8f"));

        Rect rect = new Rect();
        unitPaint.setTextSize(26);
        unitPaint.getTextBounds(textUnit, 0, textUnit.length(), rect);
        canvas.drawText(textUnit, -rect.width() / 2, rect.height() / 2 + textRect.height() / 2 + 20, unitPaint);
    }

    /**
     * 绘制指针
     *
     * @param canvas
     */

    public void canvasPointer(Canvas canvas) {

        float startX = (float) (smallRadius * Math.cos((135 + angle) * Math.PI / 180));
        float startY = (float) (smallRadius * Math.sin((135 + angle) * Math.PI / 180));

        float startX1 = (float) (220 * Math.cos((135 + angle) * Math.PI / 180));
        float startY1 = (float) (220 * Math.sin((135 + angle) * Math.PI / 180));


        Shader shader1 = new LinearGradient(startX, startY, startX1, startY1, Color.parseColor("#eee9eb"),
                Color.parseColor("#D69087"), Shader.TileMode.CLAMP);
        pointerPaint.setShader(shader1);
        pointerPaint.setStrokeCap(Paint.Cap.ROUND);

        canvas.drawLine(startX, startY, startX1, startY1, pointerPaint);
    }


}
