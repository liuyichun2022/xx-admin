package com.xx.p.modules.portal.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="NmsNews对象", description="新闻表")
public class NewsListDTO implements Serializable {

    @ApiModelProperty(value = "新闻id")
    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "新闻封面")
    private String coverImg;

    @ApiModelProperty(value = "发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private LocalDateTime pubTime;

    @ApiModelProperty(value = "发布人")
    private String publisher;

    @ApiModelProperty(value = "创建人")
    private String creator;

    @ApiModelProperty(value = "阅读数")
    private Integer readNum;

    @ApiModelProperty(value = "点赞数")
    private Integer likesNum;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

}
