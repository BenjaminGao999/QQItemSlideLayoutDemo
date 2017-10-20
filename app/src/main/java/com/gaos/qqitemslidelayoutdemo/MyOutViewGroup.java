package com.gaos.qqitemslidelayoutdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * Author:　Created by benjamin
 * DATE :  2017/10/18 18:51
 * versionCode:　v2.2
 */

public class MyOutViewGroup extends ViewGroup {
    private static final String TAG = "MyOutViewGroup";
    private float dRawY;
    private float thresholdY;
    private float dRawX;
    private float thresholdX;

    public MyOutViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                dRawY = ev.getRawY();
//                dRawX = ev.getRawX();
//                Log.d(TAG, "dispatchTouchEvent: out ");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float mRawY = ev.getRawY();
//                float mRawX = ev.getRawX();
//                float dy = mRawY - dRawY;
//                float dx = mRawX - dRawX;
//                if (Math.abs(dy) > 0) {
//                    if (Math.abs(dy) > thresholdY || Math.abs(dx) > thresholdX) {
//                        // 夹角判断
//                        double atan = Math.atan(Math.abs(dx) / Math.abs(dy));
////                    Log.d(TAG, "dispatchTouchEvent: atant = " + atan);
//                        double angle = (atan / (2 * Math.PI)) * 360;
//                        Log.d(TAG, "dispatchTouchEvent: angle = " + angle);
//
//                        if (angle >= 45) {//item horizontal slide
//
//                            return true;
//                        } else {// RecyclerView vertical scroll
//
//
//                        }
//                    }
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//            default:
//                break;
//        }
        return super.dispatchTouchEvent(ev);
//        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                dRawY = ev.getRawY();
//                dRawX = ev.getRawX();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float mRawY = ev.getRawY();
//                float mRawX = ev.getRawX();
//                float dy = mRawY - dRawY;
//                float dx = mRawX - dRawX;
//                if (Math.abs(dy) > thresholdY) {
//                    // 夹角判断
//                    double atan = Math.atan(Math.abs(dx) / Math.abs(dy));
//                    Log.d(TAG, "onInterceptTouchEvent: atan = " + atan);
//                }
//                Log.d(TAG, "onInterceptTouchEvent: ");
//
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//            default:
//                break;
//        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).layout(l, t, r, b);
            thresholdY = (b - t) / 10.0f;
            thresholdX = (r - l) / 10.0f;
        }
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                Log.d(TAG, "onTouchEvent: ACTION_DOWN ");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.d(TAG, "onTouchEvent: ACTION_MOVE");
//
//                if (Math.abs(rawX0) > 0) {
//
//                } else {
//                    rawX0 = event.getRawX();
//                }
//
//                break;
//            case MotionEvent.ACTION_UP:
//                Log.d(TAG, "onTouchEvent: ACTION_UP");
//                rawX1 = event.getRawX();
//
//
//                break;
//            default:
//                break;
//        }
//        return true;
//    }

}
