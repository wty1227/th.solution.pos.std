package cn.th.phonerf.utils.ip;

/**
 * @Info IP地址信息
 * @Auth bell
 * @Time 16-6-27 上午10:06
 * @Ver
 */
public class IpBean {

    private IpInfo data;

    public IpInfo getData() {
        return data;
    }

    public void setData(IpInfo data) {
        this.data = data;

    }


    /**
     * 内部类
     */
    public class IpInfo {
        String region;
        String city;
        String country;
        String area;
        String isp;
        String ip;

        public String getCountry() {
            return country;
        }
        public void setCountry(String country) {
            this.country = country;
        }
        public String getArea() {
            return area;
        }
        public void setArea(String area) {
            this.area = area;
        }
        public String getRegion() {
            return region;
        }
        public void setRegion(String region) {
            this.region = region;
        }
        public String getCity() {
            return city;
        }
        public void setCity(String city) {
            this.city = city;
        }
        public String getIp() {
            return ip;
        }
        public void setIp(String ip) {
            this.ip = ip;
        }
        public String getIsp() {
            return isp;
        }
        public void setIsp(String isp) {
            this.isp = isp;
        }

    }
}
