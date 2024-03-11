package cn.th.phonerf.dal;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import cn.th.phonerf.model.AccountCashierInfo;
import cn.th.phonerf.model.TRmPayflow;
import cn.th.phonerf.model.TRmSaleflow;
import cn.th.phonerf.model.TRmSaleman;
import cn.th.phonerf.model.t_bd_payment_info;
import cn.th.phonerf.utils.DBUtil;
import cn.th.phonerf.utils.DateUtil;

public class SaleBillDal {

    public String getMaxFlowID(){
//        select * from t_bd_item_cls where cls_parent='      '
        String strSql = String.format("select rtrim(max(flow_no)) flow_no  from rtrim(max(flow_no)) where oper_date > '%s' ", DateUtil.getCurrentDate());
        try {
            Cursor cursor = DBUtil.dbItemExec.rawQuery(strSql, null);
            if(cursor.getCount() == 0) {
                cursor.close();
                return null;
            }
            cursor.moveToFirst();
            String flowNo = cursor.getString(0);
            cursor.close();
            return flowNo;
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean savePosBill(boolean isServer, List<TRmSaleflow> listSaleflow, List<TRmPayflow> listPayflow)
    {
        if (isServer)
        {
            /*
            if (listSaleflow != null)
            {
                GConn.ServerDal.SaveSaleFlow(listSaleflow);
            }
            if (listPayflow != null)
            {
                GConn.ServerDal.SavePayFlow(listPayflow);
            }*/
        }
        else
        {
            try {
                DBUtil.dbSaleExec.beginTransaction();
                if (listSaleflow != null)
                {
                    saveLocalSaleFlow(listSaleflow);
                }
                if (listPayflow != null)
                {
                    saveLocalPayFlow(listPayflow);
                }
                DBUtil.dbSaleExec.setTransactionSuccessful();
                DBUtil.dbSaleExec.endTransaction();
            }catch (Exception ex){
                DBUtil.dbSaleExec.endTransaction();
                throw ex;
            }
        }
        return true;
    }

    private void saveLocalSaleFlow(List<TRmSaleflow> listSaleflow)
    {
        String saleInsertSql = this.GetSaleInsertSql();
        int num = 0;

        for (TRmSaleflow _saleflow : listSaleflow)
        {
            DBUtil.dbSaleExec.execSQL (saleInsertSql, this.GetSaleInsertParamete(_saleflow));
        }
    }
    private void saveLocalPayFlow(List<TRmPayflow> listPayflow)
    {
        String payInsertSql = this.GetPayInsertSql();
        int num = 0;
        for (TRmPayflow _payflow : listPayflow)
        {
            DBUtil.dbSaleExec.execSQL(payInsertSql, this.GetPayInsertParamete(_payflow));
        }
    }

    private String GetSaleInsertSql()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("insert into t_rm_saleflow(sale_money,sell_way,oper_id,sale_man,oper_date,shift_no,com_flag,spec_flag,pref_amt,in_price,flow_id,acc_num,dec_rate,card_id,card_no,pro_id,clss_rate,Brand_rate,sale_rate,sale_duty_rate,plan_no,flow_no,branch_no,item_no,source_price,sale_price,cost_price,sale_qnty,serials_no,gift_msg,memo");
        builder.append(") values (");
        builder.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?");
        builder.append(") ");
        return builder.toString();
    }


    private Object[] GetSaleInsertParamete(TRmSaleflow model)
    {
        Object[] parameterArray = new Object[] {
                model.getSale_money(),
                model.getSell_way(),
                model.getOper_id(),
                model.getSale_man(),
                model.getOper_date(),
                model.getShift_no(),
                model.getCom_flag(),
                model.getSpec_flag(),
                model.getPref_amt(),
                model.getIn_price(),
                model.getFlow_id(),
                model.getAcc_num(),
                model.getDec_rate(),
                model.getCard_id(),
                model.getCard_no(),
                model.getPro_id(),
                model.getClss_rate(),
                model.getBrand_rate(),
                model.getSale_rate(),
                model.getSale_duty_rate(),
                model.getPlan_no(),
                model.getFlow_no(),
                model.getBranch_no(),
                model.getItem_no(),
                model.getSource_price(),
                model.getSale_price(),
                model.getCost_price(),
                model.getSale_qnty(),
                model.getSerials_no(),
                model.getGift_msg(),
                model.getMemo()
        };
        return parameterArray;
    }

    private String GetPayInsertSql()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("insert into t_rm_payflow(vip_no,coin_no,coin_rate,pay_amount,oper_date,oper_id,counter_no,sale_man,cashier_no,memo,flow_id,voucher_no,shift_no,com_flag,flow_no,sale_amount,branch_no,pay_way,sell_way,card_no,card_id,remark");
        builder.append(") values (");
        builder.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?");
        builder.append(") ");
        return builder.toString();
    }

    private Object[] GetPayInsertParamete(TRmPayflow model) {
        //@vip_no,@coin_no,@coin_rate,@pay_amount,@oper_date,@oper_id,@counter_no,@sale_man,@cashier_no,@memo,@flow_id,@voucher_no,@shift_no,@com_flag,@flow_no,@sale_amount,@branch_no,@pay_way,@sell_way,@card_no,@card_id
        Object[] parameterArray = new Object[]{
                model.getVipNo(),
                model.getCoinNo(),
                model.getCoinRate(),
                model.getPayAmount(),
                model.getOperDate(),
                model.getOperId(),
                model.getCounterNo(),
                model.getSaleMan(),
                model.getCashierNo(),
                model.getMemo(),
                model.getFlowId(),
                model.getVoucherNo(),
                model.getShiftNo(),
                model.getComFlag(),
                model.getFlowNo(),
                model.getSaleAmount(),
                model.getBranchNo(),
                model.getPayWay(),
                model.getSellWay(),
                model.getCardNo(),
                model.getCardId(),
                model.getRemark()
        };
        return parameterArray;
    }

    /******************************************************************************************/
    /***             日结数据                                  ***/


    public ArrayList<t_bd_payment_info> getAllPayWayInfo(){
        ArrayList<t_bd_payment_info> lists = new ArrayList<t_bd_payment_info>();

        try {

            String str = "select * from t_bd_payment_info  where pay_flag = '1' or pay_flag='0'";
            Cursor cursor = DBUtil.dbItemExec.rawQuery(str, null);
            if(cursor.moveToFirst()){
                do{
                    t_bd_payment_info entity = new t_bd_payment_info();
                    entity.setAccFlag(cursor.getString(cursor.getColumnIndex("acc_flag")));
                    entity.setDefaultAmt(cursor.getDouble(cursor.getColumnIndex("default_amt")));
                    entity.setJfFlag(cursor.getString(cursor.getColumnIndex("jf_flag")));
                    entity.setPayFlag(cursor.getString(cursor.getColumnIndex("pay_flag")));
                    entity.setPayMemo(cursor.getString(cursor.getColumnIndex("pay_memo")));
                    entity.setPayName(cursor.getString(cursor.getColumnIndex("pay_name")));
                    entity.setPayWay(cursor.getString(cursor.getColumnIndex("pay_way")));
                    entity.setRate(cursor.getDouble(cursor.getColumnIndex("rate")));

                    lists.add(entity);
                }while(cursor.moveToNext());
            }
            cursor.close();
            return lists;
        }catch (Exception ex){
            throw ex;
        }

    }

    public AccountCashierInfo getPayWayAmount(String branchNo, String cashierId, String dtFrom, String dtTo)
    {
        AccountCashierInfo entity = null;
        String strSql = String.format(( "SELECT count(distinct flow_no) AS my_count, Min(oper_date) as min_date, Max(oper_date) as max_date, Sum(pay_amount) as amount " +
                "FROM t_rm_payflow " +
                "WHERE  branch_no like '%s' " +
                "and rtrim(oper_id) like '%s' " +
                "and oper_date > '%s' and oper_date <= '%s' " +
                "--and (com_flag is null or com_flag='0')"), branchNo, cashierId, dtFrom, dtTo);
        Cursor cursor = null;
        try {
            cursor = DBUtil.dbSaleExec.rawQuery(strSql, null);
            if(cursor.moveToFirst()){
                do{
                    entity = new AccountCashierInfo();
                    //entity.setMerchantId(cursor.getInt(cursor.getColumnIndex("merchant_id")));
                    entity.setMyCount(cursor.getInt(cursor.getColumnIndex("my_count")));
                    entity.setMinDate(cursor.getString(cursor.getColumnIndex("min_date")));
                    entity.setMaxDate(cursor.getString(cursor.getColumnIndex("max_date")));
                    entity.setAmount(cursor.getDouble(cursor.getColumnIndex("amount")));
                }while(cursor.moveToNext());
            }
            cursor.close();
            return entity;
        }catch (Exception ex){
            if(cursor != null)
                cursor.close();
            throw ex;
        }
    }

    public AccountCashierInfo getPayWayAmount2(String branchNo, String cashierId, String payWay, String sellWay, String dtFrom, String dtTo, String saleMan)
    {
        //string strSql = "SELECT Count(distinct flow_no) as count, Sum(pay_amount) as amount FROM t_rm_cashier_payflow  WHERE branch_no like @branchNo and pay_way = @payWay and sell_way = @sellway   and  rtrim(oper_id) like @cashierId and oper_date > @begintime AND oper_date <= @endtime --and (com_flag is null or com_flag='0')";
        //return this._posSaleHelper.GetData(strSql, new SQLiteParameter[] { new SQLiteParameter("@branchNo", branchNo + "%"), new SQLiteParameter("@payWay", payWay), new SQLiteParameter("@sellway", sellWay), new SQLiteParameter("@cashierId", cashierId), new SQLiteParameter("@begintime", dtFrom.ToString("yyyy-MM-dd HH:mm:ss.fff")), new SQLiteParameter("@endtime", dtTo) });
        AccountCashierInfo entity = null;
        String strSql = String.format(( "SELECT Count(distinct flow_no) as my_count, Sum(pay_amount) as amount " +
                "FROM t_rm_payflow  " +
                "WHERE branch_no like '%s' " +
                "and UPPER(pay_way) = '%s' " +
                "and sell_way = '%s' " +
                "and rtrim(oper_id) like '%s' " +
                "and oper_date >= '%s' and oper_date <= '%s' " +
                "and rtrim(sale_man) like '%s' " +
                "--and (com_flag is null or com_flag='0')"), branchNo, payWay.toUpperCase(), sellWay, cashierId, dtFrom, dtTo, saleMan);
        Cursor cursor = null;
        try {
            cursor = DBUtil.dbSaleExec.rawQuery(strSql, null);
            if(cursor.moveToFirst()){
                do{
                    entity = new AccountCashierInfo();
                    entity.setMyCount(cursor.getInt(cursor.getColumnIndex("my_count")));
                    //entity.setMinDate(cursor.getString(cursor.getColumnIndex("min_date")));
                    //entity.setMaxDate(cursor.getString(cursor.getColumnIndex("max_date")));
                    entity.setAmount(cursor.getDouble(cursor.getColumnIndex("amount")));
                }while(cursor.moveToNext());
            }
            cursor.close();
            return entity;
        }catch (Exception ex){
            if(cursor != null)
                cursor.close();
            throw ex;
        }
    }

    public ArrayList<TRmSaleman> selectSaleman(String branchNo, String cashierId, String dtFrom, String dtTo)
    {
        //string strSql = "SELECT Count(distinct flow_no) as count, Sum(pay_amount) as amount FROM t_rm_cashier_payflow  WHERE branch_no like @branchNo and pay_way = @payWay and sell_way = @sellway   and  rtrim(oper_id) like @cashierId and oper_date > @begintime AND oper_date <= @endtime --and (com_flag is null or com_flag='0')";
        //return this._posSaleHelper.GetData(strSql, new SQLiteParameter[] { new SQLiteParameter("@branchNo", branchNo + "%"), new SQLiteParameter("@payWay", payWay), new SQLiteParameter("@sellway", sellWay), new SQLiteParameter("@cashierId", cashierId), new SQLiteParameter("@begintime", dtFrom.ToString("yyyy-MM-dd HH:mm:ss.fff")), new SQLiteParameter("@endtime", dtTo) });
        ArrayList<TRmSaleman> list = new ArrayList<>();
        String strSql = String.format(( "SELECT distinct rtrim(sale_man) sale_man " +
                "FROM t_rm_payflow  " +
                "WHERE branch_no like '%s' " +
                "and rtrim(oper_id) like '%s' " +
                "and oper_date >= '%s' and oper_date <= '%s' " +
                "--and (com_flag is null or com_flag='0')"), branchNo,  cashierId, dtFrom, dtTo);
        Cursor cursor = null;
        try {
            cursor = DBUtil.dbSaleExec.rawQuery(strSql, null);
            if(cursor.moveToFirst()){
                do{
                    TRmSaleman tRmSaleman = new TRmSaleman();
                    tRmSaleman.setSaleId(cursor.getString(cursor.getColumnIndex("sale_man")));
                    list.add(tRmSaleman);
                }while(cursor.moveToNext());
            }
            cursor.close();
            return list;
        }catch (Exception ex){
            if(cursor != null)
                cursor.close();
            throw ex;
        }
    }
}
