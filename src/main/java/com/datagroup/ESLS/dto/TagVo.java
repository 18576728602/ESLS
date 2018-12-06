package com.datagroup.ESLS.dto;

import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@ToString
public class TagVo {
    private long id;
    private String power;
    private String tagRssi;
    private String apRssi;
    private Integer state;
    private String type;
    private Integer hardwareVersion;
    private Integer softwareVersion;
    private Integer updateStatus;
    private Integer forbidState;
    private Integer execTime;
    private Timestamp comleteTime;
    private String barCode;
    private long goodId;
    private long styleId;
    private long routerId;
}
