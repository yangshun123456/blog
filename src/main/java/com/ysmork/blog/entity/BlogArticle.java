package com.ysmork.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author YangShun
 * @since 2021-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BlogArticle extends Model<BlogArticle> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 文章封面图片
     */
    private String coverImage;

    /**
     * 文章专属二维码地址
     */
    private String qrcodePath;

    /**
     * 点赞数
     */
    private Integer praise;

    /**
     * 反对数
     */
    private Integer opposition;

    private Boolean isMarkdown;

    /**
     * 文章内容
     */
    private String content;

    /**
     * markdown版的文章内容
     */
    private String contentMd;

    /**
     * 是否置顶
     */
    private Boolean top;

    /**
     * 类型
     */
    private Long typeId;

    /**
     * 状态
     */
    private Boolean status;

    /**
     * 是否推荐
     */
    private Boolean recommended;

    /**
     * 是否原创
     */
    private Boolean original;

    /**
     * 文章简介，最多200字
     */
    private String description;

    /**
     * 文章关键字，优化搜索
     */
    private String keywords;

    /**
     * 是否开启评论
     */
    private Boolean comment;

    /**
     * 添加时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
