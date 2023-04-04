package com.xx.p.modules.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
@ApiModel(value="新闻对象")
public class NewsDTO implements Serializable  {

    @ApiModelProperty(value = "新闻id")
    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "新闻封面")
    private String coverImg;

    @ApiModelProperty(value = "新闻内容")
    private String content;

    @ApiModelProperty(value = "发布时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime pubTime;

    @ApiModelProperty(value = "发布人")
    private String publisher;

    @ApiModelProperty(value = "发布地点")
    private String pubLocation;

    @ApiModelProperty(value = "创建人")
    private String creator;

    @ApiModelProperty(value = "阅读数")
    private Integer readNum;

    @ApiModelProperty(value = "点赞数")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Integer likesNum;

    @ApiModelProperty(value = "发布状态, 0. 草稿 1. 已发布 2. 已删除")
    private Integer pubStatus;

    @ApiModelProperty(value = "开启评论 0 关闭 1 开启")
    private Integer commendStatus;

}
