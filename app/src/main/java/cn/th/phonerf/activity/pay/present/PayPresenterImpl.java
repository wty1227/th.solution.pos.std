package cn.th.phonerf.activity.pay.present;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;


import org.json.JSONObject;

import cn.th.phonerf.activity.pay.view.IPayView;
import cn.th.phonerf.constant.ApiConstants;
import cn.th.phonerf.constant.AppConst;
import cn.th.phonerf.http.request.HttpRequestImpl;
import cn.th.phonerf.model.PayBodyVo;
import cn.th.phonerf.utils.JsonUtils;
import cn.th.phonerf.utils.logger.Logger;

public class PayPresenterImpl extends HttpRequestImpl implements IPayPresenter {
    String TAG = "";
    private IPayView mView;
    private Handler mHandler;

    public PayPresenterImpl(IPayView view){
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
        /*this.mView.setPayProgressDlg(true);

        try {
            JSONObject json = new JSONObject();
            //json.put("flow_no", flowNo);
            //json.put("consum_amt", amt);
            postData(ApiConstants.ACTION_CONSUMER, json.toString(), view);
        }catch (Exception e){
            Logger.e("发送数据异常");
            mView.setClickable(view, true);
            this.mView.setPayProgressDlg(false);
        }*/
    }


    /*@Override
    public void doPayPayFlow(View view, String appId, String appKey, String branchNo, String orderNo, String authCode
            , Double trxamt, String body, String remark, String payWay, String idList) {
        this.mView.setClickable(view, false);
        this.mView.setPayProgressDlg(true);

        try {
            JSONObject json = new JSONObject();
            json.put("appId", appId);
            json.put("appKey", appKey);
            json.put("branchNo", branchNo);
            json.put("orderNo", orderNo);
            json.put("authCode", authCode);
            json.put("trxamt", trxamt);
            json.put("body", body);
            json.put("remark", "ytc_pay");
            json.put("payWay", payWay);
            json.put("cashierId",GSale.CashierNo);
            json.put("posid", AppParam.PosId);
            //json.put("cusId","561426053111N0M");
            json.put("idList", idList);

            // recordOperLog(TAG, "P1.4", json.toString());
            postData(ApiConstants.ACTION_PAY_PAY, json.toString(), view);
        }catch (Exception e){
            Logger.e("发送数据异常");
            mView.setClickable(view, true);
            this.mView.setPayProgressDlg(false);
        }
    }*/
    @Override
    public void doPayPayXz(View view, PayBodyVo vo) {
        this.mView.setClickable(view, false);
        this.mView.setPayProgressDlg(true);

        try {
            JSONObject jsonObject = (JSONObject)JsonUtils.toJSONObject(vo);
            postData(ApiConstants.ACTION_PAY_XG, jsonObject, view);
        }catch (Exception e){
            //Logger.e("发送数据异常");
            mView.setClickable(view, true);
            this.mView.showErrorMsg(e.getMessage());
        }
    }

    @Override
    public void doPayPayFlow(View view, PayBodyVo vo) {
        this.mView.setClickable(view, false);
        this.mView.setPayProgressDlg(true);

        try {
            JSONObject jsonObject = (JSONObject)JsonUtils.toJSONObject(vo);
            postData(ApiConstants.ACTION_PAY_PAY, jsonObject, view);
        }catch (Exception e){
            //Logger.e("发送数据异常");
            mView.setClickable(view, true);
            this.mView.showErrorMsg(e.getMessage());
        }
    }

    @Override
    public void doPayQueryFlow(View view, String posId, String orderNo) {
        this.mView.setClickable(view, false);
        this.mView.setPayProgressDlg(true);

        try {
            // PayBodyVo vo = new PayBodyVo();
            // vo.setOrderNo(orderNo);;
            // JSONObject jsonObject = (JSONObject)JsonUtils.toJSONObject(vo);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("posId", posId);
            jsonObject.put("orderNo", orderNo);
            postData(ApiConstants.ACTION_PAY_QUERY, jsonObject, view);
        }catch (Exception e){
            //Logger.e("发送数据异常");
            mView.setClickable(view, true);
            this.mView.showErrorMsg(e.getMessage());
        }
    }

    @Override
    public void doPayCancelFlow(View view, String appId, String appKey, String orderNo, String oldtrxid, String oldreqsn, Double trxamt, String remark) {
        this.mView.setClickable(view, false);
        this.mView.setPayProgressDlg(true);

        try {
            JSONObject json = new JSONObject();
            json.put("appId", appId);
            json.put("appKey", appKey);
            json.put("orderNo", orderNo);
            json.put("oldtrxid", oldtrxid);
            json.put("oldreqsn", oldreqsn);
            json.put("trxamt", trxamt);
            json.put("remark", remark);
            postData(ApiConstants.ACTION_PAY_CANCEL, json.toString(), view);
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

                mView.setClickable(view, true);
                if(isSuccess){
                    if(action.equals(ApiConstants.ACTION_PAY_PAY)){
                        String code = null;
                        String msg = "";
                        try {
                            code = response.getString("code");
                            msg = response.getString("msg");
                        } catch (org.json.JSONException e) {
                            e.printStackTrace();
                        }
                        if(code.equals(AppConst.API_SUCCESS_CODE)) {
                            mView.resRequestData(action, msg, response);
                        }else{
                            mView.showErrorMsg(msg);
                        }
                    } else if(action.equals(ApiConstants.ACTION_PAY_QUERY)){
                        String code = null;
                        String msg = "";
                        String data = "";
                        try {
                            code = response.getString("code");
                            msg = response.getString("msg");
                        } catch (org.json.JSONException e) {
                            e.printStackTrace();
                        }
                        if(code.equals(AppConst.API_SUCCESS_CODE)) {
                            mView.resRequestData(action, msg, response);
                        }else{
                            mView.showErrorMsg(msg);
                        }
                    }else {
                        String code = null;
                        String msg = "";
                        String data = "";
                        try {
                            code = response.getString("code");
                            msg = response.getString("msg");

                        } catch (org.json.JSONException e) {
                            e.printStackTrace();
                        }
                        if(code.equals(AppConst.API_SUCCESS_CODE)){
                            mView.resRequestData( action, msg, response  );
                        }else{
                            mView.showErrorMsg(msg);
                        }
                    }
                }else{
                    mView.showErrorMsg(resMsg);
                }
            }
        });
    }
}
