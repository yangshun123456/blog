package com.ysmork.blog.service;

import com.ysmork.blog.entity.param.UserParam;
import com.ysmork.blog.framework.web.entity.Result;

import javax.servlet.http.HttpServletRequest;

public interface LoginService {
    Result login(UserParam user);

    Result captcha(String key);

    Result getUserDetail(HttpServletRequest request);
}
