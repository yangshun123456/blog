package com.ysmork.blog.controller;

import com.ysmork.blog.common.util.SecurityUtils;
import com.ysmork.blog.entity.SysMenu;
import com.ysmork.blog.entity.param.MenuParam;
import com.ysmork.blog.framework.web.entity.Result;
import com.ysmork.blog.service.SysMenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
     * @param menuId
     * @return 用户权限
     */
    @GetMapping("getPermission")
    public Result getPermission(Integer menuId){
        Integer userId = SecurityUtils.getUserId();
        List<SysMenu> permission = sysMenuService.getPermission (menuId,userId);
        return Result.success(permission);
    }

    /**
     * 新增/修改
     * @param sysMenu
     * @return 用户权限
     */
    @PostMapping("save")
    public Result save(@RequestBody SysMenu sysMenu){
        sysMenuService.saveOrUpdate (sysMenu);
        sysMenuService.closeDown(sysMenu.getMenuId ());
        return Result.success();
    }


}
