package com.yxh.house.pojo;

import lombok.Data;

import java.util.Date;


@Data
public class Order {
    private Integer id;
    private Integer user_id;
    private Integer house_id;
    private Date start_time;
    private Date end_time;
    /**
     *    private Integer status
     *     0: 未审核
     *     1: 合同审核--已认证
     *     2: 签订认证
     */
    private Integer status;
    private String remark;
    private String snapshot;
    private Date create_time;
    private Integer close;
    private Integer define;


}
