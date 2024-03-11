package cn.th.phonerf.activity.pay.present;

import android.view.View;

import cn.th.phonerf.activity.base.IBasePresenter;
import cn.th.phonerf.model.PayBodyVo;

public interface IPayPresenter extends IBasePresenter {

    void doRequest(View view);

    //void doPayPayFlow(View view, String appId, String appKey, String branchNo, String orderNo, String authCode, Double trxamt, String body, String remark, String payWay, String idList);
    void doPayPayFlow(View view, PayBodyVo vo);

    void doPayQueryFlow(View view, String posId, String orderNo);

    void doPayCancelFlow(View view, String appId, String appKey, String orderNo, String oldtrxid, String oldreqsn, Double trxamt, String remark);


    void doPayPayXz(View view, PayBodyVo vo);
}
