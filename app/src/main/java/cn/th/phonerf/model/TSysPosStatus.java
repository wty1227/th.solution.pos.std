package cn.th.phonerf.model;

import java.io.Serializable;
import java.util.Date;

/**
 * t_sys_pos_status
 * @author 
 */
public class TSysPosStatus implements Serializable {
    private String appId;

    private String appKey;

    private String posid;

    private String posdesc;

    private String hostip;

    private String hostname;

    private String operdate;

    private Double amount;

    private Double orderqty;

    private Date lasttime;

    private String lastcashier;

    private String status;

    private String other;

    private String comFlag;

    private String rePosid;

    private String flag;

    private String supcustNo;

    private String guid;

    private String deviceid;

    private String devicesecret;

    private String operpass;

    private String version;

    private String mac;

    private String nvrIp;

    private Integer nvrPort;

    private String branch_name;

    private static final long serialVersionUID = 1L;


    public String getPosid() {
        return posid;
    }

    public void setPosid(String posid) {
        this.posid = posid;
    }

    public String getPosdesc() {
        return posdesc;
    }

    public void setPosdesc(String posdesc) {
        this.posdesc = posdesc;
    }

    public String getHostip() {
        return hostip;
    }

    public void setHostip(String hostip) {
        this.hostip = hostip;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getOperdate() {
        return operdate;
    }

    public void setOperdate(String operdate) {
        this.operdate = operdate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getOrderqty() {
        return orderqty;
    }

    public void setOrderqty(Double orderqty) {
        this.orderqty = orderqty;
    }

    public Date getLasttime() {
        return lasttime;
    }

    public void setLasttime(Date lasttime) {
        this.lasttime = lasttime;
    }

    public String getLastcashier() {
        return lastcashier;
    }

    public void setLastcashier(String lastcashier) {
        this.lastcashier = lastcashier;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getComFlag() {
        return comFlag;
    }

    public void setComFlag(String comFlag) {
        this.comFlag = comFlag;
    }

    public String getRePosid() {
        return rePosid;
    }

    public void setRePosid(String rePosid) {
        this.rePosid = rePosid;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getSupcustNo() {
        return supcustNo;
    }

    public void setSupcustNo(String supcustNo) {
        this.supcustNo = supcustNo;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getDevicesecret() {
        return devicesecret;
    }

    public void setDevicesecret(String devicesecret) {
        this.devicesecret = devicesecret;
    }

    public String getOperpass() {
        return operpass;
    }

    public void setOperpass(String operpass) {
        this.operpass = operpass;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getNvrIp() {
        return nvrIp;
    }

    public void setNvrIp(String nvrIp) {
        this.nvrIp = nvrIp;
    }

    public Integer getNvrPort() {
        return nvrPort;
    }

    public void setNvrPort(Integer nvrPort) {
        this.nvrPort = nvrPort;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TSysPosStatus other = (TSysPosStatus) that;
        return (this.getPosid() == null ? other.getPosid() == null : this.getPosid().equals(other.getPosid()))
            && (this.getPosdesc() == null ? other.getPosdesc() == null : this.getPosdesc().equals(other.getPosdesc()))
            && (this.getHostip() == null ? other.getHostip() == null : this.getHostip().equals(other.getHostip()))
            && (this.getHostname() == null ? other.getHostname() == null : this.getHostname().equals(other.getHostname()))
            && (this.getOperdate() == null ? other.getOperdate() == null : this.getOperdate().equals(other.getOperdate()))
            && (this.getAmount() == null ? other.getAmount() == null : this.getAmount().equals(other.getAmount()))
            && (this.getOrderqty() == null ? other.getOrderqty() == null : this.getOrderqty().equals(other.getOrderqty()))
            && (this.getLasttime() == null ? other.getLasttime() == null : this.getLasttime().equals(other.getLasttime()))
            && (this.getLastcashier() == null ? other.getLastcashier() == null : this.getLastcashier().equals(other.getLastcashier()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getOther() == null ? other.getOther() == null : this.getOther().equals(other.getOther()))
            && (this.getComFlag() == null ? other.getComFlag() == null : this.getComFlag().equals(other.getComFlag()))
            && (this.getRePosid() == null ? other.getRePosid() == null : this.getRePosid().equals(other.getRePosid()))
            && (this.getFlag() == null ? other.getFlag() == null : this.getFlag().equals(other.getFlag()))
            && (this.getSupcustNo() == null ? other.getSupcustNo() == null : this.getSupcustNo().equals(other.getSupcustNo()))
            && (this.getGuid() == null ? other.getGuid() == null : this.getGuid().equals(other.getGuid()))
            && (this.getDeviceid() == null ? other.getDeviceid() == null : this.getDeviceid().equals(other.getDeviceid()))
            && (this.getDevicesecret() == null ? other.getDevicesecret() == null : this.getDevicesecret().equals(other.getDevicesecret()))
            && (this.getOperpass() == null ? other.getOperpass() == null : this.getOperpass().equals(other.getOperpass()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
            && (this.getMac() == null ? other.getMac() == null : this.getMac().equals(other.getMac()))
            && (this.getNvrIp() == null ? other.getNvrIp() == null : this.getNvrIp().equals(other.getNvrIp()))
            && (this.getNvrPort() == null ? other.getNvrPort() == null : this.getNvrPort().equals(other.getNvrPort()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPosid() == null) ? 0 : getPosid().hashCode());
        result = prime * result + ((getPosdesc() == null) ? 0 : getPosdesc().hashCode());
        result = prime * result + ((getHostip() == null) ? 0 : getHostip().hashCode());
        result = prime * result + ((getHostname() == null) ? 0 : getHostname().hashCode());
        result = prime * result + ((getOperdate() == null) ? 0 : getOperdate().hashCode());
        result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
        result = prime * result + ((getOrderqty() == null) ? 0 : getOrderqty().hashCode());
        result = prime * result + ((getLasttime() == null) ? 0 : getLasttime().hashCode());
        result = prime * result + ((getLastcashier() == null) ? 0 : getLastcashier().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getOther() == null) ? 0 : getOther().hashCode());
        result = prime * result + ((getComFlag() == null) ? 0 : getComFlag().hashCode());
        result = prime * result + ((getRePosid() == null) ? 0 : getRePosid().hashCode());
        result = prime * result + ((getFlag() == null) ? 0 : getFlag().hashCode());
        result = prime * result + ((getSupcustNo() == null) ? 0 : getSupcustNo().hashCode());
        result = prime * result + ((getGuid() == null) ? 0 : getGuid().hashCode());
        result = prime * result + ((getDeviceid() == null) ? 0 : getDeviceid().hashCode());
        result = prime * result + ((getDevicesecret() == null) ? 0 : getDevicesecret().hashCode());
        result = prime * result + ((getOperpass() == null) ? 0 : getOperpass().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getMac() == null) ? 0 : getMac().hashCode());
        result = prime * result + ((getNvrIp() == null) ? 0 : getNvrIp().hashCode());
        result = prime * result + ((getNvrPort() == null) ? 0 : getNvrPort().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", posid=").append(posid);
        sb.append(", posdesc=").append(posdesc);
        sb.append(", hostip=").append(hostip);
        sb.append(", hostname=").append(hostname);
        sb.append(", operdate=").append(operdate);
        sb.append(", amount=").append(amount);
        sb.append(", orderqty=").append(orderqty);
        sb.append(", lasttime=").append(lasttime);
        sb.append(", lastcashier=").append(lastcashier);
        sb.append(", status=").append(status);
        sb.append(", other=").append(other);
        sb.append(", comFlag=").append(comFlag);
        sb.append(", rePosid=").append(rePosid);
        sb.append(", flag=").append(flag);
        sb.append(", supcustNo=").append(supcustNo);
        sb.append(", guid=").append(guid);
        sb.append(", deviceid=").append(deviceid);
        sb.append(", devicesecret=").append(devicesecret);
        sb.append(", operpass=").append(operpass);
        sb.append(", version=").append(version);
        sb.append(", mac=").append(mac);
        sb.append(", nvrIp=").append(nvrIp);
        sb.append(", nvrPort=").append(nvrPort);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }
}