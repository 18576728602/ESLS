package com.datagroup.ESLS.dao;

import com.datagroup.ESLS.entity.Logs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogDao extends JpaRepository<Logs,Long> {
}
