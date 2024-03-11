package cn.th.phonerf.activity.splash;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.histonepos.npsdk.api.IServiceManager;
import com.histonepos.npsdk.bind.IServiceConnector;
import com.histonepos.npsdk.bind.SmartService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.th.phonerf.R;
import cn.th.phonerf.activity.base.BaseActivity;
import cn.th.phonerf.activity.example.ExampleActivity;
import cn.th.phonerf.activity.login.LoginActivity;
import cn.th.phonerf.activity.splash.present.ISplashPresenter;
import cn.th.phonerf.activity.splash.present.SplashPresenterImpl;
import cn.th.phonerf.activity.splash.view.ISplashView;
import cn.th.phonerf.application.MyApplication;
import cn.th.phonerf.constant.GFunc;
import cn.th.phonerf.constant.GPrinter;
import cn.th.phonerf.constant.GSale;
import cn.th.phonerf.constant.GlobalDefines;
import cn.th.phonerf.utils.DBItemHelper;
import cn.th.phonerf.utils.DBSaleHelper;
import cn.th.phonerf.utils.DBUtil;
import cn.th.phonerf.utils.DateUtil;
import cn.th.phonerf.utils.ProgressUtils;
import cn.th.phonerf.utils.SharePreferenceUtils;
import cn.th.phonerf.utils.TextUtil;
import cn.th.phonerf.utils.UserUtil;


/**
 * @Info 启动页
 * @Auth Bello
 * @Time 17-2-28 下午2:19
 * @Ver
 */

public class SplashActivity extends BaseActivity implements ISplashView{
    private static final String[] REQUIRED_PERMISSION_LIST = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.INTERNET,
            Manifest.permission.READ_EXTERNAL_STORAGE
            //Manifest.permission.RECORD_AUDIO,
    };
    private static final int REQUEST_CODE = 1;
    private List<String> mMissPermissions = new ArrayList<>();

    private Context mContext;
    private Dialog loadingDlg;
    private ISplashPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splash);
        checkAndRequestPermissions();

        mContext = this;
        //loadingDlg = ProgressUtils.loadCircleProgress(mContext, getString(R.string.loading), true);
        //presenter = new SplashPresenterImpl(this);

        if(DBUtil.dbItemHelper == null) {
            DBUtil.dbItemHelper = new DBItemHelper(this);
            DBUtil.dbItemExec = DBUtil.dbItemHelper.getWritableDatabase();
        }
        //初始化Histone服务
        GlobalDefines.mApplicationContext = MyApplication.getContext();

       /* SmartService.getInstance().register(this, new IServiceConnector() {
            @Override
            public void onServiceDisconnected() {
                // TODO Auto-generated method stub
                GlobalDefines.sm = null;
            }

            @Override
            public void onServiceConnected(IServiceManager serviceManager) {
                // TODO Auto-generated method stub
                //Log.i("PosPad_Activity", "onServiceConnected");
                GlobalDefines.sm = serviceManager;
            }
        });*/

       /* SmartService.getInstance().register(GlobalDefines.mApplicationContext, new IServiceConnector() {
            @Override
            public void onServiceDisconnected() {
                // TODO Auto-generated method stub
            }

            @Override
            public void onServiceConnected(IServiceManager serviceManager) {
                // TODO Auto-generated method stub

                //Log.i(TAG,"onServiceConnected:" + (System.currentTimeMillis() - rtcClick));
                //Log.i("PosPad_Activity", "onServiceConnected");


                 //Step 2: get apiHander

                GlobalDefines.sm  = serviceManager;
                if (GlobalDefines.sm!=null) {
//					Toast.makeText(PosPad_Activity.this, "Bind SmartService Success", Toast.LENGTH_SHORT).show();
                    try {
                        GPrinter.initPrinter();
                    } catch (Exception e) {
                        setToast(e.getMessage());
                    }
                }
            }
        });*/
        //UserUtil.clearAuth(this);
        //new IpAddressUtil(this);

//        SystemClock.sleep(500);
//        startActivity(new Intent(this, LoginActivity.class));
//        finish();
    }


    private void checkAndRequestPermissions() {
        mMissPermissions.clear();
        for (String permission : REQUIRED_PERMISSION_LIST) {
            int result = ContextCompat.checkSelfPermission(this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                mMissPermissions.add(permission);
            }
        }
        // check permissions has granted
        if (mMissPermissions.isEmpty()) {
            startMainActivity();
        } else {
            ActivityCompat.requestPermissions(this,
                    mMissPermissions.toArray(new String[mMissPermissions.size()]),
                    REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            for (int i = grantResults.length - 1; i >= 0; i--) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    mMissPermissions.remove(permissions[i]);
                }
            }
        }
        // Get permissions success or not
        if (mMissPermissions.isEmpty()) {
            startMainActivity();
        } else {
            Toast.makeText(SplashActivity.this, "get permissions failed,exiting...",Toast.LENGTH_SHORT).show();
            SplashActivity.this.finish();
        }
    }

    private void startMainActivity() {
        try{
//            UserUtil.getBaseSetup(mContext);
            SystemClock.sleep(500);
            startActivity(new Intent(this, LoginActivity.class));
            finish();

//            Log.d("SplashActivity", "loadData() device_camera_pid:" + AppParam.CameraProductId);
        }catch (Exception e){

        }


    }

    @Override
    protected void onStop() {
        //mListener.BDStop();
        super.onStop();
    }

    /**
     * 返回 回去流水号
     * @param flowNo
     */
    @Override
    public void resGetMaxTodayFlow(String flowNo) {
        String str2 = "";
        try {
            //1.获取本地流水日期数据
            String str = SharePreferenceUtils.getValue(mContext, SharePreferenceUtils.FLOW_NO, "");
            if (!str.isEmpty())
            {
                String str3 = str.substring(0, 10);
                Date time = DateUtil.stringToDate(str3);
                long span = DateUtil.getDayByMinusDate(time, new Date());
                str = str.substring(12);
                if (span > 0) {
                    str = "1";
                }
            }
            else {
                str = "1";
            }
            if ((!flowNo.isEmpty()) && (Integer.valueOf(str) < Integer.valueOf(flowNo)))
            {
                str = String.valueOf(Integer.valueOf(flowNo) + 1);
            }
            GSale.FlowNo = Integer.valueOf(str);
            if ((GSale.FlowNo <= 0) || (GSale.FlowNo >= 0x270f))
            {
                str2 = "初始化全局变量FileRead()读取的流水号不在范围[0-9999]！读取值：" + str;
            }
            SharePreferenceUtils.setValue(mContext, SharePreferenceUtils.FLOW_NO, DateUtil.getCurrentDate() + "  " + TextUtil.padRight(String.valueOf(GSale.FlowNo), 4, '0'));
            if (str2.length() > 0)
            {
                //GSys.ErrMsg = str2;
                GFunc.setFlowNo(mContext);
            }
            //进入登陆界面
            startActivity(new Intent(this, ExampleActivity.class));
            finish();
        }catch (Exception ex){
            str2 = "系统初始化流水号文件失败！\n" + ex.getMessage();
            setToast(str2);
            //获取本地离线数据

        }
    }

    @Override
    public void resSyncError(String msg) {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);

        builder.setTitle("失败");
        builder.setMessage("同步失败, 是否重新同步");
        //builder.setCancelable(false);

        //final Double finalConsumAmt = consumAmt;
        builder.setPositiveButton("重新同步", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {

                presenter.doGetMaxTodayFlow(null , GSale.PosId);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("退出系统", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                dialog.cancel();
                finish();
            }
        });
        final android.app.AlertDialog dlg = builder.create();
        dlg.show();
    }
}
