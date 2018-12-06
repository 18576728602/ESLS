package com.datagroup.ESLS.service;

import com.datagroup.ESLS.common.request.RequestBean;
import com.datagroup.ESLS.common.response.ResponseBean;
import com.datagroup.ESLS.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagService extends Service{
    List<Tag> findAll();
    List<Tag> findAll(Integer page, Integer count);
    // 添加标签
    Tag saveOne(Tag tag);
    // 获取指定ID的标签
    Optional<Tag> findById(Long id);
    // 删除指定ID的标签
    boolean deleteById(Long id);
    // 对标签进行批量更新
    ResponseBean updateTags(RequestBean requestBean);
    // 对路由器下的标签进行批量更新
    ResponseBean updateTagsByRouter(RequestBean requestBean);
    ResponseBean updateTagsByCycle(RequestBean requestBean);
    // 对标签进行批量巡检
    ResponseBean scanTags(RequestBean requestBean);
    // 对路由器下的标签进行批量巡检
    ResponseBean scanTagsByRouter(RequestBean requestBean);
    ResponseBean scanTagsByCycle(RequestBean requestBean);
    // 禁用或启用指定标签
    ResponseBean changeStatus(RequestBean requestBean,Integer mode);
    // 闪灯或结束闪灯
    ResponseBean changeLightStatus(RequestBean requestBean,Integer mode);
}
