package cn.th.phonerf.model;

import lombok.Data;

@Data
public class t_rm_vip_info {
    private String card_id;
    private String vip_name;
    private String card_type;
    private String type_name;
    private double acc_num;
    private double remain_amt;
}
