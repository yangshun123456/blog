package com.ysmork.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 菜单权限表
 * </p>
 *
 * @author YangShun
 * @since 2020-12-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysMenu extends Model<SysMenu> {

    private static final long serialVersionUID=1L;

    /**
     * 菜单ID
     */
    @TableId(value = "menu_id", type = IdType.AUTO)
    private Integer menuId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 父菜单ID
     */
    private Integer parentId;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 前端路由名称
     */
    private String routerName;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    private String menuType;

    /**
     * 菜单状态（0停用 1正常 9删除）
     */
    private Integer status;

    /**
     * 图标
     */
    private String icoUrl;

    /**
     * 权限标识
     */
    private String perms;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone = "GMT+8" )
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone = "GMT+8" )
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 子节点
     */
    @TableField(exist = false)
    private List<SysMenu> children = new ArrayList<> ();


    @Override
    protected Serializable pkVal() {
        return this.menuId;
    }

}
