package com.datagroup.ESLS.controller;

import com.datagroup.ESLS.aop.Log;
import com.datagroup.ESLS.common.constant.TableConstant;
import com.datagroup.ESLS.common.request.RequestBean;
import com.datagroup.ESLS.common.response.ResultBean;
import com.datagroup.ESLS.dao.GoodDao;
import com.datagroup.ESLS.entity.Good;
import com.datagroup.ESLS.service.GoodService;
import com.datagroup.ESLS.utils.ConditionUtil;
import com.datagroup.ESLS.utils.CopyUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api(description = "商品管理API2")
@CrossOrigin(origins = "*", maxAge = 3600)
@Validated
public class GoodController {

    @Autowired
    private GoodService goodService;
    @Autowired
    private GoodDao goodDao;
    @ApiOperation(value = "根据条件获取商品信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "query", value = "查询条件 可为所有字段 ", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "queryString", value = "查询条件的字符串", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页码", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "count", value = "数量", dataType = "int", paramType = "query")
    })
    @GetMapping("/goods")
    @Log("获取商品信息")
    public ResponseEntity<ResultBean> getGoods(@RequestParam(required = false) String query, @RequestParam(required = false) String queryString, @Min(message = "data.page.min", value = 0)@RequestParam(required = false) Integer page,@RequestParam(required = false) @Min(message = "data.count.min", value = 0)Integer count) {
        String result = ConditionUtil.judgeArgument(query, queryString, page, count);
        if(result==null)
            return new ResponseEntity<>(ResultBean.error("参数组合有误 [query和queryString必须同时提供] [page和count必须同时提供]"), HttpStatus.OK);
        // 查询全部
        if(result.equals(ConditionUtil.QUERY_ALL)) {
            List list = goodService.findAll();
            List content = CopyUtil.copyGood(list);
            return new ResponseEntity<>(new ResultBean(content, list.size()), HttpStatus.OK);
        }
        // 查询全部分页
        if(result.equals(ConditionUtil.QUERY_ALL_PAGE)){
            List list = goodService.findAll();
            List content = goodService.findAll(page, count);
            List resultList = CopyUtil.copyGood(content);
            return new ResponseEntity<>(new ResultBean(resultList, list.size()), HttpStatus.OK);
        }
        // 带条件查询全部
        if(result.equals(ConditionUtil.QUERY_ATTRIBUTE_ALL)) {
            List content = goodService.findAllBySql(TableConstant.TABLE_GOODS, query, queryString,Good.class);
            List resultList = CopyUtil.copyGood(content);
            return new ResponseEntity<>(new ResultBean(resultList, resultList.size()), HttpStatus.OK);
        }
        // 带条件查询分页
        if(result.equals(ConditionUtil.QUERY_ATTRIBUTE_PAGE)) {
            List list = goodService.findAll();
            List content = goodService.findAllBySql(TableConstant.TABLE_GOODS, query, queryString, page, count,Good.class);
            List resultList = CopyUtil.copyGood(content);
            return new ResponseEntity<>(new ResultBean(resultList, list.size()), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultBean.error("查询组合出错 函数未执行！"), HttpStatus.OK);
    }

    @ApiOperation(value = "获取指定ID的商品信息")
    @GetMapping("/goods/{id}")
    @Log("获取指定ID的商品信息")
    @Transactional
    public ResponseEntity<ResultBean> getGoodById(@PathVariable Long id) {
        Good good = goodService.findById(id);
        if(good==null)
            return new ResponseEntity<>(ResultBean.error("此ID商品不存在"),HttpStatus.BAD_REQUEST);
        List goods = new ArrayList<Good>();
        goods.add(good);
        return new ResponseEntity<>(new ResultBean(CopyUtil.copyGood(goods)),HttpStatus.OK);
    }

    // 上传商品时带特征图片
    @ApiOperation(value = "添加或修改商品信息")
    @PostMapping("/good")
    @Log("添加或修改商品信息")
    public ResponseEntity<ResultBean>  saveGood(@RequestBody @ApiParam(value = "商品信息json格式") Good good) {
        // 设置商品等待更新变价
        good.setWaitUpdate(0);
        return new ResponseEntity<>(new ResultBean(goodService.saveOne(good)),HttpStatus.OK);
    }

    @ApiOperation(value = "根据ID删除商品信息")
    @DeleteMapping("/good/{id}")
    @Log("根据ID删除商品信息")
    public ResponseEntity<ResultBean> deleteGoodById(@PathVariable Long id) {
        boolean flag = goodService.deleteById(id);
        if(flag)
            return new ResponseEntity<>(ResultBean.success("删除成功"),HttpStatus.OK);
        return new ResponseEntity<>(ResultBean.success("删除失败！没有指定ID的商品"),HttpStatus.BAD_REQUEST);
    }

    @ApiOperation("对绑定商品的所有标签内容进行更新")
    @PutMapping("/good/update")
    @Log("对绑定商品的所有标签内容进行更新(可单个 批量)")
    public ResponseEntity<ResultBean> updateGoods(@RequestBody @ApiParam("商品信息集合") RequestBean requestBean){
        return new ResponseEntity<>(new ResultBean(goodService.updateGoods(requestBean)),HttpStatus.OK);
    }
}
