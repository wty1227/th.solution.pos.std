package cn.th.phonerf.model;

import java.io.Serializable;
import java.util.Date;

/**
 * t_sys_operator
 * @author 
 */
public class TSysOperator extends BaseEntity implements Serializable {
    private String oper_id;

    private String oper_name;

    private String oper_pw;

    private String oper_status;

    private String oper_type;

    private Date last_time;

    private Double output_rate;

    private String branch_no;

    private String data_grant;

    private String confirm_pw;

    private String cash_grant;

    private Double limit_discount;

    private Double save_discount;

    private String contral_center;

    private String price_grant;

    private String group_id;

    private String oper_flag;

    private String charge;

    private String area_manager;

    private String grant_type;

    private String oper_clsno;

    private Double recharge_limit;

    private Double recharge_amt;

    private Date recharge_date;

    private String oper_pw2;

    private static final long serialVersionUID = 1L;

    public String getOper_id() {
        return oper_id;
    }

    public void setOper_id(String oper_id) {
        this.oper_id = oper_id;
    }

    public String getOper_name() {
        return oper_name;
    }

    public void setOper_name(String oper_name) {
        this.oper_name = oper_name;
    }

    public String getOper_pw() {
        return oper_pw;
    }

    public void setOper_pw(String oper_pw) {
        this.oper_pw = oper_pw;
    }

    public String getOper_status() {
        return oper_status;
    }

    public void setOper_status(String oper_status) {
        this.oper_status = oper_status;
    }

    public String getOper_type() {
        return oper_type;
    }

    public void setOper_type(String oper_type) {
        this.oper_type = oper_type;
    }

    public Date getLast_time() {
        return last_time;
    }

    public void setLast_time(Date last_time) {
        this.last_time = last_time;
    }

    public Double getOutput_rate() {
        return output_rate;
    }

    public void setOutput_rate(Double output_rate) {
        this.output_rate = output_rate;
    }

    public String getBranch_no() {
        return branch_no;
    }

    public void setBranch_no(String branch_no) {
        this.branch_no = branch_no;
    }

    public String getData_grant() {
        return data_grant;
    }

    public void setData_grant(String data_grant) {
        this.data_grant = data_grant;
    }

    public String getConfirm_pw() {
        return confirm_pw;
    }

    public void setConfirm_pw(String confirm_pw) {
        this.confirm_pw = confirm_pw;
    }

    public String getCash_grant() {
        return cash_grant;
    }

    public void setCash_grant(String cash_grant) {
        this.cash_grant = cash_grant;
    }

    public Double getLimit_discount() {
        return limit_discount;
    }

    public void setLimit_discount(Double limit_discount) {
        this.limit_discount = limit_discount;
    }

    public Double getSave_discount() {
        return save_discount;
    }

    public void setSave_discount(Double save_discount) {
        this.save_discount = save_discount;
    }

    public String getContral_center() {
        return contral_center;
    }

    public void setContral_center(String contral_center) {
        this.contral_center = contral_center;
    }

    public String getPrice_grant() {
        return price_grant;
    }

    public void setPrice_grant(String price_grant) {
        this.price_grant = price_grant;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getOper_flag() {
        return oper_flag;
    }

    public void setOper_flag(String oper_flag) {
        this.oper_flag = oper_flag;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getArea_manager() {
        return area_manager;
    }

    public void setArea_manager(String area_manager) {
        this.area_manager = area_manager;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getOper_clsno() {
        return oper_clsno;
    }

    public void setOper_clsno(String oper_clsno) {
        this.oper_clsno = oper_clsno;
    }

    public Double getRecharge_limit() {
        return recharge_limit;
    }

    public void setRecharge_limit(Double recharge_limit) {
        this.recharge_limit = recharge_limit;
    }

    public Double getRecharge_amt() {
        return recharge_amt;
    }

    public void setRecharge_amt(Double recharge_amt) {
        this.recharge_amt = recharge_amt;
    }

    public Date getRecharge_date() {
        return recharge_date;
    }

    public void setRecharge_date(Date recharge_date) {
        this.recharge_date = recharge_date;
    }

    public String getOper_pw2() {
        return oper_pw2;
    }

    public void setOper_pw2(String oper_pw2) {
        this.oper_pw2 = oper_pw2;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TSysOperator other = (TSysOperator) that;
        return (this.getOper_id() == null ? other.getOper_id() == null : this.getOper_id().equals(other.getOper_id()))
            && (this.getOper_name() == null ? other.getOper_name() == null : this.getOper_name().equals(other.getOper_name()))
            && (this.getOper_pw() == null ? other.getOper_pw() == null : this.getOper_pw().equals(other.getOper_pw()))
            && (this.getOper_status() == null ? other.getOper_status() == null : this.getOper_status().equals(other.getOper_status()))
            && (this.getOper_type() == null ? other.getOper_type() == null : this.getOper_type().equals(other.getOper_type()))
            && (this.getLast_time() == null ? other.getLast_time() == null : this.getLast_time().equals(other.getLast_time()))
            && (this.getOutput_rate() == null ? other.getOutput_rate() == null : this.getOutput_rate().equals(other.getOutput_rate()))
            && (this.getBranch_no() == null ? other.getBranch_no() == null : this.getBranch_no().equals(other.getBranch_no()))
            && (this.getData_grant() == null ? other.getData_grant() == null : this.getData_grant().equals(other.getData_grant()))
            && (this.getConfirm_pw() == null ? other.getConfirm_pw() == null : this.getConfirm_pw().equals(other.getConfirm_pw()))
            && (this.getCash_grant() == null ? other.getCash_grant() == null : this.getCash_grant().equals(other.getCash_grant()))
            && (this.getLimit_discount() == null ? other.getLimit_discount() == null : this.getLimit_discount().equals(other.getLimit_discount()))
            && (this.getSave_discount() == null ? other.getSave_discount() == null : this.getSave_discount().equals(other.getSave_discount()))
            && (this.getContral_center() == null ? other.getContral_center() == null : this.getContral_center().equals(other.getContral_center()))
            && (this.getPrice_grant() == null ? other.getPrice_grant() == null : this.getPrice_grant().equals(other.getPrice_grant()))
            && (this.getGroup_id() == null ? other.getGroup_id() == null : this.getGroup_id().equals(other.getGroup_id()))
            && (this.getOper_flag() == null ? other.getOper_flag() == null : this.getOper_flag().equals(other.getOper_flag()))
            && (this.getCharge() == null ? other.getCharge() == null : this.getCharge().equals(other.getCharge()))
            && (this.getArea_manager() == null ? other.getArea_manager() == null : this.getArea_manager().equals(other.getArea_manager()))
            && (this.getGrant_type() == null ? other.getGrant_type() == null : this.getGrant_type().equals(other.getGrant_type()))
            && (this.getOper_clsno() == null ? other.getOper_clsno() == null : this.getOper_clsno().equals(other.getOper_clsno()))
            && (this.getRecharge_limit() == null ? other.getRecharge_limit() == null : this.getRecharge_limit().equals(other.getRecharge_limit()))
            && (this.getRecharge_amt() == null ? other.getRecharge_amt() == null : this.getRecharge_amt().equals(other.getRecharge_amt()))
            && (this.getRecharge_date() == null ? other.getRecharge_date() == null : this.getRecharge_date().equals(other.getRecharge_date()))
            && (this.getOper_pw2() == null ? other.getOper_pw2() == null : this.getOper_pw2().equals(other.getOper_pw2()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOper_id() == null) ? 0 : getOper_id().hashCode());
        result = prime * result + ((getOper_name() == null) ? 0 : getOper_name().hashCode());
        result = prime * result + ((getOper_pw() == null) ? 0 : getOper_pw().hashCode());
        result = prime * result + ((getOper_status() == null) ? 0 : getOper_status().hashCode());
        result = prime * result + ((getOper_type() == null) ? 0 : getOper_type().hashCode());
        result = prime * result + ((getLast_time() == null) ? 0 : getLast_time().hashCode());
        result = prime * result + ((getOutput_rate() == null) ? 0 : getOutput_rate().hashCode());
        result = prime * result + ((getBranch_no() == null) ? 0 : getBranch_no().hashCode());
        result = prime * result + ((getData_grant() == null) ? 0 : getData_grant().hashCode());
        result = prime * result + ((getConfirm_pw() == null) ? 0 : getConfirm_pw().hashCode());
        result = prime * result + ((getCash_grant() == null) ? 0 : getCash_grant().hashCode());
        result = prime * result + ((getLimit_discount() == null) ? 0 : getLimit_discount().hashCode());
        result = prime * result + ((getSave_discount() == null) ? 0 : getSave_discount().hashCode());
        result = prime * result + ((getContral_center() == null) ? 0 : getContral_center().hashCode());
        result = prime * result + ((getPrice_grant() == null) ? 0 : getPrice_grant().hashCode());
        result = prime * result + ((getGroup_id() == null) ? 0 : getGroup_id().hashCode());
        result = prime * result + ((getOper_flag() == null) ? 0 : getOper_flag().hashCode());
        result = prime * result + ((getCharge() == null) ? 0 : getCharge().hashCode());
        result = prime * result + ((getArea_manager() == null) ? 0 : getArea_manager().hashCode());
        result = prime * result + ((getGrant_type() == null) ? 0 : getGrant_type().hashCode());
        result = prime * result + ((getOper_clsno() == null) ? 0 : getOper_clsno().hashCode());
        result = prime * result + ((getRecharge_limit() == null) ? 0 : getRecharge_limit().hashCode());
        result = prime * result + ((getRecharge_amt() == null) ? 0 : getRecharge_amt().hashCode());
        result = prime * result + ((getRecharge_date() == null) ? 0 : getRecharge_date().hashCode());
        result = prime * result + ((getOper_pw2() == null) ? 0 : getOper_pw2().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", operId=").append(oper_id);
        sb.append(", operName=").append(oper_name);
        sb.append(", operPw=").append(oper_pw);
        sb.append(", operStatus=").append(oper_status);
        sb.append(", operType=").append(oper_type);
        sb.append(", lastTime=").append(last_time);
        sb.append(", outputRate=").append(output_rate);
        sb.append(", branchNo=").append(branch_no);
        sb.append(", dataGrant=").append(data_grant);
        sb.append(", confirmPw=").append(confirm_pw);
        sb.append(", cashGrant=").append(cash_grant);
        sb.append(", limitDiscount=").append(limit_discount);
        sb.append(", saveDiscount=").append(save_discount);
        sb.append(", contralCenter=").append(contral_center);
        sb.append(", priceGrant=").append(price_grant);
        sb.append(", groupId=").append(group_id);
        sb.append(", operFlag=").append(oper_flag);
        sb.append(", charge=").append(charge);
        sb.append(", areaManager=").append(area_manager);
        sb.append(", grantType=").append(grant_type);
        sb.append(", operClsno=").append(oper_clsno);
        sb.append(", rechargeLimit=").append(recharge_limit);
        sb.append(", rechargeAmt=").append(recharge_amt);
        sb.append(", rechargeDate=").append(recharge_date);
        sb.append(", operPw2=").append(oper_pw2);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}