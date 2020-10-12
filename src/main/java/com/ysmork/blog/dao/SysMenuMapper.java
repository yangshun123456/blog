package com.ysmork.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ysmork.blog.entity.SysMenu;

import java.util.Set;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author YangShun
 * @since 2020-09-22
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    /**
     * 根据userId查询菜单权限
     * @param userId
     * @return
     */
    Set<String> selectPerms(Integer userId);
}
