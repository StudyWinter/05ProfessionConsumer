package com.example.demo.model;

import lombok.Data;

@Data
public class Profession {
    private String major;      //专业类别
    private Integer pid;       //计数 primary key
    private String proname;    //专业名称
    private String code;       //专业代码
}
