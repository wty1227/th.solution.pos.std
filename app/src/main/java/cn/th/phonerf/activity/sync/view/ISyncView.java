package cn.th.phonerf.activity.sync.view;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import cn.th.phonerf.activity.base.IBaseView;

public interface ISyncView extends IBaseView {
    void resGetMaxTodayFlow(String flowNo);

    void resGetPayment(JSONArray data, Long num);

    void resGetItemInfo(JSONArray data, Long num);

    void resGetItemCls(JSONArray data, Long num);

    void resGetSaleman(JSONArray data, Long num);

    void resGetPrice(JSONArray data, Long num);

    void resSyncError(String msg);
}
