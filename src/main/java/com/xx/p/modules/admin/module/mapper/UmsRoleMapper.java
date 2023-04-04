package com.xx.p.modules.admin.module.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xx.p.modules.admin.module.entity.UmsRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 后台用户角色表 Mapper 接口
 * </p>
 *
 * @author liuyichun
 * @since 2023-02-20
 */
public interface UmsRoleMapper extends BaseMapper<UmsRole> {

    List<UmsRole> getRoleList(@Param("adminId") Long adminId);
}
