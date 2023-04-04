package com.xx.p.modules.portal.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.xx.p.common.constants.RedisConstants;
import com.xx.p.common.service.RedisService;
import com.xx.p.modules.portal.dto.NewsGroupListDTO;
import com.xx.p.modules.portal.dto.NewsListDTO;
import com.xx.p.modules.portal.module.dao.NmsNewsDAO;
import com.xx.p.modules.portal.module.entity.NmsNews;
import com.xx.p.modules.portal.service.HomeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class HomeServiceImpl implements HomeService {

    @Resource
    private RedisService redisService;

    @Resource
    private NmsNewsDAO newsDAO;
    @Override
    public List<NewsGroupListDTO>  newsList(Integer pageNum, Integer pageSize) {
        int start = (pageNum-1)*pageSize;
        Set<Object> newsIds = redisService.reverseRange(RedisConstants.XX_NEWS_SORTED_SET,start, pageSize);
        List<NewsGroupListDTO> groupList = new ArrayList<>();
        Map<String, List<NewsListDTO>> map = new HashMap<>();
        if (CollUtil.isNotEmpty(newsIds)) {
            String curGroup = "";
            for (Object id : newsIds) {
                NmsNews news = newsDAO.getDetailById(Long.valueOf(id.toString()));
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
