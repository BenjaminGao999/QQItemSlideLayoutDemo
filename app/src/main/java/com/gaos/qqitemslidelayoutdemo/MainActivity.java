package com.gaos.qqitemslidelayoutdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        MyOutViewGroup myOutViewGroup = (MyOutViewGroup) findViewById(R.id.my_relative);


        int dataCount = 30;
        ArrayList<String> datas = new ArrayList<>();
        for (int i = 0; i < dataCount; i++) {
            datas.add("Item " + i);
        }

        MyRVAdapter myRVAdapter = new MyRVAdapter(myOutViewGroup);
        myRVAdapter.setData(datas);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mRecyclerView.addItemDecoration(new MyRVItemDecoration());

        mRecyclerView.setAdapter(myRVAdapter);

    }
}
