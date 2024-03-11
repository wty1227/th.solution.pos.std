package cn.th.phonerf.dal;

import android.database.Cursor;

import java.util.ArrayList;

import cn.th.phonerf.model.TRmSaleman;
import cn.th.phonerf.utils.DBUtil;

public class t_rm_sale_manDal {

    //查询营业员是否存在
    public TRmSaleman selectBigSaleman(String SaleID){
        String strSql = String.format("select * from t_rm_saleman where sale_id like '%s'","%"+SaleID+"%");
        try {
            Cursor cursor = DBUtil.dbItemExec.rawQuery(strSql, null);
            TRmSaleman tRmSaleman = new TRmSaleman();
            if(cursor.getCount() == 0) {
                cursor.close();
                return tRmSaleman;
            }
            if (cursor.moveToFirst()) {
                do{
                    //商品list
                    tRmSaleman.setBranchNo(cursor.getString(cursor.getColumnIndex("branch_no")));
                    tRmSaleman.setSaleId(cursor.getString(cursor.getColumnIndex("sale_id")));
                    tRmSaleman.setSaleName(cursor.getString(cursor.getColumnIndex("sale_name")));
                    tRmSaleman.setMemo(cursor.getString(cursor.getColumnIndex("memo")));
                    tRmSaleman.setReturnRate(cursor.getDouble(cursor.getColumnIndex("return_rate")));
                    tRmSaleman.setSaleDuty(cursor.getString(cursor.getColumnIndex("sale_duty")));
                    tRmSaleman.setSaleDutyRate(cursor.getDouble(cursor.getColumnIndex("sale_duty_rate")));
                }while (cursor.moveToNext());
            }
            cursor.close();
            return tRmSaleman;

        } catch (Exception e) {
            throw e;
        }
    }

    //查询营业员是否存在
    public ArrayList<TRmSaleman> selectAll(){
        ArrayList<TRmSaleman> list = new ArrayList<>();
        String strSql = String.format("select * from t_rm_saleman");
        try {
            Cursor cursor = DBUtil.dbItemExec.rawQuery(strSql, null);


            if (cursor.moveToFirst()) {
                do{
                    TRmSaleman tRmSaleman = new TRmSaleman();
                    //商品list
                    tRmSaleman.setBranchNo(cursor.getString(cursor.getColumnIndex("branch_no")));
                    tRmSaleman.setSaleId(cursor.getString(cursor.getColumnIndex("sale_id")));
                    tRmSaleman.setSaleName(cursor.getString(cursor.getColumnIndex("sale_name")));
                    tRmSaleman.setMemo(cursor.getString(cursor.getColumnIndex("memo")));
                    tRmSaleman.setReturnRate(cursor.getDouble(cursor.getColumnIndex("return_rate")));
                    tRmSaleman.setSaleDuty(cursor.getString(cursor.getColumnIndex("sale_duty")));
                    tRmSaleman.setSaleDutyRate(cursor.getDouble(cursor.getColumnIndex("sale_duty_rate")));
                    list.add(tRmSaleman);
                }while (cursor.moveToNext());
            }
            cursor.close();
            return list;

        } catch (Exception e) {
            throw e;
        }
    }

    public ArrayList<TRmSaleman> selectFromPayFlow(String cashierId, String dtFrom, String dtTo){
        ArrayList<TRmSaleman> list = new ArrayList<>();
        String strSql = String.format("select * from t_rm_saleman where  ");
        Cursor cursor = null;
        try {
            cursor = DBUtil.dbItemExec.rawQuery(strSql, null);


            if (cursor.moveToFirst()) {
                do{
                    TRmSaleman tRmSaleman = new TRmSaleman();
                    //商品list
                    tRmSaleman.setBranchNo(cursor.getString(cursor.getColumnIndex("branch_no")));
                    tRmSaleman.setSaleId(cursor.getString(cursor.getColumnIndex("sale_id")));
                    tRmSaleman.setSaleName(cursor.getString(cursor.getColumnIndex("sale_name")));
                    tRmSaleman.setMemo(cursor.getString(cursor.getColumnIndex("memo")));
                    tRmSaleman.setReturnRate(cursor.getDouble(cursor.getColumnIndex("return_rate")));
                    tRmSaleman.setSaleDuty(cursor.getString(cursor.getColumnIndex("sale_duty")));
                    tRmSaleman.setSaleDutyRate(cursor.getDouble(cursor.getColumnIndex("sale_duty_rate")));
                    list.add(tRmSaleman);
                }while (cursor.moveToNext());
            }
            cursor.close();
            return list;

        } catch (Exception ex) {
            if(cursor != null)
                cursor.close();
            throw ex;
        }
    }

}
