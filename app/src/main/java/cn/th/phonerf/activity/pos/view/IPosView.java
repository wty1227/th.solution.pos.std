package cn.th.phonerf.activity.pos.view;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import cn.th.phonerf.activity.base.IBaseView;
import cn.th.phonerf.model.PayResult;
import cn.th.phonerf.model.TBdItemCls;
import cn.th.phonerf.model.TBdItemInfo;
import cn.th.phonerf.model.TRmPayflowUrea;
import cn.th.phonerf.model.TRmSaleflow;
import cn.th.phonerf.model.TRmSaleman;

public interface IPosView extends IBaseView {
    /**
     * 请求成功后
     */
    void resRequestData();
    void showErrorMsg(String errStr);
    void resItemInfo(TBdItemInfo data);
    void resPromote(TBdItemInfo data);

    void resRequestData(String tag, String msg, JSONObject response);
//    void resGetSalerInfo(TRmSaleman data);

    /**
     * 订单出错，重置reqsn
     * @param msg
     */
//    void resPayOrderError(String msg);
/*
    void resPayPayUrea(PayResult data);

    void resPayQueryUrea(PayResult data);

    void resPayCancelUrea(PayResult data);
*/
    //void resUploadFlow();

    //void resGetRetFlow(List<TRmSaleflow> lists, String memo);
    /**
     * 错误提示
     * @param errStr
     */

/*
    void resGetUreaFlow(List<TRmPayflowUrea> lists);
    void resGetUreaFlowFinish(List<TRmPayflowUrea> lists);
    void resGetUreaFlowById(TRmPayflowUrea item);

    void resPayPayFlow(PayResult data);
    void resPayQueryFlow(PayResult data);
    void resPayCancelFlow(PayResult data);*/
}
