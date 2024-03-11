package cn.th.phonerf.model;

/**
 * Created by fengwen on 17-4-20.
 */

public class t_im_check_detail extends BaseEntity{
    private String sheet_no;
    private String branch_no;
    private String item_no;
    private String item_subno;
    private String item_name;
    private float real_qty;
    private float server_qty;
    private boolean com_flag = false;

    public String getSheet_no() {
        return sheet_no;
    }

    public void setSheet_no(String sheet_no) {
        this.sheet_no = sheet_no;
    }

    public String getBranch_no() {
        return branch_no;
    }

    public void setBranch_no(String branch_no) {
        this.branch_no = branch_no;
    }

    public String getItem_no() {
        return item_no;
    }

    public void setItem_no(String item_no) {
        this.item_no = item_no;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_subno() {
        return item_subno;
    }

    public void setItem_subno(String item_subno) {
        this.item_subno = item_subno;
    }

    public float getReal_qty() {
        return real_qty;
    }

    public void setReal_qty(float real_qty) {
        this.real_qty = real_qty;
    }

    public boolean isCom_flag() {
        return com_flag;
    }

    public void setCom_flag(boolean com_flag) {
        this.com_flag = com_flag;
    }

    public float getServer_qty() {
        return server_qty;
    }

    public void setServer_qty(float server_qty) {
        this.server_qty = server_qty;
    }
}

