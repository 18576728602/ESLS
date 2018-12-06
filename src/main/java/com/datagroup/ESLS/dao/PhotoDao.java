package com.datagroup.ESLS.dao;

import com.datagroup.ESLS.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoDao extends JpaRepository<Photo,Integer> {
    Photo findByName(String name);
}
