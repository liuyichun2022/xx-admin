package com.xx.p.modules.portal.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xx.p.common.exception.ApiException;
import com.xx.p.common.service.RedisService;
import com.xx.p.common.util.RedisKeyUtil;
import com.xx.p.modules.portal.module.entity.*;
import com.xx.p.modules.portal.module.mapper.NmsCollectRecordMapper;
import com.xx.p.modules.portal.module.mapper.NmsCommendRecordMapper;
import com.xx.p.modules.portal.module.mapper.NmsLikesRecordMapper;
import com.xx.p.modules.portal.module.mapper.NmsNewsMapper;
import com.xx.p.modules.portal.service.UmsMemberService;
import com.xx.p.modules.portal.service.UserOperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

@Service
@Slf4j
public class UserOperationServiceImpl implements UserOperationService {

    @Resource
    private NmsCommendRecordMapper nmsCommendRecordMapper;

    @Resource
    private UmsMemberService umsMemberService;

    @Resource
    private NmsLikesRecordMapper nmsLikesRecordMapper;

    @Resource
    private NmsCollectRecordMapper nmsCollectRecordMapper;

    @Resource
    private NmsNewsMapper nmsNewsMapper;

    @Resource
    private RedisService redisService;


    @Override
    public Boolean commend(Long newsId, String msg) {

        UmsMember umsMember = umsMemberService.getCurrentMember();
        if (umsMember == null) {
            throw new ApiException("非登录用户不允许评论!");
        }
        NmsCommendRecord record = new NmsCommendRecord();
        record.setMessage(msg).setNewsId(newsId)
                .setMemberId(umsMember.getId())
                .setNickName(umsMember.getNickname())
                .setMemberIcon(umsMember.getIcon())
                .setLocation(Objects.isNull(umsMember.getCity()) ? "": umsMember.getCity())
                .setDeleted(0)
                .setCreateTime(LocalDateTimeUtil.now());

        NmsNews news = nmsNewsMapper.selectById(newsId);
        if (Objects.isNull(news)) {
            record.setNewsTitle(news.getTitle());
        }
        nmsCommendRecordMapper.insert(record);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean likeNews(Long newsId) {
        UmsMember umsMember = umsMemberService.getCurrentMember();
        if (Objects.isNull(umsMember)) {
            throw new ApiException("非登录用户不允许点赞");
        }

        LambdaQueryWrapper<NmsLikesRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(NmsLikesRecord::getNewsId, newsId)
                .eq(NmsLikesRecord::getMemberId, umsMember.getId())
                .eq(NmsLikesRecord::getDeleted, 0);
        // 已经点赞过了不需要在创建
        boolean checkResult = nmsLikesRecordMapper.exists(queryWrapper);
        if (checkResult) {
            return true;
        }

        NmsLikesRecord nmsLikesRecord = new NmsLikesRecord();
        nmsLikesRecord.setNewsId(newsId)
                .setMemberId(umsMember.getId())
                .setNickName(umsMember.getNickname())
                .setCreateTime(LocalDateTimeUtil.now())
                .setUpdateTime(LocalDateTimeUtil.now());
        int count = nmsLikesRecordMapper.insert(nmsLikesRecord);
        if (count == 1) {
            redisService.hIncr(RedisKeyUtil.getNewsCacheKey(newsId), "likesNum", 1L);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cancelLike(Long newsId) {
        UmsMember umsMember = umsMemberService.getCurrentMember();
        if (Objects.isNull(umsMember)) {
            throw new ApiException("非登录用户不允许点赞");
        }

        LambdaUpdateWrapper<NmsLikesRecord> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(NmsLikesRecord::getNewsId, newsId)
                .eq(NmsLikesRecord::getMemberId, umsMember.getId());
        NmsLikesRecord record = new NmsLikesRecord();
        record.setDeleted(1);
        int updateCount = nmsLikesRecordMapper.update(record, updateWrapper);
        if (updateCount == 1) {
            redisService.hDecr(RedisKeyUtil.getNewsCacheKey(newsId), "likesNum", 1L);
        }
        return true;
    }

    @Override
    public boolean collectNews(Long newsId) {
        UmsMember umsMember = umsMemberService.getCurrentMember();
        if (Objects.isNull(umsMember)) {
            throw new ApiException("非登录用户不允许收藏");
        }

        LambdaQueryWrapper<NmsCollectRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(NmsCollectRecord::getNewsId, newsId)
                .eq(NmsCollectRecord::getMemberId, umsMember.getId())
                .eq(NmsCollectRecord::getDeleted, 0);
        // 已经收藏过了不需要重新创建记录
        boolean checkResult = nmsCollectRecordMapper.exists(queryWrapper);
        if (checkResult) {
            return true;
        }

        NmsCollectRecord nmsCollectRecord = new NmsCollectRecord();
        nmsCollectRecord.setNewsId(newsId)
                .setMemberId(umsMember.getId())
                .setNickName(umsMember.getNickname())
                .setCreateTime(LocalDateTimeUtil.now())
                .setUpdateTime(LocalDateTimeUtil.now());
        nmsCollectRecordMapper.insert(nmsCollectRecord);
        return true;
    }

    @Override
    public boolean cancelCollect(Long newsId) {
        UmsMember umsMember = umsMemberService.getCurrentMember();
        if (Objects.isNull(umsMember)) {
            throw new ApiException("非登录用户不允许操作");
        }

        LambdaUpdateWrapper<NmsCollectRecord> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(NmsCollectRecord::getNewsId, newsId)
                .eq(NmsCollectRecord::getMemberId, umsMember.getId());
        NmsCollectRecord record = new NmsCollectRecord();
        record.setDeleted(1);
        nmsCollectRecordMapper.update(record, updateWrapper);
        return true;
    }
}
