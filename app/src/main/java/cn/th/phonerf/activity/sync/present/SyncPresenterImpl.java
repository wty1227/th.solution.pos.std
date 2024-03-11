package cn.th.phonerf.activity.sync.present;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.alibaba.fastjson.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.th.phonerf.activity.sync.view.ISyncView;
import cn.th.phonerf.constant.ApiConstants;
import cn.th.phonerf.constant.AppConst;
import cn.th.phonerf.constant.AppParam;
import cn.th.phonerf.constant.GSale;
import cn.th.phonerf.dal.SyncServiceDal;
import cn.th.phonerf.http.request.HttpRequestImpl;
import cn.th.phonerf.model.MerchantShop;
import cn.th.phonerf.model.TBdItemCls;
import cn.th.phonerf.utils.logger.Logger;

public class SyncPresenterImpl extends HttpRequestImpl implements ISyncPresenter {
    String TAG = "";
    private ISyncView mView;
    private Handler mHandler;
    public SyncPresenterImpl(ISyncView view){
        super((Context)view);
        this.mView = view;
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    //
    @Override
    public void doGetMaxTodayFlow(View view, String posId) {
        this.mView.setClickable(view, false);
        this.mView.setProgressDlg(true);

        try {
            JSONObject json = new JSONObject();
            json.put("posId", posId);
            //json.put("consum_amt", amt);
            postData(ApiConstants.ACTION_GETMAXTODAYFLOWNO, json, view);
        }catch (Exception e){
            Logger.e("发送数据异常");
            mView.setClickable(view, true);
        }
    }

    //下载商品信息
    @Override
    public void doDownloadItem(View view, Long minFlowID) {
        this.mView.setClickable(view, false);
        this.mView.setProgressDlg(true);

        try {
            JSONObject json = new JSONObject();
            json.put("minFlowID", minFlowID);

            postData(ApiConstants.ACTION_DOWNLOAD_ITEM_INFO, json, view);
        }catch (Exception e){
            Logger.e("发送数据异常");
            mView.setClickable(view, true);
        }
    }

    //下载类别信息
    @Override
    public void doDownloadCls(View view, Long minFlowID) {
        this.mView.setClickable(view, false);
        this.mView.setProgressDlg(true);

        try {
            JSONObject json = new JSONObject();
            json.put("minFlowID", minFlowID);

            postData(ApiConstants.ACTION_ITEMINFO_DOWNLIADITEMCLS, json, view);
        }catch (Exception e){
            Logger.e("发送数据异常");
            mView.setClickable(view, true);
        }
    }

    //下载营业员信息
    @Override
    public void doDownloadSaleman(View view, Long minFlowID) {
        this.mView.setClickable(view, false);
        this.mView.setProgressDlg(true);

        try {
            JSONObject json = new JSONObject();
            json.put("minFlowID", minFlowID);
            json.put("posNO", GSale.PosId);
            json.put("branchNO", GSale.StoreNo);

            postData(ApiConstants.ACTION_ITEMINFO_DOWNLOADSALEMAN, json, view);
        }catch (Exception e){
            Logger.e("发送数据异常");
            mView.setClickable(view, true);
        }
    }

    //下载价格信息
    @Override
    public void doDownloadPrice(View view, Long minFlowID) {
        this.mView.setClickable(view, false);
        this.mView.setProgressDlg(true);

        try {
            JSONObject json = new JSONObject();
            json.put("minFlowID", minFlowID);
            json.put("posNO", GSale.PosId);
            json.put("branchNO", GSale.StoreNo);

            postData(ApiConstants.ACTION_ITEMINFO_DOWNLOADPRICE, json, view);
        }catch (Exception e){
            Logger.e("发送数据异常");
            mView.setClickable(view, true);
        }
    }

    @Override
    public void doDownloadPayment(View view, Long minFlowID) {
        this.mView.setClickable(view, false);
        this.mView.setProgressDlg(true);

        try {
            JSONObject json = new JSONObject();
            json.put("minFlowID", minFlowID);
            json.put("posNO", GSale.PosId);
            json.put("branchNO", GSale.StoreNo);

            postData(ApiConstants.ACTION_ITEMINFO_DOWNLOADPAYMENT, json, view);
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
                    if(action.equals(ApiConstants.ACTION_GETMAXTODAYFLOWNO)){
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
                            mView.resGetMaxTodayFlow(data);
                        }else{
                            mView.setToast(msg);
                            mView.resSyncError(msg);
                        }
                    }else if(action.equals(ApiConstants.ACTION_DOWNLOAD_ITEM_INFO)){
                        String code = null;
                        String msg = "";
                        String data = null;
                        JSONArray jsonArray = null;
                        //List<TBdItemCls> lists = new ArrayList<>();
                        try {
                            code = response.getString("code");
                            msg = response.getString("msg");
                            data = response.getString("data");
                            //data = (JSONObject)JsonUtils.JsonStrToClass (response.getString("data"), JSONObject.class);
                            jsonArray = JSONArray.parseArray(data);
                            //lists = jsonArray.toJavaList(TBdItemCls.class);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(code.equals(AppConst.API_SUCCESS_CODE) && data != null ){
                            try {
                                Long num = Long.valueOf(msg);
                                mView.resGetItemInfo( jsonArray, num  );
                            }catch (Exception e){
                                mView.setToast(e.getMessage());
                                mView.resSyncError(e.getMessage());
                            }

                        }else{
                            mView.setToast(msg);
                            mView.resSyncError(msg);
                        }
                    }else if(action.equals(ApiConstants.ACTION_ITEMINFO_DOWNLIADITEMCLS)){
                        String code = null;
                        String msg = "";
                        String data = null;
                        JSONArray jsonArray = null;
                        //List<TBdItemCls> lists = new ArrayList<>();
                        try {
                            code = response.getString("code");
                            msg = response.getString("msg");
                            data = response.getString("data");
                            //data = (JSONObject)JsonUtils.JsonStrToClass (response.getString("data"), JSONObject.class);
                            jsonArray = JSONArray.parseArray(data);
                            //lists = jsonArray.toJavaList(TBdItemCls.class);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(code.equals(AppConst.API_SUCCESS_CODE) && data != null ){
                            try {
                                Long num = Long.valueOf(msg);
                                mView.resGetItemCls( jsonArray, num  );
                            }catch (Exception e){
                                mView.setToast(e.getMessage());
                                mView.resSyncError(e.getMessage());
                            }

                        }else{
                            mView.setToast(msg);
                            mView.resSyncError(msg);
                        }
                    }else if(action.equals(ApiConstants.ACTION_ITEMINFO_DOWNLOADSALEMAN)){
                        String code = null;
                        String msg = "";
                        String data = null;
                        JSONArray jsonArray = null;
                        //List<TBdItemCls> lists = new ArrayList<>();
                        try {
                            code = response.getString("code");
                            msg = response.getString("msg");
                            data = response.getString("data");
                            //data = (JSONObject)JsonUtils.JsonStrToClass (response.getString("data"), JSONObject.class);
                            jsonArray = JSONArray.parseArray(data);
                            //lists = jsonArray.toJavaList(TBdItemCls.class);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(code.equals(AppConst.API_SUCCESS_CODE) && data != null ){
                            try {

                                Long num = Long.valueOf(msg);
                                mView.resGetSaleman( jsonArray, num  );
                            }catch (Exception e){
                                mView.setToast(e.getMessage());
                                mView.resSyncError(e.getMessage());
                            }

                        }else{
                            mView.setToast(msg);
                            mView.resSyncError(msg);
                        }
                    }else if(action.equals(ApiConstants.ACTION_ITEMINFO_DOWNLOADPRICE)){
                        String code = null;
                        String msg = "";
                        String data = null;
                        JSONArray jsonArray = null;
                        //List<TBdItemCls> lists = new ArrayList<>();
                        try {
                            code = response.getString("code");
                            msg = response.getString("msg");
                            data = response.getString("data");
                            //data = (JSONObject)JsonUtils.JsonStrToClass (response.getString("data"), JSONObject.class);
                            jsonArray = JSONArray.parseArray(data);
                            //lists = jsonArray.toJavaList(TBdItemCls.class);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(code.equals(AppConst.API_SUCCESS_CODE) && data != null ){
                            try {
                                Long num = Long.valueOf(msg);
                                mView.resGetPrice( jsonArray, num  );
                            }catch (Exception e){
                                mView.setToast(e.getMessage());
                                mView.resSyncError(e.getMessage());
                            }

                        }else{
                            mView.setToast(msg);
                            mView.resSyncError(msg);
                        }
                    }else if(action.equals(ApiConstants.ACTION_ITEMINFO_DOWNLOADPAYMENT)){
                        String code = null;
                        String msg = "";
                        String data = null;
                        JSONArray jsonArray = null;
                        //List<TBdItemCls> lists = new ArrayList<>();
                        try {
                            code = response.getString("code");
                            msg = response.getString("msg");
                            data = response.getString("data");
                            //data = (JSONObject)JsonUtils.JsonStrToClass (response.getString("data"), JSONObject.class);
                            jsonArray = JSONArray.parseArray(data);
                            //lists = jsonArray.toJavaList(TBdItemCls.class);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(code.equals(AppConst.API_SUCCESS_CODE) && data != null ){
                            try {
                                Long num = Long.valueOf(msg);
                                mView.resGetPayment( jsonArray, num  );
                            }catch (Exception e){
                                mView.setToast(e.getMessage());
                                mView.resSyncError(e.getMessage());
                            }

                        }else{
                            mView.setToast(msg);
                            mView.resSyncError(msg);
                        }
                    }
                }else{
                    mView.setToast(resMsg);
                    mView.resSyncError(resMsg);
                }
            }
        });
    }

}
