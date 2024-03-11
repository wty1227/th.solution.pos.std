package cn.th.phonerf.activity.iteminfo.present;

import android.view.View;

import cn.th.phonerf.activity.base.IBasePresenter;

public interface IItemInfoPresenter extends IBasePresenter {

    void doRequest(View view);

    void doGetBigCls(View view);

    void doGetSmallCls(View view, String itemClsno);

    void doGetItemList(View view, String itemClsno);

}
