package com.ysmork.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ysmork.blog.entity.SysRole;
import org.apache.ibatis.annotations.Param;

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
}
