package com.xx.p.modules.portal.controller;

import com.xx.p.common.api.R;
import com.xx.p.modules.portal.dto.NewsGroupListDTO;
import com.xx.p.modules.portal.service.HomeService;
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
@Api(tags = "HomePageController")
@Tag(name = "HomePageController", description = "首页")
@RequestMapping("/app/api/home")
public class HomePageController {

    @Resource
    private HomeService homeService;

    @ApiOperation("新闻列表")
    @RequestMapping(value = "/news/list", method = RequestMethod.GET)
    @ResponseBody
    public R<List<NewsGroupListDTO>> newProductList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                    @RequestParam(value = "pageSize", defaultValue = "8") Integer pageSize) {
        return R.success(homeService.newsList(pageNum,pageSize));
    }
}
