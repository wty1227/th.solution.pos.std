package cn.th.phonerf.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * check_detail
 * @author 
 */
public class CheckDetail extends CheckDetailKey implements Serializable {
    /**
     * 机器号
     */
    private Integer phoneId;

    /**
     * 盘点数量
     */
    private BigDecimal realQty;
    /**
     * 服务器数量
     */
    private BigDecimal serverQty;

    private String comFlag;
    /**
     * 操作日期
     */
    private String operDate;

    private String itemSubno;

    private String itemName;

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private static final long serialVersionUID = 1L;

    public Integer getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Integer phoneId) {
        this.phoneId = phoneId;
    }

    public BigDecimal getRealQty() {
        return realQty;
    }

    public void setRealQty(BigDecimal realQty) {
        this.realQty = realQty;
    }

    public String getOperDate() {
        return operDate;
    }

    public void setOperDate(String operDate) {
        this.operDate = operDate;
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
        CheckDetail other = (CheckDetail) that;
        return (this.getCheckNo() == null ? other.getCheckNo() == null : this.getCheckNo().equals(other.getCheckNo()))
            && (this.getSheetNo() == null ? other.getSheetNo() == null : this.getSheetNo().equals(other.getSheetNo()))
            && (this.getItemNo() == null ? other.getItemNo() == null : this.getItemNo().equals(other.getItemNo()))
            && (this.getPhoneId() == null ? other.getPhoneId() == null : this.getPhoneId().equals(other.getPhoneId()))
            && (this.getRealQty() == null ? other.getRealQty() == null : this.getRealQty().equals(other.getRealQty()))
            && (this.getOperDate() == null ? other.getOperDate() == null : this.getOperDate().equals(other.getOperDate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCheckNo() == null) ? 0 : getCheckNo().hashCode());
        result = prime * result + ((getSheetNo() == null) ? 0 : getSheetNo().hashCode());
        result = prime * result + ((getItemNo() == null) ? 0 : getItemNo().hashCode());
        result = prime * result + ((getPhoneId() == null) ? 0 : getPhoneId().hashCode());
        result = prime * result + ((getRealQty() == null) ? 0 : getRealQty().hashCode());
        result = prime * result + ((getOperDate() == null) ? 0 : getOperDate().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", phoneId=").append(phoneId);
        sb.append(", realQty=").append(realQty);
        sb.append(", operDate=").append(operDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public String getItemSubno() {
        return itemSubno;
    }

    public void setItemSubno(String itemSubno) {
        this.itemSubno = itemSubno;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getServerQty() {
        return serverQty;
    }

    public void setServerQty(BigDecimal serverQty) {
        this.serverQty = serverQty;
    }

    public String getComFlag() {
        return comFlag;
    }

    public void setComFlag(String comFlag) {
        this.comFlag = comFlag;
    }
}