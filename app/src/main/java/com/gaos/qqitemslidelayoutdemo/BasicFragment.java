package com.gaos.qqitemslidelayoutdemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;

/**
 * Author:　Created by benjamin
 * DATE :  2017/10/23 13:34
 * versionCode:　v2.2
 */

public class BasicFragment extends Fragment {

    private View inflate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_basic, container, false);
//        return super.onCreateView(inflater, container, savedInstanceState);
        return inflate;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) inflate.findViewById(R.id.vpPager);

        /**
         * Return the FragmentManager for interacting with fragments associated
         * with this fragment's activity.  Note that this will be non-null slightly
         * before {@link #getActivity()}, during the time from when the fragment is
         * placed in a {@link FragmentTransaction} until it is committed and
         * attached to its activity.
         * 在 onAttach 后就不会为空了。
         *
         * <p>If this Fragment is a child of another Fragment, the FragmentManager
         * returned here will be the parent's {@link #getChildFragmentManager()}.
         */
//        FragmentManager childFragmentManager = getChildFragmentManager();
        FragmentManager fragmentManager = getFragmentManager();


        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(fragmentManager);
        viewPager.setAdapter(myPagerAdapter);

        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) inflate.findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);
    }

}
