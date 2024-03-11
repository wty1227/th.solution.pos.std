package cn.th.phonerf.model;

import java.io.Serializable;

/**
 * check_detail
 * @author 
 */
public class CheckDetailKey implements Serializable {
    /**
     * 批次
     */
    private String checkNo;

    /**
     * 单据号货架号
     */
    private String sheetNo;

    /**
     * 商品编码
     */
    private String itemNo;

    private static final long serialVersionUID = 1L;

    public String getCheckNo() {
        return checkNo;
    }

    public void setCheckNo(String checkNo) {
        this.checkNo = checkNo;
    }

    public String getSheetNo() {
        return sheetNo;
    }

    public void setSheetNo(String sheetNo) {
        this.sheetNo = sheetNo;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
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
        CheckDetailKey other = (CheckDetailKey) that;
        return (this.getCheckNo() == null ? other.getCheckNo() == null : this.getCheckNo().equals(other.getCheckNo()))
            && (this.getSheetNo() == null ? other.getSheetNo() == null : this.getSheetNo().equals(other.getSheetNo()))
            && (this.getItemNo() == null ? other.getItemNo() == null : this.getItemNo().equals(other.getItemNo()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCheckNo() == null) ? 0 : getCheckNo().hashCode());
        result = prime * result + ((getSheetNo() == null) ? 0 : getSheetNo().hashCode());
        result = prime * result + ((getItemNo() == null) ? 0 : getItemNo().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", checkNo=").append(checkNo);
        sb.append(", sheetNo=").append(sheetNo);
        sb.append(", itemNo=").append(itemNo);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}