package com.ysmork.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * 字典类型表
 * </p>
 *
 * @author YangShun
 * @since 2021-01-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysDictType extends Model<SysDictType> {

    private static final long serialVersionUID=1L;

    /**
     * 字典主键
     */
    @TableId(value = "dict_id", type = IdType.AUTO)
    private Long dictId;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 状态（0正常 1停用）
     */
    private Integer status;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone = "GMT+8" )
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone = "GMT+8" )
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 字典详情
     */
    @TableField(exist = false)
    private List<SysDictData> sysDictData;


    @Override
    protected Serializable pkVal() {
        return this.dictId;
    }

}
