package com.datagroup.ESLS.service;

import com.datagroup.ESLS.common.request.RequestBean;
import com.datagroup.ESLS.entity.Router;

import java.util.List;
import java.util.Optional;

public interface RouterService extends Service{
    List<Router> findAll();
    List<Router> findAll(Integer page, Integer count);
    Router saveOne(Router router);
    Optional<Router> findById(Long id);
    boolean deleteById(Long id);
}
