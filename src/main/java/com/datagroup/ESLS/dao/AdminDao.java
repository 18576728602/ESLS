package com.datagroup.ESLS.dao;

import com.datagroup.ESLS.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AdminDao extends JpaRepository<Admin,Long> {

    @Query(value = "select * FROM admin u WHERE u.username=?1",nativeQuery = true)
    Admin findByuserName(String userName);
    Admin findByUsernameAndPassword(String username, String password);
    @Query(value = "update admin set userName=?1 where id=?2 ", nativeQuery = true)
    @Modifying
    void updateOne(String name,int id);
}
