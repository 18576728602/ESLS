package com.datagroup.ESLS.common.constant;

import com.datagroup.ESLS.common.request.RequestBean;
import com.datagroup.ESLS.common.request.RequestItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlConstant {

    public static String SELECT_QUERY = "select * from ";
    public static String QUERY_TABLIE_COLUMN = "SELECT column_name FROM information_schema.Columns WHERE table_name= ";
    public static String QUERY_ALL_TABLE = "SELECT table_name FROM information_schema.tables WHERE  table_schema= 'tags' ";

    public static String getQuerySql(String table, String query, String connection, String queryString) {
        return new StringBuffer().append(SELECT_QUERY)
                .append(table)
                .append(" where ")
                .append(query + " ")
                .append(connection + " ")
                .append("\'" + queryString + "\'").toString();
    }

    public static String getUpdateSql(String table, RequestBean source, RequestBean target) {
        StringBuffer sql = new StringBuffer();
        sql.append("update ").append(table + " set ");
        List<RequestItem> items = target.getItems();
        RequestItem item ;
        int i=0;
        for (; i < items.size() - 1; i++) {
            item = items.get(i);
            sql.append(item.getQuery() + "=").append("\'"+item.getQueryString() + "\' ,");
        }
        sql.append(items.get(i).getQuery() + "=").append("\'"+items.get(i).getQueryString()).append("\' where ");
        items = source.getItems();
        for(i=0;i<items.size()-1;i++) {
            item = items.get(i);
            sql.append(item.getQuery() + "=").append("\'"+item.getQueryString() +"\'"+ " and ");
        }
        sql.append(items.get(i).getQuery() + "=").append("\'"+items.get(i).getQueryString() +"\'");
        return sql.toString();
    }

    public static Map<String, String> EntityToSqlMap = new HashMap();

    static {
        EntityToSqlMap.put("goods", "Good");
        EntityToSqlMap.put("tags", "Tag");
        EntityToSqlMap.put("routers", "Router");
        EntityToSqlMap.put("logs", "Logs");
        EntityToSqlMap.put("scans", "Scan");
        EntityToSqlMap.put("users", "User");
    }
}
