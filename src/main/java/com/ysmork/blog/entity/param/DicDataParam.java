package com.ysmork.blog.entity.param;

import lombok.Data;

/**
 * <p>
 * 字典数据查询参数
 * </p>
 *
 * @author YangShun
 * @since 2021/1/12
 */
@Data
public class DicDataParam {

    private String dictType;

    private String dataName;

    private Integer status;

}
