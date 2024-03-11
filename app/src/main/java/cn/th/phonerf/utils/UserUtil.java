package cn.th.phonerf.utils;

import android.content.Context;

import cn.th.phonerf.constant.ApiConstants;
import cn.th.phonerf.constant.AppParam;
import cn.th.phonerf.constant.GPrinter;
import cn.th.phonerf.constant.GSale;
import cn.th.phonerf.model.MerchantInfo;
import cn.th.phonerf.model.MerchantUser;
import cn.th.phonerf.model.TSysOperator;
import cn.th.phonerf.utils.print.PrintInfo;

/**
 * @Info 用户信息操作
 * @Auth Bello
 * @Time 16-6-30 下午5:00
 * @Ver
 */
public class UserUtil {

    /**
     * 判断用户是否登录
     *
     * @param mContext
     * @return
     */
    public static boolean isLogin(Context mContext) {
        if (!SharePreferenceUtils.getValue(mContext, SharePreferenceUtils.LOGIN_ID, "").equals("")) {
            return true;
        }
        return false;
    }

    public static void getBaseSetup(Context mContext){
        ApiConstants.BASE_API_URL = SharePreferenceUtils.getValue(mContext, SharePreferenceUtils.BASE_API_URL, "");
        AppParam.BranchName = SharePreferenceUtils.getValue(mContext, SharePreferenceUtils.BRANCH_NAME, "");
        PrintInfo.title = SharePreferenceUtils.getValue(mContext, SharePreferenceUtils.BRANCH_NAME, "");
        PrintInfo.title += "\r\n";

        GPrinter.printName = SharePreferenceUtils.getValue(mContext, SharePreferenceUtils.PRINT_NAME, "none");
    }
    public static void setBaseSetup(Context mContext){
        SharePreferenceUtils.setValue(mContext, SharePreferenceUtils.BASE_API_URL, ApiConstants.BASE_API_URL);
        SharePreferenceUtils.setValue(mContext, SharePreferenceUtils.BRANCH_NAME, AppParam.BranchName);
        SharePreferenceUtils.setValue(mContext, SharePreferenceUtils.PRINT_NAME, GPrinter.printName);
    }



    public static void saveLogin(Context mContext, TSysOperator data) {
        SharePreferenceUtils.setValue(mContext, SharePreferenceUtils.OPER_ID, data.getOper_id());
        SharePreferenceUtils.setValue(mContext, SharePreferenceUtils.OPER_NAME, data.getOper_name());
        SharePreferenceUtils.setValue(mContext, SharePreferenceUtils.OPER_PASS, data.getOper_pw2());
        //SharePreferenceUtils.setValue(mContext, SharePreferenceUtils.LOGIN_ID, data.getLoginId());
        //SharePreferenceUtils.setValue(mContext, SharePreferenceUtils.BRANCH_NO, shopId);
    }

    public static void getLogin(Context mContext){
        GSale.CashierNo = SharePreferenceUtils.getValue(mContext, SharePreferenceUtils.OPER_ID, "");
        GSale.CashierName = SharePreferenceUtils.getValue(mContext, SharePreferenceUtils.OPER_NAME, "");
        GSale.CashierPassword = SharePreferenceUtils.getValue(mContext, SharePreferenceUtils.OPER_PASS, "");
        GSale.StoreNo = SharePreferenceUtils.getValue(mContext, SharePreferenceUtils.STORE_NO, "");
        //AppParam.OperName = SharePreferenceUtils.getValue(mContext, SharePreferenceUtils.OPER_NAME, "");

        //AppParam.BranchName = SharePreferenceUtils.getValue(mContext, SharePreferenceUtils.OPER_ID, "");
    }

    /**
     * 退出登录
     *
     * @param mContext
     */
    public static void loginOut(Context mContext) {
        SharePreferenceUtils.setValue(mContext, SharePreferenceUtils.OPER_ID, "");
        SharePreferenceUtils.setValue(mContext, SharePreferenceUtils.OPER_NAME, "");
        SharePreferenceUtils.setValue(mContext, SharePreferenceUtils.OPER_PASS, "");
        SharePreferenceUtils.setValue(mContext, SharePreferenceUtils.LOGIN_ID, "");

        // 清空webview的缓存数据
        //mContext.deleteDatabase("webview.db");
        //mContext.deleteDatabase("webview.db-journal");
        //mContext.deleteDatabase("webviewCookiesChromium.db");
        //mContext.deleteDatabase("webviewCookiesChromiumPrivate.db");

    }

    public static void saveMerchantInfo(Context mContext, MerchantInfo data){
        SharePreferenceUtils.setValue(mContext, SharePreferenceUtils.MERCHANTER_ID, data.getMerchantId().toString());
        SharePreferenceUtils.setValue(mContext, SharePreferenceUtils.MERCHANTER_NO, data.getMerchantNo());
    }

//    public static void getMerchantInFo(Context mContext){
//        AppParam.MerchantId = Integer.parseInt( SharePreferenceUtils.getValue(mContext, SharePreferenceUtils.MERCHANTER_ID, "0") );
//        AppParam.MerchantNo = SharePreferenceUtils.getValue(mContext, SharePreferenceUtils.MERCHANTER_NO, "");
//    }

    public static void clearMerchantInfo(Context mContext){
        SharePreferenceUtils.setValue(mContext, SharePreferenceUtils.MERCHANTER_ID, "");
        SharePreferenceUtils.setValue(mContext, SharePreferenceUtils.MERCHANTER_NO, "");
    }

    /**
     * 清理auth过时数据
     *
     * @param mContext
     */
    public static void clearAuth(Context mContext) {
        SharePreferenceUtils.setValue(mContext, SharePreferenceUtils.IP, "");
        SharePreferenceUtils.setValue(mContext, SharePreferenceUtils.AREA, "");
        SharePreferenceUtils.setValue(mContext, SharePreferenceUtils.COUNTRY, "");
        SharePreferenceUtils.setValue(mContext, SharePreferenceUtils.REGION, "");
        SharePreferenceUtils.setValue(mContext, SharePreferenceUtils.CITY, "");
        SharePreferenceUtils.setValue(mContext, SharePreferenceUtils.ISP, "");
        SharePreferenceUtils.setValue(mContext, SharePreferenceUtils.LONGITUDE, "");
        SharePreferenceUtils.setValue(mContext, SharePreferenceUtils.LATITUDE, "");
        SharePreferenceUtils.setValue(mContext, SharePreferenceUtils.ADDRESS, "");

        // 清空webview的缓存数据
        mContext.deleteDatabase("webview.db");
        mContext.deleteDatabase("webview.db-journal");
        mContext.deleteDatabase("webviewCookiesChromium.db");
        mContext.deleteDatabase("webviewCookiesChromiumPrivate.db");

    }

}
