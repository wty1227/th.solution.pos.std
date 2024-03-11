package cn.th.phonerf.model;

public class LastSheetInfo {
    private String sheetNo = "";
    private Double amt = 0.00;
    private Double chgAmt = 0.00;

    public String getSheetNo() {
        return sheetNo;
    }

    public void setSheetNo(String sheetNo) {
        this.sheetNo = sheetNo;
    }

    public Double getAmt() {
        return amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public Double getChgAmt() {
        return chgAmt;
    }

    public void setChgAmt(Double chgAmt) {
        this.chgAmt = chgAmt;
    }
}
