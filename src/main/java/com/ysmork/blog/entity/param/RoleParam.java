package com.ysmork.blog.entity.param;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * <p>
 * 角色管理
 * </p>
 *
 * @author YangShun
 * @since 2021/1/4
 */
@Data
public class RoleParam {

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 角色标识
     */
    private String roleKey;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
}
