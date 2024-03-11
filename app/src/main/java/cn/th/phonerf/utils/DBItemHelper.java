package cn.th.phonerf.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016-09-26.
 */
public class DBItemHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "item.db";
    private static final int DATABASE_VERSION = 1;

    private Context context;

    public DBItemHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try
        {
            String strSql = "";
            strSql = " create table t_bd_item_info (" +
                    "[item_no] [varchar](40)  NULL," +

                    "[item_name] [varchar](40)  NULL," +

                    "[sale_price] decimal(16, 4) NULL," +

                    "primary key (item_no))";
            try
            {
                db.execSQL(strSql);
            }
            catch(Exception ex)
            {
            }
        }
        catch (Exception exception)
        {
            //throw new Exception("本地资料库创建表失败！\r\n" + exception.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*String str = "";
        if(newVersion <= 22){
            //标签
            try {

            } catch (Exception ex) {
            }
        }*/
    }
}
