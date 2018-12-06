package com.datagroup.ESLS.controller;

import com.datagroup.ESLS.common.response.ResultBean;
import com.datagroup.ESLS.dao.AdminDao;
import com.datagroup.ESLS.entity.Admin;
import com.datagroup.ESLS.redis.RedisUtil;
import com.datagroup.ESLS.utils.SpringContextUtil;
import com.datagroup.ESLS.utils.TokenUtil;
import com.datagroup.ESLS.utils.ValidatorUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@Api(description = "用户管理API")
//实现跨域注解
//origin="*"代表所有域名都可访问
//maxAge飞行前响应的缓存持续时间的最大年龄，简单来说就是Cookie的有效期 单位为秒
//若maxAge是负数,则代表为临时Cookie,不会被持久化,Cookie信息保存在浏览器内存中,浏览器关闭Cookie就消失
@CrossOrigin(origins = "*", maxAge = 3600)
public class LoginController {
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation(value = "amdin登录")
    @PostMapping("/admin/login")
    public ResponseEntity<ResultBean> login(@Valid @RequestBody @ApiParam(value = "用户信息json格式") Admin adminEntity, BindingResult error) {
        if(error.hasErrors())
            return new ResponseEntity<>(ResultBean.error(ValidatorUtil.getError(error)), HttpStatus.BAD_REQUEST);
        Admin admin = adminDao.findByUsernameAndPassword(adminEntity.getUsername(), adminEntity.getPassword());
        if (admin != null) {
            HttpHeaders responseHeaders = new HttpHeaders();
           // String token = UUID.randomUUID().toString() + System.currentTimeMillis();
            String token = TokenUtil.getToken(admin);
            redisUtil.sentinelSet(token, admin, SpringContextUtil.getAliveTime());
            responseHeaders.set("X-ESLS-TOKEN", token);
            return new ResponseEntity<>(ResultBean.success(admin), responseHeaders, HttpStatus.OK);
        } else {
            //登陆失败
            return new ResponseEntity<>(ResultBean.error("用户名或密码错误"), HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
