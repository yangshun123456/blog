package com.ysmork.blog.entity.param;

import lombok.Data;

/**
 * @author yangshun
 * @version 1.0
 * @program: blog
 * @description: 用户登录
 * @date 2020/10/15 22:53
 */
@Data
public class UserParam {
    private Integer id;

    /**
     * 验证码
     */
    private String captcha;

    private String username;

    private String password;

    /**
     * 验证码key
     */
    private String captchaKey;
}
