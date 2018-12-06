package com.datagroup.ESLS.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RouterVo {
    private long id;
    private Integer mac;
    private String ip;
    private Integer port;
    private String channelId;
    private Byte state;
    private String serialNumber;
    private String productor;
    private Integer softVersion;
    private Integer sendBaudrate;
    private Integer receiveBaudrate;
    private Double longPeriod;
    private Double shortPeriod;
    private Integer frequency;
    private Integer power;
    private Integer updateState;
    private Integer hardVersion;
    private String productBatch;
    private Byte prepareUpdate;
}
