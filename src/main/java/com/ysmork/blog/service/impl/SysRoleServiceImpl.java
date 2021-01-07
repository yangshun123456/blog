package com.ysmork.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ysmork.blog.common.model.Constants;
import com.ysmork.blog.common.model.DictDataConstants;
import com.ysmork.blog.common.util.StringUtils;
import com.ysmork.blog.common.util.TreeUtils;
import com.ysmork.blog.dao.SysMenuMapper;
import com.ysmork.blog.dao.SysRoleMapper;
import com.ysmork.blog.dao.SysRoleMenuMapper;
import com.ysmork.blog.entity.SysMenu;
import com.ysmork.blog.entity.SysRole;
import com.ysmork.blog.entity.SysRoleMenu;
import com.ysmork.blog.service.SysRoleMenuService;
import com.ysmork.blog.service.SysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author YangShun
 * @since 2020-09-22
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Resource
    private SysRoleMenuService sysRoleMenuService;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void delete(Integer id) {
        //删除关联关系
        sysRoleMenuMapper.delete (new QueryWrapper<SysRoleMenu> ()
                .eq ("role_id",id));
        //删除角色信息
        SysRole sysRole = new SysRole ();
        sysRole.setId (id);
        sysRole.setStatus (DictDataConstants.DELETE_STATUS);
        sysRoleMapper.updateById (sysRole);
    }

    @Override
    public SysRole detail(Integer id) {
        //获取角色信息
        SysRole sysRole = sysRoleMapper.selectById (id);
        //获取菜单权限信息
        List<SysMenu> permissions = sysMenuMapper.getPermissionByRoleId (id);
        sysRole.setSysMenus (TreeUtils.getTreeMenu (permissions, Constants.MENU_TOP_ID));
        return sysRole;
    }

    @Override
    public void savePermission(SysRole sysRole) {
        //插入/修改菜单权限
        List<SysMenu> sysMenus = sysRole.getSysMenus ();
        //删除原有的
        sysRoleMenuMapper.delete (new QueryWrapper<SysRoleMenu> ()
                .eq ("role_id",sysRole.getId ()));
        //添加选择的
        Set<SysRoleMenu> menus = new HashSet<> ();
//        getRelation(sysMenus,lists,sysRole.getId ());
        for (SysMenu sysMenu : sysRole.getSysMenus ()) {
            SysRoleMenu sysUserRoleMenu = new SysRoleMenu ();
            sysUserRoleMenu.setMenuId (sysMenu.getMenuId ());
            sysUserRoleMenu.setRoleId (sysRole.getId ());
            menus.add (sysUserRoleMenu);
        }
        sysRoleMenuService.saveBatch (menus);
    }



    /**
     * 将树状菜单转封装成菜单-角色关系
     * @param sysMenus
     * @param lists
     * @param roleId
     */
    private void getRelation(List<SysMenu> sysMenus ,Set<SysRoleMenu> lists,Integer roleId){
        if(StringUtils.isEmpty (sysMenus)){
            return;
        }
        for (SysMenu sysMenu : sysMenus) {
            SysRoleMenu sysUserRoleMenu = new SysRoleMenu ();
            sysUserRoleMenu.setMenuId (sysMenu.getMenuId ());
            sysUserRoleMenu.setRoleId (roleId);
            lists.add (sysUserRoleMenu);
            getRelation (sysMenu.getChildren (),lists,roleId);
        }
    }
}
