package com.datagroup.ESLS.controller;

import com.datagroup.ESLS.aop.Log;
import com.datagroup.ESLS.common.request.RequestBean;
import com.datagroup.ESLS.dto.StyleVo;
import com.datagroup.ESLS.common.constant.ArrtributeConstant;
import com.datagroup.ESLS.common.constant.TableConstant;
import com.datagroup.ESLS.common.response.ResultBean;
import com.datagroup.ESLS.entity.Dispms;
import com.datagroup.ESLS.entity.Style;
import com.datagroup.ESLS.service.DispmsService;
import com.datagroup.ESLS.service.StyleService;
import com.datagroup.ESLS.service.TagService;
import com.datagroup.ESLS.utils.ConditionUtil;
import com.datagroup.ESLS.utils.CopyUtil;
import com.datagroup.ESLS.utils.ResponseUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@Api(description = "样式管理API")
@CrossOrigin(origins = "*", maxAge = 3600)
@Validated
public class StyleController {
    @Autowired
    private StyleService styleService;
    @Autowired
    private DispmsService dispmsService;
    @Autowired
    private TagService tagService;

    @ApiOperation(value = "根据条件获取样式信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "query", value = "查询条件 可为所有字段", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "queryString", value = "查询条件的字符串", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页码", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "count", value = "数量", required = false, dataType = "int", paramType = "query")
    })
    @GetMapping("/styles")
    @Log("获取样式数据")
    public ResponseEntity<ResultBean> getStyles(@RequestParam(required = false) String query, @RequestParam(required = false) String queryString, @Min(message = "data.page.min", value = 0) @RequestParam(required = false) Integer page, @Min(message = "data.count.min", value = 0) @RequestParam(required = false) Integer count) {
        String result = ConditionUtil.judgeArgument(query, queryString, page, count);
        if(result==null)
            return new ResponseEntity<>(ResultBean.error("参数组合有误 [query和queryString必须同时提供] [page和count必须同时提供]"), HttpStatus.OK);
        // 查询全部
        if(result.equals(ConditionUtil.QUERY_ALL)) {
            List<Style> list = styleService.findAll();
            List<StyleVo> resultList = CopyUtil.copyStyle(list);
            return new ResponseEntity<>(new ResultBean(resultList, resultList.size()), HttpStatus.OK);
        }
        // 查询全部分页
        if(result.equals(ConditionUtil.QUERY_ALL_PAGE)){
            List<Style> list = styleService.findAll();
            List<Style> content = styleService.findAll(page, count);
            List<StyleVo> resultList = CopyUtil.copyStyle(content);
            return new ResponseEntity<>(new ResultBean(resultList, list.size()), HttpStatus.OK);
        }
        // 带条件查询全部
        if(result.equals(ConditionUtil.QUERY_ATTRIBUTE_ALL)) {
            List content = styleService.findAllBySql(TableConstant.TABLE_STYLE, query, queryString, Style.class);
            List<StyleVo> resultList = CopyUtil.copyStyle(content);
            return new ResponseEntity<>(new ResultBean(resultList, resultList.size()), HttpStatus.OK);
        }
        // 带条件查询分页
        if(result.equals(ConditionUtil.QUERY_ATTRIBUTE_PAGE)) {
            List<Style> list = styleService.findAll();
            List  content = styleService.findAllBySql(TableConstant.TABLE_STYLE, query, queryString, page, count, Style.class);
            List<StyleVo> resultList = CopyUtil.copyStyle(content);
            return new ResponseEntity<>(new ResultBean(resultList, list.size()), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultBean.error("查询组合出错 函数未执行！"), HttpStatus.OK);
    }

    @ApiOperation(value = "获取指定ID的样式信息")
    @GetMapping("/style/{id}")
    @Log("获取指定ID的样式信息")
    public ResponseEntity<ResultBean> getStyleById(@PathVariable Long id) {
        Optional<Style> result = styleService.findById(id);
        if (result.isPresent()) {
            ArrayList<Style> styles = new ArrayList<>();
            styles.add(result.get());
            List<StyleVo> styleVo = CopyUtil.copyStyle(styles);
            return new ResponseEntity<>(new ResultBean(styleVo), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultBean.error("此ID样式不存在"), HttpStatus.BAD_REQUEST);
    }
    @ApiOperation("获得指定ID的样式的所有小样式信息")
    @GetMapping("/style/dispms/{id}")
    @Log("获得指定ID的样式的所有小样式信息")
    public ResponseEntity<ResultBean> getDispmses(@PathVariable Long id){
        List<Dispms> dispmses = dispmsService.findByArrtribute(TableConstant.TABLE_DISPMS, ArrtributeConstant.TAG_STYLEID, String.valueOf(id), Dispms.class);
        ResponseEntity<ResultBean> result = null;
        if ((result = ResponseUtil.testListSize("没有对应ID的小样式", dispmses)) != null) return result;
        return new ResponseEntity<>(ResultBean.success(dispmses), HttpStatus.OK);
    }
    @ApiOperation(value = "添加或修改样式信息")
    @PostMapping("/style")
    @Log("添加或修改样式信息")
    public ResponseEntity<ResultBean> saveStyle(@RequestBody @ApiParam(value = "样式信息json格式") Style style) {
        Style result = styleService.saveOne(style);
        return new ResponseEntity<>(new ResultBean(result), HttpStatus.OK);
    }

    @ApiOperation(value = "根据ID删除样式信息")
    @DeleteMapping("/style/{id}")
    @Log("根据ID删除样式信息")
    public ResponseEntity<ResultBean> deleteStyleById(@PathVariable Long id) {
        boolean flag = styleService.deleteById(id);
        if (flag)
            return new ResponseEntity<>(ResultBean.success("删除成功"), HttpStatus.OK);
        return new ResponseEntity<>(ResultBean.error("删除失败！没有指定ID的样式"), HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "更改指定ID样式的小样式")
    @PostMapping("/sytle/update")
    @Log("更改指定ID样式的小样式")
    public ResponseEntity<ResultBean> updateStyleById(@RequestParam long styleId, @RequestBody @ApiParam(value = "样式信息json格式") List<Dispms> dispmses) {
        // 通过styleId更改指定样式
        List<Style> styleList = styleService.findByArrtribute(TableConstant.TABLE_STYLE, ArrtributeConstant.TABLE_ID, String.valueOf(styleId), Style.class);
        ResponseEntity<ResultBean> result = null;
        if ((result = ResponseUtil.testListSize("没有对应ID的样式", styleList)) != null) return result;
        Style style = styleList.get(0);
        // 更新所有小样式
        dispmses.forEach(item -> {
            item.setStyle(style);
            dispmsService.saveOne(item);
        });
        // 通过styleId查找使用了此样式的所有标签实体
        List<com.datagroup.ESLS.entity.Tag> tagList = tagService.findByArrtribute(TableConstant.TABLE_TAGS, ArrtributeConstant.TAG_STYLEID, String.valueOf(styleId), com.datagroup.ESLS.entity.Tag.class);
        // 通过标签实体的路由器IP地址发送更改标签内容包
        tagList.forEach(
                // 获得所有标签对应的IP地址
                item -> {
                    byte[] message = new byte[2];
                    message[0]=0x02;
                    message[1]=0x03;
                    InetSocketAddress target = new InetSocketAddress(item.getRouter().getIp(), item.getRouter().getPort());
                        //System.out.println("执行结果："+ NettyClient.startAndWrite(target,message));
                }
                // 发送命令
        );
        // 返回前端提示信息
        return new ResponseEntity<>(ResultBean.success("样式更换成功"), HttpStatus.OK);
    }
    @ApiOperation(value = "刷新选用该样式的标签或设置定期刷新",notes = "定期刷新才需加beginTime和cycleTime字段")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mode", value = " 0为刷新选用该样式的标签 1定期刷新", dataType = "int", paramType = "query")
    })
    @PutMapping("/style/flush")
    @Log("刷新选用该样式的标签或设置定期刷新")
    public ResponseEntity<ResultBean> flushTags(@RequestBody @ApiParam("标签或路由器信息集合") RequestBean requestBean,@RequestParam  @Min(message = "data.page.min", value = 0)  @Max(message = "data.mode.max", value = 1) Integer mode){
        return new  ResponseEntity<>(new ResultBean(styleService.flushTags(requestBean,mode)),HttpStatus.OK);
    }
}
