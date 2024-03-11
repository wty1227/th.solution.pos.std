package cn.th.phonerf.activity.splash.view;

import cn.th.phonerf.activity.base.IBaseView;

public interface ISplashView extends IBaseView {
    void resGetMaxTodayFlow(String flowNo);

    void resSyncError(String msg);
}
