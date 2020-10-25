package com.ysmork.blog.common.model;

/**
 * @author yangshun
 * @version 1.0
 * @program: blog
 * @description: 返回code
 * @date 2020/10/17 21:19
 */
public enum  ResultConstants {
    REQUEST_SUCCESS(20000, "请求成功"),
    /*其他错误代码从20001开始，不同的分类的错误类型间隔10，例如下一个类型的错误代码从20011开始*/

    LOGIN_CAPTCHA_ERROR(20001, "验证码错误"),
    USERNAME_OR_PASSWORD_ERROR(20002, "用户名或密码错误"),
    LOGIN_USER_UN_VERIFY(20003, "用户未验证"),
    LOGIN_ERROR(20005, "登录失败"),

    // 小程序验证密码
    MINA_VERIFY_WITH_DRAW_PASSWORD_FAILED(20011, "密码验证失败"),


    /*自定义错误代码结束*/
    REQUEST_PARAM_ERROR(40000, "参数列表错误（缺少，格式不匹配）"),
    SYS_TYPE_SAME(40030,"类型重复"),
    IS_NOT_AUTHORIZED(41000, "没有访问权限"),
    TOKEN_TIME_OUT(43000, "登录过期"),
    RESOURCES_NOT_FOUND(44000, "资源或路径不存在"),
    PROGRAM_ERROR(50000, "程序异常");

    private final Integer code;
    private final String message;

    ResultConstants(Integer code, String message)
    {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
