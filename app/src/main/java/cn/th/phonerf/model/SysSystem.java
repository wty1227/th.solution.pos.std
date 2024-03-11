package cn.th.phonerf.model;

import java.io.Serializable;

/**
 * sys_system
 * @author 
 */
public class SysSystem extends SysSystemKey implements Serializable {
    /**
     * 名称
     */
    private String sysVarName;

    /**
     * 值
     */
    private String sysVarValue;

    private static final long serialVersionUID = 1L;

    public String getSysVarName() {
        return sysVarName;
    }

    public void setSysVarName(String sysVarName) {
        this.sysVarName = sysVarName;
    }

    public String getSysVarValue() {
        return sysVarValue;
    }

    public void setSysVarValue(String sysVarValue) {
        this.sysVarValue = sysVarValue;
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
        SysSystem other = (SysSystem) that;
        return (this.getSysVarId() == null ? other.getSysVarId() == null : this.getSysVarId().equals(other.getSysVarId()))
            && (this.getMerchantId() == null ? other.getMerchantId() == null : this.getMerchantId().equals(other.getMerchantId()))
            && (this.getSysVarName() == null ? other.getSysVarName() == null : this.getSysVarName().equals(other.getSysVarName()))
            && (this.getSysVarValue() == null ? other.getSysVarValue() == null : this.getSysVarValue().equals(other.getSysVarValue()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSysVarId() == null) ? 0 : getSysVarId().hashCode());
        result = prime * result + ((getMerchantId() == null) ? 0 : getMerchantId().hashCode());
        result = prime * result + ((getSysVarName() == null) ? 0 : getSysVarName().hashCode());
        result = prime * result + ((getSysVarValue() == null) ? 0 : getSysVarValue().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", sysVarName=").append(sysVarName);
        sb.append(", sysVarValue=").append(sysVarValue);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}