package cn.th.phonerf.activity.version.bean;

/**
 * Created by ubt on 16-9-1.
 */
public class VersionBean {
    private String error;
    private String msg;
    private VersionData data;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public VersionData getData() {
        return data;
    }

    public void setData(VersionData data) {
        this.data = data;
    }
}
