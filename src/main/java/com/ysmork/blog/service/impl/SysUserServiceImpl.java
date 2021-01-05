package com.ysmork.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ysmork.blog.common.util.PageUtils;
import com.ysmork.blog.common.util.StringUtils;
import com.ysmork.blog.dao.SysUserMapper;
import com.ysmork.blog.entity.SysUser;
import com.ysmork.blog.entity.param.UserSelectParam;
import com.ysmork.blog.common.model.DictDataConstants;
import com.ysmork.blog.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author YangShun
 * @since 2020-09-22
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser selectByUserName(String username) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<> ();
        wrapper.eq ("username", username);
        wrapper.eq ("status", DictDataConstants.NORMAL_STATUS);
        return getOne (wrapper);
    }

    @Override
    public List<SysUser> findAll(UserSelectParam param) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<> ();
        wrapper.likeRight (StringUtils.isNotBlank (param.getUsername ()), "username", param.getUsername ())
                .eq (param.getStatus () != null, "status", param.getStatus ());
        return sysUserMapper.selectList (wrapper);
    }
}
