package com.ysmork.blog.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author YangShun
 * @since 2021-01-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUserRole extends Model<SysUserRole> {

    private static final long serialVersionUID=1L;

    private Integer id;

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 用户ID
     */
    private Integer userId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
