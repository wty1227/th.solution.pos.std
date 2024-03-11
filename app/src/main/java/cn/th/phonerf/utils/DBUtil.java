package cn.th.phonerf.utils;

import android.database.sqlite.SQLiteDatabase;

import com.alibaba.fastjson.JSONObject;

import org.json.JSONException;

import java.util.ArrayList;

public class DBUtil {
    public static DBItemHelper dbItemHelper;
    public static SQLiteDatabase dbItemExec;

    public static DBSaleHelper dbSaleHelper;
    public static SQLiteDatabase dbSaleExec;

    public static Object[] GetSqliteParam(JSONObject node, String[] lstParam) throws JSONException {

        ArrayList<String> list = new ArrayList<String>();
        for (String str : lstParam)
        {
            //Log.d("download", str);

            list.add( node.getString(str));
        }
        return list.toArray();
    }
}
