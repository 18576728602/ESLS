package com.datagroup.ESLS.dao;

import com.datagroup.ESLS.entity.Good;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodDao extends JpaRepository<Good,Long> {
    Good findByBarCode(String BarCode);
}
