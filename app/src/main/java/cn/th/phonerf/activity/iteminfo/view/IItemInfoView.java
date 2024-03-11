package cn.th.phonerf.activity.iteminfo.view;

import java.util.List;

import cn.th.phonerf.activity.base.IBaseView;
import cn.th.phonerf.model.TBdItemCls;
import cn.th.phonerf.model.TBdItemInfo;

public interface IItemInfoView extends IBaseView {
    /**
     * 请求成功后
     */
    void resRequestData();

    /**
     * 错误提示
     * @param errStr
     */
    void showErrorMsg(String errStr);


    void resGetBigCls(List<TBdItemCls> lists);

    void resGetSmallCls(List<TBdItemCls> lists);

    void resGetItemList(List<TBdItemInfo> lists);
}
