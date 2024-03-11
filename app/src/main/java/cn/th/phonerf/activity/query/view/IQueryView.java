package cn.th.phonerf.activity.query.view;

import com.alibaba.fastjson.JSONArray;

import java.util.List;

import cn.th.phonerf.activity.base.IBaseView;
import cn.th.phonerf.model.PrintDesignMaster;

public interface IQueryView extends IBaseView {
    /**
     * 请求成功后
     */
    void resRequestData(JSONArray data);

    void resDownLabel(List<PrintDesignMaster> list);
    /**
     * 错误提示
     * @param errStr
     */
    void showErrorMsg(String errStr);
}
