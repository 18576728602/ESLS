package com.datagroup.ESLS.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Api(description = "首页")
public class IndexController {
    @GetMapping("/")
    @ApiOperation("www.localhost进入主页")
    public String toHome(){
        return "upload";
    }
}
