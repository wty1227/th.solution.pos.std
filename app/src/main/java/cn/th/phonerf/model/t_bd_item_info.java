package cn.th.phonerf.model;

/**
 * Created by FW on 2016-10-01.
 */

public class t_bd_item_info extends BaseEntity {

    private String item_no;
    private String item_subno;
    private String item_name;
    private double sale_price;
    private double price;
    private String item_size;
    private String unit_no;
    private String package_spec;
    private String produce_area;
    private String level;

    public String getItem_no() {
        return item_no;
    }

    public void setItem_no(String item_no) {
        this.item_no = item_no;
    }

    public String getItem_subno() {
        return item_subno;
    }

    public void setItem_subno(String item_subno) {
        this.item_subno = item_subno;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public double getSale_price() {
        return sale_price;
    }

    public void setSale_price(double sale_price) {
        this.sale_price = sale_price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getItem_size() {
        return item_size;
    }

    public void setItem_size(String item_size) {
        this.item_size = item_size;
    }

    public String getUnit_no() {
        return unit_no;
    }

    public void setUnit_no(String unit_no) {
        this.unit_no = unit_no;
    }

    public String getPackage_spec() {
        return package_spec;
    }

    public void setPackage_spec(String package_spec) {
        this.package_spec = package_spec;
    }

    public String getProduce_area() {
        return produce_area;
    }

    public void setProduce_area(String produce_area) {
        this.produce_area = produce_area;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
