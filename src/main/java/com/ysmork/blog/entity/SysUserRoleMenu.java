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
 * @since 2020-09-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUserRoleMenu extends Model<SysUserRoleMenu> {

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

    /**
     * 菜单ID
     */
    private Integer menuId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
