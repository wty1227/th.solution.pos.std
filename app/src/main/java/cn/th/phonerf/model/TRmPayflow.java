package cn.th.phonerf.model;

import java.io.Serializable;
import java.util.Date;

/**
 * t_rm_payflow
 * @author 
 */
public class TRmPayflow implements Serializable {
    private Integer comNo;

    private Integer flowId;

    private String flowNo;

    private Double saleAmount;

    private String branchNo;

    private String payWay;
    private String payName = "";

    private String sellWay;

    private String cardNo;

    private String cardId;

    private String vipNo;

    private String coinNo;

    private Double coinRate;

    private Double payAmount;

    private String operDate;

    private String operId;

    private String counterNo;

    private String saleMan;

    private String cashierNo;

    private String memo;

    private String voucherNo;

    private String shiftNo;

    private String comFlag;

    private String remark;

    private String rdmNo;

    public Double chg_amt = 0d;
    private static final long serialVersionUID = 1L;

    public Integer getComNo() {
        return comNo;
    }

    public void setComNo(Integer comNo) {
        this.comNo = comNo;
    }

    public Integer getFlowId() {
        return flowId;
    }

    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
    }

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
    }

    public Double getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(Double saleAmount) {
        this.saleAmount = saleAmount;
    }

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getSellWay() {
        return sellWay;
    }

    public void setSellWay(String sellWay) {
        this.sellWay = sellWay;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getVipNo() {
        return vipNo;
    }

    public void setVipNo(String vipNo) {
        this.vipNo = vipNo;
    }

    public String getCoinNo() {
        return coinNo;
    }

    public void setCoinNo(String coinNo) {
        this.coinNo = coinNo;
    }

    public Double getCoinRate() {
        return coinRate;
    }

    public void setCoinRate(Double coinRate) {
        this.coinRate = coinRate;
    }

    public Double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Double payAmount) {
        this.payAmount = payAmount;
    }

    public String getOperDate() {
        return operDate;
    }

    public void setOperDate(String operDate) {
        this.operDate = operDate;
    }

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId;
    }

    public String getCounterNo() {
        return counterNo;
    }

    public void setCounterNo(String counterNo) {
        this.counterNo = counterNo;
    }

    public String getSaleMan() {
        return saleMan;
    }

    public void setSaleMan(String saleMan) {
        this.saleMan = saleMan;
    }

    public String getCashierNo() {
        return cashierNo;
    }

    public void setCashierNo(String cashierNo) {
        this.cashierNo = cashierNo;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getShiftNo() {
        return shiftNo;
    }

    public void setShiftNo(String shiftNo) {
        this.shiftNo = shiftNo;
    }

    public String getComFlag() {
        return comFlag;
    }

    public void setComFlag(String comFlag) {
        this.comFlag = comFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRdmNo() {
        return rdmNo;
    }

    public void setRdmNo(String rdmNo) {
        this.rdmNo = rdmNo;
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
        TRmPayflow other = (TRmPayflow) that;
        return (this.getComNo() == null ? other.getComNo() == null : this.getComNo().equals(other.getComNo()))
            && (this.getFlowId() == null ? other.getFlowId() == null : this.getFlowId().equals(other.getFlowId()))
            && (this.getFlowNo() == null ? other.getFlowNo() == null : this.getFlowNo().equals(other.getFlowNo()))
            && (this.getSaleAmount() == null ? other.getSaleAmount() == null : this.getSaleAmount().equals(other.getSaleAmount()))
            && (this.getBranchNo() == null ? other.getBranchNo() == null : this.getBranchNo().equals(other.getBranchNo()))
            && (this.getPayWay() == null ? other.getPayWay() == null : this.getPayWay().equals(other.getPayWay()))
            && (this.getSellWay() == null ? other.getSellWay() == null : this.getSellWay().equals(other.getSellWay()))
            && (this.getCardNo() == null ? other.getCardNo() == null : this.getCardNo().equals(other.getCardNo()))
            && (this.getCardId() == null ? other.getCardId() == null : this.getCardId().equals(other.getCardId()))
            && (this.getVipNo() == null ? other.getVipNo() == null : this.getVipNo().equals(other.getVipNo()))
            && (this.getCoinNo() == null ? other.getCoinNo() == null : this.getCoinNo().equals(other.getCoinNo()))
            && (this.getCoinRate() == null ? other.getCoinRate() == null : this.getCoinRate().equals(other.getCoinRate()))
            && (this.getPayAmount() == null ? other.getPayAmount() == null : this.getPayAmount().equals(other.getPayAmount()))
            && (this.getOperDate() == null ? other.getOperDate() == null : this.getOperDate().equals(other.getOperDate()))
            && (this.getOperId() == null ? other.getOperId() == null : this.getOperId().equals(other.getOperId()))
            && (this.getCounterNo() == null ? other.getCounterNo() == null : this.getCounterNo().equals(other.getCounterNo()))
            && (this.getSaleMan() == null ? other.getSaleMan() == null : this.getSaleMan().equals(other.getSaleMan()))
            && (this.getCashierNo() == null ? other.getCashierNo() == null : this.getCashierNo().equals(other.getCashierNo()))
            && (this.getMemo() == null ? other.getMemo() == null : this.getMemo().equals(other.getMemo()))
            && (this.getVoucherNo() == null ? other.getVoucherNo() == null : this.getVoucherNo().equals(other.getVoucherNo()))
            && (this.getShiftNo() == null ? other.getShiftNo() == null : this.getShiftNo().equals(other.getShiftNo()))
            && (this.getComFlag() == null ? other.getComFlag() == null : this.getComFlag().equals(other.getComFlag()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getRdmNo() == null ? other.getRdmNo() == null : this.getRdmNo().equals(other.getRdmNo()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getComNo() == null) ? 0 : getComNo().hashCode());
        result = prime * result + ((getFlowId() == null) ? 0 : getFlowId().hashCode());
        result = prime * result + ((getFlowNo() == null) ? 0 : getFlowNo().hashCode());
        result = prime * result + ((getSaleAmount() == null) ? 0 : getSaleAmount().hashCode());
        result = prime * result + ((getBranchNo() == null) ? 0 : getBranchNo().hashCode());
        result = prime * result + ((getPayWay() == null) ? 0 : getPayWay().hashCode());
        result = prime * result + ((getSellWay() == null) ? 0 : getSellWay().hashCode());
        result = prime * result + ((getCardNo() == null) ? 0 : getCardNo().hashCode());
        result = prime * result + ((getCardId() == null) ? 0 : getCardId().hashCode());
        result = prime * result + ((getVipNo() == null) ? 0 : getVipNo().hashCode());
        result = prime * result + ((getCoinNo() == null) ? 0 : getCoinNo().hashCode());
        result = prime * result + ((getCoinRate() == null) ? 0 : getCoinRate().hashCode());
        result = prime * result + ((getPayAmount() == null) ? 0 : getPayAmount().hashCode());
        result = prime * result + ((getOperDate() == null) ? 0 : getOperDate().hashCode());
        result = prime * result + ((getOperId() == null) ? 0 : getOperId().hashCode());
        result = prime * result + ((getCounterNo() == null) ? 0 : getCounterNo().hashCode());
        result = prime * result + ((getSaleMan() == null) ? 0 : getSaleMan().hashCode());
        result = prime * result + ((getCashierNo() == null) ? 0 : getCashierNo().hashCode());
        result = prime * result + ((getMemo() == null) ? 0 : getMemo().hashCode());
        result = prime * result + ((getVoucherNo() == null) ? 0 : getVoucherNo().hashCode());
        result = prime * result + ((getShiftNo() == null) ? 0 : getShiftNo().hashCode());
        result = prime * result + ((getComFlag() == null) ? 0 : getComFlag().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getRdmNo() == null) ? 0 : getRdmNo().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", comNo=").append(comNo);
        sb.append(", flowId=").append(flowId);
        sb.append(", flowNo=").append(flowNo);
        sb.append(", saleAmount=").append(saleAmount);
        sb.append(", branchNo=").append(branchNo);
        sb.append(", payWay=").append(payWay);
        sb.append(", sellWay=").append(sellWay);
        sb.append(", cardNo=").append(cardNo);
        sb.append(", cardId=").append(cardId);
        sb.append(", vipNo=").append(vipNo);
        sb.append(", coinNo=").append(coinNo);
        sb.append(", coinRate=").append(coinRate);
        sb.append(", payAmount=").append(payAmount);
        sb.append(", operDate=").append(operDate);
        sb.append(", operId=").append(operId);
        sb.append(", counterNo=").append(counterNo);
        sb.append(", saleMan=").append(saleMan);
        sb.append(", cashierNo=").append(cashierNo);
        sb.append(", memo=").append(memo);
        sb.append(", voucherNo=").append(voucherNo);
        sb.append(", shiftNo=").append(shiftNo);
        sb.append(", comFlag=").append(comFlag);
        sb.append(", remark=").append(remark);
        sb.append(", rdmNo=").append(rdmNo);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }
}