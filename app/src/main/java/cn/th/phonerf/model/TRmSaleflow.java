package cn.th.phonerf.model;

import java.io.Serializable;

/**
 * t_rm_saleflow
 * @author 
 */
public class TRmSaleflow implements Serializable {
    private Integer com_no;

    private Integer flow_id;

    private String flow_no;

    private String branch_no;

    private String item_no;
    private String item_subno;

    private Double source_price;

    private Double sale_price;

    private Double cost_price;

    private Double sale_qnty;

    private Double sale_money;

    private String sell_way;

    private String oper_id;

    private String sale_man;
    private String sale_name;

    private String oper_date;

    private Double shift_no;

    private String com_flag;

    private String spec_flag = "";

    private Double pref_amt = 0.00;

    private Double in_price = 0.00;

    private Double acc_num = 0.00;

    private Double dec_rate = 0.00;

    private String card_id;

    private String card_no;

    private Integer pro_id = 0;

    private Double clss_rate = 0.00;

    private Double brand_rate = 0.00;

    private Double sale_rate = 0.00;

    private Double sale_duty_rate = 0.00;

    private String plan_no = "";

    private String serials_no = "";

    private String gift_msg = "";

    private String memo = "";

    private String main_sup;

    private String item_name;

    private boolean checked = false;

    private Integer usra_id;

    private int measure_flag;

    private static final long serialVersionUID = 1L;

    public TRmSaleflow() {
        measure_flag = 0;
    }

    public Integer getCom_no() {
        return com_no;
    }

    public void setCom_no(Integer com_no) {
        this.com_no = com_no;
    }

    public Integer getFlow_id() {
        return flow_id;
    }

    public void setFlow_id(Integer flow_id) {
        this.flow_id = flow_id;
    }

    public String getFlow_no() {
        return flow_no;
    }

    public void setFlow_no(String flow_no) {
        this.flow_no = flow_no;
    }

    public String getBranch_no() {
        return branch_no;
    }

    public void setBranch_no(String branch_no) {
        this.branch_no = branch_no;
    }

    public String getItem_no() {
        return item_no;
    }

    public void setItem_no(String item_no) {
        this.item_no = item_no;
    }

    public Double getSource_price() {
        return source_price;
    }

    public void setSource_price(Double source_price) {
        this.source_price = source_price;
    }

    public Double getSale_price() {
        return sale_price;
    }

    public void setSale_price(Double sale_price) {
        this.sale_price = sale_price;
    }

    public Double getCost_price() {
        return cost_price;
    }

    public void setCost_price(Double cost_price) {
        this.cost_price = cost_price;
    }

    public Double getSale_qnty() {
        return sale_qnty;
    }

    public void setSale_qnty(Double sale_qnty) {
        this.sale_qnty = sale_qnty;
    }

    public Double getSale_money() {
        return sale_money;
    }

    public void setSale_money(Double sale_money) {
        this.sale_money = sale_money;
    }

    public String getSell_way() {
        return sell_way;
    }

    public void setSell_way(String sell_way) {
        this.sell_way = sell_way;
    }

    public String getOper_id() {
        return oper_id;
    }

    public void setOper_id(String oper_id) {
        this.oper_id = oper_id;
    }

    public String getSale_man() {
        return sale_man;
    }

    public void setSale_man(String sale_man) {
        this.sale_man = sale_man;
    }

    public String getOper_date() {
        return oper_date;
    }

    public void setOper_date(String oper_date) {
        this.oper_date = oper_date;
    }

    public Double getShift_no() {
        return shift_no;
    }

    public void setShift_no(Double shift_no) {
        this.shift_no = shift_no;
    }

    public String getCom_flag() {
        return com_flag;
    }

    public void setCom_flag(String com_flag) {
        this.com_flag = com_flag;
    }

    public String getSpec_flag() {
        return spec_flag;
    }

    public void setSpec_flag(String spec_flag) {
        this.spec_flag = spec_flag;
    }

    public Double getPref_amt() {
        return pref_amt;
    }

    public void setPref_amt(Double pref_amt) {
        this.pref_amt = pref_amt;
    }

    public Double getIn_price() {
        return in_price;
    }

    public void setIn_price(Double in_price) {
        this.in_price = in_price;
    }

    public Double getAcc_num() {
        return acc_num;
    }

    public void setAcc_num(Double acc_num) {
        this.acc_num = acc_num;
    }

    public Double getDec_rate() {
        return dec_rate;
    }

    public void setDec_rate(Double dec_rate) {
        this.dec_rate = dec_rate;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public Integer getPro_id() {
        return pro_id;
    }

    public void setPro_id(Integer pro_id) {
        this.pro_id = pro_id;
    }

    public Double getClss_rate() {
        return clss_rate;
    }

    public void setClss_rate(Double clss_rate) {
        this.clss_rate = clss_rate;
    }

    public Double getBrand_rate() {
        return brand_rate;
    }

    public void setBrand_rate(Double brand_rate) {
        this.brand_rate = brand_rate;
    }

    public Double getSale_rate() {
        return sale_rate;
    }

    public void setSale_rate(Double sale_rate) {
        this.sale_rate = sale_rate;
    }

    public Double getSale_duty_rate() {
        return sale_duty_rate;
    }

    public void setSale_duty_rate(Double sale_duty_rate) {
        this.sale_duty_rate = sale_duty_rate;
    }

    public String getPlan_no() {
        return plan_no;
    }

    public void setPlan_no(String plan_no) {
        this.plan_no = plan_no;
    }

    public String getSerials_no() {
        return serials_no;
    }

    public void setSerials_no(String serials_no) {
        this.serials_no = serials_no;
    }

    public String getGift_msg() {
        return gift_msg;
    }

    public void setGift_msg(String gift_msg) {
        this.gift_msg = gift_msg;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getMain_sup() {
        return main_sup;
    }

    public void setMain_sup(String main_sup) {
        this.main_sup = main_sup;
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
        TRmSaleflow other = (TRmSaleflow) that;
        return (this.getCom_no() == null ? other.getCom_no() == null : this.getCom_no().equals(other.getCom_no()))
            && (this.getFlow_id() == null ? other.getFlow_id() == null : this.getFlow_id().equals(other.getFlow_id()))
            && (this.getFlow_no() == null ? other.getFlow_no() == null : this.getFlow_no().equals(other.getFlow_no()))
            && (this.getBranch_no() == null ? other.getBranch_no() == null : this.getBranch_no().equals(other.getBranch_no()))
            && (this.getItem_no() == null ? other.getItem_no() == null : this.getItem_no().equals(other.getItem_no()))
            && (this.getSource_price() == null ? other.getSource_price() == null : this.getSource_price().equals(other.getSource_price()))
            && (this.getSale_price() == null ? other.getSale_price() == null : this.getSale_price().equals(other.getSale_price()))
            && (this.getCost_price() == null ? other.getCost_price() == null : this.getCost_price().equals(other.getCost_price()))
            && (this.getSale_qnty() == null ? other.getSale_qnty() == null : this.getSale_qnty().equals(other.getSale_qnty()))
            && (this.getSale_money() == null ? other.getSale_money() == null : this.getSale_money().equals(other.getSale_money()))
            && (this.getSell_way() == null ? other.getSell_way() == null : this.getSell_way().equals(other.getSell_way()))
            && (this.getOper_id() == null ? other.getOper_id() == null : this.getOper_id().equals(other.getOper_id()))
            && (this.getSale_man() == null ? other.getSale_man() == null : this.getSale_man().equals(other.getSale_man()))
            && (this.getOper_date() == null ? other.getOper_date() == null : this.getOper_date().equals(other.getOper_date()))
            && (this.getShift_no() == null ? other.getShift_no() == null : this.getShift_no().equals(other.getShift_no()))
            && (this.getCom_flag() == null ? other.getCom_flag() == null : this.getCom_flag().equals(other.getCom_flag()))
            && (this.getSpec_flag() == null ? other.getSpec_flag() == null : this.getSpec_flag().equals(other.getSpec_flag()))
            && (this.getPref_amt() == null ? other.getPref_amt() == null : this.getPref_amt().equals(other.getPref_amt()))
            && (this.getIn_price() == null ? other.getIn_price() == null : this.getIn_price().equals(other.getIn_price()))
            && (this.getAcc_num() == null ? other.getAcc_num() == null : this.getAcc_num().equals(other.getAcc_num()))
            && (this.getDec_rate() == null ? other.getDec_rate() == null : this.getDec_rate().equals(other.getDec_rate()))
            && (this.getCard_id() == null ? other.getCard_id() == null : this.getCard_id().equals(other.getCard_id()))
            && (this.getCard_no() == null ? other.getCard_no() == null : this.getCard_no().equals(other.getCard_no()))
            && (this.getPro_id() == null ? other.getPro_id() == null : this.getPro_id().equals(other.getPro_id()))
            && (this.getClss_rate() == null ? other.getClss_rate() == null : this.getClss_rate().equals(other.getClss_rate()))
            && (this.getBrand_rate() == null ? other.getBrand_rate() == null : this.getBrand_rate().equals(other.getBrand_rate()))
            && (this.getSale_rate() == null ? other.getSale_rate() == null : this.getSale_rate().equals(other.getSale_rate()))
            && (this.getSale_duty_rate() == null ? other.getSale_duty_rate() == null : this.getSale_duty_rate().equals(other.getSale_duty_rate()))
            && (this.getPlan_no() == null ? other.getPlan_no() == null : this.getPlan_no().equals(other.getPlan_no()))
            && (this.getSerials_no() == null ? other.getSerials_no() == null : this.getSerials_no().equals(other.getSerials_no()))
            && (this.getGift_msg() == null ? other.getGift_msg() == null : this.getGift_msg().equals(other.getGift_msg()))
            && (this.getMemo() == null ? other.getMemo() == null : this.getMemo().equals(other.getMemo()))
            && (this.getMain_sup() == null ? other.getMain_sup() == null : this.getMain_sup().equals(other.getMain_sup()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCom_no() == null) ? 0 : getCom_no().hashCode());
        result = prime * result + ((getFlow_id() == null) ? 0 : getFlow_id().hashCode());
        result = prime * result + ((getFlow_no() == null) ? 0 : getFlow_no().hashCode());
        result = prime * result + ((getBranch_no() == null) ? 0 : getBranch_no().hashCode());
        result = prime * result + ((getItem_no() == null) ? 0 : getItem_no().hashCode());
        result = prime * result + ((getSource_price() == null) ? 0 : getSource_price().hashCode());
        result = prime * result + ((getSale_price() == null) ? 0 : getSale_price().hashCode());
        result = prime * result + ((getCost_price() == null) ? 0 : getCost_price().hashCode());
        result = prime * result + ((getSale_qnty() == null) ? 0 : getSale_qnty().hashCode());
        result = prime * result + ((getSale_money() == null) ? 0 : getSale_money().hashCode());
        result = prime * result + ((getSell_way() == null) ? 0 : getSell_way().hashCode());
        result = prime * result + ((getOper_id() == null) ? 0 : getOper_id().hashCode());
        result = prime * result + ((getSale_man() == null) ? 0 : getSale_man().hashCode());
        result = prime * result + ((getOper_date() == null) ? 0 : getOper_date().hashCode());
        result = prime * result + ((getShift_no() == null) ? 0 : getShift_no().hashCode());
        result = prime * result + ((getCom_flag() == null) ? 0 : getCom_flag().hashCode());
        result = prime * result + ((getSpec_flag() == null) ? 0 : getSpec_flag().hashCode());
        result = prime * result + ((getPref_amt() == null) ? 0 : getPref_amt().hashCode());
        result = prime * result + ((getIn_price() == null) ? 0 : getIn_price().hashCode());
        result = prime * result + ((getAcc_num() == null) ? 0 : getAcc_num().hashCode());
        result = prime * result + ((getDec_rate() == null) ? 0 : getDec_rate().hashCode());
        result = prime * result + ((getCard_id() == null) ? 0 : getCard_id().hashCode());
        result = prime * result + ((getCard_no() == null) ? 0 : getCard_no().hashCode());
        result = prime * result + ((getPro_id() == null) ? 0 : getPro_id().hashCode());
        result = prime * result + ((getClss_rate() == null) ? 0 : getClss_rate().hashCode());
        result = prime * result + ((getBrand_rate() == null) ? 0 : getBrand_rate().hashCode());
        result = prime * result + ((getSale_rate() == null) ? 0 : getSale_rate().hashCode());
        result = prime * result + ((getSale_duty_rate() == null) ? 0 : getSale_duty_rate().hashCode());
        result = prime * result + ((getPlan_no() == null) ? 0 : getPlan_no().hashCode());
        result = prime * result + ((getSerials_no() == null) ? 0 : getSerials_no().hashCode());
        result = prime * result + ((getGift_msg() == null) ? 0 : getGift_msg().hashCode());
        result = prime * result + ((getMemo() == null) ? 0 : getMemo().hashCode());
        result = prime * result + ((getMain_sup() == null) ? 0 : getMain_sup().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", comNo=").append(com_no);
        sb.append(", flowId=").append(flow_id);
        sb.append(", flowNo=").append(flow_no);
        sb.append(", branchNo=").append(branch_no);
        sb.append(", itemNo=").append(item_no);
        sb.append(", sourcePrice=").append(source_price);
        sb.append(", salePrice=").append(sale_price);
        sb.append(", costPrice=").append(cost_price);
        sb.append(", saleQnty=").append(sale_qnty);
        sb.append(", saleMoney=").append(sale_money);
        sb.append(", sellWay=").append(sell_way);
        sb.append(", operId=").append(oper_id);
        sb.append(", saleMan=").append(sale_man);
        sb.append(", operDate=").append(oper_date);
        sb.append(", shiftNo=").append(shift_no);
        sb.append(", comFlag=").append(com_flag);
        sb.append(", specFlag=").append(spec_flag);
        sb.append(", prefAmt=").append(pref_amt);
        sb.append(", inPrice=").append(in_price);
        sb.append(", accNum=").append(acc_num);
        sb.append(", decRate=").append(dec_rate);
        sb.append(", cardId=").append(card_id);
        sb.append(", cardNo=").append(card_no);
        sb.append(", proId=").append(pro_id);
        sb.append(", clssRate=").append(clss_rate);
        sb.append(", brandRate=").append(brand_rate);
        sb.append(", saleRate=").append(sale_rate);
        sb.append(", saleDutyRate=").append(sale_duty_rate);
        sb.append(", planNo=").append(plan_no);
        sb.append(", serialsNo=").append(serials_no);
        sb.append(", giftMsg=").append(gift_msg);
        sb.append(", memo=").append(memo);
        sb.append(", mainSup=").append(main_sup);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public String toLog() {
        StringBuilder sb = new StringBuilder();
        sb.append(" [");
        sb.append(" itemNo=").append(item_no);
        sb.append(", salePrice=").append(sale_price);
        sb.append(", saleQnty=").append(sale_qnty);
        sb.append(", saleMoney=").append(sale_money);
        sb.append("]");
        return sb.toString();
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }


    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getSale_name() {
        return sale_name;
    }

    public void setSale_name(String sale_name) {
        this.sale_name = sale_name;
    }

    public String getItem_subno() {
        return item_subno;
    }

    public void setItem_subno(String item_subno) {
        this.item_subno = item_subno;
    }

    public Integer getUsra_id() {
        return usra_id;
    }

    public void setUsra_id(Integer usra_id) {
        this.usra_id = usra_id;
    }

    public int getMeasure_flag() {
        return measure_flag;
    }

    public void setMeasure_flag(int measure_flag) {
        this.measure_flag = measure_flag;
    }
}