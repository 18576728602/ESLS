package com.datagroup.ESLS.service;

import com.datagroup.ESLS.entity.Shop;
import com.datagroup.ESLS.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface ShopService extends Service{
    List<Shop> findAll();
    List<Shop> findAll(Integer page, Integer count);
    Shop saveOne(Shop shop);
    Optional<Shop> findById(Long id);
    boolean deleteById(Long id);
}
