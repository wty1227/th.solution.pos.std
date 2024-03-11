package cn.th.phonerf.utils.ip;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import cn.th.phonerf.utils.HttpUtils;
import cn.th.phonerf.utils.JsonUtils;
import cn.th.phonerf.utils.SharePreferenceUtils;
import cn.th.phonerf.utils.logger.Logger;


/**
 * @Info 获取设备当前外网IP
 * @Auth Bello
 * @Time 17-3-14 上午11:12
 * @Ver
 */
public class IpAddressUtil {
    Context mContext;

    String ipUrl1 = "http://ip.taobao.com/service/getIpInfo2.php?ip=myip";
    String ipUrl2 = "http://whois.pconline.com.cn/ipJson.jsp";

    int ip1Tag = 0;

    public IpAddressUtil(Context mContext) {
        this.mContext = mContext;
        getIpInfo(ipUrl1);
    }

    /**
     * 获取IP的链接请求
     *
     * @param url
     */
    private void getIpInfo(final String url) {
        ip1Tag++;
        if (ip1Tag>10) return;
        HttpUtils.doGetAsyn(url, new HttpUtils.RequestCallBack() {
            @Override
            public void onRequestComplete(String request) {
                if (null != request) {
                    if (url.equals(ipUrl1)) {
                        requestToIp(request);
                    } else {
                        requestToIp2(request);
                    }

                } else {
                    getIpInfo(ipUrl2);
                }
            }
        });

    }


    /**
     *  解析地址1的IP信息
     *
     * @param result
     */
    private void requestToIp(String result) {
        try {
            //获取IP成功写入配置文件
            if (result.contains("\"data\":{\"")) {

                //IpBean json = (IpBean) JsonUtils.JsonStrToClass(result, IpBean.class);
                IpBean json = (IpBean) JSON.parseObject(result, IpBean.class);

                SharePreferenceUtils.setValue(mContext, SharePreferenceUtils.IP, json.getData().getIp());
                SharePreferenceUtils.setValue(mContext, SharePreferenceUtils.AREA, json.getData().getArea());
                SharePreferenceUtils.setValue(mContext, SharePreferenceUtils.COUNTRY, json.getData().getCountry());
                SharePreferenceUtils.setValue(mContext, SharePreferenceUtils.REGION, json.getData().getRegion());
                SharePreferenceUtils.setValue(mContext, SharePreferenceUtils.CITY, json.getData().getCity());
                SharePreferenceUtils.setValue(mContext, SharePreferenceUtils.ISP, json.getData().getIsp());
            } else {
                //地址1请求三次，获取不到再请求地址2
                if (ip1Tag <= 3) {
                    ip1Tag++;
                    getIpInfo(ipUrl1);
                } else {
                    getIpInfo(ipUrl2);
                }
            }

        } catch (Exception e) {
            //地址1请求三次，获取不到再请求地址2
            if (ip1Tag <= 3) {
                ip1Tag++;
                getIpInfo(ipUrl1);
            } else {
                getIpInfo(ipUrl2);
            }
        }

    }


    /**
     *  解析地址2的IP信息
     *
     * @param result
     */
    private void requestToIp2(String result) {
        try {
            result = result.split("IPCallBack")[2];
            result = result.replace("(", "");
            result = result.replace(");}", "");
            IpBean2 json = (IpBean2) JsonUtils.JsonStrToClass(result, IpBean2.class);

            SharePreferenceUtils.setValue(mContext, SharePreferenceUtils.IP, json.getIp());
            SharePreferenceUtils.setValue(mContext, SharePreferenceUtils.AREA, "");
            SharePreferenceUtils.setValue(mContext, SharePreferenceUtils.COUNTRY, "");
            SharePreferenceUtils.setValue(mContext, SharePreferenceUtils.REGION, json.getPro());
            SharePreferenceUtils.setValue(mContext, SharePreferenceUtils.CITY, json.getCity());
            SharePreferenceUtils.setValue(mContext, SharePreferenceUtils.ISP, json.getAddr());

        } catch (Exception e) {
            Logger.e("IP地址获取失败");
        }
    }


}
