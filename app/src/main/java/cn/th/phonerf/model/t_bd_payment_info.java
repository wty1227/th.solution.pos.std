package cn.th.phonerf.model;

public class t_bd_payment_info {

    private String payWay;
    private String payFlag = "0";
    private String payName;
    private Double rate = 1.0;
    private Double defaultAmt;
    private String accFlag;
    private String jfFlag;
    private String payMemo;


    private String memo = "";

    private String payCardname = "";
    private String payCardno = "";
    private Double remainAmt;
    private String vipName = "";

    public String getAccFlag() {
        return accFlag;
    }

    public void setAccFlag(String accFlag) {
        this.accFlag = accFlag;
    }

    public Double getDefaultAmt() {
        return defaultAmt;
    }

    public void setDefaultAmt(Double defaultAmt) {
        this.defaultAmt = defaultAmt;
    }

    public String getJfFlag() {
        return jfFlag;
    }

    public void setJfFlag(String jfFlag) {
        this.jfFlag = jfFlag;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getPayFlag() {
        return payFlag;
    }

    public void setPayFlag(String payFlag) {
        this.payFlag = payFlag;
    }

    public String getPayMemo() {
        return payMemo;
    }

    public void setPayMemo(String payMemo) {
        this.payMemo = payMemo;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getPayCardname() {
        return payCardname;
    }

    public void setPayCardname(String payCardname) {
        this.payCardname = payCardname;
    }

    public String getPayCardno() {
        return payCardno;
    }

    public void setPayCardno(String payCardno) {
        this.payCardno = payCardno;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getRemainAmt() {
        return remainAmt;
    }

    public void setRemainAmt(Double remainAmt) {
        this.remainAmt = remainAmt;
    }

    public String getVipName() {
        return vipName;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName;
    }

}
