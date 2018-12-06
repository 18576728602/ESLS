package com.datagroup.ESLS.common.request;

import lombok.Data;

@Data
public class RequestItem {
    private String query;
    private String queryString;
    private String beginTime;
    private String cycleTime;
    public RequestItem(){}
    public RequestItem(String query,String queryString){
        this.query = query;
        this.queryString = queryString;
    }
}
