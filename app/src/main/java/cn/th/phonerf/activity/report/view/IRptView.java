package cn.th.phonerf.activity.report.view;

import com.alibaba.fastjson.JSONArray;

import cn.th.phonerf.activity.base.IBaseView;

public interface IRptView extends IBaseView {
    /**
     * 请求成功后
     */
    void resRequestData();
    /**
     * 返回数据
     * @param data
     */
    void resGetData(JSONArray data);
}
