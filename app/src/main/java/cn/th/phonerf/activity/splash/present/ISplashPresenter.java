package cn.th.phonerf.activity.splash.present;

import android.view.View;

import cn.th.phonerf.activity.base.IBasePresenter;

public interface ISplashPresenter extends IBasePresenter {
    void doGetMaxTodayFlow(View view, String posId);
}
