package cn.th.phonerf.activity.login.view;

import com.alibaba.fastjson.JSONArray;

import org.json.JSONObject;

import java.util.List;

import cn.th.phonerf.activity.base.IBaseView;
import cn.th.phonerf.model.MerchantPhone;
import cn.th.phonerf.model.MerchantShop;
import cn.th.phonerf.model.MerchantUser;
import cn.th.phonerf.model.TSysOperator;
import cn.th.phonerf.model.TSysPosStatus;
import cn.th.phonerf.model.T_trader_login;
import cn.th.phonerf.model.UserGrant;

public interface ILoginView extends IBaseView {

    /**
     * 保存用户信息
     * @param data
     */
    void saveUserData(MerchantUser data);

    void resGetGrants(List<UserGrant> data);

    void resGetPhoneInfo(MerchantPhone data);

    void resGetPosInfo(TSysPosStatus data);

    void resGetLoginInfo(TSysOperator data);
    /**
     * 成功登录
     */
    void successLogin();
    /**
     * 保存门店信息
     * @param data
     */
    void saveShopData(JSONArray data);

    /**
     * 下载系统信息
     * @param data
     */
    void saveSysSystem(JSONArray data);
    /**
     * 登录错误提示
     * @param errStr
     */
    void showErrorMsg(String errStr);

    void resRequestData(String tag, String msg, JSONObject response);
}
