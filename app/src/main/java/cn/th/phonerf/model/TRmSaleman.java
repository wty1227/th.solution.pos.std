package cn.th.phonerf.model;

import java.io.Serializable;

/**
 * t_rm_saleman
 * @author 
 */
public class TRmSaleman implements Serializable {
    private String saleId;

    private String branchNo;

    private String saleName;

    private String saleStatus;

    private String memo;

    private Double returnRate;

    private String saleDuty;

    private Double saleDutyRate;

    private static final long serialVersionUID = 1L;

    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }

    public String getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(String saleStatus) {
        this.saleStatus = saleStatus;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Double getReturnRate() {
        return returnRate;
    }

    public void setReturnRate(Double returnRate) {
        this.returnRate = returnRate;
    }

    public String getSaleDuty() {
        return saleDuty;
    }

    public void setSaleDuty(String saleDuty) {
        this.saleDuty = saleDuty;
    }

    public Double getSaleDutyRate() {
        return saleDutyRate;
    }

    public void setSaleDutyRate(Double saleDutyRate) {
        this.saleDutyRate = saleDutyRate;
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
        TRmSaleman other = (TRmSaleman) that;
        return (this.getSaleId() == null ? other.getSaleId() == null : this.getSaleId().equals(other.getSaleId()))
            && (this.getBranchNo() == null ? other.getBranchNo() == null : this.getBranchNo().equals(other.getBranchNo()))
            && (this.getSaleName() == null ? other.getSaleName() == null : this.getSaleName().equals(other.getSaleName()))
            && (this.getSaleStatus() == null ? other.getSaleStatus() == null : this.getSaleStatus().equals(other.getSaleStatus()))
            && (this.getMemo() == null ? other.getMemo() == null : this.getMemo().equals(other.getMemo()))
            && (this.getReturnRate() == null ? other.getReturnRate() == null : this.getReturnRate().equals(other.getReturnRate()))
            && (this.getSaleDuty() == null ? other.getSaleDuty() == null : this.getSaleDuty().equals(other.getSaleDuty()))
            && (this.getSaleDutyRate() == null ? other.getSaleDutyRate() == null : this.getSaleDutyRate().equals(other.getSaleDutyRate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSaleId() == null) ? 0 : getSaleId().hashCode());
        result = prime * result + ((getBranchNo() == null) ? 0 : getBranchNo().hashCode());
        result = prime * result + ((getSaleName() == null) ? 0 : getSaleName().hashCode());
        result = prime * result + ((getSaleStatus() == null) ? 0 : getSaleStatus().hashCode());
        result = prime * result + ((getMemo() == null) ? 0 : getMemo().hashCode());
        result = prime * result + ((getReturnRate() == null) ? 0 : getReturnRate().hashCode());
        result = prime * result + ((getSaleDuty() == null) ? 0 : getSaleDuty().hashCode());
        result = prime * result + ((getSaleDutyRate() == null) ? 0 : getSaleDutyRate().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", saleId=").append(saleId);
        sb.append(", branchNo=").append(branchNo);
        sb.append(", saleName=").append(saleName);
        sb.append(", saleStatus=").append(saleStatus);
        sb.append(", memo=").append(memo);
        sb.append(", returnRate=").append(returnRate);
        sb.append(", saleDuty=").append(saleDuty);
        sb.append(", saleDutyRate=").append(saleDutyRate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}