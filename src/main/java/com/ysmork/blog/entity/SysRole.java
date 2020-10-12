package com.ysmork.blog.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
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
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
