package com.ysmork.blog.controller;


import com.ysmork.blog.framework.web.entity.Result;
import com.ysmork.blog.service.SysRoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  角色管理
 * </p>
 *
 * @author YangShun
 * @since 2020-09-22
 */
@RestController
@RequestMapping("/sysRole")
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;

    public Result findAll(){
        return null;
    }
}

