package com.ysmork.blog.framework.security;

import com.ysmork.blog.entity.SysUser;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * @author yangshun
 * @version 1.0
 * @program: blog
 * @description: 登录用户身份权限
 * @date 2020/9/22 22:10
 */
@Data
public class LoginUser implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 用户唯一标识
     */
    private String token;

    /**
     * 登陆时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 权限列表
     */
    private Set<String> permissions;

    /**
     * 用户列表
     */
    private SysUser sysUser;

    /**
     * 验证码
     */
    private String captcha;


    public LoginUser(SysUser sysUser,Set<String> permissions){
        this.sysUser = sysUser;
        this.permissions = permissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return sysUser.getPassword ();
    }

    @Override
    public String getUsername() {
        return sysUser.getUsername ();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
