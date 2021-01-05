package com.ysmork.blog.service;

import com.ysmork.blog.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

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


}
