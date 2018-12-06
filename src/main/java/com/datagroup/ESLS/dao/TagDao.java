package com.datagroup.ESLS.dao;

import com.datagroup.ESLS.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagDao extends JpaRepository<Tag,Long> {
    Tag findByBarCode(String barCodd);
}
