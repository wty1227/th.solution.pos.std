package cn.th.phonerf.activity.setup.present;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import cn.th.phonerf.activity.setup.bean.SetupBean;
import cn.th.phonerf.activity.setup.view.ISetupView;
import cn.th.phonerf.constant.ApiConstants;
import cn.th.phonerf.constant.AppConst;
import cn.th.phonerf.http.request.HttpRequestImpl;
import cn.th.phonerf.model.MerchantInfo;
import cn.th.phonerf.utils.JsonUtils;
import cn.th.phonerf.utils.TextUtil;
import cn.th.phonerf.utils.logger.Logger;

public class SetupPresenterImpl extends HttpRequestImpl implements ISetupPresenter {
    String TAG = "SetupPresenterImpl";
    ISetupView _view;
    Handler mHandler;

    public SetupPresenterImpl(ISetupView view){
        super((Context)view);
        this._view = view;

        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void doSetup(View view, String merchantNo) {
        merchantNo = TextUtil.removeSpace(merchantNo);
        if(TextUtil.isEmpty(merchantNo))
            _view.setToast("商户代码不能为空");
        else{
            try {
                _view.setClickable(view, false);
                _view.setProgressDlg(true);
                SetupBean bean = new SetupBean(merchantNo);
                postData(ApiConstants.ACTION_MERCHANT_INFO_SELECTBYNO, JsonUtils.objectToJsonStr(bean), view);
            }catch (Exception e){
                Logger.e("数据异常");
                _view.setClickable(view, true);
            }
        }

    }

    @Override
    public void postResult(final boolean isSuccess,final JSONObject response,final String action,final String resMsg,final View view) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                _view.setProgressDlg(false);
                _view.setClickable(view, true);
                if(isSuccess){
                    if(action.equals(ApiConstants.ACTION_MERCHANT_INFO_SELECTBYNO)){
                        String code = null;
                        String msg = "";
                        MerchantInfo data = null;
                        try {
                            code = response.getString("code");
                            msg = response.getString("msg");
                            //data = response.getString("data");
                            data = (MerchantInfo)JsonUtils.JsonStrToClass (response.getString("data"), MerchantInfo.class);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(code.equals(AppConst.API_SUCCESS_CODE) && data != null ){
                            _view.saveMerchantData( data  );
                        }else{
                            _view.setToast(msg);
                        }

                    }

                }else
                    _view.setToast(resMsg);


            }
        });
    }

    @Override
    public void onDestroy() {
        _view = null;
    }


}
