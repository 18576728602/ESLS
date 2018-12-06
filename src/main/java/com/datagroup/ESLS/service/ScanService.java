package com.datagroup.ESLS.service;


import com.datagroup.ESLS.entity.Scan;

import java.util.List;
import java.util.Optional;

public interface ScanService extends Service{
    List<Scan> findAll();
    List<Scan> findAll(Integer page, Integer count);
    Scan saveOne(Scan scan);
    Optional<Scan> findById(Long id);
    boolean deleteById(Long id);
}
