package cn.th.phonerf.utils.auth;

import android.content.Context;

import cn.th.phonerf.constant.GSale;
import cn.th.phonerf.utils.DeviceUtils;
import cn.th.phonerf.utils.JsonUtils;
import cn.th.phonerf.utils.NetWorkType;
import cn.th.phonerf.utils.SharePreferenceUtils;
import cn.th.phonerf.utils.ip.IpAddressUtil;
import cn.th.phonerf.utils.logger.Logger;



/*
 * @info   Auth参数
 * @author Bello
 * @time 2015年5月20日 上午10:55:53  
 * @version    
 */
public class IAuthor {
    private static String app_version = "1.0.1";
    private static String app_uuid = "1";
    private static String os = "Android_os";
    private static String time_stamp;




    private static String TAG = "IAuthor";
    private static String appKey = "7986C7543b0427F787dD590d6f39a5A0";
    //private static String os = "Android_os";
    private static String userId;
    private static String username;
    private static String ip;
    private static String area;
    private static String country;
    private static String region;
    private static String city;
    private static String isp;
    private static String screen;
    private static String os_version;
    private static String mb_brand;
    private static String mb_model;
    private static String imei;
    private static String version;
    private static String mac;
    private static String netType;
    private static String[] channels;
    private static int[] screens;
    private static String time;
    private static String channel;
    private static String channel_sub;
    private static String latitude;
    private static String longitude;
    private static String address;


    public static String getVersion() { return version; }
    /**
     * 根据用户获验证信息
     *
     * @param context
     * @return
     */
    public static String getAuthParams(final Context context) {
        try {
            //app_version= SharePreferenceUtils.getValue(context, SharePreferenceUtils.ID, "");
            //app_uuid= SharePreferenceUtils.getValue(context, SharePreferenceUtils.app_uuid, "");
            //app_version= SharePreferenceUtils.getValue(context, SharePreferenceUtils.ID, "");
            //time_stamp= SharePreferenceUtils.getValue(context, SharePreferenceUtils.ID, "");

            userId = GSale.PosId;// SharePreferenceUtils.getValue(context, SharePreferenceUtils.LOGIN_ID, "");
            //OperId = SharePreferenceUtils.getValue(context, SharePreferenceUtils.ID, "");
            username = "";// SharePreferenceUtils.getValue(context, SharePreferenceUtils.MOBILE, "");



            netType = NetWorkType.GetNetworkType(context);
            ip = SharePreferenceUtils.getValue(context, SharePreferenceUtils.IP, "");
            if ("".equals(ip)){
                new IpAddressUtil(context);
            }
            area = SharePreferenceUtils.getValue(context, SharePreferenceUtils.AREA, "");
            country = SharePreferenceUtils.getValue(context, SharePreferenceUtils.COUNTRY, "");
            region = SharePreferenceUtils.getValue(context, SharePreferenceUtils.REGION, "");
            city = SharePreferenceUtils.getValue(context, SharePreferenceUtils.CITY, "");
            isp = SharePreferenceUtils.getValue(context, SharePreferenceUtils.ISP, "");
            latitude = SharePreferenceUtils.getValue(context, SharePreferenceUtils.LATITUDE, "");
            longitude = SharePreferenceUtils.getValue(context, SharePreferenceUtils.LONGITUDE, "");
            address = SharePreferenceUtils.getValue(context, SharePreferenceUtils.ADDRESS, "");

            os_version = os_version == null ? android.os.Build.VERSION.RELEASE : os_version;
            mb_brand = mb_brand ==  null ? android.os.Build.BRAND : mb_brand;
            mb_model = mb_model == null ? android.os.Build.MODEL : mb_model;

            imei = imei == null ? DeviceUtils.getIMEI(context) : imei;
            version = version == null ? DeviceUtils.getAppVersion(context) + "" : version;
            mac = mac == null ? DeviceUtils.getMacAddress(context) : mac;
            time = DeviceUtils.getAuthTime();

//            channels = channels == null ? DeviceUtils.getAppChannel(context) : channels;
//            channel = channels[0];
//            channel_sub = channels[1];

            screens = screens == null ? DeviceUtils.getDeviceMetric(context) : screens;
            screen = screens[0] +"x" + screens[1];

        } catch (Exception e) {
            e.printStackTrace();
            Logger.e("auth error");
        }

        AuthBean bean = new AuthBean();
        bean.setApp_key(appKey);
        bean.setOs(os);
        bean.setOs_version(os_version);
        bean.setMb_brand(mb_brand);
        bean.setMb_model(mb_model);
        bean.setIp(ip);
        bean.setNet_type(netType);
        bean.setCountry(country);
        bean.setRegion(region);
        bean.setMb_screen(screen);
        bean.setApp_version(version);
        bean.setCity(city);
        bean.setIsp(isp);
        bean.setMac(mac);
        bean.setImei(imei);
        bean.setArea(area);
        bean.setLatitude("");
        bean.setLongitude("");
        bean.setAddress("");
        bean.setChannel(channel);
        bean.setChannel_sub(channel_sub);
        bean.setUid(userId);
        bean.setUsername(username);
        bean.setTime_stamp(time);
        bean.setLatitude(latitude);
        bean.setLongitude(longitude);
        bean.setAddress(address);


        bean.setApp_uuid(app_uuid);
        bean.setApp_version(app_version);


        return JsonUtils.objectToJsonStr(bean);
        //return JsonUtils.objectToJsonStr(bean);
    }


}
