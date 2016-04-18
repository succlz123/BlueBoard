package org.succlz123.blueboard.model.utils.waveview;

import org.succlz123.blueboard.model.utils.common.OkUtils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by succlz123 on 15/12/30.
 */
public class WaveView extends View {
    private float mWaveWidth;
    private float mWaveHeight;

    private float mFrontMoveLength;
    private float mBackMoveLength;

    // 波宽系数,越小波浪越抖,视图范围内出现的波浪越多 (0.1 - 10.0)
    private float mWaveWidthRatio = 0.8f;
    // 波高系数,越大波浪越抖 (0.1 - 1.0)
    private float mWaveHeightRatio = 0.1f;

    // 波浪基准线位置,y轴位置
    private float mWavePlaceY;

    // 前面波浪y位置系数,越大越靠下 (0.1 - 1.0)
    private float mWaveFrontPlaceYRatio = 0.5f;
    // 后面波浪y位置系数,越大越靠下 (0.1 - 1.0)
    private float mWaveBackPlaceYRatio = 0.5f;

    // 后面的波浪,x轴位置系数,越大越靠右 (0.1 - 1.0)
    private float mWaveBackPlaceXRatio = 0.5f;

    // 右移速度
    public static final float FRONT_SPEED = 1f;
    public static final float BACK_SPEED = 0.7f;

    private ArrayList<FloatPoint> mFrontPointsList;
    private ArrayList<FloatPoint> mBackPointsList;

    private Paint mFrontPaint;
    private Paint mBackPaint;
    private Paint mOutBorderPaint;
    private Paint mWhitePaint;

    private Path mFrontPath;
    private Path mBackPath;
    private Path mOutBorderPath;

    private BitmapShader mBitmapShader;

    private boolean isMeasured = false;
    protected CompositeSubscription mCompositeSubscription;

    public WaveView(Context context) {
        this(context, null);
        init();
    }

    public WaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WaveView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mFrontPointsList = new ArrayList<>();
        mBackPointsList = new ArrayList<>();

        mFrontPaint = new Paint();
        mFrontPaint.setAntiAlias(true);
        mFrontPaint.setStyle(Paint.Style.FILL);
        mFrontPaint.setColor(Color.parseColor("#cccc669b"));

        mBackPaint = new Paint();
        mBackPaint.setAntiAlias(true);
        mBackPaint.setStyle(Paint.Style.FILL);
        mBackPaint.setColor(Color.parseColor("#4dcc669b"));

        mOutBorderPaint = new Paint();
        mOutBorderPaint.setAntiAlias(true);
        mOutBorderPaint.setStrokeWidth(OkUtils.dp2px(getContext(), 1));
        mOutBorderPaint.setStyle(Paint.Style.STROKE);
        mOutBorderPaint.setColor(Color.parseColor("#cccc669b"));

        mWhitePaint = new Paint();
        mWhitePaint.setAntiAlias(true);
        mWhitePaint.setStyle(Paint.Style.FILL);
        mWhitePaint.setColor(Color.parseColor("#ffffff"));

//        mOutBorderPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));// 选择交集去上层图片

        mFrontPath = new Path();
        mBackPath = new Path();
        mOutBorderPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!isMeasured) {
            isMeasured = true;
            int viewHeight = getMeasuredHeight();
            int viewWidth = getMeasuredWidth();

//            int mCanvasSize = 0;
//            if (viewHeight < viewWidth) {
//                mCanvasSize = viewHeight;
//            }

            mWavePlaceY = viewWidth * mWaveFrontPlaceYRatio;
            // 波宽
            mWaveWidth = viewWidth * mWaveWidthRatio;
            // 波高 (振幅 x 2 = 波高)
            mWaveHeight = viewWidth * mWaveHeightRatio;
            // 需要的波形个数
            int n = Math.round(viewWidth / mWaveWidth) + 1;
            // n个波形需要4n+1个坐标点，因为需要做横向运动,左边需要预留一个波浪
            // 所以需要 4 * (n + 1) + 1 = 4 * n + 5 个点
            for (int i = 0; i < (4 * n + 6); i++) {
                float frontX = i * mWaveWidth / 4 - mWaveWidth;
                float y = 0;
                switch (i % 4) {
                    case 0:
                        y = mWavePlaceY;
                        break;
                    case 1:
                        y = mWavePlaceY - mWaveHeight;
                        break;
                    case 2:
                        y = mWavePlaceY;
                        break;
                    case 3:
                        y = mWavePlaceY + mWaveHeight;
                        break;
                }
                mFrontPointsList.add(new FloatPoint(frontX, y));

                // 因为左移后,后波浪在左边需要隐藏2个
                float backX = i * mWaveWidth / 4 - 2 * mWaveWidth;

                mBackPointsList.add(new FloatPoint(backX + mWaveWidth * mWaveBackPlaceXRatio,
                        y - mWaveHeight * mWaveBackPlaceYRatio));
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mFrontPath.reset();
        mFrontPath.moveTo(mFrontPointsList.get(0).x, mFrontPointsList.get(0).y);

        mBackPath.reset();
        mBackPath.moveTo(mBackPointsList.get(0).x, mBackPointsList.get(0).y);

        // 画笔已经实现移动到初始位置
        int size = mFrontPointsList.size() - 2;
        // 在连续绘制时,起始点需要跳过,只需要控制点和结束点
        for (int i = 0; i < size; i = i + 2) {
            mFrontPath.quadTo(mFrontPointsList.get(i + 1).x, mFrontPointsList.get(i + 1).y,
                    mFrontPointsList.get(i + 2).x, mFrontPointsList.get(i + 2).y);

            mBackPath.quadTo(mBackPointsList.get(i + 1).x, mBackPointsList.get(i + 1).y,
                    mBackPointsList.get(i + 2).x, mBackPointsList.get(i + 2).y);
        }

        mFrontPath.lineTo(getWidth(), getHeight());
        mFrontPath.lineTo(0, getHeight());
        mFrontPath.close();

        mBackPath.lineTo(getWidth() + mWaveWidth, getHeight());
        mBackPath.lineTo(0, getHeight());
        mBackPath.close();

        canvas.clipPath(mOutBorderPath, Region.Op.INTERSECT);

        // 白色
        canvas.drawPath(mOutBorderPath, mWhitePaint);
        // 前波浪
        canvas.drawPath(mBackPath, mBackPaint);
        // 后波浪
        canvas.drawPath(mFrontPath, mFrontPaint);
        // 外边框
        canvas.drawPath(mOutBorderPath, mOutBorderPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mOutBorderPath.addCircle(getWidth() / 2, getHeight() - getWidth() / 2, getWidth() / 2, Path.Direction.CCW);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        onStart();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mCompositeSubscription.unsubscribe();
    }

    private void resetFrontPoints() {
        for (int i = 0; i < mFrontPointsList.size(); i++) {
            float frontX = (i * mWaveWidth / 4 - mWaveWidth);
            mFrontPointsList.get(i).x = frontX;
        }
    }

    private void resetBackPoints() {
        for (int i = 0; i < mBackPointsList.size(); i++) {
            float backX = i * mWaveWidth / 4 - 2 * mWaveWidth;
            mBackPointsList.get(i).x = backX + mWaveWidth * mWaveBackPlaceXRatio;
        }
    }

    private void onStart() {
        Subscription subscription = Observable.interval(20, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .filter(new Func1<Long, Boolean>() {
                    @Override
                    public Boolean call(Long aLong) {
                        mFrontMoveLength += FRONT_SPEED;
                        mBackMoveLength += BACK_SPEED;

                        for (int i = 0, size = mFrontPointsList.size(); i < size; i++) {
                            mFrontPointsList.get(i).x += FRONT_SPEED;
                            mBackPointsList.get(i).x += BACK_SPEED;
                        }

                        if (mFrontMoveLength >= mWaveWidth) {
                            // 波浪右移超过一个完整波浪后复位
                            mFrontMoveLength = 0;
                            resetFrontPoints();
                        }
                        if (mBackMoveLength >= mWaveWidth) {
                            // 波浪右移超过一个完整波浪后复位
                            mBackMoveLength = 0;
                            resetBackPoints();
                        }
                        return true;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        invalidate();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.toString();
                    }
                });
        mCompositeSubscription = new CompositeSubscription();
        mCompositeSubscription.add(subscription);
    }
}