package com.ysmork.blog.entity.param;

import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author YangShun
 * @since 2020/12/27
 */
@Data
public class MenuParam {

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 状态
     */
    private Integer status;
}
