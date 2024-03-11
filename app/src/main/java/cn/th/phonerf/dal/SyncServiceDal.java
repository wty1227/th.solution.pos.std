package cn.th.phonerf.dal;


import android.database.Cursor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cn.th.phonerf.model.TBdItemCls;
import cn.th.phonerf.model.TBdItemInfo;
import cn.th.phonerf.model.TRmPayflow;
import cn.th.phonerf.model.TRmSaleflow;
import cn.th.phonerf.model.TRmSaleman;
import cn.th.phonerf.utils.DBUtil;

public class SyncServiceDal {

    //把所有商品信息插入数据库
    public void insertList(JSONArray data, String posId, String tableName,  Long num) throws JSONException{
        try {
            //开启事物
            String[] lstParam = {"item_no", "item_subno", "item_name", "item_subname", "item_clsno", "item_brand", "unit_no", "combine_sta", "status", "en_dis", "en_gift", "change_price", "sup_rate", "item_rem", "vip_price", "vip_price2",
                    "vip_price3", "vip_price4", "vip_price5", "sale_min_price", "vip_acc_flag", "vip_acc_num", "sale_flag", "measure_flag", "promote_flag", "sale_price", "allow_flag", "price", "dpfm_type", "return_rate", "item_picture", "valid_days",
                    "item_size", "purchase_spec", "sale_tax", "purchase_tax", "main_supcust", "item_stock"};

            StringBuilder builder = null;
            StringBuilder builder2 = null;
            StringBuilder builder3 = new StringBuilder();
            builder3.append("insert or ignore into t_bd_item_info(item_no,item_subno,item_name,item_subname,item_clsno,item_brand,unit_no,combine_sta,status,en_dis,en_gift,change_price,sup_rate,item_rem,valid_days,item_size,purchase_spec,sale_tax,purchase_tax,main_supcust,item_stock, ");
            builder3.append("vip_price,vip_price2,vip_price3,vip_price4,vip_price5,sale_min_price,vip_acc_flag,vip_acc_num,sale_flag,measure_flag,promote_flag,sale_price,allow_flag,price,dpfm_type,return_rate,item_picture) ");
            //builder3.append("values(@item_no,@item_subno,@item_name,@item_subname,@item_clsno,@item_brand,@unit_no,@combine_sta,@status,@en_dis,@en_gift,@change_price,@sup_rate,@item_rem,@valid_days,@item_size,@purchase_spec,@sale_tax,@purchase_tax,@main_supcust,@item_stock,");
            //builder3.append("@vip_price,@vip_price2,@vip_price3,@vip_price4,@vip_price5,@sale_min_price,@vip_acc_flag,@vip_acc_num,@sale_flag,@measure_flag,@promote_flag,@sale_price,@allow_flag,@price,@dpfm_type,@return_rate,@item_picture) ");
            builder3.append("values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            DBUtil.dbItemExec.beginTransaction();

            //1.删除本地数据（下载范围内数据）
            builder = new StringBuilder();
            builder.append("'0'");
            for(int i = 0; i < data.size(); i++){
                JSONObject node = data.getJSONObject(i);
                builder.append( String.format(",'%s'",  node.getString("item_no")).trim() );
            }
            String strSql = String.format("delete from t_bd_item_info where 1=1 and trim(item_no) in ( %s ) ", builder.toString());
            DBUtil.dbItemExec.execSQL(strSql);
            //写入数据
            for(int i = 0; i < data.size(); i++){
                JSONObject node = data.getJSONObject(i);
                String changeType = node.getString("change_type");
                if(node.getString("change_type").trim().toLowerCase().equals("i"))
                    DBUtil.dbItemExec.execSQL(builder3.toString(), DBUtil.GetSqliteParam(node, lstParam));
            }
            //更新断点 UpdateBreakPoint
            updateBreakPoint(posId, tableName, num);
            DBUtil.dbItemExec.setTransactionSuccessful();
        } catch (JSONException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }finally {
            DBUtil.dbItemExec.endTransaction();
        }
    }

    //把所有类别信息插入数据库
    public void insertCls(JSONArray data, String posId, String tableName,  Long num){
        try {
            //开启事物
            String[] lstParam = {"item_clsno", "item_clsname" ,"item_flag","cls_parent","display_flag", "return_rate", "comp_rate", "trans_flag"};

            StringBuilder builder = null;
            StringBuilder builder2 = null;
            StringBuilder builder3 = new StringBuilder();
            builder3.append("insert or ignore into t_bd_item_cls( item_clsno, item_clsname, item_flag, cls_parent, display_flag, return_rate, comp_rate, trans_flag)");
            builder3.append("values(?,?,?,?,?,?,?,?)");
            DBUtil.dbItemExec.beginTransaction();

            //1.删除本地数据（下载范围内数据）
            builder = new StringBuilder();
            builder.append("'0'");
            for(int i = 0; i < data.size(); i++){
                JSONObject node = data.getJSONObject(i);
                builder.append( String.format(",'%s'",  node.getString("item_clsno")).trim() );
            }
            String strSql = String.format("delete from t_bd_item_cls where 1=1 and trim(item_clsno) in ( %s ) ", builder.toString());
            DBUtil.dbItemExec.execSQL(strSql);
            //写入数据
            for(int i = 0; i < data.size(); i++){
                JSONObject node = data.getJSONObject(i);
                String changeType = node.getString("change_type");
                if(node.getString("change_type").trim().toLowerCase().equals("i"))
                    DBUtil.dbItemExec.execSQL(builder3.toString(), DBUtil.GetSqliteParam(node, lstParam));
            }
            //更新断点 UpdateBreakPoint
            updateBreakPoint(posId, tableName, num);
            DBUtil.dbItemExec.setTransactionSuccessful();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            DBUtil.dbItemExec.endTransaction();
        }
    }

    //把所有营业员信息插入数据库
    public void insertSaleman(JSONArray data, String posId, String tableName,  Long num){
        try {
            //开启事物
            String[] lstParam = {"branch_no", "sale_id" ,"sale_name","sale_status","memo", "return_rate", "sale_duty", "sale_duty_rate"};

            StringBuilder builder = null;
            StringBuilder builder2 = null;
            StringBuilder builder3 = new StringBuilder();
            builder3.append("insert or ignore into t_rm_saleman( branch_no, sale_id, sale_name, sale_status, memo, return_rate, sale_duty, sale_duty_rate)");
            builder3.append("values(?,?,?,?,?,?,?,?)");
            DBUtil.dbItemExec.beginTransaction();

            //1.删除本地数据（下载范围内数据）
            builder = new StringBuilder();
            builder.append("'0'");
            for(int i = 0; i < data.size(); i++){
                JSONObject node = data.getJSONObject(i);
                builder.append( String.format(",'%s'",  node.getString("sale_id")).trim() );
            }
            String strSql = String.format("delete from t_rm_saleman where 1=1 and trim(sale_id) in ( %s ) ", builder.toString());
            DBUtil.dbItemExec.execSQL(strSql);
            //写入数据
            for(int i = 0; i < data.size(); i++){
                JSONObject node = data.getJSONObject(i);
                String changeType = node.getString("change_type");
                if(node.getString("change_type").trim().toLowerCase().equals("i"))
                    DBUtil.dbItemExec.execSQL(builder3.toString(), DBUtil.GetSqliteParam(node, lstParam));
            }
            //更新断点 UpdateBreakPoint
            updateBreakPoint(posId, tableName, num);
            DBUtil.dbItemExec.setTransactionSuccessful();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            DBUtil.dbItemExec.endTransaction();
        }
    }


    //把所有价格信息插入数据库
    public void insertPrice(JSONArray data, String posId, String tableName,  Long num){
        try {
            //开启事物
            String[] lstParam = {"branch_no", "item_no" ,"price","base_price","sale_price", "vip_price", "com_flag", "scheme_price","vip_price2","vip_price3","vip_price4","vip_price5","price_list","sale_flag","base_price1","base_price2","base_price3","base_price4","base_price5","base_price6","base_price7","base_price8"};

            StringBuilder builder = null;
            StringBuilder builder2 = null;
            StringBuilder builder3 = new StringBuilder();
            builder3.append("insert or ignore into t_pc_branch_price( branch_no, item_no, price, base_price, sale_price, vip_price, com_flag, scheme_price, vip_price2, vip_price3, vip_price4, vip_price5, price_list, sale_flag, base_price1, base_price2, base_price3, base_price4, base_price5, base_price6, base_price7, base_price8)");
            builder3.append("values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            DBUtil.dbItemExec.beginTransaction();

            //1.删除本地数据（下载范围内数据）
            builder = new StringBuilder();
            builder.append("'0'");
            for(int i = 0; i < data.size(); i++){
                JSONObject node = data.getJSONObject(i);
                builder.append( String.format(",'%s'",  node.getString("item_no")).trim());
            }
            String strSql = String.format("delete from t_pc_branch_price where 1=1 and trim(item_no) in ( %s ) ", builder.toString());
            DBUtil.dbItemExec.execSQL(strSql);
            //写入数据
            for(int i = 0; i < data.size(); i++){
                JSONObject node = data.getJSONObject(i);
                String changeType = node.getString("change_type");
                if(node.getString("change_type").trim().toLowerCase().equals("i"))
                    DBUtil.dbItemExec.execSQL(builder3.toString(), DBUtil.GetSqliteParam(node, lstParam));
            }
            //更新断点 UpdateBreakPoint
            updateBreakPoint(posId, tableName, num);
            DBUtil.dbItemExec.setTransactionSuccessful();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            DBUtil.dbItemExec.endTransaction();
        }
    }

    //把所有价格信息插入数据库
    public void insertPayment(JSONArray data, String posId, String tableName,  Long num){
        try {
            //开启事物
            String[] lstParam = {"pay_way", "pay_flag" ,"pay_name","rate","default_amt", "acc_flag", "jf_flag", "pay_memo"};

            StringBuilder builder = null;
            StringBuilder builder2 = null;
            StringBuilder builder3 = new StringBuilder();
            builder3.append("insert or ignore into t_bd_payment_info( pay_way,pay_flag,pay_name,rate,default_amt,acc_flag,jf_flag,pay_memo)");
            builder3.append("values(?,?,?,?,?,?,?,?)");
            DBUtil.dbItemExec.beginTransaction();

            //1.删除本地数据（下载范围内数据）
            builder = new StringBuilder();
            builder.append("'0'");
            for(int i = 0; i < data.size(); i++){
                JSONObject node = data.getJSONObject(i);
                builder.append( String.format(",'%s'",  node.getString("pay_way")).trim());
            }
            String strSql = String.format("delete from t_bd_payment_info where 1=1 and trim(pay_way) in ( %s ) ", builder.toString());
            DBUtil.dbItemExec.execSQL(strSql);
            //写入数据
            for(int i = 0; i < data.size(); i++){
                JSONObject node = data.getJSONObject(i);
                String changeType = node.getString("change_type");
                if(node.getString("change_type").trim().toLowerCase().equals("i"))
                    DBUtil.dbItemExec.execSQL(builder3.toString(), DBUtil.GetSqliteParam(node, lstParam));
            }
            //更新断点 UpdateBreakPoint
            updateBreakPoint(posId, tableName, num);
            DBUtil.dbItemExec.setTransactionSuccessful();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            DBUtil.dbItemExec.endTransaction();
        }
    }

    //商品流水插入数据库
    public void insertSaleflow(JSONArray data, String posId, String tableName,  Long num){
        try {
            //开启事物
            String[] lstParam = {"branch_no", "item_no" ,"price","base_price","sale_price", "vip_price", "com_flag", "scheme_price","vip_price2","vip_price3","vip_price4","vip_price5","price_list","sale_flag","base_price1","base_price2","base_price3","base_price4","base_price5","base_price6","base_price7","base_price8"};

            StringBuilder builder = null;
            StringBuilder builder2 = null;
            StringBuilder builder3 = new StringBuilder();
            builder3.append("insert or ignore into t_pc_branch_price( branch_no, item_no, price, base_price, sale_price, vip_price, com_flag, scheme_price, vip_price2, vip_price3, vip_price4, vip_price5, price_list, sale_flag, base_price1, base_price2, base_price3, base_price4, base_price5, base_price6, base_price7, base_price8)");
            builder3.append("values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            DBUtil.dbItemExec.beginTransaction();

            //1.删除本地数据（下载范围内数据）
            builder = new StringBuilder();
            builder.append("'0'");
            for(int i = 0; i < data.size(); i++){
                JSONObject node = data.getJSONObject(i);
                builder.append( String.format(",'%s'",  node.getString("item_no")).trim());
            }
            String strSql = String.format("delete from t_pc_branch_price where 1=1 and trim(item_no) in ( %s ) ", builder.toString());
            DBUtil.dbItemExec.execSQL(strSql);
            //写入数据
            for(int i = 0; i < data.size(); i++){
                JSONObject node = data.getJSONObject(i);
                String changeType = node.getString("change_type");
                if(node.getString("change_type").trim().toLowerCase().equals("i"))
                    DBUtil.dbItemExec.execSQL(builder3.toString(), DBUtil.GetSqliteParam(node, lstParam));
            }
            //更新断点 UpdateBreakPoint
            updateBreakPoint(posId, tableName, num);
            DBUtil.dbItemExec.setTransactionSuccessful();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            DBUtil.dbItemExec.endTransaction();
        }
    }


    //获取记录号
    public Long getMinFlowID(String posId, String tableName){
//        select * from t_bd_item_cls where cls_parent='      '
        String strSql = String.format("select ifnull(bk_flag,0) from t_sys_cm_breakpoint where pos_id='%s' and com_table='%s' ", posId, tableName);
        //DBUtil.dbItemExec.execSQL();
        //return SIString.TryLong(this._sqliteHelper.GetSingle(strSql));
        try {
            Cursor cursor = DBUtil.dbItemExec.rawQuery(strSql, null);
            if(cursor.getCount() == 0) {
                cursor.close();
                return 0L;
            }
            cursor.moveToFirst();
            Long count = cursor.getLong(0);
            cursor.close();
            return count;
        } catch (Exception e) {
            throw e;
        }
    }


    //查询大类
    public List<TBdItemCls> selectBigCls(){
        String strSql = String.format("select * from t_bd_item_cls where length(rtrim(item_clsno))=2");
        try {
            Cursor cursor = DBUtil.dbItemExec.rawQuery(strSql, null);
            List<TBdItemCls> data = new ArrayList<>();
            if(cursor.getCount() == 0) {
                cursor.close();
                return data;
            }
            if (cursor.moveToFirst()) {
                do{
                    TBdItemCls tBdItemCls = new TBdItemCls();
                    tBdItemCls.setDisplayFlag(cursor.getString(cursor.getColumnIndex("display_flag")));
                    tBdItemCls.setItemClsname(cursor.getString(cursor.getColumnIndex("item_clsname")));
                    tBdItemCls.setItemClsno(cursor.getString(cursor.getColumnIndex("item_clsno")));
                    tBdItemCls.setItemFlag(cursor.getString(cursor.getColumnIndex("item_flag")));
                    tBdItemCls.setTransFlag(cursor.getString(cursor.getColumnIndex("trans_flag")));
                    data.add(tBdItemCls);
                }while (cursor.moveToNext());
            }
            cursor.close();
            return data;

        } catch (Exception e) {
            throw e;
        }
    }


    //根据大类查小类
    public List<TBdItemCls> selectSmallCls(String clsId){
        String strSql = String.format("select * from t_bd_item_cls where item_clsno like '%s'",clsId);
        try {
            Cursor cursor = DBUtil.dbItemExec.rawQuery(strSql, null);
            List<TBdItemCls> data = new ArrayList<>();
            if(cursor.getCount() == 0) {
                cursor.close();
                return data;
            }
            if (cursor.moveToFirst()) {
                do{
                    String item_clsno = cursor.getString(cursor.getColumnIndex("item_clsno")).replace(" ", "")+"%";
                    if (!item_clsno.equals(clsId)){
                        TBdItemCls tBdItemCls = new TBdItemCls();
                        tBdItemCls.setDisplayFlag(cursor.getString(cursor.getColumnIndex("display_flag")));
                        tBdItemCls.setItemClsname(cursor.getString(cursor.getColumnIndex("item_clsname")));
                        tBdItemCls.setItemClsno(cursor.getString(cursor.getColumnIndex("item_clsno")));
                        tBdItemCls.setItemFlag(cursor.getString(cursor.getColumnIndex("item_flag")));
                        tBdItemCls.setTransFlag(cursor.getString(cursor.getColumnIndex("trans_flag")));
                        data.add(tBdItemCls);
                    }
                }while (cursor.moveToNext());
            }
            cursor.close();
            return data;

        } catch (Exception e) {
            throw e;
        }
    }


    //根据小类查商品
    public List<TBdItemInfo> selectByItemClsno(String clsId, String branchNo){
        String strSql = String.format(("SELECT a.item_no, item_subname, item_clsno, item_brand, item_brandname, \n" +
                "    item_size, p.price, p.base_price, combine_sta, status, display_flag, \n" +
                "    po_cycle, so_cycle, automin_flag, en_dis, change_price, purchase_tax, sale_tax, purchase_spec, \n" +
                "    shipment_spec, item_supcust, main_supcust, item_stock, build_date, modify_date, stop_date, \n" +
                "    abc, branch_price, item_rem, vip_price, sale_min_price, com_flag, vip_acc_flag, vip_acc_num, \n" +
                "    dpfm_type, return_rate, update_date,  \n" +
                "    sale_flag, scheme_price, \n" +
                "    measure_flag, item_sup_flag, \n" +
                "    en_gift, promote_flag, \n" +
                "    allow_flag, hand_flag, alert_day1, alert_day2, a.market_price\n" +
                ",a.item_name\n" +
                ",ifnull(t_bd_base_code.code_name, a.unit_no) AS unit_no\n" +
                ",ifnull(p.sale_price, a.sale_price) AS sale_price\n" +
                "FROM t_bd_item_info a\n" +
                "LEFT JOIN t_pc_branch_price p ON (\n" +
                "a.item_no = p.item_no\n" +
                "AND p.branch_no = '%s'\n" +
                ")\n" +
                "LEFT JOIN t_bd_base_code ON (\n" +
                "t_bd_base_code.type_no = 'UN'\n" +
                "AND t_bd_base_code.code_id = a.unit_no\n" +
                ")\n" +
                "WHERE a.item_clsno LIKE '%s'\n" +
                "AND (\n" +
                "p.sale_flag = '0'\n" +
                "OR p.sale_flag = ''\n" +
                "OR p.sale_flag IS NULL\n" +
                ")\n"),branchNo, clsId);
        try {
            Cursor cursor = DBUtil.dbItemExec.rawQuery(strSql, null);
            List<TBdItemInfo> data = new ArrayList<>();
            if(cursor.getCount() == 0) {
                cursor.close();
                return data;
            }
            if (cursor.moveToFirst()) {
                do{
                    //商品list
                    TBdItemInfo tBdItemInfo = new TBdItemInfo();

                    //查询价格表
                    /*String strsqlPrice =  String.format("select * from t_pc_branch_price where item_no='%s'",cursor.getString(cursor.getColumnIndex("item_no")));
                    Cursor cursorPrice = DBUtil.dbItemExec.rawQuery(strsqlPrice, null);
                    if(cursorPrice.getCount() == 0) {
                        cursorPrice.close();
                        tBdItemInfo.setPrice(cursor.getDouble(cursor.getColumnIndex("price")));
                    }else if(cursorPrice.moveToFirst()){
                        do {
                            tBdItemInfo.setPrice(cursorPrice.getDouble(cursorPrice.getColumnIndex("price")));
                        }while (cursorPrice.moveToNext());
                        cursorPrice.close();
                    }else {
                        tBdItemInfo.setPrice(cursor.getDouble(cursor.getColumnIndex("price")));
                    }*/

                    //商品信息
                    tBdItemInfo.setItemNo(cursor.getString(cursor.getColumnIndex("item_no")));
                    tBdItemInfo.setItemClsno(cursor.getString(cursor.getColumnIndex("item_clsno")));
                    tBdItemInfo.setItemSubno(cursor.getString(cursor.getColumnIndex("item_subno")));
                    tBdItemInfo.setItemName(cursor.getString(cursor.getColumnIndex("item_name")));
                    tBdItemInfo.setItemSubname(cursor.getString(cursor.getColumnIndex("item_subname")));
                    tBdItemInfo.setItemBrand(cursor.getString(cursor.getColumnIndex("item_brand")));
                    tBdItemInfo.setItemBrandname(cursor.getString(cursor.getColumnIndex("item_brandname")));
                    tBdItemInfo.setUnitNo(cursor.getString(cursor.getColumnIndex("unit_no")));
                    tBdItemInfo.setItemSize(cursor.getString(cursor.getColumnIndex("item_size")));
                    tBdItemInfo.setBasePrice(cursor.getDouble(cursor.getColumnIndex("base_price")));
                    tBdItemInfo.setSalePrice(cursor.getDouble(cursor.getColumnIndex("sale_price")));
                    tBdItemInfo.setCombineSta(cursor.getString(cursor.getColumnIndex("combine_sta")));
                    tBdItemInfo.setStatus(cursor.getString(cursor.getColumnIndex("status")));
                    tBdItemInfo.setDisplayFlag(cursor.getString(cursor.getColumnIndex("display_flag")));
                    tBdItemInfo.setEnDis(cursor.getString(cursor.getColumnIndex("en_dis")));
                    tBdItemInfo.setChangePrice(cursor.getString(cursor.getColumnIndex("change_price")));
                    tBdItemInfo.setItemSupcust(cursor.getString(cursor.getColumnIndex("item_supcust")));
                    tBdItemInfo.setMainSupcust(cursor.getString(cursor.getColumnIndex("main_supcust")));
                    tBdItemInfo.setItemStock(cursor.getString(cursor.getColumnIndex("item_stock")));
                    tBdItemInfo.setItemRem(cursor.getString(cursor.getColumnIndex("item_rem")));
                    tBdItemInfo.setSaleFlag(cursor.getString(cursor.getColumnIndex("sale_flag")));
                    tBdItemInfo.setMeasureFlag(cursor.getString(cursor.getColumnIndex("measure_flag")));
                    tBdItemInfo.setPromoteFlag(cursor.getString(cursor.getColumnIndex("promote_flag")));
                    tBdItemInfo.setDpfmType(cursor.getString(cursor.getColumnIndex("dpfm_type")));
                    data.add(tBdItemInfo);
                }while (cursor.moveToNext());
            }
            cursor.close();
            return data;

        } catch (Exception e) {
            throw e;
        }
    }


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

    //查询商品流水
    public List<TRmSaleflow> selectBigSaleflow(String flow_no){
        String strSql = String.format("select * from t_rm_saleflow where flow_no='%s'",flow_no);
        try {
            Cursor cursor = DBUtil.dbSaleExec.rawQuery(strSql, null);
            List<TRmSaleflow> data = new ArrayList<>();
            if(cursor.getCount() == 0) {
                cursor.close();
                return data;
            }
            if (cursor.moveToFirst()) {
                do{
                    TRmSaleflow tRmSaleflow = new TRmSaleflow();
                    String strSqla = String.format("select * from t_bd_item_info where item_no='%s'",cursor.getString(cursor.getColumnIndex("item_no")));
                    Cursor cursora = DBUtil.dbItemExec.rawQuery(strSqla, null);
                    if(cursora.getCount() == 0) {
                        cursora.close();
                    }
                    if (cursora.moveToFirst()) {
                        tRmSaleflow.setItem_name(cursora.getString(cursora.getColumnIndex("item_name")));
                    }
                    cursora.close();

//                    tRmSaleflow.setComNo(cursor.getInt(cursor.getColumnIndex("com_no")));
                    tRmSaleflow.setFlow_id(cursor.getInt(cursor.getColumnIndex("flow_id")));
                    tRmSaleflow.setFlow_no(cursor.getString(cursor.getColumnIndex("flow_no")));
                    tRmSaleflow.setBranch_no(cursor.getString(cursor.getColumnIndex("branch_no")));
                    tRmSaleflow.setItem_no(cursor.getString(cursor.getColumnIndex("item_no")));
                    tRmSaleflow.setSource_price(cursor.getDouble(cursor.getColumnIndex("source_price")));
                    tRmSaleflow.setSale_price(cursor.getDouble(cursor.getColumnIndex("sale_price")));
                    tRmSaleflow.setCost_price(cursor.getDouble(cursor.getColumnIndex("cost_price")));
                    tRmSaleflow.setSale_qnty(cursor.getDouble(cursor.getColumnIndex("sale_qnty")));
                    tRmSaleflow.setSale_money(cursor.getDouble(cursor.getColumnIndex("sale_money")));
                    tRmSaleflow.setSell_way(cursor.getString(cursor.getColumnIndex("sell_way")));
                    tRmSaleflow.setOper_id(cursor.getString(cursor.getColumnIndex("oper_id")));
                    tRmSaleflow.setSale_man(cursor.getString(cursor.getColumnIndex("sale_man")));
                    tRmSaleflow.setOper_date(cursor.getString(cursor.getColumnIndex("oper_date")));
                    tRmSaleflow.setShift_no(cursor.getDouble(cursor.getColumnIndex("shift_no")));
                    tRmSaleflow.setCom_flag(cursor.getString(cursor.getColumnIndex("com_flag")));
                    tRmSaleflow.setSpec_flag(cursor.getString(cursor.getColumnIndex("spec_flag")));
                    tRmSaleflow.setPref_amt(cursor.getDouble(cursor.getColumnIndex("pref_amt")));
                    tRmSaleflow.setIn_price(cursor.getDouble(cursor.getColumnIndex("in_price")));
                    tRmSaleflow.setAcc_num(cursor.getDouble(cursor.getColumnIndex("acc_num")));
                    tRmSaleflow.setDec_rate(cursor.getDouble(cursor.getColumnIndex("dec_rate")));
                    tRmSaleflow.setCard_id(cursor.getString(cursor.getColumnIndex("card_id")));
                    tRmSaleflow.setCard_no(cursor.getString(cursor.getColumnIndex("card_no")));
                    tRmSaleflow.setPro_id(cursor.getInt(cursor.getColumnIndex("pro_id")));
                    tRmSaleflow.setClss_rate(cursor.getDouble(cursor.getColumnIndex("clss_rate")));
                    tRmSaleflow.setBrand_rate(cursor.getDouble(cursor.getColumnIndex("Brand_rate")));
                    tRmSaleflow.setSale_rate(cursor.getDouble(cursor.getColumnIndex("sale_rate")));
                    tRmSaleflow.setSale_duty_rate(cursor.getDouble(cursor.getColumnIndex("sale_duty_rate")));
                    tRmSaleflow.setPlan_no(cursor.getString(cursor.getColumnIndex("plan_no")));
                    tRmSaleflow.setMemo(cursor.getString(cursor.getColumnIndex("memo")));
                    data.add(tRmSaleflow);
                }while (cursor.moveToNext());
            }
            cursor.close();
            return data;

        } catch (Exception e) {
            throw e;
        }
    }


    //查询付款流水
    public List<TRmPayflow> selectBigPayflow(){
        String key = "";
        String strSql1 = String.format("select flow_no from t_rm_payflow where com_flag='1' order by except_flag limit 1");
        List<TRmPayflow> data = new ArrayList<>();
        try {
            //查询数据库里是否又未上传流水取第一条
            Cursor cursor1 = DBUtil.dbSaleExec.rawQuery(strSql1, null);
            if(cursor1.getCount() == 0) {
                cursor1.close();
                return data;
            }
            if(cursor1.moveToFirst()) {
                key = cursor1.getString(cursor1.getColumnIndex("flow_no"));
            }
            cursor1.close();


            //查询该条未上传流水
            String strSql = String.format("select * from t_rm_payflow where flow_no='%s'",key);
            Cursor cursor = DBUtil.dbSaleExec.rawQuery(strSql, null);
            if(cursor.getCount() == 0) {
                cursor.close();
                return data;
            }
            if (cursor.moveToFirst()) {
                do{
                    TRmPayflow tRmPayflow = new TRmPayflow();
//                    tRmPayflow.setComNo(cursor.getInt(cursor.getColumnIndex("com_no")));
                    tRmPayflow.setFlowId(cursor.getInt(cursor.getColumnIndex("flow_id")));
                    tRmPayflow.setFlowNo(cursor.getString(cursor.getColumnIndex("flow_no")));
                    tRmPayflow.setSaleAmount(cursor.getDouble(cursor.getColumnIndex("sale_amount")));
                    tRmPayflow.setBranchNo(cursor.getString(cursor.getColumnIndex("branch_no")));
                    tRmPayflow.setPayWay(cursor.getString(cursor.getColumnIndex("pay_way")));
                    tRmPayflow.setSellWay(cursor.getString(cursor.getColumnIndex("sell_way")));
                    tRmPayflow.setCardId(cursor.getString(cursor.getColumnIndex("card_id")));
                    tRmPayflow.setCardNo(cursor.getString(cursor.getColumnIndex("card_no")));
                    tRmPayflow.setVipNo(cursor.getString(cursor.getColumnIndex("vip_no")));
                    tRmPayflow.setCoinNo(cursor.getString(cursor.getColumnIndex("coin_no")));
                    tRmPayflow.setCoinRate(cursor.getDouble(cursor.getColumnIndex("coin_rate")));
                    tRmPayflow.setPayAmount(cursor.getDouble(cursor.getColumnIndex("pay_amount")));
                    tRmPayflow.setOperDate(cursor.getString(cursor.getColumnIndex("oper_date")));
                    tRmPayflow.setOperId(cursor.getString(cursor.getColumnIndex("oper_id")));
                    tRmPayflow.setCounterNo(cursor.getString(cursor.getColumnIndex("counter_no")));
                    tRmPayflow.setSaleMan(cursor.getString(cursor.getColumnIndex("sale_man")));
                    tRmPayflow.setCashierNo(cursor.getString(cursor.getColumnIndex("cashier_no")));
                    tRmPayflow.setMemo(cursor.getString(cursor.getColumnIndex("memo")));
                    data.add(tRmPayflow);
                }while (cursor.moveToNext());
            }
            cursor.close();
            return data;

        } catch (Exception e) {
            throw e;
        }
    }

    //根据FlowNo查询付款流水
    public List<TRmPayflow> selectFlowNoPayflow(String flow_no){
        String strSql = String.format("select * from t_rm_payflow where rtrim(flow_no)='%s'",flow_no.trim());
        List<TRmPayflow> data = new ArrayList<>();
        try {
            Cursor cursor = DBUtil.dbSaleExec.rawQuery(strSql, null);
            if(cursor.getCount() == 0) {
                cursor.close();
                return data;
            }
            if (cursor.moveToFirst()) {
                do{
                    TRmPayflow tRmPayflow = new TRmPayflow();
//                    tRmPayflow.setComNo(cursor.getInt(cursor.getColumnIndex("com_no")));
                    tRmPayflow.setFlowId(cursor.getInt(cursor.getColumnIndex("flow_id")));
                    tRmPayflow.setFlowNo(cursor.getString(cursor.getColumnIndex("flow_no")));
                    tRmPayflow.setSaleAmount(cursor.getDouble(cursor.getColumnIndex("sale_amount")));
                    tRmPayflow.setBranchNo(cursor.getString(cursor.getColumnIndex("branch_no")));
                    tRmPayflow.setPayWay(cursor.getString(cursor.getColumnIndex("pay_way")));
                    tRmPayflow.setSellWay(cursor.getString(cursor.getColumnIndex("sell_way")));
                    tRmPayflow.setCardId(cursor.getString(cursor.getColumnIndex("card_id")));
                    tRmPayflow.setCardNo(cursor.getString(cursor.getColumnIndex("card_no")));
                    tRmPayflow.setVipNo(cursor.getString(cursor.getColumnIndex("vip_no")));
                    tRmPayflow.setCoinNo(cursor.getString(cursor.getColumnIndex("coin_no")));
                    tRmPayflow.setCoinRate(cursor.getDouble(cursor.getColumnIndex("coin_rate")));
                    tRmPayflow.setPayAmount(cursor.getDouble(cursor.getColumnIndex("pay_amount")));
                    tRmPayflow.setOperDate(cursor.getString(cursor.getColumnIndex("oper_date")));
                    tRmPayflow.setOperId(cursor.getString(cursor.getColumnIndex("oper_id")));
                    tRmPayflow.setCounterNo(cursor.getString(cursor.getColumnIndex("counter_no")));
                    tRmPayflow.setSaleMan(cursor.getString(cursor.getColumnIndex("sale_man")));
                    tRmPayflow.setCashierNo(cursor.getString(cursor.getColumnIndex("cashier_no")));
                    tRmPayflow.setMemo(cursor.getString(cursor.getColumnIndex("memo")));
                    tRmPayflow.setPayName(cursor.getString(cursor.getColumnIndex("pay_way")).equals("RMB")?"现金":cursor.getString(cursor.getColumnIndex("pay_way")).equals("WXZ")?"微信":"支付宝");
                    data.add(tRmPayflow);
                }while (cursor.moveToNext());
            }
            cursor.close();
            return data;

        } catch (Exception e) {
            throw e;
        }
    }

    //把已上传的流水改变状态
    public void updateFlowNo(String comFlag,String flowNo){
        String strSql = String.format("update t_rm_payflow set com_flag='%s' where rtrim(flow_no)='%s'",comFlag,flowNo);
        try {
            DBUtil.dbSaleExec.execSQL(strSql);
        }catch (Exception e){
            throw e;
        }
    }

    //把已上传的流水改变状态 未异常状态
    public void updateExceptFlag(String exceptFlag,String flowNo){
        String strSql = String.format("update t_rm_payflow set except_flag='%s' where rtrim(flow_no)='%s'",exceptFlag,flowNo);
        try {
            DBUtil.dbSaleExec.execSQL(strSql);
        }catch (Exception e){
            throw e;
        }
    }

    //查询最新一条付款记录
    public String getMaxFlowNo(String branchNo){
        String strSql = String.format("select max(flow_no) from t_rm_payflow where branch_no='%s'",branchNo);
        String FlowNo = "";
        try {
            Cursor cursor = DBUtil.dbSaleExec.rawQuery(strSql, null);
            if(cursor.getCount() == 0) {
                cursor.close();
                return FlowNo;
            }
            if (cursor.moveToFirst()) {
                FlowNo = cursor.getString(cursor.getColumnIndex("max(flow_no)"));
            }
            cursor.close();
            return FlowNo;
        }catch (Exception e){
            throw e;
        }
    }


    //查询付款方式
    public String getPayMent(String payName){
        String strSql = String.format("select pay_name from t_bd_payment_info where pay_way like '%s'",payName);
        String PayName = "";
        try {
            Cursor cursor = DBUtil.dbItemExec.rawQuery(strSql, null);
            if(cursor.getCount() == 0) {
                cursor.close();
                return PayName;
            }
            if (cursor.moveToFirst()) {
                PayName = cursor.getString(cursor.getColumnIndex("pay_name"));
            }
            cursor.close();
            return PayName;
        }catch (Exception e){
            throw e;
        }
    }


    //查询上一单(历史单据)
    public String getUpperOrder(String branchNo,String flow_no){
        String strSql = String.format("select max(flow_no) flow_no from t_rm_payflow where branch_no='%s' and flow_no < '%s'",branchNo,flow_no);
        String FlowNo = "";
        try {
            Cursor cursor = DBUtil.dbSaleExec.rawQuery(strSql, null);
            if(cursor.getCount() == 0) {
                cursor.close();
                return FlowNo;
            }
            if (cursor.moveToFirst()) {
                FlowNo = cursor.getString(cursor.getColumnIndex("flow_no"));
            }
            cursor.close();
            return FlowNo;
        }catch (Exception e){
            throw e;
        }
    }

    //查询下一单(历史单据)
    public String getNextOrder(String branchNo,String flow_no){
        String strSql = String.format("select flow_no from t_rm_payflow where branch_no='%s' and flow_no > '%s'",branchNo,flow_no);
        String FlowNo = "";
        try {
            Cursor cursor = DBUtil.dbSaleExec.rawQuery(strSql, null);
            if(cursor.getCount() == 0) {
                cursor.close();
                return FlowNo;
            }
            if (cursor.moveToFirst()) {
                FlowNo = cursor.getString(cursor.getColumnIndex("flow_no"));
            }
            cursor.close();
            return FlowNo;
        }catch (Exception e){
            throw e;
        }
    }


    public boolean deleteTable(String s){
        String strSql = String.format("delete from %s",s);
        try{
            Cursor cursor = DBUtil.dbItemExec.rawQuery(strSql, null);
            cursor.close();
            return true;
        }catch(Exception e){
            throw e;
        }
    }

    private void updateBreakPoint(String posId, String tableName, long maxFlowId)
    {
        try {
            String strSql = String.format("replace into t_sys_cm_breakpoint (pos_Id,com_table,bk_flag) values ('%s','%s',%d)", posId, tableName, maxFlowId);
            DBUtil.dbItemExec.execSQL(strSql);
        }catch (Exception e){
            throw e;
        }
    }
}
