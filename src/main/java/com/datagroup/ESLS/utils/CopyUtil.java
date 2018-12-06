package com.datagroup.ESLS.utils;

import com.datagroup.ESLS.dto.GoodVo;
import com.datagroup.ESLS.dto.StyleVo;
import com.datagroup.ESLS.dto.TagVo;
import com.datagroup.ESLS.entity.Good;
import com.datagroup.ESLS.entity.Style;
import com.datagroup.ESLS.entity.Tag;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CopyUtil {

    public static List<TagVo> copyTag(List<Tag> content) {
        List<TagVo> resultList = new ArrayList<>();
        content.forEach(
                item -> {
                    TagVo tagVo = new TagVo();
                    BeanUtils.copyProperties(item, tagVo);
                    tagVo.setGoodId(item.getGood() != null ? item.getGood().getId() : 0);
                    tagVo.setRouterId(item.getRouter() != null ? item.getRouter().getId() : 0);
                    tagVo.setStyleId(item.getStyle() != null ? item.getStyle().getId() : 0);
                    resultList.add(tagVo);
                }
        );
        return resultList;
    }

    public static List<GoodVo> copyGood(List<Good> content) {
        List<GoodVo> resultList = new ArrayList<>();
        content.forEach(
                item -> {
                    boolean flag = true;
                    if (item == null) flag = false;
                    if (flag) {
                        GoodVo goodVo = new GoodVo();
                        BeanUtils.copyProperties(item, goodVo);
                        List<Long> tagIdList = goodVo.getTagIdList();
                        Collection<Tag> tags = item.getTags();
                        tags.forEach(itemTag -> {
                            if (itemTag != null)
                                tagIdList.add(itemTag.getId());
                        });
                        resultList.add(goodVo);
                    }
                }
        );
        return resultList;
    }

    public static List<StyleVo> copyStyle(List<Style> content) {
        List<StyleVo> resultList = new ArrayList<>();
        content.forEach(
                item -> {
                    boolean flag = true;
                    if (item == null) flag = false;
                    if (flag) {
                        StyleVo styleVo = new StyleVo();
                        BeanUtils.copyProperties(item,styleVo);
                        List<Long> tagIdList = styleVo.getTagIdList();
                        Collection<Tag> tags = item.getTags();
                        tags.forEach(itemTag -> {
                            if (itemTag != null)
                                tagIdList.add(itemTag.getId());
                        });
                        resultList.add(styleVo);
                    }
                }
        );
        return resultList;
    }
}