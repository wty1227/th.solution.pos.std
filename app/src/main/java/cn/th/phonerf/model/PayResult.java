package cn.th.phonerf.model;

import cn.th.phonerf.constant.GSale;

public class PayResult {
    private String retCode;
    private String payOrderNo;
    private String retMsg;

    /**
     * 金额 1 = 1分钱
     */
    private String totalFee;
    /**
     * 服务区名称
     */
    private String deviceInfo;
    /**
     * flowNo 流水号
     */
    private String body;
    /**
     * 手机单号
     */
    private String orderNo;
    /**
     * 发起reqsn
     */
    private String outTradeNo;
    /**
     * 交易类型
     */
    private String tradeType;
    /**
     * StoreId
     */
    private String attach;


    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(Object retCode) {
        this.retCode = String.valueOf(retCode);
    }

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public void setPayOrderNo(Object payOrderNo) {
        this.payOrderNo = String.valueOf (payOrderNo);
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(Object retMsg) {
        this.retMsg = String.valueOf (retMsg);;
    }


    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String toLog(){
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("retcode = ").append(retCode);
        sb.append(", orderNo=").append(orderNo);
        sb.append(", retMsg=").append(retMsg);
        sb.append(", payOrderNo=").append(payOrderNo);
        sb.append("]");
        return sb.toString();
    }
}
