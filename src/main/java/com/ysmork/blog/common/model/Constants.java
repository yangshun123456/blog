package com.ysmork.blog.common.model;

import io.jsonwebtoken.Claims;

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
     * 验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;

    /**
     * 令牌
     */
    public static final String TOKEN = "token";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "";

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * 用户ID
     */
    public static final String JWT_USERID = "userid";

    /**
     * 用户名称
     */
    public static final String JWT_USERNAME = Claims.SUBJECT;

    /**
     * 用户头像
     */
    public static final String JWT_AVATAR = "avatar";

    /**
     * 创建时间
     */
    public static final String JWT_CREATED = "created";

    /**
     * 用户权限
     */
    public static final String JWT_AUTHORITIES = "authorities";

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/profile";

    /**
     * 系统工作模块类型
     */
    public static final byte SYS_WORK_MODULE_TYPE= 1;

    /**
     * 系统工序类型
     */
    public static final byte SYS_PROCESS_TYPE= 2;

    /**
     * 系统工作小组类型
     */
    public static final byte SYS_WORK_GROUP= 3;

    /**
     * 数据字典
     * sys_station:场地
     * sys_person_station:人员岗位
     * sys_belong_business:所属行业
     */
    // redis缓存key字典类型
    public static final String SYS_DICT_TYPE = "SysDictType";

    public static final String SYS_STATION_DICT = "sys_station";
    public static final String SYS_PERSON_STATION = "sys_person_station";
    public static final String SYS_BELONG_BUSINESS = "sys_belong_business";
    public static final String SYS_CLIENT_TYPE = "custom_type";
    // 闸机模式
    //
    public static final String SYS_GATE_MODEL = "17";
    public static final String SYS_UPLOAD_MODEL = "18";

    // 未分工
    public static final String BPO_STATION_NOT_DEFINE = "未分工";


    public static final String SYS_DATA_TYPE = "SYS_DATA_TYPE-";

    /**
     * 文件存储策略 fdfs/cos
     */
    public static final String FILE_STORE_STRATEGY_OF_FDFS = "fdfs";
    public static final String FILE_STORE_STRATEGY_OF_COS = "cos";
    public static final String SYS_ALL_MENU = "sysAllMenu";

    // 工号
    public static final String SYS_COMPANY_WORK_NO = "SYS_COMPANY_WORK_NO";
}
