package cn.th.phonerf.activity.query.present;

import android.view.View;

import cn.th.phonerf.activity.base.IBasePresenter;
import cn.th.phonerf.model.MerchantShop;

public interface IQueryPresenter extends IBasePresenter {

    void doRequest(View view);

    void doQuery(View view, String query, String shopId, MerchantShop merchantShop);

    void doDownLabel(View view, Integer merchantId);
}
