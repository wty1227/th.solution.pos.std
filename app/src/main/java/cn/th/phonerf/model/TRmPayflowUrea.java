package cn.th.phonerf.model;

import java.io.Serializable;

import cn.th.phonerf.constant.GSale;

public class TRmPayflowUrea implements Serializable {
    private static final long serialVersionUID = -77305074171161187L;
    /**
     * id
     */
    private Integer flowId;

    private Integer id;

    private String pushId;

    private String userId;

    private String userName;

    private String date;

    private String time;

    private Double sale;

    private Double liter;

    private Double price;

    private String mode;

    private String serNum;

    private String device;

    private String deviceName;

    private String devNum;

    private String latitude;

    private String longitude;

    private String version;

    private String appId;

    private String sign;

    private String remNum;

    private String cardNum;

    private String cardId;

    private Integer flag;

    private String reqsn;

    private String posId;

    private String operId;

    private String branchNo;

    private String payWay;

    private Double subAmt;

    private String payOrderNo;

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardAmount() {
        return cardAmount;
    }

    public void setCardAmount(String cardAmount) {
        this.cardAmount = cardAmount;
    }

    public String getCardBalance() {
        return cardBalance;
    }

    public void setCardBalance(String cardBalance) {
        this.cardBalance = cardBalance;
    }

    public String getCardTel() {
        return cardTel;
    }

    public void setCardTel(String cardTel) {
        this.cardTel = cardTel;
    }

    public String getCardCompany() {
        return cardCompany;
    }

    public void setCardCompany(String cardCompany) {
        this.cardCompany = cardCompany;
    }

    public String getCardLicensePlate() {
        return cardLicensePlate;
    }

    public void setCardLicensePlate(String cardLicensePlate) {
        this.cardLicensePlate = cardLicensePlate;
    }

    private String cardType;

    private String cardName;

    private String cardAmount;

    private String cardBalance;

    private String cardTel;

    private String cardCompany;

    private String cardLicensePlate;



    public String getReqsn() {
        return reqsn;
    }

    public void setReqsn(String reqsn) {
        this.reqsn = reqsn;
    }

    public String getPosId() {
        return posId;
    }

    public void setPosId(String posId) {
        this.posId = posId;
    }

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId;
    }

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getSale() {
        return sale;
    }

    public void setSale(Double sale) {
        this.sale = sale;
    }

    public Double getLiter() {
        return liter;
    }

    public void setLiter(Double liter) {
        this.liter = liter;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getSerNum() {
        return serNum;
    }

    public void setSerNum(String serNum) {
        this.serNum = serNum;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDevNum() {
        return devNum;
    }

    public void setDevNum(String devNum) {
        this.devNum = devNum;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getRemNum() {
        return remNum;
    }

    public void setRemNum(String remNum) {
        this.remNum = remNum;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getFlowId() {
        return flowId;
    }

    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
    }

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo;
    }

    public Double getSubAmt() {
        return subAmt;
    }

    public void setSubAmt(Double subAmt) {
        this.subAmt = subAmt;
    }

    public String toLog(){
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("id = ").append(id);
        sb.append(", sale=").append(sale);
        sb.append(", discount=").append(sale* GSale.Discount);
        sb.append(", sub_amt=").append(subAmt);
        sb.append(", reqsn=").append(reqsn);
        sb.append(", flag=").append(flag);
        sb.append("]");
        return sb.toString();
    }
}