package com.datagroup.ESLS.service;

import com.datagroup.ESLS.common.request.RequestBean;
import com.datagroup.ESLS.common.response.ResponseBean;
import com.datagroup.ESLS.entity.Good;

import java.util.List;

public interface GoodService extends Service{
    List<Good> findAll();
    List<Good> findAll(Integer page, Integer count);
    Good saveOne(Good scan);
    Good findById(Long id);
    boolean deleteById(Long id);
    // 对商品绑定的所有标签内容进行更新
    ResponseBean updateGoods(RequestBean requestBean);

}
