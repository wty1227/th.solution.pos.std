package cn.th.phonerf.http.request;

import android.view.View;

import org.json.JSONObject;

import java.io.IOException;

/**
 * @Info
 * @Auth Bello
 * @Time 16-6-22 上午11:46
 * @Ver
 */
public interface IHttpRequest {

    /**
     * 接口提交
     * @param action
     * @param info
     * @param view
     */

    void postData(String action, JSONObject info, View view);
    void postData(String action, JSONObject info, View view, int requestMethod);
    void postData(String action, String info, View view);

    void postDataA(String action, Object info, View view);

    /**
     * 本地接口
     * @param action
     * @param info
     * @param view
     */
    void postLocalData(String action, String info, View view);
    /**
     * 接口请求成功
     *
     * @param response
     * @param action
     */
//    void setDataSuccess(JSONObject response, String action);

    /**
     * 接口请求失败
     *
     * @param action
     * @param msg
     */
//    void setDataError(String action, String msg);

    /**
     * 处理接口返回的数据
     *
     * @param isSuccess 成功 、失败
     * @param response  成功的数据
     * @param action    成功的接口名
     * @param msg       失败的信息
     */
    void postResult(final boolean isSuccess, final JSONObject response, final String action, final String msg, View view);

}
