package com.ysmork.blog.entity.param;

import lombok.Data;

import java.util.List;

/**
 * <p>
 * 角色管理
 * </p>
 *
 * @author YangShun
 * @since 2021/1/8
 */
@Data
public class RoleUserParam {

    /**
     * 接收的userIds
     */
    private List<Integer> ids;

    /**
     * 角色ID
     */
    private Integer roleId;
}
