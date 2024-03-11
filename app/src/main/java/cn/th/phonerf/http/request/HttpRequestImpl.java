package cn.th.phonerf.http.request;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.th.phonerf.constant.ApiConstants;
import cn.th.phonerf.utils.DateUtil;
import cn.th.phonerf.utils.TextUtil;
import cn.th.phonerf.utils.auth.IAuthor;
import cn.th.phonerf.utils.logger.Logger;
import lombok.SneakyThrows;

import org.json.JSONObject;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

/**
 * @Info
 * @Auth Bello
 * @Time 16-6-22 下午12:02
 * @Ver
 */
public abstract class HttpRequestImpl implements IHttpRequest {
    Context mContext;
    RequestQueue queue = null;
    String sessionId = null;

    int count = 0;

    public HttpRequestImpl(Context mContext) {
        this.mContext = mContext;
    }

    public HttpRequestImpl() {

    }

    public void recordOperLog(String tag, String model, String msg){
        try {
            if(msg == null)
                return;
            //long timestamp = System.currentTimeMillis();
            String date = DateUtil.getCurrentDate();
            String time = DateUtil.getCurrentDateTime();// formatter.format(new Date());
            String fileName = "ureaoper-" + date  + ".log";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String path = "/sdcard/Android/crash/";
                        /*File dir = new File(path);
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }*/
                File file = new File(path + fileName);
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
                RandomAccessFile raf = new RandomAccessFile(file, "rwd");
                raf.seek(file.length());
                raf.write( (time + " " + tag + " " + model + " : " + msg).getBytes() );
                raf.close();

                        /*FileOutputStream fos = new FileOutputStream(path + fileName);

                        fos.write((time + " " + tag + " " + model + " : " + msg).getBytes());
                        fos.close();*/
            }
        } catch (Exception e) {
        }

    }


    @Override
    public void postData(final String action, final JSONObject jsonObject, final View view){
        postData(action, jsonObject, view, Request.Method.POST);
    }

    @SneakyThrows
    @Override
    public void postData(final String action, final JSONObject jsonObject, final View view, int requestMethod) {
        try {

            final HashMap<String, String> headers = new HashMap<>();
            headers.put("Cookie", "JSESSIONID=" + sessionId);

            String auth = IAuthor.getAuthParams(mContext);

            Logger.json("【Cookie】=> ", sessionId);

            //Logger.json("【auth】=> ", auth);

//            jsonObject.put("tenantId", "000000");
//            jsonObject.put("clientId", IAuthor.getClientId());
//            jsonObject.put("grantType", IAuthor.getGrantType());
            Logger.json("【info】=> ", jsonObject.toString());

            String url = "";

            if(requestMethod == Request.Method.GET && jsonObject.length() != 0){
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> map = objectMapper.readValue(jsonObject.toString(), new TypeReference<Map<String, Object>>(){});
                StringBuilder sb = new StringBuilder();
                for(Map.Entry<String, Object> entry : map.entrySet()){
                    String key = entry.getKey();
                    String value = entry.getValue().toString();
                    sb.append(key).append("=").append(value).append("&");
                }
                url = "?" + sb.substring(0, sb.length() - 1);
            }
            Logger.json("【url】=> ", ApiConstants.BASE_API_URL + action + url);
            JsonObjectRequest request = new JsonObjectRequest(requestMethod, ApiConstants.BASE_API_URL + action + url, jsonObject, new
                    Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Logger.json("〓 接口返回 " + action + " 〓 ", response.toString());
                            postResult(true, response, action, "", view);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Logger.json("〓 接口返回 " + action + " 〓 ", error.toString());
//                    postResult(false, null, action, "请求失败，请检查网络", view);
                    queue = null;
                    getNewApi(action, jsonObject.toString(), view);
                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
//                    headers.put("appId", IAuthor.getAppId());
//                    headers.put("Authorization", IAuthor.getAccessToken(mContext));
                    headers.put("Authorization","");
//                    headers.put("clientid", IAuthor.getClientId() );
                    headers.put("appVersion", IAuthor.getVersion() );
                    Logger.i ("【headers】=> ", headers.toString());
                    return headers;
                }

                @Override
                public Request<?> setRetryPolicy(RetryPolicy retryPolicy) {
                    retryPolicy = new DefaultRetryPolicy(10 * 1000, 1, 1.0f);
                    return super.setRetryPolicy(retryPolicy);
                }

                @Override
                public String getBodyContentType() {
                    return "application/json";
                }

                // 如何解析服务端设置的cookie
                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    Response<JSONObject> superResponse = super.parseNetworkResponse(response);
                    if (sessionId == null) {
                        Map<String, String> responseHeaders = response.headers;
                        String rawCookies = responseHeaders.get("Set-Cookie");

                        if (rawCookies != null) {
                            sessionId = rawCookies.substring(11, rawCookies.indexOf(";"));
                        }

                    }
                    return superResponse;
                }

            };

            if (queue == null) {
                /*if (ApiConstants.BASE_API_URL.contains("www.etruckbank")) {
                    //etruckbank.com域名走信任所有证书
                    //FakeX509TrustManager.allowAllSSL();
                    queue = Volley.newRequestQueue(mContext);
                } else {
                    //truckbank.cn域名走信任指定证书
                    //queue = Volley.newRequestHttpsQueue(mContext, R.raw.truckbank);
                }*/
                queue = Volley.newRequestQueue(mContext);
            }

            queue.add(request);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void postData(final String action, final String info, final View view) {
        try {

            final HashMap<String, String> headers = new HashMap<>();
            headers.put("Cookie", "JSESSIONID=" + sessionId);

            String auth = IAuthor.getAuthParams(mContext);

            Logger.json("【Cookie】=> ", sessionId);
            Logger.json("【url】=> ", ApiConstants.BASE_API_URL + action);
            Logger.json("【auth】=> ", auth);
            Logger.json("【info】=> ", info);

            JSONObject jsonObject = new JSONObject();
            //jsonObject.put("auth", Des3.encode(auth, ApiConstants.CODE_KEY_VALUE));
            /*jsonObject.put("auth", auth);
            if (!TextUtil.isEmpty(info)) {
                //jsonObject.put("info", Des3.encode(info, ApiConstants.CODE_KEY_VALUE));
                jsonObject.put("info", info);
            } else {
                jsonObject.put("info", "");
            }*/


            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, ApiConstants.BASE_API_URL + action, jsonObject, new
                    Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Logger.json("〓 接口返回 " + action + " 〓 ", response.toString());
                            postResult(true, response, action, "", view);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Logger.json("〓 接口返回 " + action + " 〓 ", error.toString());
//                    postResult(false, null, action, "请求失败，请检查网络", view);
                    queue = null;
                    getNewApi(action, info, view);
                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
//                    headers.put("appId", IAuthor.getAppId());
//                    headers.put("Authorization", IAuthor.getAccessToken(mContext));
                    headers.put("appVersion", IAuthor.getVersion() );
                    return headers;
                }

                @Override
                public Request<?> setRetryPolicy(RetryPolicy retryPolicy) {
                    retryPolicy = new DefaultRetryPolicy(30 * 1000, 1, 1.0f);
                    return super.setRetryPolicy(retryPolicy);
                }

                @Override
                public String getBodyContentType() {
                    return "application/json";
                }

                // 如何解析服务端设置的cookie
                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    Response<JSONObject> superResponse = super.parseNetworkResponse(response);
                    if (sessionId == null) {
                        Map<String, String> responseHeaders = response.headers;
                        String rawCookies = responseHeaders.get("Set-Cookie");

                        if (rawCookies != null) {
                            sessionId = rawCookies.substring(11, rawCookies.indexOf(";"));
                        }

                    }
                    return superResponse;
                }

            };

            if (queue == null) {
                /*if (ApiConstants.BASE_API_URL.contains("www.etruckbank")) {
                    //etruckbank.com域名走信任所有证书
                    //FakeX509TrustManager.allowAllSSL();
                    queue = Volley.newRequestQueue(mContext);
                } else {
                    //truckbank.cn域名走信任指定证书
                    //queue = Volley.newRequestHttpsQueue(mContext, R.raw.truckbank);
                }*/
                queue = Volley.newRequestQueue(mContext);
            }

            queue.add(request);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 当默认API地址不通时，从网络获取API地址
     *
     * @param action
     * @param view
     */
    private void getNewApi(final String action, final String info, final View view) {
        //试错5次
        if (count>5){
            postResult(false, null, action, "请求失败，请检查网络", view);
            return;
        }
        count++;
        //获取API地址
        StringRequest sq = new StringRequest(Request.Method.GET, ApiConstants.GET_API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Logger.json("〓 新API地址返回 " + " 〓 ", response.toString());
                if (!TextUtils.isEmpty(response)) {
                    if (!response.endsWith("/")) {
                        response += "/";
                    }
                    ApiConstants.BASE_API_URL = response;

                    //重新请求
                    postData(action, info, view);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                postResult(false, null, action, "请求失败，请检查网络", view);
            }
        });

        RequestQueue rq = Volley.newRequestQueue(mContext);
        rq.add(sq);
    }


    /**
     * 处理接口返回的数据
     *
     * @param isSuccess 成功 、失败
     * @param response  成功的数据
     * @param action    成功的接口名
     * @param resMsg       失败的信息
     * @param view      触发接口的控件
     */
    @Override
    public abstract void postResult(boolean isSuccess, JSONObject response, String action, String resMsg, View view);


    @Override
    public void postDataA(final String action, final Object info, final View view){
        try {
            final HashMap<String, String> headers = new HashMap<>();
            headers.put("Cookie", "JSESSIONID=" + sessionId);

            String auth = IAuthor.getAuthParams(mContext);
            Logger.json("【Cookie】=> ", sessionId);
            Logger.json("【url】=> ", ApiConstants.BASE_API_URLA + action);
            Logger.json("【auth】=> ", auth);
            Logger.json("【info】=> ", info.toString());

            JSONObject jsonObject = new JSONObject();
            //jsonObject.put("auth", Des3.encode(auth, ApiConstants.CODE_KEY_VALUE));

            JSONObject a = new JSONObject();
            jsonObject.put("auth", a);
//            jsonObject.put("auth", auth);
            if (!TextUtil.isEmpty(info.toString())) {
                //jsonObject.put("info", Des3.encode(info, ApiConstants.CODE_KEY_VALUE));
                jsonObject.put("info", info);
            } else {
                jsonObject.put("info", "");
            }

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, ApiConstants.BASE_API_URLA + action, jsonObject, new
                    Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Logger.json("〓 接口返回 " + action + " 〓 ", response.toString());
                            postResult(true, response, action, "", view);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Logger.json("〓 接口返回 " + action + " 〓 ", error.toString());
//                    postResult(false, null, action, "请求失败，请检查网络", view);
                    queue = null;
                    getNewApi(action, info+"" ,view);
                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return headers;
                }

                @Override
                public Request<?> setRetryPolicy(RetryPolicy retryPolicy) {
                    retryPolicy = new DefaultRetryPolicy(30 * 1000, 1, 1.0f);
                    return super.setRetryPolicy(retryPolicy);
                }

                @Override
                public String getBodyContentType() {
                    return "application/json";
                }

                // 如何解析服务端设置的cookie
                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    Response<JSONObject> superResponse = super.parseNetworkResponse(response);
                    if (sessionId == null) {
                        Map<String, String> responseHeaders = response.headers;
                        String rawCookies = responseHeaders.get("Set-Cookie");

                        if (rawCookies != null) {
                            sessionId = rawCookies.substring(11, rawCookies.indexOf(";"));
                        }

                    }
                    return superResponse;
                }

            };

            if (queue == null) {
                /*if (ApiConstants.BASE_API_URL.contains("www.etruckbank")) {
                    //etruckbank.com域名走信任所有证书
                    //FakeX509TrustManager.allowAllSSL();
                    queue = Volley.newRequestQueue(mContext);
                } else {
                    //truckbank.cn域名走信任指定证书
                    //queue = Volley.newRequestHttpsQueue(mContext, R.raw.truckbank);
                }*/
                queue = Volley.newRequestQueue(mContext);
            }

            queue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postLocalData(final String action, final String info, final View view) {
        try {

            final HashMap<String, String> headers = new HashMap<>();
            headers.put("Cookie", "JSESSIONID=" + sessionId);

            String auth = IAuthor.getAuthParams(mContext);

            Logger.json("【Cookie】=> ", sessionId);
            Logger.json("【url】=> ", ApiConstants.LOCAL_API_URL + action);
            Logger.json("【auth】=> ", auth);
            Logger.json("【info】=> ", info);

            JSONObject jsonObject = new JSONObject();
            //jsonObject.put("auth", Des3.encode(auth, ApiConstants.CODE_KEY_VALUE));
            jsonObject.put("auth", auth);
            if (!TextUtil.isEmpty(info)) {
                //jsonObject.put("info", Des3.encode(info, ApiConstants.CODE_KEY_VALUE));
                jsonObject.put("info", info);
            } else {
                jsonObject.put("info", "");
            }

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, ApiConstants.LOCAL_API_URL + action, jsonObject, new
                    Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Logger.json("〓 接口返回 " + action + " 〓 ", response.toString());
                            postResult(true, response, action, "", view);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Logger.json("〓 接口返回 " + action + " 〓 ", error.toString());
//                    postResult(false, null, action, "请求失败，请检查网络", view);
                    queue = null;
                    getNewApi(action, info, view);
                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return headers;
                }

                @Override
                public Request<?> setRetryPolicy(RetryPolicy retryPolicy) {
                    retryPolicy = new DefaultRetryPolicy(30 * 1000, 1, 1.0f);
                    return super.setRetryPolicy(retryPolicy);
                }

                @Override
                public String getBodyContentType() {
                    return "application/json";
                }

                // 如何解析服务端设置的cookie
                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    Response<JSONObject> superResponse = super.parseNetworkResponse(response);
                    if (sessionId == null) {
                        Map<String, String> responseHeaders = response.headers;
                        String rawCookies = responseHeaders.get("Set-Cookie");

                        if (rawCookies != null) {
                            sessionId = rawCookies.substring(11, rawCookies.indexOf(";"));
                        }

                    }
                    return superResponse;
                }

            };

            if (queue == null) {
                /*if (ApiConstants.BASE_API_URL.contains("www.etruckbank")) {
                    //etruckbank.com域名走信任所有证书
                    //FakeX509TrustManager.allowAllSSL();
                    queue = Volley.newRequestQueue(mContext);
                } else {
                    //truckbank.cn域名走信任指定证书
                    //queue = Volley.newRequestHttpsQueue(mContext, R.raw.truckbank);
                }*/
                queue = Volley.newRequestQueue(mContext);
            }

            queue.add(request);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
