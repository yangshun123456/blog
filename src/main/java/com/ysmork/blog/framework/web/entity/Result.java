package com.ysmork.blog.framework.web.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yangshun
 * @version 1.0
 * @program: blog
 * @description: 返回类
 * @date 2020/9/22 22:19
 */
@Data
public class Result<T> {

    @ApiModelProperty(value = "错误代码", example = "20000")
    private Integer code;
    @ApiModelProperty(value = "数据", example = "{}")
    private T data;
    @ApiModelProperty(value = "错误信息", example = "id为空")
    private String message;

    public Result(T data){
        this.data = data;
    }

    public Result(Integer code,T data,String message){
        this.data = data;
        this.message = message;
        this.code = code;
    }

    public static<T> Result success(T t){
        return new Result<> (20000,t,"success");
    }
}
