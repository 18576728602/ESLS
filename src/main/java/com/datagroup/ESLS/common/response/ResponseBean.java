package com.datagroup.ESLS.common.response;

import lombok.Data;

@Data
public class ResponseBean {
    private int sum;
    private int successNumber;
    public ResponseBean(int sum, int successNumber) {
        this.sum = sum;
        this.successNumber = successNumber;
    }
}
