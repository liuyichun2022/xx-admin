package com.xx.p.modules.portal.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xx.p.common.exception.ApiException;
import com.xx.p.modules.portal.dto.CollectNewsDTO;
import com.xx.p.modules.portal.dto.NewsGroupListDTO;
import com.xx.p.modules.portal.dto.NewsListDTO;
import com.xx.p.modules.portal.module.dao.NmsNewsDAO;
import com.xx.p.modules.portal.module.entity.NmsNews;
import com.xx.p.modules.portal.module.entity.UmsMember;
import com.xx.p.modules.portal.module.mapper.NmsCollectRecordMapper;
import com.xx.p.modules.portal.service.MyPageService;
import com.xx.p.modules.portal.service.UmsMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class MyPageServiceImpl implements MyPageService {

    @Resource
    private NmsCollectRecordMapper nmsCollectRecordMapper;

    @Resource
    private UmsMemberService umsMemberService;

    @Resource
    private NmsNewsDAO newsDAO;

    @Override
    public List<NewsGroupListDTO> myCollectList(Integer pageNum, Integer pageSize) {
        UmsMember umsMember = umsMemberService.getCurrentMember();
        if (umsMember == null) {
            throw new ApiException("非登录用户不允许查询!");
        }
        Page<CollectNewsDTO> page = Page.of(pageNum,pageSize);
        List<CollectNewsDTO> list = nmsCollectRecordMapper.pageSelectNewsId(page, umsMember.getId());
        List<NewsGroupListDTO> groupList = new ArrayList<>();
        Map<String, List<NewsListDTO>> map = new HashMap<>();
        if (CollUtil.isNotEmpty(list)) {
            String curGroup = "";
            for (CollectNewsDTO record: list) {
                NmsNews news = newsDAO.getDetailById(record.getNewsId());
                String group = getDateStr(news.getPubTime());
                if (curGroup != group) {
                    NewsGroupListDTO groupListDTO = new NewsGroupListDTO();
                    List<NewsListDTO> newsListDTOS = new ArrayList<>();
                    groupListDTO.setGroupName(group)
                            .setNewsList(newsListDTOS);
                    map.put(group, newsListDTOS);
                    groupList.add(groupListDTO);
                    curGroup = group;
                }
                NewsListDTO dto = new NewsListDTO();
                BeanUtil.copyProperties(news, dto);
                map.get(group).add(dto);
            }
        }
        return groupList;
    }

    private String getDateStr(LocalDateTime pubTime) {
        if (LocalDateTimeUtil.isSameDay(LocalDateTimeUtil.now(), pubTime)) {
            return "今天";
        } else if (LocalDateTimeUtil.isSameDay(LocalDateTimeUtil.of(DateUtil.yesterday()), pubTime)) {
            return "昨天";
        } else {
            return LocalDateTimeUtil.format(pubTime, "yyyy年MM月dd日");
        }
    }
}
