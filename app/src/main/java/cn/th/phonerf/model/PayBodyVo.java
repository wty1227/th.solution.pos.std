package cn.th.phonerf.model;//package org.dromara.foodself.domain.bo;

import java.io.Serializable;

import lombok.Data;

@Data
public class PayBodyVo implements Serializable {

    /**
     * 商户订单号
     */
    private String orderNo;
    /**
     * 付款码
     */
    private String authCode;
    /**
     * 订单金额
     */
    private double amount;

    private String body;
    private String posId;
}
