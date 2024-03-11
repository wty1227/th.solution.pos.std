package cn.th.phonerf.activity.query.present;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.th.phonerf.activity.query.bean.QueryBean;
import cn.th.phonerf.activity.query.view.IQueryView;
import cn.th.phonerf.constant.ApiConstants;
import cn.th.phonerf.constant.AppConst;
import cn.th.phonerf.http.request.HttpRequestImpl;
import cn.th.phonerf.model.MerchantShop;
import cn.th.phonerf.model.MerchantShopKey;
import cn.th.phonerf.model.PrintDesignMaster;
import cn.th.phonerf.utils.JsonUtils;
import cn.th.phonerf.utils.logger.Logger;

public class QueryPresenterImpl extends HttpRequestImpl implements IQueryPresenter {
    String TAG = "";
    private IQueryView mView;
    private Handler mHandler;

    public QueryPresenterImpl(IQueryView view){
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
    public void doQuery(View view, String query, String shopId, MerchantShop merchantShop) {
        this.mView.setClickable(view, false);
        this.mView.setProgressDlg(true);

        try {

            JSONObject json = new JSONObject();
            json.put("sql", query);
            json.put("shopId", shopId);

            json.put("jdbcDriver",merchantShop.getJdbcDriver());
            json.put("jdbcUrl",merchantShop.getJdbcUrl());
            json.put("jdbcUsername",merchantShop.getJdbcUsername());
            json.put("jdbcPassword",merchantShop.getJdbcPassword());
            //AppParam.
            //postLocalData(ApiConstants.ACTION_LOCAL_QUERY_SQL, JsonUtils.objectToJsonStr(bean), view);
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
    public void doDownLabel(View view, Integer merchantId) {
        if(merchantId == 0){
            this.mView.setToast("请先进入设置界面，设置商户代码");
        }else{
            try {
                this.mView.setClickable(view, false);
                this.mView.setProgressDlg(true);

                JSONObject json = new JSONObject();
                json.put("merchantId", merchantId);

                postData(ApiConstants.ACTION_DOWNLOAD_PRINT_LABEL, json, view);
            }catch (Exception e){
                Logger.e("下载数据异常");
                this.mView.setClickable(view, true);
            }
        }
    }


    @Override
    public void postResult(final boolean isSuccess, final JSONObject response, final String action, final String resMsg, final View view) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mView.setProgressDlg(false);
                mView.setClickable(view, true);
                if(isSuccess){
                    if(action.equals(ApiConstants.ACTION_LOCAL_QUERY_SQL) || action.equals(ApiConstants.ACTION_LOCAL_QUERY_SQL_JDBC) || action.equals(ApiConstants.ACTION_LOCAL_QUERY_SQL_JDBC_SYBASE)){
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
                            mView.resRequestData(jsonArray);
                        }else{
                            mView.setToast(msg);
                        }
                    }else if(action.equals(ApiConstants.ACTION_DOWNLOAD_PRINT_LABEL)){
                        String code = "";
                        String msg = "";
                        String data = "";
                        JSONArray jsonArray = null;
                        List<PrintDesignMaster> list = null;
                        try {
                            code = response.getString("code");
                            msg = response.getString("msg");
                            data = response.getString("data");
                            jsonArray = JSONArray.parseArray(data);


                            Gson gson = new Gson();
                            list = gson.fromJson(data, new TypeToken<List<PrintDesignMaster>>(){}.getType());

                            //List<PrintDesignMaster> list2 = JsonUtils.JsonStrToClass (response.getString("data"), new TypeToken<List<PrintDesignMaster>>(){}.getType() );
                            //data = (PrintDesignMaster)JsonUtils.JsonStrToClass (response.getString("data"), PrintDesignMaster.class);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(code.equals(AppConst.API_SUCCESS_CODE) ){
                            mView.resDownLabel(list);
                        }else{
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
