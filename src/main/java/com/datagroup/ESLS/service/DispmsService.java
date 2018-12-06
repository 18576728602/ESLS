package com.datagroup.ESLS.service;

import com.datagroup.ESLS.entity.Dispms;

import java.util.List;
import java.util.Optional;

public interface DispmsService extends Service{
    List<Dispms> findAll();
    List<Dispms> findAll(Integer page, Integer count);
    Dispms saveOne(Dispms dispms);
    Optional<Dispms> findById(Long id);
    boolean deleteById(Long id);
}
