package com.ysmork.blog.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 业务关联表
 * </p>
 *
 * @author YangShun
 * @since 2021-01-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BlogUserRel extends Model<BlogUserRel> {

    private static final long serialVersionUID=1L;

    /**
     * 关联ID
     */
    private Integer id;

    /**
     * 业务ID
     */
    private Integer blogId;

    /**
     * 业务类型 1：文章
     */
    private Integer blogType;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
