package cn.th.phonerf.activity.report.bean;

import cn.th.phonerf.model.BaseEntity;

public class ReportItem1 extends BaseEntity {
    private String itemNo;
    private String itemName;
    private String unitNo;
    private String qty1;
    private String qty2;

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUnitNo() {
        return unitNo;
    }

    public void setUnitNo(String unitNo) {
        this.unitNo = unitNo;
    }

    public String getQty1() {
        return qty1;
    }

    public void setQty1(String qty1) {
        this.qty1 = qty1;
    }

    public String getQty2() {
        return qty2;
    }

    public void setQty2(String qty2) {
        this.qty2 = qty2;
    }
}
