package com.datagroup.ESLS.controller;

import com.datagroup.ESLS.aop.Log;
import com.datagroup.ESLS.common.constant.TableConstant;
import com.datagroup.ESLS.common.response.ResultBean;
import com.datagroup.ESLS.entity.Dispms;
import com.datagroup.ESLS.service.DispmsService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@RestController
@Api(description = "样式块API")
//实现跨域注解
@CrossOrigin(origins = "*", maxAge = 3600)
public class DispmsController {
    @Autowired
    private DispmsService dispmsService;
    @ApiOperation(value = "根据条件获取样式块信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "query", value = "查询条件 可为所有字段", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "queryString", value = "查询条件的字符串", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页码", required = true,dataType = "int",  paramType = "query"),
            @ApiImplicitParam(name = "count", value = "数量", required = true,dataType = "int",  paramType = "query")
    })
    @GetMapping("/dispms")
    @Log("获取样式块信息")
    public ResponseEntity<ResultBean> getDispmses(@RequestParam(required = false) String query, @RequestParam(required = false) String queryString, @Min(message = "data.page.min", value = 0)@RequestParam Integer page, @Min(message = "data.count.min", value = 0) @RequestParam Integer count){
        if(query!=null && queryString!=null) {
            List<Dispms> list = dispmsService.findAllBySql(TableConstant.TABLE_DISPMS, query, queryString, page, count,Dispms.class);
            return new ResponseEntity<>(new ResultBean(list,list.size()), HttpStatus.OK);
        }
        List<Dispms> list = dispmsService.findAll();
        List<Dispms> content = dispmsService.findAll(page,count);
        return new ResponseEntity<>(new ResultBean(content,list.size()),HttpStatus.OK);
    }
    @ApiOperation(value = "获取指定ID的样式块信息")
    @GetMapping("/dispm/{id}")
    @Log("获取指定ID的样式块信息")
    public ResponseEntity<ResultBean> getDispmsById(@PathVariable Long id){
        Optional<Dispms> dispms = dispmsService.findById(id);
        if(dispms.isPresent())
            return new ResponseEntity<>(new ResultBean(dispms),HttpStatus.OK);
        return new ResponseEntity<>(ResultBean.error("此ID样式块不存在"),HttpStatus.BAD_REQUEST);
    }
    @ApiOperation(value = "添加或修改样式块信息")
    @PostMapping("/dispm")
    public ResponseEntity<ResultBean> saveDispms(@RequestBody @ApiParam(value="样式块信息json格式") Dispms dispms){
        return new ResponseEntity<>(new ResultBean(dispmsService.saveOne(dispms)),HttpStatus.OK);
    }
    @ApiOperation(value = "根据ID删除样式块信息")
    @DeleteMapping("/dispm/{id}")
    public ResponseEntity<ResultBean> deleteDispmsById(@PathVariable Long id) {
        boolean flag = dispmsService.deleteById(id);
        if(flag)
            return new ResponseEntity<>(ResultBean.success("删除成功"),HttpStatus.OK);
        return new ResponseEntity<>(ResultBean.success("删除失败！没有指定ID的样式块"),HttpStatus.BAD_REQUEST);
    }
}
