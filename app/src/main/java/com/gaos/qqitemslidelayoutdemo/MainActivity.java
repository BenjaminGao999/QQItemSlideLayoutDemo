package com.gaos.qqitemslidelayoutdemo;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.astuetz.PagerSlidingTabStrip;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


//        // Get the ViewPager and set it's PagerAdapter so that it can display items
//        ViewPager viewPager = (ViewPager) findViewById(R.id.vpPager);
//        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
//
//        // Give the PagerSlidingTabStrip the ViewPager
//        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
//        // Attach the view pager to the tab strip
//        tabsStrip.setViewPager(viewPager);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_main,new BasicFragment())
                .commit();
    }
}
