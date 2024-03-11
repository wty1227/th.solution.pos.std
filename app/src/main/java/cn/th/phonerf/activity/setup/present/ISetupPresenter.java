package cn.th.phonerf.activity.setup.present;

import android.view.View;

import cn.th.phonerf.activity.base.IBasePresenter;

public interface ISetupPresenter extends IBasePresenter {

    /**
     * 设置提交
     * @param view
     * @param merchantNo
     */
    void doSetup(View view, String merchantNo);
}
