package com.ysmork.blog.controller;

import com.ysmork.blog.common.util.SecurityUtils;
import com.ysmork.blog.entity.param.MenuParam;
import com.ysmork.blog.framework.web.entity.Result;
import com.ysmork.blog.service.SysMenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 菜单管理
 * </p>
 *
 * @author YangShun
 * @since 2020/12/23
 */
@RestController
@RequestMapping("sysMenu")
public class MenuController {

    @Resource
    private SysMenuService sysMenuService;

    /**
     * 查询所有菜单
     * @param param 参数
     * @return 菜单树状列表
     */
    @GetMapping("findAll")
    public Result findAll(MenuParam param){
        return Result.success(sysMenuService.findAll(param));
    }

    /**
     * 获取用户权限
     * @return 用户权限
     */
    @GetMapping("getPermission")
    public Result getPermission(){
        Integer userId = SecurityUtils.getUserId();
        return Result.success(sysMenuService.getPermission(userId));
    }

}
