package com.datagroup.ESLS.service;

import com.datagroup.ESLS.common.request.RequestBean;
import com.datagroup.ESLS.common.response.ResponseBean;
import com.datagroup.ESLS.entity.Style;

import java.util.List;
import java.util.Optional;

public interface StyleService extends Service{
    List<Style> findAll();
    List<Style> findAll(Integer page,Integer count);
    Style saveOne(Style style);
    Optional<Style> findById(Long id);
    boolean deleteById(Long id);
    // 刷新选用改样式的标签 或 定期刷新
    ResponseBean flushTags(RequestBean requestBean, Integer mode);
}
