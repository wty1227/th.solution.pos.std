package cn.th.phonerf.activity.report.present;

import android.view.View;

import cn.th.phonerf.activity.base.IBasePresenter;
import cn.th.phonerf.model.MerchantShop;

public interface IRptPresenter extends IBasePresenter {


    void doGetData(View view, String query, String shopId, String userId, MerchantShop merchantShop);
}
