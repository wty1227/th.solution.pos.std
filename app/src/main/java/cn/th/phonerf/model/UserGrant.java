package cn.th.phonerf.model;

import java.io.Serializable;

/**
 * user_grant
 * @author 
 */
public class UserGrant extends UserGrantKey implements Serializable {
    /**
     * 进入
     */
    private String grantName;

    private String grant0;

    /**
     * 新建
     */
    private String grant1;

    /**
     * 修改/保存
     */
    private String grant2;

    /**
     * 删除
     */
    private String grant3;

    /**
     * 审核
     */
    private String grant4;

    /**
     * 打印
     */
    private String grant5;

    /**
     * 新建批次
     */
    private String grant6;

    /**
     * 盘点结束
     */
    private String grant7;

    /**
     * 上传
     */
    private String grant8;

    /**
     * 权限
     */
    private String grant9;

    /**
     * 权限
     */
    private String grant10;

    /**
     * 权限
     */
    private String grant11;

    /**
     * 权限
     */
    private String grant12;

    /**
     * 备注
     */
    private String remark;

    private static final long serialVersionUID = 1L;

    public String getGrant0() {
        return grant0;
    }

    public void setGrant0(String grant0) {
        this.grant0 = grant0;
    }

    public String getGrant1() {
        return grant1;
    }

    public void setGrant1(String grant1) {
        this.grant1 = grant1;
    }

    public String getGrant2() {
        return grant2;
    }

    public void setGrant2(String grant2) {
        this.grant2 = grant2;
    }

    public String getGrant3() {
        return grant3;
    }

    public void setGrant3(String grant3) {
        this.grant3 = grant3;
    }

    public String getGrant4() {
        return grant4;
    }

    public void setGrant4(String grant4) {
        this.grant4 = grant4;
    }

    public String getGrant5() {
        return grant5;
    }

    public void setGrant5(String grant5) {
        this.grant5 = grant5;
    }

    public String getGrant6() {
        return grant6;
    }

    public void setGrant6(String grant6) {
        this.grant6 = grant6;
    }

    public String getGrant7() {
        return grant7;
    }

    public void setGrant7(String grant7) {
        this.grant7 = grant7;
    }

    public String getGrant8() {
        return grant8;
    }

    public void setGrant8(String grant8) {
        this.grant8 = grant8;
    }

    public String getGrant9() {
        return grant9;
    }

    public void setGrant9(String grant9) {
        this.grant9 = grant9;
    }

    public String getGrant10() {
        return grant10;
    }

    public void setGrant10(String grant10) {
        this.grant10 = grant10;
    }

    public String getGrant11() {
        return grant11;
    }

    public void setGrant11(String grant11) {
        this.grant11 = grant11;
    }

    public String getGrant12() {
        return grant12;
    }

    public void setGrant12(String grant12) {
        this.grant12 = grant12;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        UserGrant other = (UserGrant) that;
        return (this.getuId() == null ? other.getuId() == null : this.getuId().equals(other.getuId()))
            && (this.getGrantId() == null ? other.getGrantId() == null : this.getGrantId().equals(other.getGrantId()))
            && (this.getGrant0() == null ? other.getGrant0() == null : this.getGrant0().equals(other.getGrant0()))
            && (this.getGrant1() == null ? other.getGrant1() == null : this.getGrant1().equals(other.getGrant1()))
            && (this.getGrant2() == null ? other.getGrant2() == null : this.getGrant2().equals(other.getGrant2()))
            && (this.getGrant3() == null ? other.getGrant3() == null : this.getGrant3().equals(other.getGrant3()))
            && (this.getGrant4() == null ? other.getGrant4() == null : this.getGrant4().equals(other.getGrant4()))
            && (this.getGrant5() == null ? other.getGrant5() == null : this.getGrant5().equals(other.getGrant5()))
            && (this.getGrant6() == null ? other.getGrant6() == null : this.getGrant6().equals(other.getGrant6()))
            && (this.getGrant7() == null ? other.getGrant7() == null : this.getGrant7().equals(other.getGrant7()))
            && (this.getGrant8() == null ? other.getGrant8() == null : this.getGrant8().equals(other.getGrant8()))
            && (this.getGrant9() == null ? other.getGrant9() == null : this.getGrant9().equals(other.getGrant9()))
            && (this.getGrant10() == null ? other.getGrant10() == null : this.getGrant10().equals(other.getGrant10()))
            && (this.getGrant11() == null ? other.getGrant11() == null : this.getGrant11().equals(other.getGrant11()))
            && (this.getGrant12() == null ? other.getGrant12() == null : this.getGrant12().equals(other.getGrant12()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getuId() == null) ? 0 : getuId().hashCode());
        result = prime * result + ((getGrantId() == null) ? 0 : getGrantId().hashCode());
        result = prime * result + ((getGrant0() == null) ? 0 : getGrant0().hashCode());
        result = prime * result + ((getGrant1() == null) ? 0 : getGrant1().hashCode());
        result = prime * result + ((getGrant2() == null) ? 0 : getGrant2().hashCode());
        result = prime * result + ((getGrant3() == null) ? 0 : getGrant3().hashCode());
        result = prime * result + ((getGrant4() == null) ? 0 : getGrant4().hashCode());
        result = prime * result + ((getGrant5() == null) ? 0 : getGrant5().hashCode());
        result = prime * result + ((getGrant6() == null) ? 0 : getGrant6().hashCode());
        result = prime * result + ((getGrant7() == null) ? 0 : getGrant7().hashCode());
        result = prime * result + ((getGrant8() == null) ? 0 : getGrant8().hashCode());
        result = prime * result + ((getGrant9() == null) ? 0 : getGrant9().hashCode());
        result = prime * result + ((getGrant10() == null) ? 0 : getGrant10().hashCode());
        result = prime * result + ((getGrant11() == null) ? 0 : getGrant11().hashCode());
        result = prime * result + ((getGrant12() == null) ? 0 : getGrant12().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", grant0=").append(grant0);
        sb.append(", grant1=").append(grant1);
        sb.append(", grant2=").append(grant2);
        sb.append(", grant3=").append(grant3);
        sb.append(", grant4=").append(grant4);
        sb.append(", grant5=").append(grant5);
        sb.append(", grant6=").append(grant6);
        sb.append(", grant7=").append(grant7);
        sb.append(", grant8=").append(grant8);
        sb.append(", grant9=").append(grant9);
        sb.append(", grant10=").append(grant10);
        sb.append(", grant11=").append(grant11);
        sb.append(", grant12=").append(grant12);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public String getGrantName() {
        return grantName;
    }

    public void setGrantName(String grantName) {
        this.grantName = grantName;
    }
}