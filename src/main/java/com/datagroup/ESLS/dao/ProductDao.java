package com.datagroup.ESLS.dao;

import com.datagroup.ESLS.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDao extends JpaRepository<ProductEntity,Long> {
    List<ProductEntity> findByNameLikeAndProductIdLike(String name,String productId);
}
