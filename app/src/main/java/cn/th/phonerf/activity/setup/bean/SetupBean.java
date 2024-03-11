package cn.th.phonerf.activity.setup.bean;

public class SetupBean {
    private String merchantNo;

    public SetupBean(String merchantNo){
        this.merchantNo = merchantNo;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchant_no) {
        this.merchantNo = merchant_no;
    }
}
