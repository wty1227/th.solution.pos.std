package cn.th.phonerf.dal;

import android.database.Cursor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.json.JSONException;

import java.util.ArrayList;

import cn.th.phonerf.model.BaseEntity;
import cn.th.phonerf.model.TBdItemInfo;
import cn.th.phonerf.model.t_bd_item_info;
import cn.th.phonerf.utils.DBUtil;

/**
 * Created by FW on 2016-10-01.
 */

public class t_bd_item_infoDal {

    public void deleteList(){
        try {
            DBUtil.dbItemExec.beginTransaction();
            String sql = "delete from t_bd_item_info";
            DBUtil.dbItemExec.execSQL(sql);
            sql = "delete from t_bd_item_barcode";
            DBUtil.dbItemExec.execSQL(sql);
            DBUtil.dbItemExec.setTransactionSuccessful();
        }catch (Exception e) {
            throw e;
        }finally {
            DBUtil.dbItemExec.endTransaction();
        }
    }



    public void insertBarcode(JSONArray data) throws JSONException {
        try {
            //开启事物

            final String sqlInsert = "insert or replace into t_bd_item_barcode" +
                    "(item_no, item_barcode)" +
                    "values(?,?)";
            final String[] lstParam = {
                    "item_no", "item_barcode"
            };
            DBUtil.dbItemExec.beginTransaction();
            for(int i = 0; i < data.size(); i++){
                JSONObject node = data.getJSONObject(i);
                DBUtil.dbItemExec.execSQL(sqlInsert, DBUtil.GetSqliteParam(node, lstParam));
            }
            DBUtil.dbItemExec.setTransactionSuccessful();

        } catch (JSONException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }finally {
            DBUtil.dbItemExec.endTransaction();
        }
    }

    public void insertList(JSONArray data) throws JSONException {
        try {
            //开启事物

            /*final String sqlInsert = "insert or replace into t_bd_item_info" +
                    "(item_no, item_subno, item_name, sale_price, price,  unit_no, package_spec, item_size, product_area, level)" +
                    "values(?,?,?,?,?,?,?,?,?,?)";
            final String[] lstParam = {
                    "item_no", "item_subno", "item_name", "sale_price", "price", "unit_no", "package_spec", "item_size", "product_area", "level"
            };*/

            final String sqlInsert = "insert or replace into t_bd_item_info" +
                    "(item_no,  item_name, sale_price)" +
                    "values(?,?,?)";
            final String[] lstParam = {
                    "item_no",  "item_name", "sale_price"
            };
            DBUtil.dbItemExec.beginTransaction();

            String sql = "delete from t_bd_item_info";
            DBUtil.dbItemExec.execSQL(sql);

            for(int i = 0; i < data.size(); i++){
                JSONObject node = data.getJSONObject(i);
                DBUtil.dbItemExec.execSQL(sqlInsert, DBUtil.GetSqliteParam(node, lstParam));
            }
            DBUtil.dbItemExec.setTransactionSuccessful();
        } catch (JSONException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }finally {
            DBUtil.dbItemExec.endTransaction();
        }
    }

    public int getCount() throws Exception {

        final String sql = "select count(1) from t_bd_item_info";

        try {
            Cursor cursor = DBUtil.dbItemExec.rawQuery(sql, null);
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            cursor.close();
            return count;
        } catch (Exception e) {
            throw e;
        }
    }


    public ArrayList<t_bd_item_info> Select(String query){
        ArrayList<t_bd_item_info> lists = new ArrayList<t_bd_item_info>();
        try {
            String barcodes = "''";
            String str = "select item_no from t_bd_item_barcode where rtrim(item_barcode) = ?";
            Cursor cursor = DBUtil.dbItemExec.rawQuery(str, new String[]{query});
            if(cursor.moveToFirst()){
                do{
                    //barcodes = String.format("%s,%s", barcodes, cursor.getString(cursor.getColumnIndex("item_no")));
                    query = cursor.getString(cursor.getColumnIndex("item_no")).trim();
                    break;
                }while(cursor.moveToNext());
            }
            cursor.close();

            str = "SELECT info.item_no, item_subno, item_name, sale_price, price, unit_no, package_spec, item_size, product_area, level\n" +
                    "FROM t_bd_item_info info \n" +
                    "WHERE (rtrim(info.item_no) = ?) OR (rtrim(info.item_subno) = ?) \n" + //OR (rtrim(info.item_rem) = ?) OR (rtrim(info.item_no) IN (?) )\n" +
                    "ORDER BY info.item_no ASC\n";
            cursor = DBUtil.dbItemExec.rawQuery(str, new String[]{query, query});
            if(cursor.moveToFirst()){
                do{
                    t_bd_item_info entity = new t_bd_item_info();
                    entity.setItem_no(cursor.getString(cursor.getColumnIndex("item_no")));
                    entity.setItem_subno(cursor.getString(cursor.getColumnIndex("item_subno")));
                    entity.setItem_name(cursor.getString(cursor.getColumnIndex("item_name")));
                    entity.setItem_size(cursor.getString(cursor.getColumnIndex("item_size")));
                    entity.setUnit_no(cursor.getString(cursor.getColumnIndex("unit_no")));
                    entity.setPackage_spec(cursor.getString(cursor.getColumnIndex("package_spec")));

                    entity.setKey(entity.getItem_no());
                    entity.setValue(entity.getItem_name());
                    lists.add(entity);
                }while(cursor.moveToNext());
            }
            cursor.close();
            return lists;
        }catch (Exception e){
            throw e;
        }
    }

    public ArrayList<BaseEntity> SelectItem(String query){
        ArrayList<BaseEntity> lists = new ArrayList<BaseEntity>();
        try {
            String str = "SELECT info.item_no, item_subno, item_subname item_name, item_rem, item_clsno, item_brand, item_brandname, combine_sta, STATUS\n" +
                    ",display_flag, sale_flag, Price, sale_price, base_price, vip_price, purchase_spec, unit_no, item_size, main_supcust, sale_min_price\n" +
                    "FROM t_bd_item_info info \n" +
                    "WHERE ( (rtrim(info.item_no) = ?) OR (rtrim(info.item_subno) = ?) OR (rtrim(info.item_rem) = ?) OR \n" +
                        "(\n" +
                        "rtrim(info.item_no) IN (\n" +
                        "SELECT rtrim(item_no)\n" +
                        "FROM t_bd_item_barcode\n" +
                        "WHERE rtrim(item_barcode) = ?\n" +
                            ")\n" +
                        ") )\n" +
                    "ORDER BY info.item_no ASC\n";
            Cursor cursor = DBUtil.dbItemExec.rawQuery(str, new String[]{query});
            if(cursor.moveToFirst()){
                do{
                    t_bd_item_info entity = new t_bd_item_info();
                    entity.setItem_no(cursor.getString(cursor.getColumnIndex("item_no")));
                    entity.setItem_subno(cursor.getString(cursor.getColumnIndex("item_subno")));
                    entity.setItem_name(cursor.getString(cursor.getColumnIndex("item_name")));

                    entity.setKey(entity.getItem_no());
                    entity.setValue(entity.getItem_name());
                    lists.add(entity);
                }while(cursor.moveToNext());
            }
            cursor.close();
            return lists;
        }catch (Exception e){
            throw e;
        }
    }

    public ArrayList<TBdItemInfo> Select(){
        ArrayList<TBdItemInfo> lists = new ArrayList<TBdItemInfo>();
        try {
            String str = "SELECT info.item_no, item_name, sale_price " +
                    "FROM t_bd_item_info info " +
                    "ORDER BY info.item_no ASC\n";
            Cursor cursor = DBUtil.dbItemExec.rawQuery(str,null);
            if(cursor.moveToFirst()){
                do{
                    TBdItemInfo entity = new TBdItemInfo();
                    entity.setItemNo(cursor.getString(cursor.getColumnIndex("item_no")));
                    entity.setSalePrice(cursor.getDouble(cursor.getColumnIndex("sale_price")));
                    entity.setItemName(cursor.getString(cursor.getColumnIndex("item_name")));

                    lists.add(entity);
                }while(cursor.moveToNext());
            }
            cursor.close();
            return lists;
        }catch (Exception e){
            throw e;
        }
    }

}
