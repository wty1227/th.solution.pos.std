package cn.th.phonerf.activity.logrecord;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import org.json.JSONObject;

import cn.th.phonerf.activity.base.BaseBean;
import cn.th.phonerf.activity.logrecord.bean.LogRecordActBean;
import cn.th.phonerf.constant.ApiConstants;
import cn.th.phonerf.constant.AppConst;
import cn.th.phonerf.http.request.HttpRequestImpl;
import cn.th.phonerf.utils.JsonUtils;
import cn.th.phonerf.utils.TextUtil;
import cn.th.phonerf.utils.logger.Logger;


/**
 * @Info 上送日志实现类
 * @Auth Bello
 * @Time 17-3-14 上午9:46
 * @Ver
 */

public class LogRecordImpl extends HttpRequestImpl implements ILogRecord {

    private Handler mHandler;

    public LogRecordImpl(Context mContext) {
        super(mContext);
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 上送接口请求参数
     * @param level
     * @param orderID
     * @param logStr
     */
    @Override
    public void sendLogRecord(int level, String orderID, String logStr) {
        try {
            LogRecordActBean bean = new LogRecordActBean(level, orderID, logStr);
            String info = bean.toJsonStr();
            if (!TextUtil.isEmpty(info)) {
                postData(ApiConstants.LOG_RECORD_ACTION, bean.toJsonStr(), null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void postResult(boolean isSuccess, final JSONObject response, final String action, String msg, View view) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                //上送接口返回
                if (action.equals(ApiConstants.LOG_RECORD_ACTION)) {
                    /*BaseBean bean = (BaseBean) JsonUtils.JsonObjToClass(response, BaseBean.class);
                    if (bean != null && bean.getError().equals(AppConst.API_SUCCESS_CODE)) {
                        Logger.i("Log send success");
                    } else {
                        Logger.e("Log send fail");
                    }*/
                }
            }
        });
    }


}
