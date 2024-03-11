package cn.th.phonerf.model;

public class AccountCashierInfo extends BaseEntity{
    private Integer myCount = 0; //笔数
    private String minDate;  //首笔
    private Double amount = 0d;

    public Integer getMyCount() {
        return myCount;
    }

    public void setMyCount(Integer myCount) {
        this.myCount = myCount;
    }

    public String getMinDate() {
        return minDate;
    }

    public void setMinDate(String minDate) {
        this.minDate = minDate;
    }

    public String getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(String maxDate) {
        this.maxDate = maxDate;
    }

    private String maxDate;  //末笔

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
