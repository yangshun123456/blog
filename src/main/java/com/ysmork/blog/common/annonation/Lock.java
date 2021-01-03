package com.ysmork.blog.common.annonation;

import com.ysmork.blog.common.model.RedisKeyConstants;

import java.lang.annotation.*;

/**
 * <p>
 *
 * </p>
 *
 * @author YangShun
 * @since 2021/1/1
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Lock {
    /**
     * 唯一标识KEY
     * @return
     */
    String redisKey() default RedisKeyConstants.NORMAL_KEY;

    /**
     * 不可重复参数名称(为实体类的字段名称)
     * 如为继承过来的字段，请在子类重写
     * @return
     */
    String onlyName() default "id";

    /**
     * 是否加锁
     */
    boolean lock() default true;

    /**
     * 类名(实体类名)
     * @return
     */
    Class<?> className();

    /**
     * 是否需要加status = 1
     * @return
     */
    boolean status() default true;

    /**
     * 实体类 id 名称 ，用于判断方法是新增还是其他
     * 如果不需要查重，则填入""
     * @return
     */
    String idName() default "id";

}