package com.gaos.qqitemslidelayoutdemo;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Author:　Created by benjamin
 * DATE :  2017/10/18 17:50
 * versionCode:　v2.2
 */

public class MyRVAdapter extends RecyclerView.Adapter {
    private static final String TAG = "MyRVAdapter";
    private ArrayList<String> mDatas;
    private MyRVViewHolder myRVViewHolder;


    public MyRVAdapter(RelativeLayout myOutViewGroup) {

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        myRVViewHolder = new MyRVViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slide, parent, false));
        myRVViewHolder.bindItem();
        return myRVViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        ((MyRVViewHolder) holder).bindItem(position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void setData(ArrayList<String> datas) {
        mDatas = datas;
    }

    class MyRVViewHolder extends RecyclerView.ViewHolder {

        private View mViewContent;
        private View mViewDelete;

        public MyRVViewHolder(View itemView) {
            super(itemView);
            mViewContent = itemView.findViewById(R.id.ll_slide_content);
            mViewDelete = itemView.findViewById(R.id.ll_slide_delte);
        }

        public void bindItem() {
            itemView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//                    Log.d(TAG, "onLayoutChange: left = " + left);
//                    Log.d(TAG, "onLayoutChange: left = " + left);
//                    Log.d(TAG, "onLayoutChange: top = " + top);
//                    Log.d(TAG, "onLayoutChange: right = " + right);
//                    Log.d(TAG, "onLayoutChange: bottom = " + bottom);
                }
            });
        }

        public void bindItem(final int position) {
            TextView tvContent = (TextView) itemView.findViewById(R.id.mTvContent);
            tvContent.setText(mDatas.get(position));
            Log.d(TAG, "bindItem: " + mDatas.get(position));
            itemView.findViewById(R.id.ll_slide_content).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "clicked " + mDatas.get(position), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onClick: item clicked");
                }
            });
        }
    }


}
