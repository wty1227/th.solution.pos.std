package cn.th.phonerf.model;

import java.io.Serializable;
import java.util.Date;

/**
 * merchant_phone
 * @author 
 */
public class MerchantPhone implements Serializable {
    /**
     * 手机设备id
     */
    private Integer phoneId;

    /**
     * 设备sn
     */
    private String phoneSn;

    /**
     * 客户id
     */
    private Integer merchantId;

    /**
     * 状态 0.有效 1.无效
     */
    private String clearFlag;

    /**
     * 操作日期
     */
    private Date userDate;

    /**
     * 版本
     */
    private String version;

    /**
     * 登录账户
     */
    private String userId;
    private String phone;

    private static final long serialVersionUID = 1L;

    public Integer getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Integer phoneId) {
        this.phoneId = phoneId;
    }

    public String getPhoneSn() {
        return phoneSn;
    }

    public void setPhoneSn(String phoneSn) {
        this.phoneSn = phoneSn;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getClearFlag() {
        return clearFlag;
    }

    public void setClearFlag(String clearFlag) {
        this.clearFlag = clearFlag;
    }

    public Date getUserDate() {
        return userDate;
    }

    public void setUserDate(Date userDate) {
        this.userDate = userDate;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
        MerchantPhone other = (MerchantPhone) that;
        return (this.getPhoneId() == null ? other.getPhoneId() == null : this.getPhoneId().equals(other.getPhoneId()))
            && (this.getPhoneSn() == null ? other.getPhoneSn() == null : this.getPhoneSn().equals(other.getPhoneSn()))
            && (this.getMerchantId() == null ? other.getMerchantId() == null : this.getMerchantId().equals(other.getMerchantId()))
            && (this.getClearFlag() == null ? other.getClearFlag() == null : this.getClearFlag().equals(other.getClearFlag()))
            && (this.getUserDate() == null ? other.getUserDate() == null : this.getUserDate().equals(other.getUserDate()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPhoneId() == null) ? 0 : getPhoneId().hashCode());
        result = prime * result + ((getPhoneSn() == null) ? 0 : getPhoneSn().hashCode());
        result = prime * result + ((getMerchantId() == null) ? 0 : getMerchantId().hashCode());
        result = prime * result + ((getClearFlag() == null) ? 0 : getClearFlag().hashCode());
        result = prime * result + ((getUserDate() == null) ? 0 : getUserDate().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", phoneId=").append(phoneId);
        sb.append(", phoneSn=").append(phoneSn);
        sb.append(", merchantId=").append(merchantId);
        sb.append(", clearFlag=").append(clearFlag);
        sb.append(", userDate=").append(userDate);
        sb.append(", version=").append(version);
        sb.append(", userId=").append(userId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}