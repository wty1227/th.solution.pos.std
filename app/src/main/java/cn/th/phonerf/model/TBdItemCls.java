package cn.th.phonerf.model;

import java.io.Serializable;

/**
 * t_bd_item_cls
 * @author 
 */
public class TBdItemCls implements Serializable {
    private String itemClsno;

    private String itemClsname;

    private String itemFlag;

    private String clsParent;

    private String displayFlag;

    private Double returnRate;

    private Double compRate;

    private String transFlag;

    private String upFlag;

    private static final long serialVersionUID = 1L;

    public String getItemClsno() {
        return itemClsno;
    }

    public void setItemClsno(String itemClsno) {
        this.itemClsno = itemClsno;
    }

    public String getItemClsname() {
        return itemClsname;
    }

    public void setItemClsname(String itemClsname) {
        this.itemClsname = itemClsname;
    }

    public String getItemFlag() {
        return itemFlag;
    }

    public void setItemFlag(String itemFlag) {
        this.itemFlag = itemFlag;
    }

    public String getClsParent() {
        return clsParent;
    }

    public void setClsParent(String clsParent) {
        this.clsParent = clsParent;
    }

    public String getDisplayFlag() {
        return displayFlag;
    }

    public void setDisplayFlag(String displayFlag) {
        this.displayFlag = displayFlag;
    }

    public Double getReturnRate() {
        return returnRate;
    }

    public void setReturnRate(Double returnRate) {
        this.returnRate = returnRate;
    }

    public Double getCompRate() {
        return compRate;
    }

    public void setCompRate(Double compRate) {
        this.compRate = compRate;
    }

    public String getTransFlag() {
        return transFlag;
    }

    public void setTransFlag(String transFlag) {
        this.transFlag = transFlag;
    }

    public String getUpFlag() {
        return upFlag;
    }

    public void setUpFlag(String upFlag) {
        this.upFlag = upFlag;
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
        TBdItemCls other = (TBdItemCls) that;
        return (this.getItemClsno() == null ? other.getItemClsno() == null : this.getItemClsno().equals(other.getItemClsno()))
            && (this.getItemClsname() == null ? other.getItemClsname() == null : this.getItemClsname().equals(other.getItemClsname()))
            && (this.getItemFlag() == null ? other.getItemFlag() == null : this.getItemFlag().equals(other.getItemFlag()))
            && (this.getClsParent() == null ? other.getClsParent() == null : this.getClsParent().equals(other.getClsParent()))
            && (this.getDisplayFlag() == null ? other.getDisplayFlag() == null : this.getDisplayFlag().equals(other.getDisplayFlag()))
            && (this.getReturnRate() == null ? other.getReturnRate() == null : this.getReturnRate().equals(other.getReturnRate()))
            && (this.getCompRate() == null ? other.getCompRate() == null : this.getCompRate().equals(other.getCompRate()))
            && (this.getTransFlag() == null ? other.getTransFlag() == null : this.getTransFlag().equals(other.getTransFlag()))
            && (this.getUpFlag() == null ? other.getUpFlag() == null : this.getUpFlag().equals(other.getUpFlag()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getItemClsno() == null) ? 0 : getItemClsno().hashCode());
        result = prime * result + ((getItemClsname() == null) ? 0 : getItemClsname().hashCode());
        result = prime * result + ((getItemFlag() == null) ? 0 : getItemFlag().hashCode());
        result = prime * result + ((getClsParent() == null) ? 0 : getClsParent().hashCode());
        result = prime * result + ((getDisplayFlag() == null) ? 0 : getDisplayFlag().hashCode());
        result = prime * result + ((getReturnRate() == null) ? 0 : getReturnRate().hashCode());
        result = prime * result + ((getCompRate() == null) ? 0 : getCompRate().hashCode());
        result = prime * result + ((getTransFlag() == null) ? 0 : getTransFlag().hashCode());
        result = prime * result + ((getUpFlag() == null) ? 0 : getUpFlag().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", itemClsno=").append(itemClsno);
        sb.append(", itemClsname=").append(itemClsname);
        sb.append(", itemFlag=").append(itemFlag);
        sb.append(", clsParent=").append(clsParent);
        sb.append(", displayFlag=").append(displayFlag);
        sb.append(", returnRate=").append(returnRate);
        sb.append(", compRate=").append(compRate);
        sb.append(", transFlag=").append(transFlag);
        sb.append(", upFlag=").append(upFlag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}