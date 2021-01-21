package com.ysmork.blog.service.impl;

import com.ysmork.blog.entity.BlogUserRel;
import com.ysmork.blog.dao.BlogUserRelMapper;
import com.ysmork.blog.service.BlogUserRelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务关联表 服务实现类
 * </p>
 *
 * @author YangShun
 * @since 2021-01-18
 */
@Service
public class BlogUserRelServiceImpl extends ServiceImpl<BlogUserRelMapper, BlogUserRel> implements BlogUserRelService {

}
