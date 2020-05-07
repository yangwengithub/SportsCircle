package com.sz.lyw.sportscircleview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yw on 2020/5/7.
 */

public class SportsCircleView extends View {

    private float textSize = 60;
    private int textColor = Color.parseColor("#FFC125");
    private int circleColor = Color.parseColor("#FFC125");


    private float progress = 0;

    public SportsCircleView(Context context) {
        super(context);
    }

    public SportsCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SportsCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void start(float progressNum) {

        progressNum = progressNum > 100 ? 100 : progressNum;
        // 最后三个参数 0, progress + 20, progress，其中progress + 20是多绘制20%再回弹回来
        //如果不需要回弹效果可以删除：ObjectAnimator.ofFloat(sc_animation, "progress", 0, progress);
        ObjectAnimator progressAnimator
                = ObjectAnimator.ofFloat(this, "progress", 0, progressNum + 20, progressNum);
        progressAnimator.setDuration(1500);
        progressAnimator.start();
    }


    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        //这个地方一定要调用刷新方法
        invalidate();
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;

    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setCircleColor(int circleColor) {
        this.circleColor = circleColor;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //限制progress比例最大为100
        progress = progress > 100 ? 100 : progress;

        //画笔设置
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(circleColor);
        paint.setStrokeWidth(8);
        paint.setStyle(Paint.Style.STROKE);

        //绘制进度圆环
        RectF rectF = new RectF(50, 50, 350, 350);
        canvas.drawArc(rectF, -120, (float) (progress * 3.6), false, paint);
        //注意：下面这个方法也可以绘制圆环，但是在API level 21 才加入的，低版本的手机会报错，所以要小心了
        // canvas.drawArc(50, 50, 350, 350, -90, (float) (progress * 3.6), false, paint);


        //以下是绘制中心文本
        String textProgress = (int) progress + "%";
        //重新设置画笔
        paint.setTextSize(textSize);
        paint.setStrokeWidth(2);
        paint.setColor(textColor);
        paint.setStyle(Paint.Style.FILL);
        //计算出文本在圆环中心位置坐标
        float textWidth = paint.measureText(textProgress);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float dy = (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent;

        canvas.drawText(textProgress, 200 - textWidth / 2, 200 + dy, paint);
    }


}
