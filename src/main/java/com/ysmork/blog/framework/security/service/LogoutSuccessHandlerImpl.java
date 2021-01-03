package com.ysmork.blog.framework.security.service;

import com.alibaba.fastjson.JSON;
import com.ysmork.blog.common.model.ResultConstants;
import com.ysmork.blog.common.util.ServletUtils;
import com.ysmork.blog.common.util.StringUtils;
import com.ysmork.blog.framework.security.LoginUser;
import com.ysmork.blog.framework.web.entity.Result;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 退出成功处理类
 * </p>
 *
 * @author YangShun
 * @since 2021/1/3
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Resource
    private TokenService tokenService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser)) {
            // 删除用户缓存记录
            tokenService.delLoginUser(loginUser.getToken());
        }
        ServletUtils.renderString(response, JSON.toJSONString(Result.success(ResultConstants.REQUEST_SUCCESS.getCode())));
    }
}
