package cn.th.phonerf.activity.version.mode;

import android.content.Context;

import cn.th.phonerf.activity.version.CheckVersionUtil;
import cn.th.phonerf.activity.version.bean.VersionData;
import cn.th.phonerf.utils.DeviceUtils;
import cn.th.phonerf.utils.SharePreferenceUtils;


/**
 * @Info
 * @Auth Bello
 * @Time 16-7-5 上午10:34
 * @Ver
 */
public class VersionModeImpl implements IVersionMode {
    Context mContext;

    public VersionModeImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public boolean compareCode(VersionData verData) {
        CheckVersionUtil.app_Version = verData.getApp_version();
        CheckVersionUtil.app_Type = verData.getApp_type();
        CheckVersionUtil.app_Url = verData.getApp_download();
        CheckVersionUtil.app_Hash = verData.getApp_hash();
        CheckVersionUtil.currentTime = verData.getCurrentTime();
        CheckVersionUtil.mContext = mContext;
        //保存系统时间
        SharePreferenceUtils.setValue(mContext, SharePreferenceUtils.TIME , CheckVersionUtil.currentTime);
//
        String localCode = DeviceUtils.getAppVersion(mContext);
//
        if (localCode.equals(CheckVersionUtil.app_Version)){
            //不用更新
            SharePreferenceUtils.setValue(mContext, SharePreferenceUtils.VERSION, CheckVersionUtil.app_Type);
            CheckVersionUtil.deleteApk();
            return false;
        } else {
            double dbNum = Double.parseDouble(CheckVersionUtil.app_Type);
            double localNum = Double.parseDouble(SharePreferenceUtils.getValue(mContext,
                    SharePreferenceUtils.VERSION, "1"));
            //判断是否强制更新
            if (dbNum - localNum != 0) {
                CheckVersionUtil.getDialogMust(verData.getApp_info());
            } else {
                CheckVersionUtil.getDialog(verData.getApp_info());
            }
            return true;
        }
    }
}
