//package cn.th.phonerf.service.present;
//
//import android.content.Context;
//import android.os.Handler;
//import android.os.Looper;
//import android.util.Log;
//import android.view.View;
//
//import com.alibaba.fastjson.JSONArray;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import cn.th.phonerf.activity.pos.present.IPosPresenter;
//import cn.th.phonerf.activity.pos.view.IPosView;
//import cn.th.phonerf.constant.ApiConstants;
//import cn.th.phonerf.constant.AppConst;
//import cn.th.phonerf.dal.SyncServiceDal;
//import cn.th.phonerf.http.request.HttpRequestImpl;
//import cn.th.phonerf.model.PayResult;
//import cn.th.phonerf.model.TBdItemCls;
//import cn.th.phonerf.model.TBdItemInfo;
//import cn.th.phonerf.model.TRmFlow;
//import cn.th.phonerf.model.TRmPayflow;
//import cn.th.phonerf.model.TRmSaleflow;
//import cn.th.phonerf.model.TRmSaleman;
//import cn.th.phonerf.utils.JsonUtils;
//
//public class FlowServicelmp extends HttpRequestImpl implements IPosPresenter {
//
//    public FlowServicelmp(Context context){
//        super(context);
//
////        this.mView = view;
////        mHandler = new Handler(Looper.getMainLooper());
//    }
//
//    @Override
//    public void doRequest(View view) {
//
//    }
//
//    @Override
//    public void doGetSalerInfo(View view, String saleId) {
//
//    }
//
//    @Override
//    public void doClearCart(View view, String posId) {
//
//    }
//
//    @Override
//    public void doAddCart(View view, String barcode, String storeNo, String posId, String cashierNo) {
//
//    }
//
//
//
//
//    @Override
//    public void onDestroy() {
//
//    }
//
//    @Override
//    public void doFindPromote(TBdItemInfo data){
//
//    }
//
//    @Override
//    public void postResult(boolean isSuccess, JSONObject response, String action, String resMsg, View view) {
//        if(isSuccess){
//            //上传流水信息
//            if(action.equals(ApiConstants.ACTION_UPLOADFLOW)){
//                //ResultEntity resultEntity = (ResultEntity)JsonUtils.JsonObjToClass(response,ResultEntity.class);
//                String code = null;
//                String msg = "";
//                String data = null;
//                try {
//                    code = response.getString("code");
//                    msg = response.getString("msg");
//                    data = response.getString("data");
//                    SyncServiceDal dal = new SyncServiceDal();
//                    if (code.equals("500") || code.equals("1")){
//                        dal.updateFlowNo("0",data);
//                    }else if(code.equals("501")){
//                        dal.updateExceptFlag("1", data);
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//    }
//}
