package com.ysmork.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ysmork.blog.common.annonation.Lock;
import com.ysmork.blog.common.model.DictDataConstants;
import com.ysmork.blog.common.model.Page;
import com.ysmork.blog.common.util.PageUtils;
import com.ysmork.blog.common.util.SecurityUtils;
import com.ysmork.blog.common.util.StringUtils;
import com.ysmork.blog.entity.SysDictData;
import com.ysmork.blog.entity.SysDictType;
import com.ysmork.blog.entity.param.DicDataParam;
import com.ysmork.blog.framework.web.entity.Result;
import com.ysmork.blog.service.SysDictDataService;
import com.ysmork.blog.service.SysDictTypeService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 数据字典
 * </p>
 *
 * @author YangShun
 * @since 2021/1/12
 */
@RestController
@RequestMapping("dic")
public class DictionaryController {

    @Resource
    private SysDictTypeService sysDictTypeService;

    @Resource
    private SysDictDataService sysDictDataService;

    /**
     * 字典类型 对外接口
     *
     * @param typeName
     * @param status
     * @return
     */
    @GetMapping("findAll")
    public Result findAll(String typeName, Integer status) {
        PageUtils.startPage ();
        List<SysDictType> list = sysDictTypeService.list (new QueryWrapper<SysDictType> ()
                .ne ("status", DictDataConstants.DELETE_STATUS)
                .eq (StringUtils.isNotBlank (typeName), "dict_name", typeName)
                .eq (status != null, "status", status));
        return PageUtils.getData (list);
    }

    @GetMapping("detail")
    public Result findAll(Integer id) {
        SysDictType sysDictType = sysDictTypeService.getById (id);
        return Result.success (sysDictType);
    }

    @GetMapping("delete")
    public Result delete(Long id) {
        SysDictType sysDictType = new SysDictType ();
        sysDictType.setDictId (id);
        sysDictType.setStatus (DictDataConstants.DELETE_STATUS);
        sysDictTypeService.updateById (sysDictType);
        return Result.success ();
    }

    @PostMapping("save")
    @Lock(className = SysDictType.class, onlyName = "dictName", idName = "dictId")
    public Result save(@RequestBody SysDictType sysDictType) {
        sysDictType.setCreateBy (SecurityUtils.getLoginUser ().getUsername ());
        sysDictTypeService.saveOrUpdate (sysDictType);
        return Result.success ();
    }

    @GetMapping("status")
    @Transactional(rollbackFor = Throwable.class)
    public Result status(Integer id) {
        SysDictType sysDictType = sysDictTypeService.getById (id);
        if (sysDictType.getStatus () == DictDataConstants.UNUSED_STATUS) {
            sysDictType.setStatus (DictDataConstants.UNUSED_STATUS);
        } else if (sysDictType.getStatus () == DictDataConstants.NORMAL_STATUS) {
            sysDictType.setStatus (DictDataConstants.NORMAL_STATUS);
        }
        //修改状态为禁用/启用
        sysDictTypeService.updateById (sysDictType);
        return Result.success ();
    }

    @GetMapping("getDictType")
    public Result getDictType() {
        List<SysDictType> list = sysDictTypeService.list (new QueryWrapper<SysDictType> ()
                .select ("dict_type", "dict_name")
                .eq ("status", DictDataConstants.NORMAL_STATUS));
        return Result.success (list);
    }


    /**
     * 字典详情对外接口
     *
     * @param param
     * @return
     */
    @GetMapping("listData")
    public Result listData(DicDataParam param) {
        PageUtils.startPage ();
        List<SysDictData> list = sysDictDataService.list (new QueryWrapper<SysDictData> ()
                .ne ("status", DictDataConstants.DELETE_STATUS)
                .eq ("dict_type", param.getDictType ())
                .likeRight (StringUtils.isNotBlank (param.getDataName ()), "dict_name", param.getDataName ())
                .eq (param.getStatus () != null, "status", param.getStatus ()));
        return PageUtils.getData (list);
    }

    @GetMapping("dataDetail")
    public Result dataDetail(Integer id) {
        SysDictData sysDictData = sysDictDataService.getById (id);
        return Result.success (sysDictData);
    }

    @GetMapping("dataDelete")
    public Result dataDelete(Integer id) {
        sysDictDataService.removeById (id);
        return Result.success ();
    }

    @PostMapping("saveData")
    @Lock(className = SysDictData.class, onlyName = "dictLabel", idName = "dictCode")
    public Result saveData(@RequestBody SysDictData sysDictData) {
        sysDictDataService.saveOrUpdate (sysDictData);
        return Result.success ();
    }

}
