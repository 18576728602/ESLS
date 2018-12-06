package com.datagroup.ESLS.dao;

import com.datagroup.ESLS.common.request.RequestBean;

import java.util.List;

public interface BaseDao {
     // 执行具体的SQL语句
     List findBySql(String sql);
     // 执行具体的SQL语句
     List findBySql(String sql,Class clazz);
     // 通过传入的属性和值查询具体的结果
     List findByArrtribute(String table,String query,String queryString,Class clazz);
     // 根据传入的属性和值模糊查询不分页
     List findAllBySql(String table,String query,String queryString,Class clazz);
     // 通过传入的参数模糊查询
     List findAllBySql(String table,String query,String queryString,int page,int count,Class clazz);
     // 根据传入的参数修改指定数据
     Integer updateByArrtribute(String table,RequestBean source,RequestBean target);
}
