package com.ysmork.blog.entity.param;

import com.ysmork.blog.entity.SysUser;
import lombok.Data;

/**
 * @author yangshun
 * @version 1.0
 * @program: blog
 * @description: 用户查询
 * @date 2020/11/1 1:41
 */
@Data
public class UserSelectParam extends SysUser {

    public UserSelectParam(String username) {
        super (username);
    }
}
