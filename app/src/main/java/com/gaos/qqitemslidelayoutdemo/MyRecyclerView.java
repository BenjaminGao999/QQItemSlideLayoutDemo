package com.gaos.qqitemslidelayoutdemo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


/**
 * Author:　Created by benjamin
 * DATE :  2017/10/18 18:35
 * versionCode:　v2.2
 */

public class MyRecyclerView extends RecyclerView {
    private static final String TAG = "MyRecyclerView";
    private float dRawY;
    private float dRawX;
    private float thresholdX;
    private float thresholdY;
    private boolean itemSlideHasJudged;
    private float downRawX1;
    private float disX;
    private View childCapture;
    private View contentView;
    private View deleteView;
    private int deleteViewMeasuredWidth;
    private int contentViewMeasuredWidth;
    private boolean hasOpenedItem;

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        thresholdX = w / 10.0f;
        thresholdY = h / 10.0f;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dRawY = ev.getRawY();
                dRawX = ev.getRawX();
                Log.d(TAG, "dispatchTouchEvent: MyRecyclerView ");
                itemSlideHasJudged = false;
                downRawX1 = ev.getRawX();
                disX = 0;
                childCapture = null;
                break;
            case MotionEvent.ACTION_MOVE:
                float mRawY = ev.getRawY();
                float mRawX = ev.getRawX();
                float dy = mRawY - dRawY;
                float dx = mRawX - dRawX;

                disX = mRawX - downRawX1;
                downRawX1 = mRawX;

                if (hasOpenedItem) {
                    closeItem();
                    return true;
                } else {

                    if (!itemSlideHasJudged) {
                        if (Math.abs(dy) > 0) {
                            if (Math.abs(dy) > thresholdY || Math.abs(dx) > thresholdX) {
                                // 夹角判断
                                double atan = Math.atan(Math.abs(dx) / Math.abs(dy));
//                    Log.d(TAG, "dispatchTouchEvent: atant = " + atan);
                                double angle = (atan / (2 * Math.PI)) * 360;
//                                Log.d(TAG, "dispatchTouchEvent: angle = " + angle);

                                if (angle >= 45) {//item horizontal slide
                                    itemSlideHasJudged = true;
                                    return true;
                                } else {// RecyclerView vertical scroll


                                }
                            } else {
                                return true;
                            }
                        }
                    } else if (itemSlideHasJudged) {// item sliding
                        if (childCapture != null) {
                            contentView = childCapture.findViewById(R.id.item_slide_content);
                            deleteView = childCapture.findViewById(R.id.item_slide_delte);
                            int contentViewLeft = contentView.getLeft();
                            int contentViewTop = contentView.getTop();
                            int contentViewRight = contentView.getRight();
                            int contentViewBottom = contentView.getBottom();
//                        Log.d(TAG, "dispatchTouchEvent: contentViewLeft =" + contentViewLeft);
//                        Log.d(TAG, "dispatchTouchEvent: contentViewTop = " + contentViewTop);
//                        Log.d(TAG, "dispatchTouchEvent: contentViewRight " + contentViewRight);
//                        Log.d(TAG, "dispatchTouchEvent: contentViewBottom = " + contentViewBottom);

                            deleteViewMeasuredWidth = deleteView.getMeasuredWidth();
//                        Log.d(TAG, "dispatchTouchEvent: deleteViewMeasuredWidth = " + deleteViewMeasuredWidth);
                            contentViewMeasuredWidth = contentView.getMeasuredWidth();
                            int left = (int) (contentViewLeft + disX);
//                        int right = (int) (contentViewRight + disX);

                            if (left < -deleteViewMeasuredWidth) {
                                left = -deleteViewMeasuredWidth;
                            } else if (left > 0) {
                                left = 0;
                            }

                            int rightContent = left + contentViewMeasuredWidth;
                            contentView.layout(left, contentView.getTop(), rightContent, contentView.getBottom());
                            contentView.invalidate();

                            deleteView.layout(rightContent, deleteView.getTop(), rightContent + deleteViewMeasuredWidth, deleteView.getBottom());
                            deleteView.invalidate();

                        } else {
                            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) getLayoutManager();
                            int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                            int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
//                        Log.d(TAG, "dispatchTouchEvent: lastVisibleItemPosition = " + lastVisibleItemPosition);
//                        Log.d(TAG, "dispatchTouchEvent: firstVisibleItemPosition = " + firstVisibleItemPosition);
                            for (int i = firstVisibleItemPosition; i <= lastVisibleItemPosition; i++) {
//                        Log.d(TAG, "dispatchTouchEvent: count = " + linearLayoutManager.getItemCount());
//                            View childAt = linearLayoutManager.getChildAt(i);
                                ViewHolder viewHolder = findViewHolderForAdapterPosition(i);
                                if (viewHolder != null && viewHolder.itemView != null) {
                                    View itemView = viewHolder.itemView;
                                    int[] outLocation = new int[2];
                                    itemView.getLocationOnScreen(outLocation);
                                    int childAtHeight = itemView.getHeight();
//                            Log.d(TAG, "dispatchTouchEvent: childAtHeight = "+childAtHeight);
//                            Log.d(TAG, "dispatchTouchEvent: outLocation = "+ Arrays.toString(outLocation));
//                                int childAtWidth = childAt.getWidth();
                                    if (dRawY >= outLocation[1] && dRawY <= childAtHeight + outLocation[1]) {//capture this view
//                                childAt.layout((int) disX, outLocation[1], childAtWidth, outLocation[1] + childAtHeight);
//                                childAt.invalidate();
//                                Log.d(TAG, "dispatchTouchEvent: disX = "+disX);
                                        childCapture = itemView;
                                        Log.d(TAG, "dispatchTouchEvent: disX = " + disX);
                                        break;
                                    } else {
                                        childCapture = null;
                                    }
                                } else {
                                    Log.d(TAG, "dispatchTouchEvent: childAt 是空的");
                                }
                            }
                        }

                        return true;
                    }
                }


                break;
            case MotionEvent.ACTION_UP:
//                if (angle >= 45) {//item horizontal slide
//
//                    return true;
//                } else {// RecyclerView vertical scroll
//
//
//                }
                if (hasOpenedItem) {
                    hasOpenedItem = false;
                    closeItem();
                    return true;
                } else {

                    if (itemSlideHasJudged) {//item horizontal slide

                        itemHorizontalSlide();

                        return true;
                    } else {// RecyclerView vertical scroll

                    }
                }

                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void itemHorizontalSlide() {
        if (contentView != null && deleteView != null) {
            if (contentView.getLeft() <= -deleteViewMeasuredWidth / 2.0f) {//open

                ValueAnimator ofInt = ValueAnimator.ofFloat(1);
                ofInt.setDuration(100);
                ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float factor = (float) animation.getAnimatedValue();
                        Integer left = evaluate(factor, contentView.getLeft(), -deleteViewMeasuredWidth);
                        contentView.layout(left, contentView.getTop(), left + contentViewMeasuredWidth, contentView.getBottom());
                        deleteView.layout(left + contentViewMeasuredWidth, deleteView.getTop(), left + contentViewMeasuredWidth + deleteViewMeasuredWidth, contentView.getBottom());
                        contentView.invalidate();
                        deleteView.invalidate();
//                        Log.d(TAG, "onAnimationUpdate: factor = "+factor);
                        if (factor >= 1.0f) {
                            hasOpenedItem = true;
                        }
                    }
                });
                ofInt.start();

            } else {//close

                closeItem();
            }
        }

    }

    private void closeItem() {
        ValueAnimator ofInt = ValueAnimator.ofFloat(1);
        ofInt.setDuration(100);
        ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float factor = (float) animation.getAnimatedValue();
                Integer left = evaluate(factor, contentView.getLeft(), 0);
                contentView.layout(left, contentView.getTop(), left + contentViewMeasuredWidth, contentView.getBottom());
                deleteView.layout(left + contentViewMeasuredWidth, deleteView.getTop(), left + contentViewMeasuredWidth + deleteViewMeasuredWidth, contentView.getBottom());
                contentView.invalidate();
                deleteView.invalidate();
            }
        });
        ofInt.start();
    }


    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
        int startInt = startValue;
        return (int) (startInt + fraction * (endValue - startInt));
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        boolean interceptTouchEvent = super.onInterceptTouchEvent(e);
        Log.d(TAG, "onInterceptTouchEvent: interceptTouchEvent = " + interceptTouchEvent);
        return interceptTouchEvent;
    }

    /**
     * @param e
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onTouchEvent: ACTION_DOWN ");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onTouchEvent: ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "onTouchEvent: ACTION_UP");
                break;
            default:
                break;
        }
        boolean event = super.onTouchEvent(e);
        Log.d(TAG, "onTouchEvent: event = " + event);
        return event;
    }

//    ViewHolder viewHolder = findViewHolderForAdapterPosition(i);
//    if (viewHolder != null) {
//        if (viewHolder.itemView != null) {
//            View itemView = viewHolder.itemView;
//            int itemViewWidth = itemView.getWidth();
//            Log.d(TAG, "dispatchTouchEvent: itemViewWidth = " + itemViewWidth);
//            int itemViewHeight = itemView.getHeight();
//            Log.d(TAG, "dispatchTouchEvent: itemViewHeight = " + itemViewHeight);
//            int itemViewLeft = itemView.getLeft();
//            Log.d(TAG, "dispatchTouchEvent: itemViewLeft = " + itemViewLeft);
//            int itemViewTop = itemView.getTop();
//            Log.d(TAG, "dispatchTouchEvent: itemViewTop = " + itemViewTop);
//            int itemViewRight = itemView.getRight();
//            Log.d(TAG, "dispatchTouchEvent: itemViewRight = " + itemViewRight);
//            int itemViewBottom = itemView.getBottom();
//            Log.d(TAG, "dispatchTouchEvent: itemViewBottom = " + itemViewBottom);
//        }
//    } else {
//        Log.d(TAG, "dispatchTouchEvent: viewholder 是空的");
//    }

//    View slideItem = childCapture.findViewById(R.id.slide_item);
//                        int left = (int) (slideItem.getLeft() + disX);
//                        int slideItemTop = slideItem.getTop();
//                        int right = (int) (slideItem.getRight() + disX);
//                        int slideItemBottom = slideItem.getBottom();
//                        Log.d(TAG, "dispatchTouchEvent: left = " + left);
//                        Log.d(TAG, "dispatchTouchEvent: top = " + slideItemTop);
//                        Log.d(TAG, "dispatchTouchEvent: right = " + right);
//                        Log.d(TAG, "dispatchTouchEvent: bottom = " + slideItemBottom);
//                        slideItem.layout(left, slideItemTop, right, slideItemBottom);
//                        slideItem.invalidate();
//                        Log.d(TAG, "dispatchTouchEvent: capture view ");

//                        int childCaptureLeft = childCapture.getLeft();
//                        int childCaptureTop = childCapture.getTop();
//                        int childCaptureRight = childCapture.getRight();
//                        int childCaptureBottom = childCapture.getBottom();
//                        Log.d(TAG, "dispatchTouchEvent: childCaptureLeft = " + childCaptureLeft);
//                        Log.d(TAG, "dispatchTouchEvent: childCaptureTop = " + childCaptureTop);
//                        Log.d(TAG, "dispatchTouchEvent: childCaptureRight = " + childCaptureRight);
//                        Log.d(TAG, "dispatchTouchEvent: childCaptureBottom = " + childCaptureBottom);
//                        int left = (int) (childCaptureLeft + disX);
//                        int right = (int) (childCaptureRight + disX);
//                        childCapture.layout(left,childCaptureTop, right,childCaptureBottom);
//                        invalidate();

}
