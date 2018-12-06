package com.datagroup.ESLS.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
public class GoodVo {
    private long id;
    private String name;
    private String origin;
    private String provider;
    private String unit;
    private String barCode;
    private String qrCode;
    private String operator;
    private Timestamp importTime;
    private String promotionReason;
    private Integer status;
    private double price;
    private double promotePrice;
    private byte[] photo;
    private Integer waitUpdate;
    private String shelfNumber;
    private String spec;
    private String category;
    private String rfu01;
    private String rfu02;
    private String rfus01;
    private String rfus02;
    private List<Long> tagIdList = new ArrayList<>();
}
