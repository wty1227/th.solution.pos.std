//package cn.th.phonerf.service;
//
//import android.app.Notification;
//import android.app.Service;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//import android.content.ServiceConnection;
//import android.os.Handler;
//import android.os.IBinder;
//import android.os.Looper;
//import android.support.annotation.Nullable;
//import android.util.Log;
//import android.view.View;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.List;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import cn.th.phonerf.activity.pos.present.IPosPresenter;
//import cn.th.phonerf.constant.ApiConstants;
//import cn.th.phonerf.constant.AppConst;
//import cn.th.phonerf.constant.AppParam;
//import cn.th.phonerf.dal.SyncServiceDal;
//import cn.th.phonerf.http.request.HttpRequestImpl;
//import cn.th.phonerf.model.TRmFlow;
//import cn.th.phonerf.model.TRmPayflow;
//import cn.th.phonerf.model.TRmSaleflow;
//import cn.th.phonerf.service.present.FlowServicelmp;
//import cn.th.phonerf.utils.JsonUtils;
//
//public class FlowService extends Service {
//
//    private Timer timer;
//    private IPosPresenter presenter;
//    /**
//     * 绑定服务时才会调用
//     * 必须要实现的方法
//     * @param intent
//     * @return
//     */
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    /**
//     * 首次创建服务时，系统将调用此方法来执行一次性设置程序（在调用 onStartCommand() 或 onBind() 之前）。
//     * 如果服务已在运行，则不会调用此方法。该方法只被调用一次
//     */
//    @Override
//    public void onCreate() {
//        System.out.println("onCreate invoke");
//        super.onCreate();
//        try{
//            presenter = new FlowServicelmp(this);
//
//            if (timer == null){
//                timer = new Timer();
//                timer.schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        Log.e("111:", "执行流水轮询");
//
//                        SyncServiceDal dal = new SyncServiceDal();
//                        try {
//                            //1. 获取一条未上传的流水
//                            List<TRmPayflow> payList = dal.selectBigPayflow();
//                            if(payList.size() != 0 ){
//                                List<TRmSaleflow> saleList = dal.selectBigSaleflow(payList.get(0).getFlowNo());
////                                presenter.doUploadFlow(null, saleList, payList);
//                            }
//                        }catch (Exception e){
//                            Log.e("异常：",e.getMessage());
//                        }
//                    }
//                },0,60000);
//
//            }
//        }catch (Exception e){
//            Log.d("异常：",e+"");
//        }
//
//
//
//
//    }
//
//    /**
//     * 每次通过startService()方法启动Service时都会被回调。
//     * @param intent
//     * @param flags
//     * @param startId
//     * @return
//     */
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        Log.e("111:", "onStartCommand:");
//        return super.onStartCommand(intent, flags, startId);
//
//
//
////        startForeground(1,new Notification());
////
////        //绑定建立链接
////        bindService(new Intent(this,SyncService.class),mServiceConnection,Context.BIND_IMPORTANT);
////
////        return START_STICKY;
//
//
//    }
///*
//    private ServiceConnection mServiceConnection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
//            //链接上
//            Log.d("test", "StepService:建立链接");
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName componentName) {
//            //断开链接
//            startService(new Intent(FlowService.this, SyncService.class));
//            //重新绑定
//            bindService(new Intent(FlowService.this, SyncService.class),
//                    mServiceConnection, Context.BIND_IMPORTANT);
//        }
//    };*/
//
//    /**
//     * 服务销毁时的回调
//     */
//    @Override
//    public void onDestroy() {
//        System.out.println("onDestroy invoke");
//        super.onDestroy();
//    }
//}
