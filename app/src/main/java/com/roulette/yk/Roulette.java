package com.roulette.yk;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yk on 2017/9/12.
 */

public class Roulette extends View {
    //上下文
    private Context context;
    //起始位置
    private final float StartLocation = 270;
    //画笔
    private Paint paint;
    //绘制的角度
    private float mRadios;
    //显示的文字
    private List<String> listText;
    //每个扇面的中心位置
    private List<Float> listRadios;
    //绘制的次数
    private int mCount;
    //每个扇面的颜色
    private List<Integer> listColor;//

    public Roulette(Context context) {
        super(context);
    }

    /**
     * 此构造方法在xml文件中调用，不在activity调用
     *
     * @param context 上下文
     * @param attrs   属性集
     */
    public Roulette(Context context, AttributeSet attrs) {
        super(context, attrs);
        //定义上下文
        this.context = context;
        //实例化画笔
        paint = new Paint();
        //开启抗锯齿
        paint.setAntiAlias(true);
        //初始化轮廓
        Path path = new Path();
        //实例化轮盘上的文字
        listText = new ArrayList<>();
        //实例化扇面的中心位置（箭头停止的位置）
        listRadios = new ArrayList<>();
        //实例化扇面的颜色
        listColor = new ArrayList<>();
        //ps:色值不能在此应用xml里面的数据，必须用十六进制的值添加
        //0xffffffff  ARGB格式（A：透明度，R：红，G，绿，B：蓝）
        listColor.add(0xffff0000);//红
        listColor.add(0xffff7f00);//橙
        listColor.add(0xffffff00);//黄
        listColor.add(0xff00ff00);//绿
        listColor.add(0xff00ffff);//清
        listColor.add(0xff00ff00);//蓝
        listColor.add(0xff8b00ff);//紫
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //判断文字是否为空
        if (listText != null && listText.size() > 0) {
            //实例化绘制文字的画笔
            Paint mTextPaint = new Paint();
            //设置文字画笔的颜色
            mTextPaint.setColor(Color.WHITE);
            //文字画笔开启抗锯齿
            mTextPaint.setAntiAlias(true);
            //文字画笔开启抗抖动
            mTextPaint.setDither(true);
            //level 2以上可以设置字符间距
            if (Build.VERSION.SDK_INT > 20) {
                mTextPaint.setLetterSpacing(0.1f);
            }
            //设置文本对齐方式
            mTextPaint.setTextAlign(Paint.Align.CENTER);
            //设置文字大小
            mTextPaint.setTextSize(80);
            //初始化打印页
            Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);
            //设置文字画笔的打印页
            mTextPaint.setTypeface(typeface);

            //开始绘制的角度
            float startRadios = 270 - mRadios / 2;
            //初始化画图的区域
            RectF rectF = new RectF(0, 0, canvas.getWidth(), canvas.getHeight());
            //开始绘制轮盘
            for (int i = 0; i < listText.size(); i++) {
                //获取当前扇面的颜色
                paint.setColor(listColor.get(i));
                //实例化轮廓
                Path path = new Path();
                //绘制一个扇形区域
                path.addArc(rectF, startRadios, mRadios);
                //获取文字高度
                Paint.FontMetrics metrics = mTextPaint.getFontMetrics();
                //文字上边缘
                float ascent = metrics.ascent;
                //文字下边缘
                float descent = metrics.descent;
                //计算文字高度
                float height = descent - ascent;
                //绘制扇形
                canvas.drawArc(rectF, startRadios, mRadios, true, paint);
                //绘制文字
                canvas.drawTextOnPath(listText.get(i), path, 0, height, mTextPaint);
                //改变绘制起始角度
                startRadios += mRadios;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取windowManager
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //获取屏幕宽度
        int width = manager.getDefaultDisplay().getWidth();
        //绘制区域的宽高
        setMeasuredDimension(width, width);
    }

    /**
     * 设置显示的文字
     *
     * @param text activity传入的奖项数组
     */
    public void setText(String[] text) {
        try {
            mCount = text.length;
            if (mCount < 1) {
                mCount = 1;
            }
            for (int i = 0; i < mCount; i++) {
                listText.add(text[i]);
            }
            //计算每个扇形所占的幅度
            mRadios = 360.0f / mCount;
            for (int i = 0; i < listText.size(); i++) {
                float start = 270 + mRadios * i;
                if (start < 360) {
                    listRadios.add(start);
                } else {
                    listRadios.add(start - 360);
                }
            }
            invalidate();
        } catch (Exception e) {
            Log.e("绘制问题", "传入的数组为空");
        }
    }

    /**
     * activity获取选项中心位置
     * @return
     */
    public List<Float> getRadioList(){
        return listRadios;
    }
}














