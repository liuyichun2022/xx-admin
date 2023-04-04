package com.xx.p.modules.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="更新是否开启用户评论")
public class UpdateCommendStatusDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "新闻id")
    private Integer id;

    @ApiModelProperty(value = "开启评论 0 关闭 1 开启")
    private Integer commendStatus;
}
