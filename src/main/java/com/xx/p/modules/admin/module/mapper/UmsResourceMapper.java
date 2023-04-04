package com.xx.p.modules.admin.module.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xx.p.modules.admin.module.entity.UmsResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 后台资源表 Mapper 接口
 * </p>
 *
 * @author liuyichun
 * @since 2023-02-20
 */
public interface UmsResourceMapper extends BaseMapper<UmsResource> {
    /**
     * 获取用户所有可访问资源
     */
    List<UmsResource> getResourceList(@Param("adminId") Long adminId);

    /**
     * 根据角色ID获取资源
     */
    List<UmsResource> getResourceListByRoleId(@Param("roleId") Long roleId);
}
