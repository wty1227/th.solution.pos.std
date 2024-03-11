package cn.th.phonerf.activity.index.present;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import cn.th.phonerf.activity.index.view.IIndexView;
import cn.th.phonerf.constant.ApiConstants;
import cn.th.phonerf.constant.AppConst;
import cn.th.phonerf.http.request.HttpRequestImpl;
import cn.th.phonerf.utils.logger.Logger;

public class IndexPresenterImpl extends HttpRequestImpl implements cn.th.phonerf.activity.index.present.IIndexPresenter {
    String TAG = "";
    private IIndexView mView;
    private Handler mHandler;

    public IndexPresenterImpl(IIndexView view){
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
    public void postResult(final boolean isSuccess, final JSONObject response, final String action, final String resMsg, final View view) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mView.setProgressDlg(false);
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
                    }
                }else{
                    mView.setToast(resMsg);
                }
            }
        });
    }
}
