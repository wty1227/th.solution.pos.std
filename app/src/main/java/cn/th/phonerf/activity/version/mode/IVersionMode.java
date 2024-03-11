package cn.th.phonerf.activity.version.mode;


import cn.th.phonerf.activity.version.bean.VersionData;

/**
 * @Info
 * @Auth Bello
 * @Time 16-7-5 上午10:32
 * @Ver
 */
public interface IVersionMode {

    /**
     * 比较新旧版本号是否相同
     * @param data
     */
    boolean compareCode(VersionData data);
}
