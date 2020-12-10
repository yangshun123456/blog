package com.ysmork.blog.controller;

import com.ysmork.blog.common.model.server.Server;
import com.ysmork.blog.common.model.server.ServerVO;
import com.ysmork.blog.common.util.ServerUtil;
import com.ysmork.blog.framework.web.entity.Result;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: blog
 * @description: 系统信息
 * @author: YangShun
 * @create: 2020-12-10 15:56
 **/
@RestController
@RequestMapping("system")
public class SystemController {

    @GetMapping("detail")
    @SneakyThrows
    public Result getSystemDetail(){
        Server server = new Server ();
        server.copyTo();
        ServerVO serverVO = ServerUtil.wrapServerVO(server);
        return Result.success (ServerUtil.wrapServerDict(serverVO));
    }
}
