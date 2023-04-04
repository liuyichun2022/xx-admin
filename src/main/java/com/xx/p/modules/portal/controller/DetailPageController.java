package com.xx.p.modules.portal.controller;

import com.xx.p.common.api.PageR;
import com.xx.p.common.api.R;
import com.xx.p.modules.portal.dto.NmsNewsDetailDTO;
import com.xx.p.modules.portal.module.entity.NmsCommendRecord;
import com.xx.p.modules.portal.service.NewsDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@Api(tags = "NewsDetailController")
@Tag(name = "NewsDetailController", description = "详情页")
@RequestMapping("/app/api/news")
public class DetailPageController {
    @Resource
    private NewsDetailService newsDetailService;


    @ApiOperation("新闻详情页")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    public R<NmsNewsDetailDTO> getById(@RequestParam(value = "id") Long id) {
        return R.success(newsDetailService.getById(id));
    }

    @ApiOperation("精选留言")
    @RequestMapping(value = "/getCommends", method = RequestMethod.GET)
    @ResponseBody
    public R<PageR<NmsCommendRecord>> getById(@RequestParam(value = "newsId") Long newsId, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                              @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return R.success(newsDetailService.getCommendsById(newsId, pageNum, pageSize));
    }

}
