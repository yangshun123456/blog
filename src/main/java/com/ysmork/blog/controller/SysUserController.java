package com.ysmork.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ysmork.blog.common.util.SecurityUtils;
import com.ysmork.blog.entity.SysUser;
import com.ysmork.blog.entity.param.UserSelectParam;
import com.ysmork.blog.common.model.DictDataConstants;
import com.ysmork.blog.framework.web.entity.Result;
import com.ysmork.blog.service.SysUserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author YangShun
 * @since 2020-09-22
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    /**
     * 用户列表
     * @param param
     * @return
     */
    @GetMapping("findAll")
    public Result findAll(UserSelectParam param){
        List<SysUser> all = sysUserService.findAll (param);
        return Result.success (all);
    }

    /**
     * 切换状态
     * @param userId
     * @return
     */
    @GetMapping("status")
    @Transactional(rollbackFor = Throwable.class)
    public Result status(Integer userId){
        SysUser sysUser = sysUserService.getById (userId);
        if(sysUser.getStatus ().equals (DictDataConstants.UNUSED_STATUS)){
            sysUser.setStatus (DictDataConstants.NORMAL_STATUS);
        }else if(sysUser.getStatus ().equals (DictDataConstants.NORMAL_STATUS)){
            sysUser.setStatus (DictDataConstants.UNUSED_STATUS);
        }
        sysUserService.updateById (sysUser);
        return Result.success ();
    }

    /**
     * 新增或修改
     * @param sysUser
     * @return
     */
    @PostMapping("save")
    public Result save(@RequestBody SysUser sysUser){
        SysUser user = sysUserService.getOne (new QueryWrapper<SysUser> ().eq ("username", sysUser.getUsername ()));
        if(user != null && sysUser.getId () == null){
            return Result.error ("用户名重复！");
        }
        if(sysUser.getPassword () != null){
            sysUser.setPassword (SecurityUtils.encryptPassword (sysUser.getPassword ()));
        }
        sysUserService.saveOrUpdate (sysUser);
        return Result.success ();
    }

    /**
     * 用户详情
     * @param id
     * @return
     */
    @GetMapping("detail")
    public Result detail(Integer id){
        SysUser byId = sysUserService.getById (id);
        return Result.success (byId);
    }

    /**
     * 用户删除
     * @param id
     * @return
     */
    @GetMapping("delete")
    public Result delete(Integer id){
        sysUserService.removeById (id);
        return Result.success ();
    }

}

