package com.ysmork.blog.common.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author yangshun
 * @version 1.0
 * @program: blog
 * @description: 分页工具
 * @date 2020/11/1 1:49
 */
public class PageUtils {

    /**
     * 获取分页参数
     * @return
     */
    public static<T> Page<T> getPage(){
        //获取 pageNum pageSize
        Integer pageNum = Integer.parseInt (ServletUtils.getParameter ("pageNum") == null ? "0" : ServletUtils.getParameter ("pageNum"));
        Integer pageSize = Integer.parseInt (ServletUtils.getParameter ("pageSize") == null ? "0" : ServletUtils.getParameter ("pageSize"));

        //默认为 第一页 十条数据
        if(pageNum == 0){
            pageNum = 1;
        }

        if(pageSize == 0){
            pageSize = 10;
        }
        Page<T> pageParam = new Page<> (pageNum,pageSize);
        return pageParam;
    }


}
