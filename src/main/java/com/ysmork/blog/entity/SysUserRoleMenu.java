package com.ysmork.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author YangShun
 * @since 2020-12-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUserRoleMenu extends Model<SysUserRoleMenu> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
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
