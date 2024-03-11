package cn.th.phonerf.activity.main.present;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.alibaba.fastjson.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

import cn.th.phonerf.activity.example.view.IExampleView;
import cn.th.phonerf.activity.main.view.IMainView;
import cn.th.phonerf.activity.query.bean.QueryBean;
import cn.th.phonerf.constant.ApiConstants;
import cn.th.phonerf.constant.AppConst;
import cn.th.phonerf.http.request.HttpRequestImpl;
import cn.th.phonerf.model.MerchantShop;
import cn.th.phonerf.model.MerchantUser;
import cn.th.phonerf.utils.JsonUtils;
import cn.th.phonerf.utils.logger.Logger;

public class MainPresenterImpl extends HttpRequestImpl implements IMainPresenter {
    String TAG = "";
    private IMainView mView;
    private Handler mHandler;

    public MainPresenterImpl(IMainView view){
        super((Context)view);
        this.mView = view;
        mHandler = new Handler(Looper.getMainLooper());
    }
    @Override
    public void onDestroy() {
        mView = null;
    }

    @Override
    public void doRequest(View view) {
        this.mView.setClickable(view, false);
        this.mView.setProgressDlg(true);

        try {
            JSONObject json = new JSONObject();
            //json.put("flow_no", flowNo);
            //json.put("consum_amt", amt);
            postData(ApiConstants.ACTION_CONSUMER, json, view);
        }catch (Exception e){
            Logger.e("发送数据异常");
            mView.setClickable(view, true);
        }
    }

    @Override
    public void doModifyPass(View view, String userPassOld, String userPassNew, String userId, Integer merchantId) {
        this.mView.setClickable(view, false);
        this.mView.setProgressDlg(true);

        try {

            JSONObject json = new JSONObject();
            json.put("userId", userId);
            json.put("userPassOld", userPassOld);
            json.put("userPassNew",userPassNew);

            json.put("merchantId",merchantId);
            postData(ApiConstants.ACTION_MERCHANTUSER_MODIFYPASS, json, view);
        }catch (Exception e){
            Logger.e("发送数据异常");
            mView.setClickable(view, true);
        }
    }

    /*
    @Override
    public void doGetItemCount(View view, String query, String shopId, String userId) {
        this.mView.setClickable(view, false);
        this.mView.setProgressDlg(true);

        try {
            QueryBean bean = new QueryBean();
            bean.setSql(query);
            bean.setShopId(shopId);
            //AppParam.
            postLocalData(ApiConstants.ACTION_LOCAL_ITEM_COUNT_SQL, JsonUtils.objectToJsonStr(bean), view);
        }catch (Exception e){
            Logger.e("发送数据异常");
            mView.setClickable(view, true);
        }
    }*/

    @Override
    public void doGetDownloadCount(View view, String query, String shopId, String userId, MerchantShop merchantShop) {
        this.mView.setClickable(view, false);
        this.mView.setProgressDlg(true);

        try {

            JSONObject json = new JSONObject();
            json.put("sql", query);
            json.put("shopId", shopId);
            json.put("sqlFlag","DOWN_COUNT_SQL");

            json.put("jdbcDriver",merchantShop.getJdbcDriver());
            json.put("jdbcUrl",merchantShop.getJdbcUrl());
            json.put("jdbcUsername",merchantShop.getJdbcUsername());
            json.put("jdbcPassword",merchantShop.getJdbcPassword());
            if(merchantShop.getDeployFlag().equals("0"))
                postLocalData(ApiConstants.ACTION_LOCAL_QUERY_SINGLE, json.toString(), view);
            else if(merchantShop.getJdbcDriver().equals("com.sybase.jdbc3.jdbc.SybDriver"))
                postLocalData(ApiConstants.ACTION_LOCAL_QUERY_SINGLE_JDBC_SYBASE, json.toString(), view);
            else
                postLocalData(ApiConstants.ACTION_LOCAL_QUERY_SINGLE_JDBC, json.toString(), view);
        }catch (Exception e){
            Logger.e("发送数据异常");
            mView.setClickable(view, true);
        }
    }

    @Override
    public void doDownloadItem(View view, String query, String shopId, String userId, MerchantShop merchantShop) {
        this.mView.setClickable(view, false);
        this.mView.setProgressDlg(true);

        try {
            JSONObject json = new JSONObject();
            json.put("sql", query);
            json.put("shopId", shopId);
            json.put("sqlFlag","DOWN_ITEM_SQL");

            json.put("jdbcDriver",merchantShop.getJdbcDriver());
            json.put("jdbcUrl",merchantShop.getJdbcUrl());
            json.put("jdbcUsername",merchantShop.getJdbcUsername());
            json.put("jdbcPassword",merchantShop.getJdbcPassword());
            if(merchantShop.getDeployFlag().equals("0"))
                postLocalData(ApiConstants.ACTION_LOCAL_QUERY_SQL, json.toString(), view);
            else if(merchantShop.getJdbcDriver().equals("com.sybase.jdbc3.jdbc.SybDriver"))
                postLocalData(ApiConstants.ACTION_LOCAL_QUERY_SQL_JDBC_SYBASE, json.toString(), view);
            else
                postLocalData(ApiConstants.ACTION_LOCAL_QUERY_SQL_JDBC, json.toString(), view);
        }catch (Exception e){
            Logger.e("发送数据异常");
            mView.setClickable(view, true);
        }
    }

    @Override
    public void doDownloadBarcode(View view, String query, String shopId, String userId, MerchantShop merchantShop) {
        this.mView.setClickable(view, false);
        this.mView.setProgressDlg(true);

        try {
            JSONObject json = new JSONObject();
            json.put("sql", query);
            json.put("shopId", shopId);
            json.put("sqlFlag","DOWN_BARCODE_SQL");

            json.put("jdbcDriver",merchantShop.getJdbcDriver());
            json.put("jdbcUrl",merchantShop.getJdbcUrl());
            json.put("jdbcUsername",merchantShop.getJdbcUsername());
            json.put("jdbcPassword",merchantShop.getJdbcPassword());
            if(merchantShop.getDeployFlag().equals("0"))
                postLocalData(ApiConstants.ACTION_LOCAL_QUERY_SQL, json.toString(), view);
            else if(merchantShop.getJdbcDriver().equals("com.sybase.jdbc3.jdbc.SybDriver"))
                postLocalData(ApiConstants.ACTION_LOCAL_QUERY_SQL_JDBC_SYBASE, json.toString(), view);
            else
                postLocalData(ApiConstants.ACTION_LOCAL_QUERY_SQL_JDBC, json.toString(), view);
        }catch (Exception e){
            Logger.e("发送数据异常");
            mView.setClickable(view, true);
        }
    }


    @Override
    public void postResult(final boolean isSuccess, final JSONObject response, final String action, final String resMsg, final View view) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mView.setProgressDlg(false);
                mView.setClickable(view, true);
                if(isSuccess){//
                    if(action.equals(ApiConstants.ACTION_MERCHANTUSER_MODIFYPASS)){

                        String code = null;
                        String msg = "";
                        String data = null;
                        try {
                            code = response.getString("code");
                            msg = response.getString("msg");
                            data = response.getString("data");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if(code.equals(AppConst.API_SUCCESS_CODE)){
                            mView.resRequestData();
                        }else{
                            mView.setToast(msg);
                        }
                    }else if(action.equals(ApiConstants.ACTION_LOCAL_QUERY_SQL) || action.equals(ApiConstants.ACTION_LOCAL_QUERY_SQL_JDBC) || action.equals(ApiConstants.ACTION_LOCAL_QUERY_SQL_JDBC_SYBASE)) {
                        String code = "";
                        String msg = "";
                        String data = "";
                        JSONArray jsonArray = null;
                        try {
                            code = response.getString("code");
                            msg = response.getString("msg");
                            data = response.getString("data");
                            jsonArray = JSONArray.parseArray(data);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (code.equals(AppConst.API_SUCCESS_CODE)) {
                            switch(msg){
                                case "DOWN_COUNT_SQL":
                                    mView.resGetDownloadCount(data);break;
                                case "DOWN_ITEM_SQL":
                                    mView.resDownloadItem(jsonArray); break;
                                case "DOWN_BARCODE_SQL":
                                    mView.resDownloadBarcode(jsonArray); break;
                                default:
                                    mView.resRequestData();
                                    break;
                            }
                        } else {
                            mView.setToast(msg);
                        }
                    }else if(action.equals(ApiConstants.ACTION_LOCAL_QUERY_SINGLE) || action.equals(ApiConstants.ACTION_LOCAL_QUERY_SINGLE_JDBC) || action.equals(ApiConstants.ACTION_LOCAL_QUERY_SINGLE_JDBC_SYBASE)) {
                        String code = "";
                        String msg = "";
                        String data = "";
                        JSONArray jsonArray = null;
                        try {
                            code = response.getString("code");
                            msg = response.getString("msg");
                            data = response.getString("data");
                            jsonArray = JSONArray.parseArray(data);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (code.equals(AppConst.API_SUCCESS_CODE)) {
                            switch(msg){
                                case "DOWN_COUNT_SQL":
                                    mView.resGetDownloadCount(data);break;
                                case "DOWN_ITEM_SQL":
                                    mView.resDownloadItem(jsonArray); break;
                                case "DOWN_BARCODE_SQL":
                                    mView.resDownloadBarcode(jsonArray); break;
                                default:
                                    mView.resRequestData();
                                    break;
                            }
                        } else {
                            mView.setToast(msg);
                        }
                    }
                }else{
                    mView.setToast(resMsg);
                }
            }
        });
    }
}
/*
else if(action.equals(ApiConstants.ACTION_LOCAL_DOWN_COUNT_SQL)){
                        String code = "";
                        String msg = "";
                        String data = "";
                        try {
                            code = response.getString("code");
                            msg = response.getString("msg");
                            data = response.getString("data");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (code.equals(AppConst.API_SUCCESS_CODE)) {
                            mView.resGetDownloadCount(data);
                        } else {
                            mView.setToast(msg);
                        }
                    }else if(action.equals(ApiConstants.ACTION_LOCAL_DOWN_ITEM_SQL)){
                        String code = "";
                        String msg = "";
                        String data = "";
                        JSONArray jsonArray = null;
                        try {
                            code = response.getString("code");
                            msg = response.getString("msg");
                            data = response.getString("data");
                            jsonArray = JSONArray.parseArray(data);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(code.equals(AppConst.API_SUCCESS_CODE) ){
                            mView.resDownloadItem(jsonArray);
                        }else{
                            mView.setToast(msg);
                        }
                    }else if(action.equals(ApiConstants.ACTION_LOCAL_DOWN_BARCODE_SQL)){
                        String code = "";
                        String msg = "";
                        String data = "";
                        JSONArray jsonArray = null;
                        try {
                            code = response.getString("code");
                            msg = response.getString("msg");
                            data = response.getString("data");
                            jsonArray = JSONArray.parseArray(data);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(code.equals(AppConst.API_SUCCESS_CODE) ){
                            mView.resDownloadBarcode(jsonArray);
                        }else{
                            mView.setToast(msg);
                        }
                    }
 */