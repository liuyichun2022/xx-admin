package com.xx.p.modules.admin.controller;

import com.xx.p.common.api.PageR;
import com.xx.p.common.api.R;
import com.xx.p.modules.admin.dto.CommendDTO;
import com.xx.p.modules.admin.module.entity.UmsAdmin;
import com.xx.p.modules.admin.service.UmsCommendService;
import com.xx.p.modules.admin.util.LoginUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@Api(tags = "UmsCommendController")
@Tag(name = "UmsCommendController",description = "留言管理")
@RequestMapping("/admin/api/commend")
@Slf4j
public class UmsCommendController {

    @Resource
    private UmsCommendService umsCommendService;

    @ApiOperation("新闻评论列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public R<PageR<CommendDTO>> getList(@RequestParam(value = "title", required = false) String title,
                                        @RequestParam(value = "nickName", required = false) String nickName,
                                        @RequestParam(value = "phone", required = false) String phone,
                                        @RequestParam(value = "tag", required = false) Integer tag,
                                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                        @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        log.info("查询新闻评论列表: pageNum:{}, pageSize:{}", pageNum, pageSize);
        return R.success(umsCommendService.getList(title, nickName, phone, tag,pageNum, pageSize));
    }


    @ApiOperation("删除留言")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public R<Boolean> deleteCommend(@RequestParam(value = "id") Long id) {
        UmsAdmin admin = LoginUtil.getCurrentUser();
        log.info("操作人Id:{}, 账户名:{}, 删除留言, 留言id:{}",admin.getId(), admin.getUsername(), id);
        return R.success(umsCommendService.deleteCommend(id));
    }

    @ApiOperation("精选")
    @RequestMapping(value = "/wonderful", method = RequestMethod.POST)
    @ResponseBody
    public R<Boolean> wonderfulCommend(@RequestParam(value = "id") Long id, @RequestParam(value = "tag") Integer tag) {
        UmsAdmin admin = LoginUtil.getCurrentUser();
        log.info("操作人Id:{}, 账户名:{}, 留言置为精选: id:{}, tag:{}",admin.getId(), admin.getUsername(), id, tag);
        return R.success(umsCommendService.wonderfulCommend(id, tag));
    }
}
