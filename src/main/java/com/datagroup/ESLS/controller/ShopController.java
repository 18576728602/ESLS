package com.datagroup.ESLS.controller;

import com.datagroup.ESLS.aop.Log;
import com.datagroup.ESLS.common.constant.TableConstant;
import com.datagroup.ESLS.common.response.ResultBean;
import com.datagroup.ESLS.entity.Shop;
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
@Api(description = "店铺管理API")
@CrossOrigin(origins = "*",maxAge = 3600)
public class ShopController {
    @Autowired
    private ShopService shopService;
    @ApiOperation(value = "根据条件获取店铺信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "query", value = "查询条件 可为所有字段 ", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "queryString", value = "查询条件的字符串", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页码", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "count", value = "数量", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("/shops")
    @Log("获取店铺信息")
    public ResponseEntity<ResultBean> getShops(@RequestParam(required = false) String query, @RequestParam(required = false) String queryString, @Min(message = "data.page.min", value = 0)@RequestParam Integer page, @Min(message = "data.count.min", value = 0) @RequestParam Integer count) {
        if(query!=null && queryString!=null) {
            List<Shop> list = shopService.findAllBySql(TableConstant.TABLE_SHOPS, query, queryString, page, count,Shop.class);
            return new ResponseEntity<>(new ResultBean(list,list.size()), HttpStatus.OK);
        }
        List<Shop> list = shopService.findAll();
        List<Shop> content = shopService.findAll(page,count);
        return new ResponseEntity<>(new ResultBean(content,list.size()),HttpStatus.OK);    }

    @ApiOperation(value = "获取指定ID的店铺信息")
    @GetMapping("/shop/{id}")
    @Log("获取指定ID的店铺信息")
    public ResponseEntity<ResultBean> getShopById(@PathVariable Long id) {
        Optional<Shop> result = shopService.findById(id);
        if(result.isPresent())
            return new ResponseEntity<>(new ResultBean(result),HttpStatus.OK);
        return new ResponseEntity<>(ResultBean.error("此ID店铺不存在"),HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "添加或修改店铺信息")
    @PostMapping("/shop")
    @Log("添加或修改店铺信息")
    public ResponseEntity<ResultBean>  saveShop(@RequestBody @ApiParam(value = "店铺信息json格式") Shop shop) {
        return new ResponseEntity<>(new ResultBean(shopService.saveOne(shop)),HttpStatus.OK);
    }

    @ApiOperation(value = "根据ID删除店铺信息")
    @DeleteMapping("/shop/{id}")
    @Log("根据ID删除店铺信息")
    public ResponseEntity<ResultBean> deleteShopById(@PathVariable Long id) {
        boolean flag = shopService.deleteById(id);
        if(flag)
            return new ResponseEntity<>(ResultBean.success("删除成功"),HttpStatus.OK);
        return new ResponseEntity<>(ResultBean.success("删除失败！没有指定ID的店铺"),HttpStatus.BAD_REQUEST);
    }
}
