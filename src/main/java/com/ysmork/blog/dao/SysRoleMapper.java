package com.ysmork.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ysmork.blog.entity.SysRole;
import com.ysmork.blog.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author YangShun
 * @since 2020-09-22
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * 通过用户id获取角色
     * @param userId
     * @return
     */
    SysRole selectByUserId(@Param("userId") Integer userId);

    /**
     * 通过角色id获取已分配用户
     * @param roleId
     * @return
     */
    List<SysUser> selectUseUsersByRoleId(Integer roleId);

    /**
     * 通过角色id获取未分配用户
     * @param roleId
     * @return
     */
    List<SysUser> selectUsersByRoleId(Integer roleId);
}
