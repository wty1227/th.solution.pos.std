package cn.th.phonerf.dal;

import android.database.Cursor;

import java.util.ArrayList;

import cn.th.phonerf.model.MerchantShop;
import cn.th.phonerf.utils.DBUtil;

public class MerchantShopDal {

    public ArrayList<MerchantShop> selectByMerchantId(Integer merchantId){
        try {
            ArrayList<MerchantShop> lists = new ArrayList<MerchantShop>();
            String str = "select * from merchant_shop where merchant_id = ? order by oper_date  desc";
            Cursor cursor = DBUtil.dbItemExec.rawQuery(str, new String[]{ String.valueOf( merchantId )});
            if(cursor.moveToFirst()){
                do{
                    MerchantShop entity = new MerchantShop();
                    entity.setShopId(cursor.getString(cursor.getColumnIndex("shop_id")));
                    entity.setShopName(cursor.getString(cursor.getColumnIndex("shop_name")));
                    entity.setMerchantId(cursor.getInt(cursor.getColumnIndex("merchant_id")));
                    entity.setApiUrl(cursor.getString(cursor.getColumnIndex("api_url")));
                    entity.setClearFlag(cursor.getString(cursor.getColumnIndex("clear_flag")));

                    entity.setDeployFlag(cursor.getString(cursor.getColumnIndex("deploy_flag")));
                    entity.setJdbcDriver(cursor.getString(cursor.getColumnIndex("jdbc_driver")));
                    entity.setJdbcUrl(cursor.getString(cursor.getColumnIndex("jdbc_url")));
                    entity.setJdbcUsername(cursor.getString(cursor.getColumnIndex("jdbc_username")));
                    entity.setJdbcPassword(cursor.getString(cursor.getColumnIndex("jdbc_password")));

                    lists.add(entity);
                }while(cursor.moveToNext());
            }
            cursor.close();
            return lists;
        }catch (Exception ex){
            throw ex;
        }

    }

    public void updateLoginDate(Integer merchantId, String shopId){
        try {
            String sql = "update merchant_shop set oper_date =  datetime(CURRENT_TIMESTAMP,'localtime') where merchant_id = ? and shop_id = ?";
            DBUtil.dbItemExec.execSQL(sql, new String[]{String.valueOf(merchantId), shopId});
        }catch (Exception ex){
            throw ex;
        }
    }
}
