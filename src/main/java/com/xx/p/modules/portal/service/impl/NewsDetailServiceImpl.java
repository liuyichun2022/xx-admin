package com.xx.p.modules.portal.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xx.p.common.api.PageR;
import com.xx.p.modules.portal.dto.NmsNewsDetailDTO;
import com.xx.p.modules.portal.module.dao.NmsNewsDAO;
import com.xx.p.modules.portal.module.entity.NmsCommendRecord;
import com.xx.p.modules.portal.module.entity.NmsNews;
import com.xx.p.modules.portal.module.mapper.NmsCommendRecordMapper;
import com.xx.p.modules.portal.service.NewsDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class NewsDetailServiceImpl implements NewsDetailService {
    @Resource
    private NmsCommendRecordMapper nmsCommendRecordMapper;
    @Resource
    private NmsNewsDAO newsDAO;

    @Override
    public NmsNewsDetailDTO getById(Long id) {
        NmsNews news = newsDAO.getDetailById(id);
        if (news != null) {
            NmsNewsDetailDTO dto = new NmsNewsDetailDTO();
            BeanUtil.copyProperties(news, dto);
            return dto;
        }
        return null;
    }

    @Override
    public PageR<NmsCommendRecord> getCommendsById(Long newsId, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<NmsCommendRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Objects.nonNull(newsId), NmsCommendRecord::getNewsId, newsId)
                .orderByDesc(NmsCommendRecord::getCreateTime);
        Page<NmsCommendRecord> page = Page.of(pageNum,pageSize);
        nmsCommendRecordMapper.selectPage(page, queryWrapper);
        return PageR.restPage(page);
    }
}
