package com.gaos.qqitemslidelayoutdemo.interfaces;


import android.view.View;

/**
 * Author:　Created by benjamin
 * DATE :  2018/4/18 19:12
 * versionCode:　v2.2
 */

public class IViewObservable {

    public static IViewObservable iViewObservable;

    public static IViewObservable newInstance() {
        if (iViewObservable == null) {
            synchronized (IViewObservable.class) {
                if (iViewObservable == null) {
                    iViewObservable = new IViewObservable();
                }
            }
        }
        return iViewObservable;
    }


    public IViewSubscribe iViewSubscribe;

    public interface IViewSubscribe {
        // 订阅
        void onObserve(View view);
    }

    public void setiViewSubscribe(IViewSubscribe iViewSubscribe) {
        this.iViewSubscribe = iViewSubscribe;
    }
}
