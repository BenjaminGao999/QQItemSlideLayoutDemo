package com.gaos.codearchive;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Author:　Created by benjamin
 * DATE :  2017/10/18 13:22
 * versionCode:　v2.2
 */

public class ItemSlideLayout extends ViewGroup {
    private static final String TAG = "SlideDelete";
    public static boolean isClickEnable = false;
    private View mContent; // 内容部分
    private View mDelete;  // 删除部分
    private ViewDragHelper mDragHelper;
    private int mContentWidth;
    private int mContentHeight;
    private int mDeleteWidth;
    private int mDeleteHeight;

    public ItemSlideLayout(Context context) {
        super(context);
    }

    public ItemSlideLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemSlideLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
//        mDragHelper = ViewDragHelper.create(this, 1.0f, new MyDrawHelper());
        super.onFinishInflate();
        mContent = getChildAt(0);
        mDelete = getChildAt(1);
        mDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "删除", Toast.LENGTH_SHORT).show();
            }
        });

        mContent.setOnTouchListener(new OnTouchListener() {

            private float downRawY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downRawY = event.getRawY();
                        Log.d(TAG, "onTouch: action down ");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.d(TAG, "onTouch: action move");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d(TAG, "onTouch: action up");
                        float upRawY = event.getRawY();
                        if (Math.abs(upRawY - downRawY) < 1) {
                            Log.d(TAG, "onTouch: 判断条件");
                            ItemSlideLayout.isClickEnable = true;
                        } else {
                            ItemSlideLayout.isClickEnable = false;
                        }
                        break;

                    default:
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return mDragHelper.shouldInterceptTouchEvent(ev);
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        mDragHelper.processTouchEvent(event);
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                Log.d(TAG, "onTouchEvent: action down ");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.d(TAG, "onTouchEvent: action move");
//                break;
//            case MotionEvent.ACTION_UP:
//                Log.d(TAG, "onTouchEvent: action up");
//                break;
//            default:
//                break;
//        }
//        return true;
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 这跟mContent的父亲的大小有关，父亲是宽填充父窗体，高度是和孩子一样是60dp
        mContent.measure(widthMeasureSpec, heightMeasureSpec); // 测量内容部分的大小
        LayoutParams layoutParams = mDelete.getLayoutParams();
        int deleteWidth = MeasureSpec.makeMeasureSpec(layoutParams.width, MeasureSpec.EXACTLY);
        int deleteHeight = MeasureSpec.makeMeasureSpec(layoutParams.height, MeasureSpec.EXACTLY);
        // 这个参数就需要指定为精确大小
        mDelete.measure(deleteWidth, deleteHeight); // 测量删除部分的大小
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mContentWidth = mContent.getMeasuredWidth();
        mContentHeight = mContent.getMeasuredHeight();
        mContent.layout(0, 0, mContentWidth, mContentHeight); // 摆放内容部分的位置
        mDeleteWidth = mDelete.getMeasuredWidth();
        mDeleteHeight = mContentHeight;
        mDelete.layout(mContentWidth, 0, mContentWidth + mDeleteWidth, mContentHeight); // 摆放删除部分的位置
    }


    class MyDrawHelper extends ViewDragHelper.Callback {
        /**
         * Touch的down事件会回调这个方法 tryCaptureView
         *
         * @return : ViewDragHelper是否继续分析处理 child的相关touch事件
         * @Child：指定要动的孩子 （哪个孩子需要动起来）
         * @pointerId: 点的标记
         */
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
//            System.out.println("调用tryCaptureView");
//            System.out.println("contentView : " + (mContent == child));
            return mContent == child || mDelete == child;
        }
        // Touch的move事件会回调这面这几个方法
        // clampViewPositionHorizontal
        // clampViewPositionVertical
        // onViewPositionChanged

        /**
         * 捕获了水平方向移动的位移数据
         *
         * @param child 移动的孩子View
         * @param left  父容器的左上角到孩子View的距离
         * @param dx    增量值，其实就是移动的孩子View的左上角距离控件（父亲）的距离，包含正负
         * @return 如何动
         * <p>
         * 调用完此方法，在android2.3以上就会动起来了，2.3以及以下是海动不了的
         * 2.3不兼容怎么办？没事，我们复写onViewPositionChanged就是为了解决这个问题的
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            Log.d("Slide", "增量值：   " + left);
            if (child == mContent) { // 解决内容部分左右拖动的越界问题
                if (left > 0) {
                    return 0;
                } else if (-left > mDeleteWidth) {
                    return -mDeleteWidth;
                }
            }
            if (child == mDelete) { // 解决删除部分左右拖动的越界问题
                if (left < mContentWidth - mDeleteWidth) {
                    return mContentWidth - mDeleteWidth;
                } else if (left > mContentWidth) {
                    return mContentWidth;
                }
            }
            return left;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return mDeleteWidth;
        }

        /**
         * 当View的位置改变时的回调  这个方法的价值是结合clampViewPositionHorizontal或者clampViewPositionVertical
         *
         * @param changedView 哪个View的位置改变了
         * @param left        changedView的left
         * @param top         changedView的top
         * @param dx          x方向的上的增量值
         * @param dy          y方向上的增量值
         */
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            //super.onViewPositionChanged(changedView, left, top, dx, dy);
            invalidate();
            if (changedView == mContent) { // 如果移动的是mContent
                //我们移动mContent的实惠要相应的联动改变mDelete的位置
                // 怎么改变mDelete的位置，当然是mDelete的layput方法啦
                int tempDeleteLeft = mContentWidth + left;
                int tempDeleteRight = mContentWidth + left + mDeleteWidth;
                mDelete.layout(tempDeleteLeft, 0, tempDeleteRight, mDeleteHeight);
            } else { // touch的是mDelete
                int tempContentLeft = left - mContentWidth;
                int tempContentRight = left;
                mContent.layout(tempContentLeft, 0, tempContentRight, mContentHeight);
            }
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            //super.onViewReleased(releasedChild, xvel, yvel);
            // 方法的参数里面没有left，那么我们就采用 getLeft()这个方法
            int mConLeft = mContent.getLeft();
            // 这里没必要分来两个孩子判断
            if (-mConLeft > mDeleteWidth / 2) {
                //mContent.layout(-mDeleteWidth,0,mContentWidth-mDeleteWidth,mContentHeight);
                //mDelete.layout(mContentWidth-mDeleteWidth,0,mContentWidth,mDeleteHeight);
                //采用ViewDragHelper的 smoothSlideViewTo 方法让移动变得顺滑自然，不会太生硬
                //smoothSlideViewTo只是模拟了数据，但是不会真正的动起来，动起来需要调用 invalidate
                // 而 invalidate 通过调用draw()等方法之后最后还是还是会调用 computeScroll 这个方法
                // 所以，使用 smoothSlideViewTo 做过渡动画需要结合  invalidate方法 和 computeScroll方法
                // smoothSlideViewTo的动画执行时间没有暴露的参数可以设置，但是这个时间是google给我们经过大量计算给出合理时间
//                if (releasedChild == mContent) {
//                    mDragHelper.settleCapturedViewAt(-mDeleteWidth, 0);
//                } else if (releasedChild == mDelete) {
//                    mDragHelper.settleCapturedViewAt(mContentWidth - mDeleteWidth, 0);
//                }
                isShowDelete(true);
                if (onSlideDeleteListener != null) {
                    onSlideDeleteListener.onOpen(ItemSlideLayout.this);
                }
            } else {
                //mContent.layout(0,0,mContentWidth,mContentHeight);
                //mDelete.layout(mContentWidth, 0, mContentWidth + mDeleteWidth, mDeleteHeight);
//                if (releasedChild == mContent) {
//                    mDragHelper.settleCapturedViewAt(0, 0);
//                } else if (releasedChild == mDelete) {
//                    mDragHelper.settleCapturedViewAt(mContentWidth, 0);
//                }
                isShowDelete(false);
                if (onSlideDeleteListener != null) {
                    onSlideDeleteListener.onClose(ItemSlideLayout.this);
                }
            }
            invalidate();
            super.onViewReleased(releasedChild, xvel, yvel);
        }
    }

    /**
     * 控制 delete 的显示和隐藏
     *
     * @param isShowDelete
     */
    public void isShowDelete(boolean isShowDelete) {
        if (isShowDelete) {
            mDragHelper.smoothSlideViewTo(mContent, -mDeleteWidth, 0);
            mDragHelper.smoothSlideViewTo(mDelete, mContentWidth - mDeleteWidth, 0);
        } else {
            mDragHelper.smoothSlideViewTo(mContent, 0, 0);
            mDragHelper.smoothSlideViewTo(mDelete, mContentWidth, 0);
        }
        invalidate();
    }

//    @Override
//    public void computeScroll() {
//        //super.computeScroll();
//        // 把捕获的View适当的时间移动，其实也可以理解为 smoothSlideViewTo 的模拟过程还没完成
//        if (mDragHelper.continueSettling(true)) {
//            invalidate();
//        }
//        // 其实这个动画过渡的过程大概在怎么走呢？
//        // 1、smoothSlideViewTo方法进行模拟数据，模拟后就就调用invalidate();
//        // 2、invalidate()最终调用computeScroll，computeScroll做一次细微动画，
//        //    computeScroll判断模拟数据是否彻底完成，还没完成会再次调用invalidate
//        // 3、递归调用，知道数据noni完成。
//    }

    // SlideDlete的接口
    public interface OnSlideDeleteListener {
        void onOpen(ItemSlideLayout itemSlideLayout);

        void onClose(ItemSlideLayout itemSlideLayout);
    }

    private OnSlideDeleteListener onSlideDeleteListener;

    public void setOnSlideDeleteListener(OnSlideDeleteListener onSlideDeleteListener) {
        this.onSlideDeleteListener = onSlideDeleteListener;
    }
}

