
package com.datagroup.ESLS.serviceImpl;

import com.datagroup.ESLS.common.request.RequestBean;
import com.datagroup.ESLS.dao.BaseDao;
import com.datagroup.ESLS.service.Service;
import com.sun.xml.internal.messaging.saaj.packaging.mime.util.QEncoderStream;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@org.springframework.stereotype.Service("BaseService")
public class BaseServiceImpl implements Service {
    @Autowired
    protected BaseDao baseDao;

    @Override
    public List findBySql(String s) {
        return baseDao.findBySql(s);
    }

    @Override
    public List findBySql(String s,Class clazz) {
        return baseDao.findBySql(s,clazz);
    }

    @Override
    public List findAllBySql(String table, String query, String queryString, int page, int count,Class clazz) {
        return baseDao.findAllBySql(table,query,queryString,page,count,clazz);
    }

    @Override
    public  List findByArrtribute(String table, String query, String queryString,Class clazz) {
        return baseDao.findByArrtribute(table,query,queryString,clazz);
    }

    @Override
    public List findAllBySql(String table, String query, String queryString,Class clazz) {
        return baseDao.findAllBySql(table,query, queryString,clazz);
    }

    @Override
    public Integer updateByArrtribute(String table, RequestBean source, RequestBean target) {
        return baseDao.updateByArrtribute(table,source,target);
    }
}
