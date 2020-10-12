package com.ysmork.blog.framework.security.filter;

import com.ysmork.blog.common.model.Constants;
import com.ysmork.blog.common.util.RedisCache;
import com.ysmork.blog.common.util.StringUtils;
import com.ysmork.blog.dao.SysUserMapper;
import com.ysmork.blog.entity.SysUser;
import com.ysmork.blog.framework.security.LoginUser;
import com.ysmork.blog.framework.security.service.TokenService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yangshun
 * @version 1.0
 * @program: blog
 * @description: JWT鉴权
 * 验证成功当然就是进行鉴权了
 * 登录成功之后走此类进行鉴权操作
 * @date 2020/10/6 15:59
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    @Resource
    RedisTemplate<String, String> redisTemplate;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private TokenService tokenService;

    @Resource
    private RedisCache redisCache;

    @Resource
    private SysUserMapper sysUserMapper;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        LoginUser loginUser = tokenService.getLoginUser (request);

        if (StringUtils.isNotNull(loginUser) && StringUtils.isNull(com.rz.iot.bpo.common.utils.SecurityUtils.getAuthentication()))
        {
            String flag = redisCache.getCacheObject(Constants.BPO_TOKEN_REFRESH_FLAG + loginUser.getToken());
            if(!StringUtils.isEmpty(flag)) {
                SysUser sysUser = sysUserMapper.selectById (loginUser.getSysUser ().getId ());
//                permissionService.getMenuPermissionForFront(sysUser);
//                loginUser.setUser(sysUser);
//                loginUser.setPermissions(permissionService.getMenuPermission(sysUser));
                tokenService.setLoginUser(loginUser);
                redisCache.deleteObject(Constants.BPO_TOKEN_REFRESH_FLAG + loginUser.getToken());
            }
            tokenService.verifyToken(loginUser);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        chain.doFilter(request, response);
    }

}
