package cn.th.phonerf.model;

public class ReceiveRange extends BaseEntity {
    private int merchantId;
    private String itemNo;
    private String itemName;
    private float orderQty;

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String item_no) {
        this.itemNo = item_no;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String item_name) {
        this.itemName = item_name;
    }

    public float getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(float order_qty) {
        this.orderQty = order_qty;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }
}
