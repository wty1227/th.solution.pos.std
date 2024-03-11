package cn.th.phonerf.activity.logrecord;

/**
 * @Info
 * @Auth Bello
 * @Time 17-3-14 上午9:45
 * @Ver
 */

public interface ILogRecord  {

    /**
     * 上送日志
     *
     * @param level
     * @param orderID
     * @param logStr
     */
    void sendLogRecord(int level, String orderID, String logStr);
}
