package com.xx.p.modules.portal.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="NewsGroupListDTO", description="新闻列表分组")
public class CollectNewsDTO implements Serializable {

    private Long newsId;

    private LocalDateTime pubTime;
}
