package cn.th.phonerf.model;

import java.math.BigDecimal;
import java.util.Date;

public class T_rm_vip_savelist {
    private Integer flow_id;

    private Integer app_uuid;

    private Integer card_id;

    private String oper_type;

    private Integer oper_id;

    private Date oper_date;

    private Integer flow_no;

    private BigDecimal consum_amt;

    private BigDecimal real_amt;

    private BigDecimal acc_num;

    private Integer new_card_id;

    private String oper_des;

    private String memo;

    private BigDecimal residual_amt;

    private String pay_way;

    private String ret_flag;

    public Integer getFlow_id() {
        return flow_id;
    }

    public void setFlow_id(Integer flow_id) {
        this.flow_id = flow_id;
    }

    public Integer getApp_uuid() {
        return app_uuid;
    }

    public void setApp_uuid(Integer app_uuid) {
        this.app_uuid = app_uuid;
    }

    public Integer getCard_id() {
        return card_id;
    }

    public void setCard_id(Integer card_id) {
        this.card_id = card_id;
    }

    public String getOper_type() {
        return oper_type;
    }

    public void setOper_type(String oper_type) {
        this.oper_type = oper_type == null ? null : oper_type.trim();
    }

    public Integer getOper_id() {
        return oper_id;
    }

    public void setOper_id(Integer oper_id) {
        this.oper_id = oper_id;
    }

    public Date getOper_date() {
        return oper_date;
    }

    public void setOper_date(Date oper_date) {
        this.oper_date = oper_date;
    }

    public Integer getFlow_no() {
        return flow_no;
    }

    public void setFlow_no(Integer flow_no) {
        this.flow_no = flow_no;
    }

    public BigDecimal getConsum_amt() {
        return consum_amt;
    }

    public void setConsum_amt(BigDecimal consum_amt) {
        this.consum_amt = consum_amt;
    }

    public BigDecimal getReal_amt() {
        return real_amt;
    }

    public void setReal_amt(BigDecimal real_amt) {
        this.real_amt = real_amt;
    }

    public BigDecimal getAcc_num() {
        return acc_num;
    }

    public void setAcc_num(BigDecimal acc_num) {
        this.acc_num = acc_num;
    }

    public Integer getNew_card_id() {
        return new_card_id;
    }

    public void setNew_card_id(Integer new_card_id) {
        this.new_card_id = new_card_id;
    }

    public String getOper_des() {
        return oper_des;
    }

    public void setOper_des(String oper_des) {
        this.oper_des = oper_des == null ? null : oper_des.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public BigDecimal getResidual_amt() {
        return residual_amt;
    }

    public void setResidual_amt(BigDecimal residual_amt) {
        this.residual_amt = residual_amt;
    }

    public String getPay_way() {
        return pay_way;
    }

    public void setPay_way(String pay_way) {
        this.pay_way = pay_way == null ? null : pay_way.trim();
    }

    public String getRet_flag() {
        return ret_flag;
    }

    public void setRet_flag(String reg_flag) {
        this.ret_flag = reg_flag;
    }
}