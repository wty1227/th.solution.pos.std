package cn.th.phonerf.activity.example.view;

import cn.th.phonerf.activity.base.IBaseView;

public interface IExampleView extends IBaseView {
    /**
     * 请求成功后
     */
    void resRequestData();

    /**
     * 错误提示
     * @param errStr
     */
    void showErrorMsg(String errStr);
}
