package cn.th.phonerf.model;

import java.io.Serializable;

/**
 * merchant_shop
 * @author 
 */
public class MerchantShop extends MerchantShopKey implements Serializable {
    /**
     * 门店名称
     */
    private String shopName;

    /**
     * 客户服务端接口的地址
     */
    private String apiUrl;

    /**
     * 状态 0.有效 1.无效
     */
    private String clearFlag;

    private String deployFlag;
    private String jdbcDriver;
    private String jdbcUrl;
    private String jdbcUsername;
    private String jdbcPassword;

    public String getDeployFlag() {
        return deployFlag;
    }

    public void setDeployFlag(String deployFlag) {
        this.deployFlag = deployFlag;
    }

    public String getJdbcDriver() {
        return jdbcDriver;
    }

    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getJdbcUsername() {
        return jdbcUsername;
    }

    public void setJdbcUsername(String jdbcUsername) {
        this.jdbcUsername = jdbcUsername;
    }

    public String getJdbcPassword() {
        return jdbcPassword;
    }

    public void setJdbcPassword(String jdbcPassword) {
        this.jdbcPassword = jdbcPassword;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    private static final long serialVersionUID = 1L;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getClearFlag() {
        return clearFlag;
    }

    public void setClearFlag(String clearFlag) {
        this.clearFlag = clearFlag;
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
        MerchantShop other = (MerchantShop) that;
        return (this.getShopId() == null ? other.getShopId() == null : this.getShopId().equals(other.getShopId()))
            && (this.getMerchantId() == null ? other.getMerchantId() == null : this.getMerchantId().equals(other.getMerchantId()))
            && (this.getShopName() == null ? other.getShopName() == null : this.getShopName().equals(other.getShopName()))
            && (this.getApiUrl() == null ? other.getApiUrl() == null : this.getApiUrl().equals(other.getApiUrl()))
            && (this.getClearFlag() == null ? other.getClearFlag() == null : this.getClearFlag().equals(other.getClearFlag()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getShopId() == null) ? 0 : getShopId().hashCode());
        result = prime * result + ((getMerchantId() == null) ? 0 : getMerchantId().hashCode());
        result = prime * result + ((getShopName() == null) ? 0 : getShopName().hashCode());
        result = prime * result + ((getApiUrl() == null) ? 0 : getApiUrl().hashCode());
        result = prime * result + ((getClearFlag() == null) ? 0 : getClearFlag().hashCode());
        return result;
    }
    @Override
    public String toString() {
        //return super.toString();
        return "[" + super.getShopId() + "]" + shopName;
    }
    /*
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", BranchName=").append(BranchName);
        sb.append(", apiUrl=").append(apiUrl);
        sb.append(", clearFlag=").append(clearFlag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }*/
}