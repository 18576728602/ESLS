package com.datagroup.ESLS.utils;

import com.datagroup.ESLS.common.response.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

@Slf4j
public class ValidatorUtil {

    public static String getError(BindingResult error) {
        StringBuilder sBuilder = new StringBuilder();
        List<ObjectError> list = error.getAllErrors();
        for (ObjectError errorItem : list) {
            log.info(errorItem.getCode() + "---"  + "---" + errorItem.getDefaultMessage());
            sBuilder.append(errorItem.getDefaultMessage());
            sBuilder.append("\n");
        }
        return sBuilder.toString();
    }
}
