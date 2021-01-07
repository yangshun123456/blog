package com.ysmork.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ysmork.blog.common.annonation.Lock;
import com.ysmork.blog.common.model.DictDataConstants;
import com.ysmork.blog.common.util.PageUtils;
import com.ysmork.blog.common.util.StringUtils;
import com.ysmork.blog.entity.SysRole;
import com.ysmork.blog.entity.param.MenuParam;
import com.ysmork.blog.entity.param.RoleParam;
import com.ysmork.blog.framework.web.entity.Result;
import com.ysmork.blog.service.SysMenuService;
import com.ysmork.blog.service.SysRoleMenuService;
import com.ysmork.blog.service.SysRoleService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色管理
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

    @Resource
    private SysMenuService sysMenuService;

    @GetMapping("findAll")
    public Result findAll(RoleParam roleParam) {
        PageUtils.startPage ();
        List<SysRole> list = sysRoleService.list (new QueryWrapper<SysRole> ()
                .ne ("status", DictDataConstants.DELETE_STATUS)
                .eq (StringUtils.isNotBlank (roleParam.getRoleName ()), "role_name", roleParam.getRoleName ())
                .eq (roleParam.getStatus () != null, "status", roleParam.getStatus ())
                .gt (roleParam.getStartTime () != null, "create_time", roleParam.getStartTime ())
                .lt (roleParam.getEndTime () != null, "create_time", roleParam.getEndTime ()));
        return PageUtils.getData (list);
    }

    @GetMapping("delete")
    public Result delete(Integer id){
        sysRoleService.delete (id);
        return Result.success ();
    }

    @GetMapping("detail")
    public Result detail(Integer id){
        return Result.success (sysRoleService.detail (id));
    }

    /**
     * 新增/修改 角色
     * @param sysRole
     * @return
     */
    @PostMapping("save")
    @Lock (className = SysRole.class, onlyName="roleName")
    @Transactional(rollbackFor = Throwable.class)
    public Result save(@RequestBody SysRole sysRole){
        sysRoleService.saveOrUpdate (sysRole);
        sysRoleService.savePermission (sysRole);
        return Result.success ();
    }

    /**
     * 批量删除
     * @param map
     * @return
     */
    @PostMapping("deleteMany")
    public Result deleteMany(@RequestBody Map<String,List<Integer>> map){
        List<Integer> ids = map.get ("ids");
        for (Integer id : ids) {
            this.delete (id);
        }
        return Result.success ();
    }

    /**
     * 查询所有菜单
     * @return 菜单树状列表
     */
    @GetMapping("findAllMenu")
    public Result findAllMenu(){
        MenuParam param = new MenuParam ();
        param.setStatus (DictDataConstants.NORMAL_STATUS);
        return Result.success(sysMenuService.findAll(param));
    }


}

