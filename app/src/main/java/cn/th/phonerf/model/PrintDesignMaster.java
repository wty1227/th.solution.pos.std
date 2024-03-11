package cn.th.phonerf.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * print_design_master
 * @author 
 */
public class PrintDesignMaster implements Serializable {
    /**
     * id
     */
    private Integer printId;

    private String printName;

    /**
     * 商户id
     */
    private Integer merchantId;

    /**
     * 创建日期
     */
    private Date buildDate;

    /**
     * 操作日期
     */
    private Date operDate;


    List<PrintDesignDetail> detailList;

    public List<PrintDesignDetail> getDetailList(){ return detailList; }

    public void setDetailList(List<PrintDesignDetail> detailList){ this.detailList = detailList; }


    private static final long serialVersionUID = 1L;

    public Integer getPrintId() {
        return printId;
    }

    public void setPrintId(Integer printId) {
        this.printId = printId;
    }

    public String getPrintName() {
        return printName;
    }

    public void setPrintName(String printName) {
        this.printName = printName;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Date getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(Date buildDate) {
        this.buildDate = buildDate;
    }

    public Date getOperDate() {
        return operDate;
    }

    public void setOperDate(Date operDate) {
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
        PrintDesignMaster other = (PrintDesignMaster) that;
        return (this.getPrintId() == null ? other.getPrintId() == null : this.getPrintId().equals(other.getPrintId()))
            && (this.getPrintName() == null ? other.getPrintName() == null : this.getPrintName().equals(other.getPrintName()))
            && (this.getMerchantId() == null ? other.getMerchantId() == null : this.getMerchantId().equals(other.getMerchantId()))
            && (this.getBuildDate() == null ? other.getBuildDate() == null : this.getBuildDate().equals(other.getBuildDate()))
            && (this.getOperDate() == null ? other.getOperDate() == null : this.getOperDate().equals(other.getOperDate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPrintId() == null) ? 0 : getPrintId().hashCode());
        result = prime * result + ((getPrintName() == null) ? 0 : getPrintName().hashCode());
        result = prime * result + ((getMerchantId() == null) ? 0 : getMerchantId().hashCode());
        result = prime * result + ((getBuildDate() == null) ? 0 : getBuildDate().hashCode());
        result = prime * result + ((getOperDate() == null) ? 0 : getOperDate().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", printId=").append(printId);
        sb.append(", printName=").append(printName);
        sb.append(", merchantId=").append(merchantId);
        sb.append(", buildDate=").append(buildDate);
        sb.append(", operDate=").append(operDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}