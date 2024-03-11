/*package cn.th.phonerf.service;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import cn.th.phonerf.dal.UploadDal;


public class SyncService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("SyncService", "开启数据传输");
//        return super.onStartCommand(intent, flags, startId);
        startForeground(1,new Notification());

        //绑定建立链接
        bindService(new Intent(this,SyncService.class),mServiceConnection,Context.BIND_IMPORTANT);

        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("SyncService", "onDestroy");
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //链接上
            Log.d("test", "SyncService:建立链接");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            //断开链接
            startService(new Intent(SyncService.this, FlowService.class));
            //重新绑定
            bindService(new Intent(SyncService.this, FlowService.class),
                    mServiceConnection, Context.BIND_IMPORTANT);
        }
    };

    private void transData(){
        try {
            UploadDal uploadDal = new UploadDal();
            int rowCount = uploadDal.getCount();

            for(int i = 0; i < rowCount; i++){

            }

        }catch (Exception ex){
            Log.e("SyncService", ex.getMessage());
        }

    }
}
*/