package cn.th.phonerf.activity.index.view;

import cn.th.phonerf.activity.base.IBaseView;

public interface IIndexView extends IBaseView {
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
