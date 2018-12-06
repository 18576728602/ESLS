package com.datagroup.ESLS.controller;

import com.datagroup.ESLS.common.constant.TableConstant;
import com.datagroup.ESLS.common.response.ResultBean;
import com.datagroup.ESLS.entity.Dispmmanager;
import com.datagroup.ESLS.entity.Style;
import com.datagroup.ESLS.service.DispmmanagerService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@RestController
@Api(description = "样式字段管理API")
@CrossOrigin(origins = "*",maxAge = 3600)
public class DispmmanagerController {
    @Autowired
    DispmmanagerService dispmmanagerService;
    @ApiOperation(value = "根据条件获取样式字段信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "query", value = "查询条件 可为所有字段", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "queryString", value = "查询条件的字符串", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页码", required = true,dataType = "int",  paramType = "query"),
            @ApiImplicitParam(name = "count", value = "数量", required = true,dataType = "int",  paramType = "query")
    })
    @GetMapping("/fields")
    public ResponseEntity<ResultBean> getDispmmanagers(@RequestParam(required = false) String query, @RequestParam(required = false) String queryString, @Min(message = "data.page.min", value = 0)@RequestParam Integer page,@Min(message = "data.count.min", value = 0) @RequestParam Integer count){
        if(query!=null && queryString!=null) {
            List<Dispmmanager> list = dispmmanagerService.findAllBySql(TableConstant.TABLE_DISPMMANAGER, query, queryString, page, count,Dispmmanager.class);
            return new ResponseEntity<>(new ResultBean(list,list.size()), HttpStatus.OK);
        }
        List<Dispmmanager> list = dispmmanagerService.findAll();
        List<Dispmmanager> content = dispmmanagerService.findAll(page,count);
        return new ResponseEntity<>(new ResultBean(content,list.size()),HttpStatus.OK);
    }
    @ApiOperation(value = "获取指定ID的样式字段信息")
    @GetMapping("/field/{id}")
    public ResponseEntity<ResultBean> getDispmmanagerById(@PathVariable Long id){
        Optional<Dispmmanager> result = dispmmanagerService.findById(id);
        if(result.isPresent())
            return new ResponseEntity<>(new ResultBean(result),HttpStatus.OK);
        return new ResponseEntity<>(ResultBean.error("此ID样式字段不存在"),HttpStatus.BAD_REQUEST);
    }
    @ApiOperation(value = "添加或修改样式字段信息")
    @PostMapping("/field")
    public ResponseEntity<ResultBean> saveDispmmanager(@RequestBody @ApiParam(value="样式字段信息json格式") Dispmmanager dispmmanager){
        return new ResponseEntity<>(new ResultBean(dispmmanagerService.saveOne(dispmmanager)),HttpStatus.OK);
    }
    @ApiOperation(value = "根据ID删除样式字段信息")
    @DeleteMapping("/field/{id}")
    public ResponseEntity<ResultBean> deleteDispmmanagerById(@PathVariable Long id) {
        boolean flag = dispmmanagerService.deleteById(id);
        if(flag)
            return new ResponseEntity<>(ResultBean.success("删除成功"),HttpStatus.OK);
        return new ResponseEntity<>(ResultBean.success("删除失败！没有指定ID的样式字段"),HttpStatus.BAD_REQUEST);

    }
}
