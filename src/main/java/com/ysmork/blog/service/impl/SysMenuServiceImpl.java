package com.ysmork.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ysmork.blog.common.model.Constants;
import com.ysmork.blog.common.model.DictDataConstants;
import com.ysmork.blog.common.util.SecurityUtils;
import com.ysmork.blog.common.util.StringUtils;
import com.ysmork.blog.common.util.TreeUtils;
import com.ysmork.blog.dao.SysMenuMapper;
import com.ysmork.blog.entity.SysMenu;
import com.ysmork.blog.entity.SysRole;
import com.ysmork.blog.entity.param.MenuParam;
import com.ysmork.blog.service.SysMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author YangShun
 * @since 2020-12-27
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> getPermission(Integer menuId,Integer userId) {
        SysRole sysRole = SecurityUtils.getLoginUser().getSysRole();
        List<SysMenu> permission = null;
        if(Constants.ADMIN_KEY.equals(sysRole.getRoleKey())) {
            permission = sysMenuMapper.selectList(new QueryWrapper<SysMenu>()
                    .ne("status", DictDataConstants.DELETE_STATUS)
                    .orderByAsc("parent_id","order_num"));
        }else{
            permission = sysMenuMapper.getPermission(userId);
        }
        List<SysMenu> treeMenu = TreeUtils.getTreeMenu (permission, Constants.MENU_TOP_ID);
        //传入菜单id为0的为无父级，过滤
        if(menuId != null && !menuId.equals (0)) {
            SysMenu parentTree = getParentTree (treeMenu, menuId);
            treeMenu.clear ();
            treeMenu.add (parentTree);
        }
        return treeMenu;
    }

    @Override
    public List<SysMenu> findAll(MenuParam param) {
        List<SysMenu> permission = sysMenuMapper.selectList(new QueryWrapper<SysMenu>()
                .ne("status", DictDataConstants.DELETE_STATUS)
                .likeRight(StringUtils.isNotBlank(param.getMenuName()),"menu_name",param.getMenuName())
                .eq(param.getStatus() != null,"status",param.getStatus())
                .orderByAsc("parent_id","order_num"));
        return TreeUtils.getTreeMenu(permission, Constants.MENU_TOP_ID);
    }

    @Override
    public void closeDown(Integer menuId,Integer type) {
        List<SysMenu> menus = findAll (new MenuParam ());
        for (SysMenu menu : menus) {
            if(menu.getMenuId ().equals (menuId)){
                //级联设置下属菜单为禁用/启用
                closeDownMenu(menu,type);
            }
        }
    }

    /**
     * 获取此父级ID的树结构的菜单权限
     * @param menus 树结构菜单权限列表
     * @param parentId 父级id
     * @return 树状结构
     */
    private SysMenu getParentTree(List<SysMenu> menus, Integer parentId){
        if(StringUtils.isEmpty (menus)){
            return new SysMenu ();
        }
        for (SysMenu menu : menus) {
            if(menu.getMenuId ().equals (parentId)){
                return menu;
            }else {
                SysMenu parentTree = getParentTree (menu.getChildren (), parentId);
                if(parentTree != null && parentTree.getMenuId () != null){
                    return parentTree;
                }
            }
        }
        return new SysMenu ();
    }

    private void closeDownMenu(SysMenu sysMenu,Integer type){
        if(StringUtils.isEmpty (sysMenu.getChildren ())){
            return;
        }
        for (SysMenu child : sysMenu.getChildren ()) {
            if(type.equals(DictDataConstants.UNUSED_TYPE)) {
                child.setStatus(DictDataConstants.UNUSED_STATUS);
            }else if(type.equals(DictDataConstants.DELETE_TYPE)){
                child.setStatus(DictDataConstants.DELETE_STATUS);
            }else if(type.equals(DictDataConstants.NORMAL_TYPE)){
                child.setStatus(DictDataConstants.NORMAL_STATUS);
            }
            closeDownMenu(child,type);
        }
        this.updateBatchById (sysMenu.getChildren ());
    }
}
