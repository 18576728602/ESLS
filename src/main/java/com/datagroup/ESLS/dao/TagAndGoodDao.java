package com.datagroup.ESLS.dao;

import com.datagroup.ESLS.entity.TagandGood;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagAndGoodDao extends JpaRepository<TagandGood,Long> {

    List<TagandGood> findByidAndShopNumber(long id, String shopNumber);
    List<TagandGood> findByShopNumber(String shopNumber);
    List<TagandGood> findByid(long id);
}
