package com.ysmork.blog.framework.security.service;

import com.ysmork.blog.dao.SysMenuMapper;
import com.ysmork.blog.entity.SysUser;
import com.ysmork.blog.framework.security.LoginUser;
import com.ysmork.blog.service.SysUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author yangshun
 * @version 1.0
 * @program: blog
 * @description:
 * @date 2020/9/22 22:12
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.selectByUserName (username);
        if (sysUser == null) {
            throw new UsernameNotFoundException("该用户不存在！");
        }
        return createLoginUser (sysUser);
    }

    private UserDetails createLoginUser(SysUser sysUser){
        Set<String> perms = sysMenuMapper.selectPerms (sysUser.getId ());
        return new LoginUser (sysUser,perms);
    }
}
