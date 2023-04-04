package com.xx.p.modules.portal.module.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xx.p.modules.portal.dto.CollectNewsDTO;
import com.xx.p.modules.portal.module.entity.NmsCollectRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 新闻收藏关联表 Mapper 接口
 * </p>
 *
 * @author liuyichun
 * @since 2023-02-22
 */
public interface NmsCollectRecordMapper extends BaseMapper<NmsCollectRecord> {

    List<CollectNewsDTO> pageSelectNewsId(IPage<CollectNewsDTO> page, @Param("memberId") Long memberId);
}
