package cn.th.phonerf.model;

import java.io.Serializable;
import java.util.Date;

/**
 * check_init
 * @author 
 */
public class CheckInit implements Serializable {
    /**
     * 批次
     */
    private String checkNo;

    /**
     * 门店
     */
    private String shopId;

    /**
     * 客户id
     */
    private Integer merchantId;

    /**
     * 操作日期
     */
    private String operDate;

    /**
     * 备注
     */
    private String memo;

    /**
     * 状态 0.保存 1.审核
     */
    private String status;

    private static final long serialVersionUID = 1L;

    public String getCheckNo() {
        return checkNo;
    }

    public void setCheckNo(String checkNo) {
        this.checkNo = checkNo;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getOperDate() {
        return operDate;
    }

    public void setOperDate(String operDate) {
        this.operDate = operDate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        CheckInit other = (CheckInit) that;
        return (this.getCheckNo() == null ? other.getCheckNo() == null : this.getCheckNo().equals(other.getCheckNo()))
            && (this.getShopId() == null ? other.getShopId() == null : this.getShopId().equals(other.getShopId()))
            && (this.getMerchantId() == null ? other.getMerchantId() == null : this.getMerchantId().equals(other.getMerchantId()))
            && (this.getOperDate() == null ? other.getOperDate() == null : this.getOperDate().equals(other.getOperDate()))
            && (this.getMemo() == null ? other.getMemo() == null : this.getMemo().equals(other.getMemo()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCheckNo() == null) ? 0 : getCheckNo().hashCode());
        result = prime * result + ((getShopId() == null) ? 0 : getShopId().hashCode());
        result = prime * result + ((getMerchantId() == null) ? 0 : getMerchantId().hashCode());
        result = prime * result + ((getOperDate() == null) ? 0 : getOperDate().hashCode());
        result = prime * result + ((getMemo() == null) ? 0 : getMemo().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "[" + shopId + "]" + checkNo;
        /*StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", checkNo=").append(checkNo);
        sb.append(", shopId=").append(shopId);
        sb.append(", merchantId=").append(merchantId);
        sb.append(", operDate=").append(operDate);
        sb.append(", memo=").append(memo);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();*/
    }
}