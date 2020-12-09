package com.ysmork.blog.framework.security.handle;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ysmork.blog.common.model.ResultConstants;
import com.ysmork.blog.common.util.ServletUtils;
import com.ysmork.blog.common.util.SystemUtils;
import com.ysmork.blog.framework.web.entity.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author yangshun
 * @version 1.0
 * @program: blog
 * @description: 登录失败处理类
 * @date 2020/10/25 21:29
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {
    @Resource
    private SystemUtils systemUtils;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        int code = ResultConstants.TOKEN_TIME_OUT.getCode();
        String msg = StringUtils.format("请求访问：{}，认证失败，请登录", request.getRequestURI());
        ServletUtils.renderString(response, JSON.toJSONString(Result.error(code, msg)));
    }
}
