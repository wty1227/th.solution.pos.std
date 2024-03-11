package cn.th.phonerf.activity.login.present;

import android.view.View;

import cn.th.phonerf.activity.base.IBasePresenter;

public interface ILoginPresenter extends IBasePresenter {

    void doVersion();
    /**
     * 登录提交
     * @param view
     * @param login
     * @param password
     */
    void doLogin(View view, String login, String password, String branchNo);

    void doGetGrants(View view, Integer uId, String userId,  String shopId, Integer merchantId);
    /**
     * 判断是否可以登录该门店
     * @param view
     * @param userId
     * @param shopId
     * @param merchantId
     */
    void doCanLoginShop(View view, String userId, String shopId, Integer merchantId);

    void doGetPhoneInfo(View view, String phone, String phoneSn, String version, String userId, Integer merchantId);


    void doGetPosInfo(View view, String phoneSn, String version);
    /**
     * 获取查询语句
     * @param view
     * @param merchantId
     */
    //void doDownloadSystemInfo(View view,Integer merchantId);
    /**
     * 下载门店信息
     * @param view
     * @param merchantId
     */
    void doDownloadShopInfo(View view, Integer merchantId);

    void doDownloadSysSystem(View view, Integer merchantId);
}
