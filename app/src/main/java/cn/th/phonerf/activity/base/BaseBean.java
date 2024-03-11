package cn.th.phonerf.activity.base;

import java.io.Serializable;

/**
 * 接口返回的实体，只包含三个参数，没有子类
 * Created by bell on 16/8/6.
 */
public class BaseBean implements Serializable {

    private String error;

    private String msg;

    private Object data;

    public BaseBean() {
    }

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
