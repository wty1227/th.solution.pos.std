package cn.th.phonerf.activity.login.present;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.alibaba.fastjson.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.th.phonerf.activity.login.view.ILoginView;
import cn.th.phonerf.activity.version.bean.VersionData;
import cn.th.phonerf.activity.version.mode.IVersionMode;
import cn.th.phonerf.activity.version.mode.VersionModeImpl;
import cn.th.phonerf.constant.ApiConstants;
import cn.th.phonerf.constant.AppConst;
import cn.th.phonerf.http.request.HttpRequestImpl;
import cn.th.phonerf.model.MerchantPhone;
import cn.th.phonerf.model.MerchantShopKey;
import cn.th.phonerf.model.MerchantUserShopKey;
import cn.th.phonerf.model.TSysOperator;
import cn.th.phonerf.model.TSysPosStatus;
import cn.th.phonerf.model.UserGrant;
import cn.th.phonerf.utils.JsonUtils;
import cn.th.phonerf.utils.TextUtil;
import cn.th.phonerf.utils.logger.Logger;

public class LoginPresenterImpl extends HttpRequestImpl implements ILoginPresenter {
    String TAG = "LoginPresenterImpl";
    private ILoginView mView;
    private Handler mHandler;
    IVersionMode versionMode;
    public LoginPresenterImpl(ILoginView view){
        super((Context) view);
        this.mView = view;

        mHandler = new Handler(Looper.getMainLooper());

        versionMode = new VersionModeImpl((Context)view);
    }

    @Override
    public void doVersion() {
        String info = "";
        postData(ApiConstants.VERSION_ACTION, info, null);
    }



    @Override
    public void doGetPosInfo(View view, String phoneSn, String version) {
        if(phoneSn.isEmpty())
            mView.setToast("机器序列号不能为空");
        else{
            try{
                mView.setClickable(view, false);
                mView.setProgressDlg(true);
                JSONObject json = new JSONObject();
                json.put("phoneSn", phoneSn);
                postData(ApiConstants.ACTION_POSINFO_SELECTBYSN, json, view);

            }catch (Exception e){
                Logger.e("登录数据异常");
                mView.setClickable(view, true);
            }
        }
    }

    @Override
    public void doLogin(View view, String loginName, String password, String branchNo) {
        loginName = TextUtil.removeSpace(loginName);
        if(TextUtil.isEmpty(loginName)){
            mView.setToast("账号不能为空");
        }
        else if(TextUtil.isEmpty(password))
            mView.setToast("密码不能为空");
        else if(TextUtil.isEmpty(branchNo))
            mView.setToast("请先设置门店信息");
        else{
            try{
                mView.setClickable(view, false);
                mView.setProgressDlg(true);

                JSONObject json = new JSONObject();
                json.put("userId", loginName);
                json.put("userPass", password);
                json.put("branchNo", branchNo);
                postData(ApiConstants.LOGIN_ACTION, json, view);

            }catch (Exception e){
                Logger.e("登录数据异常");
                mView.setClickable(view, true);
            }
        }
    }

    @Override
    public void doGetGrants(View view, Integer uId, String userId, String shopId, Integer merchantId) {
        if(uId == 0)
            mView.setToast("账号异常");
        else if(merchantId == 0)
            mView.setToast("请先设置商户代码");
        else{
            try{
                mView.setClickable(view, false);
                mView.setProgressDlg(true);

                JSONObject json = new JSONObject();
                json.put("uId", uId);
                json.put("userId", userId);
                json.put("shopId", shopId);
                json.put("merchantId", merchantId);

                postData(ApiConstants.ACTION_MERCHANTUSER_GETGRANTS, json, view);

            }catch (Exception e){
                Logger.e("登录数据异常");
                mView.setClickable(view, true);
            }
        }
    }

    @Override
    public void doCanLoginShop(View view, String userId, String shopId, Integer merchantId) {
        userId = TextUtil.removeSpace(userId);
        if(TextUtil.isEmpty(userId)){
            mView.setToast("账号不能为空");
        }
        else if(TextUtil.isEmpty(shopId))
            mView.setToast("门店不能为空");
        else if(merchantId == 0)
            mView.setToast("请先设置商户代码");
        else{
            try {
                mView.setClickable(view, false);
                mView.setProgressDlg(true);

                MerchantUserShopKey bean = new MerchantUserShopKey();
                bean.setUserId(userId);
                bean.setShopId(shopId);
                bean.setMerchantId(merchantId);

                postData(ApiConstants.ACTION_MERCHANTUSER_CANLOGINSHOP, JsonUtils.objectToJsonStr(bean), view);
            }catch (Exception e){
                Logger.e("登录数据异常");
                mView.setClickable(view, true);
            }
        }
    }

    @Override
    public void doGetPhoneInfo(View view, String phone, String phoneSn, String version, String userId, Integer merchantId) {
        userId = TextUtil.removeSpace(userId);
        if(TextUtil.isEmpty(userId)){
            mView.setToast("账号不能为空");
        }
        else if(TextUtil.isEmpty(version))
            mView.setToast("门店不能为空");
        else if(merchantId == 0)
            mView.setToast("请先设置商户代码");
        else{
            try {
                mView.setClickable(view, false);
                mView.setProgressDlg(true);

                JSONObject json = new JSONObject();
                //json.put("flow_no", flowNo);


                MerchantPhone bean = new MerchantPhone();
                bean.setUserId(userId);
                bean.setPhoneSn(phoneSn);
                bean.setVersion(version);
                bean.setMerchantId(merchantId);
                bean.setPhone(phone);

                postData(ApiConstants.ACTION_MERCHANTPHONE_SELECTBYSN, JsonUtils.objectToJsonStr(bean), view);
            }catch (Exception e){
                Logger.e("获取手机信息数据异常");
                mView.setClickable(view, true);
            }
        }
    }


    @Override
    public void doDownloadShopInfo(View view, Integer merchantId) {
        //merchantId = TextUtil.removeSpace(merchantNo);
        if(merchantId == 0){
            mView.setToast("请先进入设置界面，设置商户代码");
        }else{
            try {
                mView.setClickable(view, false);
                mView.setProgressDlg(true);

                MerchantShopKey bean = new MerchantShopKey();
                bean.setMerchantId(merchantId);

                postData(ApiConstants.ACTION_DOWNLOAD_MERCHANT_LIST, JsonUtils.objectToJsonStr(bean), view);
            }catch (Exception e){
                Logger.e("下载数据异常");
                mView.setClickable(view, true);
            }
        }
    }

    @Override
    public void doDownloadSysSystem(View view, Integer merchantId) {
        if(merchantId == 0){
            mView.setToast("请先进入设置界面，设置商户代码");
        }else{
            try {
                mView.setClickable(view, false);
                mView.setProgressDlg(true);

                MerchantShopKey bean = new MerchantShopKey();
                bean.setMerchantId(merchantId);

                postData(ApiConstants.ACTION_DOWNLOAD_SYS_SYSTEM, JsonUtils.objectToJsonStr(bean), view);
            }catch (Exception e){
                Logger.e("下载数据异常");
                mView.setClickable(view, true);
            }
        }
    }

    @Override
    public void postResult(final boolean isSuccess, final JSONObject response, final String action, final String msg, final View view) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mView.setProgressDlg(false);
                mView.setClickable(view, true);
                if(isSuccess){
                    /*if(action.equals(ApiConstants.LOGIN_ACTION)){
                        //ResultEntity resultEntity = (ResultEntity)JsonUtils.JsonObjToClass(response,ResultEntity.class);
                        String code = null;
                        String msg = "";
                        TSysOperator data = null;
                        try {
                            code = response.getString("code");
                            msg = response.getString("msg");
                            //data = response.getString("data");
                            data = (TSysOperator)JsonUtils.JsonStrToClass (response.getString("data"), TSysOperator.class);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(code.equals(AppConst.API_SUCCESS_CODE) && data != null ){
                            mView.resGetLoginInfo( data  );
                        }else{
                            mView.setToast(msg);
                        }
                    }else if(action.equals(ApiConstants.ACTION_POSINFO_SELECTBYSN)){
                        String code = null;
                        String msg = "";
                        TSysPosStatus data = null;
                        try {
                            code = response.getString("code");
                            msg = response.getString("msg");
                            //data = response.getString("data");
                            data = (TSysPosStatus)JsonUtils.JsonStrToClass (response.getString("data"), TSysPosStatus.class);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(code.equals(AppConst.API_SUCCESS_CODE) && data != null ){
                            mView.resGetPosInfo( data  );
                        }else{
                            mView.setToast(msg);
                        }
                    }


                    else if(action.equals(ApiConstants.ACTION_DOWNLOAD_MERCHANT_LIST)){
                        String code = null;
                        String msg = "";
                        String data = null;
                        JSONArray jsonArray = null;
                        try {
                            code = response.getString("code");
                            msg = response.getString("msg");
                            data = response.getString("data");
                            //data = (JSONObject)JsonUtils.JsonStrToClass (response.getString("data"), JSONObject.class);
                            jsonArray = JSONArray.parseArray(data);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(code.equals(AppConst.API_SUCCESS_CODE) && data != null ){
                            mView.saveShopData( jsonArray  );
                        }else{
                            mView.setToast(msg);
                        }
                    }else if(action.equals(ApiConstants.ACTION_DOWNLOAD_SYS_SYSTEM)){
                        String code = null;
                        String msg = "";
                        String data = null;
                        JSONArray jsonArray = null;
                        try {
                            code = response.getString("code");
                            msg = response.getString("msg");
                            data = response.getString("data");
                            //data = (JSONObject)JsonUtils.JsonStrToClass (response.getString("data"), JSONObject.class);
                            jsonArray = JSONArray.parseArray(data);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(code.equals(AppConst.API_SUCCESS_CODE) && data != null ){
                            mView.saveSysSystem( jsonArray  );
                        }else{
                            mView.setToast(msg);
                        }
                    }else if(action.equals(ApiConstants.ACTION_MERCHANTUSER_GETGRANTS)){
                        String code = null;
                        String msg = "";
                        String data = null;
                        JSONArray jsonArray = null;
                        List<UserGrant> lists = new ArrayList<>();
                        try {
                            code = response.getString("code");
                            msg = response.getString("msg");
                            data = response.getString("data");
                            //data = (JSONObject)JsonUtils.JsonStrToClass (response.getString("data"), JSONObject.class);
                            jsonArray = JSONArray.parseArray(data);

                            //JSONArray jsonArray = this._info.getJSONArray("dataSouce");
                            lists = jsonArray.toJavaList(UserGrant.class);


                            //JSONArray jsonArray = this._info.getJSONArray("dataSouce");
                            //lists = (List<UserGrant>) jsonArray.toJavaObject(UserGrant.class);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(code.equals(AppConst.API_SUCCESS_CODE) && data != null ){
                            mView.resGetGrants( lists  );
                        }else{
                            mView.setToast(msg);
                        }
                    }else if(action.equals(ApiConstants.ACTION_MERCHANTUSER_CANLOGINSHOP)){
                        String code = null;
                        String msg = "";
                        String data = null;
                        try {
                            code = response.getString("code");
                            msg = response.getString("msg");
                            data = response.getString("data");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(code.equals(AppConst.API_SUCCESS_CODE) ){
                            mView.successLogin();
                        }else{
                            mView.setToast(msg);
                        }
                    }else if(action.equals(ApiConstants.ACTION_MERCHANTPHONE_SELECTBYSN)){
                        //ResultEntity resultEntity = (ResultEntity)JsonUtils.JsonObjToClass(response,ResultEntity.class);
                        String code = null;
                        String msg = "";
                        MerchantPhone data = null;
                        try {
                            code = response.getString("code");
                            msg = response.getString("msg");
                            //data = response.getString("data");
                            data = (MerchantPhone)JsonUtils.JsonStrToClass (response.getString("data"), MerchantPhone.class);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(code.equals(AppConst.API_SUCCESS_CODE) && data != null ){
                            mView.resGetPhoneInfo( data  );
                        }else{
                            mView.setToast(msg);
                        }
                    }

                    else
                        //banner数据
                    /*if (action.equals(ApiConstants.BANNER_ACTION)){
                        BannerBean bean = (BannerBean) JsonUtils.JsonObjToClass(response, BannerBean.class);
                        if (bean.getError().equals(AppConst.API_SUCCESS_CODE)){
                            indexView.showBannerList(bean.getData());
                        }
                    }*/

                    //版本更新
                    if (action.equals(ApiConstants.VERSION_ACTION)){
                        String code = null;
                        String msg = "";
                        String data = null;
                        VersionData bean = null;
                        try {
                            code = response.getString("code");
                            msg = response.getString("msg");
                            data = response.getString("data");
                            bean = (VersionData) JsonUtils.JsonStrToClass(data, VersionData.class);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(code.equals(AppConst.API_SUCCESS_CODE) && data != null){
                            versionMode.compareCode(bean);
                        }else{
//                            _view.setToast(msg);
                        }
                    }else if(true){
                        String code = null;
                        String msg = "";
                        String data = "";
                        try {
                            code = response.getString("code");
                            msg = response.getString("msg");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(code.equals(AppConst.API_SUCCESS_CODE)){
                            mView.resRequestData( action, msg, response  );
                        }else{
                            mView.setToast(msg);
                        }
                    }
                }else{
                    mView.setToast(msg);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

}
