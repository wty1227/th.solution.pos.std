package cn.th.phonerf.activity.pos.bean;

public class PosBean {

    private String item_clsno;
    private String item_clsname;
    private String item_flag;
    private String cls_parent;
    private String display_flag;
    private String return_rate;
    private String comp_rate;
    private String trans_flag;

    public PosBean(){}

    public PosBean(String item_clsno, String item_clsname, String item_flag, String cls_parent, String display_flag, String return_rate, String comp_rate, String trans_flag) {
        this.item_clsno = item_clsno;
        this.item_clsname = item_clsname;
        this.item_flag = item_flag;
        this.cls_parent = cls_parent;
        this.display_flag = display_flag;
        this.return_rate = return_rate;
        this.comp_rate = comp_rate;
        this.trans_flag = trans_flag;
    }

    public String getItem_clsno() {
        return item_clsno;
    }

    public void setItem_clsno(String item_clsno) {
        this.item_clsno = item_clsno;
    }

    public String getItem_clsname() {
        return item_clsname;
    }

    public void setItem_clsname(String item_clsname) {
        this.item_clsname = item_clsname;
    }

    public String getItem_flag() {
        return item_flag;
    }

    public void setItem_flag(String item_flag) {
        this.item_flag = item_flag;
    }

    public String getCls_parent() {
        return cls_parent;
    }

    public void setCls_parent(String cls_parent) {
        this.cls_parent = cls_parent;
    }

    public String getDisplay_flag() {
        return display_flag;
    }

    public void setDisplay_flag(String display_flag) {
        this.display_flag = display_flag;
    }

    public String getReturn_rate() {
        return return_rate;
    }

    public void setReturn_rate(String return_rate) {
        this.return_rate = return_rate;
    }

    public String getComp_rate() {
        return comp_rate;
    }

    public void setComp_rate(String comp_rate) {
        this.comp_rate = comp_rate;
    }

    public String getTrans_flag() {
        return trans_flag;
    }

    public void setTrans_flag(String trans_flag) {
        this.trans_flag = trans_flag;
    }



}
