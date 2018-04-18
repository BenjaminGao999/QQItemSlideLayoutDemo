package com.gaos.qqitemslidelayoutdemo.optimization;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.gaos.qqitemslidelayoutdemo.interfaces.IViewObservable;

import java.lang.reflect.Field;

/**
 * Author:　Created by benjamin
 * DATE :  2018/4/18 17:59
 * versionCode:　v2.2
 */

public class SlideRecyclerView extends RecyclerView {
    private static final String TAG = "SlideRecyclerView";
    // 需要重置当前滑动参数？
    private boolean isResetNeeded;

    public SlideRecyclerView(Context context) {
        this(context, null);
    }

    public SlideRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public SlideRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        // A。 mOnTouchListener.onTouch 在 onTouchEvent之前执行的
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        // TODO: 2018/4/18  手势判断
                        Log.i(TAG, "onTouch:ACTION_MOVE ");

//                return true;
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.i(TAG, "onTouch: ACTION_UP");

                        break;
                    default:
                        break;
                }


                return false;
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // TODO: 2018/4/18  确定手指在哪个item上
                IViewObservable.newInstance().setiViewSubscribe(new IViewObservable.IViewSubscribe() {
                    @Override
                    public void onObserve(View view) {
                        int childAdapterPosition = getChildAdapterPosition(view);
                        Log.i(TAG, "onObserve: childAdapterPosition" + childAdapterPosition);
                    }
                });

                break;

            default:
                break;
        }

        return super.onInterceptTouchEvent(e);
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return super.onTouchEvent(e);
    }


    private void resetRecyclerViewFieldmLastTouchY(MotionEvent event) {
        //通过反射拿到 mLastTouchY
        // 把mLastTouchY 重置

        if (isResetNeeded) {
            try {
                Field declaredField = RecyclerView.class.getDeclaredField("mLastTouchY");
                declaredField.setAccessible(true);
                int value = (int) (event.getY() + 0.5f);
                declaredField.set(this, value);
                Log.i(TAG, "onDraggingLis: " + value);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            isResetNeeded = false;
        }
    }

}
