package cn.th.phonerf.activity.exceptflow.present;

import android.view.View;

import cn.th.phonerf.activity.base.IBasePresenter;

public interface IExceptFlowPresenter extends IBasePresenter {

    void doRequest(View view);

    void doPayQueryPhoneOrderNo(View view, String appId, String appKey, String orderNo);

    void doGetSalerInfo(View view, String saleId);

    void doGetFlow(View view, String flowNo, String branchNo);
}
