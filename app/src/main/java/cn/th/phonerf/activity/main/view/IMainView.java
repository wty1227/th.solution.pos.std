package cn.th.phonerf.activity.main.view;

import com.alibaba.fastjson.JSONArray;

import cn.th.phonerf.activity.base.IBaseView;

public interface IMainView extends IBaseView {
    /**
     * 请求成功后
     */
    void resRequestData();

    void resGetItemCount(String data);

    void resGetDownloadCount(String data);

    void resDownloadItem(JSONArray data);

    void resDownloadBarcode(JSONArray data);
    /**
     * 错误提示
     *
     * @param errStr
     */
    void showErrorMsg(String errStr);
}