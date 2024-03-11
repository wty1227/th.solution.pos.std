package cn.th.phonerf.utils.ip;

/**
 * @Info IP地址信息
 * @Auth Bello
 * @Time 16-6-27 上午10:10
 * @Ver
 */
public class IpBean2 {
    String ip;			//IP地址
    String pro;			//"广东省",
    String proCode;		//"440000",
    String city;		// "深圳市",
    String cityCode;	// "440300",
    String region;		//"龙岗区",
    String regionCode;	//"440307",
    String addr;		//"广东省深圳市龙岗区 电信",
    String regionNames;	//

    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getPro() {
        return pro;
    }
    public void setPro(String pro) {
        this.pro = pro;
    }
    public String getProCode() {
        return proCode;
    }
    public void setProCode(String proCode) {
        this.proCode = proCode;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getCityCode() {
        return cityCode;
    }
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
    }
    public String getRegionCode() {
        return regionCode;
    }
    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }
    public String getAddr() {
        return addr;
    }
    public void setAddr(String addr) {
        this.addr = addr;
    }
    public String getRegionNames() {
        return regionNames;
    }
    public void setRegionNames(String regionNames) {
        this.regionNames = regionNames;
    }
}
