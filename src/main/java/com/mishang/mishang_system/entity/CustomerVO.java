package com.mishang.mishang_system.entity;

import lombok.Data;

@Data
public class CustomerVO {
    private Integer id;
    private String customerName;
    private String phone;
    private  String tags;
    private Integer remainingDays;
}
