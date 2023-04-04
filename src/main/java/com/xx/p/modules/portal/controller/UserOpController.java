package com.xx.p.modules.portal.controller;

import com.xx.p.common.api.R;
import com.xx.p.modules.portal.service.UserOperationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@Api(tags = "UserOpController")
@Tag(name = "UserOpController", description = "留言板")
@RequestMapping("/app/api/op")
public class UserOpController {

    @Resource
    private UserOperationService userOperationService;

    @ApiOperation("评论")
    @RequestMapping(value = "/commend", method = RequestMethod.GET)
    @ResponseBody
    public R<Boolean> commend( @RequestParam(value = "newsId")  Long newsId, @RequestParam(value = "msg") String msg) {
        return R.success(userOperationService.commend(newsId, msg));
    }

    @ApiOperation("点赞")
    @RequestMapping(value = "/like/{newsId}", method = RequestMethod.GET)
    @ResponseBody
    public R<Boolean> like(@PathVariable Long newsId) {
        return R.success(userOperationService.likeNews(newsId));
    }

    @ApiOperation("取消点赞")
    @RequestMapping(value = "/notLike/{newsId}", method = RequestMethod.DELETE)
    @ResponseBody
    public R<Boolean> notLike(@PathVariable Long newsId) {
        return R.success(userOperationService.cancelLike(newsId));
    }

    @ApiOperation("收藏")
    @RequestMapping(value = "/collect/{newsId}", method = RequestMethod.GET)
    @ResponseBody
    public R<Boolean> collect(@PathVariable Long newsId) {
        return R.success(userOperationService.collectNews(newsId));
    }

    @ApiOperation("取消收藏")
    @RequestMapping(value = "/removeCollect/{newsId}", method = RequestMethod.DELETE)
    @ResponseBody
    public R<Boolean> removeCollect(@PathVariable Long newsId) {
        return R.success(userOperationService.collectNews(newsId));
    }

}
