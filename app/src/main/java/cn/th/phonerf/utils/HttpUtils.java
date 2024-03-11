package cn.th.phonerf.utils;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * @Info HttpUrlConnect请求类
 * @Auth Bello
 * @Time 17-3-14 下午2:21
 * @Ver
 */

public class HttpUtils implements X509TrustManager {
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }

    /**
     * 请求后的回调
     */
    public interface RequestCallBack {
        void onRequestComplete(String request) throws JSONException;
    }


    /**
     * 异步GET请求
     *
     * @param url
     * @param callBack
     */
    public static void doGetAsyn(final String url, final RequestCallBack callBack){
        new Thread(){
            @Override
            public void run() {
                try {
                    String request = doGet(url);
                    if (null != request){
                        callBack.onRequestComplete(request);
                    } else {
                        callBack.onRequestComplete(null);
                    }
                } catch (Exception e){
                    try {
                        callBack.onRequestComplete(null);
                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }
        }.start();
    }


    /**
     * GET请求操作
     *
     * @param path
     * @return
     */
    public static String doGet(String path){
        HttpURLConnection conn = null;
        InputStream is = null;
        BufferedReader br = null;

        String result = null;
        try {
            URL url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
            conn.setConnectTimeout(5*1000);
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1");
            if (conn.getResponseCode() == 200) {

                is = conn.getInputStream();
                //处理编码格式
                if (conn.getContentType().toUpperCase().contains("GB")) {
                    br = new BufferedReader(new InputStreamReader(is, "GBK"));
                } else {
                    br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                }

                StringBuffer sb = new StringBuffer();
                String line ;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                result = sb.toString();
            } else {
                throw new RuntimeException(" responseCode is not 200 ... ");
            }
        } catch (Exception e) {
            throw new RuntimeException(path + " request fail ");
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }
            try {
                if (br != null)
                    br.close();
            } catch (IOException e) {
            }
            conn.disconnect();
        }

        return result;

    }
}
