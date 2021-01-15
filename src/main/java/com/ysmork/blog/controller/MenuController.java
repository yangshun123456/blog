package com.ysmork.blog.controller;

import com.ysmork.blog.common.annonation.Lock;
import com.ysmork.blog.common.model.DictDataConstants;
import com.ysmork.blog.common.util.SecurityUtils;
import com.ysmork.blog.entity.SysMenu;
import com.ysmork.blog.entity.param.MenuParam;
import com.ysmork.blog.framework.web.entity.Result;
import com.ysmork.blog.service.SysMenuService;
import org.springframework.transaction.annotation.Transactional;
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
     *
     * @param param 参数
     * @return 菜单树状列表
     */
    @GetMapping("findAll")
    public Result findAll(MenuParam param) {
        return Result.success (sysMenuService.findAll (param));
    }

    /**
     * 获取用户权限
     *
     * @param menuId
     * @return 用户权限
     */
    @GetMapping("getPermission")
    public Result getPermission(Integer menuId) {
        Integer userId = SecurityUtils.getUserId ();
        List<SysMenu> permission = sysMenuService.getPermission (menuId, userId);
        return Result.success (permission);
    }

    /**
     * 新增/修改
     *
     * @param sysMenu
     * @return null
     */
    @PostMapping("save")
    @Lock(className = SysMenu.class, onlyName = "menuName", idName = "menuId")
    @Transactional(rollbackFor = {Throwable.class})
    public Result save(@RequestBody SysMenu sysMenu) {
        if (sysMenu.getStatus ().equals (DictDataConstants.NORMAL_STATUS)) {
            sysMenuService.closeDown (sysMenu.getMenuId (), DictDataConstants.NORMAL_TYPE);
        } else if (sysMenu.getStatus ().equals (DictDataConstants.UNUSED_STATUS)) {
            sysMenuService.closeDown (sysMenu.getMenuId (), DictDataConstants.UNUSED_TYPE);
        }
        sysMenuService.saveOrUpdate (sysMenu);
        return Result.success ();
    }

    /**
     * 新增/修改
     *
     * @param id 菜单id
     * @return null
     */
    @GetMapping("delete")
    @Transactional(rollbackFor = {Throwable.class})
    public Result delete(Integer id) {
        SysMenu sysMenu = new SysMenu ();
        sysMenu.setMenuId (id);
        sysMenu.setStatus (DictDataConstants.DELETE_STATUS);
        sysMenuService.closeDown (id, DictDataConstants.DELETE_TYPE);
        sysMenuService.updateById (sysMenu);
        return Result.success ();
    }

    /**
     * 详情
     *
     * @param id 菜单id
     * @return 用详情
     */
    @GetMapping("detail")
    public Result detail(Integer id) {
        return Result.success (sysMenuService.getById (id));
    }

    /**
     * 启用/禁用
     *
     * @param id 菜单id
     * @return null
     */
    @GetMapping("status")
    @Transactional(rollbackFor = {Throwable.class})
    public Result status(Integer id) {
        SysMenu sysMenu = sysMenuService.getById (id);
        if (sysMenu.getStatus ().equals (DictDataConstants.NORMAL_STATUS)) {
            sysMenu.setStatus (DictDataConstants.UNUSED_STATUS);
            sysMenuService.closeDown (id, DictDataConstants.UNUSED_TYPE);
        } else if (sysMenu.getStatus ().equals (DictDataConstants.UNUSED_STATUS)) {
            sysMenu.setStatus (DictDataConstants.NORMAL_STATUS);
            sysMenuService.closeDown (id, DictDataConstants.NORMAL_TYPE);
        }
        sysMenuService.updateById (sysMenu);
        return Result.success (sysMenu.getStatus ());
    }

}
