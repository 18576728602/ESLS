package com.datagroup.ESLS.controller;

import com.datagroup.ESLS.common.constant.FileConstant;
import com.datagroup.ESLS.common.response.ResponseBean;
import com.datagroup.ESLS.common.response.ResultBean;
import com.datagroup.ESLS.utils.FileUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.UUID;

@RestController
@Api(description = "文件上传API")
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
public class UploadController {
    @Value("${project.profile}")
    private String UPLOAD_FOLDER;

    @ApiOperation("上传单个文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mode", value = "0为商品基本信息 1商品变价信息 2为价签信息 3为路由器信息 4为商品特征图片", dataType = "mode", paramType = "query", required = true)
    })
    @PostMapping("/uploadFile")
    public ResponseEntity<ResultBean> singleFileUpload(@ApiParam(value = "文件信息", required = true) @RequestParam("file") MultipartFile file, @RequestParam Integer mode) {
        if (Objects.isNull(file) || file.isEmpty()) {
            log.error("文件为空");
            return new ResponseEntity<>(ResultBean.error("文件为空，请重新上传"), HttpStatus.NOT_ACCEPTABLE);
        }
        try {
            String fileName = file.getOriginalFilename()+ UUID.randomUUID().toString();
            String filePath = UPLOAD_FOLDER + FileConstant.ModeMap.get(mode);
            if (FileUtil.judeFileExists(filePath, fileName))
                return new ResponseEntity<>(ResultBean.error("文件已经存在，请重新上传"), HttpStatus.NOT_ACCEPTABLE);
            FileUtil.uploadFile(file.getBytes(), filePath, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("文件写入成功...");
        return new ResponseEntity<>(ResultBean.success("文件上传成功"), HttpStatus.OK);
    }

    @ApiOperation("上传多个文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mode", value = "0为商品基本信息 1商品变价信息 2为价签信息 3为路由器信息 4为商品特征图片", dataType = "mode", paramType = "query", required = true)
    })
    @PostMapping("/uploadFiles")
    public ResponseEntity<ResultBean> multiFileUpload(@ApiParam(value = "文件信息", required = true) @RequestParam("file") MultipartFile[] file, @RequestParam Integer mode) {
        int successNumber = 0;
        for (int i = 0; i < file.length; i++) {
            if (file[i] != null) {
                ResponseEntity<ResultBean> result = singleFileUpload(file[i], mode);
               if( result.getBody().getCode()==1 )
                   successNumber++;
            }
        }
        return new ResponseEntity<>(ResultBean.success(new ResponseBean(file.length,successNumber)), HttpStatus.OK);
    }
}
