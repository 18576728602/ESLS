package com.datagroup.ESLS.controller;

import com.datagroup.ESLS.common.response.ResultBean;
import com.datagroup.ESLS.dao.ProductDao;
import com.datagroup.ESLS.entity.ProductEntity;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@Api(description = "商品管理API")
//实现跨域注解
//origin="*"代表所有域名都可访问
//maxAge飞行前响应的缓存持续时间的最大年龄，简单来说就是Cookie的有效期 单位为秒
//若maxAge是负数,则代表为临时Cookie,不会被持久化,Cookie信息保存在浏览器内存中,浏览器关闭Cookie就消失
@CrossOrigin(origins = "*",maxAge = 3600)
public class ProductController {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private RestTemplate restTemplate;

    @ApiOperation(value = "添加或修改商品信息",notes = "测试时请去掉updatetime字段 否则无法返回正确结果")
    @PostMapping("/product")
    public ResultBean saveProduct(@RequestBody @ApiParam(value="商品信息json格式")ProductEntity productEntity){
        ProductEntity save = productDao.save(productEntity);
        if(save!=null)
            return new ResultBean("success");
        return new ResultBean(new Exception("添加或修改商品失败"));
    }

    @ApiOperation(value = "获取指定ID的商品信息")
    @GetMapping("/product/{id}")
    public ResultBean getProductById(@PathVariable Long id){
        Optional<ProductEntity> entity = productDao.findById(id);
        if(entity.isPresent())
            return new ResultBean(entity);
        return new ResultBean(new Exception("获取商品信息失败"));
    }

    @ApiOperation(value = "根据ID删除商品信息")
    @DeleteMapping("/product/{id}")
    public ResultBean deleteProductById(@PathVariable Long id) {
        productDao.deleteById(id);
        return new ResultBean("删除成功");
    }

    @ApiOperation(value = "获得指定页数的的商品信息（用于分页显示）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数(从零开始)", required = true, dataType = "Int", paramType = "query"),
            @ApiImplicitParam(name = "count", value = "数量", required = true,dataType = "Int",  paramType = "query")
    })
    @GetMapping("/products")
    public ResultBean getProducts(@RequestParam Integer page, @RequestParam Integer count){
        System.out.println(page+" "+count);
        List<ProductEntity> list = productDao.findAll();
        return new ResultBean(productDao
                .findAll(PageRequest.of(page, count, Sort.Direction.DESC, "id"))
                .getContent(),list.size());
    }
    @ApiOperation(value = "根据商品名称和编号查询商品信息(搜索功能)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "商品名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "number", value = "商品编号",dataType = "String",  paramType = "query")
    })
    @GetMapping("/product")
    public ResultBean findProductByMessage(@RequestParam(required = false) String name,@RequestParam(required = false) String number){
        System.out.println(name+" "+number);
        if(number==null) number="";
        if(name==null) name="";
        return new ResultBean(productDao.findByNameLikeAndProductIdLike("%"+name+"%","%"+number+"%"));
    }
    @ApiOperation(value = "得到所有商品信息")
    @GetMapping("/products/all")
    public ResultBean getAllProduct(){
        List<ProductEntity> list = productDao.findAll();
        return new ResultBean(list);
    }
    @ApiOperation(value = "测试http请求")
    @GetMapping("/test/http")
    public ResultBean testHttp(String name){
        // http://api.douban.com/v2/movie/top250
        String uri="https://api.weixin.qq.com/sns/jscode2session?appid=wx2e7699073ea66d7a&secret=051b8d7896efd2d09e8627099a45e47d&js_code=033mPe7k0nv8xm1kt76k0bw47k0mPe7D&grant_type=authorization_code";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        System.out.println(name);
        String strbody=restTemplate.exchange(uri, HttpMethod.GET, entity,String.class).getBody();
        return new ResultBean(strbody.substring(strbody.indexOf("openid")+9,strbody.length()-2));
    }
}
