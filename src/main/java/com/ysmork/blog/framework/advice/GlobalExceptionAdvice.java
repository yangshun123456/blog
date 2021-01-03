package com.ysmork.blog.framework.advice;

import com.ysmork.blog.framework.web.entity.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  全局异常拦截
 * </p>
 *
 * @author YangShun
 * @since 2021/1/1
 */
@ControllerAdvice
public class GlobalExceptionAdvice {
    /**
     * 处理未知异常
     * @param request 请求
     * @param e 异常信息
     * @return result
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Result handleException(HttpServletRequest request, Exception e){
        e.printStackTrace();
        return Result.error(e.getMessage());
    }
}
