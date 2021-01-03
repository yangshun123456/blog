package com.ysmork.blog.common.model;

/**
 * @author yangshun
 * @version 1.0
 * @program: blog
 * @description: 通用常量信息
 * @date 2020/10/11 21:34
 */
public class Constants {
    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * http请求
     */
    public static final String HTTP = "http://";

    /**
     * https请求
     */
    public static final String HTTPS = "https://";

    /**
     * 通用成功标识
     */
    public static final String SUCCESS = "1";

    /**
     * 通用失败标识
     */
    public static final String FAIL = "0";

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    public static final String BPO_TOKEN_REFRESH_FLAG = "BPO_TOKEN_REFRESH_FLAG:";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "";

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * 菜单顶级ID
     */
    public static final Integer MENU_TOP_ID = 0;

    /**
     * admin角色key
     */
    public static final String ADMIN_KEY = "admin";

    /**
     * 所有权限标识
     */
    public static final String ALL_PERMISSION = "*:*";

}
