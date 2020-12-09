package com.ysmork.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ysmork.blog.entity.SysUser;
import com.ysmork.blog.entity.param.UserSelectParam;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author YangShun
 * @since 2020-09-22
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 根据username条件查询SysUser
     * @param username
     * @return
     */
    SysUser selectByUserName(String username);


    List<SysUser> findAll(UserSelectParam param);
}
