package com.xx.p.modules.portal.module.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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

/**
 * <p>
 * 新闻表
 * </p>
 *
 * @author liuyichun
 * @since 2023-02-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("nms_news")
@ApiModel(value="NmsNews对象", description="新闻表")
public class NmsNews implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "封面")
    private String coverImg;

    @ApiModelProperty(value = "发布时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime pubTime;

    @ApiModelProperty(value = "发布人")
    private String publisher;

    @ApiModelProperty(value = "发布地点")
    private String pubLocation;

    @ApiModelProperty(value = "新闻内容")
    private String content;

    @ApiModelProperty(value = "创建人")
    private String creator;

    @ApiModelProperty(value = "阅读数")
    private Integer readNum;

    @ApiModelProperty(value = "点赞数")
    private Integer likesNum;

    @ApiModelProperty(value = "创建时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否删除，0 否 1是")
    private Integer deleted;

    @ApiModelProperty(value = "发布状态, 0. 草稿 1. 已发布 2. 已删除")
    private Integer pubStatus;

    @ApiModelProperty(value = "开启评论 0 关闭 1 开启")
    private Integer commendStatus;

}
