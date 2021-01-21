package com.ysmork.blog.service.impl;

import com.ysmork.blog.entity.BlogArticleCommment;
import com.ysmork.blog.dao.BlogArticleCommmentMapper;
import com.ysmork.blog.service.BlogArticleCommmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章评论 服务实现类
 * </p>
 *
 * @author YangShun
 * @since 2021-01-18
 */
@Service
public class BlogArticleCommmentServiceImpl extends ServiceImpl<BlogArticleCommmentMapper, BlogArticleCommment> implements BlogArticleCommmentService {

}
