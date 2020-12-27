package com.ysmork.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ysmork.blog.entity.SysMenu;
import com.ysmork.blog.entity.param.MenuParam;

import java.util.List;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author YangShun
 * @since 2020-12-27
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 获取用户权限
     * @param userId 用户ID
     * @return 用户权限
     */
    List<SysMenu> getPermission(Integer userId);

    /**
     * 获取用户权限
     * @param param 查询条件
     * @return 用户权限
     */
    List<SysMenu> findAll(MenuParam param);


}
