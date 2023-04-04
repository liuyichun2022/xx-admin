package com.xx.p.modules.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="创建新闻")
public class AddNewsDTO {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "标题")
    @NotBlank(message = "标题title不能为空")
    private String title;

    @ApiModelProperty(value = "新闻封面")
    @NotBlank(message = "新闻封面不能为空")
    private String coverImg;

    @ApiModelProperty(value = "新闻内容")
    @NotBlank(message = "新闻内容不能为空")
    private String content;
}
