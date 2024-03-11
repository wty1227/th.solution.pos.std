package cn.th.phonerf.activity.exceptflow.view;

import cn.th.phonerf.activity.base.IBaseView;
import cn.th.phonerf.model.PayResult;
import cn.th.phonerf.model.TRmSaleman;

public interface IExceptFlowView extends IBaseView {
    /**
     * 请求成功后
     */
    void resRequestData();

    void resPayQuery(PayResult data);

    void resGetSalerInfo(TRmSaleman data);

    void resGetFlow(String code,String msg);
    /**
     * 错误提示
     * @param errStr
     */
    void showErrorMsg(String errStr);
}
