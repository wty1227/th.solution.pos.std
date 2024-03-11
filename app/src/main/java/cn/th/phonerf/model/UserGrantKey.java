package cn.th.phonerf.model;

import java.io.Serializable;

/**
 * user_grant
 * @author 
 */
public class UserGrantKey implements Serializable {
    /**
     * 登录账户id
     */
    private Integer uId;
    //private String userId;

    /**
     * 权限项目ID
     */
    private Integer grantId;

    private static final long serialVersionUID = 1L;

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public Integer getGrantId() {
        return grantId;
    }

    public void setGrantId(Integer grantId) {
        this.grantId = grantId;
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
        UserGrantKey other = (UserGrantKey) that;
        return (this.getuId() == null ? other.getuId() == null : this.getuId().equals(other.getuId()))
            && (this.getGrantId() == null ? other.getGrantId() == null : this.getGrantId().equals(other.getGrantId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getuId() == null) ? 0 : getuId().hashCode());
        result = prime * result + ((getGrantId() == null) ? 0 : getGrantId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", uId=").append(uId);
        sb.append(", grantId=").append(grantId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
/*
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }*/
}