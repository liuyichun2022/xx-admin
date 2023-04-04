package com.xx.p.modules.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xx.p.common.api.PageR;
import com.xx.p.modules.admin.dto.CommendDTO;
import com.xx.p.modules.admin.service.UmsCommendService;
import com.xx.p.modules.portal.module.entity.NmsCommendRecord;
import com.xx.p.modules.portal.module.entity.NmsNews;
import com.xx.p.modules.portal.module.entity.UmsMember;
import com.xx.p.modules.portal.module.mapper.NmsCommendRecordMapper;
import com.xx.p.modules.portal.module.mapper.NmsNewsMapper;
import com.xx.p.modules.portal.module.mapper.UmsMemberMapper;
import com.xx.p.modules.portal.service.UmsMemberService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UmsCommendServiceImpl implements UmsCommendService {

    @Resource
    private NmsCommendRecordMapper nmsCommendRecordMapper;

    @Resource
    private NmsNewsMapper nmsNewsMapper;

    @Resource
    private UmsMemberMapper umsMemberMapper;

    @Resource
    private UmsMemberService umsMemberService;

    @Override
    public PageR<CommendDTO> getList(String title, String nickName, String phone, Integer tag , Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<NmsCommendRecord> queryWrapper = new LambdaQueryWrapper<>();

        if (StrUtil.isNotBlank(phone)) {
            UmsMember member = umsMemberService.getByPhone(phone);
            if (Objects.nonNull(member)) {
                return null;
            }
            queryWrapper.eq(NmsCommendRecord::getMemberId, member.getId());
        }
        queryWrapper.like(StrUtil.isNotBlank(nickName), NmsCommendRecord::getNickName, nickName)
                .like(StrUtil.isNotBlank(title), NmsCommendRecord::getNewsTitle, title)
                .eq(Objects.nonNull(tag), NmsCommendRecord::getTag, tag)
                .eq(NmsCommendRecord::getDeleted, 0)
                .orderByDesc(NmsCommendRecord::getCreateTime);
        Page<NmsCommendRecord> page = Page.of(pageNum,pageSize);
        nmsCommendRecordMapper.selectPage(page, queryWrapper);
        List<NmsCommendRecord> records = page.getRecords();
        Page<CommendDTO> pageDTO = Page.of(pageNum, pageSize);
        if (CollUtil.isNotEmpty(records)) {
            List<CommendDTO> list = new ArrayList<>();
            for (NmsCommendRecord record : records) {
                CommendDTO dto = new CommendDTO();
                BeanUtil.copyProperties(record, dto);
                NmsNews news = nmsNewsMapper.selectById(record.getNewsId());
                if (Objects.nonNull(news )) {
                    dto.setNewsTitle(news.getTitle());
                }
                UmsMember member = umsMemberMapper.selectById(record.getMemberId());
                if (Objects.nonNull(member)) {
                    if (StrUtil.isBlank(member.getNickname())) {
                        dto.setNickName(member.getUsername());
                    } else {
                        dto.setNickName(member.getNickname());
                    }
                    dto.setPhone(member.getPhone());
                }
                list.add(dto);
            }
            pageDTO.setRecords(list).setTotal(page.getTotal());
        }
        return PageR.restPage(pageDTO);
    }

    @Override
    public boolean deleteCommend(Long id) {
        LambdaUpdateWrapper<NmsCommendRecord> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(NmsCommendRecord::getId, id);

        NmsCommendRecord record = new NmsCommendRecord();
        record.setDeleted(1).setUpdateTime(LocalDateTimeUtil.now());
        nmsCommendRecordMapper.update(record, updateWrapper);
        return true;
    }

    @Override
    public boolean wonderfulCommend(Long id, Integer tag) {
        LambdaUpdateWrapper<NmsCommendRecord> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(NmsCommendRecord::getId, id);
        NmsCommendRecord record = new NmsCommendRecord();
        record.setTag(tag).setUpdateTime(LocalDateTimeUtil.now());
        nmsCommendRecordMapper.update(record, updateWrapper);
        return true;
    }
}
