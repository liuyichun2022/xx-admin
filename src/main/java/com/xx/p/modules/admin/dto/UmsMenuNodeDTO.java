package com.xx.p.modules.admin.dto;

import com.xx.p.modules.admin.module.entity.UmsMenu;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 后台菜单节点封装
 */
@Getter
@Setter
public class UmsMenuNodeDTO extends UmsMenu {
    @ApiModelProperty(value = "子级菜单")
    private List<UmsMenuNodeDTO> children;
}
