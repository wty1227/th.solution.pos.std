package cn.th.phonerf.activity.sync.present;

import android.view.View;

import cn.th.phonerf.activity.base.IBasePresenter;

public interface ISyncPresenter extends IBasePresenter {
    void doGetMaxTodayFlow(View view, String posId);

    void doDownloadItem(View view, Long maxFlowId);

    void doDownloadCls(View view, Long maxFlowId);

    void doDownloadSaleman(View view, Long maxFlowId);

    void doDownloadPrice(View view, Long maxFlowId);

    void doDownloadPayment(View view, Long maxFlowId);

}
