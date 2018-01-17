package com.bocai.tiantianquan.tx;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;

import com.bocai.tiantianquan.R;

/**
 * Created by yuanfei on 2017/12/28.
 */

public class SpeedMeterLayout extends ViewGroup {

    private Context mContext;

    private SpeedMeterView meterView;

    private DelayMeterView delayMeterView;

    float smallRadius = 120;
    float bigRadius = 200;

    String[] text = new String[]{"0k", "64k", "128k", "256k", "512k", "1M", "5M", "10M"};

    Paint text1Paint;//圆盘上文字的画笔
    Paint bigRoundPaint;//大圆的画笔
    Paint smallRoundPaint;//小圆画笔
    Paint titlePaint;//标题画笔
    String title = "Wi-Fi";
    float titleTextSize = 26;
    int titleTextColor = Color.parseColor("#8f8f8f");

    int bigCircleColor = Color.parseColor("#5ac173");

    float speedTextSize = 26;//网速字体的大小
    int speedTextColor = Color.parseColor("#8f8f8f");
    float m1Angle = 0;


    public SpeedMeterLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context,attrs);
    }

    public SpeedMeterLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context,attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            delayMeterView.layout(0, 0, r - l, b - t);
            meterView.layout(0, 0, r - l, b - t);
        }
    }
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SpeedMeterLayout, 0, 0);
        title = typedArray.getString(R.styleable.SpeedMeterLayout_titleText);
        if (TextUtils.isEmpty(title)){
            title = "Wi-Fi";
        }
        titleTextSize = typedArray.getDimensionPixelSize(R.styleable.SpeedMeterLayout_titleTextSize, 26);
        titleTextColor = typedArray.getColor(R.styleable.SpeedMeterLayout_titleTextColor, Color.parseColor("#8f8f8f"));
        speedTextSize = typedArray.getDimensionPixelSize(R.styleable.SpeedMeterLayout_speedTextSize, 26);
        speedTextColor = typedArray.getColor(R.styleable.SpeedMeterLayout_speedTextColor,Color.parseColor("#8f8f8f"));
        bigRadius = typedArray.getDimensionPixelSize(R.styleable.SpeedMeterLayout_bigCircleRadius, 200);
        smallRadius = typedArray.getDimensionPixelSize(R.styleable.SpeedMeterLayout_smallCircleRadius, 120);
        bigCircleColor = typedArray.getColor(R.styleable.SpeedMeterLayout_bigCircleColor, Color.parseColor("#E8E8E8"));
        typedArray.recycle();
        initSpeed(context);
    }

    public void initSpeed(Context context) {

        text1Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        text1Paint.setColor(Color.parseColor("#E8E8E8"));
        text1Paint.setTextSize(16);

        bigRoundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bigRoundPaint.setColor(bigCircleColor);
        bigRoundPaint.setStrokeWidth(4);
        bigRoundPaint.setStyle(Paint.Style.STROKE);

        smallRoundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        titlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        Shader shader = new RadialGradient(0, 0, 10, Color.parseColor("#ffffff"),
                Color.parseColor("#f8f8f8"), Shader.TileMode.REPEAT);
        smallRoundPaint.setShader(shader);
        smallRoundPaint.setStrokeWidth(10);
        smallRoundPaint.setStyle(Paint.Style.STROKE);

        this.mContext = context;


        delayMeterView = new DelayMeterView(context);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        delayMeterView.setLayoutParams(layoutParams);
        addView(delayMeterView);


        meterView = new SpeedMeterView(context);
        meterView.setLayoutParams(layoutParams);
        addView(meterView);

        delayMeterView.setDottedRadius(bigRadius+6);

        //设置ViewGroup可画
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("onDraw", "onDraw111");
        canvasBigRound(canvas);
        canvasSmallRound(canvas);
        canvasTitle(canvas);
    }


    //画大圆和文字  文字的间隔是按照 50 45 40 35 30 25 20 15
    public void canvasBigRound(Canvas canvas) {

        int angle = 0;
        for (int i = 0; i < 8; i++) {
            angle = (50 - (i - 1) * 5) + angle;

            if (i == 0) {
                angle = 0;
            }
            float x3 = (float) (bigRadius * Math.cos((135 + angle) * Math.PI / 180)) + getWidth() / 2;
            float y3 = (float) (bigRadius * Math.sin((135 + angle) * Math.PI / 180)) + getHeight() / 2;
            Rect rect = new Rect();
            if (i == 0) {
                canvas.drawText(text[i], x3 + 5, y3 - 5, text1Paint);
            } else if (i == 1) {
                canvas.drawText(text[i], x3 + 5, y3 - 5, text1Paint);
            } else if (i == 2) {
                text1Paint.getTextBounds(text[i], 0, text[i].length(), rect);
                canvas.drawText(text[i], x3 + 5, y3 + rect.height() + 5, text1Paint);
            } else if (i == 3) {
                text1Paint.getTextBounds(text[i], 0, text[i].length(), rect);
                canvas.drawText(text[i], x3 - rect.width() / 2, y3 + rect.height() + 7
                        , text1Paint);
            } else if (i == 4) {
                text1Paint.getTextBounds(text[i], 0, text[i].length(), rect);
                canvas.drawText(text[i], x3 - rect.width() - 5, y3 + rect.height() + 5
                        , text1Paint);
            } else if (i == 5) {
                m1Angle = angle;
                text1Paint.getTextBounds(text[i], 0, text[i].length(), rect);
                canvas.drawText(text[i], x3 - rect.width() - 5, y3 + rect.height() + 5
                        , text1Paint);
            } else if (i == 6) {
                text1Paint.getTextBounds(text[i], 0, text[i].length(), rect);
                canvas.drawText(text[i], x3 - rect.width() - 7, y3
                        , text1Paint);
            } else if (i == 7) {
                text1Paint.getTextBounds(text[i], 0, text[i].length(), rect);
                canvas.drawText(text[i], x3 - rect.width() - 7, y3
                        , text1Paint);
            }

        }


        bigRoundPaint.setColor(Color.parseColor("#E8E8E8"));
        RectF rectF = new RectF(-bigRadius + getWidth() / 2, -bigRadius + getHeight() / 2, bigRadius + getWidth() / 2, bigRadius + getHeight() / 2);

        canvas.drawArc(rectF, 135, m1Angle, false, bigRoundPaint);


        float x1 = (float) (bigRadius * Math.cos(Math.PI / 4)) + getWidth() / 2;
        float y1 = (float) (bigRadius * Math.sin(Math.PI / 4)) + getHeight() / 2;

        float x11 = (float) (230 * Math.cos(Math.PI / 4)) + getWidth() / 2;
        float y11 = (float) (230 * Math.sin(Math.PI / 4)) + getHeight() / 2;

        float x2 = -(float) (bigRadius * Math.cos(Math.PI / 4)) + getWidth() / 2;
        float y2 = (float) (bigRadius * Math.sin(Math.PI / 4)) + getHeight() / 2;

        float x21 = -(float) (230 * Math.cos(Math.PI / 4)) + getWidth() / 2;
        float y21 = (float) (230 * Math.sin(Math.PI / 4)) + getHeight() / 2;


        canvas.drawLine(x2, y2, x21, y21, bigRoundPaint);
        bigRoundPaint.setColor(Color.parseColor("#5ac173"));
        canvas.drawArc(rectF, 135 + m1Angle, 270 - m1Angle, false, bigRoundPaint);
        canvas.drawLine(x1, y1, x11, y11, bigRoundPaint);
    }

    public void canvasSmallRound(Canvas canvas) {

        canvas.drawCircle(getWidth() / 2, getHeight() / 2, smallRadius, smallRoundPaint);

    }

    /**
     * 绘制标题
     *
     * @param canvas
     */

    public void canvasTitle(Canvas canvas) {

        titlePaint.setColor(titleTextColor);
        Rect rect = new Rect();
        titlePaint.setTextSize(titleTextSize);
        titlePaint.getTextBounds(title, 0, title.length(), rect);
        canvas.drawText(title, -rect.width() / 2 + getWidth() / 2, rect.height() + 20 - smallRadius + getHeight() / 2, titlePaint);
    }


    /**
     * 开始测量网络延时
     */
    public void startMeasuringDelay() {
        delayMeterView.setAnimaStart();
    }

    /**
     * 停止测量网络延时
     */
    public void stopMeasuringDelay() {
        delayMeterView.stopAniam();
    }


    /**
     * 开始测量网速
     */
    public void startMeasuring() {
        meterView.setValue(1000);
    }

}
