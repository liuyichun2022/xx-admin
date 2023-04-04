package com.xx.p.common.util;

import cn.hutool.core.text.StrFormatter;
import com.xx.p.common.constants.RedisConstants;

import java.util.HashMap;
import java.util.Map;

public class RedisKeyUtil {

    public static String getNewsCacheKey(Long newsId) {
        Map<String, Long> paramMap = new HashMap<>();
        paramMap.put("newsId", newsId);
        return StrFormatter.format(RedisConstants.XX_NEWS_DETAIL, paramMap, false);
    }

}
