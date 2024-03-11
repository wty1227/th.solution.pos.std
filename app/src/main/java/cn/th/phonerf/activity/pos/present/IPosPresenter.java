package cn.th.phonerf.activity.pos.present;

import android.view.View;

import cn.th.phonerf.activity.base.IBasePresenter;
import cn.th.phonerf.model.TBdItemInfo;

public interface IPosPresenter extends IBasePresenter {

    void doRequest(View view);
    void doVersion();
    void doGetSalerInfo(View view, String saleId);
    void doGetShoppingBagItem(View view);
    void doClearCart(View view, String posId);
    void doQueryCart(View view, String posId);
    void doAddCart(View view, String cardId, String barcode, String storeNo, String posId, String cashierNo);
    void doUpdateCart(View view, String posId, int comNo, double saleQnty);
    void doRemoveCart(View view, String posId, int comNo);
    void doGetVipInfo(View view, String cardId);
    void doVipUpdateFlow(View view, String posId, String cardId);
    void doFindPromote(TBdItemInfo data);
    void doPreForAllDis(View view, String storeNo, String posId);

    /*void doPayPayUrea(View view, String appId, String appKey, String branchNo, String orderNo, String authCode, Double trxamt, String body, String remark, String payWay, Integer id);

    void doPayQueryUrea(View view, String appId, String appKey, String orderNo, String payWay, Integer id);

    void doPayCancelUrea(View view, String appId, String appKey, String orderNo, String oldtrxid, String oldreqsn, Double trxamt, String remark);

    void doUploadFlow(View view, List<TRmSaleflow> saleList, List<TRmPayflow> payList);

    void doGetRetFlow(View view, String flowNo);

    void doModifyPass(View view, String userPassOld, String userPassNew, String userId);*/


/*
    void doGetUreaFlow(View view, String date, String branchNo);
    void doGetUreaFlowFinish(View view, String date, String branchNo);
    void doGetUreaFlowById(View view, Integer date);


    void doPayPayFlow(View view, String appId, String appKey, String branchNo, String orderNo, String authCode, Double trxamt, String body, String remark, String payWay, String idList);

    void doPayQueryFlow(View view, String appId, String appKey, String orderNo, String payWay, String idList);

    void doPayCancelFlow(View view, String appId, String appKey, String orderNo, String oldtrxid, String oldreqsn, Double trxamt, String remark);
*/
}
