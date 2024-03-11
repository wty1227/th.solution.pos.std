package cn.th.phonerf.activity.pay.view;

import org.json.JSONObject;

import cn.th.phonerf.activity.base.IBaseView;
import cn.th.phonerf.model.PayResult;

public interface IPayView extends IBaseView {
    /**
     * 请求成功后
     */
    void resRequestData();


    /**
     * 请求成功后
     */
    void resRequestData(String tag, String msg, JSONObject response);
    /**
     * 错误提示
     * @param errStr
     */
    void showErrorMsg(String errStr);


    /**
     * 订单出错，重置reqsn
     * @param msg
     */
    void resPayOrderError(String msg);

//    void resPayPayFlow(PayResult data);
//
//    void resPayQueryFlow(PayResult data);
//
//    void resPayCancelFlow(PayResult data);
}
