package cn.th.phonerf.dal;

import android.database.Cursor;

import java.util.ArrayList;

import cn.th.phonerf.model.SysSystem;
import cn.th.phonerf.utils.DBUtil;

public class SystemDal {

    public ArrayList<SysSystem> selectByMerchantId(Integer merchantId){
        try {
            ArrayList<SysSystem> lists = new ArrayList<SysSystem>();
            String str = "select * from sys_system where merchant_id = ? ";
            Cursor cursor = DBUtil.dbItemExec.rawQuery(str, new String[]{ String.valueOf( merchantId )});
            if(cursor.moveToFirst()){
                do{
                    SysSystem entity = new SysSystem();
                    entity.setSysVarId(cursor.getString(cursor.getColumnIndex("sys_var_id")));
                    entity.setSysVarValue(cursor.getString(cursor.getColumnIndex("sys_var_value")));
                    entity.setMerchantId(cursor.getInt(cursor.getColumnIndex("merchant_id")));
                    entity.setSysVarName(cursor.getString(cursor.getColumnIndex("sys_var_name")));

                    lists.add(entity);
                }while(cursor.moveToNext());
            }
            cursor.close();
            return lists;
        }catch (Exception ex){
            throw ex;
        }

    }

    public SysSystem selectById(Integer merchantId, String sysVarId){
        try {
            SysSystem entity = null;
            String str = "select * from sys_system where merchant_id = ? and sys_var_id = ? ";
            Cursor cursor = DBUtil.dbItemExec.rawQuery(str, new String[]{ String.valueOf( merchantId ), sysVarId});
            if(cursor.moveToFirst()){
                do{
                    entity = new SysSystem();
                    entity.setSysVarId(cursor.getString(cursor.getColumnIndex("sys_var_id")));
                    entity.setSysVarValue(cursor.getString(cursor.getColumnIndex("sys_var_value")));
                    entity.setMerchantId(cursor.getInt(cursor.getColumnIndex("merchant_id")));
                    entity.setSysVarName(cursor.getString(cursor.getColumnIndex("sys_var_name")));

                }while(cursor.moveToNext());
            }
            cursor.close();
            return entity;
        }catch (Exception ex){
            throw ex;
        }

    }
}
