package com.datagroup.ESLS.controller;

import com.datagroup.ESLS.common.constant.SqlConstant;
import com.datagroup.ESLS.common.response.ResultBean;
import com.datagroup.ESLS.dao.BaseDao;
import com.datagroup.ESLS.entity.Good;
import com.datagroup.ESLS.utils.CopyUtil;
import com.datagroup.ESLS.utils.PoiUtil;
import com.datagroup.ESLS.utils.SpringContextUtil;
import io.swagger.annotations.*;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Min;
import java.io.OutputStream;
import java.util.List;

@Api(description = "通用工具类")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Validated
public class CommonController {
    @Autowired
    private BaseDao baseDao;

    @ApiOperation("获取数据库表信息（0）或获取数据表的所有字段（表名,1）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mode", value = " 0获取所有数据库表 1获取相应表的所有字段信息", dataType = "int", paramType = "query")
    })
    @GetMapping("/common/database")
    public ResponseEntity<ResultBean> getTableColumn(@RequestParam(required = false) @ApiParam("表名") String tableName, @RequestParam Integer mode) {
        String sql = SqlConstant.QUERY_ALL_TABLE;
        if (mode == 1)
            sql = SqlConstant.QUERY_TABLIE_COLUMN + "\'" + tableName + "\'";
        return new ResponseEntity<>(new ResultBean(baseDao.findBySql(sql)), HttpStatus.OK);
    }

    @ApiOperation(value = "导出数据库excel报表")
    @GetMapping("/common/database/excel")
    public ResponseEntity<ResultBean> getExcelByTableName(@RequestParam String tableName, HttpServletRequest request, HttpServletResponse response) {
        List dataColumnList = baseDao.findBySql(SqlConstant.QUERY_TABLIE_COLUMN + "\'" + tableName + "\'");
        List dataList = null;
        try {
            dataList = baseDao.findBySql(SqlConstant.SELECT_QUERY + tableName, Class.forName("com.datagroup.ESLS.entity." + SqlConstant.EntityToSqlMap.get(tableName)));
        } catch (ClassNotFoundException e) {
            return new ResponseEntity<>(ResultBean.error("导出excel出错"), HttpStatus.BAD_REQUEST);
        }
        HSSFWorkbook hssfWorkbook = PoiUtil.exportData2Excel(dataList, dataColumnList, tableName);
        //以流输出到浏览器
        PoiUtil.writeToResponse(hssfWorkbook, request, response, tableName);
        return new ResponseEntity<>(ResultBean.success("导出excel成功"), HttpStatus.OK);
    }

    @ApiOperation("导出数据库csv文件")
    @GetMapping("/common/database/csv")
    public ResponseEntity<ResultBean> getCsvByTableName(@RequestParam @ApiParam("数据库表名") String tableName, HttpServletResponse response) {
        List dataColumnList = baseDao.findBySql(SqlConstant.QUERY_TABLIE_COLUMN + "\'" + tableName + "\'");
        List dataList = null;
        try (final OutputStream os = response.getOutputStream()) {
            dataList = baseDao.findBySql(SqlConstant.SELECT_QUERY + tableName, Class.forName("com.datagroup.ESLS.entity." + SqlConstant.EntityToSqlMap.get(tableName)));
            PoiUtil.responseSetProperties(tableName, response);
            PoiUtil.exportData2Csv(dataList, dataColumnList, os);
        } catch (Exception e) {
            return new ResponseEntity<>(ResultBean.error("导出csv出错"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ResultBean.success("导出csv成功"), HttpStatus.OK);
    }
    @ApiOperation("设置通讯命令超时时间，由于发送命令会造成http通讯阻塞，建议不超过5s(单位为秒)")
    @PutMapping("/common/command/time")
    public ResponseEntity<ResultBean> setCommandTime(@ApiParam("设置的通讯超时时间") @RequestParam @Min(message = "data.time.min",value = 0) Integer time){
        SpringContextUtil.setCommandTime(String.valueOf(time));
        return new ResponseEntity<>(ResultBean.success("设置成功"),HttpStatus.OK);
    }
    @ApiOperation("设置token存活时间(单位为秒)")
    @PutMapping("/common/token/time")
    public ResponseEntity<ResultBean> setTokenTime(@ApiParam("token存活时间") @RequestParam @Min(message = "data.time.min",value = 0) Long time){
        SpringContextUtil.setAliveTime(time);
        return new ResponseEntity<>(ResultBean.success("设置成功"),HttpStatus.OK);
    }
}
