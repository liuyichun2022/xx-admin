package com.xx.p.modules.portal.service;

import com.xx.p.modules.portal.dto.NewsGroupListDTO;

import java.util.List;

public interface HomeService {

    List<NewsGroupListDTO> newsList(Integer pageNum, Integer pageSize);
}
