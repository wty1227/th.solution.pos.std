package cn.th.phonerf.activity.exceptflow.present;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import cn.th.phonerf.activity.exceptflow.present.IExceptFlowPresenter;
import cn.th.phonerf.activity.exceptflow.view.IExceptFlowView;
import cn.th.phonerf.constant.ApiConstants;
import cn.th.phonerf.constant.AppConst;
import cn.th.phonerf.constant.GConn;
import cn.th.phonerf.dal.SyncServiceDal;
import cn.th.phonerf.http.request.HttpRequestImpl;
import cn.th.phonerf.model.PayResult;
import cn.th.phonerf.model.TRmSaleman;
import cn.th.phonerf.utils.JsonUtils;
import cn.th.phonerf.utils.logger.Logger;

public class ExceptFlowPresenterImpl extends HttpRequestImpl implements IExceptFlowPresenter {
    String TAG = "";
    private IExceptFlowView mView;
    private Handler mHandler;

    public ExceptFlowPresenterImpl(IExceptFlowView view){
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
    public void doPayQueryPhoneOrderNo(View view, String appId, String appKey, String orderNo) {
        this.mView.setClickable(view, false);
        this.mView.setProgressDlg(true);

        try {
            JSONObject json = new JSONObject();
            json.put("appId", appId);
            json.put("appKey", appKey);
            json.put("orderNo", orderNo);
            postData(ApiConstants.ACTION_JXYH_QUERYBYPHONEORDERNO, json, view);
        }catch (Exception e){
            Logger.e("发送数据异常");
            mView.setClickable(view, true);
        }
    }


    /**
     * 操作员
     * @param view
     * @param saleId
     */
    @Override
    public void doGetSalerInfo(View view, String saleId) {
        this.mView.setClickable(view, false);
        this.mView.setProgressDlg(true);
        if(GConn.IsConnectServer()) {
            try {
                JSONObject json = new JSONObject();
                json.put("saleId", saleId);
                postData(ApiConstants.ACTION_SALERINFO_SELECTBYID, json, view);
            }catch (Exception e){
                Logger.e("发送数据异常");
                mView.setClickable(view, true);
            }
        }else {
            SyncServiceDal dal = new SyncServiceDal();
            TRmSaleman data = dal.selectBigSaleman(saleId);
            mView.setProgressDlg(false);
            mView.setClickable(view, true);
            mView.resGetSalerInfo(data);
        }
    }

    //查询订单号
    @Override
    public void doGetFlow(View view, String flowNo, String branchNo) {
        this.mView.setClickable(view, false);
        this.mView.setProgressDlg(true);
        try {
            JSONObject json = new JSONObject();
            json.put("flowNo", flowNo);
            json.put("branchNo", branchNo);
            postData(ApiConstants.ACTION_FLOW_ISNOTEXISTS, json, view);
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
                if(isSuccess){
                    if(action.equals(ApiConstants.ACTION_CONSUMER)){
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
                        if(code.equals(AppConst.API_SUCCESS_CODE) ){
                            mView.resRequestData();
                        }else{
                            mView.setToast(msg);
                        }
                    }else if(action.equals(ApiConstants.ACTION_JXYH_QUERYBYPHONEORDERNO)){
                        //ResultEntity resultEntity = (ResultEntity)JsonUtils.JsonObjToClass(response,ResultEntity.class);
                        String code = null;
                        String msg = "";
                        PayResult data = null;
                        try {
                            code = response.getString("code");
                            msg = response.getString("msg");
                            //data = response.getString("data");
                            data = (PayResult) JsonUtils.JsonStrToClass (response.getString("data"), PayResult.class);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(code.equals(AppConst.API_SUCCESS_CODE) && data != null){
                            mView.resPayQuery( data  );
                        }else{
                            mView.setToast(msg);
                        }
                    }else if(action.equals(ApiConstants.ACTION_SALERINFO_SELECTBYID)){
                        //ResultEntity resultEntity = (ResultEntity)JsonUtils.JsonObjToClass(response,ResultEntity.class);
                        String code = null;
                        String msg = "";
                        TRmSaleman data = null;
                        try {
                            code = response.getString("code");
                            msg = response.getString("msg");
                            //data = response.getString("data");
                            data = (TRmSaleman) JsonUtils.JsonStrToClass (response.getString("data"), TRmSaleman.class);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(code.equals(AppConst.API_SUCCESS_CODE) && data != null ){
                            mView.resGetSalerInfo( data  );
                        }else{
                            mView.setToast(msg);
                        }
                    }else if(action.equals(ApiConstants.ACTION_FLOW_ISNOTEXISTS)){
                        //ResultEntity resultEntity = (ResultEntity)JsonUtils.JsonObjToClass(response,ResultEntity.class);
                        String code = null;
                        String msg = "";
                        PayResult data = null;
                        try {
                            code = response.getString("code");
                            msg = response.getString("msg");
                            //data = response.getString("data");
                            data = (PayResult) JsonUtils.JsonStrToClass (response.getString("data"), PayResult.class);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(code.equals(AppConst.API_SUCCESS_CODE)){
                            mView.resGetFlow(code,msg);
                        }else{
                            mView.resGetFlow(code,msg);
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
