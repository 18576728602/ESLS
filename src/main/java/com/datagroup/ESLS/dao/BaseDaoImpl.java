package com.datagroup.ESLS.dao;

import com.datagroup.ESLS.common.constant.SqlConstant;
import com.datagroup.ESLS.common.request.RequestBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Component
public class BaseDaoImpl implements BaseDao{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List findBySql(String sql) {
        Query query = entityManager.createNativeQuery(sql);
        return query.getResultList();
    }

    @Override
    public List findBySql(String sql,Class clazz) {
        Query query = entityManager.createNativeQuery(sql,clazz);
        return query.getResultList();
    }

    @Override
    public List findAllBySql(String table, String query,String queryString, int page, int count,Class clazz) {
        StringBuffer sql = new StringBuffer();
        sql.append(SqlConstant.getQuerySql(table,query,"like","%"+queryString+"%"))
                .append(" ORDER BY id DESC ")
                .append("limit ")
                .append(page*count+","+count);
        return entityManager.createNativeQuery(sql.toString(),clazz).getResultList();
    }

    @Override
    @Transactional
    public Integer updateByArrtribute(String table, RequestBean source, RequestBean target) {
        StringBuffer sql = new StringBuffer();
        sql.append(SqlConstant.getUpdateSql(table,source,target));
        return entityManager.createNativeQuery(sql.toString()).executeUpdate();
    }

    @Override
    public List findByArrtribute(String table,String query, String queryString,Class clazz) {
        StringBuffer sql = new StringBuffer();
        sql.append(SqlConstant.getQuerySql(table,query,"=",queryString));
        return entityManager.createNativeQuery(sql.toString(),clazz).getResultList();
    }

    @Override
    public List findAllBySql(String table, String query, String queryString,Class clazz) {
        StringBuffer sql = new StringBuffer();
        sql.append(SqlConstant.getQuerySql(table,query,"like","%"+queryString+"%"))
                .append(" ORDER BY id DESC ");
        return entityManager.createNativeQuery(sql.toString(),clazz).getResultList();
    }
}
