package com.ysmork.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ysmork.blog.common.model.Constants;
import com.ysmork.blog.common.model.DictDataConstants;
import com.ysmork.blog.dao.SysMenuMapper;
import com.ysmork.blog.entity.SysMenu;
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
    public List<SysMenu> getPermission(Integer userId) {
        List<SysMenu> permission = sysMenuMapper.getPermission(userId);
        List<SysMenu> treeMenu = getTreeMenu(permission, Constants.MENU_TOP_ID);
        return treeMenu;
    }

    @Override
    public List<SysMenu> findAll(MenuParam param) {
        List<SysMenu> permission = sysMenuMapper.selectList(new QueryWrapper<SysMenu>()
                .ne("status", DictDataConstants.DELETE_STATUS)
                .likeRight(StringUtils.isNotBlank(param.getMenuName()),"menu_name",param.getMenuName())
                .eq(param.getStatus() != null,"status",param.getStatus()));
        return getTreeMenu(permission, Constants.MENU_TOP_ID);
    }

    /**
     * 将菜单权限转换成树结构
     * @param menus 菜单权限列表
     * @param parentId 父级id
     * @return 树状结构
     */
    private List<SysMenu> getTreeMenu(List<SysMenu> menus,Integer parentId){
        List<SysMenu> dataList = new ArrayList<>();
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
