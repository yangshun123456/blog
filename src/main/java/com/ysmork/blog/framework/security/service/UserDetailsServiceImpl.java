package com.ysmork.blog.framework.security.service;

import com.ysmork.blog.common.model.Constants;
import com.ysmork.blog.dao.SysMenuMapper;
import com.ysmork.blog.dao.SysRoleMapper;
import com.ysmork.blog.entity.SysRole;
import com.ysmork.blog.entity.SysUser;
import com.ysmork.blog.framework.security.LoginUser;
import com.ysmork.blog.service.SysUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * @author yangshun
 * @version 1.0
 * @program: blog
 * @description:
 * @date 2020/9/22 22:12
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.selectByUserName (username);
        if (sysUser == null) {
            throw new UsernameNotFoundException("该用户不存在！");
        }
        return createLoginUser (sysUser);
    }

    private UserDetails createLoginUser(SysUser sysUser){
        SysRole sysRole = sysRoleMapper.selectByUserId(sysUser.getId());
        Set<String> userButtonPerms = new HashSet<> ();
        if(sysRole != null){
            userButtonPerms = sysMenuMapper.getUserButtonPerms(sysUser.getId());
            if(Constants.ADMIN_KEY.equals(sysRole.getRoleKey())){
                userButtonPerms.add(Constants.ALL_PERMISSION);
            }
        }
        return new LoginUser (sysUser,sysRole, userButtonPerms);
    }
}
