package com.gaos.qqitemslidelayoutdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.gaos.qqitemslidelayoutdemo.interfaces.IViewObservable;

import java.util.ArrayList;

public class FirstFragment extends Fragment {
    // Store instance variables
    private String title;
    private int page;
    private RecyclerView mRecyclerView;

    // newInstance constructor for creating fragment with arguments
    public static FirstFragment newInstance(int page, String title) {
        FirstFragment fragmentFirst = new FirstFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        RelativeLayout myOutViewGroup = (RelativeLayout) view.findViewById(R.id.my_relative);

        int dataCount = 30;
        ArrayList<String> datas = new ArrayList<>();
        for (int i = 0; i < dataCount; i++) {
            datas.add("Item " + i);
        }

        MyAdapter myAdapter = new MyAdapter(myOutViewGroup);
        myAdapter.setData(datas);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mRecyclerView.addItemDecoration(new MyItemDecoration());

        mRecyclerView.setAdapter(myAdapter);
    }
}