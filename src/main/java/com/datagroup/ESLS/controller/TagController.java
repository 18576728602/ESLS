package com.datagroup.ESLS.controller;

import com.datagroup.ESLS.aop.Log;
import com.datagroup.ESLS.common.constant.ArrtributeConstant;
import com.datagroup.ESLS.common.constant.ModeConstant;
import com.datagroup.ESLS.common.constant.TableConstant;
import com.datagroup.ESLS.common.request.RequestBean;
import com.datagroup.ESLS.common.response.ResponseBean;
import com.datagroup.ESLS.common.response.ResultBean;
import com.datagroup.ESLS.entity.Good;
import com.datagroup.ESLS.entity.Router;
import com.datagroup.ESLS.entity.Style;
import com.datagroup.ESLS.entity.Tag;
import com.datagroup.ESLS.dto.TagVo;
import com.datagroup.ESLS.netty.client.NettyClient;
import com.datagroup.ESLS.service.GoodService;
import com.datagroup.ESLS.service.TagService;
import com.datagroup.ESLS.utils.ConditionUtil;
import com.datagroup.ESLS.utils.CopyUtil;
import com.datagroup.ESLS.utils.ResponseUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.*;

@RestController
@Api(description = "标签管理API")
@CrossOrigin(origins = "*", maxAge = 3600)
@Validated
@Slf4j
public class TagController {
    @Autowired
    private TagService tagService;
    @Autowired
    private GoodService goodService;

    @ApiOperation(value = "根据条件获取标签信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "query", value = " 查询条件 可为所有字段", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "queryString", value = "查询条件的字符串", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页码", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "count", value = "数量", dataType = "int", paramType = "query")
    })
    @GetMapping("/tags")
    @Log("获取标签数据")
    public ResponseEntity<ResultBean> getTags(@RequestParam(required = false) String query, @RequestParam(required = false) String queryString, @Min(message = "data.page.min", value = 0)@RequestParam(required = false) Integer page, @Min(message = "data.count.min", value = 0) @RequestParam(required = false) Integer count) {
        String result = ConditionUtil.judgeArgument(query, queryString, page, count);
        if(result==null)
            return new ResponseEntity<>(ResultBean.error("参数组合有误 [query和queryString必须同时提供] [page和count必须同时提供]"), HttpStatus.OK);
        // 查询全部
        if(result.equals(ConditionUtil.QUERY_ALL)) {
            List<Tag> list = tagService.findAll();
            List<TagVo> resultList = CopyUtil.copyTag(list);
            return new ResponseEntity<>(new ResultBean(resultList, resultList.size()), HttpStatus.OK);
        }
        // 查询全部分页
        if(result.equals(ConditionUtil.QUERY_ALL_PAGE)){
            List<Tag> list = tagService.findAll();
            List<Tag> content = tagService.findAll(page, count);
            List<TagVo> resultList = CopyUtil.copyTag(content);
            return new ResponseEntity<>(new ResultBean(resultList, list.size()), HttpStatus.OK);
        }
        // 带条件查询全部
        if(result.equals(ConditionUtil.QUERY_ATTRIBUTE_ALL)) {
            List content = tagService.findAllBySql(TableConstant.TABLE_TAGS, query, queryString,Tag.class);
            List<TagVo> resultList = CopyUtil.copyTag(content);
            return new ResponseEntity<>(new ResultBean(resultList, resultList.size()), HttpStatus.OK);
        }
        // 带条件查询分页
        if(result.equals(ConditionUtil.QUERY_ATTRIBUTE_PAGE)) {
            List<Tag> list = tagService.findAll();
            List  content = tagService.findAllBySql(TableConstant.TABLE_TAGS, query, queryString, page, count,Tag.class);
            List<TagVo> resultList = CopyUtil.copyTag(content);
            return new ResponseEntity<>(new ResultBean(resultList, list.size()), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultBean.error("查询组合出错 函数未执行！"), HttpStatus.OK);
    }

    @ApiOperation(value = "获取指定ID的标签信息")
    @GetMapping("/tag/{id}")
    @Log("获取指定ID的标签信息")
    public ResponseEntity<ResultBean> getTagById(@PathVariable Long id) {
        Optional<Tag> result = tagService.findById(id);
        if (result.isPresent()) {
            ArrayList<Tag> tags = new ArrayList<>();
            tags.add(result.get());
            List<TagVo> tagVos = CopyUtil.copyTag(tags);
            return new ResponseEntity<>(new ResultBean(tagVos), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResultBean.error("此ID标签不存在"), HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "添加或修改标签信息")
    @PostMapping("/tag")
    @Log("添加或修改标签信息")
    public ResponseEntity<ResultBean> saveTag(@RequestBody @ApiParam(value = "标签信息json格式") TagVo tagVo) {
        Tag tag = new Tag();
        BeanUtils.copyProperties(tagVo, tag);
        // 绑定商品
        if (tagVo.getGoodId() != 0) {
            Good good = new Good();
            good.setId(tagVo.getGoodId());
            tag.setGood(good);
        }
        // 绑定样式
        if (tagVo.getStyleId() != 0) {
            Style style = new Style();
            style.setId(tagVo.getStyleId());
            tag.setStyle(style);
        }
        // 绑定路由
        if (tagVo.getRouterId() != 0) {
            Router router = new Router();
            router.setId(tagVo.getRouterId());
            tag.setRouter(router);
        }
        return new ResponseEntity<>(new ResultBean(tagService.saveOne(tag)), HttpStatus.OK);
    }

    @ApiOperation(value = "根据ID删除标签信息")
    @DeleteMapping("/tag/{id}")
    @Log("根据ID删除标签信息")
    public ResponseEntity<ResultBean> deleteTagById(@PathVariable Long id) {
        boolean flag = tagService.deleteById(id);
        if (flag)
            return new ResponseEntity<>(ResultBean.success("删除成功"), HttpStatus.OK);
        return new ResponseEntity<>(ResultBean.success("删除失败！没有指定ID的标签"), HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "自定义属性----标签和商品绑定和取消绑定")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceArgs1", value = "商品绑定属性", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "ArgsString1", value = "商品绑定属性值", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sourceArgs2", value = "标签绑定属性", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "ArgsString2", value = "标签绑定属性值", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "mode", value = "1绑定0取消绑定", required = true, dataType = "String", paramType = "query")
    })
    @PutMapping("/tag/bind")
    @Log("标签和商品绑定和取消绑定")
    public ResponseEntity<ResultBean> bindGoodsAndTagsById(@RequestParam String sourceArgs1, @RequestParam String ArgsString1, @RequestParam String sourceArgs2, @RequestParam String ArgsString2, String mode) {
        // 根据mode判断是绑定操作还是取消绑定操作
        // 根据商品ID获取商品实体
        List<Good> goods = goodService.findByArrtribute(TableConstant.TABLE_GOODS, sourceArgs1, ArgsString1, Good.class);
        // 根据标签Mac地址获取标签实体
        List<Tag> tagList = tagService.findByArrtribute(TableConstant.TABLE_TAGS, sourceArgs2, ArgsString2, Tag.class);
        ResponseEntity<ResultBean> result = null;
        if ((result = ResponseUtil.testListSize("没有相应的标签或商品 请重新选择", goods, tagList)) != null) return result;
        // 修改标签实体的goodid state
        Good good = goods.get(0);
        Tag tag = tagList.get(0);
        if (tag.getGood() == null) {
            if (mode.equals("1"))
                tag.setGood(good);
            else
                return new ResponseEntity<>(ResultBean.error(" 商品和标签暂未绑定 请改变mode重新发送请求 "), HttpStatus.BAD_REQUEST);
        } else if (tag.getGood() != null && mode.equals("1"))
            return new ResponseEntity<>(ResultBean.error(" 此标签已经绑定商品 请重新选择标签 "), HttpStatus.BAD_REQUEST);
        else
            tag.setGood(null);
        tagService.saveOne(tag);
        // 绑定完成后通过标签实体的路由器实体，向其发送修改样式包及更改显示信息包（tagsAndgoods）
        // 返回前端提示信息
        return new ResponseEntity<>(ResultBean.success(mode.equals("1") ? "绑定成功" : "取消绑定成功"), HttpStatus.OK);
    }

    @ApiOperation(value = "标签更换样式")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tagId", value = "标签ID", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "styleId", value = "需更改的目标样式ID", required = true, dataType = "long", paramType = "query"),
    })
    @PutMapping("/tag/sytle")
    @Log("标签更换样式")
    public ResponseEntity<ResultBean> updateTagStyleById(@RequestParam long tagId, @RequestParam long styleId) {
        // 修改标签实体对应的styleId
        List<Tag> tagList = tagService.findByArrtribute(TableConstant.TABLE_TAGS, ArrtributeConstant.TABLE_ID, String.valueOf(tagId), Tag.class);
        if (tagList.size() <= 0)
            return new ResponseEntity<>(ResultBean.error("没有相应ID的标签 请重新选择"), HttpStatus.BAD_REQUEST);
        Tag tag = tagList.get(0);
        if (tag.getStyle() != null && tag.getStyle().getId() == styleId)
            return new ResponseEntity<>(ResultBean.error("标签对应的样式一致,无需更改"), HttpStatus.BAD_REQUEST);
        else {
            Style style = new Style();
            style.setId(styleId);
            tag.setStyle(style);
            tagService.saveOne(tag);
        }
        // 修改样式完成后通过标签实体的路由器实体，向其发送修改样式包及更改显示信息包（tagsAndgoods）
        // 返回前端提示信息
        return new ResponseEntity<>(ResultBean.success("标签样式修改成功"), HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "对标签进行更新或设置定期更新",notes = "定期更新才需加beginTime和cycleTime字段")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mode", value = "0为对标签更新 1为对指定路由器的所有标签更新 2为定期更新", dataType = "int", paramType = "query")
    })
    @PutMapping("/tag/update")
    @Log("对指定属性的标签集合进行更新（变价 可批量）")
    public ResponseEntity<ResultBean> tagUpdate(@RequestBody @ApiParam("标签或路由器信息集合") RequestBean requestBean, @RequestParam Integer mode) {
        // 对指定的标签更新
        if(mode.equals(ModeConstant.DO_BY_TAG)) {
            ResponseBean responseBean = tagService.updateTags(requestBean);
            return new ResponseEntity<>(ResultBean.success(responseBean), HttpStatus.OK);
        }
        // 对路由器下的所有标签更新
        else if(mode.equals(ModeConstant.DO_BY_ROUTER)){
            ResponseBean responseBean = tagService.updateTagsByRouter(requestBean);
            return new ResponseEntity<>(ResultBean.success(responseBean), HttpStatus.OK);
        }
        else if(mode.equals(ModeConstant.DO_BY_CYCLE)){
            ResponseBean responseBean = tagService.updateTagsByCycle(requestBean);
            return new ResponseEntity<>(ResultBean.success(responseBean), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(ResultBean.error("参数有误 正确选择mode"), HttpStatus.BAD_REQUEST);
    }
    @ApiOperation(value = "对标签进行巡检或设置定期巡检",notes = "定期巡检才需加beginTime和cycleTime字段")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mode", value = " 0为对标签巡检 1为对指定路由器的所有标签巡检 2为定期巡检", dataType = "int", paramType = "query")
    })
    @PutMapping("/tag/scan")
    @Log("对单个标签进行巡检")
    public ResponseEntity<ResultBean> tagScan(@RequestBody @ApiParam("标签或路由器信息集合") RequestBean requestBean,@RequestParam Integer mode) {
        // 对指定的标签更新
        if(mode.equals(ModeConstant.DO_BY_TAG)) {
            ResponseBean responseBean = tagService.scanTags(requestBean);
            return new ResponseEntity<>(ResultBean.success(responseBean), HttpStatus.OK);
        }
        // 对路由器下的所有标签更新
        else if(mode.equals(ModeConstant.DO_BY_ROUTER)){
            ResponseBean responseBean = tagService.scanTagsByRouter(requestBean);
            return new ResponseEntity<>(ResultBean.success(responseBean), HttpStatus.OK);
        }
        else if(mode.equals(ModeConstant.DO_BY_CYCLE)){
            ResponseBean responseBean = tagService.scanTagsByCycle(requestBean);
            return new ResponseEntity<>(ResultBean.success(responseBean), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(ResultBean.error("参数有误 正确选择mode"), HttpStatus.BAD_REQUEST);
    }
    @ApiOperation("对标签进行禁用或启用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mode", value = " 0不禁用 1禁用", dataType = "int", paramType = "query")
    })
    @PutMapping("/tag/status")
    public ResponseEntity<ResultBean> changeStatus(@RequestBody @ApiParam("标签或路由器信息集合") RequestBean requestBean,@RequestParam  @Min(message = "data.page.min", value = 0)  @Max(message = "data.mode.max", value = 1) Integer mode){
        return new  ResponseEntity<>(new ResultBean(tagService.changeStatus(requestBean,mode)),HttpStatus.OK);
    }
    @ApiOperation("查看所有变价超时的标签信息")
    @GetMapping("/tags/overtime")
    public ResponseEntity<ResultBean> getOverTimeTags(){
        List<Tag> tagList = tagService.findBySql("SELECT * FROM tags WHERE  execTime IS NULL OR  execTime = 0",Tag.class);
        ResponseEntity<ResultBean> result = null;
        if ((result = ResponseUtil.testListSize("没有相应的标签或商品 请重新选择", tagList)) != null) return result;
        List<TagVo> tagVos = CopyUtil.copyTag(tagList);
        return new  ResponseEntity<>(new ResultBean(tagVos),HttpStatus.OK);
    }
    @ApiOperation("对指定的标签闪灯或结束闪灯")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mode", value = " 0结束闪灯 1闪灯", dataType = "int", paramType = "query")
    })
    @PutMapping("/tag/light")
    public ResponseEntity<ResultBean> changeLightStatus(@RequestBody @ApiParam("标签或路由器信息集合") RequestBean requestBean,@RequestParam  @Min(message = "data.page.min", value = 0)  @Max(message = "data.mode.max", value = 1) Integer mode){
        return new  ResponseEntity<>(new ResultBean(tagService.changeLightStatus(requestBean,mode)),HttpStatus.OK);
    }
}
