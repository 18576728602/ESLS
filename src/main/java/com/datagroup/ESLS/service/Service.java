
package com.datagroup.ESLS.service;
import com.datagroup.ESLS.common.request.RequestBean;

import java.util.List;

public interface Service<T>
{
    List findBySql(String s);
    List findBySql(String s,Class clazz);
    List findAllBySql(String table, String query,String queryString, int page, int count,Class clazz);
    List findByArrtribute(String table, String query, String queryString,Class clazz);
    List findAllBySql(String table, String query,String queryString,Class clazz);
    Integer updateByArrtribute(String table, RequestBean source, RequestBean target);

}
