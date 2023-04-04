package com.xx.p.modules.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xx.p.modules.admin.module.entity.UmsResourceCategory;

import java.util.List;

/**
 * 后台资源分类管理Service
 */
public interface UmsResourceCategoryService extends IService<UmsResourceCategory> {

    /**
     * 获取所有资源分类
     */
    List<UmsResourceCategory> listAll();

    /**
     * 创建资源分类
     */
    boolean create(UmsResourceCategory umsResourceCategory);
}
