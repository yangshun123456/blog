/*
 * FileName: FastDFSFile
 * Author:   Akeung
 * Date:     2020/8/18
 */
package com.ysmork.blog.framework.web.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Akeung
 * 2020/8/18
 */
@Data
@Accessors(chain = true)
public class FastDFSFile implements Serializable {
    //文件名字
    private String name;
    //文件内容
    private byte[] content;
    //文件扩展名
    private String ext;
    //文件MD5摘要值
    private String md5;
    //文件创建作者
    private String author;
}
