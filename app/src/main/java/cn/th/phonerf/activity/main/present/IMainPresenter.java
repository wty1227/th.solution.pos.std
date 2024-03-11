package cn.th.phonerf.activity.main.present;

import android.view.View;

import cn.th.phonerf.activity.base.IBasePresenter;
import cn.th.phonerf.model.MerchantShop;

public interface IMainPresenter extends IBasePresenter {

    void doRequest(View view);

    //void doGetItemCount(View view, String query, String shopId, String userId);
    void doModifyPass(View view, String userPassOld, String userPassNew, String userId, Integer merchantId);
    /**
     * 下载总数量
     * @param view
     * @param query
     * @param shopId
     * @param userId
     */
    void doGetDownloadCount(View view, String query, String shopId, String userId, MerchantShop merchantShop);

    /**
     * 下载商品资料
     * @param view
     * @param query
     * @param shopId
     * @param userId
     */
    void doDownloadItem(View view, String query, String shopId, String userId, MerchantShop merchantShop);

    /**
     * 下载条码资料
     * @param view
     * @param query
     * @param shopId
     * @param userId
     */
    void doDownloadBarcode(View view, String query, String shopId, String userId, MerchantShop merchantShop);
}
