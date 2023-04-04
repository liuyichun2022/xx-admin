package com.xx.p.modules.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xx.p.common.api.PageR;
import com.xx.p.modules.admin.dto.AddMemberDTO;
import com.xx.p.modules.admin.dto.UmsMemberDTO;
import com.xx.p.modules.admin.service.UmsAdminMemberService;
import com.xx.p.modules.portal.module.entity.UmsMember;
import com.xx.p.modules.portal.module.mapper.UmsMemberMapper;
import com.xx.p.modules.portal.service.UmsMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UmsAdminMemberServiceImpl implements UmsAdminMemberService {

    @Resource
    private UmsMemberMapper umsMemberMapper;

    @Resource
    private UmsMemberService umsMemberService;

    @Override
    public boolean batchImport(MultipartFile file) {

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()))){
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields == null ||fields.length != 3) {
                    continue;
                }

                if (exists(fields[1])) {
                    log.info("手机号码已存在, phone:{}", fields[1]);
                    continue;
                }
                UmsMember umsMember = new UmsMember();
                umsMember.setUsername(fields[0])
                        .setPhone(fields[1])
                        .setPassword(fields[1])
                        .setNickname(fields[2])
                        .setGender(0)
                        .setCreateTime(LocalDateTimeUtil.now());
                log.info("插入用户记录, umsMember{}:", umsMember.toString());
                umsMemberMapper.insert(umsMember);
            }
        } catch (Exception e) {

        }
        return true;
    }

    @Override
    public boolean add(AddMemberDTO dto) {
        umsMemberService.adminAdd(dto.getUsername(), dto.getPassword(),  dto.getPhone(), dto.getNickname());
        return true;
    }

    @Override
    public PageR<UmsMemberDTO> getList(String phone, String name, String nickname, Integer pageNum, Integer pageSize) {
        //分页参数
        Page<UmsMember> page = Page.of(pageNum,pageSize);

        //queryWrapper组装查询where条件
        LambdaQueryWrapper<UmsMember> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StrUtil.isNotBlank(phone),UmsMember::getPhone, phone)
                .like(StrUtil.isNotBlank(name),UmsMember::getUsername, name)
                .like(StrUtil.isNotBlank(nickname),UmsMember::getNickname, nickname);
        umsMemberMapper.selectPage(page, queryWrapper);
        List<UmsMember> records = page.getRecords();
        Page<UmsMemberDTO> pageDTO = Page.of(pageNum, pageSize);
        if (CollUtil.isNotEmpty(records)) {
            List<UmsMemberDTO> list = new ArrayList<>();
            for (UmsMember member : records) {
                UmsMemberDTO dto = new UmsMemberDTO();
                BeanUtil.copyProperties(member, dto);
                list.add(dto);
            }
            pageDTO.setRecords(list).setTotal(page.getTotal());
        }
        return PageR.restPage(pageDTO);
    }

    private boolean exists(String phone) {
        LambdaQueryWrapper<UmsMember> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(UmsMember::getPhone, phone);
        return umsMemberMapper.exists(queryWrapper);
    }
}
