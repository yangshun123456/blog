package com.ysmork.blog.controller;

import com.ysmork.blog.entity.param.UserParam;
import com.ysmork.blog.framework.web.entity.Result;
import com.ysmork.blog.service.LoginService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author yangshun
 * @version 1.0
 * @program: blog
 * @description: 登录接口
 * @date 2020/10/4 16:52
 */
@RestController
@RequestMapping("user")
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping("login")
    public Result login(@RequestBody UserParam user) {
        return loginService.login (user);
    }

    @GetMapping("captcha")
    public Result captcha(String key) {
        return loginService.captcha (key);
    }

    @GetMapping("getUserDetail")
    public Result getUserDetail(HttpServletRequest request) {
        return loginService.getUserDetail (request);
    }

    @GetMapping("logout")
    public Result logout(String key) {
        return Result.success();
    }


}
