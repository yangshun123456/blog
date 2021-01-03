package com.ysmork.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ysmork.blog.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author YangShun
 * @since 2020-12-27
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 获取用户权限
     * @param userId 用户ID
     * @return 用户权限
     */
    List<SysMenu> getPermission(@Param("userId") Integer userId);

    /**
     * 获取用户的全部按钮权限
     * @param userId 用户id
     * @return 用户权限
     */
    Set<String> getUserButtonPerms(@Param("userId") Integer userId);
}
