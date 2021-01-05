package com.ysmork.blog.common.util;

import com.ysmork.blog.entity.SysMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色管理
 * </p>
 *
 * @author YangShun
 * @since 2021/1/5
 */
public class TreeUtils {
    /**
     * 将菜单权限转换成树结构
     * @param menus 菜单权限列表
     * @param parentId 父级id
     * @return 树状结构
     */
    public static List<SysMenu> getTreeMenu(List<SysMenu> menus, Integer parentId){
        List<SysMenu> dataList = new ArrayList<> ();
        for (SysMenu menu : menus) {
            if(menu.getParentId().equals(parentId)){
                dataList.add(menu);
                List<SysMenu> treeMenu = getTreeMenu(menus, menu.getMenuId());
                menu.setChildren(treeMenu);
            }
        }
        return dataList;
    }
}
