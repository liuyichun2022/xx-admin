package com.xx.p.modules.portal.controller;

import com.xx.p.common.api.R;
import com.xx.p.modules.portal.dto.NewsGroupListDTO;
import com.xx.p.modules.portal.service.MyPageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@Api(tags = "MyController")
@Tag(name = "MyController", description = "我的")
@RequestMapping("/app/api/my")
public class MyPageController {

    @Resource
    private MyPageService myPageService;

    @ApiOperation("收藏列表")
    @RequestMapping(value = "/collectList", method = RequestMethod.GET)
    @ResponseBody
    public R<List<NewsGroupListDTO>> myCollectList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                   @RequestParam(value = "pageSize", defaultValue = "8") Integer pageSize) {
        return R.success(myPageService.myCollectList(pageNum, pageSize));
    }

}
