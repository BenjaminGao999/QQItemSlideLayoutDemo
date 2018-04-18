package com.gaos.qqitemslidelayoutdemo;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gaos.qqitemslidelayoutdemo.interfaces.IViewObservable;

import java.util.ArrayList;

/**
 * Author:　Created by benjamin
 * DATE :  2017/10/18 17:50
 * versionCode:　v2.2
 */

public class MyAdapter extends RecyclerView.Adapter {
    private static final String TAG = "MyAdapter";
    private ArrayList<String> mDatas;
    private MyViewHolder myViewHolder;


    public MyAdapter(RelativeLayout myOutViewGroup) {

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        myViewHolder = new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slide, parent, false));
        myViewHolder.bindItem();
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        ((MyViewHolder) holder).bindItem(position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void setData(ArrayList<String> datas) {
        mDatas = datas;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private View mViewContent;
        private View mViewDelete;
        private View itemView;
        private int position;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            mViewContent = itemView.findViewById(R.id.item_slide_content);
            mViewDelete = itemView.findViewById(R.id.item_slide_delte);

            initListener();
        }

        private void initListener() {
            itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    // TODO: 2018/4/18 向外发布  “我” 正在被手指按住
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        if (IViewObservable.newInstance().iViewSubscribe != null) {
                            IViewObservable.newInstance().iViewSubscribe.onObserve(itemView);
                        }
                    }

                    return false;
                }
            });
        }

        public void bindItem() {
            itemView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

                }
            });
        }

        public void bindItem(final int position) {
            this.position = position;
            TextView tvContent = (TextView) itemView.findViewById(R.id.mTvContent);
            tvContent.setText(mDatas.get(position));
            Log.d(TAG, "bindItem: " + mDatas.get(position));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "clicked " + mDatas.get(position), Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "onClick: ");
                }
            });
        }
    }


}
