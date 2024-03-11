package cn.th.phonerf.activity.logrecord.bean;

import cn.th.phonerf.utils.JsonUtils;

/**
 * @Info
 * @Auth Bello
 * @Time 17-7-5 下午3:58
 * @Ver
 */

public class BaseAct {

    /**
     * 转json字符
     * @return
     */
    public String toJsonStr(){
        return JsonUtils.objectToJsonStr(this);
    }
}
