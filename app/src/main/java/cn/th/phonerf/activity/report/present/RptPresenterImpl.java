package cn.th.phonerf.activity.report.present;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.alibaba.fastjson.JSONArray;

import org.json.JSONObject;

import cn.th.phonerf.activity.main.view.IMainView;
import cn.th.phonerf.activity.report.view.IRptView;
import cn.th.phonerf.constant.ApiConstants;
import cn.th.phonerf.constant.AppConst;
import cn.th.phonerf.http.request.HttpRequestImpl;
import cn.th.phonerf.model.MerchantShop;
import cn.th.phonerf.utils.logger.Logger;

public class RptPresenterImpl extends HttpRequestImpl implements IRptPresenter {
    String TAG = "";
    private IRptView mView;
    private Handler mHandler;

    public RptPresenterImpl(IRptView view){
        super((Context)view);
        this.mView = view;
        mHandler = new Handler(Looper.getMainLooper());
    }
    @Override
    public void onDestroy() {
        mView = null;
    }

    @Override
    public void doGetData(View view, String query, String shopId, String userId, MerchantShop merchantShop) {
        this.mView.setClickable(view, false);
        this.mView.setProgressDlg(true);

        try {
            JSONObject json = new JSONObject();
            json.put("sql", query);
            json.put("shopId", shopId);
            json.put("sqlFlag","DO_GET_DATA");

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
                    if(action.equals(ApiConstants.ACTION_LOCAL_QUERY_SQL) || action.equals(ApiConstants.ACTION_LOCAL_QUERY_SQL_JDBC) || action.equals(ApiConstants.ACTION_LOCAL_QUERY_SQL_JDBC_SYBASE)) {
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
                                case "DO_GET_DATA":
                                    mView.resGetData(jsonArray);break;
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
