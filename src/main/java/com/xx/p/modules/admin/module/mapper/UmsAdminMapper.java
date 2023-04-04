package com.xx.p.modules.admin.module.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xx.p.modules.admin.module.entity.UmsAdmin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 后台用户表 Mapper 接口
 * </p>
 *
 * @author liuyichun
 * @since 2023-02-20
 */
public interface UmsAdminMapper extends BaseMapper<UmsAdmin> {
    List<Long> getAdminIdList(@Param("resourceId") Long resourceId);
}
