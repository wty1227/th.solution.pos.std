package cn.th.phonerf.model;

import java.io.Serializable;

/**
 * sys_system
 * @author 
 */
public class SysSystemKey implements Serializable {
    /**
     * id
     */
    private String sysVarId;

    /**
     * 客户id
     */
    private Integer merchantId;

    private static final long serialVersionUID = 1L;

    public String getSysVarId() {
        return sysVarId;
    }

    public void setSysVarId(String sysVarId) {
        this.sysVarId = sysVarId;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
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
        SysSystemKey other = (SysSystemKey) that;
        return (this.getSysVarId() == null ? other.getSysVarId() == null : this.getSysVarId().equals(other.getSysVarId()))
            && (this.getMerchantId() == null ? other.getMerchantId() == null : this.getMerchantId().equals(other.getMerchantId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSysVarId() == null) ? 0 : getSysVarId().hashCode());
        result = prime * result + ((getMerchantId() == null) ? 0 : getMerchantId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", sysVarId=").append(sysVarId);
        sb.append(", merchantId=").append(merchantId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}