package com.xx.p.modules.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xx.p.common.api.PageR;
import com.xx.p.common.constants.RedisConstants;
import com.xx.p.common.exception.ApiException;
import com.xx.p.common.service.RedisService;
import com.xx.p.common.util.RedisKeyUtil;
import com.xx.p.modules.admin.dto.AddNewsDTO;
import com.xx.p.modules.admin.dto.NewsDTO;
import com.xx.p.modules.admin.dto.UpdateCommendStatusDTO;
import com.xx.p.modules.admin.module.entity.UmsAdmin;
import com.xx.p.modules.admin.service.CatchRemoteImageService;
import com.xx.p.modules.admin.service.UmsNewsService;
import com.xx.p.modules.admin.util.LoginUtil;
import com.xx.p.modules.portal.module.entity.NmsNews;
import com.xx.p.modules.portal.module.mapper.NmsNewsMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CompletableFuture;


@Service
public class UmsNewsServiceImpl implements UmsNewsService {

    @Resource
    private NmsNewsMapper nmsNewsMapper;
    
    @Resource
    private CatchRemoteImageService catchRemoteImageService;

    @Resource
    private RedisService redisService;


    @Override
    public PageR<NewsDTO> getList(String title, String publisher, Integer pubStatus, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<NmsNews> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(title), NmsNews::getTitle, title)
                .like(StrUtil.isNotBlank(publisher), NmsNews::getPublisher, publisher)
                .eq(Objects.nonNull(pubStatus), NmsNews::getPubStatus, pubStatus)
                .eq(NmsNews::getDeleted, 0)
                .orderByDesc(NmsNews::getCreateTime);
        Page<NmsNews> page = Page.of(pageNum,pageSize);
        nmsNewsMapper.selectPage(page, queryWrapper);
        List<NmsNews> records = page.getRecords();
        Page<NewsDTO> pageDTO = Page.of(pageNum, pageSize);
        if (CollUtil.isNotEmpty(records)) {
            List<NewsDTO> list = new ArrayList<>();
            for (NmsNews news : records) {
                NewsDTO dto = new NewsDTO();
                BeanUtil.copyProperties(news, dto);
                Object likesNumObject = redisService.hGet(RedisKeyUtil.getNewsCacheKey(news.getId()), "likesNum");
                if (Objects.nonNull(likesNumObject)) {
                    dto.setLikesNum(Integer.valueOf(likesNumObject.toString()));
                }
                Object readNumObject = redisService.hGet(RedisKeyUtil.getNewsCacheKey(news.getId()), "readNum");
                if (Objects.nonNull(readNumObject)) {
                    dto.setReadNum(Integer.valueOf(readNumObject.toString()));
                }
                list.add(dto);
            }
            pageDTO.setRecords(list).setTotal(page.getTotal());
        }
        return PageR.restPage(pageDTO);
    }

    @Override
    public boolean add(AddNewsDTO addNewsDTO) {

        UmsAdmin umsAdmin =  LoginUtil.getCurrentUser();
        if (Objects.isNull(umsAdmin)) {
            throw new ApiException("非登录用户不允许操作!");
        }
        if (Objects.nonNull(addNewsDTO.getId())) {
            LambdaUpdateWrapper<NmsNews> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(NmsNews::getId, addNewsDTO.getId());
            NmsNews news = new NmsNews();
            news.setTitle(addNewsDTO.getTitle())
                    .setCoverImg(addNewsDTO.getCoverImg())
                    .setContent(catchRemoteImageService.reSave(addNewsDTO.getContent()))
                    .setUpdateTime(LocalDateTimeUtil.now());
            nmsNewsMapper.update(news, updateWrapper);
        } else {
            NmsNews news = new NmsNews();
            news.setTitle(addNewsDTO.getTitle())
                    .setCoverImg(addNewsDTO.getCoverImg())
                    .setContent(catchRemoteImageService.reSave(addNewsDTO.getContent()))
                    .setCreator(umsAdmin.getUsername())
                    .setUpdateTime(LocalDateTimeUtil.now())
                    .setDeleted(0)
                    .setReadNum(0)
                    .setLikesNum(0)
                    .setPubStatus(0)
                    .setCreateTime(LocalDateTimeUtil.now());
            nmsNewsMapper.insert(news);
            addNewsDTO.setId(news.getId());
        }
        CompletableFuture.runAsync(()-> {
            updateCache(addNewsDTO.getId());
        });
        return true;
    }

    private void updateCache(Long newsId) {
        NmsNews news = nmsNewsMapper.selectById(newsId);
        Map<String, Object> cacheMap = new HashMap<>();
        BeanUtil.beanToMap(news, cacheMap, false, true);
        redisService.hSetAll(RedisKeyUtil.getNewsCacheKey(newsId), cacheMap);
    }

    @Override
    public boolean publish(Long id, Integer pubStatus) {
        UmsAdmin umsAdmin =  LoginUtil.getCurrentUser();
        if (Objects.isNull(umsAdmin)) {
            throw new ApiException("非登录用户不允许操作!");
        }
        LambdaUpdateWrapper<NmsNews> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(NmsNews::getId, id);
        NmsNews news = new NmsNews();
        news.setPublisher(umsAdmin.getUsername())
                .setPubStatus(pubStatus)
                .setUpdateTime(LocalDateTimeUtil.now());
        if (Objects.equals(1, pubStatus)) {
            news.setPubTime(LocalDateTimeUtil.now());
        }
        nmsNewsMapper.update(news, updateWrapper);
        // 直接按照创建时间排序
        if (Objects.equals(1, pubStatus)) {
            redisService.zadd(RedisConstants.XX_NEWS_SORTED_SET, id, Double.valueOf(id));
        } else {
            redisService.zRemove(RedisConstants.XX_NEWS_SORTED_SET, id);
        }
        return true;
    }

    @Override
    public boolean updateCommendStatus(UpdateCommendStatusDTO updateCommendStatusDTO) {
        LambdaUpdateWrapper<NmsNews> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(NmsNews::getId, updateCommendStatusDTO.getId());
        NmsNews news = new NmsNews();
        news.setCommendStatus(updateCommendStatusDTO.getCommendStatus()).setUpdateTime(LocalDateTimeUtil.now());
        nmsNewsMapper.update(news, updateWrapper);
        return true;
    }

    @Override
    public NewsDTO getById(Long id) {
        NmsNews news =  nmsNewsMapper.selectById(id);
        NewsDTO newsDTO = new NewsDTO();
        BeanUtil.copyProperties(news, newsDTO);
        return newsDTO;
    }

    @Override
    public boolean delete(Long id) {
        LambdaUpdateWrapper<NmsNews> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(NmsNews::getId, id);
        NmsNews news = new NmsNews();
        news.setDeleted(1).setUpdateTime(LocalDateTimeUtil.now());
        nmsNewsMapper.update(news, updateWrapper);
        return false;
    }
}
