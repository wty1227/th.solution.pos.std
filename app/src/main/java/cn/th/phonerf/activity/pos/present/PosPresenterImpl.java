package cn.th.phonerf.activity.pos.present;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import cn.th.phonerf.activity.pos.view.IPosView;
import cn.th.phonerf.activity.version.bean.VersionData;
import cn.th.phonerf.activity.version.mode.IVersionMode;
import cn.th.phonerf.activity.version.mode.VersionModeImpl;
import cn.th.phonerf.constant.ApiConstants;
import cn.th.phonerf.constant.AppConst;
import cn.th.phonerf.constant.AppParam;
import cn.th.phonerf.constant.GConn;
import cn.th.phonerf.constant.GSale;
import cn.th.phonerf.dal.SyncServiceDal;
import cn.th.phonerf.http.request.HttpRequestImpl;
import cn.th.phonerf.model.PayResult;
import cn.th.phonerf.model.TBdItemCls;
import cn.th.phonerf.model.TBdItemInfo;
import cn.th.phonerf.model.TRmFlow;
import cn.th.phonerf.model.TRmPayflow;
import cn.th.phonerf.model.TRmPayflowUrea;
import cn.th.phonerf.model.TRmSaleflow;
import cn.th.phonerf.model.TRmSaleman;
import cn.th.phonerf.utils.JsonUtils;
import cn.th.phonerf.utils.logger.Logger;

public class PosPresenterImpl extends HttpRequestImpl implements IPosPresenter {
    String TAG = "";
    private IPosView mView;
    private Handler mHandler;
    //private Double num;
    //private TBdItemInfo Data = null;
    IVersionMode versionMode;

    public PosPresenterImpl(IPosView view){
        super((Context)view);
        this.mView = view;
        mHandler = new Handler(Looper.getMainLooper());
        versionMode = new VersionModeImpl((Context)view);
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
    public void doVersion() {
        String info = "";
        postData(ApiConstants.VERSION_ACTION, info, null);
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
                json.put("branchNo", GSale.StoreNo);
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
//            mView.resGetSalerInfo(data);
        }
    }

    @Override
    public void doGetShoppingBagItem(View view) {
        this.mView.setClickable(view, false);
        this.mView.setProgressDlg(true);

        try {
            JSONObject json = new JSONObject();
            //json.put("posId", posId);
            postData(ApiConstants.GET_SHOPPINGBAG_ITEM, json, view);
        }catch (Exception e){
            Logger.e("发送数据异常");
            mView.setProgressDlg(false);
            mView.setClickable(view, true);
        }
    }


    @Override
    public void doClearCart(View view, String posId) {
        this.mView.setClickable(view, false);
        this.mView.setProgressDlg(true);

        try {
            JSONObject json = new JSONObject();
            json.put("posId", posId);
            postData(ApiConstants.CLEAR_CART, json, view);
        }catch (Exception e){
            Logger.e("发送数据异常");
            mView.setProgressDlg(false);
            mView.setClickable(view, true);
        }
    }

    @Override
    public void doQueryCart(View view, String posId) {
        this.mView.setClickable(view, false);
        this.mView.setProgressDlg(true);

        try {
            JSONObject json = new JSONObject();
            json.put("posId", posId);
            postData(ApiConstants.QUERY_CART, json, view);
        }catch (Exception e){
            Logger.e("发送数据异常");
            mView.setProgressDlg(false);
            mView.setClickable(view, true);
        }
    }


    @Override
    public void doAddCart(View view, String cardId, String barcode, String storeNo, String posId, String cashierNo) {
        if(barcode.isEmpty())
            return;
        this.mView.setClickable(view, false);
        this.mView.setProgressDlg(true);
        try {
            JSONObject json = new JSONObject();
            json.put("barcode", barcode);
            json.put("storeNo", storeNo);
            json.put("cashierNo", cashierNo);
            json.put("posId", posId);
            json.put("cardId", cardId);
            //2200001987650
            /*if(barcode.startsWith("22") && barcode.length()==13){
                json.put("barcode", barcode.substring(2,7));
                num = (Double.parseDouble(barcode.substring(7,12))/100);
            }else {
                json.put("barcode", barcode);
                num = 0.00;
            }*/

            postData(ApiConstants.ADD_TO_CART, json, view);
        }catch (Exception e){
            Logger.e("发送数据异常");
            mView.setProgressDlg(false);
            mView.setClickable(view, true);
        }
    }

    @Override
    public void doUpdateCart(View view, String posId, int comNo, double saleQnty) {
        this.mView.setClickable(view, false);
        this.mView.setProgressDlg(true);

        try {
            JSONObject json = new JSONObject();
            json.put("comNo", comNo);
            json.put("saleQnty", saleQnty);
            json.put("posId", posId);
            postData(ApiConstants.UPDATE_CART, json, view);
        }catch (Exception e){
            Logger.e("发送数据异常");
            mView.setProgressDlg(false);
            mView.setClickable(view, true);
        }
    }

    @Override
    public void doRemoveCart(View view, String posId, int comNo) {
        this.mView.setClickable(view, false);
        this.mView.setProgressDlg(true);

        try {
            JSONObject json = new JSONObject();
            json.put("comNo", comNo);
            json.put("posId", posId);
            postData(ApiConstants.REMOVE_CART, json, view);
        }catch (Exception e){
            Logger.e("发送数据异常");
            mView.setProgressDlg(false);
            mView.setClickable(view, true);
        }
    }

    @Override
    public void doGetVipInfo(View view, String cardId) {
        this.mView.setClickable(view, false);
        this.mView.setProgressDlg(true);

        try {
            JSONObject json = new JSONObject();
            json.put("cardId", cardId);
//            json.put("posId", posId);
            postData(ApiConstants.QUERY_VIP, json, view);
        }catch (Exception e){
            Logger.e("发送数据异常");
            mView.setProgressDlg(false);
            mView.setClickable(view, true);
        }
    }

    @Override
    public void doVipUpdateFlow(View view, String posId, String cardId) {
        this.mView.setClickable(view, false);
        this.mView.setProgressDlg(true);

        try {
            JSONObject json = new JSONObject();
            json.put("cardId", cardId);
            json.put("posId", posId);
            postData(ApiConstants.VIP_UPDATE_FLOW, json, view);
        }catch (Exception e){
            Logger.e("发送数据异常");
            mView.setProgressDlg(false);
            mView.setClickable(view, true);
        }
    }



    @Override
    public void doFindPromote(TBdItemInfo data){
        try {
            JSONObject json = new JSONObject();


            json.put("storeNo",GSale.StoreNo);
            json.put("item_no",data.getItemNo().trim());
            json.put("item_name",data.getItemName());
            json.put("item_clsno",data.getItemClsno().trim());
            json.put("item_brand",data.getItemBrand().trim());
            json.put("sell_way","A");
            json.put("sale_price",data.getSalePrice());
            json.put("sale_qnty",data.getItemQnty());
            json.put("sale_money",(data.getItemQnty()*data.getSalePrice()));
            json.put("source_price",data.getSalePrice());
            json.put("source_money",(data.getItemQnty()*data.getSalePrice()));
            json.put("vipType","");



            postDataA(ApiConstants.HANDLER_PROMOTE, json, null);
        }catch (Exception e){
            Logger.e("发送数据异常");
        }
    }

    @Override
    public void doPreForAllDis(View view, String storeNo, String posId) {
        this.mView.setClickable(view, false);
        this.mView.setProgressDlg(true);

        try {
            JSONObject json = new JSONObject();
            json.put("branchNo", storeNo);
            json.put("posId", posId);
            postData(ApiConstants.PRE_FOR_ALL_DISCOUNT, json, view);
        }catch (Exception e){
            Logger.e("发送数据异常");
            mView.setProgressDlg(false);
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
                    }/*if (action.equals(ApiConstants.HANDLER_PROMOTE)){
                        String code = "";
                        String msg = "";
                        JSONObject data = null;
                        try {
                            code = response.getString("code");
                            msg = response.getString("msg");
                            data = response.getJSONObject("data");


                            if(code.equals("0") && data != null ){
                                if (!data.getString("sale_price").equals("null") || data.getDouble("sale_price") > 0 || data.getDouble("sale_price") < Data.getSalePrice()){
                                    Data.setSalePrice(data.getDouble("sale_price"));
                                }
                                mView.resItemInfo(Data);
                            }else{
                                mView.setToast(msg);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else if (action.equals(ApiConstants.FINDITEMINFO)){
                        String code = "";
                        String msg = "";
                            try {
                            code = response.getString("code");
                            msg = response.getString("msg");
                            Data = (TBdItemInfo) JsonUtils.JsonStrToClass (response.getString("data"), TBdItemInfo.class);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(code.equals(AppConst.API_SUCCESS_CODE) && Data != null){
                            if (num > 0){
                                Data.setItemQnty((num/Data.getSalePrice()));
                                mView.resItemInfo(Data);
                            }else {
                                num = 1.00;
                                Data.setItemQnty(num);
                                //doFindPromote(Data);
                                mView.resItemInfo(Data);
                            }

                        }else{
                            mView.setToast("查询失败或未查询到此商品！");
                        }
                    } else if(action.equals(ApiConstants.ACTION_CONSUMER) || action.equals(ApiConstants.OPERATOR_MODIFYPASS_ACTION)){
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
                    }else if(action.equals(ApiConstants.ACTION_UREA_SELECTLIST)){
                        String code = null;
                        String msg = "";
                        String data = null;
                        JSONArray jsonArray = null;
                        List<TRmPayflowUrea> lists = new ArrayList<>();
                        try {
                            code = response.getString("code");
                            msg = response.getString("msg");
                            data = response.getString("data");
                            jsonArray = JSONArray.parseArray(data);
                            lists = jsonArray.toJavaList(TRmPayflowUrea.class);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if(code.equals(AppConst.API_SUCCESS_CODE) && data != null ){
                            //mView.resGetUreaFlow(lists);
                        }else{
                            mView.setToast(msg);
                        }
                    }else if(action.equals(ApiConstants.ACTION_UREA_SELECTLIST_FINISH)){
                        String code = null;
                        String msg = "";
                        String data = null;
                        JSONArray jsonArray = null;
                        List<TRmPayflowUrea> lists = new ArrayList<>();
                        try {
                            code = response.getString("code");
                            msg = response.getString("msg");
                            data = response.getString("data");
                            jsonArray = JSONArray.parseArray(data);
                            lists = jsonArray.toJavaList(TRmPayflowUrea.class);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if(code.equals(AppConst.API_SUCCESS_CODE) && data != null ){
                            //mView.resGetUreaFlowFinish(lists);
                        }else{
                            mView.setToast(msg);
                        }
                    }else if(action.equals(ApiConstants.ACTION_UREA_SELECTBYID)){
                        String code = null;
                        String msg = "";
                        String data = null;
                        TRmPayflowUrea item = null;
                        try {
                            code = response.getString("code");
                            msg = response.getString("msg");
                            data = response.getString("data");
                            item = com.alibaba.fastjson.JSONObject.parseObject(data, TRmPayflowUrea.class);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if(code.equals(AppConst.API_SUCCESS_CODE) ){
                            //mView.resGetUreaFlowById(item);
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
//                            mView.resGetSalerInfo( data  );
                        }else{
                            mView.setToast(msg);
                        }
                    }else if(action.equals(ApiConstants.ACTION_JXYH_PAY)){
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
                        if(code.equals(AppConst.API_SUCCESS_CODE) && data != null ){
                            //mView.resPayPayUrea( data  );
                        }else if(code.equals("404") ) {
                            mView.resPayOrderError(msg);
                            mView.setPayProgressDlg(false);
                        }else{
                            mView.setToast(msg);
                            mView.setPayProgressDlg(false);
                        }
                    }else if(action.equals(ApiConstants.ACTION_JXYH_QUERY)){
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
                        if(code.equals(AppConst.API_SUCCESS_CODE) && data != null ){
                            //mView.resPayQueryUrea( data  );
                        }else{
                            mView.setToast(msg);
                            mView.setPayProgressDlg(false);
                        }
                    }else if(action.equals(ApiConstants.ACTION_JXYH_CANCEL)){
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
                        if(code.equals(AppConst.API_SUCCESS_CODE) ){
                            //mView.resPayCancelUrea( data  );
                        }else{
                            mView.setToast(msg);
                        }
                    }else if(action.equals(ApiConstants.ACTION_PAY_PAY)){
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
                        if(code.equals(AppConst.API_SUCCESS_CODE) && data != null ){
                            mView.resPayPayFlow( data  );
                        }else if(code.equals("404") ) {
                            mView.resPayOrderError(msg);
                            mView.setPayProgressDlg(false);
                        }else{
                            mView.setToast(msg);
                            mView.setPayProgressDlg(false);
                        }
                    }else if(action.equals(ApiConstants.ACTION_PAY_QUERY)){
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
                        if(code.equals(AppConst.API_SUCCESS_CODE) && data != null ){
                            mView.resPayQueryFlow( data  );
                        }else{
                            mView.setToast(msg);
                            mView.setPayProgressDlg(false);
                        }
                    }else if(action.equals(ApiConstants.ACTION_PAY_CANCEL)){
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
                        if(code.equals(AppConst.API_SUCCESS_CODE) ){
                            mView.resPayCancelFlow( data  );
                        }else{
                            mView.setToast(msg);
                        }
                    }
                    else if(action.equals(ApiConstants.ACTION_UPLOADFLOW)){
                        //ResultEntity resultEntity = (ResultEntity)JsonUtils.JsonObjToClass(response,ResultEntity.class);
                        String code = null;
                        String msg = "";
                        String data = null;
                        try {
                            code = response.getString("code");
                            msg = response.getString("msg");
                            //data = response.getString("data");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(code.equals(AppConst.API_SUCCESS_CODE) ){
                            mView.resUploadFlow(  );
                        }else{
                            mView.setToast(msg);
                        }
                    }else if(action.equals(ApiConstants.ACTION_FLOW_SELECTBYFLOWBRANCH)){
                        //ResultEntity resultEntity = (ResultEntity)JsonUtils.JsonObjToClass(response,ResultEntity.class);
                        String code = null;
                        String msg = "";
                        String data = null;
                        JSONArray jsonArray = null;
                        List<TRmSaleflow> lists = new ArrayList<>();
                        try {
                            code = response.getString("code");
                            msg = response.getString("msg");
                            data = response.getString("data");
                            jsonArray = JSONArray.parseArray(data);
                            lists = jsonArray.toJavaList(TRmSaleflow.class);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if(code.equals(AppConst.API_SUCCESS_CODE) && data != null ){
                            mView.resGetRetFlow( lists, msg  );
                        }else{
                            mView.setToast(msg);
                        }
                    }*/
                }else{
                    mView.setToast(resMsg);
                    mView.setPayProgressDlg(false);
                }
            }
        });
    }
}
