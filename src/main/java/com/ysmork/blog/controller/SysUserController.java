package com.ysmork.blog.controller;


import com.ysmork.blog.entity.SysUser;
import com.ysmork.blog.entity.param.UserSelectParam;
import com.ysmork.blog.framework.web.entity.Result;
import com.ysmork.blog.service.SysUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author YangShun
 * @since 2020-09-22
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    @GetMapping("findAll")
    public Result findAll(UserSelectParam param){
        List<SysUser> all = sysUserService.findAll (param);
        return Result.success (all);
    }

}

