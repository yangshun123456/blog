package com.ysmork.blog.service;

import com.ysmork.blog.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ysmork.blog.entity.SysUser;
import com.ysmork.blog.entity.param.RoleUserParam;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author YangShun
 * @since 2020-09-22
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 删除角色
     * @param id 角色ID
     */
    void delete(Integer id);

    /**
     * 获取详情
     * @param id
     * @return
     */
    SysRole detail(Integer id);

    /**
     * 插入权限
     * @param sysRole
     */
    void savePermission(SysRole sysRole);

    /**
     * 获取已分配和未分配用户
     * @param id
     * @return
     */
    Map<String, List<SysUser>> getUsers(Integer id);

    /**
     * 提交分配用户
     * @param param
     */
    void setUsers(RoleUserParam param);


}
