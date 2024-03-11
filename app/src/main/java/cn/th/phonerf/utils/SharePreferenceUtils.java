package cn.th.phonerf.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharePreferenceUtils {

	public static final String OPER_ID = "OPER_ID";	//用户ID
	public static final String OPER_NAME = "OPER_NAME";	//用户ID
	public static final String OPER_PASS = "OPER_PASS";	//用户ID
	public static final String BRANCH_NO = "BRANCH_NO";	//用户ID
	public static final String STORE_NO = "STORE_NO";	//用户ID

	public static final String LOGIN_ID = "LOGIN_ID";	//用户ID
	private static final String SETTING_INFO = "SETTING_INFO";	//文件名
	private static final String BANNER_INFO = "BANNER_INFO";	//文件名
	private static final String BACKGROUND_INFO = "BACKGROUND_INFO";	//文件名
	public static final String MOBILE = "mobile";	//用户
	public static final String ID = "ID";	//文件名
	public static final String CODE = "code";	//邀请码
	public static final String AUTHOR = "author";	//实名
	public static final String NAME = "name";	//姓名
	public static final String IDENTIFY = "identify";	//身份证
	public static final String ICON = "icon";	//头像
	public static final String VERSION = "VersionType";	//强制版本号
	public static final String BANNER = "banner";	//banner
	public static final String IP = "ip";	//ip
	public static final String MAC = "mac";	//MAC
	public static final String AREA = "area";	//区域
	public static final String COUNTRY="country";//国家
	public static final String REGION="region";//省份
	public static final String CITY="city";//城市
	public static final String ISP="isp";//请求来源运营商
	public static final String MB_SCREEN="mb_screen";//手机分辨率
	public static final String GUIDE="guide";//是否第一次启动APP
	public static final String APPTIME="app_time";//更新APP列表时间
	public static final String TK_VALUE="tk_value";//token值
	public static final String TK_TIME="tk_time";//token失效时间
	public static final String JPUSH_ID="jpush_id";//极光推送ID（数据库存在的）
	public static final String LONGITUDE="longitude";//经度
	public static final String LATITUDE="latitude";//纬度
	public static final String ADDRESS="address";//定位位置
	public static final String TIME="time";//系统时间

	public static final String MERCHANTER_ID="MERCHANTER_ID";//商户id
	public static final String MERCHANTER_NO="MERCHANTER_NO";//商户代码
	public static final String BT_ADDR="BT_ADDR";//蓝牙地址

	public static final String FLOW_NO="FLOW_NO";//流水号

	public static final String BASE_API_URL="BASE_API_URL";//后台地址
	public static final String BRANCH_NAME="BRANCH_NAME";//
	public static final String PRINT_NAME="PRINT_NAME";//
	/**
	 * 存字符
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void setValue(Context context, String key, String value){
		SharedPreferences setting = context.getSharedPreferences(SETTING_INFO, Context.MODE_PRIVATE);
		Editor editor = setting.edit();
		editor.putString(key, value);
		editor.commit();
		
	}
	

	/**
	 * 取字符
	 * @param context
	 * @param key	 * @return
	 */
	public static String getValue(Context context, String key, String value){
		SharedPreferences setting = context.getSharedPreferences(SETTING_INFO, Context.MODE_PRIVATE);
		String result = setting.getString(key, value);
		return result;
		
	}
	
	/**
	 * 存BANNER
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void setBanner(Context context, String key, String value){
		SharedPreferences setting = context.getSharedPreferences(BANNER_INFO, Context.MODE_PRIVATE);
		Editor editor = setting.edit();
		editor.putString(key, value);
		editor.commit();
		
	}
	
	
	/**
	 * 取BANNER
	 * @param context
	 * @param key
	 * @return
	 */
	public static String getBanner(Context context, String key, String value){
		SharedPreferences setting = context.getSharedPreferences(BANNER_INFO, Context.MODE_PRIVATE);
		String result = setting.getString(key, value);
		return result;
		
	}
	
	
	/**
	 * 是否第一次进入APP引导页
	 * 存boolean
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void setWelcome(Context context, String key, boolean value){
		SharedPreferences setting = context.getSharedPreferences(GUIDE, Context.MODE_PRIVATE);
		Editor editor = setting.edit();
		editor.putBoolean(key, value);
		editor.commit();		
	}
	/**
	 * 是否第一次进入APP引导页
	 * 取boolean
	 * @param context
	 * @param key
	 * @return
	 */
	public static boolean getWelcome(Context context, String key){
		SharedPreferences setting = context.getSharedPreferences(GUIDE, Context.MODE_PRIVATE);
		boolean value = setting.getBoolean(key, true);
		return value;
		
	}
		/**
	 * 存boolean
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void setBoolean(Context context, String key, boolean value){	SharedPreferences setting = context.getSharedPreferences(BACKGROUND_INFO, Context.MODE_PRIVATE);
		Editor editor = setting.edit();
		editor.putBoolean(key, value);
		editor.commit();
		
	}
	
	
	/**
	 * 取boolean
	 * @param context
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(Context context, String key){
		SharedPreferences setting = context.getSharedPreferences(BACKGROUND_INFO, Context.MODE_PRIVATE);
		boolean value = setting.getBoolean(key, false);
		return value;
		
	}
	/**
	 * 存long
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void setLong(Context context, String key, long value){
		SharedPreferences setting = context.getSharedPreferences(BACKGROUND_INFO, Context.MODE_PRIVATE);
		Editor editor = setting.edit();
		editor.putLong(key, value);
		editor.commit();
		
	}
	/**
	 * 取boolean
	 * @param context
	 * @param key
	 * @return
	 */
	public static long getLong(Context context, String key){
		SharedPreferences setting = context.getSharedPreferences(BACKGROUND_INFO, Context.MODE_PRIVATE);
		long value = setting.getLong(key, 0);
		
		return value;
		
	}
	
	
}
