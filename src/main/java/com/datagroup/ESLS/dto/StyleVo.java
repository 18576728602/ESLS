package com.datagroup.ESLS.dto;

import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class StyleVo {
    private long id;
    private String styleNumber;
    private String styleType;
    private String name;
    private Integer width;
    private Integer height;
    private Integer refreshState;
    private Integer refreshTime;
    private Timestamp refreshBegin;
    private List<Long> tagIdList = new ArrayList<>();
}
