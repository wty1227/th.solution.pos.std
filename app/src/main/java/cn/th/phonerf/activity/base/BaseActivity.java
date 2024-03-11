package cn.th.phonerf.activity.base;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.util.Date;

import cn.th.phonerf.R;;
import cn.th.phonerf.model.TRmSaleflow;
import cn.th.phonerf.utils.DateUtil;
import cn.th.phonerf.utils.PayProgressUtils;
import cn.th.phonerf.utils.ProgressUtils;
import cn.th.phonerf.view.MyToast;

public class BaseActivity extends AppCompatActivity implements IBaseView {

    public Context mContext;
    public Dialog loadingDlg;
    public Dialog loadingPayDlg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        loadingDlg = ProgressUtils.loadCircleProgress(mContext, getString(R.string.loading), true);
        loadingPayDlg = PayProgressUtils.loadCircleProgress(mContext, getString(R.string.loading), true);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void setToast(String msg) {
        MyToast.toast(mContext, msg);
    }

    @Override
    public void setProgressDlg(boolean isShow) {
        if (loadingDlg != null) {
            if(isShow)
                loadingDlg.show();
            else
                loadingDlg.dismiss();
        }
    }

    @Override
    public void setPayProgressDlg(boolean isShow) {
        if (loadingPayDlg != null) {
            if(isShow)
                loadingPayDlg.show();
            else
                loadingPayDlg.dismiss();
        }
    }

    @Override
    public void setClickable(View view, boolean clickable) {
        if(view != null)
            view.setClickable(clickable);
    }

    @Override
    public void go() {
        finish();
    }

    @Override
    protected void onDestroy() {
        setProgressDlg(false);
        setPayProgressDlg(false);
        super.onDestroy();
    }

    public void queryBarcode(String itemNo){

    }

    public void addSaleFlowItem(TRmSaleflow item){

    }

    public void recordOperLog(String tag, String model, String msg){
        try {
            if(msg == null)
                return;
            //long timestamp = System.currentTimeMillis();
            String date = DateUtil.getCurrentDate();
            String time = DateUtil.getCurrentDateTime();// formatter.format(new Date());
            String fileName = "ureaoper-" + date  + ".log";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String path = "/sdcard/Android/crash/";
                        /*File dir = new File(path);
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }*/
                File file = new File(path + fileName);
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
                RandomAccessFile raf = new RandomAccessFile(file, "rwd");
                raf.seek(file.length());
                raf.write( (time + " " + tag + " " + model + " : " + msg).getBytes() );
                raf.close();

                        /*FileOutputStream fos = new FileOutputStream(path + fileName);

                        fos.write((time + " " + tag + " " + model + " : " + msg).getBytes());
                        fos.close();*/
            }
        } catch (Exception e) {
        }
        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(msg == null)
                        return;
                    //long timestamp = System.currentTimeMillis();
                    String date = DateUtil.getCurrentDate();
                    String time = DateUtil.getCurrentDateTime();// formatter.format(new Date());
                    String fileName = "ureaoper-" + date  + ".log";
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        String path = "/sdcard/Android/crash/";

                        File file = new File(path + fileName);
                        if (!file.exists()) {
                            file.getParentFile().mkdirs();
                            file.createNewFile();
                        }
                        RandomAccessFile raf = new RandomAccessFile(file, "rwd");
                        raf.seek(file.length());
                        raf.write( (time + " " + tag + " " + model + " : " + msg).getBytes() );
                        raf.close();

                    }
                } catch (Exception e) {
                }
            }
        }).start();*/
    }
}
