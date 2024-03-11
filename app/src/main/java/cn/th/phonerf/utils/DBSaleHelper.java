package cn.th.phonerf.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016-09-26.
 */
public class DBSaleHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sale.db";
    private static final int DATABASE_VERSION = 3;

    //String sqlItem = "";

    private Context context;

    public DBSaleHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try
        {
            String strSql = "";
            strSql = " CREATE TABLE t_cur_saleflow(\r\n\t                        [com_no]  integer identity ,\r\n                            [flow_id] [numeric](3, 0) NULL,\r\n                            [flow_no] [varchar](20) NULL,\r\n                            [branch_no] [char](6) NULL,\r\n                            [item_no] [varchar](50) NULL,\r\n                            [item_name] [varchar](50) NULL,\r\n                            [source_price] [numeric](16, 4)  NULL,\r\n                            [sale_price] [numeric](16, 4) NULL DEFAULT (0),\r\n                            [cost_price] [numeric](16, 4) NULL,\r\n                            [sale_qnty] [numeric](16, 4)  NULL,\r\n                            [sale_money] [numeric](16, 4)  NULL,\r\n                            [sell_way] [char](1) NULL DEFAULT ('A'),\r\n                            [oper_id] [char](6) NOT NULL,\r\n                            [sale_man] [char](6) NULL,\r\n                            [oper_date] datetime NULL,\r\n                            [shift_no] [numeric](16, 4) NULL,\r\n                            [com_flag] [char](1) NULL,\r\n                            [spec_flag] [varchar](10) NULL DEFAULT (''),\r\n                            [pref_amt] [numeric](16, 4) NULL DEFAULT (0),\r\n                            [in_price] [numeric](16, 4) NULL,\r\n                            [acc_num] [numeric](16, 4) NULL,\r\n                            [dec_rate] [numeric](16, 4) NULL,\r\n                            [card_id] [varchar](20) NULL,\r\n                            [card_no] [varchar](20) NULL,\r\n                            [pro_id] [int] NULL,\r\n                            [clss_rate] [numeric](16, 4) NULL,\r\n                            [Brand_rate] [numeric](16, 4) NULL,\r\n                            [sale_rate] [numeric](16, 4) NULL,\r\n                            [sale_duty_rate] [numeric](16, 4) NULL,\r\n                            [plan_no] [varchar](20) NULL,\r\n                            [posid] [varchar] (6) NULL,\r\n                            [item_clsno] [varchar] (12) NULL,\r\n                            [item_brand] [varchar] (12) NULL,\r\n                            [item_subno] [varchar] (12) NULL,\r\n                            [vip_price] [numeric](16, 4) NULL DEFAULT (0),\r\n                            [vip_price2] [numeric](16, 4) NULL DEFAULT (0),\r\n                            [vip_price3] [numeric](16, 4) NULL DEFAULT (0),\r\n                            [vip_price4] [numeric](16, 4) NULL DEFAULT (0),\r\n                            [vip_price5] [numeric](16, 4) NULL DEFAULT (0),\r\n                            [item_stock] [numeric](16, 4) NULL,\r\n                            [vip_acc_num] [numeric](16, 4) NULL DEFAULT (0),\r\n                            [dpfm_type] [varchar](2) NULL,\r\n                            [vip_acc_flag] [varchar](2) NULL,\r\n                            constraint pk_t_cur_saleflow primary key(com_no) );";
            try
            {
                db.execSQL(strSql);
            }
            catch (Exception ex)
            {
            }
            try
            {
                db.execSQL(" ALTER TABLE t_cur_saleflow ADD serials_no VARCHAR(200) NULL ");
            }
            catch (Exception ex)
            {
            }
            try
            {
                db.execSQL(" ALTER TABLE t_cur_saleflow ADD item_subname VARCHAR(20) NULL");
            }
            catch (Exception ex)
            {
            }
            strSql = " create table t_cur_payflow(\r\n\t                        com_no integer identity,\r\n\t                        flow_id numeric(3, 0)  NULL,\r\n\t                        flow_no varchar(14)   NULL,\r\n\t                        sale_amount numeric(16, 4) NULL DEFAULT (0),\r\n\t                        branch_no varchar(6)   NULL,\r\n\t                        pay_way varchar(3)  NULL,\r\n                            pay_name varchar(20) NULL,\r\n\t                        sell_way char(1)  NOT NULL DEFAULT ('A'),\r\n\t                        card_no varchar(20)  NULL,\r\n\t                        vip_no varchar(20)  NULL,\r\n\t                        coin_no char(3)  NOT NULL DEFAULT ('RMB'),\r\n\t                        coin_rate numeric(10, 4) NULL,\r\n\t                        pay_amount numeric(16, 4) NULL DEFAULT (0),\r\n                            convert_amt numeric(16, 4) NULL DEFAULT (0),       \r\n\t                        oper_date datetime NULL,\r\n\t                        oper_id varchar(4)   NULL,\r\n\t                        counter_no varchar(4)  NULL,\r\n\t                        sale_man varchar(4)  NULL,\r\n\t                        memo varchar(40)  NULL,\r\n\t                        voucher_no varchar(14)  NULL,\r\n\t                        remote_flag varchar(1)  NULL,\r\n\t                        exchange_flag char(1)  NULL DEFAULT ('0'),\r\n\t                        shift_no varchar(9)  NULL,\r\n\t                        com_flag varchar(1)  NULL DEFAULT ('0'),\r\n\t                        constraint pk_t_cur_payflow primary key(com_no) )";
            try
            {
                db.execSQL(strSql);
            }
            catch (Exception ex)
            {
            }
            strSql = "create table t_rm_hungbill(\r\n                                flow_no varchar(20),\r\n                                counter_no varchar(20),\r\n                                sale_man varchar(20),\r\n                                vip_mode char(1),\r\n                                vip_cardid varchar(20),\r\n                                vip_saleway char(1),\r\n                                vip_discount numeric(5,4) default(0),\r\n                                constraint pk_t_rm_hungbill primary key(flow_no))";
            try
            {
                db.execSQL(strSql);
            }
            catch (Exception ex)
            {
            }
            strSql = "create table t_rm_hungflow(\r\n                                com_no  integer identity,\r\n\t                            flow_id numeric(3, 0)  NULL,\r\n\t                            flow_no varchar(14)   NULL,\r\n\t                            branch_no varchar(6)   NULL,\r\n\t                            item_no varchar(30)  NULL,\r\n\t                            item_barcode varchar(38)  NULL,\r\n\t                            source_price numeric(16, 4) NULL DEFAULT (0),\r\n\t                            sale_price numeric(16, 4) NULL DEFAULT (0),\r\n\t                            sale_qnty numeric(16, 4) NULL DEFAULT (0),\r\n\t                            sale_money numeric(16, 4) NULL DEFAULT (0),\r\n\t                            sell_way varchar(1)  NULL DEFAULT ('A'),\r\n\t                            oper_id varchar(4)   NULL,\r\n\t                            sale_man varchar(4)  NULL,\r\n\t                            counter_no varchar(4) NULL,\r\n\t                            oper_date datetime NULL,\r\n\t                            remote_flag char(1) NULL DEFAULT ('0'),\r\n\t                            shift_no varchar(9)  NULL,\r\n\t                            com_flag varchar(1) NULL DEFAULT ('0'),\r\n\t                            spec_flag varchar(10)  NULL,\r\n                                vip_price numeric(16, 4) NULL DEFAULT (0),\r\n                                item_name  varchar(50)  NULL,\r\n                                item_subno varchar(50)  NULL,\r\n                                in_price numeric(16, 4) NULL DEFAULT (0),\r\n                                \r\n                                item_brand varchar(50)  NULL,\r\n                                item_clsno varchar(10)  NULL,\r\n                                combine_sta varchar(1)  NULL,\r\n                                item_status varchar(1)  NULL,\r\n                                item_stock varchar(1)  NULL,\r\n                                allowDiscount varchar(1)  NULL,\r\n                                allowChgPrice varchar(1)  NULL,\r\n                                size_code  varchar(4)  NULL,\r\n                                color_code  varchar(4)  NULL,\r\n                                size_name  varchar(20)  NULL,\r\n                                color_name  varchar(20) NULL,\r\n                                constraint pk_t_rm_hungflow primary key(com_no) );";
            try
            {
                db.execSQL(strSql);
            }
            catch (Exception ex)
            {
            }
            try
            {
                db.execSQL("alter table t_rm_hungflow add acc_num numeric(16,4) NULL;");
            }
            catch (Exception ex)
            {
            }
            try
            {
                db.execSQL("alter table t_rm_hungflow add vip_price2 numeric(16, 4) NULL DEFAULT (0);");
            }
            catch (Exception ex)
            {
            }
            try
            {
                db.execSQL("alter table t_rm_hungflow add vip_price3 numeric(16, 4) NULL DEFAULT (0);");
            }
            catch (Exception ex)
            {
            }
            try
            {
                db.execSQL("alter table t_rm_hungflow add vip_price4 numeric(16, 4) NULL DEFAULT (0);");
            }
            catch (Exception ex)
            {
            }
            try
            {
                db.execSQL("alter table t_rm_hungflow add vip_price5 numeric(16, 4) NULL DEFAULT (0);");
            }
            catch (Exception ex)
            {
            }


            //更换主键

            //strSql = " CREATE TABLE t_rm_saleflow(\r\n                            [com_no]  integer identity ,\r\n                            [flow_id] [numeric](3, 0) NOT NULL,\r\n                            [flow_no] [varchar](20) NOT NULL,\r\n                            [branch_no] [char](6)NOT NULL,\r\n                            [item_no] [varchar](50) NULL,\r\n                            [source_price] [numeric](16, 4)  NULL,\r\n                            [sale_price] [numeric](16, 4) NULL DEFAULT (0),\r\n                            [cost_price] [numeric](16, 4) NULL,\r\n                            [sale_qnty] [numeric](16, 4)  NULL,\r\n                            [sale_money] [numeric](16, 4)  NULL,\r\n                            [sell_way] [char](1) NULL DEFAULT ('A'),\r\n                            [oper_id] [char](6) NOT NULL,\r\n                            [sale_man] [char](6) NULL,\r\n                            [oper_date] datetime NULL,\r\n                            [shift_no] [char](9) NULL,\r\n                            [com_flag] [char](1) NULL,\r\n                            [spec_flag] [varchar](10) NULL DEFAULT (''),\r\n                            [pref_amt] [numeric](16, 4) NULL DEFAULT (0),\r\n                            [in_price] [numeric](16, 4) NULL,\r\n                            [acc_num] [numeric](16, 4) NULL,\r\n                            [dec_rate] [numeric](16, 4) NULL,\r\n                            [card_id] [varchar](20) NULL,\r\n                            [card_no] [varchar](20) NULL,\r\n                            [pro_id] [int] NULL,\r\n                            [clss_rate] [numeric](16, 4) NULL,\r\n                            [Brand_rate] [numeric](16, 4) NULL,\r\n                            [sale_rate] [numeric](16, 4) NULL,\r\n                            [sale_duty_rate] [numeric](16, 4) NULL,\r\n                            [plan_no] [varchar](20) NULL,\r\n                            constraint pk_t_rm_saleflow primary key(com_no) );";
                strSql = " CREATE TABLE t_rm_saleflow(" +
                        "[com_no]  integer identity ," +
                        "[flow_id] [numeric](3, 0) NOT NULL," +
                        "[flow_no] [varchar](20) NOT NULL," +
                        "[branch_no] [char](6)NOT NULL," +
                        "[item_no] [varchar](50) NULL," +
                        "[source_price] [numeric](16, 4)  NULL," +
                        "[sale_price] [numeric](16, 4) NULL DEFAULT (0)," +
                        "[cost_price] [numeric](16, 4) NULL," +
                        "[sale_qnty] [numeric](16, 4)  NULL," +
                        "[sale_money] [numeric](16, 4)  NULL," +
                        "[sell_way] [char](1) NULL DEFAULT ('A')," +
                        "[oper_id] [char](6) NOT NULL," +
                        "[sale_man] [char](6) NULL," +
                        "[oper_date] datetime NULL," +
                        "[shift_no] [char](9) NULL," +
                        "[com_flag] [char](1) NULL," +
                        "[spec_flag] [varchar](10) NULL DEFAULT ('')," +
                        "[pref_amt] [numeric](16, 4) NULL DEFAULT (0)," +
                        "[in_price] [numeric](16, 4) NULL," +
                        "[acc_num] [numeric](16, 4) NULL," +
                        "[dec_rate] [numeric](16, 4) NULL," +
                        "[card_id] [varchar](20) NULL," +
                        "[card_no] [varchar](20) NULL," +
                        "[pro_id] [int] NULL," +
                        "[clss_rate] [numeric](16, 4) NULL," +
                        "[Brand_rate] [numeric](16, 4) NULL," +
                        "[sale_rate] [numeric](16, 4) NULL," +
                        "[sale_duty_rate] [numeric](16, 4) NULL," +
                        "[plan_no] [varchar](20) NULL," +
                        "[memo] [varchar](20) NULL," +
                        "constraint pk_t_rm_saleflow primary key(flow_no,flow_id) );";
            try
            {
                db.execSQL(strSql);
            }
            catch (Exception ex)
            {
            }
            try
            {
                db.execSQL(" ALTER TABLE t_rm_saleflow ADD serials_no VARCHAR(200) NULL ");
            }
            catch (Exception ex)
            {
            }
            try
            {
                db.execSQL(" ALTER TABLE t_rm_saleflow ADD gift_msg VARCHAR(200) NULL ");
            }
            catch (Exception ex)
            {
            }
            //strSql = "create table t_rm_payflow(\r\n\t                        com_no integer identity,\r\n\t                        flow_id numeric(3, 0) NOT NULL,\r\n\t                        flow_no varchar(14)  NOT NULL,\r\n\t                        sale_amount numeric(16, 4) NULL DEFAULT (0),\r\n\t                        branch_no varchar(6)  NOT NULL,\r\n\t                        pay_way varchar(3)  NULL,\r\n\t                        sell_way char(1)  NOT NULL DEFAULT ('A'),\r\n\t                        card_no varchar(20)  NULL,\r\n\t                        card_id varchar(20) NULL,\r\n\t                        vip_no varchar(20)  NULL,\r\n\t                        coin_no char(3)  NOT NULL DEFAULT ('RMB'),\r\n\t                        coin_rate numeric(10, 4) NULL,\r\n\t                        pay_amount numeric(16, 4) NULL DEFAULT (0),\r\n\t                        oper_date datetime NULL,\r\n\t                        oper_id varchar(4)  NOT NULL,\r\n\t                        counter_no char(4) NULL,\r\n\t                        sale_man char(4) NULL,\r\n\t                        cashier_no char(4) NULL,\r\n\t                        memo varchar(40) NULL,\r\n\t                        voucher_no char(18) NULL,\r\n\t                        shift_no char(9) NULL,\r\n\t                        com_flag char(1) NULL,\r\n                            remark varchar(255) null,\r\n\t                        constraint pk_t_rm_payflow primary key(com_no) )";
            strSql = "create table t_rm_payflow(" +
                    "com_no integer identity," +
                    "flow_id numeric(3, 0) NOT NULL," +
                    "flow_no varchar(14)  NOT NULL," +
                    "sale_amount numeric(16, 4) NULL DEFAULT (0)," +
                    "branch_no varchar(6)  NOT NULL," +
                    "pay_way varchar(3)  NULL," +
                    "sell_way char(1)  NOT NULL DEFAULT ('A')," +
                    "card_no varchar(20)  NULL," +
                    "card_id varchar(20) NULL," +
                    "vip_no varchar(20)  NULL," +
                    "coin_no char(3)  NOT NULL DEFAULT ('RMB')," +
                    "coin_rate numeric(10, 4) NULL," +
                    "pay_amount numeric(16, 4) NULL DEFAULT (0)," +
                    "oper_date datetime NULL," +
                    "oper_id varchar(4)  NOT NULL," +
                    "counter_no char(4) NULL," +
                    "sale_man char(4) NULL," +
                    "cashier_no char(4) NULL," +
                    "memo varchar(40) NULL," +
                    "voucher_no char(18) NULL," +
                    "shift_no char(9) NULL," +
                    "com_flag char(1) NULL DEFAULT('1')," +
                    "except_flag char(1) NULL default('0')," +
                    "remark varchar(255) null," +
                    "constraint pk_t_rm_payflow primary key(flow_no,flow_id) )";
            try
            {
                db.execSQL(strSql);
            }
            catch (Exception ex)
            {
            }
            try
            {
                db.execSQL("alter table t_rm_payflow add rdm_no varchar(30) null;");
            }
            catch (Exception ex)
            {
            }
            strSql = " CREATE TABLE t_rm_operator_log(\r\n\t                        [flow_no] integer identity,\r\n\t                        [oper_date] datetime NOT NULL,\r\n\t                        [sheet_no] [char](18) NULL,\r\n\t                        [item_no] [varchar](20) NULL,\r\n\t                        [cashier_no] [char](4) NOT NULL,\r\n\t                        [oper_type] [char](3) NOT NULL,\r\n\t                        [money] numeric(16, 4)  NULL,\r\n\t                        [shift] [char](9) NULL,\r\n\t                        [vip_id] [char](20) NULL,\r\n\t                        [card_no] [char](20) NULL,\r\n\t                        [cash_no] [char](6) NULL,\r\n\t                        [price] numeric(16, 4) NULL,\r\n\t                        [process] [char](1) NULL,\r\n\t                        [com_flag] [char](1) NULL,\r\n\t                        [memo] [varchar](200) NULL,\r\n\t                        [award_grant] [char](4) NULL,\r\n                        constraint PK_t_rm_operator_log primary key (flow_no))";
            try
            {
                db.execSQL(strSql);
            }
            catch (Exception ex)
            {
            }
            strSql = "create table t_rm_opendrawer_number (\r\n                                oper_date varchar(10),\r\n                                cashier_id varchar(6),\r\n                                number int,\r\n                                constraint pk_t_rm_opendrawer_number primary key(oper_date, cashier_id))";
            try
            {
                db.execSQL(strSql);
            }
            catch (Exception ex)
            {
            }
            strSql = "CREATE TABLE t_rm_vip_acclist(\r\n\t                        card_id varchar(20) NULL,\r\n\t                        card_no varchar(20) NULL,\r\n\t                        card_type char(2) NULL,\r\n\t                        branch_no char(10) NULL,\r\n\t                        oper_type char(1) NULL,\r\n\t                        oper_id u_oper_id NULL,\r\n\t                        ope_date datetime NULL,\r\n\t                        flow_no varchar(20) NULL,\r\n\t                        consum_amt u_price NULL,\r\n\t                        acc_num numeric(10, 2) NULL DEFAULT (0),\r\n\t                        new_cardno varchar(20) NULL,\r\n\t                        oper_des varchar(60) NULL,\r\n\t                        memo varchar(40) NULL,\r\n\t                        com_flag u_com_flag NULL\r\n                        ) ";
            try
            {
                db.execSQL(strSql);
            }
            catch (Exception ex)
            {
            }
            strSql = " CREATE TABLE t_rm_pos_account (\r\n                            [flow_id]     INTEGER  primary key NOT NULL,\r\n\t                        [branch_no]   varchar(6) null,\r\n\t                        [pos_no]      varchar(4) null,\r\n\t                        [cashier_id]  varchar(4) null,\r\n\t                        [oper_date]   datetime null,\r\n\t                        [start_time]  datetime null,\r\n\t                        [end_time]    datetime null,\r\n\t                        [sale_amt]    numeric(16, 4) NULL,\r\n\t                        [hand_amt]    numeric(16, 4) NULL,\r\n\t                        [com_flag]    char(1) NULL DEFAULT ('0') )";
            try
            {
                db.execSQL(strSql);
            }
            catch (Exception ex)
            {
            }
            strSql = " create TABLE t_rm_cashier_payflow(\r\n\t                        com_no integer identity,\r\n\t                        flow_id numeric(3, 0)  NULL,\r\n\t                        flow_no varchar(14)   NULL,\r\n\t                        sale_amount numeric(16, 4) NULL DEFAULT (0),\r\n\t                        branch_no varchar(6)   NULL,\r\n\t                        pay_way varchar(3)  NULL,\r\n\t                        sell_way char(1)   NULL DEFAULT ('A'),\r\n\t                        card_no varchar(20)  NULL,\r\n\t                        card_id varchar(20) NULL,\r\n\t                        vip_no varchar(20)  NULL,\r\n\t                        coin_no char(3)   NULL DEFAULT ('RMB'),\r\n\t                        coin_rate numeric(10, 4) NULL,\r\n\t                        pay_amount numeric(16, 4) NULL DEFAULT (0),\r\n\t                        oper_date datetime NULL,\r\n\t                        oper_id varchar(4) NULL,\r\n\t                        counter_no char(4) NULL,\r\n\t                        sale_man char(4) NULL,\r\n\t                        cashier_no char(4) NULL,\r\n\t                        memo varchar(40) NULL,\r\n\t                        voucher_no char(18) NULL,\r\n\t                        shift_no char(9) NULL,\r\n\t                        com_flag char(1) NULL,\r\n\t                        constraint pk_t_rm_cashier_payflow primary key(com_no) )";
            try
            {
                db.execSQL(strSql);
            }
            catch (Exception ex)
            {
            }
        }
        catch (Exception ex)
        {
//            throw new Exception("本地资料库创建表失败！\r\n" + exception.Message);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String strSql = "";
        if(newVersion <= 2){
            //标签
            try {
                strSql = "alter table t_rm_payflow add except_flag char(1) NULL default('0')";
                db.execSQL(strSql);
            } catch (Exception ex){
            }
        }
        if(newVersion <= 32){
            //标签
            try {
                strSql = "alter table t_rm_saleflow add memo varchar(20) NULL";
                db.execSQL(strSql);
            } catch (Exception ex){
            }
        }
    }
}
