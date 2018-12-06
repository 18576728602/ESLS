package com.datagroup.ESLS.controller;

import com.datagroup.ESLS.aop.Log;
import com.datagroup.ESLS.common.constant.TableConstant;
import com.datagroup.ESLS.common.response.ResultBean;
import com.datagroup.ESLS.entity.Scan;
import com.datagroup.ESLS.entity.Style;
import com.datagroup.ESLS.service.ScanService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@RestController
@Api(description = "扫描枪管理API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ScanController {

    @Autowired
    ScanService scanService;

    @ApiOperation(value = "根据条件获取扫描枪信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "query", value = "查询条件 可为所有字段 ", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "queryString", value = "查询条件的字符串", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页码", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "count", value = "数量", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("/scans")
    @Log("获取扫描枪信息")
    public ResponseEntity<ResultBean> getScans(@RequestParam(required = false) String query, @RequestParam(required = false) String queryString, @Min(message = "data.page.min", value = 0)@RequestParam Integer page,@Min(message = "data.count.min", value = 0) @RequestParam Integer count) {
        if(query!=null && queryString!=null) {
            List<Scan> list = scanService.findAllBySql(TableConstant.TABLE_SCANS, query, queryString, page, count,Scan.class);
            return new ResponseEntity<>(new ResultBean(list,list.size()), HttpStatus.OK);
        }
        List<Scan> list = scanService.findAll();
        List<Scan> content = scanService.findAll(page,count);
        return new ResponseEntity<>(new ResultBean(content,list.size()),HttpStatus.OK);    }

    @ApiOperation(value = "获取指定ID的扫描枪信息")
    @GetMapping("/scan/{id}")
    @Log("获取指定ID的扫描枪信息")
    public ResponseEntity<ResultBean> getScanById(@PathVariable Long id) {
        Optional<Scan> result = scanService.findById(id);
        if(result.isPresent())
            return new ResponseEntity<>(new ResultBean(result),HttpStatus.OK);
        return new ResponseEntity<>(ResultBean.error("此ID扫描枪不存在"),HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "添加或修改扫描枪信息")
    @PostMapping("/scan")
    @Log("添加或修改扫描枪信息")
    public ResponseEntity<ResultBean>  saveScan(@RequestBody @ApiParam(value = "样式字段信息json格式") Scan scan) {
        return new ResponseEntity<>(new ResultBean(scanService.saveOne(scan)),HttpStatus.OK);
    }

    @ApiOperation(value = "根据ID删除扫描枪信息")
    @DeleteMapping("/scan/{id}")
    @Log("根据ID删除扫描枪信息")
    public ResponseEntity<ResultBean> deleteScanById(@PathVariable Long id) {
        boolean flag = scanService.deleteById(id);
        if(flag)
            return new ResponseEntity<>(ResultBean.success("删除成功"),HttpStatus.OK);
        return new ResponseEntity<>(ResultBean.success("删除失败！没有指定ID的扫描枪"),HttpStatus.BAD_REQUEST);
    }
}
