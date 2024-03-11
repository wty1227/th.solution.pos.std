package cn.th.phonerf.dal;

import android.database.Cursor;

import cn.th.phonerf.model.UploadParm;
import cn.th.phonerf.utils.DBUtil;

public class UploadDal {

    public int getCount(){
        String strSql = "select count( distinct flow_no) from t_rm_payflow  ";

        try {
            Cursor cursor =  DBUtil.dbSaleExec.rawQuery(strSql, null);
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            cursor.close();
            return count;
        } catch (Exception e) {
            throw e;
        }
    }
    
    public String getSingle(String strSql){
        try {
            Cursor cursor =  DBUtil.dbSaleExec.rawQuery(strSql, null);
            cursor.moveToFirst();
            String result = cursor.getString(0);
            cursor.close();
            return result;
        } catch (Exception e) {
            return "";
        }
    }

/*
    public boolean UploadDataForSale(UploadParm transParm)
    {
        String strSql = " select flow_no from t_rm_payflow limit 1";
        String str2 = " select  * from t_rm_saleflow where flow_no = @flow_no";
        String str3 = " select  * from t_rm_payflow where flow_no = @flow_no";
        String str4 = "select  * from t_rm_vip_acclist where flow_no = @flow_no";
        String str5 = " delete from t_rm_saleflow  where flow_no = @flow_no";
        String str6 = " delete from t_rm_payflow  where flow_no = @flow_no ";
        String str7 = " delete from t_rm_vip_acclist  where flow_no = @flow_no ";
        String flowNo = String.valueOf(getSingle(strSql)).trim();
        if (flowNo != "")
        {
            transParm.sheet_no = flowNo;
            SaleBillBll bll = new SaleBillBll();
            String str9 = flowNo.trim().replace("T", "");
            if (bll.IsExistSaleBill(str9) || bll.IsExistSaleBill(flowNo))
            {
                try
                {
                    this._sqliteHelper.ExecuteSql(str5, new SQLiteParameter[] { new SQLiteParameter("@flow_no", flowNo) });
                    this._sqliteHelper.ExecuteSql(str6, new SQLiteParameter[] { new SQLiteParameter("@flow_no", flowNo) });
                    this._sqliteHelper.ExecuteSql(str7, new SQLiteParameter[] { new SQLiteParameter("@flow_no", flowNo) });
                }
                catch (Exception exception)
                {
                    throw new Exception("删除本地数据，错误信息：\r\n" + exception.Message);
                }
                return true;
            }
            DataTable data = this._sqliteHelper.GetData(str2, new SQLiteParameter[] { new SQLiteParameter("@flow_no", flowNo) });
            DataTable table2 = this._sqliteHelper.GetData(str3, new SQLiteParameter[] { new SQLiteParameter("@flow_no", flowNo) });
            DataTable table3 = this._sqliteHelper.GetData(str4, new SQLiteParameter[] { new SQLiteParameter("@flow_no", flowNo) });
            List<t_rm_saleflow> listSaleflow = new List<t_rm_saleflow>();
            foreach (DataRow row in data.Rows)
            {
                t_rm_saleflow item = new t_rm_saleflow {
                com_no = SIString.TryDec(row["com_no"]),
                        sale_money = SIString.TryDec(row["sale_money"]),
                        sell_way = String.valueOf(row["sell_way"]),
                        oper_id = String.valueOf(row["oper_id"]),
                        sale_man = String.valueOf(row["sale_man"]),
                        oper_date = SIString.TryDateTime(row["oper_date"], DateTime.Now),
                        shift_no = SIString.TryDec(row["shift_no"]),
                        com_flag = String.valueOf(row["com_flag"]),
                        spec_flag = String.valueOf(row["spec_flag"]),
                        pref_amt = SIString.TryDec(row["pref_amt"]),
                        in_price = SIString.TryDec(row["in_price"]),
                        flow_id = SIString.TryDec(row["flow_id"]),
                        acc_num = SIString.TryDec(row["acc_num"]),
                        dec_rate = SIString.TryDec(row["dec_rate"]),
                        card_id = String.valueOf(row["card_id"]),
                        card_no = String.valueOf(row["card_no"]),
                        pro_id = SIString.TryInt(row["pro_id"]),
                        clss_rate = SIString.TryDec(row["clss_rate"]),
                        Brand_rate = SIString.TryDec(row["Brand_rate"]),
                        sale_rate = SIString.TryDec(row["sale_rate"]),
                        sale_duty_rate = SIString.TryDec(row["sale_duty_rate"]),
                        plan_no = String.valueOf(row["plan_no"]),
                        flow_no = String.valueOf(row["flow_no"]),
                        branch_no = String.valueOf(row["branch_no"]),
                        item_no = String.valueOf(row["item_no"]),
                        source_price = SIString.TryDec(row["source_price"]),
                        sale_price = SIString.TryDec(row["sale_price"]),
                        cost_price = SIString.TryDec(row["cost_price"]),
                        sale_qnty = SIString.TryDec(row["sale_qnty"]),
                        serials_no = String.valueOf(row["serials_no"])
            };
                listSaleflow.Add(item);
            }
            List<t_rm_payflow> listPayflow = new List<t_rm_payflow>();
            foreach (DataRow row2 in table2.Rows)
            {
                t_rm_payflow _payflow = new t_rm_payflow {
                com_no = SIString.TryDec(row2["com_no"]),
                        vip_no = String.valueOf(row2["vip_no"]),
                        coin_no = String.valueOf(row2["coin_no"]),
                        coin_rate = SIString.TryDec(row2["coin_rate"]),
                        pay_amount = SIString.TryDec(row2["pay_amount"]),
                        oper_date = SIString.TryDateTime(row2["oper_date"], DateTime.Now),
                        oper_id = String.valueOf(row2["oper_id"]),
                        counter_no = String.valueOf(row2["counter_no"]),
                        sale_man = String.valueOf(row2["sale_man"]),
                        cashier_no = String.valueOf(row2["cashier_no"]),
                        memo = String.valueOf(row2["memo"]),
                        flow_id = SIString.TryDec(row2["flow_id"]),
                        voucher_no = String.valueOf(row2["voucher_no"]),
                        shift_no = String.valueOf(row2["shift_no"]),
                        com_flag = String.valueOf(row2["com_flag"]),
                        flow_no = String.valueOf(row2["flow_no"]),
                        sale_amount = SIString.TryDec(row2["sale_amount"]),
                        branch_no = String.valueOf(row2["branch_no"]),
                        pay_way = String.valueOf(row2["pay_way"]),
                        sell_way = String.valueOf(row2["sell_way"]),
                        card_no = String.valueOf(row2["card_no"]),
                        card_id = String.valueOf(row2["card_id"]),
                        remark = String.valueOf(row2["remark"])
            };
                listPayflow.Add(_payflow);
            }
            t_rm_vip_acclist modelInterface = null;
            if ((table3 != null) && (table3.Rows.Count > 0))
            {
                modelInterface = new t_rm_vip_acclist {
                card_id = String.valueOf(table3.Rows[0]["card_id"]),
                        card_no = String.valueOf(table3.Rows[0]["card_no"]),
                        card_type = String.valueOf(table3.Rows[0]["card_type"]),
                        branch_no = String.valueOf(table3.Rows[0]["branch_no"]),
                        oper_type = "0",
                        oper_id = String.valueOf(table3.Rows[0]["oper_id"]),
                        ope_date = SIString.TryDateTime(table3.Rows[0]["ope_date"]),
                        flow_no = String.valueOf(table3.Rows[0]["flow_no"]),
                        consum_amt = SIString.TryDec(table3.Rows[0]["consum_amt"]),
                        acc_num = new decimal?(SIString.TryDec(table3.Rows[0]["acc_num"])),
                        oper_des = String.valueOf(table3.Rows[0]["oper_des"])
            };
            }
            IDbTransaction objTrans = CommBll.BeginSQLTrans();
            try
            {
                bll.SavePosBill(true, listSaleflow, listPayflow, objTrans);
                if (((listSaleflow.Count > 0) && (listSaleflow[0].card_id.trim() != "")) && (modelInterface != null))
                {
                    new VipAccBll(true).Insert(modelInterface, objTrans);
                }
                objTrans.Commit();
            }
            catch (Exception exception2)
            {
                if (objTrans != null)
                {
                    objTrans.Rollback();
                }
                throw exception2;
            }
            try
            {
                this._sqliteHelper.ExecuteSql(str5, new SQLiteParameter[] { new SQLiteParameter("@flow_no", flowNo) });
                this._sqliteHelper.ExecuteSql(str6, new SQLiteParameter[] { new SQLiteParameter("@flow_no", flowNo) });
                if (modelInterface != null)
                {
                    this._sqliteHelper.ExecuteSql(str7, new SQLiteParameter[] { new SQLiteParameter("@flow_no", flowNo) });
                }
            }
            catch (Exception exception3)
            {
                throw new Exception("删除本地数据，错误信息：\r\n" + exception3.Message);
            }
        }
        return true;
    }*/
}
