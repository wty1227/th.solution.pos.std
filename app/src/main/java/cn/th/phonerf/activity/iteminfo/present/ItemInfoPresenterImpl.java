package cn.th.phonerf.activity.iteminfo.present;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.alibaba.fastjson.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.th.phonerf.activity.example.present.IExamplePresenter;
import cn.th.phonerf.activity.example.view.IExampleView;
import cn.th.phonerf.activity.iteminfo.view.IItemInfoView;
import cn.th.phonerf.constant.ApiConstants;
import cn.th.phonerf.constant.AppConst;
import cn.th.phonerf.constant.AppParam;
import cn.th.phonerf.constant.GConn;
import cn.th.phonerf.constant.GSale;
import cn.th.phonerf.dal.SyncServiceDal;
import cn.th.phonerf.http.request.HttpRequestImpl;
import cn.th.phonerf.model.TBdItemCls;
import cn.th.phonerf.model.TBdItemInfo;
import cn.th.phonerf.utils.logger.Logger;

public class ItemInfoPresenterImpl extends HttpRequestImpl implements IItemInfoPresenter {
    String TAG = "";
    private IItemInfoView mView;
    private Handler mHandler;

    public ItemInfoPresenterImpl(Activity activity, IItemInfoView view){
        super(activity);
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


    //查询大类
    @Override
    public void doGetBigCls(View view) {
        this.mView.setClickable(view, false);
        //this.mView.setProgressDlg(true);
        if(GConn.IsConnectServer()) {
            try {
                JSONObject json = new JSONObject();
                postData(ApiConstants.ACTION_ITEMINFO_SELECTBIGCLS, json, view);
            }catch (Exception e){
                Logger.e("发送数据异常");
                mView.setClickable(view, true);
            }
        }
        else {
            SyncServiceDal dal = new SyncServiceDal();
            List lists = dal.selectBigCls();
            //mView.setProgressDlg(false);
            mView.setClickable(view, true);
            mView.resGetBigCls(lists);
        }
    }


    //根据大类item_clsno查询小类
    @Override
    public void doGetSmallCls(View view, String itemClsno) {
        this.mView.setClickable(view, false);
        //this.mView.setProgressDlg(true);
        if(GConn.IsConnectServer()) {
            try {
                JSONObject json = new JSONObject();
                json.put("itemClsno", itemClsno);
                //json.put("consum_amt", amt);
                postData(ApiConstants.ACTION_ITEMINFO_SELECTSMALLCLS, json, view);
            }catch (Exception e){
                Logger.e("发送数据异常");
                mView.setClickable(view, true);
            }
        }
        else {
            SyncServiceDal dal = new SyncServiceDal();
            List lists = dal.selectSmallCls(itemClsno.replace(" ", "") + "%");
            //mView.setProgressDlg(false);
            mView.setClickable(view, true);
            mView.resGetSmallCls(lists);
        }
    }


    //根据小类查商品
    @Override
    public void doGetItemList(View view, String itemClsno) {
        this.mView.setClickable(view, false);
        //this.mView.setProgressDlg(true);
        if(GConn.IsConnectServer()) {
            try {
                JSONObject json = new JSONObject();
                json.put("branchNo", GSale.StoreNo);
                json.put("itemClsno", itemClsno);
                //json.put("consum_amt", amt);
                postData(ApiConstants.ACTION_ITEMINFO_SELECTBYITEMCLSNO, json, view);
            }catch (Exception e){
                Logger.e("发送数据异常");
                mView.setClickable(view, true);
            }
        }
        else {
            SyncServiceDal dal = new SyncServiceDal();
            List lists = dal.selectByItemClsno(itemClsno, GSale.StoreNo);
            //mView.setProgressDlg(false);
            mView.setClickable(view, true);
            mView.resGetItemList(lists);
        }
    }


    @Override
    public void postResult(final boolean isSuccess, final JSONObject response, final String action, final String resMsg, final View view) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                //mView.setProgressDlg(false);
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
                    }else if(action.equals(ApiConstants.ACTION_ITEMINFO_SELECTBIGCLS)){
                        String code = null;
                        String msg = "";
                        String data = null;
                        JSONArray jsonArray = null;
                        List<TBdItemCls> lists = new ArrayList<>();
                        try {
                            code = response.getString("code");
                            msg = response.getString("msg");
                            data = response.getString("data");
                            //data = (JSONObject)JsonUtils.JsonStrToClass (response.getString("data"), JSONObject.class);
                            jsonArray = JSONArray.parseArray(data);
                            lists = jsonArray.toJavaList(TBdItemCls.class);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(code.equals(AppConst.API_SUCCESS_CODE) && data != null ){
                            mView.resGetBigCls(lists);
                        }else{
                            mView.setToast(msg);
                        }
                    }else if(action.equals(ApiConstants.ACTION_ITEMINFO_SELECTSMALLCLS)){
                        String code = null;
                        String msg = "";
                        String data = null;
                        JSONArray jsonArray = null;
                        List<TBdItemCls> lists = new ArrayList<>();
                        try {
                            code = response.getString("code");
                            msg = response.getString("msg");
                            data = response.getString("data");
                            //data = (JSONObject)JsonUtils.JsonStrToClass (response.getString("data"), JSONObject.class);
                            jsonArray = JSONArray.parseArray(data);
                            lists = jsonArray.toJavaList(TBdItemCls.class);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(code.equals(AppConst.API_SUCCESS_CODE) && data != null ){
                            mView.resGetSmallCls( lists  );
                        }else{
                            mView.setToast(msg);
                        }
                    }else if(action.equals(ApiConstants.ACTION_ITEMINFO_SELECTBYITEMCLSNO)){
                        String code = null;
                        String msg = "";
                        String data = null;
                        JSONArray jsonArray = null;
                        List<TBdItemInfo> lists = new ArrayList<>();
                        try {
                            code = response.getString("code");
                            msg = response.getString("msg");
                            data = response.getString("data");
                            //data = (JSONObject)JsonUtils.JsonStrToClass (response.getString("data"), JSONObject.class);
                            jsonArray = JSONArray.parseArray(data);
                            lists = jsonArray.toJavaList(TBdItemInfo.class);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(code.equals(AppConst.API_SUCCESS_CODE) && data != null ){
                            mView.resGetItemList( lists  );
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
