package cn.th.phonerf.activity.logrecord.bean;

/**
 * @Info 日志上送-发送实体
 * @Auth Bello
 * @Time 17-3-14 上午9:55
 * @Ver
 */

public class LogRecordActBean extends BaseAct {
    private int level;  //日志等级
    private String orderID; //充值单号
    private String log;  //日志内容

    public LogRecordActBean(int level, String orderID, String log) {
        this.level = level;
        this.orderID = orderID;
        this.log = log;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

}
