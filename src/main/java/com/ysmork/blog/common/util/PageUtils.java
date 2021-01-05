package com.ysmork.blog.common.util;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ysmork.blog.common.model.Page;
import com.ysmork.blog.framework.web.entity.Result;

import java.util.List;

/**
 * @author yangshun
 * @version 1.0
 * @program: blog
 * @description: 分页工具
 * @date 2020/11/1 1:49
 */
public class  PageUtils {

    private static ThreadLocal<Integer> pageNum = new ThreadLocal<> ();

    private static ThreadLocal<Integer> pageSize = new ThreadLocal<> ();

    private static final Integer DEFAULT_PAGE_NUM = 1;
    private static final Integer DEFAULT_PAGE_SIZE = 10;

    private static void init() {
        if (pageNum.get () != null) {
            pageNum.remove ();
        }
        if(pageSize.get () != null){
            pageSize.remove ();
        }

        Integer pageNums = ServletUtils.getParameterToInt ("pageNum");
        Integer pageSizes = ServletUtils.getParameterToInt ("pageSize");

        if(pageNums == null){
            pageNum.set (DEFAULT_PAGE_NUM);
        }else {
            pageNum.set (pageNums);
        }
        if(pageSizes == null){
            pageSize.set (DEFAULT_PAGE_SIZE);
        }else {
            pageSize.set (pageSizes);
        }
    }

    public static void startPage() {
        init ();
        System.out.println (pageNum);
        System.out.println (pageSize);
        Integer integer = pageNum.get ();
        Integer integer1 = pageSize.get ();
        PageHelper.startPage (pageNum.get (),pageSize.get ());
    }

    public static<T> Result getData(List<T> lists) {
        if(lists==null){
            Page<T> page = new Page<> ();
            page.setList (null);
            page.setTotal (0L);
            page.setPages (1);
            return Result.success ();
        }
        PageInfo<T> pageInfo = new PageInfo<> (lists);
        Page<T> page = new Page<> ();
        page.setList (lists);
        page.setTotal (pageInfo.getTotal ());
        page.setPages (pageInfo.getPages ());
        return Result.success (page);
    }


}
