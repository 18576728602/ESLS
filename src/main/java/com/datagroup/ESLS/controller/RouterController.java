package com.datagroup.ESLS.controller;

import com.datagroup.ESLS.aop.Log;
import com.datagroup.ESLS.common.constant.TableConstant;
import com.datagroup.ESLS.common.response.ResultBean;
import com.datagroup.ESLS.dto.RouterVo;
import com.datagroup.ESLS.entity.Router;
import com.datagroup.ESLS.entity.Style;
import com.datagroup.ESLS.service.RouterService;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@RestController
@Api(description = "路由器管理API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RouterController {

    @Autowired
    RouterService routerService;

    @ApiOperation(value = "根据条件获取路由器信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "query", value = "查询条件 可为所有字段", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "queryString", value = "查询条件的字符串", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页码", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "count", value = "数量", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("/routers")
    @Log("获取路由器信息")
    public ResponseEntity<ResultBean> getRouter(@RequestParam(required = false) String query, @RequestParam(required = false) String queryString, @Min(message = "data.page.min", value = 0)@RequestParam Integer page, @Min(message = "data.count.min", value = 0)@RequestParam Integer count) {
        if(query!=null && queryString!=null) {
            List<Router> list = routerService.findAllBySql(TableConstant.TABLE_ROUTERS, query, queryString, page, count,Router.class);
            return new ResponseEntity<>(new ResultBean(list,list.size()), HttpStatus.OK);
        }
        List<Router> list = routerService.findAll();
        List<Router> content = routerService.findAll(page,count);
        return new ResponseEntity<>(new ResultBean(content,list.size()),HttpStatus.OK);
    }

    @ApiOperation(value = "获取指定ID的路由器信息")
    @GetMapping("/router/{id}")
    @Log("获取指定ID的路由器信息")
    public ResponseEntity<ResultBean> getRouterById(@PathVariable Long id) {
        Optional<Router> result = routerService.findById(id);
        if(result.isPresent())
            return new ResponseEntity<>(new ResultBean(result),HttpStatus.OK);
        return new ResponseEntity<>(ResultBean.error("此ID路由器不存在"),HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "添加或修改路由器信息")
    @PostMapping("/router")
    @Log("添加或修改路由器信息")
    public ResponseEntity<ResultBean> saveRouter(@RequestBody @ApiParam(value = "路由器信息json格式") Router router) {
        //Router router = new Router();
      //  BeanUtils.copyProperties(routerVo,router);
        System.out.println(router);
        return new ResponseEntity<>(new ResultBean(routerService.saveOne(router)),HttpStatus.OK);
    }

    @ApiOperation(value = "根据ID删除路由器信息")
    @DeleteMapping("/router/{id}")
    @Log("根据ID删除路由器信息")
    public ResponseEntity<ResultBean> deleteRouterById(@PathVariable Long id) {
        boolean flag = routerService.deleteById(id);
        if(flag)
            return new ResponseEntity<>(ResultBean.success("删除成功"),HttpStatus.OK);
        return new ResponseEntity<>(ResultBean.success("删除失败！没有指定ID的路由器"),HttpStatus.BAD_REQUEST);

    }
    // 更换路由器
}
