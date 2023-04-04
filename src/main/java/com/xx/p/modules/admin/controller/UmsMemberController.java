package com.xx.p.modules.admin.controller;

import com.xx.p.common.api.PageR;
import com.xx.p.common.api.R;
import com.xx.p.modules.admin.dto.AddMemberDTO;
import com.xx.p.modules.admin.dto.UmsMemberDTO;
import com.xx.p.modules.admin.module.entity.UmsAdmin;
import com.xx.p.modules.admin.service.UmsAdminMemberService;
import com.xx.p.modules.admin.util.LoginUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@Api(tags = "UmsMemberController")
@Tag(name = "UmsMemberController",description = "用户管理")
@RequestMapping("/member")
@Slf4j
public class UmsMemberController {

    @Resource
    private UmsAdminMemberService umsAdminMemberService;

    @ApiOperation("用户列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public R<PageR<UmsMemberDTO>> getList(@RequestParam(value = "phone",required = false) String phone,
                                          @RequestParam(value = "name",required = false) String name,
                                          @RequestParam(value = "nickname",required = false) String nickname,
                                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                          @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        log.info("查询会员用户列表, phone: {}, pageNum:{}, pageSize:{}", phone, pageNum, pageSize);
        return R.success(umsAdminMemberService.getList(phone, name, nickname, pageNum, pageSize));
    }

    @ApiOperation("用户批量导入")
    @RequestMapping(value = "/batchImport", method = RequestMethod.POST)
    @ResponseBody
    public R<Boolean> batchImport(@RequestPart MultipartFile file) {
        UmsAdmin admin = LoginUtil.getCurrentUser();
        log.info("操作人Id:{}, 账户名:{}, 用户批量导入, fileName:{}",admin.getId(), admin.getUsername(), file.getOriginalFilename());
        return R.success(umsAdminMemberService.batchImport(file));
    }


    @ApiOperation("添加会员")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public R<Boolean> add(@RequestBody @Valid AddMemberDTO addMemberDTO) {
        UmsAdmin admin = LoginUtil.getCurrentUser();
        log.info("操作人Id:{}, 账户名:{}, 新增会员, addMemberDTO:{}.", admin.getId(), admin.getUsername(), addMemberDTO.toString());
        return R.success(umsAdminMemberService.add(addMemberDTO));
    }
}
