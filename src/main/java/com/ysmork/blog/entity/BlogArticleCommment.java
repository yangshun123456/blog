package com.ysmork.blog.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 文章评论
 * </p>
 *
 * @author YangShun
 * @since 2021-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BlogArticleCommment extends Model<BlogArticleCommment> {

    private static final long serialVersionUID=1L;

    /**
     * 评论ID
     */
    private Integer  id;

    /**
     * 文章ID
     */
    private Integer articleId;

    /**
     * 归属评论ID 最顶级为自己
     */
    private Integer topId;

    /**
     * 回复评论ID
     */
    private Integer parentId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this. id;
    }

}
