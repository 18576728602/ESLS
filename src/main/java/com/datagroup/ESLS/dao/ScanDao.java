package com.datagroup.ESLS.dao;

import com.datagroup.ESLS.entity.Scan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScanDao extends JpaRepository<Scan,Long> {
}
