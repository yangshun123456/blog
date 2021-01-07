package com.ysmork.blog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

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
public class SysRole extends Model<SysRole> {

    private static final long serialVersionUID=1L;

    /**
     * 角色id
     */
    private Integer id;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 角色权限字符串(编码)
     */
    private String roleKey;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 角色状态（1正常 9删除）
     */
    private String remark;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone = "GMT+8" )
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    /**
     * 菜单权限
     */
    @TableField(exist = false)
    private List<SysMenu> sysMenus;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
