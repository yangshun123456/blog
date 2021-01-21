package com.ysmork.blog.service.impl;

import com.ysmork.blog.entity.BlogArticle;
import com.ysmork.blog.dao.BlogArticleMapper;
import com.ysmork.blog.service.BlogArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author YangShun
 * @since 2021-01-18
 */
@Service
public class BlogArticleServiceImpl extends ServiceImpl<BlogArticleMapper, BlogArticle> implements BlogArticleService {

}
