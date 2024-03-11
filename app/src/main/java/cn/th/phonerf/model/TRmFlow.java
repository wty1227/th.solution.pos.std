package cn.th.phonerf.model;

import java.util.List;

public class TRmFlow {
    List<TRmSaleflow> saleList;

    public List<TRmSaleflow> getSaleList() {
        return saleList;
    }

    public void setSaleList(List<TRmSaleflow> saleList) {
        this.saleList = saleList;
    }

    public List<TRmPayflow> getPayList() {
        return payList;
    }

    public void setPayList(List<TRmPayflow> payList) {
        this.payList = payList;
    }

    List<TRmPayflow> payList;
}
