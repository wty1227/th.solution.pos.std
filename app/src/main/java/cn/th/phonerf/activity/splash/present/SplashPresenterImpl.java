package cn.th.phonerf.activity.splash.present;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import cn.th.phonerf.activity.splash.view.ISplashView;
import cn.th.phonerf.constant.ApiConstants;
import cn.th.phonerf.constant.AppConst;
import cn.th.phonerf.http.request.HttpRequestImpl;
import cn.th.phonerf.utils.logger.Logger;

public class SplashPresenterImpl extends HttpRequestImpl implements ISplashPresenter {
    String TAG = "";
    private ISplashView mView;
    private Handler mHandler;
    public SplashPresenterImpl(ISplashView view){
        super((Context)view);
        this.mView = view;
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onDestroy() {
        mView = null;
    }


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
                    }
                }else{
                    mView.setToast(resMsg);
                    mView.resSyncError(resMsg);
                }
            }
        });
    }

}
