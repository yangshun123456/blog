package com.ysmork.blog.controller;

import com.ysmork.blog.common.util.FastDfsUtil;
import com.ysmork.blog.framework.web.entity.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @program: blog
 * @description: 公用服务
 * @author: YangShun
 * @create: 2020-12-14 15:23
 **/
@RestController
@RequestMapping("public")
public class PublicController {

    @PostMapping("upload")
    public Result upLoadFile(MultipartFile file){
        Map<String, String> upload = FastDfsUtil.upload (file);
        return Result.success (upload);
    }
}
