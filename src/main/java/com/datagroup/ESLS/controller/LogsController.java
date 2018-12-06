package com.datagroup.ESLS.controller;

import com.datagroup.ESLS.common.constant.TableConstant;
import com.datagroup.ESLS.common.response.ResultBean;
import com.datagroup.ESLS.entity.Logs;
import com.datagroup.ESLS.entity.Shop;
import com.datagroup.ESLS.service.LogService;
import com.datagroup.ESLS.service.ShopService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@RestController
@Api(description = "日志管理API")
@CrossOrigin(origins = "*",maxAge = 3600)
public class LogsController {
    @Autowired
    private LogService logService;
    @ApiOperation(value = "根据条件获取日志信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "query", value = "查询条件 可为所有字段 ", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "queryString", value = "查询条件的字符串", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页码", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "count", value = "数量", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("/logs")
    public ResponseEntity<ResultBean> getLogs(@RequestParam(required = false) String query, @RequestParam(required = false) String queryString, @Min(message = "data.page.min", value = 0)@RequestParam Integer page, @Min(message = "data.count.min", value = 0) @RequestParam Integer count) {
        if(query!=null && queryString!=null) {
            List<Logs> list = logService.findAllBySql(TableConstant.TABLE_LOGS, query, queryString, page, count,Logs.class);
            return new ResponseEntity<>(new ResultBean(list,list.size()), HttpStatus.OK);
        }
        List<Logs> list = logService.findAll();
        List<Logs> content = logService.findAll(page,count);
        return new ResponseEntity<>(new ResultBean(content,list.size()),HttpStatus.OK);    }

    @ApiOperation(value = "获取指定ID的日志信息")
    @GetMapping("/log/{id}")
    public ResponseEntity<ResultBean> getLogById(@PathVariable Long id) {
        Optional<Logs> result = logService.findById(id);
        if(result.isPresent())
            return new ResponseEntity<>(new ResultBean(result),HttpStatus.OK);
        return new ResponseEntity<>(ResultBean.error("此ID店铺不存在"),HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "添加或修改日志信息")
    @PostMapping("/log")
    public ResponseEntity<ResultBean>  saveLog(@RequestBody @ApiParam(value = "样店铺信息json格式")  Logs logs) {
        return new ResponseEntity<>(new ResultBean(logService.saveOne(logs)),HttpStatus.OK);
    }

    @ApiOperation(value = "根据ID删除日志信息")
    @DeleteMapping("/log/{id}")
    public ResponseEntity<ResultBean> deleteLogById(@PathVariable Long id) {
        boolean flag = logService.deleteById(id);
        if(flag)
            return new ResponseEntity<>(ResultBean.success("删除成功"),HttpStatus.OK);
        return new ResponseEntity<>(ResultBean.success("删除失败！没有指定ID的店铺"),HttpStatus.BAD_REQUEST);
    }
}
