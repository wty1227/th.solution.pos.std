package cn.th.phonerf.activity.version.bean;

/**
 * Created by ubt on 16-9-1.
 */
public class VersionData {

    private String app_download;	//APP最新下载地址
    private String app_version;	//APP最新版本号
    private String app_hash;	//安装包hash
    private String app_type;	//强制更新参数
    private String app_info;	//更新内容描述
    private String currentTime;	//系统当前时间

    public String getApp_download() {
        return app_download;
    }

    public void setApp_download(String app_download) {
        this.app_download = app_download;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getApp_hash() {
        return app_hash;
    }

    public void setApp_hash(String app_hash) {
        this.app_hash = app_hash;
    }

    public String getApp_type() {
        return app_type;
    }

    public void setApp_type(String app_type) {
        this.app_type = app_type;
    }

    public String getApp_info() {
        return app_info;
    }

    public void setApp_info(String app_info) {
        this.app_info = app_info;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }
}
