package com.datagroup.ESLS.service;

import com.datagroup.ESLS.entity.Dispmmanager;

import java.util.List;
import java.util.Optional;

public interface DispmmanagerService extends Service{
    List<Dispmmanager> findAll();
    List<Dispmmanager> findAll(Integer page, Integer count);
    Dispmmanager saveOne(Dispmmanager dispmmanager);
    Optional<Dispmmanager> findById(Long id);
    boolean deleteById(Long id);
}
