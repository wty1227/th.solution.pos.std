package cn.th.phonerf.model;

import java.io.Serializable;
import java.util.Date;

/**
 * merchant_user
 * @author 
 */
public class MerchantUser extends MerchantUserKey implements Serializable {
    /**
     * 姓名
     */
    private String userName;

    /**
     * 密码
     */
    private String userPass;

    /**
     * 商户系统记录id
     */
    private String loginId;

    /**
     * 状态 0.有效 1.无效
     */
    private String clearFlag;

    /**
     * 操作日期
     */
    private Date userDate;

    /**
     * 最后使用的机器id
     */
    private Integer phoneId;

    private static final long serialVersionUID = 1L;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
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

    public Integer getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Integer phoneId) {
        this.phoneId = phoneId;
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
        MerchantUser other = (MerchantUser) that;
        return (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getMerchantId() == null ? other.getMerchantId() == null : this.getMerchantId().equals(other.getMerchantId()))
            && (this.getUserName() == null ? other.getUserName() == null : this.getUserName().equals(other.getUserName()))
            && (this.getUserPass() == null ? other.getUserPass() == null : this.getUserPass().equals(other.getUserPass()))
            && (this.getLoginId() == null ? other.getLoginId() == null : this.getLoginId().equals(other.getLoginId()))
            && (this.getClearFlag() == null ? other.getClearFlag() == null : this.getClearFlag().equals(other.getClearFlag()))
            && (this.getUserDate() == null ? other.getUserDate() == null : this.getUserDate().equals(other.getUserDate()))
            && (this.getPhoneId() == null ? other.getPhoneId() == null : this.getPhoneId().equals(other.getPhoneId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getMerchantId() == null) ? 0 : getMerchantId().hashCode());
        result = prime * result + ((getUserName() == null) ? 0 : getUserName().hashCode());
        result = prime * result + ((getUserPass() == null) ? 0 : getUserPass().hashCode());
        result = prime * result + ((getLoginId() == null) ? 0 : getLoginId().hashCode());
        result = prime * result + ((getClearFlag() == null) ? 0 : getClearFlag().hashCode());
        result = prime * result + ((getUserDate() == null) ? 0 : getUserDate().hashCode());
        result = prime * result + ((getPhoneId() == null) ? 0 : getPhoneId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", OperName=").append(userName);
        sb.append(", userPass=").append(userPass);
        sb.append(", loginId=").append(loginId);
        sb.append(", clearFlag=").append(clearFlag);
        sb.append(", userDate=").append(userDate);
        sb.append(", phoneId=").append(phoneId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}