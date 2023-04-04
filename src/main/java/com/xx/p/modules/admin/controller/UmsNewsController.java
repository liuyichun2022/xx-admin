package com.xx.p.modules.admin.controller;

import com.xx.p.common.api.PageR;
import com.xx.p.common.api.R;
import com.xx.p.modules.admin.dto.AddNewsDTO;
import com.xx.p.modules.admin.dto.NewsDTO;
import com.xx.p.modules.admin.dto.UpdateCommendStatusDTO;
import com.xx.p.modules.admin.module.entity.UmsAdmin;
import com.xx.p.modules.admin.service.UmsNewsService;
import com.xx.p.modules.admin.util.LoginUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
@Api(tags = "UmsNewsController")
@Tag(name = "UmsNewsController",description = "新闻管理")
@RequestMapping("/news")
@Slf4j
public class UmsNewsController {

    @Resource
    private UmsNewsService umsNewsService;

    @ApiOperation("新闻列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public R<PageR<NewsDTO>> getList(@RequestParam(value = "title",required = false) String title,
                                     @RequestParam(value = "publisher",required = false) String publisher,
                                     @RequestParam(value = "publishStatus",required = false) Integer pubStatus,
                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        log.info("查询新闻列表, title: {} ,publisher:{},pageNum:{}, pageSize:{}", title,publisher, pageNum, pageSize);
        return R.success(umsNewsService.getList(title,publisher, pubStatus,pageNum, pageSize));
    }

    @ApiOperation("开启评论")
    @RequestMapping(value = "/update/commendStatus", method = RequestMethod.POST)
    @ResponseBody
    public R<Boolean> updateCommendStatus( @RequestParam(value = "id") Integer id,
                                           @RequestParam(value = "commendStatus") Integer commendStatus) {
        log.info("是否开启用户评论, newsId: {} , commendStatus:{}", id, commendStatus);
        UpdateCommendStatusDTO updateCommendStatusDTO  = new UpdateCommendStatusDTO();
        updateCommendStatusDTO.setId(id).setCommendStatus(commendStatus);
        umsNewsService.updateCommendStatus(updateCommendStatusDTO);
        return R.success(true);
    }


    @ApiOperation("创建新闻")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public R<Boolean> add(@Valid @RequestBody AddNewsDTO addNewsDTO) {
        log.info("创建新闻, addNewsDTO: {} ", addNewsDTO.toString());
        umsNewsService.add(addNewsDTO);
        return R.success(true);
    }

    @ApiOperation("创建新闻")
    @RequestMapping(value = "/getDetail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public R<NewsDTO> getDetail(@PathVariable(value = "id") Long id) {
        log.info("查询新闻详情, newsId: {} ", id);
        return R.success(umsNewsService.getById(id));
    }

    @ApiOperation("新闻发布")
    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    @ResponseBody
    public R<Boolean> publish(@RequestParam(value = "id") Long id, @RequestParam(value = "pubStatus") Integer pubStatus) {
        UmsAdmin admin = LoginUtil.getCurrentUser();
        log.info("操作人Id:{}, 账户名:{}, 发布新闻, newsId: {}, pubStatus:{} ",admin.getId(), admin.getUsername(), id, pubStatus);
        umsNewsService.publish(id, pubStatus);
        return R.success(true);
    }


    @ApiOperation("新闻删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public R<Boolean> delete(@RequestParam(value = "id") Long id) {
        UmsAdmin admin = LoginUtil.getCurrentUser();
        log.info("操作人Id:{}, 账户名:{}, 新闻删除, newsId: {} ",admin.getId(), admin.getUsername(), id);
        return R.success(umsNewsService.delete(id));
    }
}
