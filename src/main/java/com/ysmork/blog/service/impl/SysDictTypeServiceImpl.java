package com.ysmork.blog.service.impl;

import com.ysmork.blog.entity.SysDictType;
import com.ysmork.blog.dao.SysDictTypeMapper;
import com.ysmork.blog.service.SysDictTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 字典类型表 服务实现类
 * </p>
 *
 * @author YangShun
 * @since 2021-01-12
 */
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {

    @Resource
    private SysDictTypeMapper sysDictTypeMapper;
}
