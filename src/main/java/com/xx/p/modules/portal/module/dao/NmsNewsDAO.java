package com.xx.p.modules.portal.module.dao;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xx.p.common.service.RedisService;
import com.xx.p.common.util.RedisKeyUtil;
import com.xx.p.modules.portal.module.entity.NmsNews;
import com.xx.p.modules.portal.module.mapper.NmsNewsMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Repository
public class NmsNewsDAO extends ServiceImpl<NmsNewsMapper, NmsNews> {

    @Resource
    private RedisService redisService;

    @Resource
    private NmsNewsMapper nmsNewsMapper;

    public NmsNews getDetailById(Long id) {

        Map<Object, Object> newsMap = redisService.hGetAll(RedisKeyUtil.getNewsCacheKey(id));
        if (MapUtil.isNotEmpty(newsMap) && newsMap.get("id") != null) {
            redisService.hIncr(RedisKeyUtil.getNewsCacheKey(id), "readNum", 1L);
            return BeanUtil.toBean(newsMap, NmsNews.class);
        }
        NmsNews news = nmsNewsMapper.selectById(id);
        Map<String, Object> cacheMap = new HashMap<>();
        BeanUtil.beanToMap(news, cacheMap, false, true);
        redisService.hSetAll(RedisKeyUtil.getNewsCacheKey(id), cacheMap);
        return news;
    }
}
