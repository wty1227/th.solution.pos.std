package cn.th.phonerf.model;

public class CheckHistoryItem extends BaseEntity{
    private String check_no;
    private String sheet_no;
    private String oper_date;

    public String getCheck_no() {
        return check_no;
    }

    public void setCheck_no(String check_no) {
        this.check_no = check_no;
    }

    public String getSheet_no() {
        return sheet_no;
    }

    public void setSheet_no(String sheet_no) {
        this.sheet_no = sheet_no;
    }

    public String getOper_date() {
        return oper_date;
    }

    public void setOper_date(String oper_date) {
        this.oper_date = oper_date;
    }

    public float getCount() {
        return count;
    }

    public void setCount(float count) {
        this.count = count;
    }

    public float getQty() {
        return qty;
    }

    public void setQty(float qty) {
        this.qty = qty;
    }

    private float count;
    private float qty;
}
