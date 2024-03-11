package cn.th.phonerf.model;

import java.io.Serializable;

/**
 * check_detail
 * @author 
 */
public class ReceiveDetail extends BaseEntity implements Serializable {
    private int merchantId;
    /**
     * 单据号货架号
     */
    private String sheetNo;

    private String shopId;
    /**
     * 商品编码
     */
    private String itemNo;
    /**
     * 机器号
     */
    private Integer phoneId;

    private float orderQty;

    /**
     * 盘点数量
     */
    private float realQty;
    /**
     * 服务器数量
     */
    private float serverQty;

    private String comFlag;
    /**
     * 操作日期
     */
    private String operDate;

    private String itemSubno;

    private String itemName;

    private String userId;

    private String flag = "receive";

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private static final long serialVersionUID = 1L;

    public Integer getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Integer phoneId) {
        this.phoneId = phoneId;
    }

    public float getRealQty() {
        return realQty;
    }

    public void setRealQty(float realQty) {
        this.realQty = realQty;
    }

    public String getOperDate() {
        return operDate;
    }

    public void setOperDate(String operDate) {
        this.operDate = operDate;
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", phoneId=").append(phoneId);
        sb.append(", realQty=").append(realQty);
        sb.append(", operDate=").append(operDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public String getItemSubno() {
        return itemSubno;
    }

    public void setItemSubno(String itemSubno) {
        this.itemSubno = itemSubno;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public float getServerQty() {
        return serverQty;
    }

    public void setServerQty(float serverQty) {
        this.serverQty = serverQty;
    }

    public String getComFlag() {
        return comFlag;
    }

    public void setComFlag(String comFlag) {
        this.comFlag = comFlag;
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

    public float getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(float orderQty) {
        this.orderQty = orderQty;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}