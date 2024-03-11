package cn.th.phonerf.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import cn.th.phonerf.utils.logger.Logger;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Info 设备的一些方法
 * @Auth Bello
 * @Time 16-11-29 下午5:10
 * @Ver
 */

public class DeviceUtils {

    /**
     * 获取屏幕尺寸
     *
     * @param mContext
     * @return
     */
    public static int[] getDeviceMetric(Context mContext) {
        WindowManager wm = ((Activity) mContext).getWindowManager();
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return new int[]{dm.widthPixels, dm.heightPixels};
    }


    /**
     * 获取当前APP版本号
     *
     * @param mContext
     * @return
     */
    public static String getAppVersion(Context mContext) {
        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.PERMISSION_GRANTED);
            return pi.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e("获取APP版本号异常");
            return null;
        }

    }


    /**
     * 获取设备的IEMI
     *
     * @param mContext
     * @return
     */
    public static String getIMEI2(Context mContext) {
        try {
            TelephonyManager manager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            String imei = manager.getDeviceId();
            //String imei = manager.getLine1Number();
            if (imei == null) {
                // android pad
                // 在主流厂商生产的设备上，有一个很经常的bug，就是每个设备都会产生相同的ANDROID_ID：9774d56d682e549c
                imei = "PAD" + Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
            } else if (imei.contains("00000000000")) {
                // 模拟器
                imei = "DEV" + Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
            return imei;
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e("获取IEMI异常");
            return null;
        }
    }


    public static String getIMEI(Context mContext){

        String serial = null;

        try {

            Class<?> c =Class.forName("android.os.SystemProperties");
            Method get =c.getMethod("get", String.class);
            serial = (String)get.invoke(c, "ro.serialno");
            if(serial.equals(""))
                serial = "PAD" + Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e("获取IEMI异常");
            return getIMEI2(mContext);
        }
        return serial;

    }

    public static String getPhoneNumber(Context mContext) {
        try {
            TelephonyManager manager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            String imei = manager.getLine1Number();
            if (imei == null) {
                // android pad
                // 在主流厂商生产的设备上，有一个很经常的bug，就是每个设备都会产生相同的ANDROID_ID：9774d56d682e549c
                imei = "PAD" + Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
            } else if (imei.contains("00000000000")) {
                // 模拟器
                imei = "DEV" + Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
            return imei.replace("+86","");
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e("获取IEMI异常");
            return null;
        }
    }


    /**
     * 通过WIFI获取Mac地址
     *
     * @param mContext
     * @return
     */
    public static String getMacAddress(Context mContext) {
        try {
            WifiManager manager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
            return manager.getConnectionInfo().getMacAddress();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e("获取Mac地址异常");
            return null;
        }
    }


    /**
     * 获取渠道和子渠道号
     *
     * @param mContext
     * @return
     */
    public static String[] getAppChannel(Context mContext) {
        String[] channel = new String[2];
        String value = "";
        String value1 = "";
        try {
            // 获取渠道的设置值
            ApplicationInfo applicationInfo = mContext.getPackageManager().getApplicationInfo(mContext.getPackageName(), PackageManager
                    .GET_META_DATA);
            value = applicationInfo.metaData.getString("CHANNEL");


            if (value.contains("_")) {
                String[] arr = value.split("_");
                if (arr.length < 2) {
                    channel[0] = arr[0];
                    channel[1] = "";
                } else {
                    channel[0] = arr[0];
                    channel[1] = arr[1];
                }
            } else {
                channel[0] = value;
                channel[1] = "";
            }

        } catch (Exception e) {
            e.printStackTrace();
            Logger.e("获取渠道异常");
        }

        return channel;
    }


    /**
     * 获取auth用的时间参数
     *
     * @return
     */
    public static String getAuthTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        return df.format(new Date());
    }
}
